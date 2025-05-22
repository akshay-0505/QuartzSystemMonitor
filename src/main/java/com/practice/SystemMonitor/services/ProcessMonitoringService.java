package com.practice.SystemMonitor.services;

import com.practice.SystemMonitor.model.ProcessInfo;
import org.springframework.beans.factory.annotation.Autowired;
import oshi.SystemInfo;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProcessMonitoringService {

    @Autowired
    private SystemInfo systemInfo;

    public List<ProcessInfo> getTopProcesses(int count) {
        OperatingSystem os = systemInfo.getOperatingSystem();
        List<OSProcess> processes = os.getProcesses();

        return processes.stream()
                .sorted(Comparator.comparingDouble(OSProcess::getProcessCpuLoadCumulative).reversed())
                .limit(count)
                .map(p -> new ProcessInfo(
                        p.getName(),
                        p.getProcessID(),
                        100.0 * p.getProcessCpuLoadCumulative(),
                        p.getResidentSetSize()))
                .collect(Collectors.toList());
    }

    public List<ProcessInfo> getTopProcesses(String sort, int count) {
        List<OSProcess> allProcesses = systemInfo.getOperatingSystem().getProcesses();

        Stream<OSProcess> sorted = allProcesses.stream();
        if ("memory".equalsIgnoreCase(sort)) {
            sorted = sorted.sorted(Comparator.comparingLong(OSProcess::getResidentSetSize).reversed());
        } else {
            sorted = sorted.sorted(Comparator.comparingDouble(OSProcess::getProcessCpuLoadCumulative).reversed());
        }

        return sorted.limit(count)
                .map(p -> new ProcessInfo(p.getName(), p.getProcessID(),
                        100.0 * p.getProcessCpuLoadCumulative(),
                        p.getResidentSetSize()))
                .collect(Collectors.toList());
    }

}
