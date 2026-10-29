package com.hexora.network.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;

class LocalNeighborPresenceTrackerTest {

    private final LocalNeighborPresenceTracker tracker = new LocalNeighborPresenceTracker();
    private final Instant start = Instant.parse("2026-10-26T20:00:00Z");

    @Test
    void tracksObservedDurationAndEstimatesAbsenceAfterThreeSamplesAndNinetySeconds() {
        var first = tracker.observe("192.168.1.0/24", List.of("192.168.1.20"), start);
        assertEquals(start, first.connected().get(0).firstSeen());

        var oneMissingSample = tracker.observe(
            "192.168.1.0/24",
            List.of(),
            start.plusSeconds(30)
        );
        assertEquals(1, oneMissingSample.connected().size());
        assertEquals(start, oneMissingSample.connected().get(0).lastSeen());
        assertEquals(0, oneMissingSample.disconnected().size());

        var twoMissingSamples = tracker.observe("192.168.1.0/24", List.of(), start.plusSeconds(60));
        assertEquals(1, twoMissingSamples.connected().size());
        assertEquals(0, twoMissingSamples.disconnected().size());

        var absent = tracker.observe("192.168.1.0/24", List.of(), start.plusSeconds(90));
        assertEquals(0, absent.connected().size());
        assertEquals(1, absent.disconnected().size());
        assertEquals(start.plusSeconds(90), absent.disconnected().get(0).disconnectedAt());
        assertEquals(start, absent.disconnected().get(0).lastSeen());
    }

    @Test
    void startsANewObservationPeriodWhenAnAddressReturnsAndKeepsRecentAbsence() {
        tracker.observe("192.168.1.0/24", List.of("192.168.1.20"), start);
        tracker.observe("192.168.1.0/24", List.of(), start.plusSeconds(30));
        tracker.observe("192.168.1.0/24", List.of(), start.plusSeconds(60));
        tracker.observe("192.168.1.0/24", List.of(), start.plusSeconds(90));

        var returned = tracker.observe(
            "192.168.1.0/24",
            List.of("192.168.1.20"),
            start.plusSeconds(120)
        );

        assertEquals(start.plusSeconds(120), returned.connected().get(0).firstSeen());
        assertEquals(1, returned.disconnected().size());
    }

    @Test
    void resetsObservationsWhenTheActiveSubnetChanges() {
        tracker.observe("192.168.1.0/24", List.of("192.168.1.20"), start);

        var changedSubnet = tracker.observe(
            "10.0.0.0/24",
            List.of("10.0.0.5"),
            start.plusSeconds(30)
        );

        assertEquals(List.of("10.0.0.5"), changedSubnet.connected().stream()
            .map(device -> device.address()).toList());
        assertEquals(0, changedSubnet.disconnected().size());
    }
}
