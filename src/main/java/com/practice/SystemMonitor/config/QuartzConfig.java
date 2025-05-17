package com.practice.SystemMonitor.config;

import com.practice.SystemMonitor.jobs.SystemMonitorJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}
