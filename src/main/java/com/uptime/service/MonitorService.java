package com.uptime.service;

import com.uptime.dto.MonitorRequest;
import com.uptime.model.CheckFrequency;
import com.uptime.model.Monitor;
import com.uptime.model.UserInfo;
import com.uptime.repository.MonitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonitorService {

    @Autowired
    MonitorRepository monitorRepository;

    public void createMonitor(MonitorRequest request, UserInfo userInfo){
        Monitor monitor = Monitor.builder()
                .url(request.url())
                .checkFrequency(request.checkFrequency())
                .userInfo(userInfo)
                .build();
        monitorRepository.save(monitor);
    }

    public List<Monitor> fetchMonitorWithForFrequency(CheckFrequency frequency){
        return monitorRepository.findAllByCheckFrequency(frequency);
    }

}
