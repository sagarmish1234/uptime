package com.uptime.service.mail;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.emails.Email;
import com.uptime.service.mail.args.EmailArguments;
import com.uptime.service.mail.factory.ContentFactory;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Primary
@Slf4j
public class MailerSendEmailService implements MailService {

    private static final String UPTIME_NAME = "Uptime";
    private static final String UPTIME_EMAIL = "uptime@trial-0p7kx4x2exvg9yjr.mlsender.net";


    private final MailerSend mailerSend;
    private final String mailToken;

    public MailerSendEmailService(MailerSend mailerSend, @Value("${MAIL_TOKEN}") String mailToken) {
        this.mailerSend = mailerSend;
        this.mailToken = mailToken;
    }

    @PostConstruct
    void initialize() {
        mailerSend.setToken(mailToken);
    }

    public boolean sendMail(Map<String, Object> args, ContentFactory contentFactory) {
        EmailArguments details = contentFactory.populateDetails(args);
        try {
            Email email = createEmail();
            email.setFrom(UPTIME_NAME, UPTIME_EMAIL);
            email.addRecipient(details.to().name(), details.to().email());
            email.setSubject(details.subject().content());
            email.setHtml(details.html().content());
            MailerSendResponse response = mailerSend.emails().send(email);
            return response.responseStatusCode == 202;
        } catch (Exception e) {
            log.error("Could not send email", e);
        }
        return false;
    }

    public Email createEmail() {
        return new Email();
    }
}
