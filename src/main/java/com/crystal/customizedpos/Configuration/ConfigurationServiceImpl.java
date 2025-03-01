package com.crystal.customizedpos.Configuration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.crystal.CustomExceptions.CustomerMobileAlreadyExist;
import com.crystal.Login.LoginDaoImpl;
import com.crystal.basecontroller.BaseController;

import com.crystal.Frameworkpackage.*;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import pdfGeneration.EstimatePDFHelper;
import pdfGeneration.InvoiceHistoryPDFHelper;



public class ConfigurationServiceImpl  extends CommonFunctions 
{
	ConfigurationDaoImpl lObjConfigDao =new ConfigurationDaoImpl();
	String filename_constant = "FileName";

	public CustomResultObject addRemoveRole(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		long userId=Long.parseLong(request.getParameter("userId"));
		String action=request.getParameter("action");
		String[] listOfRoles= request.getParameter("listOFRoles").split("~");
		
		try
		{
			
			
			HashMap<Long, Role> roleMasterMap=cf.getRoleMasterForThisAppType("Master");
			
			if(action.equals("0"))
			{
				for(String s :listOfRoles)
				{
					lObjConfigDao.removeRoleFromUser(userId, Long.valueOf(s), con);	
				}
			}
			else
			{
				for(String s :listOfRoles)
				{
					if(!lObjConfigDao.checkIfRoleUserAlreadyExist(userId,Long.valueOf(s),con))
					{
						lObjConfigDao.addUserRoleMapping(userId, Long.valueOf(s), con);
					}
				}
			}
							
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
			rs.setHasError(true);
		}	
		rs.setAjaxData("Roles Updated Succesfully");
		return rs;
	}
	
	public CustomResultObject addRemoveClientServiceMapping(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		long clientId=Long.valueOf(request.getParameter("clientId"));
		String action=request.getParameter("action");
		
		String[] lsitOfServices= request.getParameter("lsitOfServices").split("~");
		HashMap<String, Object> outputMap=new HashMap<>();				
		String returnAjaxString;
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		
		try
		{	
			if(action.equals("0"))
			{
				for(String s :lsitOfServices)
				{
					lObjConfigDao.removeClientServiceMapping(clientId, Long.valueOf(s), con);	
				}
			}
			else
			{
				for(String s :lsitOfServices)
				{
					if(!lObjConfigDao.checkIfClientServiceAlreadyExist(clientId,Long.valueOf(s),con))
					{
						lObjConfigDao.addClientServiceMapping(clientId, Long.valueOf(s),userId, con);
					}
				}
			}
							
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
			rs.setHasError(true);
		}	
		rs.setAjaxData("Client Service Mapping Updated Succesfully");
		return rs;
	}
	
	public CustomResultObject getClientServiceMappingForThisClient(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		long firmId=request.getParameter("clientId").equals("")?-1:Integer.parseInt(request.getParameter("clientId"));		
		HashMap<String, Object> outputMap=new HashMap<>();				
		String returnAjaxString;
		try
		{			
			rs.setAjaxData(mapper.writeValueAsString(lObjConfigDao.getServiceMappingForThisClient(firmId, con)));				
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
			rs.setHasError(true);
		}		
		return rs;
	}
	
	
	
	public CustomResultObject getPendingAmountForClient(HttpServletRequest request,Connection con) throws ClassNotFoundException, SQLException
	{

		CustomResultObject rs=new CustomResultObject();
		long ClientId= Integer.parseInt(request.getParameter("clientId"));		
		HashMap<String, Object> outputMap=new HashMap<>();				
		String returnAjaxString;
		try
		{
			rs.setAjaxData(mapper.writeValueAsString(getPendingAmountForClientitem(request,con)));
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	
	}
	
	public CustomResultObject getPendingInvoicesForThisClient(HttpServletRequest request,Connection con) throws ClassNotFoundException, SQLException
	{

		CustomResultObject rs=new CustomResultObject();
		long ClientId= Integer.parseInt(request.getParameter("clientId"));		
		HashMap<String, Object> outputMap=new HashMap<>();				
		String returnAjaxString;
		try
		{
			rs.setAjaxData(mapper.writeValueAsString(getPendingInvoicesForThisClientitem(request,con)));
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	
	}
	
	public HashMap<String, Object> getPendingInvoicesForThisClientitem(HttpServletRequest request,Connection con) throws ClassNotFoundException, SQLException
	{
		CustomResultObject rs=new CustomResultObject();
		String clientId= request.getParameter("clientId");
		String type= request.getParameter("type");
		HashMap<String, Object> outputMap=new HashMap<>();				
		String returnAjaxString;
		try
		{
			if(type.equals("P"))
			{
				outputMap.put("pendingAmountDetails", lObjConfigDao.getPendingInvoicesForThisVendor(clientId,con));
			}
			else
			{
				outputMap.put("pendingAmountDetails", lObjConfigDao.getPendingInvoicesForThisClient(clientId,con));
			}
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return outputMap;
	}
	
	
	
	
	
	
	public HashMap<String, Object> getPendingAmountForClientitem(HttpServletRequest request,Connection con) throws ClassNotFoundException, SQLException
	{
		CustomResultObject rs=new CustomResultObject();
		long clientId= Integer.parseInt(request.getParameter("clientId"));		
		HashMap<String, Object> outputMap=new HashMap<>();				
		String returnAjaxString;
		try
		{
			String fromDate="23/01/1992";
			String toDate=cf.getDateFromDB(con);
			outputMap.put("pendingAmountDetails", lObjConfigDao.getPendingAmountForThisClient(clientId,fromDate,toDate,con));			
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return outputMap;
	}
	
	
	public HashMap<String, Object> getRoutineDetailsForThisClient(HttpServletRequest request,Connection con) throws ClassNotFoundException, SQLException
	{
		CustomResultObject rs=new CustomResultObject();
		long clientId= Integer.parseInt(request.getParameter("clientId"));		
		HashMap<String, Object> outputMap=new HashMap<>();				
		String returnAjaxString;
		try
		{
			String fromDate="";
			String toDate="";
			outputMap.put("routineDetails", lObjConfigDao.getPendingAmountForThisClient(clientId,fromDate,toDate,con));			
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return outputMap;
	}
	
	
	
	public CustomResultObject savePayment(HttpServletRequest request,Connection con) throws ClassNotFoundException, SQLException
	{
		CustomResultObject rs=new CustomResultObject();
		String date= (request.getParameter("txtdate"));		
		long clientId= Integer.parseInt(request.getParameter("clientId"));
		long bankId= Integer.parseInt(request.getParameter("bankid"));
		String payment_for= request.getParameter("payment_for");
		String total_amount= request.getParameter("total_amount");
		
		String ref_id= request.getParameter("ref_id");
		
		String[] refIdArr= ref_id.split(",");
		
		String remarks= request.getParameter("remarks");
		
		String type= request.getParameter("type");
		
		
		HashMap<String, Object> hm =new HashMap<>();
		
		
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		hm.put("app_id", appId);
		
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		hm.put("user_id", userId);
		
		String firmId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("firm_id");
		hm.put("firm_id", firmId);
		
			
		hm.put("date", date);
		hm.put("client_id", clientId);
		hm.put("bankId", bankId);	
		hm.put("remarks", remarks);
		
		hm.put("type", type);
		hm.put("total_amount", total_amount);
		
		
		
		
		
		
		
		
		
		HashMap<String, Object> outputMap=new HashMap<>();				
		String retMessage="";
		try
		{
				long payment_id=lObjConfigDao.savePaymentRegister(hm,con);
				for(String m:refIdArr)
				{	
					String[] reqArr=m.split("-",-1);					
					hm.put("ref_id", reqArr[0]);
					hm.put("amount", reqArr[1]);
					hm.put("remarks", reqArr[2]);
					hm.put("job_sheet_no", reqArr[3]);
					hm.put("payment_for", reqArr[4]);
					hm.put("payment_id", payment_id);
					
					
				
					
					lObjConfigDao.savePaymentDetails(hm,con);
				}
		
			hm.put("returnMessage", "Payment Added Succesfully " +payment_id);
			rs.setAjaxData(mapper.writeValueAsString(hm));						
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	public CustomResultObject saveCompositeItemDetails(HttpServletRequest request,Connection con) throws ClassNotFoundException, SQLException
	{
		CustomResultObject rs=new CustomResultObject();
		
		
		Enumeration<String> params = request.getParameterNames(); 
		HashMap<String, Object> hm=new HashMap<>();
		List<HashMap<String, Object>> itemListRequired=new ArrayList<HashMap<String, Object>>();
		while(params.hasMoreElements())
		{
		 String paramName = params.nextElement();
		 
		 if(paramName.equals("itemDetails")) 
		 {
			 String[] itemsList=request.getParameter(paramName).split("\\|");
			 for(String item:itemsList)
			 {
				 String itemDetails[] =item.split("~");
				 HashMap<String, Object> itemDetailsMap=new HashMap<>();
				 itemDetailsMap.put("item_id",itemDetails[0]);
				 itemDetailsMap.put("qty",itemDetails[1]);				 
				 itemListRequired.add(itemDetailsMap);				 
				 //ID, QTY
			 }
			 hm.put("itemDetails", itemListRequired);
			 continue;
		 }		 
		 hm.put(paramName, request.getParameter(paramName));
		 
		}
		lObjConfigDao.deleteCompositeItem(hm,con);
		for(HashMap<String, Object> item:itemListRequired)
		{
			item.put("parentItemId", hm.get("parentItemId"));
			lObjConfigDao.saveCompositeItem(item,con);
		}
		rs.setReturnObject(hm);		
		rs.setAjaxData("Item Updated Succesfully");
		return rs;
	}
	
	
	
	
	
	
	public CustomResultObject getItemDetailsByAjax(HttpServletRequest request,Connection con) throws ClassNotFoundException, SQLException
	{
		CustomResultObject rs=new CustomResultObject();
		
		try
		{			
						
			rs.setAjaxData(mapper.writeValueAsString(getItemDetailsByAjaxService(request, con)));
			
							
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	
	public LinkedHashMap<String,String> getItemDetailsByAjaxService(HttpServletRequest request,Connection con) throws ClassNotFoundException, SQLException
	{
		CustomResultObject rs=new CustomResultObject();		
		String itemId= request.getParameter("itemId");
		LinkedHashMap<String, String> outputMap=new LinkedHashMap<>();				
		String returnAjaxString;
		String firmId=request.getParameter("sourcefirmId");
		String sourceWareHouseId=request.getParameter("sourceWareHouseId");
		String destinationfirmId=request.getParameter("destinationfirmId");;
		String destinationWareHouseId=request.getParameter("destinationWareHouseId");;
		String customerId= request.getParameter("customerId");
		try
		{			
						
			outputMap=lObjConfigDao.getServicedetailsByIdForfirm(customerId,itemId,firmId,destinationfirmId,sourceWareHouseId,destinationWareHouseId, con);
			
							
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}	
		return outputMap;
		
	}
	
	
	public CustomResultObject searchForClient(HttpServletRequest request,Connection con) throws ClassNotFoundException, SQLException
	{
		CustomResultObject rs=new CustomResultObject();
		String searchString= request.getParameter("searchString");
		
		HashMap<String, Object> outputMap=new HashMap<>();				
		outputMap.put("searchString", searchString);
		
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);
		
		
		String returnAjaxString;
		try
		{			
						
			
			rs.setAjaxData(mapper.writeValueAsString(lObjConfigDao.getClientList(outputMap ,con)));
			
							
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	
	
	
	public CustomResultObject showCategoryMasterNew(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
		
		String exportFlag= request.getParameter("exportFlag")==null?"":request.getParameter("exportFlag");
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+"/";	
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		 
		try
		{			
			List<LinkedHashMap<String, Object>> requiredList=new ArrayList<>();
			String [] colNames= {"categoryId","categoryName"};
			outputMap.put("app_id", appId);
			List<LinkedHashMap<String, Object>> lst = lObjConfigDao.getCategoryMaster(outputMap,con);
			
			if(!exportFlag.isEmpty())
				{
					outputMap = getCommonFileGenerator(colNames,lst,exportFlag,DestinationPath,userId,"CategoryMaster");
				}
			else
				{
					outputMap.put("ListOfCategories", lst);	
					rs.setViewName("../Category.jsp");
					rs.setReturnObject(outputMap);
				}			
			}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}
		rs.setReturnObject(outputMap);
		return rs;
	}
	
	public CustomResultObject showWareHouseMaster(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
		
		String exportFlag= request.getParameter("exportFlag")==null?"":request.getParameter("exportFlag");
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+"/";	
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		 
		try
		{			
			List<LinkedHashMap<String, Object>> requiredList=new ArrayList<>();
			String [] colNames= {"categoryId","categoryName"};
			outputMap.put("app_id", appId);
			List<LinkedHashMap<String, Object>> lst = lObjConfigDao.getListOfWareHouse(outputMap,con);
			
			if(!exportFlag.isEmpty())
				{
					outputMap = getCommonFileGenerator(colNames,lst,exportFlag,DestinationPath,userId,"WareHouseMaster");
				}
			else
				{
					outputMap.put("ListOfWareHouse", lst);	
					rs.setViewName("../WareHouse.jsp");
					rs.setReturnObject(outputMap);
				}			
			}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}
		rs.setReturnObject(outputMap);
		return rs;
	}
	
	
	public CustomResultObject showBrandMaster(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
		
		String exportFlag= request.getParameter("exportFlag")==null?"":request.getParameter("exportFlag");
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+"/";	
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		 
		try
		{			
			List<LinkedHashMap<String, Object>> requiredList=new ArrayList<>();
			String [] colNames= {"brandId","brandName"};
			outputMap.put("app_id", appId);
			List<LinkedHashMap<String, Object>> lst = lObjConfigDao.getBrandMaster(outputMap,con);
			
			if(!exportFlag.isEmpty())
				{
					outputMap = getCommonFileGenerator(colNames,lst,exportFlag,DestinationPath,userId,"BrandMaster");
				}
			else
				{
					outputMap.put("ListOfBrands", lst);	
					rs.setViewName("../Brand.jsp");
					rs.setReturnObject(outputMap);
				}			
			}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}
		rs.setReturnObject(outputMap);
		return rs;
	}
	
	
	
	
	public CustomResultObject showBankMaster(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
		
		String exportFlag= request.getParameter("exportFlag")==null?"":request.getParameter("exportFlag");
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+"/";	
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		 
		try
		{			
			List<LinkedHashMap<String, Object>> requiredList=new ArrayList<>();
			String [] colNames= {"BankId","BankName"};
			outputMap.put("app_id", appId);
			List<LinkedHashMap<String, Object>> lst = lObjConfigDao.getBankMaster(outputMap,con);
			
			if(!exportFlag.isEmpty())
				{
					outputMap = getCommonFileGenerator(colNames,lst,exportFlag,DestinationPath,userId,"BankMaster");
				}
			else
				{
					outputMap.put("ListOfBanks", lst);	
					rs.setViewName("../BankMaster.jsp");
					rs.setReturnObject(outputMap);
				}			
			}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}
		rs.setReturnObject(outputMap);
		return rs;
	}
	
	public CustomResultObject showSBUMaster(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
		
		String exportFlag= request.getParameter("exportFlag")==null?"":request.getParameter("exportFlag");
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+"/";	
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		 
		try
		{			
			List<LinkedHashMap<String, Object>> requiredList=new ArrayList<>();
			String [] colNames= {"SBUId","SBUName"};
			outputMap.put("app_id", appId);
			List<LinkedHashMap<String, Object>> lst = lObjConfigDao.getSBUMaster(outputMap,con);
			
			if(!exportFlag.isEmpty())
				{
					outputMap = getCommonFileGenerator(colNames,lst,exportFlag,DestinationPath,userId,"SBUMaster");
				}
			else
				{
					outputMap.put("ListOfSBU", lst);	
					rs.setViewName("../SBUMaster.jsp");
					rs.setReturnObject(outputMap);
				}			
			}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}
		rs.setReturnObject(outputMap);
		return rs;
	}
	
	
	
	
	
	public CustomResultObject showBookingsRegister(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
		
		String exportFlag= request.getParameter("exportFlag")==null?"":request.getParameter("exportFlag");
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+"/";	
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		 
		try
		{			
			List<LinkedHashMap<String, Object>> requiredList=new ArrayList<>();
			String [] colNames= {"categoryId","categoryName"};
			outputMap.put("app_id", appId);
			
			
			
			String fromDate=request.getParameter("fromDate");
			String toDate=request.getParameter("toDate");
			
			if(fromDate==null || fromDate.equals(""))
			{
					fromDate=getDateFromDB(con);
			}
			
			if(toDate==null || toDate.equals(""))
			{
					toDate=getDateFromDB(con);
			}
			outputMap.put("fromDate", (fromDate));
			outputMap.put("toDate", (toDate));
			outputMap.put("todaysDate", lObjConfigDao.getDateFromDB(con));
			
			
			
			List<LinkedHashMap<String, Object>> lst = lObjConfigDao.getBookingRegister(outputMap,con);
			
			
			
			
			outputMap.put("todaysDate", lObjConfigDao.getDateFromDB(con));
			
			if(!exportFlag.isEmpty())
				{
					outputMap = getCommonFileGenerator(colNames,lst,exportFlag,DestinationPath,userId,"CategoryMaster");
				}
			else
				{
					outputMap.put("ListOfBookings", lst);	
					rs.setViewName("../BookingsMaster.jsp");
					rs.setReturnObject(outputMap);
				}			
			}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}
		rs.setReturnObject(outputMap);
		return rs;
	}
	
	public CustomResultObject showGroupMaster(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
		String exportFlag= request.getParameter("exportFlag")==null?"":request.getParameter("exportFlag");
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+"/";	
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);
		try
		{			
			List<LinkedHashMap<String, Object>> requiredList=new ArrayList<>();
			String [] colNames= {"categoryId","categoryName"};
			
			List<LinkedHashMap<String, Object>> lst = lObjConfigDao.getClientGroup(appId,con);
			
			if(!exportFlag.isEmpty())
				{
					outputMap = getCommonFileGenerator(colNames,lst,exportFlag,DestinationPath,userId,"GroupMaster");
				}
			else
				{
					outputMap.put("ListofGroups", lst);	
					rs.setViewName("../GroupMaster.jsp");
					rs.setReturnObject(outputMap);
				}			
			}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}
		rs.setReturnObject(outputMap);
		return rs;
	}
	
	
	public CustomResultObject showExpenseHeads(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
		String exportFlag= request.getParameter("exportFlag")==null?"":request.getParameter("exportFlag");
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+"/";	
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);
		try
		{			
			List<LinkedHashMap<String, Object>> requiredList=new ArrayList<>();
			String [] colNames= {"expenseId","expenseName"};
			
			List<LinkedHashMap<String, Object>> lst = lObjConfigDao.getExpenseHeads(appId,con);
			
			if(!exportFlag.isEmpty())
				{
					outputMap = getCommonFileGenerator(colNames,lst,exportFlag,DestinationPath,userId,"ClientGroups");
				}
			else
				{
					outputMap.put("ListofExpenseHeads", lst);	
					rs.setViewName("../ExpenseHeads.jsp");
					rs.setReturnObject(outputMap);
				}			
			}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}
		rs.setReturnObject(outputMap);
		return rs;
	}
	
	
	public CustomResultObject showExpenseEntry(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
		String exportFlag= request.getParameter("exportFlag")==null?"":request.getParameter("exportFlag");
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+"/";	
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);
		try
		{			
			List<LinkedHashMap<String, Object>> requiredList=new ArrayList<>();
			String [] colNames= {"categoryId","categoryName"};
			
			String fromDate=request.getParameter("fromDate");
			String toDate=request.getParameter("toDate");
			
			if(fromDate==null || fromDate.equals(""))
			{
					fromDate=getDateFromDB(con);
			}
			
			if(toDate==null || toDate.equals(""))
			{
					toDate=getDateFromDB(con);
			}
			
			outputMap.put("fromDate", (fromDate));
			outputMap.put("toDate", (toDate));
			outputMap.put("todaysDate", lObjConfigDao.getDateFromDB(con));
			
			List<LinkedHashMap<String, Object>> lst = lObjConfigDao.getExpenseRegister(outputMap,con);
			
			outputMap.put("totalAmount", getTotalAmountExpenditure(lst));
			
			if(!exportFlag.isEmpty())
				{
					outputMap = getCommonFileGenerator(colNames,lst,exportFlag,DestinationPath,userId,"ClientGroups");
				}
			else
				{
					outputMap.put("ListofExpense", lst);	
					rs.setViewName("../ExpenseRegister.jsp");
					rs.setReturnObject(outputMap);
				}			
			}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}
		rs.setReturnObject(outputMap);
		return rs;
	}
	

	public CustomResultObject showClientDeliveryRoutine(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
		String exportFlag= request.getParameter("exportFlag")==null?"":request.getParameter("exportFlag");
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+"/";	
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);
		try
		{			
			List<LinkedHashMap<String, Object>> requiredList=new ArrayList<>();
			String [] colNames= {"categoryId","categoryName"};
			
			outputMap.put("client_id", request.getParameter("clientId"));
			outputMap.put("ListOfClients", lObjConfigDao.getClientMaster(outputMap, con));
			
			
			
			List<LinkedHashMap<String, Object>> lst = lObjConfigDao.getClientDeliveryRoutine(con,outputMap);
			
			if(!exportFlag.isEmpty())
				{
					outputMap = getCommonFileGenerator(colNames,lst,exportFlag,DestinationPath,userId,"ClientGroups");
				}
			else
				{
					outputMap.put("ListOfRoutines", lst);	
					rs.setViewName("../Routines.jsp");
					rs.setReturnObject(outputMap);
				}			
			}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}
		rs.setReturnObject(outputMap);
		return rs;
	}
	
	
	
	
	public CustomResultObject showServiceMaster(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
		String exportFlag= request.getParameter("exportFlag")==null?"":request.getParameter("exportFlag");
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+"/";	
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);
		
		try
		{			
			String [] colNames= {"service_id","service_name","occurance","due_date"};
			
			outputMap.put("categoryId", request.getParameter("categoryId"));
			outputMap.put("searchInput", request.getParameter("searchInput"));
			
			List<LinkedHashMap<String, Object>> lst = lObjConfigDao.getServiceMaster(outputMap,con);
			
			
			
			if(!exportFlag.isEmpty())
			{
				outputMap = getCommonFileGenerator(colNames,lst,exportFlag,DestinationPath,userId,"ServiceMaster");
			}
			else
			{
				outputMap.put("ListOfServices", lst);
				outputMap.put("ListOfCategories", lObjConfigDao.getCategories(outputMap,con));
				rs.setViewName("../ServiceMaster.jsp");
				rs.setReturnObject(outputMap);
			}
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}		
		rs.setReturnObject(outputMap);
		return rs;
	}
	
	
	
	public CustomResultObject deleteCategory(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		long categoryId= Integer.parseInt(request.getParameter("categoryid"));		
		HashMap<String, Object> outputMap=new HashMap<>();
				
		String returnAjaxString;
		try
		{	
			if(!lObjConfigDao.ProductExistForThisCategory(categoryId,con))
			{
				rs.setAjaxData(lObjConfigDao.deleteCategory(categoryId,con));
			}
			else
			{
				rs.setAjaxData("Cannot Delete as items Exist with this category");
			}
			
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	public CustomResultObject deleteWareHouse(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		long warehouseid= Integer.parseInt(request.getParameter("warehouseid"));		
		HashMap<String, Object> outputMap=new HashMap<>();
				
		String returnAjaxString;
		try
		{	
			if(!lObjConfigDao.ProductExistForThisWareHouse(warehouseid,con))
			{
				rs.setAjaxData(lObjConfigDao.deleteWareHouse(warehouseid,con));
			}
			else
			{
				rs.setAjaxData("Cannot Delete as items Exist with this category");
			}
			
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	public CustomResultObject deleteSBU(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		long sbuid= Integer.parseInt(request.getParameter("sbuid"));		
		HashMap<String, Object> outputMap=new HashMap<>();
				
		String returnAjaxString;
		try
		{	
			
				rs.setAjaxData(lObjConfigDao.deletesbu(sbuid,con));
			
			
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	public CustomResultObject deleteBank(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		long sbuid= Integer.parseInt(request.getParameter("bankid"));		
		HashMap<String, Object> outputMap=new HashMap<>();
				
		String returnAjaxString;
		try
		{	
			
				rs.setAjaxData(lObjConfigDao.deletebank(sbuid,con));
			
			
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	
	
	public CustomResultObject deleteBrand(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		long brandId= Integer.parseInt(request.getParameter("brandid"));		
		HashMap<String, Object> outputMap=new HashMap<>();
				
		String returnAjaxString;
		try
		{	
			
				rs.setAjaxData(lObjConfigDao.deleteBrand(brandId,con));
			
			
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	
	
	public CustomResultObject deleteBooking(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		long bookingid= Integer.parseInt(request.getParameter("bookingid"));		
		HashMap<String, Object> outputMap=new HashMap<>();
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);	
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		outputMap.put("user_id", userId);
		outputMap.put("booking_id", bookingid);
		String returnAjaxString;
		try
		{	
			
				rs.setAjaxData(lObjConfigDao.deleteBooking(outputMap,con));
			
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	
	
	public CustomResultObject showitems(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);
		try
		{		 
		
		
		outputMap.put("ListOfitems", lObjConfigDao.showServices(outputMap,con));
		
		
		rs.setViewName("../Showitems.jsp");									
		
		rs.setReturnObject(outputMap);
				
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	public CustomResultObject showPrintLabelsScreen(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
		try
		{		 
		
			String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
			outputMap.put("app_id", appId);
		outputMap.put("itemList", lObjConfigDao.getBankMaster(outputMap,con));
		rs.setViewName("../PrintLabels.jsp");								
		
		rs.setReturnObject(outputMap);
				
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	public CustomResultObject showAddBooking(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
		try
		{		 
		
			String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
			outputMap.put("app_id", appId);
		outputMap.put("itemList", lObjConfigDao.getBankMaster(outputMap,con));
		
		outputMap.put("todaysDate", lObjConfigDao.getDateFromDB(con));
		outputMap.put("tentativeSerialNo", lObjConfigDao.getTentativeSequenceNo(appId,"trn_booking_register",con));
		outputMap.put("clientMaster", lObjConfigDao.getClientMaster(outputMap, con));
		outputMap.put("EmployeeList", lObjConfigDao.getEmployeeMaster(outputMap, con));
		
		rs.setViewName("../AddNewBooking.jsp");								
		
		rs.setReturnObject(outputMap);
				
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	public CustomResultObject showAuditTrail(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
		try
		{		 
		
			
		String username=request.getParameter("username");
		if(username!=null && !username.equals(""))
		{
			outputMap.put("auditList", lObjConfigDao.getAuditListByUser(username,con));
		}
		outputMap.put("memoryStats", cf.getMemoryStats( ));
		outputMap.put("activeConnections", cf.getActiveConnections( con));
		outputMap.put("latestUserHits", lObjConfigDao.getLastestUserHits( con));
		
		rs.setViewName("../AuditTrail.jsp");								
		
		rs.setReturnObject(outputMap);
				
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	
	
	
	
	public CustomResultObject showPrintLabelsScreenClient(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
		try
		{		 
		
			String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
			outputMap.put("app_id", appId);
		outputMap.put("ListOfClients", lObjConfigDao.getClientMaster(outputMap,con));
		rs.setViewName("../PrintLabelsClient.jsp");								
		
		rs.setReturnObject(outputMap);
				
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	
	
	public CustomResultObject showInventoryCounting(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
		String firmId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("firm_id");
		String stockModificationId=request.getParameter("stockModificationId");
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);
		
		try
		{		 
		
			
			if(stockModificationId!=null)
			{
				outputMap.put("stockModificationDetails", lObjConfigDao.getStockModificationDetailsInventoryCounting(stockModificationId,con));
			}
			
			
			outputMap.put("firmId", firmId);
		outputMap.put("itemList", lObjConfigDao.getBankMaster(outputMap,con));
		outputMap.put("inventoryCountingList", lObjConfigDao.getInventoryCountingListForThisfirm(outputMap,con));	
		rs.setViewName("../InventoryCounting.jsp");	
		
		
		
		rs.setReturnObject(outputMap);
				
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	
	
	
	public CustomResultObject showStockStatus(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
		
		String exportFlag= request.getParameter("exportFlag")==null?"":request.getParameter("exportFlag");
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+"/";	
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		String categoryId=request.getParameter("categoryId");	
		outputMap.put("categoryId", categoryId);
		
		String searchString=request.getParameter("searchString");	
		outputMap.put("searchString", searchString);
		
		String firmId=request.getParameter("firmId");	
		outputMap.put("firmId", firmId);
		
		String warehouseid=request.getParameter("warehouseid");	
		outputMap.put("warehouseid", warehouseid);
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);
		
		try
		{			
			List<LinkedHashMap<String, Object>> requiredList=new ArrayList<>();
			String [] colNames= {"firm_name","ware_house_name","item_name","product_code","size","color","availableqty","PurchaseRate","value1"};
			
			List<LinkedHashMap<String, Object>> lst =  lObjConfigDao.getStockStatus(outputMap,con);
			
			outputMap.put("sumTotal", getTotalAmountStockStatus(lst));
			
			if(!exportFlag.isEmpty())
			{
				outputMap = getCommonFileGenerator(colNames,lst,exportFlag,DestinationPath,userId,"StockStatus");
			}
			else
			{
				outputMap.put("ListStock", lst);
				outputMap.put("ListOfCategories", lObjConfigDao.getCategories(outputMap,con));
				outputMap.put("listOffirm", lObjConfigDao.getfirmMaster(outputMap,con));
				outputMap.put("listofwarehouse", lObjConfigDao.getListOfWareHouse(outputMap,con));
				
				//outputMap.put("totalDetails", totalDetails);
				
				
				rs.setViewName("../StockStatus.jsp");
				rs.setReturnObject(outputMap);
			}
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}
		rs.setReturnObject(outputMap);
		return rs;
	}
	
	public CustomResultObject showStockModifications(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
		
		String exportFlag= request.getParameter("exportFlag")==null?"":request.getParameter("exportFlag");
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+"/";	
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);
		
		String firmId=request.getParameter("firmId");	
		outputMap.put("firmId", firmId);		
		
		try
		{			
			List<LinkedHashMap<String, Object>> requiredList=new ArrayList<>();
			String [] colNames= {"stock_id","firm_name","item_name","qty_available"};
			
			List<LinkedHashMap<String, Object>> lst =  lObjConfigDao.getStockModifications(outputMap,con);
			
			if(!exportFlag.isEmpty())
			{
				outputMap = getCommonFileGenerator(colNames,lst,exportFlag,DestinationPath,userId,"StockStatus");
			}
			else
			{
				outputMap.put("ListStockModifications", lst);			
				outputMap.put("listOffirm", lObjConfigDao.getfirmMaster(outputMap,con));				
				rs.setViewName("../StockModifications.jsp");
				rs.setReturnObject(outputMap);
			}
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}
		rs.setReturnObject(outputMap);
		return rs;
	}
	
	
	
	public CustomResultObject updateCategory(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		long categoryId= Integer.parseInt(request.getParameter("categoryid"));
		String categoryName= (request.getParameter("categoryName"));
		HashMap<String, Object> outputMap=new HashMap<>();
				
		try
		{			
			
			
			
				 rs.setAjaxData(lObjConfigDao.updateCategory(categoryId,con,categoryName));
				 rs.setReturnObject(outputMap);
			
			
			
			
							
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	
	public CustomResultObject updateLowStock(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		long stock_id= Long.parseLong(request.getParameter("stock_id"));
		long lowqty= Long.parseLong(request.getParameter("lowqty"));
		HashMap<String, Object> outputMap=new HashMap<>();				
		try
		{			
			
			lObjConfigDao.updateLowStockDetails(stock_id,lowqty,con);
			 rs.setAjaxData("<script>alert('Low Stock Configured Succesfully');window.location='?a=showStockStatus';</script>");			
				 rs.setReturnObject(outputMap);
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	
	public CustomResultObject transferStock(HttpServletRequest request,Connection con) 
	{
		CustomResultObject rs=new CustomResultObject();
		long fromfirmId= Long.parseLong(request.getParameter("drpfromfirm"));
		long fromWareHouseId= Long.parseLong(request.getParameter("ware_house_id"));
		long tofirmId= Long.parseLong(request.getParameter("drptofirm"));
		long toWareHouseId= Long.parseLong(request.getParameter("to_ware_house_id"));
		String transactionDate= request.getParameter("transactionDate");
		String outerremarks= request.getParameter("outerremarks");

		
		
		String ListoFitems[]=request.getParameter("itemDetails").toString().split("\\|");
		HashMap<String, Object> outputMap=new HashMap<>();
		try 
		{
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		
		outputMap.put("userId", userId);
		outputMap.put("firmId", fromfirmId);
		outputMap.put("destinationFirmId", tofirmId);
		outputMap.put("outerRemarks", outerremarks);
		outputMap.put("ware_house_id", fromWareHouseId);
		
		outputMap.put("destination_ware_house_id", toWareHouseId);
		
		
		
		String StockModificationType="StockTransfer";			
		outputMap.put("type", StockModificationType);	
		
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);
		
		long stockModificationId= lObjConfigDao.addStockModification(outputMap,con);
		
		
		for(String item:ListoFitems)
		{
			String[] itemdetails=item.split("~");		
			long itemId=Long.valueOf(itemdetails[0]);
			String sourceBefore=(itemdetails[1]);
			String sourceAfter=(itemdetails[2]);
			double qtytotransfer=Double.parseDouble(itemdetails[3]);
			String destinationBefore=(itemdetails[4]);
			String destinationAfter=(itemdetails[5]);
			String destinationPrice=(itemdetails[6]);
		
		
		
		
		outputMap.put("stockModificationId", stockModificationId);
		outputMap.put("itemId", itemId);
		outputMap.put("sourcebefore", sourceBefore);
		outputMap.put("sourceafter", sourceAfter);
		outputMap.put("qty", qtytotransfer);
		outputMap.put("destinationbefore", destinationBefore);
		outputMap.put("destinationafter", destinationAfter);
		outputMap.put("sourcefirm", fromfirmId);
		outputMap.put("destinationfirm", tofirmId);
		lObjConfigDao.saveStockModificationtransferStock(outputMap,con);
		
				
			String fromStockId=lObjConfigDao.checkifStockAlreadyExist(fromfirmId, itemId,fromWareHouseId, con);
			if(fromStockId.equals("0")) 
			{ 
				HashMap<String, Object> stockDetails=new HashMap<>();
				stockDetails.put("drpfirmId",fromfirmId);
				stockDetails.put("drpitems",itemId);
				stockDetails.put("qty",0);
				stockDetails.put("app_id",appId);
				stockDetails.put("ware_house_id",fromWareHouseId);
				
				fromStockId=String.valueOf(lObjConfigDao.addStockMaster(stockDetails, con));				
			}	
			String toStockId=lObjConfigDao.checkifStockAlreadyExist(tofirmId, itemId,toWareHouseId, con);
			if(toStockId.equals("0")) 
			{ 
				HashMap<String, Object> stockDetails=new HashMap<>();
				stockDetails.put("drpfirmId",tofirmId);
				stockDetails.put("drpitems",itemId);
				stockDetails.put("qty",0);
				stockDetails.put("price",0);
				stockDetails.put("app_id",appId);
				stockDetails.put("ware_house_id",toWareHouseId);
				toStockId=String.valueOf(lObjConfigDao.addStockMaster(stockDetails, con));				
			}
			
			
			
			outputMap.put("qty", qtytotransfer*-1);
			outputMap.put("stock_id", fromStockId);
			double qtyAvailable= Double.parseDouble(lObjConfigDao.getStockDetailsbyId(outputMap, con).get("qty_available"));
			double averagePrice= Double.parseDouble(lObjConfigDao.getStockDetailsbyId(outputMap, con).get("average_price"));		
			double newPrice=averagePrice;		
			double newAveragePrice=0;		
			newAveragePrice=((qtyAvailable*averagePrice) + (qtytotransfer*newPrice)) / (qtyAvailable + qtytotransfer);			
			outputMap.put("average_price", newAveragePrice);		
			lObjConfigDao.updateStockMaster(outputMap, con);
			
			
			
			// code to calculate average price for destination
			outputMap.put("qty", qtytotransfer);
			outputMap.put("stock_id", toStockId);
			 qtyAvailable= Double.parseDouble(lObjConfigDao.getStockDetailsbyId(outputMap, con).get("qty_available"));
			averagePrice= Double.parseDouble(lObjConfigDao.getStockDetailsbyId(outputMap, con).get("average_price"));		
			newPrice=Double.valueOf(destinationPrice);		
			newAveragePrice=0;		
			newAveragePrice=((qtyAvailable*averagePrice) + (qtytotransfer*newPrice)) / (qtyAvailable + qtytotransfer);			
			outputMap.put("average_price", newAveragePrice);			
			lObjConfigDao.updateStockMaster(outputMap, con);
			
			
			
			outputMap.put("drpfirmId", fromfirmId);
			outputMap.put("ware_house_id", fromWareHouseId);
			
			outputMap.put("drpitems", itemId);
			outputMap.put("qty", qtytotransfer*-1);
			outputMap.put("type", "StockTransfer");
			outputMap.put("user_id", userId);
			outputMap.put("remarks", "");			
			
			lObjConfigDao.addStockRegister(outputMap, con);
			
			
			outputMap.put("drpfirmId", tofirmId);
			outputMap.put("ware_house_id", toWareHouseId);
			outputMap.put("drpitems", itemId);
			outputMap.put("qty", qtytotransfer);
			outputMap.put("type", "StockTransfer");
			outputMap.put("user_id", userId);
			outputMap.put("remarks", "");
			
			
			if(lObjConfigDao.checkifStockAlreadyExist(tofirmId,itemId,toWareHouseId, con).equals("0"))
			{
				lObjConfigDao.addStockMaster(outputMap,con);
			}
			
			lObjConfigDao.addStockRegister(outputMap, con);
		}
			
			rs.setAjaxData("Stock Transfered Succesfully:~"+stockModificationId);
			
			rs.setReturnObject(outputMap);						
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	
	
	public CustomResultObject showAddService(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);
		try
		{		
			String serviceId=request.getParameter("serviceId");
			
			if(serviceId!=null)
			{
				outputMap.put("service_id", serviceId);
				outputMap.put("serviceDetails", lObjConfigDao.getServiceDetailsById(outputMap,con));

						
			}
			outputMap.put("CategoriesList", lObjConfigDao.getCategories(outputMap,con));
			
			
			
			
			
			rs.setViewName("../AddServices.jsp");	
			rs.setReturnObject(outputMap);
			
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	public CustomResultObject showAddStock(HttpServletRequest request,Connection con)
	{

		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
		String stockType=request.getParameter("type");
		String firmId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("firm_id");
		String stockModificationId=request.getParameter("stockModificationId");
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);
		outputMap.put("stockType", stockType);
		
		try
		{	
			
			if(stockModificationId!=null)
			{
				outputMap.put("stockModificationDetails", lObjConfigDao.getStockModificationDetailsAddRemove(stockModificationId,con));
			}
			
		outputMap.put("firmId", firmId);
		outputMap.put("itemList", lObjConfigDao.getBankMaster(outputMap,con));
		outputMap.put("wareHouseList", lObjConfigDao.getListOfWareHouse(outputMap, con));
		outputMap.put("todaysDate", lObjConfigDao.getDateFromDB(con));
		outputMap.put("addStockList", lObjConfigDao.getInventoryCountingListForThisfirm(outputMap,con));	
		rs.setViewName("../AddStock.jsp");	
		rs.setReturnObject(outputMap);
				
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	
	}
	
	public CustomResultObject showCollectPayment(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");		
		outputMap.put("app_id", appId);
		try
		{
			outputMap.put("todaysDate", lObjConfigDao.getDateFromDB(con));
			outputMap.put("clientMaster", lObjConfigDao.getClientMaster(outputMap, con));
			
			rs.setViewName("../CollectPayment.jsp");	
			rs.setReturnObject(outputMap);
			
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	
	
	
	
	public CustomResultObject showGenerateInvoice(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
		
		try
		{			
			
			String invoiceId=request.getParameter("invoice_id");
			String tableId=request.getParameter("table_id");
			String bookingId=request.getParameter("booking_id");
			String MobilebookingId=request.getParameter("mobile_booking_id");
			
			String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
			
			String firmId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("firm_id");
			
			
			outputMap.put("firm_id", firmId);
			outputMap.put("app_id", appId);
			outputMap.put("table_id", tableId);			
			
			if(invoiceId!=null)
			{
				outputMap.put("invoiceDetails", lObjConfigDao.getInvoiceDetails(invoiceId,con));				
			}
			
			if(tableId!=null)
			{
				outputMap.put("invoiceDetails", lObjConfigDao.getInvoiceDetailsForTable(tableId,con));				
			}
			
			if(bookingId!=null)
			{
				LinkedHashMap<String, Object> hm=lObjConfigDao.getInvoiceDetailsForBooking(bookingId,con);				
				List<LinkedHashMap<String, Object>> lst=(List<LinkedHashMap<String, Object>>) hm.get("listOfitems");
				hm.put("client_id", lst.get(0).get("client_id"));
				hm.put("client_name", lst.get(0).get("client_name"));				
				outputMap.put("invoiceDetails", hm);			
			}
			
			if(MobilebookingId!=null)
			{
				LinkedHashMap<String, Object> hm=lObjConfigDao.getInvoiceDetailsForMobileBooking(bookingId,con);				
				List<LinkedHashMap<String, Object>> lst=(List<LinkedHashMap<String, Object>>) hm.get("listOfitems");
				hm.put("client_id", lst.get(0).get("client_id"));
				hm.put("client_name", lst.get(0).get("client_name"));				
				outputMap.put("invoiceDetails", hm);			
			}
			
			
			
			
			outputMap.put("itemList", lObjConfigDao.getItemMasterForGenerateInvoice(outputMap,con));
			outputMap.put("todaysDate", lObjConfigDao.getDateFromDB(con));
			outputMap.put("tentativeSerialNo", lObjConfigDao.getTentativeSequenceNo(appId,"trn_invoice_register",con));
			outputMap.put("clientMaster", lObjConfigDao.getClientMaster(outputMap, con));
			
			
			
			rs.setViewName("../GenerateInvoice.jsp");	
			rs.setReturnObject(outputMap);
			
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	public CustomResultObject showGenerateSI(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
		
		try
		{			
			
			String invoiceId=request.getParameter("invoiceId");
			
			
			
			String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
			
			String firmId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("firm_id");
			
			
			outputMap.put("firm_id", firmId);
			outputMap.put("app_id", appId);
						
			
			if(invoiceId!=null)
			{
				outputMap.put("invoiceDetails", lObjConfigDao.getSalesInvoiceDetails(invoiceId,con));				
			}
			
		
			List<LinkedHashMap<String, Object>> lst=new ArrayList<>();
			//lst.addAll(lObjConfigDao.getitemMasterForSales(outputMap,con));
			lst.addAll(lObjConfigDao.getPurchaseMinusSalesItems(outputMap, con));
			
			outputMap.put("itemList", lst);
			outputMap.put("todaysDate", lObjConfigDao.getDateFromDB(con));
			outputMap.put("tentativeSerialNo", lObjConfigDao.getTentativeSequenceNo(appId,"trn_sales_invoice_register",con));
			
			outputMap.put("clientList", lObjConfigDao.getClientMaster(outputMap,con));
			outputMap.put("listwarehouse", lObjConfigDao.getListOfWareHouse(outputMap, con));
			
			
			
			rs.setViewName("../GenerateSI.jsp");	
			rs.setReturnObject(outputMap);
			
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	
	
	public CustomResultObject showGeneratePI(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
		
		try
		{			
			
			String invoiceId=request.getParameter("invoiceId");
			String challanId=request.getParameter("challanId");
			
			
			String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
			
			String firmId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("firm_id");
			
			
			outputMap.put("firm_id", firmId);
			outputMap.put("app_id", appId);
						
			
			if(invoiceId!=null)
			{
				outputMap.put("invoiceDetails", lObjConfigDao.getPurchaseInvoiceDetails(invoiceId,con));				
			}
			
			if(challanId!=null)
			{
				outputMap.put("challanDetails", lObjConfigDao.getChallanInDetails(challanId,con));				
			}
			
			
			outputMap.put("itemList", lObjConfigDao.getItemMasterForGenerateInvoice(outputMap,con));
			outputMap.put("todaysDate", lObjConfigDao.getDateFromDB(con));
			outputMap.put("tentativeSerialNo", lObjConfigDao.getTentativeSequenceNo(appId,"trn_purchase_invoice_register",con));
			
			outputMap.put("vendorList", lObjConfigDao.getClientMaster(outputMap,con));
			outputMap.put("listwarehouse", lObjConfigDao.getListOfWareHouse(outputMap, con));
			
			
			
			rs.setViewName("../GeneratePI.jsp");	
			rs.setReturnObject(outputMap);
			
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	
	
	public CustomResultObject showGenerateChallanOut(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
		
		try
		{			
			
			String invoiceId=request.getParameter("invoice_id");
			
			
			String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
			
			String firmId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("firm_id");
			
			
			outputMap.put("firm_id", firmId);
			outputMap.put("app_id", appId);
						
			
			if(invoiceId!=null)
			{
				outputMap.put("invoiceDetails", lObjConfigDao.getInvoiceDetails(invoiceId,con));				
			}
			
			outputMap.put("itemList", lObjConfigDao.getItemMasterForGenerateInvoice(outputMap,con));
			outputMap.put("todaysDate", lObjConfigDao.getDateFromDB(con));
			outputMap.put("tentativeSerialNo", lObjConfigDao.getTentativeSequenceNo(appId,"trn_challan_out",con));
			
			outputMap.put("clientList", lObjConfigDao.getClientMaster(outputMap,con));
			outputMap.put("listwarehouse", lObjConfigDao.getListOfWareHouse(outputMap, con));
			
			
			
			rs.setViewName("../GenerateChallanOut.jsp");	
			rs.setReturnObject(outputMap);
			
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	public CustomResultObject showGenerateChallanIn(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
		
		try
		{			
			
			String invoiceId=request.getParameter("invoice_id");
			
			
			String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
			
			String firmId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("firm_id");
			
			
			outputMap.put("firm_id", firmId);
			outputMap.put("app_id", appId);
						
			
			if(invoiceId!=null)
			{
				outputMap.put("invoiceDetails", lObjConfigDao.getInvoiceDetails(invoiceId,con));				
			}
			
			outputMap.put("itemList", lObjConfigDao.getItemMasterForGenerateInvoice(outputMap,con));
			outputMap.put("todaysDate", lObjConfigDao.getDateFromDB(con));
			outputMap.put("tentativeSerialNo", lObjConfigDao.getTentativeSequenceNo(appId,"trn_challan_in",con));
			
			outputMap.put("vendorList", lObjConfigDao.getClientMaster(outputMap,con));
			outputMap.put("listwarehouse", lObjConfigDao.getListOfWareHouse(outputMap, con));
			
			
			
			rs.setViewName("../GenerateChallanIn.jsp");	
			rs.setReturnObject(outputMap);
			
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	
	
	
	public CustomResultObject showAddCategory(HttpServletRequest request,Connection connections)
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
		
		long categoryId=request.getParameter("categoryId")==null?0L:Long.parseLong(request.getParameter("categoryId"));
		outputMap.put("category_id", categoryId);
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);
		try
		{	
			if(categoryId!=0) {			outputMap.put("categoryDetails", lObjConfigDao.getCategoryDetails(outputMap ,connections));} 
			rs.setViewName("../AddCategory.jsp");	
			rs.setReturnObject(outputMap);		
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	public CustomResultObject showInternalTransfer(HttpServletRequest request,Connection connections)
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
		
		long categoryId=request.getParameter("categoryId")==null?0L:Long.parseLong(request.getParameter("categoryId"));
		outputMap.put("category_id", categoryId);
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);
		try
		{	
			outputMap.put("listfirmData", lObjConfigDao.getfirmMaster(outputMap,connections)); 
			rs.setViewName("../InternalTransfer.jsp");	
			rs.setReturnObject(outputMap);		
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	public CustomResultObject showInternalJournalsEntry(HttpServletRequest request,Connection connections)
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
		
		long categoryId=request.getParameter("categoryId")==null?0L:Long.parseLong(request.getParameter("categoryId"));
		outputMap.put("category_id", categoryId);
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);
		try
		{	
			outputMap.put("listfirmData", lObjConfigDao.getfirmMaster(outputMap,connections)); 
			rs.setViewName("../InternalJournalTransfer.jsp");	
			rs.setReturnObject(outputMap);		
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	
	
	
	
	
	
	
	public CustomResultObject saveJournal(HttpServletRequest request,Connection con) throws Exception
	{
		CustomResultObject rs=new CustomResultObject();		
		rs.setAjaxData(saveJournalitem(request,con).get("returnMessage").toString());	
		return rs;
	}
	
	
	public HashMap<String, Object> saveJournalitem(HttpServletRequest request,Connection con) throws Exception
	{
		Enumeration<String> params = request.getParameterNames(); 
		HashMap<String, Object> hm=new HashMap<>();
		List<HashMap<String, Object>> itemListRequired=new ArrayList<>();
		while(params.hasMoreElements())
		{
		 String paramName = params.nextElement();		 
		 if(paramName.equals("itemDetails")) 
		 {
			 String[] itemsList=request.getParameter(paramName).split("\\|");
			 for(String item:itemsList)
			 {
				 String[] itemDetails =item.split("~");
				 HashMap<String, Object> itemDetailsMap=new HashMap<>();
				 itemDetailsMap.put("job_sheet_no",itemDetails[0]);
				 itemDetailsMap.put("amount",itemDetails[1]);			 
				 itemListRequired.add(itemDetailsMap);				 
				 //ID, QTY, RATE,CustomRate
			 }
			 hm.put("itemDetails", itemListRequired);
			 continue;
		 }		 
		 hm.put(paramName, request.getParameter(paramName));
		 
		}
		
		
		
		String userId=request.getParameter("user_id");
		String firmId=request.getParameter("firm_id");
		String appId=request.getParameter("appId");		
		String hdnPreviousInvoiceId=request.getParameter("hdnPreviousInvoiceId");
		request.setAttribute("invoiceId", hdnPreviousInvoiceId);
		if(hdnPreviousInvoiceId!=null && !hdnPreviousInvoiceId.equals("") && !hdnPreviousInvoiceId.equals("0") ) {deleteInvoice(request, con);}
		
		if(appId==null || appId.equals(""))
			{
				appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
			}
		hm.put("app_id", appId);
				
		if(userId==null ||userId.equals(""))
		{
			userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
			firmId= ((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("firm_id");
		}
		
		hm.put("user_id", userId);
		hm.put("firm_id", firmId);	
		
		try
		{	
			
		
			
			HashMap<String, Object>  returnMap=lObjConfigDao.saveJournal(hm, con);
					hm.put("invoice_id", returnMap.get("invoice_id"));
			
					
			
			
			hm.put("returnMessage", returnMap.get("journal_no")+"~"+returnMap.get("journal_id"));
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
			throw e;
		}		
		return hm;
	}
	
	
	
	public CustomResultObject showAddWareHouse(HttpServletRequest request,Connection connections)
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
		
		long wareHouseId=request.getParameter("wareHouseId")==null?0L:Long.parseLong(request.getParameter("wareHouseId"));
		outputMap.put("wareHouseId", wareHouseId);
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);
		try
		{	
			if(wareHouseId!=0) {			outputMap.put("wareHouseDetails", lObjConfigDao.getWareHouseDetails(outputMap ,connections));} 
			rs.setViewName("../AddWareHouse.jsp");	
			rs.setReturnObject(outputMap);		
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	public CustomResultObject showAddBrand(HttpServletRequest request,Connection connections)
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
		
		long categoryId=request.getParameter("brandId")==null?0L:Long.parseLong(request.getParameter("brandId"));
		outputMap.put("brand_id", categoryId);
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);
		try
		{	
			if(categoryId!=0) {			outputMap.put("brandDetails", lObjConfigDao.getBrandDetail(outputMap ,connections));} 
			rs.setViewName("../AddBrand.jsp");	
			rs.setReturnObject(outputMap);		
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	public CustomResultObject showAddSBU(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
		
		long categoryId=request.getParameter("sbuId")==null?0L:Long.parseLong(request.getParameter("sbuId"));
		outputMap.put("sbu_id", categoryId);
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);
		try
		{	
			if(categoryId!=0) {			outputMap.put("sbuDetails", lObjConfigDao.getsbuDetail(outputMap ,con));}
			outputMap.put("EmployeeList", lObjConfigDao.getEmployeeMaster(outputMap, con));
			rs.setViewName("../AddSBU.jsp");	
			rs.setReturnObject(outputMap);		
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	public CustomResultObject showAddBank(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
		
		long categoryId=request.getParameter("bankId")==null?0L:Long.parseLong(request.getParameter("bankId"));
		outputMap.put("bank_id", categoryId);
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);
		try
		{	
			if(categoryId!=0) {			outputMap.put("BankDetails", lObjConfigDao.getBankDetail(outputMap ,con));}
			
			rs.setViewName("../AddBank.jsp");	
			rs.setReturnObject(outputMap);		
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	
	
	
	public CustomResultObject showConfigureTables(HttpServletRequest request,Connection conWithF)
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
		
		long noOftables=request.getParameter("noOfTables")==null?0L:Long.parseLong(request.getParameter("noOfTables"));		
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);
		try
		{	
			rs.setViewName("../ConfigureTables.jsp");	
			rs.setReturnObject(outputMap);		
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	public CustomResultObject showTableOrders(HttpServletRequest request,Connection conWithF)
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
		
		long tableId=request.getParameter("table_id")==null?0L:Long.parseLong(request.getParameter("table_id"));		
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);
		try
		{	
			outputMap.put("itemList", lObjConfigDao.getServiceMaster(outputMap,conWithF));
			List<LinkedHashMap<String, Object>> orderDetails=lObjConfigDao.getOrderDetailsForTable(tableId,conWithF);
			int i=1;
			for(LinkedHashMap<String, Object> hm:orderDetails)
			{
				hm.put("SrNo", i++);
			}
			outputMap.put("orderDetails", orderDetails);
			rs.setViewName("../AddOrder.jsp");	
			rs.setReturnObject(outputMap);		
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	
	
	
	public CustomResultObject showTables(HttpServletRequest request,Connection conWithF)
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();	
				
		
		int firmId=Integer.parseInt(((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("firm_id"));
		
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);
		try
		{	
			
			List<LinkedHashMap<String, Object>> lst= lObjConfigDao.getTableStatus(firmId,conWithF);
			outputMap.put("ListOfTables", lst);
			rs.setViewName("../Tables.jsp");	
			rs.setReturnObject(outputMap);		
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	public CustomResultObject showPendingOrders(HttpServletRequest request,Connection conWithF)
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();	
				
		
		int firmId=Integer.parseInt(((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("firm_id"));
		
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);
		try
		{	
			
			List<LinkedHashMap<String, Object>> lst= lObjConfigDao.getPendingOrders(appId,firmId,conWithF);
			int i=1;
			for(LinkedHashMap<String, Object> hm :lst) 
			{
				hm.put("SrNo", i++);
			}
			outputMap.put("pendingitems", lst);
			rs.setViewName("../PendingOrders.jsp");	
			rs.setReturnObject(outputMap);		
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	
	
	
	public CustomResultObject showUserRoleMapping(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
		
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);
		try
		{	
			outputMap.put("userList", lObjConfigDao.getEmployeeMaster(outputMap,con));
			outputMap.put("roleList", apptypes.get("Master"));
			rs.setViewName("../UserRoleMapping.jsp");	
			rs.setReturnObject(outputMap);		
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	public CustomResultObject showClientServicesMapping(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
		
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);		
		try
		{	
			outputMap.put("clientList", lObjConfigDao.getClientMaster(outputMap,con));
			
			
			
			
			
			
			outputMap.put("serviceList", lObjConfigDao.getServiceMaster(outputMap,con));
			rs.setViewName("../ClientServicesMapping.jsp");	
			rs.setReturnObject(outputMap);		
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	
	
	public CustomResultObject getRoleDetailsForthisUser(HttpServletRequest request, Connection con) {
		CustomResultObject rs = new CustomResultObject();
		long userId = Integer.parseInt(request.getParameter("userId"));
		HashMap<String, Object> hm = null;
		try {
			List<LinkedHashMap<String, Object>> lstUserRoleDetails = lObjConfigDao.getUserRoleDetails(userId, con);
			List<LinkedHashMap<String, Object>> lstUserRoleDetailsNew = new ArrayList<>();
			LinkedHashMap<Long, Role> roleMaster=apptypes.get("Master");

			for(LinkedHashMap<String, Object> lm:lstUserRoleDetails)
			{
				Role realRole=roleMaster.get(Long.valueOf(lm.get("role_id").toString()));
				lm.put("role_name", realRole.getRoleName());
				lstUserRoleDetailsNew.add(lm);
				
			}
			
			hm = new HashMap<>();
			hm.put("lstUserRoleDetails", lstUserRoleDetailsNew);
			List<String> roles = new ArrayList<>();
			
			for (LinkedHashMap<String, Object> mappedRole : lstUserRoleDetails) {
				roles.add(mappedRole.get("role_id").toString());
			}

			hm.put("lstElements", getElementsNewLogic(roles, CommonFunctions.elements, CommonFunctions.roles));
			rs.setAjaxData(mapper.writeValueAsString(hm));
		} catch (Exception e) {
			writeErrorToDB(e);
			rs.setHasError(true);
		}
		return rs;
	}
	
	public CustomResultObject getServiceMappingForthisClient(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		long clientId=request.getParameter("clientId").equals("")?-1:Integer.parseInt(request.getParameter("clientId"));
		
		HashMap<String, Object> outputMap=new HashMap<>();				
		String returnAjaxString;
		try
		{			
			rs.setAjaxData(mapper.writeValueAsString(lObjConfigDao.getServiceMappingForThisClient(clientId, con)));				
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
			rs.setHasError(true);
		}		
		return rs;
	}
	
	
	
	
	
	
	public CustomResultObject showAddGroup(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
		
		long groupId=request.getParameter("groupId")==null?0L:Long.parseLong(request.getParameter("groupId"));
		
		try
		{	
			if(groupId!=0) {			outputMap.put("groupDetails", lObjConfigDao.getGroupDetails(groupId,con));} 
			rs.setViewName("../AddGroup.jsp");	
			rs.setReturnObject(outputMap);		
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	public CustomResultObject showAddExpenseHead(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
		
		long groupId=request.getParameter("expenseHeadId")==null?0L:Long.parseLong(request.getParameter("expenseHeadId"));
		
		try
		{	
			if(groupId!=0) {			outputMap.put("expenseHeadDetails", lObjConfigDao.getExpenseHeadDetails(groupId,con));} 
			rs.setViewName("../AddExpenseHead.jsp");	
			rs.setReturnObject(outputMap);		
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	
	
	
	
	public CustomResultObject showAddExpense(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
		
		long groupId=request.getParameter("expenseId")==null?0L:Long.parseLong(request.getParameter("expenseId"));
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);

		try
		{	
			String todaysDate=lObjConfigDao.getDateFromDB(con);
			outputMap.put("todaysDate", todaysDate);
			outputMap.put("distinctExpenseList", lObjConfigDao.getDistinctExpenseList(con,appId));
			String expenseDate=request.getParameter("expenseDate");
			
			if(expenseDate==null || expenseDate.equals(""))
			{
				outputMap.put("fromDate", todaysDate);
				outputMap.put("toDate", todaysDate);
			}
			else
			{
				outputMap.put("fromDate", expenseDate);
				outputMap.put("toDate", expenseDate);
				outputMap.put("todaysDate", expenseDate);
				
			}
			List<LinkedHashMap<String, Object>> expenseList=lObjConfigDao.getExpenseRegister(outputMap,con);
			
			outputMap.put("expenseList", expenseList);
			outputMap.put("totalAmount", getTotalAmountExpenditure(expenseList));
			
			
			if(groupId!=0) 
			{
				outputMap.put("expenseDetails", lObjConfigDao.getExpenseDetails(groupId,con));
				
			} 
			rs.setViewName("../AddExpense.jsp");	
			rs.setReturnObject(outputMap);		
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	private double getTotalAmountExpenditure(List<LinkedHashMap<String, Object>> expenseList) 
	{
		double ReqTotal=0;
		for(LinkedHashMap<String, Object> hm:expenseList)
		{
			ReqTotal+=Double.valueOf(hm.get("amount").toString());
		}
		return ReqTotal;
	}
	
	
	public CustomResultObject showAddRoutine(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
		
		long RoutineId=request.getParameter("RoutineId")==null?0L:Long.parseLong(request.getParameter("RoutineId"));
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);
		
		try
		{	
			if(RoutineId!=0) {			outputMap.put("routineDetails", lObjConfigDao.getRoutinepDetails(RoutineId,con));}
			outputMap.put("itemList", lObjConfigDao.getBankMaster(outputMap,con));
			rs.setViewName("../AddRoutine.jsp");	
			rs.setReturnObject(outputMap);		
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	
	public CustomResultObject showReturnScreen(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
		
		long detailId=request.getParameter("detailsId")==null?0L:Long.parseLong(request.getParameter("detailsId"));
		
		try
		{	
			if(detailId!=0) 
			{	
				outputMap.put("invoiceSubDetails", lObjConfigDao.getInvoiceSubDetails(detailId,con));
			} 
			rs.setViewName("../Returnitems.jsp");	
			rs.setReturnObject(outputMap);		
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	public HashMap<String, Object> showReturnScreenitem(HttpServletRequest request,Connection con) throws SQLException
	{
					
		HashMap<String, Object> outputMap=new HashMap<>();		
		long detailId=request.getParameter("detailsId")==null?0L:Long.parseLong(request.getParameter("detailsId"));		
		outputMap.put("invoiceSubDetails", lObjConfigDao.getInvoiceSubDetails(detailId,con));		
		return outputMap;
	}
	
	
	
	
	
	
	
	public CustomResultObject showConfigureLowStock(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
		
		long stockId=request.getParameter("stockId")==null?0L:Long.parseLong(request.getParameter("stockId"));
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);		
		try
		{
			outputMap.put("stock_id", stockId);
			outputMap.put("firmList", lObjConfigDao.getfirmMaster(outputMap,con));
			outputMap.put("stockDetails", lObjConfigDao.getStockDetailsbyId(outputMap,con));			
			rs.setViewName("../ConfigureLowStock.jsp");	
			rs.setReturnObject(outputMap);		
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	
	
	
	CommonFunctions cf=new CommonFunctions();
	private Object addressOfTheEstablishment;
	
	public CustomResultObject addService(HttpServletRequest request,Connection con) throws Exception
	{
		CustomResultObject rs=new CustomResultObject();	
		HashMap<String, Object> outputMap=new HashMap<>();	
			
		
		FileItemFactory itemFacroty=new DiskFileItemFactory();
		ServletFileUpload upload=new ServletFileUpload(itemFacroty);		
		//String webInfPath = cf.getPathForAttachments();
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+File.separator;		
		
		
		
		HashMap<String,Object> hm=new HashMap<>();
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		hm.put("app_id", appId);
		
		List<FileItem> toUpload=new ArrayList<FileItem>();
		
		if(ServletFileUpload.isMultipartContent(request))
		{
			List<FileItem> items=upload.parseRequest(request);
			for(FileItem item:items)
			{	
				
				if (item.isFormField()) 
				{
			        hm.put(item.getFieldName(), item.getString());			        
			    }
				else if(item.getSize()>0)
				{
					toUpload.add(item);
				}
			}			
		}
		
		try
		{			
			
			HashMap<String, String> LoginDetails=(HashMap<String, String>) request.getSession().getAttribute("userdetails");
			String userid=LoginDetails.get("user_id");
			hm.put("userId",userid);
			
			
			
			
			
			if(hm.get("servicename").toString().contains("\"") || hm.get("servicename").toString().contains("/"))
			{
				rs.setReturnObject(outputMap);		
				rs.setAjaxData("<script>alert('Special Characters \" / not allowed');window.history.back();</script>");
				return rs;
			}
			
			
			
			
			
			long serviceId=0;
			if(hm.get("hdnServiceId").equals(""))
			{
				serviceId=lObjConfigDao.saveService(hm,con);
				hm.put("hdnServiceId", serviceId);				
			}
			else
			{
				lObjConfigDao.updateService(hm,con);
				serviceId=Long.parseLong(hm.get("hdnServiceId").toString());
			}
			
			
			
			
			// code to add image of items
			if(!toUpload.isEmpty())
			{
				for(FileItem f:toUpload)
				{
					f.write(new File(DestinationPath+f.getName()));					
					long attachmentId=cf.uploadFileToDBDual(DestinationPath+f.getName(), con, "Image", serviceId);					
					Files.copy(Paths.get(DestinationPath+f.getName()), Paths.get(DestinationPath+attachmentId+f.getName()),StandardCopyOption.REPLACE_EXISTING);
				}
			}
			
			rs.setReturnObject(outputMap);		
			rs.setAjaxData("<script>alert('Service Updated Succesfully');window.location='?a=showServiceMaster';</script>");			
										
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	public CustomResultObject addCategory(HttpServletRequest request,Connection con) throws Exception
	{
		CustomResultObject rs=new CustomResultObject();	
		HashMap<String, Object> outputMap=new HashMap<>();	
				
		FileItemFactory itemFacroty=new DiskFileItemFactory();
		ServletFileUpload upload=new ServletFileUpload(itemFacroty);		
		//String webInfPath = cf.getPathForAttachments();
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+File.separator;
		
		HashMap<String,Object> hm=new HashMap<>();
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		hm.put("app_id", appId);
		
		
		List<String> quantities =new ArrayList<>();
		List<FileItem> toUpload=new ArrayList<FileItem>();
		if(ServletFileUpload.isMultipartContent(request))
		{
			List<FileItem> items=upload.parseRequest(request);
			for(FileItem item:items)
			{		
				
				if (item.isFormField()) 
				{
					hm.put(item.getFieldName(), item.getString());
			    }
				else
				{
					toUpload.add(item);
				}
			}			
		}		
		String categoryName= hm.get("categoryName").toString();
		hm.put("category_id", categoryName);
		
		long categoryId=hm.get("hdnCategoryId").equals("")?0l:Long.parseLong(hm.get("hdnCategoryId").toString()); 
		try
		{			
									
			
			
			if(categoryId==0)
			{
				categoryId=lObjConfigDao.addCategory(con, hm);
			}
			else
			{
				lObjConfigDao.updateCategory(categoryId, con, categoryName);
			}
			
			if(!toUpload.isEmpty() && toUpload.get(0).getSize()>0)
			{
				
				toUpload.get(0).write(new File(DestinationPath+toUpload.get(0).getName()));	
				long attachmentId=cf.uploadFileToDBDual(DestinationPath+toUpload.get(0).getName(), con, "Category",categoryId);
				Files.copy(Paths.get(DestinationPath+toUpload.get(0).getName()), Paths.get(DestinationPath+attachmentId+toUpload.get(0).getName()),StandardCopyOption.REPLACE_EXISTING);
				
				
			}

			rs.setReturnObject(outputMap);
			
			
			rs.setAjaxData("<script>window.location='?a=showCategoryMasterNew'</script>");
			
										
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	
	
	
	public CustomResultObject addWareHouse(HttpServletRequest request,Connection con) throws Exception
	{
		CustomResultObject rs=new CustomResultObject();	
		HashMap<String, Object> outputMap=new HashMap<>();	
				
		FileItemFactory itemFacroty=new DiskFileItemFactory();
		ServletFileUpload upload=new ServletFileUpload(itemFacroty);		
		//String webInfPath = cf.getPathForAttachments();
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+File.separator;
		
		HashMap<String,Object> hm=new HashMap<>();
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		hm.put("app_id", appId);
		
		
		List<String> quantities =new ArrayList<>();
		List<FileItem> toUpload=new ArrayList<FileItem>();
		if(ServletFileUpload.isMultipartContent(request))
		{
			List<FileItem> items=upload.parseRequest(request);
			for(FileItem item:items)
			{		
				
				if (item.isFormField()) 
				{
					hm.put(item.getFieldName(), item.getString());
			    }
				else
				{
					toUpload.add(item);
				}
			}			
		}		
		String ware_house_name= hm.get("ware_house_name").toString();
		hm.put("ware_house_name", ware_house_name);
		
		long hdnWareHouseId=hm.get("hdnWareHouseId").equals("")?0l:Long.parseLong(hm.get("hdnWareHouseId").toString()); 
		try
		{			
									
			
			
			if(hdnWareHouseId==0)
			{
				hdnWareHouseId=lObjConfigDao.addWareHouse(con, hm);
			}
			else
			{
				lObjConfigDao.updateWareHouse(hdnWareHouseId, con, ware_house_name);
			}
			
			if(!toUpload.isEmpty() && toUpload.get(0).getSize()>0)
			{
				
				toUpload.get(0).write(new File(DestinationPath+toUpload.get(0).getName()));	
				long attachmentId=cf.uploadFileToDBDual(DestinationPath+toUpload.get(0).getName(), con, "WareHouse",hdnWareHouseId);
				Files.copy(Paths.get(DestinationPath+toUpload.get(0).getName()), Paths.get(DestinationPath+attachmentId+toUpload.get(0).getName()),StandardCopyOption.REPLACE_EXISTING);
				
				
			}

			rs.setReturnObject(outputMap);
			
			
			rs.setAjaxData("<script>window.location='?a=showWareHouseMaster'</script>");
			
										
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	
	
	public CustomResultObject addBrand(HttpServletRequest request,Connection con) throws Exception
	{
		CustomResultObject rs=new CustomResultObject();	
		HashMap<String, Object> outputMap=new HashMap<>();	
				
		FileItemFactory itemFacroty=new DiskFileItemFactory();
		ServletFileUpload upload=new ServletFileUpload(itemFacroty);		
		//String webInfPath = cf.getPathForAttachments();
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+File.separator;
		
		HashMap<String,Object> hm=new HashMap<>();
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		hm.put("app_id", appId);
		hm.put("user_id", userId);
		
		
		
		List<String> quantities =new ArrayList<>();
		List<FileItem> toUpload=new ArrayList<FileItem>();
		if(ServletFileUpload.isMultipartContent(request))
		{
			List<FileItem> items=upload.parseRequest(request);
			for(FileItem item:items)
			{		
				
				if (item.isFormField()) 
				{
					hm.put(item.getFieldName(), item.getString());
			    }
				else
				{
					toUpload.add(item);
				}
			}			
		}		
		String brandName= hm.get("brandName").toString();
		hm.put("brand_id", brandName);
		
		long categoryId=hm.get("hdnBrandId").equals("")?0l:Long.parseLong(hm.get("hdnBrandId").toString()); 
		try
		{			
									
			
			
			if(categoryId==0)
			{
				categoryId=lObjConfigDao.addBrand(con, hm);
			}
			else
			{
				lObjConfigDao.updateBrand(categoryId, con, brandName);
			}
			
			if(!toUpload.isEmpty() && toUpload.get(0).getSize()>0)
			{
				
				toUpload.get(0).write(new File(DestinationPath+toUpload.get(0).getName()));	
				long attachmentId=cf.uploadFileToDBDual(DestinationPath+toUpload.get(0).getName(), con, "Brand",categoryId);
				Files.copy(Paths.get(DestinationPath+toUpload.get(0).getName()), Paths.get(DestinationPath+attachmentId+toUpload.get(0).getName()),StandardCopyOption.REPLACE_EXISTING);
				
				
			}

			rs.setReturnObject(outputMap);
			
			
			rs.setAjaxData("<script>window.location='?a=showBrandMaster'</script>");
			
										
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	
	public CustomResultObject addSBU(HttpServletRequest request,Connection con) throws Exception
	{
		CustomResultObject rs=new CustomResultObject();	
		HashMap<String, Object> outputMap=new HashMap<>();	
				
		FileItemFactory itemFacroty=new DiskFileItemFactory();
		ServletFileUpload upload=new ServletFileUpload(itemFacroty);		
		//String webInfPath = cf.getPathForAttachments();
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+File.separator;
		
		HashMap<String,Object> hm=new HashMap<>();
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		hm.put("app_id", appId);
		hm.put("user_id", userId);
		
		
		
		List<String> quantities =new ArrayList<>();
		List<FileItem> toUpload=new ArrayList<FileItem>();
		if(ServletFileUpload.isMultipartContent(request))
		{
			List<FileItem> items=upload.parseRequest(request);
			for(FileItem item:items)
			{		
				
				if (item.isFormField()) 
				{
					hm.put(item.getFieldName(), item.getString());
			    }
				else
				{
					toUpload.add(item);
				}
			}			
		}		
		String brandName= hm.get("sbuName").toString();
		hm.put("sbu_id", brandName);
		
		long categoryId=hm.get("hdnSBUId").equals("")?0l:Long.parseLong(hm.get("hdnSBUId").toString()); 
		try
		{			
									
			
			
			if(categoryId==0)
			{
				categoryId=lObjConfigDao.addSBU(con, hm);
			}
			else
			{
				lObjConfigDao.updateSBU(categoryId, con, brandName);
			}
			
			if(!toUpload.isEmpty() && toUpload.get(0).getSize()>0)
			{
				
				toUpload.get(0).write(new File(DestinationPath+toUpload.get(0).getName()));	
				long attachmentId=cf.uploadFileToDBDual(DestinationPath+toUpload.get(0).getName(), con, "SBU",categoryId);
				Files.copy(Paths.get(DestinationPath+toUpload.get(0).getName()), Paths.get(DestinationPath+attachmentId+toUpload.get(0).getName()),StandardCopyOption.REPLACE_EXISTING);
				
				
			}

			rs.setReturnObject(outputMap);
			
			
			rs.setAjaxData("<script>window.location='?a=showSBUMaster'</script>");
			
										
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	

	public CustomResultObject addBank(HttpServletRequest request,Connection con) throws Exception
	{
		CustomResultObject rs=new CustomResultObject();	
		HashMap<String, Object> outputMap=new HashMap<>();	
				
		FileItemFactory itemFacroty=new DiskFileItemFactory();
		ServletFileUpload upload=new ServletFileUpload(itemFacroty);		
		//String webInfPath = cf.getPathForAttachments();
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+File.separator;
		
		HashMap<String,Object> hm=new HashMap<>();
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		hm.put("app_id", appId);
		hm.put("user_id", userId);
		
		
		
		List<String> quantities =new ArrayList<>();
		List<FileItem> toUpload=new ArrayList<FileItem>();
		if(ServletFileUpload.isMultipartContent(request))
		{
			List<FileItem> items=upload.parseRequest(request);
			for(FileItem item:items)
			{		
				
				if (item.isFormField()) 
				{
					hm.put(item.getFieldName(), item.getString());
			    }
				else
				{
					toUpload.add(item);
				}
			}			
		}		
		
		
		long bankId=hm.get("hdnBankId").equals("")?0l:Long.parseLong(hm.get("hdnBankId").toString()); 
		try
		{			
									
			
			
			if(bankId==0)
			{
				bankId=lObjConfigDao.addBank(con, hm);
			}
			else
			{
				lObjConfigDao.updateBank(hm,con);
			}
			
			

			rs.setReturnObject(outputMap);
			
			
			rs.setAjaxData("<script>window.location='?a=showBankMaster'</script>");
			
										
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	
	
	
	
	public CustomResultObject addExpense(HttpServletRequest request,Connection con) throws Exception
	{
		CustomResultObject rs=new CustomResultObject();	
		HashMap<String, Object> outputMap=new HashMap<>();	
				
		FileItemFactory itemFacroty=new DiskFileItemFactory();
		ServletFileUpload upload=new ServletFileUpload(itemFacroty);		
		//String webInfPath = cf.getPathForAttachments();
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+File.separator;		
		HashMap<String,Object> hm=new HashMap<>();
		String userId= ((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		hm.put("user_id",userId);
		
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		hm.put("app_id", appId);
		
		List<String> quantities =new ArrayList<>();
		List<FileItem> toUpload=new ArrayList<FileItem>();
		if(ServletFileUpload.isMultipartContent(request))
		{
			List<FileItem> items=upload.parseRequest(request);
			for(FileItem item:items)
			{		
				
				if (item.isFormField()) 
				{
					hm.put(item.getFieldName(), item.getString());
			    }
				else
				{
					toUpload.add(item);
				}
			}			
		}		
		
		long hdnExpenseId=hm.get("hdnExpenseId").equals("")?0l:Long.parseLong(hm.get("hdnExpenseId").toString()); 
		try
		{			
									
			hm.put("hdnExpenseId", hdnExpenseId);
			
			if(hdnExpenseId==0)
			{
				hdnExpenseId=lObjConfigDao.addExpense(con, hm);
			}
			else
			{
				lObjConfigDao.updateExpense( con, hm);
			}
			
			

			rs.setReturnObject(outputMap);
			
			
			rs.setAjaxData("<script>alert('Updated succesfully');window.location='?a=showAddExpense&expenseDate="+hm.get("txtdate")+"'</script>");
			
										
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	public CustomResultObject addGroup(HttpServletRequest request,Connection con) throws Exception
	{
		CustomResultObject rs=new CustomResultObject();	
		HashMap<String, Object> outputMap=new HashMap<>();	
				
		FileItemFactory itemFacroty=new DiskFileItemFactory();
		ServletFileUpload upload=new ServletFileUpload(itemFacroty);		
		//String webInfPath = cf.getPathForAttachments();
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+File.separator;		
		
		
		HashMap<String,Object> hm=new HashMap<>();
		List<String> quantities =new ArrayList<>();
		List<FileItem> toUpload=new ArrayList<FileItem>();
		if(ServletFileUpload.isMultipartContent(request))
		{
			List<FileItem> items=upload.parseRequest(request);
			for(FileItem item:items)
			{		
				
				if (item.isFormField()) 
				{
					hm.put(item.getFieldName(), item.getString());
			    }
				else
				{
					toUpload.add(item);
				}
			}		
		}		
		String group_name= hm.get("group_name").toString();
		long groupId=hm.get("hdnGroupId").equals("")?0l:Long.parseLong(hm.get("hdnGroupId").toString());
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		try
		{			
									
			
			
			if(groupId==0)
			{
				groupId=lObjConfigDao.addGroup(con, group_name,appId);
			}
			else
			{
				lObjConfigDao.updateGroup(groupId, con, group_name);
			}
			
			

			rs.setReturnObject(outputMap);
			
			
			rs.setAjaxData("<script>window.location='?a=showGroupMaster'</script>");
			
										
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	public CustomResultObject addExpenseHead(HttpServletRequest request,Connection con) throws Exception
	{
		CustomResultObject rs=new CustomResultObject();	
		HashMap<String, Object> outputMap=new HashMap<>();	
				
		FileItemFactory itemFacroty=new DiskFileItemFactory();
		ServletFileUpload upload=new ServletFileUpload(itemFacroty);		
		//String webInfPath = cf.getPathForAttachments();
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+File.separator;		
		
		
		HashMap<String,Object> hm=new HashMap<>();
		List<String> quantities =new ArrayList<>();
		List<FileItem> toUpload=new ArrayList<FileItem>();
		if(ServletFileUpload.isMultipartContent(request))
		{
			List<FileItem> items=upload.parseRequest(request);
			for(FileItem item:items)
			{		
				
				if (item.isFormField()) 
				{
					hm.put(item.getFieldName(), item.getString());
			    }
				else
				{
					toUpload.add(item);
				}
			}			
		}		
		String group_name= hm.get("expense_name").toString();
		long groupId=hm.get("hdnExpenseHeadId").equals("")?0l:Long.parseLong(hm.get("hdnExpenseHeadId").toString());
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		try
		{			
									
			
			
			if(groupId==0)
			{
				groupId=lObjConfigDao.addExpenseHead(con, group_name,appId);
			}
			else
			{
				lObjConfigDao.updateExpenseHead(groupId, con, group_name);
			}
			
			

			rs.setReturnObject(outputMap);
			
			
			rs.setAjaxData("<script>window.location='?a=showExpenseHeads'</script>");
			
										
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	public CustomResultObject addRoutine(HttpServletRequest request,Connection con) throws Exception
	{
		CustomResultObject rs=new CustomResultObject();	
		HashMap<String, Object> outputMap=new HashMap<>();	
				
		FileItemFactory itemFacroty=new DiskFileItemFactory();
		ServletFileUpload upload=new ServletFileUpload(itemFacroty);		
		//String webInfPath = cf.getPathForAttachments();
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+File.separator;		
		
		
		HashMap<String,Object> hm=new HashMap<>();
		List<String> quantities =new ArrayList<>();
		List<FileItem> toUpload=new ArrayList<FileItem>();
		if(ServletFileUpload.isMultipartContent(request))
		{
			List<FileItem> items=upload.parseRequest(request);
			for(FileItem item:items)
			{		
				
				if (item.isFormField()) 
				{
					hm.put(item.getFieldName(), item.getString());
			    }
				else
				{
					toUpload.add(item);
				}
			}			
		}		
		
		String userId= ((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		hm.put("user_id",userId);
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		hm.put("app_id", appId);
		long routineId=hm.get("hdnroutineid").equals("")?0l:Long.parseLong(hm.get("hdnroutineid").toString()); 
		try
		{			
									
			
			
			if(routineId==0)
			{
				routineId=lObjConfigDao.addRoutine(con,hm);
			}
			else
			{
				lObjConfigDao.updateRoutine(con,hm);
			}
			
			

			rs.setReturnObject(outputMap);
			
			
			rs.setAjaxData("<script>window.location='?a=showCustomerDeliveryRoutine'</script>");
			
										
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	
	
	
	public CustomResultObject addStock(HttpServletRequest request,Connection con) throws Exception
	{
		CustomResultObject rs=new CustomResultObject();	
		HashMap<String, Object> outputMap=new HashMap<>();	
				
		
		try
		{								
			String actiontype=request.getParameter("action");
			String itemDetails=request.getParameter("itemDetails");
			String outerRemarks=request.getParameter("outerremarks");
			Long wareHouseId=Long.valueOf(request.getParameter("ware_house_id"));
			String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
			outputMap.put("app_id", appId);
			outputMap.put("ware_house_id", wareHouseId);
			
			long firm_id=Long.valueOf(((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("firm_id"));
			String userId= ((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
			outputMap.put("user_id",userId);
			outputMap.put("drpfirmId",firm_id);
			outputMap.put("outerRemarks", outerRemarks);
			
			String items[]=itemDetails.split("\\|");
			
			outputMap.put("userId", userId);
			outputMap.put("firmId", firm_id);
			
			String StockModificationType=actiontype.equals("Remove")?"Damage":"StockIn";			
			outputMap.put("type", StockModificationType);	
			
			long stockModificationId= lObjConfigDao.addStockModification(outputMap, con);
			
			for(String item:items)
			{
				String[] itemVo=item.split("~");
				outputMap.put("drpitems", itemVo[0]);
				outputMap.put("qty", itemVo[1]);
				outputMap.put("remarks", itemVo[3]);
				outputMap.put("price", itemVo[4]);
				
				
				
				long item_id=Long.parseLong(outputMap.get("drpitems").toString());
				outputMap.put("type", "StockIn");
				if(actiontype.equals("Remove"))
				{
					outputMap.put("qty", Double.parseDouble(outputMap.get("qty").toString()) *-1);
					outputMap.put("type", "Damage");
				}			
				if(lObjConfigDao.checkifStockAlreadyExist(firm_id,item_id,wareHouseId, con).equals("0"))
				{
					lObjConfigDao.addStockMaster(outputMap, con);
				}		
							
				lObjConfigDao.addStockRegister(outputMap, con);
				outputMap.put("stock_id", lObjConfigDao.checkifStockAlreadyExist(firm_id, item_id,wareHouseId, con));
				
				
				double qtyAvailable= Double.parseDouble(lObjConfigDao.getStockDetailsbyId(outputMap, con).get("qty_available"));
				double averagePrice= Double.parseDouble(lObjConfigDao.getStockDetailsbyId(outputMap, con).get("average_price"));			
				
				double newQyt=Double.parseDouble(itemVo[1]);
				double newPrice=Double.parseDouble(itemVo[4]);
				
				double newAveragePrice=0;
				
				if(actiontype.equals("Add")) 
				{
					newAveragePrice=((qtyAvailable*averagePrice) + (newQyt*newPrice)) / (qtyAvailable + newQyt);
				}
				
				if(actiontype.equals("Remove")) 
				{
					if(qtyAvailable==newQyt)
					{
						newAveragePrice=0;
					}
					else
					{
						newAveragePrice=((qtyAvailable*averagePrice) - (newQyt*newPrice)) / (qtyAvailable - newQyt);
					}
				}
				
				
				
						
				outputMap.put("average_price", newAveragePrice); 
				
				lObjConfigDao.updateStockMaster(outputMap,con);
				
				outputMap.put("stockModificationId",stockModificationId);
				outputMap.put("itemId",itemVo[0]);
				outputMap.put("currentStock",itemVo[2]);
				outputMap.put("qty",itemVo[1]);
				
				lObjConfigDao.addStockModificationAddRemove(outputMap, con);
				
			}
			
			
			
			
			
			
			
			
			rs.setReturnObject(outputMap);			
			rs.setAjaxData("Stock Updated Succesfully~"+stockModificationId);
			
										
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				throw e;
		}		
		return rs;
	}
	
	
	public CustomResultObject deleteService(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		long serviceId= Integer.parseInt(request.getParameter("serviceId"));		
		HashMap<String, Object> outputMap=new HashMap<>();				
		String returnAjaxString;
		try
		{			
						
						
			rs.setAjaxData(lObjConfigDao.deleteService(serviceId, con));
			
							
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	public CustomResultObject markAsServed(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		long orderDetailId= Long.valueOf(request.getParameter("orderDetailsId"));		
		HashMap<String, Object> outputMap=new HashMap<>();				
		String returnAjaxString;
		try
		{			
						
						
			rs.setAjaxData(lObjConfigDao.markAsServed(orderDetailId, con));
			
							
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	public CustomResultObject cancelOrderDetail(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		long orderDetailId= Long.valueOf(request.getParameter("cancelOrderDetailId"));		
		HashMap<String, Object> outputMap=new HashMap<>();				
		String returnAjaxString;
		try
		{			
						
						
			rs.setAjaxData(lObjConfigDao.cancelOrderDetail(orderDetailId, con));
			
							
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	
	
	public CustomResultObject deletePayment(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		long paymentId= Integer.parseInt(request.getParameter("paymentId"));		
		HashMap<String, Object> outputMap=new HashMap<>();				
		String returnAjaxString;
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		
		try
		{
			rs.setAjaxData(lObjConfigDao.deletePayment(paymentId, userId,con));						
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	public CustomResultObject deleteJournals(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		long paymentId= Integer.parseInt(request.getParameter("journalid"));		
		HashMap<String, Object> outputMap=new HashMap<>();				
		String returnAjaxString;
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		
		try
		{
			rs.setAjaxData(lObjConfigDao.deleteJournal(paymentId, userId,con));						
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	
	
	
	public CustomResultObject deleteTransfer(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		long transferId= Integer.parseInt(request.getParameter("transferId"));		
		HashMap<String, Object> outputMap=new HashMap<>();				
		String returnAjaxString;
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		
		try
		{
			lObjConfigDao.deleteTransfer(transferId, userId,con);
			lObjConfigDao.deleteTransferMapping(transferId, con);			
			lObjConfigDao.deleteTransactionsAgainstTransfer(transferId, con);			
			rs.setAjaxData("Deleted Transfer");						
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	public CustomResultObject deleteJournalTransfer(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		long transferId= Integer.parseInt(request.getParameter("transferId"));		
		HashMap<String, Object> outputMap=new HashMap<>();				
		String returnAjaxString;
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		
		try
		{
			lObjConfigDao.deleteJournalTransfer(transferId, userId,con);						
			rs.setAjaxData("Deleted Transfer");						
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	
	
	
	
	
	public CustomResultObject deleteGroup(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		long groupId= Long.parseLong(request.getParameter("groupId"));		
		HashMap<String, Object> outputMap=new HashMap<>();				
		String returnAjaxString;
		try
		{			
						
						
			rs.setAjaxData(lObjConfigDao.deleteGroup(groupId, con));
			
							
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	
	
	
	
	public CustomResultObject deleteExpenseHeads(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		long expenseHeadId= Long.parseLong(request.getParameter("expenseHeadId"));		
		HashMap<String, Object> outputMap=new HashMap<>();				
		String returnAjaxString;
		try
		{			
						
						
			rs.setAjaxData(lObjConfigDao.deleteExpenseHeads(expenseHeadId, con));
			
							
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	
	
	
	public CustomResultObject deleteExpense(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		long expenseId= Long.parseLong(request.getParameter("expenseId"));		
		HashMap<String, Object> outputMap=new HashMap<>();
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		String returnAjaxString;
		try
		{			
						
						
			rs.setAjaxData(lObjConfigDao.deleteExpense(expenseId, userId,con));
			
							
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	public CustomResultObject deleteInvoice(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		String invoiceId=request.getParameter("invoiceId");
		String type=request.getParameter("type");
		
		
		long invoiceIdLong=Long.valueOf(invoiceId);
				
		HashMap<String, Object> outputMap=new HashMap<>();
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		String firmId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("firm_id");
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		
		
		outputMap.put("firm_id", firmId);
		outputMap.put("user_id", userId);
		outputMap.put("app_id", appId);
		
		String returnAjaxString;
		try
		{			
			
			
			LinkedHashMap<String, Object> dtls=null;
			List<LinkedHashMap<String, Object>> ListOfitemDetails=null;
			List<HashMap<String, Object>> itemDetailsListNegativeQuantity = new ArrayList<>();
			if(type.equals("P"))
			{
				lObjConfigDao.deleteInvoicePurchase(invoiceIdLong, userId,con);
				
				
			}
			else
			{
				lObjConfigDao.deleteInvoiceSales(invoiceIdLong, userId,con);
				
				
			}

			
			

			
			//lObjConfigDao.deleteReturnsAgainstInvoice(invoiceIdLong, userId,con);
			
			//List<LinkedHashMap<String, Object>>  itemDetails =(List<LinkedHashMap<String, Object>>) lObjConfigDao.getInvoiceDetails(invoiceId, con).get("listOfitems"); 
			//outputMap.put("itemDetails", itemDetails);
			//lObjConfigDao.addStockAgainstCorrection(outputMap, con);
			
			
			
			rs.setAjaxData("Invoice Deleted Succesfully");
			
							
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	
	public CustomResultObject deleteChallan(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		String invoiceId=request.getParameter("challanid");
		String type=request.getParameter("type");
		
		
		long invoiceIdLong=Long.valueOf(invoiceId);
				
		HashMap<String, Object> outputMap=new HashMap<>();
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		String firmId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("firm_id");
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		
		
		outputMap.put("firm_id", firmId);
		outputMap.put("user_id", userId);
		outputMap.put("app_id", appId);
		
		String returnAjaxString;
		try
		{		
			LinkedHashMap<String, Object> dtls=null;
			List<LinkedHashMap<String, Object>> ListOfitemDetails=null;
			List<HashMap<String, Object>> itemDetailsListNegativeQuantity = new ArrayList<>();
			if(type.equals("O"))
			{
				lObjConfigDao.deleteChallanOut(invoiceIdLong, userId,con);				
			}
			else
			{
				lObjConfigDao.deleteChallanIn(invoiceIdLong, userId,con);
				
			}

			
			

			
			
			rs.setAjaxData("Challan Deleted Succesfully");
			
							
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	

	public CustomResultObject deleteRoutine(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		long routineId= Long.parseLong(request.getParameter("RoutineId"));		
		HashMap<String, Object> outputMap=new HashMap<>();				
		String returnAjaxString;
		try
		{			
						
						
			rs.setAjaxData(lObjConfigDao.deleteRoutine(routineId, con));
			
							
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	
	public CustomResultObject saveSalesInvoice(HttpServletRequest request,Connection con) throws Exception
	{
		Enumeration<String> params = request.getParameterNames(); 
		HashMap<String, Object> hm=new HashMap<>();
		List<HashMap<String, Object>> itemListRequired=new ArrayList<>();
		CustomResultObject rs=new CustomResultObject();
		while(params.hasMoreElements())
		{
		 String paramName = params.nextElement();		 
		 if(paramName.equals("itemDetails")) 
		 {
			 String[] itemsList=request.getParameter(paramName).split("\\|");
			 for(String item:itemsList)
			 {
				 String[] itemDetails =item.split("~");
				 //ID,WAREHOUSEID,JOBSHEETNO, QTY, RATE,GSTAMOUNT,GSTPERCENT,itemAMOUNT
				 HashMap<String, Object> itemDetailsMap=new HashMap<>();
				 itemDetailsMap.put("item_id",itemDetails[0]);
				 itemDetailsMap.put("ware_house_id",itemDetails[1]);
				 itemDetailsMap.put("job_sheet_no",itemDetails[2]);
				 itemDetailsMap.put("qty",itemDetails[3]);
				 itemDetailsMap.put("rate",itemDetails[4]);
				 itemDetailsMap.put("gst_amount",itemDetails[5]);
				 itemDetailsMap.put("gst_percentage",itemDetails[6]);
				 itemDetailsMap.put("item_amount",itemDetails[7]);
				 itemDetailsMap.put("purchase_details_id",itemDetails[8]);
				 
				 itemListRequired.add(itemDetailsMap);				 
				 //ID, QTY, RATE,CustomRate
			 }
			 hm.put("itemDetails", itemListRequired);
			 continue;
		 }		 
		 hm.put(paramName, request.getParameter(paramName));
		 
		}
		
		
		
		String userId=request.getParameter("user_id");
		String firm_id=request.getParameter("firm_id");
		String appId=request.getParameter("appId");
		String challanId=request.getParameter("challanId");
		
		String hdnPreviousInvoiceId=request.getParameter("hdnPreviousInvoiceId");		
		if(hdnPreviousInvoiceId!=null && !hdnPreviousInvoiceId.equals("") && !hdnPreviousInvoiceId.equals("0") ) {deleteInvoice(request, con);}		
		if(appId==null || appId.equals(""))
			{
				appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
			}
		hm.put("app_id", appId);
		userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		firm_id= ((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("firm_id");
		hm.put("user_id", userId);
		hm.put("firm_id", firm_id);
		hm.put("challanId", challanId);
		hm.put("type", "S");
		
		
		
		String client_id=request.getParameter("client_id").equals("")?"0":request.getParameter("client_id");
		hm.put("client_id", client_id);
		try
		{
			HashMap<String, Object>  returnMap=lObjConfigDao.saveInvoice(hm, con);
					hm.put("invoice_id", returnMap.get("invoice_id"));
					if(!challanId.equals("") && challanId!=null)
					{
						lObjConfigDao.saveChallanAgainstInvoice(hm,con);
					}
					
					
			
			
			
			//hm.put("returnMessage", returnMap.get("invoice_no")+"~"+returnMap.get("invoice_id"));
			rs.setAjaxData(returnMap.get("invoice_no")+"~"+returnMap.get("invoice_id"));
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
			throw e;
		}		
		return rs;
	}
	
	public CustomResultObject savePurchaseInvoice(HttpServletRequest request,Connection con) throws Exception
	{
		Enumeration<String> params = request.getParameterNames(); 
		HashMap<String, Object> hm=new HashMap<>();
		List<HashMap<String, Object>> itemListRequired=new ArrayList<>();
		CustomResultObject rs=new CustomResultObject();
		while(params.hasMoreElements())
		{
		 String paramName = params.nextElement();		 
		 if(paramName.equals("itemDetails")) 
		 {
			 String[] itemsList=request.getParameter(paramName).split("\\|");
			 for(String item:itemsList)
			 {
				 String[] itemDetails =item.split("~");
				 //ID,WAREHOUSEID,JOBSHEETNO, QTY, RATE,GSTAMOUNT,GSTPERCENT,itemAMOUNT
				 HashMap<String, Object> itemDetailsMap=new HashMap<>();
				 itemDetailsMap.put("item_id",itemDetails[0]);
				 itemDetailsMap.put("ware_house_id",itemDetails[1]);
				 itemDetailsMap.put("job_sheet_no",itemDetails[2]);
				 itemDetailsMap.put("qty",itemDetails[3]);
				 itemDetailsMap.put("rate",itemDetails[4]);
				 itemDetailsMap.put("gst_amount",itemDetails[5]);
				 itemDetailsMap.put("gst_percentage",itemDetails[6]);
				 itemDetailsMap.put("item_amount",itemDetails[7]);
				 
				 itemListRequired.add(itemDetailsMap);				 
				 //ID, QTY, RATE,CustomRate
			 }
			 hm.put("itemDetails", itemListRequired);
			 continue;
		 }		 
		 hm.put(paramName, request.getParameter(paramName));
		 
		}
		
		
		
		String userId=request.getParameter("user_id");
		String firm_id=request.getParameter("firm_id");
		String appId=request.getParameter("appId");
		String challanId=request.getParameter("challanId");
		String txttallyrefno=request.getParameter("txttallyrefno");
		String txtvendorinvoiceno=request.getParameter("txtvendorinvoiceno");
		
		
		
		hm.put("txttallyrefno", txttallyrefno);
		hm.put("txtvendorinvoiceno", txtvendorinvoiceno);
		
		hm.put("type", "P");
		String hdnPreviousInvoiceId=request.getParameter("hdnPreviousInvoiceId");		
		if(hdnPreviousInvoiceId!=null && !hdnPreviousInvoiceId.equals("") && !hdnPreviousInvoiceId.equals("0") ) {deleteInvoice(request, con);}		
		if(appId==null || appId.equals(""))
			{
				appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
			}
		hm.put("app_id", appId);
		userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		firm_id= ((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("firm_id");
		hm.put("user_id", userId);
		hm.put("firm_id", firm_id);
		hm.put("challanId", challanId);
		
		
		String client_id=request.getParameter("client_id").equals("")?"0":request.getParameter("client_id");
		hm.put("client_id", client_id);
		try
		{
			HashMap<String, Object>  returnMap=lObjConfigDao.savePurchaseInvoice(hm, con);
					hm.put("invoice_id", returnMap.get("invoice_id"));			
				
					if(!challanId.equals("") && challanId!=null)
					{
						lObjConfigDao.saveChallanAgainstInvoice(hm,con);
					}				
			rs.setAjaxData(returnMap.get("invoice_no")+"~"+returnMap.get("invoice_id"));
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
			throw e;
		}		
		return rs;
	}
	
	
	
	

	public CustomResultObject saveChallanOut(HttpServletRequest request,Connection con) throws Exception
	{
		Enumeration<String> params = request.getParameterNames(); 
		HashMap<String, Object> hm=new HashMap<>();
		List<HashMap<String, Object>> itemListRequired=new ArrayList<>();
		CustomResultObject rs=new CustomResultObject();
		while(params.hasMoreElements())
		{
		 String paramName = params.nextElement();		 
		 if(paramName.equals("itemDetails")) 
		 {
			 String[] itemsList=request.getParameter(paramName).split("\\|");
			 for(String item:itemsList)
			 {
				 String[] itemDetails =item.split("~");
				 //ID,WAREHOUSEID,JOBSHEETNO, QTY, RATE,GSTAMOUNT,GSTPERCENT,itemAMOUNT
				 HashMap<String, Object> itemDetailsMap=new HashMap<>();
				 itemDetailsMap.put("item_id",itemDetails[0]);
				 itemDetailsMap.put("ware_house_id",itemDetails[1]);
				 itemDetailsMap.put("job_sheet_no",itemDetails[2]);
				 itemDetailsMap.put("qty",itemDetails[3]);
				 
				 
				 itemListRequired.add(itemDetailsMap);				 
				 //ID, QTY, RATE,CustomRate
			 }
			 hm.put("itemDetails", itemListRequired);
			 continue;
		 }		 
		 hm.put(paramName, request.getParameter(paramName));
		 
		}
		
		
		
		String userId=request.getParameter("user_id");
		String firm_id=request.getParameter("firm_id");
		String appId=request.getParameter("appId");
		
		String hdnPreviousInvoiceId=request.getParameter("hdnPreviousInvoiceId");		
		if(hdnPreviousInvoiceId!=null && !hdnPreviousInvoiceId.equals("") && !hdnPreviousInvoiceId.equals("0") ) {deleteInvoice(request, con);}		
		if(appId==null || appId.equals(""))
			{
				appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
			}
		hm.put("app_id", appId);
		userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		firm_id= ((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("firm_id");
		hm.put("user_id", userId);
		hm.put("firm_id", firm_id);
		
		String client_id=request.getParameter("client_id").equals("")?"0":request.getParameter("client_id");
		hm.put("client_id", client_id);
		try
		{
			HashMap<String, Object>  returnMap=lObjConfigDao.saveChallanOut(hm, con);
					hm.put("invoice_id", returnMap.get("invoice_id"));			
					
					rs.setAjaxData(returnMap.get("invoice_no")+"~"+returnMap.get("invoice_id"));
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
			throw e;
		}		
		return rs;
	}
	
	
	public CustomResultObject saveChallanIn(HttpServletRequest request,Connection con) throws Exception
	{
		Enumeration<String> params = request.getParameterNames(); 
		HashMap<String, Object> hm=new HashMap<>();
		List<HashMap<String, Object>> itemListRequired=new ArrayList<>();
		CustomResultObject rs=new CustomResultObject();
		while(params.hasMoreElements())
		{
		 String paramName = params.nextElement();		 
		 if(paramName.equals("itemDetails")) 
		 {
			 String[] itemsList=request.getParameter(paramName).split("\\|");
			 for(String item:itemsList)
			 {
				 String[] itemDetails =item.split("~");
				 //ID,WAREHOUSEID,JOBSHEETNO, QTY, RATE,GSTAMOUNT,GSTPERCENT,itemAMOUNT
				 HashMap<String, Object> itemDetailsMap=new HashMap<>();
				 itemDetailsMap.put("item_id",itemDetails[0]);
				 itemDetailsMap.put("ware_house_id",itemDetails[1]);
				 itemDetailsMap.put("job_sheet_no",itemDetails[2]);
				 itemDetailsMap.put("qty",itemDetails[3]);
				 
				 
				 itemListRequired.add(itemDetailsMap);				 
				 //ID, QTY, RATE,CustomRate
			 }
			 hm.put("itemDetails", itemListRequired);
			 continue;
		 }		 
		 hm.put(paramName, request.getParameter(paramName));
		 
		}
		
		
		
		String userId=request.getParameter("user_id");
		String firm_id=request.getParameter("firm_id");
		String appId=request.getParameter("appId");
		String challanId=request.getParameter("challanId");
		String txtvendorchallanno =request.getParameter("txtvendorchallanno");
		
		
		
		String hdnPreviousInvoiceId=request.getParameter("hdnPreviousInvoiceId");		
		if(hdnPreviousInvoiceId!=null && !hdnPreviousInvoiceId.equals("") && !hdnPreviousInvoiceId.equals("0") ) {deleteInvoice(request, con);}		
		if(appId==null || appId.equals(""))
			{
				appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
			}
		hm.put("app_id", appId);
		userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		firm_id= ((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("firm_id");
		hm.put("user_id", userId);
		hm.put("firm_id", firm_id);
		hm.put("type", "P");
		hm.put("challanId", challanId);
		hm.put("txtvendorchallanno", txtvendorchallanno);
		
		
		String client_id=request.getParameter("client_id").equals("")?"0":request.getParameter("client_id");
		hm.put("client_id", client_id);
		try
		{
			HashMap<String, Object>  returnMap=lObjConfigDao.saveChallanIn(hm, con);
					hm.put("invoice_id", returnMap.get("invoice_id"));			
					

			
					
			
			
			
			
			//hm.put("returnMessage", returnMap.get("invoice_no")+"~"+returnMap.get("invoice_id"));
			rs.setAjaxData(returnMap.get("invoice_no")+"~"+returnMap.get("invoice_id"));
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
			throw e;
		}		
		return rs;
	}
	
	
	public CustomResultObject saveOrder(HttpServletRequest request,Connection con)
	{
		Enumeration<String> params = request.getParameterNames(); 
		HashMap<String, Object> hm=new HashMap<>();
		List<HashMap<String, Object>> itemListRequired=new ArrayList<HashMap<String, Object>>();
		CustomResultObject rs=new CustomResultObject();
		while(params.hasMoreElements())
		{
		 String paramName = params.nextElement();		 
		 if(paramName.equals("itemDetails")) 
		 {
			 String[] itemsList=request.getParameter(paramName).split("\\|");
			 for(String item:itemsList)
			 {
				 String itemDetails[] =item.split("~",-1);
				 HashMap<String, Object> itemDetailsMap=new HashMap<>();
				 itemDetailsMap.put("item_id",itemDetails[0]);
				 itemDetailsMap.put("qty",itemDetails[1]);
				 itemDetailsMap.put("remarks",itemDetails[2]);
				 itemListRequired.add(itemDetailsMap);
			 }
			 hm.put("itemDetails", itemListRequired);
			 continue;
		 }		 
		 hm.put(paramName, request.getParameter(paramName));		 
		}		
		HashMap<String, Object> outputMap=new HashMap<>();
		String userId=request.getParameter("user_id");
		String firmId=request.getParameter("firm_id");
		String appId=request.getParameter("appId");
		String orderId=(request.getParameter("order_id").toString());
		String tableId=request.getParameter("table_id");
		
		if(userId==null ||userId.equals(""))
		{
			userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
			firmId= ((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("firm_id");
		}
		hm.put("user_id", userId);
		hm.put("table_id", tableId);
		hm.put("order_id", orderId);
		
		try
		{	
			if(orderId.equals(""))
			{
				orderId=String.valueOf(lObjConfigDao.saveOrder(hm, con));
			}
			hm.put("order_id", orderId);
			lObjConfigDao.saveOrderDetails(hm, con);
			lObjConfigDao.updateTableWithOrderId(hm, con);
								
			hm.put("returnMessage", "Ordered Succesfull");
			rs.setAjaxData("Ordered Succesfull");		
		}
		catch (Exception e)
		{
			writeErrorToDB(e);		
		}		
		return rs;
	}
	
	
	
	
	
	
	
	
	
	
	public CustomResultObject saveClient(HttpServletRequest request,Connection con)
	{
	  
		HashMap<String, Object> hm=new HashMap<>(); String clientId=
				  request.getParameter("hdnClientId"); List<HashMap<String, Object>>
				  itemListRequired=new ArrayList<HashMap<String, Object>>(); CustomResultObject
				  rs=new CustomResultObject();
				  
				  String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
				  hm.put("user_id", userId);
		 
				 
	  HashMap<String, Object> outputMap=new HashMap<>();
	  String client_name=request.getParameter("client_name");
	  String address_of_the_establishment=request.getParameter("address_of_the_establishment"); 
	  String txtdateOfSetup=request.getParameter("txtdateOfSetup"); 
	  String company_pan=request.getParameter("company_pan"); 
	  String ownership_details=request.getParameter("ownership_details");
	  String gst_no=request.getParameter("gst_no"); 
	  String contact_person_name=request.getParameter("contact_person_name"); 
	  String contact_person_email_id=request.getParameter("contact_person_email_id"); 
	  String contact_person_mobile_no=request.getParameter("contact_person_mobile_no"); 
	  String director_name=request.getParameter("director_name"); 
	  String director_pan_card=request.getParameter("director_pan_card"); 
	  String txtdirectorDob=request.getParameter("txtdirectorDob"); 
	  String director_email_id=request.getParameter("director_email_id"); 
	  String director_mobile_no=request.getParameter("director_mobile_no");

	  
	  String hiddendirectorvalues=request.getParameter("hiddendirectorvalues");

	  String billing_amount=request.getParameter("billing_amount");

	  
	  
	  
	  hm.put("client_name",client_name); 
	  hm.put("address_of_the_establishment",address_of_the_establishment); 
	  hm.put("txtdateOfSetup", txtdateOfSetup);
	  hm.put("company_pan", company_pan); 
	  hm.put("ownership_details",ownership_details); 
	  hm.put("gst_no", gst_no); 
	  hm.put("contact_person_name",contact_person_name); 
	  hm.put("contact_person_email_id", contact_person_email_id);
	  hm.put("contact_person_mobile_no", contact_person_mobile_no);
	  hm.put("director_name", director_name); 
	  hm.put("director_pan_card", director_pan_card);
	  hm.put("txtdirectorDob", txtdirectorDob);
	  hm.put("director_email_id",director_email_id); 
	  hm.put("director_mobile_no", director_mobile_no);
	  hm.put("clientId", clientId);
	  hm.put("billing_amount", billing_amount);
	  
	  
	  try { 
	  
		  
	hm.put("client_id", clientId);
	
	if(clientId!=null && !clientId.equals(""))
	{
		lObjConfigDao.updateClient(hm, con);
	}
	else
	{
		lObjConfigDao.saveClient(hm, con);
	}
	  
	  
	  hm.put("returnMessage", "Client Saved Successfully");
	  
	  rs.setAjaxData("<script>window.location='?a=showClientMaster&type='</script>");
	  
	  }
	catch(Exception e)
	{
		writeErrorToDB(e);
	}

	return rs;
	}
	 
	
	
	public CustomResultObject printLabels(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		
		
		Enumeration<String> params = request.getParameterNames(); 
		HashMap<String, Object> hm=new HashMap<>();
		List<HashMap<String, Object>> itemListRequired=new ArrayList<HashMap<String, Object>>();
		while(params.hasMoreElements())
		{
		 String paramName = params.nextElement();
		 
		 if(paramName.equals("itemDetails")) 
		 {
			 String[] itemsList=request.getParameter(paramName).split("\\|");
			 for(String item:itemsList)
			 {
				 String itemDetails[] =item.split("~");
				 HashMap<String, Object> itemDetailsMap=new HashMap<>();
				 itemDetailsMap.put("item_id",itemDetails[0]);
				 itemDetailsMap.put("product_code",itemDetails[1]);
				 itemDetailsMap.put("item_name",itemDetails[2]);
				 itemDetailsMap.put("noOfLabels",itemDetails[3]);
				 itemDetailsMap.put("isPrintPrice",itemDetails[4]);
				 itemListRequired.add(itemDetailsMap);				 
				 //ID, QTY, RATE,CustomRate
			 }
			 hm.put("itemDetails", itemListRequired);
			 continue;
		 }		 
		 hm.put(paramName, request.getParameter(paramName));
		 
		}
		
		HashMap<String, Object> outputMap=new HashMap<>();
		
		
		List<HashMap<String, Object>> newListRequired=new ArrayList<HashMap<String, Object>>();
		// based on no of labels adding more to list
		for(HashMap<String, Object> tempObj:itemListRequired)
		{
			int noOfLabels=Integer.parseInt( tempObj.get("noOfLabels").toString());
			for(int x=0;x<noOfLabels;x++)
			{
				newListRequired.add(tempObj);
			}
		}
		while(true)
		{
			if(newListRequired.size()%5==0) 
			{
				break;
			}
			HashMap<String, Object> tempObj=new HashMap<>();
			tempObj.put("product_code", 00000);tempObj.put("item_name", "Adjustment");
			newListRequired.add(tempObj);
		}
		
		
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		String firmId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("firm_id");
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+File.separator;
		hm.put("user_id", userId);
		hm.put("firm_id", firmId);
		try
		{	
			
			for(HashMap<String, Object> item: newListRequired)
			{
				generateQRForThisString(item.get("product_code").toString(), DestinationPath,118,120,"QR");
			}
			
			
			
			
			Document document = new Document(PageSize.A4, 0, 0, 0, 0);
			document.setMargins(0, 0, 0, 0);
			  PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(DestinationPath+"reqPDF.pdf"));
			  writer.setCompressionLevel(9);
			  writer.setFullCompression();
			  ConfigurationServiceImpl event = new ConfigurationServiceImpl();
		        writer.setPageEvent(event);
			  document.open();		  
			  PdfPTable table = new PdfPTable(5);
			  table.setWidthPercentage(100);
			  int i=0;
			  List<String> tempList=new ArrayList<>();
			  
			  
			  
			  
			  for(HashMap<String, Object> item : newListRequired)
				{
				  PdfPCell cell;
				  com.itextpdf.text.Image image=com.itextpdf.text.Image.getInstance(DestinationPath+item.get("product_code")+".jpg");			  		        
			      cell = new PdfPCell(image);
			      cell.setPadding(5);
			      cell.setBorder(Rectangle.NO_BORDER);			      
			      table.addCell(cell);
			      tempList.add(item.get("item_name").toString());
			      i++;
			      if(i%5==0)
			      {
			    	  for(String s:tempList)
			    	  {			    		
					  	cell = new PdfPCell(new Phrase(s,new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL) ));      		       
				        cell.setHorizontalAlignment(com.itextpdf.text.Image.ALIGN_CENTER);
				        cell.setBorder(Rectangle.RECTANGLE);
				        table.addCell(cell);
			    	  }
			    	  
			    	  for(String s:tempList)
			    	  {			    		
					  	cell = new PdfPCell(new Phrase("--------------------------",new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL) ));      		       
				        cell.setHorizontalAlignment(com.itextpdf.text.Image.ALIGN_CENTER);
				        cell.setBorder(Rectangle.NO_BORDER);
				        table.addCell(cell);
			    	  }
			    	  tempList.clear();
			      }
				}
			  table.completeRow();
			document.add(table);
			  document.close();

				rs.setAjaxData("reqPDF.pdf");
		
							
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	public CustomResultObject printLabelsClient(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		
		
		Enumeration<String> params = request.getParameterNames(); 
		HashMap<String, Object> hm=new HashMap<>();
		List<HashMap<String, Object>> itemListRequired=new ArrayList<HashMap<String, Object>>();
		while(params.hasMoreElements())
		{
		 String paramName = params.nextElement();
		 
		 if(paramName.equals("clientDetails")) 
		 {
			 String[] itemsList=request.getParameter(paramName).split("\\|");
			 for(String item:itemsList)
			 {
				 String itemDetails[] =item.split("~");
				 HashMap<String, Object> itemDetailsMap=new HashMap<>();
				 itemDetailsMap.put("client_id",itemDetails[0]);
				 itemDetailsMap.put("client_name",itemDetails[1]);
				 itemDetailsMap.put("mobile_number",itemDetails[2]);				 
				 itemListRequired.add(itemDetailsMap);				 
				 //ID, Name, Number
			 }
			 hm.put("clientDetails", itemListRequired);
			 continue;
		 }		 
		 hm.put(paramName, request.getParameter(paramName));
		 
		}
		
		HashMap<String, Object> outputMap=new HashMap<>();
		
		
		List<HashMap<String, Object>> newListRequired=new ArrayList<HashMap<String, Object>>();
		// based on no of labels adding more to list
		for(HashMap<String, Object> tempObj:itemListRequired)
		{
			
				newListRequired.add(tempObj);
			
		}
		while(true)
		{
			if(newListRequired.size()%5==0) 
			{
				break;
			}
			HashMap<String, Object> tempObj=new HashMap<>();
			tempObj.put("client_id", 00000);tempObj.put("client_name", "Adjustment");
			newListRequired.add(tempObj);
		}
		
		
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		String firmId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("firm_id");
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+File.separator;
		DestinationPath+=userId;
		hm.put("user_id", userId);
		hm.put("firm_id", firmId);
		try
		{	
			
			for(HashMap<String, Object> item: newListRequired)
			{
				generateQRForThisString(item.get("client_id").toString(), DestinationPath,118,120,"QR");
			}
			
			
			
			
			Document document = new Document(PageSize.A4, 0, 0, 0, 0);
			document.setMargins(0, 0, 0, 0);
			  PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(DestinationPath+"reqPDF.pdf"));
			  writer.setCompressionLevel(9);
			  writer.setFullCompression();
			  ConfigurationServiceImpl event = new ConfigurationServiceImpl();
		        writer.setPageEvent(event);
			  document.open();		  
			  PdfPTable table = new PdfPTable(5);
			  table.setWidthPercentage(100);
			  int i=0;
			  List<String> tempList=new ArrayList<>();
			  
			  
			  
			  
			  for(HashMap<String, Object> item : newListRequired)
				{
				  PdfPCell cell;
				  com.itextpdf.text.Image image=com.itextpdf.text.Image.getInstance(DestinationPath+item.get("client_id")+".jpg");			  		        
			      cell = new PdfPCell(image);
			      cell.setPadding(5);
			      cell.setBorder(Rectangle.NO_BORDER);			      
			      table.addCell(cell);
			      tempList.add(item.get("client_name").toString());
			      i++;
			      if(i%5==0)
			      {
			    	  for(String s:tempList)
			    	  {			    		
					  	cell = new PdfPCell(new Phrase(s,new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL) ));      		       
				        cell.setHorizontalAlignment(com.itextpdf.text.Image.ALIGN_CENTER);
				        cell.setBorder(Rectangle.RECTANGLE);
				        table.addCell(cell);
			    	  }
			    	  
			    	  for(String s:tempList)
			    	  {			    		
					  	cell = new PdfPCell(new Phrase("--------------------------",new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL) ));      		       
				        cell.setHorizontalAlignment(com.itextpdf.text.Image.ALIGN_CENTER);
				        cell.setBorder(Rectangle.NO_BORDER);
				        table.addCell(cell);
			    	  }
			    	  tempList.clear();
			      }
				}
			  table.completeRow();
			document.add(table);
			  document.close();

				rs.setAjaxData(userId+"reqPDF.pdf");
		
							
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	public CustomResultObject deleteStock(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		long stockid= Integer.parseInt(request.getParameter("stockid"));		
		HashMap<String, Object> outputMap=new HashMap<>();				
		String returnAjaxString;
		try
		{			
						
						
			rs.setAjaxData(lObjConfigDao.deleteStock(stockid, con));
			
							
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	public CustomResultObject saveInventoryCounting(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		try
		{		
			HashMap<String, Object> outputMap=new HashMap<>();
		Long firmId=Long.parseLong(((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("firm_id"));
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		String itemDetailsString=request.getParameter("itemDetails");
		String remarksouter=request.getParameter("remarksouter");
		Long wareHouseId=Long.valueOf(request.getParameter("ware_house_id").toString());
		
		String[] listOfitems=itemDetailsString.split("\\|");
		
		outputMap.put("userId", userId);
		outputMap.put("firmId", firmId);
		outputMap.put("type", "Inventory Counting");
		outputMap.put("outerRemarks", remarksouter);
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);
		
		long stockModificationId= lObjConfigDao.addStockModification(outputMap, con);
		
		List<HashMap<String, Object>> listitems=new ArrayList<>();
		for(String s:listOfitems)
		{
			String[] item=s.split("\\~");
			HashMap<String, Object> hm=new HashMap<>();
			hm.put("stockModificationId",stockModificationId);
			
			hm.put("itemId",item[0]);
			hm.put("expectedCount",item[1]);
			hm.put("currentCount",item[2]);
			hm.put("difference",item[3]);
			hm.put("differenceAmount",item[4]);
			hm.put("app_id",appId);
			
			listitems.add(hm);
		}
		
		for(HashMap<String, Object> hm:listitems)
		{
			
			
			lObjConfigDao.saveStockModificationInventoryCounting(hm,con);
			
			
			hm.put("type", "InventoryCounting");
			hm.put("remarks", "");
			hm.put("app_id", appId);
			hm.put("qty", hm.get("difference"));
			hm.put("drpfirmId", firmId);
			hm.put("drpitems", hm.get("itemId"));
			hm.put("user_id", userId);
			lObjConfigDao.addStockRegister(hm, con);
			
			
			
			
		
			hm.put("qty", hm.get("currentCount"));
			
			long itemId=Long.parseLong(hm.get("itemId").toString());
			if(lObjConfigDao.checkifStockAlreadyExist(firmId,itemId,wareHouseId, con).equals("0"))
			{
				lObjConfigDao.addStockMaster(hm,con);
			}
			
				hm.put("stock_id", lObjConfigDao.checkifStockAlreadyExist(firmId, itemId,wareHouseId, con));
				lObjConfigDao.updateStockMasterInventoryCounting(hm, con);				
			
			
		}
		
		
		
		
						
		String returnAjaxString;
		
						
			rs.setAjaxData("Counting added Succesfully");
			
							
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	
	
	
	ObjectMapper mapper = new ObjectMapper();
	public CustomResultObject getitemsByCategoryId(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		long categoryId= Integer.parseInt(request.getParameter("categoryId"));		
		HashMap<String, Object> outputMap=new HashMap<>();
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);
		String returnAjaxString;
		try
		{			
						
						
			rs.setAjaxData(mapper.writeValueAsString(lObjConfigDao.getServicesByCategoryId(outputMap, con)));
			
							
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	
	
	public CustomResultObject showEdititem(HttpServletRequest request,Connection con) throws UnknownHostException, ClassNotFoundException, SQLException
	{
		CustomResultObject rs=new CustomResultObject();
		long itemId= Integer.parseInt(request.getParameter("itemId"));
		HashMap<String, Object> outputMap=new HashMap<>();
		
				try
		{		
		
			 outputMap.put("CategoriesList", lObjConfigDao.getCategories(outputMap,con));
			 outputMap.put("itemDetails", lObjConfigDao.getDetailsforItem(itemId,con));			
		rs.setViewName("../Additems.jsp");	
		rs.setReturnObject(outputMap);
		
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	

	
	
	
	
	
	
	
	

	
	public CustomResultObject ShowOrders(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
			
		HashMap<String, Object> outputMap=new HashMap<>();
		try
		{		 
		
		
	//	outputMap.put("ListOfOrders", lObjConfigDao.ShowOrders(request));
		
		
		rs.setViewName("../ShowOrders.jsp");									
		
		rs.setReturnObject(outputMap);
				
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	public CustomResultObject showMobileBookings(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
			
		HashMap<String, Object> outputMap=new HashMap<>();
		try
		{		 
		
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);
			
		outputMap.put("ListOfOrders", lObjConfigDao.getMobileAppOrders(appId,con));				
		rs.setViewName("../MobileBookings.jsp");									
		
		rs.setReturnObject(outputMap);
				
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	
	

	
	
	

	
	
	
	
	
	
	
	
	
	
	public CustomResultObject deleteAttachment(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		long attachmentId= Long.valueOf(request.getParameter("attachmentId").toString());		
				
		String returnAjaxString;		
		try
		{			
			
					
			rs.setAjaxData(lObjConfigDao.deleteAttachment (attachmentId,con));
			
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	public CustomResultObject showClientMaster(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
		
		String exportFlag= request.getParameter("exportFlag")==null?"":request.getParameter("exportFlag");
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+"/";	
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		String searchInput=request.getParameter("searchInput");
		
		
		
		
		outputMap.put("searchInput", searchInput);
		
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);
		
		try
		{			
			List<LinkedHashMap<String, Object>> requiredList=new ArrayList<>();
			String [] colNames= {"client_id","nameOfTheEstablishment","addressOfTheEstablishment", "dateOfSetup"};
			
			List<LinkedHashMap<String, Object>> lst = lObjConfigDao.getClientMaster(outputMap,con);
			
			
			if(!exportFlag.isEmpty())
			{
				outputMap = getCommonFileGenerator(colNames,lst,exportFlag,DestinationPath,userId,"ClientMaster");
			}
			else
			{
				outputMap.put("groupList", lObjConfigDao.getClientGroup(appId,con));
				outputMap.put("ListOfClients", lst);	
				rs.setViewName("../Client.jsp");
				rs.setReturnObject(outputMap);
			}
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}
		rs.setReturnObject(outputMap);
		return rs;
	}
	

	public CustomResultObject deleteClient(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		long clientId= Long.parseLong(request.getParameter("clientId"));		
		HashMap<String, Object> outputMap=new HashMap<>();				
		String returnAjaxString;	
		try
		{			
			
			
			
			rs.setAjaxData(lObjConfigDao.deleteClient(clientId,con));
			
			
							
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	public CustomResultObject deleteTransaction(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		long clientId= Long.parseLong(request.getParameter("clientid"));		
				
		try
		{			
			
			
			
			
			rs.setAjaxData(lObjConfigDao.deleteTransaction(clientId,con));
			
			
							
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	
	
	
				
	public CustomResultObject showAddClient(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
		
		long clientId=request.getParameter("clientId")==null?0L:Long.parseLong(request.getParameter("clientId"));
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		
		String type=request.getParameter("type");
		outputMap.put("app_id", appId);
		outputMap.put("type", type);
		try
		{	
			LinkedHashMap<String, String> hm=new LinkedHashMap<>();
			
			if(clientId!=0) 
			{			
				hm=lObjConfigDao.getClientDetails(clientId,con);				
			}
			outputMap.put("clientDetails", hm);
			outputMap.put("groupList", lObjConfigDao.getClientGroup(appId,con));
			
			rs.setViewName("../AddClient.jsp");	
			rs.setReturnObject(outputMap);		
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	public CustomResultObject showFirmMaster(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
		
		String exportFlag= request.getParameter("exportFlag")==null?"":request.getParameter("exportFlag");
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+"/";	
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);
		try
		{			
			String [] colNames= {"firmId","firmName", "firmAddress", "firmEmail"};
			
			List<LinkedHashMap<String, Object>> lst = null;
			
			lst=  lObjConfigDao.getfirmMaster(outputMap,con);
						
			if(!exportFlag.isEmpty())
				outputMap = getCommonFileGenerator(colNames,lst,exportFlag,DestinationPath,userId,"firmMaster");
			else
			{
				outputMap.put("ListOffirms", lst);	
				rs.setViewName("../Firm.jsp");
				rs.setReturnObject(outputMap);
			}
			
				
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}	
		rs.setReturnObject(outputMap);
		return rs;
	}
	
	
	public CustomResultObject showAddFirm(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
		
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);
		
		long firmId=request.getParameter("firmId")==null?0L:Long.parseLong(request.getParameter("firmId"));
		
		try
		{	
			if(firmId!=0) {outputMap.put("firmDetails", lObjConfigDao.getfirmDetails(firmId,con));}
			outputMap.put("sbuMaster", lObjConfigDao.getSBUMaster(outputMap,con));
			
			
			rs.setViewName("../AddFirm.jsp");	
			rs.setReturnObject(outputMap);		
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	public CustomResultObject showSwitchFirm(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();		
		try
		{
			rs.setViewName("../SwitchFirm.jsp");	
			rs.setReturnObject(showSwitchFirmitem(request, con));		
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}

	
	public HashMap<String, Object> showSwitchFirmitem(HttpServletRequest request,Connection con)
	{
					
		HashMap<String, Object> outputMap=new HashMap<>();
		
		String appId=request.getParameter("appId");
		if(appId==null || appId.equals(""))
		{
			appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		}
		outputMap.put("app_id", appId);
		String userId=request.getParameter("userId");
		if(userId==null||userId.equals("") )
		{
			userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		}
		try
		{
			outputMap.put("listOffirms", lObjConfigDao.getfirmMaster(outputMap,con));
			outputMap.put("userDetails", lObjConfigDao.getuserDetailsById(Long.valueOf(userId),con));				
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				
		}		
		return outputMap;
	}
	
	
	
	public CustomResultObject addFirm(HttpServletRequest request,Connection con) throws FileUploadException
	{
		
		CustomResultObject rs=new CustomResultObject();	
		HashMap<String, Object> outputMap=new HashMap<>();	
				
		FileItemFactory itemFactory=new DiskFileItemFactory();
		ServletFileUpload upload=new ServletFileUpload(itemFactory);		
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);
		HashMap<String,Object> hm=new HashMap<>();
		hm.put("app_id", appId);
		List<FileItem> toUpload=new ArrayList<FileItem>();
		if(ServletFileUpload.isMultipartContent(request))
		{
			List<FileItem> items=upload.parseRequest(request);
			for(FileItem item:items)
			{		
				
				if (item.isFormField()) 
				{
					hm.put(item.getFieldName(), item.getString());
			    }
				else
				{
					toUpload.add(item);
				}
			}			
		}	
		

			long firmId=hm.get("hdnfirmId").equals("")?0l:Long.parseLong(hm.get("hdnfirmId").toString());
			try
			{	
				
				
				
				if(firmId==0)
				{
					firmId=lObjConfigDao.addfirm(con, hm);
				}
				else
				{
					lObjConfigDao.updatefirm(firmId,con,hm);
				}
				
				rs.setReturnObject(outputMap);
				
				
				rs.setAjaxData("<script>window.location='?a=showFirmMaster'</script>");
				
											
			}
			catch (Exception e)
			{
				writeErrorToDB(e);
					rs.setHasError(true);
			}		
			return rs;
		
			
	}
	public CustomResultObject deleteFirm(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		long firmId= Long.parseLong(request.getParameter("firmId"));		
				
		try
		{			
			
			
			
			rs.setAjaxData(lObjConfigDao.deletefirm(firmId,con));
							
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	public CustomResultObject showDailyInvoiceReportParameter(HttpServletRequest request,Connection con) throws ClassNotFoundException, SQLException
	{
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
		
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);

		List<LinkedHashMap<String, Object>> lst = lObjConfigDao.getfirmMaster(outputMap,con);
		
		try
		{
			outputMap.put("listfirmData", lst);					
			rs.setViewName("../DailyInvoiceReportParameter.jsp");
			rs.setReturnObject(outputMap);			
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}	
		
		return rs;
	}
	
	public CustomResultObject showClientInvocieRegisterParameter(HttpServletRequest request,Connection con) throws ClassNotFoundException, SQLException
	{
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);
		try
		{
			List<LinkedHashMap<String, Object>> lst = lObjConfigDao.getClientMaster(outputMap, con);
			outputMap.put("clientList", lst);
			rs.setViewName("../ClientInvoiceReportParameter.jsp");
			rs.setReturnObject(outputMap);			
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}	
		
		return rs;
	}
	public CustomResultObject showClientLedgerParameter(HttpServletRequest request,Connection con) throws ClassNotFoundException, SQLException
	{
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);	
		try
		{
			List<LinkedHashMap<String, Object>> lst = lObjConfigDao.getClientMaster(outputMap, con);
			outputMap.put("clientList", lst);
			rs.setViewName("../ClientLedgerReportParameter.jsp");
			rs.setReturnObject(outputMap);			
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}	
		
		return rs;
	}
	
	public CustomResultObject showSalesReport2Parameters(HttpServletRequest request,Connection con) throws ClassNotFoundException, SQLException
	{
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);	
		try
		{
			List<LinkedHashMap<String, Object>> lst = lObjConfigDao.getClientMaster(outputMap, con);
			outputMap.put("clientList", lst);
			rs.setViewName("../SalesReport2Parameters.jsp");
			rs.setReturnObject(outputMap);			
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}	
		
		return rs;
	}
	

	
	
	
	
	
	
	
	public CustomResultObject showStockTransfer(HttpServletRequest request,Connection con) throws ClassNotFoundException, SQLException
	{
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
		String stockModificationId=request.getParameter("stockModificationId");
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);
		List<LinkedHashMap<String, Object>> lst = lObjConfigDao.getfirmMaster(outputMap,con);		
		try
		{
			
			if(stockModificationId!=null)
			{
				outputMap.put("stockModificationDetails", lObjConfigDao.getStockModificationDetailsStocktransfer(stockModificationId,con));
			}
			
			
			
			outputMap.put("listfirmData", lst);
			outputMap.put("listwarehouse", lObjConfigDao.getListOfWareHouse(outputMap, con));
			
			outputMap.put("todaysDate", lObjConfigDao.getDateFromDB(con));
			outputMap.put("itemList", lObjConfigDao.getServiceMaster(outputMap,con));
			
			outputMap.put("fromDate", lObjConfigDao.getDateFromDB(con));
			outputMap.put("toDate", lObjConfigDao.getDateFromDB(con));
			outputMap.put("firmId", "-1");
			
			List<LinkedHashMap<String, Object>> lst1 = lObjConfigDao.getStockRegister(outputMap,con);
			outputMap.put("stockRegisterList", lst1);
			
			rs.setViewName("../StockTransfer.jsp");
			rs.setReturnObject(outputMap);			
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}	
		
		return rs;
	}
	
	
	
	
	
	
	public CustomResultObject showStockRegister(HttpServletRequest request,Connection con) throws ClassNotFoundException, SQLException
	{
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
		
		String exportFlag= request.getParameter("exportFlag")==null?"":request.getParameter("exportFlag");
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+"/";	
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		String categoryId=request.getParameter("categoryId");	
		outputMap.put("categoryId", categoryId);
		
		String searchString=request.getParameter("searchString");	
		outputMap.put("searchString", searchString);
		
		String firmId=request.getParameter("firmId");	
		outputMap.put("firmId", firmId);
		
		String itemId=request.getParameter("itemId");	
		outputMap.put("itemId", itemId);
		
		String warehouseid=request.getParameter("warehouseid");	
		outputMap.put("warehouseid", warehouseid);
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);
		String fromDate=request.getParameter("txtfromdate")==null?"":request.getParameter("txtfromdate");
		String toDate=request.getParameter("txttodate")==null?"":request.getParameter("txttodate");		
		
		if(fromDate.equals(""))
		{
			fromDate=lObjConfigDao.getDateFromDB(con);
		}
		if(toDate.equals(""))
		{
			toDate=lObjConfigDao.getDateFromDB(con);
		}
		
		outputMap.put("fromDate", fromDate);
		outputMap.put("toDate", toDate);
		
		try
		{			
			List<LinkedHashMap<String, Object>> requiredList=new ArrayList<>();
			String [] colNames= {"invoicedate","firm_name","ware_house_name","item_name","product_code","size","color","qty","rate","typeOfTransaction"};
			
			List<LinkedHashMap<String, Object>> lst =  lObjConfigDao.getStockRegister(outputMap,con);
			
			
			if(!exportFlag.isEmpty())
			{
				outputMap = getCommonFileGenerator(colNames,lst,exportFlag,DestinationPath,userId,"StockStatus");
			}
			else
			{
				outputMap.put("ListStock", lst);
				outputMap.put("ListOfCategories", lObjConfigDao.getCategories(outputMap,con));
				outputMap.put("listOffirm", lObjConfigDao.getfirmMaster(outputMap,con));
				outputMap.put("listofwarehouse", lObjConfigDao.getListOfWareHouse(outputMap,con));
				
				//outputMap.put("totalDetails", totalDetails);
				
				outputMap.put("txtfromdate", fromDate);
				outputMap.put("txttodate", toDate);
				
				rs.setViewName("../StockRegisterGenerated.jsp");
				rs.setReturnObject(outputMap);
			}
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}
		rs.setReturnObject(outputMap);
		return rs;
	}
	


	
	
	public CustomResultObject generateBankPassBook(HttpServletRequest request,Connection con) throws SQLException
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
				
		String fromDate=request.getParameter("txtfromdate")==null?"":request.getParameter("txtfromdate");
		String toDate=request.getParameter("txttodate")==null?"":request.getParameter("txttodate");
		String bankId= request.getParameter("bankId")==null? "" : request.getParameter("bankId").toString();
		String firmId= request.getParameter("firmId")==null? "" : request.getParameter("firmId").toString();
		
		
		
		
		
		HashMap<String, Object> hm= new HashMap<>();

		
		
		String exportFlag= request.getParameter("exportFlag")==null?"":request.getParameter("exportFlag");
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+"/";	
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		hm.put("app_id", appId);
		
		
		// if parameters are blank then set to defaults
		if(fromDate.equals(""))
		{
			fromDate=lObjConfigDao.getDateFromDB(con);
		}
		if(toDate.equals(""))
		{
			toDate=lObjConfigDao.getDateFromDB(con);
		}
		
		
		outputMap.put("txtfromdate", fromDate);
		outputMap.put("txttodate", toDate);
		
		
		try
		{		
			
			

			outputMap.put("listOfFirms", lObjConfigDao.getfirmMaster(hm,con));
			outputMap.put("fromDate", fromDate);
			outputMap.put("toDate", toDate);
			outputMap.put("bankId", bankId);
			outputMap.put("firmId", firmId);
			
		
			
			
			hm.put("fromDate", fromDate);
			hm.put("toDate", toDate);
			hm.put("bankId", bankId);
			hm.put("firmId", firmId);
			
			String [] colNames= {"client_name","formattedPaymentDate", "DebitAmount","CreditAmount", "updatedDate","name"};
			
			
			
			
			LinkedHashMap<String, Object> lhm=getBankPassBookitem(hm, con);
			
			

			if(!exportFlag.isEmpty())
			{
				outputMap = getCommonFileGenerator(colNames,(List<LinkedHashMap<String, Object>>)lhm.get("dailyInvoiceData"),exportFlag,DestinationPath,userId,"DailyInvoiceReport");
			}
			else
			{
				
				
				outputMap.putAll(lhm);
				
				
				rs.setViewName("../BankPassBookGenerated.jsp");
				rs.setReturnObject(outputMap);
			}
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}
		rs.setReturnObject(outputMap);
		return rs;
	}
	
	public CustomResultObject generateExpenseReport(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
				
		String fromDate=request.getParameter("txtfromdate")==null?"":request.getParameter("txtfromdate");
		String toDate=request.getParameter("txttodate")==null?"":request.getParameter("txttodate");		
		String firmId= request.getParameter("firmId")==null? "" : request.getParameter("firmId");
		
		
		
		
		
		HashMap<String, Object> hm= new HashMap<>();

		
		
		String exportFlag= request.getParameter("exportFlag")==null?"":request.getParameter("exportFlag");
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+"/";	
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		hm.put("app_id", appId);
		try
		{		
			
			

			outputMap.put("listOfFirms", lObjConfigDao.getfirmMaster(hm,con));
			outputMap.put("fromDate", fromDate);
			outputMap.put("toDate", toDate);
			outputMap.put("firmId", firmId);
			
		
			if(fromDate.equals("") || toDate.equals("") )
			{
				outputMap.put("dailyInvoiceData", null);	
				rs.setViewName("../ExpenseReportGenerated.jsp");
				rs.setReturnObject(outputMap);
				return rs;
			}
			
			
			hm.put("fromDate", fromDate);
			hm.put("toDate", toDate);
			
			
			
			
			String [] colNames= {"firm_name","PartyName","job_sheet_no", "Head", "vendor_invoice_no","TransactionDate","amount","fullamount","theType","updated_date","updated_by"};
			
			
			LinkedHashMap<String, Object> lhm =generateExpenseReportitem(hm, con);
			
			if(!exportFlag.isEmpty())
			{
				outputMap = getCommonFileGenerator(colNames,(List<LinkedHashMap<String, Object>>)lhm.get("dailyInvoiceData"),exportFlag,DestinationPath,userId,"DailyInvoiceReport");
			}
			else
			{
				outputMap.putAll(lhm);
				rs.setViewName("../ExpenseReportGenerated.jsp");
				rs.setReturnObject(outputMap);
			}
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}
		rs.setReturnObject(outputMap);
		return rs;
	}
	
	DecimalFormat f = new DecimalFormat("##.00");
	public CustomResultObject generateProfitAndLossReport(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
				
		String fromDate=request.getParameter("txtfromdate")==null?"":request.getParameter("txtfromdate");
		String toDate=request.getParameter("txttodate")==null?"":request.getParameter("txttodate");		
		String firmId= request.getParameter("firmId")==null? "" : request.getParameter("firmId");
		String sbuId= request.getParameter("sbuId")==null? "" : request.getParameter("sbuId");
		
		
		
		
		
		

		
		
		String exportFlag= request.getParameter("exportFlag")==null?"":request.getParameter("exportFlag");
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+"/";	
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);
		try
		{		
			
			

			outputMap.put("listOfFirms", lObjConfigDao.getfirmMaster(outputMap,con));
			outputMap.put("sbuMaster", lObjConfigDao.getSBUMaster(outputMap,con));
			outputMap.put("fromDate", fromDate);
			outputMap.put("toDate", toDate);
			outputMap.put("firmId", firmId);
			outputMap.put("sbuId", sbuId);
			
			
		
			if(fromDate.equals(""))
			{
				fromDate=lObjConfigDao.getDateFromDB(con);
			}
			if(toDate.equals(""))
			{
				toDate=lObjConfigDao.getDateFromDB(con);
			}
			
			if(firmId.equals(""))
			{
				firmId="-1";
			}
			
			if(sbuId.equals(""))
			{
				sbuId="-1";
			}
			
			
			outputMap.put("fromDate", fromDate);
			outputMap.put("toDate", toDate);
			
			outputMap.put("firmId", firmId);
			outputMap.put("sbuId", sbuId);
			
			
			
			
			
			String [] colNames= {"firm_name","PartyName","job_sheet_no", "Head", "vendor_invoice_no","theDate","amount","fullamount","theType","updated_date","name"};
			
			outputMap=getProfitAndLossCalculations(outputMap,con,toDate,fromDate);
			
			
				


				
				
				rs.setViewName("../ProfitAndLossGenerated.jsp");
				rs.setReturnObject(outputMap);
			
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}
		rs.setReturnObject(outputMap);
		return rs;
	}
	DecimalFormat decimalFormat =  new DecimalFormat("#0.00"); 
	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	
	public HashMap<String, Object> getProfitAndLossCalculations(HashMap<String, Object> outputMap,Connection con,String toDate,String fromDate) throws ClassNotFoundException, SQLException, ParseException 
	{
		
		 
		
		
		
		 Date   date    = format.parse(outputMap.get("fromDate").toString());
		 Calendar cal = Calendar.getInstance();
		 cal.setTime(date);
		 cal.add(Calendar.DATE, -1);
		 Date dateBefore1Days = cal.getTime();		 
		 String fromDateMinusOne = format.format(dateBefore1Days);
		 
		 outputMap.put("fromDate", "23/01/1992");
		 outputMap.put("toDate", fromDateMinusOne );
		BigDecimal totalPurchaseTillToDateMinusOne= lObjConfigDao.getPurchaseAmount(outputMap,con);
		
		
		outputMap.put("fromDate", "23/01/1992");
		 outputMap.put("toDate", fromDateMinusOne );
		BigDecimal totalSalesTillDateMinusOneAtPurchaseRate= lObjConfigDao.getSoldPurchases(outputMap,con);
		
		
		
		outputMap.put("fromDate", "23/01/1992");
		 outputMap.put("toDate", toDate);
		BigDecimal totalPurchaseTillToDate= lObjConfigDao.getPurchaseAmount(outputMap,con);
		
		
		outputMap.put("fromDate", "23/01/1992");
		 outputMap.put("toDate", toDate);
		BigDecimal totalSalesTillDatePurchaseRate= lObjConfigDao.getSoldPurchases(outputMap,con);
		
		BigDecimal openingSalesAmount= lObjConfigDao.getOpeningSalesAmount(outputMap,con);
		
		outputMap.put("fromDate", fromDate);
		 outputMap.put("toDate", toDate);		
		BigDecimal purchaseAccounts= lObjConfigDao.getPurchaseAccounts(outputMap,con);
		
		
		outputMap.put("fromDate", fromDate);
		outputMap.put("toDate", toDate);
		BigDecimal salesAccounts= lObjConfigDao.getSalesAccounts(outputMap,con);
		
		
		
		
		
		
		List<LinkedHashMap<String, Object>> listOfExpensesClubbed=lObjConfigDao.getExpenseReportClubbed(outputMap,con);
		List<LinkedHashMap<String, Object>> listOfIncomesClubbed=lObjConfigDao.getIncomeReportClubbed(outputMap,con);
		
		
		
		outputMap.put("purchaseAccounts", purchaseAccounts);
		outputMap.put("salesAccounts", salesAccounts);
		BigDecimal OpeningStockAmount=totalPurchaseTillToDateMinusOne.subtract(totalSalesTillDateMinusOneAtPurchaseRate);
		BigDecimal ClosingStockAmount=totalPurchaseTillToDate.subtract(totalSalesTillDatePurchaseRate);
		

		outputMap.put("OpeningStockAmount", decimalFormat.format(OpeningStockAmount));
		
		outputMap.put("totalPurchaseTillToDateMinusOne", totalPurchaseTillToDateMinusOne);
		outputMap.put("totalSalesTillDateMinusOneAtPurchaseRate", totalSalesTillDateMinusOneAtPurchaseRate);
		
		
		
		
		
		outputMap.put("ClosingStockAmount", decimalFormat.format(ClosingStockAmount));
		outputMap.put("totalPurchaseTillToDate", totalPurchaseTillToDate);
		outputMap.put("totalSalesTillDatePurchaseRate", totalSalesTillDatePurchaseRate);
		
		
		
		outputMap.put("listOfExpensesClubbed", listOfExpensesClubbed);
		outputMap.put("listOfIncomesClubbed", listOfIncomesClubbed);
		
		
		
		BigDecimal leftTotal=new BigDecimal(0);
		BigDecimal rightTotal=new BigDecimal(0);
		leftTotal=leftTotal.add(OpeningStockAmount);
		leftTotal=leftTotal.add(purchaseAccounts);
		
		for (LinkedHashMap<String, Object> entry : listOfExpensesClubbed) 
			{
				leftTotal=leftTotal.add(new BigDecimal(entry.get("theAmount").toString()));
			}
		rightTotal=rightTotal.add(ClosingStockAmount);
			rightTotal=rightTotal.add(salesAccounts);
			for (LinkedHashMap<String, Object> entry : listOfIncomesClubbed) 
			{
			    //String key = entry.getKey();
				rightTotal=rightTotal.add(new BigDecimal(entry.get("theAmount").toString()));
			    // now work with key and value...
			}
			outputMap.put("leftTotal", decimalFormat.format(leftTotal));
			outputMap.put("rightTotal", decimalFormat.format(rightTotal));
			
			BigDecimal totalProfit=rightTotal.subtract(leftTotal);
			
			outputMap.put("ProfitFlag",totalProfit.compareTo(BigDecimal.ZERO));
			outputMap.put("totalProfit", decimalFormat.format(rightTotal.subtract(leftTotal)));
			
			outputMap.put("fromDate", fromDate);
			 outputMap.put("toDate", toDate);
			
		return outputMap;
		
	}
	
	
	
	public HashMap<String, Object> getCostSheetData(String jobsheetNo,Connection con) throws ClassNotFoundException, SQLException, ParseException
	{
		
		
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		BigDecimal profit=new BigDecimal(0);

		
		map.putAll(lObjConfigDao.getClientName(jobsheetNo,con));
		map.putAll(lObjConfigDao.getVendorName(jobsheetNo,con));
		map.putAll(lObjConfigDao.getProductName(jobsheetNo,con));
		map.putAll(lObjConfigDao.getFirmName(jobsheetNo,con));
		
		
		List<LinkedHashMap<String, Object>> lstOfSales=lObjConfigDao.getListOfSalesForJobSheet(jobsheetNo,con);			
		BigDecimal totalSales=new BigDecimal(0);
		BigDecimal totalSalesCount=new BigDecimal(0);

		for(LinkedHashMap<String, Object> sale:lstOfSales )
		{
			totalSales=totalSales.add(new BigDecimal(sale.get("values1").toString()));
			totalSalesCount=totalSalesCount.add(new BigDecimal(sale.get("Qty").toString()));
		}
		
		
		  List<LinkedHashMap<String, Object>>
		  lstOfPurchase=lObjConfigDao.getListOfPurchaseForJobSheet(jobsheetNo,con);
		  BigDecimal totalPurchase=new BigDecimal(0);
		  
		  BigDecimal totalPurchaseCount=new BigDecimal(0);
		  
		  for(LinkedHashMap<String, Object> purchase:lstOfPurchase ) {
		  totalPurchase=totalPurchase.add(new
		  BigDecimal(purchase.get("values1").toString()));
		  totalPurchaseCount=totalPurchaseCount.add(new
		  BigDecimal(purchase.get("Qty").toString())); }
		 
		
		List<LinkedHashMap<String, Object>> lstFromOtherJobSheet=lObjConfigDao.getListOfPurchaseFromOtherJobSheet(jobsheetNo,con);			
		BigDecimal totalPurchaseFromOtherJobSheet=new BigDecimal(0);
		
		BigDecimal totalPurchaseCountFromOtherJobSheet=new BigDecimal(0);
		
		for(LinkedHashMap<String, Object> purchase:lstFromOtherJobSheet )
		{
			totalPurchaseFromOtherJobSheet=totalPurchaseFromOtherJobSheet.add(new BigDecimal(purchase.get("values1").toString()));
			totalPurchaseCountFromOtherJobSheet=totalPurchaseCountFromOtherJobSheet.add(new BigDecimal(purchase.get("Qty").toString()));				
		}
		
		
		
		
		
		List<LinkedHashMap<String, Object>> lstOfExpense=lObjConfigDao.getListOfExpenseForJobSheet(jobsheetNo,con);
		lstOfExpense.addAll(lObjConfigDao.getListOfPaymentForJobSheet(jobsheetNo,con));// need to make changes here..
		
		BigDecimal totalExpense=new BigDecimal(0);
		for(LinkedHashMap<String, Object> expense:lstOfExpense )
		{
			totalExpense=totalExpense.add(new BigDecimal(expense.get("amount").toString()));								
		}
		
		
		profit=totalSales.subtract(totalPurchase).subtract(totalPurchaseFromOtherJobSheet).subtract(totalExpense);
		BigDecimal profitPercentage=new BigDecimal(0);
		if(totalSales.compareTo(BigDecimal.ZERO) != 0)				
		{
			 profitPercentage= profit.multiply(new BigDecimal(100)).divide(totalSales,MathContext.DECIMAL128);
		}
		
		map.put("lstPurchase", lstOfPurchase);
		map.put("lstFromOtherJobSheet", lstFromOtherJobSheet);
		
		map.put("lstSales", lstOfSales);			
		map.put("lstExpense", lstOfExpense);
		map.put("profit", f.format(profit));
		map.put("profitPercentage", f.format(profitPercentage));
		
		map.put("totalExpense", totalExpense);
		
		map.put("totalPurchase", totalPurchase);
		map.put("totalPurchaseCount", totalPurchaseCount);
		
		map.put("totalPurchaseFromOtherJobSheet", totalPurchaseFromOtherJobSheet);
		map.put("totalPurchaseCountFromOtherJobSheet", totalPurchaseCountFromOtherJobSheet);
		
		
		map.put("totalSales", totalSales);
		map.put("totalSalesCount", totalSalesCount);
		map.put("jobsheetNo", jobsheetNo);
		
		
		
		
		
		map.put("SOQty", totalPurchaseCount.subtract(totalSalesCount));
		
		
		
		return map;
		
	}
	
	
	
	
	
	
	
	public CustomResultObject generateFirmLedger(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
				
		String fromDate=request.getParameter("txtfromdate")==null?"":request.getParameter("txtfromdate");
		String toDate=request.getParameter("txttodate")==null?"":request.getParameter("txttodate");		
		String firmId= request.getParameter("firmId")==null? "" : request.getParameter("firmId");
		
		
		
		
		
		HashMap<String, Object> hm= new HashMap<>();

		
		
		String exportFlag= request.getParameter("exportFlag")==null?"":request.getParameter("exportFlag");
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+"/";	
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		hm.put("app_id", appId);
		try
		{		
			
			

			outputMap.put("listOfFirms", lObjConfigDao.getfirmMaster(hm,con));
			outputMap.put("fromDate", fromDate);
			outputMap.put("toDate", toDate);
			outputMap.put("firmId", firmId);
			
		
			if(fromDate.equals("") || toDate.equals("") )
			{
				outputMap.put("dailyInvoiceData", null);	
				rs.setViewName("../ExpenseReportGenerated.jsp");
				rs.setReturnObject(outputMap);
				return rs;
			}
			
			
			hm.put("fromDate", fromDate);
			hm.put("toDate", toDate);
			
			
			
			
			String [] colNames= {"firm_name","PartyName","job_sheet_no", "Head", "vendor_invoice_no","theDate","amount","fullamount","theType","updated_date","name"};
			
			List<LinkedHashMap<String, Object>> lst = lObjConfigDao.getFirmLedger(outputMap,con);
			
			
			if(!exportFlag.isEmpty())
			{
				outputMap = getCommonFileGenerator(colNames,lst,exportFlag,DestinationPath,userId,"DailyInvoiceReport");
			}
			else
			{
				outputMap.put("dailyInvoiceData", lst);
				//LinkedHashMap<String, Object> totalDetails=gettotalDetailsBankPassBook(lst);
				
				outputMap.put("dailyInvoiceData", lst);
				//outputMap.put("totalDetails", totalDetails);				
				
				
				//Double OpeningBalance=Double.parseDouble(outputMap.get("openingBalance").toString());
				
				//Double creditTotal=Double.parseDouble(totalDetails.get("creditTotal").toString());
				//Double debitTotal=Double.parseDouble(totalDetails.get("debitTotal").toString());
				
				//Double closingBalance=OpeningBalance-debitTotal+creditTotal;
				
				
				
				//outputMap.put("closingBalance", String.format("%.2f",closingBalance));


				
				
				rs.setViewName("../ExpenseReportGenerated.jsp");
				rs.setReturnObject(outputMap);
			}
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}
		rs.setReturnObject(outputMap);
		return rs;
	}
	public CustomResultObject generateStockRegister(HttpServletRequest request,Connection con) throws SQLException
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
				
		String fromDate=request.getParameter("txtfromdate")==null?"":request.getParameter("txtfromdate");
		String toDate=request.getParameter("txttodate")==null?"":request.getParameter("txttodate");
		long firmId= request.getParameter("txtfirm")==null? -1 : Long.valueOf(request.getParameter("txtfirm").toString());
		
		
		
		if(fromDate.equals(""))
		{
			fromDate=lObjConfigDao.getDateFromDB(con);
		}
		if(toDate.equals(""))
		{
			toDate=lObjConfigDao.getDateFromDB(con);
		}
		
		HashMap<String, Object> hm= new HashMap<>();
		hm.put("fromDate", fromDate);
		hm.put("toDate", toDate);
		hm.put("firmId", firmId);
		
		hm.put("firmId", firmId);
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		hm.put("app_id", appId);
		
		String exportFlag= request.getParameter("exportFlag")==null?"":request.getParameter("exportFlag");
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+"/";	
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		 
		try
		{			
			String [] colNames= {"client_name","total_amount","invoice_id", "invoice_date", "payment_type","payment_mode","name"};
			
			List<LinkedHashMap<String, Object>> lst = lObjConfigDao.getStockRegister(hm,con);
			
			if(!exportFlag.isEmpty())
			{
				outputMap = getCommonFileGenerator(colNames,lst,exportFlag,DestinationPath,userId,"DailyInvoiceReport");
			}
			else
			{
				outputMap.put("stockRegisterList", lst);	
				rs.setViewName("../StockRegisterGenerated.jsp");
				rs.setReturnObject(outputMap);
			}
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}
		rs.setReturnObject(outputMap);
		return rs;
	}
	
	
	
	public CustomResultObject showDailyPaymentRegisterParameter(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
		try {				
			rs.setViewName("../DailyPaymentRegisterParameter.jsp");
			rs.setReturnObject(outputMap);			
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}	
		
		return rs;
	}
	
	public CustomResultObject generateDailyPaymentRegister(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
			
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		

		String fromDate=request.getParameter("txtfromdate")==null?"":request.getParameter("txtfromdate");
		String toDate=request.getParameter("txttodate")==null?"":request.getParameter("txttodate");
		String paymentMode=request.getParameter("paymentMode")==null?"":request.getParameter("paymentMode");
		String firmId=request.getParameter("firmId")==null?"":request.getParameter("firmId");
		String paymentFor=request.getParameter("paymentFor")==null?"":request.getParameter("paymentFor");
		String type=request.getParameter("type")==null?"":request.getParameter("type");
		
		

		
		if(!fromDate.equals(""))
		{
		 request.getSession().setAttribute("fromDate",fromDate);
		 request.getSession().setAttribute("toDate",toDate);
		 request.getSession().setAttribute("paymentMode",toDate);
		 request.getSession().setAttribute("firmId",toDate);
		 request.getSession().setAttribute("paymentFor",toDate);
		 request.getSession().setAttribute("type",type);
		 
		}
		else
		{
			fromDate=request.getSession().getAttribute("fromDate").toString();
			toDate=request.getSession().getAttribute("toDate").toString();
			paymentMode=request.getSession().getAttribute("paymentMode").toString();
			firmId=request.getSession().getAttribute("firmId").toString();
			paymentFor=request.getSession().getAttribute("paymentFor").toString();
			type=request.getSession().getAttribute("type").toString();
			
			
			
		}
		
		 
		
		
		
		HashMap<String, Object> hm= new HashMap<>();
		hm.put("fromDate", fromDate);
		hm.put("toDate", toDate);
		hm.put("firmId", firmId);
		hm.put("paymentMode", paymentMode);
		hm.put("paymentFor", paymentFor);
		hm.put("type", type);
		
		
		hm.put("app_id", appId);
		String exportFlag= request.getParameter("exportFlag")==null?"":request.getParameter("exportFlag");
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+"/";	
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		 
		try
		{			
			String [] colNames= {"client_name","payment_mode","payment_date","amount"};
			List<LinkedHashMap<String, Object>> lst = null;
			if(type.equals("Debit"))
			{
				lst = lObjConfigDao.getDailyDebitRegister(hm,con);
			}
			else
			{
				lst = lObjConfigDao.getDailyPaymentRegister(hm,con);				
			}
			
			
			if(!exportFlag.isEmpty())
			{
				outputMap = getCommonFileGenerator(colNames,lst,exportFlag,DestinationPath,userId,"DailyPaymentRegister");
			}
			else
			{
				outputMap.put("dailyPaymentData", lst);
				outputMap.put("type", type);
				
				
				rs.setViewName("../DailyPaymentRegisterGenerated.jsp");
				rs.setReturnObject(outputMap);
			}
			
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}
		rs.setReturnObject(outputMap);
		return rs;
	}
	public CustomResultObject showPendingClientCollectionReport(HttpServletRequest request,Connection con) throws ClassNotFoundException, SQLException
	{
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();		
		try
		{				
			rs.setViewName("../PendingClientCollectionParameter.jsp");
			rs.setReturnObject(outputMap);			
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}	
		
		return rs;
	}

	public CustomResultObject generatePendingClientCollectionReport(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
		
		String fromDate=request.getParameter("txtfromdate")==null?"":request.getParameter("txtfromdate");
		String toDate=request.getParameter("txttodate")==null?"":request.getParameter("txttodate");

		
		if(!fromDate.equals(""))
		{
		 request.getSession().setAttribute("fromDate",fromDate);
		 request.getSession().setAttribute("toDate",toDate);
		}
		else
		{
			fromDate=request.getSession().getAttribute("fromDate").toString();
			toDate=request.getSession().getAttribute("toDate").toString();
		}
				
		
		HashMap<String, Object> hm= new HashMap<>();
		hm.put("fromDate", fromDate);
		hm.put("toDate", toDate);

		
		String exportFlag= request.getParameter("exportFlag")==null?"":request.getParameter("exportFlag");
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+"/";	
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		hm.put("app_id", appId);
		 
		try
		{			
			List<LinkedHashMap<String, Object>> requiredList=new ArrayList<>();
			String [] colNames= {"No","client_name","client_reference","PendingAmount","mobile_number","alternate_mobile_no","city"};
			int i=0;
			List<LinkedHashMap<String, Object>> lst = lObjConfigDao.getPendingClientCollection(hm,con);
			for(LinkedHashMap<String, Object> tempObj:lst)
			{
				tempObj.put("No", ++i);
			}
			
			
			if(!exportFlag.isEmpty())
			{
				outputMap = getCommonFileGenerator(colNames,lst,exportFlag,DestinationPath,userId,"PendingClientCollection");
			}
			else
			{
				outputMap.put("ListOfPendingCollection", lst);	
				rs.setViewName("../PendingClientCollection.jsp");
				rs.setReturnObject(outputMap);
			}
			
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}
		rs.setReturnObject(outputMap);
		return rs;
	}
	
	public CustomResultObject showCategoryWiseReportParameter(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
		try {				
			rs.setViewName("../CategoryWiseReportParameter.jsp");
			rs.setReturnObject(outputMap);			
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}	
		
		return rs;
	}
	
	public CustomResultObject generateCategoryWiseReport(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
			

		String fromDate=request.getParameter("txtfromdate")==null?"":request.getParameter("txtfromdate");
		String toDate=request.getParameter("txttodate")==null?"":request.getParameter("txttodate");

		
		if(!fromDate.equals(""))
		{
		 request.getSession().setAttribute("fromDate",fromDate);
		 request.getSession().setAttribute("toDate",toDate);
		}
		else
		{
			fromDate=request.getSession().getAttribute("fromDate").toString();
			toDate=request.getSession().getAttribute("toDate").toString();
		}
		
		 
		long firmId= Long.valueOf( ((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("firm_id"));
		
		
		HashMap<String, Object> hm= new HashMap<>();
		hm.put("fromDate", fromDate);
		hm.put("toDate", toDate);
		
		
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		hm.put("app_id", appId);
		
		

		String exportFlag= request.getParameter("exportFlag")==null?"":request.getParameter("exportFlag");
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+"/";	
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		 
		try
		{			
			String [] colNames= {"CategoryName","firmName","InvoiceDate","TotalAmount"};
			
			List<LinkedHashMap<String, Object>> lst = lObjConfigDao.getCategoryWiseReport(hm,con);
			
			if(!exportFlag.isEmpty())
			{
				outputMap = getCommonFileGenerator(colNames,lst,exportFlag,DestinationPath,userId,"CategoryWiseReport");
			}
			else
			{
				outputMap.put("CategoryWiseReportData", lst);	
				rs.setViewName("../CategoryWiseReportGenerated.jsp");
				rs.setReturnObject(outputMap);
			}
			
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}
		rs.setReturnObject(outputMap);
		return rs;
	}

	public CustomResultObject showEmployeeWiseReportParameter(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
		try {				
			rs.setViewName("../EmployeeWiseReportParameter.jsp");
			rs.setReturnObject(outputMap);			
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}	
		
		return rs;
	}
	
	public CustomResultObject generateEmployeeWiseReport(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
			

		String fromDate=request.getParameter("txtfromdate")==null?"":request.getParameter("txtfromdate");
		String toDate=request.getParameter("txttodate")==null?"":request.getParameter("txttodate");

		
		if(!fromDate.equals(""))
		{
		 request.getSession().setAttribute("fromDate",fromDate);
		 request.getSession().setAttribute("toDate",toDate);
		}
		else
		{
			fromDate=request.getSession().getAttribute("fromDate").toString();
			toDate=request.getSession().getAttribute("toDate").toString();
		}
		
		 
		long firmId= Long.valueOf( ((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("firm_id"));
		
		
		HashMap<String, Object> hm= new HashMap<>();
		hm.put("fromDate", fromDate);
		hm.put("toDate", toDate);
		hm.put("firmId", firmId);
		String exportFlag= request.getParameter("exportFlag")==null?"":request.getParameter("exportFlag");
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+"/";	
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		 
		try
		{			
			String [] colNames= {"EmployeeName","firmName","InvoiceDate","TotalAmount"};
			
			List<LinkedHashMap<String, Object>> lst = lObjConfigDao.getEmployeeWiseReport(hm,con);
			
			if(!exportFlag.isEmpty())
			{
				outputMap = getCommonFileGenerator(colNames,lst,exportFlag,DestinationPath,userId,"EmployeeWiseReport");
			}
			else
			{
				outputMap.put("EmployeeWiseReportData", lst);	
				rs.setViewName("../EmployeeWiseReportGenerated.jsp");
				rs.setReturnObject(outputMap);
			}
			
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}
		rs.setReturnObject(outputMap);
		return rs;
	}
	
	public CustomResultObject showConsolidatedPaymentModeCollection(HttpServletRequest request,Connection con) throws ClassNotFoundException, SQLException
	{
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
			
		
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);

		List<LinkedHashMap<String, Object>> lst = lObjConfigDao.getfirmMaster(outputMap,con);
		
		try
		{
			outputMap.put("listfirmData", lst);					
			rs.setViewName("../ConsolidatedPaymentModeParameter.jsp");
			rs.setReturnObject(outputMap);			
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}	
		
		return rs;
	}

	public CustomResultObject generateConsolidatedPaymentModeCollection(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
				
		String fromDate=request.getParameter("txtfromdate")==null?"":request.getParameter("txtfromdate");
		String toDate=request.getParameter("txttodate")==null?"":request.getParameter("txttodate");
		long firmId= request.getParameter("txtfirm")==null? -1 : Long.valueOf(request.getParameter("txtfirm").toString());
		
		if(!fromDate.equals(""))
		{
			request.getSession().setAttribute("fromDate1",fromDate);
			request.getSession().setAttribute("toDate1",toDate);
			request.getSession().setAttribute("firmId1",firmId);
		}
		else
		{
			fromDate=request.getSession().getAttribute("fromDate1").toString();
			toDate=request.getSession().getAttribute("toDate1").toString();
			firmId=Long.valueOf(request.getSession().getAttribute("firmId1").toString());
		}
		
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		
		
		
		HashMap<String, Object> hm= new HashMap<>();
		hm.put("app_id", appId);
		hm.put("fromDate", fromDate);
		hm.put("toDate", toDate);
		hm.put("firmId", firmId);
		String exportFlag= request.getParameter("exportFlag")==null?"":request.getParameter("exportFlag");
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+"/";	
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		 
		try
		{			
			String [] colNames= {"firmName","TotalAmount","PaymentMode", "InvoiceDate"};
			
			List<LinkedHashMap<String, Object>> lst = lObjConfigDao.getConsolidatedPaymentModeCollection(hm,con);
			
			if(!exportFlag.isEmpty())
			{
				outputMap = getCommonFileGenerator(colNames,lst,exportFlag,DestinationPath,userId,"ConsolidatedPaymentModeCollection");
			}
			else
			{
				outputMap.put("ConsolidatedPaymentMode", lst);	
				rs.setViewName("../ConsolidatedPaymentModeGenerated.jsp");
				rs.setReturnObject(outputMap);
			}
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}
		rs.setReturnObject(outputMap);
		return rs;
	}
	
	public CustomResultObject showPaymentTypeCollection(HttpServletRequest request,Connection con) throws ClassNotFoundException, SQLException
	{
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
		
		
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);

		List<LinkedHashMap<String, Object>> lst = lObjConfigDao.getfirmMaster(outputMap,con);
		
		try
		{
			outputMap.put("listfirmData", lst);					
			rs.setViewName("../PaymentTypeCollectionParameter.jsp");
			rs.setReturnObject(outputMap);			
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}	
		
		return rs;
	}

	public CustomResultObject generatePaymentTypeCollection(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
				
		String fromDate=request.getParameter("txtfromdate")==null?"":request.getParameter("txtfromdate");
		String toDate=request.getParameter("txttodate")==null?"":request.getParameter("txttodate");
		long firmId= request.getParameter("txtfirm")==null? -1 : Long.valueOf(request.getParameter("txtfirm").toString());
		
		if(!fromDate.equals(""))
		{
			request.getSession().setAttribute("fromDate1",fromDate);
			request.getSession().setAttribute("toDate1",toDate);
			request.getSession().setAttribute("firmId1",firmId);
		}
		else
		{
			fromDate=request.getSession().getAttribute("fromDate1").toString();
			toDate=request.getSession().getAttribute("toDate1").toString();
			firmId=Long.valueOf(request.getSession().getAttribute("firmId1").toString());
		}
		
		
		
		HashMap<String, Object> hm= new HashMap<>();
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		hm.put("app_id", appId);
		hm.put("fromDate", fromDate);
		hm.put("toDate", toDate);
		hm.put("firmId", firmId);
		String exportFlag= request.getParameter("exportFlag")==null?"":request.getParameter("exportFlag");
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+"/";	
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		 
		try
		{			
			String [] colNames= {"firmName","InvoiceDate","PaymentType", "Amount"};
			
			List<LinkedHashMap<String, Object>> lst = lObjConfigDao.getPaymentTypeCollectionCollection(hm,con);
			
			if(!exportFlag.isEmpty())
			{
				outputMap = getCommonFileGenerator(colNames,lst,exportFlag,DestinationPath,userId,"PaymentTypeCollection");
			}
			else
			{
				outputMap.put("PaymentTypeCollection", lst);	
				rs.setViewName("../PaymentTypeCollectionGenerated.jsp");
				rs.setReturnObject(outputMap);
			}
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}
		rs.setReturnObject(outputMap);
		return rs;
	}
	

	public CustomResultObject switchFirm(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		try
		{
			rs.setAjaxData(switchFirmitem(request, con).get("returnMessage").toString());						
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	public CustomResultObject changeInvoiceFormat(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		try
		{
			rs.setAjaxData(changeInvoiceFormatitem(request, con).get("returnMessage").toString());						
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	
	
	
	public HashMap<String, Object> switchFirmitem(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		long firmId= Integer.parseInt(request.getParameter("firmId"));
		
		String userId=request.getParameter("userId");
		if(userId==null || userId.equals(""))
		{
			userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		}
		
		HashMap<String, Object> outputMap=new HashMap<>();				
		String returnAjaxString;
		try
		{
			outputMap.put("returnMessage",lObjConfigDao.updatefirmForThisUser (firmId,userId,con));
			HashMap<String, String> hm= (HashMap<String, String>) request.getSession().getAttribute("userdetails");
			hm.put("firm_name", lObjConfigDao.getfirmDetails(firmId, con).get("firm_name"));
			hm.put("firm_id", String.valueOf(firmId));
			request.getSession().setAttribute("userdetails", hm);			
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return outputMap;
	}
	
	
	public HashMap<String, Object> changeInvoiceFormatitem(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		long formatId= Integer.parseInt(request.getParameter("formatId"));
		
		String userId=request.getParameter("userId");
		if(userId==null || userId.equals(""))
		{
			userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		}
		
		HashMap<String, Object> outputMap=new HashMap<>();				
		String returnAjaxString;
		try
		{
			outputMap.put("returnMessage",lObjConfigDao.updateInvoiceFormatForThisUser (formatId,userId,con));
			HashMap<String, String> hm= (HashMap<String, String>) request.getSession().getAttribute("userdetails");		
			hm.put("invoice_format", String.valueOf(formatId));
			request.getSession().setAttribute("userdetails", hm);			
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return outputMap;
	}
	
	
	
	
	public CustomResultObject showEmployeeMaster(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
		
		String exportFlag= request.getParameter("exportFlag")==null?"":request.getParameter("exportFlag");
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+"/";	
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);
		 
		try
		{			
			List<LinkedHashMap<String, Object>> requiredList=new ArrayList<>();
			String [] colNames= {"EmployeeId","EmployeeName", "EmployeeRole", "MobileNumber"};
			
			List<LinkedHashMap<String, Object>> lst = lObjConfigDao.getEmployeeMaster(outputMap,con);
			
			if(!exportFlag.isEmpty())
				{
					outputMap = getCommonFileGenerator(colNames,lst,exportFlag,DestinationPath,userId,"EmployeeMaster");
				}
			else
				{
					outputMap.put("ListOfEmployee", lst);	
					rs.setViewName("../Employee.jsp");
					rs.setReturnObject(outputMap);
				}			
			}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}
		rs.setReturnObject(outputMap);
		return rs;
	}
	
	public CustomResultObject addEmployee(HttpServletRequest request,Connection con) throws FileUploadException
	{		
		CustomResultObject rs=new CustomResultObject();	
		HashMap<String, Object> outputMap=new HashMap<>();				
		FileItemFactory itemFactory=new DiskFileItemFactory();
		ServletFileUpload upload=new ServletFileUpload(itemFactory);		
		HashMap<String,Object> hm=new HashMap<>();
		List<FileItem> toUpload=new ArrayList<FileItem>();
		if(ServletFileUpload.isMultipartContent(request))
		{
			List<FileItem> items=upload.parseRequest(request);
			for(FileItem item:items)
			{		
				
				if (item.isFormField()) 
				{
					hm.put(item.getFieldName(), item.getString());
			    }
				else
				{
					toUpload.add(item);
				}
			}			
		}	
		

			long employeeId=hm.get("hdnEmployeeId").equals("")?0l:Long.parseLong(hm.get("hdnEmployeeId").toString());
			String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
			hm.put("app_id", appId);
			try
			{								
				if(employeeId==0)
				{
					employeeId=lObjConfigDao.addEmployee(con, hm);
				}
				else
				{
					lObjConfigDao.updateEmployee(employeeId,con,hm);
				}			
				rs.setReturnObject(outputMap);			
				rs.setAjaxData("<script>window.location='?a=showEmployeeMaster'</script>");										
			}
			catch (Exception e)
			{
				writeErrorToDB(e);
					rs.setHasError(true);
			}		
			return rs;
		
			
	}
	public CustomResultObject deleteEmployee(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		long employeeId= Long.parseLong(request.getParameter("employeeId"));		
				
		try
		{			
			
			
						
			rs.setAjaxData(lObjConfigDao.deleteEmployee(employeeId,con));
			
			
							
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	public CustomResultObject showAddEmployee(HttpServletRequest request,Connection con) throws ClassNotFoundException, SQLException
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
		
		long employeeId=request.getParameter("employeeId")==null?0L:Long.parseLong(request.getParameter("employeeId"));
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);
		try
		{	
			if(employeeId!=0) {outputMap.put("employeeDetails", lObjConfigDao.getEmployeeDetails(employeeId,con));}
			

			outputMap.put("employeeList", lObjConfigDao.getEmployeeMaster(outputMap, con));
			rs.setViewName("../AddEmployee.jsp");	
			rs.setReturnObject(outputMap);
			
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	
	public CustomResultObject showChangePassword(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
		try
		{		 
			
			rs.setViewName("changePassword.jsp");
			rs.setReturnObject(outputMap);

		}
		catch (Exception e)
		{
			writeErrorToDB(e);
			rs.setHasError(true);
		}		
		return rs;
	}
	
	public CustomResultObject showReturnRegisterReport(HttpServletRequest request,Connection con) throws ClassNotFoundException, SQLException
	{
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
				

		List<LinkedHashMap<String, Object>> lst = lObjConfigDao.getfirmMaster(outputMap,con);
		
		try
		{
			outputMap.put("listReturnData", lst);					
			rs.setViewName("../ReturnRegisterParameter.jsp");
			rs.setReturnObject(outputMap);			
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}	
		
		return rs;
	}
	
	
	
	
	public CustomResultObject showChallans(HttpServletRequest request,Connection con) throws ClassNotFoundException, SQLException, ParseException
	{
		
		
		String firmId= request.getParameter("firmId")==null? "" : (request.getParameter("firmId").toString());
		
		if(firmId.equals(""))
		{
			firmId=(((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("firm_id"));
		}
		
		long app_id=Long.valueOf(((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id"));
		
		
		
		
		
		String type=request.getParameter("type")==null?"":request.getParameter("type");
		
		
		String fromDate=request.getParameter("txtfromdate");
		String toDate=request.getParameter("txttodate");
		if(fromDate==null || fromDate.equals(""))
		{
				fromDate=getDateFromDB(con);
		}
		
		if(toDate==null || toDate.equals(""))
		{
				toDate=getDateFromDB(con);
		}
		
		
		
		
		
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
		
		outputMap.put("type", type);
		outputMap.put("firmId", firmId);
		outputMap.put("app_id", app_id);
		
		outputMap.put("txtfromdate", fromDate);
		outputMap.put("txttodate", toDate);
		
		
		
				

		List<LinkedHashMap<String, Object>> lst;
		if(type.equals("O"))
		{
			lst=lObjConfigDao.getChallanOut(outputMap,con);
		}
		else
		{
			lst=lObjConfigDao.getChallanIn(outputMap,con);
		}
		
		try
		{
			
			outputMap.put("ListOffirms", lObjConfigDao.getfirmMaster(outputMap,con));	
			outputMap.put("listReturnData", lst);			
			rs.setViewName("../Challans.jsp");
			rs.setReturnObject(outputMap);			
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}	
		
		return rs;
	}
	
	
	public CustomResultObject showTransactions(HttpServletRequest request,Connection con) throws ClassNotFoundException, SQLException, ParseException
	{
		
		
		String firmId= request.getParameter("firmId")==null? "" : (request.getParameter("firmId").toString());
		
		if(firmId.equals(""))
		{
			firmId=(((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("firm_id"));
		}
		
		long app_id=Long.valueOf(((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id"));
		
		
		
		
		
		String type=request.getParameter("type")==null?"":request.getParameter("type");
		String paymentId=request.getParameter("payment_id")==null?"":request.getParameter("payment_id");
		
		
		
		String fromDate=request.getParameter("txtfromdate");
		String toDate=request.getParameter("txttodate");
		if(fromDate==null || fromDate.equals(""))
		{
				fromDate=getDateFromDB(con);
		}
		
		if(toDate==null || toDate.equals(""))
		{
				toDate=getDateFromDB(con);
		}
		
		
		
		
		
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
		
		outputMap.put("type", type);
		outputMap.put("firmId", firmId);
		outputMap.put("app_id", app_id);
		
		outputMap.put("txtfromdate", fromDate);
		outputMap.put("txttodate", toDate);
		
		
		
				

		
		
		try
		{
			
			outputMap.put("ListOffirms", lObjConfigDao.getfirmMaster(outputMap,con));
			
			
		
			
			
			outputMap.putAll(getTransactionsitem(outputMap,con));			
			rs.setViewName("../Transactions.jsp");
			rs.setReturnObject(outputMap);			
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}	
		
		return rs;
	}
	
	
	public LinkedHashMap<String, Object> getTransactionsitem(HashMap<String, Object> hm,Connection con) throws ClassNotFoundException, SQLException, ParseException	
	{
		LinkedHashMap<String, Object> lhm =new LinkedHashMap<>();
		List<LinkedHashMap<String, Object>> lst = lObjConfigDao.getTransactions(hm,con);
		lhm.put("listReturnData", lst);
		BigDecimal totalAmount=new BigDecimal(0);
		
		for(LinkedHashMap<String, Object> tempLM:lst)
		{
			totalAmount=totalAmount.add(new BigDecimal(tempLM.get("amount").toString()));
		}
		lhm.put("totalAmount", totalAmount);
		
		return lhm;
		
		
	}
	
	
	public LinkedHashMap<String, Object> getBankPassBookitem(HashMap<String, Object> hm,Connection con) throws ClassNotFoundException, SQLException, ParseException	
	{
		
		
		
		LinkedHashMap<String, Object> lhm =new LinkedHashMap<>();
		
		List<LinkedHashMap<String, Object>> lst = lObjConfigDao.getBankPassBook(hm,con);
		lhm.put("dailyInvoiceData", lst);
		LinkedHashMap<String, Object> totalDetails=gettotalDetailsBankPassBook(lst);			
		lhm.put("dailyInvoiceData", lst);
		lhm.put("totalDetails", totalDetails);			
		lhm.put("openingBalance", lObjConfigDao.getOpeningBalanceForThisBank(hm.get("fromDate").toString(), hm.get("bankId").toString(),hm.get("firmId").toString(), con));			
		BigDecimal OpeningBalance=new BigDecimal(lhm.get("openingBalance").toString());			
		BigDecimal creditTotal=new BigDecimal(totalDetails.get("creditTotal").toString());
		BigDecimal debitTotal=new BigDecimal(totalDetails.get("debitTotal").toString());			
		BigDecimal closingBalance=OpeningBalance.add(debitTotal).subtract(creditTotal);
		lhm.put("closingBalance", String.format("%.2f",closingBalance));
		
		return lhm;
		
		
	}
	
	public LinkedHashMap<String, Object> generateExpenseReportitem(HashMap<String, Object> hm,Connection con) throws ClassNotFoundException, SQLException, ParseException	
	{	
		LinkedHashMap<String, Object> lhm =new LinkedHashMap<>();

		
		List<LinkedHashMap<String, Object>> lst = lObjConfigDao.getExpenseReport(hm,con);
		BigDecimal totalAmount=new BigDecimal(0);
		
		for(LinkedHashMap<String, Object> tempHm:lst)
		{
			totalAmount=totalAmount.add(new BigDecimal(tempHm.get("fullamount").toString()));
		}
		
		lhm.put("totalExpenseAmount",totalAmount);
		lhm.put("dailyInvoiceData",lst);
		
		
		return lhm;
		
	}
	
	
	public CustomResultObject showJournals(HttpServletRequest request,Connection con) throws ClassNotFoundException, SQLException, ParseException
	{
		
		
		String firmId= request.getParameter("firmId")==null? "" : (request.getParameter("firmId").toString());
		
		if(firmId.equals(""))
		{
			firmId=(((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("firm_id"));
		}
		
		long app_id=Long.valueOf(((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id"));
		
		
		
		
		
		
		
		
		String fromDate=request.getParameter("txtfromdate");
		String toDate=request.getParameter("txttodate");
		if(fromDate==null || fromDate.equals(""))
		{
				fromDate=getDateFromDB(con);
		}
		
		if(toDate==null || toDate.equals(""))
		{
				toDate=getDateFromDB(con);
		}
		
		
		
		
		
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
		
		
		outputMap.put("firmId", firmId);
		outputMap.put("app_id", app_id);
		
		outputMap.put("txtfromdate", fromDate);
		outputMap.put("txttodate", toDate);
		
		
		
				

		
		try
		{
			List<LinkedHashMap<String, Object>> lst = lObjConfigDao.getJournals(outputMap,con);

			outputMap.put("ListOffirms", lObjConfigDao.getfirmMaster(outputMap,con));	
			outputMap.put("listReturnData", lst);			
			rs.setViewName("../Journals.jsp");
			rs.setReturnObject(outputMap);			
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}	
		
		return rs;
	}
	
	
	public CustomResultObject showTransferRegister(HttpServletRequest request,Connection con) throws ClassNotFoundException, SQLException, ParseException
	{
		
		
		String firmId= request.getParameter("firmId")==null? "" : (request.getParameter("firmId").toString());
		
		if(firmId.equals(""))
		{
			firmId=(((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("firm_id"));
		}
		
		long app_id=Long.valueOf(((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id"));
		
		
		
		
		
		
		
		
		String fromDate=request.getParameter("txtfromdate");
		String toDate=request.getParameter("txttodate");
		if(fromDate==null || fromDate.equals(""))
		{
				fromDate=getDateFromDB(con);
		}
		
		if(toDate==null || toDate.equals(""))
		{
				toDate=getDateFromDB(con);
		}
		
		
		
		
		
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
		
		
		outputMap.put("firmId", firmId);
		outputMap.put("app_id", app_id);
		
		outputMap.put("txtfromdate", fromDate);
		outputMap.put("txttodate", toDate);
		
		
		
				

		List<LinkedHashMap<String, Object>> lst = lObjConfigDao.getInternalTransfers(outputMap,con);
		
		try
		{
			
			outputMap.put("ListOffirms", lObjConfigDao.getfirmMaster(outputMap,con));	
			outputMap.put("listReturnData", lst);			
			rs.setViewName("../Transfers.jsp");
			rs.setReturnObject(outputMap);			
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}	
		
		return rs;
	}
	
	
	
	public CustomResultObject showInternalJournalRegister(HttpServletRequest request,Connection con) throws ClassNotFoundException, SQLException, ParseException
	{
		
		
		String firmId= request.getParameter("firmId")==null? "" : (request.getParameter("firmId").toString());
		
		if(firmId.equals(""))
		{
			firmId=(((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("firm_id"));
		}
		
		long app_id=Long.valueOf(((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id"));
		
		
		
		
		
		
		
		
		String fromDate=request.getParameter("txtfromdate");
		String toDate=request.getParameter("txttodate");
		if(fromDate==null || fromDate.equals(""))
		{
				fromDate=getDateFromDB(con);
		}
		
		if(toDate==null || toDate.equals(""))
		{
				toDate=getDateFromDB(con);
		}
		
		
		
		
		
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
		
		
		outputMap.put("firmId", firmId);
		outputMap.put("app_id", app_id);
		
		outputMap.put("txtfromdate", fromDate);
		outputMap.put("txttodate", toDate);
		
		
		
				

		List<LinkedHashMap<String, Object>> lst = lObjConfigDao.getInternalJournalTransfers(outputMap,con);
		
		try
		{
			
			outputMap.put("ListOffirms", lObjConfigDao.getfirmMaster(outputMap,con));	
			outputMap.put("listReturnData", lst);			
			rs.setViewName("../JournalTransfers.jsp");
			rs.setReturnObject(outputMap);			
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}	
		
		return rs;
	}
	
	
	public CustomResultObject showInvoices(HttpServletRequest request,Connection con) throws ClassNotFoundException, SQLException, ParseException
	{
		
		
		String firmId= request.getParameter("firmId")==null? "" : (request.getParameter("firmId").toString());
		
		if(firmId.equals(""))
		{
			firmId=(((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("firm_id"));
		}
		
		long app_id=Long.valueOf(((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id"));
		
		
		
		
		
		String type=request.getParameter("type")==null?"":request.getParameter("type");
		
		
		String fromDate=request.getParameter("txtfromdate");
		String toDate=request.getParameter("txttodate");
		if(fromDate==null || fromDate.equals(""))
		{
				fromDate=getDateFromDB(con);
		}
		
		if(toDate==null || toDate.equals(""))
		{
				toDate=getDateFromDB(con);
		}
		
		
		
		
		
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
		
		outputMap.put("type", type);
		outputMap.put("firmId", firmId);
		outputMap.put("app_id", app_id);
		
		outputMap.put("txtfromdate", fromDate);
		outputMap.put("txttodate", toDate);
		
		
		
				

		List<LinkedHashMap<String, Object>> lst;
		if(type.equals("P"))
		{
			lst=lObjConfigDao.getInvoicesPurchase(outputMap,con);
		}
		else
		{
			lst=lObjConfigDao.getInvoicesSales(outputMap,con);
		}
		
		try
		{
			
			outputMap.put("ListOffirms", lObjConfigDao.getfirmMaster(outputMap,con));	
			outputMap.put("listReturnData", lst);			
			rs.setViewName("../Invoices.jsp");
			rs.setReturnObject(outputMap);			
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}	
		
		return rs;
	}
	
	
	
	public CustomResultObject generateReturnRegisterReport(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
				
		String fromDate=request.getParameter("txtfromdate")==null?"":request.getParameter("txtfromdate");
		String toDate=request.getParameter("txttodate")==null?"":request.getParameter("txttodate");
		long firmId= request.getParameter("txtfirm")==null? -1 : Long.valueOf(request.getParameter("txtfirm").toString());
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);
		if(!fromDate.equals(""))
		{
			request.getSession().setAttribute("fromDate1",fromDate);
			request.getSession().setAttribute("toDate1",toDate);
			request.getSession().setAttribute("firmId1",firmId);
		}
		else
		{
			fromDate=request.getSession().getAttribute("fromDate1").toString();
			toDate=request.getSession().getAttribute("toDate1").toString();
			firmId=Long.valueOf(request.getSession().getAttribute("firmId1").toString());
		}
		
		
		
		HashMap<String, Object> hm= new HashMap<>();
		hm.put("fromDate", fromDate);
		hm.put("toDate", toDate);
		hm.put("firmId", firmId);
		String exportFlag= request.getParameter("exportFlag")==null?"":request.getParameter("exportFlag");
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+"/";	
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		 
		try
		{			
			String [] colNames= {"ReturnId","DetailsId","QuantityToReturn", "UpdatedBy"};
			
			List<LinkedHashMap<String, Object>> lst = lObjConfigDao.getReturnRegister(hm,con);
			
			if(!exportFlag.isEmpty())
			{
				outputMap = getCommonFileGenerator(colNames,lst,exportFlag,DestinationPath,userId,"ReturnRegister");
			}
			else
			{
				outputMap.put("ReturnRegister", lst);	
				rs.setViewName("../ReturnRegisterGenerated.jsp");
				rs.setReturnObject(outputMap);
			}
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}
		rs.setReturnObject(outputMap);
		return rs;
	}
	
	public CustomResultObject showSalesRegister(HttpServletRequest request,Connection con)  
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
				
		String exportFlag= request.getParameter("exportFlag")==null?"":request.getParameter("exportFlag");
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+"/";	
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		
		
		String fromDate=request.getParameter("txtfromdate")==null?"":request.getParameter("txtfromdate");
		String toDate=request.getParameter("txttodate")==null?"":request.getParameter("txttodate");
		String clientId=request.getParameter("clientId")==null?"":request.getParameter("clientId");
		
		if(!fromDate.equals(""))
		{
			request.getSession().setAttribute("fromDate1",fromDate);
			request.getSession().setAttribute("toDate1",toDate);
			request.getSession().setAttribute("clientId",clientId);		
		}
		else
		{
			fromDate=request.getSession().getAttribute("fromDate1").toString();
			toDate=request.getSession().getAttribute("toDate1").toString();
			clientId=request.getSession().getAttribute("clientId").toString();
			
		}
		
		
		
		
		try
		{			
			String [] colNames= {"formattedInvoiceDate","invoice_id","item_name","qty","qty_to_return","BilledQty","rate","custom_rate"
					,"DiscountAmount","itemAmount","formattedUpdatedDate"};
			
			List<LinkedHashMap<String, Object>> lst = lObjConfigDao.getClientInvoiceHistory(clientId,fromDate,toDate,con);			
			if(!exportFlag.isEmpty())
			{
				outputMap = getCommonFileGenerator(colNames,lst,exportFlag,DestinationPath,userId,"ClientInvoiceHistory");
			}
			else
			{
				outputMap.put("ClientInvoiceHistory", lst);				
				LinkedHashMap<String, Object> totalDetails=gettotalDetailsInvoiceHistory(lst);
								
				outputMap.put("totalDetails",totalDetails);
				outputMap.put("clientDetails", lObjConfigDao.getClientDetails(Long.valueOf(clientId),con));
				rs.setViewName("../ClientInvoiceHistoryGenerated.jsp");
				rs.setReturnObject(outputMap);
			}
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}
		rs.setReturnObject(outputMap);
		return rs;
	}
	
	
	
	
	
	
	
	
	
	
	public HashMap<String, Object> generateChallanPDFitem(HttpServletRequest request,Connection con) throws ClassNotFoundException, SQLException, ParseException
	{
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
				

		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+"/";
		String BufferedImagesFolderPath=request.getServletContext().getRealPath("BufferedImagesFolder")+"/";
		
		String challanId=request.getParameter("challanId").toString();
		String type=request.getParameter("type").toString();
		
		String appenders="Challan"+type+challanId+".pdf";
		DestinationPath+=appenders;
		
		
		
		String userId=request.getParameter("userId");
		if(userId==null || userId.equals(""))
		{
			userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		}
		
		outputMap.put("user_id",userId);
		
		try
		{			
			if(type.equals("I"))
			{
				new InvoiceHistoryPDFHelper().generatePDFForChallan(DestinationPath,BufferedImagesFolderPath, lObjConfigDao.getChallanInDetails(challanId, con), con,type);
			}
			else
			{
				new InvoiceHistoryPDFHelper().generatePDFForChallan(DestinationPath,BufferedImagesFolderPath, lObjConfigDao.getChallanOutDetails(challanId, con), con,type);
			}
			
			
			outputMap.put("returnData", appenders);
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				
		}	
		
		return outputMap;
	}
	
	
	public HashMap<String, Object> generatePdfStockTransferitem(HttpServletRequest request,Connection con) throws ClassNotFoundException, SQLException, ParseException
	{
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
				

		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+"/";
		String BufferedImagesFolderPath=request.getServletContext().getRealPath("BufferedImagesFolder")+"/";
		
		String modificationId=request.getParameter("modificationId").toString();		
		
		String appenders="StockTransfer"+modificationId+".pdf";
		DestinationPath+=appenders;
		
		
		
		String userId=request.getParameter("userId");
		if(userId==null || userId.equals(""))
		{
			userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		}
		
		outputMap.put("user_id",userId);
		
		try
		{			
			
				new InvoiceHistoryPDFHelper().generatePDFForTransfer(DestinationPath,BufferedImagesFolderPath, lObjConfigDao.getStockTransferDetails(modificationId, con), con);
			
			
			
			outputMap.put("returnData", appenders);
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				
		}	
		
		return outputMap;
	}
	
	
	
	public HashMap<String, Object> generateInvoicePDFitem(HttpServletRequest request,Connection con) throws ClassNotFoundException, SQLException, ParseException
	{
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
				

		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+"/";
		String BufferedImagesFolderPath=request.getServletContext().getRealPath("BufferedImagesFolder")+"/";
		
		String invoiceId=request.getParameter("invoiceId").toString();
		String type=request.getParameter("type").toString();
		
		String appenders="Invoice"+type+invoiceId+cf.getDateTimeWithSeconds(con)+".pdf";
		DestinationPath+=appenders;
		
		
		
		String userId=request.getParameter("userId");
		if(userId==null || userId.equals(""))
		{
			userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		}
		
		outputMap.put("user_id",userId);
		
		try
		{			
			if(type.equals("P"))
			{
				new InvoiceHistoryPDFHelper().generatePDFForInvoice(DestinationPath,BufferedImagesFolderPath, lObjConfigDao.getPurchaseInvoiceDetails(invoiceId, con), con,type);
			}
			else
			{
				new InvoiceHistoryPDFHelper().generatePDFForInvoice(DestinationPath,BufferedImagesFolderPath, lObjConfigDao.getSalesInvoiceDetails(invoiceId, con), con,type);
			}
			
			
			outputMap.put("returnData", appenders);
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				
		}	
		
		return outputMap;
	}
	
	
	
	
	String delimiter="/";
	private Object lst;

	public CustomResultObject exportClientLedgerAsPDF(HttpServletRequest request,Connection con) throws ClassNotFoundException, SQLException, ParseException
	{
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
				

		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+delimiter;
		String fromDate=request.getParameter("fromDate").toString();
		String toDate=request.getParameter("toDate").toString();
		String toDateDisplay=request.getParameter("toDate").toString();
		String clientId=request.getParameter("clientId").toString();
		String firmId=request.getParameter("clientId")==null?"":request.getParameter("firmId");

		
		List<LinkedHashMap<String, Object>> lst = lObjConfigDao.getClientLedgerReport(clientId,firmId,fromDate,toDate,con);
		
		
		LinkedHashMap<String, Object> totalDetails=gettotalDetailsLedger(lst);
		
		
		 Date toDateDate= new SimpleDateFormat("dd/MM/yyyy").parse(fromDate);

		 Calendar cal = Calendar.getInstance();
		 cal.setTime(toDateDate);
		 cal.add(Calendar.DATE, -1);
		 toDateDate= cal.getTime();
		 
		 toDate=new SimpleDateFormat("dd/MM/yyyy").format(toDateDate);
		 String startOfApplication="23/01/1992";
		 String pendingAmount=lObjConfigDao.getPendingAmountForThisClient(Long.valueOf(clientId),startOfApplication, toDate, con).get("PendingAmount");
		 
		 
		 Double openingAmount=pendingAmount==null?0:Double.parseDouble(pendingAmount);
			totalDetails.put("openingAmount", openingAmount);
			Double totalAmount= openingAmount - Double.parseDouble(totalDetails.get("debitSum").toString()) +  Double.parseDouble(totalDetails.get("creditSum").toString());
			totalDetails.put("totalAmount", String.format("%.2f", totalAmount));
			outputMap.put("totalDetails",totalDetails);
			
		 
		 
			

		
		LinkedHashMap<String, String> clientDetails= lObjConfigDao.getClientDetails(Long.valueOf(clientId),con);
		
		outputMap.put("fromDate",fromDate);
		outputMap.put("toDate",toDateDisplay);		
		//outputMap.put("totalDetails",gettotalDetailsLedger(lst));
		outputMap.put("clientDetails", clientDetails);
		try
		{
			String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
			
			
			String appenders="ClientLedger"+userId+clientDetails.get("client_name").replaceAll(" ", "")+"("+getDateASYYYYMMDD(fromDate)+")"+"("+getDateASYYYYMMDD(toDateDisplay)+")"+getDateTimeWithSeconds(con)+".pdf";
			DestinationPath+=appenders;
			outputMap.put("ListOfitemDetails", lst);
			new InvoiceHistoryPDFHelper().generatePDFForClientLedger(DestinationPath,outputMap,con);			
			outputMap.put("listReturnData", lst);
			outputMap.put(filename_constant, appenders);
			rs.setReturnObject(outputMap);						
			
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}	
		
		return rs;
	}
	
	public CustomResultObject showClientLedger(HttpServletRequest request,Connection con) throws SQLException, ClassNotFoundException
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
				
		String exportFlag= request.getParameter("exportFlag")==null?"":request.getParameter("exportFlag");
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+"/";	
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		
		
		String fromDate=request.getParameter("txtfromdate")==null?"":request.getParameter("txtfromdate");
		String toDate=request.getParameter("txttodate")==null?"":request.getParameter("txttodate");
		String clientId=request.getParameter("clientId")==null?"":request.getParameter("clientId");
		String firmId=request.getParameter("clientId")==null?"":request.getParameter("firmId");
		
		
	
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);
		
		// if parameters are blank then set to defaults
				if(fromDate.equals(""))
				{
					fromDate=lObjConfigDao.getDateFromDB(con);
				}
				if(toDate.equals(""))
				{
					toDate=lObjConfigDao.getDateFromDB(con);
				}
				
				
				outputMap.put("clientMaster", lObjConfigDao.getClientMaster(outputMap, con));
				outputMap.put("ListOffirms", lObjConfigDao.getfirmMaster(outputMap,con));
				outputMap.put("txtfromdate", fromDate);
				outputMap.put("txttodate", toDate);
				
				
				if(clientId.equals(""))
				{
					rs.setViewName("../ClientLedgerGenerated.jsp");			
					rs.setReturnObject(outputMap);
					return rs;
				}
		
		
		try
		{	
			
			
			
			
			
			
			String [] colNames= {"firm_name","Particular","transaction_date","type","RefId","debitAmount","creditAmount"};
			
			List<LinkedHashMap<String, Object>> lst = lObjConfigDao.getClientLedgerReport(clientId,firmId,fromDate,toDate,con);
			
			if(!exportFlag.isEmpty())
			{
				outputMap = getCommonFileGenerator(colNames,lst,exportFlag,DestinationPath,userId,"ClientInvoiceHistory");
			}
			else
			{
				
				LinkedHashMap<String, Object> totalDetails=gettotalDetailsLedger(lst);
				 Date toDateDate= new SimpleDateFormat("dd/MM/yyyy").parse(fromDate);

				 Calendar cal = Calendar.getInstance();
				 cal.setTime(toDateDate);
				 cal.add(Calendar.DATE, -1);
				 toDateDate= cal.getTime();
				 
				 toDate=new SimpleDateFormat("dd/MM/yyyy").format(toDateDate);
				 String startOfApplication="23/01/1992";
				 String pendingAmount=lObjConfigDao.getPendingAmountForThisClient(Long.valueOf(clientId),startOfApplication, toDate, con).get("pendingAmount");
				 BigDecimal openingAmount=pendingAmount==null?new BigDecimal(0l):new BigDecimal(pendingAmount);
				totalDetails.put("openingAmount", openingAmount);
				// Formula changes as  by Tejasbhai
				//Double totalAmount= openingAmount - Double.parseDouble(totalDetails.get("debitSum").toString()) +  Double.parseDouble(totalDetails.get("creditSum").toString());
				BigDecimal totalAmount= openingAmount.add(new BigDecimal(totalDetails.get("debitSum").toString())).subtract(new BigDecimal(totalDetails.get("creditSum").toString()));


				totalDetails.put("totalAmount", String.format("%.2f", totalAmount));
				outputMap.put("totalDetails",totalDetails);		
				
				outputMap.put("ListLedger",lst );
				outputMap.put("clientDetails",lObjConfigDao.getClientDetails(Long.valueOf(clientId),con));
				
				rs.setViewName("../ClientLedgerGenerated.jsp");
				rs.setReturnObject(outputMap);
			}
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}
		rs.setReturnObject(outputMap);
		return rs;
	}
	
	
	public LinkedHashMap<String, Object> gettotalDetailsLedger(List<LinkedHashMap<String, Object>> lst)
	{
		LinkedHashMap<String, Object> reqHM=new LinkedHashMap<>();
		double debitSum=0;double creditSum=0;
		for(LinkedHashMap<String, Object> tempHm:lst)
		{
			debitSum+=Double.valueOf(tempHm.get("debitAmount").toString());
			creditSum+=Double.valueOf(tempHm.get("creditAmount").toString());
		}
		
		reqHM.put("debitSum", String.format("%.2f",debitSum));
		reqHM.put("creditSum", String.format("%.2f",creditSum));		
		reqHM.put("totalAmount", String.format("%.2f",creditSum-debitSum) );
		return reqHM;
	}
	
	
	public LinkedHashMap<String, Object> gettotalDetailsBankPassBook(List<LinkedHashMap<String, Object>> lst)
	{
		LinkedHashMap<String, Object> reqHM=new LinkedHashMap<>();
		double debitTotal=0;
		double creditTotal=0;
		for(LinkedHashMap<String, Object> tempHm:lst)
		{
			if(tempHm.get("type").equals("P"))
			{
				creditTotal+=Double.valueOf(tempHm.get("amount").toString());
			}
			else
			{
				debitTotal+=Double.valueOf(tempHm.get("amount").toString());
			}
		}
		
		reqHM.put("debitTotal", String.format("%.2f",debitTotal));
		reqHM.put("creditTotal", String.format("%.2f",creditTotal));		
		
		return reqHM;
	}
	
	public LinkedHashMap<String, Object> getTotalDetailsPurchase(List<LinkedHashMap<String, Object>> lst)
	{
		LinkedHashMap<String, Object> reqHM=new LinkedHashMap<>();
		for(LinkedHashMap<String, Object> tempHm:lst)
		{
			if(reqHM.get(tempHm.get("Head"))==null)
			{
				reqHM.put(tempHm.get("Head").toString(),tempHm.get("amount").toString());
			}
			else
			{
				double amount=Double.valueOf(tempHm.get("amount").toString());
				double currentSum=Double.valueOf(reqHM.get(tempHm.get("Head")).toString());
				reqHM.put(tempHm.get("Head").toString(),amount+currentSum);

			}
			
		}
		
		
	
		
		return reqHM;
	}
	
	
	
	
	public LinkedHashMap<String, Object> gettotalDetailsInvoiceHistory(List<LinkedHashMap<String, Object>> lst)
	{
		LinkedHashMap<String, Object> reqHM=new LinkedHashMap<>();
		double itemAmountSum=0;
		double discountAmountSum=0;
		for(LinkedHashMap<String, Object> tempHm:lst)
		{
			itemAmountSum+=Double.valueOf(tempHm.get("itemAmount").toString());
			discountAmountSum+=Double.valueOf(tempHm.get("DiscountAmount").toString());		
			
		}
		reqHM.put("itemAmountSum", String.format("%.2f",itemAmountSum) );
		reqHM.put("discountAmountSum", String.format("%.2f",discountAmountSum));
		
		return reqHM;
	}
	
	
	
	public CustomResultObject showClientitemHistory(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
				
		String exportFlag= request.getParameter("exportFlag")==null?"":request.getParameter("exportFlag");
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+"/";	
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		
		
		String fromDate=request.getParameter("txtfromdate")==null?"":request.getParameter("txtfromdate");
		String toDate=request.getParameter("txttodate")==null?"":request.getParameter("txttodate");
		
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);
		if(!fromDate.equals(""))
		{
			request.getSession().setAttribute("fromDate1",fromDate);
			request.getSession().setAttribute("toDate1",toDate);
			
		}
		else
		{
			fromDate=request.getSession().getAttribute("fromDate1").toString();
			toDate=request.getSession().getAttribute("toDate1").toString();
			
		}
		
		String clientId=request.getParameter("clientId")==null?"":request.getParameter("clientId");
		
		
		try
		{	
			String [] colNames= {"formattedInvoiceDate","invoice_id","item_name","qty","qty_to_return","BilledQty","rate","custom_rate"
					,"DiscountAmount","itemAmount","formattedUpdatedDate"};
			
			List<LinkedHashMap<String, Object>> lst = lObjConfigDao.getClientItemHistory(appId,clientId,fromDate,toDate,con);
			
			if(!exportFlag.isEmpty())
			{
				outputMap = getCommonFileGenerator(colNames,lst,exportFlag,DestinationPath,userId,"ClientInvoiceHistory");
			}
			else
			{
				outputMap.put("ClientInvoiceHistory", lst);	
				rs.setViewName("../ClientitemHistoryGenerated.jsp");
				rs.setReturnObject(outputMap);
			}
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}
		rs.setReturnObject(outputMap);
		return rs;
	}
	
	
	
	public CustomResultObject showClientitemRegisterParameter(HttpServletRequest request,Connection con) throws ClassNotFoundException, SQLException
	{
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);
		try
		{
			List<LinkedHashMap<String, Object>> lst = lObjConfigDao.getClientMaster(outputMap, con);
			outputMap.put("clientList", lst);
			rs.setViewName("../ClientitemReportParameter.jsp");
			rs.setReturnObject(outputMap);			
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}	
		
		return rs;
	}
	
	
	public HashMap<String, Object> getDataForGenerateInvoiceScreen(HttpServletRequest request,Connection con) throws ClassNotFoundException, SQLException
	
	  {
		HashMap<String, Object> outputMap=new HashMap<>();
		
		outputMap.put("customerId", request.getParameter("customerId"));
		
		if(request.getParameter("customerId")!=null && !request.getParameter("customerId").equals(""))
		{
			outputMap.put("routineDetails", lObjConfigDao.getRoutineDetailsForThisClient(request.getParameter("customerId"), con));
			outputMap.put("customerDetails", lObjConfigDao.getClientDetails(Long.valueOf(request.getParameter("customerId")), con));
		}
		
		
		outputMap.put("categoryId", request.getParameter("categoryId"));
		
		outputMap.put("app_id", request.getParameter("appId")); 
		
		List<LinkedHashMap<String, Object>> itemMasterList= lObjConfigDao.getServiceMaster(outputMap, con);		
		List<LinkedHashMap<String, Object>> filteredItemMasterList=new ArrayList<>();
		for(LinkedHashMap<String, Object> item:itemMasterList)
		{
			LinkedHashMap<String, Object> tempHm=new LinkedHashMap<>();
			tempHm.put("item_id", item.get("item_id"));
			tempHm.put("parent_category_id", item.get("parent_category_id"));
			tempHm.put("debit_in", item.get("debit_in"));
			tempHm.put("item_name", item.get("item_name"));
			tempHm.put("product_code", item.get("product_code"));
			tempHm.put("category_name", item.get("category_name"));
			tempHm.put("price", item.get("price"));
			tempHm.put("ImagePath", item.get("ImagePath"));
			filteredItemMasterList.add(tempHm);
		}
		outputMap.put("itemMaster", filteredItemMasterList);
		
		
		List<LinkedHashMap<String, Object>> custList= lObjConfigDao.getClientMaster(outputMap, con);		
		List<LinkedHashMap<String, Object>> filteredCustList=new ArrayList<>();
		for(LinkedHashMap<String, Object> custom:custList)
		{
			LinkedHashMap<String, Object> tempHm=new LinkedHashMap<>();
			tempHm.put("customerId", custom.get("customerId"));
			tempHm.put("customerName", custom.get("customerName"));
			tempHm.put("mobileNumber", custom.get("mobileNumber"));
			filteredCustList.add(tempHm);
		}
		outputMap.put("customerMaster", filteredCustList);
		
		
		outputMap.put("categoryMaster", lObjConfigDao.getCategoryMaster(outputMap,con));
		return outputMap;
	  }
	
	
	public HashMap<String, Object> getInvoiceDetailsitem(HttpServletRequest request,Connection con) throws ClassNotFoundException, SQLException	
	  {
		HashMap<String, Object> outputMap=new HashMap<>();		
		String invoiceId=request.getParameter("invoice_id");
		outputMap.put("invoiceDetails", lObjConfigDao.getInvoiceDetails(invoiceId,con));	
		return outputMap;
	  }
	
	public LinkedHashMap<String,String> validateLoginForApp(HttpServletRequest request,Connection con)
	{		
		LinkedHashMap<String, String> returnMap=null;
		String number = (request.getParameter("number").toString());
		String password = URLDecoder.decode(request.getParameter("password").toString());
		
					
		
		try
		{
			returnMap=lObjConfigDao.validateLoginForApp(number,password,con);
			if(returnMap.get("user_id")!=null)
			{
				returnMap.put("validLogin", "true");
			}
		}
		catch (Exception e)
		{
			writeErrorToDB(e);				
		}
		
		return returnMap;
	}
	
	
	
	public LinkedHashMap<String,Object> getRecentInvoiceForThisUser(HttpServletRequest request,Connection con)
	{		
		LinkedHashMap<String, Object> returnMap=new LinkedHashMap<>();
		String userId = (request.getParameter("userId").toString());
		String appId = (request.getParameter("appId").toString());
		returnMap.put("user_id", userId);
		returnMap.put("app_id", appId);
		try
		{
			returnMap.put("ListOfInvoices",lObjConfigDao.getRecentInvoiceForUser(returnMap, con));
		}
		catch (Exception e)
		{
			writeErrorToDB(e);				
		}
		
		return returnMap;
	}
	
	
	
	
	public CustomResultObject addBooking(HttpServletRequest request,Connection con) throws ClassNotFoundException, SQLException
	{
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> hm=new HashMap<>();
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		hm.put("app_id", appId);
		try 
		{
		Enumeration<String> params = request.getParameterNames(); 
		
		List<HashMap<String, Object>> itemListRequired=new ArrayList<HashMap<String, Object>>();
		while(params.hasMoreElements())
		{
		 String paramName = params.nextElement();
		 
		 if(paramName.equals("itemDetails")) 
		 {
			 String[] itemsList=request.getParameter(paramName).split("\\|");
			 for(String item:itemsList)
			 {
				 String itemDetails[] =item.split("~");
				 HashMap<String, Object> itemDetailsMap=new HashMap<>();
				 itemDetailsMap.put("item_id",itemDetails[0]);
				 itemDetailsMap.put("qty",itemDetails[1]);				 
				 itemListRequired.add(itemDetailsMap);				 
				 //ID, QTY
			 }
			 hm.put("itemDetails", itemListRequired);
			 continue;
		 }		 
		 hm.put(paramName, request.getParameter(paramName));
		 
		}
		if(lObjConfigDao.checkIfBookingAlreadyExist(hm,con))
		{
			rs.setReturnObject(hm);		
			rs.setAjaxData("Booking Already Exists for another Customer");
			return rs;
		}
		long bookingId=lObjConfigDao.saveBooking(hm,con);
		for(HashMap<String, Object> item:itemListRequired)
		{
			item.put("bookingId", bookingId);
			lObjConfigDao.saveBookingItems(item,con);
		}
		}
		catch(Exception e)
		{
			
			e.printStackTrace();
			rs.setHasError(true);
		}
		rs.setReturnObject(hm);		
		rs.setAjaxData("Booking Updated Succesfully");
		return rs;
	}	
	
	
	

	
	
	
	
	
	
	public HashMap<String, Object> getitemsByCategoryIdApp(HttpServletRequest request,Connection con)
	{		
		HashMap<String, Object> returnMap=null;
		long category_id=Long.valueOf(request.getParameter("category_id").toString());
		long appId=Long.parseLong(request.getParameter("appId"));
		
		try
		{
			returnMap=new HashMap<>();
			returnMap.put("productsList",lObjConfigDao.getProductsByCategoryId(appId,category_id,con));			
		}
		catch (Exception e)
		{
			writeErrorToDB(e);				
		}
		return returnMap;			
	}


	
	public HashMap<String, Object> SearchitemsByString(HttpServletRequest request,Connection con)
	{		
		HashMap<String, Object> returnMap=null;
		String lStrSearchText=request.getParameter("searchtext").toString();
		long appId=Long.parseLong(request.getParameter("appId"));
		try
		{
			returnMap=new HashMap<>();
			returnMap.put("productsList",lObjConfigDao.getItemsBySearchString(appId,lStrSearchText,con));			
		}
		catch (Exception e)
		{
			writeErrorToDB(e);				
		}
		return returnMap;			
	}

	
	
	
	public Long getWalletAmountForThisNumber(String number,Connection con) throws ClassNotFoundException, SQLException
	{	
		
		
		
		PreparedStatement stmnt=null;
		ResultSet rs=null;
		Long amount = 0L;
		
		
		
		
		try
		{		
			
			stmnt=con.prepareStatement("SELECT SUM(cashback_amount) FROM " +
					"trn_order_register order1, " +
					"trn_cashback_register cashback " +
					"WHERE order1.number=? " +
					"AND cashback.orderId=order1.order_id");	
			stmnt.setString(1, number);
			
			rs = stmnt.executeQuery();
						
			while (rs.next()) 
			{		
				amount = rs.getLong(1);
			}
		
		}
		catch(Exception e)
		{
			writeErrorToDB(e);
		}
		finally
		{
			
			if(!rs.isClosed())
				{rs.close();}		
			if(!stmnt.isClosed())
				{stmnt.close();}
			
		}
	
		return amount;
	}
	
	

	public CustomResultObject showInvoiceConfig(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();		
		try
		{
			rs.setViewName("../InvoiceConfig.jsp");	
			rs.setReturnObject(showInvoiceConfigitem(request, con));		
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	
	public HashMap<String, Object> showInvoiceConfigitem(HttpServletRequest request,Connection con)
	{
					
		HashMap<String, Object> outputMap=new HashMap<>();
		
		String appId=request.getParameter("appId");
		if(appId==null || appId.equals(""))
		{
			appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		}
		outputMap.put("app_id", appId);
		String userId=request.getParameter("userId");
		if(userId==null||userId.equals("") )
		{
			userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		}
		try
		{
			outputMap.put("listOfInvoiceFormats", lObjConfigDao.getInvoiceFormatList(outputMap,con));
			outputMap.put("userDetails", lObjConfigDao.getuserDetailsById(Long.valueOf(userId),con));				
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				
		}		
		return outputMap;
	}
	
	
	public CustomResultObject generateChallanPDF(HttpServletRequest request,Connection con) 
	{
		CustomResultObject rs=new CustomResultObject();
				

		
		
		String challanId=request.getParameter("challanId");
		String type=request.getParameter("type");
		
		
		String appenders="Challan"+type+challanId+".pdf";
		
		
		try
		{			
			
			
			generateChallanPDFitem(request,con);
			
			
			rs.setAjaxData(appenders);						
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}	
		
		return rs;
	}
	
	public CustomResultObject generateInvoicePDF(HttpServletRequest request,Connection con) throws SQLException 
	{
		CustomResultObject rs=new CustomResultObject();
		String invoiceId=request.getParameter("invoiceId");
		String type=request.getParameter("type");
		
		try
		{
			HashMap<String,Object> hm=generateInvoicePDFitem(request,con);
			rs.setAjaxData(hm.get("returnData").toString());						
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}
		return rs;
	}
	
	public CustomResultObject generatePdfStockTransfer(HttpServletRequest request,Connection con) 
	{
		CustomResultObject rs=new CustomResultObject();
				

		
		
		String modificationId=request.getParameter("modificationId");
		
		
		
		String appenders="StockTransfer"+modificationId+".pdf";
		
		
		try
		{			
			
			
			generatePdfStockTransferitem(request,con);
			
			
			rs.setAjaxData(appenders);						
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}	
		
		return rs;
	}
	
	
	public CustomResultObject generatePdfAddStock(HttpServletRequest request,Connection con) 
	{
		CustomResultObject rs=new CustomResultObject();
				

		
		
		String modificationId=request.getParameter("modificationId");
		
		
		
		
		String type=request.getParameter("type").toString();
		
		String appenders="AddStock"+modificationId+type+".pdf";
		
		try
		{			
			
			
			generatePdfAddStockitem(request,con);
			
			
			rs.setAjaxData(appenders);						
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}	
		
		return rs;
	}
	
	
	public HashMap<String, Object> generatePdfAddStockitem(HttpServletRequest request,Connection con) throws ClassNotFoundException, SQLException, ParseException
	{
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
				

		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+"/";
		String BufferedImagesFolderPath=request.getServletContext().getRealPath("BufferedImagesFolder")+"/";
		
		String modificationId=request.getParameter("modificationId").toString();
		String type=request.getParameter("type").toString();
		
		String appenders="AddStock"+modificationId+type+".pdf";
		DestinationPath+=appenders;
		
		
		
		String userId=request.getParameter("userId");
		if(userId==null || userId.equals(""))
		{
			userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		}
		
		outputMap.put("user_id",userId);
		
		try
		{			
			
				new InvoiceHistoryPDFHelper().generatePDFForAddStock(DestinationPath,BufferedImagesFolderPath, lObjConfigDao.getAddStockDetails(modificationId, con), con,type);
			
			
			
			outputMap.put("returnData", appenders);
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				
		}	
		
		return outputMap;
	}
	
	public CustomResultObject internalBankTransfer(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		String date= request.getParameter("txtdate");
		String fromBank= request.getParameter("frombank");
		String toBank= request.getParameter("tobank");
		String amount= request.getParameter("amount");
		String remarks= request.getParameter("remarks");
		String fromfirm= request.getParameter("fromfirm");
		String tofirm= request.getParameter("tofirm");
		
		
		
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		
		
		
		HashMap<String, Object> outputMap=new HashMap<>();
				
		String returnAjaxString;
		try
		{	
			
			

			
			outputMap.put("date",date);
			outputMap.put("client_id",0);
			outputMap.put("bankId",fromBank);
			outputMap.put("total_amount",Double.parseDouble(amount));			
			outputMap.put("remarks",remarks);
			
			outputMap.put("firm_id",fromfirm);
			
			outputMap.put("from_firm",fromfirm);
			outputMap.put("to_firm",tofirm);
			
			outputMap.put("from_account",fromBank);
			outputMap.put("to_account",toBank);
			
			outputMap.put("app_id", appId);
			outputMap.put("user_id", userId);
			outputMap.put("type", "P");		
			
			long tranferId=lObjConfigDao.saveInternalTransfer(outputMap,con);

			
			long fromPaymentId=lObjConfigDao.savePaymentRegister(outputMap,con);
			
			
			outputMap.put("bankId",toBank);
			outputMap.put("total_amount",Double.parseDouble(amount));
			outputMap.put("firm_id",tofirm);
			outputMap.put("type", "R");
			
			
			
			long toPaymentId=lObjConfigDao.savePaymentRegister(outputMap,con);
			
			outputMap.put("transfer_id", tranferId);
			outputMap.put("to_payment_id", toPaymentId);
			outputMap.put("from_payment_id", fromPaymentId);
			
			lObjConfigDao.saveTransferPaymentMapping(outputMap,con);
			
				rs.setAjaxData("Updated");
			
			
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	public CustomResultObject saveInternalJournalTransfer(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		String date= request.getParameter("txtdate");		
		String amount= request.getParameter("amount");
		String remarks= request.getParameter("remarks");
		String fromfirm= request.getParameter("fromfirm");
		String tofirm= request.getParameter("tofirm");
		
		
		
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		
		
		
		HashMap<String, Object> outputMap=new HashMap<>();
				
		String returnAjaxString;
		try
		{	
			
			

			
			outputMap.put("date",date);
			outputMap.put("client_id",0);
			
			outputMap.put("total_amount",Double.parseDouble(amount));			
			outputMap.put("remarks",remarks);
			
			outputMap.put("firm_id",fromfirm);
			
			outputMap.put("from_firm",fromfirm);
			outputMap.put("to_firm",tofirm);
			
			
			outputMap.put("app_id", appId);
			outputMap.put("user_id", userId);
			
			long tranferId=lObjConfigDao.saveJournalInternalTransfer(outputMap,con);

			
			
				rs.setAjaxData("Updated");
			
			
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	
	public CustomResultObject showStockStatusClubbed(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
		
		String exportFlag= request.getParameter("exportFlag")==null?"":request.getParameter("exportFlag");
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+"/";	
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		String categoryId=request.getParameter("categoryId");	
		outputMap.put("categoryId", categoryId);
		
		String searchString=request.getParameter("searchString");	
		outputMap.put("searchString", searchString);
		
		String firmId=request.getParameter("firmId");	
		outputMap.put("firmId", firmId);
		
		String warehouseid=request.getParameter("warehouseid");	
		outputMap.put("warehouseid", warehouseid);
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);
		
		try
		{			
			List<LinkedHashMap<String, Object>> requiredList=new ArrayList<>();
			String [] colNames= {"firm_name","item_name","product_code","size","color","sumQty"};
			
			List<LinkedHashMap<String, Object>> lst=null;
			if(outputMap.get("searchString")!=null
					|| outputMap.get("warehouseid")!=null 
							|| outputMap.get("categoryId")!=null 
									|| outputMap.get("firmId")!=null )
			{
				lst =  lObjConfigDao.getStockStatusClubbed(outputMap,con);
			}
			
			
			
			if(!exportFlag.isEmpty())
			{
				outputMap = getCommonFileGenerator(colNames,lst,exportFlag,DestinationPath,userId,"StockStatus");
			}
			else
			{
				outputMap.put("ListStock", lst);
				outputMap.put("ListOfCategories", lObjConfigDao.getCategories(outputMap,con));
				outputMap.put("listOffirm", lObjConfigDao.getfirmMaster(outputMap,con));
				outputMap.put("listofwarehouse", lObjConfigDao.getListOfWareHouse(outputMap,con));
				
				//outputMap.put("totalDetails", totalDetails);
				
				
				rs.setViewName("../StockStatusClubbed.jsp");
				rs.setReturnObject(outputMap);
			}
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}
		rs.setReturnObject(outputMap);
		return rs;
	}
	
	
	
	

	public CustomResultObject exportStockStatusClubbedAsPDF(HttpServletRequest request,Connection con) throws SQLException, ClassNotFoundException
	{

		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
				

		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+delimiter;		
		String DestinationPathBuffered=request.getServletContext().getRealPath("BufferedImagesFolder")+delimiter;
		
		String searchString=request.getParameter("searchString").toString();
		String warehouseid=request.getParameter("warehouseid").toString();
		String categoryId=request.getParameter("categoryId").toString();
		String firmId=request.getParameter("firmId").toString();	
		
		
		outputMap.put("searchString", searchString);
		outputMap.put("warehouseid",warehouseid);
		outputMap.put("categoryId",categoryId);	
		outputMap.put("firmId", firmId);
		
		
		List<LinkedHashMap<String, Object>> lst =  lObjConfigDao.getStockStatusClubbed(outputMap,con);

	
		try
		{
			String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");		
			
			//String appenders="StockStatusClubbed"+userId.replaceAll(" ", "")+"("+firmId+")"+".pdf";
			
			String appenders="StockStatusClubbed"+firmId+userId+".pdf";
			DestinationPath+=appenders;
			outputMap.put("ListOfitemDetails", lst);
			new InvoiceHistoryPDFHelper().generatePDFForStockStatusClubbed(DestinationPath,DestinationPathBuffered,outputMap,con);			
			outputMap.put("listReturnData", lst);
			outputMap.put(filename_constant, appenders);
			rs.setReturnObject(outputMap);						
			
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}	
		
		return rs;
	
	}
	
	public CustomResultObject exportStockStatusSales(HttpServletRequest request,Connection con) throws SQLException, ClassNotFoundException
	{

		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
				

		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+delimiter;		
		String DestinationPathBuffered=request.getServletContext().getRealPath("BufferedImagesFolder")+delimiter;
		
		String searchString=request.getParameter("searchString").toString();
		String warehouseid=request.getParameter("warehouseid").toString();
		String categoryId=request.getParameter("categoryId").toString();
		String firmId=request.getParameter("firmId").toString();	
		
		
		outputMap.put("searchString", searchString);
		outputMap.put("warehouseid",warehouseid);
		outputMap.put("categoryId",categoryId);	
		outputMap.put("firmId", firmId);
		
		
		List<LinkedHashMap<String, Object>> lst =  lObjConfigDao.getStockStatusClubbed(outputMap,con);

	
		try
		{
			String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");		
			
			//String appenders="StockStatusClubbed"+userId.replaceAll(" ", "")+"("+firmId+")"+".pdf";
			
			String appenders="StockStatusSales"+firmId+userId+".pdf";
			DestinationPath+=appenders;
			outputMap.put("ListOfitemDetails", lst);
			new InvoiceHistoryPDFHelper().generatePDFForStockStatusClubbed(DestinationPath,DestinationPathBuffered,outputMap,con);			
			outputMap.put("listReturnData", lst);
			outputMap.put(filename_constant, appenders);
			rs.setReturnObject(outputMap);						
			
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}	
		
		return rs;
	
	}
	
	
	
	public CustomResultObject generateCostSheetReport(HttpServletRequest request,Connection con) throws SQLException, ClassNotFoundException
	{
		
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
				
		String exportFlag= request.getParameter("exportFlag")==null?"":request.getParameter("exportFlag");
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+"/";	
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		
		
		
		String jobsheetno=request.getParameter("jobsheetno")==null?"":request.getParameter("jobsheetno");
	
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);
		
		if(jobsheetno.equals(""))
		{
			rs.setViewName("../CostSheetReportGenerate.jsp");
			rs.setReturnObject(outputMap);
			return rs;

		}
		
		try
		{
			
			
		
			outputMap.put("jobsheetno",jobsheetno);
			
			outputMap.put("requiredData",getCostSheetData(jobsheetno, con));
			
				rs.setViewName("../CostSheetReportGenerated.jsp");
				rs.setReturnObject(outputMap);
			
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}
		rs.setReturnObject(outputMap);
		return rs;
	}
	
	public CustomResultObject showCreateEnquiry(HttpServletRequest request, Connection con) {
		CustomResultObject rs = new CustomResultObject();
		HashMap<String, Object> outputMap = new HashMap<>();

		String firm_id = ((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("firm_id");
		outputMap.put("firm_id", firm_id);

		String app_id = ((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", app_id);

		try {
			// if(categoryId!=0) { outputMap.put("categoryDetails",
			// lObjConfigDao.getCategoryDetails(outputMap ,connections));}
			outputMap.put("clientMaster", lObjConfigDao.getClientMaster(outputMap, con));

			rs.setViewName("../CreateEnquiry.jsp");
			rs.setReturnObject(outputMap);
		} catch (Exception e) {
			writeErrorToDB(e);
			rs.setHasError(true);
		}
		return rs;
	}
	
	
	
	
	
	public CustomResultObject addEnquiry(HttpServletRequest request, Connection con) throws FileUploadException {

		CustomResultObject rs = new CustomResultObject();
		HashMap<String, Object> outputMap = new HashMap<>();		
		String enquiryId = ((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("enquiry_id");
		
		String firmId = ((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("firm_id");

		String userId = ((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");

		HashMap<String, Object> hm = new HashMap<>();
		hm.put("user_id", userId);
		hm.put("enquiry_id", enquiryId);
		hm.put("firm_id", firmId);
		
		long clientId=request.getParameter("hdnSelectedClientType")==null?0L:Long.parseLong(request.getParameter("clientId"));
		try {

			if (clientId != 0) {
				clientId = lObjConfigDao.addEnquiry(con, hm);
			} 

			rs.setReturnObject(outputMap);

			rs.setAjaxData("<script>alert('Updated Succesfully');window.location='?a=showCreateEnquiry'</script>");

		} 
		catch (Exception e) {
			writeErrorToDB(e);
			rs.setHasError(true);
		}
		return rs;

	}
	
	
	
	public CustomResultObject showEnquiries(HttpServletRequest request, Connection con)
			throws ClassNotFoundException, SQLException {
		CustomResultObject rs = new CustomResultObject();
		HashMap<String, Object> outputMap = new HashMap<>();

		String exportFlag = request.getParameter("exportFlag") == null ? "" : request.getParameter("exportFlag");
		String DestinationPath = request.getServletContext().getRealPath("BufferedImagesFolder") + "/";
		String userId = ((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		String categoryId = request.getParameter("categoryId");
		outputMap.put("categoryId", categoryId);

		String firmId = request.getParameter("firmId");
		outputMap.put("firmId", firmId);
		
		String appId = ((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);
		
		String fromDate = request.getParameter("txtfromdate") == null ? "" : request.getParameter("txtfromdate");
		String toDate = request.getParameter("txttodate") == null ? "" : request.getParameter("txttodate");

		if (fromDate.equals("")) {
			fromDate = lObjConfigDao.getDateFromDB(con);
		}
		if (toDate.equals("")) {
			toDate = lObjConfigDao.getDateFromDB(con);
		}

		outputMap.put("fromDate", fromDate);
		outputMap.put("toDate", toDate);

		try {
			List<LinkedHashMap<String, Object>> requiredList = new ArrayList<>();
			String[] colNames = { "enquiryId", "message", "receivedDate"};

			List<LinkedHashMap<String, Object>> lst = lObjConfigDao.getenquiries(outputMap, con);

			
			rs.setViewName("../showEnquiries.jsp");
			rs.setReturnObject(outputMap); 
			
			
		} catch (Exception e) {
			writeErrorToDB(e);
			rs.setHasError(true);
		}
		rs.setReturnObject(outputMap);
		return rs;
	}
	
	public CustomResultObject exportCostSheetReportPdf(HttpServletRequest request,Connection con) throws SQLException, ClassNotFoundException
	{
		
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
				
		String exportFlag= request.getParameter("exportFlag")==null?"":request.getParameter("exportFlag");
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+"/";	
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		
		
		
		String jobsheetno=request.getParameter("jobsheetno")==null?"":request.getParameter("jobsheetno");
	
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);
		
		try
		{
			
			
			HashMap<String, Object> hm= getCostSheetData(jobsheetno, con);
			
			outputMap.put("jobsheetno",jobsheetno);
			outputMap.put("requiredData",hm);
			
					
			String appenders="CostSheetReport"+userId+"("+jobsheetno+")"+getDateTimeWithSeconds(con)+".pdf";
			DestinationPath+=appenders;
			outputMap.put("ListOfitemDetails", hm);
			new InvoiceHistoryPDFHelper().generatePDFForCostSheetReport(DestinationPath,outputMap,con);			
			outputMap.put("listReturnData", hm);
			outputMap.put(filename_constant, appenders);
			rs.setReturnObject(outputMap);						
					
				
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}
		rs.setReturnObject(outputMap);
		return rs;
	}
	
	
	private BigDecimal getTotalAmountStockStatus(List<LinkedHashMap<String, Object>> stockStatusList) 
	{
		BigDecimal valueTotal=new BigDecimal(0);
		for(LinkedHashMap<String, Object> hm:stockStatusList)
		{
			valueTotal=valueTotal.add(new BigDecimal(hm.get("value1").toString()));
		}
		return valueTotal;
	}
	
	
	/*
	 * public CustomResultObject deletePaymenttransaction(HttpServletRequest
	 * request,Connection con) { CustomResultObject rs=new CustomResultObject();
	 * long paymentId= Long.parseLong(request.getParameter("paymentid"));
	 * 
	 * try { rs.setAjaxData(lObjConfigDao.deletePaymentTransaction(paymentId,con));
	 * 
	 * } catch (Exception e) { writeErrorToDB(e); rs.setHasError(true); } return rs;
	 * }
	 */
	
	public CustomResultObject deletePaymentTransaction(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		long paymentId= Integer.parseInt(request.getParameter("paymentid")); 
		HashMap<String, Object> outputMap=new HashMap<>();				
	
		try
		{
			rs.setAjaxData(lObjConfigDao.deletePaymentTransaction(paymentId,con));						
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	public CustomResultObject showStockStatusSales(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
		
		String exportFlag= request.getParameter("exportFlag")==null?"":request.getParameter("exportFlag");
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+"/";	
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		String categoryId=request.getParameter("categoryId");	
		outputMap.put("categoryId", categoryId);
		
		String searchString=request.getParameter("searchString");	
		outputMap.put("searchString", searchString);
		
		String firmId=request.getParameter("firmId");	
		outputMap.put("firmId", firmId);
		
		String warehouseid=request.getParameter("warehouseid");	
		outputMap.put("warehouseid", warehouseid);
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);
		
		try
		{			
			List<LinkedHashMap<String, Object>> requiredList=new ArrayList<>();
			String [] colNames= {"firm_name","item_name","product_code","size","color","sumQty"};
			
			List<LinkedHashMap<String, Object>> lst =  lObjConfigDao.getStockStatusClubbed(outputMap,con);
			
			
			if(!exportFlag.isEmpty())
			{
				outputMap = getCommonFileGenerator(colNames,lst,exportFlag,DestinationPath,userId,"StockStatus");
			}
			else
			{
				outputMap.put("ListStock", lst);
				outputMap.put("ListOfCategories", lObjConfigDao.getCategories(outputMap,con));
				outputMap.put("listOffirm", lObjConfigDao.getfirmMaster(outputMap,con));
				outputMap.put("listofwarehouse", lObjConfigDao.getListOfWareHouse(outputMap,con));
				
				//outputMap.put("totalDetails", totalDetails);
				
				
				rs.setViewName("../StockStatusSales.jsp");
				rs.setReturnObject(outputMap);
			}
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}
		rs.setReturnObject(outputMap);
		return rs;
	}
	
	public CustomResultObject sendResetPasswordEmail(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		String emailid= request.getParameter("emailid");	
		String returnMessage="";
		try
		{	
			
			
			// check if the provided email exists in our system
			if(! lObjConfigDao.getuserDetailsByEmailId(emailid, con).isEmpty())
			{
					
				List<String> ls=new ArrayList<>();
				String newTempPassword= cf.getRandomNumberByLength(10);
				sendMessage(emailid,"crystaldevelopers2017@gmail.com",ls,ls,"Your Temporary Password for 'Hisaab Cloud Application' is <b>"+newTempPassword+"</b>","Reset Password Requested");
				new LoginDaoImpl().changePasswordByEmailId(emailid,newTempPassword,con);
				returnMessage="Temporary Password Sent to "+emailid+ " Succesfully ";
			}
			else
			{
				returnMessage="Please check the email and try again";
			}
			
			
			rs.setAjaxData(returnMessage);			
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	public void sendMessage(String toAddress,String ccAddress,List<String> CCOtherEmailsList,List<String> attachmentPaths,String emailContent,String emailSubject) throws AddressException, MessagingException
	{

		
		Properties props = new Properties();
		/*System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");*/
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		//props.put("mail.debug", "true");
		
		
		
		
		
		
		
		/*final String fromAddress="snr@ssegpl.com";
		final String fromPassword="ssegpl@123";*/
		
		
		final String fromAddress="crystaldevelopers2017@gmail.com";
		final String fromPassword="limsndhrlhevyfut";
		
		
		
		
		

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() 
		  {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromAddress, fromPassword);
			}
		  });
		
		
		
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(fromAddress));
		message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(toAddress));

		CCOtherEmailsList=new ArrayList<>(CCOtherEmailsList);
		String[] ccAddressArray=ccAddress.split(",");
		CCOtherEmailsList.addAll(Arrays.asList(ccAddressArray));
		
		
		
		InternetAddress[] recipientAddress = new InternetAddress[CCOtherEmailsList.size()];
		int counter = 0;
		for (String recipient : CCOtherEmailsList)
		{		    
			if(!recipient.equals(""))
			{
				recipientAddress[counter] = new InternetAddress(recipient.trim());
			    counter++;
			}
		}
		message.setRecipients(Message.RecipientType.CC, recipientAddress);       

    	   	    	   
    	   
       
		message.setSubject(emailSubject);
		
		//message.setText("Please Find attached Quotes and Respective Technicals.");
		
		
		
		
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		

        Multipart multipart = new MimeMultipart();
        
        
		/*
		 * for(String s:attachmentPaths) { addAttachment(multipart,s); }
		 */
        
        
        
        BodyPart htmlBodyPart = new MimeBodyPart(); 
        htmlBodyPart.setContent(emailContent, "text/html"); 
        multipart.addBodyPart(htmlBodyPart); 
        
        message.setContent(multipart);
		
        
		
		
		

		Transport.send(message);
	}
	
	public CustomResultObject showForgotPasswordScreen(HttpServletRequest request,Connection connections)
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
		 
			rs.setViewName("../ResetPassword.jsp");	
			rs.setReturnObject(outputMap);						
		return rs;
	}
	
	
	public CustomResultObject showGenerateEstimate(HttpServletRequest request,Connection con) throws Exception
	{
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);
		
		
		outputMap.put("ListOfCategories", lObjConfigDao.getCategories(outputMap,con));
		outputMap.put("ListOfBrands", lObjConfigDao.getBrandMaster(outputMap,con));
		outputMap.put("EstimateNo", lObjConfigDao.getEstimateSeqNo(con,false));
		outputMap.put("clientList", lObjConfigDao.getClientMaster(outputMap,con));
		
		
		
		rs.setViewName("../GenerateEstimate.jsp");
		rs.setReturnObject(outputMap);

	
		rs.setReturnObject(outputMap);
		return rs;
	}
	
	

	
	
	
	
	
	
	


	

	
	
	
	
	
	
	
	
	
	
	public CustomResultObject showGeneratedEstimates(HttpServletRequest request,Connection con) throws SQLException
	{
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
		
		String exportFlag= request.getParameter("exportFlag")==null?"":request.getParameter("exportFlag");
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+"/";	
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		
		String searchString=request.getParameter("searchString");	
		outputMap.put("searchString", searchString);
		
		String firmId=request.getParameter("firmId");	
		outputMap.put("firmId", firmId);
		
		
		
		String fromDate = request.getParameter("txtfromdate") == null ? "" : request.getParameter("txtfromdate");
		String toDate = request.getParameter("txttodate") == null ? "" : request.getParameter("txttodate");

		if (fromDate.equals("")) {
			fromDate = lObjConfigDao.getDateFromDB(con);
		}
		
		if (toDate.equals("")) {
			toDate = lObjConfigDao.getDateFromDB(con);
		}

		outputMap.put("fromDate", fromDate);
		outputMap.put("toDate", toDate);
		
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);
		
		try
		{			
			List<LinkedHashMap<String, Object>> requiredList=new ArrayList<>();
			String [] colNames= {"estimate_id","estimate_no","firm_id","estimate_date","updated_by"};
			
			List<LinkedHashMap<String, Object>> lst =  lObjConfigDao.getGeneratedEstimate(outputMap,con);
			
			if(!exportFlag.isEmpty())
			{
				outputMap = getCommonFileGenerator(colNames,lst,exportFlag,DestinationPath,userId,"GeneratedEstimates");
			}
			else
			{
				outputMap.put("ListEstimate", lst);
				outputMap.put("listOffirm", lObjConfigDao.getfirmMaster(outputMap,con));
				outputMap.put("txtfromdate", fromDate);
				outputMap.put("txttodate", toDate);
				
				
				rs.setViewName("../GeneratedEstimates.jsp");
				rs.setReturnObject(outputMap);
			}
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}
		rs.setReturnObject(outputMap);
		return rs;
	}
	
	



	
	public CustomResultObject exportPaymentsPDF(HttpServletRequest request,Connection con) throws SQLException, ClassNotFoundException
	{
		
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
				
		
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+"/";	
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		
		
		
		String payment_id=request.getParameter("payment_id")==null?"":request.getParameter("payment_id");
	
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);
		
		try
		{
			
			
			HashMap<String, Object> hm= lObjConfigDao.getexportPaymentsPDF(payment_id, con);
			
	
			
					
			String appenders="Payment"+userId+"("+payment_id+")"+getDateTimeWithSeconds(con)+".pdf";
			DestinationPath+=appenders;			
			new InvoiceHistoryPDFHelper().generatePDFPaymentVoucher(DestinationPath,hm,con);
			outputMap.put(filename_constant, appenders);
			rs.setReturnObject(outputMap);
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}
		rs.setReturnObject(outputMap);
		return rs;
	}
	
	
	public CustomResultObject showUserDetails(HttpServletRequest request, Connection con) {
		CustomResultObject rs = new CustomResultObject();
		HashMap<String, Object> outputMap = new HashMap<>();

		
		String userId = ((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
	
		
		try {
			
			
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
			
			
			
			
			List<String> roles = new ArrayList<>();
			
			for (LinkedHashMap<String, Object> mappedRole : lstUserRoleDetails) {
				roles.add(mappedRole.get("role_id").toString());
			}
			
			
				outputMap.put("userDetails", lObjConfigDao.getuserDetailsById(Long.valueOf(userId), con));
				outputMap.put("lstUserRoleDetails", lstUserRoleDetailsNew);
				rs.setViewName("../UserDetails.jsp");
				rs.setReturnObject(outputMap);
			
		} catch (Exception e) {
			writeErrorToDB(e);
			rs.setHasError(true);
		}
		rs.setReturnObject(outputMap);
		return rs;
	}
	
	
	
	public CustomResultObject showHolidayMaster(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
		String exportFlag= request.getParameter("exportFlag")==null?"":request.getParameter("exportFlag");
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+delimiter;
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		try
		{
			String [] colNames= {"holiday_id","holiday_name","holiday_date"}; // change according to dao return
			List<LinkedHashMap<String, Object>> lst=lObjConfigDao.getHolidayMaster(con);
			outputMap.put("ListOfHolidays", lst);

			
			if(!exportFlag.isEmpty())
			{
				outputMap = getCommonFileGenerator(colNames,lst,exportFlag,DestinationPath,userId,"HolidayMaster");
			}
		else
			{
				
				rs.setViewName("../HolidayMaster.jsp");
				
			}	
			
			

		}
		catch (Exception e)
		{
			writeErrorToDB(e);
			rs.setHasError(true);
		}		
		rs.setReturnObject(outputMap);

		return rs;
	}
	
	public CustomResultObject showAddHoliday(HttpServletRequest request,Connection connections)
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
		
		long holidayId=request.getParameter("holidayId")==null?0L:Long.parseLong(request.getParameter("holidayId"));
		outputMap.put("holiday_id", holidayId);
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		
		try
		{	
			if(holidayId!=0) {			outputMap.put("holidayDetails", lObjConfigDao.getHolidayDetails(outputMap ,connections));} 
			rs.setViewName("../AddHoliday.jsp");	
			rs.setReturnObject(outputMap);		
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}

	public CustomResultObject showAccessDenied(HttpServletRequest request,Connection connections)
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
		rs.setReturnObject(outputMap);
		rs.setViewName("../AccessDenied.jsp");		
		return rs;
	}
	

	
	
	
	public CustomResultObject addHoliday(HttpServletRequest request,Connection con) throws Exception
	{
		CustomResultObject rs=new CustomResultObject();	
		HashMap<String, Object> outputMap=new HashMap<>();	
				
		FileItemFactory itemFacroty=new DiskFileItemFactory();
		ServletFileUpload upload=new ServletFileUpload(itemFacroty);		
		//String webInfPath = cf.getPathForAttachments();
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+File.separator;
		
		HashMap<String,Object> hm=new HashMap<>();
		
		
		
		
		List<FileItem> toUpload=new ArrayList<>();
		if(ServletFileUpload.isMultipartContent(request))
		{
			List<FileItem> items=upload.parseRequest(request);
			for(FileItem item:items)
			{		
				
				if (item.isFormField()) 
				{
					hm.put(item.getFieldName(), item.getString());
			    }
				else
				{
					toUpload.add(item);
				}
			}			
		}		
		String holidayName= hm.get("txtholidayname").toString();
		String holidaydate= hm.get("txtholidaydate").toString();
		
		
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		hm.put("txtholidayname", holidayName);
		hm.put("txtholidaydate", holidaydate);
		hm.put("user_id", userId);
		
		
		long holidayId=hm.get("hdnHolidayId").equals("")?0l:Long.parseLong(hm.get("hdnHolidayId").toString()); 
		try
		{			
									
			
			
			if(holidayId==0)
			{
				holidayId=lObjConfigDao.addHoliday(con, hm);
			}
			else
			{
				lObjConfigDao.updateHoliday(holidayId, con, holidayName,holidaydate,userId);
			}
			
			
		
			rs.setReturnObject(outputMap);
			
			
			rs.setAjaxData("<script>window.location='"+hm.get("callerUrl")+"?a=showHolidayMaster'</script>");
			
										
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	
	public CustomResultObject deleteHoliday(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		long holidayId= Integer.parseInt(request.getParameter("holidayId"));		
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		try
		{	
			
			rs.setAjaxData(lObjConfigDao.deleteHoliday(holidayId,userId,con));
			
			
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	
	public CustomResultObject checkInThisEmployee(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		String qr_code= (request.getParameter("qr_code"));	
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		try
		{
			String returnMessage="";
			LinkedHashMap<String, String> employeeDetails =lObjConfigDao.getEmployeeDetailsByQrCode(qr_code, con);			
			if(lObjConfigDao.checkifduplicateentry(employeeDetails.get("user_id").toString(),120,con))
			{
				returnMessage="Check in / Check out is alreadyCaptured. Please try again after 120 Seconds for "+employeeDetails.get("name");
			}
			else
			if(lObjConfigDao.isAccessBlocked(employeeDetails.get("user_id").toString(),con))
			{
				returnMessage="Access is blocked for this employee "+employeeDetails.get("name");
			}
			else
			{
				String getCheckType=lObjConfigDao.getCheckType(employeeDetails.get("user_id").toString(),con);
				String time=lObjConfigDao.checkInEmployee(employeeDetails.get("user_id"),getCheckType, con);
				returnMessage = "Data Saved Successfully for " + employeeDetails.get("name") + "|"
						+ employeeDetails.get("name") +"~"+time+"~"+getCheckType ;
			}			
			rs.setAjaxData(returnMessage);
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}

	public CustomResultObject checkInGatePass(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		String qr_code= (request.getParameter("qr_code"));	
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		try
		{
			String returnMessage="";
			LinkedHashMap<String, String> employeeDetails =lObjConfigDao.getEmployeeDetailsByQrCode(qr_code, con);	
					
			if(lObjConfigDao.checkIfApprovedGatePassExistForUser(employeeDetails.get("user_id").toString(),con))
			{
				returnMessage="No Approved Gate Pass Exists for this employee "+employeeDetails.get("name");
			}
			else
			{
				String CheckType=lObjConfigDao.getGatePassCheckType(employeeDetails.get("user_id").toString(),con);
				
				lObjConfigDao.checkInOutGatePass(employeeDetails.get("user_id"),CheckType, con);
				returnMessage="Gate Pass Checked "+ CheckType+" Successfully";
			}			
			rs.setAjaxData(returnMessage);
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}


	
	public CustomResultObject showVendorCheckIn(HttpServletRequest request,Connection connections)
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
		
		
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		
		try
		{	
			
			LinkedHashMap<String, String> empDetails=lObjConfigDao.getEmployeeDetails(Long.valueOf(userId), connections);
			
			
			outputMap.put("empDetails", empDetails);
			outputMap.put("checkInType", lObjConfigDao.getLastCheckInType(userId,connections));
			outputMap.put("lstLastCheckIns", lObjConfigDao.getListOfLastCheckIns(userId,10,connections));
			
			
			rs.setViewName("../VendorCheckIn.jsp");	
			rs.setReturnObject(outputMap);		
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	
	public CustomResultObject showCheckInScreen(HttpServletRequest request,Connection connections)
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
		
		
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		
		try
		{	
			
			LinkedHashMap<String, String> empDetails=lObjConfigDao.getEmployeeDetails(Long.valueOf(userId), connections);
			
			
			outputMap.put("empDetails", empDetails);
			outputMap.put("checkInType", lObjConfigDao.getLastCheckInType(userId,connections));
			outputMap.put("lstLastCheckIns", lObjConfigDao.getListOfLastCheckIns(userId,10,connections));
			
			
			rs.setViewName("../CheckIn.jsp");	
			rs.setReturnObject(outputMap);		
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}		
	
		return rs;
	}

	public CustomResultObject showCheckInForThisVendor(HttpServletRequest request,Connection connections)
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
		String qrCodeForVendor=request.getParameter("qrCodeForVendor");
		
		
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		
		try
		{	
			
			LinkedHashMap<String, String> vendorDetails=lObjConfigDao.getVendorDetailsByVendorCode(qrCodeForVendor, connections);
			
			
			outputMap.put("vendorDetails", vendorDetails);
			
			
			rs.setViewName("../CheckInScreenForThisVendor.jsp");	
			rs.setReturnObject(outputMap);		
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}		
	
		return rs;
	}
	


	public CustomResultObject addVisitor(HttpServletRequest request, Connection con) throws FileUploadException, IOException {

		CustomResultObject rs = new CustomResultObject();
		HashMap<String, Object> outputMap = new HashMap<>();

		FileItemFactory itemFactory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(itemFactory);
		String appId = request.getParameter("app_id");
		outputMap.put("app_id", appId);
		HashMap<String, Object> hm = new HashMap<>();
		hm.put("app_id", appId);
		String imageName = request.getParameter("photo");
		String textfield = request.getParameter("textfield");
		
		List<FileItem> toUpload = new ArrayList<>();
		if (ServletFileUpload.isMultipartContent(request)) {
			List<FileItem> items = upload.parseRequest(request);
			for (FileItem item : items) {

				if (item.isFormField()) {
					hm.put(item.getFieldName(), item.getString());
				} else {
					toUpload.add(item);
				}
			}
		}

		long visitorId = hm.get("hdnvisitorId").equals("") ? 0l : Long.parseLong(hm.get("hdnvisitorId").toString());

		List<String> lstBase64ImageContent=new ArrayList<>();
		for(int i=0;i<10;i++)
		{			
			if(hm.get("hdnphotobase64"+i)==null)
			{
				break;
			}

			lstBase64ImageContent.add(hm.get("hdnphotobase64"+i).toString());
		}


		
		

		


		try {

			
				visitorId = lObjConfigDao.AddVisitor(con, hm);
				for(String base64ImageContent:lstBase64ImageContent)
				{
					
					base64ImageContent=base64ImageContent.split(",")[1];
					byte[] decodedImg = Base64.getDecoder()
								.decode(base64ImageContent.getBytes(StandardCharsets.UTF_8));
					String DestinationPath = request.getServletContext().getRealPath("BufferedImagesFolder") + delimiter;

				Path destinationFile = Paths.get(DestinationPath, "/myImage"+visitorId+".png");
				Files.write(destinationFile, decodedImg);
				uploadFileToDB(DestinationPath+"/myImage"+visitorId+".png", con, "Image", visitorId);
				Files.delete(destinationFile);
				}
				
				
				
				
				
				
			
			rs.setAjaxData("<script>window.location='?a=showVisitors'</script>");
			rs.setReturnObject(outputMap);
			
			

		} catch (Exception e) {
			writeErrorToDB(e);
			rs.setHasError(true);
		}
		return rs;

	}

	public CustomResultObject checkoutVendor(HttpServletRequest request, Connection con) throws FileUploadException, IOException {

		CustomResultObject rs = new CustomResultObject();
		HashMap<String, Object> outputMap = new HashMap<>();

		FileItemFactory itemFactory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(itemFactory);
		
		HashMap<String, Object> hm = new HashMap<>();
		
		String imageName = request.getParameter("photo");
		String textfield = request.getParameter("textfield");
		
		List<FileItem> toUpload = new ArrayList<>();
		if (ServletFileUpload.isMultipartContent(request)) {
			List<FileItem> items = upload.parseRequest(request);
			for (FileItem item : items) {

				if (item.isFormField()) {
					hm.put(item.getFieldName(), item.getString());
				} else {
					toUpload.add(item);
				}
			}
		}

		long vendor_checkin_id = hm.get("vendor_checkin_id").equals("") ? 0l : Long.parseLong(hm.get("vendor_checkin_id").toString());

		List<String> lstBase64ImageContent=new ArrayList<>();
		for(int i=0;i<10;i++)
		{			
			if(hm.get("hdnphotobase64"+i)==null)
			{
				break;
			}

			lstBase64ImageContent.add(hm.get("hdnphotobase64"+i).toString());
		}


		
		

		


		try {

			
			lObjConfigDao.checkoutVendor(con, hm);
				for(String base64ImageContent:lstBase64ImageContent)
				{
					
					base64ImageContent=base64ImageContent.split(",")[1];
					byte[] decodedImg = Base64.getDecoder()
								.decode(base64ImageContent.getBytes(StandardCharsets.UTF_8));
					String DestinationPath = request.getServletContext().getRealPath("BufferedImagesFolder") + delimiter;

				Path destinationFile = Paths.get(DestinationPath, "/myImage"+vendor_checkin_id+".png");
				Files.write(destinationFile, decodedImg);
				uploadFileToDB(DestinationPath+"/myImage"+vendor_checkin_id+".png", con, "VendorImage", vendor_checkin_id);
				Files.delete(destinationFile);
				}
				
				
				
				
				
				
			
			rs.setAjaxData("<script>window.location='?a=showVendorsCheckIn'</script>");
			rs.setReturnObject(outputMap);
			
			

		} catch (Exception e) {
			writeErrorToDB(e);
			rs.setHasError(true);
		}
		return rs;

	}

	public CustomResultObject addVendorCheckIn(HttpServletRequest request, Connection con) throws FileUploadException, IOException {

		CustomResultObject rs = new CustomResultObject();
		HashMap<String, Object> outputMap = new HashMap<>();

		FileItemFactory itemFactory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(itemFactory);
		String appId = request.getParameter("app_id");
		outputMap.put("app_id", appId);
		HashMap<String, Object> hm = new HashMap<>();
		hm.put("app_id", appId);
		String imageName = request.getParameter("photo");
		String textfield = request.getParameter("textfield");
		
		List<FileItem> toUpload = new ArrayList<>();
		if (ServletFileUpload.isMultipartContent(request)) {
			List<FileItem> items = upload.parseRequest(request);
			for (FileItem item : items) {

				if (item.isFormField()) {
					hm.put(item.getFieldName(), item.getString());
				} else {
					toUpload.add(item);
				}
			}
		}


		List<String> lstBase64ImageContent=new ArrayList<>();
		for(int i=0;i<10;i++)
		{			
			if(hm.get("hdnphotobase64"+i)==null)
			{
				break;
			}

			lstBase64ImageContent.add(hm.get("hdnphotobase64"+i).toString());
		}


		
		

		


		try {

			
				long vendorCheckInId = lObjConfigDao.AddVendorCheckIn(con, hm);
				for(String base64ImageContent:lstBase64ImageContent)
				{
					
					base64ImageContent=base64ImageContent.split(",")[1];
					byte[] decodedImg = Base64.getDecoder()
								.decode(base64ImageContent.getBytes(StandardCharsets.UTF_8));
					String DestinationPath = request.getServletContext().getRealPath("BufferedImagesFolder") + delimiter;

				Path destinationFile = Paths.get(DestinationPath, "/myImageVendor"+vendorCheckInId+".png");
				Files.write(destinationFile, decodedImg);
				uploadFileToDB(DestinationPath+"/myImageVendor"+vendorCheckInId+".png", con, "VendorImage", vendorCheckInId);
				Files.delete(destinationFile);
				}
				
				
				
				
				
				
			
			rs.setAjaxData("<script>window.location='?a=showVendorsCheckIn'</script>");
			rs.setReturnObject(outputMap);
			
			

		} catch (Exception e) {
			writeErrorToDB(e);
			rs.setHasError(true);
		}
		return rs;

	}

	

	public CustomResultObject showVisitors(HttpServletRequest request, Connection con)
			throws SQLException, ClassNotFoundException {

		CustomResultObject rs = new CustomResultObject();
		HashMap<String, Object> outputMap = new HashMap<>();
		String exportFlag = request.getParameter("exportFlag") == null ? "" : request.getParameter("exportFlag");
		String DestinationPath = request.getServletContext().getRealPath("BufferedImagesFolder") + delimiter;

		String fromDate = request.getParameter("txtfromdate") == null ? "" : request.getParameter("txtfromdate");
		String toDate = request.getParameter("txttodate") == null ? "" : request.getParameter("txttodate");
		String storeId = request.getParameter("storeId") == null ? "" : request.getParameter("storeId");

		String userId = ((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		String appId = ((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);

		// if parameters are blank then set to defaults
		if (fromDate.equals("")) {
			fromDate = lObjConfigDao.getDateFromDB(con);
		}
		if (toDate.equals("")) {
			toDate = lObjConfigDao.getDateFromDB(con);
		}

		outputMap.put("txtfromdate", fromDate);
		outputMap.put("txttodate", toDate);

		try {
			String[] colNames = { "visitor_id", "visitor_name", "address","purpose_of_visit","mobile_no", "email_id", "contact_person","checkin_time","checkout_time","image","remarks" };

			List<LinkedHashMap<String, Object>> lst = null;

			lst = lObjConfigDao.showVisitors(outputMap, con);

			if (!exportFlag.isEmpty()) {
				outputMap = getCommonFileGenerator(colNames, lst, exportFlag, DestinationPath, userId, "VisitorEntry");
			} else {

				Date toDateDate = new SimpleDateFormat("dd/MM/yyyy").parse(fromDate);

				Calendar cal = Calendar.getInstance();
				cal.setTime(toDateDate);
				cal.add(Calendar.DATE, -1);
				toDateDate = cal.getTime();

				toDate = new SimpleDateFormat("dd/MM/yyyy").format(toDateDate);
				String startOfApplication = "23/01/1992";
				outputMap.put("ListOfVisitors", lst);

				rs.setViewName("../Visitors.jsp");
				rs.setReturnObject(outputMap);
			}
		} catch (Exception e) {
			writeErrorToDB(e);
			rs.setHasError(true);
		}
		rs.setReturnObject(outputMap);
		return rs;
	}


	public CustomResultObject showVendorsCheckIn(HttpServletRequest request, Connection con)
			throws SQLException, ClassNotFoundException {

		CustomResultObject rs = new CustomResultObject();
		HashMap<String, Object> outputMap = new HashMap<>();
		String exportFlag = request.getParameter("exportFlag") == null ? "" : request.getParameter("exportFlag");
		String DestinationPath = request.getServletContext().getRealPath("BufferedImagesFolder") + delimiter;

		String fromDate = request.getParameter("txtfromdate") == null ? "" : request.getParameter("txtfromdate");
		String toDate = request.getParameter("txttodate") == null ? "" : request.getParameter("txttodate");
		String storeId = request.getParameter("storeId") == null ? "" : request.getParameter("storeId");

		String userId = ((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		String appId = ((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);

		// if parameters are blank then set to defaults
		if (fromDate.equals("")) {
			fromDate = lObjConfigDao.getDateFromDB(con);
		}
		if (toDate.equals("")) {
			toDate = lObjConfigDao.getDateFromDB(con);
		}

		outputMap.put("txtfromdate", fromDate);
		outputMap.put("txttodate", toDate);

		try {
			String[] colNames = { "vendor_checkin_id", "vendor_name", "purpose_of_visit","contact_no1", "email", "contact_person","firm_name","remarks","check_in_time","check_out_time" };

			List<LinkedHashMap<String, Object>> lst = null;

			lst = lObjConfigDao.showVendorCheckIn(outputMap, con);

			if (!exportFlag.isEmpty()) {
				outputMap = getCommonFileGenerator(colNames, lst, exportFlag, DestinationPath, userId, "VisitorEntry");
			} else {

				Date toDateDate = new SimpleDateFormat("dd/MM/yyyy").parse(fromDate);

				Calendar cal = Calendar.getInstance();
				cal.setTime(toDateDate);
				cal.add(Calendar.DATE, -1);
				toDateDate = cal.getTime();

				toDate = new SimpleDateFormat("dd/MM/yyyy").format(toDateDate);
				String startOfApplication = "23/01/1992";
				outputMap.put("ListOfVendors", lst);

				rs.setViewName("../VendorsCheckInRegister.jsp");
				rs.setReturnObject(outputMap);
			}
		} catch (Exception e) {
			writeErrorToDB(e);
			rs.setHasError(true);
		}
		rs.setReturnObject(outputMap);
		return rs;
	}

	
	public CustomResultObject showCheckoutForVendor(HttpServletRequest request, Connection con) {
		CustomResultObject rs = new CustomResultObject();
		HashMap<String, Object> outputMap = new HashMap<>();

		long vendor_checkin_id = request.getParameter("vendor_checkin_id") == null ? 0L
				: Long.parseLong(request.getParameter("vendor_checkin_id"));

		String appId = ((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");

		try {
		
			
			
			outputMap.put("vendor_checkin_id",vendor_checkin_id);
			rs.setViewName("../CheckoutVendor.jsp");
			rs.setReturnObject(outputMap);
		} catch (Exception e) {
			writeErrorToDB(e);
			rs.setHasError(true);
		}
		return rs;
	}
	

	public CustomResultObject showAddVisitor(HttpServletRequest request, Connection con) {
		CustomResultObject rs = new CustomResultObject();
		HashMap<String, Object> outputMap = new HashMap<>();

		long visitorId = request.getParameter("visitorId") == null ? 0L
				: Long.parseLong(request.getParameter("visitorId"));

		long prefilledvisitorId = request.getParameter("prefilledvisitorId") == null ? 0L
				: Long.parseLong(request.getParameter("prefilledvisitorId"));


		String appId = ((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");

		try {
			if (visitorId != 0) {
				outputMap.put("visitorDetails", lObjConfigDao.getvisitorDetails(visitorId, con));
			}
			
			if (prefilledvisitorId != 0) {
				outputMap.put("prefilledvisitorDetails", lObjConfigDao.getprefilledvisitorDetails(prefilledvisitorId, con));
			}

			outputMap.put("EmployeeList", lObjConfigDao.getEmployeeMaster(outputMap,con));

			outputMap.put("employeeList", lObjConfigDao.getEmployeeMaster(outputMap,con));
			outputMap.put("distinctPurposeOfVisist", lObjConfigDao.getDistinctPurposeOfVisitList(con, appId));
			rs.setViewName("../AddVisitor.jsp");
			rs.setReturnObject(outputMap);
		} catch (Exception e) {
			writeErrorToDB(e);
			rs.setHasError(true);
		}
		return rs;
	}


	public CustomResultObject deleteVisitor(HttpServletRequest request, Connection con) {

		CustomResultObject rs = new CustomResultObject();
		long visitorId = Long.parseLong(request.getParameter("visitorId"));
		try {

			rs.setAjaxData(lObjConfigDao.deleteVisitor(visitorId, con));

		} catch (Exception e) {
			writeErrorToDB(e);
			rs.setHasError(true);
		}
		return rs;
	}
	
	public CustomResultObject checkoutVisitor(HttpServletRequest request, Connection con) {

		CustomResultObject rs = new CustomResultObject();
		long visitorId = Long.parseLong(request.getParameter("visitorId"));
		try {

			rs.setAjaxData(lObjConfigDao.checkoutVisitor(visitorId, con));

		} catch (Exception e) {
			writeErrorToDB(e);
			rs.setHasError(true);
		}
		return rs;
	}
	public CustomResultObject showSupervisorSubmitLeave(HttpServletRequest request, Connection con) {
		CustomResultObject rs = new CustomResultObject();
		HashMap<String, Object> outputMap = new HashMap<>();

		long employeeId = request.getParameter("employeeId") == null ? 0L
				: Long.parseLong(request.getParameter("employeeId"));

		String appId = ((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		String userId = ((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		
		
		outputMap.put("parent_user_id", userId);

		try {
		
			
			
			outputMap.put("todaysDate", lObjConfigDao.getDateFromDB(con));
			outputMap.put("EmployeeList", lObjConfigDao.getEmployeeMaster(outputMap,con));

			
			outputMap.put("employeeList", lObjConfigDao.getEmployeeMaster(outputMap,con));
			rs.setViewName("../SupervisorSubmitLeave.jsp");
			rs.setReturnObject(outputMap);
		} catch (Exception e) {
			writeErrorToDB(e);
			rs.setHasError(true);
		}
		return rs;
	}

	public CustomResultObject saveSupervisorSubmitLeave(HttpServletRequest request, Connection con)
			throws SQLException, ClassNotFoundException {

		CustomResultObject rs = new CustomResultObject();
		HashMap<String, Object> outputMap = new HashMap<>();
		String exportFlag = request.getParameter("exportFlag") == null ? "" : request.getParameter("exportFlag");
		String DestinationPath = request.getServletContext().getRealPath("BufferedImagesFolder") + delimiter;
		
		String fromDate=request.getParameter("txtfromDate");
		String empId=request.getParameter("hdnselectedemployee");
		String reason=request.getParameter("reason");
		String remark=request.getParameter("remark");
		String toDate=request.getParameter("txttoDate");

		String userId = ((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		String appId = ((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);
		
		
		
		outputMap.put("employee_id", empId);
		outputMap.put("supervisor_id", userId);
		outputMap.put("txtfromDate", fromDate);
		outputMap.put("reason", reason);
		outputMap.put("remark", remark);
		outputMap.put("txttoDate", toDate);
		
		

		try {

			lObjConfigDao.saveSupervisorSubmitLeave(con, outputMap);
			rs.setAjaxData("<script>alert('Leave Saved Successfully');window.location='?a=showLeaveRegister'</script>");
			rs.setReturnObject(outputMap);
	
		} catch (Exception e) {
			writeErrorToDB(e);
			rs.setHasError(true);
		}
		
		return rs;
	}
	

	public CustomResultObject showCurrentLeaveRegister(HttpServletRequest request,Connection con) throws SQLException
	{
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
		String exportFlag= request.getParameter("exportFlag")==null?"":request.getParameter("exportFlag");
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+delimiter;
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		String fromDate = request.getParameter("txtfromdate") == null ? "" : request.getParameter("txtfromdate");
		String emp_id = request.getParameter("emp_id") == null ? "" : request.getParameter("emp_id");

		
		if (fromDate.equals("")) {
			fromDate = lObjConfigDao.getDateFromDB(con);
		}
		
		
		try
		{
			String [] colNames= {"EmployeeName","reason","remark","from_date","to_date", "SuperVisorName"}; // change according to dao return
			List<LinkedHashMap<String, Object>> lst=lObjConfigDao.getLeaves(fromDate,emp_id,con);
			outputMap.put("ListOfEmployees", lst);
			outputMap.put("txtfromdate", fromDate);

			if (!emp_id.equals(""))
			{
				outputMap.put("empdetails", lObjConfigDao.getEmployeeDetails(Long.valueOf(emp_id),con));

			}

			outputMap.put("fromDate", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
				
				outputMap.put("toDate", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
				outputMap.put("EmployeeList", lObjConfigDao.getEmployeeMaster(outputMap,con));

			if(!exportFlag.isEmpty())
			{
				outputMap = getCommonFileGenerator(colNames,lst,exportFlag,DestinationPath,userId,"LeaveRegister");
			}
		else
			{
				
				rs.setViewName("../CurrentLeaveRegister.jsp");
				
			}	
			
			

		}
		catch (Exception e)
		{
			writeErrorToDB(e);
			rs.setHasError(true);
		}		
		rs.setReturnObject(outputMap);

		return rs;
	}
	public CustomResultObject showShiftMaster(HttpServletRequest request, Connection con) {
		CustomResultObject rs = new CustomResultObject();
		HashMap<String, Object> outputMap = new HashMap<>();

		String exportFlag = request.getParameter("exportFlag") == null ? "" : request.getParameter("exportFlag");
		String DestinationPath = request.getServletContext().getRealPath("BufferedImagesFolder") + delimiter;
		String userId = ((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		String appId = ((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");

		try {

			String[] colNames = { "shift_id", "shift_name", "from_time", "to_time", "activate_flag", "updated_by",
					"updated_date" };
			outputMap.put("app_id", appId);
			List<LinkedHashMap<String, Object>> lst = lObjConfigDao.getShiftMaster(outputMap, con);

			if (!exportFlag.isEmpty()) {
				outputMap = getCommonFileGenerator(colNames, lst, exportFlag, DestinationPath, userId,
						"CategoryMaster");
			} else {
				outputMap.put("ListOfShift", lst);
				rs.setViewName("../ShiftMaster.jsp");
				rs.setReturnObject(outputMap);
			}
		} catch (Exception e) {
			writeErrorToDB(e);
			rs.setHasError(true);
		}
		rs.setReturnObject(outputMap);
		return rs;

	}
	public CustomResultObject showAddShift(HttpServletRequest request, Connection connections) {
		CustomResultObject rs = new CustomResultObject();
		HashMap<String, Object> outputMap = new HashMap<>();

		long shiftId = request.getParameter("shiftId") == null ? 0L : Long.parseLong(request.getParameter("shiftId"));
		outputMap.put("shift_id", shiftId);
		String appId = ((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);
		try {
			if (shiftId != 0) {
				outputMap.put("ShiftDetails", lObjConfigDao.getShiftDetails(outputMap, connections));
			}
			outputMap.put("lisitOfShift", lObjConfigDao.getShiftMaster(outputMap, connections));
			rs.setViewName("../AddShift.jsp");
			rs.setReturnObject(outputMap);
		} catch (Exception e) {
			writeErrorToDB(e);
			rs.setHasError(true);
		}
		return rs;
	}

	public CustomResultObject addShift(HttpServletRequest request, Connection con) throws Exception {
		CustomResultObject rs = new CustomResultObject();
		HashMap<String, Object> outputMap = new HashMap<>();

		FileItemFactory itemFacroty = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(itemFacroty);
		// String webInfPath = cf.getPathForAttachments();
		String DestinationPath = request.getServletContext().getRealPath("BufferedImagesFolder") + File.separator;

		HashMap<String, Object> hm = new HashMap<>();

		List<FileItem> toUpload = new ArrayList<>();
		if (ServletFileUpload.isMultipartContent(request)) {
			List<FileItem> items = upload.parseRequest(request);
			for (FileItem item : items) {

				if (item.isFormField()) {
					hm.put(item.getFieldName(), item.getString());
				} else {
					toUpload.add(item);
				}
			}
		}

		String shift_name = hm.get("shift_name").toString();
		String from_time_hour = hm.get("from_time_hour").toString();
		String from_time_minute = hm.get("from_time_minute").toString();
		String to_time_hour = hm.get("to_time_hour").toString();
		String to_time_minute = hm.get("to_time_minute").toString();
		String late_shift_cutoff = hm.get("late_shift_cutoff").toString();

		hm.put("shift_name", shift_name);
		hm.put("from_time_hour", from_time_hour);
		hm.put("from_time_minute", from_time_minute);
		hm.put("to_time_hour", to_time_hour);
		hm.put("to_time_minute", to_time_minute);
		hm.put("late_shift_cutoff", late_shift_cutoff);

		String appId = hm.get("app_id").toString();
		hm.put("app_id", appId);

		String userId = ((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		hm.put("user_id", userId);

		long hdnShiftId = hm.get("hdnShiftId").equals("") ? 0l : Long.parseLong(hm.get("hdnShiftId").toString());
		try {

			if (hdnShiftId == 0) {
				hdnShiftId = lObjConfigDao.addShift(con, hm);
			} else {				
				lObjConfigDao.updateShift(hdnShiftId,shift_name,from_time_hour,from_time_minute,to_time_hour,to_time_minute,userId,late_shift_cutoff,con);
			}

			rs.setReturnObject(outputMap);

			rs.setAjaxData("<script>window.location='" + hm.get("callerUrl") + "?a=showShiftMaster'</script>");

		} catch (Exception e) {
			writeErrorToDB(e);
			rs.setHasError(true);
		}
		return rs;
	}

	public CustomResultObject deleteShift(HttpServletRequest request, Connection con) {
		CustomResultObject rs = new CustomResultObject();
		long shiftId = Integer.parseInt(request.getParameter("shiftId"));

		try {
			rs.setAjaxData(lObjConfigDao.deleteShift(shiftId, con));

		} catch (Exception e) {
			writeErrorToDB(e);
			rs.setHasError(true);
		}
		return rs;
	}
	public CustomResultObject showInwardEntry(HttpServletRequest request,Connection connections)
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
		
		long inwardId=request.getParameter("inwardId")==null?0L:Long.parseLong(request.getParameter("inwardId"));
		outputMap.put("inward_id", inwardId);
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		
		try
		{	
			if(inwardId!=0) {outputMap.put("inwardDetails", lObjConfigDao.getInwardDetails(outputMap ,connections));} 
			rs.setViewName("../InwardEntry.jsp");	
			rs.setReturnObject(outputMap);		
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	public CustomResultObject showInwardRegister(HttpServletRequest request,Connection con) throws SQLException
	{
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
		String exportFlag= request.getParameter("exportFlag")==null?"":request.getParameter("exportFlag");
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+delimiter;
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		String fromDate = request.getParameter("txtfromdate") == null ? "" : request.getParameter("txtfromdate");
		String toDate = request.getParameter("txttodate") == null ? "" : request.getParameter("txttodate");
		
		if (fromDate.equals("")) {
			fromDate = lObjConfigDao.getDateFromDB(con);
		}
		if (toDate.equals("")) {
			toDate = lObjConfigDao.getDateFromDB(con);
		}
		
		
		try
		{
			String [] colNames= {"vehicleNo","contactPerson","mobileNo","transporterName","vendorName"}; // change according to dao return
			List<LinkedHashMap<String, Object>> lst=lObjConfigDao.getInward(fromDate,toDate,con);
			outputMap.put("ListOfEmployees", lst);
			outputMap.put("txtfromdate", fromDate);

			outputMap.put("txttodate", toDate);

			
			if(!exportFlag.isEmpty())
			{
				outputMap = getCommonFileGenerator(colNames,lst,exportFlag,DestinationPath,userId,"InwardRegister");
			}
		else
			{
				
				rs.setViewName("../InwardRegister.jsp");
				
			}	
			
			

		}
		catch (Exception e)
		{
			writeErrorToDB(e);
			rs.setHasError(true);
		}		
		rs.setReturnObject(outputMap);

		return rs;
	}
	public CustomResultObject showAttendanceRegister(HttpServletRequest request,Connection con) throws SQLException
	{
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
		String exportFlag= request.getParameter("exportFlag")==null?"":request.getParameter("exportFlag");
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+delimiter;
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		String fromDate = request.getParameter("txtfromdate") == null ? "" : request.getParameter("txtfromdate");
		String toDate = request.getParameter("txttodate") == null ? "" : request.getParameter("txttodate");
		String searchString=request.getParameter("searchString") == null ? "" : request.getParameter("searchString");
		
		if (fromDate.equals("")) {
			fromDate = lObjConfigDao.getDateFromDB(con);
		}
		if (toDate.equals("")) {
			toDate = lObjConfigDao.getDateFromDB(con);
		}
		
		
		try
		{
			String [] colNames= {"name","checked_in_time","checked_out_time"}; // change according to dao return
			List<LinkedHashMap<String, Object>> lst=lObjConfigDao.getAttendance(fromDate,toDate,searchString,con);
			
			
			List<LinkedHashMap<String, Object>> reqList =new ArrayList<>();
			String previousRow="";
			
			for(int i=0;i<lst.size()-1;i++)
			{
				LinkedHashMap<String, Object> lhm=new LinkedHashMap<>();
				lhm.put("user_id", lst.get(i).get("user_id").toString());
				lhm.put("name", lst.get(i).get("name").toString());
				lhm.put("qr_code", lst.get(i).get("qr_code").toString());
				if(!previousRow.equals(lst.get(i).get("name").toString()) && i!=0)
				{
					LinkedHashMap<String, Object> lhm1=new LinkedHashMap<>();
					lhm1.put("user_id", "");
					lhm1.put("name", "");
					lhm1.put("checked_in_time", "");
					lhm1.put("checked_out_time", "");
					reqList.add(lhm1);
				}
				previousRow=lst.get(i).get("name").toString();
				if(lst.get(i).get("check_in_type").equals("I"))
				{
					lhm.put("checked_in_time",lst.get(i).get("checked_time").toString());
				}
				else
				{
					lhm.put("checked_out_time",lst.get(i).get("checked_time").toString());
				}
				
				if(lst.get(i).get("user_id").toString().equals(lst.get(i+1).get("user_id").toString()))
				{
					if(lst.get(i+1).get("check_in_type").equals("I"))
					{
						lhm.put("checked_in_time",lst.get(i+1).get("checked_time").toString());
					}
					else
					{
						lhm.put("checked_out_time",lst.get(i+1).get("checked_time").toString());
					}
					i++;
				}
				
				
				reqList.add(lhm);
			}
			
			
			
			outputMap.put("ListOfEmployees", lObjConfigDao.getEmployeeMaster(outputMap, con));
			outputMap.put("reqList", reqList);
			
			outputMap.put("txtfromdate", fromDate);

			if(!searchString.equals(""))
			{
				outputMap.put("empDetails", lObjConfigDao.getEmployeeDetails(Long.valueOf(searchString), con));
			}

			outputMap.put("txttodate", toDate);

			
			if(!exportFlag.isEmpty())
			{
				outputMap = getCommonFileGenerator(colNames,reqList,exportFlag,DestinationPath,userId,"AttendanceRegister");
			}
		else
			{
				
				rs.setViewName("../AttendanceRegister.jsp");
				
			}	
			
			

		}
		catch (Exception e)
		{
			writeErrorToDB(e);
			rs.setHasError(true);
		}		
		rs.setReturnObject(outputMap);

		return rs;
	}

	public CustomResultObject deleteLeave(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		long leaveid=Long.parseLong(request.getParameter("leaveId"));		
		HashMap<String, Object> outputMap=new HashMap<>();
				
		String returnAjaxString;
		try
		{	
			
				rs.setAjaxData(lObjConfigDao.deleteleave(leaveid,con));
			
			
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	public CustomResultObject showAccessBlockEntry(HttpServletRequest request, Connection con) throws Exception {
    CustomResultObject rs = new CustomResultObject();
    HashMap<String, Object> outputMap = new HashMap<>();
    
    String exportFlag = request.getParameter("exportFlag") == null ? "" : request.getParameter("exportFlag");
    String DestinationPath = request.getServletContext().getRealPath("BufferedImagesFolder") + delimiter;
    String userId = ((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
    String searchInput = request.getParameter("searchInput"); // Search input
    String appId = ((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
    
    outputMap.put("searchInput", searchInput);
    outputMap.put("app_id", appId);

    try {
        String[] colNames = {"accessBlockId", "employeeId", "employeeName", "updatedBy", "remarks", "qrCode"};

        List<LinkedHashMap<String, Object>> lst = null;

        // DAO method call with search parameters
        lst = lObjConfigDao.getAccessBlockEntries(outputMap, con);

        if (!exportFlag.isEmpty()) {
            outputMap = getCommonFileGenerator(colNames, lst, exportFlag, DestinationPath, userId, "AccessBlockEntry");
        } else {
            outputMap.put("ListOfAccessBlockEntry", lst);
            rs.setViewName("../BlockAccessRegister.jsp");
            rs.setReturnObject(outputMap);
        }
    } catch (Exception e) {
        request.setAttribute("error_id", writeErrorToDB(e) + "-" + getDateTimeWithSeconds(con));
        rs.setHasError(true);
    }
    
    return rs;
}

		
	
		public CustomResultObject showAddAccessBlockEntry(HttpServletRequest request,Connection con) throws SQLException
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
		
		long accessblockId=request.getParameter("accessblockId")==null?0L:Long.parseLong(request.getParameter("accessblockId"));
			
		try
		{	
			if(accessblockId!=0)
			 {
				outputMap.put("accessblockDetails", lObjConfigDao.getAccessblockDetails(accessblockId,con));
		} 
			
		outputMap.put("EmployeeList", lObjConfigDao.getEmployeeMaster(outputMap,con));
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
	rs.setViewName("../AddAccessBlockEntry.jsp");	
			rs.setReturnObject(outputMap);		
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	public CustomResultObject saveAccessBlockEntry(HttpServletRequest request, Connection con) throws Exception {
		CustomResultObject rs = new CustomResultObject();
		HashMap<String, Object> outputMap = new HashMap<>();

		FileItemFactory itemFacroty = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(itemFacroty);
		// String webInfPath = cf.getPathForAttachments();

		HashMap<String, Object> hm = new HashMap<>();

		List<FileItem> toUpload = new ArrayList<>();
		if (ServletFileUpload.isMultipartContent(request)) {
			List<FileItem> items = upload.parseRequest(request);
			for (FileItem item : items) {

				if (item.isFormField()) {
					hm.put(item.getFieldName(), item.getString());
				} else {
					toUpload.add(item);
				}
			}
		}
		String employeeId = hm.get("hdnselectedemployee").toString();
		String remarks = hm.get("txtremarks").toString();

		String userId = ((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		
	
		hm.put("hdnselectedemployee", employeeId);
		hm.put("txtremarks", remarks);
		hm.put("user_id", userId);

		lObjConfigDao.saveAccessBlockEntry(hm, con);
		
		try {

			rs.setReturnObject(outputMap);

			rs.setAjaxData("<script>window.location='" + hm.get("callerUrl") + "?a=showAccessBlockEntry'</script>");

		} catch (Exception e) {
			writeErrorToDB(e);
		}
		return rs;
	}
	public CustomResultObject deleteAccessBlockEntry(HttpServletRequest request, Connection con) {
		CustomResultObject rs = new CustomResultObject();
		String accessblockIdsParam = request.getParameter("accessblockIds"); // Comma-separated IDs
		String userId = ((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
	
		if (accessblockIdsParam == null || accessblockIdsParam.trim().isEmpty()) {
			rs.setHasError(true);			
			return rs;
		}
	
		String[] accessblockIds = accessblockIdsParam.split(",");
		try {
			for (String accessblockId : accessblockIds) {
				long id = Long.parseLong(accessblockId.trim());
				rs.setAjaxData(lObjConfigDao.deleteAccessBlockEntry(id, userId, con));
			}
		} catch (Exception e) {
			writeErrorToDB(e);
			rs.setHasError(true);			
		}
		return rs;
	}
	public CustomResultObject showVendorMaster(HttpServletRequest request, Connection con) {
		CustomResultObject rs = new CustomResultObject();
		HashMap<String, Object> outputMap = new HashMap<>();

		String exportFlag = request.getParameter("exportFlag") == null ? "" : request.getParameter("exportFlag");
		String DestinationPath = request.getServletContext().getRealPath("BufferedImagesFolder") + delimiter;
		String userId = ((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		String searchString=request.getParameter("searchString");
		
		try {
			String[] colNames = { "vendor_name","firm_name","contact_person", "address", "state" ,"email","contact_no1"};

			List<LinkedHashMap<String, Object>> lst = null;

			lst = lObjConfigDao.getVendorMaster(con,searchString);

			if (!exportFlag.isEmpty())
				outputMap = getCommonFileGenerator(colNames, lst, exportFlag, DestinationPath, userId,
						"VendorMaster");
			else {
				outputMap.put("ListOfVendor", lst);
				rs.setViewName("../VendorMaster.jsp");
				rs.setReturnObject(outputMap);
			}

		} catch (Exception e) {
			writeErrorToDB(e);
			rs.setHasError(true);
		}
		rs.setReturnObject(outputMap);
		return rs;
	}
	public CustomResultObject showAddVendor(HttpServletRequest request, Connection con) {
		CustomResultObject rs = new CustomResultObject();
		HashMap<String, Object> outputMap = new HashMap<>();

		long vendorId = request.getParameter("vendorId") == null ? 0L : Long.parseLong(request.getParameter("vendorId"));

		try {
			if (vendorId != 0) {
				outputMap.put("vendorDetails", lObjConfigDao.getVendorDetails(vendorId, con));
			}
			rs.setViewName("../AddVendor.jsp");
			rs.setReturnObject(outputMap);
		} catch (Exception e) {
			writeErrorToDB(e);
			rs.setHasError(true);
		}
		return rs;
	}
	public CustomResultObject addVendor(HttpServletRequest request, Connection con)
			throws FileUploadException {

		CustomResultObject rs = new CustomResultObject();
		HashMap<String, Object> outputMap = new HashMap<>();

		FileItemFactory itemFactory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(itemFactory);
		String appId = request.getParameter("app_id");
		outputMap.put("app_id", appId);
		HashMap<String, Object> hm = new HashMap<>();
		hm.put("app_id", appId);
		List<FileItem> toUpload = new ArrayList<>();
		if (ServletFileUpload.isMultipartContent(request)) {
			List<FileItem> items = upload.parseRequest(request);
			for (FileItem item : items) {

				if (item.isFormField()) {
					hm.put(item.getFieldName(), item.getString());
				} else {
					toUpload.add(item);
				}
			}
		}

		long hdnvendorId = hm.get("hdnvendorId").equals("") ? 0l : Long.parseLong(hm.get("hdnvendorId").toString());
		try {

			if (hdnvendorId == 0) {
				hdnvendorId = lObjConfigDao.addVendor(con, hm);
			} else {
				lObjConfigDao.updateVendor(hdnvendorId, con, hm);
			}

			rs.setReturnObject(outputMap);

			rs.setAjaxData("<script>window.location='?a=showVendorMaster'</script>");


		} catch (Exception e) {
			writeErrorToDB(e);
			rs.setHasError(true);
		}
		return rs;

	}
	public CustomResultObject deleteVendor(HttpServletRequest request, Connection con) {
		CustomResultObject rs = new CustomResultObject();
		long vendorId = Long.parseLong(request.getParameter("vendorId"));

		try {

			rs.setAjaxData(lObjConfigDao.deleteVendor(vendorId, con));

		} catch (Exception e) {
			writeErrorToDB(e);
			rs.setHasError(true);
		}
		return rs;
	}

	public CustomResultObject deleteVendorCheckIn(HttpServletRequest request, Connection con) {
		CustomResultObject rs = new CustomResultObject();
		long vendor_checkin_id = Long.parseLong(request.getParameter("vendor_checkin_id"));

		try {

			rs.setAjaxData(lObjConfigDao.deleteVendorCheckIn(vendor_checkin_id, con));

		} catch (Exception e) {
			writeErrorToDB(e);
			rs.setHasError(true);
		}
		return rs;
	}

	
	public CustomResultObject showLeaveRegister(HttpServletRequest request,Connection con) throws SQLException
	{
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
		String exportFlag= request.getParameter("exportFlag")==null?"":request.getParameter("exportFlag");
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+delimiter;
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		String fromDate = request.getParameter("txtfromdate") == null ? "" : request.getParameter("txtfromdate");
		String toDate = request.getParameter("txttodate") == null ? "" : request.getParameter("txttodate");
		String emp_id = request.getParameter("emp_id") == null ? "" : request.getParameter("emp_id");

		
		if (fromDate.equals("")) {
			fromDate = lObjConfigDao.getDateFromDB(con);
		}
		if (toDate.equals("")) {
			toDate = lObjConfigDao.getDateFromDB(con);
		}
		
		
		try
		{
			String [] colNames= {"EmployeeName","reason","from_date","to_date", "SuperVisorName","remark"}; // change according to dao return
			List<LinkedHashMap<String, Object>> lst=lObjConfigDao.getLeavesRegister(fromDate,toDate,emp_id,con);
			outputMap.put("ListOfEmployees", lst);
			outputMap.put("txtfromdate", fromDate);

			outputMap.put("txttodate", toDate);
			if (!emp_id.equals(""))
			{
				outputMap.put("empdetails", lObjConfigDao.getEmployeeDetails(Long.valueOf(emp_id),con));

			}


			outputMap.put("fromDate", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
				
				outputMap.put("toDate", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
				outputMap.put("EmployeeList", lObjConfigDao.getEmployeeMaster(outputMap,con));

			if(!exportFlag.isEmpty())
			{
				outputMap = getCommonFileGenerator(colNames,lst,exportFlag,DestinationPath,userId,"LeaveRegister");
			}
		else
			{
				
				rs.setViewName("../LeaveRegister.jsp");
				
			}	
			
			

		}
		catch (Exception e)
		{
			writeErrorToDB(e);
			rs.setHasError(true);
		}		
		rs.setReturnObject(outputMap);

		return rs;
	}

	

	public CustomResultObject getFileFromDbToBuffer(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		long attachment_id=request.getParameter("attachment_id").equals("")?-1:Integer.parseInt(request.getParameter("attachment_id"));		
		HashMap<String, Object> outputMap=new HashMap<>();				
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+"/";
		String returnAjaxString;
		try
		{
			String FileName=actualCopyNew(DestinationPath,con,attachment_id);
			rs.setAjaxData(FileName);
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
			rs.setHasError(true);
		}		
		return rs;
	}
	   

	
	public CustomResultObject showVisitorsInside(HttpServletRequest request, Connection con)
			throws SQLException, ClassNotFoundException {

		CustomResultObject rs = new CustomResultObject();
		HashMap<String, Object> outputMap = new HashMap<>();
		String exportFlag = request.getParameter("exportFlag") == null ? "" : request.getParameter("exportFlag");
		String DestinationPath = request.getServletContext().getRealPath("BufferedImagesFolder") + delimiter;

		String fromDate = request.getParameter("txtfromdate") == null ? "" : request.getParameter("txtfromdate");
		String toDate = request.getParameter("txttodate") == null ? "" : request.getParameter("txttodate");
		String storeId = request.getParameter("storeId") == null ? "" : request.getParameter("storeId");

		String userId = ((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		String appId = ((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);

		// if parameters are blank then set to defaults
		if (fromDate.equals("")) {
			fromDate = lObjConfigDao.getDateFromDB(con);
		}
		if (toDate.equals("")) {
			toDate = lObjConfigDao.getDateFromDB(con);
		}

		outputMap.put("txtfromdate", fromDate);
		outputMap.put("txttodate", toDate);

		try {
			String[] colNames = { "visitor_id", "visitor_name", "address","purpose_of_visit","mobile_no", "email_id", "contact_person","checkin_time","checkout_time","image","remarks" };

			List<LinkedHashMap<String, Object>> lst = null;

			lst = lObjConfigDao.showIVisitorsInside(outputMap, con);

			if (!exportFlag.isEmpty()) {
				outputMap = getCommonFileGenerator(colNames, lst, exportFlag, DestinationPath, userId, "VisitorEntry");
			} else {

				Date toDateDate = new SimpleDateFormat("dd/MM/yyyy").parse(fromDate);

				Calendar cal = Calendar.getInstance();
				cal.setTime(toDateDate);
				cal.add(Calendar.DATE, -1);
				toDateDate = cal.getTime();

				toDate = new SimpleDateFormat("dd/MM/yyyy").format(toDateDate);
				String startOfApplication = "23/01/1992";
				outputMap.put("ListOfVisitors", lst);

				rs.setViewName("../VisitorsInside.jsp");
				rs.setReturnObject(outputMap);
			}
		} catch (Exception e) {
			writeErrorToDB(e);
			rs.setHasError(true);
		}
		rs.setReturnObject(outputMap);
		return rs;
	}


	public CustomResultObject showEmployeeInside(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
		
		String exportFlag= request.getParameter("exportFlag")==null?"":request.getParameter("exportFlag");
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+"/";	
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		String PermanentFlag= request.getParameter("PermanentFlag")==null?"":request.getParameter("PermanentFlag");



		
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);
		 
		try
		{			
			List<LinkedHashMap<String, Object>> requiredList=new ArrayList<>();
			String [] colNames= {"EmployeeId","EmployeeName", "EmployeeRole", "MobileNumber"};
			
			List<LinkedHashMap<String, Object>> lst = lObjConfigDao.getEmployeesInside(con,PermanentFlag);
			
			if(!exportFlag.isEmpty())
				{
					outputMap = getCommonFileGenerator(colNames,lst,exportFlag,DestinationPath,userId,"EmployeeMaster");
				}
			else
				{
					outputMap.put("ListOfEmployee", lst);	
					rs.setViewName("../EmployeeInside.jsp");
					rs.setReturnObject(outputMap);
				}			
			}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}
		rs.setReturnObject(outputMap);
		return rs;
	}

	public CustomResultObject showAddPrefilledVisitor(HttpServletRequest request, Connection con) {
		CustomResultObject rs = new CustomResultObject();
		HashMap<String, Object> outputMap = new HashMap<>();

		long visitorId = request.getParameter("visitorId") == null ? 0L
				: Long.parseLong(request.getParameter("visitorId"));

		String appId = ((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");

		try {
			if (visitorId != 0) {
				outputMap.put("visitorDetails", lObjConfigDao.getvisitorDetails(visitorId, con));
			}
			
			
			outputMap.put("EmployeeList", lObjConfigDao.getEmployeeMaster(outputMap,con));

			outputMap.put("employeeList", lObjConfigDao.getEmployeeMaster(outputMap,con));
			outputMap.put("distinctPurposeOfVisist", lObjConfigDao.getDistinctPurposeOfVisitList(con, appId));
			rs.setViewName("../AddPrefilledVisitor.jsp");
			rs.setReturnObject(outputMap);
		} catch (Exception e) {
			writeErrorToDB(e);
			rs.setHasError(true);
		}
		return rs;
	}


	public CustomResultObject addPrefilledVisitor(HttpServletRequest request, Connection con) throws FileUploadException, IOException {

		CustomResultObject rs = new CustomResultObject();
		HashMap<String, Object> outputMap = new HashMap<>();

		FileItemFactory itemFactory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(itemFactory);
		String appId = request.getParameter("app_id");
		outputMap.put("app_id", appId);
		HashMap<String, Object> hm = new HashMap<>();
		hm.put("app_id", appId);
		String imageName = request.getParameter("photo");
		String textfield = request.getParameter("textfield");
		
		List<FileItem> toUpload = new ArrayList<>();
		if (ServletFileUpload.isMultipartContent(request)) {
			List<FileItem> items = upload.parseRequest(request);
			for (FileItem item : items) {

				if (item.isFormField()) {
					hm.put(item.getFieldName(), item.getString());
				} else {
					toUpload.add(item);
				}
			}
		}

		long prefilledvisitorId = hm.get("hdnprefilledvisitorId").equals("") ? 0l : Long.parseLong(hm.get("hdnprefilledvisitorId").toString());


		try {

			
			prefilledvisitorId = lObjConfigDao.addPrefilledVisitor(con, hm);
				
			
			
			rs.setAjaxData("<script>window.location='?a=showPrefilledVisitors'</script>");
			rs.setReturnObject(outputMap);
			
			

		} catch (Exception e) {
			writeErrorToDB(e);
			rs.setHasError(true);
		}
		return rs;

	}

	public CustomResultObject showPrefilledVisitors(HttpServletRequest request, Connection con)
			throws SQLException, ClassNotFoundException {

		CustomResultObject rs = new CustomResultObject();
		HashMap<String, Object> outputMap = new HashMap<>();
		String exportFlag = request.getParameter("exportFlag") == null ? "" : request.getParameter("exportFlag");
		String DestinationPath = request.getServletContext().getRealPath("BufferedImagesFolder") + delimiter;

		String fromDate = request.getParameter("txtfromdate") == null ? "" : request.getParameter("txtfromdate");
		String toDate = request.getParameter("txttodate") == null ? "" : request.getParameter("txttodate");
		String storeId = request.getParameter("storeId") == null ? "" : request.getParameter("storeId");

		String userId = ((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		String appId = ((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);

		// if parameters are blank then set to defaults
		if (fromDate.equals("")) {
			fromDate = lObjConfigDao.getDateFromDB(con);
		}
		if (toDate.equals("")) {
			toDate = lObjConfigDao.getDateFromDB(con);
		}

		outputMap.put("txtfromdate", fromDate);
		outputMap.put("txttodate", toDate);

		try {
			String[] colNames = { "prefilled_visitor_id", "visitor_name", "address","purpose_of_visit","mobile_no", "email_id", "contact_person","remarks" };

			List<LinkedHashMap<String, Object>> lst = null;

			lst = lObjConfigDao.showPrefilledVisitors(outputMap, con);

			if (!exportFlag.isEmpty()) {
				outputMap = getCommonFileGenerator(colNames, lst, exportFlag, DestinationPath, userId, "VisitorEntry");
			} else {

				Date toDateDate = new SimpleDateFormat("dd/MM/yyyy").parse(fromDate);

				Calendar cal = Calendar.getInstance();
				cal.setTime(toDateDate);
				cal.add(Calendar.DATE, -1);
				toDateDate = cal.getTime();

				toDate = new SimpleDateFormat("dd/MM/yyyy").format(toDateDate);
				String startOfApplication = "23/01/1992";
				outputMap.put("ListOfVisitors", lst);

				rs.setViewName("../PrefilledVisitors.jsp");
				rs.setReturnObject(outputMap);
			}
		} catch (Exception e) {
			writeErrorToDB(e);
			rs.setHasError(true);
		}
		rs.setReturnObject(outputMap);
		return rs;
	}


	public CustomResultObject deletePrefilledVisitor(HttpServletRequest request, Connection con) {

		CustomResultObject rs = new CustomResultObject();
		long visitorId = Long.parseLong(request.getParameter("visitorId"));
		try {

			rs.setAjaxData(lObjConfigDao.deletePrefilledVisitor(visitorId, con));

		} catch (Exception e) {
			writeErrorToDB(e);
			rs.setHasError(true);
		}
		return rs;
	}

	public CustomResultObject checkoutPrefilledVisitor(HttpServletRequest request, Connection con) {

		CustomResultObject rs = new CustomResultObject();
		long visitorId = Long.parseLong(request.getParameter("visitorId"));
		try {

			rs.setAjaxData(lObjConfigDao.checkoutPrefilledVisitor(visitorId, con));

		} catch (Exception e) {
			writeErrorToDB(e);
			rs.setHasError(true);
		}
		return rs;
	}


	public CustomResultObject showGenerateGatePass(HttpServletRequest request, Connection con) throws SQLException {
		CustomResultObject rs = new CustomResultObject();
		HashMap<String, Object> outputMap = new HashMap<>();

		long gatepassId = request.getParameter("gatepassId") == null ? 0L : Long.parseLong(request.getParameter("gatepassId"));
		outputMap.put("gatepass_id", gatepassId);
		String appId = ((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		outputMap.put("app_id", appId);
		String todaysDate=lObjConfigDao.getDateFromDB(con);

		try {
			outputMap.put("pass_no", lObjConfigDao.getPassNo(con,false));
			outputMap.put("EmployeeList", lObjConfigDao.getEmployeeMaster(outputMap,con));

			outputMap.put("lisitOfGatepass", lObjConfigDao.getGatepass(outputMap, con));
			outputMap.put("todaysDate", todaysDate);

			rs.setViewName("../GenerateGatePass.jsp");
			rs.setReturnObject(outputMap);
		} catch (Exception e) {
			writeErrorToDB(e);
			rs.setHasError(true);
		}
		return rs;
	}
	public CustomResultObject addGatePass(HttpServletRequest request,Connection con) throws Exception
	{
		CustomResultObject rs=new CustomResultObject();	
		HashMap<String, Object> outputMap=new HashMap<>();	
				
		FileItemFactory itemFacroty=new DiskFileItemFactory();
		ServletFileUpload upload=new ServletFileUpload(itemFacroty);		
		//String webInfPath = cf.getPathForAttachments();
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+File.separator;
		
		HashMap<String,Object> hm=new HashMap<>();
		
		
		
		
		List<FileItem> toUpload=new ArrayList<>();
		if(ServletFileUpload.isMultipartContent(request))
		{
			List<FileItem> items=upload.parseRequest(request);
			for(FileItem item:items)
			{		
				
				if (item.isFormField()) 
				{
					hm.put(item.getFieldName(), item.getString());
			    }
				else
				{
					toUpload.add(item);
				}
			}			
		}		
		

		
		
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		
		
		
		try
		{			


			hm.put("pass_no", lObjConfigDao.getPassNo(con,true));
			hm.put("request_raised_by",userId );
			

									
			
			
			
				lObjConfigDao.addGatepass(con, hm);
			
			
			
		
			rs.setReturnObject(outputMap);
			
			
			rs.setAjaxData("<script>window.location='"+hm.get("callerUrl")+"?a=showGatepassRegister'</script>");
			
										
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	public CustomResultObject showGatepassRegister(HttpServletRequest request,Connection con) throws SQLException
	{
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
		String exportFlag= request.getParameter("exportFlag")==null?"":request.getParameter("exportFlag");
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+delimiter;
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		String fromDate = request.getParameter("txtfromdate") == null ? "" : request.getParameter("txtfromdate");
		String toDate = request.getParameter("txttodate") == null ? "" : request.getParameter("txttodate");
		
		if (fromDate.equals("")) {
			fromDate = lObjConfigDao.getDateFromDB(con);
		}
		if (toDate.equals("")) {
			toDate = lObjConfigDao.getDateFromDB(con);
		}
		
		
		try
		{
			String [] colNames= {"pi_no","payment_request_date","accountNo","bank_name","total_gross_amount",
					"total_amount_requested"}; // change according to dao return
			List<LinkedHashMap<String, Object>> lst=lObjConfigDao.getGatepassRegister(fromDate,toDate,con);
			outputMap.put("lstGatepassRegister", lst);
			outputMap.put("txtfromdate", fromDate);

			outputMap.put("txttodate", toDate);

			
			if(!exportFlag.isEmpty())
			{
				outputMap = getCommonFileGenerator(colNames,lst,exportFlag,DestinationPath,userId,"GatepassRegister");
			}
		else
			{
				
				rs.setViewName("../GatePassRegister.jsp");
				
			}	
			


		}
		catch (Exception e)
		{
			writeErrorToDB(e);
			rs.setHasError(true);
		}		
		rs.setReturnObject(outputMap);

		return rs;
	}

	public CustomResultObject approveGatepass(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		long gate_pass_id= Long.parseLong(request.getParameter("gate_pass_id"));	

		HashMap<String, Object> outputMap=new HashMap<>();
		String requestApprovedBy=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
				
		String returnAjaxString;
		try
		{			
						
						
			rs.setAjaxData(lObjConfigDao.approveGatepass(gate_pass_id,requestApprovedBy, con));
			
							
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	public CustomResultObject deleteGatepass(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		long gate_pass_id= Long.parseLong(request.getParameter("gate_pass_id"));		
				
		try
		{			
			
			
						
			rs.setAjaxData(lObjConfigDao.deleteGatepass(gate_pass_id,con));
			
			
							
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}

	public CustomResultObject showScanGatePass(HttpServletRequest request,Connection connections)
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
		
		
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		
		try
		{	
			
			//LinkedHashMap<String, String> empDetails=lObjConfigDao.getEmployeeDetails(Long.valueOf(userId), connections);
			
			
			//outputMap.put("empDetails", empDetails);
			outputMap.put("checkInType", lObjConfigDao.getLastCheckInType(userId,connections));
			//outputMap.put("lstLastCheckIns", lObjConfigDao.getListOfLastCheckIns(userId,10,connections));
			
			
			rs.setViewName("../GatePassScan.jsp");	
			rs.setReturnObject(outputMap);		
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}		
	
		return rs;
	}


	public CustomResultObject showGatePassSummary(HttpServletRequest request,Connection con) throws SQLException
	{
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
		String exportFlag= request.getParameter("exportFlag")==null?"":request.getParameter("exportFlag");
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+delimiter;
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		String fromDate = request.getParameter("txtfromdate") == null ? "" : request.getParameter("txtfromdate");
		String toDate = request.getParameter("txttodate") == null ? "" : request.getParameter("txttodate");
		String empId = request.getParameter("empId") == null ? "" : request.getParameter("empId");
		
		if (fromDate.equals("")) {
			fromDate = lObjConfigDao.getDateFromDB(con);
		}
		if (toDate.equals("")) {
			toDate = lObjConfigDao.getDateFromDB(con);
		}
		
		
		try
		{
			String [] colNames= {"pi_no","payment_request_date","accountNo","bank_name","total_gross_amount",
					"total_amount_requested"}; // change according to dao return
			List<LinkedHashMap<String, Object>> lst=lObjConfigDao.getGatePassSummary(fromDate,toDate,empId,con);
			outputMap.put("lstGatepassRegister", lst);
			if(empId!=null && !empId.equals(""))
			{
				outputMap.put("employeeDetails", lObjConfigDao.getEmployeeDetails(Long.valueOf(empId),con));
				
			}
			outputMap.put("EmployeeList", lObjConfigDao.getEmployeeMaster(outputMap,con));

			outputMap.put("txtfromdate", fromDate);

			outputMap.put("txttodate", toDate);

			
			if(!exportFlag.isEmpty())
			{
				outputMap = getCommonFileGenerator(colNames,lst,exportFlag,DestinationPath,userId,"GatepassRegister");
			}
		else
			{
				
				rs.setViewName("../GatePassSummary.jsp");
				
			}	
			


		}
		catch (Exception e)
		{
			writeErrorToDB(e);
			rs.setHasError(true);
		}		
		rs.setReturnObject(outputMap);

		return rs;
	}

	public CustomResultObject showEmployeeAttendanceSummary(HttpServletRequest request,Connection con) throws SQLException
	{
		CustomResultObject rs=new CustomResultObject();
		HashMap<String, Object> outputMap=new HashMap<>();
		String exportFlag= request.getParameter("exportFlag")==null?"":request.getParameter("exportFlag");
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+delimiter;
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		String fromDate = request.getParameter("txtfromdate") == null ? "" : request.getParameter("txtfromdate");
		
		String empId = request.getParameter("empId") == null ? "" : request.getParameter("empId");
		
		if (fromDate.equals("")) {
			fromDate = lObjConfigDao.getDateFromDB(con);
		}
		
		
		
		try
		{
			String [] colNames= {"pi_no","payment_request_date","accountNo","bank_name","total_gross_amount",
					"total_amount_requested"}; // change according to dao return


			
					outputMap.put("PresentSummary", lObjConfigDao.getPresentSummary(fromDate,con));
					outputMap.put("LeavesSummary", lObjConfigDao.getEmployeeOnLeaveSummary(fromDate,con));
					outputMap.put("LWISummary", lObjConfigDao.getEmployeeOnLeaveWithoutIntimation(fromDate,con));
					outputMap.put("txtfromdate", fromDate);
					
					

			
			
				
				rs.setViewName("../EmployeeAttendanceSummary.jsp");
				
				
			


		}
		catch (Exception e)
		{
			writeErrorToDB(e);
			rs.setHasError(true);
		}		
		rs.setReturnObject(outputMap);

		return rs;
	}

	
	public CustomResultObject showDepartmentMaster(HttpServletRequest request, Connection con) {
		CustomResultObject rs = new CustomResultObject();
		HashMap<String, Object> outputMap = new HashMap<>();

		String exportFlag = request.getParameter("exportFlag") == null ? "" : request.getParameter("exportFlag");
		String DestinationPath = request.getServletContext().getRealPath("BufferedImagesFolder") + delimiter;
		String userId = ((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");

		try {

			String[] colNames = { "department_id", "department_name"
					 };
			List<LinkedHashMap<String, Object>> lst = lObjConfigDao.getDepartmentMaster(outputMap, con);

			if (!exportFlag.isEmpty()) {
				outputMap = getCommonFileGenerator(colNames, lst, exportFlag, DestinationPath, userId,
						"DepartmentMaster");
			} else {
				outputMap.put("ListOfDepartment", lst);
				rs.setViewName("../DepartmentMaster.jsp");
				rs.setReturnObject(outputMap);
			}
		} catch (Exception e) {
			writeErrorToDB(e);
			rs.setHasError(true);
		}
		rs.setReturnObject(outputMap);
		return rs;

	}

	public CustomResultObject showAddDepartment(HttpServletRequest request,Connection connections)
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
		
		long departmentId=request.getParameter("departmentId")==null?0L:Long.parseLong(request.getParameter("departmentId"));
		outputMap.put("department_id", departmentId);
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		
		try
		{	
			if(departmentId!=0) {			outputMap.put("departmentDetails", lObjConfigDao.getDepartmentDetails(outputMap ,connections));} 
			rs.setViewName("../AddDepartment.jsp");	
			rs.setReturnObject(outputMap);		
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}

	public CustomResultObject addDepartment(HttpServletRequest request,Connection con) throws Exception
	{
		CustomResultObject rs=new CustomResultObject();	
		HashMap<String, Object> outputMap=new HashMap<>();	
				
		FileItemFactory itemFacroty=new DiskFileItemFactory();
		ServletFileUpload upload=new ServletFileUpload(itemFacroty);		
		//String webInfPath = cf.getPathForAttachments();
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+File.separator;
		
		HashMap<String,Object> hm=new HashMap<>();
		
		
		
		
		List<FileItem> toUpload=new ArrayList<>();
		if(ServletFileUpload.isMultipartContent(request))
		{
			List<FileItem> items=upload.parseRequest(request);
			for(FileItem item:items)
			{		
				
				if (item.isFormField()) 
				{
					hm.put(item.getFieldName(), item.getString());
			    }
				else
				{
					toUpload.add(item);
				}
			}			
		}		
		String departmentName= hm.get("txtdepartmentname").toString();
		
		
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		hm.put("txtdepartmentname", departmentName);
		hm.put("user_id", userId);
		
		
		long departmentId=hm.get("hdnDepartmentId").equals("")?0l:Long.parseLong(hm.get("hdnDepartmentId").toString()); 
		try
		{			
									
			
			
			if(departmentId==0)
			{
				departmentId=lObjConfigDao.addDepartment(con, hm);
			}
			else
			{
				lObjConfigDao.updateDepartment(departmentId, con, departmentName,userId);
			}
			
			
		
			rs.setReturnObject(outputMap);
			
			
			rs.setAjaxData("<script>window.location='"+hm.get("callerUrl")+"?a=showDepartmentMaster'</script>");
			
										
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	public CustomResultObject deleteDepartment(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		long departmentId= Integer.parseInt(request.getParameter("departmentId"));		
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		try
		{	
			
			rs.setAjaxData(lObjConfigDao.deleteDepartment(departmentId,userId,con));
			
			
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	
	public CustomResultObject showAbbreviationMaster(HttpServletRequest request, Connection con) {
		CustomResultObject rs = new CustomResultObject();
		HashMap<String, Object> outputMap = new HashMap<>();

		String exportFlag = request.getParameter("exportFlag") == null ? "" : request.getParameter("exportFlag");
		String DestinationPath = request.getServletContext().getRealPath("BufferedImagesFolder") + delimiter;
		String userId = ((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");

		try {

			String[] colNames = { "abbreviation_id", "abbreviation","full_form","description","sequence_no"
					 };
			List<LinkedHashMap<String, Object>> lst = lObjConfigDao.getAbbreviationMaster(outputMap, con);

			if (!exportFlag.isEmpty()) {
				outputMap = getCommonFileGenerator(colNames, lst, exportFlag, DestinationPath, userId,
						"AbbreviationMaster");
			} else {
				outputMap.put("ListOfAbbreviation", lst);
				rs.setViewName("../AbbreviationMaster.jsp");
				rs.setReturnObject(outputMap);
			}
		} catch (Exception e) {
			writeErrorToDB(e);
			rs.setHasError(true);
		}
		rs.setReturnObject(outputMap);
		return rs;

	}

	public CustomResultObject showAddAbbreviation(HttpServletRequest request,Connection connections)
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
		
		long abbreviationId=request.getParameter("abbreviationId")==null?0L:Long.parseLong(request.getParameter("abbreviationId"));
		outputMap.put("abbreviation_id", abbreviationId);
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		
		try
		{	
			if(abbreviationId!=0) {			outputMap.put("abbreviationDetails", lObjConfigDao.getAbbreviationDetails(outputMap ,connections));} 
			rs.setViewName("../AddAbbreviation.jsp");	
			rs.setReturnObject(outputMap);		
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}

	public CustomResultObject addAbbreviation(HttpServletRequest request,Connection con) throws Exception
	{
		CustomResultObject rs=new CustomResultObject();	
		HashMap<String, Object> outputMap=new HashMap<>();	
				
		FileItemFactory itemFacroty=new DiskFileItemFactory();
		ServletFileUpload upload=new ServletFileUpload(itemFacroty);		
		//String webInfPath = cf.getPathForAttachments();
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+File.separator;
		
		HashMap<String,Object> hm=new HashMap<>();
		
		
		
		
		List<FileItem> toUpload=new ArrayList<>();
		if(ServletFileUpload.isMultipartContent(request))
		{
			List<FileItem> items=upload.parseRequest(request);
			for(FileItem item:items)
			{		
				
				if (item.isFormField()) 
				{
					hm.put(item.getFieldName(), item.getString());
			    }
				else
				{
					toUpload.add(item);
				}
			}			
		}		
		String abbreviation= hm.get("txtabbreviation").toString();
		String full_form= hm.get("txtfullform").toString();
		String description= hm.get("txtdescription").toString();
		String sequence_no= hm.get("txtsequenceno").toString();


		
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		hm.put("txtabbreviation", abbreviation);
		hm.put("txtfullform", full_form);

		hm.put("txtdescription", description);

		hm.put("txtsequenceno", sequence_no);

		hm.put("user_id", userId);
		
		
		long abbreviationId=hm.get("hdnAbbreviationId").equals("")?0l:Long.parseLong(hm.get("hdnAbbreviationId").toString()); 
		try
		{			
									
			
			
			if(abbreviationId==0)
			{
				abbreviationId=lObjConfigDao.addAbbreviation(con, hm);
			}
			else
			{
				lObjConfigDao.updateAbbreviation(abbreviationId, con, abbreviation,full_form,description,sequence_no,userId);
			}
			
			
		
			rs.setReturnObject(outputMap);
			
			
			rs.setAjaxData("<script>window.location='"+hm.get("callerUrl")+"?a=showAbbreviationMaster'</script>");
			
										
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}


	public CustomResultObject deleteAbbreviation(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		long abbreviationId= Integer.parseInt(request.getParameter("abbreviationId"));		
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		try
		{	
			
			rs.setAjaxData(lObjConfigDao.deleteAbbreviation(abbreviationId,userId,con));
			
			
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}

	public CustomResultObject showLevelMaster(HttpServletRequest request, Connection con) {
		CustomResultObject rs = new CustomResultObject();
		HashMap<String, Object> outputMap = new HashMap<>();

		String exportFlag = request.getParameter("exportFlag") == null ? "" : request.getParameter("exportFlag");
		String DestinationPath = request.getServletContext().getRealPath("BufferedImagesFolder") + delimiter;
		String userId = ((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");

		try {

			String[] colNames = { "level_id", "level_name","level_no"
					 };
			List<LinkedHashMap<String, Object>> lst = lObjConfigDao.getLevelMaster(outputMap, con);

			if (!exportFlag.isEmpty()) {
				outputMap = getCommonFileGenerator(colNames, lst, exportFlag, DestinationPath, userId,
						"LevelMaster");
			} else {
				outputMap.put("ListOfLevels", lst);
				rs.setViewName("../LevelMaster.jsp");
				rs.setReturnObject(outputMap);
			}
		} catch (Exception e) {
			writeErrorToDB(e);
			rs.setHasError(true);
		}
		rs.setReturnObject(outputMap);
		return rs;

	}

	public CustomResultObject showAddLevel(HttpServletRequest request,Connection connections)
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
		
		long levelId=request.getParameter("levelId")==null?0L:Long.parseLong(request.getParameter("levelId"));
		outputMap.put("level_id", levelId);
		String appId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("app_id");
		
		try
		{	
			if(levelId!=0) {			outputMap.put("levelDetails", lObjConfigDao.getLevelDetails(outputMap ,connections));} 
			rs.setViewName("../AddLevel.jsp");	
			rs.setReturnObject(outputMap);		
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}

	public CustomResultObject addLevel(HttpServletRequest request,Connection con) throws Exception
	{
		CustomResultObject rs=new CustomResultObject();	
		HashMap<String, Object> outputMap=new HashMap<>();	
				
		FileItemFactory itemFacroty=new DiskFileItemFactory();
		ServletFileUpload upload=new ServletFileUpload(itemFacroty);		
		//String webInfPath = cf.getPathForAttachments();
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+File.separator;
		
		HashMap<String,Object> hm=new HashMap<>();
		
		
		
		
		List<FileItem> toUpload=new ArrayList<>();
		if(ServletFileUpload.isMultipartContent(request))
		{
			List<FileItem> items=upload.parseRequest(request);
			for(FileItem item:items)
			{		
				
				if (item.isFormField()) 
				{
					hm.put(item.getFieldName(), item.getString());
			    }
				else
				{
					toUpload.add(item);
				}
			}			
		}		
		String level_name= hm.get("txtlevelname").toString();
		String level_no= hm.get("txtlevelno").toString();


		
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");

		hm.put("txtlevelname", level_name);
		hm.put("txtlevelno", level_no);

		hm.put("user_id", userId);
		
		
		long levelId=hm.get("hdnLevelId").equals("")?0l:Long.parseLong(hm.get("hdnLevelId").toString()); 
		try
		{			
									
			
			
			if(levelId==0)
			{
				levelId=lObjConfigDao.addLevel(con, hm);
			}
			else
			{
				lObjConfigDao.updateLevel(levelId, con, level_name,level_no,userId);
			}
			
			
		
			rs.setReturnObject(outputMap);
			
			
			rs.setAjaxData("<script>window.location='"+hm.get("callerUrl")+"?a=showLevelMaster'</script>");
			
										
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}


	public CustomResultObject deleteLevel(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		long levelId= Integer.parseInt(request.getParameter("levelId"));		
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		try
		{	
			
			rs.setAjaxData(lObjConfigDao.deleteLevel(levelId,userId,con));
			
			
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}

	public CustomResultObject showDocumentGroupMaster(HttpServletRequest request, Connection con) {
		CustomResultObject rs = new CustomResultObject();
		HashMap<String, Object> outputMap = new HashMap<>();

		String exportFlag = request.getParameter("exportFlag") == null ? "" : request.getParameter("exportFlag");
		String DestinationPath = request.getServletContext().getRealPath("BufferedImagesFolder") + delimiter;
		String userId = ((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");

		try {

			String[] colNames = { "document_group_id", "group_name"
					 };
			List<LinkedHashMap<String, Object>> lst = lObjConfigDao.getDocumentGroupMaster(outputMap, con);

			if (!exportFlag.isEmpty()) {
				outputMap = getCommonFileGenerator(colNames, lst, exportFlag, DestinationPath, userId,
						"DocumentGroupMaster");
			} else {
				outputMap.put("ListOfDocumentGroup", lst);
				rs.setViewName("../DocumentGroupMaster.jsp");
				rs.setReturnObject(outputMap);
			}
		} catch (Exception e) {
			writeErrorToDB(e);
			rs.setHasError(true);
		}
		rs.setReturnObject(outputMap);
		return rs;

	}

	public CustomResultObject showAddDocumentGroup(HttpServletRequest request,Connection connections)
	{
		CustomResultObject rs=new CustomResultObject();			
		HashMap<String, Object> outputMap=new HashMap<>();
		
		long documentgroupId=request.getParameter("documentgroupId")==null?0L:Long.parseLong(request.getParameter("documentgroupId"));
		outputMap.put("document_group_id", documentgroupId);
		
		try
		{	
			if(documentgroupId!=0) {			outputMap.put("documentgroupDetails", lObjConfigDao.getDocumentGroupDetails(outputMap ,connections));} 
			rs.setViewName("../AddDocumentGroup.jsp");	
			rs.setReturnObject(outputMap);		
		}
		catch (Exception e)
		{
				writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}

	public CustomResultObject addDocumentGroup(HttpServletRequest request,Connection con) throws Exception
	{
		CustomResultObject rs=new CustomResultObject();	
		HashMap<String, Object> outputMap=new HashMap<>();	
				
		FileItemFactory itemFacroty=new DiskFileItemFactory();
		ServletFileUpload upload=new ServletFileUpload(itemFacroty);		
		//String webInfPath = cf.getPathForAttachments();
		String DestinationPath=request.getServletContext().getRealPath("BufferedImagesFolder")+File.separator;
		
		HashMap<String,Object> hm=new HashMap<>();
		
		
		
		
		List<FileItem> toUpload=new ArrayList<>();
		if(ServletFileUpload.isMultipartContent(request))
		{
			List<FileItem> items=upload.parseRequest(request);
			for(FileItem item:items)
			{		
				
				if (item.isFormField()) 
				{
					hm.put(item.getFieldName(), item.getString());
			    }
				else
				{
					toUpload.add(item);
				}
			}			
		}		
		String groupName= hm.get("txtgroupname").toString();
		
		
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		hm.put("txtgroupname", groupName);
		hm.put("user_id", userId);
		
		
		long documentgroupId=hm.get("hdnDocumentGroupId").equals("")?0l:Long.parseLong(hm.get("hdnDocumentGroupId").toString()); 
		try
		{			
									
			
			
			if(documentgroupId==0)
			{
				documentgroupId=lObjConfigDao.addDocumentGroup(con, hm);
			}
			else
			{
				lObjConfigDao.updateDocumentGroup(documentgroupId, con, groupName,userId);
			}
			
			
		
			rs.setReturnObject(outputMap);
			
			
			rs.setAjaxData("<script>window.location='"+hm.get("callerUrl")+"?a=showDocumentGroupMaster'</script>");
			
										
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}

	public CustomResultObject deleteDocumentGroup(HttpServletRequest request,Connection con)
	{
		CustomResultObject rs=new CustomResultObject();
		long documentgroupId= Integer.parseInt(request.getParameter("documentgroupId"));		
		String userId=((HashMap<String, String>) request.getSession().getAttribute("userdetails")).get("user_id");
		try
		{	
			
			rs.setAjaxData(lObjConfigDao.deleteDocumentGroup(documentgroupId,userId,con));
			
			
		}
		catch (Exception e)
		{
			writeErrorToDB(e);
				rs.setHasError(true);
		}		
		return rs;
	}
	

}
