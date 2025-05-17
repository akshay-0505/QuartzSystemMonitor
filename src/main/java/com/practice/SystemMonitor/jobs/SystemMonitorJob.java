package com.practice.SystemMonitor.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;

import java.util.logging.Logger;

public class SystemMonitorJob implements Job {

    private static final Logger logger = Logger.getLogger(SystemMonitorJob.class.getName());
    private static final SystemInfo systemInfo = new SystemInfo();
    private static final CentralProcessor processor = systemInfo.getHardware().getProcessor();

    // Store previous ticks as static state
    private static long[] prevTicks = processor.getSystemCpuLoadTicks();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            // Sleep a little to simulate interval between ticks
            Thread.sleep(1000);

            long[] currentTicks = processor.getSystemCpuLoadTicks();
            double cpuLoad = processor.getSystemCpuLoadBetweenTicks(prevTicks) * 100;
            prevTicks = currentTicks;

            GlobalMemory memory = systemInfo.getHardware().getMemory();
            long totalMemory = memory.getTotal() / (1024 * 1024);
            long availableMemory = memory.getAvailable() / (1024 * 1024);

            logger.info(String.format("CPU Load: %.2f%%", cpuLoad));
            logger.info(String.format("Memory: %d MB used / %d MB total",
                    (totalMemory - availableMemory), totalMemory));

        } catch (InterruptedException e) {
            throw new JobExecutionException("Monitoring job interrupted", e);
        }
    }
}
