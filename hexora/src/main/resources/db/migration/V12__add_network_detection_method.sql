ALTER TABLE network_detected_device
    ADD COLUMN detection_method VARCHAR(40) NOT NULL DEFAULT 'Sin respuesta actual';
