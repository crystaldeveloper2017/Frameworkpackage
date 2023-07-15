  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
           
<c:set var="message" value='${requestScope["outputObject"].get("ListOfItems")}' />
<c:set var="ListOfCategories" value='${requestScope["outputObject"].get("ListOfCategories")}' />
<c:set var="ListOfBrands" value='${requestScope["outputObject"].get("ListOfBrands")}' />
<c:set var="EstimateNo" value='${requestScope["outputObject"].get("EstimateNo")}' /> 
<c:set var="customerList" value='${requestScope["outputObject"].get("customerList")}' />

  
</head>
<br>

<div class="container" style="padding:20px;background-color:white;max-width:100%">

<form id="frm" method="post" enctype="multipart/form-data" accept-charset="UTF-8">


<div class="row">

	<datalist id="customerList">	
	   <c:forEach items="${customerList}" var="customer">
			    <option id="${customer.customerId}">${customer.customerName}~${customer.mobileNumber}~${customer.customerAddress}</option>			    
	   </c:forEach>	
	</datalist>






	<div class="col-sm-2" align="center">
	 <label for="email">Category Name</label>
		<div class="input-group input-group-sm" style="width: 200px;">
		  		
		
		<select id="drpcategoryId" name="drpcategoryId" class="form-control float-right"  style="margin-right: 15px;" >
		
		<option value='-1'>--Select--</option>
		
		<c:forEach items="${ListOfCategories}" var="item">
		<option value='${item.category_id}'> ${item.category_name}</option>
		</c:forEach>  							
		</select>
		</div>
	</div>



	<div class="col-sm-2" align="center">
	<label for="email">List Of Brand</label>
		<div class="input-group input-group-sm" style="width: 200px;">
		<select id="drpbrandId" name="drpbrandId" class="form-control float-right" onchange='' style="margin-right: 15px;" >
		
		<option value='-1'>--Select--</option>
		
		<c:forEach items="${ListOfBrands}" var="item">
		<option value='${item.brand_id}'> ${item.brand_name}</option>
		</c:forEach>  							
		</select>
		</div>
	</div>
	
	<div class="col-sm-2" align="center">
	<label for="email">Concept</label>
		<div class="input-group input-group-sm" style="width: 200px;">
		<input class="form-control form-control-sm" placeholder="Concept" id="txtconcept" type="text">
		</div>
	</div>
	
	<div class="col-sm-2" align="center">
	<label for="email">Item name / Product Code</label>
		<div class="input-group input-group-sm" style="width: 200px;">
		<input class="form-control form-control-sm" placeholder="Item name / Product Code" id="txtitemname" type="text">
		</div>
	</div>


<div class="col-sm-2" align="center">
	<label for="email">MIN Price / Max Price</label>

			<div class='input-group'>
				<input type='text'  class='form-control form-control-sm'
					name='txtgstamountgroup' id='fromrangeprice' value=''
					style='width: 50%'> <input type='text' style='width: 50%'
					class='form-control form-control-sm' id='torangeprice' value=''>
			</div>
