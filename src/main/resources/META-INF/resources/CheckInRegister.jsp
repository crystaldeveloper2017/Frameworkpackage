<style>
	.date_field {position: relative; z-index:1000;}
	.ui-datepicker{position: relative; z-index:1000!important;}
</style>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:set var="message" value='${requestScope["outputObject"].get("listReturnData")}' />

<c:set var="userList" value='${requestScope["outputObject"].get("userList")}' />


<c:set var="txtfromdate" value='${requestScope["outputObject"].get("txtfromdate")}' />
<c:set var="txttodate" value='${requestScope["outputObject"].get("txttodate")}' />
<c:set var="isAdmin" value='${requestScope["outputObject"].get("isAdmin")}' />
<c:set var="drpuser" value='${requestScope["outputObject"].get("drpuser")}' />






<br>
<div class="card">


	<br>
	
<div class="row">
              
              
              
              
              
              
              
               
              
              
				<div class="col-sm-3" align="center">
					<div class="input-group input-group-sm" style="width: 200px;">
  						<input type="text" id="txtfromdate" onchange="checkforvalidfromtodate();ReloadFilters();"  name="txtfromdate" readonly class="form-control date_field" placeholder="From Date"/>
                  </div>
				</div>
				
				
				
				
				<div class="col-sm-3" align="center">
					<div class="input-group input-group-sm" style="width: 200px;">
					<input type="text" id="txttodate"  onchange="checkforvalidfromtodate();ReloadFilters();"    name="txttodate" readonly class="form-control date_field"  placeholder="To Date"/>
  						
                    </div>
				</div>
				
				
				
				
				
				<c:if test="${isAdmin eq true}">
				
				<div class="col-sm-3" align="center">
					<div class="input-group input-group-sm" style="width: 200px;">
					
					
					<select class='form-control form-control-sm' id="drpuser" onchange="ReloadFilters();">
    	<option value="-1">--Select--</option>
		<c:forEach items="${userList}" var="user">
  					<option value="${user.user_id}">${user.name }</option>
  		</c:forEach>
	</select>
					
					
  						
                    </div>
				</div>
				</c:if>
				
				
				
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
                <table id="example1"class="table table-head-fixed  table-bordered table-striped dataTable dtr-inline" role="grid" aria-describedby="example1_info">
                  <thead>
                    <tr>
                     <th><b>Check In Id</b></th>
                     <th><b>User Name</b></th>
                     <th><b>Check In Type</b></th>
                     <th><b>Time Stamp</b></th>
                     <th><b>Location</b></th>
                     <th><b>Remarks</b></th>
                     
                     
                     <th><b></b></th>
                    </tr>
                  </thead>
                  <tbody>
				<c:forEach items="${message}" var="item">
					<tr >
						
						<td>${item.check_in_id}</td>	
						<td>${item.name}</td>
						
						
									<td>
						
						<c:if test="${item.check_in_type eq 'I'}">							  				
							Check In			  									  
						</c:if>
						
						
						<c:if test="${item.check_in_type eq 'O'}">							  				
							Check Out			  									  
						</c:if>
												
						</td>
						
												<td>${item.checked_time}</td>
						
						
						<td>
						<a href="#" onclick='openLocation(${check.latitude},${check.longitude})'>
								${item.latitude} ${item.longitude}								
							</a>
						</td>
						
						
						<td>${item.remarks}</td>
						<td>${item.firm_name}</td>
						
						
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
      "pageLength": 50
    });
  });
  
  
  
  
  	document.getElementById("divTitle").innerHTML="Check In Register";
  
  
  
  
  
  $( "#txtfromdate" ).datepicker({ dateFormat: 'dd/mm/yy' });
  $( "#txttodate" ).datepicker({ dateFormat: 'dd/mm/yy' });
  
  
  
    
  
  
  
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
	  window.location="?a=showCheckInRegister&txtfromdate="+txtfromdate.value+"&txttodate="+txttodate.value+"&drpuser="+drpuser.value;
	  
  }
  
  txtfromdate.value='${txtfromdate}';
  txttodate.value='${txttodate}';
  drpuser.value='${drpuser}';
  
  
  
  
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