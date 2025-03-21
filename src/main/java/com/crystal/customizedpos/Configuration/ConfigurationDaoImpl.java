package com.crystal.customizedpos.Configuration;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.jsoup.select.Evaluator.IsEmpty;

import com.crystal.Frameworkpackage.CommonFunctions;
import com.crystal.Frameworkpackage.Query;


public class ConfigurationDaoImpl extends CommonFunctions {

	private Connection con;
	public List<LinkedHashMap<String, Object>> getCategoryMaster(HashMap<String, Object> hm,Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("app_id"));
		return getListOfLinkedHashHashMap(parameters,
				"select category_id categoryId,category_name categoryName from mst_category where activate_flag=1 and app_id=?",
				con);
	}
	
	public List<LinkedHashMap<String, Object>> getListOfWareHouse(HashMap<String, Object> hm,Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("app_id"));
		return getListOfLinkedHashHashMap(parameters,
				"select ware_house_id ,ware_house_name from mst_ware_house where activate_flag=1 and app_id=?",
				con);
	}
	
	
	public List<LinkedHashMap<String, Object>> getBrandMaster(HashMap<String, Object> hm,Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("app_id"));
		return getListOfLinkedHashHashMap(parameters,
				"select * from mst_brand where activate_flag=1 and app_id=?",
				con);
	}
	
	public List<LinkedHashMap<String, Object>> getSBUMaster(HashMap<String, Object> hm,Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("app_id"));
		return getListOfLinkedHashHashMap(parameters,
				"select sbu_id ,sbu_name from sbu_master where activate_flag=1 and app_id=?",
				con);
	}
	
	public List<LinkedHashMap<String, Object>> getBankMaster(HashMap<String, Object> hm,Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("app_id"));
		return getListOfLinkedHashHashMap(parameters,
				"select * from mst_bank where activate_flag=1 and app_id=?",
				con);
	}
	
	
	public List<LinkedHashMap<String, Object>> getBookingRegister(HashMap<String, Object> hm,Connection con)
			throws ClassNotFoundException, SQLException, ParseException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("app_id"));
		
		parameters.add(getDateASYYYYMMDD(hm.get("fromDate").toString()));
		parameters.add(getDateASYYYYMMDD(hm.get("toDate").toString()));
		
		return getListOfLinkedHashHashMap(parameters,
				"select\r\n"
				+ "	*,date_format(from_date,'%d/%m/%Y %H:%i') as FormattedFromDate,date_format(to_date,'%d/%m/%Y %H:%i') as FormattedToDate \r\n"
				+ "from\r\n"
				+ "	trn_booking_register tbr ,\r\n"
				+ "	mst_Client mc,tbl_user_mst tum  \r\n"
				+ "where\r\n"
				+ "	tbr.Client_id = mc.Client_id\r\n"
				+ "	and tbr.activate_flag = 1  and tum.user_id =tbr.preffered_employee \r\n"
				+ "	and tbr.app_id = ? and date(tbr.from_date) between ? and ? ",
				con);
	}
	

	public List<LinkedHashMap<String, Object>> getItemDetailsStock(HashMap<String, Object> hm, Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();

		List<HashMap<String, Object>> itemDetailsList = (List<HashMap<String, Object>>) hm.get("itemDetails");
		String questionMarks = "";
		for (HashMap<String, Object> item : itemDetailsList) {
			parameters.add(Long.parseLong(item.get("item_id").toString()));
			questionMarks += "?,";
		}
		questionMarks = questionMarks.substring(0, questionMarks.length() - 1);

		parameters.add(hm.get("firm_id"));
		parameters.add(hm.get("app_id"));

		return getListOfLinkedHashHashMap(parameters,
				"select item_id,qty_available from stock_status where item_id in (" + questionMarks
						+ ") and firm_id=? and app_id=? for update",
				con);
	}

	public List<LinkedHashMap<String, Object>> getUserRoleDetails(long userId, Connection con)
			throws SQLException, ClassNotFoundException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(userId);
		return getListOfLinkedHashHashMap(parameters,
				"select\r\n"
				+ "	*\r\n"
				+ "from\r\n"
				+ "	tbl_user_mst user,\r\n"
				+ "	acl_user_role_rlt userrole	\r\n"
				+ "where\r\n"
				+ "	user.user_id = ? \r\n"
				+ "	and user.user_id = userrole.user_id	\r\n"
				+ "	and userrole.activate_flag = 1",
				con);
	}
	public List<LinkedHashMap<String, Object>> getServiceMappingForThisClient(long clientId, Connection con)
			throws SQLException, ClassNotFoundException {
		ArrayList<Object> parameters = new ArrayList<>();
		
		parameters.add(clientId);
		return getListOfLinkedHashHashMap(parameters,
				"select * from client_service_mapping csm,mst_services sm "
				+ "where sm.service_id=csm.service_id and csm.client_id=? and csm.activate_flag=1",
				con);
	}
	
	

	public List<LinkedHashMap<String, Object>> getClientList(HashMap<String, Object> hm, Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add("%" + hm.get("searchString") + "%");
		parameters.add("%" + hm.get("searchString") + "%");
		
		parameters.add(hm.get("app_id"));

		return getListOfLinkedHashHashMap(parameters,
				"select * from mst_client where activate_flag=1 and (Client_name like ? or mobile_number like ?) and app_id=?",
				con);
	}

	public List<LinkedHashMap<String, Object>> getServiceMaster(HashMap<String, Object> hm, Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		String query = "select\r\n"
				+ "	service.*,\r\n"
				+ "	cat.*,\r\n"
				+ "	case\r\n"
				+ "		when concat(attachment_id, file_name) is null then 'dummyImage.jpg'\r\n"
				+ "		else concat(attachment_id, file_name)\r\n"
				+ "	end as ImagePath\r\n"
				+ "from\r\n"
				+ "	mst_services service\r\n"
				+ "inner join mst_category cat on\r\n"
				+ "	cat.category_id = service.parent_category_id\r\n"
				+ "left outer join tbl_attachment_mst tam on\r\n"
				+ "	tam.file_id = service.service_id\r\n"
				+ "	and tam.type = 'Image'\r\n"
				+ "where\r\n"
				+ "	service.activate_flag = 1\r\n"
				+ "	and service.app_id = ? \r\n"
				+ "	and cat.app_id = service.app_id  ";
		
		parameters.add(hm.get("app_id"));

		if (hm.get("searchInput") != null && !hm.get("searchInput").equals("")) {
			parameters.add("%" + hm.get("searchInput") + "%");
			parameters.add("%" + hm.get("searchInput") + "%");
			query += " and (occurance like ? or service_name like ?)";
		}

		if (hm.get("categoryId") != null && !hm.get("categoryId").equals("-1") && !hm.get("categoryId").equals("")) {
			parameters.add(hm.get("categoryId"));
			query += " and parent_category_id=? ";
		}
		query += " group by service.service_id";
		query += " order by service_name";
		return getListOfLinkedHashHashMap(parameters, query, con);
	}

	public LinkedHashMap<String, String> getServiceDetailsById(HashMap<String, Object> hm, Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("service_id"));
		

		return getMap(parameters, "select *,date_format(due_date,'%d/%m/%Y') as FormattedDueDate from \r\n"
				+ "mst_services services,\r\n"
				+ "mst_category cat \r\n"
				+ "where service_id=? and\r\n"
				+ "cat.category_id =services.parent_category_id \r\n"
				+ ";", con);

	}

	public List<LinkedHashMap<String, Object>> getServicesByCategoryId(HashMap<String, Object> hm,Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("category_id"));
		parameters.add(hm.get("app_id"));
		return getListOfLinkedHashHashMap(parameters,
				"select * from mst_services where activate_flag=1 and parent_category_id=? and app_id=?", con);
	}

	

	public LinkedHashMap<String, String> getServicedetailsByIdForfirm(String ClientId, String serviceId, String firmId,
			String destinationfirmId,String wareHouseId,String destinationWareHouseId, Connection con) throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		
		parameters.add(serviceId);
		parameters.add(firmId);
		parameters.add(wareHouseId);
		
		
		return getMap(parameters, "select\r\n"
				+ "	firm.firm_name ,\r\n"
				+ "	ware.ware_house_name,\r\n"
				+ "	service1.service_name,\r\n"
				+ "	service1.product_code,\r\n"
				+ "	service1.`size` ,\r\n"
				+ "	service1.color ,\r\n"
				+ "	sum(Qty) stockAvailable,\r\n"
				+ "	sum(PurchasePrice)/ sum(vQty) as AvgPrice,\r\n"
				+ "	firm.firm_id,\r\n"
				+ "	ware.ware_house_id ,\r\n"
				+ "	service1.service_id,service1.price,service1.gst\r\n"
				+ "from\r\n"
				+ "	(\r\n"
				+ "	select\r\n"
				+ "		tsid.service_id , tsid.qty * -1 Qty, tsir.firm_id, tsid.ware_house_id, 0 PurchasePrice,-1 vQty\r\n"
				+ "	from\r\n"
				+ "		trn_sales_invoice_register tsir, trn_sales_invoice_details tsid\r\n"
				+ "	where\r\n"
				+ "		tsir.invoice_id = tsid.invoice_id\r\n"
				+ "		and tsir.activate_flag = 1\r\n"
				+ "union all\r\n"
				+ "	select\r\n"
				+ "		tsid.service_id , tsid.qty Qty, tsir.firm_id, tsid.ware_house_id, tsid.rate PurchasePrice, 1 vQty\r\n"
				+ "	from\r\n"
				+ "		trn_purchase_invoice_register tsir, trn_purchase_invoice_details tsid\r\n"
				+ "	where\r\n"
				+ "		tsir.invoice_id = tsid.invoice_id\r\n"
				+ "		and tsir.activate_flag = 1 ) as T,\r\n"
				+ "	mst_items service1 ,\r\n"
				+ "	mst_firm firm,\r\n"
				+ "	mst_ware_house ware,\r\n"
				+ "	mst_category cat\r\n"
				+ "where\r\n"
				+ "	T.service_id = service1.service_id\r\n"
				+ "	and T.firm_id = firm.firm_id\r\n"
				+ "	and cat.category_id = item1.parent_category_id\r\n"
				+ "	and T.ware_house_id = ware.ware_house_id\r\n"
				+ "	and T.service_id = ?\r\n"
				+ "	and T.firm_id = ?\r\n"
				+ "	and ware.ware_house_id = ?\r\n"
				+ "group by\r\n"
				+ "	firm.firm_id,\r\n"
				+ "	ware.ware_house_id,\r\n"
				+ "	service1.service_id;", con);
	}

	public List<LinkedHashMap<String, Object>> getListofServiceImages(HashMap<String, Object> hm, Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("service_id"));
		parameters.add(hm.get("app_id"));
		
		return getListOfLinkedHashHashMap(parameters,
				"select\r\n" + "	concat(attachment_id, file_name) fileName,\r\n" + "	attachment_id\r\n" + "from\r\n"
						+ "	mst_services service,\r\n" + "	tbl_attachment_mst attach\r\n" + "where\r\n"
						+ "	service_id = ? \r\n" + "	and type = 'Image'\r\n" + "	and service.service_id=attach.file_id and service.app_id=?",
				con);
	}

	public long saveService(HashMap<String, Object> serviceDetails, Connection con)
			throws ClassNotFoundException, SQLException, ParseException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(serviceDetails.get("drpcategoryId"));
			
		parameters.add(serviceDetails.get("servicename"));
		
		parameters.add(getDateASYYYYMMDD(serviceDetails.get("txtduedate").toString()));
		
		parameters.add(serviceDetails.get("userId"));
		
		
		parameters.add(serviceDetails.get("app_id"));
		
		parameters.add(serviceDetails.get("occurance"));
		
		

		String insertQuery = "insert into mst_services values (default,?,?,1,?,?,sysdate(),?,?)";
		return insertUpdateDuablDB(insertQuery, parameters, con);
	}



	public long updateService(HashMap<String, Object> serviceDetails, Connection con)
			throws ClassNotFoundException, SQLException, ParseException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(serviceDetails.get("drpcategoryId"));
		
		parameters.add(serviceDetails.get("servicename"));
		
		parameters.add(getDateASYYYYMMDD(serviceDetails.get("txtduedate").toString()));
		
		
		parameters.add(serviceDetails.get("userId"));
		
		
		parameters.add(serviceDetails.get("occurance"));

		parameters.add(Long.parseLong(serviceDetails.get("hdnServiceId").toString()));

		String insertQuery = "UPDATE mst_services \r\n"
				+ "SET parent_category_id=?, service_name=?,due_date=?,  updated_by=?, updated_date=sysdate(),occurance=? \r\n"
				+ "WHERE service_id=?";
		return insertUpdateDuablDB(insertQuery, parameters, con);
	}

	public List<LinkedHashMap<String, Object>> showServices(HashMap<String, Object> hm,Connection con) throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("app_id"));
		return getListOfLinkedHashHashMap(parameters,
				"SELECT service_name servicename, category_name categoryname ,occurance , due_date txtduedate , service_id serviceId FROM  mst_services service, mst_category cat"
						+ " WHERE  service.parent_category_id=cat.category_id  AND service.`activate_flag`=1 and service.app_id=? and cat.app_id=service.app_id ",
				con);
	}
	
	
	public List<LinkedHashMap<String, Object>> getStockStatusSalesWithoutPurchase(HashMap<String, Object> hm, Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		String query = "select firm_name,ware_house_name,item_name,product_code,size,color,qty*-1 qtyRound from \r\n"
				+ "trn_sales_invoice_register tsir ,\r\n"
				+ "trn_sales_invoice_details tsid ,\r\n"
				+ "mst_firm mf ,\r\n"
				+ "mst_ware_house mwh,\r\n"
				+ "mst_items mi \r\n"
				+ "where tsir.invoice_id =tsid.invoice_id \r\n"
				+ "and tsid.purchase_details_id is null \r\n"
				+ "and mf.firm_id =tsir.firm_id\r\n"
				+ "and mwh.ware_house_id=tsid.ware_house_id \r\n"
				+ "and mi.item_id =tsid.item_id  \r\n";
				

		if (hm.get("categoryId") != null && !hm.get("categoryId").equals("") && !hm.get("categoryId").equals("-1")) {
			query += " and item.parent_category_id=?";
			parameters.add(hm.get("categoryId"));
		}

		if (hm.get("storeId") != null && !hm.get("storeId").equals("") && !hm.get("storeId").equals("-1")) {
			query += " and store.store_id=?";
			parameters.add(hm.get("storeId"));
		}
		
		

		return getListOfLinkedHashHashMap(parameters, query, con);
	}
	
	
	public List<LinkedHashMap<String, Object>> getStockStatusClubbed(HashMap<String, Object> hm, Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		
		String query = "select item_id,fileName,firm_name,group_concat(ware_house_name) ware_house_name,item_name,product_code,size,color,round(sum(availableqty)) sumQty,group_concat(vendor_invoice_no) purchaseInvoices from (\r\n"
				+ " select\r\n"
				+ "	concat(tam.attachment_id,tam.file_name) fileName,tpir.invoice_no,tpir.vendor_invoice_no, items.item_name ,tpid.qty purchasedqty,sum(coalesce(tsid.qty,0)*tsir.activate_flag) soldqty, \r\n"
				+ "	tpid.qty -sum(coalesce(tsid.qty,0)*COALESCE (tsir.activate_flag,0))  as availableqty ,tpid.details_id,tsir.activate_flag ,tpid.invoice_id,tpid.rate PurchaseRate,\r\n"
				+ "	tpid.details_id as PurchaseDetailsId,items.item_id,items.gst,items.price,items.size,items.color ,items.product_code ,mf.firm_name,mwh.ware_house_name \r\n"
				+ "from\r\n"
				+ "	trn_purchase_invoice_register tpir\r\n"
				+ "inner join trn_purchase_invoice_details tpid on\r\n"
				+ "	tpir.invoice_id = tpid.invoice_id left outer join trn_sales_invoice_details tsid on tsid.purchase_details_id =tpid.details_id\r\n"
				+ "	left join trn_sales_invoice_register tsir on tsir.invoice_id =tsid.invoice_id\r\n"
				+ "	left outer join mst_items items on items.item_id =tpid.item_id 	  left outer join mst_firm mf on mf.firm_id=tpir.firm_id\r\n"
				+ "	left outer join mst_ware_house mwh on mwh.ware_house_id =tpid.ware_house_id left outer join mst_category cat on cat.category_id=items.parent_category_id "
				+ "left outer join tbl_attachment_mst tam on items.item_id =tam.file_id and tam.`type` ='Image' \r\n"
				+ "	where tpir.activate_flag =1  searchCondition wareHouseCondition categoryCondition firmCondition\r\n"
				+ "	group by tpid.details_id \r\n"
				+ "	) as T where availableqty !=0 group by firm_name,product_code  \r\n"
				+ "order by item_name ";
				
		
		
		if (hm.get("searchString") != null && !hm.get("searchString").equals("") && !hm.get("searchString").equals("-1")) {
			query=query.replaceFirst("searchCondition", " and (items.product_code=? or items.item_name like ? )") ;
			parameters.add(hm.get("searchString"));
			parameters.add("%"+hm.get("searchString")+"%");
			
		}
		else
		{
			query=query.replaceFirst("searchCondition", " ") ;
		}
		
		
		if (hm.get("warehouseid") != null && !hm.get("warehouseid").equals("") && !hm.get("warehouseid").equals("-1")) {
			query=query.replaceFirst("wareHouseCondition", " and mwh.ware_house_id=? ") ;
			parameters.add(hm.get("warehouseid"));
		}
		else
		{
			query=query.replaceFirst("wareHouseCondition", " ") ;
		}
		

		if (hm.get("categoryId") != null && !hm.get("categoryId").equals("") && !hm.get("categoryId").equals("-1")) {
			query=query.replaceFirst("categoryCondition", " and cat.category_id=? ") ;
			parameters.add(hm.get("categoryId"));
		}
		else
		{
			query=query.replaceFirst("categoryCondition", " ") ;

		}

		if (hm.get("firmId") != null && !hm.get("firmId").equals("") && !hm.get("firmId").equals("-1")) {
			query=query.replaceFirst("firmCondition", " and mf.firm_id=? ") ;
			parameters.add(hm.get("firmId"));
		}
		else
		{
			query=query.replaceFirst("firmCondition", " ") ;
		}
		
		
		
		
		
		
		

		return getListOfLinkedHashHashMap(parameters, query, con);
	}

	

	public List<LinkedHashMap<String, Object>> getStockStatus(HashMap<String, Object> hm, Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		
		String query = "select * from (\r\n"
				+ " select\r\n"
				+ "	tpir.invoice_no,tpir.vendor_invoice_no, items.item_name ,tpid.qty purchasedqty,sum(coalesce(tsid.qty,0)*tsir.activate_flag) soldqty, \r\n"
				+ "	tpid.qty -sum(coalesce(tsid.qty,0)*COALESCE (tsir.activate_flag,0))  as availableqty ,tpid.details_id,tsir.activate_flag ,tpid.invoice_id,tpid.rate PurchaseRate,\r\n"
				+ "	tpid.details_id as PurchaseDetailsId,items.item_id,items.gst,items.price,items.size,items.color ,items.product_code ,mf.firm_name,mwh.ware_house_name,tpid.rate*(tpid.qty -sum(coalesce(tsid.qty,0)*COALESCE (tsir.activate_flag,0))) value1  \r\n"
				+ "from\r\n"
				+ "	trn_purchase_invoice_register tpir\r\n"
				+ "inner join trn_purchase_invoice_details tpid on\r\n"
				+ "	tpir.invoice_id = tpid.invoice_id left outer join trn_sales_invoice_details tsid on tsid.purchase_details_id =tpid.details_id\r\n"
				+ "	left join trn_sales_invoice_register tsir on tsir.invoice_id =tsid.invoice_id\r\n"
				+ "	left outer join mst_items items on items.item_id =tpid.item_id 	  left outer join mst_firm mf on mf.firm_id=tpir.firm_id\r\n"
				+ "	left outer join mst_ware_house mwh on mwh.ware_house_id =tpid.ware_house_id left outer join mst_category cat on cat.category_id=items.parent_category_id \r\n"
				+ "	where tpir.activate_flag =1  searchCondition wareHouseCondition categoryCondition firmCondition\r\n"
				+ "	group by tpid.details_id \r\n"
				+ "	) as T where availableqty !=0\r\n"
				+ "order by item_name ";
				
		
		
		if (hm.get("searchString") != null && !hm.get("searchString").equals("") && !hm.get("searchString").equals("-1")) {
			query=query.replaceFirst("searchCondition", " and (items.product_code=? or items.item_name like ? )") ;
			parameters.add(hm.get("searchString"));
			parameters.add("%"+hm.get("searchString")+"%");
			
		}
		else
		{
			query=query.replaceFirst("searchCondition", " ") ;
		}
		
		
		if (hm.get("warehouseid") != null && !hm.get("warehouseid").equals("") && !hm.get("warehouseid").equals("-1")) {
			query=query.replaceFirst("wareHouseCondition", " and mwh.ware_house_id=? ") ;
			parameters.add(hm.get("warehouseid"));
		}
		else
		{
			query=query.replaceFirst("wareHouseCondition", " ") ;
		}
		

		if (hm.get("categoryId") != null && !hm.get("categoryId").equals("") && !hm.get("categoryId").equals("-1")) {
			query=query.replaceFirst("categoryCondition", " and cat.category_id=? ") ;
			parameters.add(hm.get("categoryId"));
		}
		else
		{
			query=query.replaceFirst("categoryCondition", " ") ;

		}

		if (hm.get("firmId") != null && !hm.get("firmId").equals("") && !hm.get("firmId").equals("-1")) {
			query=query.replaceFirst("firmCondition", " and mf.firm_id=? ") ;
			parameters.add(hm.get("firmId"));
		}
		else
		{
			query=query.replaceFirst("firmCondition", " ") ;
		}
		
		
		
		
		
		
		

		return getListOfLinkedHashHashMap(parameters, query, con);
	}

	

	

	public boolean ProductExistForThisCategory(long categoryId, Connection con)
			throws ClassNotFoundException, SQLException {
		boolean returnvalue = true;
		int count = 0;

		PreparedStatement stmnt = con.prepareStatement(
				"SELECT COUNT(1) AS cnt FROM mst_items WHERE parent_category_id=? AND activate_flag=1");
		stmnt.setLong(1, categoryId);

		ResultSet rs = stmnt.executeQuery();
		while (rs.next()) {
			count = rs.getInt(1);
		}
		if (count == 0) {
			returnvalue = false;
		}
		stmnt.close();
		rs.close();

		return returnvalue;
	}
	public boolean ProductExistForThisWareHouse(long categoryId, Connection con)
			throws ClassNotFoundException, SQLException {
		boolean returnvalue = true;
		int count = 0;

		PreparedStatement stmnt = con.prepareStatement(
				"SELECT COUNT(1) AS cnt FROM stock_status WHERE ware_house_id=? AND activate_flag=1");
		stmnt.setLong(1, categoryId);

		ResultSet rs = stmnt.executeQuery();
		while (rs.next()) {
			count = rs.getInt(1);
		}
		if (count == 0) {
			returnvalue = false;
		}
		stmnt.close();
		rs.close();

		return returnvalue;
	}
	

	public String deleteCategory(long categoryId, Connection conWithF) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(categoryId);
		insertUpdateDuablDB("UPDATE mst_category  SET activate_flag=0,updated_date=SYSDATE() WHERE category_id=?",
				parameters, conWithF);
		return "Category updated Succesfully";
	}
	public String deleteWareHouse(long wareHouseId, Connection conWithF) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(wareHouseId);
		insertUpdateDuablDB("UPDATE mst_ware_house  SET activate_flag=0 WHERE ware_house_id=?",
				parameters, conWithF);
		return "Ware House updated Succesfully";
	}
	
	
	public String deletesbu(long categoryId, Connection conWithF) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(categoryId);
		insertUpdateDuablDB("UPDATE sbu_master  SET activate_flag=0,updated_date=SYSDATE() WHERE sbu_id=?",
				parameters, conWithF);
		return "SBU Deleted Succesfully";
	}
	
	public String deletebank(long bankId, Connection conWithF) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(bankId);
		insertUpdateDuablDB("UPDATE mst_bank SET activate_flag=0,updated_date=SYSDATE() WHERE bank_id=?",
				parameters, conWithF);
		return "Bank Deleted Succesfully";
	}
	
	public String deleteBrand(long categoryId, Connection conWithF) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(categoryId);
		insertUpdateDuablDB("UPDATE mst_brand  SET activate_flag=0,updated_date=SYSDATE() WHERE brand_id=?",
				parameters, conWithF);
		return "Brand Deleted Succesfully";
	}
	
	
	
	public String deleteBooking(HashMap<String, Object> hm, Connection conWithF) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("user_Id"));
		parameters.add(hm.get("booking_id"));
		
		
		insertUpdateDuablDB("update trn_booking_register set activate_flag=0,updated_by=?,updated_date=sysdate() where booking_id=?",
				parameters, conWithF);
		return "Booking Deleted Succesfully";
	}
	
	

	public long removeRoleFromUser(long userId, long roleId, Connection conWithF) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(userId);
		parameters.add(roleId);
		return insertUpdateDuablDB(
				"update acl_user_role_rlt set activate_flag=0,updated_date=sysdate() where user_id=? and role_id=?",
				parameters, conWithF);
	}
	
	public long removeClientServiceMapping(long clientId, long serviceId,Connection conWithF) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(clientId);
		parameters.add(serviceId);
		return insertUpdateDuablDB(
				"update client_service_mapping set activate_flag=0,updated_date=sysdate() where client_id=? and service_id=? ",
				parameters, conWithF);
	}
	
	
	
	
	
	public boolean isItemComposite(long itemId, Connection conWithF) throws NumberFormatException, SQLException 
	{
		ArrayList<Object> parameters = new ArrayList<>();
		String query = "select count(1) cnt from rlt_composite_item_mpg rcim  where item_id =?";
		parameters.add(itemId);		
		int count = Integer.parseInt(getMap(parameters, query, conWithF).get("cnt").toString());
		return count != 0;
	}
	
	

	
	
	
	public String debitStockItem(HashMap<String, Object> item, Connection conWithF) throws Exception 
	{
		long firmId = Long.parseLong(item.get("firm_id").toString());
			long itemId = Long.parseLong(item.get("item_id").toString());
			long wareHouseId= Long.parseLong(item.get("ware_house_id").toString());
			String stockId = checkifStockAlreadyExist(firmId, itemId,wareHouseId, conWithF);
			
			if (stockId.equals("0")) 
			{
				HashMap<String, Object> stockDetails = new HashMap<>();
				stockDetails.put("drpfirmId", firmId);
				stockDetails.put("drpitems", itemId);
				stockDetails.put("qty", 0);
				stockDetails.put("app_id",item.get("app_id"));				
				stockId = String.valueOf(addStockMaster(stockDetails, conWithF));
			}
			item.put("stock_id", stockId);

			ArrayList<Object> parameters = new ArrayList<>();
			parameters.add(item.get("qty"));
			parameters.add(item.get("item_id"));
			parameters.add(firmId);
			
		
			String previousQty = getStockDetailsbyId(item, conWithF).get("qty_available");
			Double newQty = Double.valueOf(previousQty) - Double.valueOf(item.get("qty").toString());
			insertUpdateDuablDB("UPDATE stock_status  SET qty_available=qty_available-? WHERE item_id=? and firm_id=?",
					parameters, conWithF); // for update issue

			parameters = new ArrayList<>();
			parameters.add(item.get("firm_id")); // to be validated
			parameters.add(item.get("item_id"));
			parameters.add(Double.parseDouble(item.get("qty").toString()) * -1);
			parameters.add("Sales");
			parameters.add(item.get("user_id"));
			parameters.add(item.get("invoice_id"));
			parameters.add(getDateASYYYYMMDD(item.get("invoice_date").toString()));
			parameters.add(newQty);
			parameters.add(item.get("app_id"));
			parameters.add(item.get("ware_house_id"));

			insertUpdateDuablDB(
					"insert into trn_stock_register values (default,?,?,?,?,?,sysdate(),'Against Invoice',?,?,?,?,?)",
					parameters, conWithF);

		
		return "Stock Debited Succesfully";

	}
	
	
	public String addStockAgainstCorrection(HashMap<String, Object> hm, Connection conWithF) throws Exception 
	{
		long firmId = Long.parseLong(hm.get("firm_id").toString());
		long wareHouseId= Long.parseLong(hm.get("ware_house_id").toString());
		List<HashMap<String, Object>> itemDetailsList = (List<HashMap<String, Object>>) hm.get("itemDetails");

		for (HashMap<String, Object> item : itemDetailsList) 
		{
			long itemId = Long.parseLong(item.get("item_id").toString());
			String stockId = checkifStockAlreadyExist(firmId, itemId,wareHouseId, conWithF);
			if (stockId.equals("0")) 
			{
				HashMap<String, Object> stockDetails = new HashMap<>();
				stockDetails.put("drpfirmId", firmId);
				stockDetails.put("drpitems", itemId);
				stockDetails.put("qty", 0);
				stockDetails.put("app_id",hm.get("app_id"));				
				stockId = String.valueOf(addStockMaster(stockDetails, conWithF));

			}

			ArrayList<Object> parameters = new ArrayList<>();
			parameters.add((Double.valueOf(item.get("qty").toString())*-1));
			parameters.add(item.get("item_id"));
			parameters.add(firmId);
			
			hm.put("stock_id", stockId);
			String previousQty = getStockDetailsbyId(hm, conWithF).get("qty_available");
			Double newQty = Double.valueOf(previousQty) - (Double.valueOf(item.get("qty").toString())*-1);
			insertUpdateDuablDB("UPDATE stock_status  SET qty_available=qty_available-? WHERE item_id=? and firm_id=?",
					parameters, conWithF); // for update issue

			parameters = new ArrayList<>();
			parameters.add(hm.get("firm_id")); // to be validated
			parameters.add(item.get("item_id"));
			parameters.add(Double.parseDouble(item.get("qty").toString()));
			parameters.add("AddAgainstDeleteInvoice");
			parameters.add(hm.get("user_id"));
			parameters.add(hm.get("invoice_id"));
			parameters.add(getDateASYYYYMMDD(getDateFromDB(conWithF)));
			parameters.add(newQty);
			parameters.add(hm.get("app_id"));
			parameters.add(item.get("ware_house_id"));

			insertUpdateDuablDB(
					"insert into trn_stock_register values (default,?,?,?,?,?,sysdate(),'AddAgainstDeleteInvoice',?,?,?,?,?)",
					parameters, conWithF);

		}
		return "Stock Reverted";

	}

	public void addStockRegister(HashMap<String, Object> hm, Connection conWithF)
			throws SQLException, ParseException, NumberFormatException, ClassNotFoundException {
		String stockId = checkifStockAlreadyExist(Long.valueOf(hm.get("drpfirmId").toString()),
				Long.valueOf(hm.get("drpitems").toString()),Long.valueOf(hm.get("ware_house_id").toString()), conWithF);
		hm.put("stock_id", stockId);
		String previousQty = getStockDetailsbyId(hm, conWithF).get("qty_available");
		previousQty=previousQty==null?"0":previousQty;
		Double newQty = Double.valueOf(previousQty) + Double.valueOf(hm.get("qty").toString());
		ArrayList<Object> parameters = new ArrayList<>();
		parameters = new ArrayList<>();
		parameters.add(hm.get("drpfirmId")); // to be validated
		parameters.add(hm.get("drpitems"));
		parameters.add(Double.parseDouble(hm.get("qty").toString()));
		parameters.add(hm.get("type"));
		parameters.add(hm.get("user_id"));
		parameters.add(hm.get("remarks"));
		parameters.add(hm.get("invoice_id"));
		parameters.add(getDateASYYYYMMDD(getDateFromDB(conWithF)));
		parameters.add(newQty);
		parameters.add(hm.get("app_id"));
		parameters.add(hm.get("price"));
		parameters.add(hm.get("ware_house_id"));
		
		insertUpdateDuablDB("insert into trn_stock_register values (default,?,?,?,?,?,sysdate(),?,?,?,?,?,?,?)", parameters,
				conWithF);
	}

	public HashMap<String, Object> saveInvoice(HashMap<String, Object> hm, Connection conWithF) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		long invoiceNo=getPkForThistable("trn_sales_invoice_register",Long.valueOf(hm.get("app_id").toString()),conWithF);
		
		
		parameters.add(hm.get("Client_id"));
		parameters.add(hm.get("gross_amount"));		
		parameters.add(hm.get("total_amount"));
		parameters.add(getDateASYYYYMMDD(hm.get("invoice_date").toString()));
		parameters.add(hm.get("user_id"));
		
		parameters.add(hm.get("firm_id"));
		parameters.add(hm.get("remarks"));
		parameters.add(hm.get("app_id"));
		parameters.add(invoiceNo);
		
		parameters.add(hm.get("total_gst"));
		parameters.add(hm.get("freight_amount"));
		parameters.add(hm.get("other_amount"));
		
		
		
		long invoiceId=insertUpdateDuablDB(
				"insert into trn_sales_invoice_register values (default,?,?,?,?,?,sysdate(),1,"
				+ "?,?,?,?,?,?,?)", parameters,
				conWithF);
		hm.put("invoice_id", invoiceId);
		
		
		
		
		

		List<HashMap<String, Object>> itemDetailsList = (List<HashMap<String, Object>>) hm.get("itemDetails");
		for (HashMap<String, Object> item : itemDetailsList) {
			parameters = new ArrayList<>();
			parameters.add(invoiceId);						
			parameters.add(item.get("item_id"));			
			parameters.add(item.get("ware_house_id"));
			parameters.add(item.get("job_sheet_no"));
			parameters.add(item.get("qty"));
			parameters.add(item.get("rate"));
			parameters.add(item.get("gst_amount"));
			parameters.add(item.get("gst_percentage"));
			parameters.add(item.get("item_amount"));
			parameters.add(item.get("user_id"));
			parameters.add(hm.get("app_id"));
			parameters.add(item.get("purchase_details_id"));

			insertUpdateDuablDB("insert into trn_sales_invoice_details values (default,?,?,?,?,?,?,"
					+ "?,?,?,?,sysdate(),?,?)", parameters,
					conWithF);
		}		
		hm.put("invoice_id", invoiceId);
		hm.put("invoice_no", invoiceNo);
		return hm;
	}
	
	public HashMap<String, Object> savePurchaseInvoice(HashMap<String, Object> hm, Connection conWithF) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		long invoiceNo=getPkForThistable("trn_purchase_invoice_register",Long.valueOf(hm.get("app_id").toString()),conWithF);
		
		
		parameters.add(hm.get("Client_id"));
		parameters.add(hm.get("gross_amount"));		
		parameters.add(hm.get("total_amount"));
		parameters.add(getDateASYYYYMMDD(hm.get("invoice_date").toString()));
		parameters.add(hm.get("user_id"));
		
		parameters.add(hm.get("firm_id"));
		parameters.add(hm.get("remarks"));
		parameters.add(hm.get("app_id"));
		parameters.add(invoiceNo);
		
		parameters.add(hm.get("total_gst"));
		parameters.add(hm.get("freight_amount"));
		parameters.add(hm.get("other_amount"));
		parameters.add(hm.get("txttallyrefno"));
		parameters.add(hm.get("txtvendorinvoiceno"));
		
		
		
		long invoiceId=insertUpdateDuablDB(
				"insert into trn_purchase_invoice_register values (default,?,?,?,?,?,sysdate(),1,"
				+ "?,?,?,?,?,?,?,?,?)", parameters,
				conWithF);
		hm.put("invoice_id", invoiceId);
		
		
		
		
		

		List<HashMap<String, Object>> itemDetailsList = (List<HashMap<String, Object>>) hm.get("itemDetails");
		for (HashMap<String, Object> item : itemDetailsList) {
			parameters = new ArrayList<>();
			parameters.add(invoiceId);						
			parameters.add(item.get("item_id"));			
			parameters.add(item.get("ware_house_id"));
			parameters.add(item.get("job_sheet_no"));
			parameters.add(item.get("qty"));
			parameters.add(new DecimalFormat("#.00000").format(new BigDecimal(item.get("rate").toString())));
			parameters.add(new DecimalFormat("#.00000").format(new BigDecimal(item.get("gst_amount").toString())));			
			parameters.add(item.get("gst_percentage"));
			parameters.add(item.get("item_amount"));
			parameters.add(item.get("user_id"));
			parameters.add(hm.get("app_id"));			

			insertUpdateDuablDB("insert into trn_purchase_invoice_details values (default,?,?,?,?,?,?,"
					+ "?,?,?,?,sysdate(),?)", parameters,
					conWithF);
		}		
		hm.put("invoice_id", invoiceId);
		hm.put("invoice_no", invoiceNo);
		return hm;
	}
	
	public HashMap<String, Object> saveChallanOut(HashMap<String, Object> hm, Connection conWithF) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		long invoiceNo=getPkForThistable("trn_challan_out",Long.valueOf(hm.get("app_id").toString()),conWithF);
		
		
		parameters.add(hm.get("Client_id"));
		
		parameters.add(getDateASYYYYMMDD(hm.get("invoice_date").toString()));
		parameters.add(hm.get("user_id"));
		
		parameters.add(hm.get("firm_id"));
		parameters.add(hm.get("remarks"));
		parameters.add(hm.get("app_id"));
		parameters.add(invoiceNo);
		
		
		
		
		
		long invoiceId=insertUpdateDuablDB(
				"insert into trn_challan_out values (default,?,?,?,sysdate(),1,?,?,?,?)", parameters,
				conWithF);
		hm.put("invoice_id", invoiceId);
		
		
		
		
		

		List<HashMap<String, Object>> itemDetailsList = (List<HashMap<String, Object>>) hm.get("itemDetails");
		for (HashMap<String, Object> item : itemDetailsList) {
			parameters = new ArrayList<>();
			parameters.add(invoiceId);						
			parameters.add(item.get("item_id"));			
			parameters.add(item.get("ware_house_id"));
			parameters.add(item.get("job_sheet_no"));
			parameters.add(item.get("qty"));
			
			parameters.add(item.get("user_id"));
			parameters.add(hm.get("app_id"));			

			insertUpdateDuablDB("insert into trn_challan_out_details values (default,?,?,?,?,?,?,sysdate(),?)", parameters,
					conWithF);
		}		
		hm.put("invoice_id", invoiceId);
		hm.put("invoice_no", invoiceNo);
		return hm;
	}
	
	
	public HashMap<String, Object> saveChallanIn(HashMap<String, Object> hm, Connection conWithF) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		long invoiceNo=getPkForThistable("trn_challan_in",Long.valueOf(hm.get("app_id").toString()),conWithF);
		
		
		parameters.add(hm.get("Client_id"));
		
		parameters.add(getDateASYYYYMMDD(hm.get("invoice_date").toString()));
		parameters.add(hm.get("user_id"));
		
		parameters.add(hm.get("firm_id"));
		parameters.add(hm.get("remarks"));
		parameters.add(hm.get("app_id"));
		parameters.add(invoiceNo);
		parameters.add(hm.get("txtvendorchallanno"));
		
		
		
		
		
		
		long invoiceId=insertUpdateDuablDB(
				"insert into trn_challan_in values (default,?,?,?,sysdate(),1,?,?,?,?,?)", parameters,
				conWithF);
		hm.put("invoice_id", invoiceId);
		
		
		
		
		

		List<HashMap<String, Object>> itemDetailsList = (List<HashMap<String, Object>>) hm.get("itemDetails");
		for (HashMap<String, Object> item : itemDetailsList) {
			parameters = new ArrayList<>();
			parameters.add(invoiceId);						
			parameters.add(item.get("item_id"));			
			parameters.add(item.get("ware_house_id"));
			parameters.add(item.get("job_sheet_no"));
			parameters.add(item.get("qty"));
			
			parameters.add(item.get("user_id"));
			parameters.add(hm.get("app_id"));			

			insertUpdateDuablDB("insert into trn_challan_in_details values (default,?,?,?,?,?,?,sysdate(),?)", parameters,
					conWithF);
		}		
		hm.put("invoice_id", invoiceId);
		hm.put("invoice_no", invoiceNo);
		return hm;
	}
	
	
	
	

	public long getPkForThistable(String sequenceName, Long appId, Connection conWithF) throws SQLException 
	{
		ArrayList<Object> parameters=new ArrayList<>();
		parameters.add(appId);
		parameters.add(sequenceName);
		
		
		long generatedPK=0;
		LinkedHashMap<String, String> hm= getMap(parameters, "select current_seq_no from seq_master where app_id=? and sequence_name=? for update", conWithF);
		
		if(hm.get("current_seq_no")==null)
		{
			parameters.clear();
			parameters.add(sequenceName);
			parameters.add(appId);			
			insertUpdateDuablDB("insert into seq_master values (default,?,0,?)", parameters, conWithF);
			generatedPK=1;
		}
		else
		{
			generatedPK=Long.valueOf(hm.get("current_seq_no"));
			generatedPK=generatedPK+1;
			parameters.clear();
			parameters.add(generatedPK);
			parameters.add(appId);
			parameters.add(sequenceName);
			insertUpdateDuablDB("update seq_master set current_seq_no=? where app_id=? and sequence_name=?", parameters, conWithF);
		}
		return generatedPK;
	}

	public long savePaymentRegister(HashMap<String, Object> hm, Connection conWithF) throws Exception 
	{
		
		ArrayList<Object> parameters = new ArrayList<>();		
		parameters.add(getDateASYYYYMMDD(hm.get("date").toString()));
		parameters.add(hm.get("Client_id"));
		parameters.add(hm.get("bankId"));
		parameters.add(hm.get("total_amount"));
		
		parameters.add(hm.get("remarks"));
				
		parameters.add(hm.get("firm_id"));
		parameters.add(hm.get("app_id"));
		parameters.add(hm.get("user_id"));
		
		parameters.add(hm.get("type"));
		
		return insertUpdateDuablDB("insert into trn_payment_register values (default,?,?,?,?,?,?,?,?,sysdate(),1,?)", parameters,
				conWithF);
		

	}
	
	public long saveInternalTransfer(HashMap<String, Object> hm, Connection conWithF) throws Exception 
	{
		
		ArrayList<Object> parameters = new ArrayList<>();
		
		parameters.add((hm.get("from_firm").toString()));
		parameters.add(hm.get("from_account"));
		parameters.add(hm.get("to_firm"));
		parameters.add(hm.get("to_account"));		
		parameters.add(hm.get("total_amount"));				
		parameters.add(getDateASYYYYMMDD(hm.get("date").toString()));
		parameters.add(hm.get("remarks"));		
		parameters.add(hm.get("user_id"));
		
		
		return insertUpdateDuablDB("insert into trn_internal_transfer values (default,?,?,?,?,?,?,?,?,sysdate(),1)", parameters,
				conWithF);
		

	}
	
	public long saveJournalInternalTransfer(HashMap<String, Object> hm, Connection conWithF) throws Exception 
	{
		
		ArrayList<Object> parameters = new ArrayList<>();
		
		parameters.add((hm.get("from_firm").toString()));		
		parameters.add(hm.get("to_firm"));				
		parameters.add(hm.get("total_amount"));				
		parameters.add(getDateASYYYYMMDD(hm.get("date").toString()));
		parameters.add(hm.get("remarks"));		
		parameters.add(hm.get("user_id"));
		
		
		return insertUpdateDuablDB("insert into trn_journal_transfer values (default,?,?,?,?,?,?,sysdate(),1)", parameters,
				conWithF);
		

	}
	
	
	
	
	
	public long savePaymentDetails(HashMap<String, Object> hm, Connection conWithF) throws Exception 
	{
		
		ArrayList<Object> parameters = new ArrayList<>();
		
		parameters.add(hm.get("payment_id"));
		parameters.add(hm.get("payment_for"));
		parameters.add(hm.get("ref_id"));
		parameters.add(hm.get("amount"));
		parameters.add(hm.get("remarks"));
		parameters.add(hm.get("job_sheet_no"));
		
		return insertUpdateDuablDB("insert into trn_payment_details values (default,?,?,?,?,?,?)", parameters,
				conWithF);
		

	}
	
	
	
	public String saveChallanAgainstInvoice(HashMap<String, Object> hm, Connection conWithF) throws Exception 
	{
		
		ArrayList<Object> parameters = new ArrayList<>();		
		
		parameters.add(hm.get("challanId"));
		parameters.add(hm.get("invoice_id"));
		parameters.add(hm.get("type"));
		
		
		
		insertUpdateDuablDB("insert into rlt_challan_invoice values (default,?,?,?,1,sysdate())", parameters,
				conWithF);
		return "Saved Added";

	}
	
	
	public String updateBrand(long brandId, Connection con, String brandName) throws Exception {

		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(brandName);
		parameters.add(brandId);
		insertUpdateDuablDB("UPDATE mst_brand  SET brand_name=?,updated_date=SYSDATE() WHERE brand_id=?",
				parameters, con);
		return "Brand updated Succesfully";

	}
	
	public String updateSBU(long brandId, Connection con, String brandName) throws Exception {

		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(brandName);
		parameters.add(brandId);
		insertUpdateDuablDB("UPDATE sbu_master  SET sbu_name=?,updated_date=SYSDATE() WHERE sbu_id=?",
				parameters, con);
		return "SBU updated Succesfully";

	}
	
	public String updateBank(HashMap<String, Object> hm, Connection con) throws Exception {

		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("bank_name"));
		parameters.add(hm.get("account_no"));
		parameters.add(hm.get("ifsc_code"));
		parameters.add(hm.get("address"));		
		parameters.add(hm.get("user_id"));		
		parameters.add(hm.get("txtopeningbalance"));
		
		parameters.add(hm.get("hdnBankId"));
		
		insertUpdateDuablDB("UPDATE mst_bank SET bank_name=?,account_no=?,ifsc_code=?,address=?,updated_date=SYSDATE(),updated_by=?,opening_balance=? "
				+ " WHERE bank_id=?",
				
				parameters, con);
		return "Bank updated Succesfully";

	}
	
	
	

	public String updateCategory(long categoryId, Connection con, String categoryName) throws Exception {

		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(categoryName);
		parameters.add(categoryId);
		insertUpdateDuablDB("UPDATE mst_category  SET category_Name=?,updated_date=SYSDATE() WHERE category_id=?",
				parameters, con);
		return "Category updated Succesfully";

	}

	
	public String updateDocument(Connection con, HashMap<String, Object> hm) throws Exception {

		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("txtdocumentname"));
		parameters.add(hm.get("txtdocumentcode"));
		parameters.add(hm.get("txtdescription"));

		parameters.add(hm.get("user_id"));
		parameters.add(hm.get("hdnDocumentId"));

		insertUpdateDuablDB("UPDATE mst_document  SET document_name=?,document_code=?,document_description=?,current_status='DRAFT',approved_by=null,updated_by=?,updated_date=sysdate() WHERE document_id=?",
				parameters, con);
		return "Document updated Succesfully";

	}
	
	public String updateWareHouse(long categoryId, Connection con, String categoryName) throws Exception {

		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(categoryName);
		parameters.add(categoryId);
		insertUpdateDuablDB("UPDATE mst_ware_house  SET ware_house_name=? WHERE ware_house_id=?",
				parameters, con);
		return "Ware House updated Succesfully";

	}

	public long addCategory(Connection con, HashMap<String, Object> hm) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("categoryName"));
		parameters.add(hm.get("app_id"));
		
		return insertUpdateDuablDB("insert into mst_category values (default,?,1,sysdate(),null,null,?)", parameters,
				con);
	}

	public long addDocument(Connection con, HashMap<String, Object> hm) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();

		parameters.add(hm.get("drpdocumentgroup"));
		parameters.add(hm.get("drpdepartmentname"));
		parameters.add(hm.get("txtdocumentname"));
		parameters.add(hm.get("txtdocumentcode"));
		parameters.add(hm.get("txtdescription"));		

		parameters.add(hm.get("user_id"));		
		
		
		return insertUpdateDuablDB("insert into mst_document values (default,?,?,?,?,?,'DRAFT',?,null,sysdate(),null,null,1)", parameters,
				con);
	}

	public long addDocumentHistory(Connection con, HashMap<String, Object> hm) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();

		parameters.add(hm.get("hdnDocumentId"));
		parameters.add(hm.get("drpdocumentgroup"));
		parameters.add(hm.get("drpdepartmentname"));
		parameters.add(hm.get("txtdocumentname"));
		parameters.add(hm.get("txtdocumentcode"));
		parameters.add(hm.get("txtdescription"));		


		parameters.add(hm.get("document_status"));		



		parameters.add(hm.get("user_id"));		
		parameters.add(hm.get("txtchangesdescription"));				
		
		
		return insertUpdateDuablDB("insert into hst_mst_document values (?,?,?,?,?,?,?,?,null,sysdate(),null,null,1,?)", parameters,
				con);
	}
	
	
	public long addWareHouse(Connection con, HashMap<String, Object> hm) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("ware_house_name"));
		parameters.add(hm.get("app_id"));
		
		return insertUpdateDuablDB("insert into mst_ware_house values (default,?,1,?)", parameters,
				con);
	}
	
	public long addBrand(Connection con, HashMap<String, Object> hm) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("brandName"));
		parameters.add(hm.get("user_id"));
		parameters.add(hm.get("app_id"));
		
		return insertUpdateDuablDB("insert into mst_brand values (default,?,1,?,sysdate(),?)", parameters,
				con);
	}
	

	public long addSBU(Connection con, HashMap<String, Object> hm) throws Exception 
	{
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("sbuName"));
		parameters.add(hm.get("drpheadid"));
		parameters.add(hm.get("user_id"));
		parameters.add(hm.get("app_id"));
		
		return insertUpdateDuablDB("insert into sbu_master values (default,?,?,?,sysdate(),1,?)", parameters,
				con);
	}
	
	public long addBank(Connection con, HashMap<String, Object> hm) throws Exception 
	{
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("bank_name"));
		parameters.add(hm.get("account_no"));
		parameters.add(hm.get("ifsc_code"));
		parameters.add(hm.get("address"));
		parameters.add(hm.get("user_id"));
		parameters.add(hm.get("app_id"));
		parameters.add(hm.get("txtopeningbalance"));
		
		return insertUpdateDuablDB("insert into mst_bank values (default,?,?,?,?,1,?,sysdate(),?,?)", parameters,
				con);
	}
	
	
	
	

	public long addStockMaster(HashMap<String, Object> stockDetails, Connection conWithF) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(stockDetails.get("drpfirmId"));
		parameters.add(stockDetails.get("drpitems"));
		parameters.add(0);
		parameters.add(stockDetails.get("app_id"));
		
		parameters.add(stockDetails.get("price")==null?"0":stockDetails.get("price"));
		parameters.add(stockDetails.get("ware_house_id"));
		return insertUpdateDuablDB("insert into stock_status values (default,?,?,?,1,0,?,?,?)", parameters, conWithF);
	}

	public long updateStockMaster(HashMap<String, Object> stockDetails, Connection conWithF) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(Double.parseDouble(stockDetails.get("qty").toString()));
		parameters.add(stockDetails.get("average_price"));
		parameters.add(stockDetails.get("stock_id"));
		
		return insertUpdateDuablDB("update stock_status set qty_available=qty_available+(?),average_price=? where stock_id=?",
				parameters, conWithF);
	}
	
	public long updateStockMasterInventoryCounting(HashMap<String, Object> stockDetails, Connection conWithF) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(Double.parseDouble(stockDetails.get("qty").toString()));
		parameters.add(stockDetails.get("stock_id"));
		return insertUpdateDuablDB("update stock_status set qty_available=? where stock_id=?",
				parameters, conWithF);
	}
	

	public List<LinkedHashMap<String, Object>> getCategories(HashMap<String, Object> hm,Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("app_id"));
		return getListOfLinkedHashHashMap(parameters,
				"SELECT category_id,category_name FROM mst_category WHERE activate_Flag=1 and app_id=?", con);

	}
	
	public List<LinkedHashMap<String, Object>> getBrands(HashMap<String, Object> hm,Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("app_id"));
		return getListOfLinkedHashHashMap(parameters,
				"SELECT * FROM mst_brand WHERE activate_Flag=1 and app_id=?", con);

	}
	
	

	public LinkedHashMap<String, String> getStockDetailsbyId(HashMap<String, Object> hm, Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("stock_id"));
		parameters.add(hm.get("app_id"));		
		return getMap(parameters, "select * from stock_status stock,mst_items item\r\n"
				+ "where stock_id=? and item.item_id=stock.item_id and stock.app_id=? and item.app_id=stock.app_id", con);

	}

	

	

	public int getMaxAttachmentNoByItemId(long itemId, Connection con) throws ClassNotFoundException, SQLException {
		int count = 0;
		PreparedStatement stmnt = con.prepareStatement("SELECT count(1) FROM tbl_attachment_mst WHERE file_id=?");
		stmnt.setLong(1, itemId);
		ResultSet rs = stmnt.executeQuery();
		while (rs.next()) {
			count = rs.getInt(1);
		}
		stmnt.close();
		rs.close();
		return count;
	}

	public String deleteService(long serviceId, Connection conWithF) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(serviceId);
		insertUpdateDuablDB(
				"UPDATE mst_services  SET activate_flag=0,updated_date=SYSDATE() WHERE service_Id=?",
				parameters, conWithF);
		return "Service Deleted Succesfully";
	}
	
	public String deletePayment(long paymentId, String userId,Connection conWithF) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(userId);		
		parameters.add(paymentId);
		insertUpdateDuablDB(
				"update trn_payment_register set activate_flag=0,updated_date=sysdate(),updated_by=? where payment_id=?",
				parameters, conWithF);
		return "Deleted Succesfully";
	}
	
	public String deleteJournal(long paymentId, String userId,Connection conWithF) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(userId);		
		parameters.add(paymentId);
		insertUpdateDuablDB(
				"update trn_journal_entry set activate_flag=0,updated_date=sysdate(),updated_by=? where journal_id=?",
				parameters, conWithF);
		return "Deleted Succesfully";
	}
	
	
	public String deleteTransfer(long paymentId, String userId,Connection conWithF) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(userId);		
		parameters.add(paymentId);
		insertUpdateDuablDB(
				"update trn_internal_transfer set activate_flag=0,updated_date=sysdate(),updated_by=? where transfer_id=?",
				parameters, conWithF);
		return "Deleted Succesfully";
	}
	
	public String deleteJournalTransfer(long paymentId, String userId,Connection conWithF) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(userId);		
		parameters.add(paymentId);
		insertUpdateDuablDB(
				"update trn_journal_transfer set activate_flag=0,updated_date=sysdate(),updated_by=? where transfer_id=?",
				parameters, conWithF);
		return "Deleted Succesfully";
	}
	
	
	
	public String deleteTransferMapping(long paymentId,Connection conWithF) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(paymentId);
		insertUpdateDuablDB(
				"update rlt_transfer_payment set activate_flag=0 where transfer_id=?",
				parameters, conWithF);
		return "Deleted Succesfully";
	}
	public String deleteTransactionsAgainstTransfer(long transferId,Connection conWithF) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(transferId);
		insertUpdateDuablDB(
				"update trn_payment_register tpr,rlt_transfer_payment rtp set tpr.activate_flag=0 where (rtp.from_payment_id=tpr.payment_id or rtp.to_payment_id=tpr.payment_id )  and transfer_id=?",
				parameters, conWithF);
		return "Deleted Succesfully";
	}
	
	
	
	
	public String saveTransferPaymentMapping(HashMap<String, Object> hm,Connection conWithF) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("transfer_id"));
		parameters.add(hm.get("from_payment_id"));
		parameters.add(hm.get("to_payment_id"));
		
		
		insertUpdateDuablDB(
				"insert into rlt_transfer_payment values (default,?,?,?,1)",
				parameters, conWithF);
		return "Mapped Succesfully";
	}
	
	
	
	
	

	public String deleteStock(long stockId, Connection conWithF) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(stockId);
		insertUpdateDuablDB("UPDATE stock_status  SET activate_flag=0 where stock_id=?", parameters, conWithF);
		return "Stock Deleted Succesfully";
	}

	public HashMap<String, Object> getDetailsforItem(long itemId, Connection con)
			throws ClassNotFoundException, SQLException {
		HashMap<String, Object> returnMap = null;

		List<HashMap<String, Object>> listofAttachments = new ArrayList<HashMap<String, Object>>();
		try {

			PreparedStatement stmnt = con.prepareStatement(
					"SELECT item_id,item_name,price,parent_category_id,price FROM mst_items WHERE item_id=?");
			stmnt.setLong(1, itemId);

			ResultSet rs = stmnt.executeQuery();
			while (rs.next()) {
				returnMap = new HashMap<String, Object>();
				returnMap.put("itemId", rs.getString(1));
				returnMap.put("itemName", rs.getString(2));
				returnMap.put("itemPrice", rs.getString(3));
				returnMap.put("itemParentCategoryId", rs.getString(4));
				returnMap.put("price", rs.getString(5));
				

				stmnt = con.prepareStatement(
						"SELECT attachment_id,concat(attachment_id,file_name) as file_name ,file_id,length(attachment_asblob) FROM tbl_attachment_mst WHERE file_id=? AND TYPE='Image' and activate_flag=1");
				stmnt.setLong(1, itemId);
				rs = stmnt.executeQuery();
				HashMap<String, Object> attachment = null;

				while (rs.next()) {
					attachment = new HashMap<>();

					attachment.put("attachmentId", rs.getString(1));
					attachment.put("path", "BufferedImagesFolder/" + rs.getString(2));
					attachment.put("file_id", rs.getString(3));
					attachment.put("file_size", rs.getLong(4) / 1024);
					listofAttachments.add(attachment);

				}

			}
			returnMap.put("listofAttachments", listofAttachments);
			stmnt.close();
			rs.close();

		} catch (Exception e) {
			writeErrorToDB(e);
		}

		return returnMap;
	}

	

	

	

	

	



	public String deleteAttachment(long attachmentId, Connection conWithF) throws Exception {
		try {
			String insertTableSQL = "delete from tbl_attachment_mst where  attachment_id=?";

			PreparedStatement preparedStatement = conWithF.prepareStatement(insertTableSQL);
			preparedStatement.setLong(1, attachmentId);
			preparedStatement.executeUpdate();

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			

			return "Attachment Deleted Successfully";
		} catch (Exception e) {
			// write to error log
			writeErrorToDB(e);
			throw e;
		}

	}

	

	public LinkedHashMap<String, String> getCategoryDetails(HashMap<String, Object> hm, Connection con) throws SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("category_id"));
		parameters.add(hm.get("app_id"));
		
		return getMap(parameters,
				"select mc.*,concat(tam.attachment_id,tam.file_name) ImagePath,tam.attachment_id as attachId from "
				+ " mst_category as mc left outer join tbl_attachment_mst tam  on tam.file_id=mc.category_id  and tam.type = 'category' where mc.category_id=? and mc.app_id=?",
				con);
	}
	
	public LinkedHashMap<String, String> getWareHouseDetails(HashMap<String, Object> hm, Connection con) throws SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("wareHouseId"));
		parameters.add(hm.get("app_id"));
		
		return getMap(parameters,
				"select * from mst_ware_house where ware_house_id=? and app_id=?",
				con);
	}
	
	
	public LinkedHashMap<String, String> getBrandDetail(HashMap<String, Object> hm, Connection con) throws SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("brand_id"));
		parameters.add(hm.get("app_id"));
		
		return getMap(parameters,
				"select mc.*,concat(tam.attachment_id,tam.file_name) ImagePath,tam.attachment_id as attachId from "
				+ " mst_brand as mc left outer join tbl_attachment_mst tam  on tam.file_id=mc.brand_id  and tam.type = 'brand' where mc.brand_id=? and mc.app_id=?",
				con);
	}
	
	public LinkedHashMap<String, String> getsbuDetail(HashMap<String, Object> hm, Connection con) throws SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("sbu_id"));
		parameters.add(hm.get("app_id"));
		
		return getMap(parameters,
				"select user.*,mc.*,concat(tam.attachment_id,tam.file_name) ImagePath,tam.attachment_id as attachId from "
				+ " sbu_master as mc inner join tbl_user_mst user on user.user_id=mc.sbu_head_id left outer join tbl_attachment_mst tam  on tam.file_id=mc.sbu_id  and tam.type = 'brand' where mc.sbu_id=? and mc.app_id=?",
				con);
	}
	
	public LinkedHashMap<String, String> getBankDetail(HashMap<String, Object> hm, Connection con) throws SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("bank_id"));
		
		
		return getMap(parameters,
				"select * from mst_bank where bank_id=?",
				con);
	}
	
	
	
	
	
	

	public List<LinkedHashMap<String, Object>> getClientMaster(HashMap<String, Object> hm, Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		
		
		String query = "select *,date_format(date_of_setup,'%d/%m/%Y') as FormattedToDate"
				
				+ " from mst_client  where activate_flag = 1   ";
		
		

		if (hm.get("searchInput") != null && !hm.get("searchInput").equals("")) {
			query += " and (client_name like ? or company_pan like ? or gst_no like ?) ";
			parameters.add("%" + hm.get("searchInput") + "%");
			parameters.add("%" + hm.get("searchInput") + "%");
			parameters.add("%" + hm.get("searchInput") + "%");
		}

		

		

		query += " order by client_name";
		return getListOfLinkedHashHashMap(parameters, query, con);
	}

	public String deleteClient(long clientId, Connection conWithF) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(clientId);
		insertUpdateDuablDB("UPDATE mst_client  SET activate_flag=0,updated_date=SYSDATE() WHERE client_id=?",
				parameters, conWithF);
		return "Client Deleted Succesfully";
	}
	
	public String deleteTransaction(long ClientId, Connection conWithF) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(ClientId);
		insertUpdateDuablDB("UPDATE trn_payment_register  SET activate_flag=0,updated_date=SYSDATE() WHERE payment_id=?",
				parameters, conWithF);
		return "Deleted Succesfully";
	}
	
	

	public long updateClient(HashMap<String, Object> clientDetails, Connection con)
			throws ClassNotFoundException, SQLException, ParseException {
		ArrayList<Object> parameters = new ArrayList<>();
		
		parameters.add(clientDetails.get("client_name"));
		
		parameters.add(clientDetails.get("address_of_the_establishment"));
		parameters.add(getDateASYYYYMMDD(clientDetails.get("txtdateOfSetup").toString()));
		
		parameters.add(clientDetails.get("company_pan"));

		parameters.add(clientDetails.get("ownership_details"));
		parameters.add(clientDetails.get("gst_no"));
		parameters.add(clientDetails.get("contact_person_name"));
		parameters.add(clientDetails.get("contact_person_email_id"));
		parameters.add(clientDetails.get("contact_person_mobile_no"));
		parameters.add(clientDetails.get("director_name"));
		parameters.add(clientDetails.get("director_pan_card"));
		parameters.add(getDateASYYYYMMDD(clientDetails.get("txtdirectorDob").toString()));
		parameters.add(clientDetails.get("director_email_id"));
		parameters.add(clientDetails.get("director_mobile_no"));
		parameters.add(clientDetails.get("billing_amount"));
		
		
		
		parameters.add(Long.parseLong(clientDetails.get("clientId").toString()));
		
		String insertQuery = "update mst_client set client_name=?,address_of_the_establishment=?,date_of_setup=?,company_pan=?,ownership_details=?,gst_no=?,contact_person_name=?,contact_person_email_id=?,"
				+ "contact_person_mobile_no=?,director_name=?,director_pan_card=?,director_dob=?,director_email_id=?,director_mobile_no=?,billing_amount=? where client_id=?";
		return insertUpdateDuablDB(insertQuery, parameters, con);
	}
	
	
	

	public long saveClient(HashMap<String, Object> clientDetails, Connection con)
			throws ClassNotFoundException, SQLException, ParseException {
			
		ArrayList<Object> parameters = new ArrayList<>();
		
		parameters.add(clientDetails.get("client_name"));
		parameters.add (clientDetails.get("address_of_the_establishment"));
		parameters.add(getDateASYYYYMMDD(clientDetails.get("txtdateOfSetup").toString()) );		
		
		parameters.add(clientDetails.get("company_pan"));
		parameters.add(clientDetails.get("ownership_details"));
		parameters.add(clientDetails.get("gst_no"));
		parameters.add(clientDetails.get("contact_person_name"));
		parameters.add(clientDetails.get("contact_person_email_id"));
		parameters.add(clientDetails.get("contact_person_mobile_no"));
		parameters.add(clientDetails.get("director_name"));
		parameters.add(clientDetails.get("director_pan_card"));
		
		parameters.add(clientDetails.get("user_id"));
		parameters.add(getDateASYYYYMMDD(clientDetails.get("txtdirectorDob").toString()));
		parameters.add(clientDetails.get("director_email_id"));
		parameters.add(clientDetails.get("director_mobile_no"));
		parameters.add(clientDetails.get("billing_amount"));
		String insertQuery = "insert into mst_client values"
				+ " (default,?,?,?,?,?,?,?,?,?,?,?,1,?,?,sysdate(),?,?,?)";

		return insertUpdateDuablDB(insertQuery, parameters, con);
	}

	public LinkedHashMap<String, String> getClientDetails(long clientId, Connection con) throws SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(clientId);
		return getMap(parameters, "select *,date_format(date_of_setup, '%d/%m/%Y') as dateOfSetup, date_format(director_dob, '%d/%m/%Y') as directorDOB from mst_client where client_id=?", con);
	}

	public LinkedHashMap<String, String> getPendingAmountForThisClient(long clientId, String fromDate,
			String toDate, Connection con) throws SQLException, ParseException {
		ArrayList<Object> parameters = new ArrayList<>();
		String query = "select sum(debitAmount)-sum(creditAmount) pendingAmount from (\r\n"
				+ " select firm_id,invoice_id as RefId ,invoice_date transaction_date,total_amount as debitAmount,0 creditAmount,remarks,updated_date updDate,'Sales' type from trn_sales_invoice_register tsir where \r\n"
				+ "	client_id =? and invoice_date  between ? and ? and tsir.activate_flag =1	\r\n"
				+ "union all\r\n"
				+ "select firm_id,payment_id as RefId,payment_date transaction_date,\r\n"
				+ "	case when type='P' then amount  else 0 end as debitAmount,\r\n"
				+ "	case when type='R' then amount else 0 end as creditAmount,remarks ,updated_date updDate,\r\n"
				+ "	case when type='P' then 'Payment' else 'Collection' end type\r\n"
				+ "	from trn_payment_register tpr  where \r\n"
				+ "		client_id =? and payment_date  between ? and ?	and tpr.activate_flag =1	\r\n"
				+ "union all \r\n"
				+ "	select firm_id,invoice_id as RefId,invoice_date transaction_date,0 as debitAmount,total_amount creditAmount,remarks,updated_date updDate,\r\n"
				+ "	'Purchase' type\r\n"
				+ "	from trn_purchase_invoice_register tsir where \r\n"
				+ "	client_id =? and invoice_date  between ? and ?  and tsir.activate_flag=1 union all \r\n"
				+ "	select firm_id,journal_id as RefId,journal_date transaction_date,0 as debitAmount,total_amount creditAmount,remarks,updated_date updDate,\r\n"
				+ "	'JournalEntryCredit' type\r\n"
				+ "	from trn_journal_entry tje where \r\n"
				+ "	credit =? and tje.journal_date  between ? and ?  and tje.activate_flag =1\r\n"
				+ " union all \r\n"
				+ "	select firm_id,journal_id as RefId,journal_date transaction_date,total_amount as debitAmount,0 creditAmount,remarks,updated_date updDate,\r\n"
				+ "	'JournalEntryDebit' type\r\n"
				+ "	from trn_journal_entry tje where \r\n"
				+ "	debit =? and tje.journal_date  between ? and ?  and tje.activate_flag =1 \r\n"
				+ "	) as T,mst_firm firm where T.firm_id=firm.firm_id \r\n"
				+ "	order by transaction_date,updDate asc";

		
			parameters.add(clientId);
			parameters.add(getDateASYYYYMMDD(fromDate));
			parameters.add(getDateASYYYYMMDD(toDate));
			
			parameters.add(clientId);
			parameters.add(getDateASYYYYMMDD(fromDate));
			parameters.add(getDateASYYYYMMDD(toDate));
			
			parameters.add(clientId);
			parameters.add(getDateASYYYYMMDD(fromDate));
			parameters.add(getDateASYYYYMMDD(toDate));
			
			parameters.add(clientId);
			parameters.add(getDateASYYYYMMDD(fromDate));
			parameters.add(getDateASYYYYMMDD(toDate));
			
			parameters.add(clientId);
			parameters.add(getDateASYYYYMMDD(fromDate));
			parameters.add(getDateASYYYYMMDD(toDate));
			

		return getMap(parameters, query, con);
	}
	
	

	public HashMap<String, String> getDataForHomepage(HashMap<String, Object> hm,Connection conWithF) throws SQLException {
		
		
		String query="select \r\n"
				+ " (select count(1) from mst_items where activate_flag=1 and  app_id=?) as ActiveItems,\r\n"
				+ " (select count(1) from mst_category where activate_flag=1 and app_id=?) as ActiveCategories,\r\n"
				+ " (select count(1) from mst_Client where activate_flag=1 and  app_id=? and client_vendor_flag='C') as ActiveClients, \r\n"
				+ " (select count(1) from mst_Client where activate_flag=1 and  app_id=? and client_vendor_flag='V') as ActiveVendors \r\n"
				+ "  \r\n"
				+ " from dual";
		
		ArrayList<Object> parameters=new ArrayList<>();
		parameters.add(hm.get("app_id"));
		parameters.add(hm.get("app_id"));
		parameters.add(hm.get("app_id"));
		parameters.add(hm.get("app_id"));
		
		return getMap(parameters,query,conWithF);
		
		
	}
	
	
	

	

	


	

	public List<LinkedHashMap<String, Object>> getfirmMaster(HashMap<String, Object> hm, Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("app_id"));
		return getListOfLinkedHashHashMap(parameters,
				"select firm_id firmId,firm_name firmName, address_line_1 address_line_1,"
				+ "address_line_2 address_line_2, firm_email firmEmail,sbu_name,city from mst_firm firm,sbu_master mast where firm.activate_flag=1 and firm.app_id=? and firm.parent_sbu_id=mast.sbu_id ",
				con);

	}
	
	public List<LinkedHashMap<String, Object>> getPendingInvoicesForThisClient(String ClientId, Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(ClientId);
		return getListOfLinkedHashHashMap(parameters,
				"select\r\n"
				+ "	tsir.invoice_id ,\r\n"
				+ "	total_amount,\r\n"
				+ "	COALESCE (sum(tpd.amount),\r\n"
				+ "	0) smAmount,\r\n"
				+ "	total_amount - COALESCE (sum(tpd.amount),\r\n"
				+ "	0) pendingAmount,\r\n"
				+ "	date_format(tsir.invoice_date, '%d/%m/%Y') as invoiceDate\r\n"
				+ "from\r\n"
				+ "	trn_sales_invoice_register tsir\r\n"
				+ "left outer join trn_payment_register tpr on\r\n"
				+ "	tsir.Client_id = tpr.Client_id \r\n"
				+ "left outer join trn_payment_details tpd on tpd.payment_id =tpr.payment_id and tsir.invoice_id =tpd.ref_id \r\n"
				+ "where\r\n"
				+ "	tsir.Client_id = ? \r\n"
				+ "group by\r\n"
				+ "	tsir.invoice_id\r\n"
				+ "HAVING\r\n"
				+ "	total_amount>smAmount;",
				con);

	}
	
	
	public List<LinkedHashMap<String, Object>> getPendingInvoicesForThisVendor(String ClientId, Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(ClientId);
		return getListOfLinkedHashHashMap(parameters,
				"select\r\n"
				+ "	tsir.invoice_id ,\r\n"
				+ "	total_amount,\r\n"
				+ "	COALESCE (sum(tpd.amount),\r\n"
				+ "	0) smAmount,\r\n"
				+ "	total_amount - COALESCE (sum(tpd.amount),\r\n"
				+ "	0) pendingAmount,\r\n"
				+ "	date_format(tsir.invoice_date, '%d/%m/%Y') as invoiceDate\r\n"
				+ "from\r\n"
				+ "	trn_purchase_invoice_register tsir\r\n"
				+ "left outer join trn_payment_register tpr on\r\n"
				+ "	tsir.Client_id = tpr.Client_id \r\n"
				+ "left outer join trn_payment_details tpd on tpd.payment_id =tpr.payment_id and tsir.invoice_id =tpd.ref_id \r\n"
				+ "where\r\n"
				+ "	tsir.Client_id = ? \r\n"
				+ "group by\r\n"
				+ "	tsir.invoice_id\r\n"
				+ "HAVING\r\n"
				+ "	total_amount>smAmount;",
				con);

	}
	
	
	
	
	public List<LinkedHashMap<String, Object>> getInvoiceFormatList(HashMap<String, Object> hm, Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();	
		return getListOfLinkedHashHashMap(parameters,
				"select * from invoice_formats",
				con);

	}
	
	

	public LinkedHashMap<String, String> getfirmDetails(long firmId, Connection con) throws SQLException {

		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(firmId);
		return getMap(parameters,
				"select * from mst_firm where firm_id=?", con);

	}

	public long addfirm(Connection conWithF, HashMap<String, Object> hm) throws SQLException {

		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("drpsbuname"));
		parameters.add(hm.get("firmName"));
		parameters.add((String) hm.get("address_line_1"));
		parameters.add((String) hm.get("firmEmail"));
		
		parameters.add((String) hm.get("app_id"));
		
		
		parameters.add((String) hm.get("address_line_2"));
		parameters.add((String) hm.get("city"));
		parameters.add((String) hm.get("pincode"));
		parameters.add((String) hm.get("txtgstno"));
		
		
		

		String insertQuery = "insert into mst_firm values (default,?,?,?,?,1,null,sysdate(),?,?,?,?,?)";

		return insertUpdateDuablDB(insertQuery, parameters, conWithF);

	}

	public String updatefirm(long firmId, Connection conWithF, HashMap<String, Object> hm) throws SQLException {

		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("drpsbuname"));
		parameters.add(hm.get("firmName"));
		parameters.add((String) hm.get("address_line_1"));
		parameters.add((String) hm.get("address_line_2"));
		parameters.add((String) hm.get("city"));
		parameters.add((String) hm.get("pincode"));
		parameters.add((String) hm.get("firmEmail"));
		parameters.add((String) hm.get("txtgstno"));
		parameters.add(firmId);
		insertUpdateDuablDB(
				"UPDATE mst_firm  SET parent_sbu_id=?,firm_name=?, address_line_1 = ?,address_line_2 = ?,city=?,pincode=?, firm_email=?, updated_date=SYSDATE(),gst_no=? WHERE firm_id=?",
				parameters, conWithF);
		return "firm Updated Succesfully";

	}

	public String deletefirm(long firmId, Connection conWithF) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(firmId);
		insertUpdateDuablDB("UPDATE mst_firm  SET activate_flag=0,updated_date=SYSDATE() WHERE firm_id=?", parameters,
				conWithF);
		return "firm Deleted Succesfully";
	}

	
	public List<LinkedHashMap<String, Object>> getBankPassBook(HashMap<String, Object> hm1, Connection con)
			throws ClassNotFoundException, SQLException, ParseException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(getDateASYYYYMMDD(hm1.get("fromDate").toString()));
		parameters.add(getDateASYYYYMMDD(hm1.get("toDate").toString()));
		

		String query = "select\r\n"
				+ "	*,type,\r\n"
				+ "	date_format(tpr.payment_date, '%d/%m/%Y') as formattedPaymentDate,\r\n"
				+ "	tpr.updated_date as updatedDate,\r\n"
				+ "	case when type='P' then \r\n"
				+ "		(select firm.firm_name from rlt_transfer_payment rtp ,trn_internal_transfer tit,mst_firm firm  where \r\n"
				+ "		rtp.transfer_id=tit.transfer_id and rtp.from_payment_id =tpr.payment_id and firm.firm_id=tit.to_firm)\r\n"
				+ "		ELSE \r\n"
				+ "		(select firm.firm_name from rlt_transfer_payment rtp ,trn_internal_transfer tit,mst_firm firm  where\r\n"
				+ "		rtp.transfer_id=tit.transfer_id and rtp.to_payment_id =tpr.payment_id  and firm.firm_id=tit.from_firm) end TransferFirmName ,tpr.Client_id,"
				+ " case when type='P' then amount else 0 end as CreditAmount ,"
				+ "	case when type='R' then amount else 0  end as DebitAmount\r\n"
				+ "from\r\n"
				+ "	trn_payment_register tpr left outer join mst_Client cust on tpr.Client_id = cust.Client_id\r\n"
				+ "	left outer join tbl_user_mst user on user.user_id = tpr.updated_by "
				+ "inner join mst_firm firm on firm.firm_id=tpr.firm_id inner join mst_bank bank on bank.bank_id=tpr.bank_id 	\r\n"
				+ "where\r\n"
				+ "	tpr.payment_date between ? and ? \r\n"
				+ "	bankCondition firmCondition	 and tpr.activate_flag =1 \r\n"
				+ "order by\r\n"
				+ "	payment_date asc,\r\n"
				+ "	payment_id asc;\r\n"
				+ "	";
		
		if((!hm1.get("bankId").equals("")) && !hm1.get("bankId").equals("-1"))
		{
			query=query.replaceFirst("bankCondition", "and tpr.bank_id=?");
			parameters.add((hm1.get("bankId")));
		}
		else
		{
			query=query.replaceFirst("bankCondition", "");
		}
		
		if((!hm1.get("firmId").equals("")) && !hm1.get("firmId").equals("-1"))
		{
			query=query.replaceFirst("firmCondition", "and tpr.firm_id=?");
			parameters.add((hm1.get("firmId")));
		}
		else
		{
			query=query.replaceFirst("firmCondition", "");
		}
		
		
		
		
		return getListOfLinkedHashHashMap(parameters, query, con);

	}
	
	public List<LinkedHashMap<String, Object>> getExpenseReport(HashMap<String, Object> hm1, Connection con)
			throws ClassNotFoundException, SQLException, ParseException {
		ArrayList<Object> parameters = new ArrayList<>();
		
		

		String query = "select\r\n"
				+ "	firm_name,\r\n"
				+ "	mc2.Client_name PartyName,\r\n"
				+ "	tjd.job_sheet_no job_sheet_no,\r\n"
				+ "	mc.Client_name Head,\r\n"
				+ "	tje.vendor_invoice_no ,\r\n"
				+ "	tje.journal_date TransactionDate,\r\n"
				+ "	tjd.amount,\r\n"
				+ "	tje.total_amount fullamount,\r\n"
				+ "	'JournalEntry' theType,\r\n"
				+ "	tje.updated_date ,\r\n"
				+ "	tje.updated_by\r\n"
				+ "from\r\n"
				+ "	trn_journal_entry tje,\r\n"
				+ "	trn_journal_details tjd,\r\n"
				+ "	mst_Client mc ,\r\n"
				+ "	mst_firm mf ,\r\n"
				+ "	mst_Client mc2,sbu_master sbumast \r\n"
				+ "where\r\n"
				+ "	journal_date between ? and ? and sbumast.activate_flag=1 and  sbumast.sbu_id=mf.parent_sbu_id \r\n"
				+ "	firmIdString1 sbuIdString1 \r\n"
				+ "	and tje.journal_id = tjd.journal_id\r\n"
				+ "	and mc.Client_id = tje.debit\r\n"
				+ "	and mc.group_id = 6\r\n"
				+ "	and mf.firm_id = tje.firm_id\r\n"
				+ "	and mc2.Client_id = tje.credit\r\n"
				+ "	and tje.activate_flag = 1\r\n"
				+ "union all\r\n"
				+ "select\r\n"
				+ "	firm_name,\r\n"
				+ "	mc2.Client_name PartyName,\r\n"
				+ "	tjd.job_sheet_no job_sheet_no,\r\n"
				+ "	mc.Client_name Head,\r\n"
				+ "	tje.vendor_invoice_no ,\r\n"
				+ "	tje.journal_date TransactionDate,\r\n"
				+ "	tjd.amount*-1 ,\r\n"
				+ "	tje.total_amount*-1 fullamount,\r\n"
				+ "	'JournalEntry' theType,\r\n"
				+ "	tje.updated_date ,\r\n"
				+ "	tje.updated_by\r\n"
				+ "from\r\n"
				+ "	trn_journal_entry tje,\r\n"
				+ "	trn_journal_details tjd,\r\n"
				+ "	mst_Client mc ,\r\n"
				+ "	mst_firm mf ,\r\n"
				+ "	mst_Client mc2,sbu_master sbumast\r\n"
				+ "where\r\n"
				+ "	journal_date between ? and ? and sbumast.activate_flag=1 and sbumast.sbu_id=mf.parent_sbu_id \r\n"
				+ "	firmIdString2 sbuIdString2 \r\n"
				+ "	and tje.journal_id = tjd.journal_id\r\n"
				+ "	and mc.Client_id = tje.credit\r\n"
				+ "	and mc.group_id = 6\r\n"
				+ "	and mf.firm_id = tje.firm_id\r\n"
				+ "	and mc2.Client_id = tje.debit\r\n"
				+ "	and tje.activate_flag = 1\r\n"
				+ "union all\r\n"
				+ "select\r\n"
				+ "	firm_name,\r\n"
				+ "	bank_name PartyName,\r\n"
				+ "	tpd.job_sheet_no job_sheet_no,\r\n"
				+ "	cust.Client_name ,\r\n"
				+ "	tpd.remarks,\r\n"
				+ "	payment_date TransactionDate,\r\n"
				+ "	tpd.amount,\r\n"
				+ "	tpr.amount fullamount,\r\n"
				+ "	'Payment' theType,\r\n"
				+ "	tpr.updated_date,\r\n"
				+ "	tpr.updated_by\r\n"
				+ "from\r\n"
				+ "	trn_payment_register tpr,\r\n"
				+ "	trn_payment_details tpd ,\r\n"
				+ "	mst_bank bank,\r\n"
				+ "	mst_firm firm,\r\n"
				+ "	mst_Client cust,sbu_master sbumast\r\n"
				+ "where\r\n"
				+ "	payment_date between ? and ?\r\n"
				+ "	and firm.firm_id = tpr.firm_id\r\n"
				+ "	and tpr.activate_flag = 1\r\n"
				+ "	and tpd.payment_id = tpr.payment_id\r\n"
				+ "	and tpr.type = 'P'\r\n"
				+ "	and bank.bank_id = tpr.bank_id\r\n"
				+ "	firmIdString3 sbuIdString3 \r\n"
				+ "	and cust.Client_id = tpr.Client_id  and cust.group_id =6 and sbumast.activate_flag=1 and sbumast.sbu_id = firm.parent_sbu_id ";
		
		parameters.add(getDateASYYYYMMDD(hm1.get("fromDate").toString()));
		parameters.add(getDateASYYYYMMDD(hm1.get("toDate").toString()));
		
		if(hm1.get("firmId")==null || hm1.get("firmId").equals("-1"))
		{
			query=query.replaceAll("firmIdString1", "");
		}
		else
		{
			query=query.replaceAll("firmIdString1", " and tje.firm_id=?");
			parameters.add(hm1.get("firmId"));
		}
		
		if(hm1.get("sbuId")==null || hm1.get("sbuId").equals("-1"))
		{
			query=query.replaceAll("sbuIdString1", "");
		}
		else
		{
			query=query.replaceAll("sbuIdString1", " and sbumast.sbu_id=?");
			parameters.add(hm1.get("sbuId"));
		}
		
		parameters.add(getDateASYYYYMMDD(hm1.get("fromDate").toString()));
		parameters.add(getDateASYYYYMMDD(hm1.get("toDate").toString()));
		
		if(hm1.get("firmId")==null ||hm1.get("firmId").equals("-1"))
		{
			query=query.replaceAll("firmIdString2", "");
		}
		else
		{
			query=query.replaceAll("firmIdString2", " and tje.firm_id=?");
			parameters.add(hm1.get("firmId"));
		}
		

		if(hm1.get("sbuId")==null || hm1.get("sbuId").equals("-1"))
		{
			query=query.replaceAll("sbuIdString2", "");
		}
		else
		{
			query=query.replaceAll("sbuIdString2", " and sbumast.sbu_id=?");
			parameters.add(hm1.get("sbuId"));
		}
		
		
		parameters.add(getDateASYYYYMMDD(hm1.get("fromDate").toString()));
		parameters.add(getDateASYYYYMMDD(hm1.get("toDate").toString()));
		
		if(hm1.get("firmId")==null||hm1.get("firmId").equals("-1"))
		{
			query=query.replaceAll("firmIdString3", "");
		}
		else
		{
			query=query.replaceAll("firmIdString3", " and tpr.firm_id=?");
			parameters.add(hm1.get("firmId"));
		}
		

		if(hm1.get("sbuId")==null || hm1.get("sbuId").equals("-1"))
		{
			query=query.replaceAll("sbuIdString3", "");
		}
		else
		{
			query=query.replaceAll("sbuIdString3", " and sbumast.sbu_id=?");
			parameters.add(hm1.get("sbuId"));
		}
		
		
		
		return getListOfLinkedHashHashMap(parameters, query, con);

	}
	
	
	
	public List<LinkedHashMap<String, Object>> getExpenseReportClubbed(HashMap<String, Object> hm1, Connection con)
			throws ClassNotFoundException, SQLException, ParseException {
		ArrayList<Object> parameters = new ArrayList<>();
		
		

		String query = "select T.Head,sum(amount) theAmount,Client_id from (select\r\n"
				+ "	firm_name,\r\n"
				+ "	mc2.Client_name PartyName,\r\n"
				+ "	tjd.job_sheet_no job_sheet_no,\r\n"
				+ "	mc.Client_name Head,\r\n"
				+ "	tje.vendor_invoice_no ,\r\n"
				+ "	tje.journal_date TransactionDate,\r\n"
				+ "	tjd.amount,\r\n"
				+ "	tje.total_amount fullamount,\r\n"
				+ "	'JournalEntry' theType,\r\n"
				+ "	tje.updated_date ,\r\n"
				+ "	tje.updated_by,mc.Client_id \r\n"
				+ "from\r\n"
				+ "	trn_journal_entry tje,\r\n"
				+ "	trn_journal_details tjd,\r\n"
				+ "	mst_Client mc ,\r\n"
				+ "	mst_firm mf ,\r\n"
				+ "	mst_Client mc2,sbu_master sbumast \r\n"
				+ "where\r\n"
				+ "	journal_date between ? and ? and sbumast.activate_flag=1 and  sbumast.sbu_id=mf.parent_sbu_id \r\n"
				+ "	firmIdString1 sbuIdString1 \r\n"
				+ "	and tje.journal_id = tjd.journal_id\r\n"
				+ "	and mc.Client_id = tje.debit\r\n"
				+ "	and mc.group_id = 6\r\n"
				+ "	and mf.firm_id = tje.firm_id\r\n"
				+ "	and mc2.Client_id = tje.credit\r\n"
				+ "	and tje.activate_flag = 1\r\n"
				+ "union all\r\n"
				+ "select\r\n"
				+ "	firm_name,\r\n"
				+ "	mc2.Client_name PartyName,\r\n"
				+ "	tjd.job_sheet_no job_sheet_no,\r\n"
				+ "	mc.Client_name Head,\r\n"
				+ "	tje.vendor_invoice_no ,\r\n"
				+ "	tje.journal_date TransactionDate,\r\n"
				+ "	tjd.amount*-1 ,\r\n"
				+ "	tje.total_amount*-1 fullamount,\r\n"
				+ "	'JournalEntry' theType,\r\n"
				+ "	tje.updated_date ,\r\n"
				+ "	tje.updated_by,mc2.Client_id\r\n"
				+ "from\r\n"
				+ "	trn_journal_entry tje,\r\n"
				+ "	trn_journal_details tjd,\r\n"
				+ "	mst_Client mc ,\r\n"
				+ "	mst_firm mf ,\r\n"
				+ "	mst_Client mc2,sbu_master sbumast\r\n"
				+ "where\r\n"
				+ "	journal_date between ? and ? and sbumast.activate_flag=1 and sbumast.sbu_id=mf.parent_sbu_id \r\n"
				+ "	firmIdString2 sbuIdString2 \r\n"
				+ "	and tje.journal_id = tjd.journal_id\r\n"
				+ "	and mc.Client_id = tje.credit\r\n"
				+ "	and mc.group_id = 6\r\n"
				+ "	and mf.firm_id = tje.firm_id\r\n"
				+ "	and mc2.Client_id = tje.debit\r\n"
				+ "	and tje.activate_flag = 1\r\n"
				+ "union all\r\n"
				+ "select\r\n"
				+ "	firm_name,\r\n"
				+ "	bank_name PartyName,\r\n"
				+ "	tpd.job_sheet_no job_sheet_no,\r\n"
				+ "	cust.Client_name ,\r\n"
				+ "	tpd.remarks,\r\n"
				+ "	payment_date TransactionDate,\r\n"
				+ "	tpd.amount,\r\n"
				+ "	tpr.amount fullamount,\r\n"
				+ "	'Payment' theType,\r\n"
				+ "	tpr.updated_date,\r\n"
				+ "	tpr.updated_by,cust.Client_id\r\n"
				+ "from\r\n"
				+ "	trn_payment_register tpr,\r\n"
				+ "	trn_payment_details tpd ,\r\n"
				+ "	mst_bank bank,\r\n"
				+ "	mst_firm firm,\r\n"
				+ "	mst_Client cust,sbu_master sbumast\r\n"
				+ "where\r\n"
				+ "	payment_date between ? and ?\r\n"
				+ "	and firm.firm_id = tpr.firm_id\r\n"
				+ "	and tpr.activate_flag = 1\r\n"
				+ "	and tpd.payment_id = tpr.payment_id\r\n"
				+ "	and tpr.type = 'P'\r\n"
				+ "	and bank.bank_id = tpr.bank_id\r\n"
				+ "	firmIdString3 sbuIdString3 \r\n"
				+ "	and cust.Client_id = tpr.Client_id  and cust.group_id =6 and sbumast.activate_flag=1 and sbumast.sbu_id = firm.parent_sbu_id ) as T group by T.Head ";
		
		parameters.add(getDateASYYYYMMDD(hm1.get("fromDate").toString()));
		parameters.add(getDateASYYYYMMDD(hm1.get("toDate").toString()));
		
		if(hm1.get("firmId")==null || hm1.get("firmId").equals("-1"))
		{
			query=query.replaceAll("firmIdString1", "");
		}
		else
		{
			query=query.replaceAll("firmIdString1", " and tje.firm_id=?");
			parameters.add(hm1.get("firmId"));
		}
		
		if(hm1.get("sbuId")==null || hm1.get("sbuId").equals("-1"))
		{
			query=query.replaceAll("sbuIdString1", "");
		}
		else
		{
			query=query.replaceAll("sbuIdString1", " and sbumast.sbu_id=?");
			parameters.add(hm1.get("sbuId"));
		}
		
		parameters.add(getDateASYYYYMMDD(hm1.get("fromDate").toString()));
		parameters.add(getDateASYYYYMMDD(hm1.get("toDate").toString()));
		
		if(hm1.get("firmId")==null ||hm1.get("firmId").equals("-1"))
		{
			query=query.replaceAll("firmIdString2", "");
		}
		else
		{
			query=query.replaceAll("firmIdString2", " and tje.firm_id=?");
			parameters.add(hm1.get("firmId"));
		}
		

		if(hm1.get("sbuId")==null || hm1.get("sbuId").equals("-1"))
		{
			query=query.replaceAll("sbuIdString2", "");
		}
		else
		{
			query=query.replaceAll("sbuIdString2", " and sbumast.sbu_id=?");
			parameters.add(hm1.get("sbuId"));
		}
		
		
		parameters.add(getDateASYYYYMMDD(hm1.get("fromDate").toString()));
		parameters.add(getDateASYYYYMMDD(hm1.get("toDate").toString()));
		
		if(hm1.get("firmId")==null||hm1.get("firmId").equals("-1"))
		{
			query=query.replaceAll("firmIdString3", "");
		}
		else
		{
			query=query.replaceAll("firmIdString3", " and tpr.firm_id=?");
			parameters.add(hm1.get("firmId"));
		}
		

		if(hm1.get("sbuId")==null || hm1.get("sbuId").equals("-1"))
		{
			query=query.replaceAll("sbuIdString3", "");
		}
		else
		{
			query=query.replaceAll("sbuIdString3", " and sbumast.sbu_id=?");
			parameters.add(hm1.get("sbuId"));
		}
		
		
		
		return getListOfLinkedHashHashMap(parameters, query, con);

	}
	
	
	public List<LinkedHashMap<String, Object>> getIncomeReport(HashMap<String, Object> hm1, Connection con)
			throws ClassNotFoundException, SQLException, ParseException {
		ArrayList<Object> parameters = new ArrayList<>();
		
		

		String query = "select\r\n"
				+ "	firm_name,\r\n"
				+ "	mc2.Client_name PartyName,\r\n"
				+ "	tjd.job_sheet_no job_sheet_no,\r\n"
				+ "	mc.Client_name Head,\r\n"
				+ "	tje.vendor_invoice_no ,\r\n"
				+ "	tje.journal_date TransactionDate,\r\n"
				+ "	tjd.amount,\r\n"
				+ "	tje.total_amount fullamount,\r\n"
				+ "	'JournalEntry' theType,\r\n"
				+ "	tje.updated_date ,\r\n"
				+ "	tje.updated_by\r\n"
				+ "from\r\n"
				+ "	trn_journal_entry tje,\r\n"
				+ "	trn_journal_details tjd,\r\n"
				+ "	mst_Client mc ,\r\n"
				+ "	mst_firm mf ,\r\n"
				+ "	mst_Client mc2,sbu_master sbumast \r\n"
				+ "where\r\n"
				+ "	journal_date between ? and ? and sbumast.activate_flag=1 and sbumast.sbu_id=mf.parent_sbu_id \r\n"
				+ "	firmIdString1 sbuIdString1 \r\n"
				+ "	and tje.journal_id = tjd.journal_id\r\n"
				+ "	and mc.Client_id = tje.debit\r\n"
				+ "	and mc.group_id = 10\r\n"
				+ "	and mf.firm_id = tje.firm_id\r\n"
				+ "	and mc2.Client_id = tje.credit\r\n"
				+ "	and tje.activate_flag = 1\r\n"
				+ "union all\r\n"
				+ "select\r\n"
				+ "	firm_name,\r\n"
				+ "	mc2.Client_name PartyName,\r\n"
				+ "	tjd.job_sheet_no job_sheet_no,\r\n"
				+ "	mc.Client_name Head,\r\n"
				+ "	tje.vendor_invoice_no ,\r\n"
				+ "	tje.journal_date TransactionDate,\r\n"
				+ "	tjd.amount ,\r\n"
				+ "	tje.total_amount fullamount,\r\n"
				+ "	'JournalEntry' theType,\r\n"
				+ "	tje.updated_date ,\r\n"
				+ "	tje.updated_by\r\n"
				+ "from\r\n"
				+ "	trn_journal_entry tje,\r\n"
				+ "	trn_journal_details tjd,\r\n"
				+ "	mst_Client mc ,\r\n"
				+ "	mst_firm mf ,\r\n"
				+ "	mst_Client mc2,sbu_master sbumast\r\n"
				+ "where\r\n"
				+ "	journal_date between ? and ? and sbumast.activate_flag=1 and sbumast.sbu_id=mf.parent_sbu_id \r\n"
				+ "	firmIdString2 sbuIdString2 \r\n"
				+ "	and tje.journal_id = tjd.journal_id\r\n"
				+ "	and mc.Client_id = tje.credit\r\n"
				+ "	and mc.group_id = 10\r\n"
				+ "	and mf.firm_id = tje.firm_id\r\n"
				+ "	and mc2.Client_id = tje.debit\r\n"
				+ "	and tje.activate_flag = 1\r\n"
				+ "union all\r\n"
				+ "select\r\n"
				+ "	firm_name,\r\n"
				+ "	bank_name PartyName,\r\n"
				+ "	tpd.job_sheet_no job_sheet_no,\r\n"
				+ "	cust.Client_name ,\r\n"
				+ "	tpd.remarks,\r\n"
				+ "	payment_date TransactionDate,\r\n"
				+ "	tpd.amount,\r\n"
				+ "	tpr.amount fullamount,\r\n"
				+ "	'Payment' theType,\r\n"
				+ "	tpr.updated_date,\r\n"
				+ "	tpr.updated_by\r\n"
				+ "from\r\n"
				+ "	trn_payment_register tpr,\r\n"
				+ "	trn_payment_details tpd ,\r\n"
				+ "	mst_bank bank,\r\n"
				+ "	mst_firm firm,\r\n"
				+ "	mst_Client cust,sbu_master sbumast\r\n"
				+ "where\r\n"
				+ "	payment_date between ? and ?\r\n"
				+ "	and firm.firm_id = tpr.firm_id\r\n"
				+ "	and tpr.activate_flag = 1\r\n"
				+ "	and tpd.payment_id = tpr.payment_id\r\n"
				+ "	and tpr.type = 'P'\r\n"
				+ "	and bank.bank_id = tpr.bank_id\r\n"
				+ "	firmIdString3 sbuIdString3 \r\n"
				+ "	and cust.Client_id = tpr.Client_id  and cust.group_id =10 and sbumast.activate_flag=1 and sbumast.sbu_id = firm.parent_sbu_id";
		
		parameters.add(getDateASYYYYMMDD(hm1.get("fromDate").toString()));
		parameters.add(getDateASYYYYMMDD(hm1.get("toDate").toString()));
		
		if(hm1.get("firmId")==null||hm1.get("firmId").equals("-1"))
		{
			query=query.replaceAll("firmIdString1", "");
		}
		else
		{
			query=query.replaceAll("firmIdString1", " and tje.firm_id=?");
			parameters.add(hm1.get("firmId"));
		}
		
		if(hm1.get("sbuId")==null||hm1.get("sbuId").equals("-1"))
		{
			query=query.replaceAll("sbuIdString1", "");
		}
		else
		{
			query=query.replaceAll("sbuIdString1", " and sbumast.sbu_id=?");
			parameters.add(hm1.get("sbuId"));
		}
		
		parameters.add(getDateASYYYYMMDD(hm1.get("fromDate").toString()));
		parameters.add(getDateASYYYYMMDD(hm1.get("toDate").toString()));
		
		if(hm1.get("firmId")==null ||hm1.get("firmId").equals("-1"))
		{
			query=query.replaceAll("firmIdString2", "");
		}
		else
		{
			query=query.replaceAll("firmIdString2", " and tje.firm_id=?");
			parameters.add(hm1.get("firmId"));
		}
		

		if(hm1.get("sbuId")==null ||hm1.get("sbuId").equals("-1"))
		{
			query=query.replaceAll("sbuIdString2", "");
		}
		else
		{
			query=query.replaceAll("sbuIdString2", " and sbumast.sbu_id=?");
			parameters.add(hm1.get("sbuId"));
		}
		
		
		parameters.add(getDateASYYYYMMDD(hm1.get("fromDate").toString()));
		parameters.add(getDateASYYYYMMDD(hm1.get("toDate").toString()));
		
		if(hm1.get("firmId")==null||hm1.get("firmId").equals("-1"))
		{
			query=query.replaceAll("firmIdString3", "");
		}
		else
		{
			query=query.replaceAll("firmIdString3", " and tpr.firm_id=?");
			parameters.add(hm1.get("firmId"));
		}
		

		if(hm1.get("sbuId")==null||hm1.get("sbuId").equals("-1"))
		{
			query=query.replaceAll("sbuIdString3", "");
		}
		else
		{
			query=query.replaceAll("sbuIdString3", " and sbumast.sbu_id=?");
			parameters.add(hm1.get("sbuId"));
		}
		
		
		
		return getListOfLinkedHashHashMap(parameters, query, con);

	}
	
	public List<LinkedHashMap<String, Object>> getIncomeReportClubbed(HashMap<String, Object> hm1, Connection con)
			throws ClassNotFoundException, SQLException, ParseException {
		ArrayList<Object> parameters = new ArrayList<>();
		
		

		String query = "select T.Head,sum(amount) theAmount,Client_id from (select\r\n"
				+ "	firm_name,\r\n"
				+ "	mc2.Client_name PartyName,\r\n"
				+ "	tjd.job_sheet_no job_sheet_no,\r\n"
				+ "	mc.Client_name Head,\r\n"
				+ "	tje.vendor_invoice_no ,\r\n"
				+ "	tje.journal_date TransactionDate,\r\n"
				+ "	tjd.amount,\r\n"
				+ "	tje.total_amount fullamount,\r\n"
				+ "	'JournalEntry' theType,\r\n"
				+ "	tje.updated_date ,\r\n"
				+ "	tje.updated_by,mc2.Client_id \r\n"
				+ "from\r\n"
				+ "	trn_journal_entry tje,\r\n"
				+ "	trn_journal_details tjd,\r\n"
				+ "	mst_Client mc ,\r\n"
				+ "	mst_firm mf ,\r\n"
				+ "	mst_Client mc2,sbu_master sbumast \r\n"
				+ "where\r\n"
				+ "	journal_date between ? and ? and sbumast.activate_flag=1 and sbumast.sbu_id=mf.parent_sbu_id \r\n"
				+ "	firmIdString1 sbuIdString1 \r\n"
				+ "	and tje.journal_id = tjd.journal_id\r\n"
				+ "	and mc.Client_id = tje.debit\r\n"
				+ "	and mc.group_id = 10\r\n"
				+ "	and mf.firm_id = tje.firm_id\r\n"
				+ "	and mc2.Client_id = tje.credit\r\n"
				+ "	and tje.activate_flag = 1\r\n"
				+ "union all\r\n"
				+ "select\r\n"
				+ "	firm_name,\r\n"
				+ "	mc2.Client_name PartyName,\r\n"
				+ "	tjd.job_sheet_no job_sheet_no,\r\n"
				+ "	mc.Client_name Head,\r\n"
				+ "	tje.vendor_invoice_no ,\r\n"
				+ "	tje.journal_date TransactionDate,\r\n"
				+ "	tjd.amount ,\r\n"
				+ "	tje.total_amount fullamount,\r\n"
				+ "	'JournalEntry' theType,\r\n"
				+ "	tje.updated_date ,\r\n"
				+ "	tje.updated_by,mc.Client_id \r\n"
				+ "from\r\n"
				+ "	trn_journal_entry tje,\r\n"
				+ "	trn_journal_details tjd,\r\n"
				+ "	mst_Client mc ,\r\n"
				+ "	mst_firm mf ,\r\n"
				+ "	mst_Client mc2,sbu_master sbumast\r\n"
				+ "where\r\n"
				+ "	journal_date between ? and ? and sbumast.activate_flag=1 and sbumast.sbu_id=mf.parent_sbu_id \r\n"
				+ "	firmIdString2 sbuIdString2 \r\n"
				+ "	and tje.journal_id = tjd.journal_id\r\n"
				+ "	and mc.Client_id = tje.credit\r\n"
				+ "	and mc.group_id = 10\r\n"
				+ "	and mf.firm_id = tje.firm_id\r\n"
				+ "	and mc2.Client_id = tje.debit\r\n"
				+ "	and tje.activate_flag = 1\r\n"
				+ "union all\r\n"
				+ "select\r\n"
				+ "	firm_name,\r\n"
				+ "	bank_name PartyName,\r\n"
				+ "	tpd.job_sheet_no job_sheet_no,\r\n"
				+ "	cust.Client_name ,\r\n"
				+ "	tpd.remarks,\r\n"
				+ "	payment_date TransactionDate,\r\n"
				+ "	tpd.amount,\r\n"
				+ "	tpr.amount fullamount,\r\n"
				+ "	'Payment' theType,\r\n"
				+ "	tpr.updated_date,\r\n"
				+ "	tpr.updated_by,cust.Client_id \r\n"
				+ "from\r\n"
				+ "	trn_payment_register tpr,\r\n"
				+ "	trn_payment_details tpd ,\r\n"
				+ "	mst_bank bank,\r\n"
				+ "	mst_firm firm,\r\n"
				+ "	mst_Client cust,sbu_master sbumast\r\n"
				+ "where\r\n"
				+ "	payment_date between ? and ?\r\n"
				+ "	and firm.firm_id = tpr.firm_id\r\n"
				+ "	and tpr.activate_flag = 1\r\n"
				+ "	and tpd.payment_id = tpr.payment_id\r\n"
				+ "	and tpr.type = 'P'\r\n"
				+ "	and bank.bank_id = tpr.bank_id\r\n"
				+ "	firmIdString3 sbuIdString3 \r\n"
				+ "	and cust.Client_id = tpr.Client_id  and cust.group_id =10 and sbumast.activate_flag=1 and sbumast.sbu_id = firm.parent_sbu_id ) as T group by T.Head ";
		
		parameters.add(getDateASYYYYMMDD(hm1.get("fromDate").toString()));
		parameters.add(getDateASYYYYMMDD(hm1.get("toDate").toString()));
		
		if(hm1.get("firmId")==null||hm1.get("firmId").equals("-1"))
		{
			query=query.replaceAll("firmIdString1", "");
		}
		else
		{
			query=query.replaceAll("firmIdString1", " and tje.firm_id=?");
			parameters.add(hm1.get("firmId"));
		}
		
		if(hm1.get("sbuId")==null||hm1.get("sbuId").equals("-1"))
		{
			query=query.replaceAll("sbuIdString1", "");
		}
		else
		{
			query=query.replaceAll("sbuIdString1", " and sbumast.sbu_id=?");
			parameters.add(hm1.get("sbuId"));
		}
		
		parameters.add(getDateASYYYYMMDD(hm1.get("fromDate").toString()));
		parameters.add(getDateASYYYYMMDD(hm1.get("toDate").toString()));
		
		if(hm1.get("firmId")==null ||hm1.get("firmId").equals("-1"))
		{
			query=query.replaceAll("firmIdString2", "");
		}
		else
		{
			query=query.replaceAll("firmIdString2", " and tje.firm_id=?");
			parameters.add(hm1.get("firmId"));
		}
		

		if(hm1.get("sbuId")==null ||hm1.get("sbuId").equals("-1"))
		{
			query=query.replaceAll("sbuIdString2", "");
		}
		else
		{
			query=query.replaceAll("sbuIdString2", " and sbumast.sbu_id=?");
			parameters.add(hm1.get("sbuId"));
		}
		
		
		parameters.add(getDateASYYYYMMDD(hm1.get("fromDate").toString()));
		parameters.add(getDateASYYYYMMDD(hm1.get("toDate").toString()));
		
		if(hm1.get("firmId")==null||hm1.get("firmId").equals("-1"))
		{
			query=query.replaceAll("firmIdString3", "");
		}
		else
		{
			query=query.replaceAll("firmIdString3", " and tpr.firm_id=?");
			parameters.add(hm1.get("firmId"));
		}
		

		if(hm1.get("sbuId")==null||hm1.get("sbuId").equals("-1"))
		{
			query=query.replaceAll("sbuIdString3", "");
		}
		else
		{
			query=query.replaceAll("sbuIdString3", " and sbumast.sbu_id=?");
			parameters.add(hm1.get("sbuId"));
		}
		
		
		
		return getListOfLinkedHashHashMap(parameters, query, con);

	}
	
	
	

	public List<LinkedHashMap<String, Object>> getProfitAndLossReport(HashMap<String, Object> hm1, Connection con)
			throws ClassNotFoundException, SQLException, ParseException {
		ArrayList<Object> parameters = new ArrayList<>();
		
		

		String query = "select *,Client_name as Head,theDate as TransactionDate  \r\n"
				+ "from \r\n"
				+ "(\r\n"
				+ "select firm_id,Client_name PartyName,tjd.job_sheet_no,debit,vendor_invoice_no,journal_date theDate,tjd.amount,tje.total_amount fullamount,'JournalEntry' theType,tje.updated_date,tje.updated_by from \r\n"
				+ "trn_journal_entry tje,\r\n"
				+ "trn_journal_details tjd ,mst_Client cust \r\n"
				+ "where journal_date between ? and ? and tje.activate_flag=1 and tjd.journal_id =tje.journal_id   and cust.Client_id=credit firmIdString \r\n"
				+ "union \r\n"
				+ "select firm_id,bank_name PartyName,tpd.job_sheet_no,Client_id,tpd.remarks,payment_date,tpd.amount,tpr.amount fullamount,'Payment' theType,tpr.updated_date,tpr.updated_by from \r\n"
				+ "trn_payment_register tpr,trn_payment_details tpd ,mst_bank bank where \r\n"
				+ "payment_date between ? and ?  and tpr.activate_flag=1 and tpd.payment_id =tpr.payment_id and tpr.type='P'  and bank.bank_id=tpr.bank_id firmIdString \r\n"
				+ ") as T left outer join mst_Client cust2 on  cust2.Client_id =T.debit and cust2.group_id=6 \r\n"
				+ ",mst_firm firm \r\n"
				+ ", tbl_user_mst user, sbu_master sbumast\r\n"
				+ "where T.firm_id=firm.firm_id and sbumast.activate_flag=1 and sbumast.sbu_id=firm.parent_sbu_id sbuIdString \r\n"
				+ "and user.user_id=T.updated_by \r\n" // added cust2.group_id for indirect expense
				+ "order by T.theDate,T.updated_date  ";
		
		parameters.add(getDateASYYYYMMDD(hm1.get("fromDate").toString()));
		parameters.add(getDateASYYYYMMDD(hm1.get("toDate").toString()));
		
		if(hm1.get("firmId").equals("-1"))
		{
			query=query.replaceAll("firmIdString", "");
		}
		else
		{
			query=query.replaceAll("firmIdString", " and firm_id=?");
			parameters.add(hm1.get("firmId"));
		}
		
		
		
		parameters.add(getDateASYYYYMMDD(hm1.get("fromDate").toString()));
		parameters.add(getDateASYYYYMMDD(hm1.get("toDate").toString()));
		if(hm1.get("firmId").equals("-1"))
		{
			query=query.replaceAll("firmIdString", "");
		}
		else
		{
			query=query.replaceAll("firmIdString", " and firm_id=?");
			parameters.add(hm1.get("firmId"));
		}
		
		
		if(hm1.get("sbuId").equals("-1"))
		{
			query=query.replaceAll("sbuIdString", "");
		}
		else
		{
			query=query.replaceAll("sbuIdString", " and sbu_id=?");
			parameters.add(hm1.get("sbuId"));
		}
		
		return getListOfLinkedHashHashMap(parameters, query, con);

	}
	
	
	
	public List<LinkedHashMap<String, Object>> getFirmLedger(HashMap<String, Object> hm1, Connection con)
			throws ClassNotFoundException, SQLException, ParseException {
		ArrayList<Object> parameters = new ArrayList<>();
		
		

		String query = " ";
		
		parameters.add(getDateASYYYYMMDD(hm1.get("fromDate").toString()));
		parameters.add(getDateASYYYYMMDD(hm1.get("toDate").toString()));
		
		
		
		parameters.add(getDateASYYYYMMDD(hm1.get("fromDate").toString()));
		parameters.add(getDateASYYYYMMDD(hm1.get("toDate").toString()));
		
		
		
		return getListOfLinkedHashHashMap(parameters, query, con);

	}
	
	
	
	
	public BigDecimal getOpeningBalanceForThisBank(String fromDate,String BankId,String firmId, Connection con)
			throws ClassNotFoundException, SQLException, ParseException {
		ArrayList<Object> parameters = new ArrayList<>();
				
		

		String query = "select\r\n"
				+ "	COALESCE((select opening_balance from mst_bank mb  where bank_id =?),\r\n"
				+ "	0)+ COALESCE (sum(case when type = 'R' then amount else amount*-1 end),\r\n"
				+ "	0) as RequiredOpeningBalance	\r\n"
				+ "from trn_payment_register tpr \r\n"
				+ "	where \r\n"
				+ "tpr.payment_date <?\r\n"
				+ "	and tpr.activate_flag = 1\r\n"
				+ "	and tpr.bank_id = ?";
		
		
			
			parameters.add(BankId);
			parameters.add(getDateASYYYYMMDD(fromDate));
			parameters.add(BankId);
		
		
		
		
		return new BigDecimal(getMap(parameters, query, con).get("RequiredOpeningBalance").toString());

	}
	
	

	public List<LinkedHashMap<String, Object>> getStockRegister(HashMap<String, Object> hm1, Connection con)
			throws ClassNotFoundException, SQLException, ParseException {

		ArrayList<Object> parameters = new ArrayList<>();
		
		parameters.add(getDateASYYYYMMDD(hm1.get("fromDate").toString()));
		parameters.add(getDateASYYYYMMDD(hm1.get("toDate").toString()));
		
		parameters.add(getDateASYYYYMMDD(hm1.get("fromDate").toString()));
		parameters.add(getDateASYYYYMMDD(hm1.get("toDate").toString()));
		

		String query = "select\r\n"
				+ "	firm_name,\r\n"
				+ "	ware_house_name,\r\n"
				+ "	item_name,\r\n"
				+ "	product_code,`size`,color,	\r\n"
				+ "	T.qty,\r\n"
				+ "	T.rate,\r\n"
				+ "	typeOfTransaction,date_format(invoicedate,'%d/%m/%Y') invoicedate,cust.Client_name,T.firm_id,invoiceId \r\n"
				+ "from\r\n"
				+ "	(\r\n"
				+ "	select\r\n"
				+ "		tpir.Client_id Client_id,tpid.item_id, tpir.invoice_date invoicedate, tpir.updated_date, tpir.updated_by, tpid.qty, tpid.rate, 'Purchase' typeOfTransaction, tpir.firm_id , tpid.ware_house_id,tpir.invoice_id invoiceId\r\n"
				+ "	from\r\n"
				+ "		trn_purchase_invoice_register tpir , trn_purchase_invoice_details tpid\r\n"
				+ "	where\r\n"
				+ "		tpir.invoice_id = tpid.invoice_id\r\n"
				+ "		and tpir.invoice_date between ? and ? \r\n"
				+ "		and tpir.activate_flag = 1\r\n"
				+ "union all\r\n"
				+ "	select\r\n"
				+ "		tpir.Client_id Client_id,tpid.item_id, tpir.invoice_date invoicedate, tpir.updated_date, tpir.updated_by, tpid.qty, tpid.rate, 'Sales' typeOfTransaction, tpir.firm_id , tpid.ware_house_id,tpir.invoice_id invoiceId\r\n"
				+ "	from\r\n"
				+ "		trn_sales_invoice_register tpir , trn_sales_invoice_details tpid\r\n"
				+ "	where\r\n"
				+ "		tpir.invoice_id = tpid.invoice_id\r\n"
				+ "		and tpir.invoice_date between ? and ? \r\n"
				+ "		and tpir.activate_flag = 1 ) as T,\r\n"
				+ "	mst_items item,\r\n"
				+ "	mst_firm firm,\r\n"
				+ "	mst_ware_house ware,mst_Client cust\r\n"
				+ "where\r\n"
				+ "	T.item_id = item.item_id\r\n"
				+ "	and T.firm_id = firm.firm_id\r\n"
				+ "	and T.ware_house_id = ware.ware_house_id and T.Client_id=cust.Client_id\r\n"
				+ "	\r\n"
				+ "";
		
		if (hm1.get("firmId") != null && !hm1.get("firmId").equals("") && !hm1.get("firmId").toString().equals("-1")) {
			parameters.add(hm1.get("firmId").toString());
			query += " and firm.firm_id=?";
		}
		
		if (hm1.get("warehouseid") != null && !hm1.get("warehouseid").equals("") && !hm1.get("warehouseid").toString().equals("-1")) {
			parameters.add(hm1.get("warehouseid").toString());
			query += " and ware.ware_house_id=?";
		}
		
	
		
		if ( hm1.get("categoryId") != null && !hm1.get("categoryId").equals("") && !hm1.get("categoryId").toString().equals("-1")) {
			parameters.add(hm1.get("categoryId").toString());
			query += " and item.parent_category_id=?";
		}
		
		
		if (hm1.get("searchString") != null && !hm1.get("searchString").equals("") && !hm1.get("searchString").equals("-1")) {
			query+=" and (item.product_code=? or item.item_name like ? )";
			parameters.add(hm1.get("searchString"));
			parameters.add("%"+hm1.get("searchString")+"%");
			
		}
		
		if (hm1.get("itemId") != null && !hm1.get("itemId").equals("") && !hm1.get("itemId").toString().equals("-1")) {
			parameters.add(hm1.get("itemId").toString());
			query += " and T.item_id=?";
		}
		

		//query += " order by stockreg.updated_date desc";

		return getListOfLinkedHashHashMap(parameters, query, con);

	}

	public List<LinkedHashMap<String, Object>> getInventoryCountingListForThisfirm(HashMap<String, Object> hm1,
			Connection con) throws ClassNotFoundException, SQLException, ParseException {

		ArrayList<Object> parameters = new ArrayList<>();
		String query = "select firm_name,item_name,qty,type,invoice_id,name,stockreg.updated_date,date_format(stockreg.updated_date,'%d/%m/%Y %H:%i') as FormattedUpdatedDate from \r\n"
				+ "trn_stock_register stockreg,\r\n" + "mst_items  item ,\r\n" + "mst_firm firm,\r\n"
				+ "tbl_user_mst user\r\n" + "					where \r\n" + "					  \r\n"
				+ "					item.item_id=stockreg.item_id and user.user_id=stockreg.updated_by\r\n"
				+ "					and stockreg.firm_id=firm.firm_id and stockreg.type=?";
		parameters.add(hm1.get("stockType"));
		if (!hm1.get("firmId").toString().equals("-1")) {
			parameters.add(hm1.get("firmId").toString());
			query += " and stockreg.firm_id=?";
		}

		query += " order by stockreg.updated_date desc";

		return getListOfLinkedHashHashMap(parameters, query, con);

	}
	
	public List<LinkedHashMap<String, Object>> getDailyDebitRegister(HashMap<String, Object> hm, Connection con)
			throws ParseException, ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(getDateASYYYYMMDD(hm.get("fromDate").toString()));
		parameters.add(getDateASYYYYMMDD(hm.get("toDate").toString()));
		parameters.add(hm.get("app_id"));
		
		
		String query="select *,date_format(payment_date,'%d/%m/%Y') as FormattedPaymentDate,payment_for,name,date_format(inv.updated_date,'%d/%m/%Y %H:%i') as FormattedUpdatedDate  \r\n"
				+ "				 from trn_payment_register inv left outer join mst_Client cust on inv.Client_id=cust.Client_id\r\n"
				+ "				  inner join tbl_user_mst user on user.user_id=inv.updated_by\r\n"
				+ "				  where date(payment_date) between ? and ?  and inv.activate_flag=1 and inv.app_id=? and payment_for in ('Debit Entry') ";
		
		if(hm.get("paymentMode")!=null && !hm.get("paymentMode").equals(""))
		{
			query+=" and payment_mode=?";
			parameters.add(hm.get("paymentMode"));
		}
		
		if(hm.get("firmId")!=null && !hm.get("firmId").equals(""))
		{
			query+="and inv.firm_id = ?";
			parameters.add(hm.get("firmId"));
		}
		
		if(hm.get("paymentFor")!=null && !hm.get("paymentFor").equals(""))
		{
			query+=" and inv.payment_for = ?";
			parameters.add(hm.get("paymentFor"));
		}
		
		
		
		
		
		query+="order by inv.updated_date desc;";
				
		return getListOfLinkedHashHashMap(parameters,query,con);

	}

	public List<LinkedHashMap<String, Object>> getDailyPaymentRegister(HashMap<String, Object> hm, Connection con)
			throws ParseException, ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(getDateASYYYYMMDD(hm.get("fromDate").toString()));
		parameters.add(getDateASYYYYMMDD(hm.get("toDate").toString()));
		parameters.add(hm.get("app_id"));
		
		
		String query="select *,date_format(payment_date,'%d/%m/%Y') as FormattedPaymentDate,payment_for,name,date_format(inv.updated_date,'%d/%m/%Y %H:%i') as FormattedUpdatedDate \r\n"
				+ "				 from trn_payment_register inv left outer join mst_Client cust on inv.Client_id=cust.Client_id\r\n"
				+ "				  inner join tbl_user_mst user on user.user_id=inv.updated_by\r\n"
				+ "				  where date(payment_date) between ? and ?  and inv.activate_flag=1 and inv.app_id=? and payment_for not in ('Debit Entry') ";
		
		if(hm.get("paymentMode")!=null && !hm.get("paymentMode").equals(""))
		{
			query+=" and payment_mode=?";
			parameters.add(hm.get("paymentMode"));
		}
		
		if(hm.get("firmId")!=null && !hm.get("firmId").equals(""))
		{
			query+="and inv.firm_id = ?";
			parameters.add(hm.get("firmId"));
		}
		
		if(hm.get("paymentFor")!=null && !hm.get("paymentFor").equals(""))
		{
			query+=" and inv.payment_for = ?";
			parameters.add(hm.get("paymentFor"));
		}
		
		
		
		
		
		query+="order by inv.updated_date desc;";
				
		return getListOfLinkedHashHashMap(parameters,query,con);

	}

	public List<LinkedHashMap<String, Object>> getPendingClientCollection(HashMap<String, Object> hm, Connection con)
			throws ClassNotFoundException, SQLException, ParseException {
		ArrayList<Object> parameters = new ArrayList<>();
		
		parameters.add(getDateASYYYYMMDD(hm.get("fromDate").toString()));
		parameters.add(getDateASYYYYMMDD(hm.get("toDate").toString()));
		parameters.add(hm.get("app_id"));
		
		parameters.add(getDateASYYYYMMDD(hm.get("fromDate").toString()));
		parameters.add(getDateASYYYYMMDD(hm.get("toDate").toString()));		
		parameters.add(hm.get("app_id"));

		return getListOfLinkedHashHashMap(parameters,
				"select sum(amount) PendingAmount,T.Client_id,T.Client_name,T.mobile_number ,T.Client_reference,T.alternate_mobile_no,T.city from \r\n" + 
				"(\r\n" + 
				"select mc.Client_id,mc.Client_name,mc.mobile_number ,mc.alternate_mobile_no,mc.Client_reference, mc.city,(tir.total_amount) amount from mst_Client mc\r\n" + 
				"left outer join trn_invoice_register tir on tir.activate_flag=1 and mc.Client_id =tir.Client_id and tir.invoice_date  between ? and ?  and mc.app_id=?\r\n" + 
				"union all\r\n" + 
				"select mc.Client_id,mc.Client_name,mc.mobile_number ,mc.alternate_mobile_no,mc.Client_reference ,mc.city,(tpr.amount*-1)  from mst_Client mc\r\n" + 
				"left outer join trn_payment_register tpr on mc.Client_id =tpr.Client_id and tpr.payment_date  between ? and ? and mc.app_id=? and tpr.activate_flag =1 \r\n" + 
				")\r\n" + 
				" as T group by T.Client_id having PendingAmount>0 order by T.Client_name",
				con);

	}

	public List<LinkedHashMap<String, Object>> getEmployeeWiseReport(HashMap<String, Object> hm, Connection con)
			throws ParseException, ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(getDateASYYYYMMDD(hm.get("fromDate").toString()));
		parameters.add(getDateASYYYYMMDD(hm.get("toDate").toString()));
		parameters.add(hm.get("firmId").toString());

		return getListOfLinkedHashHashMap(parameters,
				"select username EmployeeName, c.firm_name firmName, invoice_date InvoiceDate, \r\n"
						+ " sum(total_amount) TotalAmount,date_format(a.updated_date,'%d/%m/%Y %H:%i') as FormattedInvoiceDate from trn_invoice_register a, tbl_user_mst b, mst_firm c \r\n"
						+ " where a.updated_by = b.user_id and a.firm_id = c.firm_id and a.invoice_date between ? and ? \r\n"
						+ " and a.firm_id = ? and a.activate_flag=1 group by a.invoice_date,a.updated_by;",
				con);
	}

	public List<LinkedHashMap<String, Object>> getCategoryWiseReport(HashMap<String, Object> hm, Connection con)
			throws ParseException, ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(getDateASYYYYMMDD(hm.get("fromDate").toString()));
		parameters.add(getDateASYYYYMMDD(hm.get("toDate").toString()));
		parameters.add((hm.get("app_id").toString()));

		return getListOfLinkedHashHashMap(parameters,
				"select cat.category_name CategoryName, sto.firm_name firmName,\r\n "
						+ " sum(total_amount) TotalAmount from trn_invoice_register tir, trn_invoice_details tid,\r\n"
						+ " mst_items itm, mst_category cat, mst_firm sto where tir.invoice_id=tid.invoice_id and tid.item_id = itm.item_id\r\n"
						+ " and itm.parent_category_id = cat.category_id and tir.firm_id = sto.firm_id and tir.invoice_date between ? and ? \r\n"
						+ " and tir.activate_flag=1 and tir.app_id=? group by tir.firm_id,itm.parent_category_id;",
				con);
	}

	public long getSequenceForItem(Connection conWithF) throws NumberFormatException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		long product_code = Long.parseLong(
				getMap(parameters, "select current_seq_no from seq_master where sequence_name='Item' for update ",
						conWithF).get("current_seq_no").toString());
		insertUpdateDuablDB("update seq_master set  current_seq_no=current_seq_no+1 where sequence_name='Item' ",
				parameters, conWithF);
		return product_code + 1;
	}

	public boolean checkIfProductCodeAlreadyExist(HashMap<String, Object> hm, Connection conWithF)
			throws NumberFormatException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		String query = "select count(1) cnt from mst_items where product_code=? and size=? and activate_flag=1 and app_id=?";
		parameters.add(hm.get("product_code"));
		parameters.add(hm.get("size"));
		parameters.add(hm.get("app_id"));
		if (!hm.get("hdnItemId").equals("")) {
			query += " and item_id!=?";
			parameters.add(hm.get("hdnItemId"));
		}
		int count = Integer.parseInt(getMap(parameters, query, conWithF).get("cnt").toString());
		return count != 0;
	}

	public String checkifStockAlreadyExist(long firmId, long itemId,long wareHouseId, Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(firmId);
		parameters.add(itemId);
		parameters.add(wareHouseId);
		String stockId = getMap(parameters,
				"select stock_id from stock_status where firm_id=? and item_id=? and ware_house_id=? and activate_flag=1", con)
						.get("stock_id");
		stockId = stockId == null ? "0" : stockId;
		return stockId;
	}

	public void addfirmItemMapping(HashMap<String, Object> hm, Connection conWithF) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("hdnItemId"));
		insertUpdateDuablDB("delete from firm_item_mpg where item_id=?", parameters, conWithF);

		List<String> availablefirmIds = (List<String>) hm.get("availablefirmIds");
		for (String s : availablefirmIds) {
			parameters = new ArrayList<>();
			parameters.add(s);
			parameters.add(hm.get("hdnItemId"));
			parameters.add(hm.get("app_id"));

			insertUpdateDuablDB("insert into firm_item_mpg values (default,?,?,sysdate(),1,?)", parameters, conWithF);
		}

	}

	public List<String> getListOffirmsForThisItem(String itemId, Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(itemId);
		return getListOfString(parameters, "select firm_id from firm_item_mpg where item_id=?", con);

	}
	
	public List<LinkedHashMap<String, Object>> getTableStatus(int firmId, Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(firmId);
		return getListOfLinkedHashHashMap(parameters, "select table1.table_id,table_no,tor.*,dtls.*,item.*,\r\n"
				+ "	concat('', SEC_TO_TIME(TIMESTAMPDIFF(second, start_time, sysdate() ))) activeSince,\r\n"
				+ "	sum(qty) totalQty,\r\n"
				+ "	sum(qty*price) totalAmount from  mst_tables table1 "
				+ " left outer join trn_order_register tor on tor.order_id =table1.order_id left outer join trn_order_details dtls on dtls.order_id=tor.order_id left outer join mst_items item on item.item_id=dtls.item_id "
				+ "where firm_id =? group by table1.table_id", con);

	}
	
	

	public LinkedHashMap<String, Object> getInvoiceDetails(String invoiceId, Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(invoiceId);
		LinkedHashMap<String, Object> itemDetailsMap = new LinkedHashMap<>();
		itemDetailsMap = getMapReturnObject(parameters, "select \r\n" + "*,\r\n"
				+ "case when cust.Client_id is null then \"\" else Client_name end  as ClientName,\r\n"
				+ "date_format(invoice_date,'%d/%m/%Y') theInvoiceDate,\r\n" + "sum(qty) totalQuantities,\r\n"
				+ "paym.amount as paid_amount\r\n" + " from\r\n"
				+ " trn_invoice_register invoice inner join mst_firm firm1 on firm1.firm_id=invoice.firm_id left outer join  mst_Client cust on cust.Client_id=invoice.Client_id and invoice.activate_flag=1 \r\n"
				+ " inner join  trn_invoice_details dtls on  dtls.invoice_id=invoice.invoice_id left outer join  trn_payment_register paym on paym.ref_id=invoice.invoice_id and paym.payment_for='Invoice'\r\n"
				+ "where invoice.invoice_id=? ", con);

		parameters = new ArrayList<>();
		parameters.add(invoiceId);

		
		itemDetailsMap.put("listOfItems",
				getListOfLinkedHashHashMap(parameters, "select item.*,dtls.*,cat.*,return1.*,"
						+ "case when concat(attachment_id, file_name) is null then 'dummyImage.jpg' else concat(attachment_id, file_name) end as ImagePath,"
						+ " sum(qty_to_return) ReturnedQty\r\n"
						+ "from mst_items item  inner join trn_invoice_details dtls on item.item_id=dtls.item_id "
						+ "left outer join tbl_attachment_mst tam on tam.file_id=item.item_id and tam.type='Image' "
						+ "inner join mst_category cat on cat.category_id=item.parent_category_id \r\n"
						+ "	left outer join trn_return_register return1 on return1.details_id=dtls.details_id\r\n"
						+ "where\r\n" + "invoice_id = ? group by dtls.details_id  order by dtls.details_id ", con));
		return itemDetailsMap;

	}
	
	public LinkedHashMap<String, Object> getJournalDetalilsById(String invoiceId, Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(invoiceId);
		LinkedHashMap<String, Object> itemDetailsMap = new LinkedHashMap<>();
		itemDetailsMap = getMapReturnObject(parameters, "select *,mc1.Client_name debitName,mc2.Client_name creditName from \r\n"
				+ "trn_journal_entry tje ,\r\n"
				+ "mst_Client mc1 ,\r\n"
				+ "mst_Client mc2\r\n"
				+ "where tje.debit =mc1.Client_id \r\n"
				+ "and tje.credit =mc2.Client_id \r\n"
				+ "and tje.journal_id =?\r\n"
				+ "", con);

		parameters = new ArrayList<>();
		parameters.add(invoiceId);

		
		itemDetailsMap.put("listOfItems",
				getListOfLinkedHashHashMap(parameters, "select * from trn_journal_details where journal_id=? ", con));
		return itemDetailsMap;

	}
	
	
	public LinkedHashMap<String, Object> getChallanInDetails(String invoiceId, Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(invoiceId);
		LinkedHashMap<String, Object> itemDetailsMap = new LinkedHashMap<>();
		itemDetailsMap = getMapReturnObject(parameters, "select *,date_format(challan.invoice_date, '%d-%b-%Y ') as "
				+ " FormattedChallanDate,date_format(challan.invoice_date, '%d-%b-%Y ') as invoice_date,cust.address as Client_address,"
				+ "cust.city as Client_city,cust.pincode as Client_pincode,cust.gst_no as Client_gst,cust.state as Client_state,cust.email as Client_email,"
				+ "firm.city as firm_city,firm.pincode as firm_pincode,firm.gst_no as firm_gst,firm.firm_email as firm_email "
				+ " from trn_challan_in challan,mst_firm firm,mst_Client cust where challan_id=? and challan.firm_id=firm.firm_id and cust.Client_id=challan.Client_id ", con);

		parameters = new ArrayList<>();
		parameters.add(invoiceId);

		
		itemDetailsMap.put("listOfItems",
				getListOfLinkedHashHashMap(parameters, "select *,round(qty) formattedQty from trn_challan_in challanin, trn_challan_in_details details,mst_items item,mst_ware_house ware where details.item_id=item.item_id and challanin.challan_id=details.challan_id and challanin.challan_id=?  and ware.ware_house_id=details.ware_house_id", con));
		return itemDetailsMap;

	}
	
	
	public LinkedHashMap<String, Object> getPurchaseInvoiceDetails(String invoiceId, Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(invoiceId);
		LinkedHashMap<String, Object> itemDetailsMap = new LinkedHashMap<>();
		itemDetailsMap = getMapReturnObject(parameters, "select\r\n"
				+ "	*,\r\n"
				+ "	date_format(tpir.invoice_date, '%d-%b-%Y ') as FormattedChallanDate,\r\n"
				+ "	cust.address as Client_address,\r\n"
				+ "	cust.city as Client_city,\r\n"
				+ "	cust.pincode as Client_pincode,\r\n"
				+ "	cust.gst_no as Client_gst,\r\n"
				+ "	cust.state as Client_state,\r\n"
				+ "	cust.email as Client_email,cust.address_line_2 Client_address2,\r\n"
				+ "	firm.city as firm_city,\r\n"
				+ "	firm.pincode as firm_pincode,\r\n"
				+ "	firm.gst_no as firm_gst,\r\n"
				+ "	firm.firm_email as firm_email\r\n"
				+ "from\r\n"
				+ "	trn_purchase_invoice_register tpir ,\r\n"
				+ "	mst_firm firm,\r\n"
				+ "	mst_Client cust\r\n"
				+ "where\r\n"
				+ "	tpir.invoice_id =?\r\n"
				+ "	and tpir.firm_id = firm.firm_id\r\n"
				+ "	and cust.Client_id = tpir.Client_id ", con);

		parameters = new ArrayList<>();
		parameters.add(invoiceId);

		
		itemDetailsMap.put("listOfItems",
				getListOfLinkedHashHashMap(parameters, "select\r\n"
						+ "	*,\r\n"
						+ "	round(qty) formattedQty\r\n"
						+ "from\r\n"
						+ "	trn_purchase_invoice_register tpir ,\r\n"
						+ "	trn_purchase_invoice_details tpid ,\r\n"
						+ "	mst_items item,\r\n"
						+ "	mst_ware_house ware\r\n"
						+ "where\r\n"
						+ "	tpid.item_id = item.item_id\r\n"
						+ "	and tpir.invoice_id = tpid.invoice_id \r\n"
						+ "	and tpir.invoice_id =?\r\n"
						+ "	and ware.ware_house_id = tpid.ware_house_id", con));
		return itemDetailsMap;

	}
	
	public LinkedHashMap<String, Object> getSalesInvoiceDetails(String invoiceId, Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(invoiceId);
		LinkedHashMap<String, Object> itemDetailsMap = new LinkedHashMap<>();
		itemDetailsMap = getMapReturnObject(parameters, "select\r\n"
				+ "	*,\r\n"
				+ "	date_format(tsir.invoice_date, '%d-%b-%Y ') as FormattedChallanDate,\r\n"
				+ "	cust.address as Client_address,\r\n"
				+ "	cust.city as Client_city,\r\n"
				+ "	cust.pincode as Client_pincode,\r\n"
				+ "	cust.gst_no as Client_gst,\r\n"
				+ "	cust.state as Client_state,\r\n"
				+ "	cust.email as Client_email,cust.address_line_2 Client_address2,\r\n"
				+ "	firm.city as firm_city,\r\n"
				+ "	firm.pincode as firm_pincode,\r\n"
				+ "	firm.gst_no as firm_gst,\r\n"
				+ "	firm.firm_email as firm_email\r\n"
				+ "from\r\n"
				+ "	trn_sales_invoice_register tsir,\r\n"
				+ "	mst_firm firm,\r\n"
				+ "	mst_Client cust\r\n"
				+ "where\r\n"
				+ "	tsir.invoice_id =?\r\n"
				+ "	and tsir.firm_id = firm.firm_id\r\n"
				+ "	and cust.Client_id = tsir.Client_id;", con);

		parameters = new ArrayList<>();
		parameters.add(invoiceId);

		
		itemDetailsMap.put("listOfItems",
				getListOfLinkedHashHashMap(parameters, "select\r\n"
						+ "	*,\r\n"
						+ "	round(qty) formattedQty\r\n"
						+ "from\r\n"
						+ "	trn_sales_invoice_register tpir ,\r\n"
						+ "	trn_sales_invoice_details tpid ,\r\n"
						+ "	mst_items item,\r\n"
						+ "	mst_ware_house ware\r\n"
						+ "where\r\n"
						+ "	tpid.item_id = item.item_id\r\n"
						+ "	and tpir.invoice_id = tpid.invoice_id \r\n"
						+ "	and tpir.invoice_id =?\r\n"
						+ "	and ware.ware_house_id = tpid.ware_house_id", con));
		return itemDetailsMap;

	}
	
	
	
	
	public LinkedHashMap<String, Object> getChallanOutDetails(String invoiceId, Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(invoiceId);
		LinkedHashMap<String, Object> itemDetailsMap = new LinkedHashMap<>();
		itemDetailsMap = getMapReturnObject(parameters, "select *,date_format(challan.invoice_date, '%d-%b-%Y ') as "
				+ " FormattedChallanDate, "
				+ "cust.city as Client_city,cust.pincode as Client_pincode,cust.gst_no as Client_gst,cust.state as Client_state,cust.email as Client_email,cust.address as Client_address,"
				+ "firm.city as firm_city,firm.pincode as firm_pincode,firm.gst_no as firm_gst,firm.firm_email as firm_email "
				+ "from trn_challan_out challan,mst_firm firm,mst_Client cust where challan_id=? and challan.firm_id=firm.firm_id and cust.Client_id=challan.Client_id ", con);

		parameters = new ArrayList<>();
		parameters.add(invoiceId);

		
		itemDetailsMap.put("listOfItems",
				getListOfLinkedHashHashMap(parameters, "select *,round(qty) formattedQty from trn_challan_out challanin, trn_challan_out_details details,mst_items item,mst_ware_house ware where details.item_id=item.item_id and challanin.challan_id=details.challan_id and challanin.challan_id=? and ware.ware_house_id=details.ware_house_id ", con));
		return itemDetailsMap;

	}
	
	
	
	
	public LinkedHashMap<String, Object> getInvoiceDetailsForTable(String tableId, Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(tableId);
		LinkedHashMap<String, Object> itemDetailsMap = new LinkedHashMap<>();
		itemDetailsMap.put("invoice_id","0");
		itemDetailsMap.put("theInvoiceDate",getDateFromDB(con));
		itemDetailsMap.put("listOfItems",
				getListOfLinkedHashHashMap(parameters, "select\r\n"
						+ "	mt.table_no,item_name,mi.item_id,price as rate,price as custom_rate,qty\r\n"
						+ "from\r\n"
						+ "	mst_tables mt,trn_order_details tod,mst_items mi \r\n"
						+ "where\r\n"
						+ "	table_id = ? and tod.order_id =mt.order_id and mi.item_id =tod.item_id ", con));
		return itemDetailsMap;

	}
	
	public LinkedHashMap<String, Object> getInvoiceDetailsForBooking(String bookingId, Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(bookingId);
		LinkedHashMap<String, Object> itemDetailsMap = new LinkedHashMap<>();
		itemDetailsMap.put("invoice_id","0");
		itemDetailsMap.put("theInvoiceDate",getDateFromDB(con));
		itemDetailsMap.put("listOfItems",
				getListOfLinkedHashHashMap(parameters, "select\r\n"
						+ "*,tbr.booking_id,item_name,mi.item_id,price as rate,price as custom_rate,qty \r\n"
						+ "from\r\n"
						+ "	trn_booking_register tbr,booking_item_mpg bim,mst_items mi,mst_Client cust \r\n"
						+ "where\r\n"
						+ "	tbr.booking_id=? and bim.booking_id =tbr.booking_id and mi.item_id =bim.item_id and cust.Client_id=tbr.Client_id", con));
		return itemDetailsMap;

	}
	
	public LinkedHashMap<String, Object> getInvoiceDetailsForMobileBooking(String bookingId, Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(bookingId);
		LinkedHashMap<String, Object> itemDetailsMap = new LinkedHashMap<>();
		itemDetailsMap.put("invoice_id","0");
		itemDetailsMap.put("theInvoiceDate",getDateFromDB(con));
		itemDetailsMap.put("listOfItems",
				getListOfLinkedHashHashMap(parameters, "select\r\n"
						+ "*,tbr.booking_id,item_name,mi.item_id,price as rate,price as custom_rate,qty \r\n"
						+ "from\r\n"
						+ "	trn_booking_register tbr,booking_item_mpg bim,mst_items mi,mst_Client cust \r\n"
						+ "where\r\n"
						+ "	tbr.booking_id=? and bim.booking_id =tbr.booking_id and mi.item_id =bim.item_id and cust.Client_id=tbr.Client_id", con));
		return itemDetailsMap;

	}
	
	
	

	public List<LinkedHashMap<String, Object>> getConsolidatedPaymentModeCollection(HashMap<String, Object> hm1,
			Connection con) throws ClassNotFoundException, SQLException, ParseException {

		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm1.get("app_id"));
		parameters.add(getDateASYYYYMMDD(hm1.get("fromDate").toString()));
		parameters.add(getDateASYYYYMMDD(hm1.get("toDate").toString()));
		
		String query = "select\r\n" + "	firm.firm_name firmName,\r\n" + "	sum(amount) TotalAmount,\r\n"
				+ "	payment_mode PaymentMode,\r\n" + "	payment_date PaymentDate,\r\n"
				+ "	date_format(invoice.payment_date, '%d/%m/%Y %H:%i') as FormattedInvoiceDate\r\n" + "from\r\n"
				+ "	trn_payment_register invoice,\r\n" + "	mst_firm firm\r\n" + "where\r\n"
				+ "	invoice.firm_id = firm.firm_id and invoice.app_id=?\r\n"
				+ "	and date(payment_date) between ? and ?  and invoice.activate_flag=1 ";

		if(!hm1.get("firmId").toString().equals("-1"))
		{
			query += " and firm.firm_id=? ";
			parameters.add(hm1.get("firmId").toString());
		}
		query += "group by PaymentDate,payment_mode order by payment_date desc";
		return getListOfLinkedHashHashMap(parameters, query, con);

	}

	public List<LinkedHashMap<String, Object>> getPaymentTypeCollectionCollection(HashMap<String, Object> hm1,
			Connection con) throws ClassNotFoundException, SQLException, ParseException 
	{
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add((hm1.get("app_id").toString()));
		parameters.add(getDateASYYYYMMDD(hm1.get("fromDate").toString()));
		parameters.add(getDateASYYYYMMDD(hm1.get("toDate").toString()));
		String query="select firm.firm_name firmName,payment_type PaymentType,invoice_date InvoiceDate,sum(total_amount) Amount from "+ 
				"trn_invoice_register invoice, mst_firm firm where invoice.activate_flag=1 and invoice.firm_id=firm.firm_id and invoice.app_id=? and date(invoice_date) between ? and ? \r\n";
		

		if (!hm1.get("firmId").toString().equals("-1")) 
		{
			parameters.add(hm1.get("firmId").toString());
			query+=" and firm.firm_id=? ";			
		}
		query+=" group by payment_type,firm.firm_id, invoice_date order by invoice_date desc";
		
		return getListOfLinkedHashHashMap(parameters, query, con);
		
	}

	public String updateGroup(long categoryId, Connection conWithF, String categoryName) throws Exception {

		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(categoryName);
		parameters.add(categoryId);
		insertUpdateDuablDB("UPDATE Client_group  SET group_Name=?,updated_date=SYSDATE() WHERE group_id=?",
				parameters, conWithF);
		return "Group updated Succesfully";

	}
	
	public String updateExpenseHead(long categoryId, Connection conWithF, String categoryName) throws Exception {

		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(categoryName);
		parameters.add(categoryId);
		insertUpdateDuablDB("UPDATE mst_expense_heads  SET expense_name=?,updated_date=SYSDATE() WHERE expense_head_id=?",
				parameters, conWithF);
		return "Group updated Succesfully";

	}
	
	

	public String deleteGroup(long itemId, Connection conWithF) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(itemId);
		insertUpdateDuablDB("UPDATE Client_group  SET activate_flag=0 WHERE group_id=?", parameters, conWithF);
		return "Group Deleted Succesfully";
	}
	public String deleteExpenseHeads(long itemId, Connection conWithF) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(itemId);
		insertUpdateDuablDB("UPDATE mst_expense_heads  SET activate_flag=0 WHERE expense_head_id=?", parameters, conWithF);
		return "Expense Head Deleted Succesfully";
	}
	

	public long updateLowStockDetails(long stock_id, long lowqty, Connection conWithF) throws SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(lowqty);
		parameters.add(stock_id);
		return insertUpdateDuablDB("update stock_status set low_stock_limit=? where stock_id=?", parameters, conWithF);

	}

	public long addGroup(Connection conWithF, String groupName,String appId) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(groupName);
		parameters.add(appId);
		return insertUpdateDuablDB("insert into Client_group values (default,?,1,sysdate(),1,?)", parameters, conWithF);
	}
	
	public long addExpenseHead(Connection conWithF, String groupName,String appId) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(groupName);
		parameters.add(appId);
		return insertUpdateDuablDB("insert into mst_expense_heads values (default,?,1,sysdate(),1,?)", parameters, conWithF);
	}
	

	public List<LinkedHashMap<String, Object>> getClientGroup(String appId,Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		return getListOfLinkedHashHashMap(parameters, "select * from mst_group where activate_flag=1", con);
	}
	
	public List<LinkedHashMap<String, Object>> getExpenseHeads(String appId,Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(appId);
		return getListOfLinkedHashHashMap(parameters, "select * from mst_expense_heads where activate_flag=1 and app_id=?", con);
	}
	

	public LinkedHashMap<String, String> getGroupDetails(long categoryId, Connection con) throws SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(categoryId);
		return getMap(parameters, "select * from Client_group where group_id=?", con);
	}
	
	public LinkedHashMap<String, String> getExpenseHeadDetails(long categoryId, Connection con) throws SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(categoryId);
		return getMap(parameters, "select * from mst_expense_heads where expense_head_id=?", con);
	}
	

	public LinkedHashMap<String, String> getInvoiceSubDetails(long detailId, Connection con) throws SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(detailId);
		return getMap(parameters, "select\r\n"
				+ "	register.Client_id,dtls.custom_rate,register.invoice_id,dtls.item_id,dtls.details_id,item_name,qty-coalesce(sum( qty_to_return),0) returnAbleQty\r\n"
				+ "from\r\n" + "	trn_invoice_register register,\r\n" + "	\r\n"
				+ " 	mst_items item ,trn_invoice_details dtls left outer join\r\n"
				+ "	trn_return_register ret on ret.details_id=dtls.details_id where\r\n"
				+ "	dtls.details_id =?\r\n" + "	and item.item_id = dtls.item_id\r\n"
				+ "	 and register.activate_flag=1 and register.invoice_id = dtls.invoice_id;\r\n" + "\r\n" + "\r\n" + "", con);
	}

	public void insertToTrnReturnRegister(long detailsId, Double returnqty, String userId,String appId, Connection conWithF)
			throws SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(detailsId);
		parameters.add(returnqty);
		parameters.add(userId);
		parameters.add(appId);
		insertUpdateDuablDB("insert into trn_return_register values (default,?,?,?,sysdate(),?);", parameters, conWithF);
	}

	public LinkedHashMap<String, String> getuserDetailsById(long userId, Connection con) throws SQLException {

		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(userId);
		return getMap(parameters, "select * from tbl_user_mst where user_id=?", con);

	}

	public String updatefirmForThisUser(long firmId, String userId, Connection conWithF) throws SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(firmId);
		parameters.add(userId);
		insertUpdateDuablDB("update tbl_user_mst set firm_id=? where user_id=?", parameters, conWithF);
		return "Updated Succesfully";
	}
	
	public String updateInvoiceFormatForThisUser(long firmId, String userId, Connection conWithF) throws SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(firmId);
		parameters.add(userId);
		insertUpdateDuablDB("update tbl_user_mst set invoice_format=? where user_id=?", parameters, conWithF);
		return "Updated Succesfully";
	}
	
	

	public List<LinkedHashMap<String, Object>> getEmployeeMaster(HashMap<String, Object> hm,Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		return getListOfLinkedHashHashMap(parameters,
				 "select	*,tum2.name supervisorName from tbl_user_mst user left outer join  tbl_user_mst tum2 on tum2.user_id=user.parent_user_id "
						+ " where user.activate_flag = 1 ",
				con);
	}
	
	public List<LinkedHashMap<String, Object>> getEmployeeMasterWithSupervisorId(HashMap<String, Object> hm,Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("parent_user_id"));
		return getListOfLinkedHashHashMap(parameters,
				 "select	*,tum2.name supervisorName from tbl_user_mst user, tbl_user_mst tum2"
						+ " where user.activate_flag = 1 and tum2.user_id=user.parent_user_id and user.parent_user_id=?",
				con);
	}
	
	

	public String deleteEmployee(long employeeId, Connection conWithF) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(employeeId);
		insertUpdateDuablDB("UPDATE tbl_user_mst SET activate_flag=0,updated_date=SYSDATE() WHERE user_id=?",
				parameters, conWithF);
		return "Employee Deleted Succesfully";
	}

	public String updateEmployee(long employeeId, Connection conWithF, HashMap<String, Object> hm) throws Exception {

		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("username"));
		parameters.add((String) hm.get("EmployeeName"));
		parameters.add(Long.parseLong(hm.get("MobileNumber").toString()));
		parameters.add(hm.get("email").toString());
		parameters.add(hm.get("AadharCardNo").toString());
		parameters.add(hm.get("parent_user_id").toString());
		parameters.add(hm.get("QrCode").toString());
		parameters.add(hm.get("type").toString());
		parameters.add(employeeId);

		insertUpdateDuablDB(
				"UPDATE tbl_user_mst  SET username=?, name = ?,updated_date=SYSDATE(),mobile=?,email=?,aadhar_card_no=?,parent_user_id=?,qr_code=?,type=? WHERE user_id=?",
				parameters, conWithF);
		return "Employee Updated Succesfully";

	}

	public long addEmployee(Connection conWithF, HashMap<String, Object> hm) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("username"));
		parameters.add(getSHA256String("default@123"));
		parameters.add((String) hm.get("EmployeeName"));
		parameters.add(Long.parseLong(hm.get("MobileNumber").toString()));
		parameters.add(hm.get("email").toString());
		
		parameters.add(Long.parseLong(hm.get("app_id").toString()));
		parameters.add(hm.get("AadharCardNo").toString());
		parameters.add(Long.parseLong(hm.get("parent_user_id").toString()));
		parameters.add(hm.get("QrCode").toString());
		parameters.add(hm.get("type").toString());
		String insertQuery = "insert into tbl_user_mst values (default,?,?,sysdate(),null,1,?,?,?,?,?,?,?,?)";
		return insertUpdateDuablDB(insertQuery, parameters, conWithF);
	}

	public LinkedHashMap<String, String> getEmployeeDetails(long ClientId, Connection con) throws SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(ClientId);
		return getMap(parameters, "select  * from tbl_user_mst where user_id=?", con);
	}
	public LinkedHashMap<String, String> getEmployeeDetailsByQrCode(String qrCode, Connection con) throws SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(qrCode);
		return getMap(parameters, "select  * from tbl_user_mst where qr_code=? and activate_flag=1", con);
	}

	public LinkedHashMap<String, String> getGatePassDetailsByQrCode(String qrCode, Connection con) throws SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(qrCode);
		return getMap(parameters, "select  * from trn_gate_pass tgp,tbl_user_mst tum where tgp.user_id=tum.user_id and activate_flag=1 and tum.qr_code=? order by gate_pass_id desc limit 1", con);
	}

	public boolean mobileNoAlreadyExist(String mobileNo, long ClientId,String appId,String type, Connection con) throws SQLException {
		String query = "select count(1) as cnt from mst_Client where activate_flag=1 and mobile_number=? and app_id=? and client_vendor_flag=?";
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(mobileNo);
		parameters.add(appId);
		parameters.add(type);
		if (ClientId != 0) {
			parameters.add(ClientId);
			query += " and Client_id!=?";
		}
		return !getMap(parameters, query, con).get("cnt").equals("0");
	}

	public List<LinkedHashMap<String, Object>> getItemHistory(long firmId, String itemId, Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		
		parameters.add(itemId);

		String query = "select *,invoice_id,qty,'' as custom_rate,"
				+ "	`type` as type,"
				+ "	date_format(tsr.updated_date, '%d/%m/%Y %H:%i:%s') as formattedUpdatedDate from"
				+ "	trn_stock_register tsr,mst_firm firm, tbl_user_mst user1 "
				+ "where item_id = ? and tsr.firm_id = firm.firm_id "
				+ "	and tsr.updated_by = user1.user_id ";
		if (firmId != -1) {
			query += "and firm.firm_id=? ";
			parameters.add(firmId);
		}
		query += " order by tsr.stock_register_id desc";

		return getListOfLinkedHashHashMap(parameters, query, con);
	}

	public List<LinkedHashMap<String, Object>> getRoleList(Connection con) throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		return getListOfLinkedHashHashMap(parameters, "select * from acl_role_mst where role_name!='SuperAdmin' ", con);
	}

	public boolean checkIfRoleUserAlreadyExist(long userId, Long roleId, Connection conWithF) throws SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(userId);
		parameters.add(roleId);
		return !getMap(parameters,
				"select count(1) cnt from acl_user_role_rlt where user_id=? and role_id=? and activate_flag=1",
				conWithF).get("cnt").equals("0");

	}
	
	public boolean checkIfClientServiceAlreadyExist(long clientId, Long serviceId, Connection conWithF) throws SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(clientId);
		parameters.add(serviceId);
		return !getMap(parameters,
				"select count(1) cnt from client_service_mapping where client_id=? and service_id=? and activate_flag=1",
				conWithF).get("cnt").equals("0");

	}

	public long addUserRoleMapping(long userId, Long roleId,Connection conWithF) throws SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(userId);
		parameters.add(roleId);
		
		return insertUpdateDuablDB("insert into acl_user_role_rlt values (default,?,?,1,sysdate(),null)", parameters,
				conWithF);

	}
	
	public long addClientServiceMapping(long clientId, Long serviceId,String userId, Connection conWithF) throws SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(clientId);
		parameters.add(serviceId);
		parameters.add(userId);
		
		
		return insertUpdateDuablDB("insert into client_service_mapping values (default,?,?,sysdate(),?,1)", parameters,
				conWithF);

	}
	

	public List<LinkedHashMap<String, Object>> getReturnRegister(HashMap<String,Object> hm,Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("app_id"));
		return getListOfLinkedHashHashMap(parameters,
				"SELECT return_id ReturnId, details_id detailsId, qty_to_return QuantityToReturn, updated_by UpdatedBy  from trn_return_Register where app_id=?",
				con);

	}
	
	
	public List<LinkedHashMap<String, Object>> getTransactions(HashMap<String,Object> hm,Connection con)
			throws ClassNotFoundException, SQLException, ParseException {
		ArrayList<Object> parameters = new ArrayList<>();
		
		
		String query="SELECT\r\n"
				+ "	*\r\n"
				+ "from\r\n"
				+ "	trn_payment_register tpr  inner join trn_payment_details tpd on tpr.payment_id =tpd.payment_id  \r\n"
				+ "	left outer join mst_Client cust on  cust.Client_id = tpr.Client_id\r\n"
				+ "	left outer join mst_bank bank on  bank.bank_id = tpr.bank_id"
				+ " inner join tbl_user_mst tum on tpr.updated_by =tum.user_id  \r\n"
				+ "where\r\n"
				+ "tpr.activate_flag = 1 ";		
		String type=hm.get("type").toString();
		String firmId=hm.get("firmId").toString();
		String fromDate=hm.get("txtfromdate").toString();
		String toDate=hm.get("txttodate").toString();
		
		
		
		
		if(firmId!=null && !firmId.equals("") && !firmId.equals("-1"))
		{
			query+=" and tpr.firm_id= ? ";
			parameters.add(firmId);
		}
		
		if(type!=null && !type.equals(""))
		{
			query+=" and tpr.type= ? ";
			parameters.add(type);
		}
		
		if(fromDate!=null && !fromDate.equals(""))
		{
			query+=" and tpr.payment_date between  ?  and ?";
			parameters.add(getDateASYYYYMMDD(fromDate));
			parameters.add(getDateASYYYYMMDD(toDate));
			
		}
		
		query+=" order by tpr.updated_date  desc";
		
		
		return getListOfLinkedHashHashMap(parameters,query,con);

	}
	
	public List<LinkedHashMap<String, Object>> getJournals(HashMap<String,Object> hm,Connection con)
			throws ClassNotFoundException, SQLException, ParseException {
		ArrayList<Object> parameters = new ArrayList<>();
		
		
		String query="select *,(select Client_name from mst_Client where Client_id in (tje.debit)) as Client_name1 from trn_journal_entry tje, mst_firm firm"
				+ " where tje.activate_flag=1  and firm.firm_id=tje.firm_id";		
		
		String firmId=hm.get("firmId").toString();
		String fromDate=hm.get("txtfromdate").toString();
		String toDate=hm.get("txttodate").toString();
		
		
		
		
		if(firmId!=null && !firmId.equals("") && !firmId.equals("-1"))
		{
			query+=" and tje.firm_id= ? ";
			parameters.add(firmId);
		}
		
		
		
		if(fromDate!=null && !fromDate.equals(""))
		{
			query+=" and tje.journal_date between  ?  and ?";
			parameters.add(getDateASYYYYMMDD(fromDate));
			parameters.add(getDateASYYYYMMDD(toDate));
			
		}
		
		
		
		
		return getListOfLinkedHashHashMap(parameters,query,con);

	}
	
	public List<LinkedHashMap<String, Object>> getInternalTransfers(HashMap<String,Object> hm,Connection con)
			throws ClassNotFoundException, SQLException, ParseException {
		ArrayList<Object> parameters = new ArrayList<>();
		
		
		String query="select *,firm2.firm_name to_firm_name,firm1.firm_name from_firm_name,"
				+ "bank2.account_no to_bank_account,bank1.account_no from_bank_account,  "
				+ "bank2.bank_name to_bank_name,bank1.bank_name from_bank_name  "
				+ " from trn_internal_transfer tje,mst_firm firm1,mst_firm firm2,mst_bank bank1,mst_bank bank2"
				+ " where tje.activate_flag=1  and firm1.firm_id=tje.from_firm and firm2.firm_id=tje.to_firm and bank1.bank_id=tje.from_account and bank2.bank_id=tje.to_account";		
		
		String firmId=hm.get("firmId").toString();
		String fromDate=hm.get("txtfromdate").toString();
		String toDate=hm.get("txttodate").toString();
		
		
		
		
		if(firmId!=null && !firmId.equals("") && !firmId.equals("-1"))
		{
			query+=" and (tje.from_firm= ? or tje.to_firm=?)";
			parameters.add(firmId);
			parameters.add(firmId);
		}
		
		
		
		if(fromDate!=null && !fromDate.equals(""))
		{
			query+=" and tje.transfer_date between  ?  and ?";
			parameters.add(getDateASYYYYMMDD(fromDate));
			parameters.add(getDateASYYYYMMDD(toDate));
			
		}
		
		
		
		
		return getListOfLinkedHashHashMap(parameters,query,con);

	}
	
	public List<LinkedHashMap<String, Object>> getInternalJournalTransfers(HashMap<String,Object> hm,Connection con)
			throws ClassNotFoundException, SQLException, ParseException {
		ArrayList<Object> parameters = new ArrayList<>();
		
		
		String query="select *,firm1.firm_name as FromFirm,firm2.firm_name as ToFirm from trn_journal_transfer tje,mst_firm firm1,mst_firm firm2 where tje.activate_flag=1 and firm1.firm_id=tje.from_firm "
				+ " and firm2.firm_id=tje.to_firm";		
		
		String firmId=hm.get("firmId").toString();
		String fromDate=hm.get("txtfromdate").toString();
		String toDate=hm.get("txttodate").toString();
		
		
		
		
		if(firmId!=null && !firmId.equals("") && !firmId.equals("-1"))
		{
			query+=" and (tje.from_firm= ? or tje.to_firm=?)";
			parameters.add(firmId);
			parameters.add(firmId);
		}
		
		
		
		if(fromDate!=null && !fromDate.equals(""))
		{
			query+=" and tje.transfer_date between  ?  and ?";
			parameters.add(getDateASYYYYMMDD(fromDate));
			parameters.add(getDateASYYYYMMDD(toDate));
			
		}
		
		
		
		
		return getListOfLinkedHashHashMap(parameters,query,con);

	}
	
	
	
	
	
	
	public List<LinkedHashMap<String, Object>> getInvoicesPurchase(HashMap<String,Object> hm,Connection con)
			throws ClassNotFoundException, SQLException, ParseException {
		ArrayList<Object> parameters = new ArrayList<>();
		
		
		String query="select * from trn_purchase_invoice_register tpr,mst_Client cust, mst_firm firm where cust.Client_id=tpr.Client_id and firm.firm_id=tpr.firm_id and tpr.activate_flag=1 ";		
		
		String firmId=hm.get("firmId").toString();
		String fromDate=hm.get("txtfromdate").toString();
		String toDate=hm.get("txttodate").toString();
		
		
		
		
		if(firmId!=null && !firmId.equals("") && !firmId.equals("-1"))
		{
			query+=" and tpr.firm_id= ? ";
			parameters.add(firmId);
		}
		
		
		
		if(fromDate!=null && !fromDate.equals(""))
		{
			query+=" and tpr.invoice_date between  ?  and ?";
			parameters.add(getDateASYYYYMMDD(fromDate));
			parameters.add(getDateASYYYYMMDD(toDate));
			
		}
		
		
		
		
		return getListOfLinkedHashHashMap(parameters,query,con);

	}
	
	public List<LinkedHashMap<String, Object>> getInvoicesSales(HashMap<String,Object> hm,Connection con)
			throws ClassNotFoundException, SQLException, ParseException {
		ArrayList<Object> parameters = new ArrayList<>();
		
		
		String query="select * from trn_sales_invoice_register tsr ,mst_Client cust,mst_firm firm where cust.Client_id=tsr.Client_id and firm.firm_id=tsr.firm_id and tsr.activate_flag=1  ";		
		
		String firmId=hm.get("firmId").toString();
		String fromDate=hm.get("txtfromdate").toString();
		String toDate=hm.get("txttodate").toString();
		
		
		
		
		if(firmId!=null && !firmId.equals("") && !firmId.equals("-1"))
		{
			query+=" and tsr.firm_id= ? ";
			parameters.add(firmId);
		}
		
		
		
		if(fromDate!=null && !fromDate.equals(""))
		{
			query+=" and tsr.invoice_date between  ?  and ?";
			parameters.add(getDateASYYYYMMDD(fromDate));
			parameters.add(getDateASYYYYMMDD(toDate));
			
		}
		
		
		
		
		return getListOfLinkedHashHashMap(parameters,query,con);

	}
	
	public List<LinkedHashMap<String, Object>> getChallanOut(HashMap<String,Object> hm,Connection con)
			throws ClassNotFoundException, SQLException, ParseException {
		ArrayList<Object> parameters = new ArrayList<>();
		
		
		String query="select\r\n"
				+ "	*\r\n"
				+ "from\r\n"
				+ "	trn_challan_out tsr \r\n"
				+ "	inner join 	mst_Client cust on cust.Client_id = tsr.Client_id\r\n"
				+ "	inner join	mst_firm firm on  firm.firm_id = tsr.firm_id\r\n"
				+ "	left outer join rlt_challan_invoice rci on rci.challan_id =tsr.challan_id and rci.type='S'\r\n"
				+ "where\r\n"
				+ "	tsr.activate_flag = 1 ";		
		
		String firmId=hm.get("firmId").toString();
		String fromDate=hm.get("txtfromdate").toString();
		String toDate=hm.get("txttodate").toString();
		
		
		
		
		if(firmId!=null && !firmId.equals("") && !firmId.equals("-1"))
		{
			query+=" and tsr.firm_id= ? ";
			parameters.add(firmId);
		}
		
		
		
		if(fromDate!=null && !fromDate.equals(""))
		{
			query+=" and tsr.invoice_date between  ?  and ?";
			parameters.add(getDateASYYYYMMDD(fromDate));
			parameters.add(getDateASYYYYMMDD(toDate));
			
		}
		
		
		
		
		return getListOfLinkedHashHashMap(parameters,query,con);

	}
	
	
	public List<LinkedHashMap<String, Object>> getChallanIn(HashMap<String,Object> hm,Connection con)
			throws ClassNotFoundException, SQLException, ParseException {
		ArrayList<Object> parameters = new ArrayList<>();
		
		
		String query="select\r\n"
				+ "	*\r\n"
				+ "from\r\n"
				+ "	trn_challan_in tsr \r\n"
				+ "	inner join mst_Client cust on  cust.Client_id = tsr.Client_id\r\n"
				+ "	inner join 	mst_firm firm on firm.firm_id = tsr.firm_id\r\n"
				+ "	left outer join rlt_challan_invoice rci on rci.challan_id =tsr.challan_id and rci.type='P'\r\n"
				+ "where\r\n"
				+ "tsr.activate_flag = 1 ";		
		
		String firmId=hm.get("firmId").toString();
		String fromDate=hm.get("txtfromdate").toString();
		String toDate=hm.get("txttodate").toString();
		
		
		
		
		if(firmId!=null && !firmId.equals("") && !firmId.equals("-1"))
		{
			query+=" and tsr.firm_id= ? ";
			parameters.add(firmId);
		}
		
		
		
		if(fromDate!=null && !fromDate.equals(""))
		{
			query+=" and tsr.invoice_date between  ?  and ?";
			parameters.add(getDateASYYYYMMDD(fromDate));
			parameters.add(getDateASYYYYMMDD(toDate));
			
		}
		
		
		
		
		return getListOfLinkedHashHashMap(parameters,query,con);

	}
	

	public List<LinkedHashMap<String, Object>> getClientInvoiceHistory(String ClientId, String fromDate,
			String toDate, Connection con) throws ClassNotFoundException, SQLException, ParseException {
		ArrayList<Object> parameters = new ArrayList<>();
		String query = " select\r\n" + "	*,\r\n"
				+ "	date_format(invoice_date, '%d/%m/%Y') as formattedInvoiceDate,\r\n"
				+ "	date_format(a.updated_date, '%d/%m/%Y %H:%i:%s') as formattedUpdatedDate,\r\n"
				+ "	rate-custom_rate Discount,\r\n"
				+ "	(details.qty-coalesce(return1.qty_to_return,0))*custom_rate as ItemAmount, \r\n"
				+ "	((details.qty-coalesce(return1.qty_to_return,0))*rate - (details.qty-coalesce(return1.qty_to_return,0))*custom_rate) DiscountAmount,\r\n"
				+ "	details.qty-coalesce(return1.qty_to_return,0) as BilledQty	\r\n" + "from\r\n"
				+ "mst_items item inner join trn_invoice_details details on item.item_id=details.item_id\r\n"
				+ "inner join	trn_invoice_register a on a.activate_flag=1 and details.invoice_id=a.invoice_id\r\n"
				+ "left outer join mst_Client b on	a.Client_id = b.Client_id\r\n"
				+ "left outer join  trn_return_register return1 on details.details_id=return1.details_id\r\n"
				+ "where\r\n" + "	a.activate_flag = 1\r\n" + "	and b.activate_flag = 1\r\n"
				+ "	 \r\n" + "	and date(a.invoice_date) between ? and ?  ";
		
		parameters.add(getDateASYYYYMMDD(fromDate));
		parameters.add(getDateASYYYYMMDD(toDate));
		if(ClientId!=null && !ClientId.equals(""))
		{
			query+=" and b.Client_id = ? ";
			parameters.add(ClientId);
		}
		
		query+=" order by a.invoice_date,a.invoice_id,details.details_id";

		

		

		return getListOfLinkedHashHashMap(parameters, query, con);

	}

	public List<LinkedHashMap<String, Object>> getClientItemHistory(String appId,String ClientId, String fromDate, String toDate,
			Connection con) throws ClassNotFoundException, SQLException, ParseException {
		ArrayList<Object> parameters = new ArrayList<>();
		
		String query = " select\r\n" + "	*,\r\n"
				+ "	date_format(invoice_date, '%d/%m/%Y') as formattedInvoiceDate,\r\n"
				+ "	date_format(a.updated_date, '%d/%m/%Y %H:%i:%s') as formattedUpdatedDate,\r\n"
				+ "	rate-custom_rate Discount,\r\n"
				+ "	(details.qty-coalesce(return1.qty_to_return,0))*custom_rate as ItemAmount, \r\n"
				+ "	((details.qty-coalesce(return1.qty_to_return,0))*rate - (details.qty-coalesce(return1.qty_to_return,0))*custom_rate) DiscountAmount,\r\n"
				+ "	details.qty-coalesce(return1.qty_to_return,0) as BilledQty	\r\n" + "from\r\n"
				+ "mst_items item inner join trn_invoice_details details on item.item_id=details.item_id\r\n"
				+ "inner join	trn_invoice_register a on a.activate_flag=1 and details.invoice_id=a.invoice_id\r\n"
				+ "left outer join mst_Client b on	a.Client_id = b.Client_id\r\n"
				+ "left outer join  trn_return_register return1 on details.details_id=return1.details_id\r\n"
				+ "where\r\n" + "	a.activate_flag = 1\r\n" + "	and b.activate_flag = 1\r\n"
				+ "	 \r\n" + "	and date(a.invoice_date) between ? and ? and details.app_id=?  ";
		
		if(ClientId!=null && !ClientId.equals(""))
		{
			query+=" and b.Client_id = ? ";
			parameters.add(ClientId);
		}
		
		query+=" order by b.Client_name,a.invoice_date,a.invoice_id,details.details_id ";

		

		parameters.add(getDateASYYYYMMDD(fromDate));
		parameters.add(getDateASYYYYMMDD(toDate));
		parameters.add(appId);

		return getListOfLinkedHashHashMap(parameters, query, con);

	}

	public List<LinkedHashMap<String, Object>> getClientLedgerReport(String ClientId,String firmId, String fromDate,
			String toDate, Connection con) throws ParseException, ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		String query = "\r\n"
				+ "select * from (\r\n"
				+ " select firm_id,invoice_id as RefId ,invoice_date transaction_date,total_amount as debitAmount,0 creditAmount,remarks,tsir.updated_date updDate,'Sales' type,'' Particular from trn_sales_invoice_register tsir where \r\n"
				+ "	Client_id =? and invoice_date  between ? and ? and tsir.activate_flag =1 firmCondition	\r\n"
				+ "union all\r\n"
				+ "select firm_id,payment_id as RefId,payment_date transaction_date,\r\n"
				+ "	case when type='P' then amount  else 0 end as debitAmount,\r\n"
				+ "	case when type='R' then amount else 0 end as creditAmount,remarks ,tpr.updated_date updDate,\r\n"
				+ "	case when type='P' then 'Payment' else 'Collection' end type,bank_name\r\n"
				+ "	from trn_payment_register tpr,mst_bank bank  where \r\n"
				+ "		Client_id =? and payment_date  between ? and ?	and tpr.activate_flag =1 and bank.bank_id=tpr.bank_id firmCondition	\r\n"
				+ "union all \r\n"
				+ "	select firm_id,invoice_id as RefId,invoice_date transaction_date,0 as debitAmount,total_amount creditAmount,remarks,tsir.updated_date updDate,\r\n"
				+ "	'Purchase' type,'' Particular \r\n"
				+ "	from trn_purchase_invoice_register tsir where \r\n"
				+ "	Client_id =? and invoice_date  between ? and ?  and tsir.activate_flag=1 firmCondition "
				+ "union all \r\n"
				+ "	select firm_id,journal_id as RefId,journal_date transaction_date,0 as debitAmount,total_amount creditAmount,remarks,tje.updated_date updDate,\r\n"
				+ "	'JournalEntryCredit' type,mc.Client_name \r\n"
				+ "	from trn_journal_entry tje,mst_Client mc where \r\n"
				+ "	credit =? and tje.journal_date  between ? and ?  and tje.activate_flag =1 and mc.Client_id=tje.debit firmCondition \r\n"
				+ " union all \r\n"
				+ "	select firm_id,journal_id as RefId,journal_date transaction_date,total_amount as debitAmount,0 creditAmount,remarks,tje.updated_date updDate,\r\n"
				+ "	'JournalEntryDebit' type,mc.Client_name\r\n"
				+ "	from trn_journal_entry tje,mst_Client mc where \r\n"
				+ "	debit =? and tje.journal_date  between ? and ?  and tje.activate_flag =1 and mc.Client_id=tje.credit firmCondition \r\n"
				+ "	) as T,mst_firm firm where T.firm_id=firm.firm_id \r\n"
				+ "	order by transaction_date,updDate asc";

		parameters.add((ClientId));
		parameters.add(getDateASYYYYMMDD(fromDate));
		parameters.add(getDateASYYYYMMDD(toDate));
		
		if((!firmId.equals("")) && !firmId.equals("-1"))
		{
			query=query.replaceFirst("firmCondition", "and firm_id=?");
			parameters.add((firmId));
		}
		
		parameters.add((ClientId));
		parameters.add(getDateASYYYYMMDD(fromDate));
		parameters.add(getDateASYYYYMMDD(toDate));
		
		if((!firmId.equals("")) && !firmId.equals("-1"))
		{
			query=query.replaceFirst("firmCondition", "and firm_id=?");
			parameters.add((firmId));
		}

		parameters.add((ClientId));
		parameters.add(getDateASYYYYMMDD(fromDate));
		parameters.add(getDateASYYYYMMDD(toDate));
		
		if((!firmId.equals("")) && !firmId.equals("-1"))
		{
			query=query.replaceFirst("firmCondition", "and firm_id=?");
			parameters.add((firmId));
		}
		
		parameters.add((ClientId));
		parameters.add(getDateASYYYYMMDD(fromDate));
		parameters.add(getDateASYYYYMMDD(toDate));
		
		if((!firmId.equals("")) && !firmId.equals("-1"))
		{
			query=query.replaceFirst("firmCondition", "and firm_id=?");
			parameters.add((firmId));
		}
		
		parameters.add((ClientId));
		parameters.add(getDateASYYYYMMDD(fromDate));
		parameters.add(getDateASYYYYMMDD(toDate));
		
		if((!firmId.equals("")) && !firmId.equals("-1"))
		{
			query=query.replaceFirst("firmCondition", "and firm_id=?");
			parameters.add((firmId));
		}
		
		query=query.replaceAll("firmCondition", "");

		return getListOfLinkedHashHashMap(parameters, query, con);

	}

	public List<LinkedHashMap<String, Object>> getStockModifications(HashMap<String, Object> hm, Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("app_id"));
		String query = "select *,date_format(modification.updated_date,'%d/%m/%Y') as formattedUpdatedDate from stock_modification_master modification, mst_firm firm ,"
				+ " tbl_user_mst user where firm.firm_id=modification.firm_id and user.user_id=modification.updated_user and modification.app_id=firm.app_id and firm.app_id=? ";
		if (hm.get("firmId") != null && !hm.get("firmId").equals("") && !hm.get("firmId").equals("-1")) {
			query += " and firm.firm_id=?";
			parameters.add(hm.get("firmId"));
		}
		return getListOfLinkedHashHashMap(parameters, query, con);
	}

	public long addStockModification(HashMap<String, Object> outputMap, Connection conWithF) throws SQLException {

		ArrayList<Object> parameters = new ArrayList<>();

		parameters.add(outputMap.get("type"));
		parameters.add(outputMap.get("userId"));
		parameters.add(outputMap.get("firmId"));
		parameters.add(outputMap.get("outerRemarks"));
		parameters.add(outputMap.get("app_id"));
		parameters.add(outputMap.get("ware_house_id"));
		parameters.add(outputMap.get("destination_ware_house_id"));
		parameters.add(outputMap.get("destinationFirmId"));
		

		String insertQuery = "insert into stock_modification_master values (default,?,curdate(),sysdate(),?,?,?,?,?,?,?)";
		return insertUpdateDuablDB(insertQuery, parameters, conWithF);

	}

	public long addStockModificationAddRemove(HashMap<String, Object> outputMap, Connection conWithF)
			throws SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(outputMap.get("stockModificationId"));
		parameters.add(outputMap.get("itemId"));
		parameters.add(outputMap.get("currentStock"));
		parameters.add(outputMap.get("qty"));
		parameters.add(outputMap.get("remarks"));
		parameters.add(outputMap.get("app_id"));
		String insertQuery = "insert into stock_modification_addremove values (default,?,?,?,?,?,?)";
		return insertUpdateDuablDB(insertQuery, parameters, conWithF);
	}

	public List<LinkedHashMap<String, Object>> getStockModificationDetailsAddRemove(String stockModificationId,
			Connection con) throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();

		String query = "select\r\n"
				+ "	*,date_format(transaction_date,'%d/%m/%Y') as transactionDateFormatted,master.remarks remarksouter,addrem.remarks as remarksinner \r\n"
				+ "from\r\n" + "	stock_modification_master master,\r\n"
				+ "	stock_modification_addremove addrem,\r\n" + "	mst_items item\r\n" + "where\r\n"
				+ "	master.stock_modification_id = ? and master.stock_modification_id=addrem.stock_modification_id and item.item_id=addrem.item_id";
		parameters.add(stockModificationId);
		return getListOfLinkedHashHashMap(parameters, query, con);
	}

	public List<LinkedHashMap<String, Object>> getStockModificationDetailsInventoryCounting(String stockModificationId,
			Connection con) throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();

		String query = "select\r\n"
				+ "	*,date_format(transaction_date,'%d/%m/%Y') as transactionDateFormatted,master.remarks remarksouter \r\n"
				+ "from\r\n" + "	stock_modification_master master,\r\n"
				+ "	stock_modification_inventorycounting ivecounting,\r\n" + "	mst_items item\r\n" + "where\r\n"
				+ "	master.stock_modification_id = ? and master.stock_modification_id=ivecounting.stock_modification_id and item.item_id=ivecounting.item_id";
		parameters.add(stockModificationId);
		return getListOfLinkedHashHashMap(parameters, query, con);
	}

	public long saveStockModificationInventoryCounting(HashMap<String, Object> outputMap, Connection conWithF)
			throws SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(outputMap.get("stockModificationId"));
		parameters.add(outputMap.get("itemId"));
		parameters.add(outputMap.get("expectedCount"));
		parameters.add(outputMap.get("currentCount"));
		parameters.add(outputMap.get("difference"));
		parameters.add(outputMap.get("differenceAmount"));
		parameters.add(outputMap.get("app_id"));
		String insertQuery = "insert into stock_modification_inventorycounting values (default,?,?,?,?,?,?,?)";
		return insertUpdateDuablDB(insertQuery, parameters, conWithF);
	}

	public long saveStockModificationtransferStock(HashMap<String, Object> outputMap, Connection conWithF)
			throws SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(outputMap.get("stockModificationId"));
		parameters.add(outputMap.get("itemId"));
		parameters.add(outputMap.get("sourcebefore"));
		parameters.add(outputMap.get("sourceafter"));
		parameters.add(outputMap.get("qty"));
		parameters.add(outputMap.get("destinationbefore"));
		parameters.add(outputMap.get("destinationafter"));
		parameters.add(outputMap.get("sourcefirm"));
		parameters.add(outputMap.get("destinationfirm"));
		parameters.add(outputMap.get("app_id"));

		String insertQuery = "insert into stock_modification_transferstock values (default,?,?,?,?,?,?,?,?,?,?)";
		return insertUpdateDuablDB(insertQuery, parameters, conWithF);
	}

	public List<LinkedHashMap<String, Object>> getStockModificationDetailsStocktransfer(String stockModificationId,
			Connection con) throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();

		String query = "select *,date_format(transaction_date,'%d/%m/%Y') as transactionDateFormatted "
				+ "from	stock_modification_master master,"
				+ "	stock_modification_transferstock transferstock,	mst_items item where "
				+ "	master.stock_modification_id = ? and master.stock_modification_id=transferstock.stock_modification_id and item.item_id=transferstock.item_id";
		parameters.add(stockModificationId);
		return getListOfLinkedHashHashMap(parameters, query, con);
	}
	
	
	
	public LinkedHashMap<String, String> validateLoginForApp(String number, String password, Connection con)
			throws SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(number);
		parameters.add(password);
		
		return getMap(parameters,
				"select  user_id,firm1.firm_id,firm_name,usermst.app_id from tbl_user_mst usermst,mst_firm firm1 where mobile=? and password=? and firm1.firm_id=usermst.firm_id ",
				
				con);
	}

	public List<LinkedHashMap<String, Object>> getRecentInvoiceForUser(LinkedHashMap<String, Object> hm1,
			Connection con) throws ClassNotFoundException, SQLException, ParseException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm1.get("user_id"));
		parameters.add(hm1.get("app_id"));
		return getListOfLinkedHashHashMap(parameters,
				"select inv.*,cust.*,date_format(inv.updated_date,'%d/%m/%Y') as FormattedInvoiceDate "
						+ "from trn_invoice_register inv left outer join mst_Client cust on inv.Client_id=cust.Client_id "
						+ "left outer join tbl_user_mst usertbl on inv.updated_by = usertbl.user_id "
						+ "where inv.updated_by=? and inv.activate_flag=1 and inv.app_id=? order by invoice_id desc limit 100 ",
				con);
	}

	

	public List<LinkedHashMap<String, Object>> getPaymentDataAgainstSales(HashMap<String, Object> hm, Connection con)
			throws ClassNotFoundException, SQLException, ParseException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(getDateASYYYYMMDD(hm.get("fromDate").toString()));
		parameters.add(getDateASYYYYMMDD(hm.get("toDate").toString()));
		parameters.add((hm.get("app_id").toString()));
		String query = "select firm_name,round(sum(Cash)) Cash,	round(sum(Paytm)) Paytm,round(sum(Amazon)) Amazon,round(sum(GooglePay)) GooglePay,round(sum(Zomato)) Zomato,round(sum(Swiggy)) Swiggy,round(sum(Card)) Card,round(sum(PhonePay)) PhonePay, "
				+ "round(sum(Cash)+sum(Paytm)+sum(Amazon)+sum(GooglePay)+sum(Zomato)+sum(Swiggy)+sum(Card)+sum(PhonePay)) as HoriTotal,T.firm_id \r\n"
				+ " from (\r\n"
				+ "\r\n" + "select\r\n"
				+ "		ms.firm_id ,firm_name,case when payment_mode ='Cash' then amount else 0 end Cash, \r\n"
				+ "							case when payment_mode ='Paytm' then amount else 0 end Paytm, \r\n"
				+ "							case when payment_mode ='Amazon' then amount else 0 end Amazon, \r\n"
				+ "							case when payment_mode ='Google Pay' then amount else 0 end GooglePay, \r\n"
				+ "							case when payment_mode ='Phone Pay' then amount else 0 end PhonePay, \r\n"				
				+ "							case when payment_mode ='Zomato' then amount else 0 end Zomato, \r\n"
				+ "							case when payment_mode ='Card' then amount else 0 end Card, \r\n"
				+ "							case when payment_mode ='Swiggy' then amount else 0 end Swiggy \r\n"
				+ "							\r\n" + "from\r\n" + "	trn_payment_register tpr\r\n"
				+ "inner join mst_firm ms on\r\n" + "	ms.firm_id = tpr.firm_id\r\n" + "where\r\n"
				+ "	payment_for in ('invoice') and date(payment_date) between ? and ? and tpr.app_id=? and tpr.activate_flag=1 and tpr.app_id=ms.app_id \r\n"
				+ ") as T group by firm_name";
		return getListOfLinkedHashHashMap(parameters, query, con);
	}

	public List<LinkedHashMap<String, Object>> getPaymentDataAgainstCollection(HashMap<String, Object> hm,
			Connection con) throws ClassNotFoundException, SQLException, ParseException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(getDateASYYYYMMDD(hm.get("fromDate").toString()));
		parameters.add(getDateASYYYYMMDD(hm.get("toDate").toString()));
		parameters.add((hm.get("app_id").toString()));
		String query = "select firm_name,round(sum(Cash)) Cash,	round(sum(Paytm)) Paytm,round(sum(Amazon)) Amazon,round(sum(GooglePay)) GooglePay,round(sum(Zomato)) Zomato,round(sum(Swiggy)) Swiggy,round(sum(Card)) Card,round(sum(PhonePay)) PhonePay,"
				+ "round(sum(Cash)+sum(Paytm)+sum(Amazon)+sum(GooglePay)+sum(Zomato)+sum(Swiggy)+sum(Card)+sum(PhonePay)) as HoriTotal,T.firm_id,sum(Kasar) Kasar from (\r\n"
				+ "\r\n" + "select\r\n"
				+ "		ms.firm_id ,firm_name,case when payment_mode ='Cash' then amount else 0 end Cash, \r\n"
				+ "							case when payment_mode ='Paytm' then amount else 0 end Paytm, \r\n"
				+ "							case when payment_mode ='Amazon' then amount else 0 end Amazon, \r\n"
				+ "							case when payment_mode ='Google Pay' then amount else 0 end GooglePay, \r\n"
				+ "							case when payment_mode ='Phone Pay' then amount else 0 end PhonePay, \r\n"
				
				+ "							case when payment_mode ='Zomato' then amount else 0 end Zomato, \r\n"
				+ "							case when payment_mode ='Kasar' then amount else 0 end Kasar, \r\n"
				+ "							case when payment_mode ='Card' then amount else 0 end Card, \r\n"
				+ "							case when payment_mode ='Swiggy' then amount else 0 end Swiggy \r\n"
				+ "							\r\n" + "from\r\n" + "	trn_payment_register tpr\r\n"
				+ "inner join mst_firm ms on\r\n" + "	ms.firm_id = tpr.firm_id\r\n" + "where\r\n"
				+ "	payment_for in ('Collection') and date(payment_date) between ? and ?  and tpr.app_id=? and tpr.app_id= ms.app_id and tpr.activate_flag =1 \r\n"
				+ ") as T group by firm_name";
		return getListOfLinkedHashHashMap(parameters, query, con);
	}

	public List<LinkedHashMap<String, Object>> getPaymentData(HashMap<String, Object> hm, Connection con)
			throws ClassNotFoundException, SQLException, ParseException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(getDateASYYYYMMDD(hm.get("fromDate").toString()));
		parameters.add(getDateASYYYYMMDD(hm.get("toDate").toString()));
		parameters.add(hm.get("app_id"));
		String query = "select firm_name,"
				+ "round(sum(Cash)) Cash,round(sum(Paytm)) Paytm,round(sum(Amazon)) Amazon,round(sum(GooglePay)) GooglePay,"
				+ "round(sum(Zomato)) Zomato,round(sum(Swiggy)) Swiggy,round(sum(Card)) Card,round(sum(PhonePay)) PhonePay,"
				+ "round(sum(Cash)+sum(Paytm)+sum(Amazon)+sum(GooglePay)+sum(Zomato)+sum(Swiggy)+sum(Card)+sum(PhonePay)) as HoriTotal,firm_id , sum(Kasar) Kasar from (\r\n"
				+ "\r\n" + "select\r\n"
				+ "		ms.firm_id ,firm_name,case when payment_mode ='Cash' then amount else 0 end Cash, \r\n"
				+ "							case when payment_mode ='Paytm' then amount else 0 end Paytm, \r\n"
				+ "							case when payment_mode ='Amazon' then amount else 0 end Amazon, \r\n"
				+ "							case when payment_mode ='Google Pay' then amount else 0 end GooglePay, \r\n"
				+ "							case when payment_mode ='Phone Pay' then amount else 0 end PhonePay, \r\n"
				
				+ "							case when payment_mode ='Zomato' then amount else 0 end Zomato, \r\n"
				+ "							case when payment_mode ='Kasar' then amount else 0 end Kasar, \r\n"
				+ "							case when payment_mode ='Card' then amount else 0 end Card, \r\n"
				+ "							case when payment_mode ='Swiggy' then amount else 0 end Swiggy \r\n"
				+ "							\r\n" + "from\r\n" + "	trn_payment_register tpr\r\n"
				+ "inner join mst_firm ms on\r\n" + "	ms.firm_id = tpr.firm_id\r\n" + "where\r\n"
				+ "	payment_for in ('Collection','invoice') and tpr.activate_flag=1 and date(payment_date) between ? and ? and tpr.app_id=? and tpr.app_id=ms.app_id \r\n"
				+ ") as T group by firm_name";
		return getListOfLinkedHashHashMap(parameters, query, con);
	}

	

	public List<LinkedHashMap<String, Object>> getEmployeeWiseDetailsForDashboard(HashMap<String, Object> hm,
			Connection con) throws ParseException, ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(getDateASYYYYMMDD(hm.get("fromDate").toString()));
		parameters.add(getDateASYYYYMMDD(hm.get("toDate").toString()));
		parameters.add((hm.get("app_id").toString()));
		return getListOfLinkedHashHashMap(parameters, "select T.firm_id,T.updated_by,firm_name,name,\r\n" + "round(sum(Cash)) Cash,\r\n"
				+ "round(sum(Paytm)) Paytm,\r\n" + "round(sum(Amazon)) Amazon,\r\n"
				+ "round(sum(GooglePay)) GooglePay,\r\n"  
				+ "round(sum(Zomato)) Zomato,\r\n" + "round(sum(Swiggy)) Swiggy,\r\n" + "round(sum(Card)) Card,\r\n"
				+ "round(sum(PhonePay)) PhonePay,\r\n" + "round(sum(pendingAmount)) pendingAmount,"
						+ "round(sum(Cash)+sum(Paytm)+sum(Amazon)+sum(GooglePay)+sum(Zomato)+sum(Swiggy)+sum(Card)+sum(PhonePay)+sum(pendingAmount)) as HoriTotal, T.firm_id"
						+ " \r\n" + "from (\r\n"
				+ "select \r\n" + "tir.updated_by,tir.firm_id,firm_name,name,\r\n"
				+ "case when payment_mode ='Cash' then amount else 0 end Cash,  \r\n"
				+ "											case when payment_mode ='Paytm' then amount else 0 end Paytm,  \r\n"
				+ "											case when payment_mode ='Amazon' then amount else 0 end Amazon,  \r\n"
				+ "											case when payment_mode ='Google Pay' then amount else 0 end GooglePay, \r\n"
				+ "											case when payment_mode ='Phone Pay' then amount else 0 end PhonePay, \r\n"
				
				+ "											case when payment_mode ='Zomato' then amount else 0 end Zomato, \r\n"
				+ "											case when payment_mode ='Card' then amount else 0 end Card, \r\n"
				+ "											case when payment_mode ='Swiggy' then amount else 0 end Swiggy ,\r\n"
				+ "total_amount -coalesce (amount,0) pendingAmount from trn_invoice_register tir inner join tbl_user_mst tum  on tir.activate_flag=1 and tum.user_id =tir.updated_by					\r\n"
				+ "					inner join mst_firm firm1 on firm1.firm_id =tir.firm_id  \r\n"
				+ "					left outer join trn_payment_register tpr  on tpr.ref_id =tir.invoice_id  and tpr.payment_for ='Invoice' and tpr.app_id=tir.app_id \r\n"
				+ "					where date(tir.invoice_date) between ? and ? and  tir.app_id=? and tir.app_id=tum.app_id and firm1.app_id=tir.app_id  \r\n" + "					) as T\r\n"
				+ "					group by firm_name,name", con);
	}

	
	

	public List<LinkedHashMap<String, Object>> getClientDeliveryRoutine(Connection con,HashMap<String, Object> hm)
			throws ClassNotFoundException, SQLException 
	{
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("app_id"));
		String query="select * from "
				+ " Client_delivery_routine routine,mst_Client cust,mst_items item"
				+ " where cust.Client_id=routine.Client_id and item.item_id=routine.item_id and routine.activate_flag=1 and routine.app_id=? ";
		
		if(hm.get("Client_id")!=null && !hm.get("Client_id").equals(""))
		{
			parameters.add(hm.get("Client_id"));
			query+=" and routine.Client_id=?";
		}
		
		return getListOfLinkedHashHashMap(parameters, query, con);
	}

	
	
	public String deleteRoutine(long routineId, Connection conWithF) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(routineId);
		insertUpdateDuablDB("UPDATE Client_delivery_routine  SET activate_flag=0 WHERE routine_id=?", parameters, conWithF);
		return "Routine Deleted Succesfully";
	}

	
	
	public LinkedHashMap<String, String> getRoutinepDetails(long routineId, Connection con) throws SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(routineId);
		return getMap(parameters, "select * from Client_delivery_routine routine,mst_items item,mst_Client cust "
				+ " where routine.routine_id=? and item.item_id=routine.item_id and routine.Client_id=cust.Client_id", con);
	}
	
	public long addRoutine(Connection conWithF, HashMap<String, Object> hm) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("hdnSelectedClient"));
		parameters.add(hm.get("hdnselecteditem"));
		parameters.add(hm.get("txtcustomrate"));
		parameters.add(hm.get("txtitemqty"));		
		parameters.add(hm.get("deliverypreference"));
		
		parameters.add(hm.get("user_id"));
		parameters.add(hm.get("app_id"));
		return insertUpdateDuablDB("insert into Client_delivery_routine values (default,?,?,?,?,?,1,sysdate(),?,?)", parameters, conWithF);
	}

	public long updateRoutine(Connection conWithF, HashMap<String, Object> hm) throws SQLException 
	{
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("hdnSelectedClient"));
		parameters.add(hm.get("hdnselecteditem"));
		parameters.add(hm.get("txtcustomrate"));
		parameters.add(hm.get("txtitemqty"));		
		parameters.add(hm.get("deliverypreference"));		
		parameters.add(hm.get("user_id"));
		parameters.add(hm.get("app_id"));
		parameters.add(hm.get("hdnroutineid"));
		
		return insertUpdateDuablDB("update Client_delivery_routine set Client_id=?,item_id=?,"
				+ "custom_rate=?,qty=?,occurance=?,updated_by=?,app_id=?,updated_date=sysdate() where routine_id=?", parameters, conWithF);
		
	}

	public List<LinkedHashMap<String, Object>> getRoutineDetailsForThisClient(String ClientId, Connection con) throws ClassNotFoundException, SQLException 
	{
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(ClientId);
		String query="select\r\n" + 
				"	deliveryroutine.*,\r\n" + 
				"	cust.*,cat.*,\r\n" + 
				"	item.*,\r\n" + 
				"	case\r\n" + 
				"		when concat(attachment_id, file_name) is null then 'dummyImage.jpg'\r\n" + 
				"		else concat(attachment_id, file_name)\r\n" + 
				"	end as ImagePath\r\n" + 
				"from\r\n" + 
				"	Client_delivery_routine deliveryroutine inner join \r\n" + 
				"	mst_items item on deliveryroutine.item_id =item.item_id inner join \r\n" + 
				"	mst_Client cust on cust.Client_id =deliveryroutine .Client_id"
				+ " inner join mst_category cat on cat.category_id=item.parent_category_id \r\n" + 
				"left outer join tbl_attachment_mst tam on\r\n" + 
				"	item.item_id = tam.file_id\r\n" + 
				"	and tam.type = 'Image'\r\n" + 
				"where\r\n" + 
				"	deliveryroutine.Client_id =? \r\n" + 
				"	and deliveryroutine.activate_flag = 1\r\n" + 
				"	and item.item_id = deliveryroutine.item_id\r\n" + 
				"	and cust.Client_id = deliveryroutine.Client_id";
		return getListOfLinkedHashHashMap(parameters, query, con);
	}

	public List<LinkedHashMap<String, Object>> getExpenseRegister(HashMap<String, Object> outputMap, Connection con) throws ClassNotFoundException, SQLException, ParseException 
	{
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(outputMap.get("app_id"));
		parameters.add(getDateASYYYYMMDD(outputMap.get("fromDate").toString()));
		parameters.add(getDateASYYYYMMDD(outputMap.get("toDate").toString()));
		
		String query="select *,date_format(expense_date,'%d/%m/%Y') as FormattedExpenseDate from trn_expense_register where app_id=? and expense_date between ? and ? and activate_flag=1 ";
		return getListOfLinkedHashHashMap(parameters, query, con);
	}

	
	
	public String deleteExpense(long expenseId,String userId, Connection conWithF) throws Exception 
	{
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(userId);
		parameters.add(expenseId);		
		insertUpdateDuablDB("UPDATE trn_expense_register SET activate_flag=0,updated_by=?,updated_date=sysdate() WHERE expense_id=?", parameters, conWithF);
		return "Expense Deleted Succesfully";
	}
	
	public String deleteInvoicePurchase(long invoiceId,String userId, Connection conWithF) throws Exception 
	{
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(userId);
		parameters.add(invoiceId);
		insertUpdate("UPDATE trn_purchase_invoice_register SET activate_flag=0,updated_by=?,updated_date=sysdate() WHERE invoice_id=?", parameters, conWithF);
		
		parameters.clear();
		
		
		
				// update stock_status
				// reverse stock_register entry
				
				return userId;
	}
	
	public String deleteInvoiceSales(long invoiceId,String userId, Connection conWithF) throws Exception 
	{
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(userId);
		parameters.add(invoiceId);
		insertUpdate("UPDATE trn_sales_invoice_register SET activate_flag=0,updated_by=?,updated_date=sysdate() WHERE invoice_id=?", parameters, conWithF);
		
		parameters.clear();
		
		
		
				// update stock_status
				// reverse stock_register entry
				
				return userId;
	}
	public String deleteChallanOut(long invoiceId,String userId, Connection conWithF) throws Exception 
	{
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(userId);
		parameters.add(invoiceId);
		insertUpdate("UPDATE trn_challan_out SET activate_flag=0,updated_by=?,updated_date=sysdate() WHERE challan_id=?", parameters, conWithF);
		
		parameters.clear();
		
		
		
				// update stock_status
				// reverse stock_register entry
				
				return userId;
	}
	
	public String deleteChallanIn(long invoiceId,String userId, Connection conWithF) throws Exception 
	{
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(userId);
		parameters.add(invoiceId);
		insertUpdate("UPDATE trn_challan_in SET activate_flag=0,updated_by=?,updated_date=sysdate() WHERE challan_id=?", parameters, conWithF);
		
		parameters.clear();
		
		
		
				// update stock_status
				// reverse stock_register entry
				
				return userId;
	}
	
	
	

	public String deletePaymentAgainstInvoice(long invoiceId,String userId, Connection conWithF) throws Exception 
	{
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(userId);
		parameters.add(invoiceId);
		insertUpdate("UPDATE trn_payment_register SET activate_flag=0,updated_by=?,updated_date=sysdate() WHERE ref_id=? ", parameters, conWithF);
		return "Deleted Payment";
		
	}
	
	public String deleteReturnsAgainstInvoice(long invoiceId,String userId, Connection conWithF) throws Exception 
	{
		ArrayList<Object> parameters = new ArrayList<>();
		
		parameters.add(invoiceId);
		insertUpdate("delete return1 from trn_return_register return1 ,trn_invoice_register register ,trn_invoice_details  dtls  \r\n"
				+ " where register.invoice_id =? and register.invoice_id =dtls.invoice_id  and \r\n"
				+ "return1.details_id = dtls.details_id", parameters, conWithF);
		return "Deleted Payment";
		
	}
	
	
	
	

	
	
	public LinkedHashMap<String, String> getExpenseDetails(long expenseId, Connection con) throws SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(expenseId);
		return getMap(parameters, "select *,date_format(expense_date,'%d/%m/%Y') as FormattedExpenseDate from trn_expense_register where expense_id=?", con);
	}
	
	public long addExpense(Connection conWithF, String groupName) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(groupName);
		return insertUpdateDuablDB("insert into trn_expense_register values (default,?,?,?,sysdate(),?,1,?)", parameters, conWithF);
	}
	
	public String checkInEmployee(String employeeId,String checkInType,Connection conWithF) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		String getCurrentTimeWithSeconds=getDateTimeWithSecondsYYYYMMDDHHMMSS(conWithF);
		parameters.add(employeeId);
		parameters.add(checkInType);
		parameters.add(getCurrentTimeWithSeconds);
		insertUpdateDuablDB("insert into trn_checkin_register values (default,?,?,?,1)", parameters, conWithF);
		return getCurrentTimeWithSeconds;
	}

	public String checkInOutGatePass(String employeeId,String checkInType,Connection conWithF) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		String getCurrentTimeWithSeconds=getDateTimeWithSecondsYYYYMMDDHHMMSS(conWithF);
		
		String query="";

		if(checkInType.equals("O"))
		{
			query+="update trn_gate_pass set out_time=sysdate() where user_id=? order by gate_pass_id desc limit 1";	
		}
		else
		{
			query+="update trn_gate_pass set in_time=sysdate() where user_id=? order by gate_pass_id desc limit 1";	
		}
		parameters.add(employeeId);
		
		insertUpdateDuablDB(query, parameters, conWithF);
		return getCurrentTimeWithSeconds;
	}

	
	
	public boolean checkifduplicateentry(String employeeId,int seconds, Connection conWithF) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(seconds);
		parameters.add(employeeId);
		
		return getMap(parameters, "select \r\n"
				+ "case when TIMESTAMPDIFF(SECOND,max(checked_time),sysdate()) <? then true else false end isDuplicate\r\n"
				+ "from trn_checkin_register tcr  where user_id =?", conWithF).get("isDuplicate").equals("1");
	}

	public boolean isAccessBlocked(String employeeId, Connection conWithF) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		
		parameters.add(employeeId);
		
		return !getMap(parameters, "select count(1) cnt from trn_access_block_register where employee_id=? and activate_flag=1 ", conWithF).get("cnt").equals("0");
	}

	public boolean checkIfApprovedGatePassExistForUser(String employeeId, Connection conWithF) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		
		parameters.add(employeeId);
		
		return getMap(parameters, "select count(1) cnt from trn_gate_pass where user_id=? and activate_flag=1 and request_approved_by is not null and (in_time is null or out_time is null) order by gate_pass_id desc limit 1", conWithF).get("cnt").equals("0");
	}

	

	public boolean isGatePassApproved(String employeeId, Connection conWithF) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(employeeId);
		return !getMap(parameters, "select count(1) cnt from trn_gate_pass where employee_id=? and activate_flag=1 and request_approved_by is not null and (in_time is null or out_time is null) order by gate_pass_id desc limit 1", conWithF).get("cnt").equals("0");
	}

	
	public String getCheckType(String employeeId,Connection conWithF) throws Exception {
		String returnString="";
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(employeeId);
		LinkedHashMap<String, String> LastCheckInMap=getMap(parameters,
				"select check_in_type from trn_checkin_register where user_id=? and activate_flag=1 order by check_in_id desc limit 1",
				conWithF);
		if((LastCheckInMap.isEmpty()))
		{
			returnString="I";
		}
		else
		{
			String lastCheckIn=LastCheckInMap.get("check_in_type");
			if(lastCheckIn.equals("O")) 
			{
				returnString="I";
			}
			else
			{
				returnString="O";
			}
		}
		return returnString;
	}

	public String getGatePassCheckType(String employeeId,Connection conWithF) throws Exception {
		String returnString="";
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(employeeId);
		returnString=getMap(parameters,
				"select case when out_time is null then 'O' else 'I' END check_in_type from trn_gate_pass where user_id=?  order by gate_pass_id  desc limit 1",
				conWithF).get("check_in_type");
		
		return returnString;
	}
	
	
	

	
	public long addExpense(Connection con, HashMap<String, Object> hm) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(getDateASYYYYMMDD(hm.get("txtdate").toString()));
		parameters.add(hm.get("expense_name"));
		parameters.add(hm.get("amount"));
		parameters.add(hm.get("user_id"));
		parameters.add(hm.get("app_id"));
		parameters.add(hm.get("qty"));
		
		return insertUpdateDuablDB("insert into trn_expense_register values (default,?,?,?,sysdate(),?,1,?,?)", parameters, con);
	}

	public long updateExpense(Connection con, HashMap<String, Object> hm) throws ParseException, SQLException 
	{
		
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(getDateASYYYYMMDD(hm.get("txtdate").toString()));
		parameters.add(hm.get("expense_name"));
		parameters.add(hm.get("amount"));
		parameters.add(hm.get("user_id"));
		parameters.add(hm.get("app_id"));
		parameters.add(hm.get("qty"));
		
		parameters.add(hm.get("hdnExpenseId"));
		
		return insertUpdateDuablDB("update trn_expense_register set expense_date=?,expense_name=?,amount=?,updated_by=?,app_id=?,qty=? where expense_id=?", parameters, con);
		
	}

	public List<LinkedHashMap<String, Object>> getDistinctExpenseList(Connection con, String appId) throws SQLException, ClassNotFoundException 
	{
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(appId);
		return getListOfLinkedHashHashMap(parameters, "select distinct(expense_name) expense_name from trn_expense_register where app_id=?", con);
	}

	public long getTentativeSequenceNo(String appId,String tableName, Connection con) throws NumberFormatException, SQLException 
	{
		long generatedPK=0;
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(appId);
		parameters.add(tableName);
		HashMap<String, String> hm= getMap(parameters, "select current_seq_no+1 as  current_seq_no from seq_master where app_id=? and sequence_name=?", con);
		if(hm.get("current_seq_no")==null)
		{
			parameters.clear();
			parameters.add(tableName);
			parameters.add(appId);			
			insertUpdateDuablDB("insert into seq_master values (default,?,0,?)", parameters, con);
			generatedPK=1;
		}
		else
		{
			generatedPK=Long.valueOf(hm.get("current_seq_no").toString());
		}
		return generatedPK;
	}

	public List<String> getDistinctCityNames(String appId, Connection con) throws ClassNotFoundException, SQLException 
	{
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(appId);		
		return getListOfString(parameters,"select distinct(City) from mst_Client where app_id=?", con);
	}

	public String saveTableConfig(int noOfTables,int firmId, Connection con) throws SQLException 
	{
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(firmId);
		insertUpdate("delete from mst_tables where firm_id=?", parameters, con);
		for(int x=1;x<=noOfTables;x++)
		{
			parameters.clear();
			parameters.add(firmId);
			parameters.add(x);
			insertUpdate("insert into mst_tables values (default,?,?,null)", parameters, con);
		}
			
		return "Tables Added Succesfully";
	}

	public List<LinkedHashMap<String, Object>> getOrderDetailsForTable(long tableId, Connection conWithF) throws ClassNotFoundException, SQLException 
	{
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(tableId);
		return getListOfLinkedHashHashMap(parameters, "select table1.order_id orderId,table1.table_no  tableNo,tor.*,tod.*,mi.* from\r\n"
				+ "mst_tables table1 left outer join  trn_order_register tor  on tor.order_id=table1.order_id \r\n"
				+ "inner join trn_order_details tod   on tor.order_id =tod.order_id \r\n"
				+ "inner join mst_items mi  on mi.item_id =tod.item_id \r\n"
				+ "where table1.table_id=?", conWithF);
	}

	public long saveOrder(HashMap<String, Object> hm, Connection con) throws SQLException 
	{
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("table_id"));
		return insertUpdateDuablDB( "insert into trn_order_register values (default,?,sysdate(),null)", parameters,con);
	}

	public String saveOrderDetails(HashMap<String, Object> hm, Connection con) throws SQLException 
	{
		ArrayList<Object> parameters = new ArrayList<>();
		
		
		List<HashMap<String, Object>> itemListRequired =(List<HashMap<String, Object>>) hm.get("itemDetails");
		for(HashMap<String, Object> itemdetail : itemListRequired)
		{
			parameters.clear();
			parameters.add(hm.get("order_id"));
			parameters.add(itemdetail.get("item_id"));
			parameters.add(itemdetail.get("qty"));
			parameters.add("O");
			parameters.add(itemdetail.get("remarks"));
			
			insertUpdateDuablDB( "insert into trn_order_details values (default,?,?,?,?,sysdate(),null,null,?)", parameters,con);
		}
		return "Save Succesfully";
		
	}


	
	public List<LinkedHashMap<String, Object>> getPendingOrders(String appId,int firmId, Connection conWithF) throws ClassNotFoundException, SQLException 
	{
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(appId);
		parameters.add(firmId);
		return getListOfLinkedHashHashMap(parameters, "select * from mst_firm ms ,mst_tables mt ,trn_order_register tor ,trn_order_details tod,mst_items item \r\n"
				+ "  where ms.app_id =? and ms.firm_id =? and ms.firm_id =mt.firm_id and tor.order_id =mt.order_id and tod.order_id =tor.order_id   and item.item_id=tod.item_id and tod.status='O' order by ordered_time asc", conWithF);
	}

	
	public String markAsServed(long itemId, Connection conWithF) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(itemId);
		insertUpdateDuablDB(
				"UPDATE trn_order_details  SET status='S',served_time=sysdate() WHERE order_details_id=?",
				parameters, conWithF);
		return "Served Succesfully";
	}
	
	public String markAllAsServed(HashMap<String,Object> hm, Connection conWithF) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("table_id"));
		insertUpdateDuablDB(
				"update \r\n"
				+ "mst_tables mt,\r\n"
				+ "trn_order_details tod\r\n"
				+ "set status='S',served_time=sysdate()\r\n"
				+ "where mt.table_id =? and tod.order_id =mt.order_id  and status='O'",
				parameters, conWithF);
		return "Served Succesfully";
	}
	

	public String cancelOrderDetail(long orderDetailId, Connection con) throws SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(orderDetailId);
		insertUpdateDuablDB(
				"UPDATE trn_order_details  SET status='C',cancelled_time=sysdate() WHERE order_details_id=?",
				parameters, con);
		return "Cancelled Succesfully";
	}

	public void updateTableWithOrderId(HashMap<String, Object> hm, Connection con) throws SQLException 
	{
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("order_id"));
		parameters.add(hm.get("table_id"));
		
		insertUpdateDuablDB(
				"update mst_tables set order_id=? where table_id=?",
				parameters, con);
		
	}
	
	public void removeOrderFromTable(HashMap<String, Object> hm, Connection con) throws SQLException 
	{
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("table_id"));
		
		
		insertUpdateDuablDB(
				"update mst_tables set order_id=null where table_id=?",
				parameters, con);
		
	}

	public List<LinkedHashMap<String, Object>> getCompositeItemDetails(long itemId, Connection con) throws ClassNotFoundException, SQLException 
	{
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(itemId);		
		return getListOfLinkedHashHashMap(parameters, "select * from rlt_composite_item_mpg rcim ,mst_items item where item.item_id=rcim.child_item_id and rcim.item_id =? ", con);
	}

	
	
	public long saveCompositeItem(HashMap<String, Object> itemDetails, Connection con)
			throws ClassNotFoundException, SQLException 
	{
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(itemDetails.get("parentItemId"));
		parameters.add(itemDetails.get("item_id"));
		parameters.add(itemDetails.get("qty"));
		String insertQuery = "insert into rlt_composite_item_mpg values (default,?,?,?)";
		return insertUpdateDuablDB(insertQuery, parameters, con);
	}

	public long deleteCompositeItem(HashMap<String, Object> hm, Connection con) throws SQLException 
	{
		ArrayList<Object> parameters = new ArrayList<>();		
		parameters.add(hm.get("parentItemId"));		
		String insertQuery = "delete from rlt_composite_item_mpg where item_id=?";
		return insertUpdateDuablDB(insertQuery, parameters, con);
	}

	
	public long saveBooking(HashMap<String, Object> itemDetails, Connection con)
			throws ClassNotFoundException, SQLException, ParseException 
	{
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(itemDetails.get("ClientId"));
		parameters.add(getDateASYYYYMMDDHHMM(itemDetails.get("fromDateTime").toString()));
		parameters.add(getDateASYYYYMMDDHHMM(itemDetails.get("toDateTime").toString()));		
		parameters.add(itemDetails.get("prefferedEmployee"));
		parameters.add(itemDetails.get("app_id"));
		parameters.add(itemDetails.get("user_id"));
		parameters.add(itemDetails.get("remarks"));
		
		String insertQuery = "insert into trn_booking_register values (default,?,?,?,?,?,?,sysdate(),1,'O',?)";
		return insertUpdateDuablDB(insertQuery, parameters, con);
	}


	
	public long saveBookingItems(HashMap<String, Object> itemDetails, Connection con)
			throws ClassNotFoundException, SQLException 
	{
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(itemDetails.get("bookingId"));
		parameters.add(itemDetails.get("item_id"));
		parameters.add(itemDetails.get("qty"));
		String insertQuery = "insert into booking_item_mpg values (default,?,?,?)";
		return insertUpdateDuablDB(insertQuery, parameters, con);
	}

	
	
	public String markBookingAsServed(long bookingId, Connection conWithF) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(bookingId);
		insertUpdateDuablDB(
				"UPDATE trn_booking_register  SET status='S',updated_date=sysdate() WHERE booking_id=?",
				parameters, conWithF);
		return "Served Succesfully";
	}


	
	
	public boolean checkIfBookingAlreadyExist(HashMap<String, Object> hm, Connection con)
			throws ClassNotFoundException, SQLException, ParseException 
	{
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(getDateASYYYYMMDDHHMM(hm.get("fromDateTime").toString()));
		parameters.add(getDateASYYYYMMDDHHMM(hm.get("fromDateTime").toString()));
		parameters.add(getDateASYYYYMMDDHHMM(hm.get("toDateTime").toString()));
		parameters.add(getDateASYYYYMMDDHHMM(hm.get("toDateTime").toString()));
		parameters.add(hm.get("prefferedEmployee"));
		return !getMap(parameters,"select\r\n"
				+ "	count(1) as existingBookings\r\n"
				+ "from\r\n"
				+ "	trn_booking_register tbr\r\n"
				+ "where\r\n"
				+ "	(\r\n"
				+ "		((? between from_date and to_date) and (to_date !=?))\r\n"
				+ "		or\r\n"
				+ "		((? between from_date and to_date)  and from_date !=?)\r\n"
				+ "	) and preffered_employee =? and tbr.activate_flag =1", con).get("existingBookings").equals("0");
		
		
	}
	
	
	public List<LinkedHashMap<String, Object>> getCompositeItemChildsAndQuantity(long itemId,Connection con) throws ClassNotFoundException, SQLException 
	{
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(itemId);
		return getListOfLinkedHashHashMap(parameters,"select * from rlt_composite_item_mpg where item_id=?",con);
	}
	
	public List<LinkedHashMap<String, Object>> getAuditList(Connection con) throws ClassNotFoundException, SQLException 
	{
		ArrayList<Object> parameters = new ArrayList<>();
		
		return getListOfLinkedHashHashMap(parameters,"select * from frm_audit_trail order by accessed_time desc limit 400",con);
	}




	
	public List<LinkedHashMap<String, Object>> getSliderImages(long appId,Connection con) throws ClassNotFoundException, SQLException
	{
		ArrayList<Object> parameters=new ArrayList<>();
		parameters.add(appId);
		return getListOfLinkedHashHashMap(parameters, "SELECT attachment_id,concat(attachment_id,file_name) as file_name FROM tbl_attachment_mst WHERE activate_flag=1 AND TYPE='Slider' and file_id=?", con);
	}
	
	
	public List<LinkedHashMap<String, Object>> getItemNamesForSearchAutocomplete(long appId,Connection con) throws ClassNotFoundException, SQLException
	{		
		ArrayList<Object> parameters=new ArrayList<>();
		parameters.add(appId);
		return getListOfLinkedHashHashMap(parameters, "SELECT item_id,item_name FROM mst_items WHERE activate_flag=1 and app_id=?", con);
	}
	
	
	public List<LinkedHashMap<String, Object>> CategoryNameWithImage(long appId,Connection con) throws ClassNotFoundException, SQLException
	{	
		
		
		ArrayList<Object> parameters=new ArrayList<>();
		parameters.add(appId);
		return getListOfLinkedHashHashMap(parameters, "select \r\n"
				+ "		category_id,category_name, \r\n"
				+ "		case when concat(tam.attachment_id,file_name) is null then \r\n"
				+ "			(select concat(tam.attachment_id,file_name) from mst_items as mi,tbl_attachment_mst tam where parent_category_id=mc.category_id and tam.file_id=mi.item_id and tam.type='Image' order by rand() limit 1) \r\n"
				+ "	else concat(tam.attachment_id,file_name) end file_name \r\n"
				+ "	from \r\n"
				+ "		mst_category as mc \r\n"
				+ "		left outer join tbl_attachment_mst tam on mc.category_id=tam.file_id and tam.type='category'\r\n"
				+ "	where\r\n"
				+ "		mc.activate_Flag=1 and  app_id =?", con);
		
		
		
		
		
	}
	
	public List<LinkedHashMap<String, Object>> getProductsByCategoryId(long appId,long categoryId,Connection con) throws ClassNotFoundException, SQLException
	{
		
		ArrayList<Object> parameters=new ArrayList<>();
		parameters.add(appId);
		parameters.add(categoryId);
		return getListOfLinkedHashHashMap(parameters, "SELECT  " +
				"item.item_id,item.item_name,item.`price`,cat.`category_name`, " +				
				" case when concat(attachment_id, file_name) is null then 'dummyImage.jpg' else concat(attachment_id, file_name) end as path " +
				"FROM  " +
				"mst_category cat, mst_items item   " +
				" left outer join tbl_attachment_mst tam on tam.file_id=item.item_id and tam.type='Image'   " +
				"WHERE cat.`category_id`=item.`parent_category_id` " +
				"AND item.`activate_flag`=1 AND cat.`activate_flag`=1  and item.app_id=? and cat.app_id=item.app_id  " +
				"AND cat.`category_id`=?", con);
		
		
		
		
		
		
	}
	
	public List<LinkedHashMap<String, Object>> getItemsBySearchString(long appId, String SearchString,Connection con) throws ClassNotFoundException, SQLException
	{	
		
		
		ArrayList<Object> parameters=new ArrayList<>();
		parameters.add(SearchString);
		parameters.add(appId);
		
		return getListOfLinkedHashHashMap(parameters, "select\r\n"
				+ "	item.item_id,\r\n"
				+ "	item.item_name,\r\n"
				+ "	item.price,\r\n"
				+ "	cat.category_name,\r\n"				
				+ " case when concat(attachment_id, file_name) is null then 'dummyImage.jpg' else concat(attachment_id, file_name) end as path "
				+ "	\r\n"
				+ "from\r\n"
				+ "	mst_category cat, mst_items item left outer join  tbl_attachment_mst attachment on 	attachment.file_id = item.item_id	 and attachment.`activate_flag` = 1\r\n"
				+ "where\r\n"
				+ "	item.item_name like ?	\r\n"
				+ "	and item.`activate_flag` = 1\r\n"
				+ "	and item.`parent_category_id` = cat.`category_id`	\r\n"
				+ "	and cat.activate_flag = 1\r\n"
				+ "	and item.app_id = ?\r\n"
				+ "	and cat.app_id = item.app_id", con);
		
		
		
	}
	
	public LinkedHashMap<String, String> getAboutUsDetails(long appId, Connection con) throws SQLException 
	{
		ArrayList<Object> parameters=new ArrayList<>();
		parameters.add(appId);
		return getMap(parameters, "select apm.*,concat(attach.attachment_id,attach.file_name) as fileName from mst_app apm left outer join tbl_attachment_mst attach on attach.file_id=apm.app_id  and attach.type='aboutus' where apm.app_id=? ", con);
	}
	
	

	public HashMap<String, Object> getCashbackForDeliveryTypes(Connection con) throws ClassNotFoundException, SQLException
	{	
		HashMap<String, Object> hm=new HashMap<>();
		
		ResultSet rs=null;
		try
		{			
			
			Statement stmt=con.createStatement();  
			rs=stmt.executeQuery("SELECT TYPE AS t1,PERCENTAGE AS p1 FROM mst_cashback ");		
			while(rs.next())
				{		
					
					hm.put(rs.getString(1),rs.getInt(2));				
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
		}
		
			 return hm;
	}
	
	public int getTaxPercentage(Connection con) throws SQLException
	{
		
		PreparedStatement stmnt=null;
		ResultSet rs=null;
		int percentage=0;
		try
		{		
			
			String query="select gst_tax from mst_app_config";
			stmnt=con.prepareStatement(query);					
			
			rs = stmnt.executeQuery( );
			int k=0;			
			while (rs.next()) 
			{		
				percentage=rs.getInt(1);
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
		return percentage;
	}
	
	public Long getWalletAmountForThisNumber(String number,Connection con) throws ClassNotFoundException, SQLException
	{	
		
		
		
		PreparedStatement stmnt=null;
		ResultSet rs=null;
		Long amount = 0L;
		
		
		
		
		try
		{		
			
			stmnt=con.prepareStatement("SELECT SUM(cashback_amount) FROM " +
					"trn_order_register_fromMobileApp order1, " +
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
	
	
	public boolean  isRestaurantOpen(String appId,Connection con) 
	{		
		
		boolean is_restaurant_open=false;
		try{											

			
				
		PreparedStatement stmnt=con.prepareStatement("select is_restaurant_open from mst_app_config where app_id=?");
		stmnt.setString(1, appId);			
		ResultSet rs = stmnt.executeQuery( );
		while (rs.next()) 
		{	
			
			if(rs.getString(1).equals("1"))
			{
				is_restaurant_open=true;
			}
						
		}	
		stmnt.close();
		rs.close();
		
		
		}	
		catch(Exception e)
		{
			writeErrorToDB(e);
		}
	
		return is_restaurant_open;
	}
	
	
	public String placeorder(List<HashMap<String, String>> cartdataList,long appId,String name,String number,String address,String ordertype,double totalAmount,String specialCookingInstruction,String uniqueDeviceToken,Connection con) throws ClassNotFoundException, SQLException
	{			
		
		PreparedStatement preparedStatement=null;
		
		long orderId,cashBackId;		
		String message="";
		try
		 {
			
			con.setAutoCommit(false);
			
			long cashbackPercentage=new Long(getCashbackForDeliveryTypes(con).get(ordertype).toString());
			//long walletAmount=getWalletAmountForThisNumber(number,con);
			long walletAmount=0;
			double amounttopay=totalAmount;
			long totalTaxAmount=Math.round((totalAmount-(totalAmount*cashbackPercentage/100))*getTaxPercentage(con)/100);
			amounttopay+=totalTaxAmount;
			
			
			
			long cashbackamountused=0;
			
			if(amounttopay>walletAmount)
			{
				amounttopay=(long) (amounttopay-walletAmount);
				cashbackamountused=walletAmount;
			}
			if(walletAmount>amounttopay)
			{
				cashbackamountused=(long) amounttopay;
			}
			
			
			
			
			
			
			
			String insertTableSQL = "insert into trn_order_register_frommobileapp values (default,?,?,?,sysdate(),1,null,?,?,?,?,null,1,?,?,?,?)";
			//String insertTableSQL = "insert into trn_order_register values (default,:name,:number,:address,:pincode,sysdate(),1,null,:orderType,:amount,:previouscashbackamountused,:amounttopay,null,:ClientId,:cashbackId,curr_status);";			

			preparedStatement = con.prepareStatement(insertTableSQL,Statement.RETURN_GENERATED_KEYS);			
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, number);			
			preparedStatement.setString(3, address);
			preparedStatement.setString(4, ordertype);
			preparedStatement.setDouble(5, totalAmount);
			preparedStatement.setLong(6, cashbackamountused);  
			
			preparedStatement.setString(8, specialCookingInstruction);
			
			
			preparedStatement.setDouble(7, amounttopay);			
			preparedStatement.setLong(9, totalTaxAmount);
			preparedStatement.setString(10, uniqueDeviceToken);
			preparedStatement.setLong(11, appId);
			
			
			
			
		
						
			
			
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();
			rs.next();
			orderId= rs.getLong(1);
			if (preparedStatement != null) 
			{
				preparedStatement.close();
			}
			
			
			if(walletAmount>0 && walletAmount<=totalAmount)
			{
				walletAmount=walletAmount*-1;
			}
			if(walletAmount>totalAmount)
			{
				walletAmount=(long) (totalAmount*-1);
			}
			
			if(walletAmount!=0)
			{			
				insertTableSQL = "insert into trn_cashback_register values (default,?,'Redeemed',0,?,sysdate())";
				preparedStatement = con.prepareStatement(insertTableSQL,Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setLong(1, orderId);
				preparedStatement.setLong(2, walletAmount);
				preparedStatement.executeUpdate();
			}
				
			
			
			
			
			
			
			
			
			
			
			
			
			for(HashMap<String, String> tempHm:cartdataList)
			{
			
				insertTableSQL = "INSERT INTO trn_suborder_register VALUES (DEFAULT,?,?,?,?,?,null,SYSDATE())";			
				preparedStatement = con.prepareStatement(insertTableSQL);
				preparedStatement.setDouble(1, orderId);
				preparedStatement.setString(2, tempHm.get("item_id").toString());
				preparedStatement.setString(3, tempHm.get("item_name").toString());
				preparedStatement.setString(3, tempHm.get("item_name").toString());
				preparedStatement.setString(4, tempHm.get("price").toString());
				preparedStatement.setString(5, tempHm.get("item_count").toString());
				/*preparedStatement.setString(6, tempHm.get("remarks").toString());*/
				preparedStatement.executeUpdate();				
				if (preparedStatement != null) 
				{
					preparedStatement.close();
				}		
			}
			message="Order Placed Succesfully";
			con.commit();
		}
		
		 catch(Exception e)
		 {
			 writeErrorToDB(e);
			 message="something went wrong";					 
			 throw e;
			 
		 }	
		finally
			{
				
				if(!preparedStatement.isClosed())
					{preparedStatement.close();}		
			}
	
		return message;
	
	}

	public List<LinkedHashMap<String, Object>> getMobileAppOrders(String appId, Connection con) throws SQLException, ClassNotFoundException 
	{
		ArrayList<Object> parameters=new ArrayList<>();
		parameters.add(appId);
		return getListOfLinkedHashHashMap(parameters, "select * from trn_order_register_frommobileapp where app_id=? ", con);
	}
	
	public LinkedHashMap<String, String> getInvoiceFormatName(HashMap<String, Object> hm, Connection con)
			throws ClassNotFoundException, SQLException 
	{
		ArrayList<Object> parameters = new ArrayList<>();
		
		parameters.add(hm.get("user_id"));

		return getMap(parameters, "select * from tbl_user_mst tum , invoice_formats format where tum.user_id=? and tum.invoice_format=format.format_id ", con);

	}
	
	
	public List<LinkedHashMap<String, Object>> getItemMasterForGenerateInvoice(HashMap<String, Object> hm, Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		String query = "select item.*,cat.*,"
				+ " case when concat(attachment_id, file_name) is null then 'dummyImage.jpg' else concat(attachment_id, file_name) end as ImagePath, stock.qty_available "
				+ "from mst_items item inner join mst_category cat on cat.category_id=item.parent_category_id left outer join "
				+ " tbl_attachment_mst tam on tam.file_id=item.item_id and tam.type='Image' left outer join stock_status stock on  stock.item_id=item.item_id and stock.app_id=item.app_id and stock.firm_id=? "
				+ " where item.activate_flag=1 and item.app_id=? and cat.app_id=item.app_id ";
		
		parameters.add(hm.get("firm_id"));
		parameters.add(hm.get("app_id"));
		
		
		query += " group by item.item_id";
		query += " order by item_name";
		return getListOfLinkedHashHashMap(parameters, query, con);
	}
	
	
	public List<LinkedHashMap<String, Object>> getItemMasterForSales(HashMap<String, Object> hm, Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		String query = "select * from mst_items where app_id=?";
		parameters.add(hm.get("app_id"));
		query += " order by item_name";
		return getListOfLinkedHashHashMap(parameters, query, con);
	}

	public HashMap<String, Object> saveJournal(HashMap<String, Object> hm, Connection conWithF) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		long invoiceNo=getPkForThistable("trn_journal_entry",Long.valueOf(hm.get("app_id").toString()),conWithF);
		
		
		parameters.add(invoiceNo);		
		parameters.add(getDateASYYYYMMDD(hm.get("journal_date").toString()));
		parameters.add(hm.get("debit"));
		parameters.add(hm.get("credit"));
		parameters.add(hm.get("remarks"));
		parameters.add(hm.get("totalamount"));
		parameters.add(hm.get("user_id"));
		parameters.add(hm.get("firm_id"));
		parameters.add(hm.get("app_id"));
		parameters.add(hm.get("gross_amount"));
		parameters.add(hm.get("gst_percentage"));	
		parameters.add(hm.get("gst_amount"));
		parameters.add(hm.get("vendorinvoiceno"));
		
		
		long journalId=insertUpdateDuablDB(
				"insert into trn_journal_entry values (default,?,?,?,?,?,?,sysdate(),?,1,?,?,?,?,?,?)", parameters,
				conWithF);
		hm.put("journal_id", journalId);

		List<HashMap<String, Object>> itemDetailsList = (List<HashMap<String, Object>>) hm.get("itemDetails");
		for (HashMap<String, Object> item : itemDetailsList) 
		{
			parameters = new ArrayList<>();
			parameters.add(journalId);
			parameters.add(item.get("job_sheet_no"));
			parameters.add(item.get("amount"));						
			insertUpdateDuablDB("insert into trn_journal_details values (default,?,?,?)", parameters,
					conWithF);
		}	
		hm.put("journal_no", invoiceNo);
		return hm;
	}
	
	
	public LinkedHashMap<String, Object> getStockTransferDetails(String invoiceId, Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(invoiceId);
		LinkedHashMap<String, Object> itemDetailsMap = new LinkedHashMap<>();
		itemDetailsMap = getMapReturnObject(parameters, "select\r\n"
				+ "*,\r\n"
				+ "mf.firm_name as sourceFirm,mwh.ware_house_name  as sourceWareHouseName,\r\n"
				+ "mf1.firm_name as destinationFirm,mwh1.ware_house_name  as destinationWareHouseName,"
				+ "date_format(transaction_date,'%d/%m/%Y %H:%i') as FormattedTransactionDate,"
				+ "mf.gst_no sourceGSTNO,mf.city sourceCity,mf.firm_email sourceEmail,mf.pincode sourcePincode,mf.address_line_1 sourceAddress1,mf.address_line_2 sourceAddress2, \r\n"
				+ "mf1.gst_no destinationGSTNO,mf1.city destinationCity,mf1.firm_email destinationEmail,mf1.address_line_1 destinationAddress1,mf1.address_line_2 destinationAddress2,mf1.pincode destinationPincode \r\n"
				+ "from\r\n"
				+ "	stock_modification_master smm \r\n"
				+ "	inner join mst_firm mf on smm.firm_id =mf.firm_id \r\n"
				+ "	inner join mst_ware_house mwh  on smm.ware_house_id =mwh.ware_house_id 	\r\n"
				+ "		inner join mst_firm mf1 on smm.destination_firm =mf1.firm_id \r\n"
				+ "	inner join mst_ware_house mwh1  on smm.destination_ware_house_id =mwh1.ware_house_id\r\n"
				+ "where\r\n"
				+ "	stock_modification_id = ?", con);

		parameters = new ArrayList<>();
		parameters.add(invoiceId);

		
		itemDetailsMap.put("listOfItems",
				getListOfLinkedHashHashMap(parameters, " select * from  \r\n"
						+ "stock_modification_master smm \r\n"
						+ "inner join stock_modification_transferstock smt on smm.stock_modification_id=smt.stock_modification_id "
						+ "inner join mst_items item1 on item1.item_id=smt.item_id \r\n"
						+ "where smm.stock_modification_id =? ", con));
		return itemDetailsMap;

	}
	
	
	public LinkedHashMap<String, Object> getAddStockDetails(String invoiceId, Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(invoiceId);
		LinkedHashMap<String, Object> itemDetailsMap = new LinkedHashMap<>();
		itemDetailsMap = getMapReturnObject(parameters, "select\r\n"
				+ "*,\r\n"
				+ "mf.firm_name as sourceFirm,mwh.ware_house_name  as sourceWareHouseName,\r\n"				
				+ "date_format(transaction_date,'%d/%m/%Y %H:%i') as FormattedTransactionDate,"
				+ "mf.gst_no sourceGSTNO,mf.city sourceCity,mf.firm_email sourceEmail,mf.pincode sourcePincode,mf.address_line_1 sourceAddress1,mf.address_line_2 sourceAddress2 \r\n"				
				+ "from\r\n"
				+ "	stock_modification_master smm \r\n"
				+ "	inner join mst_firm mf on smm.firm_id =mf.firm_id \r\n"
				+ "	inner join mst_ware_house mwh  on smm.ware_house_id =mwh.ware_house_id 	\r\n"				
				+ " where \r\n"
				+ "	stock_modification_id = ?", con);

		parameters = new ArrayList<>();
		parameters.add(invoiceId);

		
		itemDetailsMap.put("listOfItems",
				getListOfLinkedHashHashMap(parameters, " select * from  \r\n"
						+ "stock_modification_master smm \r\n"
						+ "inner join stock_modification_addremove smt on smm.stock_modification_id=smt.stock_modification_id "
						+ "inner join mst_items item1 on item1.item_id=smt.item_id \r\n"
						+ "where smm.stock_modification_id =? ", con));
		return itemDetailsMap;

	}

	public LinkedHashMap<String, Object> getPaymentDetails(String invoiceId, Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(invoiceId);
		LinkedHashMap<String, Object> itemDetailsMap = new LinkedHashMap<>();
		itemDetailsMap = getMapReturnObject(parameters, "select * from trn_payment_register tpr left outer join mst_Client cust on tpr.Client_id=cust.Client_id where payment_id=? ", con);

		parameters = new ArrayList<>();
		parameters.add(invoiceId);

		
		itemDetailsMap.put("listOfItems",
				getListOfLinkedHashHashMap(parameters, "select * from trn_payment_details where payment_id =? ", con));
		return itemDetailsMap;

	}
	
	
	public List<LinkedHashMap<String, Object>> getPurchaseMinusSalesItems(HashMap<String, Object> hm, Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("firm_id"));
		String query = "select * from (\r\n"
				+ " select\r\n"
				+ "	tpir.invoice_no,tpir.vendor_invoice_no, items.item_name ,tpid.qty purchasedqty,sum(coalesce(tsid.qty,0)*tsir.activate_flag) soldqty, \r\n"
				+ "	tpid.qty -sum(coalesce(tsid.qty,0)*COALESCE (tsir.activate_flag,0))  as availableqty ,tpid.details_id,tsir.activate_flag ,tpid.invoice_id,tpid.rate PurchaseRate,\r\n"
				+ "	tpid.details_id as PurchaseDetailsId,items.item_id,items.gst,items.price,items.size,items.color \r\n"
				+ "from\r\n"
				+ "	trn_purchase_invoice_register tpir\r\n"
				+ "inner join trn_purchase_invoice_details tpid on\r\n"
				+ "	tpir.invoice_id = tpid.invoice_id left outer join trn_sales_invoice_details tsid on tsid.purchase_details_id =tpid.details_id\r\n"
				+ "	left join trn_sales_invoice_register tsir on tsir.invoice_id =tsid.invoice_id\r\n"
				+ "	left outer join mst_items items on items.item_id =tpid.item_id 	  \r\n"
				+ "	where tpir.activate_flag =1 and tpir.firm_id=? \r\n"
				+ "	 \r\n"				
				+ "	group by tpid.details_id \r\n"
				+ "	) as T where availableqty !=0 \r\n"
				+ "order by item_name ";
		
		return getListOfLinkedHashHashMap(parameters, query, con);
	}
	
	
	
	public BigDecimal getPurchaseAccounts(HashMap<String, Object> hm, Connection con)
			throws ClassNotFoundException, SQLException, ParseException {
		ArrayList<Object> parameters = new ArrayList<>();
		
		
		parameters.add(getDateASYYYYMMDD(hm.get("fromDate").toString()));
		parameters.add(getDateASYYYYMMDD(hm.get("toDate").toString()));
		
		

		String query = "select sum(gross_amount+freight_amount) purchaseAmounts from "
				+ "trn_purchase_invoice_register tpir,mst_firm firm,sbu_master sbumast"
				+ " where invoice_date between ? and ? and tpir.activate_flag=1 and tpir.firm_id=firm.firm_id and sbumast.activate_flag=1 and firm.parent_sbu_id=sbumast.sbu_id firmCondition sbuCondition ";
		
		
		if (hm.get("firmId") != null && !hm.get("firmId").equals("") && !hm.get("firmId").equals("-1")) {
			query=query.replaceFirst("firmCondition", " and firm.firm_id=? ") ;
			parameters.add(hm.get("firmId"));
		}
		else
		{
			query=query.replaceFirst("firmCondition", " ") ;
		}
		
		if (hm.get("sbuId") != null && !hm.get("sbuId").equals("") && !hm.get("sbuId").equals("-1")) {
			query=query.replaceFirst("sbuCondition", " and sbumast.sbu_id=? ") ;
			parameters.add(hm.get("sbuId"));
		}
		else
		{
			query=query.replaceFirst("sbuCondition", " ") ;
		}
		
		LinkedHashMap<String, String> purchaseAccountsMap=getMap(parameters, query, con);
		BigDecimal purchaseAccounts=purchaseAccountsMap.get("purchaseAmounts")==null?new BigDecimal(0):new BigDecimal(purchaseAccountsMap.get("purchaseAmounts"));
		return purchaseAccounts; 
		 
		

	}
	
	

	
	public BigDecimal getPurchaseAmount(HashMap<String, Object> hm, Connection con)
			throws ClassNotFoundException, SQLException, ParseException {
		ArrayList<Object> parameters = new ArrayList<>();
		
		
		
		
		 
		 
		parameters.add(getDateASYYYYMMDD(hm.get("fromDate").toString()));
		parameters.add(getDateASYYYYMMDD(hm.get("toDate").toString()));
		
		
		
		
		String query = "select sum(gross_amount) purchaseAmounts from trn_purchase_invoice_register tpir,mst_firm firm,sbu_master sbumast "
				+ " where tpir.activate_flag=1 and  invoice_date between ? and ? and tpir.firm_id=firm.firm_id and sbumast.activate_flag=1 and sbumast.sbu_id=firm.parent_sbu_id ";
		
		
		if (hm.get("firmId") != null && !hm.get("firmId").equals("") && !hm.get("firmId").equals("-1")) {
			query+=" and firm.firm_id=? " ;
			parameters.add(hm.get("firmId"));
		}
		
		if (hm.get("sbuId") != null && !hm.get("sbuId").equals("") && !hm.get("sbuId").equals("-1")) {
			query+=" and sbumast.sbu_id=? " ;
			parameters.add(hm.get("sbuId"));
		}
		
		
		LinkedHashMap<String, String> purchaseAccountsMap=getMap(parameters, query, con);
		BigDecimal purchaseAccounts=purchaseAccountsMap.get("purchaseAmounts")==null?new BigDecimal(0):new BigDecimal(purchaseAccountsMap.get("purchaseAmounts"));
		return purchaseAccounts; 
		 
		

	}
	
	public BigDecimal getOpeningSalesAmount(HashMap<String, Object> hm, Connection con)
			throws ClassNotFoundException, SQLException, ParseException {
		ArrayList<Object> parameters = new ArrayList<>();
		
		
		parameters.add(getDateASYYYYMMDD(hm.get("fromDate").toString()));
		
		String query = "select sum(tpid.rate) salesAmount from trn_sales_invoice_register tsir,trn_sales_invoice_details tsid, trn_purchase_invoice_details tpid,mst_firm firm,sbu_master sbumast "
				+ "  where  tsir.activate_flag=1 and invoice_date between '1992-01-23' and ? and tpid.details_id=tsid.purchase_details_id and tsid.invoice_id=tsir.invoice_id and sbumast.activate_flag=1 and firm.parent_sbu_id=sbumast.sbu_id ";
		
		
		if (hm.get("firmId") != null && !hm.get("firmId").equals("") && !hm.get("firmId").equals("-1")) {
			query+=" and firm.firm_id=? " ;
			parameters.add(hm.get("firmId"));
		}
		
		if (hm.get("sbuId") != null && !hm.get("sbuId").equals("") && !hm.get("sbuId").equals("-1")) {
			query+=" and sbumast.sbu_id=? " ;
			parameters.add(hm.get("sbuId"));
		}
		
		
		LinkedHashMap<String, String> purchaseAccountsMap=getMap(parameters, query, con);
		BigDecimal purchaseAccounts=purchaseAccountsMap.get("salesAmount")==null?new BigDecimal(0):new BigDecimal(purchaseAccountsMap.get("salesAmount"));
		return purchaseAccounts; 
		 
		

	}
	
	public BigDecimal getSoldPurchases(HashMap<String, Object> hm, Connection con)
			throws ClassNotFoundException, SQLException, ParseException {
		ArrayList<Object> parameters = new ArrayList<>();
		
		
		parameters.add(getDateASYYYYMMDD(hm.get("fromDate").toString()));
		parameters.add(getDateASYYYYMMDD(hm.get("toDate").toString()));
		
		String query = "select sum(tpid.rate*(tsid.qty)) as soldPurchases from trn_purchase_invoice_register tpir,trn_purchase_invoice_details tpid,"
				+ " trn_sales_invoice_details tsid,trn_sales_invoice_register tsir,"
				+ "mst_firm firm,sbu_master sbumast  where tsir.invoice_date between ? and ? and tpir.activate_flag=1 "
				+ "and tpid.invoice_id=tpir.invoice_id and tsid.purchase_details_id=tpid.details_id and tsir.firm_id=firm.firm_id and tsir.invoice_id=tsid.invoice_id and tsir.activate_flag=1 and sbumast.activate_flag=1 and firm.parent_sbu_id=sbumast.sbu_id ";
		
		
		if (hm.get("firmId") != null && !hm.get("firmId").equals("") && !hm.get("firmId").equals("-1")) {
			query+=" and tpir.firm_id=? " ;
			parameters.add(hm.get("firmId"));
		}
		
		if (hm.get("sbuId") != null && !hm.get("sbuId").equals("") && !hm.get("sbuId").equals("-1")) {
			query+=" and sbumast.sbu_id=? " ;
			parameters.add(hm.get("sbuId"));
		}
		
		
		
		
		LinkedHashMap<String, String> purchaseAccountsMap=getMap(parameters, query, con);
		BigDecimal purchaseAccounts=purchaseAccountsMap.get("soldPurchases")==null?new BigDecimal(0):new BigDecimal(purchaseAccountsMap.get("soldPurchases"));
		return purchaseAccounts; 
		 
		

	}
	
	
	
	
	
	
	public BigDecimal getSalesAccounts(HashMap<String, Object> hm, Connection con)
			throws ClassNotFoundException, SQLException, ParseException {
		ArrayList<Object> parameters = new ArrayList<>();
		
		
		parameters.add(getDateASYYYYMMDD(hm.get("fromDate").toString()));
		parameters.add(getDateASYYYYMMDD(hm.get("toDate").toString()));
		
		

		String query = "select sum(gross_amount+freight_amount) salesAmounts from "
				+ "trn_sales_invoice_register tpir,mst_firm firm,sbu_master sbumast "
				+ "where tpir.invoice_date between ? and ? and tpir.activate_flag=1 and tpir.firm_id=firm.firm_id  and sbumast.activate_flag=1 and firm.parent_sbu_id=sbumast.sbu_id firmCondition sbuCondition";
		
		if (hm.get("firmId") != null && !hm.get("firmId").equals("") && !hm.get("firmId").equals("-1")) {
			query=query.replaceFirst("firmCondition", " and firm.firm_id=? ") ;
			parameters.add(hm.get("firmId"));
		}
		else
		{
			query=query.replaceFirst("firmCondition", " ") ;
		}
		
		
		if (hm.get("sbuId") != null && !hm.get("sbuId").equals("") && !hm.get("sbuId").equals("-1")) {
			query=query.replaceFirst("sbuCondition", " and sbumast.sbu_id=? ") ;
			parameters.add(hm.get("sbuId"));
		}
		else
		{
			query=query.replaceFirst("sbuCondition", " ") ;
		}
		LinkedHashMap<String, String> salesAmountsMap=getMap(parameters, query, con);
		BigDecimal salesAmounts=salesAmountsMap.get("salesAmounts")==null?new BigDecimal(0):new BigDecimal(salesAmountsMap.get("salesAmounts"));
		return salesAmounts;
		

	}

	
	
	public HashMap<String, String> getsalesForDashboard(String fromDate,String toDate, Connection conWithF) throws SQLException, ParseException 
	{
		String query = "select COALESCE(sum(total_amount),0) as totalSalesDashboard from trn_sales_invoice_register where activate_flag=1 and invoice_date between ? and ?";

		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(getDateASYYYYMMDD(fromDate));
		parameters.add(getDateASYYYYMMDD(toDate));
		

		return getMap(parameters, query, conWithF);

	}
	
	public HashMap<String, String> getpurchaseForDashboard(String fromDate,String toDate, Connection conWithF) throws SQLException, ParseException 
	{
		String query = "select COALESCE(sum(total_amount),0) as totalPurchaseDashboard from trn_purchase_invoice_register where activate_flag=1 and invoice_date between ? and ?";

		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(getDateASYYYYMMDD(fromDate));
		parameters.add(getDateASYYYYMMDD(toDate));
		

		return getMap(parameters, query, conWithF);

	}
	
	
	public LinkedHashMap<String, String> getClientName(String jobSheetNo, Connection con) throws ClassNotFoundException, SQLException, ParseException 
	{
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(jobSheetNo);
		
		String query="select\r\n"
				+ "	cust.Client_name \r\n"
				+ "from\r\n"
				+ "	trn_sales_invoice_details tsid,\r\n"
				+ "	trn_sales_invoice_register tsir,\r\n"
				+ "	mst_Client cust\r\n"
				+ "where\r\n"
				+ "	job_sheet_no = ?\r\n"
				+ "	and tsir.invoice_id = tsid.invoice_id  and cust.Client_id =tsir.Client_id ; ";
		
		return getMap(parameters, query, con);
	}
	
	public LinkedHashMap<String, String> getVendorName(String jobSheetNo, Connection con) throws ClassNotFoundException, SQLException, ParseException 
	{
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(jobSheetNo);
		
		String query="select\r\n"
				+ "	cust.Client_name as vendorName \r\n"
				+ "from\r\n"
				+ "	trn_purchase_invoice_details tpid,\r\n"
				+ "	trn_purchase_invoice_register tpir,\r\n"
				+ "	mst_Client cust\r\n"
				+ "where\r\n"
				+ "	job_sheet_no = ?\r\n"
				+ "	and tpir.invoice_id = tpid.invoice_id  and cust.Client_id =tpir.Client_id ; ";
		
		return getMap(parameters, query, con);
	}
	
	
	
	public LinkedHashMap<String, String> getProductName(String jobsheetNo, Connection con) throws ClassNotFoundException, SQLException, ParseException 
	{
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(jobsheetNo);
		
		String query="select mi.item_name from \r\n"
				+ "trn_sales_invoice_details tsid ,mst_items mi \r\n"
				+ "where job_sheet_no =? and mi.item_id =tsid.item_id ; ";
		
		return getMap(parameters, query, con);
	}
	
	public LinkedHashMap<String, String> getFirmName(String jobsheetNo, Connection con) throws ClassNotFoundException, SQLException, ParseException 
	{
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(jobsheetNo);
		
		String query="select group_concat(distinct(firm_name)) firmName from trn_sales_invoice_details tsid,trn_sales_invoice_register tsir ,mst_firm firm"
				+ " where tsid.invoice_id=tsir.invoice_id "
				+ " and tsid.job_sheet_no=? and firm.firm_id=tsir.firm_id";
		
		return getMap(parameters, query, con);
	}
	
	
	
	
	
	
	public List<LinkedHashMap<String, Object>> getListOfSalesForJobSheet(String jobsheetno,Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(jobsheetno);
		return getListOfLinkedHashHashMap(parameters,
				"select date_format(tsir.invoice_date,'%d/%m/%Y') as FormattedFromDate ,round(tsid.qty,0) as Qty , format(tsid.rate,2) as Rate ,round(tsid.qty * tsid.rate,2) as values1, tsir.invoice_id from \r\n"
				+ "trn_sales_invoice_details tsid  ,\r\n"
				+ "trn_sales_invoice_register tsir \r\n"
				+ "where job_sheet_no =? and tsir.invoice_id =tsid.invoice_id and tsir.activate_flag=1;",
				con);
	}
	
	public List<LinkedHashMap<String, Object>> getListOfPurchaseForJobSheet(String jobsheetNo,Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(jobsheetNo);
		return getListOfLinkedHashHashMap(parameters,
				"select date_format(tsir.invoice_date,'%d/%m/%Y') as FormattedFromDate ,round(tsid.qty,0) as Qty , format(tsid.rate,2) as Rate ,round(tsid.qty * tsid.rate,2) as values1, tsir.invoice_id from \r\n"
				+ "trn_purchase_invoice_details tsid  ,\r\n"
				+ "trn_purchase_invoice_register tsir \r\n"
				+ "where job_sheet_no =? and tsir.invoice_id =tsid.invoice_id and tsir.activate_flag  = 1;",
				con);
	}
	
	public List<LinkedHashMap<String, Object>> getListOfPurchaseFromOtherJobSheet(String  jobsheetNo,Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(jobsheetNo);
		return getListOfLinkedHashHashMap(parameters,
				"select\r\n"
				+ "	date_format(tpir.invoice_date, '%d/%m/%Y') as FormattedFromDate ,\r\n"
				+ "	round(tsid.qty) as Qty ,\r\n"
				+ "	format(tpid.rate,\r\n"
				+ "	2) as Rate ,\r\n"
				+ "	round(tsid.qty * tpid.rate,2)+ 0 as values1,\r\n"
				+ "	tpir.invoice_id,tpid.job_sheet_no ,tpir.activate_flag \r\n"
				+ "from\r\n"
				+ "	trn_sales_invoice_details tsid,	\r\n"
				+ "	trn_purchase_invoice_details tpid,\r\n"
				+ "	trn_purchase_invoice_register tpir \r\n"
				+ "where\r\n"
				+ "	tsid.job_sheet_no = ?\r\n"
				+ "	and tsid.purchase_details_id = tpid.details_id\r\n"
				+ "	and tpid.job_sheet_no != tsid.job_sheet_no\r\n"
				+ "	and tpir.invoice_id =tpid.invoice_id and tpir.activate_flag =1;",
				con);
	}
	
	
	public List<LinkedHashMap<String, Object>> getListOfExpenseForJobSheet(String jobsheetNo,Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(jobsheetNo);
		return getListOfLinkedHashHashMap(parameters,
				"select\r\n"
				+ "	debitClient.Client_name,creditClient.Client_name creditClient,tjd.amount,tje.journal_id,tje.debit,date_format(tje.journal_date, '%d/%m/%Y') as JournalDate \r\n"
				+ "from\r\n"
				+ "	trn_journal_entry tje,\r\n"
				+ "	trn_journal_details tjd,\r\n"
				+ "	mst_Client debitClient,\r\n"
				+ "	mst_Client creditClient\r\n"
				+ "	\r\n"
				+ "where\r\n"
				+ "	tje.journal_id = tjd.journal_id\r\n"
				+ "	and tjd.job_sheet_no = ? and tje.debit=debitClient.Client_id and tje.credit=creditClient.Client_id and tje.activate_flag=1",
				con);
	}	
	
	
	public List<LinkedHashMap<String, Object>> getListOfPaymentForJobSheet(String jobsheetNo,Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(jobsheetNo);
		return getListOfLinkedHashHashMap(parameters,
				"select\r\n"
				+ "	debitClient.Client_name,creditClient.bank_name creditClient,tjd.amount,tje.payment_id,tje.payment_id,date_format(tje.payment_date, '%d/%m/%Y') as JournalDate \r\n"
				+ "from\r\n"
				+ "	trn_payment_register tje,\r\n"
				+ "	trn_payment_details tjd,\r\n"
				+ "	mst_Client debitClient,\r\n"
				+ "	mst_bank creditClient\r\n"
				+ "	\r\n"
				+ "where\r\n"
				+ "	tje.payment_id = tjd.payment_id\r\n"
				+ "	and tjd.job_sheet_no = ? and tje.Client_id=debitClient.Client_id and tje.bank_id=creditClient.bank_id and tje.activate_flag=1 and debitClient.group_id=6",
				con);
	}	
	

	public long addEnquiry(Connection conWithF, HashMap<String, Object> hm) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("client_id"));
		parameters.add(hm.get("firm_id"));
		parameters.add(hm.get("message"));
		
		return insertUpdateDuablDB("insert into tbl_user_mst values (default,?,?,?,sysdate(),1,sysdate())", parameters,
				conWithF);
		

	}
	
	public List<LinkedHashMap<String, Object>> getenquiries(HashMap<String, Object> hm, Connection con)
			throws ClassNotFoundException, SQLException, ParseException{
		ArrayList<Object> parameters = new ArrayList<>();
		
		
		
		
		return getListOfLinkedHashHashMap(parameters,
				"select enquiry_id enquiryId,client_id ClientId,message Message from tbl_enquiry_mst where respondedFlag=1",
				con);
		
		
	}
	
	public String deletePaymentTransaction(long paymentId, Connection conWithF) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(paymentId);
		insertUpdateDuablDB("UPDATE trn_payment_register  SET activate_flag=0,updated_date=SYSDATE() WHERE payment_id=?",
				parameters, conWithF);
		return "Deleted Succesfully";
	}
	
	public List<LinkedHashMap<String, Object>> getAuditListByUser(String username,Connection con) throws ClassNotFoundException, SQLException 
	{
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(username);
		parameters.add(username);
		
		return getListOfLinkedHashHashMap(parameters,"select * from \r\n"
				+ "(\r\n"				
				+ "select * from (select * from agserp.frm_audit_trail where user_name=? order by accessed_time desc  limit 100 ) as b\r\n"
				+ "union all\r\n"
				+ "select * from (select * from ssegpl.frm_audit_trail where user_name=? order by accessed_time desc  limit 100 ) as c\r\n"
				+ ") m order by accessed_time  desc limit 100",con);
	}
	
	
	public List<LinkedHashMap<String, Object>> getLastestUserHits(Connection con) throws ClassNotFoundException, SQLException 
	{
		ArrayList<Object> parameters = new ArrayList<>();
		
		return getListOfLinkedHashHashMap(parameters,"	"
				+ "	select max(accessed_time) as T1,user_name,'AGS' from  agserp.frm_audit_trail fat group by user_name having user_name is not null union all \r\n"
				+ "	select max(accessed_time) as T1,user_name,'SSEGPL' from  ssegpl.frm_audit_trail fat group by user_name having user_name is not null  \r\n"
				+ "	order by T1 desc\r\n"
				+ "	limit 20;",con);
	}
	
	
	public LinkedHashMap<String, String> getuserDetailsByEmailId(String emailId,Connection con) throws SQLException {

		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(emailId);		
		return getMap(parameters, "select * from tbl_user_mst where email=?", con);

	}
	
	
	public List<LinkedHashMap<String, Object>> getItemFiltersById(HashMap<String, Object> hm, Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		
		
		
		String query="select * from mst_items where 1=1 ";
		
		
				
		if(!hm.get("category_id").equals("") && !hm.get("category_id").equals("-1"))
		{
			query+=" and parent_category_id=?";
			parameters.add(hm.get("category_id"));			
		}
		
		if(!hm.get("brand_id").equals("") && !hm.get("brand_id").equals("-1"))
		{
			query+=" and brand_id=?";
			parameters.add(hm.get("brand_id"));			
		}
		
		
		if(!hm.get("concept").equals(""))
		{
			query+=" and product_details like ?";
			parameters.add("%"+hm.get("concept")+"%");			
		}
		
		if(hm.get("itemSearch")!=null && !hm.get("itemSearch").equals(""))
		{
			query+=" and (item_name like ? or product_code like ?)";
			parameters.add("%"+hm.get("itemSearch")+"%");
			parameters.add("%"+hm.get("itemSearch")+"%");
		}
		
		
		if(hm.get("fromrangeprice")!=null && !hm.get("fromrangeprice").equals("") && hm.get("torangeprice")!=null && !hm.get("torangeprice").equals(""))
		{
			query+=" and aop between ? and ?";
			parameters.add(hm.get("fromrangeprice"));
			parameters.add(hm.get("torangeprice"));
			
		}

		return getListOfLinkedHashHashMap(parameters, query, con);

	}
	
	
	
	public String saveEstimateToDB(HashMap<String, Object> hm,Connection con,List<LinkedHashMap<String,Object>> prodDetails) throws Exception
	{		
		
		
		ArrayList<Object> parameters = new ArrayList<>();
		String query = "INSERT INTO trn_estimate_register "
				+ " (estimate_id,estimate_no, firm_id, client_id, estimate_date, activate_flag, updated_by, updated_date, remarks) "
				+ " VALUES( default,?, ?,?, curdate(), 1, ?, sysdate(), ?)";
		
		parameters.add(hm.get("EstimateNo"));
		parameters.add(hm.get("firmId"));
		
		parameters.add(hm.get("clientId"));
		parameters.add(hm.get("updatedBy"));		
		parameters.add(hm.get("remarks"));
	
		
		long estimateId = insertUpdateDuablDB(query, parameters, con);
		
		for(HashMap<String, Object> prod:prodDetails)
			{
			
			parameters = new ArrayList<>();
			query= "INSERT INTO rlt_estimate_product VALUES (DEFAULT,?, ?, ?, ?,?)";
			parameters.add(estimateId);
			parameters.add(String.valueOf(prod.get("itemId")));
			parameters.add(String.valueOf(prod.get("itemName")));
			parameters.add(String.valueOf(prod.get("mrp")));
			parameters.add(String.valueOf(prod.get("offerPrice")));
			
			 insertUpdateDuablDB(query, parameters, con);
			}
			
			return "Estimate Added Succesfully";
		
			
	}
	
	
	 public String getEstimateSeqNo(Connection con,boolean updateflag) throws Exception
	 {		 
		 ArrayList<Object> parameters = new ArrayList<>();
			String query="select current_seq_no seqNo from seq_master where sequence_name='estimate_seq'";		
			Integer sequence =Integer.parseInt(getMap(parameters, query, con).get("seqNo"));
		 
		
			String BeginerString="EST-2223";
			String SequenceWithLeadingZeros=String.format("%03d", sequence);
			String finalRequiredString=BeginerString+SequenceWithLeadingZeros;
			
			String insertTableSQL = "UPDATE seq_master  SET current_seq_no=current_seq_no+1 where sequence_name= 'estimate_seq'";
			parameters = new ArrayList<>();
			query= "UPDATE seq_master  SET current_seq_no=current_seq_no+1 where sequence_name= 'estimate_seq'";	
			
			if(updateflag)
			{
				insertUpdateDuablDB(query, parameters, con);
			}
			
			
		
			return finalRequiredString;		 
	 }

	public List<String> getListofJobSheetsByFilters(HashMap<String, Object> outputMap, Connection con) throws ParseException, ClassNotFoundException, SQLException 
	{
		
		ArrayList<Object> parameters = new ArrayList<>();
		
		String 	query="select distinct(job_sheet_no) from trn_purchase_invoice_register tpir,trn_purchase_invoice_details tpid where tpir.invoice_id=tpid.invoice_id and job_sheet_no !='' and tpir.activate_flag=1 ";
		
		
		
		
		
		
		if(outputMap.get("fromDate")!=null && !outputMap.get("fromDate").toString().equals("") && outputMap.get("fromDate")!=null && !outputMap.get("fromDate").toString().equals(""))
		{
			query+=" and invoice_date between ? and ?";
			parameters.add(getDateASYYYYMMDD(outputMap.get("fromDate").toString()));
			parameters.add(getDateASYYYYMMDD(outputMap.get("toDate").toString()));
		}
		
		if(outputMap.get("firmId")!=null && !outputMap.get("firmId").toString().equals("-1"))
		{
			query+=" and tpir.firm_id=? ";			
			parameters.add(outputMap.get("firmId").toString());
		}
		
		
		
		
		
		
		
		return getListOfString(parameters,query,con);

	}

	public String getLastCheckInType(String userId, Connection con) throws SQLException 
	{	
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(userId);
		HashMap<String, String> hm=getMap(parameters, "select check_in_type from trn_checkin_register where user_id=? and date(checked_time)=curdate() order by checked_time desc", con);		
		return hm.isEmpty()?"O":hm.get("check_in_type");
	}

	public long saveCheckIn(String checkInType, String remarks, String latitude, String longitude, String userId,
			Connection con) throws SQLException 
	{
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(userId);
		parameters.add(checkInType);
		parameters.add(latitude);
		parameters.add(longitude);
		parameters.add(remarks);
		
		return insertUpdateDuablDB("insert into trn_checkin_register values (default,?,?,sysdate(),?,?,?,1)", parameters,con);

	}


	
	public List<LinkedHashMap<String, Object>> getListOfLastCheckIns(String userId,int limit,Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		
		parameters.add(limit);
		
		return getListOfLinkedHashHashMap(parameters,
				"select * from trn_checkin_register tcr,tbl_user_mst tum where tum.user_id=tcr.user_id order by checked_time desc limit ?",
				con);
	}

	public List<LinkedHashMap<String, Object>> getCheckInsForThisUser(HashMap<String, Object> outputMap,
			Connection con) throws ParseException, ClassNotFoundException, SQLException
	{
		
		
		String query="select * from trn_checkin_register tcr,tbl_user_mst user where 1=1 and user.user_id=tcr.user_id ";
		ArrayList<Object> parameters = new ArrayList<>();
		
		if(outputMap.get("userId")!=null && !outputMap.get("userId").toString().equals(""))
		{
			query+="and user.user_id=? ";
			parameters.add(outputMap.get("userId"));
		}
		
		if(outputMap.get("txtfromdate")!=null && !outputMap.get("txtfromdate").toString().equals("") && outputMap.get("txttodate")!=null && !outputMap.get("txttodate").toString().equals("")) 
		{
			query+="and date(checked_time) between ? and ?";
			parameters.add(getDateASYYYYMMDD(outputMap.get("txtfromdate").toString()));
			parameters.add(getDateASYYYYMMDD(outputMap.get("txttodate").toString()));
		}
		
		query+="order by checked_time desc ";
		
		
		return getListOfLinkedHashHashMap(parameters,query,con);
	}
	

	
	 
	 
	 public List<LinkedHashMap<String, Object>> getGeneratedEstimate(HashMap<String, Object> hm, Connection con)
				throws ClassNotFoundException, SQLException, ParseException {
			ArrayList<Object> parameters = new ArrayList<>();
			
			
			
			String query ="select *,date_format(estimate_date, '%d/%m/%Y') as formattedEstimateDate  from trn_estimate_register ter,mst_firm firm, tbl_user_mst user1 where firm.firm_id=ter.firm_id and ter.updated_by = user1.user_id  and ter.activate_flag=1";
			
			
			
			
			
			if(hm.get("searchString")!=null && !hm.get("searchString").equals(""))
			{
				query+=" and estimate_no like ?";
				parameters.add(hm.get("searchString"));			
			}
			
			if(hm.get("firmId")!=null && !hm.get("firmId").equals("-1"))
			{
				query+=" and ter.firm_id=?";
				parameters.add(hm.get("firmId"));			
			}
			
			
			if(hm.get("fromDate")!=null && !hm.get("fromDate").equals("") && hm.get("toDate")!=null && !hm.get("toDate").equals("") )
			{
				query+=" and estimate_date between ? and ?";
				parameters.add(getDateASYYYYMMDD(hm.get("fromDate").toString()));
				parameters.add(getDateASYYYYMMDD(hm.get("toDate").toString()));
			}
		
			
			return getListOfLinkedHashHashMap(parameters, query, con);
		}
	 
	 public LinkedHashMap<String, Object> getEstimateDetails(String estimateNo, Connection con)
				throws ClassNotFoundException, SQLException {
			ArrayList<Object> parameters = new ArrayList<>();
			parameters.add(estimateNo);
			LinkedHashMap<String, Object> itemDetailsMap = new LinkedHashMap<>();
			itemDetailsMap = getMapReturnObject(parameters, "select * from trn_estimate_register ter  where estimate_no=?", con);
			
			parameters = new ArrayList<>();
			parameters.add(estimateNo);

			
			itemDetailsMap.put("listOfItems",
					getListOfLinkedHashHashMap(parameters, "select * from trn_estimate_register ter,rlt_estimate_product rep,  mst_items mi  where rep.estimate_id=ter.estimate_id and ter.estimate_no=? ", con));
			
			
			
		
			
			return itemDetailsMap;

		}
	 
	


	
	 
	 public LinkedHashMap<String, Object> getexportPaymentsPDF(String paymentId, Connection con)
				throws ClassNotFoundException, SQLException {
			ArrayList<Object> parameters = new ArrayList<>();
			parameters.add(paymentId);
			LinkedHashMap<String, Object> itemDetailsMap = new LinkedHashMap<>();
			itemDetailsMap = getMapReturnObject(parameters, "select payment_id,firm_name ,date_format(payment_date,'%d/%m/%Y') as formattedPaymentDate , Client_name \r\n"
					+ "from\r\n"
					+ "trn_payment_register tpr,mst_Client mc,mst_firm mf where mc.Client_id =tpr.Client_id\r\n"
					+ " and \r\n"
					+ "payment_id=? and mf.firm_id =tpr.firm_id \r\n"
					+ "and mc.Client_id =tpr.Client_id",
					con);
					
				
			parameters = new ArrayList<>();
			parameters.add(paymentId);

					
			itemDetailsMap.put("listOfItems",
					getListOfLinkedHashHashMap(parameters, "select amount ,job_sheet_no, remarks \r\n"
							+ "from \r\n"
							+ "trn_payment_details tpd\r\n"
							+ "where payment_id=?",
							con));
					
			return itemDetailsMap;

		}


	
	public List<LinkedHashMap<String, Object>> getServiceMappingForThisClient(long firmId,String type, Connection con)
			throws SQLException, ClassNotFoundException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(type);
		parameters.add(firmId);
		return getListOfLinkedHashHashMap(parameters,
				"select\r\n"
				+ "	bank.bank_id,\r\n"
				+ "	bank.bank_name, bank.account_no,bank.ifsc_code \r\n"
				+ "from\r\n"
				+ "	firm_bank_mapping fbm,\r\n"
				+ "	mst_bank bank\r\n"
				+ "	\r\n"
				+ "where	\r\n"
				+ "	fbm.type = ? \r\n"
				+ "	and fbm.firm_id = ? \r\n"
				+ "	and fbm.bank_id = bank.bank_id\r\n"
				+ "	and fbm.activate_flag = 1 ",
				con);
	}

	public List<LinkedHashMap<String, Object>> getHolidayMaster(Connection con)
			throws SQLException, ClassNotFoundException {
		ArrayList<Object> parameters = new ArrayList<>();
		return getListOfLinkedHashHashMap(parameters,
				"select * from holiday_master where activate_flag=1 order by holiday_date ",
				con);
	}
	public long addHoliday(Connection con, HashMap<String, Object> hm) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		
		
		String query="insert into holiday_master values (default,?,?,1,?,sysdate())";
		parameters.add(hm.get("txtholidayname"));
		parameters.add(getDateASYYYYMMDD(hm.get("txtholidaydate").toString()));
		parameters.add(hm.get("user_id"));
		
		
		return insertUpdateDuablDB(query, parameters,
				con);
	}
	public String updateHoliday(long holidayId, Connection con, String holidayName,String holidayDate,String updatedBy) throws Exception {

		ArrayList<Object> parameters = new ArrayList<>();
		
		
		parameters.add(holidayName);
		parameters.add(getDateASYYYYMMDD(holidayDate));
		parameters.add(updatedBy);

		parameters.add(holidayId);
		

		insertUpdateDuablDB("UPDATE holiday_master SET holiday_Name=?,holiday_date=?,updated_date=SYSDATE(),updated_by=? WHERE holiday_id=?",
				parameters, con);
		return "Category updated Succesfully";

	}
	
	public String deleteHoliday(long holidayId,String userId, Connection conWithF) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		
		parameters.add(userId);
		parameters.add(holidayId);
		insertUpdateDuablDB("UPDATE holiday_master  SET activate_flag=0,updated_date=SYSDATE(),updated_by=? WHERE holiday_id=?",
				parameters, conWithF);
		return "Holiday updated Succesfully";
	}


	public LinkedHashMap<String, String> getHolidayDetails(HashMap<String, Object> hm, Connection con) throws SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("holiday_id"));
		
		
		return getMap(parameters,
				"select *,date_format(holiday_date,'%d/%m/%Y') as formattedHolidateDate from holiday_master where holiday_id=?",
				con);
	}
	public long AddVisitor(Connection conWithF, HashMap<String, Object> hm) throws SQLException {

		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("visitorname"));
		parameters.add( hm.get("address"));
		parameters.add( hm.get("purpose_of_visit"));		
		parameters.add( hm.get("remarks"));	
		parameters.add( hm.get("MobileNo"));	
		parameters.add( hm.get("EmailId"));	
		parameters.add( hm.get("app_id"));
		parameters.add( hm.get("hdnselectedemployee"));
		
		
		String insertQuery = "insert into visitor_entry values (default,?,?,?,?,?,?,sysdate(),?,sysdate(),1,?,null)";
		
		return insertUpdateDuablDB(insertQuery, parameters, conWithF);

	}

	public long checkoutVendor(Connection conWithF, HashMap<String, Object> hm) throws SQLException {

		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("check_out_remark"));

		parameters.add(hm.get("vendor_checkin_id"));

		
		String insertQuery = "update vendor_check_in_register set check_out_time=sysdate(),check_out_remark=? where vendor_checkin_id=?";
		
		return insertUpdateDuablDB(insertQuery, parameters, conWithF);

	}
	

	public long AddVendorCheckIn(Connection conWithF, HashMap<String, Object> hm) throws SQLException {

		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("vendor_qr_code"));
		parameters.add(hm.get("remarks"));

		parameters.add(hm.get("purpose_of_visit"));

		
		String insertQuery = "insert into vendor_check_in_register values (default,?,sysdate(),null,?,1,?,null)";
		
		return insertUpdateDuablDB(insertQuery, parameters, conWithF);

	}
	
	
	
	public LinkedHashMap<String, String> getvisitorDetails(long VisitorId, Connection con) throws SQLException {

		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(VisitorId);
		return getMap(parameters,
				"select * from visitor_entry where visitor_id=?", con);
		

	}
	
	public List<LinkedHashMap<String, Object>> showVisitors(HashMap<String, Object> hm, Connection con)
			throws ClassNotFoundException, SQLException, ParseException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("app_id"));
		parameters.add(getDateASYYYYMMDD(hm.get("txtfromdate").toString()));
		parameters.add(getDateASYYYYMMDD(hm.get("txttodate").toString()));
		return getListOfLinkedHashHashMap(parameters,
		"select\n" + 
		"ve.*,tum.*,\n" + 
		"(\n" + 
		"select group_concat(attachment_id) from tbl_attachment_mst tam where file_id =ve.visitor_id\n" + 
		") as attachmentIds\n" + 
		"from\n" + 
		"visitor_entry ve\n" + 
		"left outer join tbl_user_mst tum on tum.user_id=ve.contact_to_employee\n" + 
		"where\n" + 
		"ve.app_id =? and ve.activate_flag = 1\n" + 
		"and date(in_time) between ? and ?\n" + 
		"order by\n" + 
		"in_time desc\n",
				con);

	}

	public List<LinkedHashMap<String, Object>> showVendorCheckIn(HashMap<String, Object> hm, Connection con)
			throws ClassNotFoundException, SQLException, ParseException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(getDateASYYYYMMDD(hm.get("txtfromdate").toString()));
		parameters.add(getDateASYYYYMMDD(hm.get("txttodate").toString()));
		return getListOfLinkedHashHashMap(parameters,
		"select\n" + 
"ve.*,mv.*,\n" + 
"(\n" + 
"select group_concat(attachment_id) from tbl_attachment_mst tam where file_id =ve.vendor_checkin_id and type='VendorImage'\n" + 
") as attachmentIds\n" + 
"from\n" + 
"vendor_check_in_register ve,mst_vendor mv\n" + 
"where\n" + 
"ve.activate_flag = 1\n" + 
"and date(check_in_time) between ? and ? and mv.qr_code =ve.vendor_qr_code\n" + 
"order by\n" + 
"check_in_time desc\n",
				con);

	}
	
	
	public String deleteVisitor(long visitorId, Connection conWithF) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(visitorId);
		
		insertUpdateDuablDB("UPDATE visitor_entry  SET activate_flag=0 WHERE visitor_id=?",
				parameters, conWithF);
		return "Visitor deleted Succesfully";
	}
	
	public String checkoutVisitor(long visitorId, Connection conWithF) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(visitorId);
		
		insertUpdateDuablDB("UPDATE visitor_entry  SET checkout_time=sysdate() WHERE visitor_id=?",
				parameters, conWithF);
		return "Visitor checkout Succesfully";
	}
	
	
	
	
	public List<LinkedHashMap<String, Object>> getDistinctPurposeOfVisitList(Connection con, String appId) throws SQLException, ClassNotFoundException 
	{
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(appId);
		return getListOfLinkedHashHashMap(parameters, "select distinct(purpose_of_visit) from visitor_entry where app_id=? and activate_flag=1", con);
	}
	
	
	
	
	public long saveSupervisorSubmitLeave(Connection conWithF, HashMap<String, Object> hm) throws SQLException, ParseException {

		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("employee_id"));
		parameters.add( hm.get("supervisor_id"));
		parameters.add( hm.get("reason"));
		parameters.add( hm.get("remark"));		
		parameters.add( getDateASYYYYMMDD(hm.get("txtfromDate").toString()));	
		parameters.add( getDateASYYYYMMDD(hm.get("txttoDate").toString()));	
		
		
		String insertQuery = "insert into trn_leave_register values (default,?,?,?,?,?,?,1,sysdate())";
		
		return insertUpdateDuablDB(insertQuery, parameters, conWithF);

	}
	public List<LinkedHashMap<String, Object>> getLeaves(String fromDate,String emp_id,Connection con)
			throws SQLException, ClassNotFoundException, ParseException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(getDateASYYYYMMDD(fromDate));
		String query="select\r\n"
		+ "	*,date_format(from_date,'%d/%m/%Y') as FormattedFromDate,date_format(to_date,'%d/%m/%Y') as FormattedToDate, \r\n"
		+ "	tum.name as EmployeeName,tum2.name as SuperVisorName from trn_leave_register tlr,tbl_user_mst tum,tbl_user_mst tum2 \r\n "
		+" where (? between from_date and to_date) and tum.user_id=tlr.employee_id and tum2.user_id=tlr.supervisor_id and tlr.activate_flag=1 ";

		if (!emp_id.equals(""))
		{

			parameters.add(emp_id);
			query+=" and tlr.employee_id=?";


		}
	 query+=" order by from_date desc";
		
		return getListOfLinkedHashHashMap(parameters,
				query, con
				);
	}

	public List<LinkedHashMap<String, Object>> getLeavesRegister(String fromDate,String toDate,String emp_id,Connection con)
			throws SQLException, ClassNotFoundException, ParseException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(getDateASYYYYMMDD(fromDate));
		parameters.add(getDateASYYYYMMDD(toDate));

		
	
		String query="select\r\n"
		+ "	*,date_format(from_date,'%d/%m/%Y') as FormattedFromDate,date_format(to_date,'%d/%m/%Y') as FormattedToDate, \r\n"
		+ "	tum.name as EmployeeName,tum2.name as SuperVisorName from trn_leave_register tlr,tbl_user_mst tum,tbl_user_mst tum2 \r\n "
		+" where from_date <=? and to_date >= ?  and tum.user_id=tlr.employee_id and tum2.user_id=tlr.supervisor_id and tlr.activate_flag=1 ";

		if (!emp_id.equals(""))
		{

			parameters.add(emp_id);
			query+=" and tlr.employee_id=?";


		}
	 query+=" order by from_date desc";
		
		return getListOfLinkedHashHashMap(parameters,
				query, con
				);
	}
	
	public LinkedHashMap<String, String> getShiftDetails(HashMap<String, Object> hm, Connection con) throws SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("shift_id"));
		
		
		return getMap(parameters,
				"select\r\n"
				+ "*,	TIME_FORMAT(from_time, '%H') fromHour,\r\n"
				+ "	minute(from_time) fromMinute,\r\n"
				+ "	TIME_FORMAT(to_time, '%H') toHour,\r\n"
				+ "	minute(to_time) toMinute\r\n"
				+ "from\r\n"
				+ "	shift_master where shift_id=? ",
				con);
	}

	
	public List<LinkedHashMap<String, Object>> getShiftMaster(HashMap<String, Object> hm,Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("app_id"));
		return getListOfLinkedHashHashMap(parameters,
				"select * from shift_master where app_id=? and activate_flag=1",
				con);


	}
	public long addShift(Connection conWithF, HashMap<String, Object> hm) throws SQLException {

		ArrayList<Object> parameters = new ArrayList<>();
		
		parameters.add(hm.get("shift_name"));
		
		parameters.add(hm.get("from_time_hour")+":"+hm.get("from_time_minute"));
		parameters.add(hm.get("to_time_hour")+":"+hm.get("to_time_minute"));
		
		
		
		parameters.add(hm.get("app_id"));
        parameters.add(hm.get("user_id"));
        parameters.add(hm.get("late_shift_cutoff"));
        
		//String insertQuery = "insert into shift_master values (default,?,?,?,?,?,1,?,?,sysdate()) ";
		String insertQuery = "insert into shift_master values (default,?,?,?,1,?,?,sysdate(),?) ";
		
		return insertUpdateDuablDB(insertQuery, parameters, conWithF);

	}
	
	public String updateShift(long shiftId,String shift_name,String from_time_hour,String from_time_minute,String to_time_hour,String to_time_minute,String userId,String late_shift_cutoff,Connection con) throws Exception 
	{
		ArrayList<Object> parameters = new ArrayList<>();
		
		
		parameters.add(shift_name);
		parameters.add(from_time_hour+":"+from_time_minute);
		
		parameters.add(to_time_hour+":"+to_time_minute);
		
		
		
		parameters.add(userId);
		
		parameters.add(late_shift_cutoff);
              parameters.add(shiftId);


		insertUpdateDuablDB("update shift_master set shift_name=?,from_time=?,to_time=?,updated_by=?,updated_date=sysdate(),late_shift_cutoff=? where shift_id=?",parameters, con);
		return "Shift Updated Succesfully";
	}
	public String deleteShift(long shiftId, Connection conWithF) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(shiftId);
		insertUpdateDuablDB("UPDATE shift_master  SET activate_flag=0,updated_date=SYSDATE() WHERE shift_id=?",
				parameters, conWithF);
		return "Shift Deleted Succesfully";
	}
	public Object getInwardDetails(HashMap<String, Object> outputMap, Connection connections) throws SQLException {		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(outputMap);
		return getMap(parameters,
			"select * from trn_inward_register where inward_id=?", con);

	}
	public List<LinkedHashMap<String, Object>> getInward(String fromDate,String toDate,Connection con)
			throws SQLException, ClassNotFoundException, ParseException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(getDateASYYYYMMDD(fromDate));
		parameters.add(getDateASYYYYMMDD(toDate));
		return getListOfLinkedHashHashMap(parameters,
				"select *,tum2.name supervisorName from trn_leave_register tlr,tbl_user_mst tum,tbl_user_mst tum2 where tum.user_id=tlr.employee_id "
				+ "and tum2.user_id=tlr.supervisor_id and tlr.leave_date between ? and ? order by leave_date desc" ,
				con);
	}

	public List<LinkedHashMap<String, Object>> getAttendance(String fromDate,String toDate,String empId,Connection con)
			throws SQLException, ClassNotFoundException, ParseException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(getDateASYYYYMMDD(fromDate));
		parameters.add(getDateASYYYYMMDD(toDate));

		String query="select *,tum.name employeeName,"
				+ "case when check_in_type = 'I' then 'Check In' else 'Check Out' end checkintyped from trn_checkin_register tcr,\r\n"
				+ "tbl_user_mst tum where tum.user_id=tcr.user_id\r\n"
				+ "and date(tcr.checked_time) between ? and ?";

		


		if(!empId.equals(""))
		{
			query+=" and tum.user_id=? ";
			parameters.add(empId);
		}
		query+="order by tum.name,checked_time,check_in_type";

		return getListOfLinkedHashHashMap(parameters,
		query,
				con);
	}

	public String deleteleave(long leaveId, Connection conWithF) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(leaveId);
		insertUpdateDuablDB("UPDATE trn_leave_register SET activate_flag=0 WHERE leave_id=?",
				parameters, conWithF);
		return "Leave Deleted Succesfully";
	}
	public List<LinkedHashMap<String, Object>> getAccessBlockEntries(HashMap<String, Object> inputMap, Connection con) 
        throws SQLException, ClassNotFoundException {
    ArrayList<Object> parameters = new ArrayList<>();
    StringBuilder query = new StringBuilder(
        "SELECT * " +
        "FROM trn_access_block_register tabr " +
        "INNER JOIN tbl_user_mst tum ON tum.user_id = tabr.employee_id " +
        "WHERE tabr.activate_flag = 1 "
    );

    // Check for search input and add filtering by employee name
    if (inputMap.get("searchInput") != null && !inputMap.get("searchInput").toString().trim().isEmpty()) {
        query.append("AND tum.name LIKE ? ");
        parameters.add("%" + inputMap.get("searchInput").toString().trim() + "%");
    }

    // Add ordering by access block ID
    query.append("ORDER BY tabr.access_block_id DESC");

    // Execute query and return results
    return getListOfLinkedHashHashMap(parameters, query.toString(), con);
}


