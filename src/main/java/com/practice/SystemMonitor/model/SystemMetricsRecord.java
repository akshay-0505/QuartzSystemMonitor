package com.practice.SystemMonitor.model;

import lombok.Getter;
import lombok.Setter;

@Getter
public class SystemMetricsRecord {
    private final String timeStamp;
    private final double cpuLoad;
    private final long usedMemory;
    private final long totalMemory;

    public SystemMetricsRecord(String timestamp, double cpuLoad, long usedMemory, long totalMemory) {
        this.timeStamp = timestamp;
        this.cpuLoad = cpuLoad;
        this.usedMemory = usedMemory;
        this.totalMemory = totalMemory;
    }
}
