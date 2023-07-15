  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
`           
           
           
<c:set var="sbuDetails" value='${requestScope["outputObject"].get("sbuDetails")}' />

<c:set var="EmployeeList" value='${requestScope["outputObject"].get("EmployeeList")}' />



</head>


<script>


function addSBU()
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

<form id="frm" action="?a=addSBU" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
<div class="row">
  <div class="col-sm-12">
  	<div class="form-group">
      <label for="email">SBU Name</label>
      <input type="text" class="form-control" id="sbuname" value="${sbuDetails.sbu_name}"  placeholder="eg. Ceramic" name="sbuName">
      <input type="hidden" name="hdnSBUId" value="${sbuDetails.sbu_id}" id="hdnSBUId">
    </div>
  </div>
  
  
  <div class="col-sm-12">
  	<div class="form-group">
      <label for="email">SBU Head Employee</label>
       
       <select class="form-control" name="drpheadid" id="drpheadid">
      <c:forEach items="${EmployeeList}" var="employee">
			    <option value="${employee.user_id}">${employee.name}</option>			    
	   </c:forEach>
	   </select>         
    </div>
  </div>
  
  <div class="col-sm-12">
  	 <div class="form-group">
      <label for="email">Upload the Images (800px X 800px)</label>
      
      <c:if test="${sbuDetails.ImagePath eq null}">	        
			        	<input type="file" name="file" multiple/>
	  </c:if>
	  
	  
	  <c:if test="${sbuDetails.ImagePath ne null}">			        
								        
				        		
				        						        	
				        				       
				        				       <img src="BufferedImagesFolder/${sbuDetails.ImagePath}" height="200px" width="200px"/>
				        				       File Size ${attachment.file_size} KB
					        				       
					        				       		<button type="button" class="btn btn-danger" onclick='deleteAttachment("${sbuDetails.attachId}")'>Delete</button>
					        				       
				        				        <br>
				        				        	
				        				        	
				       
			        		
			        </c:if>
	  
	  
    </div>
  </div>
  
  <c:if test="${action ne 'Update'}">
		
		<button class="btn btn-success" type="button" onclick='addSBU()'>Save</button>
		<button class="btn btn-danger" type="reset" onclick='window.location="?a=showSBUMasterNew"'>Cancel</button>
		
		
		</c:if>
		
		
		
		<c:if test="${action eq 'Update'}">	
				
				<input type="button" type="button" class="btn btn-success" onclick='addSBU()' value="update">		
		</c:if> 
</div>
</form>

<script>
	
	
	<c:if test="${sbuDetails.sbu_id eq null}">
		document.getElementById("divTitle").innerHTML="Add SBU";
	</c:if>
	<c:if test="${sbuDetails.sbu_id ne null}">
		document.getElementById("divTitle").innerHTML="Update SBU";		
		drpheadid.value='${sbuDetails.user_id}';
	</c:if>
</script>



