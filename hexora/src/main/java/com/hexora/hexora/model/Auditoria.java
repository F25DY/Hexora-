package com.hexora.hexora.model;

import java.time.LocalDate;

// Frontend-only mode: JPA annotations disabled
public class Auditoria {

    //Definimos Id
    private Long idAuditoria;
    
    private String nombre;

    private String tipoAuditoria;

    private LocalDate fechaInico;

    private LocalDate fechaFin;

    private String estado;

    private Empresa empresa;

    //@ManyToOne
    //@JoinColumn(name = "auditor", nullable = false)
    //private Usuario auditor;
    
    //Constructores
    public Auditoria() {
    }

    public Auditoria(Long idAuditoria, String nombre, String tipoAuditoria, LocalDate fechaInico, LocalDate fechaFin, String estado,
            Empresa empresa) {
        this.idAuditoria = idAuditoria;
        this.nombre = nombre;
        this.tipoAuditoria = tipoAuditoria;
        this.fechaInico = fechaInico;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.empresa = empresa;
    }

    //Getters y Setters

    public Long getIdAuditoria() {
        return idAuditoria;
    }

    public void setIdAuditoria(Long idAuditoria) {
        this.idAuditoria = idAuditoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoAuditoria() {
        return tipoAuditoria;
    }

    public void setTipoAuditoria(String tipoAuditoria) {
        this.tipoAuditoria = tipoAuditoria;
    }

    public LocalDate getFechaInico() {
        return fechaInico;
    }

    public void setFechaInico(LocalDate fechaInico) {
        this.fechaInico = fechaInico;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    
}
