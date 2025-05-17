package com.practice.SystemMonitor.controller;

import com.practice.SystemMonitor.model.MetricsStats;
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
    public List<SystemMetricsRecord> getMonitoringHistory(@RequestParam(defaultValue = "10") int n) {
        return CsvMetricsReader.readFirstNRecords(n);
    }

    @GetMapping("/range")
    public List<SystemMetricsRecord> getMonitoringHistoryInRange(@RequestParam String from, @RequestParam String to) {
        return CsvMetricsReader.readRecordsBetween(from, to);
    }

    @GetMapping("/stats")
    public MetricsStats getStatsInRange(@RequestParam String from, @RequestParam String to) {
        List<SystemMetricsRecord> records = CsvMetricsReader.readRecordsBetween(from, to);

        double totalCpu = 0;
        long maxMemory = Long.MIN_VALUE;
        long minMemory = Long.MAX_VALUE;

        for (SystemMetricsRecord record : records) {
            totalCpu += record.getCpuLoad();
            maxMemory = Math.max(maxMemory, record.getUsedMemory());
            minMemory = Math.min(minMemory, record.getUsedMemory());
        }

        MetricsStats stats = new MetricsStats();
        stats.setAvgCpuLoad(records.isEmpty() ? 0 : totalCpu / records.size());
        stats.setMaxUsedMemory(records.isEmpty() ? 0 : maxMemory);
        stats.setMinUsedMemory(records.isEmpty() ? 0 : minMemory);
        stats.setCount(records.size());

        return stats;
    }

}
