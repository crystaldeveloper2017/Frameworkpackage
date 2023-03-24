package com.crystal.Frameworkpackage;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import com.crystal.Login.LoginServiceImpl;

import com.crystal.Frameworkpackage.CommonFunctions;

public class DumpCollection {

	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
		CommonFunctions cf=new CommonFunctions();
    	cf.initializeApplication();
		cf.doDump();
	}
	

}
