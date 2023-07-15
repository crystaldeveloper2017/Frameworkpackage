<style>
	.date_field {position: relative; z-index:1000;}
	.ui-datepicker{position: relative; z-index:1000!important;}
</style>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>



<c:set var="listOfFirms" value='${requestScope["outputObject"].get("listOfFirms")}' />
<c:set var="sbuMaster" value='${requestScope["outputObject"].get("sbuMaster")}' />
<c:set var="sbuId" value='${requestScope["outputObject"].get("sbuId")}' />
<c:set var="ProfitFlag" value='${requestScope["outputObject"].get("ProfitFlag")}' />


<c:set var="fromDate" value='${requestScope["outputObject"].get("fromDate")}' />
<c:set var="toDate" value='${requestScope["outputObject"].get("toDate")}' />
<c:set var="firmId" value='${requestScope["outputObject"].get("firmId")}' />
<c:set var="bankId" value='${requestScope["outputObject"].get("bankId")}' />
<c:set var="openingBalance" value='${requestScope["outputObject"].get("openingBalance")}' />
<c:set var="closingBalance" value='${requestScope["outputObject"].get("closingBalance")}' />

<c:set var="totalDetails" value='${requestScope["outputObject"].get("totalDetails")}' />
<c:set var="purchaseAccounts" value='${requestScope["outputObject"].get("purchaseAccounts")}' />
<c:set var="salesAccounts" value='${requestScope["outputObject"].get("salesAccounts")}' />


<c:set var="listOfExpensesClubbed" value='${requestScope["outputObject"].get("listOfExpensesClubbed")}' />
<c:set var="listOfIncomesClubbed" value='${requestScope["outputObject"].get("listOfIncomesClubbed")}' />


<c:set var="OpeningStockAmount" value='${requestScope["outputObject"].get("OpeningStockAmount")}' />
<c:set var="ClosingStockAmount" value='${requestScope["outputObject"].get("ClosingStockAmount")}' />
<c:set var="leftTotal" value='${requestScope["outputObject"].get("leftTotal")}' />
<c:set var="rightTotal" value='${requestScope["outputObject"].get("rightTotal")}' />
<c:set var="totalProfit" value='${requestScope["outputObject"].get("totalProfit")}' />

<c:set var="totalPurchaseTillToDateMinusOne" value='${requestScope["outputObject"].get("totalPurchaseTillToDateMinusOne")}' />
<c:set var="totalSalesTillDateMinusOneAtPurchaseRate" value='${requestScope["outputObject"].get("totalSalesTillDateMinusOneAtPurchaseRate")}' />

