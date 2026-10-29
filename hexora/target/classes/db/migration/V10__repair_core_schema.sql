CREATE TABLE IF NOT EXISTS empresa (
    id_empresa BIGINT NOT NULL AUTO_INCREMENT,
    nit VARCHAR(50) DEFAULT NULL,
    nombre VARCHAR(100) NOT NULL,
    sector VARCHAR(80) DEFAULT NULL,
    PRIMARY KEY (id_empresa)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS auditoria (
    id_auditoria BIGINT NOT NULL AUTO_INCREMENT,
    estado VARCHAR(30) NOT NULL,
    fecha_fin DATE DEFAULT NULL,
    fecha_inico DATE NOT NULL,
    nombre VARCHAR(150) NOT NULL,
    tipo_auditoria VARCHAR(80) NOT NULL,
    empresa_id BIGINT NOT NULL,
    PRIMARY KEY (id_auditoria),
    KEY fk_auditoria_empresa (empresa_id),
    CONSTRAINT fk_auditoria_empresa FOREIGN KEY (empresa_id) REFERENCES empresa (id_empresa)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS area_auditable (
    id_area BIGINT NOT NULL AUTO_INCREMENT,
    descripcion TEXT DEFAULT NULL,
    nombre VARCHAR(150) NOT NULL,
    id_auditoria BIGINT NOT NULL,
    PRIMARY KEY (id_area),
    KEY fk_area_auditable_auditoria (id_auditoria),
    CONSTRAINT fk_area_auditable_auditoria FOREIGN KEY (id_auditoria) REFERENCES auditoria (id_auditoria)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS vulnerabilidad (
    id BIGINT NOT NULL AUTO_INCREMENT,
    confidencialidad INT NOT NULL,
    cve VARCHAR(30) DEFAULT NULL,
    disponibilidad INT NOT NULL,
    estado VARCHAR(30) NOT NULL,
    fecha_descubrimiento DATE NOT NULL,
    herramienta VARCHAR(100) DEFAULT NULL,
    host_afectado VARCHAR(150) DEFAULT NULL,
    impacto DECIMAL(4,2) NOT NULL,
    integridad INT NOT NULL,
    ip_afectada VARCHAR(45) DEFAULT NULL,
    nivel_riesgo VARCHAR(20) NOT NULL,
    nombre VARCHAR(150) NOT NULL,
    plan_mitigacion TEXT DEFAULT NULL,
    probabilidad INT NOT NULL,
    puerto_afectado INT DEFAULT NULL,
    servicio VARCHAR(100) DEFAULT NULL,
    tipo VARCHAR(100) NOT NULL,
    id_area BIGINT NOT NULL,
    PRIMARY KEY (id),
    KEY fk_vulnerabilidad_area (id_area),
    CONSTRAINT fk_vulnerabilidad_area FOREIGN KEY (id_area) REFERENCES area_auditable (id_area)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