</div>



	<div class="col-sm-2" align="center">
	<label for="search"> <br></label>
	
		<div class="input-group input-group-sm" style="width: 200px;">
		
		<input type="button" class="btn btn-block btn-primary btn-sm" onclick="reloadFilters()" value="Search">
			
		</div>
	</div>
	
	<div class="col-sm-6">
    <div class="form-group">
      <label for="email">customer Name</label>
      <div class="form-group">
  	
  	
  	
  	
  	<div class="input-group input-group-sm">
                  <input type="text" class="form-control form-control-sm" id="txtsearchcustomer"    placeholder="Search For Customer" name="txtsearchtxtsearchcustomer"  list='customerList' oninput="checkforMatchCustomer()">
                  
                  <span class="input-group-append">
                    <button type="button" class="btn btn-danger btn-flat" onclick="resetCustomer()">Reset</button>
                  </span>
                  
                  <span class="input-group-append">
                    <button type="button" class="btn btn-primary btn-flat" onclick="addCustomer()">Add</button>
                  </span>
    </div>
  	
  	
        	      
      
            
      <input  type="hidden" name="hdnSelectedCustomer" id="hdnSelectedCustomer" value="">
   			<input  type="hidden" name="hdnSelectedCustomerType" id="hdnSelectedCustomerType" value="">
   			<input  type="hidden" name="hdnPreviousInvoiceId" id="hdnPreviousInvoiceId" value="">
   			      
    </div>
	 </div>
  </div>
  
   <div class="col-sm-2" align="center">
   <label>Remarks</label>
		<div class="input-group input-group-sm" style="width: 200px;">
		<input class="form-control form-control-sm" placeholder="Remarks" id=txtremarks type="text">
		</div>
	</div>

  
  <br>
  <br>
  
  
  
  <div class="col-sm-6">
                      <!-- Select multiple-->
                      <div class="form-group">
                        <label>Available Items
                        </label>
                     <div class="card-body table-responsive p-0" style="height: 300px;">
			<table id="tblitems"
				class="table table-head-fixed  table-bordered table-striped dataTable dtr-inline"
				role="grid" aria-describedby="example1_info">
				
				
			

			</table>
		</div>
                      </div>
                    </div>
                    
         <div class="col-sm-6">
                      <!-- Select multiple-->
                      <div class="form-group">
                        <label>Selected Items
                        </label>
                     <div class="card-body table-responsive p-0" style="height: 300px;">
			<table id="tblselecteditems"
				class="table table-head-fixed  table-bordered table-striped dataTable dtr-inline"
				role="grid" aria-describedby="example1_info">
				
					
				<thead>
	                    <tr align="center">
	  			<th style="z-index:0">Item ID</th>
	  			<th style="z-index:0">Item Name</th>
	  			<th style="z-index:0">Product Code</th>
	  			<th style="z-index:0">Color</th>	  			
	  			<th style="z-index:0">Size</th>
	  			<th style="z-index:0">Price</th>
	  			<th style="z-index:0">AOP</th>
	  			<th></th>
	                    </tr>
						
	                  </thead>
			

			</table>
		</div>
                      </div>
                    </div>
       
       
  
  
  
  
  <div class="col-sm-12" align="center">
  	<div class="form-group">
  	 
		<button class="btn btn-success" type="button"  id="generatePDF" onclick='saveEstimate();'>Save</button>   
	   <button class="btn btn-danger" type="reset" onclick='window.location="?a=showHomePage"'>Cancel</button>
   </div>
  </div>
  
  
   
</div>
</div>
 <br>
  <br>


<datalist id="userList">
<c:forEach items="${userList}" var="user">
			    <option id="${user.user_id}">${user.username}~${user.name}</option>			    
	   </c:forEach></select>	   	   	
</datalist>
</form>

<script>

$(function() {

	$('#example1').DataTable({
		"paging" : true,
		"lengthChange" : false,
		"searching" : false,
		"ordering" : true,
		"info" : true,
		"autoWidth" : false,
		"responsive" : true,
		"pageLength" : 50,
		"order" : [ [ 1, "asc" ] ]
	});
});

document.getElementById("divTitle").innerHTML = "Generate Estimate :-  ${EstimateNo}";


