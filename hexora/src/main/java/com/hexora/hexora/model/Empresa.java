package com.hexora.hexora.model;

// Frontend-only mode: JPA annotations disabled
public class Empresa {

    //Creamos columnas

    private Long id;

    private String nombre;

    private String nit;
    
    private String sector;

    //Constructores
    public Empresa() {
    }

    public Empresa(Long id, String nombre, String nit, String sector) {
        this.id = id;
        this.nombre = nombre;
        this.nit = nit;
        this.sector = sector;
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

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

}
