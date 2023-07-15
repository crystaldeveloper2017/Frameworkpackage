<style>
	.date_field {position: relative; z-index:1000;}
	.ui-datepicker{position: relative; z-index:1000!important;}
</style>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="listOffirm"
	value='${requestScope["outputObject"].get("listOffirm")}' />
	
<c:set var="txtfromdate" value='${requestScope["outputObject"].get("txtfromdate")}' />
<c:set var="txttodate" value='${requestScope["outputObject"].get("txttodate")}' />


<c:set var="ListEstimate" value='${requestScope["outputObject"].get("ListEstimate")}' />


<script>


</script>


<br>
<div class="card">

	<br>

	<div class="row">
		<div class="col-sm-2" align="center">
			<div class="input-group input-group-sm" style="width: 200px;">
				<select id="drpfirmId" style="margin-left:3px;" name="drpfirmId" class="form-control float-right" style="margin-right: 15px;" >
  						
  						<option value='-1'>--Select--</option>  						
  						<c:forEach items="${listOffirm}" var="firm">
							<option value='${firm.firmId}'> ${firm.firmName}</option>
						</c:forEach>  							
  					</select>
			</div>
		</div>	
		
		
		<div class="col-sm-1" align="center">
			<label for="txtfromdate">From Date</label>
		</div>
	
		<div class="col-sm-2" align="center">
			<div class="input-group input-group-sm" style="width: 200px;">
				<input type="text" id="txtfromdate" onchange="checkforvalidfromtodate();"  name="txtfromdate" readonly class="form-control date_field" placeholder="Estimate Date"/>
			</div>
		</div>
		
		
		
			<div class="col-sm-1" align="center">
			<label for="txtfromdate">To Date</label>
		</div>
	
		<div class="col-sm-2" align="center">
			<div class="input-group input-group-sm" style="width: 200px;">
				<input type="text" id="txttodate" onchange="checkforvalidfromtodate();"  name="txttodate" readonly class="form-control date_field" placeholder="Estimate Date"/>
			</div>
		</div>

		<div class="col-sm-2" align="center">
			<div class="input-group input-group-sm" style="width: 200px;">
				<input class="form-control form-control-sm" placeholder="Search Estimate No" id="txtsearch"
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
				<th><b>Estimate Id</b></th>
				<th>Estimate No</th>
				<th>Firm Name</th>
				<th>Estimate Date</th>
				<th>Updated By</th>
				
				



			</tr>
		</thead>
		<tbody>
			<c:forEach items="${ListEstimate}" var="item">

				

				
					<tr style="color: green">
				

				
				<td>${item.estimate_id}</td>				
				<td><a style="color:black;text-decoration: underline" href="#" onclick="openGeneratedEstimate('${item.estimate_no}')">${item.estimate_no}.pdf</a></td>
				
				<td>${item.firm_name}</td>
				<td>${item.formattedEstimateDate}</td>
				<td>${item.username}</td>
				
				
				
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
  
  document.getElementById("divTitle").innerHTML="Generated Estimates";
  
  function ReloadFilters()
  {
	  
	 window.location="?a=showGeneratedEstimates&firmId="+drpfirmId.value+"&txtfromdate="+txtfromdate.value+"&txttodate="+txttodate.value+"&searchString="+txtsearch.value;
  }
  
  drpfirmId.value="${param.firmId}";
  txtsearch.value="${param.searchString}";
  
  
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
  
  

  function exportPDFForLedger()
  {
	  

		var xhttp = new XMLHttpRequest();
		  xhttp.onreadystatechange = function() 
		  {
		    if (xhttp.readyState == 4 && xhttp.status == 200) 
		    { 		      
		    	//window.open("BufferedImagesFolder/"+xhttp.responseText);
		    	window.location="BufferedImagesFolder/"+xhttp.responseText;
			}
		  };
		  var url1=window.location.href;
		  url1=url1.substring(url1.indexOf("&"),url1.length);
			
		  

		  xhttp.open("GET","?a=exportCustomerLedgerAsPDF"+url1, true);    
		  xhttp.send();
  }
  
  
  txtfromdate.value='${txtfromdate}';
  txttodate.value='${txttodate}';
	 
	  $( "#txtfromdate" ).datepicker({ dateFormat: 'dd/mm/yy' });
	  $( "#txttodate" ).datepicker({ dateFormat: 'dd/mm/yy' });
	  
	  
	  
	  function openGeneratedEstimate(estimateNo)
		{
			
			var xhttp = new XMLHttpRequest();
			  xhttp.onreadystatechange = function() 
			  {
			    if (xhttp.readyState == 4 && xhttp.status == 200) 
			    { 	
			    	
			    	window.open("BufferedImagesFolder/"+xhttp.responseText);		  
				}
			  };
		  	  
			  xhttp.open("GET","?a=generateEstimatePDF&estimate_no="+estimateNo, false);    
		  	  xhttp.send();
			
			
			
			//window.open("BufferedImagesFolder/"+qtName);			
		}
  
  
  
</script>