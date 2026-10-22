package com.hexora.hexora.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import com.hexora.hexora.model.Cve;
import com.hexora.hexora.repository.CveRepository;

/**
 * Busca CVEs. Primero mira en la base de datos local (rápido). Si no encuentra
 * nada, le pregunta a la API del NVD, guarda lo que llegue y vuelve a buscar.
 * Así la tabla "cve" se va llenando sola con lo que la gente consulta.
 */
@Service
public class CveService {

    private static final Logger log = LoggerFactory.getLogger(CveService.class);

    private static final String URL_NVD = "https://services.nvd.nist.gov/rest/json/cves/2.0";
    private static final Pattern PATRON_CVE = Pattern.compile("^CVE-\\d{4}-\\d{4,}$", Pattern.CASE_INSENSITIVE);

    private final CveRepository cveRepository;
    private final RestClient restClient;
    private final String apiKey;

    // NVD_API_KEY sale del archivo .env. Si está vacía, igual funciona, pero con menos consultas permitidas.
    public CveService(CveRepository cveRepository, @Value("${NVD_API_KEY:}") String apiKey) {
        this.cveRepository = cveRepository;
        this.apiKey = apiKey;
        this.restClient = RestClient.builder().baseUrl(URL_NVD).build();
    }

    public List<Cve> buscar(String texto) {
        String q = texto == null ? "" : texto.trim();
        if (q.length() < 3) {
            return List.of();
        }

        List<Cve> locales = cveRepository
                .findTop10ByCodigoCveContainingIgnoreCaseOrDescripcionContainingIgnoreCase(q, q);
        if (!locales.isEmpty()) {
            return locales;
        }

        traerDesdeNvd(q);
        return cveRepository.findTop10ByCodigoCveContainingIgnoreCaseOrDescripcionContainingIgnoreCase(q, q);
    }

    // ---------------------------------------------------------------
    // Conexión con el NVD
    // ---------------------------------------------------------------

    private void traerDesdeNvd(String q) {
        boolean esCodigo = PATRON_CVE.matcher(q).matches();
        String parametro = esCodigo ? "cveId" : "keywordSearch";
        String valor = esCodigo ? q.toUpperCase() : q;

        try {
            Map<String, Object> respuesta = restClient.get()
                    .uri(uri -> uri.queryParam(parametro, "{valor}")
                            .queryParam("resultsPerPage", 10)
                            .build(valor))
                    .headers(h -> {
                        if (!apiKey.isBlank()) {
                            h.set("apiKey", apiKey);
                        }
                    })
                    .retrieve()
                    .body(new ParameterizedTypeReference<Map<String, Object>>() {
                    });

            if (respuesta == null) {
                return;
            }

            for (Map<String, Object> item : lista(respuesta.get("vulnerabilities"))) {
                Cve cve = convertir(mapa(item.get("cve")));
                if (cve != null && cveRepository.findByCodigoCve(cve.getCodigoCve()).isEmpty()) {
                    cveRepository.save(cve);
                }
            }
        } catch (RestClientException e) {
            // Si el NVD no responde o nos pasamos del límite, la app no se cae: solo avisa en el log.
            log.warn("No se pudo consultar el NVD para '{}': {}", q, e.getMessage());
        }
    }

    // ---------------------------------------------------------------
    // Convertir la respuesta del NVD en nuestro objeto Cve
    // ---------------------------------------------------------------

    private Cve convertir(Map<String, Object> datos) {
        Object id = datos.get("id");
        if (!(id instanceof String codigo) || codigo.isBlank()) {
            return null;
        }

        Cve cve = new Cve();
        cve.setCodigoCve(codigo);
        cve.setDescripcion(leerDescripcion(datos));
        cve.setFechaPublicacion(leerFecha(datos.get("published")));
        cve.setFechaModificacion(leerFecha(datos.get("lastModified")));
        cve.setCwe(leerCwe(datos));
        leerPuntaje(datos, cve);
        return cve;
    }

    // Toma la descripción en inglés; si no hay, la primera que venga.
    private String leerDescripcion(Map<String, Object> datos) {
        String primera = null;
        for (Map<String, Object> d : lista(datos.get("descriptions"))) {
            Object valor = d.get("value");
            if (!(valor instanceof String texto)) {
                continue;
            }
            if ("en".equals(d.get("lang"))) {
                return texto;
            }
            if (primera == null) {
                primera = texto;
            }
        }
        return primera;
    }

    // Busca el primer código que empiece con "CWE-" (ignora "NVD-CWE-Other", etc.).
    private String leerCwe(Map<String, Object> datos) {
        for (Map<String, Object> debilidad : lista(datos.get("weaknesses"))) {
            for (Map<String, Object> d : lista(debilidad.get("description"))) {
                Object valor = d.get("value");
                if (valor instanceof String texto && texto.startsWith("CWE-")) {
                    return texto;
                }
            }
        }
        return null;
    }

    // Usa el puntaje CVSS más moderno que exista (4.0, 3.1, 3.0 o 2.0).
    private void leerPuntaje(Map<String, Object> datos, Cve cve) {
        Map<String, Object> metricas = mapa(datos.get("metrics"));
        String[] versiones = { "cvssMetricV40", "cvssMetricV31", "cvssMetricV30", "cvssMetricV2" };

        for (String version : versiones) {
            List<Map<String, Object>> entradas = lista(metricas.get(version));
            if (entradas.isEmpty()) {
                continue;
            }

            // Preferimos la entrada marcada como "Primary"; si no hay, la primera.
            Map<String, Object> elegida = entradas.get(0);
            for (Map<String, Object> entrada : entradas) {
                if ("Primary".equals(entrada.get("type"))) {
                    elegida = entrada;
                    break;
                }
            }

            Map<String, Object> cvssData = mapa(elegida.get("cvssData"));
            if (cvssData.get("baseScore") instanceof Number n) {
                cve.setPuntajeCvss(BigDecimal.valueOf(n.doubleValue()));
            }

            // En CVSS 2.0 la severidad viene afuera de cvssData.
            Object severidad = cvssData.get("baseSeverity");
            if (severidad == null) {
                severidad = elegida.get("baseSeverity");
            }
            if (severidad instanceof String s) {
                cve.setSeveridad(s);
            }
            return;
        }
    }

    private LocalDateTime leerFecha(Object valor) {
        if (valor instanceof String texto) {
            try {
                return LocalDateTime.parse(texto);
            } catch (java.time.format.DateTimeParseException e) {
                return null;
            }
        }
        return null;
    }

    // ---------------------------------------------------------------
    // Ayudantes para leer el JSON sin errores si falta algún dato
    // ---------------------------------------------------------------

    @SuppressWarnings("unchecked")
    private static Map<String, Object> mapa(Object o) {
        return o instanceof Map ? (Map<String, Object>) o : Map.of();
    }

    @SuppressWarnings("unchecked")
    private static List<Map<String, Object>> lista(Object o) {
        return o instanceof List ? (List<Map<String, Object>>) o : List.of();
    }
}
