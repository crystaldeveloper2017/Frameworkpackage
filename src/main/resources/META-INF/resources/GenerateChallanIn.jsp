<style>
	th
	{
		vertical-align:middle!important;
	}
	td
	{
		vertical-align:middle!important;
	}
</style>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
           
           
           



<c:set var="itemList" value='${requestScope["outputObject"].get("itemList")}' />
<c:set var="todaysDate" value='${requestScope["outputObject"].get("todaysDate")}' />
<c:set var="invoiceDetails" value='${requestScope["outputObject"].get("invoiceDetails")}' />
<c:set var="tentativeSerialNo" value='${requestScope["outputObject"].get("tentativeSerialNo")}' />
<c:set var="vendorList" value='${requestScope["outputObject"].get("vendorList")}' />
<c:set var="listwarehouse" value='${requestScope["outputObject"].get("listwarehouse")}' />




   





</head>


<script>


function saveInvoice()
{

	
	
	var rows=tblitems.rows;
	
	var requiredDetails=[];
	 
	var arr = [];
	var itemString="";
	var confirmMessage="";
	var proceedFlag=true;
	var messageToShow="";
	
	
	
	var warehousegroup=document.getElementsByName('warehousegroup');
	var quantityTextboxes=document.getElementsByName('txtqtygroup');
	var pricesTextboxes=document.getElementsByName('txtpricesgroup');
	var gstTextboxes=document.getElementsByName('txtgstamountgroup');
	var totalAmountTextboxes=document.getElementsByName('txttotalamountgroup');
	
	for (var x= 1; x < rows.length; x++) 
	{   
	    // ID,WAREHOUSEID,JOBSHEETNO, QTY
	    itemString+=
	    	rows[x].childNodes[0].childNodes[1].value+ 
	    "~"+Number(rows[x].childNodes[2].childNodes[1].value)+
	    "~"+(rows[x].childNodes[3].childNodes[0].value)+
	    "~"+Number(rows[x].childNodes[4].childNodes[1].value)+
	    "~"+rows[x].childNodes[1].childNodes[0].innerHTML+
	    "|";       
	}
	
	var reqString="customer_id="+hdnSelectedCustomer.value+	
	"&invoice_date="+txtinvoicedate.value+
	"&remarks="+txtremarks.value+
	"&hdnPreviousInvoiceId="+hdnPreviousInvoiceId.value+
	"&txtvendorchallanno="+txtvendorchallanno.value+	
	"&itemDetails="+itemString;
	
	
	
	  var xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() {
	    if (this.readyState == 4 && this.status == 200) {
	      var arr=this.responseText.split("~");
	      alert(arr[0]+ " Invoice Saved Succesfully" +arr[1]);
	      
	      if(typeof chkgeneratePDF !='undefined' && chkgeneratePDF.checked==true)
    		{
	      		
    			generatePdfChallanIn(arr[1]);
    			
    		}
	      
	      
	      window.location.reload();
	    }
	  };
	  xhttp.open("POST", "${masterUrl}?a=saveChallanIn", true);
	  xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	  //alert(reqString);
	  xhttp.send(encodeURI(reqString));
	
	
	
}
function savedInvoiceCallback(data1)
{
		console.log(data1);
}


function updateItem()
{
	
	document.getElementById("frm").action="?a=updateItem"; 
	document.getElementById("frm").submit();
	return;
	
	
}





function deleteAttachment(id)
{
		
		
		
		  document.getElementById("closebutton").style.display='none';
		   document.getElementById("loader").style.display='block';
		$("#myModal").modal();

		var xhttp = new XMLHttpRequest();
		  xhttp.onreadystatechange = function() 
		  {
		    if (xhttp.readyState == 4 && xhttp.status == 200) 
		    { 		      
		      document.getElementById("responseText").innerHTML=xhttp.responseText;
			  document.getElementById("closebutton").style.display='block';
			  document.getElementById("loader").style.display='none';
			  $("#myModal").modal();
		      
			  
			}
		  };
		  xhttp.open("GET","?a=deleteAttachment&attachmentId="+id, true);    
		  xhttp.send();
		
		
		
}








</script>


<div class="container" style="padding:20px;background-color:white;margin-top:5px;max-width:100%"> 

<form id="frm" action="?a=addCategory" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
<div class="row">
<datalist id="vendorList">

<c:forEach items="${vendorList}" var="customer">
			    <option id="${customer.customerId}">${customer.customerName}~${customer.mobileNumber}~${customer.customerAddress}</option>			    
	   </c:forEach>	   	 

  	
