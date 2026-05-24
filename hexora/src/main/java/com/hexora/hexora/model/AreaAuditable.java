package com.hexora.hexora.model;

// Frontend-only mode: JPA annotations disabled
public class AreaAuditable {

    private Long id;
    private String nombre;
    private String descripcion;
    private Auditoria auditoria;

    //Constructores
    public AreaAuditable() {
    }

    public AreaAuditable(Long id, String nombre, String descripcion, Auditoria auditoria) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.auditoria = auditoria;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Auditoria getAuditoria() {
        return auditoria;
    }

    public void setAuditoria(Auditoria auditoria) {
        this.auditoria = auditoria;
    }

    
}
