package com.crystal.customizedpos.Configuration;

import java.sql.Connection;
import java.sql.SQLException;
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

    @Override
    public void run() {
        System.out.println("Task executed at: " + new java.util.Date());
        Connection con = null;
        try {
            CommonFunctions cf = new CommonFunctions();
            con = new CommonFunctions().getConnectionJDBC();
            con.setAutoCommit(false);

            List<LinkedHashMap<String, Object>> listToBlock = lobjconfigdao.getLast24HourNotCheckedEmployees(con);
            for (LinkedHashMap<String, Object> emp : listToBlock) {
                HashMap<String, Object> tempHm = new HashMap<>();
                tempHm.put("hdnselectedemployee", emp.get("user_id").toString());
                tempHm.put("txtremarks", "Cron Job Ran at " + cf.getDateTimeWithSecondsYYYYMMDDHHMMSS(con) + " And Blocked this because Absent on : " + emp.get("latest_absent_date"));
                tempHm.put("user_id", "99999");
                long blockaccessid = lobjconfigdao.saveAccessBlockEntry(tempHm, con);
                tempHm.put("employee_id", emp.get("user_id").toString());
                tempHm.put("supervisor_id", "99999");
                tempHm.put("reason", "Found Absent By System on " + emp.get("latest_absent_date"));
                tempHm.put("remark", "Also added to Block Register: " + blockaccessid);
                tempHm.put("txtfromDate", cf.getDateASDDMMYYYYFromYYYYMMDD(emp.get("latest_absent_date").toString()));
                tempHm.put("txttoDate", cf.getDateASDDMMYYYYFromYYYYMMDD(emp.get("latest_absent_date").toString()));
                lobjconfigdao.saveSupervisorSubmitLeave(con, tempHm);
            }
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                con.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Create a new Timer
        Timer timer = new Timer();

        // Create the ScheduledTask instance (this class also implements TimerTask)
        ScheduledTaskAndListener task = new ScheduledTaskAndListener();

        // Calculate the delay to the next full hour
        long currentTime = System.currentTimeMillis();
        long nextFullHour = (currentTime / 3600000 + 1) * 3600000; // Get the next full hour timestamp
        long initialDelay = nextFullHour - currentTime; // Delay until the next full hour

        // Schedule the task to run at the next full hour and then every hour thereafter
        System.out.println("Scheduling task to run at the top of each hour...");
        timer.scheduleAtFixedRate(task, initialDelay, 3600000); // 3600000 ms = 1 hour
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Clean up resources if needed
    }
}
