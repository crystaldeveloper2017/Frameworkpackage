package com.crystal.framework.Frameworkpackage;

import java.util.List;

public class Role 
{
	private Long roleId;
	
	private String roleName;	
	private String[] actions;	
	private Integer[] elements;
	private String[] dashboard;	
	
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public Integer[] getElements() {
		return elements;
	}
	public void setElements(Integer[] elements) {
		this.elements = elements;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String[] getActions() {
		return actions;
	}
	public void setActions(String[] actions) {
		this.actions = actions;
	}
	
	
	public String[] getDashboardList() {
		return dashboard;
	}
	public void setDashboardList(String[] dashboard) {
		this.dashboard= dashboard;
	}
	
	public Role(String roleName, String[] actions,Integer[] elements) {
		super();
		this.roleName = roleName;
		this.actions = actions;
		this.elements = elements;
		
	}
	public Role(Long i, String string) {
		this.roleId=i;
		this.roleName=string;		
	}
	
	public Role(int i, String string) {
		this.roleId=Long.valueOf(i);
		this.roleName=string;		
	}
	
	

	
}
