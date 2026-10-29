package com.hexora.network.repository;

import com.hexora.network.model.MonitoredPort;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonitoredPortRepository extends JpaRepository<MonitoredPort, Long> {

    List<MonitoredPort> findAllByHost_IdOrderByPortAsc(Long hostId);

    List<MonitoredPort> findAllByHost_IdAndEnabledTrueOrderByPortAsc(Long hostId);

    Optional<MonitoredPort> findByHost_IdAndPort(Long hostId, int port);

    boolean existsByHost_IdAndPort(Long hostId, int port);

    long countByHost_Id(Long hostId);
}
