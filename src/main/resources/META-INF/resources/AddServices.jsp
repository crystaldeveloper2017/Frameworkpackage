 <style>
	.date_field {position: relative; z-index:1000;}
	.ui-datepicker{position: relative; z-index:1000!important;}
</style> 
  
  
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
           
           
           


<c:set var="CategoriesList" value='${requestScope["outputObject"].get("CategoriesList")}' />

<c:set var="serviceDetails" value='${requestScope["outputObject"].get("serviceDetails")}' />
<c:set var="dueDate" value='${requestScope["outputObject"].get("dueDate")}' />






   





</head>


<script>


function addService()
{
	document.getElementById("frm").submit(); 
}


function updateService()
{
	document.getElementById("frm").action="?a=updateService"; 
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
    

<form id="frm" action="?a=addService" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
<div class="row">
<div class="col-sm-3">

  	<div class="form-group">
      <label for="email">Category Name <a onclick='showAddCategory()' href="#">Add</a></label>     
      <select class="form-control" name="drpcategoryId" id="drpcategoryId">
      <c:forEach items="${CategoriesList}" var="cat">
			    <option value="${cat.category_id}">${cat.category_name}</option>			    
	   </c:forEach></select>     
    </div>
  </div>
  
  

  	

  <div class="col-sm-3">
  	<div class="form-group">
      <label for="email">Service Name</label>
      <input type="text" class="form-control" id="Servicename" value="${serviceDetails.service_name}"  placeholder="Service Name" name="servicename">
      <input type="hidden" name="hdnServiceId" value="${serviceDetails.service_id}" id="hdnServiceId">
    </div>
  </div>
 
 
 <div class="col-sm-3">
  	<div class="form-group">
      <label for="email">Occurance</label>
      
      
      <select id="occurance" name="occurance" class='form-control'>
	      <option value="Monthly">Monthly</option>
	      <option value="Weekly">Weekly</option>
	      <option value="Yearly">Yearly</option>    
	      <option value="One time">One time</option>  
      </select>
      
    </div>
  </div>
  
  
   <div class="col-sm-3">
  	<div class="form-group">
      <label for="txtduedate">Due Date</label>
      <input type="text" class="form-control" id="txtduedate" value="${serviceDetails.FormattedDueDate}"  class="form-control date_field"  placeholder="Due Date" name="txtduedate">
    </div>
   </div>
  
  
  <div class="col-sm-12">
  	 <div class="form-group" align="center">
  	 
	  <c:if test="${serviceDetails.service_id eq null}">
	   	<button class="btn btn-success" type="button" onclick='addService()'>Save</button>	
	   </c:if>
	   
	   <c:if test="${serviceDetails.service_id ne null}">
	   	<input type="button" type="button" class="btn btn-success" onclick='addService()' value="Update">	
	   </c:if>
	   
	   <button class="btn btn-danger" type="reset" onclick='window.location="?a=showServiceMaster"'>Cancel</button>
   </div>
   </div>
  
		
		 
</div>

</form>




<script>

	

	$( "#txtduedate" ).datepicker({ dateFormat: 'dd/mm/yy' });

	<c:if test="${serviceDetails.service_id ne null}">
		document.getElementById('drpcategoryId').value='${serviceDetails.parent_category_id}';
		
		
	</c:if>
	<c:if test="${serviceDetails.service_id eq null}">
		document.getElementById("divTitle").innerHTML="Add Service";
	</c:if>
	<c:if test="${serviceDetails.service_id ne null}">
		document.getElementById("divTitle").innerHTML="Update Service";
	</c:if>
	
	
		
	if('${userdetails.app_id}'!='1')
		{
			appspecific.style='display:none';
		}
	
	function showAddCategory()
	{
		window.open('?a=showAddCategory');
	}
	
	
	
	
	
	
</script>
