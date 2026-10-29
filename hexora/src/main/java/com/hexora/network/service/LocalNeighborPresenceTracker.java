package com.hexora.network.service;

import com.hexora.network.model.NetworkNeighborDto;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class LocalNeighborPresenceTracker {

    private static final int MISSING_SAMPLES_TO_DISCONNECT = 3;
    private static final Duration MINIMUM_MISSING_DURATION = Duration.ofSeconds(90);
    private static final int MAX_RECENT_DISCONNECTS = 10;

    private final Map<String, Presence> activePresences = new LinkedHashMap<>();
    private final ArrayDeque<NetworkNeighborDto> recentDisconnects = new ArrayDeque<>();
    private String currentSubnet;

    synchronized Snapshot observe(String subnet, List<String> addresses, Instant observedAt) {
        if (!subnet.equals(currentSubnet)) {
            activePresences.clear();
            recentDisconnects.clear();
            currentSubnet = subnet;
        }

        Set<String> observedAddresses = Set.copyOf(addresses);
        for (String address : observedAddresses) {
            Presence presence = activePresences.get(address);
            if (presence == null) {
                activePresences.put(address, new Presence(address, observedAt));
            } else {
                presence.lastSeen = observedAt;
                presence.missingSamples = 0;
            }
        }

        Iterator<Presence> iterator = activePresences.values().iterator();
        while (iterator.hasNext()) {
            Presence presence = iterator.next();
            if (presence.disconnectedAt == null && !observedAddresses.contains(presence.address)) {
                presence.missingSamples++;
                if (presence.missingSamples >= MISSING_SAMPLES_TO_DISCONNECT
                    && Duration.between(presence.lastSeen, observedAt).compareTo(MINIMUM_MISSING_DURATION) >= 0) {
                    presence.disconnectedAt = observedAt;
                    recentDisconnects.addFirst(presence.toDto());
                    iterator.remove();
                }
            }
        }

        List<NetworkNeighborDto> connected = activePresences.values().stream()
            .map(Presence::toDto)
            .sorted(Comparator.comparing(NetworkNeighborDto::address))
            .toList();
        while (recentDisconnects.size() > MAX_RECENT_DISCONNECTS) {
            recentDisconnects.removeLast();
        }
        List<NetworkNeighborDto> disconnected = List.copyOf(recentDisconnects);

        return new Snapshot(connected, disconnected);
    }

    record Snapshot(List<NetworkNeighborDto> connected, List<NetworkNeighborDto> disconnected) {
    }

    private static final class Presence {
        private final String address;
        private final Instant firstSeen;
        private Instant lastSeen;
        private Instant disconnectedAt;
        private int missingSamples;

        private Presence(String address, Instant firstSeen) {
            this.address = address;
            this.firstSeen = firstSeen;
            this.lastSeen = firstSeen;
        }

        private NetworkNeighborDto toDto() {
            return new NetworkNeighborDto(address, firstSeen, lastSeen, disconnectedAt);
        }
    }
}
