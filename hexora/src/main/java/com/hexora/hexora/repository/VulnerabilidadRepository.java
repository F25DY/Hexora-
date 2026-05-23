package com.hexora.hexora.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexora.hexora.model.AreaAuditable;
import com.hexora.hexora.model.Vulnerabilidad;

public interface VulnerabilidadRepository extends JpaRepository<Vulnerabilidad, Long> {

    List<Vulnerabilidad> findByAreaAuditable(AreaAuditable areaAuditable);
}
