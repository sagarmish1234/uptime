package com.uptime.dto.monitor;

import com.uptime.model.CheckFrequency;

public record MonitorRequest(String url, CheckFrequency checkFrequency) {
}
