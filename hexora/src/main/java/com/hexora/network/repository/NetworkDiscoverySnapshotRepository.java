package com.hexora.network.repository;

import com.hexora.network.model.NetworkDiscoverySnapshot;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NetworkDiscoverySnapshotRepository extends JpaRepository<NetworkDiscoverySnapshot, Long> {

    Optional<NetworkDiscoverySnapshot> findFirstByOrderByObservedAtDesc();

    List<NetworkDiscoverySnapshot> findAllByOrderByObservedAtDesc(Pageable pageable);
}
