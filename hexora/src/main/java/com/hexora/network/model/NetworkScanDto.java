package com.hexora.network.model;

import java.time.LocalDateTime;
import java.util.List;

public record NetworkScanDto(
    Long id,
    Long hostId,
    String hostName,
    String address,
    NetworkScanStatus status,
    NetworkHostStatus hostStatus,
    Integer latencyMs,
    int portsScanned,
    LocalDateTime startedAt,
    LocalDateTime completedAt,
    List<PortScanResultDto> ports,
    List<NetworkEventDto> events
) {
}
