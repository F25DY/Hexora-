package com.hexora.network.model;

import java.time.LocalDateTime;

public record NetworkEventDto(
    Long id,
    Long hostId,
    String hostName,
    NetworkEventType type,
    String description,
    LocalDateTime createdAt
) {
}
