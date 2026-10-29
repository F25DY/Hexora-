# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/4.0.6/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/4.0.6/maven-plugin/build-image.html)
* [Spring Web](https://docs.spring.io/spring-boot/4.0.6/reference/web/servlet.html)
* [Thymeleaf](https://docs.spring.io/spring-boot/4.0.6/reference/web/servlet.html#web.servlet.spring-mvc.template-engines)
* [Spring Data JPA](https://docs.spring.io/spring-boot/4.0.6/reference/data/sql.html#data.sql.jpa-and-spring-data)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Handling Form Submission](https://spring.io/guides/gs/handling-form-submission/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)

### Network Monitoring

The independent monitoring page is available at `/network-monitoring`. Network monitoring is isolated in `com.hexora.network` and uses Spring MVC, Spring Data JPA, Spring Scheduler, and a WebSocket endpoint. The page updates scan state over WebSocket, with a 30-second REST refresh as a fallback.

The **Detectar dispositivos en la red** action actively probes the current Windows private IPv4 subnet only when requested, then combines successful ICMP replies with current reachable ARP neighbors to obtain MAC addresses. Windows probes send two echo requests to tolerate one dropped packet. The bounded scan supports subnet sizes from `/22` through `/30` (up to 1,022 host addresses); larger subnets are rejected rather than scanned without bounds. Old ARP entries alone do not prove presence. Each scan marks devices missing from the current response snapshot offline immediately; persisted online state is cleared on application startup until a new check verifies it. The table shows IP, MAC, hostname, current state, detection method, and last detection time. If there is no active private IPv4 interface, the request is cancelled with `No existe una red activa para realizar el escaneo.` Devices not registered for monitoring are not pinged or port-scanned. The `V11__create_detected_network_inventory.sql` and `V12__add_network_detection_method.sql` migrations add inventory/event storage and the persisted detection method without changing earlier migrations.

Devices may be authorized in the operator configuration:

```properties
hexora.network.allowed-hosts[0].name=Development API
hexora.network.allowed-hosts[0].address=192.0.2.10
hexora.network.allowed-hosts[1].name=Development database
hexora.network.allowed-hosts[1].address=192.0.2.11
```

`192.0.2.0/24` is reserved for documentation; replace these example addresses with systems you are authorized to monitor. The configured list is synchronized to `network_host` when the application starts. The page also allows an operator to register a device by private IPv4 address, set its display name, and add or remove monitored TCP ports. Port monitoring can be paused per port. Removing a page-managed device disables it without deleting its scan history.

The scheduler checks ICMP reachability for explicitly authorized devices and tests only their enabled monitored TCP ports (`21,22,25,53,80,110,143,443,3306,5432` by default). These configured-host checks are separate from discovery, which is never scheduled. On Windows, monitoring falls back to one bounded `ping.exe` request when Java's ICMP check does not receive a reply. A registered device becomes online only after a current reachability response or successful TCP connection, and becomes offline after a failed check. Tune `hexora.network.default-ports`, `hexora.network.monitor-interval-ms` (minimum 10 seconds), and `hexora.network.connect-timeout-millis` (1-5000 ms) as needed.

Discovery uses the active default-route private IPv4 interface. ICMP probes determine current presence; the interface-scoped reachable ARP table provides MAC addresses. Some devices or firewalls block ICMP and may not be detected unless Windows currently reports their ARP neighbor as reachable. For exact client connection state, use the authorized router/AP client list. On-demand scans of registered devices can use a contiguous range of at most 128 ports and are limited to one request per device every 15 seconds.

History can be cleared globally while retaining registered devices, or cleared for one UI-managed device while retaining that device. Clearing history also resets the device's last-known online state.

Flyway applies `V6__create_network_monitoring.sql`, `V7__add_network_device_management.sql`, `V8__create_monitored_ports.sql`, and `V9__include_common_mail_ports.sql`. The module-owned entities are `NetworkHost`, `NetworkScan`, `PortScanResult`, `NetworkEvent`, and `MonitoredPort`. Scan and state-change history is retained in the database.

REST endpoints:

* `GET /api/network/devices` (also `/hosts`), `GET /api/network/status`
* `POST /api/network/discover` (on-demand only; reads current reachable Windows neighbors and marks previously detected devices absent from that snapshot offline)
* `GET /api/network/history?hostId={id}&limit=20`
* `POST /api/network/devices` with `{"name":"NAS","address":"192.168.1.25","ports":[22,443]}`
* `POST /api/network/devices/{hostId}/ports` with `{"port":8080}`
* `PUT /api/network/devices/{hostId}/ports/{port}` with `{"enabled":false}`
* `DELETE /api/network/devices/{hostId}/ports/{port}`
* `DELETE /api/network/devices/{hostId}` (page-managed devices only)
* `DELETE /api/network/history` (all scans and events; retain devices)
* `DELETE /api/network/history/scans` with `{"scanIds":[1,2]}` (select up to 40 scan records; also removes their port results and events)
* `DELETE /api/network/devices/{hostId}/history` (clear one page-managed device's history)
* `POST /api/network/scan` with `{"hostId":1}` or `{"hostId":1,"startPort":20,"endPort":30}`
* WebSocket updates at `/ws/network`

The page includes device and port management, an up-to-date table for manually registered devices, current host/port tables, the latest 40 scan records, event history, and status metrics. The “Escanear red WiFi” action checks only the selected registered device and its enabled monitored ports; it does not discover unregistered devices or scan the subnet. It is linked from the existing dashboard; authentication, vulnerability analysis, and project/report features remain separate.

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.
