package com.uptime.service.mail;

import com.uptime.service.mail.factory.ContentFactory;

import java.util.Map;

public interface MailService {

    boolean sendMail(Map<String,Object> args, ContentFactory contentFactory);

}
