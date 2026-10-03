INSERT INTO empresa (id_empresa, nit, nombre, sector) VALUES
  (1, '', 'Tecnologico Comfenalco', 'Educativo'),
  (2, '', 'MaxNet', 'IT'),
  (3, '', 'Test1', ''),
  (4, '', 'Test2', 'Educativo'),
  (5, '', 'Tecnologico Comfenalco', 'Educativo'),
  (7, '', 'Tecnologico Comefenalco', 'Educativo'),
  (14, '', 'Lingual S.A.S', 'Turismo');

INSERT INTO auditoria (id_auditoria, estado, fecha_fin, fecha_inico, nombre, tipo_auditoria, empresa_id) VALUES
  (4, 'Planificado', '2026-05-23', '2026-05-23', 'Auditoria FUTCO', 'pentesting', 7),
  (11, 'En Progreso', '2026-05-24', '2026-05-24', 'Pentest Data-Center', 'Pentesting + Hardening', 14);

INSERT INTO area_auditable (id_area, descripcion, nombre, id_auditoria) VALUES
  (1, 'test1', 'Apps Web', 4),
  (10, 'Auditoria completa de los multiples servicios alojados en el servidor principal de Lingual S.A.S conocido como Metasplotaible', 'Servidor central - Metasplotaible', 11),
  (13, 'aaaa', 'asa', 11);

