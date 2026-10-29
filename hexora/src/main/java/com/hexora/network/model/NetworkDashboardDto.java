package com.hexora.network.model;

import java.util.List;

public record NetworkDashboardDto(
    int hostsMonitored,
    int onlineHosts,
    int offlineHosts,
    int openPorts,
    List<NetworkHostDto> hosts,
    List<NetworkScanDto> recentScans,
    List<NetworkEventDto> recentEvents
) {
}
