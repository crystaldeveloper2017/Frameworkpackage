  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
           
           
           

<c:set var="pendingItems" value='${requestScope["outputObject"].get("pendingItems")}' />



   





</head>


<script>







</script>


<br>

<div class="container" style="padding:20px;background-color:white">


<div class="row">

<div class="col-sm-12">  
	  <div class="card-body table-responsive p-0" >                
	                <table id="tblordereditems"  class="table table-head-fixed  table-bordered table-striped dataTable dtr-inline" role="grid" aria-describedby="example1_info">
	                  <thead>
	                    <tr align="center">
	                     <th style="z-index:0">Sr</th>
	                     <th style="z-index:0">Order No</th>
	                     <th style="z-index:0">Table No</th>
	  					 <th style="z-index:0">Item Name</th>
	  					 <th style="z-index:0">Item Qty</th>
	  					 <th style="z-index:0">Status</th>
	  					 <th style="z-index:0">Remarks</th>	  			
	  					 <th></th>
	                    </tr>
	                  </thead>
	                  <c:forEach items="${pendingItems}" var="pendingItems">
			    		<tr align="center">
			    		
			    			<td>${pendingItems.SrNo}</td>
			    			<td>${pendingItems.order_id}</td>
			    			<td>${pendingItems.table_no}</td>
	                  		<td>${pendingItems.item_name}</td>
	                  		<td>${pendingItems.qty}</td>
	                  		<td>${pendingItems.status}</td>
	                  		<td>${pendingItems.remarks}</td>
	                  		
	                  		<td><button class="btn btn-primary" onclick="markAsServed(${pendingItems.order_details_id})">Mark As Served</button></td>
	                  		
	                  	</tr>			    
	   				  </c:forEach>
	                  
	                </table>
	   </div>	
  </div>
   
</div>






<script>
	document.getElementById("divTitle").innerHTML="Pending Orders";	
	function markAsServed(orderDetailsId)
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
		  xhttp.open("GET","?a=markAsServed&orderDetailsId="+orderDetailsId, true);    
		  xhttp.send();
	}
	
	
</script>
