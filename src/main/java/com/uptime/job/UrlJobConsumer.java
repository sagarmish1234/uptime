package com.uptime.job;


import com.uptime.config.MessageQueueConfig;
import com.uptime.dto.CheckURLJob;
import com.uptime.model.Activity;
import com.uptime.service.ActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UrlJobConsumer {

    @Autowired
    ActivityService activityService;

    @Autowired
    JmsTemplate jmsTemplate;

    @JmsListener(destination = MessageQueueConfig.JOB_QUEUE, containerFactory = "containerFactory")
    public void consumeJob(CheckURLJob checkURLJob) {
        log.info("Message received");
        checkURLJob.execute();
        Activity activity = activityService.createActivity(checkURLJob);
        if (!checkURLJob.getResult()) {
            jmsTemplate.convertAndSend(MessageQueueConfig.MAIL_QUEUE, activity);
        }
        log.info("Ping - {}, result - {}", checkURLJob.getUrl(), checkURLJob.getResult());
    }
}
