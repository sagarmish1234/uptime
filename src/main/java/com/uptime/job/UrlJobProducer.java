package com.uptime.job;

import com.uptime.config.MessageQueueConfig;
import com.uptime.dto.CheckURLJob;
import com.uptime.model.CheckFrequency;
import com.uptime.model.Monitor;
import com.uptime.service.MonitorService;
import jakarta.jms.Queue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalTime;
import java.util.List;

@Component
@Slf4j
public class UrlJobProducer {


    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    MonitorService monitorService;


    RestTemplate restTemplate = new RestTemplate();

    @Scheduled(cron = "0 0/3 * * * *")
    public void every3Minute() {
        log.info("Job executed every 3 minute {}", LocalTime.now());
        createJobs(CheckFrequency.EVERY_3_MINUTES);
    }

    @Scheduled(cron = "0 0/5 * * * *")
    public void every5Minute() {
        log.info("Job executed every 5 minute {}", LocalTime.now());
        createJobs(CheckFrequency.EVERY_5_MINUTES);
    }

    @Scheduled(cron = "0 0/15 * * * *")
    public void every15Minute() {
        log.info("Job executed every 15 minute {}", LocalTime.now());
        createJobs(CheckFrequency.EVERY_15_MINUTES);
    }

    @Scheduled(cron = "0 0/30 * * * *")
    public void every30Minute() {
        log.info("Job executed every 30 minute {}", LocalTime.now());
        createJobs(CheckFrequency.EVERY_30_MINUTES);
    }

    @Scheduled(cron = "0 0 * * * *")
    public void every60Minute() {
        log.info("Job executed every 60 minute {}", LocalTime.now());
        createJobs(CheckFrequency.EVERY_60_MINUTES);
    }

    private void createJobs(CheckFrequency frequency){
        List<Monitor> monitors = monitorService.fetchMonitorWithForFrequency(frequency);
        monitors.stream().map(monitor -> CheckURLJob.builder().url(monitor.getUrl()).monitor(monitor).build()).forEach(job -> {
            jmsTemplate.convertAndSend(MessageQueueConfig.JOB_QUEUE,job);
        });
    }
}
