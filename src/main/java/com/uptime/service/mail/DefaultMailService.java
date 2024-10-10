package com.uptime.service.mail;

import com.uptime.model.Activity;
import com.uptime.model.UserInfo;
import com.uptime.model.VerificationToken;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class DefaultMailService implements MailService{

    @Value("${spring.mail.username}")
    private String from;

//    @Value("${server.port}")
//    private String port;

    @Value("${RENDER_EXTERNAL_URL}")
    private String url;


    private final JavaMailSender javaMailSender;
    private final Configuration configuration;

    public DefaultMailService(JavaMailSender javaMailSender, Configuration configuration){
        this.javaMailSender = javaMailSender;
        this.configuration = configuration;
    }

    public void sendMail(UserInfo userInfo, VerificationToken verificationToken) {
       try{

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;
        helper = new MimeMessageHelper(message, true);//true indicates multipart message
        helper.setFrom(String.format("Uptime <%s>",from)); // <--- THIS IS IMPORTANT

        helper.setSubject("Welcome to Uptime");
        helper.setTo(userInfo.getEmail());
        helper.setText(getEmailContent(userInfo,verificationToken.getToken()), true);//true indicates body is html
        javaMailSender.send(message);
       } catch (Exception e) {
            log.error("Could not send email",e);
       }
    }
    String getEmailContent(UserInfo user,String token) throws IOException, TemplateException {
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("user", user);
        model.put("verificationUrl",String.format(url+"/api/v1/verify?token=%s", token));
        configuration.getTemplate("uptime-signin.ftlh").process(model, stringWriter);
        return stringWriter.getBuffer().toString();
    }

    public void sendAlertMail(Activity activity){
        try{

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper;
            helper = new MimeMessageHelper(message, true);//true indicates multipart message

            helper.setFrom(String.format("Uptime <%s>",from)); // <--- THIS IS IMPORTANT

            helper.setSubject("Monitoring Alert");
            helper.setTo(activity.getMonitor().getUserInfo().getEmail());
            helper.setText(getAlertContent(activity), true);//true indicates body is html
            javaMailSender.send(message);
        } catch (Exception e) {
            log.error("Could not send email",e);
        }
    }

    private String getAlertContent(Activity activity) throws IOException, TemplateException {
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("url", activity.getMonitor().getUrl());
        model.put("user",activity.getMonitor().getUserInfo());
        model.put("status",activity.getStatus());
        model.put("lastChecked",activity.getDateTime().toString());
        configuration.getTemplate("uptime-alert.ftlh").process(model, stringWriter);
        return stringWriter.getBuffer().toString();
    }

}
