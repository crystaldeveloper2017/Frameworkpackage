package com.crystal.framework.Frameworkpackage;

import java.util.List;

public class Role 
{
	private String roleName;
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
	private String[] actions;
	public Integer[] getElements() {
		return elements;
	}
	public void setElements(Integer[] elements) {
		this.elements = elements;
	}
	private Integer[] elements;
	public Role(String roleName, String[] actions,Integer[] elements) {
		super();
		this.roleName = roleName;
		this.actions = actions;
		this.elements = elements;
		
	}
	public Role(int i, String string) {
		this.roleName=string;		
	}
	
	

	
}
