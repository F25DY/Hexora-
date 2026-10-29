package com.hexora.network.model;

import java.time.Instant;
import java.util.List;

public record NetworkDiscoverySnapshotDto(
    Long id,
    String subnet,
    String source,
    Instant observedAt,
    List<NetworkDetectedDeviceDto> devices
) {
}