</datalist>

<datalist id="itemList">
<c:forEach items="${itemList}" var="item">
			    <option id="${item.item_id}">${item.item_name}-${item.product_code}-${item.size}-${item.color}</option>
			    <input type="hidden" id="hdn${item.item_id}" value="${item.item_name}~${item.price}~${item.gst}~${item.size}~${item.color}~${item.product_code}~${item.availableQty}">			    
	   </c:forEach>	   	   	
</datalist>


<div class="col-sm-3">
  	<div class="form-group">
  	
  	
  	
  	
  	<div class="input-group input-group-sm">
                  <input type="text" class="form-control form-control-sm" id="txtsearchcustomer"    placeholder="Search For Vendor" name="txtsearchtxtsearchcustomer"  list='vendorList' oninput="checkforMatchCustomer()">
                  
                  <span class="input-group-append">
                    <button type="button" class="btn btn-danger btn-flat" onclick="resetCustomer()">Reset</button>
                  </span>
                  
                  <span class="input-group-append">
                    <button type="button" class="btn btn-primary btn-flat" onclick="addCustomer()">Add</button>
                  </span>
    </div>
  	
  	
        	      
      
            
      <input  type="hidden" name="hdnSelectedCustomer" id="hdnSelectedCustomer" value="">
   			<input  type="hidden" name="hdnSelectedCustomerType" id="hdnSelectedCustomerType" value="">
   			<input  type="hidden" name="hdnPreviousInvoiceId" id="hdnPreviousInvoiceId" value="">
   			      
    </div>
  </div>
  
  <div class="col-sm-1">
  	<div class="form-group" align="center"> 	
  		<label>Due </label>  		
  	</div>
  </div>
   <div class="col-sm-1">
  	<div class="form-group"> 	
  		<input type="text" id="txtcustomerpendingamount" name="txtcustomerpendingamount" class="form-control form-control-sm" value="0" readonly/>  		
  	</div>
  </div>

 <div class="col-sm-1">
  	<div class="form-group" align="center"> 	
  		<label>Sales Date</label>  		
  	</div>
  </div>
  <div class="col-sm-2">
  	<div class="form-group">	
  		<input type="text" id="txtinvoicedate" name="txtinvoicedate" class="form-control form-control-sm" value="${todaysDate}" placeholder="Invoice Date" readonly/>
  	</div>
  </div>
  
  
  
  <div class="col-sm-1">
  <select class='form-control form-control-sm' id="drpwarehouse" onchange="freezeWareHouse()">
    	<option value="-1">--Select--</option>
		<c:forEach items="${listwarehouse}" var="warehouse">
  					<option value="${warehouse.ware_house_id}">${warehouse.ware_house_name }</option>
  		</c:forEach>
	</select>
  </div>
  
  <div class="col-sm-3">    
    <div class="input-group">
    <input type="text" class="form-control form-control-sm"    placeholder="Search for Items" list="itemList" id="txtitem" name="txtitem" oninput="checkforMatchItem()">
    <div class="input-group-append">
      <button class="btn btn-secondary btn-sm" type="button" >
        <i class="fa fa-search fa-sm" ></i>
      </button>
    </div>
  </div>
  </div>
  
  
  
  
  
  
  <div class="col-sm-3">
    <div class="form-group">
      <label for="email">Vendor Name</label>      
      <input type='text' readonly id="txtclientname" name="txtclientname" onkeypress="digitsOnly(event)" onkeyup="calculateTotal()" class='form-control form-control-sm'>
    </div>
  </div>
  
  <div class="col-sm-6">
    <div class="form-group">
      <label for="email">Address</label>      
      <input type='text' readonly id="txtaddress" name="txtaddress" onkeypress="digitsOnly(event)" onkeyup="calculateTotal()" class='form-control form-control-sm'>
    </div>
  </div>
  
  
  <div class="col-sm-3">
    <div class="form-group">
      <label for="email">Vendor Challan No</label>      
      <input type='text' id="txtvendorchallanno" name="txtvendorchallanno"  onkeyup="calculateTotal()" class='form-control form-control-sm'>
    </div>
  </div>
  
   
 
  <div class="col-sm-12">  
	  <div class="card-body table-responsive p-0" style="height: 370px;">                
	                <table id="tblitems"  class="table table-head-fixed  table-bordered table-striped dataTable dtr-inline" role="grid" aria-describedby="example1_info">
	                  <thead>
	                    <tr align="center">
	                     <th style="z-index:0">Sr</th>
	  			<th style="z-index:0">Item Name</th>
	  			<th style="z-index:0">Ware House</th>
	  			<th style="z-index:0">Job Sheet No</th>
	  			<th style="z-index:0">Item Qty</th>	  			
	  			
	  			<th></th>
	                    </tr>
	                  </thead>
	                </table>
	   </div>	
  </div>
  
  <div class="col-sm-12">  
	  <div class="card-body table-responsive p-0">                
	                <table id="tblTotalItems"class="table table-head-fixed  table-bordered table-striped dataTable dtr-inline" role="grid" aria-describedby="example1_info">
	                  <thead>	                   
	                  
	                  	                  	                  
	                  
	                  
	                    <tr>
	                        <th colspan="2" class="text-right">Total Quantity : <span id="totalQty">0</span></th>	                        
	                        <td colspan="6"><input type="text" id="txtremarks" name="txtremarks" class="form-control form-control-sm" placeholder="Remarks"></td>
	                         			
	                    </tr>
	                    
	                    
	                  </thead>
	                </table>
	   </div>	
  </div>
  
   
  
  <c:if test="${invoiceDetails.invoice_id eq null}">
	   	
   <div class="col-sm-12">
  	 <div class="form-group" align="center">	  
	   <input type="checkbox" class="form-check-input" id="chkgeneratePDF">
    	<label class="form-check-label" for="chkgeneratePDF">Generate PDF</label>
   </div>
   </div>
   	
	   </c:if>
	   
	   
	    <div class="col-sm-12">
  	 <div class="form-group" align="center">	  
	   <label for="email">Upload Attachment</label>
      	<input type="file" name="file" multiple/>    	
   </div>
   </div>
  
  
 <div class="col-sm-12">
  	 <div class="form-group" align="center">	  
	   	<button class="btn btn-success" type="button" onclick='saveInvoice()'>Save</button>   
	   <button class="btn btn-danger" type="reset" onclick='window.location="?a=showHomePage"'>Cancel</button>
	   <button class="btn btn-primary" id="btnRegister" type="reset" onclick='window.open("?a=showChallans&type=I&txtfromdate=${todaysDate}&txttodate=${todaysDate}&txtfirm=${userdetails.firm_id}")'>Register</button>
	   
	  	   
	   <button class="btn btn-primary" style="display:none" id="generatePDF" type="button" onclick='generateInvoice(${invoiceDetails.invoice_id});'>Generate PDF</button>
   </div>
   </div>
  
