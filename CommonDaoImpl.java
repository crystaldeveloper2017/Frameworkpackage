package com.crystal.Frameworkpackage;

import java.sql.Connection;
import java.sql.SQLException;
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

    public List<String> getListOfColumns(String report_id, Connection con) throws ClassNotFoundException, SQLException {
        		ArrayList<Object> parameters = new ArrayList<>();
				parameters.add(report_id);
				String query="select column_value from report_columns where report_id=?";				
				return getListOfString(parameters, query, con);
        
    }
	
}
