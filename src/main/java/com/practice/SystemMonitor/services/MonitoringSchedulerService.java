package com.practice.SystemMonitor.services;

import com.practice.SystemMonitor.jobs.SystemMonitorJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;


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

}
