package com.hexora.hexora.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexora.hexora.model.Auditoria;
import com.hexora.hexora.model.Empresa;

public interface AuditoriaRepository extends JpaRepository<Auditoria, Long> {

    long countByEmpresa(Empresa empresa);

}
