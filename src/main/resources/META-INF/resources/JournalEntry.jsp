  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
           
           
           


<c:set var="customerDetails" value='${requestScope["outputObject"].get("customerDetails")}' />
<c:set var="groupList" value='${requestScope["outputObject"].get("groupList")}' />
<c:set var="DistinctCityNames" value='${requestScope["outputObject"].get("DistinctCityNames")}' />
<c:set var="type" value='${requestScope["outputObject"].get("type")}' />
<c:set var="tentativeSerialNo" value='${requestScope["outputObject"].get("tentativeSerialNo")}' />
<c:set var="todaysDate" value='${requestScope["outputObject"].get("todaysDate")}' />

<c:set var="customerVendorList" value='${requestScope["outputObject"].get("customerVendorList")}' />
<c:set var="journalDetails" value='${requestScope["outputObject"].get("journalDetails")}' />








   





</head>


<script>


function saveJournal()
{	
	
	
	if(hdnSelectedDebit.value==hdnSelectedCredit.value)
		{
			alert('Debit and Credit Cannot be same');
			return;
		}

	btnsave.disabled=true;
	var rows=tblitems.rows;
	
	var requiredDetails=[];
	 
	var arr = [];
	var itemString="";
	var confirmMessage="";
	var proceedFlag=true;
	var messageToShow="";
	for (var x= 1; x < rows.length; x++) 
	{   
	    // JOBSHEET NO, AMOUNT
	    itemString+=
	    	rows[x].childNodes[0].innerHTML+ 
	    "~"+rows[x].childNodes[1].innerHTML+	    
	    "|";
	}
	

	
	
	
	var reqString="journal_no="+txtjournalno.value+
	"&journal_date="+txtjournaldate.value+
	"&debit="+hdnSelectedDebit.value+
	"&credit="+hdnSelectedCredit.value+
	"&gross_amount="+txtgrossamount.value+
	"&gst_amount="+txtgstamount.value+
	"&gst_percentage="+txtgstpercent.value+
	"&totalamount="+txttotalamount.value+
	"&remarks="+txtremarks.value+
	"&vendorinvoiceno="+txtvendorinvoiceno.value+
	
	"&itemDetails="+itemString;	
	
	
	

	var xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() {
	    if (this.readyState == 4 && this.status == 200) 
	    {
	    	var invoiceId=this.responseText.split("~");
	      	alert(invoiceId[1]);	      	
	      	window.location.reload();
	      	
	      	
	    	  
	      
	    }
	  };
	  xhttp.open("POST", "?a=saveJournal", true);
	  xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");	  
	  xhttp.send(encodeURI(reqString));
	
	
	
	
	
	
	
	
	
	
}






</script>



<br>


<datalist id="customerVendorList">
<c:forEach items="${customerVendorList}" var="customer">
			    <option id="${customer.customerId}">${customer.customerName}~${customer.customerType}</option>
	   </c:forEach>	   	  	
</datalist>




<div class="container" style="padding:20px;background-color:white"> 

