  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
           
           
           


<c:set var="itemList" value='${requestScope["outputObject"].get("itemList")}' />
<c:set var="addStockList" value='${requestScope["outputObject"].get("addStockList")}' />
<c:set var="todaysDate" value='${requestScope["outputObject"].get("todaysDate")}' />
<c:set var="listfirmData" value='${requestScope["outputObject"].get("listfirmData")}' />
<c:set var="listwarehouse" value='${requestScope["outputObject"].get("listwarehouse")}' />
<c:set var="stockModificationDetails" value='${requestScope["outputObject"].get("stockModificationDetails")}' />






</head>


<script>



function removethisitem(btn1)
{
	btn1.parentElement.parentElement.remove();	 
}

function checkforMatchItem()
{
	var searchString= document.getElementById("txtitem").value;	
	var options1=document.getElementById("itemList").options;
	
	
	var itemId=0;
	for(var x=0;x<options1.length;x++)
		{
			if(searchString==options1[x].value)
				{
				itemId=options1[x].id;
				
					break;
				}
		}
	if(itemId!=0)
		{			
				
				
				var total=0;
				var rows=tblitems.rows;
				for(var x=1;x<rows.length;x++)
					{							
						if(itemId==rows[x].childNodes[0].innerHTML)
							{
								alert('item already exist in selection');
								document.getElementById("txtitem").value="";
								return;
							}
					}
				
				// code to check if item already exist inselection				
			getItemDetailsAndAddToTable(itemId,searchString);
				document.getElementById("txtitem").value="";
		}
	
}




function getItemDetailsAndAddToTable(itemId,itemName)
{
	
	document.getElementById("closebutton").style.display='none';
	document.getElementById("loader").style.display='block';
	var xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() 
	  {
	    if (xhttp.readyState == 4 && xhttp.status == 200) 
	    { 	
		    	
	    	//alert(xhttp.responseText);
	    	var itemDetails=JSON.parse(xhttp.responseText);
	    	console.log(itemDetails);
	    	var table = document.getElementById("tblitems");	    	
	    	var row = table.insertRow(-1);
	    	var cell0 = row.insertCell(0);
	    	var cell1 = row.insertCell(1);
	    	var cell2 = row.insertCell(2);
	    	var cell3 = row.insertCell(3);
	    	var cell4 = row.insertCell(4);
	    	var cell5 = row.insertCell(5);
	    	var cell6 = row.insertCell(6);
	    	var cell7 = row.insertCell(7);
	    	var cell8 = row.insertCell(8);
	    	
	    	
	    	
	    	
	    	var arritemName=itemName.split('~');    	
	    	cell0.innerHTML = itemId;	    	
	    	cell1.innerHTML = arritemName[0];
	    	cell2.innerHTML = " <input type='text' class='form-control input-sm'  readonly  onkeypress='digitsOnlyWithDot(event)' value="+itemDetails.stockAvailable+">";   	
	    	cell3.innerHTML = '<input  type="text" class="form-control"  readonly    > ';
	    	cell4.innerHTML = '<input  type="text" class="form-control" value="0" onkeypress="digitsOnlyWithDot(event)" onkeyup="calculateQuantities(this)"> ';
	    	cell5.innerHTML = '<input  type="text" class="form-control" value='+itemDetails.destinationStockAvailable+' readonly  >';
	    	cell6.innerHTML = '<input  type="text" class="form-control" readonly  >';
	    	cell7.innerHTML = '<input  type="text" class="form-control" >';
	    	cell8.innerHTML = '<button type="button" class="btn btn-danger"  onclick=removethisitem(this) id="btn11" style="cursor:pointer">Delete</button>';
	    	
		}
	  };
	  xhttp.open("GET","?a=getItemDetailsByAjax&itemId="+itemId+"&destinationfirmId="+drptofirm.value+"&sourcefirmId="+drpfromfirm.value+"&sourceWareHouseId="+drpfromwarehouse.value+"&destinationWareHouseId="+towarehouse.value, true);    
	  xhttp.send();	
	    
		
	  
			
}


