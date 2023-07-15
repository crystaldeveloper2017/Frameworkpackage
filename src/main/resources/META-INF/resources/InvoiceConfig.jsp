  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
           
           
           


<c:set var="userDetails" value='${requestScope["outputObject"].get("userDetails")}' />
<c:set var="listOfInvoiceFormats" value='${requestScope["outputObject"].get("listOfInvoiceFormats")}' />

   





</head>





<br>

<div class="container" style="padding:20px;background-color:white">


<div class="row">

<div class="col-sm-12">
  	<div class="form-group">
      <label for="email">Invoice Format</label>     
      <select class="form-control" name="drpinvoiceid" id="drpinvoiceid" onchange="changeFormat()">
      <c:forEach items="${listOfInvoiceFormats}" var="invoiceformat">
			    <option value="${invoiceformat.format_id}">${invoiceformat.format_name}</option>			    
	   </c:forEach></select>   
    </div>
  </div>
   
</div>






<script>
	document.getElementById("divTitle").innerHTML="Switch firm";	
	drpinvoiceid.value="${userDetails.invoice_format}";
	
	
	function changeFormat()
	{
		var answer = window.confirm("Are you sure you want to Change Format ?");
		if (!answer) 
		{
			return;    
		}
		document.getElementById("closebutton").style.display='none';
		document.getElementById("loader").style.display='block';
		$('#myModal').modal({backdrop: 'static', keyboard: false});;

		var xhttp = new XMLHttpRequest();
		  xhttp.onreadystatechange = function() 
		  {
		    if (xhttp.readyState == 4 && xhttp.status == 200) 
		    { 		      
		      document.getElementById("responseText").innerHTML=xhttp.responseText;
			  document.getElementById("closebutton").style.display='block';
			  document.getElementById("loader").style.display='none';
			  $('#myModal').modal({backdrop: 'static', keyboard: false});;
		      
			  
			}
		  };
		  xhttp.open("GET","?a=changeInvoiceFormat&formatId="+drpinvoiceid.value, true);    
		  xhttp.send();
		
	}
</script>
