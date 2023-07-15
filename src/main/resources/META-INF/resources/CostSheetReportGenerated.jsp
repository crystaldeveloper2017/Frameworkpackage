
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:set var="requiredData" value='${requestScope["outputObject"].get("requiredData")}' />



<br>

<script>

function checkforMatchItem() {

  	var searchString = document.getElementById("txtjobsheet").value;
  	var options1 = document.getElementById("jobsheetlist").options;
  	var journalId = 0;
  	for (var x = 0; x < options1.length; x++) {
  		if (searchString == options1[x].value) {
  			journalId = options1[x].id;

  			break;
  		}
  	}
  	if (journalId != 0) {
  		document.getElementById("jobsheetlist").value = journalId;
  		document.getElementById("journalid").disabled = true;
  		ReloadFilters();
  	} else {
  		//searchForCustomer(searchString);
  	}

  }
</script>


<div class="card">
<br>

	<div class="row">					
	</div>
	<!-- /.card-header -->
	
	<div class="card-body table-responsive p-0">                
		<table id="example1"class="table table-head-fixed  table-bordered table-striped dataTable dtr-inline" role="grid" aria-describedby="example1_info">             
		<head></head>
			<tbody>
			<tr>
				<td><b>J S No.</b></td>
				<td>
				  <div class="input-group">	  					
				    <input type="text" class="form-control form-control-sm"   placeholder="Search for J S No" list="jobsheetlist" id="txtjobsheet" name="txtjobsheet">
				    <div class="input-group-append">
				      <button class="btn btn-secondary btn-sm" type="button"  onclick="searchJobSheet()">
				        <i class="fa fa-search fa-sm" ></i>
				      </button>
				    </div>
				  </div>
				</td>
				<td>
					<div align="center">
						<div>
                  			<div align="left" style="width: 200px;">
                   				 <div  style="font-size:22px;color:firebrick">
 									<a title="Download PDF" onclick="exportPDFForCostSheet()"><i class="fa fa-file-pdf-o"></i></a>
								</div>           
                  			</div>
                		</div>
				</div>
			
				</td>
				
			</tr>
			
			<tr>
				<td><b>Firm Name</b></td>
				<td>${requiredData.firmName}</td>
			</tr>
			
			<tr>
				<td><b>Sales Person</b></td>
				<td></td>
			</tr>
			
			<tr>
				<td><b>Client Name</b></td>
				<td>${requiredData.customer_name}</td>
			</tr>
			
			<tr>
				<td><b>Vendor Name</b></td>
				<td>${requiredData.vendorName}</td>
			</tr>
			
			
			
			<tr>
				<td></td>
				<td></td>
			</tr>
			
			<tr>
				<td><b>Product Name</b></td>
				<td>${requiredData.item_name}</td>
			</tr>
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			
			
			<tr>
				<td colspan="4"><b>Sales</b></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			
			<tr>
				<td><b>Date</b></td>
				<td align="right"><b>Qty</b></td>
				<td align="right"><b>Rate</b></td>
				<td align="right"><b>Value</b></td>
				<td align="right"><b>salesId</b></td>
			</tr>
			
			<c:forEach items="${requiredData.lstSales}" var="sale">
								
				<tr>
					<td>${sale.FormattedFromDate }</td>
					<td align="right">${sale.Qty }</td>
					<td align="right">${sale.Rate }</td>
					<td align="right">${sale.values1 }</td>
					<td align="right"><a href="?a=showGenerateSI&invoiceId=${sale.invoice_id}">${sale.invoice_id}</a></td>
					
					
				</tr>
			</c:forEach>
			
			
			<tr>
			
			<td align="right"><b>Total Sales Qty</b></td>				
				<td align="right">${requiredData.totalSalesCount}</td>
				
				<td align="right"><b>Total Sales</b></td>				
				<td align="right">${requiredData.totalSales}</td>
				
				
				
				<td></td>
			</tr>
			
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			
			<tr>
				<td colspan="4"><b>Purchase</b></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td><b>Date</b></td>
				<td align="right"><b>Qty</b></td>
				<td align="right"><b>Rate</b></td>
				<td align="right"><b>Value</b></td>
				<td align="right"><b>purchaseId</b></td>
			</tr>

			<c:forEach items="${requiredData.lstPurchase}" var="purchase">

				<tr>
					<td>${purchase.FormattedFromDate }</td>
					<td align="right">${purchase.Qty }</td>
					<td align="right">${purchase.Rate }</td>
					<td align="right">${purchase.values1 }</td>
					<td align="right"><a href="?a=showGeneratePI&invoiceId=${purchase.invoice_id}">${purchase.invoice_id}</a></td>					
				</tr>
			</c:forEach>
			
			
			<tr>
			
			<td align="right"><b>Total Purchase Qty</b></td>				
				<td align="right">${requiredData.totalPurchaseCount}</td>
				
				<td align="right"><b>Total Purchase</b></td>				
				<td align="right">${requiredData.totalPurchase}</td>
				
				
				
				<td></td>
			</tr>
			
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				
			</tr>
			
				<tr>
				<td colspan="4"><b>Purchase Used From Other Job Sheet</b></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td><b>Date</b></td>
				<td align="right"><b>Qty</b></td>
				<td align="right"><b>Rate</b></td>
				<td align="right"><b>Value</b></td>
				<td align="right"><b>purchaseId</b></td>
				<td align="right"><b>Job Sheet No</b></td>
			</tr>

			<c:forEach items="${requiredData.lstFromOtherJobSheet}" var="purchase">

				<tr>
					<td>${purchase.FormattedFromDate }</td>
					<td align="right">${purchase.Qty }</td>
					<td align="right">${purchase.Rate }</td>
					<td align="right">${purchase.values1 }</td>
					<td align="right"><a href="?a=showGeneratePI&invoiceId=${purchase.invoice_id}">${purchase.invoice_id}</a></td>
					<td align="right">${purchase.job_sheet_no }</td>
					
				</tr>
			</c:forEach>
			
			
			
			<tr>
			
			<td align="right"><b>Total Purchase Qty</b></td>				
				<td align="right">${requiredData.totalPurchaseCountFromOtherJobSheet}</td>
				
				<td align="right"><b>Total Purchase</b></td>				
				<td align="right">${requiredData.totalPurchaseFromOtherJobSheet}</td>
				
				
				
				<td></td>
			</tr>
			
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>

				
			
			<tr>
				<td><b>Balance Dispatch</b></td>
				<td>${requiredData.SOQty}</td>
				<td></td>
				<td></td>
			</tr>
			
			<tr>
				<td><b>SO Qty</b></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			
			<tr>
				<td><b>SO Balance</b></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			
			<tr>
				<td colspan="3"><b>Expenses</b></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<c:forEach items="${requiredData.lstExpense}" var="expense">

					<tr>
						<td>${expense.customer_name }</td>
						<td>${expense.creditCustomer }</td>
						<td align="right"><a href="?a=showJournalEntry&journal_id=${expense.journal_id }"> ${expense.amount }</a></td>
						
						<td></td>
					</tr>
				</c:forEach>
			
			
			<tr>				
				<td></td>
				<td><b>Total</b></td>
				<td align="right">${requiredData.totalExpense}</td>
				<td></td>
			</tr>
			
			
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			
			<tr>
				
				<td><b>Profit</b></td>
				<td></td>
				<td align="right">${requiredData.profit }</td>
				<td></td>
			</tr>
			
			<tr>
				
				<td><b>% Profit</b></td>
				<td></td>
				
				<td align="right">${requiredData.profitPercentage } %</td>
				<td></td>
			</tr>
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
      "info": true,
      "autoWidth": false,
      "responsive": true,
      "pageLength": 50,
      "order": [[ 4, 'desc' ]],
      "zeroRecords": " "

    });
  });
  
  document.getElementById("divTitle").innerHTML="Cost Sheet Report";




  function exportPDFForCostSheet()
  {
	  if(txtjobsheet.value=="")
		  {
		  	return;
		  }
	  
	  var stringtosend="?a=exportCostSheetReportPdf&jobsheetno="+txtjobsheet.value;
	  window.open(stringtosend);
	  return;

  }



function searchJobSheet()
{
	  window.location="?a=generateCostSheetReport&jobsheetno="+txtjobsheet.value;
}
txtjobsheet.value='${param.jobsheetno}';


  
</script>