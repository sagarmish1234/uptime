package com.uptime.service.mail.factory.alert;

import com.uptime.model.Activity;
import com.uptime.service.mail.args.EmailArguments;
import com.uptime.service.mail.args.EmailUser;
import com.uptime.service.mail.args.Html;
import com.uptime.service.mail.args.Subject;
import com.uptime.service.mail.factory.ContentFactory;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class AlertFactory implements ContentFactory {

    private final Configuration configuration;

    public AlertFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public EmailArguments populateDetails(Map<String, Object> args) {
        Activity activity = (Activity) args.get("activity");
        EmailUser to = new EmailUser(activity.getMonitor().getUserInfo().getFirstName(), activity.getMonitor().getUserInfo().getEmail());
        Subject subject = new Subject("Monitoring Alert");
        Html html = new Html(getHtml(activity));
        return new EmailArguments(to, subject, html);
    }

    public String getHtml(Activity activity) {
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        try {
            model.put("url", activity.getMonitor().getUrl());
            model.put("user", activity.getMonitor().getUserInfo());
            model.put("status", activity.getStatus());
            model.put("lastChecked", activity.getDateTime().toString());
            configuration.getTemplate("uptime-alert.ftlh").process(model, stringWriter);
        } catch (IOException | TemplateException e) {
            log.error("Failed to process template ", e);
        }
        return stringWriter.getBuffer().toString();
    }
}
