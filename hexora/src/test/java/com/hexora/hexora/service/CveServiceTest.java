package com.hexora.hexora.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.hexora.hexora.model.Cve;
import com.hexora.hexora.repository.CveRepository;

class CveServiceTest {

    @Test
    void usesOneCvssVersionForScoreAndImpactWhenSeveralAreAvailable() {
        CveService service = new CveService(mock(CveRepository.class), mock(TraductorService.class), "");
        Cve cve = new Cve();
        Map<String, Object> cvss31 = Map.of(
                "type", "Primary",
                "cvssData", Map.of(
                        "baseScore", 7.5,
                        "baseSeverity", "HIGH",
                        "confidentialityImpact", "HIGH",
                        "integrityImpact", "LOW",
                        "availabilityImpact", "NONE"),
                "exploitabilityScore", 3.9);
        Map<String, Object> cvss40 = Map.of(
                "type", "Primary",
                "cvssData", Map.of("baseScore", 9.8, "baseSeverity", "CRITICAL"));
        Map<String, Object> datos = Map.of("metrics", Map.of(
                "cvssMetricV40", List.of(cvss40),
                "cvssMetricV31", List.of(cvss31)));

        service.leerMetricas(datos, cve);

        assertEquals(new BigDecimal("7.5"), cve.getPuntajeCvss());
        assertEquals("HIGH", cve.getSeveridad());
        assertEquals("3.1", cve.getVersionCvss());
        assertEquals("HIGH", cve.getImpactoConfidencialidad());
        assertEquals("LOW", cve.getImpactoIntegridad());
        assertEquals("NONE", cve.getImpactoDisponibilidad());
        assertEquals(new BigDecimal("3.9"), cve.getPuntajeExplotabilidad());
    }
}