  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
           
           
           


<c:set var="wareHouseDetails" value='${requestScope["outputObject"].get("wareHouseDetails")}' />
   





</head>


<script>


function addWareHouse()
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

<form id="frm" action="?a=addWareHouse" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
<div class="row">
  <div class="col-sm-12">
  	<div class="form-group">
      <label for="email">WareHouse Name</label>
      <input type="text" class="form-control" id="ware_house_name" value="${wareHouseDetails.ware_house_name}"   name="ware_house_name">
      <input type="hidden" name="hdnWareHouseId" value="${wareHouseDetails.ware_house_id}" id="hdnWareHouseId">
    </div>
  </div>
  <div class="col-sm-12">
  	 <div class="form-group">
      <label for="email">Upload the Images (800px X 800px)</label>
      
      <c:if test="${wareHouseDetails.ImagePath eq null}">	        
			        	<input type="file" name="file" multiple/>
	  </c:if>
	  
	  
	  <c:if test="${wareHouseDetails.ImagePath ne null}">			        
								        
				        		
				        						        	
				        				       
				        				       <img src="BufferedImagesFolder/${wareHouseDetails.ImagePath}" height="200px" width="200px"/>
				        				       File Size ${attachment.file_size} KB
					        				       
					        				       		<button type="button" class="btn btn-danger" onclick='deleteAttachment("${wareHouseDetails.attachId}")'>Delete</button>
					        				       
				        				        <br>
				        				        	
				        				        	
				       
			        		
			        </c:if>
	  
	  
    </div>
  </div>
  
  <c:if test="${action ne 'Update'}">
		
		<button class="btn btn-success" type="button" onclick='addWareHouse()'>Save</button>
		<button class="btn btn-danger" type="reset" onclick='window.location="?a=showWareHouseMasterNew"'>Cancel</button>
		
		
		</c:if>
		
		
		
		<c:if test="${action eq 'Update'}">	
				
				<input type="button" type="button" class="btn btn-success" onclick='addWareHouse()' value="update">		
		</c:if> 
</div>
</form>

<script>
	
	
	<c:if test="${wareHouseDetails.ware_house_id eq null}">
		document.getElementById("divTitle").innerHTML="Add WareHouse";
	</c:if>
	<c:if test="${wareHouseDetails.ware_house_id ne null}">
		document.getElementById("divTitle").innerHTML="Update WareHouse";
	</c:if>
</script>



