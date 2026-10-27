package com.hexora.hexora.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Copia local de un CVE traído desde la API del NVD.
 * Representa la tabla "cve" (V3, V4 y V5 de las migraciones).
 */
@Entity
@Table(name = "cve")
public class Cve {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cve")
    private Long id;

    @Column(name = "codigo_cve", nullable = false, unique = true, length = 30)
    private String codigoCve;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "descripcion_es", columnDefinition = "TEXT")
    private String descripcionEs;

    @Column(name = "puntaje_cvss", precision = 3, scale = 1)
    private BigDecimal puntajeCvss;

    @Column(length = 20)
    private String severidad;

    @Column(length = 30)
    private String cwe;

    @Column(name = "fecha_publicacion")
    private LocalDateTime fechaPublicacion;

    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;

    @Column(name = "fecha_sincronizacion", nullable = false)
    private LocalDateTime fechaSincronizacion = LocalDateTime.now();

    // ---- Datos para sugerir valores en el formulario (V5) ----

    @Column(name = "impacto_confidencialidad", length = 15)
    private String impactoConfidencialidad;

    @Column(name = "impacto_integridad", length = 15)
    private String impactoIntegridad;

    @Column(name = "impacto_disponibilidad", length = 15)
    private String impactoDisponibilidad;

    @Column(name = "puntaje_explotabilidad", precision = 3, scale = 1)
    private BigDecimal puntajeExplotabilidad;

    @Column(name = "version_cvss", length = 10)
    private String versionCvss;

    @Column(name = "referencias_parche", columnDefinition = "TEXT")
    private String referenciasParche;

    public Cve() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoCve() {
        return codigoCve;
    }

    public void setCodigoCve(String codigoCve) {
        this.codigoCve = codigoCve;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcionEs() {
        return descripcionEs;
    }

    public void setDescripcionEs(String descripcionEs) {
        this.descripcionEs = descripcionEs;
    }

    public BigDecimal getPuntajeCvss() {
        return puntajeCvss;
    }

    public void setPuntajeCvss(BigDecimal puntajeCvss) {
        this.puntajeCvss = puntajeCvss;
    }

    public String getSeveridad() {
        return severidad;
    }

    public void setSeveridad(String severidad) {
        this.severidad = severidad;
    }

    public String getCwe() {
        return cwe;
    }

    public void setCwe(String cwe) {
        this.cwe = cwe;
    }

    public LocalDateTime getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDateTime fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public LocalDateTime getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(LocalDateTime fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public LocalDateTime getFechaSincronizacion() {
        return fechaSincronizacion;
    }

    public void setFechaSincronizacion(LocalDateTime fechaSincronizacion) {
        this.fechaSincronizacion = fechaSincronizacion;
    }

    public String getImpactoConfidencialidad() {
        return impactoConfidencialidad;
    }

    public void setImpactoConfidencialidad(String impactoConfidencialidad) {
        this.impactoConfidencialidad = impactoConfidencialidad;
    }

    public String getImpactoIntegridad() {
        return impactoIntegridad;
    }

    public void setImpactoIntegridad(String impactoIntegridad) {
        this.impactoIntegridad = impactoIntegridad;
    }

    public String getImpactoDisponibilidad() {
        return impactoDisponibilidad;
    }

    public void setImpactoDisponibilidad(String impactoDisponibilidad) {
        this.impactoDisponibilidad = impactoDisponibilidad;
    }

    public BigDecimal getPuntajeExplotabilidad() {
        return puntajeExplotabilidad;
    }

    public void setPuntajeExplotabilidad(BigDecimal puntajeExplotabilidad) {
        this.puntajeExplotabilidad = puntajeExplotabilidad;
    }

    public String getVersionCvss() {
        return versionCvss;
    }

    public void setVersionCvss(String versionCvss) {
        this.versionCvss = versionCvss;
    }

    public String getReferenciasParche() {
        return referenciasParche;
    }

    public void setReferenciasParche(String referenciasParche) {
        this.referenciasParche = referenciasParche;
    }
}