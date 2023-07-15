<style>
	.date_field {position: relative; z-index:1000;}
	.ui-datepicker{position: relative; z-index:1000!important;}
</style>


  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
           
           
           

<c:set var="message" value='${requestScope["outputObject"].get("ListOfEnquiries")}' />
<c:set var="ListOfCategories" value='${requestScope["outputObject"].get("ListOfCategories")}' />
<c:set var="allProducts" value='${requestScope["outputObject"].get("allProducts")}' />
<c:set var="ListOfQuotes" value='${requestScope["outputObject"].get("ListOfQuotes")}' />
<c:set var="fromDate" value='${requestScope["outputObject"].get("fromDate")}' />
<c:set var="toDate" value='${requestScope["outputObject"].get("toDate")}' />


   




<script>

 $(function () {
    
    $('#example1').DataTable({
      "paging": true,      
      "lengthChange": false,
      "searching": false,      
      "info": true,
      "autoWidth": false,
      "responsive": true,
      "pageLength": 50

    });
  });
 
 document.getElementById("divTitle").innerHTML="Showing Application Enquiries";


 

 
 function ReloadFilters()
 {	  
 	  window.location="?a=showEnquiries&txtfromdate="+txtfromdate.value+"&txttodate="+txttodate.value;  	  
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



$( document ).ready(function() {   
    
    $("#txtsearch").on('input', function ()  
    		{
    	alert("hie");
        var val = this.value;
        if($('#itemslist option').filter(function(){ 
            return this.value === val;         
        }).length) {     
            
            
            
            alert(arr[this.value]);
        }
    });
});
    



function discardEnquiry(id)
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
	  xhttp.open("GET","?a=deleteEnquiry&enquiryId="+id, true);    
	  xhttp.send();
}

