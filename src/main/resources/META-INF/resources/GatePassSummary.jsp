<style>
	.date_field {position: relative; z-index:1000;}
	.ui-datepicker{position: relative; z-index:1000!important;}
</style>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:set var="txtfromdate" value='${requestScope["outputObject"].get("txtfromdate")}' />
<c:set var="txttodate" value='${requestScope["outputObject"].get("txttodate")}' />
<c:set var="lstGatepassRegister" value='${requestScope["outputObject"].get("lstGatepassRegister")}' />
<c:set var="listOfEmployees" value='${requestScope["outputObject"].get("EmployeeList")}' />
<c:set var="employeeDetails" value='${requestScope["outputObject"].get("employeeDetails")}' />



<datalist id="listOfEmployee">
<c:forEach items="${listOfEmployees}" var="cat">
 <option id="${cat.user_id}">${cat.name} </option>	
 </c:forEach>
</datalist>

<br>

 <div class="card">
 <br>
 <div class="row">
 
		
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
		
		  <div class="col-sm-1" align="center">
	
			<label for="email">Employee Name</label>
		</div>

       <div class="col-sm-3" align="center">
			<div class="input-group input-group-sm" style="width: 200px;">
				 <input type="textbox" class="form-control form-control-sm" id="employeename" value="${gatepassDetails.name}"  list="listOfEmployee" name="employeename" value="${gatepassDetails.name}" onchange="checkforMatchEmployee()" />
	  	<input type="hidden" name="hdnselectedemployee" id="hdnselectedemployee" value="${gatepassDetails.user_id}"> 
       <span class="input-group-append">
	<button type="button" class="btn btn-danger btn-flat" onclick="resetEmployee()">Reset</button>
		</span>
		    </div>
  </div>


		<input  type="hidden" name="customerId" id="customerId" value="">
		
		
		
		
		<div class="col-sm-2" align="center">
			<div class="card-tools">
				<div class="input-group input-group-sm" align="center" style="width: 200px;display:inherit">
					<div class="icon-bar" style="font-size:22px;color:firebrick">
						<a title="Download Excel" onclick="downloadExcel()"><i class="fa fa-file-excel-o" aria-hidden="true"></i></a> 
 						<a title="Download PDF" onclick="exportSalesRegister2()"><i class="fa fa-file-pdf-o"></i></a>
  						<a title="Download Text"  onclick="downloadText()"><i class="fa fa-file-text-o"></i></a>  
					</div>           
				</div>
			</div>
		</div>

    
      
	</div>

	<br>

              
          
     
                
              
              
              <!-- /.card-header -->
              <div class="card-body table-responsive p-0" style="height: 800px;">                
                <table id="example1"class="table table-head-fixed  table-bordered table-striped dataTable dtr-inline" role="grid" aria-describedby="example1_info">
                <thead>
                 
                    <tr>
                    <th><b>Sr No</b></th>
                    <th><b>Gate Pass No</b></th>                  
                    <th><b>Date</b></th>
                    <th><b>Employee Name</b></th>
                    <th><b>Request By</b>
                    <th><b>Approved By</b>
                    <th><b>Reason</b>
                    <th><b>Out Time</b></th>
                    <th><b>In Time</b></th>
                    <th><b>Hours</b></th>
                    
                    </tr>
                  </thead>
                  <tbody>
				<c:forEach items="${lstGatepassRegister}" var="item" varStatus="status">
					<tr >
                  
					  
					  
					   <td>${status.index + 1}</td>
					  <td>${item.pass_no} </td>
					  <td>${item.gate_pass_date}</td>
            <td>${item.name}</td>
					  <td>${item.requesRaisedBy}</td>
					  <td>${item.requestApprovedBy}</td>
					  <td>${item.reason}</td>
            <td>${item.out_time}</td>
            <td>${item.in_time}</td>
            <td>${item.Hour1}</td>
            
					  
		  			
					</tr>
				</c:forEach>
				
				
                  </tbody>
                </table>
              </div>
              <!-- /.card-body -->
             
</div>
            
            
       
            
   

<script>


function deleteGatepass(gate_pass_id)
{
	
	var answer = window.confirm("Are you sure you want to delete ?");
	if (!answer) 
	{
		return;    
	}
	
	  

	var xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() 
	  {
	    if (xhttp.readyState == 4 && xhttp.status == 200) 
	    { 		      
	      
		  toastr["success"](xhttp.responseText);
	    	toastr.options = {"closeButton": false,"debug": false,"newestOnTop": false,"progressBar": false,
	    	  "positionClass": "toast-top-right","preventDuplicates": false,"onclick": null,"showDuration": "1000",
	    	  "hideDuration": "500","timeOut": "500","extendedTimeOut": "500","showEasing": "swing","hideEasing": "linear",
	    	  "showMethod": "fadeIn","hideMethod": "fadeOut"}
	    	
	    	window.location.reload();
	      
		  
		}
	  };
	  xhttp.open("GET","?a=deleteGatepass&gate_pass_id="+gate_pass_id, true);    
	  xhttp.send();
}

