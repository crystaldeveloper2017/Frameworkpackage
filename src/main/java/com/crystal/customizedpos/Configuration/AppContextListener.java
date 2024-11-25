package com.crystal.customizedpos.Configuration;

import java.util.Calendar;
import java.util.Date;
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

        // Schedule the task to start at the next whole hour and then run every hour
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
        calendar.add(Calendar.HOUR, 1); // Move to the next hour
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Date nextHour = calendar.getTime();
        return nextHour.getTime() - System.currentTimeMillis();
    }
}
