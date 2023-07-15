  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
           
           
           


<c:set var="invoiceSubDetails" value='${requestScope["outputObject"].get("invoiceSubDetails")}' />
   






</head>


<script>










</script>



<br>


<div class="container" style="padding:20px;background-color:white">

<form id="frm" action="?a=addReturn" method="post"  accept-charset="UTF-8">
<div class="row">
  <div class="col-sm-6">
  	<div class="form-group">
      <label for="email">Item Name</label>
      <input type="text" class="form-control" id="item_name" value="${invoiceSubDetails.item_name}"   readonly name="item_name">
      <input type="hidden" name="hdnDetailsId" value="${invoiceSubDetails.details_id}" id="hdnDetailsId">
      <input type="hidden" name="hdnItemId" value="${invoiceSubDetails.item_id}" id="hdnItemId">
      <input type="hidden" name="hdnInvoiceId" value="${invoiceSubDetails.invoice_id}" id="hdnInvoiceId">
      <input type="hidden" name="hdnCustomRate" value="${invoiceSubDetails.custom_rate}" id="hdnCustomRate">
      <input type="hidden" name="hdnCustomerId" value="${invoiceSubDetails.customer_id}" id="hdnCustomerId">
      
      
    </div>
  </div>
  
  <div class="col-sm-6">
  	<div class="form-group">
      <label for="email">Returnable Qty</label>
      <input type="text" class="form-control" id="itemqty" value="${invoiceSubDetails.returnAbleQty}"   readonly name="itemqty">      
    </div>
  </div>
  
  
  <div class="col-sm-6">
  	<div class="form-group">
      <label for="email">Item Return qty</label>
      <input type="text" class="form-control" id="returnqty" value="" name="returnqty">      
    </div>
  </div>
  
  
  <div class="col-sm-12">
  	<div class="form-group">      
      <input   class="btn btn-success" type="button" value="Return" onclick="returnItems()">      
    </div>
  </div>
  
  			
		 
</div>
</form>

<script>

function returnItems()
{
	
	if(Number(returnqty.value)>Number(itemqty.value))
		{
			alert("Please enter quantity less than returnable quantity");
			return;
		}	
	frm.submit();	
}

		document.getElementById("divTitle").innerHTML="Return Items";	
</script>



