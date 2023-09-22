  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
           
           
           


<c:set var="accessblockDetails" value='${requestScope["outputObject"].get("accessblockDetails")}' />
<c:set var="listOfEmployees" value='${requestScope["outputObject"].get("EmployeeList")}' />

   





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


function checkforMatchEmployee()
{
	var searchString= document.getElementById("EmployeeName").value;
	
	var options1=document.getElementById("listOfEmployee").options;
	var employeeId=0;
	for(var x=0;x<options1.length;x++)
		{
			if(searchString==options1[x].value)
				{
					employeeId=options1[x].id;
					break;
				}
		}
	if(employeeId!=0)
		{
			document.getElementById("hdnselectedemployee").value=employeeId;			
			document.getElementById("EmployeeName").disabled=true;						
		}
	else
		{
			//searchForCustomer(searchString);
		}
	
	
}

function resetEmployee()
{	
	EmployeeName.disabled=false;
	EmployeeName.value="";
	hdnselectedemployee.value=0;
}




</script>



<br>

<div class="container" style="padding:20px;background-color:white">

<form id="frm" action="?a=saveAccessBlockEntry" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
<input type="hidden" name="app_id" value="${userdetails.app_id}">
<input type="hidden" name="user_id" value="${userdetails.user_id}">
<input type="hidden" name="callerUrl" id="callerUrl" value="">

<datalist id="listOfEmployee">
<c:forEach items="${listOfEmployees}" var="cat">
 <option id="${cat.user_id}">${cat.name} </option>	
 </c:forEach>
</datalist>
 
  
<div class="row">
  <div class="col-sm-12">
	<div class="form-group">
		
	<label for="email">Employee Name </label>     
	<div class="input-group input-group-sm">

	<input type="textbox" name="EmployeeName" id="EmployeeName" class="form-control form-control-sm" list="listOfEmployee" onchange="checkforMatchEmployee()"/> 
	<input type="hidden" name="hdnselectedemployee" id="hdnselectedemployee" value="">  <span class="input-group-append">
		<button type="button" class="btn btn-danger btn-flat" onclick="resetEmployee()">Reset</button>
		</span>
	</div>
	</div>
		</div>
</div>


     <div class="col-sm-12">
  	<div class="form-group">
      <label for="text">Remarks</label>
      <input type="text" class="form-control" id="txtremarks" value="${accessblockDetails.remarks}" name="txtremarks" placeholder="Remarks"  >
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
	
	
	<c:if test="${holidayDetails.categoryId eq null}">
		document.getElementById("divTitle").innerHTML="Add Access Block Entry";
	</c:if>
	<c:if test="${holidayDetails.categoryId ne null}">
		document.getElementById("divTitle").innerHTML="Update Holiday";
	</c:if>
	
	
	$( "#txtholidaydate" ).datepicker({ dateFormat: 'dd/mm/yy' });
</script>



