<style>
	.date_field {position: relative; z-index:1000;}
	.ui-datepicker{position: relative; z-index:1000!important;}
</style>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
	




<c:set var="listOfEmployees" value='${requestScope["outputObject"].get("EmployeeList")}' />

<c:set var="message" value='${requestScope["outputObject"].get("ListOfEmployees")}' />
<c:set var="txtfromdate" value='${requestScope["outputObject"].get("txtfromdate")}' />
<c:set var="txttodate" value='${requestScope["outputObject"].get("txttodate")}' />
<c:set var="fromDate" value='${requestScope["outputObject"].get("fromDate")}' />
<c:set var="toDate" value='${requestScope["outputObject"].get("toDate")}' />


<datalist id="listOfEmployee">
<c:forEach items="${listOfEmployees}" var="cat">
 <option id="${cat.user_id}">${cat.name} </option>	
 </c:forEach>
</datalist>
<br>


<br>
<div class="card">



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
                            
	<div class="col-sm-3">
	<div class="form-group">
		
	   
	<div class="input-group input-group-sm">

	<input type="textbox" name="ContactToEmployee" id="ContactToEmployee" class="form-control form-control-sm" list="listOfEmployee" onchange="checkforMatchEmployee()"/> 
	<input type="hidden" name="hdnselectedemployee" id="hdnselectedemployee" value="">  <span class="input-group-append">
		<button type="button" class="btn btn-danger btn-flat" onclick="resetEmployee()">Reset</button>
		</span>
	</div>
	</div>
		</div>
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
              
       
              
              
              
              <!-- /.card-header -->
              <div class="card-body table-responsive p-0" style="height: 800px;">                
                <table id="example1"class="table table-head-fixed  table-bordered table-striped dataTable dtr-inline" role="grid" aria-describedby="example1_info">
                  <thead>
                    <tr>
                     
                     <th><b>Employee Name</b></th>
                     <th><b>Updated By</b></th>
                     <th><b>Reason</b></th>
					  <th><b>Remark</b></th>
                     <th><b>From Date</b></th>
                       <th><b>To Date</b></th>
					    <th><b>Updated Date</b></th>
					   
                     
                    </tr>
                  </thead>
                  <tbody>
				<c:forEach items="${message}" var="item">
					<tr >
						<td>${item.EmployeeName}</td>
						<td>${item.SuperVisorName}</td>
						
						<td>${item.reason}</td>		
						<td>${item.remark}</td>						
						<td>${item.FormattedFromDate}</td>
            <td>${item.FormattedToDate}</td>
			<td>${item.updated_date}
			
            <td><button class="btn btn-danger" onclick="deleteLeave(${item.leave_id})">Delete</button></td>
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
      "ordering": false,
      "info": true,
      "autoWidth": false,
      "responsive": true,
      "pageLength": 100
    });
  });
  
  
  $( "#txtfromdate" ).datepicker({ dateFormat: 'dd/mm/yy' });
  $( "#txttodate" ).datepicker({ dateFormat: 'dd/mm/yy' });



  txtfromdate.value='${txtfromdate}';
  txttodate.value='${txttodate}';
  
  document.getElementById("divTitle").innerHTML="Leave Register";
  
  
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
  	  		window.location="?a=showLeaveRegister&txtfromdate="+txtfromdate.value+"&txttodate="+txttodate.value+"&emp_id="+hdnselectedemployee.value;
		  
  }

  
function deleteLeave(leaveId)
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
	  xhttp.open("GET","?a=deleteLeave&leaveId="+leaveId, true);    
	  xhttp.send();
}

function checkforMatchEmployee()
{
	var searchString= document.getElementById("ContactToEmployee").value;
	
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
			document.getElementById("ContactToEmployee").disabled=true;	
          ReloadFilters();

		}
	else
		{
			//searchForCustomer(searchString);
		}
	
	
}

function resetEmployee()
{	
	ContactToEmployee.disabled=false;
	ContactToEmployee.value="";
	hdnselectedemployee.value=0;
}

  
</script>