public LinkedHashMap<String, String> getAccessblockDetails(long accessblockId, Connection con) throws SQLException
		 {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(accessblockId);

		return getMap(parameters,
				"select * from trn_access_block_register where access_block_id=?",
				con);
	}

	public long saveAccessBlockEntry(HashMap<String, Object> hm, Connection conWithF) throws Exception {

		HashMap<String, Object> valuesMap = new HashMap<>();
		valuesMap.put("access_block_id", "~default");
		valuesMap.put("employee_id", hm.get("hdnselectedemployee"));
		valuesMap.put("remarks", hm.get("txtremarks"));
		valuesMap.put("updated_by", hm.get("user_id"));
		valuesMap.put("updated_date", "~sysdate()");
		valuesMap.put("activate_flag", "~1");
		Query q = new Query("trn_access_block_register", "insert", valuesMap);
		return insertUpdateEnhanced(q, conWithF);

	}


	public String deleteAccessBlockEntry(long accessblockId, String userId, Connection conWithF) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();

		parameters.add(userId);
		parameters.add(accessblockId);
		insertUpdateDuablDB("UPDATE trn_access_block_register  SET activate_flag=0,updated_date=SYSDATE(),updated_by=? WHERE access_block_id=?",
				parameters, conWithF);
		return "Access Block Entry Deleted Succesfully";
	}

	public String getInVisitorsCount(Connection con) throws SQLException {

		ArrayList<Object> parameters = new ArrayList<>();		
		String query="" + 
		"select\n" + 
		"count(1) visitorsInside\n" + 
		"from\n" + 
		"visitor_entry ve\n" + 
		"where\n" + 
		"checkin_time is not null\n" + 
		"and checkout_time is null\n" + 
		"and activate_flag =1;\n";

		return getMap(parameters,
		query, con).get("visitorsInside");
		

	}

	public List<LinkedHashMap<String, Object>> getInEmployeesCount(Connection con) throws SQLException, ClassNotFoundException {

		ArrayList<Object> parameters = new ArrayList<>();		
		String query="select count(1) insideCount,type from trn_checkin_register tcr2,tbl_user_mst tum2 ,\n" + 
		"(select max(checked_time) c1time,tcr.user_id  from tbl_user_mst tum, trn_checkin_register tcr\n" + 
		"where tum.user_id =tcr.user_id group by tcr.user_id) as T\n" + 
		"where tcr2.user_id =T.user_id and tcr2.checked_time =T.c1time and tcr2.check_in_type ='I'\n" + 
		"and T.user_id=tum2.user_id\n" + 
		"group by type order by type\n";

		return getListOfLinkedHashHashMap(parameters,
		query, con);
		

	}

	public List<LinkedHashMap<String, Object>> getEmployeesInside(Connection con,String permanentFlag) throws SQLException, ClassNotFoundException {

		ArrayList<Object> parameters = new ArrayList<>();		
		String query="SELECT t1.user_id, t1.check_in_type, t1.checked_time,tum.name,tum.username,tum.mobile,tum2.name supervisor_name,tum.qr_code,tum.type \n" + 
		"FROM trn_checkin_register t1\n" + 
		"JOIN (\n" + 
		"SELECT user_id, MAX(checked_time) AS latest_checked_time\n" + 
		"FROM trn_checkin_register\n" + 
		"GROUP BY user_id\n" + 
		") t2\n" + 
		"ON t1.user_id = t2.user_id AND t1.checked_time = t2.latest_checked_time \n" + 
		"inner join tbl_user_mst tum on tum.user_id =t2.user_id \n" + 
		"inner join tbl_user_mst tum2 on tum2.user_id =tum.parent_user_id\n" + 
		"WHERE t1.check_in_type = 'I'  \n";

		if(permanentFlag.equals("1"))
		{
			query+=" and tum.`type` ='Permanent' ";
		}
		else
		{
			query+=" and tum.`type` ='Contract' ";
		}

		query+=" order by checked_time desc ";


		



		return getListOfLinkedHashHashMap(parameters,
		query, con);
		

	}
	public List<LinkedHashMap<String, Object>> getVendorMaster(Connection con,String searchString)
	throws ClassNotFoundException, SQLException {				

		ArrayList<Object> parameters = new ArrayList<>();
		
			String query="select * from mst_vendor where activate_flag=1";
		
		
		
		if (searchString != null && !searchString.trim().isEmpty()) {
				parameters.add("%" + searchString + "%");
				parameters.add("%" + searchString + "%");
				query += " AND (vendor_name LIKE ? OR contact_no1 LIKE ?)";
			}
		return getListOfLinkedHashHashMap(parameters,query,con);

		}





		public LinkedHashMap<String, String> getVendorDetails(long vendorId, Connection con) throws SQLException {

		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(vendorId);
		return getMap(parameters,
			"select * from mst_vendor where vendor_id=?", con);

		}	
		
		public LinkedHashMap<String, String> getVendorDetailsByVendorCode(String vendorCode, Connection con) throws SQLException {

			ArrayList<Object> parameters = new ArrayList<>();
			parameters.add(vendorCode);
			return getMap(parameters,
				"select * from mst_vendor where qr_code=?", con);
	
			}	
		
		public long addVendor(Connection conWithF, HashMap<String, Object> hm) throws SQLException {

		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("vendorName"));
		parameters.add(hm.get("address")); 
		parameters.add(hm.get("state"));
		parameters.add(hm.get("pincode"));
		parameters.add(hm.get("contactNo1"));
		parameters.add(hm.get("contactNo2"));
		parameters.add(hm.get("email"));
		parameters.add(hm.get("gst"));
		parameters.add(hm.get("contactPerson"));
		parameters.add(hm.get("panNo"));
		parameters.add(hm.get("ismsme"));
		parameters.add(hm.get("uAdhaar"));
		parameters.add(hm.get("qrCode"));
		parameters.add(hm.get("firmName"));
		String insertQuery = "insert into mst_vendor values (default,?,?,?,?,?,?,?,?,?,?,?,?,1,?,?)";	

		return insertUpdateDuablDB(insertQuery, parameters, conWithF);

		}
		public String updateVendor(long vendorId, Connection conWithF, HashMap<String, Object> hm) throws SQLException {

		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("vendorName"));
		parameters.add( hm.get("address"));
		parameters.add( hm.get("state"));
		parameters.add(hm.get("pincode"));
		parameters.add(hm.get("contactNo1"));
		parameters.add(hm.get("contactNo2"));
		parameters.add(hm.get("email"));
		parameters.add(hm.get("gst"));
		parameters.add(hm.get("contactPerson"));
		parameters.add(hm.get("panNo"));
		parameters.add(hm.get("ismsme"));
		parameters.add(hm.get("uAdhaar"));
		parameters.add(hm.get("qrCode"));
		parameters.add(hm.get("firmName"));

		parameters.add(vendorId);
		insertUpdateDuablDB(
				"UPDATE mst_vendor  SET vendor_name=?,address=?, state = ?, pincode=?,contact_no1=?,contact_no2=?,email=?,gst=?,contact_person=?,pan_no=?,ismsme=?,u_adhaar=?,qr_code=?,firm_name=?  WHERE vendor_id=?",
				parameters, conWithF);
		return "Vendor Updated Succesfully";

		}

		public String deleteVendor(long vendorId, Connection conWithF) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(vendorId);

		insertUpdateDuablDB("UPDATE mst_vendor  SET activate_flag=0 WHERE vendor_id=?",
				parameters, conWithF);
		return "Vendor  Deleted Succesfully";
		}

		public String deleteVendorCheckIn(long vendorId, Connection conWithF) throws Exception {
			ArrayList<Object> parameters = new ArrayList<>();
			parameters.add(vendorId);
	
			insertUpdateDuablDB("UPDATE vendor_check_in_register  SET activate_flag=0 WHERE vendor_checkin_id=?",
					parameters, conWithF);
			return "Vendor CheckIn Deleted Succesfully";
			}
			

		public List<LinkedHashMap<String, Object>> getCountriesList(Connection con) throws SQLException, ClassNotFoundException 
	{
		
		
		ArrayList<Object> parameters = new ArrayList<>();
		String query="select country_id countryId,country_name countryName from cmn_country_mst";
		return getListOfLinkedHashHashMap(parameters, query, con);		
		
	}

	public List<LinkedHashMap<String, Object>> showIVisitorsInside(HashMap<String, Object> hm, Connection con)
			throws ClassNotFoundException, SQLException, ParseException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("app_id"));
		
		return getListOfLinkedHashHashMap(parameters,
		"select\n" + 
		"ve.*,tum.*,\n" + 
		"(\n" + 
		"select group_concat(attachment_id) from tbl_attachment_mst tam where file_id =ve.visitor_id\n" + 
		") as attachmentIds\n" + 
		"from\n" + 
		"visitor_entry ve\n" + 
		"left outer join tbl_user_mst tum on tum.user_id=ve.contact_to_employee\n" + 
		"where\n" + 
		"ve.app_id =? and ve.activate_flag = 1\n" + 
		"and checkout_time is null \n" + 
		"order by\n" + 
		"in_time desc\n",
				con);

	}

	public long addPrefilledVisitor(Connection conWithF, HashMap<String, Object> hm) throws SQLException {

		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("visitorname"));
		parameters.add( hm.get("address"));
		parameters.add( hm.get("purpose_of_visit"));		
		parameters.add( hm.get("remarks"));	
		parameters.add( hm.get("MobileNo"));	
		parameters.add( hm.get("EmailId"));	
		parameters.add( hm.get("app_id"));
		parameters.add( hm.get("hdnselectedemployee"));
		
		
		String insertQuery = "insert into trn_prefilled_visitor values (default,?,?,?,?,?,?,?,sysdate(),1,?)";
		
		return insertUpdateDuablDB(insertQuery, parameters, conWithF);

	}
			
		public List<LinkedHashMap<String, Object>> showPrefilledVisitors(HashMap<String, Object> hm, Connection con)
		throws ClassNotFoundException, SQLException, ParseException {
	ArrayList<Object> parameters = new ArrayList<>();
	parameters.add(hm.get("app_id"));
	parameters.add(getDateASYYYYMMDD(hm.get("txtfromdate").toString()));
	parameters.add(getDateASYYYYMMDD(hm.get("txttodate").toString()));
	return getListOfLinkedHashHashMap(parameters,
	"select\n" + 
	"tpv.*,tum.*,\n" + 
	"(\n" + 
	"select group_concat(attachment_id) from tbl_attachment_mst tam where file_id =tpv.prefilled_visitor_id\n" + 
	") as attachmentIds\n" + 
	"from\n" + 
	"trn_prefilled_visitor tpv\n" + 
	"left outer join tbl_user_mst tum on tum.user_id=tpv.contact_to_employee\n" + 
	"where\n" + 
	"tpv.app_id =? and tpv.activate_flag = 1\n" + 
	"and date(in_time) between ? and ?\n" + 
	"order by\n" + 
	"in_time desc\n",
			con);

	}

	public String deletePrefilledVisitor(long visitorId, Connection conWithF) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(visitorId);
		
		insertUpdateDuablDB("UPDATE trn_prefilled_visitor  SET activate_flag=0 WHERE prefilled_visitor_id=?",
				parameters, conWithF);
		return "Prefilled Visitor deleted Succesfully";
	}
	
	public String checkoutPrefilledVisitor(long visitorId, Connection conWithF) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(visitorId);
		
		insertUpdateDuablDB("UPDATE prefilled_visitor  SET checkout_time=sysdate() WHERE prefilled_visitor_id=?",
				parameters, conWithF);
		return "Prefilled Visitor checkout Succesfully";
	}
	
	public LinkedHashMap<String, String> getprefilledvisitorDetails(long VisitorId, Connection con) throws SQLException {

		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(VisitorId);
		return getMap(parameters,
				"select * from trn_prefilled_visitor tpv , tbl_user_mst tum where tum.user_id =tpv.contact_to_employee and prefilled_visitor_id=? and  tpv.activate_flag=1", con);
		

	}

	public long addGatepass(Connection con, HashMap<String, Object> hm) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		
		
		parameters.add(hm.get("pass_no"));
		parameters.add( hm.get("hdnselectedemployee"));
		parameters.add(getDateASYYYYMMDD(hm.get("txtdate").toString()));

		parameters.add(hm.get("reason"));
	
		parameters.add(hm.get("request_raised_by"));
		

		String query="insert into trn_gate_pass values (default,?,?,?,?,null,null,?,null,sysdate(),1)";

		
		
		return insertUpdateDuablDB(query, parameters,
				con);
	}

	public String getPassNo(Connection con,boolean updateflag) throws Exception
	{	
		String seqName="Gate-Pass-"+getCurrentMonth(con)+"-"+getCurrentYear(con);
			 long uniqueNo=getSequenceNumberWithoutAppid(con,updateflag,seqName);
			 int uniqueNoInt=(int) uniqueNo;
			 seqName+="-"+String.format("%03d",uniqueNoInt);
			 return seqName;
	}

	public List<LinkedHashMap<String, Object>> getGatepass(HashMap<String, Object> hm,Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		return getListOfLinkedHashHashMap(parameters,
				"select * from trn_gate_pass where activate_flag=1",
				con);


	}

	public List<LinkedHashMap<String, Object>> getGatepassRegister(String fromDate,String toDate,Connection con)
						throws SQLException, ClassNotFoundException, ParseException {
					ArrayList<Object> parameters = new ArrayList<>();
					parameters.add((getDateASYYYYMMDD(fromDate)));
					parameters.add((getDateASYYYYMMDD(toDate)));
					return getListOfLinkedHashHashMap(parameters,
"select *,date_format (gate_pass_date , '%d/%m/%Y') gate_pass_date, tum2.name requesRaisedBy, tum3.name requestApprovedBy from tbl_user_mst tum,tbl_user_mst tum2,trn_gate_pass tgp \tleft outer join tbl_user_mst tum3 on tum3.user_id=tgp.request_approved_by where tgp.user_id = tum.user_id and tum2.user_id =tgp.request_raised_by and gate_pass_date between ? and ? and tgp.activate_flag = 1",
							
							con); 
				}
				
				public String approveGatepass(long gate_pass_id,String requestApprovedBy, Connection conWithF) throws Exception {
					ArrayList<Object> parameters = new ArrayList<>();
					parameters.add(requestApprovedBy);

					parameters.add(gate_pass_id);
					insertUpdateDuablDB("UPDATE trn_gate_pass  SET request_approved_by=? WHERE gate_pass_id=?", parameters, conWithF);
					return "Approved Succesfully";
				}




					public String deleteGatepass(long gate_pass_id, Connection conWithF) throws Exception {
						ArrayList<Object> parameters = new ArrayList<>();
						parameters.add(gate_pass_id);
						insertUpdateDuablDB("UPDATE trn_gate_pass SET activate_flag=0 WHERE gate_pass_id=?",
								parameters, conWithF);
						return "Gate Pass Deleted Succesfully";
					}


					// public String getLastCheckInType(String userId, Connection con) throws SQLException 
					// {	
					// 	ArrayList<Object> parameters = new ArrayList<>();
					// 	parameters.add(userId);
					// 	HashMap<String, String> hm=getMap(parameters, "select check_in_type from trn_checkin_register where user_id=? and date(checked_time)=curdate() order by checked_time desc", con);		
					// 	return hm.isEmpty()?"O":hm.get("check_in_type");
					// }


					public List<LinkedHashMap<String, Object>> getGatePassSummary(String fromDate,String toDate,String empId,Connection con)
						throws SQLException, ClassNotFoundException, ParseException {
					ArrayList<Object> parameters = new ArrayList<>();
					String query="select *,date_format (gate_pass_date , '%d/%m/%Y') gate_pass_date, tum2.name requesRaisedBy, tum3.name requestApprovedBy,CONCAT(\n" + //
												"        FLOOR(TIMESTAMPDIFF(SECOND, out_time, in_time) / 3600), ' hours ',\n" + //
												"        FLOOR((TIMESTAMPDIFF(SECOND, out_time, in_time) % 3600) / 60), ' minutes ',\n" + //
												"        TIMESTAMPDIFF(SECOND, out_time, in_time) % 60, ' seconds'\n" + //
												"    ) AS Hour1  "
					+"from tbl_user_mst tum,tbl_user_mst tum2,trn_gate_pass tgp \tleft outer join tbl_user_mst tum3 on tum3.user_id=tgp.request_approved_by where tgp.user_id = tum.user_id and tum2.user_id =tgp.request_raised_by and gate_pass_date between ? and ? and tgp.activate_flag = 1";
					parameters.add((getDateASYYYYMMDD(fromDate)));
					parameters.add((getDateASYYYYMMDD(toDate)));


					if(empId!=null && !empId.equals(""))
					{
						query+=" and tgp.user_id=? ";
						parameters.add(empId);
					}

					return getListOfLinkedHashHashMap(parameters,query,con); 
				}


			public List<LinkedHashMap<String, Object>> getLast24HourNotCheckedEmployees(Connection con) throws SQLException, ClassNotFoundException, ParseException 
			{
				ArrayList<Object> parameters = new ArrayList<>();		
				String sqlQuery = 
				"WITH RECURSIVE date_range AS ( " +
				"    SELECT CURDATE() - INTERVAL 1 DAY AS absent_date " + // Start with yesterday
				"    UNION ALL " +
				"    SELECT absent_date - INTERVAL 1 DAY " +
				"    FROM date_range " +
				"    WHERE absent_date > CURDATE() - INTERVAL 1 MONTH " +
				") " +
				"SELECT " +
				"    u.user_id, " +
				"    u.name, " +
				"    d.absent_date AS latest_absent_date, " +
				"    u.qr_code " +
				"FROM " +
				"    tbl_user_mst u " +
				"CROSS JOIN date_range d " +
				"LEFT JOIN trn_checkin_register c ON " +
				"    u.user_id = c.user_id " +
				"    AND DATE(c.checked_time) = d.absent_date " +
				"LEFT JOIN trn_access_block_register b ON " +
				"    u.user_id = b.employee_id " +
				"    AND b.activate_flag = 1 " +
				"LEFT JOIN trn_leave_register l ON " +
				"    u.user_id = l.employee_id " +
				"    AND l.activate_flag = 1 " +
				"    AND d.absent_date BETWEEN l.from_date AND l.to_date " +
				"LEFT JOIN holiday_master h ON " +
				"    d.absent_date = h.holiday_date " +
				"    AND h.activate_flag = 1 " +
				"WHERE " +
				"    c.check_in_id IS NULL " +
				"    AND b.employee_id IS NULL " +
				"    AND h.holiday_id IS NULL " +
				"    AND l.leave_id IS NULL " +
				"    AND u.activate_flag = 1 " +
				"ORDER BY " +
				"    c.checked_time DESC, " +
				"    u.user_id, " +
				"    d.absent_date DESC;";


				
				return getListOfLinkedHashHashMap(parameters,sqlQuery,con); 
			}

			public List<LinkedHashMap<String, Object>> getPresentSummary(String date,Connection con) throws SQLException, ClassNotFoundException, ParseException 
			{
				ArrayList<Object> parameters = new ArrayList<>();		
				parameters.add(getDateASYYYYMMDD(date));
				String query="SELECT\n" + 
				"u.type,\n" + 
				"COUNT(u.user_id) AS cnt\n" + 
				"FROM\n" + 
				"tbl_user_mst u\n" + 
				"JOIN\n" + 
				"trn_checkin_register c ON u.user_id = c.user_id\n" + 
				"WHERE\n" + 
				"DATE(c.checked_time) = ? and check_in_type='I' \n" + 
				"GROUP BY\n" + 
				"u.type\n";
				return getListOfLinkedHashHashMap(parameters,query,con); 
			}


			public List<LinkedHashMap<String, Object>> getEmployeeOnLeaveSummary(String date,Connection con) throws SQLException, ClassNotFoundException, ParseException 
			{
				ArrayList<Object> parameters = new ArrayList<>();		
				parameters.add(getDateASYYYYMMDD(date));
				String query="SELECT\n" + 
						"u.type,\n" + 
						"COUNT(u.user_id) AS cnt\n" + 
						"FROM\n" + 
						"tbl_user_mst u\n" + 
						"JOIN\n" + 
						"trn_leave_register l ON u.user_id = l.employee_id\n" + 
						"WHERE\n" + 
						"l.activate_flag = 1\n" + 
						"AND ? BETWEEN l.from_date AND l.to_date -- Replace with the date parameter\n" + 
						"GROUP BY\n" + 
						"u.type;\n";
				return getListOfLinkedHashHashMap(parameters,query,con); 
			}

			public List<LinkedHashMap<String, Object>> getEmployeeOnLeaveWithoutIntimation(String date,Connection con) throws SQLException, ClassNotFoundException, ParseException 
			{
				ArrayList<Object> parameters = new ArrayList<>();		
				parameters.add(getDateASYYYYMMDD(date));
				parameters.add(getDateASYYYYMMDD(date));
				String query="SELECT\n" + 
"u.type,\n" + 
"COUNT(u.user_id) AS cnt\n" + 
"FROM\n" + 
"tbl_user_mst u\n" + 
"LEFT JOIN\n" + 
"trn_leave_register l ON u.user_id = l.employee_id\n" + 
"AND ? BETWEEN l.from_date AND l.to_date -- Check if the specified date is within the leave period\n" + 
"LEFT JOIN\n" + 
"trn_checkin_register c ON u.user_id = c.user_id\n" + 
"AND DATE(c.checked_time) = ? -- Check if the specified date has a check-in record\n" + 
"WHERE\n" + 
"l.leave_id IS NULL -- No leave record found\n" + 
"AND c.check_in_id IS NULL -- No check-in record found\n" + 
"GROUP BY\n" + 
"u.type;";;
				return getListOfLinkedHashHashMap(parameters,query,con); 
			}


			public boolean checkIfEmployeeAlreadyBlocked(String employeeId, Connection conWithF) throws SQLException {
				ArrayList<Object> parameters = new ArrayList<>();
				parameters.add(employeeId);				
				return getMap(parameters,
						"select count(1) cnt from trn_access_block_register where employee_id=? and activate_flag=1",
						conWithF).get("cnt").equals("0");
		
			}


			
				public List<LinkedHashMap<String, Object>> getDepartmentMaster(HashMap<String, Object> hm,Connection con)
				throws ClassNotFoundException, SQLException {
			ArrayList<Object> parameters = new ArrayList<>();
			return getListOfLinkedHashHashMap(parameters,
					"select * from department_master where activate_flag=1",
					con);


	}

	public LinkedHashMap<String, String> getDepartmentDetails(HashMap<String, Object> hm, Connection con) throws SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("department_id"));
		
		
		return getMap(parameters,
				"select * from department_master where department_id=?",
				con);
	}

	public long addDepartment(Connection con, HashMap<String, Object> hm) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		
		
		String query="insert into department_master values (default,?,?,sysdate(),1)";
		parameters.add(hm.get("txtdepartmentname"));
		parameters.add(hm.get("user_id"));
		
		
		return insertUpdateDuablDB(query, parameters,
				con);
	}

	public String updateDepartment(long departmentId, Connection con, String departmentName,String updatedBy) throws Exception {

		ArrayList<Object> parameters = new ArrayList<>();
		
		
		parameters.add(departmentName);
		parameters.add(updatedBy);

		parameters.add(departmentId);
		

		insertUpdateDuablDB("UPDATE department_master SET department_name=?,updated_by=?,updated_date=SYSDATE() WHERE department_id=?",
				parameters, con);
		return "Department updated Succesfully";

	}

	public String deleteDepartment(long departmentId,String userId, Connection conWithF) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		
		parameters.add(userId);
		parameters.add(departmentId);
		insertUpdateDuablDB("UPDATE department_master  SET activate_flag=0,updated_by=?,updated_date=SYSDATE() WHERE department_id=?",
				parameters, conWithF);
		return "Department deleted Succesfully";
	}

				
			public List<LinkedHashMap<String, Object>> getAbbreviationMaster(HashMap<String, Object> hm,Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		return getListOfLinkedHashHashMap(parameters,
				"select * from abbreviation_master where activate_flag=1 order by sequence_no",
				con);


      }




	  public LinkedHashMap<String, String> getAbbreviationDetails(HashMap<String, Object> hm, Connection con) throws SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("abbreviation_id"));
		
		
		return getMap(parameters,
				"select * from abbreviation_master where abbreviation_id=?",
				con);
	}
	public long addAbbreviation(Connection con, HashMap<String, Object> hm) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		
		
		String query="insert into abbreviation_master values (default,?,?,?,?,?,sysdate(),1)";
		parameters.add(hm.get("txtabbreviation"));
		parameters.add(hm.get("txtfullform"));
		parameters.add(hm.get("txtdescription"));
		parameters.add(hm.get("txtsequenceno"));

		parameters.add(hm.get("user_id"));
		
		
		return insertUpdateDuablDB(query, parameters,
				con);
	}

	public String updateAbbreviation(long abbreviationId, Connection con, String abbreviation,String full_form,String description,String sequence_no,String updatedBy) throws Exception {

		ArrayList<Object> parameters = new ArrayList<>();
		
		
		parameters.add(abbreviation);
		parameters.add(full_form);

		parameters.add(description);

		parameters.add(sequence_no);

		parameters.add(updatedBy);

		parameters.add(abbreviationId);
		

		insertUpdateDuablDB("UPDATE abbreviation_master SET abbreviation=?,full_form=?,description=?,sequence_no=?,updated_by=?,updated_date=SYSDATE() WHERE abbreviation_id=?",
				parameters, con);
		return "Abbreviation updated Succesfully";

	}

	public String deleteAbbreviation(long abbreviationId,String userId, Connection conWithF) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		
		parameters.add(userId);
		parameters.add(abbreviationId);
		insertUpdateDuablDB("UPDATE abbreviation_master  SET activate_flag=0,updated_by=?,updated_date=SYSDATE() WHERE abbreviation_id=?",
				parameters, conWithF);
		return "Abbreviation deleted Succesfully";
	}

			public List<LinkedHashMap<String, Object>> getLevelMaster(HashMap<String, Object> hm,Connection con)
			throws ClassNotFoundException, SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		return getListOfLinkedHashHashMap(parameters,
				"select * from level_master where activate_flag=1",
				con);


    }


	public LinkedHashMap<String, String> getLevelDetails(HashMap<String, Object> hm, Connection con) throws SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("level_id"));
		
		
		return getMap(parameters,
				"select * from level_master where level_id=?",
				con);
	}

	public long addLevel(Connection con, HashMap<String, Object> hm) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		
		
		String query="insert into level_master values (default,?,?,?,sysdate(),1)";
		parameters.add(hm.get("txtlevelname"));
		parameters.add(hm.get("txtlevelno"));

		parameters.add(hm.get("user_id"));
		
		
		return insertUpdateDuablDB(query, parameters,
				con);
	}

	public String updateLevel(long levelId, Connection con, String level_name,String level_no,String updatedBy) throws Exception {

		ArrayList<Object> parameters = new ArrayList<>();
		
		
		parameters.add(level_name);
		parameters.add(level_no);

		parameters.add(updatedBy);

		parameters.add(levelId);
		

		insertUpdateDuablDB("UPDATE level_master SET level_name=?,level_no=?,updated_by=?,updated_date=SYSDATE() WHERE level_id=?",
				parameters, con);
		return "Level updated Succesfully";

	}

	public String deleteLevel(long levelId,String userId, Connection conWithF) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		
		parameters.add(userId);
		parameters.add(levelId);
		insertUpdateDuablDB("UPDATE level_master  SET activate_flag=0,updated_by=?,updated_date=SYSDATE() WHERE level_id=?",
				parameters, conWithF);
		return "Level deleted Succesfully";
	}


	public List<LinkedHashMap<String, Object>> getDocumentMaster(HashMap<String, Object> hm,Connection con,String searchString)
	throws ClassNotFoundException, SQLException {
ArrayList<Object> parameters = new ArrayList<>();
String query="select md.*,tam.file_name actualPath,dm.*,dgm.* from mst_document md "+
		"left outer join tbl_attachment_mst tam on tam.file_id=md.document_id "+
		"left outer join department_master dm on dm.department_id=md.document_department_id "+
		"left outer join document_group_master dgm on dgm.document_group_id=md.document_group_id"+
		" where md.activate_flag=1 ";
	
		if(hm.get("document_group_id")!=null && !hm.get("document_group_id").equals("-1") && !hm.get("document_group_id").equals(""))
		{
			query+=" and dgm.document_group_id=?";
			parameters.add(hm.get("document_group_id"));
		}

		if(hm.get("department_id")!=null && !hm.get("department_id").equals("-1") && !hm.get("department_id").equals(""))
		{
			query+=" and dm.department_id=?";
			parameters.add(hm.get("department_id"));
		}
		if (searchString != null && !searchString.isEmpty()) {
			query += " AND (md.document_name LIKE ? OR md.document_code LIKE ?)";
			parameters.add("%" + searchString + "%");
			parameters.add("%" + searchString + "%");
		}
		
return getListOfLinkedHashHashMap(parameters,
query,
		con);


}

