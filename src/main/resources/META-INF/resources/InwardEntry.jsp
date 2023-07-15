  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="inwardDetails" value='${requestScope["outputObject"].get("inwardDetails")}' />

</head>


<script>


function addInward()
{	
	
	document.getElementById("frm").submit(); 
}


</script>



<c:set var="inwardDetails" value='${requestScope["outputObject"].get("inwardDetails")}' />

<br>

<div class="container" style="padding:20px;background-color:white">


	
	<input type="hidden" name="app_id" value="${userdetails.app_id}">
	<input type="hidden" name="user_id" value="${userdetails.user_id}">
	<input type="hidden" name="callerUrl" id="callerUrl" value="">

<div class="row">
  <div class="col-sm-3">
  	<div class="form-group">
      <label for="vehicleNo">Vehicle No</label>
      <input type="text" class="form-control" id="vehicleNo" value="${inwardDetails.vehicle_no}" name="vehicleNo" placeholder="Vehicle No">
    </div>
  </div>
  
  <div class="col-sm-3">
  	<div class="form-group">
      <label for="contactPerson">Contact Person</label>
      <input type="text" class="form-control" id="contactPerson" value="${inwardDetails.contact_person}" name="contactPerson" placeholder="Contact Person">
    </div>
  </div>
  
 
 
 <div class="col-sm-3">
  	<div class="form-group">
      <label for="mobileNo">Mobile No</label>
      <input type="text" class="form-control" id="mobileNo" value="${inwardDetails.mobile_no}" name="mobileNo" placeholder="Mobile No">
    </div>
  </div>
  

  <div class="col-sm-3">
  	<div class="form-group">
      <label for="transporterName">Transporter Name</label>
      <input type="text" class="form-control" id="transporterName" value="${inwardDetails.transporter_name}" name="transporterName" placeholder="Transporter Name">
    </div>
  </div>
  

 <div class="col-sm-3">
  	<div class="form-group">
  	<label for="vendorName">Vendor Name</label>
      <input type="text" class="form-control" id="vendorName" value="${inwardDetails.vendor_name}" name="vendorName" placeholder="Vendor Name">
    </div>
  </div>
 
 
  <div class="col-sm-12">
  	<div class="form-group">
      <button class="btn btn-success" type="button" onclick='addInward()'>Save</button>
		<button class="btn btn-danger" type="reset" onclick='window.location="?a=showClientMaster"'>Cancel</button>
		
    </div>
  </div>
 
		
		
	
</div>
</form>








<script>
<c:if test="${inwardDetails.inward_id eq null}">
document.getElementById("divTitle").innerHTML="Inward Entry";
</c:if>
<c:if test="${visitorDetails.visitor_id eq null}">
</c:if>


var arr=window.location.toString().split("/");
callerUrl.value=(arr[0]+"//"+arr[1]+arr[2]+"/"+arr[3]+"/");	
	
</script>
