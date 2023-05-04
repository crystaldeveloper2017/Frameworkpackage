package com.crystal.Frameworkpackage;


import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;







public class ControllerServiceImpl extends CommonFunctions {

	
	private static final int MegaBytes = 1024 * 1024;

	public void serveRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ClassNotFoundException, SQLException, InterruptedException {

		response.addHeader("Access-Control-Allow-Origin", "*");
		Connection con = null;
		response.addHeader("Access-Control-Allow-Origin", "*");
		HashMap<String, Object> mapFromRequest = null;
		String reqStartTime = null;
		try {
			Date startDatetime = new Date();
			
			long freeMemory = Runtime.getRuntime().freeMemory() / MegaBytes;
			long totalMemory = Runtime.getRuntime().totalMemory() / MegaBytes;
			long maxMemory = Runtime.getRuntime().maxMemory() / MegaBytes;
			
			for(String s:getMemoryStats())
			{
				logger.info(s);
			}

			logger.info("JVM freeMemory: " + freeMemory);
			logger.info("JVM totalMemory also equals to initial heap size of JVM : " + totalMemory);
			logger.info("JVM maxMemory also equals to maximum heap size of JVM: " + maxMemory);

			logger.info("Request Start Time " + startDatetime);
			String ApplicationName = ((HttpServletRequest) request).getContextPath().replace("/", "");
			logger.info("Inside Serve Request" + ApplicationName);
			String action = request.getParameter("a") == null ? request.getParameter("actionName")
					: request.getParameter("a");
			action = action == null ? "showHomePage" : action;

			logger.info("Action Flag receieved is :- " + action);
			con = getConnectionJDBC();
			con.setAutoCommit(false);

			logger.info("Connection Opened Succesfully");
			reqStartTime = getDateTime(con);
			logger.info("Datetime From DB Received as" + reqStartTime);

			
			//System.out.println(actions);			
			boolean isBypassed =  lstbypassedActions.contains(action);
			
			logger.info("is ByPassed :-" + isBypassed);
			if ((action == null || action.equals("")) && request.getSession().getAttribute(username_constant) != null) {
				logger.info("Redirecting Back to homepage");
				response.sendRedirect("?a=showHomePage"); // No logged-in user found, so redirect to login page.
				response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
				response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
				response.setDateHeader("Expires", 0);
				mapFromRequest = getMapfromRequest(request, reqStartTime, webPortal, con);
				
				return;
			}
			request.getSession().setAttribute("projectName",CommonFunctions.projectName);
			// send to login if session is null and the action is also not bypassed
			if (!isBypassed && request.getSession().getAttribute(username_constant) == null) {
				logger.info("Session Found as Null and the action is also not bypassed");
				
				response.sendRedirect("frameworkjsps/Login.jsp"); // No logged-in user found, so redirect to login page.
				response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
				response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
				mapFromRequest = getMapfromRequest(request, reqStartTime, webPortal, con);
				
				return;
			}

			// if the action is not bypassed then check if this action is mapped to list of
			// actions available for this role
			HashSet<String> allowedActionsForThisRole = (HashSet<String>) request.getSession().getAttribute("actions");
			//logger.info("List of Allowed Actions for this role" + allowedActionsForThisRole);
			if (allowedActionsForThisRole == null) {
				allowedActionsForThisRole = new HashSet<String>();
			}
			if (isBypassed && action != null) {
				allowedActionsForThisRole.add(action);
			}

			if (action != null && !allowedActionsForThisRole.contains(action)) {
				logger.info("Redirecting to Unauthorized Page");
				RequestDispatcher dispatcher = request.getRequestDispatcher("frameworkjsps/unAuthorized.jsp");
				dispatcher.forward(request, response);
				mapFromRequest = getMapfromRequest(request, reqStartTime, webPortal, con);
				
				return;
			}
			// code for authorization ends

			//HashMap<String, String> classandmethodInfo = getClassNameAndMethodNameUsingJDBC(action, con);
			mapFromRequest = getMapfromRequest(request, reqStartTime, webPortal, con);
			FrmActionService frmAction= (FrmActionService)actions.get(action);
			
			// added so that threads donot over lap with each other
			if(request.getSession().getAttribute("userdetails")!=null)
			{
				HashMap<String, String> hm= (HashMap<String, String>) request.getSession().getAttribute("userdetails");
				Integer threads_overlap=Integer.parseInt(hm.get("threads_overlap").toString());
				
				if(CommonFunctions.threadSleep>threads_overlap)
				{
					Thread.sleep(CommonFunctions.threadSleep * 1000);
				}
				else
				{
					Thread.sleep(threads_overlap* 1000);
				}
			}
			
			logger.info("Class and Method Info From Database" + frmAction.toString());
			Class<?>[] paramString = new Class[2];
			paramString[0] = HttpServletRequest.class;
			paramString[1] = Connection.class;
			Class<?> cls = Class.forName(frmAction.getClassName());
			Object obj = cls.newInstance();
			//Thread.sleep(4000);
			Method method = cls.getDeclaredMethod(frmAction.getActionName(), paramString);
			CustomResultObject rs = null;
			
			
			rs = (CustomResultObject) method.invoke(obj, request, con);

			if (rs.getViewName() != null) {
				logger.info("Found a view Name so redirecting to " + rs.getViewName());
				HashMap<String, Object> hm = rs.getReturnObject();
				hm.put("contentJspName", rs.getViewName());
				hm.put(username_constant, request.getSession().getAttribute(username_constant));
				
				request.setAttribute("outputObject", hm);

				if (isBypassed) {
					RequestDispatcher dispatcher = request.getRequestDispatcher(rs.getViewName());
					dispatcher.forward(request, response);
				} else {
					RequestDispatcher dispatcher = request.getRequestDispatcher("frameworkjsps/model.jsp");
					dispatcher.forward(request, response);
				}

			} else if (rs.getAjaxData() != null) // its ajax data
			{
				//logger.info("Ajax Call so returning Ajax content:- " + rs.getAjaxData());
				response.setContentType("text/html; charset=UTF-8");
				response.setHeader("Access-Control-Allow-Origin", "*");
				response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS");
				response.setHeader("Access-Control-Allow-Headers", "*");			      
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(rs.getAjaxData());

			} else // its a file
			{
				logger.info("Its not ajax nor a view name, Unless its a file download something is wrong");
				String filepath = rs.getReturnObject().get(filename_constant).toString();
				logger.info("Found a File So returning " + "BufferedImagesFolder/" + filepath);
				response.sendRedirect("BufferedImagesFolder/" + filepath);
			}

			
			
			con.commit();
			
			Date EndTime = new Date();
			logger.info("Request End Time " + EndTime);
			logger.info("\nTime Taken For Request -- " + (EndTime.getTime() - startDatetime.getTime()));

			freeMemory = Runtime.getRuntime().freeMemory() / MegaBytes;
			totalMemory = Runtime.getRuntime().totalMemory() / MegaBytes;
			maxMemory = Runtime.getRuntime().maxMemory() / MegaBytes;

			logger.info("Used Memory in JVM: " + (maxMemory - freeMemory));
			logger.info("freeMemory in JVM: " + freeMemory);
			logger.info("totalMemory in JVM shows current size of java heap : " + totalMemory);
			logger.info("maxMemory in JVM: " + maxMemory);
			if (freeMemory <= 20) {
				logger.info("Calling System GC");
				System.gc();
				logger.info("Call Completed");
			}

			logger.info("Used Memory in JVM: " + (maxMemory - freeMemory));
			logger.info("freeMemory in JVM: " + freeMemory);
			logger.info("totalMemory in JVM shows current size of java heap : " + totalMemory);
			logger.info("maxMemory in JVM: " + maxMemory);

		} catch (Exception e) {
			logger.error(e);
			con.rollback();			
			mapFromRequest = getMapfromRequest(request, reqStartTime, webPortal, con);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("frameworkjsps/errorPage.jsp");
			dispatcher.forward(request, response);
		}
		finally
		{
			con.close();
			makeAuditTrailEntry(mapFromRequest, reqStartTime, webPortal);
			
		}
	}
	
	
	

