



<style>
.switch {
  position: relative;
  display: inline-block;
  width: 60px;
  height: 34px;
}

.switch input {display:none;}

.slider {
  position: absolute;
  cursor: pointer;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: #ccc;
  -webkit-transition: .4s;
  transition: .4s;
}

.slider:before {
  position: absolute;
  content: "";
  height: 26px;
  width: 26px;
  left: 4px;
  bottom: 4px;
  background-color: white;
  -webkit-transition: .4s;
  transition: .4s;
}

input:checked + .slider {
  background-color: gold;
}

input:focus + .slider {
  box-shadow: 0 0 1px gold;
}

input:checked + .slider:before {
  -webkit-transform: translateX(26px);
  -ms-transform: translateX(26px);
  transform: translateX(26px);
}

/* Rounded sliders */
.slider.round {
  border-radius: 34px;
}

.slider.round:before {
  border-radius: 50%;
}
</style>

<script>

function logout()
{
	document.getElementById("closebutton").style.display='none';
	$('#myModal').modal({backdrop: 'static', keyboard: false});;
	
	 var xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() 
	  {
	    if (xhttp.readyState == 4 && xhttp.status == 200) 
	    {	
		   document.getElementById("loader").style.display='none';		   		   		  
	        document.getElementById("responseText").innerHTML=xhttp.responseText;
	        document.getElementById("closebutton").style.display='block';
        	$('#myModal').modal({backdrop: 'static', keyboard: false});;
        	
        	document.getElementById("closebutton").onclick = function() {window.location="Login.jsp"};
		}
	  };
	  xhttp.open("GET","?a=Logout", true);    
	  xhttp.send();
}
</script>



<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>                        
      </button>
      <a class="navbar-brand active" style="color:blanchedalmond" href="?a=showHomePage"><b>${sessionScope.userdetails.school_name}</b></a>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav">
      
      
      
      <c:forEach items="${elements}" var="item">							
		<li class="dropdown">
	          <a class="dropdown-toggle" data-toggle="dropdown" href="#"><b>${item.getElementName()}</b><span class="caret"></span></a>
	          <ul class="dropdown-menu">          
          
		          <c:forEach items="${item.getChildElements()}" var="item1">
		            	<li><a href="${ item1.getElementUrl()}">${ item1.getElementName()}</a></li>                                    
		           </c:forEach>           
          	   </ul>
        </li>				
	  </c:forEach>
	  
	  
      
      
           
        
      
        
        
    
      
      <ul class="nav navbar-nav navbar-right">        
        <li><a href="javascript:logout();">Logout (${userName})</a></li>
      </ul>
      
        <ul class="nav navbar-nav navbar-right">        
        <li><a href="?a=showChangePassword">Change Password</a></li>
      </ul>
      
      
    </div>
  </div>
</nav>



		
			