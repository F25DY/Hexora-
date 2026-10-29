package com.hexora.network.repository;

import com.hexora.network.model.NetworkEvent;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NetworkEventRepository extends JpaRepository<NetworkEvent, Long> {

    List<NetworkEvent> findTop20ByOrderByCreatedAtDesc();

    @Modifying
    @Query("delete from NetworkEvent event where event.host.id = :hostId")
    int deleteAllForHost(@Param("hostId") Long hostId);

    @Modifying
    @Query("delete from NetworkEvent event where event.scan.id in :scanIds")
    int deleteAllForScans(@Param("scanIds") List<Long> scanIds);
}