INSERT INTO vulnerabilidad (id, confidencialidad, cve, disponibilidad, estado, fecha_descubrimiento, herramienta, host_afectado, impacto, integridad, ip_afectada, nivel_riesgo, nombre, plan_mitigacion, probabilidad, puerto_afectado, servicio, tipo, id_area) VALUES
  (2, 7, '', 5, 'Abierta', '2026-05-23', 'nmap', 'pc administración', 6.00, 6, '192.168.1.66', 'Alto', 'VNC', '', 5, NULL, 'VNC', 'RCE', 1),
  (11, 9, '', 8, 'Abierta', '2026-05-17', 'nmap', 'Servidor central', 8.67, 9, '192.168.197.131', 'Critico', 'Sistema Operativo', 'Migrar a SO más actualizado con soporte disponible', 8, NULL, '', 'Desactualización', 10),
  (12, 8, '', 8, 'Mitigada', '2026-05-17', 'nmap', 'Servidor central', 7.67, 7, '192.168.197.131', 'Critico', 'UnrealIRCd Backdoor Detection', 'Eliminar servicio', 9, 6666, 'IRC', 'Backdoor', 10),
  (13, 9, '', 7, 'Mitigada', '2026-05-17', 'nmap', 'Servidor central', 7.67, 7, '192.168.197.131', 'Critico', 'VNC Server ''password'' Password', 'Cambiar credenciales de acceso', 8, 5900, 'VNC', 'Credenciales default', 10),
  (14, 8, '', 5, 'Mitigada', '2026-05-17', 'nmap', 'Servidor central', 6.00, 5, '192.168.197.131', 'Critico', 'SSL Version 2 and 3 Protocol Detection', 'Deshabilitar SSLv2/3.
', 8, 25, 'SSL/TLS', 'Desactualización', 10),
  (15, 9, '', 8, 'Mitigada', '2026-05-17', 'nmap', 'Servidor central', 8.67, 9, '192.168.197.131', 'Critico', 'Bind Shell Backdoor ', 'Eliminar backdoor.
', 6, 1524, 'Bind Shell', 'Backdoor', 10),
  (16, 9, '', 5, 'Mitigada', '2026-05-18', 'enum4linux y showmount', 'Servidor central', 7.00, 7, '192.168.197.131', 'Alto', 'NFS Shares World Readable', 'Restringir exports.', 3, 2049, 'NFS', 'Exposición', 10),
  (17, 9, '', 8, 'Mitigada', '2026-05-18', 'nmap', 'Servidor Central', 8.00, 7, '192.168.197.131', 'Alto', 'rlogin Service Detection', 'Eliminar servicio', 2, 513, 'rlogin', 'Servicio abierto', 10),
  (18, 9, '', 8, 'Mitigada', '2026-05-18', 'enum4linux', 'Servidor central', 8.33, 8, '192.168.197.131', 'Alto', 'Samba Badlock Vulnerability', 'Actualizar Samba.', 2, 448, 'SMB', 'Desactualización', 10),
  (19, 7, '', 9, 'Abierta', '2026-05-18', 'nikto', 'Servidor web Tomcat', 7.67, 7, '192.168.197.131', 'Critico', 'BIND vulnerable.', 'Actualizar BIND', 8, 52, 'DNS', 'Desactualización', 10),
  (20, 8, '', 7, 'Abierta', '2026-05-18', 'nikto', 'Servidor web Tomcat', 7.00, 6, '192.168.197.131', 'Alto', 'Tomcat legacy.', 'Actualizar', 4, 8180, 'Tomcat', 'Desactualización', 10),
  (21, 7, '', 6, 'Mitigada', '2026-05-19', 'ghostcat', 'Servidor web Tomcat', 6.33, 6, '192.168.197.131', 'Alto', 'Apache Tomcat AJP Connector Request Injection (Ghostcat)', 'Restringir AJP', 4, 8009, 'Tomcat/AJP', 'Exposición', 10),
  (23, 5, '', 4, 'Mitigada', '2026-05-19', 'nmap', 'Servidor central', 4.67, 5, '192.168.197.131', 'Medio', 'Telnet expuesto', 'Eliminar Telnet', 5, 23, 'Telnet', 'Exposición', 10),
  (24, 7, '', 3, 'Mitigada', '2026-05-19', 'nikto', 'Servidor web Tomcat', 4.67, 4, '192.168.197.131', 'Medio', 'HTTP TRACE / TRACK Methods Allowed', 'Deshabilitar TRACE.
', 2, 80, 'HTTP', 'Exposición', 10),
  (25, 7, '', 5, 'Abierta', '2026-05-20', '', 'Servidor Central', 5.33, 4, '192.168.197.131', 'Medio', 'SSH Weak Algorithms Supported', 'Eliminar RC4/CBC.
', 3, 22, 'SSH', 'Encriptación debil', 10),
  (26, 6, '', 3, 'Mitigada', '2026-05-20', '', 'Servidor de correos', 4.33, 4, '192.168.197.131', 'Medio', 'SMTP Service STARTTLS Plaintext Command Injection', 'Actualizar servicio', 5, 25, 'SMTP', 'Inyección de comandos', 10),
  (27, 5, '', 2, 'Mitigada', '2026-05-21', '', 'Servidor central', 3.67, 4, '192.168.197.131', 'Bajo', 'X Server Detection', 'Deshabilitar X11', 0, 6000, 'X11', 'Exposición', 10),
  (28, 5, '', 2, 'Mitigada', '2026-05-21', '', 'Servidor central', 3.33, 3, '192.168.197.131', 'Bajo', 'ICMP Timestamp Request Remote Date Disclosure', 'Filtrar ICMP', 0, 10114, 'ICMP', 'Acceso', 10),
  (29, 4, '', 3, 'Abierta', '2026-05-22', 'nmap', 'Servidor central', 3.00, 2, '192.168.197.131', 'Bajo', 'RPC Services Enumeration', 'Restringir RPC.
', 1, 111, 'RPC', 'Servicios RPC enumerables.', 10),
  (30, 3, '', 1, 'Abierta', '2026-05-17', 'Nmap', 'Servidor central', 2.00, 2, '192.168.197.131', 'Bajo', 'Service Detection', 'Reducir exposición de los servicios mediante el filtrado de puertos con un firewall', 1, NULL, 'Multiples', 'Alta Exposición', 10),
  (31, 2, '', 2, 'Abierta', '2026-05-17', '', 'Servidor central', 2.33, 3, '192.168.197.131', 'Bajo', 'Remote Services Not Using Post-Quantum Ciphers', 'Actualizar suites.
', 1, NULL, 'TLS/SSH', 'Falta de cifrado', 10),
  (32, 2, '', 1, 'Abierta', '2026-05-22', '', 'Servidor central', 2.00, 3, '192.168.197.131', 'Bajo', 'Unknown Service Detection: Banner Retrieval', 'Eliminar servicios.
', 0, NULL, '', 'Banner retrieval.', 10),
  (33, 2, '', 3, 'Abierta', '2026-05-22', '', 'Servidor web Tomcat', 2.67, 3, '192.168.197.131', 'Bajo', 'WebDAV Detection', 'Deshabilitar WebDAV.
', 1, 8180, 'Tomcat/WebDAV', 'Exposición', 10),
  (34, 2, '', 2, 'Abierta', '2026-05-22', '', '', 2.33, 3, '', 'Bajo', 'AJP Connector Detection', 'Restringir AJP.
', 1, 8012, 'AJP13', 'AJP expuesto.', 10),
  (35, 3, '', 2, 'Abierta', '2026-05-22', 'Nmap', 'Servidor central', 2.67, 3, '192.168.197.131', 'Bajo', 'vsftpd Detection', 'Actualizar FTP.
', 2, 21, 'FTP', 'Desactualizado', 10),
  (36, 4, '', 5, 'Abierta', '2026-05-19', 'Nmap NSE', 'Servidor central', 4.00, 3, '192.168.197.131', 'Bajo', 'MySQL Server Detection', 'Actualizar credenciales de acceso a BD y restringir su exposición', 1, 3306, 'MySQL', 'Exposición', 10),
  (37, 3, '', 3, 'Abierta', '2026-05-22', '', 'Servidor Central', 2.67, 2, '192.168.197.131', 'Bajo', 'RMI Registry Detection', 'Eliminar/restringir.
', 1, 1099, 'Java RMI', 'Java RMI expuesto.', 10),
  (40, 6, '', 2, 'Abierta', '2026-09-01', '', '', 4.00, 4, '', 'Alto', 'aa', '', 8, NULL, '', 'aa', 10);
