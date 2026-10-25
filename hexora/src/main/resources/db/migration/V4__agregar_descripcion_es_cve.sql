-- Descripción del CVE traducida al español.
-- Se llena sola la primera vez que alguien elige ese CVE en el formulario,
-- y queda guardada para no volver a traducirla (así se ahorra cuota del traductor).

ALTER TABLE cve
    ADD COLUMN descripcion_es TEXT DEFAULT NULL AFTER descripcion;