function approveGatepass(gate_pass_id)
{
	
	var answer = window.confirm("Are you sure you want to approve ?");
	if (!answer) 
	{
		return;    
	}
	
	  

	var xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() 
	  {
	    if (xhttp.readyState == 4 && xhttp.status == 200) 
	    { 		      
	      
		  toastr["success"](xhttp.responseText);
	    	toastr.options = {"closeButton": false,"debug": false,"newestOnTop": false,"progressBar": false,
	    	  "positionClass": "toast-top-right","preventDuplicates": false,"onclick": null,"showDuration": "1000",
	    	  "hideDuration": "500","timeOut": "500","extendedTimeOut": "500","showEasing": "swing","hideEasing": "linear",
	    	  "showMethod": "fadeIn","hideMethod": "fadeOut"}
	    	
	    	window.location.reload();
	      
		  
		}
	  };
	  xhttp.open("GET","?a=approveGatepass&gate_pass_id="+gate_pass_id, true);    
	  xhttp.send();
}


    function checkforMatchEmployee()
  {
    var searchString= document.getElementById("employeename").value;
    
    var options1=document.getElementById("listOfEmployee").options;
    var employeeId=0;
    for(var x=0;x<options1.length;x++)
      {
        if(searchString==options1[x].value)
          {
            employeeId=options1[x].id;
            break;
          }
      }
    if(employeeId!=0)
      {
        document.getElementById("hdnselectedemployee").value=employeeId;			
        document.getElementById("employeename").disabled=true;
        ReloadFilters();			
      }
    else
      {
        //searchForCustomer(searchString);
      }
    
    
  }


function resetEmployee()
{	
	employeename.readOnly=false;
	employeename.value="";
	hdnselectedemployee.value='';
  ReloadFilters();
}




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
	   "order": [[ 1, "asc" ]]

    });
  });


  txtfromdate.value='${txtfromdate}';
  txttodate.value='${txttodate}';

  function exportSalesRegister2()
  {
	try
	{
	
		window.open("?a=exportSalesRegister2&txtfromdate="+txtfromdate.value+"&txttodate="+txttodate.value+"&customerId="+hdnSelectedCustomer.value);
		return;
		
	  var xhttp = new XMLHttpRequest();
		  xhttp.onreadystatechange = function() 
		  {
		    if (xhttp.readyState == 4 && xhttp.status == 200) 
		    { 		      
		    	//window.location="BufferedImagesFolder/"+xhttp.responseText;
		    	window.open("BufferedImagesFolder/"+xhttp.responseText,'_blank','height=500,width=500,status=no, toolbar=no,menubar=no,location=no');
			}
		  };
		  xhttp.open("GET","?a=exportSalesRegister2", true);    
		  xhttp.send();
	}
	catch(ex)
	{
		alert(ex.message);
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
  
  function ReloadFilters()
  {	 	  
  	  		window.location="?a=showGatePassSummary&txtfromdate="+txtfromdate.value+"&txttodate="+txttodate.value+"&empId="+hdnselectedemployee.value;
		  
  }
  
  
  
  function checkforLengthAndEnableDisable()
  {
  		if(txtsearchcustomer.value.length>=3)
  			{
  				txtsearchcustomer.setAttribute("list", "hdnSelectedCustomer");
  			}
  		else
  			{
  				txtsearchcustomer.setAttribute("list", "");
  			}
  }
  
  function resetCustomer()
  {	
  	txtsearchcustomer.disabled=false;
  	txtsearchcustomer.value="";
  	hdnSelectedCustomer.value="";  	
  	ReloadFilters();
  	
  }
  
  document.getElementById("divTitle").innerHTML=" Gate Pass Summary ";
  document.title +=" Gate Pass Summary ";
  
  $( "#txtfromdate" ).datepicker({ dateFormat: 'dd/mm/yy' });
  $( "#txttodate" ).datepicker({ dateFormat: 'dd/mm/yy' });
  
  
  function openPRNo(qtName)
	{
		
		var xhttp = new XMLHttpRequest();
		  xhttp.onreadystatechange = function() 
		  {
		    if (xhttp.readyState == 4 && xhttp.status == 200) 
		    { 		      
		    	//alert(xhttp.responseText);
		    	window.open("BufferedImagesFolder/"+xhttp.responseText);		  
			}
		  };
		  xhttp.open("GET","?a=generatePRNoPDF&PRNo="+qtName, false);    
		  xhttp.send();
		
		
		
		//window.open("BufferedImagesFolder/"+qtName);			
	}

if('${param.empId}'!='')
{
    hdnselectedemployee.value='${param.empId}';
    employeename.value='${employeeDetails.name}';
    employeename.readOnly=true;
}

</script> 