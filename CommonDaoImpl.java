package com.crystal.Frameworkpackage;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class CommonDaoImpl extends CommonFunctions{
    public LinkedHashMap<String, String> getReportdetails(String reportId,Connection con) throws Exception
	{		
		ArrayList<Object> parameters = new ArrayList<>();
		String query="select * from report where report_id=?";		
		parameters.add(reportId);
		return getMap(parameters, query, con);			
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
	
}