function printLabels()
{
	
	var rows=tblitems.rows;
	
	var requiredDetails=[];
	 
	
	var arr = [];
	var itemString="";
	var confirmMessage="";
	var proceedFlag=true;
	for (var x= 1; x < rows.length; x++) 
	{   
	    // ID, differenceQty
	    var id=rows[x].childNodes[0].innerHTML;
	    var sourceBefore=rows[x].childNodes[2].childNodes[1].value;
	    var sourceAfter=rows[x].childNodes[3].childNodes[0].value;
	    var qty=rows[x].childNodes[4].childNodes[0].value;
	    var destinationBefore=rows[x].childNodes[5].childNodes[0].value;
	    var destinationAfter=rows[x].childNodes[6].childNodes[0].value;
	    var destinationPrice=rows[x].childNodes[7].childNodes[0].value;
	    
	    itemString+=id+"~"+
	    sourceBefore+"~"+
	    sourceAfter+"~"+
	    qty+"~"+
	    destinationBefore+"~"+
	    destinationAfter+"~"+
	    destinationPrice+"|";
	}
	
	 var xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() {
	    if (this.readyState == 4 && this.status == 200) 
	    {
	    	
	    	var arr=this.responseText.split("~");
		    alert(arr[0]);
	    	if(typeof chkgeneratePDF !='undefined' && chkgeneratePDF.checked==true)
    		{
	      		
	    	  	generatePdfPurchaseInvoice(arr[1]);
    			
    			
    		}
	    	
	    	
	    	window.location="?a=showStockStatus&firmId="+drptofirm.value+"&warehouseid="+towarehouse.value;	    	
	    }
	  };
	  xhttp.open("POST", "?a=transferStock", true);
	  xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	  xhttp.send("itemDetails="+itemString+"&drpfromfirm="+drpfromfirm.value+"&ware_house_id="+drpfromwarehouse.value+"&to_ware_house_id="+towarehouse.value+"&drptofirm="+drptofirm.value+"&transactionDate="+txtinvoicedate.value+"&outerremarks="+outerremarks.value); 
	
	
	
}



</script>



<br>

<div class="container" style="padding:20px;background-color:white">

<form id="frm" action="?a=addCategory" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
<div class="row">


<div class="col-sm-3">
  	<div class="form-group">
      <label for="email">From firm</label>
      
      <select class='form-control' id="drpfromfirm" name = "drpfromfirm" class="form-control" onchange="checkForSamefirm()">
				<option value="-1">---------------Select ----------</option>
				<c:forEach items="${listfirmData}" var="item">
				    <option value="${item.firmId}">${item.firmName}</option>			    
		   		</c:forEach>
	  		
			
			</select>
            
    </div>
  </div>
  
  <div class="col-sm-3">
  	<div class="form-group">
      <label for="email">From Ware House</label>
      
      <select class='form-control' id="drpfromwarehouse" name = "drpfromwarehouse" class="form-control" onchange="checkForSamefirm()">
				<option value="-1">---------------Select ----------</option>
				<c:forEach items="${listwarehouse}" var="warehouse">
				    <option value="${warehouse.ware_house_id}">${warehouse.ware_house_name}</option>			    
		   		</c:forEach>
	  		
			
			</select>
            
    </div>
  </div>
  
  <input id="hdnitemid" name="hdnitemid" value="" type="hidden"> 
  
  <div class="col-sm-3">
  	<div class="form-group">
      <label for="email">To firm</label>      
      <select class='form-control' id="drptofirm" name = "drptofirm" class="form-control" onchange="checkForSamefirm()">
				<option value="-1">---------------Select ----------</option>
				<c:forEach items="${listfirmData}" var="item">
				    <option value="${item.firmId}">${item.firmName}</option>			    
		   		</c:forEach>
	  		
			
			</select>
            
    </div>
  </div>
  <div class="col-sm-3">
  	<div class="form-group">
      <label for="email">To Ware House</label>
      
      <select class='form-control' id="towarehouse" name = "towarehouse" class="form-control" onchange="checkForSamefirm()" >
				<option value="-1">---------------Select ----------</option>
				<c:forEach items="${listwarehouse}" var="warehouse">
				    <option value="${warehouse.ware_house_id}">${warehouse.ware_house_name}</option>			    
		   		</c:forEach>
	  		
			
			</select>
            
    </div>
  </div>


<div class="col-sm-6">
  	<div class="form-group">      
  		<input type="text" class="form-control"    placeholder="Search for Items" list="itemList" id="txtitem" name="txtitem" oninput="checkforMatchItem()">          
    </div>
  </div>
  
  <div class="col-sm-6">
  	<div class="form-group">      
  		<input type="text" id="txtinvoicedate" name="txtinvoicedate" class="form-control form-control-sm" value="${todaysDate}" placeholder="Invoice Date" readonly/>          
    </div>
  </div>
  
  <datalist id="itemList">
<c:forEach items="${itemList}" var="item">
			    <option id="${item.item_id}">${item.item_name}~${item.product_code}~${item.size}~${item.color}</option>			    
	   </c:forEach></select>	   	   	
</datalist>


