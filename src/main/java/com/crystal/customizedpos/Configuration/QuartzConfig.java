package com.crystal.customizedpos.Configuration;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail jobDetail() {
        return JobBuilder.newJob(MyJob.class)
                .withIdentity("myJob", "group1")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger trigger(JobDetail jobDetail) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity("myTrigger", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 08 23 * * ?"))  // Cron expression
                .build();
    }
}
