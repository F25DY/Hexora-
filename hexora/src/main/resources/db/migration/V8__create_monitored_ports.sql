CREATE TABLE monitored_port (
    id BIGINT NOT NULL AUTO_INCREMENT,
    network_host_id BIGINT NOT NULL,
    port INT NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    PRIMARY KEY (id),
    UNIQUE KEY uk_monitored_port_host_port (network_host_id, port),
    CONSTRAINT fk_monitored_port_host FOREIGN KEY (network_host_id) REFERENCES network_host (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
