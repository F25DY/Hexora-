package com.hexora.network.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "network_host")
public class NetworkHost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "display_name", nullable = false, length = 100)
    private String displayName;

    @Column(nullable = false, unique = true, length = 255)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private NetworkHostStatus status = NetworkHostStatus.UNKNOWN;

    @Column(name = "latency_ms")
    private Integer latencyMs;

    @Column(name = "last_checked_at")
    private LocalDateTime lastCheckedAt;

    @Column(nullable = false)
    private boolean enabled = true;

    @Column(name = "managed_by_ui", nullable = false)
    private boolean managedByUi;

    @Column(name = "monitored_ports", nullable = false, length = 512)
    private String monitoredPorts = "21,22,25,53,80,110,143,443,3306,5432";

    @Column(name = "consecutive_failures", nullable = false)
    private int consecutiveFailures;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public NetworkHostStatus getStatus() {
        return status;
    }

    public void setStatus(NetworkHostStatus status) {
        this.status = status;
    }

    public Integer getLatencyMs() {
        return latencyMs;
    }

    public void setLatencyMs(Integer latencyMs) {
        this.latencyMs = latencyMs;
    }

    public LocalDateTime getLastCheckedAt() {
        return lastCheckedAt;
    }

    public void setLastCheckedAt(LocalDateTime lastCheckedAt) {
        this.lastCheckedAt = lastCheckedAt;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isManagedByUi() {
        return managedByUi;
    }

    public void setManagedByUi(boolean managedByUi) {
        this.managedByUi = managedByUi;
    }

    public List<Integer> getMonitoredPorts() {
        if (monitoredPorts == null || monitoredPorts.isBlank()) {
            return List.of();
        }
        return Arrays.stream(monitoredPorts.split(","))
            .filter(value -> !value.isBlank())
            .map(Integer::parseInt)
            .toList();
    }

    public void setMonitoredPorts(List<Integer> ports) {
        monitoredPorts = ports.stream().map(String::valueOf).collect(Collectors.joining(","));
    }

    public int getConsecutiveFailures() {
        return consecutiveFailures;
    }

    public void setConsecutiveFailures(int consecutiveFailures) {
        this.consecutiveFailures = consecutiveFailures;
    }
}
