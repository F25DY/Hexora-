package com.hexora.network.model;

import java.time.Instant;

public record NetworkNeighborDto(
    String address,
    Instant firstSeen,
    Instant lastSeen,
    Instant disconnectedAt
) {
}
