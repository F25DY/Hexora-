package com.hexora.network.model;

import java.time.LocalDateTime;
import java.util.List;

public record NetworkHostDto(
    Long id,
    String name,
    String address,
    NetworkHostStatus status,
    Integer latencyMs,
    LocalDateTime lastResponse,
    List<MonitoredPortDto> monitoredPorts,
    boolean managedByUi
) {
}
