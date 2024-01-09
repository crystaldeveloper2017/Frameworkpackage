  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="vendorDetails" value='${requestScope["outputObject"].get("vendorDetails")}' />
<c:set var="countries" value='${requestScope["outputObject"].get("ListOfCountries")}' />


</head>


<script>


function addVendor()
{	
	
	document.getElementById("frm").submit(); 
}


</script>

<c:set var="countries" value='${requestScope["outputObject"].get("ListOfCountries")}' />

<c:set var="vendorDetails" value='${requestScope["outputObject"].get("vendorDetails")}' />

<br>

<div class="container" style="padding:20px;background-color:white">


	<form id="frm" action="?a=addVendor" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
	<input type="hidden" name="app_id" value="${userdetails.app_id}">
	<input type="hidden" name="user_id" value="${userdetails.user_id}">
	<input type="hidden" name="callerUrl" id="callerUrl" value="">

<div class="row">
  <div class="col-sm-6">
  	<div class="form-group">
      <label for="vendorName">Vendor Name*</label>
      <input type="text" class="form-control" id="vendorName" value="${vendorDetails.vendor_name}" name="vendorName" placeholder="Vendor Name">
      <input type="hidden" name="hdnvendorId" value="${vendorDetails.vendor_id}" id="hdnvendorId">
    </div>
  </div>
  
  <div class="col-sm-6">
  	<div class="form-group">
      <label for="contactPerson">Contact Person*</label>
      <input type="text" class="form-control" id="contactPerson" value="${vendorDetails.contact_person}" name="contactPerson" placeholder="Contact Person">
    </div>
  </div>
  
 
 
 <div class="col-sm-12">
  	<div class="form-group">
      <label for="address">Address*</label>
      <input type="text" class="form-control" id="address" value="${vendorDetails.address}" name="address" placeholder="Address">
    </div>
  </div>
  

  <div class="col-sm-3">
  	<div class="form-group">
      <label for="pincode">Pincode*</label>
      <input type="text" class="form-control" id="pincode" value="${vendorDetails.pincode}" name="pincode" placeholder="Pincode">
    </div>
  </div>
  

 
 <div class="col-sm-3">
  	<div class="form-group">
      <label for="state">State*</label>
      <input type="text" class="form-control" id="state" value="${vendorDetails.state}" name="state" placeholder="State">
    </div>
  </div>
  

  
  
  
 

 
 <div class="col-sm-3">
  	<div class="form-group">
      <label for="contactNo1">Contact No1*</label>
      <input type="text" class="form-control" id="contactNo1" value="${vendorDetails.contact_no1}" name="contactNo1" placeholder="Contact No1" onkeypress="digitsOnly(event)" maxlength="10" required>
    </div>
  </div>
  

 <div class="col-sm-3">
  	<div class="form-group">
      <label for="contactNo2">Contact No2</label>
      <input type="text" class="form-control" id="contactNo2" value="${vendorDetails.contact_no2}" name="contactNo2" placeholder="Contact No2" onkeypress="digitsOnly(event)" maxlength="10" required>
    </div>
  </div>
  

 
 <div class="col-sm-3">
  	<div class="form-group">
      <label for="email">Email*</label>
      <input type="text" class="form-control" id="email" value="${vendorDetails.email}" name="email" placeholder="Email">
    </div>
  </div>
  

 
 <div class="col-sm-3">
  	<div class="form-group">
      <label for="gst">GST</label>
      <input type="text" class="form-control" id="gst" value="${vendorDetails.gst}" name="gst" placeholder="GST">
    </div>
  </div>
  
  <div class="col-sm-3">
  	<div class="form-group">
      <label for="panNo">Pan No</label>
      <input type="text" class="form-control" id="panNo" value="${vendorDetails.pan_no}" name="panNo" placeholder="Pan No">
    </div>
  </div>
  
  <div class="col-sm-3">
  	<div class="form-group">
      <label for="ismsme">MSME Registered</label>
      <select class="form-control" id="ismsme" name="ismsme">
      	<option value="Yes">Yes</option>
      	<option value="No">No</option>
      	 </select>     
    </div>
  </div>
  
  <div class="col-sm-3">
  	<div class="form-group">
      <label for="uAdhaar">Udhyam Adhaar</label>
      <input type="text" class="form-control" id="uAdhaar" value="${vendorDetails.u_adhaar}" name="uAdhaar" placeholder="Udhyam Adhaar">
    </div>
  </div>
  
<div class="col-sm-3">
  	<div class="form-group">
      <label for="email">Qr Code*</label>
      <input type="text" class="form-control" id="qrCode" value="${vendorDetails.qr_code}" name="qrCode" placeholder="Qr Code">
    </div>
  </div>



  <div class="col-sm-3">
  	<div class="form-group">
      <label for="email">Firm Name</label>
      <input type="text" class="form-control" id="firmName" value="${vendorDetails.firm_name}" name="firmName" placeholder="Firm Name">
    </div>
  </div>
  
 
  <div class="col-sm-12">
  	<div class="form-group">
      <button class="btn btn-success" type="button" onclick='addVendor()'>Save</button>
		<button class="btn btn-danger" type="reset" onclick='window.location="?a=showVendorMaster"'>Cancel</button>
		
    </div>
  </div>
 
		
		
	
</div>
</form>








<script>
<c:if test="${vendorDetails.vendor_id eq null}">
document.getElementById("divTitle").innerHTML="Create Vendor";
</c:if>
<c:if test="${vendorDetails.vendor_id ne null}">
document.getElementById("divTitle").innerHTML="Update Vendor";
</c:if>
<c:if test="${vendorDetails.vendor_id ne null}">
	document.getElementById("country").value='${vendorDetails.country_id}';
</c:if>


var arr=window.location.toString().split("/");
callerUrl.value=(arr[0]+"//"+arr[1]+arr[2]+"/"+arr[3]+"/");	
	
</script>