function saveEstimate()
{
	
	
	if(hdnSelectedCustomer.value=="")
		{
			alert('Please select Customer');
			return;
		}
	
	
	var rows=tblselecteditems.rows;
	
	var requiredDetails=[];
	 
	var arr = [];
	var itemString="";
	var confirmMessage="";
	var proceedFlag=true;
	var messageToShow="";
	for (var x= 1; x < rows.length; x++) 
	{   
	  
	    itemString+=
	    rows[x].childNodes[0].innerHTML+"~"+ // itemId
	    rows[x].childNodes[1].innerHTML+"~"+ // itemName
	    rows[x].childNodes[5].innerHTML+"~"+ // mrp
	    rows[x].childNodes[6].innerHTML+"~"+ // offerPrice   
	    
	  
	    
	    "|";      
	}
	
	
	
	
	var reqString="clientId="+document.getElementById("hdnSelectedCustomer").value+	
	"&remarks="+document.getElementById("txtremarks").value+"&itemString="+itemString;
	
	
	
	var xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() {
	    if (this.readyState == 4 && this.status == 200) 
	    {
	    	
	      	
	      	toastr["success"]("Estimate Generated Succesfully "+this.responseText);
	    	toastr.options = {"closeButton": false,"debug": false,"newestOnTop": false,"progressBar": false,
	    	  "positionClass": "toast-top-right","preventDuplicates": false,"onclick": null,"showDuration": "1000",
	    	  "hideDuration": "500","timeOut": "500","extendedTimeOut": "500","showEasing": "swing","hideEasing": "linear",
	    	  "showMethod": "fadeIn","hideMethod": "fadeOut"
	    	}
	    	
	    	
	    		window.open("BufferedImagesFolder/"+this.responseText+".pdf");
	    		window.location="?a=showGenerateEstimate";
		    	
	    		

	    	 
	      
	    }
	  };
	  xhttp.open("POST", "${masterUrl}?a=saveEstimate", true);
	  xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");	  
	  xhttp.send(encodeURI(reqString));
	
	
	
}
	
function searchprod(evnt) {
	if (evnt.which == 13) {
		// do some search stuff
		actualSearch();
	}

}

function removethisitem(btn1)
{
	btn1.parentElement.parentElement.remove();
}


function reloadFilters() {
	// need to make ajax call to get all items using this filter
	// will send 5 filters input to this
	// categoryid,brandid,concept,itemname,range

	document.getElementById("closebutton").style.display = 'none';
	document.getElementById("loader").style.display = 'block';
	//$('#myModal').modal({backdrop: 'static', keyboard: false});; 

	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4 && xhttp.status == 200) {

			var myItems = JSON.parse(xhttp.responseText);

			$('#myModal').modal('hide');

			$("#tblitems tr").remove(); // this removes all table rows and add header again and then adds rows from search results..

			var table = document.getElementById("tblitems");
			var row = table.insertRow(-1);
			var cell1 = row.insertCell(0);
			var cell2 = row.insertCell(1);
			var cell3 = row.insertCell(2);
			var cell4 = row.insertCell(3);
			var cell5 = row.insertCell(4);
			var cell6 = row.insertCell(5);
			var cell7 = row.insertCell(6);

			cell1.innerHTML = "<b>ITEM ID</b>";
			cell2.innerHTML = "<b>ITEM Name</b>";
			cell3.innerHTML = "<b>PRODUCT CODE</b>";
			cell4.innerHTML = "<b>COLOR</b>";
			cell5.innerHTML = "<b>SIZE</b>";
			cell6.innerHTML = "<b>PRICE</b>";
			cell7.innerHTML = "<b>AOP</b>";

			for (var m = 0; m < myItems.length; m++) {

				var table = document.getElementById("tblitems");
				var row = table.insertRow(-1);
				
				
				
				row.onclick = (function (e) {
					
					
					//we need to check if item already exist in selection
					// for that we need to loop through all the selected items and compare with current item
					
					 var rowid = (this.cells[0].innerHTML);
			        var itemid=this.cells[0].innerHTML;
			        var itemname=this.cells[1].innerHTML;
			        var productcode=this.cells[2].innerHTML;
			        var color=this.cells[3].innerHTML;
			        var size=this.cells[4].innerHTML;
			        var price=this.cells[5].innerHTML;
			        var aop=this.cells[6].innerHTML;
			        
					var selectionTable= document.getElementById("tblselecteditems");
					var rows1=selectionTable.rows;
					var proceedFlag=true;
					for(var m=1;m<rows1.length;m++)
						{
							if(itemid==rows1[m].cells[0].innerHTML)
								{
									alert('item already exist in selection');
									proceedFlag=false;
									break;
								}
						}
					
					if(proceedFlag==false)
						{
							return;
						}
					
					
			        
			        
			        
			        
			       
			        
			        
			        // we need to create new row in the new table
			        
			        var table = document.getElementById("tblselecteditems");
					var row = table.insertRow(-1);
					var cell1 = row.insertCell(0);
					var cell2 = row.insertCell(1);
					var cell3 = row.insertCell(2);
					var cell4 = row.insertCell(3);
					var cell5 = row.insertCell(4);
					var cell6 = row.insertCell(5);
					var cell7 = row.insertCell(6);
					var cell8 = row.insertCell(7);
					

					cell1.innerHTML = itemid;
					cell2.innerHTML = itemname;
					cell3.innerHTML = productcode;
					cell4.innerHTML = color;
					cell5.innerHTML = size;
					cell6.innerHTML = price;
					cell7.innerHTML = aop;
					cell8.innerHTML = '<button type="button" class="btn btn-sm btn-danger"  onclick=removethisitem(this) id="btn11" style="cursor:pointer">Delete</button>';
					
					
			        
			        
			        
			        
			        
			    });
				
				var cell1 = row.insertCell(0);
				var cell2 = row.insertCell(1);
				var cell3 = row.insertCell(2);
				var cell4 = row.insertCell(3);
				var cell5 = row.insertCell(4);
				var cell6 = row.insertCell(5);
				var cell7 = row.insertCell(6);
				

				cell1.innerHTML = myItems[m].item_id;
				cell2.innerHTML = myItems[m].item_name;
				cell3.innerHTML = myItems[m].product_code;
				cell4.innerHTML = myItems[m].color;
				cell5.innerHTML = myItems[m].size;
				cell6.innerHTML = myItems[m].price;
				cell7.innerHTML = myItems[m].aop;

			}

		}
	};
	xhttp.open("GET", "?a=getItemsWithFilters&categoryId="
			+ drpcategoryId.value + "&brandId=" + drpbrandId.value
			+ "&concept=" + txtconcept.value + "&itemSearch="
			+ txtitemname.value+"&fromrangeprice="+fromrangeprice.value+"&torangeprice="+torangeprice.value, true);
	xhttp.send();

}

