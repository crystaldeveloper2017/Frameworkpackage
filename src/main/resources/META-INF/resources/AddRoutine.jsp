  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
           
           
           


<c:set var="routineDetails" value='${requestScope["outputObject"].get("routineDetails")}' />
<c:set var="itemList" value='${requestScope["outputObject"].get("itemList")}' />





</head>


<script>


function addRoutine()
{	
	
	
	document.getElementById("frm").submit(); 
}


function updateGroup()
{
	
	document.getElementById("frm").action="?a=addRoutine"; 
	document.getElementById("frm").submit();
	return;
	
	
}








</script>



<br>

<div class="container" style="padding:20px;background-color:white">

<form id="frm" action="?a=addRoutine" method="post" enctype="multipart/form-data" accept-charset="UTF-8">

<div class="row">
  <div class="col-sm-12">
  	<div class="form-group">
  	<label for="email">Customer Name</label>
      <div class="input-group input-group-sm">
         <input type="text" class="form-control form-control-sm" id="txtsearchcustomer"   value="${invoiceDetails.customer_name}" placeholder="Search For Customer" name="txtsearchcustomer"  list='customerList' oninput="checkforMatchCustomer()">                  
         <span class="input-group-append">
           <button type="button" class="btn btn-danger btn-flat" onclick="reloadPage()">Reset</button>
         </span>
         <datalist id="customerList">	   	   	
			</datalist>
			<input  type="hidden" name="hdnSelectedCustomer" id="hdnSelectedCustomer" value="">
			<input  type="hidden" name="hdnroutineid" id="hdnroutineid" value="${ param.RoutineId}">
   			<input  type="hidden" name="hdnSelectedCustomerType" id="hdnSelectedCustomerType" value="">
    </div>
    </div>
  </div>
 </div> 
 
 <div class="row">
  <div class="col-sm-12">
  	<div class="form-group">
  	<label for="email">Item Name</label>
      <div class="input-group input-group-sm">
         <input type="text" class="form-control form-control-sm"    placeholder="Search for Items" list="itemList" id="txtitem" name="txtitem" oninput="checkforMatchItem()">
         <datalist id="itemList">
			   <c:forEach items="${itemList}" var="item">
					    <option id="${item.item_id}">${item.item_name} (${item.product_code})</option>			    
			   </c:forEach></select>	   	   	
		</datalist>
		                  
         <span class="input-group-append">
           <button type="button" class="btn btn-danger btn-flat" onclick="reloadPage()">Reset</button>
         </span>
         
			<input  type="hidden" name="hdnselecteditem" id="hdnselecteditem" value="">
   			
    </div>
    </div>
  </div>
 </div> 
 
 
 <div class="row">
  <div class="col-sm-12">
  	<div class="form-group">
  	<label for="email">Item Rate</label>
      <div class="input-group input-group-sm">
         <input type="text" class="form-control form-control-sm"     id="txtcustomrate" name="txtcustomrate" placeholder="custom rate">         
    </div>
    </div>
  </div>
 </div> 
 
 <div class="row">
  <div class="col-sm-12">
  	<div class="form-group">
  	<label for="email">Item Qty</label>
      <div class="input-group input-group-sm">
         <input type="text" class="form-control form-control-sm"     id="txtitemqty" name="txtitemqty" placeholder="Qty">         
    </div>
    </div>
  </div>
 </div>
 
  <div class="row">
  <div class="col-sm-12">
  	<div class="form-group">
  	<label for="email">Delivery Occurance</label>
      <div class="input-group input-group-sm">
          <select id="deliverypreference" name="deliverypreference" class="form-control" >
	  				<option value="Daily">Daily</option>
	  				<option value="Alternate Days">Alternate Days</option>
	  				<option value="Weekly">Weekly</option>				
	  	  </select>    
    </div>
    </div>
  </div>
 </div>
  
  <c:if test="${action ne 'Update'}">
		
		<button class="btn btn-success" type="button" onclick='addRoutine()'>Save</button>
		<button class="btn btn-danger" type="reset" onclick='window.location="?a=showCustomerDeliveryRoutine"'>Cancel</button>
		
		
		</c:if>
		
		
		
		<c:if test="${action eq 'Update'}">	
				
				<input type="button" type="button" class="btn btn-success" onclick='addCategory()' value="update">		
		</c:if> 

