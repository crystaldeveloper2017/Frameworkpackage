package com.crystal.customizedpos.Configuration;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.crystal.Login.LoginServiceImpl;
import com.crystal.Frameworkpackage.CommonFunctions;



@WebListener
public class Config implements ServletContextListener {

    public void contextInitialized(ServletContextEvent event) {
    	
    	
    	CommonFunctions cf=new CommonFunctions();
    	try {
			cf.initializeApplication(new Class[] {ConfigurationServiceImpl.class,LoginServiceImpl.class},event.getServletContext());
		} catch (ClassNotFoundException | SQLException | IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	    	

    	
    }

    public void contextDestroyed(ServletContextEvent event) {
        // Do stuff during webapp's shutdown.
    }

}