drpcategoryId.value = '${param.categoryId}';



function searchForCustomer(searchString)
{	
	//if(searchString.length<3){return;}

	document.getElementById("closebutton").style.display='none';
	   document.getElementById("loader").style.display='block';
	var xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() 
	  {
	    if (xhttp.readyState == 4 && xhttp.status == 200) 
	    { 		      
	    	var cusomerList=JSON.parse(xhttp.responseText);
	    	var reqString="";
	    	for(var x=0;x<cusomerList.length;x++)
	    	{
	    		//console.log(cusomerList[x]);
	    		reqString+="<option id="+cusomerList[x].customer_id+">"+cusomerList[x].customer_name+"~"+cusomerList[x].mobile_number+"~"+cusomerList[x].customer_type+"</option>";
	    	}
	    	
	    	document.getElementById('customerList').innerHTML=reqString;
		}
	  };
	  xhttp.open("GET","?a=searchForCustomer&searchString="+searchString, true);    
	  xhttp.send();
	
	 
	
}




function resetCustomer()
{
	window.location.reload();
	txtsearchcustomer.disabled=false;
	txtsearchcustomer.value="";
	hdnSelectedCustomer.value=0;	
}

function addCustomer()
{
	window.open("?a=showAddCustomer&mobileNo="+txtsearchcustomer.value);	
}


function checkforMatchCustomer()
{
	
	var searchString= document.getElementById("txtsearchcustomer").value;	
	var options1=document.getElementById("customerList").options;
	var customerId=0;
	for(var x=0;x<options1.length;x++)
		{
		
			if(searchString==options1[x].value)
				{
					customerId=options1[x].id;					
					break;
				}
		}
	if(customerId!=0)
		{
			document.getElementById("hdnSelectedCustomer").value=customerId;			
			document.getElementById("txtsearchcustomer").disabled=true;
			
			
						
		}
	else
		{
			//searchForCustomer(searchString);
		}
	
	
	
}

</script>



