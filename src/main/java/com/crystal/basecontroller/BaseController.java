package com.crystal.basecontroller;

import java.io.File;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.crystal.Login.LoginServiceImpl;
import com.crystal.Frameworkpackage.*;
import com.crystal.customizedpos.Configuration.ConfigurationServiceImpl;


@WebServlet(name = "BaseController" , urlPatterns = {""})
public class BaseController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BaseController() 
    {
        super();

    }
    
    static Logger logger = Logger.getLogger(ControllerServiceImpl.class.getName());
    public static HashMap<String, FrmActionService> actions=null;
    public static HashMap<String, Role> roles=null;
    public static List<Element> elements=null;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		serverReq(request, response);

		
	}
		
	public HashMap<String, FrmActionService> getActionServiceListNew() 
	{
		Class[] Classes= {ConfigurationServiceImpl.class,LoginServiceImpl.class};
		HashMap<String, FrmActionService> reqActions = new HashMap<>();
		for(Class c:Classes)
		{		
			for(Method m:cf.getAccessibleMethods(c))
			{
				reqActions.put(m.getName(),new FrmActionService(m.getName(), c.getName(), cf.lstbypassedActions.contains(m.getName())));
				//System.out.println( c.getName()+ ""+ m.getName());
			}
		}
		return reqActions;
	}

	
	
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		serverReq(request, response);
	}

	ControllerServiceImpl cf = new ControllerServiceImpl();

	public void serverReq(HttpServletRequest request, HttpServletResponse response) {
		logger.info("Incoming Request on doGet");
		try {
			logger.info("Checking if actions is null " + actions == null);
			
			cf.serveRequest(request, response);
		} catch (Exception e) {

			cf.writeErrorToDB(e);
		}
	}

}
