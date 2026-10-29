package com.hexora.network.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "network_discovery_snapshot")
public class NetworkDiscoverySnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 18)
    private String subnet;

    @Column(nullable = false, length = 255)
    private String source;

    @Column(name = "observed_at", nullable = false)
    private LocalDateTime observedAt;

    @OneToMany(mappedBy = "snapshot", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("address ASC")
    private List<NetworkDiscoverySnapshotDevice> devices = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public String getSubnet() {
        return subnet;
    }

    public void setSubnet(String subnet) {
        this.subnet = subnet;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public LocalDateTime getObservedAt() {
        return observedAt;
    }

    public void setObservedAt(LocalDateTime observedAt) {
        this.observedAt = observedAt;
    }

    public List<NetworkDiscoverySnapshotDevice> getDevices() {
        return devices;
    }

    public void addDevice(NetworkDiscoverySnapshotDevice device) {
        devices.add(device);
        device.setSnapshot(this);
    }
}
