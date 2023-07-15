
<style>
.datepicker {position: relative; z-index:100;}
</style>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="jspName" value='${requestScope["outputObject"].get("contentJspName")}' />

<c:set var="userName" value='${requestScope["outputObject"].get("userName")}' />




<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>SKP Security Gate</title>
  <link rel="icon" href="img/favicon.ico" type="image/png" sizes="16x16">
  <!-- Tell the browser to be responsive to screen width -->
  <meta name="viewport" content="width=device-width, initial-scale=1">
  
   <link rel="stylesheet" href="css/jquery-ui.css">
  <!-- Font Awesome -->
  
  

  <link rel="stylesheet" href="plugins/datatables-bs4/css/dataTables.bootstrap4.min.css">
  <link rel="stylesheet" href="plugins/datatables-responsive/css/responsive.bootstrap4.min.css">
  
  <link rel="stylesheet" href="plugins/fontawesome-free/css/all.min.css">
  <link rel="stylesheet" href="css/font-awesome.min.css">
  <!-- Ionicons -->
  <!-- <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css"> -->
  <!-- overlayScrollbars -->
  <link rel="stylesheet" href="plugins/overlayScrollbars/css/OverlayScrollbars.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="dist/css/adminlte.min.css">
  <!-- Google Font: Source Sans Pro -->
  <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700" rel="stylesheet">
  
  
  <!-- jQuery -->
  <script src="js/common.js"></script>
<script src="plugins/jquery/jquery.min.js"></script>
<!-- Bootstrap 4 -->
<script src="plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- overlayScrollbars -->
<script src="plugins/overlayScrollbars/js/jquery.overlayScrollbars.min.js"></script>
<!-- AdminLTE App -->
<script src="dist/js/adminlte.min.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="dist/js/demo.js"></script>

<script src="js/jquery-ui.js"></script>

  <link rel="stylesheet" href="plugins/toastr/toastr.min.css">
  <script src="plugins/toastr/toastr.min.js"></script>

<script src="plugins/datatables/jquery.dataTables.min.js"></script>
<script src="plugins/datatables-bs4/js/dataTables.bootstrap4.min.js"></script>
<script src="plugins/datatables-responsive/js/dataTables.responsive.min.js"></script>
<script src="plugins/datatables-responsive/js/responsive.bootstrap4.min.js"></script>


</head>
<body class="hold-transition sidebar-mini sidebar-expand layout-fixed ">
<!-- Site wrapper -->
<div class="wrapper">
  <!-- Navbar -->
  <nav class="main-header navbar navbar-expand navbar-white navbar-light">
    <!-- Left navbar links -->
    <ul class="navbar-nav">
      <li class="nav-item">
        <a class="nav-link" data-widget="pushmenu" href="#" role="button"><i class="fas fa-bars"></i></a>
      </li>
     <!--  <li class="nav-item d-none d-sm-inline-block">
        <a href="#" id="divBackButton" class="nav-link">Back</a>
      </li> -->
      <!-- <li class="nav-item d-none d-sm-inline-block">
        <a href="#" class="nav-link">Contact</a>
      </li> -->
    </ul>

			<div class="row" style="width:-webkit-fill-available" align="center">
				<div class="col-12"><div style="font-weight:800"id="divTitle"></div></div>
			</div>




<ul class="navbar-nav ml-auto">     
      
      <li class="nav-item">
        <a href="?a=showHomePage" class="nav-link"><strong>Home</strong></a>
      </li> 
      
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          <strong>${userdetails.username}</strong>
        </a>
        <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdown">
         
          
          <a href="?a=showChangePassword" class="dropdown-item">
            <strong>Change Password</strong>
          </a>
         
          <a href="javascript:logout();" class="dropdown-item" id="refLogout">
            <div class="dropdown-divider"></div>
            <strong>Logout</strong>
          </a>
        </div>
      </li>
      
    
    </ul>

   
  </nav>
  <!-- /.navbar -->

  <!-- Main Sidebar Container -->
  <aside class="main-sidebar sidebar-dark-primary elevation-4">
    <!-- Sidebar -->
    <div class="sidebar">
      <!-- Sidebar user (optional) -->
      <div class="user-panel mt-3 pb-3 mb-3 d-flex">
        <div class="image" style="padding-top:10px">
          <img src="dist/img/avatar5.png" class="img-circle elevation-2" alt="User Image">
        </div>
        <div class="info">
          <a href="#" class="d-block">${userdetails.username}<br>(${userdetails.firm_name})</a>
        </div>
      </div>
      
      
      

      <!-- Sidebar Menu -->
      <nav class="mt-2">
        <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
          <!-- Add icons to the links using the .nav-icon class
               with font-awesome or any other icon font library -->
         
         
         
         <c:forEach items="${elements}" var="item">	  
	   <li class="nav-item has-treeview">         
            <a href="#" class="nav-link" style="color:floralwhite;font-weight:800"><i class="nav-icon fas fa-tachometer-alt"></i><p>${item.getElementName()}<i class="right fas fa-angle-left"></i></p></a>	          
            <ul class="nav nav-treeview">
				
						          <c:forEach items="${item.getChildElements()}" var="item1">
		          		
		          		<li class="nav-item"><a href="${item1.getElementUrl()}" style="color:floralwhite" class="nav-link"><i class="nav-icon fas fa-edit"></i><p style="color:cyan">${ item1.getElementName()}</p></a></li>		            	
		           </c:forEach>           
            </ul>
          </li>
		  </c:forEach>
         
         
          
        </ul>
      </nav>
      <!-- /.sidebar-menu -->
    </div>
    <!-- /.sidebar -->
  </aside>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper"  style="background:lightblue">
  
      <!-- Main content -->
    <section class="content">

      <div class="container-fluid">
        <div class="row">
          <div class="col-12">         
            <jsp:include page="${jspName}"/>      
            
            
            
          </div>
        </div>
      </div>
    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->



  <!-- Control Sidebar -->
  <aside class="control-sidebar control-sidebar-dark">
    <!-- Control sidebar content goes here -->
  </aside>
  <!-- /.control-sidebar -->
</div>
<!-- ./wrapper -->


</body>
</html>



<div class="container" > 

  <!-- Modal -->
  <div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog modal-lg" >
    
      <!-- Modal content-->
      <div class="modal-content">
        <!-- <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Modal Header</h4>
        </div> -->
        <div class="modal-body" align="center">
          <p id="responseText"><div align="center" class="loader" id="loader"></div></p>
        </div>
        <div class="modal-footer" style="text-align:-webkit-right"> 
          <button id="closebutton" type="button"  onclick='location.reload()' class="btn btn-danger" data-dismiss="modal">Close</button>
        </div>
      </div>
      
    </div>
  </div>
  
</div>

<script>
function navigateToURL(theURL)
{
    
	window.location=theURL;
	
		
}


</script>

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
        	
        	window.location.reload();
		}
	  };
	  xhttp.open("GET","?a=Logout", true);    
	  xhttp.send();
}


$(document).ready(function () {
	
  });




</script>