public List<LinkedHashMap<String, Object>> getDocumentHistory(HashMap<String, Object> hm,Connection con)
	throws ClassNotFoundException, SQLException {
ArrayList<Object> parameters = new ArrayList<>();
String query="select md.*,tam.file_name actualPath,dm.*,dgm.* from hst_mst_document md "+
		"left outer join tbl_attachment_mst tam on tam.file_id=md.document_id "+
		"left outer join department_master dm on dm.department_id=md.document_department_id "+
		"left outer join document_group_master dgm on dgm.document_group_id=md.document_group_id"+
		" where md.activate_flag=1 ";
	
		if(hm.get("document_id")!=null && !hm.get("document_id").equals("-1") && !hm.get("document_id").equals(""))
		{
			query+=" and md.document_id=?";
			parameters.add(hm.get("document_id"));
		}

		query+=" order by md.created_date desc ";

		
return getListOfLinkedHashHashMap(parameters,
query,
		con);


}

public LinkedHashMap<String, String> getDocumentDetails(HashMap<String, Object> hm, Connection con) throws SQLException {
	ArrayList<Object> parameters = new ArrayList<>();
	parameters.add(hm.get("documentId"));
	
	
	return getMap(parameters,
			"select md.*,tam.file_name,tam.attachment_id from mst_document md left outer join tbl_attachment_mst tam on tam.file_id=md.document_id where document_id=?",
			con);
}

