


<style>
body, html {
  height: 100%;
  margin: 0;
}

.bg {
  /* The image used */
  

  /* Full height */
  height: 100%; 

  /* Center and scale the image nicely */
  background-position: center;
  background-repeat: no-repeat;
  background-size: cover;
  
}
</style>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>


<link rel="stylesheet" href="css/bootstrap.min.css">
 <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />

<script src="js/jquery.min.js"></script>
  <script src="js/bootstrap.min.js"></script>
  
  <script>
  
  
  
  
  window.addEventListener('keydown', function (e) {
	  if(e.keyCode == 13){
		  login();
	   }
	});
  
  
  
  
  function login()
  {
	  document.getElementById("closebutton").style.display='none';
		//$('#myModal').modal({backdrop: 'static', keyboard: false});;
		
		
		
		var usernameRegex =/^[a-zA-Z0-9]+$/;
		var passwordRegex =/^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,}$/;
		

		

	  
	  
	  var username=document.getElementById("txtusername");
	  var password=document.getElementById("txtpassword");
	  
	  
	  
	  
	  if(username.value=="")
		  {
		  	
		  
		  toastr["error"]("Please enter Valid username");
	    	toastr.options = {"closeButton": false,"debug": false,"newestOnTop": false,"progressBar": false,
	    	  "positionClass": "toast-top-right","preventDuplicates": false,"onclick": null,"showDuration": "1000",
	    	  "hideDuration": "500","timeOut": "1000","extendedTimeOut": "1000","showEasing": "swing","hideEasing": "linear",
	    	  "showMethod": "fadeIn","hideMethod": "fadeOut"
	    	};
		  	
		  	 
	        	
		  	username.focus();
		  	return;
		  }
	  
	/*   if(!usernameRegex.test(username.value))
		  {
		  document.getElementById("responseText").innerHTML="Please enter Valid username";
		  document.getElementById("loader").style.display='none';
		  document.getElementById("closebutton").style.display='block';
      	$('#myModal').modal({backdrop: 'static', keyboard: false});;      	
	  	username.focus();
	  	return;
		  } */
	  
	  
	  if(password.value=="")
	  {
		  
		  toastr["error"]("Please enter Valid password");
	    	toastr.options = {"closeButton": false,"debug": false,"newestOnTop": false,"progressBar": false,
	    	  "positionClass": "toast-top-right","preventDuplicates": false,"onclick": null,"showDuration": "1000",
	    	  "hideDuration": "500","timeOut": "1000","extendedTimeOut": "1000","showEasing": "swing","hideEasing": "linear",
	    	  "showMethod": "fadeIn","hideMethod": "fadeOut"
	    	};
		  
		  
			   	
	  	password.focus();
	  	return;
	  }
	  
/* 	  if(!passwordRegex.test(password.value))
	  {
		  
			 document.getElementById("responseText").innerHTML="Please enter Valid password";
			 document.getElementById("loader").style.display='none';
			 document.getElementById("closebutton").style.display='block';
	        	$('#myModal').modal({backdrop: 'static', keyboard: false});;  	
	  	password.focus();
	  	return;
	  } */
	  
	  
	  
	  
	  var xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() 
	  {
	    if (this.readyState == 4 && this.status == 200) 
	    {
	      if(this.responseText=="Succesfully Logged In")
	    	  {
	    	  
	    	  	window.location=window.location.toString().replace('Login.jsp','?a=showHomePage');
	    	  	

	    	  	
	    	  }
	      else
	    	  {
	    	  
	    	  toastr["error"](this.responseText);
		    	toastr.options = {"closeButton": false,"debug": false,"newestOnTop": false,"progressBar": false,
		    	  "positionClass": "toast-top-right","preventDuplicates": false,"onclick": null,"showDuration": "1000",
		    	  "hideDuration": "500","timeOut": "500","extendedTimeOut": "500","showEasing": "swing","hideEasing": "linear",
		    	  "showMethod": "fadeIn","hideMethod": "fadeOut"
		    	};	    	  
	    	  
	    	  	username.value="";
	    	  	password.value="";
	    	  	username.focus(); 
	    	  	
	    	  }
	    }
	  };
	  xhttp.open("POST", "BaseController?actionName=validateLogin", true);
	  xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	  xhttp.send("txtusername="+username.value+"&txtpassword="+password.value);
	  
	  
	  
	  
	  
  }
  </script>    