</form>

<script>
	
	
	<c:if test="${routineDetails.routine_id eq null}">
		document.getElementById("divTitle").innerHTML="Add Routine";
	</c:if>
	<c:if test="${routineDetails.routine_id ne null}">
		document.getElementById("divTitle").innerHTML="Update Routine";
	</c:if>
	
	
	function checkforMatchCustomer()
	{
		
		var searchString= document.getElementById("txtsearchcustomer").value;	
		var options1=document.getElementById("customerList").options;
		var customerId=0;
		for(var x=0;x<options1.length;x++)
			{
				if(searchString==options1[x].value)
					{
						customerId=options1[x].id;						
						break;
					}
			}
		if(customerId!=0)
			{
				document.getElementById("hdnSelectedCustomer").value=customerId;			
				document.getElementById("txtsearchcustomer").disabled=true;			
				document.getElementById("hdnSelectedCustomerType").value=document.getElementById("txtsearchcustomer").value.split("-")[2];					
			}
		else
			{
				searchForCustomer(searchString);
			}
		
		
		
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
		    		console.log(cusomerList[x]);
		    		reqString+="<option id="+cusomerList[x].customer_id+">"+cusomerList[x].customer_name+"-"+cusomerList[x].mobile_number+"-"+cusomerList[x].customer_type+"</option>";
		    	}
		    	
		    	document.getElementById('customerList').innerHTML=reqString;
			}
		  };
		  xhttp.open("GET","?a=searchForCustomer&searchString="+searchString, true);    
		  xhttp.send();
		
		 
		
	}
	
	
	function checkforMatchItem()
	{
		var customerId=document.getElementById("hdnSelectedCustomer").value;

		if(customerId=="")
		{
				alert("Please select customer");
				document.getElementById("txtitem").value="";
				return;
		}
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
				getItemDetailsAndAddToTable(itemId);
				document.getElementById("txtitem").disabled=true;
				hdnselecteditem.value=itemId;
			}
		
	}
	
	
	function getItemDetailsAndAddToTable(itemId)
	{
		document.getElementById("closebutton").style.display='none';
		document.getElementById("loader").style.display='block';
		var customerId=document.getElementById("hdnSelectedCustomer").value;
		
		var xhttp = new XMLHttpRequest();
		  xhttp.onreadystatechange = function() 
		  {
		    if (xhttp.readyState == 4 && xhttp.status == 200) 
		    { 		      
		    	var itemDetails=JSON.parse(xhttp.responseText);
		    	txtcustomrate.value= itemDetails.CustomersPrice;
		    	console.log(itemDetails);
		    	
			}
		  };
		  xhttp.open("GET","?a=getItemDetailsByAjax&itemId="+itemId+"&customerId="+customerId, true);    
		  xhttp.send();
				
	}
	
	
	function reloadPage()
	{
		window.location.reload();
	}
	
	
	if("${routineDetails.routine_id}"!="")
	{
		
		document.getElementById("hdnSelectedCustomer").value="${routineDetails.customer_id}";		
		document.getElementById("txtsearchcustomer").value="${routineDetails.customer_name}";
		document.getElementById("txtsearchcustomer").disabled=true;
		
		
		document.getElementById("txtitem").value="${routineDetails.item_name}";
		hdnselecteditem.value="${routineDetails.item_id}";
		document.getElementById("txtitem").disabled=true;
		
		
		txtcustomrate.value="${routineDetails.custom_rate}";
		txtitemqty.value="${routineDetails.qty}";		
		deliverypreference.value="${routineDetails.occurance}";
		
	}

	
	
</script>



