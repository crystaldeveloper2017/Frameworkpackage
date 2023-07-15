  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
           
           
           


<c:set var="customerDetails" value='${requestScope["outputObject"].get("customerDetails")}' />
<c:set var="groupList" value='${requestScope["outputObject"].get("groupList")}' />
<c:set var="DistinctCityNames" value='${requestScope["outputObject"].get("DistinctCityNames")}' />
<c:set var="type" value='${requestScope["outputObject"].get("type")}' />


   





</head>


<script>


function addCustomer()
{	
	
	
	if(document.getElementById("customername").value=="")
	{
		alert('Please enter Customer Name');
		mobileNumber.focus();
		return; 
	}
	
	
	if(document.getElementById("mobileNumber").value=="" || document.getElementById("mobileNumber").value.length!=10)
		{
			alert('Please enter Valid Mobile number');
			mobileNumber.focus();
			return; 
		}
	
	if(document.getElementById("alternate_mobile_no").value!="" && document.getElementById("alternate_mobile_no").value.length!=10)
	{
		alert('Please enter Valid Alternate Mobile number');
		alternate_mobile_no.focus();
		return; 
	}
	
	document.getElementById("frm").submit(); 
}






</script>



<br>

<datalist id="cityList">
	<c:forEach items="${DistinctCityNames}" var="DistinctCityName">
			    <option id="${DistinctCityName}">${DistinctCityName}</option>			    
	   </c:forEach>	   	   	
</datalist>

<div class="container" style="padding:20px;background-color:white"> 

