<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
           
           
           


<c:set var="ShiftDetails" value='${requestScope["outputObject"].get("ShiftDetails")}' />


   





</head>


<script>


function addShift()
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

<form id="frm" action="?a=addShift" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
<input type="hidden" name="app_id" value="${userdetails.app_id}">
<input type="hidden" name="user_id" value="${userdetails.user_id}">
<input type="hidden" name="callerUrl" id="callerUrl" value="">

<div class="row">
  <div class="col-sm-12">
  	<div class="form-group">
      <label for="email">Shift Name</label>
      <input type="text" class="form-control" id="shift_name" value="${ShiftDetails.shift_name}"  placeholder="eg. Shift Name" name="shift_name">
      <input type="hidden" name="hdnShiftId" value="${ShiftDetails.shift_id}" id="hdnShiftId">
    </div>
  </div>
  
   <div class="col-sm-3">
  	<div class="form-group">
      <label for="email">From Time Hour</label>
      <input type="text" class="form-control" id="time_hour" value="${ShiftDetails.fromHour}"  placeholder="eg. Time Hour" name="from_time_hour">
            
    </div>
  </div>
  
   <div class="col-sm-3">
  	<div class="form-group">
      <label for="email">From Time Minute</label>
      <input type="text" class="form-control" id="time_minute" value="${ShiftDetails.fromMinute}"  placeholder="eg. Time Minute" name="from_time_minute">
            
    </div>
  </div>
  
 <div class="col-sm-3">
  	<div class="form-group">
      <label for="email">To Time Hour</label>
      <input type="text" class="form-control" id="time_hour" value="${ShiftDetails.toHour}"  placeholder="eg. Time Hour" name="to_time_hour">
            
    </div>
  </div>
  
   <div class="col-sm-3">
  	<div class="form-group">
      <label for="email">To Time Minute</label>
      <input type="text" class="form-control" id="time_minute" value="${ShiftDetails.toMinute}"  placeholder="eg. Time Minute" name="to_time_minute">
            
    </div>
  </div>
  
  <div class="col-sm-12">
  	<div class="form-group">
      <label for="email">Late Shift Cutoff</label>
      <input type="text" class="form-control" id="time_hour" value="${ShiftDetails.late_shift_cutoff}"  placeholder="eg. Late Shift Cutoff" name="late_shift_cutoff">
            
    </div>
  </div>
  
  
  
 		
		<button class="btn btn-success" type="button" onclick='addShift()'>Save</button>
		<button class="btn btn-danger" type="reset" onclick='window.location="?a=showShiftMaster"'>Cancel</button>
		
		
</div>
</form>

<script>
	
	
	<c:if test="${ShiftDetails.ShiftId eq null}">
		document.getElementById("divTitle").innerHTML="Add Shift";
		document.title +=" Add Shift ";
	</c:if>
	<c:if test="${ShiftDetails.ShiftId ne null}">
		document.getElementById("divTitle").innerHTML="Update Shift";
		document.title +=" Update Shift ";
	</c:if>
	
	
	
</script>



