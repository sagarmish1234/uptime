package com.uptime.job;

import com.uptime.config.MessageQueueConfig;
import com.uptime.model.Activity;
import com.uptime.service.mail.MailService;
import com.uptime.service.mail.factory.alert.AlertFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class MailConsumer {

    @Autowired
    MailService mailService;

    @Autowired
    AlertFactory alertFactory;


    @JmsListener(destination = MessageQueueConfig.MAIL_QUEUE, containerFactory = "containerFactory")
    public void consumeMail(Activity activity) {

        mailService.sendMail(Map.of("activity", activity), alertFactory);

    }


}
