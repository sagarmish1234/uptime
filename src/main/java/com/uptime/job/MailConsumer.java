package com.uptime.job;

import com.uptime.config.MessageQueueConfig;
import com.uptime.model.Activity;
import com.uptime.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MailConsumer {

    @Autowired
    MailService mailService;


    @JmsListener(destination = MessageQueueConfig.MAIL_QUEUE, containerFactory = "containerFactory")
    public void consumeMail(Activity activity){

        mailService.sendAlertMail(activity);

    }



}
