package com.uptime.service.mail;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.emails.Email;
import com.uptime.model.Activity;
import com.uptime.model.UserInfo;
import com.uptime.model.VerificationToken;
import com.uptime.service.mail.factory.EmailFactory;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Service
@Primary
@Slf4j
public class MailerSendEmailService implements MailService {

    private final Configuration configuration;


    private final String url;
    private final MailerSend mailerSend;
    private final EmailFactory emailFactory;
    private final String mailToken;

    public MailerSendEmailService(Configuration configuration, @Value("${RENDER_EXTERNAL_URL}") String url, MailerSend mailerSend, EmailFactory emailFactory,@Value("${MAIL_TOKEN}") String mailToken) {
        this.configuration = configuration;
        this.url = url;
        this.emailFactory = emailFactory;
        this.mailerSend = mailerSend;
        this.mailToken = mailToken;
    }

    @PostConstruct
    void initialize() {
        mailerSend.setToken(mailToken);
    }

    @Override
    public boolean sendMail(UserInfo userInfo, VerificationToken verificationToken) {
        try {
            Email email = emailFactory.getEmail();
            email.setFrom("Uptime", "uptime@trial-0p7kx4x2exvg9yjr.mlsender.net");
            email.addRecipient(userInfo.getFirstName(), userInfo.getEmail());
            email.setSubject("Welcome to Uptime");
            email.setHtml(getEmailContent(userInfo, verificationToken.getToken()));
            MailerSendResponse response = mailerSend.emails().send(email);
            return response.responseStatusCode == 202;
        } catch (Exception e) {
            log.error("Could not send email", e);
        }
        return false;
    }

    @Override
    public void sendAlertMail(Activity activity) {
        try {
            Email email = emailFactory.getEmail();
            email.setFrom("Uptime", "uptime@trial-0p7kx4x2exvg9yjr.mlsender.net");
            email.setSubject("Monitoring Alert");
            email.addRecipient(activity.getMonitor().getUserInfo().getFirstName(), activity.getMonitor().getUserInfo().getEmail());
            email.setHtml(getAlertContent(activity));
            mailerSend.emails().send(email);
        } catch (Exception e) {
            log.error("Could not send email", e);
        }
    }


    String getEmailContent(UserInfo user, String token) throws IOException, TemplateException {
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("user", user);
        model.put("verificationUrl", String.format(url + "/api/v1/verify?token=%s", token));
        configuration.getTemplate("uptime-signin.ftlh").process(model, stringWriter);
        return stringWriter.getBuffer().toString();
    }


    private String getAlertContent(Activity activity) throws IOException, TemplateException {
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("url", activity.getMonitor().getUrl());
        model.put("user", activity.getMonitor().getUserInfo());
        model.put("status", activity.getStatus());
        model.put("lastChecked", activity.getDateTime().toString());
        configuration.getTemplate("uptime-alert.ftlh").process(model, stringWriter);
        return stringWriter.getBuffer().toString();
    }
}
