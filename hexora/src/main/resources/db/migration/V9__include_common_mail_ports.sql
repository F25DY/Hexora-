INSERT INTO monitored_port (network_host_id, port, enabled)
SELECT id, 110, TRUE
FROM network_host
WHERE monitored_ports = '21,22,25,53,80,443,3306,5432'
UNION ALL
SELECT id, 143, TRUE
FROM network_host
WHERE monitored_ports = '21,22,25,53,80,443,3306,5432';

UPDATE network_host
SET monitored_ports = '21,22,25,53,80,110,143,443,3306,5432'
WHERE monitored_ports = '21,22,25,53,80,443,3306,5432';