public String updateDocumentStatus(Connection con, HashMap<String, Object> hm) throws Exception {
    ArrayList<Object> parameters = new ArrayList<>();
    parameters.add(hm.get("new_status"));
    parameters.add(hm.get("user_id"));
    parameters.add(hm.get("document_id"));

    insertUpdateDuablDB(
        "UPDATE mst_document SET current_status=?, updated_by=?, updated_date=NOW() WHERE document_id=?", 
        parameters, 
        con
    );

    return "Document status updated successfully.";
}
public String deleteDocument(long documentId,String userId, Connection conWithF) throws Exception {
	ArrayList<Object> parameters = new ArrayList<>();
	
	parameters.add(userId);
	parameters.add(documentId);
	insertUpdateDuablDB("UPDATE mst_document  SET activate_flag=0,updated_by=?,updated_date=SYSDATE() WHERE document_id=?",
			parameters, conWithF);
	return "Document deleted Succesfully";
}





		public List<LinkedHashMap<String, Object>> getDocumentGroupMaster(HashMap<String, Object> hm,Connection con)
		throws ClassNotFoundException, SQLException {
	ArrayList<Object> parameters = new ArrayList<>();
	return getListOfLinkedHashHashMap(parameters,
			"select * from  mst_doc_group where activate_flag=1",
			con);


	}

	public LinkedHashMap<String, String> getDocumentGroupDetails(HashMap<String, Object> hm, Connection con) throws SQLException {
		ArrayList<Object> parameters = new ArrayList<>();
		parameters.add(hm.get("document_group_id"));
		
		
		return getMap(parameters,
				"select * from  mst_doc_group where document_group_id=?",
				con);
	}

	public long addDocumentGroup(Connection con, HashMap<String, Object> hm) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		
		
		String query="insert into  mst_doc_group values (default,?,?,?,sysdate(),1)";
		parameters.add(hm.get("txtgroupname"));
		parameters.add(hm.get("txtgroupshortname"));
		parameters.add(hm.get("user_id"));
		
		
		return insertUpdateDuablDB(query, parameters,
				con);
	}

	public String updateDocumentGroup(long documentgroupId, Connection con, String groupName,String groupshortName,String updatedBy) throws Exception {

		ArrayList<Object> parameters = new ArrayList<>();
		
		
		parameters.add(groupName);
		parameters.add(groupshortName);
		parameters.add(updatedBy);

		parameters.add(documentgroupId);
		

		insertUpdateDuablDB("UPDATE  mst_doc_group SET group_name=?,group_short_name=?,updated_by=?,updated_date=SYSDATE() WHERE document_group_id=?",
				parameters, con);
		return "Document Group updated Succesfully";

	}


	public String deleteDocumentGroup(long documentgroupId,String userId, Connection conWithF) throws Exception {
		ArrayList<Object> parameters = new ArrayList<>();
		
		parameters.add(userId);
		parameters.add(documentgroupId);
		insertUpdateDuablDB("UPDATE  mst_doc_group  SET activate_flag=0,updated_by=?,updated_date=SYSDATE() WHERE document_group_id=?",
				parameters, conWithF);
		return "Document Group deleted Succesfully";
	}

	public List<LinkedHashMap<String, Object>> getDocumentGroupMasterNew(HashMap<String, Object> hm, Connection con)
        throws ClassNotFoundException, SQLException {
    ArrayList<Object> parameters = new ArrayList<>();
    String query = "SELECT * FROM  mst_doc_group WHERE activate_flag = 1";

    if (hm.get("document_group_id") != null && !hm.get("document_group_id").toString().isEmpty()) {
        query += " AND document_group_id = ?";
        parameters.add(hm.get("document_group_id"));
    }

    return getListOfLinkedHashHashMap(parameters, query, con);
}


	}