<c:set var="totalPurchaseTillToDate" value='${requestScope["outputObject"].get("totalPurchaseTillToDate")}' />
<c:set var="totalSalesTillDatePurchaseRate" value='${requestScope["outputObject"].get("totalSalesTillDatePurchaseRate")}' />












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
				
				<div class="col-sm-2" align="center">
					<div class="input-group input-group-sm" style="width: 200px;">
	  					<select id="drpfirmid" name="drpfirmid"  class="form-control float-right" onchange="ReloadFilters()" style="margin-right: 15px;" >
	  						
	  						<option value='-1'>--Select--</option>  						
	  						<c:forEach items="${listOfFirms}" var="firm">
	  						<option value='${firm.firmId}'> ${firm.firmName}</option>
							</c:forEach>  							
	  					</select>
                  	</div>
				</div>
				
				<div class="col-sm-2" align="center">
					<div class="input-group input-group-sm" style="width: 200px;">
						<select name="drpsbuname" id="drpsbuname"  class="form-control float-right" onchange="ReloadFilters()" style="margin-right: 15px;" >
							<option value='-1'>--Select--</option>  
							<c:forEach items="${sbuMaster}" var="sbu">
							<option value="${sbu.sbu_id}">${sbu.sbu_name}</option>			    
							</c:forEach>
						</select>        
					</div>
				</div>


	
		


		<div class="col-sm-3" align="center">
							<div class="card-tools">
		                  <div class="input-group input-group-sm" align="center" style="width: 200px;display:inherit">
		                    <div class="icon-bar" style="font-size:22px;color:firebrick">
		  						<a title="Download Excel" onclick="downloadExcel()"><i class="fa fa-file-excel-o" aria-hidden="true"></i></a> 
		 						<a title="Download PDF" onclick="downloadPDF()"><i class="fa fa-file-pdf-o"></i></a>
		  						<a title="Download Text"  onclick="downloadText()"><i class="fa fa-file-text-o"></i></a>  
							</div>           
		                  </div>
		                </div>
				</div>
				
			  </div>
              
              
              
              
              
              
              <!-- /.card-header -->
              <div class="card-body table-responsive p-0" style="height: 580px;">                
                <table id="example1" class="table table-head-fixed  table-bordered table-striped dataTable dtr-inline" role="grid" aria-describedby="example1_info">
                  <thead>
                    <tr>
                     <th><b>Particulars </b></th>
                     <th><b></b></th>
                     <th><b> </b></th>
                     <th><b>Particulars</b></th>
                     <th><b></b></th>                     
                     <th><b></b></th>	
                    </tr>
                  </thead>
                  <tbody>
                  
                  
                  
                 
                  
                  <tr >
						
											
						
																	
											
						
						 <td colspan="1">Opening Stock</td>
						<td colspan="2" align="right">${OpeningStockAmount}<%-- = <br> totalPurchaseTillFromDateMinusOne ${totalPurchaseTillToDateMinusOne }  <br>Minus<br>totalSalesFromDateMinusOneAtPurchaseRate ${totalSalesTillDateMinusOneAtPurchaseRate } --%></td>
						
						 <td colspan="1"></td>
						<td colspan="2" align="right"></td>
						
						
						
						
					</tr>
                  <tr >
						
											
						
																	
											
						
						<td colspan="1">PurchaseAccounts</td>
						<td colspan="2" align="right"><a href="?a=showInvoices&type=P&firmId=${param.firmId}&txtfromdate=${param.txtfromdate}&txttodate=${param.txttodate}">${purchaseAccounts}</a></td>
						
						<td colspan="1">Sales Accounts</td>
						<td colspan="2" align="right"><a href="?a=showInvoices&type=S&firmId=${param.firmId}&txtfromdate=${param.txtfromdate}&txttodate=${param.txttodate}">${salesAccounts}</a></td>
						
					</tr>




				
				
				
				<c:forEach var="expense" items="${listOfExpensesClubbed}">
					<tr>
						<td>${expense.get('Head')} </td>
						
						<td colspan="2" align="right"><a href="?a=showCustomerLedger&txtfromdate=${param.txtfromdate}&txttodate=${param.txttodate}&customerId=${expense.get('customer_id')}&firmId=${param.firmId }">${expense.get('theAmount')}</a>
					</tr>
				</c:forEach>


				<c:forEach var="income" items="${listOfIncomesClubbed}">
					<tr>
					
					
					<td colspan="3"></td>
						<td>${income.get('Head')}</td>
						
						
						
						<td colspan="2"><a
							href="?a=showCustomerLedger&txtfromdate=${param.txtfromdate}&txttodate=${param.txttodate}&customerId=${income.get('customer_id')}&firmId=${param.firmId }">${income.get('theAmount')}</a></td>

						
					</tr>
				</c:forEach>

				
				
				
				
				<tr >
						
											
						<td colspan="3"></td>
						<td>Closing Stock</td>
						
						
						<td colspan="2" align="right">${ClosingStockAmount} <%-- = <br> totalPurchaseTillToDate ${totalPurchaseTillToDate }  <br>Minus<br>totalSalesTillDatePurchaseRate ${totalSalesTillDatePurchaseRate } --%></td>

						
						
						
					</tr>
				
				
				
				
				
				<tr >
						
											
						
						<td>Left Total</td>
						
						<td colspan="2" align="right">${leftTotal}</td>
						
					<td>Right Total</td>
						
						<td colspan="2" align="right">${rightTotal}</td>
						
						
					</tr>


				<tr>
					
					<c:if test="${ProfitFlag ne 1}">
									<td style="background-color:orange;">Total Loss </td>							  				
					  				<td colspan="5" style="background-color:orange;color:black" align="right">${totalProfit}</td>					  
	  				</c:if>
	  				
	  				<c:if test="${ProfitFlag eq 1}">
	  								<td style="background-color:lightgreen;">Total Profit </td>							  				
					  				<td colspan="5" style="background-color:lightgreen;color:black" align="right">${totalProfit}</td>					  
	  				</c:if>
					
				</tr>





			</tbody>
                </table>
              </div>
              <!-- /.card-body -->
              
              
              
              <%-- <div class="card-body table-responsive p-0">                
                <table id="testing"class="table table-head-fixed  table-bordered table-striped" role="grid" aria-describedby="example1_info">             
                 <thead>
                    <tr>                  
                    <th colspan="3"></th>
                    <th colspan="2">Opening Balance: ${openingBalance} </th>
                    <th colspan="1"><b>Debit Total: ${totalDetails.debitTotal}</b></th>                    
                    <th colspan="1"><b>Credit Total: ${totalDetails.creditTotal}</b></th>
                    <th colspan="1"><b>Closing Balance: ${closingBalance}</b></th>                                        
                     </th>
                     </tr>
                  <thead>    
                  
              
				
				
                  </tbody>
                </table>
              </div> --%>
              
              
