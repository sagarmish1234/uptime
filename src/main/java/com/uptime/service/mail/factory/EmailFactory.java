package com.uptime.service.mail.factory;

import com.mailersend.sdk.emails.Email;
import org.springframework.stereotype.Component;

@Component
public class EmailFactory {


    public Email getEmail(){
        return new Email();
    }


}