<script>	
	
	function sendresetpasswordemail()
	{
		var xhttp = new XMLHttpRequest();
		
		
		document.getElementById("closebutton").style.display='none';
		   document.getElementById("loader").style.display='block';
		$("#myModal").modal();
		
		
		  xhttp.onreadystatechange = function() 
		  {
		    if (xhttp.readyState == 4 && xhttp.status == 200) 
		    { 		      
		    	
		    	
		    	
		    	
		    	$('#myModal').modal('hide');				  
		      
			  toastr["success"](xhttp.responseText);
		    	toastr.options = {"closeButton": false,"debug": false,"newestOnTop": false,"progressBar": false,
		    	  "positionClass": "toast-top-right","preventDuplicates": false,"onclick": null,"showDuration": "5000",
		    	  "hideDuration": "5000","timeOut": "5000","extendedTimeOut": "5000","showEasing": "swing","hideEasing": "linear",
		    	  "showMethod": "fadeIn","hideMethod": "fadeOut"}
		    	
		    	setTimeout(function() { window.location="?a=showHomePage";}, 2000);

		    	
		      
			  
			}
		  };
		  xhttp.open("GET","${masterUrl}?a=sendResetPasswordEmail&emailid="+txtemail.value, true);    
		  xhttp.send();
	}
	
	
	
	
</script>
	


<style>








.loader {
  border: 16px solid #f3f3f3;
  border-radius: 50%;
  border-top: 16px solid #3498db;
  width: 120px;
  height: 120px;
  -webkit-animation: spin 2s linear infinite;
  animation: spin 2s linear infinite;
}

@-webkit-keyframes spin {
  0% { -webkit-transform: rotate(0deg); }
  100% { -webkit-transform: rotate(360deg); }
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}
</style>



<style type="text/css">


/*
 * Specific styles of signin component
 */
/*
 * General styles
 */
body, html {
    height: 100%;
    background-repeat: no-repeat;
    background-image: linear-gradient(rgb(104, 145, 162), lightblue); 
    
    
}

.card-container.card {
    max-width: 350px;
    padding: 40px 40px;
}

.btn {
    font-weight: 700;
    height: 36px;
    -moz-user-select: none;
    -webkit-user-select: none;
    user-select: none;
    cursor: default;
}

/*
 * Card component
 */
.card {
    background-color: #F7F7F7;
    /* just in case there no content*/
    padding: 20px 25px 30px;
    margin: 0 auto 25px;
    margin-top: 50px;
    /* shadows and rounded borders */
    -moz-border-radius: 2px;
    -webkit-border-radius: 2px;
    border-radius: 2px;
    -moz-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
    -webkit-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
    box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
}

.profile-img-card {
    width: 96px;
    height: 96px;
    margin: 0 auto 10px;
    display: block;
    -moz-border-radius: 50%;
    -webkit-border-radius: 50%;
    border-radius: 50%;
}

/*
 * Form styles
 */
.profile-name-card {
    font-size: 16px;
    font-weight: bold;
    text-align: center;
    margin: 10px 0 0;
    min-height: 1em;
}

.reauth-email {
    display: block;
    color: #404040;
    line-height: 2;
    margin-bottom: 10px;
    font-size: 14px;
    text-align: center;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    -moz-box-sizing: border-box;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
}

.form-signin #inputEmail,
.form-signin #inputPassword {
    direction: ltr;
    height: 44px;
    font-size: 16px;
}

