  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
           
           
           


<c:set var="itemList" value='${requestScope["outputObject"].get("itemList")}' />
   





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
	    	
	    	var table = document.getElementById("tblitems");	    	
	    	var row = table.insertRow(-1);
	    	var cell0 = row.insertCell(0);
	    	var cell1 = row.insertCell(1);
	    	var cell2 = row.insertCell(2);
	    	var cell3 = row.insertCell(3);
	    	var cell4 = row.insertCell(4);
	    	var cell5 = row.insertCell(5);
	    	
	    	var arritemName=itemName.split('~');
	    	
	    	
	    	cell0.innerHTML = itemId;
	    	cell1.innerHTML = arritemName[1];
	    	cell2.innerHTML = arritemName[0];
	    	cell3.innerHTML = " <input type='text' class='form-control input-sm' id='txtqty' onkeyup='calculateAmount(this);checkIfEnterisPressed(event)' onkeypress='digitsOnlyWithDot(event)' value='1'>";   	
	    	cell4.innerHTML = '<input class="form-control" type="checkbox" style="height:20px">';	    	
	    	
	    	cell5.innerHTML = '<button type="button" class="btn btn-danger"  onclick=removethisitem(this) id="btn11" style="cursor:pointer">Delete</button>';
	    	
	    	
	    
		
	  
			
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
	    // ID, Product Code, Item Name,No Of Labels,isPrintPrice
	    itemString+=
	    	rows[x].childNodes[0].innerHTML+
	    "~"+rows[x].childNodes[1].innerHTML+
	    "~"+rows[x].childNodes[2].innerHTML+
	    "~"+rows[x].childNodes[3].childNodes[1].value+
	    "~"+rows[x].childNodes[4].childNodes[0].checked+
	    "|";
	}
	
	 var xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() {
	    if (this.readyState == 4 && this.status == 200) 
	    {
	    	window.open("BufferedImagesFolder/"+xhttp.responseText);
	        
	      
	    }
	  };
	  xhttp.open("POST", "?a=printLabels", true);
	  xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	  xhttp.send("itemDetails="+itemString);
	
	
	
}



</script>



<br>

<div class="container" style="padding:20px;background-color:white">

<form id="frm" action="?a=addCategory" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
<div class="row">


<div class="col-sm-12">
  	<div class="form-group">      
  		<input type="text" class="form-control"    placeholder="Search for Items" list="itemList" id="txtitem" name="txtitem" oninput="checkforMatchItem()">          
    </div>
  </div>
  
  <datalist id="itemList">
<c:forEach items="${itemList}" var="item">
			    <option id="${item.item_id}">${item.item_name}~${item.product_code}</option>			    
	   </c:forEach></select>	   	   	
</datalist>


<div class="col-sm-12">  
	  <div class="card-body table-responsive p-0" style="height: 370px;">                
	                <table id="tblitems"  class="table table-head-fixed  table-bordered table-striped dataTable dtr-inline" role="grid" aria-describedby="example1_info">
	                  <thead>
	                    <tr align="center">
	                     <th style="z-index:0">Item Id</th>
	                     <th style="z-index:0">Product Code</th>
	  			<th style="z-index:0">Item Name</th>
	  			<th style="z-index:0">No Of Labels</th>
	  			<th style="z-index:0">Print Price</th>	  				  			
	  			<th></th>
	                    </tr>
	                  </thead>
	                </table>
	   </div>	
  </div>
  
  
  
  <div class="col-sm-12" align="center">
  	<div class="form-group">      
  		<button class="btn btn-success" type="button" onclick='printLabels()'>Print</button>
		<button class="btn btn-danger" type="reset" onclick='window.location="?a=showCategoryMasterNew"'>Cancel</button>          
    </div>
  </div>
  
  
		
	 
</div>
</form>

<script>
	
	

		document.getElementById("divTitle").innerHTML="Print Labels";

</script>