</div>
</form>





<script>

function generateInvoice(invoiceId)
{
	
	var xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() 
	  {
	    if (xhttp.readyState == 4 && xhttp.status == 200) 
	    { 		      
	    	window.open("BufferedImagesFolder/"+xhttp.responseText);		  
		}
	  };
	  xhttp.open("GET","?a=generateInvoicePDF&invoiceId="+invoiceId, false);    
	  xhttp.send();
}
function searchForCustomer(searchString)
{	
	//if(searchString.length<3){return;}

	document.getElementById("closebutton").style.display='none';
	   document.getElementById("loader").style.display='block';
	var xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() 
	  {
	    if (xhttp.readyState == 4 && xhttp.status == 200) 
	    { 		      
	    	var cusomerList=JSON.parse(xhttp.responseText);
	    	var reqString="";
	    	for(var x=0;x<cusomerList.length;x++)
	    	{
	    		//console.log(cusomerList[x]);
	    		reqString+="<option id="+cusomerList[x].customer_id+">"+cusomerList[x].customer_name+"~"+cusomerList[x].mobile_number+"~"+cusomerList[x].customer_type+"</option>";
	    	}
	    	
	    	document.getElementById('customerList').innerHTML=reqString;
		}
	  };
	  xhttp.open("GET","?a=searchForCustomer&searchString="+searchString, true);    
	  xhttp.send();
	
	 
	
}

function checkforMatchCustomer()
{
	
	var searchString= document.getElementById("txtsearchcustomer").value;	
	var options1=document.getElementById("vendorList").options;
	var customerId=0;
	for(var x=0;x<options1.length;x++)
		{
			if(searchString==options1[x].value)
				{
					customerId=options1[x].id;
					txtitem.focus();
					break;
				}
		}
	if(customerId!=0)
		{
			document.getElementById("hdnSelectedCustomer").value=customerId;			
			document.getElementById("txtsearchcustomer").disabled=true;			
			document.getElementById("hdnSelectedCustomerType").value=document.getElementById("txtsearchcustomer").value.split("~")[2];
			fetchPendingAmountForThisCustomer(customerId);
			
			var ClientDetails=document.getElementById("txtsearchcustomer").value.split("~");
			
			txtclientname.value=ClientDetails[0];
			txtaddress.value=ClientDetails[2];			
		}
	else
		{
			//searchForCustomer(searchString);
		}
	
	
	
}


