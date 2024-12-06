package com.crystal.customizedpos.Configuration;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {
    private Timer timer;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        timer = new Timer();

        // Calculate the delay to the next whole hour
        long delay = calculateDelayToNextHour();

        // Schedule the task to run at every whole hour
        timer.scheduleAtFixedRate(new ScheduledTask(), delay, 60 * 60 * 1000); // Every 1 hour
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (timer != null) {
            timer.cancel();
        }
    }

    // Calculate the delay to the next whole hour
    private long calculateDelayToNextHour() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.HOUR, 1); // Move to the next hour

        long nextHour = calendar.getTimeInMillis();
        return nextHour - System.currentTimeMillis();
    }

    // Your scheduled task
    class ScheduledTask extends TimerTask {
        @Override
        public void run() {
            System.out.println("Task executed at: " + Calendar.getInstance().getTime());
            // Add your task logic here
        }
    }
}
