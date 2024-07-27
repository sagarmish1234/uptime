package com.uptime.service;

import com.uptime.dto.CheckURLJob;
import com.uptime.dto.MonitorRequest;
import com.uptime.model.CheckFrequency;
import com.uptime.model.CheckStatus;
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

        CheckURLJob checkURLJob = new CheckURLJob();
        checkURLJob.setUrl(request.url());
        checkURLJob.execute();

        Monitor monitor = Monitor.builder()
                .url(request.url())
                .checkFrequency(request.checkFrequency())
                .userInfo(userInfo)
                .currentStatus(checkURLJob.getResult()? CheckStatus.UP:CheckStatus.DOWN)
                .build();
        monitorRepository.save(monitor);
    }

    public List<Monitor> fetchMonitorWithForFrequency(CheckFrequency frequency){
        return monitorRepository.findAllByCheckFrequencyAndIsPaused(frequency,false);
    }

    public List<Monitor> fetchMonitorsForUser(String email,UserInfo userInfo){
        return monitorRepository.findAllByUserInfo(userInfo);
    }

}
