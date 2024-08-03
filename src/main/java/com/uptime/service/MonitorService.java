package com.uptime.service;

import com.uptime.dto.CheckURLJob;
import com.uptime.dto.MonitorRequest;
import com.uptime.exception.MonitorNotFound;
import com.uptime.model.CheckFrequency;
import com.uptime.model.CheckStatus;
import com.uptime.model.Monitor;
import com.uptime.model.UserInfo;
import com.uptime.repository.MonitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.BooleanUtils.TRUE;

@Service
public class MonitorService {


    @Autowired
    MonitorRepository monitorRepository;

    public void createMonitor(MonitorRequest request, UserInfo userInfo) {

        CheckURLJob checkURLJob = getJob(request.url());

        Monitor monitor = Monitor.builder()
                .url(request.url())
                .checkFrequency(request.checkFrequency())
                .userInfo(userInfo)
                .currentStatus(checkURLJob.getResult() ? CheckStatus.UP : CheckStatus.DOWN)
                .build();
        monitorRepository.save(monitor);
    }

    public List<Monitor> fetchMonitorWithForFrequency(CheckFrequency frequency) {
        return monitorRepository.findAllByCheckFrequencyEqualsAndCurrentStatusNotAndUserInfoIdIsNotNull(frequency, CheckStatus.PAUSED);
    }

    public List<Monitor> fetchMonitorsForUser(String email, UserInfo userInfo) {
        return monitorRepository.findAllByUserInfo(userInfo);
    }

    public void removeMonitor(String id) {
        Optional<Monitor> monitorOptional = monitorRepository.findById(id);
        if(monitorOptional.isEmpty())
            throw new MonitorNotFound();
        Monitor monitor = monitorOptional.get();
        monitor.setUserInfo(null);
        monitorRepository.save(monitor);
    }

    public void pauseUnpauseMonitor(String id, Boolean toPause) {
        Optional<Monitor> monitorOptional = monitorRepository.findById(id);
        if(monitorOptional.isEmpty())
            throw new MonitorNotFound();
        if(toPause){
        monitorRepository.updateMonitorStatus(id, CheckStatus.PAUSED.name());
        }else{
            Monitor monitor = monitorOptional.get();
            CheckURLJob job = getJob(monitor);
            monitorRepository.save(monitor);
        }
    }

    private static CheckURLJob getJob(String url) {
        CheckURLJob checkURLJob = new CheckURLJob();
        checkURLJob.setUrl(url);
        checkURLJob.execute();
        return checkURLJob;
    }

    private static CheckURLJob getJob(Monitor monitor) {
        CheckURLJob checkURLJob = new CheckURLJob();
        checkURLJob.setMonitor(monitor);
        checkURLJob.setUrl(monitor.getUrl());
        checkURLJob.execute();
        return checkURLJob;
    }


}
