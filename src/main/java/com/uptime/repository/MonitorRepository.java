package com.uptime.repository;

import com.uptime.model.CheckFrequency;
import com.uptime.model.Monitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonitorRepository extends JpaRepository<Monitor,String> {

    List<Monitor> findAllByCheckFrequency(CheckFrequency frequency);
}
