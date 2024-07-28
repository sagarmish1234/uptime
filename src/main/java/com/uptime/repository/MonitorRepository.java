package com.uptime.repository;

import com.uptime.model.CheckFrequency;
import com.uptime.model.CheckStatus;
import com.uptime.model.Monitor;
import com.uptime.model.UserInfo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonitorRepository extends JpaRepository<Monitor,String> {

    List<Monitor> findAllByCheckFrequencyEqualsAndCurrentStatusNot(CheckFrequency frequency,CheckStatus status);

    List<Monitor> findAllByUserInfo(UserInfo userInfo);

    @Modifying
    @Query(value = "update monitor set current_status=?2 where id=?1",nativeQuery = true)
    @Transactional
    void updateMonitorStatus(String id, String status);
}
