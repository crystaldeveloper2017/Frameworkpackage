  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 

<c:set var="todaysDate" value='${requestScope["outputObject"].get("todaysDate")}' />
<c:set var="customerMaster" value='${requestScope["outputObject"].get("customerMaster")}' />

<br>

<div class="container" style="padding:20px;background-color:white">

<datalist id="customerList">
<c:forEach items="${customerMaster}" var="customer">
			    <option id="${customer.customerId}">${customer.customerName}~(${customer.mobileNumber})~(${customer.customerType})</option>			    
	   </c:forEach>	 	   	   	
</datalist>

<div class="row">

<div class="col-sm-2">
  	<div class="form-group">
  	<label for="email">Date</label>	
  		<input type="text" id="txtdate" name="txtdate" class="form-control  form-control-sm" value="${todaysDate}" placeholder="Date" readonly/>
  	</div>
  </div>

<div class="col-sm-6">
  	<div class="form-group"> 
  	<label for="email">Customer Name</label> 
  	  	<div class="input-group input-group-sm">
  	    
      <input type="text" class="form-control form-control-sm" id="txtsearchcustomer"   placeholder="Search For Customer" name="txtsearchcustomer"  list='customerList' oninput="checkforMatchCustomer()">
      <span class="input-group-append">
                    <button type="button" class="btn btn-danger btn-flat" onclick="resetCustomer()">Reset</button>
                  </span>  
                  </div>    
      <input  type="hidden" name="hdnSelectedCustomer" id="hdnSelectedCustomer" value="">
	  <input  type="hidden" name="hdnSelectedCustomerType" id="hdnSelectedCustomerType" value="">      
    </div>
  </div>
  
  <div class="col-sm-3">
  	<div class="form-group"> 
  	<label for="email">Current Balance</label>     
      <input type="text" class="form-control form-control-sm" id="txtpendingamount" readonly=true >          
    </div>
  </div>
  
  <div class="col-sm-3">
  	<div class="form-group">
  	
  	<c:if test="${param.type eq 'debit'}">
  		<label for="email">Debit Amount</label>
  	</c:if>
  	
  	<c:if test="${param.type ne 'debit'}">
  		<label for="email">Credit Amount</label>
  	</c:if>
  	     
      <input type="text" class="form-control form-control-sm" id="txtpayamount" >          
    </div>
  </div>
  
  
  
  <c:if test="${param.type eq 'debit'}">  		
	    <select class='form-control form-control-sm' id="drppaymentmode" style="display:none">
	    </select>
  </c:if>
  
  
  
  
  
  <c:if test="${param.type ne 'debit'}">  		
	  <div class="col-sm-3">
	  	<div class="form-group"> 
	  	<label for="email">Payment Mode</label>
	  	
	  	 <select class='form-control form-control-sm' id="drppaymentmode">
						  				<option value="Cash">Cash</option>		  				
						  				<option value="Paytm">Paytm</option>
						  				<option value="Amazon">Amazon</option>
						  				<option value="Google Pay">Google Pay</option>
						  				<option value="Phone Pay">Phone Pay</option>
						  				<option value="Card">Card</option>						  				
						  				<option value="Kasar">Kasar</option>					  									  				
				  					</select>
	  	     
	                
	    </div>
	  </div>  
  </c:if>
  
  
    <div class="col-sm-3">
  	<div class="form-group"> 
  	<label for="email">Remarks</label>     
                   <input type="txtremarks" class="form-control form-control-sm" id="txtremarks" >          
                
    </div>
  </div>
  
  <div class="col-sm-12">
  	 <div class="form-group" align="center">
  	 	
  	 		  
	   	<button class="btn btn-success" type="button" onclick='savePayment()'>Save</button>   
	   <button class="btn btn-danger" type="reset" onclick='window.location="?a=showHomePage"'>Cancel</button>
   </div>
   </div>
 
  
  </div>


<script>
	
	
</script>



<script>
function searchForCustomer(searchString)
{	
	console.log(5);
	if(searchString.length<3){return;}

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
	    		reqString+="<option id="+cusomerList[x].customer_id+">"+cusomerList[x].customer_name+"-"+cusomerList[x].mobile_number+"-"+cusomerList[x].customer_type+"</option>";
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
	if(searchString.length<3){return;}
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
			getPendingAmountForThisCustomer(customerId);			
		}
	else
		{
			//searchForCustomer(searchString);
		}
	
	
}

function getPendingAmountForThisCustomer(customerId)
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
	    			txtpendingamount.value=details.pendingAmountDetails.PendingAmount;
	    		}
	    	else
	    		{
	    			alert("no pending amount for this customer");
	    			//window.location.reload();
	    		}
		}
	  };
	  xhttp.open("GET","?a=getPendingAmountForCustomer&customerId="+customerId, true);    
	  xhttp.send();
}

function savePayment()
{
		
	
	
	document.getElementById("closebutton").style.display='none';
	   document.getElementById("loader").style.display='block';
	//$('#myModal').modal({backdrop: 'static', keyboard: false});;
	
	if('${param.type}'!='debit' && '${todaysDate}'!=txtdate.value)
		{
				alert('Only Todays Entry is allowed');			
				return;
		}
	var xhttp = new XMLHttpRequest();
		  xhttp.onreadystatechange = function() 
		  {
		    if (xhttp.readyState == 4 && xhttp.status == 200) 
		    { 		
		    	var details=JSON.parse(xhttp.responseText);
		    	//$("#myModal").modal('hide');
		    	alert("Record Updated Succesfully");  	
		    	txtsearchcustomer.value="";
		    	txtsearchcustomer.disabled=false;		    	
		    	txtpendingamount.value="";
		    	txtpayamount.value="";
		    	txtremarks.value="";
		    	drppaymentmode.value="Cash";
			}
		  };
		  
		  xhttp.open("GET","?a=savePayment&customerId="+hdnSelectedCustomer.value+"&payAmount="+txtpayamount.value+
				  "&paymentMode="+drppaymentmode.value+
				  "&txtdate="+txtdate.value+
				  
				  "&remarks="+txtremarks.value, true);    
		  xhttp.send();
		
}


function showCustomer()
{
	window.location='?a=showSalesRegister&customerId='+hdnSelectedCustomer.value;
}

$( "#txtdate" ).datepicker({ dateFormat: 'dd/mm/yy' });

if('${param.type}'=="debit")
	{
		document.getElementById("divTitle").innerHTML="Debit Entry";
	}
else
	{
		document.getElementById("divTitle").innerHTML="Collect Payment";
	}
	
	
function resetCustomer()
{
	txtsearchcustomer.disabled=false;
	txtsearchcustomer.value="";
	hdnSelectedCustomer.value=0;
	txtpendingamount.value="";
}



</script>