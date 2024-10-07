<style>
	.date_field {position: relative; z-index:1000;}
	.ui-datepicker{position: relative; z-index:1000!important;}
</style>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  


<c:set var="reqList" value='${requestScope["outputObject"].get("reqList")}' />
<c:set var="txtfromdate" value='${requestScope["outputObject"].get("txtfromdate")}' />
<c:set var="txttodate" value='${requestScope["outputObject"].get("txttodate")}' />
<c:set var="empDetails" value='${requestScope["outputObject"].get("empDetails")}' />
<c:set var="ListOfEmployees" value='${requestScope["outputObject"].get("ListOfEmployees")}' />
<c:set var="empDetails" value='${requestScope["outputObject"].get("empDetails")}' />





<datalist id="ListOfEmployees">
<c:forEach items="${ListOfEmployees}" var="cat">
 <option id="${cat.user_id}">${cat.name} ~ ${cat.qr_code}</option>	
 </c:forEach>
</datalist>




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
							<input type="text" class="form-control form-control-sm"
								id="EmployeeName" placeholder="Search For Employee Name"
								name="EmployeeName" autocomplete="off" list='ListOfEmployees' 
								oninput="checkforMatchEmployee()"> <span
								class="input-group-append">
								<button type="button" class="btn btn-danger btn-flat"
									onclick="resetEmployee()">Reset</button>
							</span>

							
						<input type="hidden" name="hdnselectedemployee"
							id="hdnselectedemployee" value=""> 

							</div>
				</div>
					</div>
		
		<div class="col-sm-2" align="center">
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
	<br>
	
              
              <!-- /.card-header -->
              <div class="card-body table-responsive p-0" style="height: 800px;">                
                <table id="example1"class="table table-head-fixed  table-bordered table-striped dataTable dtr-inline" role="grid" aria-describedby="example1_info">
                  <thead>
                    <tr>
                     
                     <th><b>Employee Name</b></th>
                     <th><b>Emp Code</b></th>
                     
                     <th><b>Check Time</b></th>
                     <th><b> Check Out Time</b></th>
                     	
                     
                     
	
                    </tr>
                  </thead>
                  <tbody>
				<c:forEach items="${reqList}" var="item">
					<tr >
						<td>${item.name}</td>
						<td>${item.qr_code}</td>
						
						<td>${item.checked_in_time}</td>
						<td>${item.checked_out_time}</td>
						
						
						<%-- <td><a href="?a=showAttendanceRegister=${item.user_id}"></td> --%>
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
  
  document.getElementById("divTitle").innerHTML="Attendance Register";
  
  
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
  	  		window.location="?a=showAttendanceRegister&txtfromdate="+txtfromdate.value+"&txttodate="+txttodate.value+"&searchString="+hdnselectedemployee.value;
		  
  }
  

 function checkforMatchEmployee()
{
	var searchString= document.getElementById("EmployeeName").value;
	
	var options1=document.getElementById("ListOfEmployees").options;
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
			document.getElementById("EmployeeName").disabled=true;		
			ReloadFilters();				
		}
	else
		{
			//searchForCustomer(searchString);
		}
	
	
}


function resetEmployee()
{	
	EmployeeName.readOnly=false;
	EmployeeName.value="";
	hdnselectedemployee.value='';
	ReloadFilters();
}

if('${param.searchString}'!='')
{
	EmployeeName.value='${empDetails.name}';
	EmployeeName.readOnly=true;
}


</script>