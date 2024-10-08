package com.uptime.controller;

import com.uptime.dto.MessageResponse;
import com.uptime.dto.monitor.MonitorRequest;
import com.uptime.dto.monitor.MonitorResponse;
import com.uptime.model.UserInfo;
import com.uptime.service.MonitorService;
import com.uptime.service.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Slf4j
@CrossOrigin
public class MonitorController {

    @Autowired
    MonitorService monitorService;

    @PostMapping("/monitor/create")
    public MessageResponse createMonitor(@RequestBody MonitorRequest monitorRequest) {
        UserInfo userInfo = UserDetailsServiceImpl.extractCurrentUserInfo();
        monitorService.createMonitor(monitorRequest, userInfo);
        return new MessageResponse("Monitor created successfully");
    }

    @GetMapping("/monitors/{user}")
    public List<MonitorResponse> fetchMonitorsForUser(@PathVariable String user){
        UserInfo userInfo = UserDetailsServiceImpl.extractCurrentUserInfo();
        return monitorService.fetchMonitorsForUser(user,userInfo).stream().map(monitor -> new MonitorResponse(monitor.getId(),monitor.getUrl(),monitor.getCurrentStatus(),monitor.getCheckFrequency())).toList();
    }

    @DeleteMapping("/monitor/{id}/delete")
    public MessageResponse removeMonitor(@PathVariable String id){
        monitorService.removeMonitor(id);
        return new MessageResponse("Monitor successfully removed");
    }

    @PutMapping("/monitor/{id}/pause/{toPause}")
    public MessageResponse pauseUnpauseMonitor(@PathVariable String id,@PathVariable Boolean toPause ){
        monitorService.pauseUnpauseMonitor(id, toPause);
        return new MessageResponse("Monitor paused successfully");
    }

    @PutMapping("/monitor/{id}/update")
    public MessageResponse updateMonitor(@PathVariable String id,@RequestBody MonitorRequest request){
        monitorService.updateMonitor(id,request);
        return new MessageResponse("Monitor updated successfully");
    }

}
