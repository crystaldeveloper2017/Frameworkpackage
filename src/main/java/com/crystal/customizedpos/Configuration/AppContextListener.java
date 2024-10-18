package com.crystal.customizedpos.Configuration;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {
    private Timer timer;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        timer = new Timer();
        timer.schedule(new ScheduledTask(), 0,  1 *60 * 60 * 1000 ); // Every 1 Hour
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (timer != null) {
            timer.cancel();
        }
    }
}
