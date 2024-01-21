package com.crystal.Frameworkpackage;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;



public class CommonServiceImpl extends CommonFunctions {

    CommonDaoImpl lobjCommonDaoImpl=new CommonDaoImpl();
    public CustomResultObject showReport(HttpServletRequest request,Connection con) throws SQLException
	{
	 	CustomResultObject rs=new CustomResultObject();
		
	 	HashMap<String, Object> outputMap=new HashMap<>();			
		
		String report_id=request.getParameter("report_id")==null?"":request.getParameter("report_id");
		String exportFlag= request.getParameter("exportFlag")==null?"":request.getParameter("exportFlag");
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+"/";	
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		

		try
	 	{   
			LinkedHashMap reportDetails=lobjCommonDaoImpl.getReportdetails(report_id,con);
		List<LinkedHashMap<String, Object>> listOfParameters=lobjCommonDaoImpl.getReportParameters(report_id,con);
		List<LinkedHashMap<String, Object>> listOfColumns=lobjCommonDaoImpl.getReportColumns(report_id,con);

		LinkedHashMap<String, Object> defaultParameterValues=new LinkedHashMap<>();

		for(LinkedHashMap<String, Object> param:listOfParameters)
		{
			var parameterValue=request.getParameter(param.get("parameter_form_id").toString());
			if(parameterValue==null &&  param.get("parameter_type").equals("date") && param.get("default_value").equals("~todaysdate"))
			{
				String dateFromDB=getDateFromDB(con);
				outputMap.put(param.get("parameter_form_id").toString(),dateFromDB);	
				defaultParameterValues.put(param.get("parameter_form_id").toString(),dateFromDB);
			}
			else if(parameterValue==null &&  param.get("parameter_type").equals("select"))
			{
				outputMap.put(param.get("parameter_form_id").toString(),param.get("default_value").toString());	
				defaultParameterValues.put(param.get("parameter_form_id").toString(), param.get("default_value").toString());
			}
			else
			{
				outputMap.put(param.get("parameter_form_id").toString(), request.getParameter(param.get("parameter_form_id").toString()));
			}
		}

		

		
		Class<?>[] paramString = new Class[2];
			paramString[0] = HashMap.class;
			paramString[1] = Connection.class;

		Class<?> cls = Class.forName(reportDetails.get("class_name").toString());
			Object obj = cls.newInstance();
			//Thread.sleep(4000);
			Method method = cls.getDeclaredMethod(reportDetails.get("method_name").toString(), paramString);
			
			
			
			List<LinkedHashMap<String, Object>> reportData =  (List<LinkedHashMap<String, Object>>) method.invoke(obj,outputMap, con);

		 //lObjConfigDao.getWPOStatistics(outputMap,con);


		
		reportDetails.put("parameters", listOfParameters);
		reportDetails.put("columns", listOfColumns);
		outputMap.put("reportData", reportData);
		outputMap.put("defaultParameterValues", defaultParameterValues);
		
		if(!exportFlag.isEmpty())
				{				
					List<String> columnNames=lobjCommonDaoImpl.getListOfColumns(report_id,con);
					outputMap = getCommonFileGenerator(columnNames.toArray(new String[0]),reportData,exportFlag,DestinationPath,userId,reportDetails.get("report_name").toString(),reportDetails.get("report_name").toString());
				}
				else
				{			
					outputMap.put("reportDetails", reportDetails);
					rs.setViewName("../Reports.jsp");		
				}
		rs.setReturnObject(outputMap);

	 	}
	 	catch (Exception e)
	 	{
			writeErrorToDB(e);
			rs.setHasError(true);
		}		
		return rs;
	}
	
    
}
