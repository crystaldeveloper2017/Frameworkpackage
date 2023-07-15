  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
           
           
           

<c:set var="ListOfTables" value='${requestScope["outputObject"].get("ListOfTables")}' />



   





</head>


<script>







</script>


<br>

<div class="container" style="padding:20px;background-color:white">

<form id="frm" action="?a=addfirm" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
<div class="row">

<c:forEach items="${ListOfTables}" var="item">
	<div class="col-lg-3 col-6">
	
	<c:if test="${item.order_id eq null}">
		<div class="small-box bg-danger">
		<div class="inner">	              
	              <h3>${item.table_no}</h3>
	              	
	               
	              </div>
	              <div class="icon">
	                <i class="ion ion-bag"></i>
	              </div>
	              <a href="#" onclick="window.location='?a=showTableOrders&table_id=${item.table_id}'" class="small-box-footer">More info <i class="fas fa-arrow-circle-right"></i></a>
	            
	</c:if>
	
	<c:if test="${item.order_id ne null}">
		<div class="small-box bg-info">
		<div class="inner">	              
	              <h3>${item.table_no}</h3>
	              	<div style="color:chartreuse">Active Since : ${item.activeSince}</div>
	              	<div style="color:chartreuse">Total Items : ${item.totalQty}</div>
	              	<div style="color:chartreuse">Total Amount: ${item.totalAmount}</div>
	              	
	               
	              </div>
	              <div class="icon">
	                <i class="ion ion-bag"></i>
	              </div>
	              <a href="#" onclick="window.location='?a=showTableOrders&table_id=${item.table_id}'" class="small-box-footer">More info <i class="fas fa-arrow-circle-right"></i></a>
	            
	</c:if>
	         
	            
	              </div>
	</div>
</c:forEach>
   
</div>
</form>





<script>
	document.getElementById("divTitle").innerHTML=" Tables";	
	
	function saveConfig()
	{
		
		
		  document.getElementById("closebutton").style.display='none';
		   document.getElementById("loader").style.display='block';
		$('#myModal').modal({backdrop: 'static', keyboard: false});;

		var xhttp = new XMLHttpRequest();
		  xhttp.onreadystatechange = function() 
		  {
		    if (xhttp.readyState == 4 && xhttp.status == 200) 
		    { 		      
		      document.getElementById("responseText").innerHTML=xhttp.responseText;
			  document.getElementById("closebutton").style.display='block';
			  document.getElementById("loader").style.display='none';
			  $('#myModal').modal({backdrop: 'static', keyboard: false});;
		      
			  
			}
		  };
		  xhttp.open("GET","?a=saveTableConfig&noOfTables="+noOfTables.value, true);    
		  xhttp.send();
		
	}
</script>