<form id="frm" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
<div class="row">
  <div class="col-sm-4">
  	<div class="form-group">
  	
      <label for="CustomerName">Invoice No</label>      
      <input type="text" class="form-control" id="txtjournalno" readonly value="${tentativeSerialNo}" name="txtjournalno" placeholder="Journal No">      
    </div>
  </div>
  
  
  <div class="col-sm-4">
  	<div class="form-group">
  	
      <label for="CustomerName">Vendor Invoice No</label>      
      <input type="text" class="form-control" id="txtvendorinvoiceno" value="${tentativeSerialNo}" name="txtvendorinvoiceno" placeholder="Journal No">      
    </div>
  </div>
  
  
   <div class="col-sm-4">
  	<div class="form-group">
      <label for="contactperson">Journal Date</label>
      <input type="text" class="form-control" id="txtjournaldate" readonly value="${todaysDate}" name="txtjournaldate" placeholder="Journal Date">
      
    </div>
  </div>
  
  <div class="col-sm-6">
  	<div class="form-group">
      <label for="CustomerName">Debit</label>
      <input type="text" class="form-control" id="txtdebit" value="${customerDetails.email}" name="txtdebit" list="customerVendorList" oninput="checkforMatchDebit()" placeholder="Debit From">
      <input  type="hidden" name="hdnSelectedDebit" id="hdnSelectedDebit" value="">
      
      
    </div>
  </div>
  
  <div class="col-sm-6">
  	<div class="form-group">
      <label for="MobileNumber">Credit</label>
      <input type="text" class="form-control" id="txtcredit" value="${customerDetails.mobile_number}" name="txtcredit" list="customerVendorList"  oninput="checkforMatchCredit()" placeholder="Credit To">
      <input  type="hidden" name="hdnSelectedCredit" id="hdnSelectedCredit" value="">
    </div>
  </div>
  
  
  
  
  
  
  <div class="col-sm-6">
  	<div class="form-group">
      <label for="MobileNumber">Job Sheet No</label>
      <input type="text" class="form-control" id="txtjobsheetno" value="${customerDetails.mobile_number}" name="mobileNumber" placeholder="Job Sheet No" >
    </div>
  </div>
  
  <div class="col-sm-6">
  	<div class="form-group">
      <label for="MobileNumber">Amount</label>
      <input type="text" class="form-control" id="txtamount" value="${customerDetails.mobile_number}" name="mobileNumber" placeholder="Amount" onkeypress="digitsOnlyWithDot(event)" maxlength="10" required onkeyup="checkIfEnterisPressed(event)">
    </div>
  </div>
  
  
  <div class="col-sm-12">  
	  <div class="card-body table-responsive p-0" style="height: 370px;">                
	                <table id="tblitems"  class="table table-head-fixed  table-bordered table-striped dataTable dtr-inline" role="grid" aria-describedby="example1_info">
	                  <thead>
	                    <tr align="center">
	                     <th style="z-index:0">Job Sheet No</th>
	  			<th style="z-index:0">Amount</th>	  			
	  			<th></th>
	                    </tr>
	                  </thead>
	                </table>
	   </div>	
  </div>
  
  <div class="col-sm-6">
  	<div class="form-group">
      <label for="MobileNumber">Remarks</label>
      <input type="text" class="form-control" id="txtremarks" value="${customerDetails.mobile_number}" name="mobileNumber" placeholder="Remarks" >
    </div>
  </div>
  
  <div class="col-sm-6">
  	<div class="form-group">
      <label for="MobileNumber">Gross Amount</label>
      <input type="text" class="form-control" id="txtgrossamount" name="mobileNumber" value="0" readonly >
    </div>
  </div>
  
  
  <div class="col-sm-6">
  	<div class="form-group">
  	
  	      <label for="MobileNumber">GST</label>

					<div class='input-group'>
					<input type='text' style='width: 50%'
							class='form-control form-control-sm' onkeyup='calculateTotalAndGST()'
							id='txtgstpercent' value='0'>
						<input type='text' readonly class='form-control form-control-sm'
							name='txtgstamountgroup' id='txtgstamount' value=''
							style='width: 50%'> 
					</div>
				</div>
  </div>
  
  
  <div class="col-sm-6">
  	<div class="form-group">
      <label for="MobileNumber">Total Amount</label>
      <input type="text" class="form-control" id="txttotalamount" name="mobileNumber" value="0" readonly >
    </div>
  </div>
  
  <div class="col-sm-12" align="center">
  	<div class="form-group">
      
   <button class="btn btn-success" id="btnsave" onclick='saveJournal()'>Save</button>
  <button class="btn btn-danger" onclick='window.location="?a=showHomePage"' id="btncancel">Cancel</button>
  <button class="btn btn-primary" type="button" onclick='window.location="?a=showJournals"' id="btnregister">Register</button>
  
  
  <button class="btn btn-danger" onclick="deleteJournals(${param.journal_id})" id="btndelete">Delete</button>
     
    </div>
  </div>
  
  
  
  
  
  
  </div>
  
  
</div>
</form>








