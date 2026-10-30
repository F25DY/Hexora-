ALTER TABLE network_detected_device
    MODIFY COLUMN detection_method VARCHAR(255) NOT NULL DEFAULT 'Sin respuesta actual';