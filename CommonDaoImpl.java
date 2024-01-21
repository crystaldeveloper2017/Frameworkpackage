package com.crystal.Frameworkpackage;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class CommonDaoImpl extends CommonFunctions{
    public LinkedHashMap<String,Object> getReportdetails(String reportId,Connection con) throws Exception
	{	
		LinkedHashMap<String,Object> reportDetails=new LinkedHashMap<>();
		
		ArrayList<Object> parameters = new ArrayList<>();
		String query="select * from report where report_id=?";		
		parameters.add(reportId);
		reportDetails=getMapReturnObject(parameters, query, con);

		
		
		query="select * from report_parameters where report_id=?";				
		List<LinkedHashMap<String,Object>> paramters= getListOfLinkedHashHashMap(parameters, query, con);
		List<LinkedHashMap<String,Object>> paramtersWithOptions=new ArrayList<>();


		for(LinkedHashMap<String,Object> lhm: paramters)
		{
			query="select * from parameters_options where parameter_id=?";				
			parameters.clear();
			parameters.add(lhm.get("parameter_id"));
			List<LinkedHashMap<String,Object>> parameterOptions= getListOfLinkedHashHashMap(parameters, query, con);
			LinkedHashMap paramNew=new LinkedHashMap<>();
			lhm.put("parameterOptions", parameterOptions);
			paramNew.putAll(lhm);
			paramtersWithOptions.add(paramNew);
		}
		reportDetails.put("paramtersWithOptions", paramtersWithOptions);

		parameters.clear();
		parameters.add(reportId);
		query="select * from report_columns where report_id=?";				
		reportDetails.put("columns", getListOfLinkedHashHashMap(parameters, query, con));


		
		

		



		return reportDetails;
	}

    public List<LinkedHashMap<String, Object>> getReportParameters(String reportId,Connection con) throws Exception
	{		
		ArrayList<Object> parameters = new ArrayList<>();
		String query="select * from report_parameters where report_id=?";		
		parameters.add(reportId);
		return getListOfLinkedHashHashMap(parameters, query, con);			
	}



    public List<LinkedHashMap<String, Object>> getReportColumns(String reportId,Connection con) throws Exception
	{		
		ArrayList<Object> parameters = new ArrayList<>();
		String query="select * from report_columns where report_id=?";		
		parameters.add(reportId);
		return getListOfLinkedHashHashMap(parameters, query, con);			
	}

    public List<String> getListOfColumns(String report_id, Connection con) throws ClassNotFoundException, SQLException {
        		ArrayList<Object> parameters = new ArrayList<>();
				parameters.add(report_id);
				String query="select column_value from report_columns where report_id=?";				
				return getListOfString(parameters, query, con);
        
    }
	
}