function showRespondPopup(id,name,email,companyname,clientId)
{
	
	
	
	var strForResponse='<table class="table table-bordered tablecss"> <input class="form-control input-sm" type="hidden" id="uniqueEmailId" value="'+id+'" readonly></input> <input type="hidden" class="form-control " id="emailMessageContentPopup" value="" > ';
	
	
	
	strForResponse+="<tr>";	
		strForResponse+="<td>Choose From Existing Quote</td>";		
		strForResponse+='<td colspan="3"><select class="form-control input-sm" id="drpexistingquote" onchange="fetchValuesFromQuote(this)"> <option value="-1">------Select---------</option>';
		<c:forEach items="${ListOfQuotes}" var="item">
			strForResponse+="<option value='${item.quoteId}' >${item.quoteName}</option>";					
		</c:forEach>
		strForResponse+='</select></td>';		
	strForResponse+="</tr>";
	
	
	
	strForResponse+="<tr>";	
		strForResponse+="<td>Party Name</td>";
		strForResponse+='<td colspan="3"><input class="form-control input-sm" id="sendeeName" value="'+name+'"></input></td>';
	strForResponse+="</tr>";	
	
	
	strForResponse+="<tr>";		
		strForResponse+="<td>Company Name</td>";
		strForResponse+='<td colspan="3"><input class="form-control input-sm" id="txtcompanyname" value="'+companyname+'" ></input></td>';	
	strForResponse+="</tr>";	
	
	
		strForResponse+="<tr>";		
			strForResponse+="<td>To:-</td>";
			strForResponse+='<td><input class="form-control input-sm" id="emailtoSendPopup" value='+email+' ></input></td>';
			strForResponse+="<td>CC:-</td>";
			strForResponse+='<td><input class="form-control input-sm" id="CCemailtoSendPopup" value="deepakjani@ssegpl.com,maharshi@ssegpl.com" readonly></input></td>';			
		strForResponse+="</tr>";		
		
	
	strForResponse+="<tr>";		
		strForResponse+="<td colspan='1'>Other CC (use Comma to Seperate multiple emails)</td>";
		strForResponse+='<td colspan="3"><input class="form-control input-sm" id="CCotherPopup" value="" ></input></td>'		
	strForResponse+="</tr>";
	
	strForResponse+="<tr>";
		strForResponse+='<td colspan="1">Email Subject</td>';	
		strForResponse+='<td colspan="3"><input class="form-control input-sm" id="emailSubjectPopup" value="Response to your query (From SSEGPL)" /></td>';
	strForResponse+="</tr>";
	
	
	
	
	
	
	
	
	
	
	
	
	strForResponse+="<tr>";
		strForResponse+="<td>Search For Products</td>";		
		strForResponse+="<td colspan=3><input type='text' id='txtsearch' oninput='addMeToAvailableFromSearch(this)' class='form-control' list='itemslist'></td>";		
	strForResponse+="<tr>";
	
	strForResponse+="<tr>";
		strForResponse+="<td>Search By Product Code</td>";		
		strForResponse+="<td colspan=3><input type='text' id='txtsearchcode' oninput='addMeToAvailableFromSearch(this)' class='form-control' list='itemListByProdCode'></td>";		
	strForResponse+="<tr>";
	
	
		
strForResponse+="</tr>";
		
	
		strForResponse+="<tr>";		
			strForResponse+="<td >Available Products</td>";
			
				strForResponse+="<td colspan=3 align='center'><select class=\"form-control\" onchange='categorySelected(this.value)'>";
				
				strForResponse+="<option value='-1' selected>select</option>";
				
				<c:forEach items="${ListOfCategories}" var="item">
					strForResponse+="<option value='${item.categoryId}' >${item.categoryName}</option>";					
				</c:forEach>			
				
				strForResponse+="</select> <br>";
				
				strForResponse+="<select class=\"form-control\" style='display:none'  multiple id='availableproducstId'></select>"
					

				
		strForResponse+="<button class='btn' style='margin-top:5px;display:none;background:lightblue;color:black' onclick='showProductDetailsAsReadOnly()' id='btndetails'>Details</button> ";
		strForResponse+="<button class='btn' style='margin-top:5px;display:none;background:lightblue;color:black' onclick='addmeToSelected()' id='btnadd'>Add To Selected</button>  </td> </tr>";
	
		
		
		strForResponse+="<tr>";		
		strForResponse+="<td>Selected products to Send</td>";		
			strForResponse+="<td colspan=3><select class=\"form-control\" onchange='removeMe(this)'  multiple id='selectedproductsid'></select> </td>";
			
	strForResponse+="</tr>";
	
	
	
	
	strForResponse+="<tr align='center'>";		
	strForResponse+="<td>Type of Quote</td>";		
		strForResponse+='<td colspan=3>   <label><input type="radio" name="optquoteType" id="radioDomestic" value="0">Domestic</label>   <label><input type="radio" value="1" id="radioInternational" name="optquoteType">International</label></td>';		
	strForResponse+="</tr>";
	
	strForResponse+="<tr align='center'>";		
	strForResponse+="<td>Discount (%) </td>";		
		strForResponse+='<td><input type="number" class="form-control input-sm" id="txtdiscount" value="0" ></input></td>';
		strForResponse+="<td>Surcharge (%) </td>";		
		strForResponse+='<td><input type="number" class="form-control input-sm" id="txtsurcharge" value="0" ></input></td>';
	strForResponse+="</tr>";
	
	
	
	
	strForResponse+="<tr align='center'>";		
	strForResponse+="<td >Remarks</td>";		
		strForResponse+='<td colspan=3><input type="text" class="form-control input-sm" id="txtremarks" value="" ></input><input type="hidden" value='+clientId+' id="txtclientid">  </td>';		
	strForResponse+="</tr>";
	
	
	strForResponse+='<tr align="center">';
		strForResponse+='<td colspan=4><button style="margin:5px" type="button"  onclick="PreviewQuote()"  class="btn btn-info" >Preview</button></td>';						
	strForResponse+="</tr>";
		
	strForResponse+="</table>";
	
	
	document.getElementById("responseText").innerHTML=strForResponse;
	  //document.getElementById("closebutton").style.display='block';
	  document.getElementById("loader").style.display='none';
	  document.getElementById("modalDialogId").style='max-width:1200px;';	  
	  $('#myModal').modal({backdrop: 'static', keyboard: false});;
	
}
</script>	

</head>




<form id="TheForm" method="post" action="?a=ShowQuotePreview" target="TheWindow">
<input type="hidden" name="productIds" id="productIds" value="" />
<input type="hidden" name="internationalFlag" id="internationalFlag" value="something" />
<input type="hidden" name="emailTosend" id="emailTosend" value="" />
<input type="hidden" name="CCemailTosend" id="CCemailTosend" value="" />
<input type="hidden" name="CCother" id="CCother" value="" />
<input type="hidden" name="emailMessageContent" id="emailMessageContent" value="" />
<input type="hidden" name="emailSubject" id="emailSubject" value="" />


