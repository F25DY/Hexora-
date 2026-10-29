package com.hexora.network.model;

public record MonitoredPortDto(Long id, int port, String service, boolean enabled) {
}
