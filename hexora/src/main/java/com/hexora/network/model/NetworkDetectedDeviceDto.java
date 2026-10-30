package com.hexora.network.model;

import java.time.LocalDateTime;

public record NetworkDetectedDeviceDto(
    Long id,
    String name,
    String address,
    String macAddress,
    String detectionMethod,
    NetworkHostStatus status,
    boolean registered,
    LocalDateTime firstDetectedAt,
    LocalDateTime lastDetectedAt,
    int consecutiveMisses,
    boolean gateway,
    String gatewayAddress
) {
    public NetworkDetectedDeviceDto(
        Long id,
        String name,
        String address,
        String macAddress,
        String detectionMethod,
        NetworkHostStatus status,
        boolean registered,
        LocalDateTime firstDetectedAt,
        LocalDateTime lastDetectedAt,
        int consecutiveMisses
    ) {
        this(
            id,
            name,
            address,
            macAddress,
            detectionMethod,
            status,
            registered,
            firstDetectedAt,
            lastDetectedAt,
            consecutiveMisses,
            false,
            null
        );
    }
}
