package com.practice.SystemMonitor.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

public class SystemMonitorJob implements Job {

    private static final Logger logger = Logger.getLogger(SystemMonitorJob.class.getName());
    private static final SystemInfo systemInfo = new SystemInfo();
    private static final CentralProcessor processor = systemInfo.getHardware().getProcessor();

    // Store previous ticks as static state
    private static long[] prevTicks = processor.getSystemCpuLoadTicks();

    private static final Path csvPath = Paths.get("./logs/system_metrics.csv");
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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

            // Write to CSV
            boolean fileExists = Files.exists(csvPath);
            long usedMemory = totalMemory - availableMemory;
            try (FileWriter writer = new FileWriter(csvPath.toFile(), true)) {
                if (!fileExists) {
                    writer.append("Timestamp,CPU Load (%),Used Memory (MB),Total Memory (MB)\n");
                }
                String now = LocalDateTime.now().format(formatter);
                writer.append(String.format("%s,%.2f,%d,%d\n", now, cpuLoad, usedMemory, totalMemory));
            } catch (IOException e) {
                throw new JobExecutionException("Error Occurred while adding logs to csv file");
            }
        } catch (InterruptedException e) {
            throw new JobExecutionException("Monitoring job interrupted", e);
        }
    }
}