<input type="hidden" name="companyNameForm" id="companyNameForm" value="" />
<input type="hidden" name="hdnClientId" id="hdnClientId" value="" />


<input type="hidden" name="sendeeNameForm" id="sendeeNameForm" value="" />
<input type="hidden" name="txtdiscountForm" id="txtdiscountForm" value="" />
<input type="hidden" name="txtsurchargeForm" id="txtsurchargeForm" value="" />
<input type="hidden" name="txtremarksForm" id="txtremarksForm" value="" />
<input type="hidden" name="mailFlag"  value="${param.mailFlag}" />



<input type="hidden" name="uniqueEmailIdForm" id="uniqueEmailIdForm" value="" />

</form>


	
	


<div class="card">
<br>


	<div class="row">
	
		<datalist id="customerList">
			<c:forEach items="${customerMaster}" var="customer">
				<option id="${customer.customerId}">${customer.customerName}</option>			    
			</c:forEach>	   	   	
		</datalist>
	            
		<div class="col-sm-1" align="center">
			<label for="txtfromdate">From Date</label>
		</div>
	
		<div class="col-sm-2" align="center">
			<div class="input-group input-group-sm" style="width: 200px;">
				<input type="text" id="txtfromdate" onchange="checkforvalidfromtodate();ReloadFilters();"  name="txtfromdate" readonly class="form-control date_field" placeholder="From Date"/>
			</div>
		</div>
			
		<div class="col-sm-1" align="center">
			<label for="txttodate">To Date</label>
		</div>
	
		<div class="col-sm-2" align="center">
			<div class="input-group input-group-sm" style="width: 200px;">
				<input type="text" id="txttodate"  onchange="checkforvalidfromtodate();ReloadFilters();"    name="txttodate" readonly class="form-control date_field"  placeholder="To Date"/>
			</div>
		</div>
		
		
		<input  type="hidden" name="customerId" id="customerId" value="">
		
		<div class="col-sm-2" align="center">
			<div class="card-tools">
				<div class="input-group input-group-sm" align="center" style="width: 200px;display:inherit">
					<div class="icon-bar" style="font-size:22px;color:firebrick">
						<a title="Download Excel" onclick="downloadExcel()"><i class="fa fa-file-excel-o" aria-hidden="true"></i></a> 
						<a title="Download PDF" onclick="exportPDFForLedger()"><i class="fa fa-file-pdf-o"></i></a>
						<a title="Download Text"  onclick="downloadText()"><i class="fa fa-file-text-o"></i></a>  
					</div>           
				</div>
			</div>
		</div>
	
	</div>
	
	
	<!-- /.card-header -->
	
	<div class="card-body table-responsive p-0" style="height: 580px;">                
		<table id="example1"class="table table-head-fixed  table-bordered table-striped dataTable dtr-inline" role="grid" aria-describedby="example1_info">             
			
			<thead>
				<tr>
					<th><b>Enquiry No</b></th>
					<th><b>Message</b></th>
					<th><b>Received Date</b></th>
					<th></th><th></th><th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${message}" var="item">
					<tr>
						<td>${item.enquiryId}</td>
						<td>${item.message}</td>
						<td>${item.receivedDate}</td> 
						
						<td>
							<c:if test="${item.clientId ne null}">
								<b><button style="margin:5px" type="button" onclick="showRespondPopup('${item.enquiryId}','${item.name}','${item.email}','${item.companyName}','${item.clientId }')" class="btn btn-success" >Generate Quote</button></b>	
							</c:if>
						</td>
						<td>
							<c:if test="${item.clientId eq null}">
								<b><button style="margin:5px" type="button" onclick="addEnquiry('${item.name}','${item.email}','${item.contact}','${item.message}')" class="btn btn-success" >Add to Application Enquiry</button></b>	
							</c:if>
						</td>
						<td>
							<c:if test="${item.clientId ne null}">
								<b><button style="margin:5px" type="button" onclick="discardEnquiry('${item.enquiryId}')" class="btn btn-danger" >Discard</button></b>
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>






<script>
 txtfromdate.value='${fromDate}';
	 txttodate.value='${toDate}';
	 
	  $( "#txtfromdate" ).datepicker({ dateFormat: 'dd/mm/yy' });
	  $( "#txttodate" ).datepicker({ dateFormat: 'dd/mm/yy' });

	 
</script>