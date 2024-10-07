
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

<c:set var="visitorDetails" value='${requestScope["outputObject"].get("visitorDetails")}' />
<c:set var="distinctPurposeOfVisist" value='${requestScope["outputObject"].get("distinctPurposeOfVisist")}' />
<c:set var="employeeList" value='${requestScope["outputObject"].get("employeeList")}' />
<c:set var="listOfEmployees" value='${requestScope["outputObject"].get("EmployeeList")}' />


</head>


<script>


function addPrefilledVisitor()
{	

  var listofPhotosImage=document.getElementsByClassName("photosimg");
  for(var m=0;m<listofPhotosImage.length;m++)
  {
    hdnphotosbase64div.innerHTML+="<input type='hidden' name='hdnphotobase64"+m+"' value='"+document.getElementById("photo"+m).src+"'>";
  }


  

  
  
  
  
	
	 if(visitorname.value=="")
	  {
	  	
	  
	  toastr["error"]("Please enter Visitor Name");
   	  toastr.options = {"closeButton": false,"debug": false,"newestOnTop": false,"progressBar": false,
   	  "positionClass": "toast-top-right","preventDuplicates": false,"onclick": null,"showDuration": "1000",
   	  "hideDuration": "500","timeOut": "1000","extendedTimeOut": "1000","showEasing": "swing","hideEasing": "linear",
   	  "showMethod": "fadeIn","hideMethod": "fadeOut"
   	   };
	  	
	  	 
       	
   	visitorname.focus();
	  	return;
	  }
	 

	 if(purpose_of_visit.value=="")
	  {
	  	
	  
	  toastr["error"]("Please enter Purpose Of Visit");
  	  toastr.options = {"closeButton": false,"debug": false,"newestOnTop": false,"progressBar": false,
  	  "positionClass": "toast-top-right","preventDuplicates": false,"onclick": null,"showDuration": "1000",
  	  "hideDuration": "500","timeOut": "1000","extendedTimeOut": "1000","showEasing": "swing","hideEasing": "linear",
  	  "showMethod": "fadeIn","hideMethod": "fadeOut"
  	   };
	  	
	  	 
      	
  	purpose_of_visit.focus();
	  	return;
	  }
	 
	 if(document.getElementById("MobileNo").value=="" || document.getElementById("MobileNo").value.length!=10)
	  {
	  	
	  
	  toastr["error"]("Please enter Mobile Number");
	  toastr.options = {"closeButton": false,"debug": false,"newestOnTop": false,"progressBar": false,
	  "positionClass": "toast-top-right","preventDuplicates": false,"onclick": null,"showDuration": "1000",
	  "hideDuration": "500","timeOut": "1000","extendedTimeOut": "1000","showEasing": "swing","hideEasing": "linear",
	  "showMethod": "fadeIn","hideMethod": "fadeOut"
	   };
	  	
	  	 
   	
	  MobileNo.focus();
	  	return;
	  }
	 
	
	
	document.getElementById("frm").submit(); 
}

function checkforMatchEmployee()
{
	var searchString= document.getElementById("ContactToEmployee").value;
	
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
			document.getElementById("ContactToEmployee").disabled=true;						
		}
	else
		{
			//searchForCustomer(searchString);
		}
	
	
}

function resetEmployee()
{	
	ContactToEmployee.disabled=false;
	ContactToEmployee.value="";
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


<form id="frm" action="?a=addPrefilledVisitor" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
<input type="hidden" name="app_id" value="${userdetails.app_id}">
<input type="hidden" name="user_id" value="${userdetails.user_id}">
<input type="hidden" name="callerUrl" id="callerUrl" value="">

<div class="row">
  <div class="col-sm-12">
  	<div class="form-group">
      <label for="visitorname">Visitor Name*</label>
      <input type="text" class="form-control" id="visitorname" value="${visitorDetails.visitor_name}" name="visitorname" placeholder="Visitor Name">
      <input type="hidden" name="hdnprefilledvisitorId" value="${visitorDetails.prefilled_visitor_id}" id="hdnprefilledvisitorId">
    </div>
  </div>
  
  <div class="col-sm-12">
  	<div class="form-group">
      <label for=Address>Address</label>
      <input type="text" class="form-control" id="address" value="${visitorDetails.address}" name="address" placeholder="Address">
    </div>
  </div>
  
	<div class="col-sm-12">
		<div class="form-group">
			<label for="PurposeofVisit">Purpose Of Visit*</label>
			<input type="text" class="form-control form-control-sm" id="purpose_of_visit" placeholder="Eg. case" value="${visitorDetails.purpose_of_visit}" list="datalistPurposes"  name="purpose_of_visit">
			<input type="hidden" name="drpVisitorId" value="${visitorDetails.visitor_id}" id="drpVisitorId">
			<datalist id="datalistPurposes">
				<c:forEach items="${distinctPurposeOfVisist}" var="purpose">
				<option id="${purpose.purpose_of_visit}">${purpose.purpose_of_visit}</option>			    
				</c:forEach>	
			</datalist>      
		</div>
	</div>
  
   <div class="col-sm-12">
  	<div class="form-group">
      <label for="Remarks">Remarks</label>
      <input type="text" class="form-control" id="remarks" value="${visitorDetails.remarks}" name="remarks" placeholder="Remarks">
    </div>
  </div>
  
  <div class="col-sm-12">
  	<div class="form-group">
      <label for="MobileNo">Mobile No*</label>
      <input type="text" class="form-control" id="MobileNo" value="${visitorDetails.mobile_no}" name="MobileNo" placeholder="Mobile No" onkeypress="digitsOnly(event)" maxlength="10" required>
    </div>
  </div>
  
  <div class="col-sm-12">
  	<div class="form-group">
      <label for="EmailId">Email Id</label>
      <input type="text" class="form-control" id="EmailId" value="${visitorDetails.email_id}" name="EmailId" placeholder="Email Id">
    </div>
  </div>
  

  <div class="col-sm-12">
	<div class="form-group">
		
	<label for="email">Contact To Employee </label>     
	<div class="input-group input-group-sm">

	<input type="textbox" name="ContactToEmployee" id="ContactToEmployee" class="form-control form-control-sm" list="listOfEmployee" onchange="checkforMatchEmployee()"/> 
	<input type="hidden" name="hdnselectedemployee" id="hdnselectedemployee" value="">  <span class="input-group-append">
		<button type="button" class="btn btn-danger btn-flat" onclick="resetEmployee()">Reset</button>
		</span>
	</div>
	</div>
		</div>



  

  
  
  <c:if test="${action ne 'Update'}">
	<div class="col-sm-12" align="center">
  		<div class="form-group">
			<button class="btn btn-success" type="button" onclick='addPrefilledVisitor()'>Save</button>
			<button class="btn btn-danger" type="reset" onclick='window.location="?a=showVisitors"'>Cancel</button>
		</div>
  	</div>
		
  </c:if>
  
		
		
	  
     
  
</div>
  
  </div>
  	
		
</div>
</form>








<script>

<c:if test="${visitorDetails.visitor_id eq null}">
	document.getElementById("divTitle").innerHTML="Add Prefilled Visitor";
	document.title +=" Add Prefilled Visitor ";
</c:if>



var arr=window.location.toString().split("/");
callerUrl.value=(arr[0]+"//"+arr[1]+arr[2]+"/"+arr[3]+"/");	
	
</script>



  
