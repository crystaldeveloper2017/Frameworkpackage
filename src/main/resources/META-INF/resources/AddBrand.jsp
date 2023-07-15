  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
           
           
           
<c:set var="brandDetails" value='${requestScope["outputObject"].get("brandDetails")}' />




</head>


<script>


function addBrand()
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

<form id="frm" action="?a=addBrand" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
<div class="row">
  <div class="col-sm-12">
  	<div class="form-group">
      <label for="email">Brand Name</label>
      <input type="text" class="form-control" id="brandname" value="${brandDetails.brand_name}"  placeholder="eg. Sweets" name="brandName">
      <input type="hidden" name="hdnBrandId" value="${brandDetails.brand_id}" id="hdnBrandId">
    </div>
  </div>
  <div class="col-sm-12">
  	 <div class="form-group">
      <label for="email">Upload the Images (800px X 800px)</label>
      
      <c:if test="${brandDetails.ImagePath eq null}">	        
			        	<input type="file" name="file" multiple/>
	  </c:if>
	  
	  
	  <c:if test="${brandDetails.ImagePath ne null}">			        
								        
				        		
				        						        	
				        				       
				        				       <img src="BufferedImagesFolder/${brandDetails.ImagePath}" height="200px" width="200px"/>
				        				       File Size ${attachment.file_size} KB
					        				       
					        				       		<button type="button" class="btn btn-danger" onclick='deleteAttachment("${brandDetails.attachId}")'>Delete</button>
					        				       
				        				        <br>
				        				        	
				        				        	
				       
			        		
			        </c:if>
	  
	  
    </div>
  </div>
  
  <c:if test="${action ne 'Update'}">
		
		<button class="btn btn-success" type="button" onclick='addBrand()'>Save</button>
		<button class="btn btn-danger" type="reset" onclick='window.location="?a=showBrandMasterNew"'>Cancel</button>
		
		
		</c:if>
		
		
		
		<c:if test="${action eq 'Update'}">	
				
				<input type="button" type="button" class="btn btn-success" onclick='addBrand()' value="update">		
		</c:if> 
</div>
</form>

<script>
	
	
	<c:if test="${brandDetails.brandId eq null}">
		document.getElementById("divTitle").innerHTML="Add Brand";
	</c:if>
	<c:if test="${brandDetails.brandId ne null}">
		document.getElementById("divTitle").innerHTML="Update Brand";
	</c:if>
</script>



