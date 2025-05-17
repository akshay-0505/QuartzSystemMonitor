package com.practice.SystemMonitor.controller;

import com.practice.SystemMonitor.services.MonitoringSchedulerService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/scheduler")
public class SchedulerController {

    @Autowired
    private MonitoringSchedulerService schedulerService;

    @PostMapping("/start")
    public ResponseEntity<String> startScheduler(@RequestParam int intervalInSeconds) {
        try {
            schedulerService.scheduleJob(intervalInSeconds);
            return ResponseEntity.ok("Scheduler started with interval: " + intervalInSeconds + " seconds");
        } catch (SchedulerException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateInterval(@RequestParam int newIntervalInSeconds) {
        try {
            schedulerService.rescheduleJob(newIntervalInSeconds);
            return ResponseEntity.ok("Scheduler interval updated to: " + newIntervalInSeconds + " seconds");
        } catch (SchedulerException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}
