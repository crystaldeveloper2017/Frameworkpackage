  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
           
           
           


<c:set var="departmentDetails" value='${requestScope["outputObject"].get("departmentDetails")}' />
   





</head>


<script>


function addCategory()
{	
	
	
	document.getElementById("frm").submit(); 
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

	<form id="frm" action="?a=addDepartment" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
	<input type="hidden" name="app_id" value="${userdetails.app_id}">
	<input type="hidden" name="user_id" value="${userdetails.user_id}">
	<input type="hidden" name="callerUrl" id="callerUrl" value="">

		<div class="row">
		<div class="col-sm-12">
			<div class="form-group">
			<label for="email">Department Name</label>
			<input type="text" class="form-control" id="txtdepartmentname" value="${departmentDetails.department_name}"  placeholder="eg. Name" name="txtdepartmentname">
			<input type="hidden" name="hdnDepartmentId" value="${departmentDetails.department_id}" id="hdnDepartmentId">
			</div>
		</div>
		

  
  
  <c:if test="${action ne 'Update'}">
		
		<button class="btn btn-success" type="button" onclick='addCategory()'>Save</button>
		<button class="btn btn-danger" type="reset" onclick='window.location="?a=showCategoryMasterNew"'>Cancel</button>
		
		
		</c:if>
		
		
		
		<c:if test="${action eq 'Update'}">	
				
				<input type="button" type="button" class="btn btn-success" onclick='addCategory()' value="update">		
		</c:if> 
</div>
</form>

<script>
	
	
	<c:if test="${departmentDetails.categoryId eq null}">
		document.getElementById("divTitle").innerHTML="Add Department";
	</c:if>
	<c:if test="${departmentDetails.categoryId ne null}">
		document.getElementById("divTitle").innerHTML="Update Department";
	</c:if>
	
	
</script>



