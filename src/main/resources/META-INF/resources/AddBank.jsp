  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
`           
           
           
<c:set var="BankDetails" value='${requestScope["outputObject"].get("BankDetails")}' />





</head>


<script>


function addBank()
{	
	
	
	document.getElementById("frm").submit(); 
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



<br>

<div class="container" style="padding:20px;background-color:white">

<form id="frm" action="?a=addBank" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
<div class="row">
  
  <div class="col-sm-4">
  	<div class="form-group">
      <label for="email">Bank Name</label>
      <input type="text" class="form-control" id="bank_name" value="${BankDetails.bank_name}"  placeholder="eg. ICICI" name="bank_name">
      <input type="hidden" name="hdnBankId" value="${BankDetails.bank_id}" id="hdnBankId">
    </div>
  </div>
  
  <div class="col-sm-4">
  	<div class="form-group">
      <label for="email">Account No</label>
      <input type="text" class="form-control" id="account_no" value="${BankDetails.account_no}"  placeholder="eg. Account No" name="account_no">
      
    </div>
  </div>
  
  <div class="col-sm-4">
  	<div class="form-group">
      <label for="email">IFSC Code</label>
      <input type="text" class="form-control" id="ifsc_code" value="${BankDetails.ifsc_code}"  placeholder="eg. IFSC Code" name="ifsc_code">
      
    </div>
  </div>
  
  
  <div class="col-sm-6">
  	<div class="form-group">
      <label for="email">Address</label>
      <textarea rows="4" id="address" placeholder="Eg Infocity Gandhinagar" name="address" class="form-control">${BankDetails.address }</textarea>
    </div>
  </div>
  
  
  <div class="col-sm-6">
  	<div class="form-group">
      <label for="email">Opening Balance</label>
      <input class="form-control" onkeypress="digitsOnlyWithDot(event)" id="txtopeningbalance" placeholder="Opening Balance" value="${BankDetails.opening_balance }" name="txtopeningbalance" class="form-control"/>
    </div>
  </div>
  
  
  
  
  <div class="col-sm-12">
  <c:if test="${action ne 'Update'}">
		
		<button class="btn btn-success" type="button" onclick='addBank()'>Save</button>
		<button class="btn btn-danger" type="reset" onclick='window.location="?a=showBankMasterNew"'>Cancel</button>
		
		
		</c:if>
		
		
		
		<c:if test="${action eq 'Update'}">	
				
				<input type="button" type="button" class="btn btn-success" onclick='addBank()' value="update">		
		</c:if> 
		</div>
</div>
</form>

<script>
	
	
	<c:if test="${BankDetails.Bank_id eq null}">
		document.getElementById("divTitle").innerHTML="Add Bank";
	</c:if>
	<c:if test="${BankDetails.Bank_id ne null}">
		document.getElementById("divTitle").innerHTML="Update Bank";		
		drpheadid.value='${BankDetails.user_id}';
	</c:if>
</script>



