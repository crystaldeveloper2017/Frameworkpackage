
 <style type="text/css">
            body {
              padding: 0;
              margin: 0;
            }

            svg:not(:root) {
              display: block;
            }

            .playable-code {
              background-color: #f4f7f8;
              border: none;
              border-left: 6px solid #558abb;
              border-width: medium medium medium 6px;
              color: #4d4e53;
              height: 100px;
              width: 90%;
              padding: 10px 10px 0;
            }

            .playable-canvas {
              border: 1px solid #4d4e53;
              border-radius: 2px;
            }

            .playable-buttons {
              text-align: right;
              width: 90%;
              padding: 5px 10px 5px 26px;
            }
        </style>
        
        <style type="text/css">
            #video {
  border: 1px solid black;
  box-shadow: 2px 2px 3px black;
  width:320px;
  height:240px;
}

#photo {
  border: 1px solid black;
  box-shadow: 2px 2px 3px black;
  width:320px;
  height:240px;
}

#canvas {
  display:none;
}

.camera {
  width: 340px;
  display:inline-block;
}

.output {
  width: 340px;
  display:inline-block;
  vertical-align: top;
}

#startbutton {
  display:block;
  position:relative;
  margin-left:auto;
  margin-right:auto;
  bottom:32px;
  background-color: rgba(0, 150, 0, 0.5);
  border: 1px solid rgba(255, 255, 255, 0.7);
  box-shadow: 0px 0px 1px 2px rgba(0, 0, 0, 0.2);
  font-size: 14px;
  font-family: "Lucida Grande", "Arial", sans-serif;
  color: rgba(255, 255, 255, 1.0);
}

.contentarea {
  font-size: 16px;
  font-family: "Lucida Grande", "Arial", sans-serif;
  width: 760px;
}

        </style>

  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="employeeList" value='${requestScope["outputObject"].get("employeeList")}' />
<c:set var="distinctPurposeOfVisist" value='${requestScope["outputObject"].get("distinctPurposeOfVisist")}' />
<c:set var="todaysDate" value='${requestScope["outputObject"].get("todaysDate")}' />
<c:set var="listOfEmployee" value='${requestScope["outputObject"].get("EmployeeList")}' />


</head>


<script>



function checkforMatchEmployee()
{
	var searchString= document.getElementById("employee_id").value;
	
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
			document.getElementById("employee_id").disabled=true;						
		}
	else
		{
			//searchForCustomer(searchString);
		}
	
	
}

function resetEmployee()
{	
	employee_id.disabled=false;
	employee_id.value="";
	hdnselectedemployee.value=0;
}







</script>

<datalist id="listOfEmployee">
<c:forEach items="${listOfEmployee}" var="employee">
 <option id="${employee.user_id}">${employee.name} - ${employee.qr_code}</option>	
 </c:forEach>
</datalist>

 

<br>

<div class="container" style="padding:20px;background-color:white">


<form id="frm" action="?a=saveSupervisorSubmitLeave" method="post"  accept-charset="UTF-8">
<input type="hidden" name="app_id" value="${userdetails.app_id}">
<input type="hidden" name="user_id" value="${userdetails.user_id}">
<input type="hidden" name="callerUrl" id="callerUrl" value="">


 

  <div class="col-sm-12">
	<div class="form-group">
		
	<label for="email">Employee Name </label>     
	<div class="input-group input-group-sm">

	<input type="textbox" name="employee_id" id="employee_id" class="form-control form-control-sm" list="listOfEmployee" onchange="checkforMatchEmployee()"/> 
	<input type="hidden" name="hdnselectedemployee" id="hdnselectedemployee" value="">  <span class="input-group-append">
		<button type="button" class="btn btn-danger btn-flat" onclick="resetEmployee()">Reset</button>
		</span>
	</div>
	</div>
		</div>



  
   <div class="col-sm-12">
  	<div class="form-group">
      <label for="email">From Date </label>
      <input type="text" class="form-control" readonly id="txtfromDate" placeholder="From Date" value="${clientDetails.fromDate}" name="txtfromDate" >
      
    </div>
  </div>
  
  <div class="col-sm-12">
  	<div class="form-group">
      <label for="email">To Date </label>
      <input type="text" class="form-control" readonly id="txttoDate" placeholder="To Date" value="${clientDetails.toDate}" name="txttoDate" >
      
    </div>
  </div>
  
  
  <div class="col-sm-12">
  	<div class="form-group">
      <label for=Address>Reason</label>
      <input type="text" class="form-control" id="reason" value="${employeeDetails.reason}" name="reason" placeholder="Reason">
    </div>
  </div>
  
  <div class="col-sm-12">
  	<div class="form-group">
      <label for=Address>Remark</label>
      <input type="text" class="form-control" id="remark" value="${employeeDetails.remark}" name="remark" placeholder="Remark">
    </div>
  </div>
  
  
  
 
</div>
  
  </div>
  
  
  
  
  
	<div class="col-sm-12" align="center">
  		<div class="form-group">
			<button class="btn btn-success" type="button" onclick='saveSupervisorSubmitLeave()'>Save</button>
			
			<button class="btn btn-danger" type="reset" onclick='window.location="?a=showSupervisorSubmitLeave"'>Cancel</button>
		</div>
  	</div>
		
 
		
	
</div>
</form>

<script>

txtfromDate.value='${todaysDate}';
$( "#txtfromDate" ).datepicker({ dateFormat: 'dd/mm/yy' });


txttoDate.value='${todaysDate}';
$( "#txttoDate" ).datepicker({ dateFormat: 'dd/mm/yy' });

document.getElementById("divTitle").innerHTML="Supervisor Submit Leave";



var arr=window.location.toString().split("/");
callerUrl.value=(arr[0]+"//"+arr[1]+arr[2]+"/"+arr[3]+"/");


function saveSupervisorSubmitLeave()
{
	document.getElementById("frm").submit(); 

}
	
</script>



