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

@Entity
@Table(name = "network_detected_device")
public class NetworkDetectedDevice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 45)
    private String address;

    @Column(name = "mac_address", length = 17)
    private String macAddress;

    @Column(name = "host_name", nullable = false, length = 255)
    private String hostName;

    @Column(name = "detection_method", nullable = false, length = 40)
    private String detectionMethod = "Sin respuesta actual";

    @Column(nullable = false, length = 18)
    private String subnet;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private NetworkHostStatus status = NetworkHostStatus.ONLINE;

    @Column(name = "consecutive_misses", nullable = false)
    private int consecutiveMisses;

    @Column(name = "first_detected_at", nullable = false)
    private LocalDateTime firstDetectedAt;

    @Column(name = "last_detected_at", nullable = false)
    private LocalDateTime lastDetectedAt;

    @Column(name = "last_status_changed_at", nullable = false)
    private LocalDateTime lastStatusChangedAt;

    public Long getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getDetectionMethod() {
        return detectionMethod;
    }

    public void setDetectionMethod(String detectionMethod) {
        this.detectionMethod = detectionMethod;
    }

    public String getSubnet() {
        return subnet;
    }

    public void setSubnet(String subnet) {
        this.subnet = subnet;
    }

    public NetworkHostStatus getStatus() {
        return status;
    }

    public void setStatus(NetworkHostStatus status) {
        this.status = status;
    }

    public int getConsecutiveMisses() {
        return consecutiveMisses;
    }

    public void setConsecutiveMisses(int consecutiveMisses) {
        this.consecutiveMisses = consecutiveMisses;
    }

    public LocalDateTime getFirstDetectedAt() {
        return firstDetectedAt;
    }

    public void setFirstDetectedAt(LocalDateTime firstDetectedAt) {
        this.firstDetectedAt = firstDetectedAt;
    }

    public LocalDateTime getLastDetectedAt() {
        return lastDetectedAt;
    }

    public void setLastDetectedAt(LocalDateTime lastDetectedAt) {
        this.lastDetectedAt = lastDetectedAt;
    }

    public LocalDateTime getLastStatusChangedAt() {
        return lastStatusChangedAt;
    }

    public void setLastStatusChangedAt(LocalDateTime lastStatusChangedAt) {
        this.lastStatusChangedAt = lastStatusChangedAt;
    }
}
