package com.hexora.network.repository;

import com.hexora.network.model.NetworkDiscoveryEvent;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NetworkDiscoveryEventRepository extends JpaRepository<NetworkDiscoveryEvent, Long> {

    List<NetworkDiscoveryEvent> findTop20ByOrderByCreatedAtDesc();
}
