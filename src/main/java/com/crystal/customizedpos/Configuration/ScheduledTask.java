package com.crystal.customizedpos.Configuration;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.crystal.Frameworkpackage.CommonFunctions;

public class ScheduledTask extends TimerTask {
    ConfigurationDaoImpl lobjconfigdao=new ConfigurationDaoImpl();
    @Override    
    public void run() 
    {        
        System.out.println("Task executed at: " + new java.util.Date());
        Connection con=null;
        try 
        {
            CommonFunctions cf=new CommonFunctions();
            con = new CommonFunctions().getConnectionJDBC();
            con.setAutoCommit(false);

            List<LinkedHashMap<String, Object>> listToBlock= lobjconfigdao.getLast24HourNotCheckedEmployees(con);
            for(LinkedHashMap<String, Object> emp:listToBlock)
            {
                HashMap<String,Object> tempHm=new HashMap<>();
                tempHm.put("hdnselectedemployee",emp.get("user_id").toString());
                tempHm.put("txtremarks","Cron Job Ran at "+cf.getDateTimeWithSeconds(con)+" And Blocked this because Lasted Checkin : "+ emp.get("last_checked_time"));
                tempHm.put("user_id","99999");
                lobjconfigdao.saveAccessBlockEntry(tempHm, con);
            }
            con.commit();
        }
        catch (Exception e) 
        {            
            e.printStackTrace();
            try
            {
                con.rollback();
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }
}
