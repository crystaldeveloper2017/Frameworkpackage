<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
           
           
           


<c:set var="gatepassDetails" value='${requestScope["outputObject"].get("gatepassDetails")}' />
<c:set var="todaysDate" value='${requestScope["outputObject"].get("todaysDate")}' />
<c:set var="listOfEmployees" value='${requestScope["outputObject"].get("EmployeeList")}' />
<c:set var="pass_no" value='${requestScope["outputObject"].get("pass_no")}' />





   





</head>


<script>


function addGatePass()
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
	var searchString= document.getElementById("name").value;
	
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
			document.getElementById("name").disabled=true;						
		}
	else
		{
			//searchForCustomer(searchString);
		}
	
	
}


function resetEmployee()
{	
	name.disabled=false;
	name.value="";
	hdnselectedemployee.value=0;
}





</script>

<datalist id="listOfEmployee">
<c:forEach items="${listOfEmployees}" var="cat">
 <option id="${cat.user_id}">${cat.name} </option>	
 </c:forEach>
</datalist>


<br>

<div class="container" style="padding:20px;background-color:white">


<form id="frm" action="?a=addGatePass" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
<input type="hidden" name="app_id" value="${userdetails.app_id}">
<input type="hidden" name="user_id" value="${userdetails.user_id}">
<input type="hidden" name="callerUrl" id="callerUrl" value="">
<div class="row">

<div class="col-sm-12">
	<div class="form-group">
  
      <label for="email">Name</label>
	  	<div class="input-group input-group-sm">

      <input type="textbox" class="form-control form-control-sm" id="name" value="${gatepassDetails.name}"  list="listOfEmployee" name="name" value="${gatepassDetails.name}" onchange="checkforMatchEmployee()" />
	  	<input type="hidden" name="hdnselectedemployee" id="hdnselectedemployee" value="${gatepassDetails.user_id}">  <span class="input-group-append">
	<button type="button" class="btn btn-danger btn-flat" onclick="resetEmployee()">Reset</button>
		</span>
	</div>
    </div>
  </div>

  
  
   <div class="col-sm-6">
  	<div class="form-group">
      <label for="email">Date</label>
      <input type="text" class="form-control" id="txtdate"  readonly  value="${todaysDate}"  name="txtdate">
            
    </div>
  </div>

 
  

   <div class="col-sm-6">
  	<div class="form-group">
      <label for="email">Reason</label>
      <input type="text" class="form-control" id="reason" value="${gatepassDetails.reason}"  placeholder=" " name="reason">
            
    </div>
  </div>
  



  
    <div class="col-sm-12" align="center">
  	<div class="form-group">
 		
		<button class="btn btn-success" type="button" onclick='addGatePass()'>Save</button>
		<button class="btn btn-danger" type="reset" onclick='window.location="?a=showShiftMaster"'>Cancel</button>
		
		</div>
		</div>
</div>
</form>

<script>
	
	
	<c:if test="${gatepassDetails.gatepassId eq null}">
		document.getElementById("divTitle").innerHTML="Gate Pass : - ${pass_no}";
		document.title +=" Gate Pass ";
	</c:if>
	<c:if test="${gatepassDetails.gatepassId ne null}">
		document.getElementById("divTitle").innerHTML="Gate Pass";
		document.title +=" Update Shift ";
	</c:if>
	
		$( "#txtdate" ).datepicker({ dateFormat: 'dd/mm/yy' });

	
</script>



