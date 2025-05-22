package com.practice.SystemMonitor.config;

import com.practice.SystemMonitor.jobs.SystemMonitorJob;
import com.practice.SystemMonitor.services.HardwareMonitoringService;
import com.practice.SystemMonitor.services.MonitoringSchedulerService;
import com.practice.SystemMonitor.services.NetworkMonitoringService;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import oshi.SystemInfo;

@Configuration
public class QuartzConfig {
    @Bean
    public JobDetail systemMonitorJobDetail() {
        return JobBuilder.newJob(SystemMonitorJob.class)
                .withIdentity("systemMonitorJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger systemMonitorJobTrigger() {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(10)  // change interval here
                .repeatForever();

        return TriggerBuilder.newTrigger()
                .forJob(systemMonitorJobDetail())
                .withIdentity("systemMonitorTrigger")
                .withSchedule(scheduleBuilder)
                .build();
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        return new SchedulerFactoryBean();
    }

    @Bean
    public MonitoringSchedulerService createMonitoringSchedulerService() {
        return new MonitoringSchedulerService();
    }

    @Bean
    public HardwareMonitoringService createHardwareMonitoringService() {
        return new HardwareMonitoringService(getSystemInfoInstance());
    }

    @Bean
    NetworkMonitoringService createNetworkMonitoringService() {
        return new NetworkMonitoringService();
    }

    @Bean
    public SystemInfo getSystemInfoInstance() {
        return new SystemInfo();
    }
}
