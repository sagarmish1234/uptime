package com.uptime.controller;

import com.uptime.dto.MessageResponse;
import com.uptime.dto.MonitorRequest;
import com.uptime.dto.MonitorResponse;
import com.uptime.model.Monitor;
import com.uptime.model.UserInfo;
import com.uptime.service.MonitorService;
import com.uptime.service.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Slf4j
@CrossOrigin
public class MonitorController {

    @Autowired
    MonitorService monitorService;

    @PostMapping("/create/monitor")
    public MessageResponse createMonitor(@RequestBody MonitorRequest monitorRequest) {
        UserInfo userInfo = UserDetailsServiceImpl.extractCurrentUserInfo();
        monitorService.createMonitor(monitorRequest, userInfo);
        return new MessageResponse("Monitor created successfully");
    }

    @GetMapping("/monitors/{user}")
    public List<MonitorResponse> fetchMonitorsForUser(@PathVariable String user){
        UserInfo userInfo = UserDetailsServiceImpl.extractCurrentUserInfo();
        return monitorService.fetchMonitorsForUser(user,userInfo).stream().map(monitor -> new MonitorResponse(monitor.getUrl(),monitor.getCurrentStatus(),monitor.getCheckFrequency(),monitor.isPaused())).toList();
    }

}
