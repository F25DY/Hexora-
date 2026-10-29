package com.hexora.network.model;

import java.time.Instant;
import java.util.List;

public record NetworkDiscoveryDto(
    String subnet,
    String source,
    Instant observedAt,
    List<NetworkNeighborDto> devices,
    List<NetworkNeighborDto> recentDisconnects
) {
}
