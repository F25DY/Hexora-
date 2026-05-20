package com.hexora.hexora.model;

import java.time.LocalDate;

public class Vulnerabilidad {
    private Long id;
    private String nombre;
    private String descripcion;
    private String tipo;
    private String methodDescubrimiento;

    private String host;
    private int puerto;
    private String protocolo;
    private String servicio;

    private String cve;
    private double cvss;
    private String cwe;

    private int confidencialidad;
    private int integridad;
    private int disponibilidad;
    private int probabilidad;

    private Double impacto;
    private Double riesgo;
    private String nivelRiesgo;

    private String planMitigacion;
    private String recomendacionTecnica;
    private String estado;
    private String observaciones;
    private LocalDate fechaDeteccion;

    private AreaAuditable areaAuditable;

    //Constructores

    public Vulnerabilidad() {
    }

    public Vulnerabilidad(Long id, String nombre, String descripcion, String tipo, String methodDescubrimiento,
            String host, int puerto, String protocolo, String servicio, String cve, double cvss, String cwe,
            int confidencialidad, int integridad, int disponibilidad, int probabilidad, Double impacto, Double riesgo,
            String nivelRiesgo, String planMitigacion, String recomendacionTecnica, String estado, String observaciones,
            LocalDate fechaDeteccion, AreaAuditable areaAuditable) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.methodDescubrimiento = methodDescubrimiento;
        this.host = host;
        this.puerto = puerto;
        this.protocolo = protocolo;
        this.servicio = servicio;
        this.cve = cve;
        this.cvss = cvss;
        this.cwe = cwe;
        this.confidencialidad = confidencialidad;
        this.integridad = integridad;
        this.disponibilidad = disponibilidad;
        this.probabilidad = probabilidad;
        this.impacto = impacto;
        this.riesgo = riesgo;
        this.nivelRiesgo = nivelRiesgo;
        this.planMitigacion = planMitigacion;
        this.recomendacionTecnica = recomendacionTecnica;
        this.estado = estado;
        this.observaciones = observaciones;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMethodDescubrimiento() {
        return methodDescubrimiento;
    }

    public void setMethodDescubrimiento(String methodDescubrimiento) {
        this.methodDescubrimiento = methodDescubrimiento;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPuerto() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

    public String getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
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

    public double getCvss() {
        return cvss;
    }

    public void setCvss(double cvss) {
        this.cvss = cvss;
    }

    public String getCwe() {
        return cwe;
    }

    public void setCwe(String cwe) {
        this.cwe = cwe;
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

    public Double getImpacto() {
        return impacto;
    }

    public void setImpacto(Double impacto) {
        this.impacto = impacto;
    }

    public Double getRiesgo() {
        return riesgo;
    }

    public void setRiesgo(Double riesgo) {
        this.riesgo = riesgo;
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

    public String getRecomendacionTecnica() {
        return recomendacionTecnica;
    }

    public void setRecomendacionTecnica(String recomendacionTecnica) {
        this.recomendacionTecnica = recomendacionTecnica;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
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
