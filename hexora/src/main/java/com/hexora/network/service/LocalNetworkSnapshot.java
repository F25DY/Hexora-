package com.hexora.network.service;

import java.time.Instant;
import java.util.List;

record LocalNetworkSnapshot(
    String subnet,
    String source,
    Instant observedAt,
    List<NeighborEntry> devices
) {
}

record NeighborEntry(String address, String macAddress, String detectionMethod) {

    NeighborEntry(String address, String macAddress) {
        this(address, macAddress, "ARP (vecino Reachable)");
    }
}
