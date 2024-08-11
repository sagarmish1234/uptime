package com.uptime.dto.monitor;

import com.uptime.model.CheckFrequency;
import com.uptime.model.CheckStatus;

public record MonitorResponse(String id,String url, CheckStatus currentStatus, CheckFrequency checkFrequency) {
}
