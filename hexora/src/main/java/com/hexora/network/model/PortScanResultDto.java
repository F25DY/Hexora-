package com.hexora.network.model;

import java.time.LocalDateTime;

public record PortScanResultDto(
    int port,
    String service,
    PortStatus status,
    LocalDateTime detectedAt
) {
}
