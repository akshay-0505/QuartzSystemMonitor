package com.practice.SystemMonitor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/monitor")
public class SystemInfoController {

    @GetMapping("/systeminfo")
    public Map<String, Object> getSystemInfo() {
        Map<String, Object> info = new HashMap<>();

        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
        Runtime runtime = Runtime.getRuntime();

        info.put("osName", osBean.getName());
        info.put("osVersion", osBean.getVersion());
        info.put("architecture", osBean.getArch());
        info.put("availableProcessors", osBean.getAvailableProcessors());

        info.put("jvmName", runtimeBean.getVmName());
        info.put("jvmVendor", runtimeBean.getVmVendor());
        info.put("jvmVersion", runtimeBean.getVmVersion());

        info.put("systemUptimeMillis", runtimeBean.getUptime());

        long totalMemory = runtime.totalMemory();     // in bytes
        long freeMemory = runtime.freeMemory();       // in bytes
        long usedMemory = totalMemory - freeMemory;

        info.put("usedMemoryMB", usedMemory / (1024 * 1024));
        info.put("freeMemoryMB", freeMemory / (1024 * 1024));
        info.put("totalMemoryMB", totalMemory / (1024 * 1024));
        info.put("maxMemoryMB", runtime.maxMemory() / (1024 * 1024));

        return info;
    }
}
