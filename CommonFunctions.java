package com.crystal.framework.Frameworkpackage;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.imageio.ImageIO;
import javax.naming.ldap.StartTlsRequest;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.yaml.snakeyaml.Yaml;

import com.crystal.Login.LoginServiceImpl;

import com.crystal.customizedpos.Configuration.Config;
import com.crystal.customizedpos.Configuration.ExecuteSqlFile;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.qrcode.EncodeHintType;
import com.itextpdf.text.pdf.qrcode.ErrorCorrectionLevel;

public class CommonFunctions extends PdfPageEventHelper 
{
	
	
	public static HashMap<String, FrmActionService> actions=null;
    public static HashMap<Long, Role> roles=new HashMap<>();
    public static HashMap<String, LinkedHashMap<Long, Role>> apptypes=new HashMap<>();
    public static List<Element> elements=new ArrayList<>();
    public static HashMap<String, String> dashboardLinks=new HashMap();
    
    public static String url;
	public static String username;
	public static String password;
	public static Boolean isAuditEnabled;
	public static Boolean queryLogEnabled;	
	public static Boolean copyAttachmentsToBuffer;	
	public static String port;
	public static String schemaName;
	public static String projectName;
	public static String host;
	public static Boolean isSendEmail;
	public static String mySqlPath;
	public static List<String> lstbypassedActions;
	public static int threadSleep;
    
	
	
	public LinkedHashMap<String, String> getMap(ArrayList<Object> parameters, String query, Connection con)
			throws SQLException {
		ResultSet rs = null;
		PreparedStatement stmnt = null;
		try {
			LinkedHashMap<String, String> hm = new LinkedHashMap<>();
			stmnt = con.prepareStatement(query);
			int j = 1;
			for (Object o : parameters) {
				stmnt.setObject(j++, o);
			}
			logger.info("\n"+stmnt);
			
			Date dt1=new Date();			
			rs = stmnt.executeQuery();
			Date dt2=new Date();
			if(queryLogEnabled)
			{			
			Long l=(dt2.getTime()-dt1.getTime());
			 ArrayList<Object> parameters1 = new ArrayList<>();
			  parameters1.add(stmnt.toString().substring(stmnt.toString().lastIndexOf(":") + 1));
			  parameters1.add(String.valueOf(l));				
				insertUpdateDuablDB("insert into frm_query_log values (default,?,?,sysdate())", parameters1,con);
			}
				
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			List<String> ar1 = new ArrayList<>();
			for (int i = 1; i <= columnCount; i++) {
				ar1.add(rsmd.getColumnLabel(i));
			}
			while (rs.next()) {

				for (String s : ar1) {
					hm.put(s, rs.getString(s));
				}
			}

			return hm;
		} catch (Exception e) {
			throw e;
		} finally {
			if (rs != null)
				rs.close();
			if (stmnt != null)
				stmnt.close();
		}

	}
	
	public long insertUpdateEnhanced(Query q,Connection con) throws SQLException
    {
        String query="";        
        String updateValues="";
        String whereValues=" where ";
        String finalQuery="";
        String columnNames=" (";
        String valueNames=" (";
        PreparedStatement ps=null;
        long reqVAlue = 0;
        try 
        {
	        if(q.queryMode.equals("insert"))
	        {
	            query="insert into "+q.tableName+"";
	            finalQuery+=query+updateValues;            
	            for (Map.Entry<String, Object> entry : q.keyValueMap.entrySet()) {
	            	columnNames+=entry.getKey()+",";
	                if(entry.getValue().getClass().equals(String.class) && entry.getValue().toString().startsWith("~"))
	                {
	                	valueNames+=entry.getValue().toString().replaceAll("~", "")+",";
	                }
	                else
	                {
	                	valueNames+="?,";
	                }
	                        
	            }
	            columnNames=columnNames.substring(0,columnNames.length()-1);
	            valueNames=valueNames.substring(0,valueNames.length()-1);	            
	            finalQuery+=columnNames+")" + " values "+ valueNames+")";
	            ps=con.prepareStatement(finalQuery,Statement.RETURN_GENERATED_KEYS);            
	            int i=1;
	            for (Map.Entry<String, Object> entry : q.keyValueMap.entrySet()) 
	            {	                
	            	if(entry.getValue().getClass().equals(String.class) && entry.getValue().toString().startsWith("~"))
	            	{
	            		continue;
	            	}	            		
	                ps.setObject(i++,entry.getValue());            
	            }
	        }
	        else
	        {
	            query="update "+q.tableName+" set ";
	            for (Map.Entry<String, Object> entry : q.keyValueMap.entrySet()) 
	            {
	            	if(entry.getValue().getClass().equals(String.class) && entry.getValue().toString().startsWith("~"))
	                {
	                	updateValues+=entry.getKey()+"="+entry.getValue().toString().replace("~", "")+",";
	                }
	                else
	                {
	                	updateValues+=entry.getKey()+"=?,";
	                }
	            }
	            updateValues=updateValues.substring(0,updateValues.length()-1);
	            
	            for (Map.Entry<String, Object> entry : q.whereValueMap.entrySet()) 
	            {
	                whereValues+=entry.getKey()+"=? and ";
	            }
	            whereValues=whereValues.substring(0,whereValues.length()-5);	
	            if(q.whereValueMap.isEmpty())
	            {whereValues="";}	            	            
	            finalQuery+=query+updateValues+whereValues;
	            ps=con.prepareStatement(finalQuery,Statement.RETURN_GENERATED_KEYS);            
	            int i=1;
	            for (Map.Entry<String, Object> entry : q.keyValueMap.entrySet()) 
	            {
	            	if(entry.getValue().getClass().equals(String.class) && entry.getValue().toString().startsWith("~"))
	            	{
	            		continue;
	            	}
	                ps.setObject(i++,entry.getValue());	                
	            }
	            for (Map.Entry<String, Object> entry : q.whereValueMap.entrySet()) 
	            {
	            	ps.setObject(i++,entry.getValue());	                
	            }
	        }
	        ps.executeUpdate();
	        logger.info("\n"+ps);
	        try (ResultSet generatedKeys = ps.getGeneratedKeys()) 
			{
				if (generatedKeys.next()) 
				{					
					reqVAlue = generatedKeys.getLong(1);					
				} 
				else 
				{
					// do nothing as ID mignt not have generated
				}
			}        
        }
        catch(Exception e)
        {
        	writeErrorToDB(e);
        	throw e;
        }
        finally 
		{
			if(ps!=null)
				ps.close();
		}        
        return reqVAlue;        
    }
	
