package com.practice.SystemMonitor.services;

import com.practice.SystemMonitor.model.HardwareInfo;
import org.springframework.beans.factory.annotation.Autowired;
import oshi.SystemInfo;
import oshi.hardware.Sensors;

public class HardwareMonitoringService {
    @Autowired
    private SystemInfo systemInfo;
    private final Sensors sensors;

    public HardwareMonitoringService(SystemInfo systemInfo) {
        this.sensors = systemInfo.getHardware().getSensors();
    }

    public HardwareInfo getHardwareInfo() {
        return new HardwareInfo(
                sensors.getCpuTemperature(),
                sensors.getFanSpeeds(),
                sensors.getCpuVoltage()
        );
    }
}
