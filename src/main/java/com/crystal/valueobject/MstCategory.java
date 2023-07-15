package com.crystal.valueobject;

import java.util.List;

public class MstCategory 
{

	private Integer categoryId;
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public short getActivateFlag() {
		return activateFlag;
	}
	public void setActivateFlag(short activateFlag) {
		this.activateFlag = activateFlag;
	}
	private String categoryName;
	private short activateFlag;
	
	private List<MstItems> Items;
	public List<MstItems> getItems() {
		return Items;
	}
	public void setItems(List<MstItems> items) {
		Items = items;
	}
	
	
	
	
}