	 public String getFinYearString()
	 {
		 java.util.Date today= new java.util.Date();
		 int month=today.getMonth();
		 String reqString="";
		 if(month<3)
		 {
			 String curYear=String.valueOf(today.getYear());
			 String lastTwoDigs=curYear.substring(1, curYear.length());
			 int i=Integer.valueOf( lastTwoDigs)-1;
			 reqString=i+lastTwoDigs;
			 
		 }
		 else
		 {
			 
			 String curYear=String.valueOf(today.getYear());
			 String lastTwoDigs=curYear.substring(1, curYear.length());
			 int i=Integer.valueOf( lastTwoDigs)+1;
			 reqString=lastTwoDigs+i;
			 
			 
		 }
		 return reqString;
	 }

	public LinkedHashMap<String, Object> getMapReturnObject(ArrayList<Object> parameters, String query, Connection con)
			throws SQLException {
		ResultSet rs = null;
		PreparedStatement stmnt = null;
		try {
			LinkedHashMap<String, Object> hm = new LinkedHashMap<>();
			stmnt = con.prepareStatement(query);

			int j = 1;

			for (Object o : parameters) {
				stmnt.setObject(j++, o);
			}

			logger.info("\n"+stmnt);
			
			Date dt1=new Date();			
			rs = stmnt.executeQuery();
			Date dt2=new Date();

			if(queryLogEnabled)
			{
			
			Long l=(dt2.getTime()-dt1.getTime());
			 ArrayList<Object> parameters1 = new ArrayList<>();
			  parameters1.add(stmnt.toString().substring(stmnt.toString().lastIndexOf(":") + 1));
			  parameters1.add(String.valueOf(l));				
				insertUpdateDuablDB("insert into frm_query_log values (default,?,?,sysdate())", parameters1,con);
			}

			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();

			List<String> ar1 = new ArrayList<>();
			for (int i = 1; i <= columnCount; i++) {
				ar1.add(rsmd.getColumnLabel(i));
			}

			while (rs.next()) {

				for (String s : ar1) {
					hm.put(s, rs.getString(s));
				}
			}

			return hm;
		} catch (Exception e) {
			throw e;
		} finally {
			if (rs != null)
				rs.close();
			if (stmnt != null)
				stmnt.close();
		}
	}

	public List<LinkedHashMap<String, Object>> getListOfLinkedHashHashMap(ArrayList<Object> parameters, String query,
			Connection con) throws ClassNotFoundException, SQLException {
		ResultSet rs = null;
		PreparedStatement stmnt = null;
		try {
			List<LinkedHashMap<String, Object>> retLst = null;
			retLst = new ArrayList<LinkedHashMap<String, Object>>();
			stmnt = con.prepareStatement(query);
			int j = 1;
			for (Object o : parameters) {
				stmnt.setObject(j++, o);
			}

			logger.info("\n"+stmnt);
			
			Date dt1=new Date();			
			rs = stmnt.executeQuery();
			Date dt2=new Date();
			if(queryLogEnabled)
			{
			
			Long l=(dt2.getTime()-dt1.getTime());
			 ArrayList<Object> parameters1 = new ArrayList<>();
			  parameters1.add(stmnt.toString().substring(stmnt.toString().lastIndexOf(":") + 1));
			  parameters1.add(String.valueOf(l));				
				insertUpdateDuablDB("insert into frm_query_log values (default,?,?,sysdate())", parameters1,con);
			}

			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();

			List<String> ar1 = new ArrayList<>();
			for (int i = 1; i <= columnCount; i++) {
				ar1.add(rsmd.getColumnLabel(i));
			}

			while (rs.next()) {

				LinkedHashMap<String, Object> hm = new LinkedHashMap<>();
				for (String s : ar1) {
					hm.put(s, rs.getObject(s));
				}
				retLst.add(hm);
			}

			return retLst;
		} catch (Exception e) {
			throw e;
		} finally {
			if (rs != null)
				rs.close();
			if (stmnt != null)
				stmnt.close();

		}
	}

	public List<String> getListOfString(List<Object> parameters, String query, Connection con)
			throws ClassNotFoundException, SQLException {
		ResultSet rs1 = null;
		PreparedStatement stmnt = null;
		try {

			List<String> retLst = new ArrayList<String>();
			stmnt = con.prepareStatement(query);
			int j = 1;
			for (Object o : parameters) {
				stmnt.setObject(j++, o);
			}

			logger.info("\n"+stmnt);
			
			Date dt1=new Date();			
			rs1 = stmnt.executeQuery();
			Date dt2=new Date();
			
			if(queryLogEnabled)
			{
			
			Long l=(dt2.getTime()-dt1.getTime());
			 ArrayList<Object> parameters1 = new ArrayList<>();
			  parameters1.add(stmnt.toString().substring(stmnt.toString().lastIndexOf(":") + 1));
			  parameters1.add(String.valueOf(l));				
				insertUpdateDuablDB("insert into frm_query_log values (default,?,?,sysdate())", parameters1,con);
			}
			
			
			try (ResultSet rs = stmnt.executeQuery()) {
				while (rs.next()) {
					retLst.add(rs.getString(1));
				}
			}
			return retLst;
		} catch (Exception e) {
			throw e;
		} finally {
			if (stmnt != null)
				stmnt.close();
		}
	}

	public String convertToIndianCurrency(String num) {
		BigDecimal bd = new BigDecimal(num);
		long number;
		long no = bd.longValue();
		int decimal = (int) (bd.remainder(BigDecimal.ONE).doubleValue() * 100);
		int digits_length = String.valueOf(no).length();
		int i = 0;
		ArrayList<String> str = new ArrayList<>();
		HashMap<Integer, String> words = new HashMap<>();
		words.put(0, "");
		words.put(1, "One");
		words.put(2, "Two");
		words.put(3, "Three");
		words.put(4, "Four");
		words.put(5, "Five");
		words.put(6, "Six");
		words.put(7, "Seven");
		words.put(8, "Eight");
		words.put(9, "Nine");
		words.put(10, "Ten");
		words.put(11, "Eleven");
		words.put(12, "Twelve");
		words.put(13, "Thirteen");
		words.put(14, "Fourteen");
		words.put(15, "Fifteen");
		words.put(16, "Sixteen");
		words.put(17, "Seventeen");
		words.put(18, "Eighteen");
		words.put(19, "Nineteen");
		words.put(20, "Twenty");
		words.put(30, "Thirty");
		words.put(40, "Forty");
		words.put(50, "Fifty");
		words.put(60, "Sixty");
		words.put(70, "Seventy");
		words.put(80, "Eighty");
		words.put(90, "Ninety");
		String digits[] = { "", "Hundred", "Thousand", "Lakh", "Crore" };
		while (i < digits_length) {
			int divider = (i == 2) ? 10 : 100;
			number = no % divider;
			no = no / divider;
			i += divider == 10 ? 1 : 2;
			if (number > 0) {
				int counter = str.size();
				String plural = (counter > 0 && number > 9) ? "s" : "";
				String tmp = (number < 21) ? words.get(Integer.valueOf((int) number)) + " " + digits[counter] + plural
						: words.get(Integer.valueOf((int) (number / 10) * 10)) + " "
						+ words.get(Integer.valueOf((int) (number % 10))) + " " + digits[counter] + plural;
				str.add(tmp);
			} else {
				str.add("");
			}
		}

		Collections.reverse(str);
		String Rupees = String.join(" ", str).trim();

		String paise = (decimal) > 0
				? " And Paise " + words.get(Integer.valueOf((int) (decimal - decimal % 10))) + " "
				+ words.get(Integer.valueOf((int) (decimal % 10)))
				: "";
				return "Rupees " + Rupees + paise + " Only";
	}

