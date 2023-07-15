  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
           
           
           


<c:set var="itemList" value='${requestScope["outputObject"].get("itemList")}' />
<c:set var="orderDetails" value='${requestScope["outputObject"].get("orderDetails")}' />

   





</head>




<script>







</script>


<br>

<div class="container" style="padding:20px;background-color:white">
 <div class="col-sm-12">  
	  <div class="card-body table-responsive p-0" style="height: 370px;">                
	                <table id="tblordereditems"  class="table table-head-fixed  table-bordered table-striped dataTable dtr-inline" role="grid" aria-describedby="example1_info">
	                  <thead>
	                    <tr align="center">
	                     <th style="z-index:0">Sr</th>
	  					 <th style="z-index:0">Item Name</th>
	  					 <th style="z-index:0">Item Qty</th>
	  					 <th style="z-index:0">Status</th>
	  					 <th style="z-index:0">Remarks</th>	  			
	  					 <th></th>
	                    </tr>
	                  </thead>
	                  <c:forEach items="${orderDetails}" var="order">
			    		<tr align="center">
			    		
			    			<td>${order.SrNo}
			    			<input type="hidden" value='${order.order_id}' id="hdnOrderId"</input>
			    			</td>
	                  		<td>${order.item_name}</td>
	                  		<td>${order.qty}</td>
	                  		<td>${order.status}</td>
	                  		<td>${order.remarks}</td>
	                  		
	                  		<td>
	                  		<c:if test="${order.status eq 'O'}">
	                  			<button class="btn btn-danger" onclick="cancelOrderDetail(${order.order_details_id})">Cancel</button>
	                  			<button class="btn btn-primary" onclick="markAsServed(${order.order_details_id})">Mark As Served</button>
	                  		</c:if>
	                  		</td>
	                  		
	                  	</tr>			    
	   				  </c:forEach>
	                  
	                </table>
	   </div>	
  </div>
  
  
  
  
<datalist id="itemList">
		  <c:forEach items="${itemList}" var="item">
				    <option id="${item.item_id}">${item.item_name} (${item.product_code})</option>			    
		   </c:forEach>	   	   	
</datalist>
  
  <br>
  <div class="col-sm-12">   
    <div class="input-group">
    <input type="text" class="form-control form-control-sm"    placeholder="Search for Items" list="itemList" id="txtitem" name="txtitem" oninput="checkforMatchItem()">
    <div class="input-group-append">
      <button class="btn btn-secondary btn-sm" type="button" onclick="showItems()">
        <i class="fa fa-search fa-sm" ></i>
      </button>
    </div>
  </div>
  </div>
  
  
  <div class="col-sm-12">  
	  <div class="card-body table-responsive p-0" style="height: 370px;">                
	                <table id="tblitems"  class="table table-head-fixed  table-bordered table-striped dataTable dtr-inline" role="grid" aria-describedby="example1_info">
	                  <thead>
	                    <tr align="center">
	                     <th style="z-index:0">Sr</th>
	  					 <th style="z-index:0">Item Name</th>
	  					 <th style="z-index:0">Item Qty</th>
	  					 <th style="z-index:0">Remarks</th>	  					 	  			
	  					 <th></th>
	                    </tr>
	                  </thead>
	                  
			    					    
	   				  
	                  
	                </table>
	   </div>	
  </div>
  
 <div class="col-sm-12">
  	 <div class="form-group" align="center">	  
	   	<button class="btn btn-success" type="button" onclick='saveOrder()'>Save</button>   
	   	<button class="btn btn-danger" type="reset" onclick='window.location="?a=showHomePage"'>Cancel</button>
	   	<button class="btn btn-primary" type="reset" onclick='window.location="?a=showGenerateInvoice&editInvoice=Y&table_id=${param.table_id}"'>Generate Invoice</button>	   
     </div>
   </div>


</div>



