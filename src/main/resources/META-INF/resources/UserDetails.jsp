<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
           
           
           


<c:set var="userDetails" value='${requestScope["outputObject"].get("userDetails")}' />
<c:set var="lstUserRoleDetails" value='${requestScope["outputObject"].get("lstUserRoleDetails")}' />

   


</head>




<br>


<div class="container" style="padding:20px;background-color:white"> 


<form id="frm" action="?a=addUser" method="post" enctype="multipart/form-data" accept-charset="UTF-8">



<div class="row">
  <div class="col-sm-6">
  	<div class="form-group">
      <label for="UserName">User Name </label>
      <input type="text" class="form-control" id="username" readonly value="${userDetails.username}" name="userName" placeholder="User Name">
      <input type="hidden" name="hdnUserId" value="${userDetails.user_id}" id="hdnUserId">
    </div>
  </div>
  
  <div class="col-sm-6">
  	<div class="form-group">
      <label for="EmailId">Email Id</label>
      <input type="text" class="form-control" id="email_id" readonly value="${userDetails.email}" name="email_id" placeholder="Email Id">
      
    </div>
  </div>
  
   <div class="col-sm-6">
  	<div class="form-group">
      <label for="MobileNumber">Mobile No </label>
      <input type="text" class="form-control" id="mobile_no" readonly value="${customerDetails.mobile_no}" name="mobile_no" placeholder="Mobile No" onkeypress="digitsOnly(event)" maxlength="10" required>
    </div>
  </div>
  
  
  
  
  <div class="col-sm-12">
  	<div class="form-group">
     
      
      
          <div class="form-group">
                        <label>Tagged Roles
                        </label>
                        <select disabled style="height:250px" multiple="" class="custom-select" id="drpavailableroles">
                        	
	   						
	   						
	   						<c:forEach var="entry" items="${lstUserRoleDetails}">
	   							<option id="${entry.role_id}">${entry.role_name}</option>	
	   						</c:forEach>                          
	   						
	   						
                        </select>
                      </div>
      
      
    </div>
  </div>
  
  
  
 
  
  
  
  
  <div class="col-sm-12" align="center">
  	<div class="form-group">
      
   
  <button class="btn btn-danger" type="reset" onclick='window.location="?a=showHomePage"'>Back</button>
     
    </div>
  </div>
  
  <script>
  
<c:if test="${userDetails.user_id eq null}">
		document.getElementById("divTitle").innerHTML="User Details";
		document.title +=" User Details ";
	</c:if>

	</script>
