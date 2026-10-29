-- Datos del CVE que sirven para sugerir valores en el formulario de vulnerabilidad.
-- Los impactos se guardan tal como los entrega el NVD (NONE, LOW, HIGH; en CVSS 2.0: NONE, PARTIAL, COMPLETE).
-- La conversión a números del 0 al 10 se hace en cve-buscador.js, para poder ajustarla sin tocar la base de datos.
--
-- version_cvss: versión del CVSS de donde salieron los datos ("3.1", "3.0", "2.0", "4.0"),
-- o "NINGUNA" si el NVD todavía no califica ese CVE. Si está en NULL, el CVE se guardó antes
-- de esta migración y se completa solo la primera vez que alguien lo elige.

ALTER TABLE cve
    ADD COLUMN impacto_confidencialidad VARCHAR(15) DEFAULT NULL,
    ADD COLUMN impacto_integridad VARCHAR(15) DEFAULT NULL,
    ADD COLUMN impacto_disponibilidad VARCHAR(15) DEFAULT NULL,
    ADD COLUMN puntaje_explotabilidad DECIMAL(3,1) DEFAULT NULL,
    ADD COLUMN version_cvss VARCHAR(10) DEFAULT NULL,
    ADD COLUMN referencias_parche TEXT DEFAULT NULL;
