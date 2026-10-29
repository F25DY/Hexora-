CREATE TABLE network_discovery_snapshot (
    id BIGINT NOT NULL AUTO_INCREMENT,
    subnet VARCHAR(18) NOT NULL,
    source VARCHAR(255) NOT NULL,
    observed_at DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    KEY idx_network_discovery_snapshot_observed (observed_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE network_discovery_snapshot_device (
    id BIGINT NOT NULL AUTO_INCREMENT,
    snapshot_id BIGINT NOT NULL,
    address VARCHAR(45) NOT NULL,
    mac_address VARCHAR(17) DEFAULT NULL,
    host_name VARCHAR(255) NOT NULL,
    detection_method VARCHAR(40) NOT NULL,
    status VARCHAR(20) NOT NULL,
    registered BOOLEAN NOT NULL,
    first_detected_at DATETIME(6) NOT NULL,
    last_detected_at DATETIME(6) NOT NULL,
    consecutive_misses INT NOT NULL,
    PRIMARY KEY (id),
    KEY idx_network_discovery_snapshot_device_snapshot (snapshot_id),
    CONSTRAINT fk_network_discovery_snapshot_device_snapshot
        FOREIGN KEY (snapshot_id) REFERENCES network_discovery_snapshot (id)
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
