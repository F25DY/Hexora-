package com.hexora.hexora.model;

import java.math.BigDecimal;
import java.time.LocalDate;

// Frontend-only mode: JPA annotations disabled
public class Vulnerabilidad {
    private Long id;

    private String nombre;

    private String tipo;

    private String herramienta;

    private String host;

    private String ipAfectada;

    private Integer puerto;

    private String servicio;

    private String cve;

    private int confidencialidad;

    private int integridad;

    private int disponibilidad;

    private int probabilidad;

    private BigDecimal impacto;

    private String nivelRiesgo;

    private String planMitigacion;

    private String estado;

    private LocalDate fechaDeteccion;

    private AreaAuditable areaAuditable;

    //Constructores

    public Vulnerabilidad() {
    }
    
    public Vulnerabilidad(Long id, String nombre, String tipo, String herramienta, String host, String ipAfectada,
            int puerto, String servicio, String cve, int confidencialidad, int integridad, int disponibilidad,
            int probabilidad, BigDecimal impacto, String nivelRiesgo, String planMitigacion, String estado,
            LocalDate fechaDeteccion, AreaAuditable areaAuditable) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.herramienta = herramienta;
        this.host = host;
        this.ipAfectada = ipAfectada;
        this.puerto = puerto;
        this.servicio = servicio;
        this.cve = cve;
        this.confidencialidad = confidencialidad;
        this.integridad = integridad;
        this.disponibilidad = disponibilidad;
        this.probabilidad = probabilidad;
        this.impacto = impacto;
        this.nivelRiesgo = nivelRiesgo;
        this.planMitigacion = planMitigacion;
        this.estado = estado;
        this.fechaDeteccion = fechaDeteccion;
        this.areaAuditable = areaAuditable;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getHerramienta() {
        return herramienta;
    }

    public void setHerramienta(String herramienta) {
        this.herramienta = herramienta;
    }

    public String getIpAfectada() {
        return ipAfectada;
    }

    public void setIpAfectada(String ipAfectada) {
        this.ipAfectada = ipAfectada;
    }

    public int getPuerto() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getCve() {
        return cve;
    }

    public void setCve(String cve) {
        this.cve = cve;
    }

    public int getConfidencialidad() {
        return confidencialidad;
    }

    public void setConfidencialidad(int confidencialidad) {
        this.confidencialidad = confidencialidad;
    }

    public int getIntegridad() {
        return integridad;
    }

    public void setIntegridad(int integridad) {
        this.integridad = integridad;
    }

    public int getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(int disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public int getProbabilidad() {
        return probabilidad;
    }

    public void setProbabilidad(int probabilidad) {
        this.probabilidad = probabilidad;
    }

    public BigDecimal getImpacto() {
        return impacto;
    }

    public void setImpacto(BigDecimal impacto) {
        this.impacto = impacto;
    }

    public String getNivelRiesgo() {
        return nivelRiesgo;
    }

    public void setNivelRiesgo(String nivelRiesgo) {
        this.nivelRiesgo = nivelRiesgo;
    }

    public String getPlanMitigacion() {
        return planMitigacion;
    }

    public void setPlanMitigacion(String planMitigacion) {
        this.planMitigacion = planMitigacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getFechaDeteccion() {
        return fechaDeteccion;
    }

    public void setFechaDeteccion(LocalDate fechaDeteccion) {
        this.fechaDeteccion = fechaDeteccion;
    }

    public AreaAuditable getAreaAuditable() {
        return areaAuditable;
    }

    public void setAreaAuditable(AreaAuditable areaAuditable) {
        this.areaAuditable = areaAuditable;
    }

    

    
}
