# Correcciones de la auditoría CVE

Escala de prioridad: **10 = corregir primero**. Los puntajes consideran impacto y exposición; el punto sobre cuotas externas es especialmente urgente si la aplicación se publica para usuarios no confiables.

## 1. Endpoints CVE y consumo de cuotas externas

**Prioridad: 9/10**

Las rutas de búsqueda y detalle permiten disparar consultas a NVD y MyMemory sin autenticación ni límite de solicitudes. Un uso automatizado podría agotar las cuotas de esos servicios y degradar la aplicación.

**Corrección propuesta:** limitar solicitudes por cliente y validar el acceso a estas rutas. Mantener la búsqueda local como primera opción y añadir límites de uso a las consultas externas.

**Referencias:** `CveController.java` (`/api/cves/buscar`, `/api/cves/{codigo}/detalle`), `CveService.java` (`consultarNvd`).

## 2. Datos CVSS de versiones distintas

**Prioridad: 8/10**

El puntaje y la severidad priorizan CVSS 4.0, mientras que los impactos y la versión guardada priorizan CVSS 3.1. Si NVD proporciona ambas versiones, el formulario puede combinar valores que no corresponden a una misma evaluación.

**Corrección propuesta:** seleccionar una sola entrada y versión CVSS, y derivar de esa misma entrada el puntaje, la severidad, los impactos y la explotabilidad. Añadir una prueba para CVEs que tengan métricas 4.0 y 3.1 simultáneamente.

**Referencia:** `CveService.java` (`leerPuntaje`, `leerImpactos`).

## 3. Llamadas HTTP externas sin timeout explícito

**Prioridad: 6/10**

Los clientes HTTP de NVD y MyMemory no definen tiempos límite explícitos. Una conexión lenta puede mantener solicitudes de la aplicación bloqueadas por más tiempo del esperado.

**Corrección propuesta:** configurar timeout de conexión y de respuesta para ambos clientes; manejar el timeout como fallo recuperable, conservando el comportamiento de usar datos locales o permitir el ingreso manual.

**Referencias:** `CveService.java`, `TraductorService.java` (creación de `RestClient`).

## 4. Artefactos compilados versionados

**Prioridad: 5/10**

El cambio incluye `.class` y recursos compilados bajo `target/`. Esos archivos dependen del entorno de compilación y generan ruido en revisiones y merges.

**Corrección propuesta:** retirar los artefactos generados del control de versiones, ignorar `target/` y conservar únicamente fuentes, recursos y migraciones.

**Referencia:** `target/`.

## Verificación pendiente

La suite actual pasa, pero solo contiene una prueba de carga del contexto. Después de corregir los puntos 1 y 2, agregar pruebas enfocadas en límites de solicitudes y selección coherente de métricas CVSS.
