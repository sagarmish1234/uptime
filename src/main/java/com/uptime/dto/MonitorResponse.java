package com.uptime.dto;

import com.uptime.model.CheckFrequency;
import com.uptime.model.CheckStatus;

public record MonitorResponse(String url, CheckStatus currentStatus, CheckFrequency checkFrequency,boolean isPaused) {
}
