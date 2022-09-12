package com.crystal.framework.Frameworkpackage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.yaml.snakeyaml.Yaml;

import com.crystal.basecontroller.BaseController;

import com.crystal.customizedpos.Configuration.Config;
import com.crystal.customizedpos.Configuration.ExecuteSqlFile;
import com.crystal.framework.Frameworkpackage.Element;
import com.crystal.framework.Frameworkpackage.Role;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class someMainMethodClass {

	public static void main(String[] args) throws StreamWriteException, DatabindException, IOException {
		
		/*
		 * BaseController bc=new BaseController();
		 * 
		 * 
		 * 
		 * 
		 * ObjectMapper om = new ObjectMapper(new YAMLFactory());
		 * 
		 * List<Role> lst=new ArrayList<>(); for (Map.Entry<String, Role> entry :
		 * bc.getRolesWithAction().entrySet()) { lst.add(entry.getValue()); }
		 * //om.writeValue(new File("ERAMapping.yaml"), lst); // get all actions
		 * //om.writeValue(new File("ERAMapping.yaml"), bc.getElementsMaster()); // gets
		 * all elements
		 */
		// continue from here
		
		  InputStream in = ExecuteSqlFile.class.getResourceAsStream("Roles.yaml");
		  Yaml yaml = new Yaml(); Map<String, Object> data = yaml.load(in);
		  List<LinkedHashMap<String, Object>> lm= (List<LinkedHashMap<String, Object>>)data.get("appTypes");
		  
		  
		  
		  for(LinkedHashMap<String, Object> tempLm:lm)
		  {
			  List<LinkedHashMap<String, Object>> lmRoles=(List<LinkedHashMap<String, Object>>) tempLm.get("roles");
			  for(LinkedHashMap<String, Object> lmRole : lmRoles)
			  {
				  
				  String reqactioncsv="";
				   for(Integer action: (List<Integer>) lmRole.get("elements"))
				   {
					   reqactioncsv+=","+action;
				   }
				   System.out.println(lmRole.get("roleName")+" "+reqactioncsv); 
				  System.out.println("-----------------------------------------------");
			  }
		  }
		  
		  List<Element> lstRoles=new ArrayList<>();
			
		 
		
		
		
		
		
		


		
	}

}
