package com.crystal.framework.Frameworkpackage;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;



public class Element implements Serializable
{
		private long elementId;
		public long getElementId() {
			return elementId;
		}
		public void setElementId(long elementId) {
			this.elementId = elementId;
		}
		public String getElementName() {
			return elementName;
		}
		public void setElementName(String elementName) {
			this.elementName = elementName;
		}
		public long getParentElementId() {
			return parentElementId;
		}
		public void setParentElementId(long parentElementId) {
			this.parentElementId = parentElementId;
		}
		public String getElementUrl() {
			return elementUrl;
		}
		public void setElementUrl(String elementUrl) {
			this.elementUrl = elementUrl;
		}
		public boolean isActivateFlag() {
			return activateFlag;
		}
		public void setActivateFlag(boolean activateFlag) {
			this.activateFlag = activateFlag;
		}
		public int getOrderNo() {
			return orderNo;
		}
		public void setOrderNo(int orderNo) {
			this.orderNo = orderNo;
		}
		private String elementName;
		private long parentElementId;
		private String elementUrl;
		private boolean activateFlag;
		private int orderNo;
		
		public Element(long elementId, String elementName, long parentElementId, String elementUrl,
				boolean activateFlag, int orderNo, List<Element> childElements) {
			super();
			this.elementId = elementId;
			this.elementName = elementName;
			this.parentElementId = parentElementId;
			this.elementUrl = elementUrl;
			this.activateFlag = activateFlag;
			this.orderNo = orderNo;
			this.childElements = childElements;
		}
		public Element() 
		{
			
		}
		private List<Element> childElements;
		public List<Element> getChildElements() {
			return childElements;
		}
		public void setChildElements(List<Element> childElements) {
			this.childElements = childElements;
		}
		
		public String toString()
		{
			return this.elementId+"~"+this.elementName;			
		}
		@Override
		public int hashCode() {
			// TODO Auto-generated method stub
			return super.hashCode();
		}
		@Override
		public boolean equals(Object obj) {
			// TODO Auto-generated method stub
			return this.elementId==((Element)obj).getElementId();
			
		}
		
		
}


 