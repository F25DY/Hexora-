ALTER TABLE network_detected_device
    ADD COLUMN gateway BOOLEAN NOT NULL DEFAULT FALSE,
    ADD COLUMN gateway_address VARCHAR(45) DEFAULT NULL;

ALTER TABLE network_discovery_snapshot_device
    ADD COLUMN gateway BOOLEAN NOT NULL DEFAULT FALSE,
    ADD COLUMN gateway_address VARCHAR(45) DEFAULT NULL;

UPDATE network_detected_device
SET gateway = TRUE, gateway_address = address
WHERE detection_method LIKE '%Puerta de enlace%';

UPDATE network_discovery_snapshot_device
SET gateway = TRUE, gateway_address = address
WHERE detection_method LIKE '%Puerta de enlace%';
