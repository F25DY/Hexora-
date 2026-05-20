package com.hexora.hexora.model;

public class Auditoria {

    //Atributos

    private Long id;
    private String nombre;
    private String tipoAuditoria;
    private String fechaInico;
    private String fechaFin;
    private String estado;
    private Empresa empresa;
    
    //Constructores
    public Auditoria() {
    }

    public Auditoria(Long id, String nombre, String tipoAuditoria, String fechaInico, String fechaFin, String estado,
            Empresa empresa) {
        this.id = id;
        this.nombre = nombre;
        this.tipoAuditoria = tipoAuditoria;
        this.fechaInico = fechaInico;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.empresa = empresa;
    }

    //Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getFechaInico() {
        return fechaInico;
    }

    public void setFechaInico(String fechaInico) {
        this.fechaInico = fechaInico;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
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
