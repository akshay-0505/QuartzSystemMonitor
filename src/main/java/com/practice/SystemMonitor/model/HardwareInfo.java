package com.practice.SystemMonitor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HardwareInfo {
    private double temperature;
    private int[] fanSpeeds;
    private double voltage;

}

