package com.crystal.framework;

import java.util.Comparator;

import com.crystalcommonfunctions.Element;

class SoryByOrderNo implements Comparator<Element> 
{
	 
    // Method
    // Sorting in ascending order of roll number
    public int compare(Element a, Element b)
    {
 
        return a.getOrderNo() - b.getOrderNo();
    }
}