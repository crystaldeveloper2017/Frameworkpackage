package com.crystal.framework.Frameworkpackage;

import java.io.InputStream;

import com.crystal.Login.LoginServiceImpl;

import com.crystal.framework.Frameworkpackage.CommonFunctions;

public class DumpCollection {

	public static void main(String[] args) {
		CommonFunctions cf=new CommonFunctions();
    	cf.initializeApplication();
		cf.doDump();
	}
	

}
