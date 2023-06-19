package com.crystal.Frameworkpackage;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.crystal.Configuration.ConfigurationDaoImpl;
import com.crystal.Login.LoginDaoImpl;

public class CommonServiceImpl extends CommonFunctions {

    	public CustomResultObject reloadSession(HttpServletRequest request, Connection con) throws ClassNotFoundException, SQLException {
		LoginDaoImpl lObjLoginDao =new LoginDaoImpl();
		ConfigurationDaoImpl lObjConfigDao=new ConfigurationDaoImpl();
		CustomResultObject rs = new CustomResultObject();
		HashMap<String, Object> outputMap = new HashMap<>();

		
		String userId = ((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		long userIdL=Long.valueOf(userId);
		List<String> roles=getRoles( userIdL,con);
					List<String> roleIds = lObjLoginDao.getRoleIds( userIdL, con);

					request.getSession().setAttribute("listOfRoles", roles);
					
					request.getSession().setAttribute("adminFlag", roles.contains("Admin"));
					
					request.getSession().setAttribute("projectName", projectName);

					
					
					request.getSession().setAttribute("elements", getElementsNewLogic(roleIds,CommonFunctions.elements,CommonFunctions.roles));
					request.getSession().setAttribute("listOfDashboardForThisUser", getDashboardForThisUser(roleIds,CommonFunctions.roles));
					request.getSession().setAttribute("actions", getActionsForthisUserDecoupled( userIdL,con,CommonFunctions.roles));

		
					List<LinkedHashMap<String, Object>> lstUserRoleDetails = lObjConfigDao.getUserRoleDetails(Long.valueOf(userId), con);
					HashMap<String,Object> hm = new HashMap<>();
					hm.put("lstUserRoleDetails", lstUserRoleDetails);
					
					
					LinkedHashMap<Long, Role> roleMaster=apptypes.get("Master");
		
		
					List<LinkedHashMap<String, Object>> lstUserRoleDetailsNew = new ArrayList<>();
					for(LinkedHashMap<String, Object> lm:lstUserRoleDetails)
					{
						Role realRole=roleMaster.get(Long.valueOf(lm.get("role_id").toString()));
						lm.put("role_name", realRole.getRoleName());
						lstUserRoleDetailsNew.add(lm);
						
					}
					outputMap.put("userDetails", lObjConfigDao.getuserDetailsById(Long.valueOf(userId), con));
					outputMap.put("lstUserRoleDetails", lstUserRoleDetailsNew);
		
		rs.setViewName("../UserDetails.jsp");
		rs.setReturnObject(outputMap);	
		return rs;
	}
    
}
