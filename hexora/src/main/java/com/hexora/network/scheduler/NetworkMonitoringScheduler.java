package com.hexora.network.scheduler;

import com.hexora.network.model.NetworkInventoryUpdateDto;
import com.hexora.network.service.NetworkDiscoveryException;
import com.hexora.network.model.NetworkScanDto;
import com.hexora.network.service.NetworkMonitorProperties;
import com.hexora.network.service.NetworkMonitoringService;
import com.hexora.network.service.ScanRateLimitException;
import com.hexora.network.websocket.NetworkMonitorWebSocketHandler;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NetworkMonitoringScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(NetworkMonitoringScheduler.class);
    private final NetworkMonitoringService monitoringService;
    private final NetworkMonitorWebSocketHandler webSocketHandler;
    private final NetworkMonitorProperties properties;

    public NetworkMonitoringScheduler(
        NetworkMonitoringService monitoringService,
        NetworkMonitorWebSocketHandler webSocketHandler,
        NetworkMonitorProperties properties
    ) {
        this.monitoringService = monitoringService;
        this.webSocketHandler = webSocketHandler;
        this.properties = properties;
    }

    @Scheduled(fixedDelayString = "${hexora.network.monitor-interval-ms:30000}")
    public void monitorConfiguredHosts() {
        for (Long hostId : monitoringService.getConfiguredHostIds()) {
            try {
                NetworkScanDto scan = monitoringService.scanHost(hostId, null, null);
                webSocketHandler.publish(scan);
            } catch (IllegalStateException | EntityNotFoundException | DataAccessException
                | NetworkDiscoveryException
                | ScanRateLimitException exception) {
                LOGGER.error("Scheduled monitoring failed for configured host id {}", hostId, exception);
            }
        }
    }

    @Scheduled(
        fixedDelayString = "${hexora.network.discovery-interval-ms:30000}",
        initialDelayString = "${hexora.network.discovery-initial-delay-ms:1000}"
    )
    public void discoverConnectedDevices() {
        if (!properties.isDiscoveryEnabled()) {
            return;
        }
        try {
            NetworkInventoryUpdateDto update = monitoringService.synchronizeDetectedDevices();
            webSocketHandler.publish(update);
        } catch (IllegalStateException | DataAccessException | NetworkDiscoveryException exception) {
            LOGGER.error("Automatic local network discovery failed", exception);
        }
    }
}
