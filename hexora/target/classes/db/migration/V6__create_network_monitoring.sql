CREATE TABLE IF NOT EXISTS network_host (
    id BIGINT NOT NULL AUTO_INCREMENT,
    display_name VARCHAR(100) NOT NULL,
    address VARCHAR(255) NOT NULL,
    status VARCHAR(20) NOT NULL,
    latency_ms INT DEFAULT NULL,
    last_checked_at DATETIME(6) DEFAULT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    PRIMARY KEY (id),
    UNIQUE KEY uk_network_host_address (address)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS network_scan (
    id BIGINT NOT NULL AUTO_INCREMENT,
    host_id BIGINT NOT NULL,
    status VARCHAR(20) NOT NULL,
    host_status VARCHAR(20) NOT NULL,
    latency_ms INT DEFAULT NULL,
    ports_scanned INT NOT NULL,
    started_at DATETIME(6) NOT NULL,
    completed_at DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    KEY idx_network_scan_host_started (host_id, started_at),
    CONSTRAINT fk_network_scan_host FOREIGN KEY (host_id) REFERENCES network_host (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS port_scan_result (
    id BIGINT NOT NULL AUTO_INCREMENT,
    network_scan_id BIGINT NOT NULL,
    network_host_id BIGINT NOT NULL,
    port INT NOT NULL,
    service_name VARCHAR(50) NOT NULL,
    status VARCHAR(20) NOT NULL,
    detected_at DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_port_scan_result_scan_port (network_scan_id, port),
    KEY idx_port_scan_result_host_port_date (network_host_id, port, detected_at),
    CONSTRAINT fk_port_scan_result_scan FOREIGN KEY (network_scan_id) REFERENCES network_scan (id),
    CONSTRAINT fk_port_scan_result_host FOREIGN KEY (network_host_id) REFERENCES network_host (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS network_event (
    id BIGINT NOT NULL AUTO_INCREMENT,
    network_host_id BIGINT NOT NULL,
    network_scan_id BIGINT DEFAULT NULL,
    event_type VARCHAR(30) NOT NULL,
    description VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    KEY idx_network_event_host_created (network_host_id, created_at),
    CONSTRAINT fk_network_event_host FOREIGN KEY (network_host_id) REFERENCES network_host (id),
    CONSTRAINT fk_network_event_scan FOREIGN KEY (network_scan_id) REFERENCES network_scan (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