<script>
	document.getElementById("divTitle").innerHTML="Add Order";	
	
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
	
	function saveOrder()
	{
		var rows=tblitems.rows;		
		var requiredDetails=[];
		var arr = [];
		var itemString="";
		var confirmMessage="";
		var proceedFlag=true;
		var messageToShow="";
		
		for (var x= 1; x < rows.length; x++) 
		{   
		    // ID,QTY,REMARKS
		    itemString+=
		    	rows[x].childNodes[1].childNodes[0].value+
		   	"~"+rows[x].childNodes[2].childNodes[1].value+
		   	"~"+rows[x].childNodes[3].childNodes[1].value+		    
		    "|";
		 }	
		var reqString="";
		if(typeof hdnOrderId!='undefined')
		{
			reqString="table_id=${param.table_id}"+"&order_id="+hdnOrderId.value+"&itemDetails="+itemString;
		}
		else
		{
			reqString="table_id=${param.table_id}"+"&order_id=&itemDetails="+itemString;
		}
		//alert(reqString);
		//return;
	
		
		
		var xhttp = new XMLHttpRequest();
		  xhttp.onreadystatechange = function() {
		    if (this.readyState == 4 && this.status == 200) 
		    {
		    	alert(this.responseText);
		    	window.location.reload();
		    }
		  };
		  xhttp.open("POST", "?a=saveOrder", true);
		  xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		  xhttp.send(reqString);
		
	}
	
	function showItems()
	{
		var reqString='<div class="container"><div class="row">';
		
		<c:forEach items="${itemList}" var="item">
			reqString+='<div class="col-sm">'
				reqString+='<img style="padding:5px" onclick="showThisItemIntoSelection(${item.item_id})" height="100px" width="100px" src="BufferedImagesFolder/${item.ImagePath}">';
				reqString+="<div>${item.item_name}</div>";
			reqString+='</div>';
			 
		</c:forEach>
		reqString+='</div></div>';
		
		
		
		 
		document.getElementById("responseText").innerHTML=reqString;
		  document.getElementById("closebutton").style.display='block';
		  document.getElementById("loader").style.display='none';
		  $("#myModal").modal();
		
	}

	function showThisItemIntoSelection(itemId)
	{
		
		getItemDetailsAndAddToTable(itemId);
		document.getElementById("responseText").innerHTML="";
		$("#myModal").modal('hide');
	}
	function checkforMatchItem()
	{
		var searchString= document.getElementById("txtitem").value;	
		var options1=document.getElementById("itemList").options;
		var itemName="";
		var itemId=0;
		for(var x=0;x<options1.length;x++)
			{
				if(searchString==options1[x].value)
					{
					itemId=options1[x].id;
					itemName=options1[x].value;		
						break;
					}
			}
		if(itemId!=0)
			{	
					
					
					var table = document.getElementById("tblitems");	    	
			    	var row = table.insertRow(-1);	    	
			    	var cell1 = row.insertCell(0);
			    	var cell2 = row.insertCell(1);
			    	var cell3 = row.insertCell(2);
			    	var cell4 = row.insertCell(3);
			    	var cell5 = row.insertCell(4);
			    	
			    	
			    	
			    	
			    	cell1.innerHTML = tblitems.rows.length-1;			    	
			    	cell2.innerHTML = "<input type='hidden' value="+itemId+">"+itemName;
			    	cell3.innerHTML = " <input type='text' class='form-control form-control-sm' onkeypress='digitsOnlyWithDot(event);' value='1'>";
			    	cell4.innerHTML = " <input type='text' class='form-control form-control-sm'  >  ";
			    	cell5.innerHTML = '<button type="button" class="btn btn-sm btn-danger"  onclick=removethisitem(this) id="btn11" style="cursor:pointer">Delete</button>';
			    	
			    	
			    	
					
					
					document.getElementById("txtitem").value="";
			}
		
	}
	
	function removethisitem(btn1)
	{
		btn1.parentElement.parentElement.remove();	
		reshuffleSrNos();		
		
	}
	
	function reshuffleSrNos()
	{
		var rows1=tblitems.rows;
		for(var x=1;x<rows1.length;x++)
			{
				rows1[x].childNodes[0].innerHTML=x;
			}
	}
	
	function cancelOrderDetail(cancelOrderDetailId)
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
		  xhttp.open("GET","?a=cancelOrderDetail&cancelOrderDetailId="+cancelOrderDetailId, true);    
		  xhttp.send();
	}
	
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
			  //document.getElementById("closebutton").style.display='block';
			  document.getElementById("loader").style.display='none';
			  $('#myModal').modal({backdrop: 'static', keyboard: false});;
			  
			  
			  setTimeout(function(){ window.location.reload();}, 300);

			  
		      
			  
			}
		  };
		  xhttp.open("GET","?a=markAsServed&orderDetailsId="+orderDetailsId, true);    
		  xhttp.send();
	}

</script>

