package com.hexora.hexora.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexora.hexora.model.AreaAuditable;
import com.hexora.hexora.model.Auditoria;

public interface AreaAuditableRepository extends JpaRepository<AreaAuditable, Long> {

    List<AreaAuditable> findByAuditoria(Auditoria auditoria);

}