.form-signin input[type=email],
.form-signin input[type=password],
.form-signin input[type=text],
.form-signin button {
    width: 100%;
    display: block;
    margin-bottom: 10px;
    z-index: 1;
    position: relative;
    -moz-box-sizing: border-box;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
}

.form-signin .form-control:focus {
    border-color: rgb(104, 145, 162);
    outline: 0;
    -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075),0 0 8px rgb(104, 145, 162);
    box-shadow: inset 0 1px 1px rgba(0,0,0,.075),0 0 8px rgb(104, 145, 162);
}

.btn.btn-signin {
    /*background-color: #4d90fe; */
    background-color: rgb(104, 145, 162);
    /* background-color: linear-gradient(rgb(104, 145, 162), rgb(12, 97, 33));*/
    padding: 0px;
    font-weight: 700;
    font-size: 14px;
    height: 36px;
    -moz-border-radius: 3px;
    -webkit-border-radius: 3px;
    border-radius: 3px;
    border: none;
    -o-transition: all 0.218s;
    -moz-transition: all 0.218s;
    -webkit-transition: all 0.218s;
    transition: all 0.218s;
}

.btn.btn-signin:hover,
.btn.btn-signin:active,
.btn.btn-signin:focus {
    background-color: rgb(12, 97, 33);
}

.forgot-password {
    color: rgb(104, 145, 162);
}

.forgot-password:hover,
.forgot-password:active,
.forgot-password:focus{
    color: rgb(12, 97, 33);
}

</style>


<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Hisaab Cloud</title>
<link rel="icon" href="img/favicon.ico" type="image/png" sizes="16x16">

<link rel="stylesheet" href="plugins/toastr/toastr.min.css">
<script src="plugins/toastr/toastr.min.js"></script>
</head>
<body class="bg">







    <div class="container">
        <div class="card card-container">
        
        
        <div class="row">
    		<div align="center" class="col-md-12" style="font-size:30px;font-size: 30px;font-style: italic;color: darkblue;font-weight: 800;">Hisaab Cloud</div>
		</div>
        
        <img id="profile-img" src="img/pos.jpg"   class="img-responsive" />
            <p id="profile-name" class="profile-name-card"></p>
         <form  action="?a=validateLogin" method="POST">
<span id="reauth-email" class="reauth-email"></span>
  <div class="form-group"> 
  
     <input type="text" style="margin-bottom:10px"  id="txtemail" maxlength="50" name="txtemail" class="form-control"  placeholder="Email" required autofocus>
     	
    
    <br>
<button class="btn btn-lg btn-primary btn-block btn-signin" type="button" onclick="sendresetpasswordemail()">Send Reset Password</button>


  </div>
</form>

  <div align="center">
            	Designed and Developed By <br> 
            	<a href="mailto:crystaldevelopers2017@gmail.com">Crystal Developers</a>
            	
            </div>

        </div><!-- /card-container -->
    </div><!-- /container -->




<div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <!-- <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Modal Header</h4>
        </div> -->
        <div class="modal-body" align="center">
          <p id="responseText"><div align="center" class="loader" id="loader"></div></p>
        </div>
        <div class="modal-footer">
          <button id="closebutton" type="button" onclick='location.reload()' class="btn btn-default" data-dismiss="modal">Close Me</button>
        </div>
      </div>
      
    </div>
  </div>





</body>
</html>


<script>

$(document).ready(function() {
    $("#show_hide_password a").on('click', function(event) {
        event.preventDefault();
        if($('#show_hide_password input').attr("type") == "text"){
            $('#show_hide_password input').attr('type', 'password');
            $('#show_hide_password i').addClass( "fa-eye-slash" );
            $('#show_hide_password i').removeClass( "fa-eye" );
        }else if($('#show_hide_password input').attr("type") == "password"){
            $('#show_hide_password input').attr('type', 'text');
            $('#show_hide_password i').removeClass( "fa-eye-slash" );
            $('#show_hide_password i').addClass( "fa-eye" );
        }
    });
});

</script>