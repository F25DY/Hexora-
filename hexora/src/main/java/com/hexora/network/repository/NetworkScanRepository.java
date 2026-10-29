package com.hexora.network.repository;

import com.hexora.network.model.NetworkScan;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NetworkScanRepository extends JpaRepository<NetworkScan, Long> {

    Slice<NetworkScan> findAllByOrderByStartedAtDesc(Pageable pageable);

    Slice<NetworkScan> findAllByHost_IdOrderByStartedAtDesc(Long hostId, Pageable pageable);

    Optional<NetworkScan> findFirstByHost_IdOrderByStartedAtDesc(Long hostId);

    @Modifying
    @Query("delete from NetworkScan scan where scan.host.id = :hostId")
    int deleteAllForHost(@Param("hostId") Long hostId);
}
