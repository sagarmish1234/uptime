package com.uptime.service.mail.args;

public record EmailArguments(EmailUser to, Subject subject, Html html) {
}