function fetchPendingAmountForThisCustomer(customerId)
{
	document.getElementById("closebutton").style.display='none';
	   document.getElementById("loader").style.display='block';
	var xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() 
	  {
	    if (xhttp.readyState == 4 && xhttp.status == 200) 
	    { 		      
	    	var details=JSON.parse(xhttp.responseText);	    	
	    	if(details.pendingAmountDetails.PendingAmount!=undefined)
	    		{
	    			txtcustomerpendingamount.value=details.pendingAmountDetails.PendingAmount;
	    		}
	    	else
	    		{
					//alert("no pending amount for this customer");
	    			//window.location.reload();
	    		}
		}
	  };
	  xhttp.open("GET","?a=getPendingAmountForCustomer&customerId="+customerId, true);    
	  xhttp.send();
}


function checkforMatchItem()
{
	var searchString= document.getElementById("txtitem").value;	
	var options1=document.getElementById("itemList").options;
	
	var itemId=0;
	for(var x=0;x<options1.length;x++)
		{
			if(searchString==options1[x].value)
				{
				itemId=options1[x].id;
				
					break;
				}
		}
	if(itemId!=0)
		{			
				
				
				var total=0;
				var rows=tblitems.rows;
				for(var x=1;x<rows.length;x++)
					{							
						if(itemId==rows[x].childNodes[0].childNodes[1].value)
							{
								alert('item already exist in selection');
								document.getElementById("txtitem").value="";
								return;
							}
					}
				
				// code to check if item already exist inselection				
				getItemDetailsAndAddToTable(itemId);
				document.getElementById("txtitem").value="";
		}
	
}


function getItemDetailsAndAddToTable(itemId)
{
		      
	
	
	if(drpwarehouse.value==-1)
		{
			alert("please select ware house");
			return;
		}
		
		
		
		
		var xhttp = new XMLHttpRequest();
		  xhttp.onreadystatechange = function() 
		  {
		    if (xhttp.readyState == 4 && xhttp.status == 200) 
		    { 	
			    	
		    	//alert(xhttp.responseText);
		    	var itemDetails=JSON.parse(xhttp.responseText);
		    	console.log(itemDetails);
		    	
		    	
		    	var table = document.getElementById("tblitems");	    	
		    	var row = table.insertRow(-1);	    	
		    	var cell1 = row.insertCell(0);
		    	var cell2 = row.insertCell(1);
		    	var cell3 = row.insertCell(2);
		    	
		    	var cell4 = row.insertCell(3);
		    	var cell5 = row.insertCell(4);
		    	
		    	var cell6 = row.insertCell(5);
		    	
		    	
		    	
		    	
		    	
		    	
		    	cell1.innerHTML = "<div>"+Number(table.rows.length-1)+"</div>" +"<input name='itemidgroup' type='hidden' value='"+itemId+"'>";
		    	cell2.innerHTML = "<a onclick=window.open('?a=showItemHistory&itemId="+itemId+"') href='#'>"+ itemDetails.item_name + "-"+ itemDetails.product_code + "-"+ itemDetails.color + "-"+ itemDetails.size + "-"+ itemDetails.stockAvailable + "</a>";
		    	cell3.innerHTML = drpwarehouse.selectedOptions[0].text + "<input type='hidden' name='warehousegroup' value='"+drpwarehouse.selectedOptions[0].value+"' id='hdnwarehouse'"+itemId+">";
		    	cell4.innerHTML = '<input type="text" class="form-control form-control-sm" name="txtjobheetgroup" id="txtjobsheetno"'+itemId+'>';
		    	cell5.innerHTML = " <input type='text' class='form-control form-control-sm'  name='txtqtygroup' id='txtqty"+itemId+"' onkeyup='calculateAmount("+itemId+")' onkeypress='digitsOnlyWithDot(event);' value='1'> <input type='hidden' class='form-control form-control-sm'  readonly id='hdnavailableqty"+itemId+"' value="+itemDetails.stockAvailable+">";   	
		    	
		      	
		    	cell6.innerHTML = '<button type="button" class="btn btn-sm btn-danger"  onclick=removethisitem(this) id="btn11" style="cursor:pointer">Delete</button>';
		    	
		    	
		    	calculateAmount(itemId);
		    	document.getElementById("txtqty"+itemId).select();
		    	document.getElementById("txtqty"+itemId).focus();
		    	
		    	
		    	
			}
		  };
		  xhttp.open("GET","?a=getItemDetailsByAjax&itemId="+itemId+"&sourcefirmId=${userdetails.firm_id}"+"&sourceWareHouseId="+drpwarehouse.value, true);    
		  xhttp.send();	
		
		
		
	
	    	
	    	//console.log(itemDetails);
	    
	    	
			
}



