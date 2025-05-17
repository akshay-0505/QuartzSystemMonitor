package com.practice.SystemMonitor.controller;

import com.practice.SystemMonitor.model.SystemMetricsRecord;
import com.practice.SystemMonitor.utils.CsvMetricsReader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/monitor")
public class MonitorController {

    @GetMapping("/history")
    public List<SystemMetricsRecord> getMonitoringHistory(@RequestParam(defaultValue = "10") int n){
        return CsvMetricsReader.readFirstNRecords(n);
    }
}
