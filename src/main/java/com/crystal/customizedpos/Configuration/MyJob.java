package com.crystal.customizedpos.Configuration;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MyJob implements Job {
    private static final Logger logger = LoggerFactory.getLogger(MyJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // Your job logic here
        logger.info("Will Block Employees job at {}", new java.util.Date());
    }
}
