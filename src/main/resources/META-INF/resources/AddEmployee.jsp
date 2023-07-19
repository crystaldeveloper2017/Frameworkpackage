  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
           
           
           


<c:set var="employeeDetails" value='${requestScope["outputObject"].get("employeeDetails")}' />
<c:set var="employeeList" value='${requestScope["outputObject"].get("employeeList")}' />


   





</head>




<script>


function addEmployee()
{	
	
	
	document.getElementById("frm").submit(); 
}






</script>


<br>

<div class="container" style="padding:20px;background-color:white">


<form id="frm" action="?a=addEmployee" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
<div class="row">
	
  <div class="col-sm-3">
  	<div class="form-group">
      <label for="EmployeeName">Employee Name </label>
      <input type="text" class="form-control" id="EmployeeName" value="${employeeDetails.name}" name="EmployeeName" placeholder="Employee Name">
      <input type="hidden" name="hdnEmployeeId" value="${employeeDetails.user_id}" id="hdnEmployeeId">
    </div>
  </div> 
  
  <div class="col-sm-3">
  	<div class="form-group">
      <label for="EmployeeName">User Name</label>
      <input type="text" class="form-control" id="username" value="${employeeDetails.username}" name="username" placeholder="Username">
      
    </div>
  </div>
 
  
  <div class="col-sm-3">
  	<div class="form-group">
      <label for="MobileNumber">Mobile Number *</label>
      <input type="text" class="form-control" id="MobileNumber" value="${employeeDetails.mobile}" name="MobileNumber" placeholder="Mobile Number" onkeypress="digitsOnly(event)" maxlength="10" required>
    </div>
  </div>
  
    <div class="col-sm-3">
  	<div class="form-group">
      <label for="AadharCardNo">Aadhar Card No</label>
      <input type="text" class="form-control" id="AadharCardNo" value="${employeeDetails.aadhar_card_no}" name="AadharCardNo" placeholder="Aadhar Card No" onkeypress="digitsOnly(event)" maxlength="12" required>
    </div>
  </div>
  
   <div class="col-sm-3">
  	<div class="form-group">
      <label for="MobileNumber">Email</label>
      <input type="text" class="form-control" id="email" value="${employeeDetails.email}" name="email" placeholder="Email"  required>
    </div>
  </div>
  
      
  
  <div class="col-sm-3">
  	<div class="form-group">
      <label for="ParentUserId">Supervisor Name</label>
      <select class="form-control" name="parent_user_id" id="parent_user_id">
      	
      	
      	<c:forEach items="${employeeList}" var="employee">
			    			    <option value="${employee.user_id}">${employee.name}</option>
	   </c:forEach>
      	</select> 
      
    </div>
  </div>
  
      	 <div class="col-sm-3">
  	<div class="form-group">
      <label for="QrCode">Qr Code</label>
      <input type="text" class="form-control" id="Qr Code" value="${employeeDetails.qr_code}" name="QrCode" placeholder="Qr Code" >
    </div>
  </div>
      	
      <div class="col-sm-3">
  	<div class="form-group">
      <label for="email">Type</label>
      <select class="form-control" id="type" name="type">
      	<option value="Contract">Contract</option>
      	<option value="Permanent">Permanent</option>
      	
      </select>     
    </div>
  </div>
  
  <div class="col-sm-12" align="center">
  	<div class="form-group">
      <button class="btn btn-success" type="button" onclick='addEmployee()'>Save</button>
		<button class="btn btn-danger" type="reset" onclick='window.location="?a=showEmployeeMaster"'>Cancel</button>
	</div>
  </div>    
    
  
  </div>
  
 
		
	
</div>
</form>








<script>

<c:if test="${employeeDetails.user_id eq null}">
	document.getElementById("divTitle").innerHTML="Add Employee";
</c:if>
<c:if test="${employeeDetails.user_id ne null}">
	
	document.getElementById("divTitle").innerHTML="Update Employee";
	type.value="${employeeDetails.type}";
	
	document.getElementById('parent_user_id').value='${employeeDetails.parent_user_id}';
</c:if>
	
	
</script>