	public String getDateASYYYYMMDD(String dateAsDDMMYYYY) throws ParseException {
		Date d1 = new SimpleDateFormat("dd/MM/yyyy").parse(dateAsDDMMYYYY);
		return new SimpleDateFormat("yyyy-MM-dd").format(d1);
	}
	
	public String getDateASYYYYMMDDHHMM(String dateAsDDMMYYYY) throws ParseException {
		Date d1 = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(dateAsDDMMYYYY);
		return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(d1);
	}

	public String getFileNameFromPath(String wholePath) {
		return new File(wholePath).getName();
	}

	
	
	
	
	
	

	String username_constant = "username";
	String filename_constant = "FileName";
	
	
	
	
	
	
	public void setEnvVariables(String schemaName) {		
		try 
		{
			System.out.println("mysqlusername" + System.getenv("mysqlusername"));
			System.out.println("password" + System.getenv("password"));
			System.out.println("host" + System.getenv("host"));
			System.out.println("port" + System.getenv("port"));
			System.out.println("mySqlPath" + System.getenv("mySqlPath"));		
			if (System.getenv("mysqlusername") == null|| System.getenv("password") == null|| System.getenv("port") == null
					|| System.getenv("mySqlPath") == null|| System.getenv("host") == null)
			{
				logger.error("Hey did you forget to set env variables ? ");
				logger.error("If you set these recently try restarting your IDE");
				logger.error("Application will now exit");
				System.exit(0);
				return;
			}			
			host=System.getenv("host");
			url = "jdbc:mysql://"+host;
			username = System.getenv("mysqlusername");
			password = System.getenv("password");
			port = System.getenv("port");			
			mySqlPath=System.getenv("mySqlPath");
			
			
			isAuditEnabled = Boolean.valueOf(System.getenv(schemaName+"isAuditEnabled"));
			queryLogEnabled = Boolean.valueOf(System.getenv(schemaName+"queryLogEnabled"));
			copyAttachmentsToBuffer = Boolean.valueOf(System.getenv(schemaName+"copyAttachmentsToBuffer"));
			isSendEmail = Boolean.valueOf(System.getenv(schemaName+"sendEmail"));
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void setElementsMaster()
	{
		InputStream in = ExecuteSqlFile.class.getResourceAsStream("Elements.yaml");
		Yaml yaml = new Yaml(); Map<String, Object> data = yaml.load(in);
		List<HashMap<String, String>> lstElements= (List<HashMap<String, String>>)data.get("elements");
		
		for(HashMap<String, String> hm:lstElements)
		{
			Element e=new Element();
			e.setElementId(Long.valueOf(String.valueOf(hm.get("elementId"))));
			e.setElementName(hm.get("elementName"));
			e.setParentElementId(Long.valueOf(String.valueOf(hm.get("parentElementId"))));
			e.setElementUrl(hm.get("elementUrl"));
			e.setOrderNo(Integer.parseInt(String.valueOf(hm.get("orderNo"))));
			elements.add(e);
		}
	}
	
	public void setRoles(Class[] scanClasses) 
	{		
		try 
		{
			InputStream in = ExecuteSqlFile.class.getResourceAsStream("Roles.yaml");
			Yaml yaml = new Yaml(); Map<String, Object> data = yaml.load(in);
			  List<LinkedHashMap<String, Object>> lst= (List<LinkedHashMap<String,Object>>)data.get("roles");
			  for(LinkedHashMap role: lst)
			  {
				  
					
							
							
							Role r = new Role(Long.parseLong(String.valueOf(role.get("roleId"))),String.valueOf(role.get("roleName")));
							
							String actions= (String)role.get("actions");
							r.setActions(actions.split(","));
							
							if(role.get("dashboard")!=null)
							{
							String dashboard= (String)role.get("dashboard");
							r.setDashboardList(dashboard.split(","));
							}
							
							
							String elementscsv= (String)role.get("elements");
							
							
							int[] elements=Arrays.stream(elementscsv.split(",")).mapToInt(Integer::parseInt).toArray();
							Integer[] elementsI = Arrays.stream( elements ).boxed().toArray( Integer[]::new );
							
							r.setElements(elementsI);
							
							roles.put(r.getRoleId(), r);
						
				
			  }
			  
			  // set apptypes here
			
			actions=getActionServiceList(scanClasses);
						

			
			
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public void setByPassedActions() 
	{		
		try 
		{
			InputStream in = ExecuteSqlFile.class.getResourceAsStream("Application.yaml");
			Yaml yaml = new Yaml(); 
			Map<String, Object> data = yaml.load(in);
			String[] bypassedActions=((String) data.get("bypassedActions")).split(",");
			lstbypassedActions= Arrays.asList(bypassedActions);
			
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public void setDashboardLinks() 
	{		
		try 
		{
			
 			InputStream in = ExecuteSqlFile.class.getResourceAsStream("DashboardLinkMapping.yaml");
 			if(in==null)
 			{return;}
			Yaml yaml = new Yaml(); 
			dashboardLinks= yaml.load(in);
			
			
			
			
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	
	public void setSchemaName() 
	{		
		try 
		{
			InputStream in = ExecuteSqlFile.class.getResourceAsStream("Application.yaml");
			Yaml yaml = new Yaml(); 
			Map<String, Object> data = yaml.load(in);
			schemaName= (String) data.get("schemaName");
			projectName= (String) data.get("projectName");
			
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	
	public void setApplicationTypes() 
	{		
		try 
		{
			InputStream in = ExecuteSqlFile.class.getResourceAsStream("Application.yaml");
			Yaml yaml = new Yaml(); 
			Map<String, Object> data = yaml.load(in);				
				List<LinkedHashMap<String,Object>> lst= (List<LinkedHashMap<String,Object>>)data.get("appTypes");
				 HashMap<String, List<Role>> appType=new HashMap<>();
			for(LinkedHashMap<String, Object> lm: lst)
			{
				String[] roles=String.valueOf(lm.get("roles")).split(",");
				List<Long> rolesInt=Stream.of(roles).map(Long::valueOf).collect(Collectors.toList());
				LinkedHashMap<Long, Role> lstRoles=getRolesById(rolesInt);
				apptypes.put(lm.get("appType").toString(),lstRoles);
			}
			threadSleep=(Integer) data.get("thread_sleep");
			schemaName= (String) data.get("schemaName");
			
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	  
	
	private LinkedHashMap<Long, Role> getRolesById(List<Long> roles2) 
	{
		LinkedHashMap<Long, Role> r=new LinkedHashMap<>();
		for(Long roleId:roles2)
		{
			for(Entry<Long, Role> tmpRole: roles.entrySet())
			{
				if(tmpRole.getKey()==roleId)
				{
					//r.add(tmpRole.getValue());
					r.put(roleId, tmpRole.getValue());
				}
			}
		}
		return r;
	}



	public HashMap<String, FrmActionService> getActionServiceList(Class[] classes) 
	{
		
		HashMap<String, FrmActionService> reqActions = new HashMap<>();
		for(Class c:classes)
		{		
			for(Method m:getAccessibleMethods(c))
			{
				reqActions.put(m.getName(),new FrmActionService(m.getName(), c.getName(),lstbypassedActions.contains(m.getName())));
				//System.out.println( c.getName()+ ""+ m.getName());
			}
		}
		return reqActions;
	}


	
	public Connection getConnectionJDBC() throws SQLException, ClassNotFoundException
	{				
				
		Class.forName("com.mysql.jdbc.Driver");
		return DriverManager.getConnection (url+":"+port+"/"+schemaName+"?user="+username+"&password="+password+"&characterEncoding=utf8&sessionVariables=sql_mode='STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION,PIPES_AS_CONCAT'");
	}

	




	public void writeErrorToDB(Exception e) 
	{
		try {
			e.printStackTrace();
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String sStackTrace = sw.toString(); // stack trace as a string
			Connection con = getConnectionJDBC();
			ArrayList<Object> parameters = new ArrayList<>();
			parameters.add(sStackTrace);
			insertUpdateDuablDB("insert into frm_error_log values (default,?,sysdate())", parameters, con);
		} catch (Exception e1) {

			e.printStackTrace();
			e1.printStackTrace();
		}

		return;
	}

	


	public long insertUpdateDuablDB(final String sql,final ArrayList<Object> parameters,final Connection con) throws SQLException
	{
		long returnValue=0;
		final HashMap<String, Object> hm=insertUpdate(sql, parameters, con);
		returnValue=hm.get("reqVAlue")==null?0:(long)hm.get("reqVAlue");
		
			return returnValue;
		
	}
	
	
	public long insertUpdateCustomParameterized(String sql, HashMap<String, Object> hm, Connection conWithF) throws SQLException 
	{
		ArrayList<Object> parameters=new ArrayList<>();
		
		
		String[] columnNames=sql.split(",");
		for(String columnName:columnNames)
		{
			
			
			if(columnName.startsWith(":") && columnName.endsWith(")"))
			{
				sql=sql.replace(columnName, "?)");
				columnName=columnName.replaceAll(":", "");
				parameters.add(hm.get(columnName));
				continue;
			}
			
			if(columnName.startsWith(":"))
			{
				sql=sql.replace(columnName, "?");
				columnName=columnName.replaceAll(":", "");
				parameters.add(hm.get(columnName));
			}
		}
				
		
		return insertUpdateDuablDB(sql,parameters,conWithF);
	}


	
	public HashMap<String, Object> insertUpdate(String sql, ArrayList<Object> parameters, Connection conWithF) throws SQLException 
	{
		long reqVAlue = 0;
		PreparedStatement preparedStatement = null;
		HashMap<String, Object> hm=new HashMap<>();
		try 
		{
			preparedStatement = conWithF.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			int i = 1;
			for (Object o : parameters) 
			{
				preparedStatement.setObject(i++, o);
			}
			hm.put("preparedStatement",preparedStatement.toString());
			logger.info("\n"+preparedStatement);
			preparedStatement.executeUpdate();

			try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) 
			{
				if (generatedKeys.next()) {
					
					reqVAlue = generatedKeys.getLong(1);
					hm.put("reqVAlue", reqVAlue);
				} else 
				{
					// do nothing as ID mignt not have generated
				}
			}
		} catch (SQLException e) 
		{
			// write to error log
			writeErrorToDB(e);
			throw e;
		} finally 
		{
			if(preparedStatement!=null)
				preparedStatement.close();
		}
		return hm;
	}

	public String convertSecondsToWords(long seconds) {
		long sec = seconds % 60;
		long minutes = seconds % 3600 / 60;
		long hours = seconds % 86400 / 3600;

		String outputString = "";
		if (hours != 0) {
			outputString += hours + " Hour ";
		}
		if (minutes != 0) {
			outputString += minutes + " Minute and ";
		}
		if (sec != 0) {
			outputString += sec + " Seconds Ago";
		}
		return outputString;
	}
	
	

	public void copyImagesFromDBToBufferFolder(String DestinationPath, Connection con)
			throws ClassNotFoundException, SQLException, IOException {
		
		
		
		
		logger.info("Destination path is"+DestinationPath);
		
		// set global session
		
		
		while (true) {
			File f1 = new File(DestinationPath);
			List<String> listFromServerFolder = Arrays.asList(f1.list());
			ArrayList<Object> parameters = new ArrayList<>();
			List<String> lstFromDb = getListOfString(parameters,
					"select concat(attachment_id,file_name) as file_name from tbl_attachment_mst where activate_flag=1",
					con);
			HashSet<String> setFromServerFolder = new HashSet<String>(listFromServerFolder);
			HashSet<String> setFromDb = new HashSet<String>(lstFromDb);
			setFromDb.removeAll(setFromServerFolder);
			ArrayList<String> namesList = new ArrayList<>(setFromDb);

			logger.info("Pending Files to copy"+namesList);

			if (actualCopy(DestinationPath, con, namesList)) {
				break;
			}

		}
	}
	public void copyImagesFromDBToBufferFolder(ServletContext sc, Connection con)
			throws ClassNotFoundException, SQLException, IOException {
		String DestinationPath = sc.getRealPath("BufferedImagesFolder") + "/";
		logger.info("Destination path is"+DestinationPath);
		
		// set global session
		if(!copyAttachmentsToBuffer)
			return;
		
		
		while (true) {
			File f1 = new File(DestinationPath);
			List<String> listFromServerFolder = Arrays.asList(f1.list());
			ArrayList<Object> parameters = new ArrayList<>();
			List<String> lstFromDb = getListOfString(parameters,
					"select concat(attachment_id,file_name) as file_name from 	tbl_attachment_mst where activate_flag=1 ",
					con);
			HashSet<String> setFromServerFolder = new HashSet<String>(listFromServerFolder);
			HashSet<String> setFromDb = new HashSet<String>(lstFromDb);
			setFromDb.removeAll(setFromServerFolder);
			ArrayList<String> namesList = new ArrayList<>(setFromDb);

			logger.info("Pending Files to copy"+namesList);

			if (actualCopy(DestinationPath, con, namesList)) {
				break;
			}

		}
	}

	public boolean actualCopy(String DestinationPath, Connection con, List<String> setOfPendingFiles)
			throws SQLException, IOException {
		PreparedStatement ps = null;
		FileOutputStream fos = null;
		boolean returnValue=false;
		try {
			String reqString = "";
			int m = 1;
			if (setOfPendingFiles.size() == 0) {
				return true;
			}

			StringBuilder builder = new StringBuilder();

			for (int i = 0; i < setOfPendingFiles.size(); i++) {
				builder.append("?,");
			}

			ps = con.prepareStatement(
					"select concat(attachment_id,file_name) as file_name,attachment_asblob from 	tbl_attachment_mst where activate_flag=1 and concat(attachment_id,file_name) in ("
							+ builder.deleteCharAt(builder.length() - 1).toString() + ") limit 100");
			int k = 1;
			for (int i = 0; i < setOfPendingFiles.size(); i++) {
				ps.setString(k++, setOfPendingFiles.get(i));
			}

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					m++;
					String PathToNewFile = DestinationPath + rs.getString("file_name");
					logger.info(PathToNewFile);
					File f = new File(PathToNewFile);
					if (!f.exists()) {
						fos = new FileOutputStream(f);
						byte b[];
						Blob blob;
						blob = rs.getBlob("attachment_asblob");
						b = blob.getBytes(1, (int) blob.length());
						fos.write(b);
						fos.close();
					}
				}
				returnValue=(m == 1);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return returnValue;

		} finally {
			if(ps!=null)
				ps.close();
			if(fos!=null)
				fos.close();
		}

	}

	public String getDateTime(Connection con) throws SQLException {
		logger.info("will fire db query 1");
		return getMap(new ArrayList<>(), "select sysdate() as dt1 from dual", con).get("dt1");

	}

	public String getDateTimeWithSeconds(Connection con) throws SQLException {
		return getMap(new ArrayList<>(), "select date_format(sysdate(),'%d%m%Y%H%i%s') as dt1 from dual", con)
				.get("dt1").toString();
	}

	public String getDateTimeWithoutSeconds(Connection con) throws SQLException {
		return getMap(new ArrayList<>(), "select date_format(sysdate(),'%d/%m/%Y %H:%i') as dt1 from dual", con)
				.get("dt1").toString();
	}

	public String getDateFromDB(Connection con) throws SQLException {
		return getMap(new ArrayList<>(), "select date_format(sysdate(),'%d/%m/%Y') as dt1 from dual", con).get("dt1")
				.toString();
	}
	
	   public String getYesterdaysDateFromDB(Connection con) throws SQLException {
	        return getMap(new ArrayList<>(), "select date_format(subdate(current_date, 1),'%d/%m/%Y') as dt1 from dual", con).get("dt1")
	                .toString();
	    }
	   
	   public String getTommorowsDateFromDB(Connection con) throws SQLException {
           return getMap(new ArrayList<>(), "select date_format(adddate(current_date, 1),'%d/%m/%Y') as dt1 from dual", con).get("dt1")
                   .toString();
       }
	
	public String getDateFromDBMinusOneMonth(Connection con) throws SQLException {
		return getMap(new ArrayList<>(), "select date_format(DATE_SUB(sysdate(),INTERVAL 1 MONTH),'%d/%m/%Y') as dt1 from dual", con).get("dt1")
				.toString();
	}
	
	public String getDateFromDBMinusOneDay(String date,Connection con) throws SQLException {
		return getMap(new ArrayList<>(), "select date_format(DATE_SUB('"+date+"',INTERVAL 1 DAY),'%d/%m/%Y') as dt1 from dual", con).get("dt1")
				.toString();
	}
	

	


	
	public List<Element> getElementsNewLogic(List<String> roleIds, List<Element> elementsMaster,HashMap<Long, Role> rolesMaster) throws ClassNotFoundException, SQLException 
	{
		List<Element> finalListRequired=new ArrayList<>();
		List<Element> parentElements=new ArrayList<>();
		List<Element> childElements=new ArrayList<>();
		for (String s : roleIds) 
		{
			
			Role r=rolesMaster.get(Long.parseLong(s));
			
			 parentElements.addAll(getParentElements(r.getElements(),elementsMaster));
			 childElements.addAll(getChildElements(r.getElements(),elementsMaster));
			
		}	
		
	
		
		
		Set<Element> set = new HashSet<>(parentElements);
		parentElements.clear();
		parentElements.addAll(set);
		
		Set<Element> set1 = new HashSet<>(childElements);
		childElements.clear();
		childElements.addAll(set1);
		
		Collections.sort(parentElements,new SoryByOrderNo());
		Collections.sort(childElements,new SoryByOrderNo());
		
		
		for(Element p:parentElements)
		{
			List<Element> myChildElements=new ArrayList<>();
			for(Element c:childElements)
			{
				if(c.getParentElementId()==p.getElementId())
				{
					myChildElements.add(c);
				}
			}
			p.setChildElements(myChildElements);
			finalListRequired.add(p);			
		}
		
		
		
		
		return finalListRequired;
		
	}
	
	public Set<String> getDashboardForThisUser(List<String> roleIds,HashMap<Long, Role> rolesMaster) throws ClassNotFoundException, SQLException 
	{
		Set<String> finalListRequired=new HashSet<>();	
		for (String s : roleIds) 
		{
			
			Role r=rolesMaster.get(Long.parseLong(s));
			finalListRequired.addAll(Arrays.asList(r.getDashboardList()));
		}	
		
		return finalListRequired;
		
	}
	
	
	
	public List<Element> getParentElements(Integer[] elementsIds,List<Element> elements)
	{
		List<Element> elementsWithParent=new ArrayList<>();
		for(Integer I:elementsIds)
		{
			for(Element e:elements)
			{
				if(e.getParentElementId()==-1 && e.getElementId()==I)
				{
					elementsWithParent.add(e);
				}
			}
		}
		return elementsWithParent;
	}
	
	public List<Element> getChildElements(Integer[] elementsIds,List<Element> elements)
	{
		List<Element> elementsWithParent=new ArrayList<>();
		for(Integer I:elementsIds)
		{
			for(Element e:elements)
			{
				if(e.getParentElementId()!=-1 && e.getElementId()==I)
				{
					elementsWithParent.add(e);
				}
			}
		}
		return elementsWithParent;
	}
	
	
	public Element getElementById(Integer id,List<Element> elementsMaster)
	{
		Element reqElement=null;
		for(Element e:elementsMaster)
		{
			if(e.getElementId()==id)
			{
				reqElement=e;
				break;
			}
		}
		return reqElement;
	}
	
	
	
	
	
	
	
	
	public HashSet<String> getActionsForthisUserDecoupled(long userId, Connection con,HashMap<Long, Role> roles) throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<Object>();
		parameters.add(userId);
		
		String query = "select role_id from acl_user_role_rlt rlt where user_id=? and activate_flag=1 ";
		HashSet<String> distinctActions=new HashSet<>();
		for(String roleName:getListOfString(parameters, query, con))
		{
			String[] roleNameActions=roles.get(Long.valueOf(roleName)).getActions();
			 List<String> lst=Arrays.asList(roleNameActions);
			 distinctActions.addAll(lst);
		}
		return distinctActions;

	}
	
	public List<String> getRoles(Long userId,Connection con) throws SQLException, ClassNotFoundException
	{
		ArrayList<Object> parameters=new ArrayList<>();
		parameters.add(userId);
		return getListOfString(parameters, "select role_name from acl_user_role_rlt userrole where userrole.user_id=? and \r\n"
				+ "userrole.activate_flag=1 ", con);	
	}
	public List<String> getRoleIds(Long userId,Connection con) throws SQLException, ClassNotFoundException
	{
		ArrayList<Object> parameters=new ArrayList<>();
		parameters.add(userId);
		return getListOfString(parameters, "select userrole.role_id from acl_user_role_rlt userrole where userrole.user_id=? and \r\n"
				+ "userrole.activate_flag=1 ", con);	
	}
	

	public final String webPortal = "WebPortal";
	static Logger logger = Logger.getLogger(CommonFunctions.class.getName());
	List<String> bypassedACtions=null;

	

	public void generateExcelFromList(String FileName, List<LinkedHashMap<String, Object>> listHm) throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("The Sheet 1");
		int rowCount = 0;
		int columnCount = 0;
		Row row = sheet.createRow(rowCount++);
		for (Entry<String, Object> entry : listHm.get(0).entrySet()) {
			Cell cell = row.createCell(columnCount++);
			cell.setCellValue(entry.getKey());
		}
		for (LinkedHashMap<String, Object> temp : listHm) {
			columnCount = 0;
			row = sheet.createRow(rowCount++);
			for (Entry<String, Object> entry : listHm.get(0).entrySet()) {
				Cell cell = row.createCell(columnCount++);
				cell.setCellValue(String.valueOf(temp.get(entry.getKey())));
			}

		}
		try (FileOutputStream outputStream = new FileOutputStream(FileName)) {
			workbook.write(outputStream);
		}
	}

	public void generatePDFFromList(String FileName, List<LinkedHashMap<String, Object>> listHm, String[] colNames,BigDecimal total)
			throws IOException, DocumentException {

		Document document = new Document(PageSize.A4.rotate(), 20, 20, 20, 60);
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(FileName));

		CommonFunctions event = new CommonFunctions();
		writer.setPageEvent(event);
		document.open();
		PdfPTable table = new PdfPTable(1);
		PdfPCell cell;

		cell = new PdfPCell(new Phrase("Export Data", new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD)));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		table.addCell(cell);
		document.add(table);
		document.add(new Paragraph("\n\n"));

		table = new PdfPTable(colNames.length);

		for (String s : colNames) {
			cell = new PdfPCell(new Phrase(s, new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
			cell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			cell.setVerticalAlignment(com.itextpdf.text.Element.ALIGN_MIDDLE);
			table.addCell(cell);
		}

		for (LinkedHashMap<String, Object> tempHm : listHm) {
			for (String s : colNames) {
				String reqVal = tempHm.get(s) == null ? "" : tempHm.get(s).toString();
				cell = new PdfPCell(new Phrase(reqVal, new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
				cell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
				cell.setVerticalAlignment(com.itextpdf.text.Element.ALIGN_MIDDLE);
				table.addCell(cell);
			}
		}
		
		if(!total.toString().equals("0"))
		{
		cell = new PdfPCell(new Phrase("Total Amount: "+ String.valueOf(total), new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
		cell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
		cell.setVerticalAlignment(com.itextpdf.text.Element.ALIGN_MIDDLE);
		cell.setColspan(colNames.length);
		cell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
		table.addCell(cell);
		}
		
		

		document.add(table);

		document.close();

	}

	public long uploadFileToDBDual(final String path,final Connection con, final String fileType, final long fileId)
			throws Exception 
	{
				
			final HashMap<String, Object> hm=uploadFileToDB(path, con, fileType, fileId);			
				return (long)hm.get("returnedPK");
			
	}


	public HashMap<String, Object> uploadFileToDB(String path, Connection con, String fileType, long fileId)
			throws SQLException, IOException {
		InputStream targetStream = null;
		PreparedStatement preparedStatement = null;
		HashMap<String, Object> hm=new HashMap<>();
		try {
			File f1 = new File(path);
			String fileName = f1.getName();
			targetStream = new FileInputStream(f1);
			String insertTableSQL = "insert into tbl_attachment_mst values (default,?,sysdate(),1,?,?,?)";
			preparedStatement = con.prepareStatement(insertTableSQL, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, fileName);
			preparedStatement.setLong(2, fileId);
			preparedStatement.setString(3, fileType);
			preparedStatement.setBlob(4, targetStream);
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();
			rs.next();
			targetStream.close();
			hm.put("preparedStatement", preparedStatement.toString());
			hm.put("returnedPK", rs.getLong(1));
			return hm;			
		} catch (Exception e) {
			throw e;
		} finally {
			if (targetStream != null)
				targetStream.close();
			if (preparedStatement != null)
				preparedStatement.close();
		}
	}
	
	
	public void generateFileFromList(String FileName, List<LinkedHashMap<String, Object>> listHm, String[] colNames)
			throws IOException, DocumentException {
		FileWriter fw = new FileWriter(FileName);
		BufferedWriter br = new BufferedWriter(fw);
		try {

			String dataWithNewLine = System.getProperty("line.separator");
			StringBuffer temp = new StringBuffer();

			for (String s : colNames) {
				temp.append(s + ",");
			}
			br.write(temp.substring(0, temp.length() - 1));

			br.write(dataWithNewLine);

			for (LinkedHashMap<String, Object> tempHm : listHm) {
				temp = new StringBuffer();
				for (String s : colNames) {
					String reqVal = tempHm.get(s) == null ? "" : tempHm.get(s).toString();
					temp.append(reqVal + ",");
				}
				br.write(temp.substring(0, temp.length() - 1));
				br.write(dataWithNewLine);
			}

		} catch (IOException e) {
			logger.error("File Read Error ");
			throw e;
		} finally {
			br.close();
			fw.close();
		}

	}

	public HashMap<String, Object> getCommonFileGenerator(String[] colNames, List<LinkedHashMap<String, Object>> lst,
			String exportFlag, String DestinationPath, String userId, String documentName)
					throws IOException, DocumentException {

		HashMap<String, Object> outputMap = new HashMap<>();
		List<LinkedHashMap<String, Object>> requiredList = new ArrayList<>();

		for (HashMap<String, Object> tempHm : lst) {
			LinkedHashMap<String, Object> tempLink = new LinkedHashMap<>();
			for (String s : colNames) {
				tempLink.put(s, tempHm.get(s));
			}
			requiredList.add(tempLink);
		}

		if (exportFlag.equals("E")) {
			generateExcelFromList(DestinationPath + userId + documentName + ".xlsx", requiredList);
			outputMap.put(filename_constant, userId + documentName + ".xlsx");
		} else if (exportFlag.equals("P")) {
			generatePDFFromList (DestinationPath + userId + documentName + ".pdf", requiredList, colNames,new BigDecimal(0L));
			outputMap.put(filename_constant, userId + documentName + ".pdf");
		} else if (exportFlag.equals("T")) {
			generateFileFromList(DestinationPath + userId + documentName + ".txt", requiredList, colNames);
			outputMap.put(filename_constant, userId + documentName + ".txt");

		}

		return outputMap;
	}
	
	
	
	public HashMap<String, Object> getCommonFileGeneratorWithTotal(String[] colNames, HashMap<String, Object> hm,
			String exportFlag, String DestinationPath, String userId, String documentName)
					throws IOException, DocumentException {

		HashMap<String, Object> outputMap = new HashMap<>();
		List<LinkedHashMap<String, Object>> requiredList = new ArrayList<>();

		for (HashMap<String, Object> tempHm : (List<LinkedHashMap<String, Object>>) hm.get("lst")) {
			LinkedHashMap<String, Object> tempLink = new LinkedHashMap<>();
			for (String s : colNames) {
				tempLink.put(s, tempHm.get(s));
			}
			requiredList.add(tempLink);
		}

		if (exportFlag.equals("E")) {
			generateExcelFromList(DestinationPath + userId + documentName + ".xlsx", requiredList);
			outputMap.put(filename_constant, userId + documentName + ".xlsx");
		} else if (exportFlag.equals("P")) {
			generatePDFFromList(DestinationPath + userId + documentName + ".pdf", requiredList, colNames,new BigDecimal(hm.get("totalAmount").toString()));
			outputMap.put(filename_constant, userId + documentName + ".pdf");
		} else if (exportFlag.equals("T")) {
			generateFileFromList(DestinationPath + userId + documentName + ".txt", requiredList, colNames);
			outputMap.put(filename_constant, userId + documentName + ".txt");

		}

		return outputMap;
	}

	public void generateQRForThisString(String qrString, String DestinationPath, int height, int width,String type)
			throws IOException {
		StringBuffer sb = new StringBuffer();
		sb.append(qrString);
		Map<EncodeHintType, Object> qrParam = new HashMap<EncodeHintType, Object>();
		qrParam.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
		qrParam.put(EncodeHintType.CHARACTER_SET, "UTF-8");

		
		Image myImage =null; 
		
		
		if(type.equals("QR"))
		{
			BarcodeQRCode qrcode = new BarcodeQRCode(sb.toString(), height, width, qrParam);
			 myImage = qrcode.createAwtImage(Color.BLACK, Color.WHITE);
		}
		else
		{
		
		 Barcode128 code128 = new Barcode128();
         code128.setCode(sb.toString().trim());
         code128.setCodeType(Barcode128.CODE128);
         
         myImage= code128.createAwtImage(Color.BLACK, Color.WHITE);
         
         
         
		}
          
        
		
		
		
		
		BufferedImage bi = new BufferedImage(myImage.getWidth(null), myImage.getHeight(null),
				BufferedImage.TYPE_INT_RGB);
		bi.getGraphics().drawImage(myImage, 0, 0, null);
		ImageIO.write(bi, "JPG", new File(DestinationPath + qrString +type +".jpg"));
	}




	public String getSlaveStatus(Connection con) throws SQLException 
	{
		return getMap(new ArrayList<>(), "SHOW STATUS LIKE 'Slave_running'", con).get("Value");
	}

	public String getDateAsddmmyyyy(String dateAsDDMMYYYY) throws ParseException 
	{
			Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse(dateAsDDMMYYYY);
			return new SimpleDateFormat("dd/MM/yyyy").format(d1);
	}
	
	public List<String> getMemoryStats() throws IOException, InterruptedException
	{
	    List<String> lst=new ArrayList<>();
	    // added so that threads do not overlap on each other
	    
		if(!System.getProperty("os.name").toString().contains("Windows")) 
		{
			String[] command = { "/bin/bash", "-c", "ps aux  | awk '{print $6/1024  $11}'  | sort -n"};
			
			
			String str;
		    Process exec = Runtime.getRuntime().exec(command);
		    if (exec.waitFor() == 0) {
		      InputStream inputStream = exec.getInputStream();
		      byte[] buffer = new byte[inputStream.available()];
		      inputStream.read(buffer);
		       str = new String(buffer);
		      
		    } else {
		      InputStream errorStream = exec.getErrorStream();
		      byte[] buffer = new byte[errorStream.available()];
		      errorStream.read(buffer);
		       str = new String(buffer);
		      
		    } 
		    List<String> arr=(Arrays.asList(str.split("\n")));
		    Collections.reverse(arr);
		    
		    if(arr.size()>5)
		    {
		    	arr=arr.subList(0, 5);
		    }
		    for(String s:arr)
		    {
		    	lst.add(s);
		    }
		}
		return lst;
	}
	
	
	public String getActiveConnections(Connection con) throws SQLException
	{
		ArrayList<Object> parameters=new ArrayList<>();
		return getMap(parameters, "show status where `variable_name` = 'Threads_connected'", con).get("Value").toString();
	}

	
	public String getSHA256String(String input)
	{
		try {
			  
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } 
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
		
	}
	
	public String getRandomNumberByLength(int length) 
	{
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < length) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
	
	public Long getSequenceNumber(Connection con,boolean updateflag,String tableName,String appId) throws Exception
	 {		 
		 ArrayList<Object> parameters = new ArrayList<>();
			String query="select current_seq_no seqNo from seq_master where sequence_name='"+tableName+"' and app_id="+appId+" ";
			String returnString=getMap(parameters, query, con).get("seqNo");
			Long sequence =0L;
			if(returnString==null)
			{
				query = "insert into seq_master values (default,?,1,?)";
				parameters = new ArrayList<>();
				parameters.add(tableName);
				parameters.add(appId);			
				insertUpdateDuablDB(query, parameters, con);
				returnString="1";
			}
			sequence=Long.valueOf(returnString);
			
			parameters = new ArrayList<>();
			query= "UPDATE seq_master  SET current_seq_no=current_seq_no+1 where sequence_name= '"+tableName+"' and app_id="+appId+"";
			if(updateflag)
			{
				insertUpdateDuablDB(query, parameters, con);
			}
			
			
		
			return sequence;		 
	 }
	
	public static String getDump(String schemaname,String port) throws Exception
    {
		String commandToExecute=CommonFunctions.mySqlPath+" -P "+port+" -h "+CommonFunctions.host+" -u"+CommonFunctions.username +" -p"+CommonFunctions.password+" --no-data --databases "+schemaname+" > mybackup.sql";
		System.out.println(commandToExecute);
    	Process exec = Runtime.getRuntime().exec(new String[]{"cmd.exe","/c",commandToExecute});

    	//Process exec = Runtime.getRuntime().exec(new String[]{"bash","-c","/usr/local/mysql/bin/mysqldump -u"+CommonFunctions.username +" -p"+CommonFunctions.password+" "+schemaname+" > mybackup.sql"});    	
    	if(exec.waitFor()==0)
    	{
    	    InputStream inputStream = exec.getInputStream();
    	    byte[] buffer = new byte[inputStream.available()];
    	    inputStream.read(buffer);
    	    String str = new String(buffer);
    	    System.out.println(str);
    	}
    	else
    	{
    	    // abnormally terminated, there was some problem
    	                //a way to read the error during the execution of the command
    	    InputStream errorStream = exec.getErrorStream();
    	    byte[] buffer = new byte[errorStream.available()];
    	    errorStream.read(buffer);

    	    String str = new String(buffer);
    	    System.out.println(str);

    	}
    	
        
    	
    	return "mybackup.sql";
    }
	
	
	
	public void doDump()
	{
		try {
			
			System.out.println("Starting Dump "+new Date());
			setEnvVariables(schemaName);
			System.out.println("Set credentials done"+new Date());
			getDump(CommonFunctions.schemaName,CommonFunctions.port);
			System.out.println("Dump Done Succesfully at ");
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
	}
	
	
	public String getCurrentMonth(Connection con) throws SQLException {
		return getMap(new ArrayList<>(), "select SUBSTR(MONTHNAME(sysdate()),1,3) as dt1 from dual", con).get("dt1");
	}
	
	public String getCurrentYear(Connection con) throws SQLException {
		return getMap(new ArrayList<>(), "select (YEAR (sysdate())) as dt1 from dual", con).get("dt1");
	}
	
	public Long getSequenceNumberWithoutAppid(Connection con,boolean updateflag,String tableName) throws Exception
	 {		 
		 ArrayList<Object> parameters = new ArrayList<>();
			String query="select current_seq_no seqNo from seq_master where sequence_name='"+tableName+"' ";
			String returnString=getMap(parameters, query, con).get("seqNo");
			Long sequence =0L;
			if(returnString==null)
			{
				query = "insert into seq_master values (default,?,1)";
				parameters = new ArrayList<>();
				parameters.add(tableName);							
				insertUpdateDuablDB(query, parameters, con);
				returnString="1";
			}
			sequence=Long.valueOf(returnString);
			
			parameters = new ArrayList<>();
			query= "UPDATE seq_master  SET current_seq_no=current_seq_no+1 where sequence_name= '"+tableName+"' ";
			if(updateflag)
			{
				insertUpdateDuablDB(query, parameters, con);
			}
			
			
		
			return sequence;		 
	 }
	
	
	public static void ExecuteDump() throws SQLException, FileNotFoundException
	{
		  CommonFunctions cf = new CommonFunctions();
		  cf.setEnvVariables(schemaName);
		
	      DriverManager.registerDriver(new com.mysql.jdbc.Driver());
	      String mysqlUrl = CommonFunctions.url+":"+CommonFunctions.port;
	      if(!mysqlUrl.contains("localhost"))
	      {
	    	  System.out.println("seems you are not running for localhost");
	    	  System.exit(0);
	      }
	      Connection con = DriverManager.getConnection(mysqlUrl, CommonFunctions.username, CommonFunctions.password);
	      
	      ScriptRunner sr = new ScriptRunner(con);
	      Reader reader = new BufferedReader(new FileReader("mybackup.sql"));
	      sr.setLogWriter(null);
	      sr.runScript(reader);
	      
	}
	
	
	
	public String addDaysTodate(String inputDate,String noOfDays) throws ParseException
	{
		

		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Date date = format.parse(inputDate);
		
		Calendar c = Calendar.getInstance();
		c.setTime(date); 
		c.add(Calendar.DATE, Integer.valueOf(noOfDays)); 	
		return format.format(c.getTime());
	}
	
	public static Method[] getAccessibleMethods(Class clazz) {
	    List<Method> result = new ArrayList<Method>();
	    while (clazz != null) {
	        for (Method method : clazz.getDeclaredMethods()) {
	            int modifiers = method.getModifiers();
	            if (Modifier.isPublic(modifiers) || Modifier.isProtected(modifiers)) {
	                result.add(method);
	            }
	        }
	        clazz = clazz.getSuperclass();
	    }
	    return result.toArray(new Method[result.size()]);
	}



	public void initializeApplication(Class[] scanClasses) {
		setSchemaName();
		setEnvVariables(schemaName);
		setByPassedActions();
		setRoles(scanClasses);
		setApplicationTypes();
		
		setElementsMaster();
		setDashboardLinks();
		
		
		
		
		
    	
    	// copy images from db to buffer
	}
	
	public void initializeApplication() {
		
		setSchemaName();
    	setEnvVariables(schemaName);
    	// copy images from db to buffer
	}


	public LinkedHashMap<Long, Role> getRoleMasterForThisAppType(String app_type) 
	{
		
		return apptypes.get(app_type);

	}
	
	
	
	
	
	
	
	
	

    
	
	

}
