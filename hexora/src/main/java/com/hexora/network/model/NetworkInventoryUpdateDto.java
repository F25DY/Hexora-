package com.hexora.network.model;

import java.time.Instant;
import java.util.List;

public record NetworkInventoryUpdateDto(
    String type,
    String subnet,
    String source,
    Instant observedAt,
    List<NetworkDetectedDeviceDto> devices,
    boolean changed
) {
}
