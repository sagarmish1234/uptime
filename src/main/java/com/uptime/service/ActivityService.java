package com.uptime.service;

import com.uptime.dto.CheckURLJob;
import com.uptime.model.Activity;
import com.uptime.model.CheckStatus;
import com.uptime.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.ExecutorService;

@Component
public class ActivityService {


    @Autowired
    ActivityRepository activityRepository;

    public Activity createActivity(CheckURLJob job){
        Activity activity = Activity.builder()
                .monitor(job.getMonitor())
                .dateTime(LocalDateTime.now(ZoneId.of("UTC")))
                .status(job.getResult() ? CheckStatus.UP : CheckStatus.DOWN)
                .build();
        return activityRepository.save(activity);
    }

}
