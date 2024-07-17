package com.uptime.controller;

import com.uptime.dto.MessageResponse;
import com.uptime.dto.MonitorRequest;
import com.uptime.model.UserInfo;
import com.uptime.service.MonitorService;
import com.uptime.service.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class MonitorController {

    @Autowired
    MonitorService monitorService;

    @PostMapping("/create/monitor")
    public MessageResponse createMonitor(@RequestBody MonitorRequest monitorRequest) {
        UserInfo userInfo = UserDetailsServiceImpl.extractCurrentUserInfo();
        monitorService.createMonitor(monitorRequest, userInfo);
        return new MessageResponse("Monitor created successfully");
    }

}
