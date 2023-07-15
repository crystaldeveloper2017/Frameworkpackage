package com.crystal.valueobject;

public class MstItems 
{

	private Integer itemId;
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public short getAvailability() {
		return availability;
	}
	public void setAvailability(short availability) {
		this.availability = availability;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	private String itemName;	
	private short availability;
	private Double price;
	
	
}
