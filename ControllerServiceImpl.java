package com.crystal.Frameworkpackage;



import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;









public class ControllerServiceImpl extends CommonFunctions {

	
	private static final int MegaBytes = 1024 * 1024;

	public void serveRequest(HttpServletRequest request, HttpServletResponse response)
			 {

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
				logger.debug(s);
			}

			//logger.debug("JVM freeMemory: " + freeMemory);
			//logger.debug("JVM totalMemory also equals to initial heap size of JVM : " + totalMemory);
			//logger.debug("JVM maxMemory also equals to maximum heap size of JVM: " + maxMemory);

			//logger.debug("Request Start Time " + startDatetime);
			String ApplicationName = ((HttpServletRequest) request).getContextPath().replace("/", "");
			//logger.debug("Inside Serve Request" + ApplicationName);
			String action = request.getParameter("a") == null ? request.getParameter("actionName")
					: request.getParameter("a");
			action = action == null ? "showHomePage" : action;

			logger.debug("Action Flag receieved is :- " + action);
			con = getConnectionJDBC();
			con.setAutoCommit(false);

			//logger.debug("Connection Opened Succesfully");
			reqStartTime = getDateTime(con);
			//logger.debug("Datetime From DB Received as" + reqStartTime);

			
			//System.out.println(actions);			
			boolean isBypassed =  lstbypassedActions.contains(action);
			
			
			logger.debug("is ByPassed :-" + isBypassed);
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
				logger.debug("Session Found as Null and the action is also not bypassed");
				
				response.sendRedirect("frameworkjsps/Login.jsp"); // No logged-in user found, so redirect to login page.
				response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
				response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
				mapFromRequest = getMapfromRequest(request, reqStartTime, webPortal, con);
				
				return;
			}

			// if the action is not bypassed then check if this action is mapped to list of
			// actions available for this role
			//HashSet<String> allowedActionsForThisRole = (HashSet<String>) request.getSession().getAttribute("actions");
			String userId="0";
			HashSet<String> allowedActionsForThisRole=null;
			HashSet<Integer> allowedReportsForThisRole=null;
			
			if(request.getSession().getAttribute("userdetails")!=null)
			{
				userId = ((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");			
				 allowedActionsForThisRole= getActionsForthisUserDecoupled(Long.valueOf(userId), con,CommonFunctions.roles);			
				 allowedReportsForThisRole= getReportsForthisUserDecoupled(Long.valueOf(userId), con,CommonFunctions.roles);			
				 
			}
			//logger.info("List of Allowed Actions for this role" + allowedActionsForThisRole);
			if (allowedActionsForThisRole == null) {
				allowedActionsForThisRole = new HashSet<String>();
			}
			if (isBypassed && action != null) {
				allowedActionsForThisRole.add(action);
			}

			if (action != null && !allowedActionsForThisRole.contains(action) ) {
				logger.debug("Redirecting to Unauthorized Page as Action is "+action );				
				RequestDispatcher dispatcher = request.getRequestDispatcher("frameworkjsps/unAuthorized.jsp");
				dispatcher.forward(request, response);
				mapFromRequest = getMapfromRequest(request, reqStartTime, webPortal, con);				
				return;
			}

			String reportId=request.getParameter("report_id");
			if (action != null && action.equals("showReport") && !lstbypassedReports.contains(reportId) && !allowedReportsForThisRole.contains(Integer.valueOf(reportId))) {
				logger.debug("Redirecting to Unauthorized Page as Report is "+reportId + "Allowed Reports for this role is "+allowedReportsForThisRole);				
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
				logger.debug("session is not null");
				HashMap<String, String> hm= (HashMap<String, String>) request.getSession().getAttribute("userdetails");
				logger.debug("getting userdetails"+hm);
				Integer threads_overlap=0;
				if(hm.get("threads_overlap")!=null)
				{
					threads_overlap=Integer.parseInt(hm.get("threads_overlap").toString());
				}
				
				logger.debug("getting threads_overlap"+threads_overlap);				
				if(CommonFunctions.threadSleep>threads_overlap)
				{
					Thread.sleep(CommonFunctions.threadSleep * 1000);
				}
				else
				{
					Thread.sleep(threads_overlap* 1000);
				}
			}
			
			logger.debug("Class and Method Info From Database" + frmAction.toString());
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
				
				
				List<String> roleIds =getRoleIds(Long.valueOf(userId), con);

				
				logger.debug("Found a view Name so redirecting to " + rs.getViewName());
				HashMap<String, Object> hm = rs.getReturnObject();
				hm.put("contentJspName", rs.getViewName());
				hm.put(username_constant, request.getSession().getAttribute(username_constant));
				hm.put("elementsDB",getElementsNewLogic(roleIds,CommonFunctions.elements,CommonFunctions.roles));
				
				
				
				request.setAttribute("outputObject", hm);

				if (isBypassed) {
					RequestDispatcher dispatcher = request.getRequestDispatcher("frameworkjsps/model.jsp");
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
			logger.debug("Request End Time " + EndTime);
			logger.debug("\nTime Taken For Request -- " + (EndTime.getTime() - startDatetime.getTime()));

			freeMemory = Runtime.getRuntime().freeMemory() / MegaBytes;
			totalMemory = Runtime.getRuntime().totalMemory() / MegaBytes;
			maxMemory = Runtime.getRuntime().maxMemory() / MegaBytes;

			//logger.info("Used Memory in JVM: " + (maxMemory - freeMemory));
			//logger.info("freeMemory in JVM: " + freeMemory);
			//logger.info("totalMemory in JVM shows current size of java heap : " + totalMemory);
			//logger.info("maxMemory in JVM: " + maxMemory);
			if (freeMemory <= 20) {
				logger.debug("Calling System GC");
				System.gc();
				logger.debug("Call Completed");
			}

			//logger.info("Used Memory in JVM: " + (maxMemory - freeMemory));
			//logger.info("freeMemory in JVM: " + freeMemory);
			//logger.info("totalMemory in JVM shows current size of java heap : " + totalMemory);
			//logger.info("maxMemory in JVM: " + maxMemory);

		} catch (Exception e) {
			
			try {
			logger.error(e);
			e.printStackTrace();
			con.rollback();			
			mapFromRequest = getMapfromRequest(request, reqStartTime, webPortal, con);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("frameworkjsps/errorPage.jsp");
			dispatcher.forward(request, response);
			}
			catch(Exception m) {m.printStackTrace();}
		}
		finally
		{
			try 
			{
			con.close();
			makeAuditTrailEntry(mapFromRequest, reqStartTime, webPortal);
			}
			catch(Exception m)
			{
				m.printStackTrace();
			}
			
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
					logger.debug("Going to Make audittrail entry " + e);

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					logger.debug("Going to Make audittrail entry " + e);
				}
			}
		}).start();

	}
	
	

	public HashMap<String, Object> getMapfromRequest(HttpServletRequest request, String reqStartTime,
			String responseJson, Connection con) throws ClassNotFoundException, SQLException {

		logger.debug("Going to Make audittrail entry ");
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
