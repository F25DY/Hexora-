ALTER TABLE network_host
    ADD COLUMN consecutive_failures INT NOT NULL DEFAULT 0;

CREATE TABLE network_detected_device (
    id BIGINT NOT NULL AUTO_INCREMENT,
    address VARCHAR(45) NOT NULL,
    mac_address VARCHAR(17) DEFAULT NULL,
    host_name VARCHAR(255) NOT NULL,
    subnet VARCHAR(18) NOT NULL,
    status VARCHAR(20) NOT NULL,
    consecutive_misses INT NOT NULL DEFAULT 0,
    first_detected_at DATETIME(6) NOT NULL,
    last_detected_at DATETIME(6) NOT NULL,
    last_status_changed_at DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_network_detected_device_address (address),
    KEY idx_network_detected_device_mac (mac_address),
    KEY idx_network_detected_device_status (status, last_detected_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE network_discovery_event (
    id BIGINT NOT NULL AUTO_INCREMENT,
    detected_device_id BIGINT DEFAULT NULL,
    address VARCHAR(45) NOT NULL,
    device_name VARCHAR(255) NOT NULL,
    event_type VARCHAR(30) NOT NULL,
    description VARCHAR(255) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    KEY idx_network_discovery_event_created (created_at),
    KEY idx_network_discovery_event_device (detected_device_id, created_at),
    CONSTRAINT fk_network_discovery_event_device
        FOREIGN KEY (detected_device_id) REFERENCES network_detected_device (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
