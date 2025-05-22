package com.practice.SystemMonitor.services;

import com.practice.SystemMonitor.model.NetworkStats;
import org.springframework.beans.factory.annotation.Autowired;
import oshi.SystemInfo;
import oshi.hardware.NetworkIF;

import java.util.List;
import java.util.stream.Collectors;

public class NetworkMonitoringService {

    @Autowired
    private SystemInfo systemInfo;

    public List<NetworkStats> getNetworkStats() {
        List<NetworkIF> networkIFs = systemInfo.getHardware().getNetworkIFs();

        return networkIFs.stream()
                .map(nif -> {
                    nif.updateAttributes(); // must call before reading stats
                    return new NetworkStats(
                            nif.getName(),
                            nif.getIPv4addr(),
                            nif.getBytesSent(),
                            nif.getBytesRecv(),
                            nif.getIfOperStatus().toString()
                    );
                }).collect(Collectors.toList());
    }
}