<script>
	document.getElementById("divTitle").innerHTML="Journal Entry";	
	
	
	
	function checkIfEnterisPressed(evt)
	{
		if(evt.which!=13)
		{
			return;
		}
		
		var table = document.getElementById("tblitems");	    	
    	var row = table.insertRow(-1);	    	
    	var cell1 = row.insertCell(0);
    	var cell2 = row.insertCell(1);
    	var cell3 = row.insertCell(2);
    	
    	
    	
    	
    	cell1.innerHTML=txtjobsheetno.value;
    	cell2.innerHTML=txtamount.value;
    	cell3.innerHTML = '<button type="button" class="btn btn-sm btn-danger"  onclick=removethisitem(this) id="btn11" name="deleteButtons" style="cursor:pointer">Delete</button> ';
    	
    	
    	txtjobsheetno.value="";
    	txtamount.value="";
    	
    	txtjobsheetno.focus();
    	
    	calculateTotal();
		
	}
	
	function calculateTotal()
	{
		
		var total=0;
		
		var rows=tblitems.rows;
		for(var x=1;x<rows.length;x++)
			{
				var itemTotalAmount=rows[x].childNodes[1].innerHTML;
				total+=Number(itemTotalAmount);			
			}
		
		txtgrossamount.value=total;
		
		calculateTotalAndGST();
		
	}
	$( "#txtjournaldate" ).datepicker({ dateFormat: 'dd/mm/yy' });
	
	
	function checkforMatchDebit()
	{
		
		var searchString= document.getElementById("txtdebit").value;	
		var options1=document.getElementById("customerVendorList").options;
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
				document.getElementById("hdnSelectedDebit").value=customerId;			
				document.getElementById("txtdebit").disabled=true;		
					
			}
		else
			{
				//searchForCustomer(searchString);
			}
		
		
		
	}
	function checkforMatchCredit()
	{
		
		var searchString= document.getElementById("txtcredit").value;	
		var options1=document.getElementById("customerVendorList").options;
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
				document.getElementById("hdnSelectedCredit").value=customerId;			
				document.getElementById("txtcredit").disabled=true;		
					
			}
		else
			{
				//searchForCustomer(searchString);
			}
		
		
		
	}
	
	
function calculateTotalAndGST()
{
	
	var gstAmount=Number(txtgrossamount.value)*Number(txtgstpercent.value) / 100;	
	txtgstamount.value=gstAmount;
	
	txttotalamount.value=Number(gstAmount)+Number(txtgrossamount.value);
}


if('${journalDetails.journal_id}'!='')
{
	
	document.getElementById("divTitle").innerHTML="Invoice No:-"+"${journalDetails.journal_no}";
	document.getElementById("txtvendorinvoiceno").value="${journalDetails.vendor_invoice_no}";
	document.getElementById("txtjournaldate").value="${journalDetails.journal_date}";
	document.getElementById("txtdebit").value="${journalDetails.debitName}";
	document.getElementById("txtcredit").value="${journalDetails.creditName}";
	
	
	document.getElementById("txtremarks").value="${journalDetails.remarks}";	
	document.getElementById("txtgrossamount").value="${journalDetails.gross_amount}";
	document.getElementById("txtgstpercent").value="${journalDetails.gst_percentage}";
	document.getElementById("txtgstamount").value="${journalDetails.gst_amount}";
	document.getElementById("txttotalamount").value="${journalDetails.total_amount}";
	
	
	var m=0;
	var tableNo="";
	<c:forEach items="${journalDetails.listOfItems}" var="item">
	
	m++;
	
	var table = document.getElementById("tblitems");	    	
	var row = table.insertRow(-1);	    	
	var cell1 = row.insertCell(0);
	var cell2 = row.insertCell(1);
	var cell3 = row.insertCell(2);
		
	
	
	cell1.innerHTML = '<div>${item.job_sheet_no}</div>';    	
	cell2.innerHTML = '${item.amount}';
	cell3.innerHTML = '';
	
	
	
    		//alert('${item.item_id}'+'-${item.item_name}'+'-${item.qty}'+'-${item.rate}'+'-${item.custom_rate}');			    
	</c:forEach>

	
	if('${param.editInvoice}'=='Y')
		{
			hdnPreviousInvoiceId.value="${invoiceDetails.invoice_id}";
			var returnButtons=document.getElementsByName('returnButtons');
			for(var m=0;m<returnButtons.length;m++)
				{
					returnButtons[m].style="display:none";
				}
			txtsearchcustomer.disabled=true;
			txtinvoicedate.disabled=true;
			document.getElementById("divTitle").innerHTML="Edit Invoice:-"+"${invoiceDetails.invoice_no}";
		}
	else
		{
			$("#frm :input").prop('disabled', true);
			$("[name=returnButtons]").prop('disabled', false);
			
			
			var deleteButtons=document.getElementsByName('deleteButtons');
			for(var m=0;m<deleteButtons.length;m++)
				{
					deleteButtons[m].style="display:none";
				}
		}
	

	btnregister.disabled=false;
	btncancel.disabled=false;
	btndelete.disabled=false;
	
	

	
}

function deleteJournals(customerId)
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
	  xhttp.open("GET","?a=deleteJournals&journalid="+customerId, true);    
	  xhttp.send();
}
	

	
	
	
</script>
