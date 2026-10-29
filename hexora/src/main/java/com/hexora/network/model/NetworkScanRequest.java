package com.hexora.network.model;

public record NetworkScanRequest(Long hostId, Integer startPort, Integer endPort) {
}
