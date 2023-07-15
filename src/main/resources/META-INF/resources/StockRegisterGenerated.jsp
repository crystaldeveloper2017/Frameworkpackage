<style>
	.date_field {position: relative; z-index:1000;}
	.ui-datepicker{position: relative; z-index:1000!important;}
</style>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ListStock"
	value='${requestScope["outputObject"].get("ListStock")}' />
<c:set var="ListOfCategories"
	value='${requestScope["outputObject"].get("ListOfCategories")}' />
<c:set var="listOffirm"
	value='${requestScope["outputObject"].get("listOffirm")}' />
	
<c:set var="listofwarehouse"
	value='${requestScope["outputObject"].get("listofwarehouse")}' />
	
	

<c:set var="totalDetails"
	value='${requestScope["outputObject"].get("totalDetails")}' />
	
	
<c:set var="txtfromdate" value='${requestScope["outputObject"].get("txtfromdate")}' />
<c:set var="txttodate" value='${requestScope["outputObject"].get("txttodate")}' />




<script>
function deleteStock(stockId)
{
	
	var answer = window.confirm("Are you sure you want to delete ?");
	if (!answer) 
	{
		return;    
	}
	
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
	  xhttp.open("GET","?a=deleteStock&stockid="+stockId, true);    
	  xhttp.send();
}

function configureLowStock(stockId)
{
		window.location='?a=showConfigureLowStock&stockId='+stockId;
}

</script>







<br>
<div class="card">

	<br>

	<div class="row">
	
	
	
	
	<div class="col-sm-2" align="center">
					<div class="input-group input-group-sm" style="width: 200px;">
  						<input type="text" id="txtfromdate" onchange="checkforvalidfromtodate();ReloadFilters();"  name="txtfromdate" readonly class="form-control date_field" placeholder="From Date"/>
                  </div>
				</div>
				
				<div class="col-sm-2" align="center">
					<div class="input-group input-group-sm" style="width: 200px;">
					<input type="text" id="txttodate"  onchange="checkforvalidfromtodate();ReloadFilters();"    name="txttodate" readonly class="form-control date_field"  placeholder="To Date"/>
  						
                    </div>
				</div>
	
	
	
		<div class="col-sm-1" align="center">
			<div class="input-group input-group-sm" style="width: 200px;">
				<select id="drpfirmId" style="margin-left:3px;" name="drpfirmId" class="form-control float-right" onchange="ReloadFilters()" style="margin-right: 15px;" >
  						
  						<option value='-1'>--Select--</option>  						
  						<c:forEach items="${listOffirm}" var="firm">
							<option value='${firm.firmId}'> ${firm.firmName}</option>
						</c:forEach>  							
  					</select>
			</div>
		</div>
		
		
		<div class="col-sm-1" align="center">
			<div class="input-group input-group-sm" style="width: 200px;">
				<select id="drpwarehouseId" style="margin-left:3px;" name="drpwarehouseId" class="form-control float-right" onchange="ReloadFilters()" style="margin-right: 15px;" >
  						
  						<option value='-1'>--Select--</option>  						
  						<c:forEach items="${listofwarehouse}" var="warehouse">
							<option value='${warehouse.ware_house_id}'> ${warehouse.ware_house_name}</option>
						</c:forEach>  							
  					</select>
			</div>
		</div>
		
		
		
		
		<div class="col-sm-1" align="center">
			<div class="input-group input-group-sm" style="width: 200px;">
				<select id="drpcategoryId" name="drpcategoryId"
					class="form-control float-right" onchange="ReloadFilters()"
					style="margin-right: 15px;">

					<option value='-1'>--Select--</option>

					<c:forEach items="${ListOfCategories}" var="item">
						<option value='${item.category_id}'>
							${item.category_name}</option>
					</c:forEach>
				</select>
			</div>
		</div>

		<div class="col-sm-1" align="center">
			<div class="input-group input-group-sm" style="width: 200px;">
				<input class="form-control form-control-sm" placeholder="Search Item" id="txtsearch"
					type="text">
			</div>
		</div>

		<div class="col-sm-1" align="center">
			<input type="button" class="btn btn-primary btn-sm"
				style="margin-right: 11px;" onclick="ReloadFilters()" value="Search"
				class="form-control float-right">
		</div>




		<div class="col-sm-1" align="center">
			<div class="icon-bar" style="font-size: 22px; color: firebrick">
				<a title="Download Excel" onclick="downloadExcel()"><i
					class="fa fa-file-excel-o" aria-hidden="true"></i></a> <a
					title="Download PDF" onclick="exportPDFForLedger()"><i
					class="fa fa-file-pdf-o"></i></a> <a title="Download Text"
					onclick="downloadText()"><i class="fa fa-file-text-o"></i></a>
			</div>
		</div>

		



	</div>

	











