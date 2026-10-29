package com.hexora.network.scheduler;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hexora.network.model.NetworkInventoryUpdateDto;
import com.hexora.network.service.NetworkMonitorProperties;
import com.hexora.network.service.NetworkMonitoringService;
import com.hexora.network.websocket.NetworkMonitorWebSocketHandler;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NetworkMonitoringSchedulerTest {

    @Mock
    private NetworkMonitoringService monitoringService;

    @Mock
    private NetworkMonitorWebSocketHandler webSocketHandler;

    private NetworkMonitorProperties properties;
    private NetworkMonitoringScheduler scheduler;

    @BeforeEach
    void setUp() {
        properties = new NetworkMonitorProperties();
        scheduler = new NetworkMonitoringScheduler(monitoringService, webSocketHandler, properties);
    }

    @Test
    void publishesAutomaticConnectedDeviceUpdatesToLiveClients() {
        NetworkInventoryUpdateDto update = new NetworkInventoryUpdateDto(
            "inventory",
            "192.168.1.0/24",
            "ICMP + ARP",
            Instant.parse("2026-10-27T12:00:00Z"),
            List.of(),
            false
        );
        when(monitoringService.synchronizeDetectedDevices()).thenReturn(update);

        scheduler.discoverConnectedDevices();

        verify(webSocketHandler).publish(update);
    }

    @Test
    void doesNotRunAutomaticDiscoveryWhenItIsDisabled() {
        properties.setDiscoveryEnabled(false);

        scheduler.discoverConnectedDevices();

        org.mockito.Mockito.verifyNoInteractions(monitoringService, webSocketHandler);
    }
}