	public void makeAuditTrailEntryDelayed(final HashMap<String, Object> hmFromRequest, final String reqStartTime,
			final String responseJson) throws SQLException, ClassNotFoundException {

		if (!CommonFunctions.isAuditEnabled) {
			return;
		}

		new Thread(new Runnable() {
			public void run() {
				try {
					makeAuditTrailEntry(hmFromRequest, reqStartTime, responseJson);
				} catch (ClassNotFoundException e) {
					logger.info("Going to Make audittrail entry " + e);

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					logger.info("Going to Make audittrail entry " + e);
				}
			}
		}).start();

	}
	
	

	public HashMap<String, Object> getMapfromRequest(HttpServletRequest request, String reqStartTime,
			String responseJson, Connection con) throws ClassNotFoundException, SQLException {

		logger.info("Going to Make audittrail entry ");
		StringBuffer requestURL = request.getRequestURL();
		if (request.getQueryString() != null) {
			requestURL.append("?").append(request.getQueryString());
		}
		String completeURL = requestURL.toString();
		String Parameters = "";
		String username = "";
		
		
		for (Map.Entry<String, String[]> entry :
			 ((Map<String, String[]>)request.getParameterMap()).entrySet()) {
			String name = entry.getKey();
			String value = entry.getValue()[0];
			Parameters += "\n" + name + " " + value;
			if (name.equals("user_id")) {
				username = dao.getUserDetailsByUserId(value, con).get("username");
			}
			 }
		
		
		
		String ipAddress = request.getHeader("X-FORWARDED-FOR") == null ? request.getRemoteAddr()
				: request.getHeader("X-FORWARDED-FOR");
		String session_username = request.getSession().getAttribute(username_constant) == null ? ""
				: request.getSession().getAttribute(username_constant).toString();
		String browserInfo = request.getHeader("User-Agent");
		HashMap<String, Object> hmDetails = new HashMap<>();

		hmDetails.put("sessionUsername", session_username);

		if (session_username.equals("")) {
			hmDetails.put("sessionUsername", username);
		}

		hmDetails.put("completeURL", completeURL);
		hmDetails.put("Parameters", Parameters);
		hmDetails.put("reqStartTime", reqStartTime);
		hmDetails.put("ipAddress", ipAddress);
		hmDetails.put("browserInfo", browserInfo);
		hmDetails.put("responseJson", responseJson);
		return hmDetails;

	}

	public void makeAuditTrailEntry(HashMap<String, Object> hmDetails, String reqStartTime, String responseJson)
			throws SQLException, ClassNotFoundException {
		try 
		{
		if (!CommonFunctions.isAuditEnabled) {
			return;
		}

		Connection con = getConnectionJDBC();
		dao.makeAuditTrailEntryDB(hmDetails, con);
		con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	ControllerDaoImpl dao = new ControllerDaoImpl();
}