<form id="frm" action="?a=addCustomer" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
<div class="row">
  <div class="col-sm-4">
  	<div class="form-group">
  	
      <label for="CustomerName">Name</label>
      
      <input type="text" class="form-control" id="customername" value="${customerDetails.customer_name}" name="customerName" placeholder="Name">
      <input type="hidden" name="hdnCustomerId" value="${customerDetails.customer_id}" id="hdnCustomerId">
      <input type="hidden" name="hdnType" value="${param.type}" id="hdnType">
    </div>
  </div>
  
  
   <div class="col-sm-4">
  	<div class="form-group">
      <label for="contactperson">Contact Person</label>
      <input type="text" class="form-control" id="email" value="${customerDetails.contact_person}" name="contact_person" placeholder="Contact Person ">
      
    </div>
  </div>
  
  <div class="col-sm-4">
  	<div class="form-group">
      <label for="CustomerName">Email Id</label>
      <input type="text" class="form-control" id="email" value="${customerDetails.email}" name="email" placeholder="Email ">
      
    </div>
  </div>
  
  <div class="col-sm-4">
  	<div class="form-group">
      <label for="MobileNumber">Mobile Number *</label>
      <input type="text" class="form-control" id="mobileNumber" value="${customerDetails.mobile_number}" name="mobileNumber" placeholder="Mobile Number" onkeypress="digitsOnly(event)" maxlength="10" required>
    </div>
  </div>
  
  <div class="col-sm-4">
  	<div class="form-group">
      <label for="MobileNumber">Alternate Mobile No (Optional)</label>
      <input type="text" class="form-control" id="alternate_mobile_no" value="${customerDetails.alternate_mobile_no}" name="alternate_mobile_no" placeholder="Alternate Mobile No" onkeypress="digitsOnly(event)" maxlength="10" required>
    </div>
  </div>
  
  <div class="col-sm-4">
  	<div class="form-group">
      <label for="MobileNumber">GST No</label>
      <input type="text" class="form-control" id="gst_no" value="${customerDetails.gst_no}" name="gst_no" placeholder="GST No">
    </div>
  </div>
  
    <div class="col-sm-4">
  	<div class="form-group">
      <label for="Address">Address</label>
      <input type="text" class="form-control" id="address" value="${customerDetails.address}" name="address" placeholder="Address">
    </div>
  </div>
  
  <div class="col-sm-4">
  	<div class="form-group">
      <label for="Address">Address Line 2</label>
      <input type="text" class="form-control" id="address_line_2" value="${customerDetails.address_line_2}" name="address_line_2" placeholder="Address">
    </div>
  </div>
  
  <div class="col-sm-4">
  	<div class="form-group">
      <label for="City">City</label>
      <input type="text" list="cityList" class="form-control" id="city" value="${customerDetails.city}" name="city" placeholder="City">
    </div>
  </div>
  
   <div class="col-sm-4">
  	<div class="form-group">
      <label for="City">Pincode</label>
      <input type="text" class="form-control" id="pincode" value="${customerDetails.pincode}" name="pincode" placeholder="pincode">
    </div>
  </div>
  
  <div class="col-sm-4">
  	<div class="form-group">
      <label for="State">State</label>
      <input type="text" class="form-control" id="state" value="${customerDetails.state}" name="state" placeholder="state">
    </div>
  </div>
  
    
  <div class="col-sm-4">
  	<div class="form-group">
      <label for="Country">Country</label>
      <input type="text" class="form-control" id="country" value="${customerDetails.country}" name="country" placeholder="Country">
    </div>
  </div>
  

  
   <div class="col-sm-4">
  	<div class="form-group">
      <label for="MobileNumber">Pan No</label>
      <input type="text" class="form-control" id="gst_no" value="${customerDetails.pan_no}" name="pan_no" placeholder="Pan No">
    </div>
  </div>
  
    <div class="col-sm-4">
  	<div class="form-group">
      <label for="MobileNumber">Division</label>
      <input type="text" class="form-control" id="division" value="${customerDetails.division}" name="division" placeholder="division">
    </div>
  </div>
  
  
  <div class="col-sm-4">
  	<div class="form-group">
      <label for="MobileNumber">Birthday</label>
      <input type="text" class="form-control" name="birthday" id="birthday" value="${customerDetails.Formattedbirthday}" name="birthday" placeholder="Birthday">
    </div>
  </div>
  
  <div class="col-sm-4">
  	<div class="form-group">
      <label for="MobileNumber">Anniversary</label>
      <input type="text" class="form-control" id="anniversary" value="${customerDetails.Formattedanniversary}" name="anniversary" placeholder="">
    </div>
  </div>
  
  
  
  <div class="col-sm-4">
  	<div class="form-group">
      <label for="CustomerType">Group </label>      
  				<select id="customerGroup" name="customerGroup" class="form-control" >
  				<c:forEach items="${groupList}" var="group">
  						<option value="${group.group_id}">${group.group_name }</option>
  				</c:forEach>
  					
  				</select>
     
    </div>
  </div>
  
 <div class="col-sm-4">
  	<div class="form-group">
      <label for="firmEmail">Bank Mapping</label><br>
      <input type="button" type="button" class="btn btn-primary" onclick='window.location="?a=showFirmBankMapping&type=${param.type}"' value="Update">
    </div>
  </div>
  
  
  
  
  <div class="col-sm-12" align="center">
  	<div class="form-group">
      
   <button class="btn btn-success" type="button" onclick='addCustomer()'>Save</button>
  <button class="btn btn-danger" type="reset" onclick='window.location="?a=showCustomerMaster&type=${param.type}"'>Cancel</button>
     
    </div>
  </div>
  
  
  
  
  
  
  </div>
  
  
</div>
</form>








<script>
	<c:if test="${customerDetails.customer_id ne null}">		
		document.getElementById('customerGroup').value='${customerDetails.group_id}';
	</c:if>
	<c:if test="${customerDetails.customer_id eq null && param.type eq 'C'}">
		document.getElementById("divTitle").innerHTML="Add Customer";
	 </c:if>
	 <c:if test="${customerDetails.customer_id eq null && param.type eq 'V'}">
		document.getElementById("divTitle").innerHTML="Add Vendor";
	</c:if>

	<c:if test="${customerDetails.customer_id ne null && customerDetails.client_vendor_flag eq 'V'}">
		document.getElementById("divTitle").innerHTML="Update Vendor";
	</c:if>
	
	<c:if test="${customerDetails.customer_id ne null && customerDetails.client_vendor_flag eq 'C'}">
		document.getElementById("divTitle").innerHTML="Update Customer";
	</c:if>
	
	
	$( "#birthday" ).datepicker({ dateFormat: 'dd/mm/yy' });
	$( "#anniversary" ).datepicker({ dateFormat: 'dd/mm/yy' });
	
</script>
