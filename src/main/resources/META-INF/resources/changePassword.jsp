<style>
.vertiAlignMiddle
{
	vertical-align:middle!important;
}
</style>

<script>
	
function changePasswordAjax()
{	
	var oldPassword= encodeURIComponent(document.getElementById("txtoldpassword").value);
	var newPassword= encodeURIComponent(document.getElementById("txtnewpassword").value);
	var confirmnewpassword= encodeURIComponent(document.getElementById("txtconfirmnewpassword").value);
	
	
	if(confirmnewpassword!=newPassword)
		{
			alert("New password and Confirm Password should be same");
			return;	
		}
	
	
	
	
	
	var xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() 
	  {
	    if (xhttp.readyState == 4 && xhttp.status == 200) 
	    { 		     
	    	 
	    	
	    
	    
		    	
		    	if(xhttp.responseText=="Password Changed Succesfully ")
		    		{
		    		  toastr["success"](xhttp.responseText);
				    	toastr.options = {"closeButton": false,"debug": false,"newestOnTop": false,"progressBar": false,
				    	  "positionClass": "toast-top-right","preventDuplicates": false,"onclick": null,"showDuration": "1000",
				    	  "hideDuration": "500","timeOut": "500","extendedTimeOut": "500","showEasing": "swing","hideEasing": "linear",
				    	  "showMethod": "fadeIn","hideMethod": "fadeOut"}
		    			window.location='?a=showHomePage';
		    		}
		    	else
		    		{
		    		 toastr["error"](xhttp.responseText);
				    	toastr.options = {"closeButton": false,"debug": false,"newestOnTop": false,"progressBar": false,
				    	  "positionClass": "toast-top-right","preventDuplicates": false,"onclick": null,"showDuration": "1000",
				    	  "hideDuration": "500","timeOut": "500","extendedTimeOut": "500","showEasing": "swing","hideEasing": "linear",
				    	  "showMethod": "fadeIn","hideMethod": "fadeOut"}
		    			
		    		}
		    	
	    	 
	    	    	 
	    
		}
	  };
	  xhttp.open("POST","${masterUrl}?a=changePassword&oldPassword="+oldPassword+"&newPassword="+newPassword+"&username=${userdetails.username}",true);    
	  xhttp.send();	
}
	
</script>

<br>

<div class="container" style="padding:20px;background-color:white">
<form id="frm" action="${masterUrl}?a=addCategory" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
<input type="hidden" name="app_id" value="${userdetails.app_id}">
<input type="hidden" name="user_id" value="${userdetails.user_id}">
<input type="hidden" name="callerUrl" id="callerUrl" value="">


<div class="row">
  <div class="col-sm-12">
  	<div class="form-group">      
	  <label>Current Password</label>      
      <input type="password" class="form-control" id="txtoldpassword" placeholder="Current Password">
      <input type="hidden" name="hdnCategoryId" value="${categoryDetails.category_id}" id="hdnCategoryId">
    </div>
  </div>
  
  
  <div class="col-sm-12">
  	<div class="form-group">      
	  <label>New Password</label>      
	  <input type="password"  class="form-control" id="txtnewpassword" placeholder="New Password"> 
	 </div>
  </div>
  
   <div class="col-sm-12">
  	<div class="form-group">      
	  <label>Confirm Password</label>      
      <input type="password" class="form-control" id="txtconfirmnewpassword"  placeholder="Confirm Password">
      
    </div>
  </div>
  
  
   
 
 
  
 
 
		<button class="btn btn-success" type="button" onclick='changePasswordAjax()'>Save</button>
		<button class="btn btn-danger" type="reset" onclick='window.location="?a=showHomePage"'>Cancel</button>
		
		
</div>
</form>

</div>




     
     
     <script>
	
	

		document.getElementById("divTitle").innerHTML="Change Password";

</script>



     