</div>
            
            
            
            



<script>
  
  // make order changes here
  
  document.getElementById("divTitle").innerHTML="Profit and Loss Report";
  
  
  
</script>


<script>
function ReloadFilters()
{
	
	if(txtfromdate.value!="" && txttodate.value!="" && drpfirmid.value!="" && drpsbuname.value!="")
	{
	 	window.location="?a=generateProfitAndLossReport&txtfromdate="+txtfromdate.value+"&txttodate="+txttodate.value+"&firmId="+drpfirmid.value+"&sbuId="+drpsbuname.value;
	}
	  
}

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

$( "#txtfromdate" ).datepicker({ dateFormat: 'dd/mm/yy' });
$( "#txttodate" ).datepicker({ dateFormat: 'dd/mm/yy' });



txtfromdate.value='${fromDate}';
txttodate.value='${toDate}';

drpfirmid.value='${firmId}';
drpsbuname.value='${sbuId}';







function editInvoice(invoiceId)
{
		window.location="?a=showGenerateInvoice&editInvoice=Y&invoice_id="+invoiceId;
		//alert(invoiceId);
}
function deleteInvoice(invoiceId)
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
	  xhttp.open("GET","${masterUrl}?a=deleteInvoice&user_id=${userdetails.user_id}&app_id=${userdetails.app_id}&store_id=${userdetails.store_id}&invoiceId="+invoiceId, true);    
	  xhttp.send();
}


function getBanksForThisFirm(firmId,elementName)
{
	
	
	var xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() 
	  {
	    if (xhttp.readyState == 4 && xhttp.status == 200) 
	    { 		      
	      
	      var listOfRoleDetails=JSON.parse(xhttp.responseText);
	      var reqString="<option id='-1'>------Select-----</option>";
	      for(var x=0;x<listOfRoleDetails.length;x++)
	      {
	    	  reqString+="<option value='"+listOfRoleDetails[x].bank_id+"' id='"+listOfRoleDetails[x].bank_id+"'>"+listOfRoleDetails[x].bank_name+"-"+listOfRoleDetails[x].account_no+"</option>";
	      }
	      document.getElementById(elementName).innerHTML=reqString;
	      
	      
		}
	  };
	  xhttp.open("GET","?a=getBankMappingForThisFirm&type=F&firmId="+firmId, true);    
	  xhttp.send();
}

</script>
