  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
           
           
           


<c:set var="firmDetails" value='${requestScope["outputObject"].get("firmDetails")}' />
<c:set var="sbuMaster" value='${requestScope["outputObject"].get("sbuMaster")}' />
   





</head>


<script>


function addfirm()
{	
	
	
	document.getElementById("frm").submit(); 
}






</script>


<br>

<div class="container" style="padding:20px;background-color:white">


<form id="frm" action="?a=addFirm" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
<div class="row">


	   
	   
	    <div class="col-sm-4">
  	<div class="form-group">
      <label for="firmName">Parent SBU Name </label>
      <select class="form-control" name="drpsbuname" id="drpsbuname">
      <c:forEach items="${sbuMaster}" var="sbu">
			    <option value="${sbu.sbu_id}">${sbu.sbu_name}</option>			    
	   </c:forEach>
	   </select>        
    </div>
  </div>

  <div class="col-sm-4">
  	<div class="form-group">
      <label for="firmName">firm Name</label>
      <input type="text" class="form-control" id="firmname" value="${firmDetails.firm_name}" name="firmName" placeholder="firm Name">
      <input type="hidden" name="hdnfirmId" value="${firmDetails.firm_id}" id="hdnfirmId">
    </div>
  </div>
  
  <div class="col-sm-4">
  	<div class="form-group">
      <label for="firmAddress">Address Line 1</label>
      <input type="text" class="form-control" id="address_line_1" value="${firmDetails.address_line_1}" name="address_line_1" placeholder="Address 1">
    </div>
  </div>
  
  <div class="col-sm-4">
  	<div class="form-group">
      <label for="firmAddress">Address Line 2</label>
      <input type="text" class="form-control" id="address_line_2" value="${firmDetails.address_line_2}" name="address_line_2" placeholder="Address 2">
    </div>
  </div>
  
  
  
  <div class="col-sm-4">
  	<div class="form-group">
      <label for="firmAddress">City</label>
      <input type="text" class="form-control" id="city" value="${firmDetails.city}" name="city" placeholder="City">
    </div>
  </div>
  
  <div class="col-sm-4">
  	<div class="form-group">
      <label for="firmAddress">Pincode</label>
      <input type="text" class="form-control" id="pincode" value="${firmDetails.pincode}" name="pincode" placeholder="Pincode">
    </div>
  </div>
  
  <div class="col-sm-4">
  	<div class="form-group">
      <label for="firmEmail">Email</label>
      <input type="text" class="form-control" id="firmEmail" value="${firmDetails.firm_email}" name="firmEmail" placeholder="Email">
    </div>
  </div>  
  
   <div class="col-sm-4">
  	<div class="form-group">
      <label for="firmEmail">GST No</label>
      <input type="text" class="form-control" id="txtgstno" value="${firmDetails.gst_no}" name="txtgstno" placeholder="Gst no">
    </div>
  </div>  
  
   <div class="col-sm-4">
  	<div class="form-group">
      <label for="firmEmail">Bank Mapping</label><br>
      <input type="button" type="button" class="btn btn-primary" onclick='window.location="?a=showFirmBankMapping&type=F"' value="Update">
    </div>
  </div> 
  
  </div>
  
  <c:if test="${action ne 'Update'}">
		
		<button class="btn btn-success" type="button" onclick='addfirm()'>Save</button>
		<button class="btn btn-danger" type="reset" onclick='window.location="?a=showfirmMaster"'>Cancel</button>
		
		
		</c:if>
		
		
		
		<c:if test="${action eq 'Update'}">	
				
				<input type="button" type="button" class="btn btn-success" onclick='addfirm()' value="update">Update</button>	
					
		</c:if> 
</div>
</form>








<script>

<c:if test="${firmDetails.firm_id eq null}">
	document.getElementById("divTitle").innerHTML="Add firm";
</c:if>
<c:if test="${firmDetails.firm_id ne null}">
	document.getElementById("divTitle").innerHTML="Update firm";
	document.getElementById("drpsbuname").value="${firmDetails.parent_sbu_id}";
</c:if>
	
	
</script>
