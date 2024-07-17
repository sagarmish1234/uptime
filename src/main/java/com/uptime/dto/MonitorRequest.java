package com.uptime.dto;

import com.uptime.model.CheckFrequency;

public record MonitorRequest(String url, CheckFrequency checkFrequency) {
}
