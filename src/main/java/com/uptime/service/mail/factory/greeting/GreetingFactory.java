package com.uptime.service.mail.factory.greeting;

import com.uptime.model.UserInfo;
import com.uptime.model.VerificationToken;
import com.uptime.service.mail.args.EmailArguments;
import com.uptime.service.mail.args.EmailUser;
import com.uptime.service.mail.args.Html;
import com.uptime.service.mail.args.Subject;
import com.uptime.service.mail.factory.ContentFactory;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class GreetingFactory implements ContentFactory {


    private final Configuration configuration;
    private final String url;

    public GreetingFactory(Configuration configuration, @Value("${RENDER_EXTERNAL_URL}") String url) {
        this.configuration = configuration;
        this.url = url;
    }

    public EmailArguments populateDetails(Map<String, Object> args) {

        UserInfo userInfo = (UserInfo) args.get("userInfo");
        VerificationToken token = (VerificationToken) args.get("verificationToken");
        EmailUser to = new EmailUser(userInfo.getFirstName(), userInfo.getEmail());
        Subject subject = new Subject("Welcome to Uptime");
        Html html = new Html(getHtml(userInfo, token));
        return new EmailArguments(to,subject,html);
    }

    public String getHtml(UserInfo user, VerificationToken token) {
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("user", user);
        model.put("verificationUrl", String.format(url + "/api/v1/verify?token=%s", token.getToken()));
        try {
            configuration.getTemplate("uptime-signin.ftlh").process(model, stringWriter);
        } catch (TemplateException | IOException e) {
            log.error("Failed to process template ", e);
        }
        return stringWriter.getBuffer().toString();
    }
}
