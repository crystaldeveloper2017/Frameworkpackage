package com.crystal.customizedpos.Configuration;

import java.sql.Connection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.crystal.Frameworkpackage.CommonFunctions;

@WebListener
public class ScheduledTaskAndListener extends TimerTask implements ServletContextListener {
    ConfigurationDaoImpl lobjconfigdao = new ConfigurationDaoImpl();

    // Task logic executed at each interval
    @Override
    public void run() {
        System.out.println("Task executed at: " + new java.util.Date());
        Connection con = null;
        try {
            CommonFunctions cf = new CommonFunctions();
            con = cf.getConnectionJDBC();
            con.setAutoCommit(false);

            // Fetch employees not checked in the last 24 hours
            List<LinkedHashMap<String, Object>> listToBlock = lobjconfigdao.getLast24HourNotCheckedEmployees(con);
            for (LinkedHashMap<String, Object> emp : listToBlock) {
                HashMap<String, Object> tempHm = new HashMap<>();
                tempHm.put("hdnselectedemployee", emp.get("user_id").toString());
                tempHm.put("txtremarks", "Cron Job Ran at " + cf.getDateTimeWithSecondsYYYYMMDDHHMMSS(con) + 
                                             " And Blocked this because Absent on : " + emp.get("latest_absent_date"));
                tempHm.put("user_id", "99999");

                // Save access block entry
                long blockaccessid = lobjconfigdao.saveAccessBlockEntry(tempHm, con);

                tempHm.put("employee_id", emp.get("user_id").toString());
                tempHm.put("supervisor_id", "99999");
                tempHm.put("reason", "Found Absent By System on " + emp.get("latest_absent_date"));
                tempHm.put("remark", "Also added to Block Register: " + blockaccessid);
                tempHm.put("txtfromDate", cf.getDateASDDMMYYYYFromYYYYMMDD(emp.get("latest_absent_date").toString()));
                tempHm.put("txttoDate", cf.getDateASDDMMYYYYFromYYYYMMDD(emp.get("latest_absent_date").toString()));

                // Save supervisor leave submission
                lobjconfigdao.saveSupervisorSubmitLeave(con, tempHm);
            }
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    // Application start: Schedule the periodic task
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Create a new Timer
        Timer timer = new Timer();

        // Create the ScheduledTask instance
        ScheduledTaskAndListener task = new ScheduledTaskAndListener();

        // Define the interval (30 seconds)
        long interval = 30 * 1000; // 30 seconds in milliseconds

        // Schedule the task to run immediately and then every 30 seconds
        System.out.println("Scheduling task to run every 30 seconds from application start...");
        timer.scheduleAtFixedRate(task, 0, interval);
    }

    // Application shutdown: Clean up resources
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Application shutting down. Cleaning up resources...");
    }
}
