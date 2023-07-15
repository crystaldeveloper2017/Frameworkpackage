
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:set var="requiredData" value='${requestScope["outputObject"].get("requiredData")}' />
<c:set var="customerMaster" value='${requestScope["outputObject"].get("customerMaster")}' />



<br>

<script>




function resetCustomer() {
	txtsearchcustomer.disabled = false;
	txtsearchcustomer.value = "";
	hdnSelectedCustomer.value = 0;
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
		} else {
			//searchForCustomer(searchString);
		}

	}


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
  
  document.getElementById("divTitle").innerHTML="Create New Enquiry";
  
  
 

function addEnquiry()
{	
	
	
	document.getElementById("frm").submit(); 
}  
 
 
</script>
<form id="frm" action="${masterUrl}?a=addEnquiry" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
<div class="card">
<br>
	
	<datalist id="customerList">
		<c:forEach items="${customerMaster}" var="customer">
			<option id="${customer.customerId}">${customer.customerName}</option>
		</c:forEach>
	</datalist>
	
	<div class="row">	
	<!-- /.card-header -->
	
	<div class="card-body table-responsive p-0" style="height: 580px;">                
		<table id="example1"class="table table-head-fixed  table-bordered table-striped dataTable dtr-inline" role="grid" aria-describedby="example1_info">             
		<head></head>
			<tbody>
			<tr align="center">
				<td>Existing Client</td>	
				<td colspan="6">			
					<div class="form-group">
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
						id="hdnSelectedCustomer" value="">
					 <input type="hidden"
						name="hdnSelectedCustomerType" id="hdnSelectedCustomerType"
						value="">
					</div>
			
			
				</td>  
			</tr>
			
			<tr align="center">
				<td>Message</td>
				<td colspan="6"><input type="text" class="form-control" id="txtmessage" value="${param.message} " ></td>  
			</tr>
			<tr align="center">
				<td></td>
				<td colspan="6"></td>  
			</tr>
			
			
			</tbody>
		</table>
		 <div class="col-sm-12" align="center">
		  	<div class="form-group">
		  	<button class="btn btn-success" type="button" id="btnsave"	onclick='addEnquiry()'>Save</button>
		 	<button class="btn btn-danger" type="reset" onclick='window.location="?a=showHomePage"'>Cancel</button>		     
		    </div>
 		 </div>
	</div>
	<!-- /.card-body -->
					
	</div>
</div>
            
 </form>           
            
            



<script>
 


  
</script>