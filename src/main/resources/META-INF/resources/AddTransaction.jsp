
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<c:set var="todaysDate"
	value='${requestScope["outputObject"].get("todaysDate")}' />
<c:set var="customerMaster"
	value='${requestScope["outputObject"].get("customerMaster")}' />
<c:set var="banksForThisFirm"
	value='${requestScope["outputObject"].get("banksForThisFirm")}' />
<c:set var="todaysDate"
	value='${requestScope["outputObject"].get("todaysDate")}' />
<c:set var="paymentDetails"
	value='${requestScope["outputObject"].get("paymentDetails")}' />




<br>

<div class="container" style="padding: 20px; background-color: white">

	<datalist id="customerList">
		<c:forEach items="${customerMaster}" var="customer">
			<option id="${customer.customerId}">${customer.customerName}~(${customer.mobileNumber})~(${customer.customerType})</option>
		</c:forEach>
	</datalist>

	<div class="row">

		<div class="col-sm-2">
			<div class="form-group">
				<label for="email">Date</label> <input type="text" id="txtdate"
					name="txtdate" class="form-control  form-control-sm"
					value="${todaysDate}" placeholder="Date" readonly />
			</div>
		</div>

		<div class="col-sm-6">
			<div class="form-group">
				<label for="email">Party Name</label>
				<div class="input-group input-group-sm">

					<input type="text" class="form-control form-control-sm"
						id="txtsearchcustomer" placeholder="Search For Customer"
						name="txtsearchcustomer" list='customerList'
						oninput="checkforMatchCustomer()"> <span
						class="input-group-append">
						<button type="button" class="btn btn-danger btn-flat"
							onclick="resetCustomer()">Reset</button>
					</span>
				</div>
				<input type="hidden" name="hdnSelectedCustomer"
					id="hdnSelectedCustomer" value=""> <input type="hidden"
					name="hdnSelectedCustomerType" id="hdnSelectedCustomerType"
					value="">
			</div>
		</div>

		<div class="col-sm-3">
			<div class="form-group">
				<label for="email">Current Balance</label> <input type="text"
					class="form-control form-control-sm" id="txtpendingamount"
					readonly=true>
			</div>
		</div>


		<div class="col-sm-3">
			<div class="form-group">

				<c:if test="${param.type eq 'P'}">
					<label for="email">Paid From</label>
				</c:if>

				<c:if test="${param.type eq 'R'}">
					<label for="email">Received In</label>
				</c:if>

				<select class='form-control form-control-sm' id="drpbank">


					<c:forEach items="${banksForThisFirm}" var="bank">
						<option id="${bank.bank_id}" value="${bank.bank_id}">${bank.bank_name}~(${bank.account_no}~(${bank.ifsc_code})</option>
					</c:forEach>


				</select>

			</div>
		</div>

















		<div class="col-sm-3">
			<div class="form-group">

				<c:if test="${param.type eq 'P'}">
					<label for="email">Payment For</label>
				</c:if>

				<c:if test="${param.type eq 'R'}">
					<label for="email">Received For</label>
				</c:if>

				<select class='form-control form-control-sm' id="drppaymentfor"
					onchange="showHideRefId()">
					<option value="-1">----Select----</option>
					<option value="Advance">Advance</option>
					<option value="OnAccount">OnAccount</option>
					<option value="Invoice">Invoice</option>

				</select>

			</div>
		</div>





		<div class="col-sm-2">
			<div class="form-group">
				<label for="email">Remarks</label> <input type="txtremarks"
					class="form-control form-control-sm" id="txtremarks">

			</div>



		</div>







		<div class="col-sm-2" id="grpuRefId">
			<div class="form-group">
				<label for="email">Ref Id</label> <input type="text"
					id="txtsearchrefid" class="form-control" list="listOfInvoices"
					onchange="checkForMatchInvoice()">


				<datalist id="listOfInvoices">

				</datalist>


			</div>
		</div>


		<div class="col-sm-12">
			<div class="card-body table-responsive p-0" style="height: 370px;">
				<table id="tblitems"
					class="table table-head-fixed  table-bordered table-striped dataTable dtr-inline"
					role="grid" aria-describedby="example1_info">
					<thead>
						<tr align="center">
							<th style="z-index: 0">Invoice No</th>
							<th style="z-index: 0">Amount</th>
							<th>Amount to Round Off</th>
							<th>Remarks</th>
							<th>Job sheet no</th>
							<th>Payment For</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>


		<div class="col-sm-12">
			<div class="form-group" align="center">
				<label for="" id="lblTotalAmount"></label>
			</div>
		</div>


		<div class="col-sm-12">
			<div class="form-group" align="center">


				<button class="btn btn-success" type="button" id="btnsave"
					onclick='savePayment()'>Save</button>
				<button class="btn btn-danger" type="reset"
					onclick='window.location="?a=showHomePage"'>Cancel</button>
					<button class="btn btn-danger" id="btndelete" style="display:none" 
					onclick='deletePaymenttransaction(${param.payment_id})'>Delete</button>
			</div>
		</div>





	</div>


	<script>
		
	</script>



	<script>
		function searchForCustomer(searchString) {
			console.log(5);
			if (searchString.length < 3) {
				return;
			}

			document.getElementById("closebutton").style.display = 'none';
			document.getElementById("loader").style.display = 'block';
			var xhttp = new XMLHttpRequest();
			xhttp.onreadystatechange = function() {
				if (xhttp.readyState == 4 && xhttp.status == 200) {
					var cusomerList = JSON.parse(xhttp.responseText);
					var reqString = "";
					for (var x = 0; x < cusomerList.length; x++) {
						//console.log(cusomerList[x]);
						reqString += "<option id="+cusomerList[x].customer_id+">"
								+ cusomerList[x].customer_name
								+ "-"
								+ cusomerList[x].mobile_number
								+ "-"
								+ cusomerList[x].customer_type + "</option>";
					}

					document.getElementById('customerList').innerHTML = reqString;
				}
			};
			xhttp.open("GET", "?a=searchForCustomer&searchString="
					+ searchString, true);
			xhttp.send();

		}

		function checkforMatchCustomer() {
			var searchString = document.getElementById("txtsearchcustomer").value;
			if (searchString.length < 3) {
				return;
			}
			var options1 = document.getElementById("customerList").options;
			var customerId = 0;
			for (var x = 0; x < options1.length; x++) {
				if (searchString == options1[x].value) {
					customerId = options1[x].id;
					break;
				}
			}
			if (customerId != 0) {
				document.getElementById("hdnSelectedCustomer").value = customerId;
				document.getElementById("txtsearchcustomer").disabled = true;
				document.getElementById("hdnSelectedCustomerType").value = document
						.getElementById("txtsearchcustomer").value.split("-")[2];
				getPendingAmountForThisCustomer(customerId);
			} else {
				//searchForCustomer(searchString);
			}

		}

		function getPendingAmountForThisCustomer(customerId) {
			document.getElementById("closebutton").style.display = 'none';
			document.getElementById("loader").style.display = 'block';
			var xhttp = new XMLHttpRequest();
			xhttp.onreadystatechange = function() {
				if (xhttp.readyState == 4 && xhttp.status == 200) {
					var details = JSON.parse(xhttp.responseText);

					if (details.pendingAmountDetails.pendingAmount != undefined) {
						txtpendingamount.value = details.pendingAmountDetails.pendingAmount;
						getPendingInvoicesForThisClient(customerId);
					} else {
						alert("no pending amount for this customer");
						//window.location.reload();
					}
				}
			};
			xhttp.open("GET", "?a=getPendingAmountForCustomer&customerId="
					+ customerId, true);
			xhttp.send();
		}

		function savePayment() {

			btnsave.disabled = true;

			if (!checkForEmptyAmount()) {
				alert("Round Off Amount Cannot be empty");
				return;
			}

			if (!checkForGreaterRoundingThanInvoice()) {
				alert("Round Off Amount Greater than Invoice Amount");
				return;
			}

			document.getElementById("closebutton").style.display = 'none';
			document.getElementById("loader").style.display = 'block';
			//$('#myModal').modal({backdrop: 'static', keyboard: false});;

			var xhttp = new XMLHttpRequest();
			xhttp.onreadystatechange = function() {
				if (xhttp.readyState == 4 && xhttp.status == 200) {
					alert(JSON.parse(xhttp.responseText).returnMessage);
					window.location.reload();
				}
			};

			//var refIds=getSelectValues(document.getElementById("refInvoices"));

			var rows1 = tblitems.rows;
			var exitFlag = false;
			var refIds = "";
			for (var x = 1; x < rows1.length; x++) {
				var invId = rows1[x].childNodes[0].innerHTML;
				var amountToRoundOff = rows1[x].childNodes[2].childNodes[0].value;
				var remarks = rows1[x].childNodes[3].childNodes[0].value;
				var jobsheetno = rows1[x].childNodes[4].childNodes[0].value;
				var paymentfor = rows1[x].childNodes[5].childNodes[0].value;

				refIds += invId + "-" + amountToRoundOff + "-" + remarks + "-"
						+ jobsheetno + "-" + paymentfor + ",";
			}

			var stringTosend = "&txtdate=" + txtdate.value + "&customerId="
					+ hdnSelectedCustomer.value + "&bankid=" + drpbank.value
					+ "&payment_for=" + drppaymentfor.value + "&ref_id="
					+ refIds + "&remarks=" + txtremarks.value
					+ "&total_amount="
					+ lblTotalAmount.innerHTML.replace("Total Amount : - ", "")
					+ "&type=${param.type}";

			xhttp.open("GET", "?a=savePayment" + stringTosend, true);
			xhttp.send();

		}

		function showCustomer() {
			window.location = '?a=showSalesRegister&customerId='
					+ hdnSelectedCustomer.value;
		}

		$("#txtdate").datepicker({
			dateFormat : 'dd/mm/yy'
		});

		if ('${param.type}' == "R") {
			document.getElementById("divTitle").innerHTML = "Add Receipt";
		} else {
			document.getElementById("divTitle").innerHTML = "Send Payment";
		}

		function resetCustomer() {
			txtsearchcustomer.disabled = false;
			txtsearchcustomer.value = "";
			hdnSelectedCustomer.value = 0;
			txtpendingamount.value = "";
		}

		function getPendingInvoicesForThisClient(customerId) {

			document.getElementById("closebutton").style.display = 'none';
			document.getElementById("loader").style.display = 'block';
			var xhttp = new XMLHttpRequest();
			xhttp.onreadystatechange = function() {
				if (xhttp.readyState == 4 && xhttp.status == 200) {
					var details = JSON.parse(xhttp.responseText);
					var listOfInvoicesVar = details.pendingAmountDetails;
					console.log(listOfInvoices);

					var optionsString = "";
					for (var m = 0; m < listOfInvoicesVar.length; m++) {
						optionsString += "<option >"
								+ listOfInvoicesVar[m].invoice_id + "-"
								+ listOfInvoicesVar[m].pendingAmount + "-"
								+ listOfInvoicesVar[m].invoiceDate
								+ "</option>"
					}

					listOfInvoices.innerHTML = optionsString;

				}
			};
			xhttp.open("GET", "?a=getPendingInvoicesForThisClient&customerId="
					+ customerId + "&type=${param.type}", true);
			xhttp.send();

		}

		function showHideRefId() {

			if (drppaymentfor.value == 'Advance'
					|| drppaymentfor.value == 'OnAccount') {
				grpuRefId.style = "display:none";

				var table = document.getElementById("tblitems");
				var row = table.insertRow(-1);
				var cell1 = row.insertCell(0);
				var cell2 = row.insertCell(1);
				var cell3 = row.insertCell(2);
				var cell4 = row.insertCell(3);
				var cell5 = row.insertCell(4);
				var cell6 = row.insertCell(5);
				var cell7 = row.insertCell(6);

				cell1.innerHTML = "0";
				cell2.innerHTML = "0";
				cell3.innerHTML = '<input type="text" class="form-control" onkeypress="digitsOnlyWithDot(event);" onkeyup="calculateTotal()">';
				cell4.innerHTML = '<input type="text" class="form-control" placeholder="remarks">';
				cell5.innerHTML = '<input type="text" class="form-control" placeholder="job sheet no">';

				cell6.innerHTML = '<input type="text" class="form-control" readonly value='+drppaymentfor.value+'>';
				cell7.innerHTML = '<button type="button" class="btn btn-sm btn-danger"  onclick=removethisitem(this) id="btn11" name="deleteButtons" style="cursor:pointer">Delete</button> ';

				calculateTotal();

			} else {
				grpuRefId.style = "display:";
			}

		}

		showHideRefId();

		function checkForEmptyAmount() {
			var rows1 = tblitems.rows;
			var totalAmount = 0;
			var resultValue = true;
			for (var x = 1; x < rows1.length; x++) {
				if (rows1[x].childNodes[2].childNodes[0].value == "") {
					resultValue = false;
					break;
				}
			}
			return resultValue;

		}

		function isValidPaymentDetails() {
			if (drppaymentfor.value == 'Advance') {
				return true;
			}

			// txtamount

			var rows1 = tblitems.rows;
			var booleanResult = false;

			var totalAmount = 0;
			for (var x = 1; x < rows1.length; x++) {
				totalAmount += Number(rows1[x].childNodes[2].childNodes[0].value);
			}

			if (Number(txtamount.value) == totalAmount) {
				booleanResult = true;
			}

			return booleanResult;

		}

		function checkForGreaterRoundingThanInvoice() {

			var rows1 = tblitems.rows;
			var booleanResult = true;

			var totalAmount = 0;
			for (var x = 1; x < rows1.length; x++) {
				var inputAmount = rows1[x].childNodes[2].childNodes[0].value;
				var invoiceNo = rows1[x].childNodes[0].innerHTML;
				var pendingInvoiceAmount = rows1[x].childNodes[1].innerHTML;
				if (Number(inputAmount) > Number(pendingInvoiceAmount)
						&& invoiceNo != "0") {
					booleanResult = false;
					break;
				}
			}
			return booleanResult;
		}

		function getSelectValues(select) {
			var result = [];
			var options = select && select.options;
			var opt;

			for (var i = 0, iLen = options.length; i < iLen; i++) {
				opt = options[i];

				if (opt.selected) {
					result.push(opt.text);
				}
			}
			return result;
		}

		function checkForMatchInvoice() {
			var id = 0;
			var amount = 0;
			var searchString = document.getElementById("txtsearchrefid").value;
			var options1 = document.getElementById("listOfInvoices").options;
			var customerId = 0;
			for (var x = 0; x < options1.length; x++) {
				if (searchString == options1[x].value) {

					value = options1[x].value;
					var values = value.split("-");
					id = values[0];
					amount = values[1];
					break;
				}
			}

			var rows1 = tblitems.rows;
			var exitFlag = false;
			for (var x = 1; x < rows1.length; x++) {
				if (rows1[x].childNodes[0].innerHTML == id) {
					alert('already exist');
					exitFlag = true;
				}
			}

			if (exitFlag) {
				document.getElementById("txtsearchrefid").value = "";
				return;
			}

			var table = document.getElementById("tblitems");
			var row = table.insertRow(-1);
			var cell1 = row.insertCell(0);
			var cell2 = row.insertCell(1);
			var cell3 = row.insertCell(2);
			var cell4 = row.insertCell(3);
			var cell5 = row.insertCell(4);
			var cell6 = row.insertCell(5);
			var cell7 = row.insertCell(6);

			cell1.innerHTML = id;
			cell2.innerHTML = amount;
			cell3.innerHTML = '<input type="text" class="form-control" value='
					+ amount
					+ ' onkeypress="digitsOnlyWithDot(event);" onkeyup="calculateTotal()">';
			cell4.innerHTML = '<input type="text" class="form-control" placeholder="remarks">';
			cell5.innerHTML = '<input type="text" class="form-control" placeholder="job sheet no">';
			cell6.innerHTML = '<input type="text" class="form-control" readonly value='+drppaymentfor.value+'>';
			cell7.innerHTML = '<button type="button" class="btn btn-sm btn-danger"  onclick=removethisitem(this) id="btn11" name="deleteButtons" style="cursor:pointer">Delete</button> ';

			document.getElementById("txtsearchrefid").value = "";
			calculateTotal();

		}

		function removethisitem(btn1) {
			btn1.parentElement.parentElement.remove();

		}
		function calculateTotal() {
			var rows1 = tblitems.rows;
			var totalAmount = 0;
			var resultValue = true;
			for (var x = 1; x < rows1.length; x++) {
				totalAmount += Number(rows1[x].childNodes[2].childNodes[0].value);
			}
			lblTotalAmount.innerHTML = "Total Amount : - " + totalAmount;
		}
		
		
		if('${paymentDetails.payment_id}'!='')
		{
			
			
			

			document.getElementById("divTitle").innerHTML="Transaction No:-"+"${paymentDetails.payment_id}";
			hdnSelectedCustomer.value="${paymentDetails.customer_id}";
			txtsearchcustomer.value="${paymentDetails.customer_name}"
			btndelete.style="display:block";
			
			
				
			if('${paymentDetails.customer_name}'!='')
				{
					txtsearchcustomer.value="${paymentDetails.customer_name}~${paymentDetails.mobile_number}~${paymentDetails.customer_type}";
					txtsearchcustomer.disabled=true;
				}			
			
			document.getElementById("hdnSelectedCustomerType").value='${paymentDetails.customer_type}';
			
			
			 var inputs = document.getElementsByTagName("input");
			    for (var i = 0; i < inputs.length; i++) {
			        inputs[i].disabled = true;
			    }
			    var selects = document.getElementsByTagName("select");
			    for (var i = 0; i < selects.length; i++) {
			        selects[i].disabled = true;
			    }
			    var textareas = document.getElementsByTagName("textarea");
			    for (var i = 0; i < textareas.length; i++) {
			        textareas[i].disabled = true;
			    }
			    var buttons = document.getElementsByTagName("button");
			    for (var i = 0; i < buttons.length; i++) {
			        buttons[i].disabled = true;
			    }
			    btndelete.disabled=false;
			<c:forEach items="${paymentDetails.listOfItems}" var="item">
			
			m++;
			tableNo='${item.table_no}';
			var table = document.getElementById("tblitems");	    	
	    	var row = table.insertRow(-1);	    	
	    	var cell1 = row.insertCell(0);
	    	var cell2 = row.insertCell(1);
	    	var cell3 = row.insertCell(2);
	    	var cell4 = row.insertCell(3);
	    	var cell5 = row.insertCell(4);
	    	var cell6 = row.insertCell(5);
	    	
	    	
	    	
	    	
	    	    	
	    	cell1.innerHTML = '${item.ref_id}';
	    	cell2.innerHTML = '-';
	    	cell3.innerHTML = '${item.amount}';
	    	cell4.innerHTML = '${item.remarks}';
	    	cell5.innerHTML = '${item.job_sheet_no}';
	    	cell6.innerHTML = '${item.payment_for}';
	    	
			
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
					//txtinvoicedate.disabled=true;
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
			
			if('${param.table_id}'!='')
				{
					calculateTotal();
					txtpaymenttype.value='Paid';
					drppaymentmode.value='Cash';
					txtsearchcustomer.disabled=false;
					txtinvoicedate.disabled=false;
					document.getElementById("divTitle").innerHTML="Invoice For Table No:-"+tableNo;
					txtinvoicediscount.value=0;
				}
			
			if('${param.booking_id}'!='' || '${param.mobile_booking_id}'!='')
			{
				calculateTotal();
				txtpaymenttype.value='Paid';
				drppaymentmode.value='Cash';
				txtsearchcustomer.disabled=true;
				txtinvoicedate.disabled=false;
				document.getElementById("divTitle").innerHTML="Invoice For Booking Id - ${param.booking_id}";
				txtinvoicediscount.value=0;
			}
			
			document.getElementById("generatePDF").style="display:";
			generatePDF.disabled=false;
			btnRegister.disabled=false;
			
		}
		
		
		function deletePaymenttransaction(paymentId)
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
				  
				  window.location="?a=showTransactions&type=P";
			      
				  
				}
			  };
			  xhttp.open("GET","?a=deletePaymentTransaction&paymentid="+paymentId, true);    
			  xhttp.send();
		}
		/* function deletePayment()
		{
			var paymentId='${param.payment_id}';
			^
		} */
		
		
		
		
	</script>