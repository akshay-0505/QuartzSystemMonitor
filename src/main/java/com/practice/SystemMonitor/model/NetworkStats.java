package com.practice.SystemMonitor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NetworkStats {
    private String name;
    private String[] ipAddresses;
    private long bytesSent;
    private long bytesReceived;
    private String status;
}
