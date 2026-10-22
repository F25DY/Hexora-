package com.hexora.hexora.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexora.hexora.model.Cve;

public interface CveRepository extends JpaRepository<Cve, Long> {

    // Busca un CVE exacto, por ejemplo "CVE-2021-44228".
    Optional<Cve> findByCodigoCve(String codigoCve);

    // Para el buscador: coincide con el código o con una palabra de la descripción.
    // Devuelve máximo 10 resultados para no llenar la pantalla.
    List<Cve> findTop10ByCodigoCveContainingIgnoreCaseOrDescripcionContainingIgnoreCase(
            String codigo, String texto);
}