function calculateAmount(itemId)
{
		
	var qty=document.getElementById('txtqty'+itemId).value;
	
	
	
	
	
	
	
	
	calculateTotal();
}



function calculateTotal()
{	
		var total=0;
		var totalQtyCalculated=0;
		var totalDiscountCalculated=0;
		var grossAmountCalculated=0;
		var gstAmountCalculated=0;
		
		
		
		
		var quantityTextboxes=document.getElementsByName('txtqtygroup');
		
		
		
		var rows=tblitems.rows;
		for(var x=0;x<rows.length-1;x++)
			{
				
				
				var itemQty=quantityTextboxes[x].value;
				totalQtyCalculated+=Number(itemQty);
				
				
				
			}
		
		//totalQty		
		
		totalQty.innerHTML=Number(totalQtyCalculated).toFixed(2);		
		
		
		
			
}

function removethisitem(btn1)
{
	btn1.parentElement.parentElement.remove();
	//reshuffle id's next to this
	
	reshuffleSrNos();
	
	calculateTotal(); 
}

function reshuffleSrNos()
{
	var rows1=tblitems.rows;
	for(var x=1;x<rows1.length;x++)
		{
			rows1[x].childNodes[0].childNodes[0].innerHTML=x;
		}
}

document.getElementById("divTitle").innerHTML="Generate Challan In:- "+"${tentativeSerialNo}";


