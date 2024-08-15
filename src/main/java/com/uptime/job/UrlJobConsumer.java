package com.uptime.job;


import com.uptime.config.MessageQueueConfig;
import com.uptime.dto.CheckURLJob;
import com.uptime.model.Activity;
import com.uptime.model.Monitor;
import com.uptime.repository.MonitorRepository;
import com.uptime.service.ActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.beans.PropertyChangeListener;

@Component
@Slf4j
public class UrlJobConsumer {

    @Autowired
    ActivityService activityService;

    @Autowired
    MonitorRepository monitorRepository;

    @Autowired
    JmsTemplate jmsTemplate;

    @JmsListener(destination = MessageQueueConfig.JOB_QUEUE, containerFactory = "containerFactory")
    public void consumeJob(CheckURLJob checkURLJob) {
        log.info("Message received");
        checkURLJob.execute();
        Monitor monitor = checkURLJob.getMonitor();
        monitorRepository.updateMonitorStatus(monitor.getId(),monitor.getCurrentStatus().name());
        Activity activity = activityService.createActivity(checkURLJob);
        log.info("Activity {}-{}",activity.getStatus(),activity.getMonitor());
        if (!checkURLJob.getResult()) {
            jmsTemplate.convertAndSend(MessageQueueConfig.MAIL_QUEUE, activity);
        }
        log.info("Ping - {}, result - {}", checkURLJob.getUrl(), checkURLJob.getResult());
    }
}
