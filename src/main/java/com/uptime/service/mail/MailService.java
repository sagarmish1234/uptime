package com.uptime.service.mail;

import com.uptime.model.Activity;
import com.uptime.model.UserInfo;
import com.uptime.model.VerificationToken;

public interface MailService {

    void sendMail(UserInfo userInfo, VerificationToken verificationToken);
    void sendAlertMail(Activity activity);
}