$( "#txtinvoicedate" ).datepicker({ dateFormat: 'dd/mm/yy' });
if('${invoiceDetails.invoice_id}'!='')
	{
		
		
		

		document.getElementById("divTitle").innerHTML="Invoice No:-"+"${invoiceDetails.invoice_no}";
		hdnSelectedCustomer.value="${invoiceDetails.customer_id}"
		
			
		if('${invoiceDetails.customer_name}'!='')
			{
				txtsearchcustomer.value="${invoiceDetails.customer_name}~${invoiceDetails.mobile_number}~${invoiceDetails.customer_type}";			
			}
		
		
		document.getElementById("hdnSelectedCustomerType").value='${invoiceDetails.customer_type}';
		txtinvoicedate.value="${invoiceDetails.theInvoiceDate}";
		totalQty.innerHTML="${invoiceDetails.totalQuantities}";
		grossAmount.innerHTML="${invoiceDetails.gross_amount}";
		txtitemdiscount.innerHTML="${invoiceDetails.item_discount}";
		txtinvoicediscount.value="${invoiceDetails.invoice_discount}";
		txtpaymenttype.value="${invoiceDetails.payment_type}";
		
		totalAmount.innerHTML="${invoiceDetails.total_amount}";
		txtpaidamount.value="${invoiceDetails.paid_amount}";
		paymentTypeChanged(txtpaymenttype.value);
		txtpendingamount.innerHTML=Number(totalAmount.innerHTML)-Number(txtpaidamount.value);
		txtremarks.value='${invoiceDetails.remarks}';
		drppaymentmode.value="${invoiceDetails.payment_mode}";
		
		var m=0;
		var tableNo="";
		<c:forEach items="${invoiceDetails.listOfItems}" var="item">
		
		m++;
		tableNo='${item.table_no}';
		var table = document.getElementById("tblitems");	    	
    	var row = table.insertRow(-1);	    	
    	var cell1 = row.insertCell(0);
    	var cell2 = row.insertCell(1);
    	var cell3 = row.insertCell(2);
    	var cell4 = row.insertCell(3);
    	var cell5 = row.insertCell(4);
    	var cell6 = row.insertCell(5);
    	var cell7 = row.insertCell(6);
    	var cell8 = row.insertCell(7);
    	
    	
    	
    	cell1.innerHTML = '<div>'+m+'</div><input type="hidden" value="${item.item_id}">';    	
    	cell2.innerHTML = '${item.item_name}';
    	cell3.innerHTML = " <input type='text' class='form-control form-control-sm' id='txtqty"+${item.item_id}+"' onkeyup='calculateAmount(this);'  onkeypress='digitsOnlyWithDot(event);' value='${item.qty}'> <input type='hidden' readonly id='hdnavailableqty"+${item.item_id}+"'>";   	
    	cell4.innerHTML = '${item.rate}';
    	cell5.innerHTML = '<input typ="text" class="form-control form-control-sm" value="${item.custom_rate}" onkeyup="calculateAmount(this)" onkeypress="digitsOnlyWithDot(event)">';
    	cell6.innerHTML = Number((Number('${item.rate}') * Number('${item.qty}')) - (Number('${item.custom_rate}') * Number('${item.qty}'))).toFixed(2);
    	var itemTotal=Number('${item.custom_rate}') * Number('${item.qty}');
    	cell7.innerHTML ='<input typ="text" class="form-control form-control-sm" value="'+itemTotal+'">';
    	
    		
    	cell8.innerHTML = '<button type="button" class="btn btn-sm btn-danger"  onclick=removethisitem(this) id="btn11" name="deleteButtons" style="cursor:pointer">Delete</button> <button type="button" class="btn btn-primary"  onclick=returnThisItem("${item.details_id}") name="returnButtons" id="btn11" style="cursor:pointer">Return (${item.ReturnedQty})</button>';
    	
		
	    		//alert('${item.item_id}'+'-${item.item_name}'+'-${item.qty}'+'-${item.rate}'+'-${item.custom_rate}');			    
		</c:forEach>
 
		
		if('${param.editInvoice}'=='Y')
			{
				hdnPreviousInvoiceId.value="${invoiceDetails.invoice_id}";
				var returnButtons=document.getElementsByName('returnButtons');
				for(var m=0;m<returnButtons.length;m++)
					{
						returnButtons[m].style="display:none";
					}
				txtsearchcustomer.disabled=true;
				txtinvoicedate.disabled=true;
				document.getElementById("divTitle").innerHTML="Edit Invoice:-"+"${invoiceDetails.invoice_no}";
			}
		else
			{
				$("#frm :input").prop('disabled', true);
				$("[name=returnButtons]").prop('disabled', false);
				
				
				var deleteButtons=document.getElementsByName('deleteButtons');
				for(var m=0;m<deleteButtons.length;m++)
					{
						deleteButtons[m].style="display:none";
					}
			}
		
		if('${param.table_id}'!='')
			{
				calculateTotal();
				txtpaymenttype.value='Paid';
				drppaymentmode.value='Cash';
				txtsearchcustomer.disabled=false;
				txtinvoicedate.disabled=false;
				document.getElementById("divTitle").innerHTML="Invoice For Table No:-"+tableNo;
				txtinvoicediscount.value=0;
			}
		
		if('${param.booking_id}'!='')
		{
			calculateTotal();
			txtpaymenttype.value='Paid';
			drppaymentmode.value='Cash';
			txtsearchcustomer.disabled=true;
			txtinvoicedate.disabled=false;
			document.getElementById("divTitle").innerHTML="Invoice For Booking Id - ${param.booking_id}";
			txtinvoicediscount.value=0;
		}
		
		document.getElementById("generatePDF").style="display:";
		generatePDF.disabled=false;
		btnRegister.disabled=false;
		
	}
	
	
function returnThisItem(detailsId)
{
		window.location="?a=showReturnScreen&detailsId="+detailsId;
}
function resetCustomer()
{
	window.location.reload();
	txtsearchcustomer.disabled=false;
	txtsearchcustomer.value="";
	hdnSelectedCustomer.value=0;	
}

function addCustomer()
{
	window.open("?a=showAddCustomer&mobileNo="+txtsearchcustomer.value);	
}







function showThisItemIntoSelection(itemId)
{
	
	getItemDetailsAndAddToTable(itemId);
	document.getElementById("responseText").innerHTML="";
	$("#myModal").modal('hide');
}
function freezeWareHouse()
{
	return;

		if(drpwarehouse.value!=-1)
			{
				drpwarehouse.disabled=true;
			}
}

function generatePdfChallanIn(challanId)
{
	var xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() 
	  {
	    if (xhttp.readyState == 4 && xhttp.status == 200) 
	    { 		      
	    	window.open("BufferedImagesFolder/"+xhttp.responseText);		  
		}
	  };
	  xhttp.open("GET","?a=generateChallanPDF&challanId="+challanId+"&type=I", false);    
	  xhttp.send();
}



</script>