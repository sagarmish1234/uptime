package com.uptime.service.mail.factory;

import com.uptime.service.mail.args.EmailArguments;

import java.util.HashMap;
import java.util.Map;


public interface ContentFactory {
    EmailArguments populateDetails(Map<String, Object> args);
}
