  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
           
           
           


<c:set var="userDetails" value='${requestScope["outputObject"].get("userDetails")}' />
<c:set var="listOffirms" value='${requestScope["outputObject"].get("listOffirms")}' />

   





</head>


<script>


function addfirm()
{	
	
	
	document.getElementById("frm").submit(); 
}






</script>


<br>

<div class="container" style="padding:20px;background-color:white">

<form id="frm" action="?a=addfirm" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
<div class="row">

<div class="col-sm-12">
  	<div class="form-group">
      <label for="email">firm Name</label>     
      <select class="form-control" name="drpfirmId" id="drpfirmId" onchange="changefirm()">
      <c:forEach items="${listOffirms}" var="firm">
			    <option value="${firm.firmId}">${firm.firmName}</option>			    
	   </c:forEach></select>     
	   
	   <input type="hidden" name="hdnStockId" value="${stockDetails.stock_id}" id="hdnStockId">
    </div>
  </div>
   
</div>
</form>





<script>
	document.getElementById("divTitle").innerHTML="Switch firm";	
	drpfirmId.value="${userDetails.firm_id}";
	
	
	function changefirm()
	{
		var answer = window.confirm("Are you sure you want to Switch firm ?");
		if (!answer) 
		{
			return;    
		}
		
		  document.getElementById("closebutton").style.display='none';
		   document.getElementById("loader").style.display='block';
		

		var xhttp = new XMLHttpRequest();
		  xhttp.onreadystatechange = function() 
		  {
		    if (xhttp.readyState == 4 && xhttp.status == 200) 
		    { 		      
		      toastr["success"](xhttp.responseText);
		    	toastr.options = {"closeButton": false,"debug": false,"newestOnTop": false,"progressBar": false,
		    	  "positionClass": "toast-top-right","preventDuplicates": false,"onclick": null,"showDuration": "1000",
		    	  "hideDuration": "500","timeOut": "500","extendedTimeOut": "500","showEasing": "swing","hideEasing": "linear",
		    	  "showMethod": "fadeIn","hideMethod": "fadeOut"}
		    	
		    	window.location.reload();
		      
			  
			}
		  };
		  xhttp.open("GET","${masterUrl}?a=switchFirm&firmId="+drpfirmId.value, true);    
		  xhttp.send();
		
	}
</script>
