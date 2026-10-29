-- Caché local de CVEs traídos desde la API 2.0 del NVD.
-- La app busca aquí (rápido y sin límite de consultas) y una sincronización
-- programada mantiene los datos al día.
-- Puntaje, severidad y CWE pueden venir vacíos: hay CVEs que el NVD aún no califica.
-- Si no hay puntaje se usa la escala de riesgo propia de TinyHat.

CREATE TABLE IF NOT EXISTS cve (
    id_cve BIGINT NOT NULL AUTO_INCREMENT,
    codigo_cve VARCHAR(30) NOT NULL,
    descripcion TEXT DEFAULT NULL,
    puntaje_cvss DECIMAL(3,1) DEFAULT NULL,
    severidad VARCHAR(20) DEFAULT NULL,
    cwe VARCHAR(30) DEFAULT NULL,
    fecha_publicacion DATETIME DEFAULT NULL,
    fecha_modificacion DATETIME DEFAULT NULL,
    fecha_sincronizacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id_cve),
    UNIQUE KEY uk_cve_codigo (codigo_cve),
    KEY idx_cve_cwe (cwe),
    FULLTEXT KEY ft_cve_descripcion (descripcion)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
