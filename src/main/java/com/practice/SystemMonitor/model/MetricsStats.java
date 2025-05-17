package com.practice.SystemMonitor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MetricsStats {
    private double avgCpuLoad;
    private long maxUsedMemory;
    private long minUsedMemory;
    private int count;

    // Constructor, Getters, Setters
}

