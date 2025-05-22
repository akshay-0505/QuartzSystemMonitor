package com.practice.SystemMonitor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProcessInfo {
    private String name;
    private int pid;
    private double cpuUsage;
    private long memoryUsage;

    // Getters and Setters
}

