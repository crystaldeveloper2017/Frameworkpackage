  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
           
           
           


<c:set var="levelDetails" value='${requestScope["outputObject"].get("levelDetails")}' />
   





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

	<form id="frm" action="?a=addLevel" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
	<input type="hidden" name="app_id" value="${userdetails.app_id}">
	<input type="hidden" name="user_id" value="${userdetails.user_id}">
	<input type="hidden" name="callerUrl" id="callerUrl" value="">

		<div class="row">
		<div class="col-sm-12">
			<div class="form-group">
			<label for="email">Level Name</label>
			<input type="text" class="form-control" id="txtlevelname" value="${levelDetails.level_name}"  placeholder="eg. Name" name="txtlevelname">
			<input type="hidden" name="hdnLevelId" value="${levelDetails.level_id}" id="hdnLevelId">
			</div>
		</div>
		

    <div class="col-sm-12">
  	<div class="form-group">
      <label for="fullform">Level no</label>
      <input type="text" class="form-control" id="txtlevelno" value="${levelDetails.level_no}" name="txtlevelno" placeholder="eg. No">
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
	
	
	<c:if test="${levelDetails.levelId eq null}">
		document.getElementById("divTitle").innerHTML="Add Level";
	</c:if>
	<c:if test="${levelDetails.levelId ne null}">
		document.getElementById("divTitle").innerHTML="Update Level";
	</c:if>
	
	
</script>