<!-- /.card-header -->
<div class="card-body table-responsive p-0" style="height: 580px;">
	<table id="example1"
		class="table table-head-fixed  table-bordered table-striped dataTable dtr-inline"
		role="grid" aria-describedby="example1_info">
		<thead>
			<tr>
				<th><b>Invoice Date</b></th>
				<th><b>firm Name</b></th>
				<th>Ware House</th>
				<th>Item Name</th>
				<th>Product Code</th>
				<th>Client/Vendor</th>
				<th>Quantity</th>
				<th>Price</th>
				<th>Transaction Type</th>
				
				



			</tr>
		</thead>
		<tbody>
			<c:forEach items="${ListStock}" var="item">

				

				
					<tr style="color: green">
				

				<th><b>${item.invoicedate }</b></th>
				<td>${item.firm_name}</td>
				<td>${item.ware_house_name}</td>

				<td>
				 
		<c:if test="${item.typeOfTransaction eq 'Purchase'}">
			<a href="?a=showGeneratePI&invoiceId=${item.invoiceId}">${item.item_name}</a>
			
			
		</c:if>
		
		<c:if test="${item.typeOfTransaction eq 'Sales'}">
			<a href="?a=showGenerateSI&invoiceId=${item.invoiceId}">${item.item_name}</a>
		</c:if>
		
	</td>
	
					
				<td>${item.product_code}</td>
				<td>${item.customer_name}</td>
				<td>${item.qty}</td>
				<td>${item.rate}</td>
				<td>${item.typeOfTransaction}</td>
				
				
				
				</tr>
			</c:forEach>


		</tbody>
	</table>
</div>
<!-- /.card-body -->
</div>














<script>
  $(function () {
    
    $('#example1').DataTable({
      "paging": true,      
      "lengthChange": false,
      "searching": false,
      "ordering": true,
      "info": true,
      "autoWidth": false,
      "responsive": true,
      "pageLength": 50,
      "order": [[ 0, "desc" ]]
    });
  });
  
  document.getElementById("divTitle").innerHTML="Stock Register";
  
  function ReloadFilters()
  {
	  
	window.location = "?a=showStockRegister&categoryId="
				+ drpcategoryId.value + "&firmId=" + drpfirmId.value
				+ "&warehouseid=" + drpwarehouseId.value + "&searchString="
				+ txtsearch.value
				+ "&txtfromdate=" + txtfromdate.value 
				+ "&txttodate=" + txttodate.value ;

	}

	drpcategoryId.value = "${param.categoryId}";
	drpfirmId.value = "${param.firmId}";
	drpwarehouseId.value = "${param.warehouseid}";
	txtsearch.value = "${param.searchString}";

	function deleteStock(categoryId) {

		var answer = window.confirm("Are you sure you want to delete ?");
		if (!answer) {
			return;
		}

		document.getElementById("closebutton").style.display = 'none';
		document.getElementById("loader").style.display = 'block';
		$('#myModal').modal({
			backdrop : 'static',
			keyboard : false
		});
		;

		var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function() {
			if (xhttp.readyState == 4 && xhttp.status == 200) {
				document.getElementById("responseText").innerHTML = xhttp.responseText;
				document.getElementById("closebutton").style.display = 'block';
				document.getElementById("loader").style.display = 'none';
				$('#myModal').modal({
					backdrop : 'static',
					keyboard : false
				});
				;

			}
		};
		xhttp.open("GET", "?a=deleteStock&stockid=" + categoryId, true);
		xhttp.send();
	}

	function exportPDFForLedger() {

		var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function() {
			if (xhttp.readyState == 4 && xhttp.status == 200) {
				//window.open("BufferedImagesFolder/"+xhttp.responseText);
				window.location = "BufferedImagesFolder/" + xhttp.responseText;
			}
		};
		var url1 = window.location.href;
		url1 = url1.substring(url1.indexOf("&"), url1.length);

		xhttp.open("GET", "?a=exportCustomerLedgerAsPDF" + url1, true);
		xhttp.send();
	}

	$("#txtfromdate").datepicker({
		dateFormat : 'dd/mm/yy'
	});
	$("#txttodate").datepicker({
		dateFormat : 'dd/mm/yy'
	});

	txtfromdate.value = '${txtfromdate}';
	txttodate.value = '${txttodate}';
	
	
	
	function checkforvalidfromtodate()
	{        	
		var fromDate=document.getElementById("txtfromdate").value;
		var toDate=document.getElementById("txttodate").value;
		
		var fromDateArr=fromDate.split("/");
		var toDateArr=toDate.split("/");
		
		
		var fromDateArrDDMMYYYY=fromDate.split("/");
		var toDateArrDDMMYYYY=toDate.split("/");
		
		var fromDateAsDate=new Date(fromDateArrDDMMYYYY[2],fromDateArrDDMMYYYY[1]-1,fromDateArrDDMMYYYY[0]);
		var toDateAsDate=new Date(toDateArrDDMMYYYY[2],toDateArrDDMMYYYY[1]-1,toDateArrDDMMYYYY[0]);
		
		if(fromDateAsDate>toDateAsDate)
			{
				alert("From Date should be less than or equal to To Date");
				window.location.reload();        			
			}
	}

	
	
</script>