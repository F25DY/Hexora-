package com.hexora.network.repository;

import com.hexora.network.model.PortScanResult;
import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PortScanResultRepository extends JpaRepository<PortScanResult, Long> {

    Optional<PortScanResult> findTopByHost_IdAndPortOrderByDetectedAtDesc(Long hostId, int port);

    @Modifying
    @Query("delete from PortScanResult result where result.host.id = :hostId")
    int deleteAllForHost(@Param("hostId") Long hostId);

    @Modifying
    @Query("delete from PortScanResult result where result.scan.id in :scanIds")
    int deleteAllForScans(@Param("scanIds") List<Long> scanIds);
}