<div class="col-sm-12">  
	  <div class="card-body table-responsive p-0" style="height: 370px;">                
	                <table id="tblitems"  class="table table-head-fixed  table-bordered table-striped dataTable dtr-inline" role="grid" aria-describedby="example1_info">
	                  <thead>
	                    <tr align="center">
				            <th style="z-index:0">Item Id</th>               
				  			<th style="z-index:0">Item Name</th>
				  			<th style="z-index:0">Source Stock Before</th>
				  			<th style="z-index:0">Source Stock After</th>
				  			<th style="z-index:0">${param.type} Qty</th>	  			  				  				  			  				  				  			
				  			<th style="z-index:0">Destination Stock Before</th>
				  			<th style="z-index:0">Destination Stock After</th>
				  			<th style="z-index:0">Destination Price</th>	  			
				  			<th></th>
	                    </tr>
	                  </thead>
	                </table>
	   </div>	
  </div>
  
  
  
  <div class="col-sm-12" align="center">
  	<div class="form-group">  	      
  		<input type="text" id="outerremarks" name="outerremarks" class="form-control form-control-sm"  placeholder="Remarks" />
    </div>
  </div>
  
  
  
 <div class="col-sm-12">
  	 <div class="form-group" align="center">	  
	   <input type="checkbox" class="form-check-input" id="chkgeneratePDF">
    	<label class="form-check-label" for="chkgeneratePDF">Generate PDF</label>
   </div>
   </div>

  
  <div class="col-sm-12" align="center">
  	<div class="form-group">  	      
  		<button class="btn btn-success" type="button" onclick='printLabels()'>Save</button>		
		<button class="btn btn-danger" id="btncancel" type="reset" onclick='window.location="?a=showStockModifications"'>Cancel</button>		          
    </div>
  </div>
  
  
  
  
  
  
   
  
  
		
	 
</div>
</form>

<script>
	
	

		document.getElementById("divTitle").innerHTML="Stock Transfer";
		
		$( "#txtinvoicedate" ).datepicker({ dateFormat: 'dd/mm/yy' });
		
		
		function checkForSamefirm()
		{
			if(drpfromfirm.value==drptofirm.value &&  drpfromwarehouse.value==towarehouse.value)
				{
					alert('From Firm Warehouse  And To firm Ware house cannot be same');
					towarehouse.value=-1;
				}
		}
		
		function calculateQuantities(elementText)
		{
			var sourceStockBefore=elementText.parentNode.parentNode.childNodes[2].childNodes[1].value;
			var qty=elementText.parentNode.parentNode.childNodes[4].childNodes[0].value;			
			var destinationStockBefore=elementText.parentNode.parentNode.childNodes[5].childNodes[0].value;
			
			
			var sourceStockAfter=Number(sourceStockBefore)-Number(qty);
			var destinationStockAfter=Number(destinationStockBefore)+Number(qty);
				
			elementText.parentNode.parentNode.childNodes[3].childNodes[0].value=sourceStockAfter;
			
			elementText.parentNode.parentNode.childNodes[6].childNodes[0].value=destinationStockAfter;
			
		}
		
		
		
		if('${stockModificationDetails.get(0)}'!='')
		{
		var m=0;
		
		txtinvoicedate.value='${stockModificationDetails.get(0).transactionDateFormatted}';
		drpfromfirm.value='${stockModificationDetails.get(0).sourcefirm}';
		drptofirm.value='${stockModificationDetails.get(0).destinationfirm}';
		outerremarks.value='${stockModificationDetails.get(0).remarks}';
		<c:forEach items="${stockModificationDetails}" var="item">			
		m++;			
		var table = document.getElementById("tblitems");	    	
    	var row = table.insertRow(-1);	    	
    	var cell0 = row.insertCell(0);
    	var cell1 = row.insertCell(1);
    	var cell2 = row.insertCell(2);
    	var cell3 = row.insertCell(3);
    	var cell4 = row.insertCell(4);
    	var cell5 = row.insertCell(5);
    	var cell6 = row.insertCell(6);
    	var cell7 = row.insertCell(7);
    	
    	
    	
    	cell0.innerHTML = '${item.item_id}';    	
    	cell1.innerHTML = '${item.item_name}';	    	   	
    	cell2.innerHTML = '${item.sourcebefore}';
    	cell3.innerHTML = '${item.sourceafter}';
    	cell4.innerHTML = '${item.qty}';
    	cell5.innerHTML = '${item.destinationbefore}';
    	cell6.innerHTML = '${item.destinationafter}';
    	cell7.innerHTML = '${item.destinationPrice}';
    	
    	
    	
    	
		
	    		//alert('${item.item_id}'+'-${item.item_name}'+'-${item.qty}'+'-${item.rate}'+'-${item.custom_rate}');			    
		</c:forEach>
			
		
		
		
		
		
		$("#frm :input").prop('disabled', true);		
		$("[name=returnButtons]").prop('disabled', false);
		btncancel.disabled=false;
		
		}
		
		

		function generatePdfPurchaseInvoice(invoiceId)
		{
			var xhttp = new XMLHttpRequest();
			  xhttp.onreadystatechange = function() 
			  {
			    if (xhttp.readyState == 4 && xhttp.status == 200) 
			    { 		      
			    	window.open("BufferedImagesFolder/"+xhttp.responseText);		  
				}
			  };
			  xhttp.open("GET","?a=generatePdfStockTransfer&modificationId="+invoiceId, false);    
			  xhttp.send();
		}

		

</script>



