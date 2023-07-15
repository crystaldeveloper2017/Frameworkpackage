package com.crystal.customizedpos.Configuration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.ibatis.jdbc.ScriptRunner;

import com.crystal.Login.LoginDaoImpl;
import com.crystal.Login.LoginServiceImpl;
import com.crystal.customizedpos.Configuration.ConfigurationDaoImpl;
import com.crystal.customizedpos.Configuration.ConfigurationServiceImpl;
import com.crystal.Frameworkpackage.CommonFunctions;

public class ExecuteSqlFile {


		  public static void main(String argv[]) throws SQLException {
			  Connection con=null;
		    try {
				  CommonFunctions cf = new CommonFunctions();
			    cf.initializeApplication(new Class[] {ConfigurationServiceImpl.class,LoginServiceImpl.class});
				
			      DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			      String mysqlUrl = CommonFunctions.url+":"+CommonFunctions.port;
			      if(!mysqlUrl.contains("localhost"))
			      {
			    	  System.out.println("seems you are not running for localhost");
			    	  System.exit(0);
			      }
			      con= DriverManager.getConnection(mysqlUrl, CommonFunctions.username, CommonFunctions.password);
			      
			      ScriptRunner sr = new ScriptRunner(con);
			      Reader reader = new BufferedReader(new FileReader("mybackup.sql"));
			      sr.runScript(reader);
			      
			      
			      ConfigurationDaoImpl lobjConfigDaoimpl=new ConfigurationDaoImpl();
					LoginDaoImpl loginDao= new LoginDaoImpl();
					
					
					HashMap<String, Object> hm=new HashMap<>();

					hm.put("sbuName", "Test SBU");
					hm.put("drpheadid", "0");
					hm.put("user_id", "1");
					hm.put("app_id", "1");
					
					
					

			      
					hm=new HashMap<>();
					
					hm.put("username", "testadmin");
					hm.put("EmployeeName", "Test Admin");
					
					hm.put("MobileNumber", "7600787650");
					hm.put("email", "crystaldevelopers2017@gmail.com");
					hm.put("parent_user_id", "4");
					
					hm.put("txtfirm", "1");
					hm.put("AadharCardNo", "1234567890");
					
					hm.put("app_id", "1");
					
					
				
					long userId=lobjConfigDaoimpl.addEmployee(con, hm);
					
					loginDao.changePassword("testadmin", "1", con);
					
					
					
					lobjConfigDaoimpl.addUserRoleMapping(userId, 1L,  con) ;
					
					
					
			      con.commit();
			      
			      
			    }
			    catch (Exception err) {
			      err.printStackTrace();
			    }
		    finally{
			      con.close();
			    }
			}

}
