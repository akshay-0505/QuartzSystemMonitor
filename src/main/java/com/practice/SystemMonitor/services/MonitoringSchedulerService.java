package com.practice.SystemMonitor.services;

import com.practice.SystemMonitor.jobs.SystemMonitorJob;
import com.practice.SystemMonitor.model.ProcessInfo;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import oshi.SystemInfo;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class MonitoringSchedulerService {
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    public void scheduleJob(int intervalInSeconds) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();

        JobDetail jobDetail = JobBuilder.newJob(SystemMonitorJob.class)
                .withIdentity("monitoringJob")
                .storeDurably()
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("monitoringTrigger")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(intervalInSeconds)
                        .repeatForever())
                .build();

        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.start();
    }

    public void rescheduleJob(int newIntervalInSeconds) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        TriggerKey triggerKey = TriggerKey.triggerKey("monitoringTrigger");

        Trigger newTrigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerKey)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(newIntervalInSeconds)
                        .repeatForever())
                .build();

        scheduler.rescheduleJob(triggerKey, newTrigger);
    }

    public List<ProcessInfo> getTopProcesses(int count) {
        final SystemInfo systemInfo = new SystemInfo();
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
}
