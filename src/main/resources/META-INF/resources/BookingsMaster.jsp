






<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="message" value='${requestScope["outputObject"].get("ListOfBookings")}' />
<c:set var="todaysDate" value='${requestScope["outputObject"].get("todaysDate")}' />

<script>
function deleteBooking(bookingId)
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
	  xhttp.open("GET","?a=deleteBooking&bookingid="+bookingId, true);    
	  xhttp.send();
}

function EditBooking(bookingId,bookingName)
{
	
	
	document.getElementById("closebutton").style.display='none';
	   document.getElementById("loader").style.display='block';
	$('#myModal').modal({backdrop: 'static', keyboard: false});;
	
	var stringToPopulate='<table class="table table-bordered tablecss" border="3">';
	stringToPopulate+='<tr style="background-color:cornsilk;" align="center"><td colspan="2">Update Booking</td></tr>';
	stringToPopulate+='<tr><td>Booking Name </td> <td colspan="2"><input id="txtbookingnamepopup" placeholder="Booking Name" value="'+bookingName+'" class="form-control input-sm" id="inputsm" type="text"></td></tr>';
	stringToPopulate+="<tr align=\"center\"><td colspan=\"2\"><button class=\"btn btn-primary\" onclick=\"updatedbooking("+bookingId+")\">Update</button></td></tr>";
	stringToPopulate+='</table>';
	
	document.getElementById("responseText").innerHTML=stringToPopulate;
     document.getElementById("closebutton").style.display='block';
	   document.getElementById("loader").style.display='none';
	$('#myModal').modal({backdrop: 'static', keyboard: false});;

	
}

function updatedbooking(catId)
{
	var catName=document.getElementById('txtbookingnamepopup').value;
	document.getElementById("closebutton").style.display='none';
	document.getElementById("loader").style.display='block';
	document.getElementById("responseText").innerHTML="";
	$('#myModal').modal({backdrop: 'static', keyboard: false});;
	
	var xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() 
	  {
	    if (xhttp.readyState == 4 && xhttp.status == 200) 
	    { 		    	
	    	
	    	document.getElementById("responseText").innerHTML=xhttp.responseText;
		     document.getElementById("closebutton").style.display='block';
			   document.getElementById("loader").style.display='none';			  
		}
	  };
	  xhttp.open("GET","?a=updateBooking&bookingid="+catId+"&bookingName="+catName, true);    
	  xhttp.send();
	
	
}




function addBookingpopup()
{
	
	document.getElementById("closebutton").style.display='none';
	document.getElementById("loader").style.display='block';		 
	$('#myModal').modal({backdrop: 'static', keyboard: false});;		
	var stringToPopulate='<table class="table table-bordered tablecss" border="3">';
	stringToPopulate+='<tr style="background-color:cornsilk;" align="center"><td colspan="2">Add Booking</td></tr>';
	stringToPopulate+='<tr><td>Booking Name </td> <td colspan="2"><input id="txtbookingnamepopup" placeholder="Booking Name"  class="form-control input-sm" id="inputsm" type="text"></td></tr>';
	stringToPopulate+="<tr align=\"center\"><td colspan=\"2\"><button class=\"btn btn-primary\" onclick=\"addBooking()\">Add</button></td></tr>";
	stringToPopulate+='</table>';
	
	document.getElementById("responseText").innerHTML=stringToPopulate;
     document.getElementById("closebutton").style.display='block';
	   document.getElementById("loader").style.display='none';
	$('#myModal').modal({backdrop: 'static', keyboard: false});;
	
	
}

function addBooking()
{			
	
	var catName=document.getElementById('txtbookingnamepopup').value;
	var xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() 
	  {
	    if (xhttp.readyState == 4 && xhttp.status == 200) 
	    { 		    	
	    	
	    	document.getElementById("responseText").innerHTML=xhttp.responseText;
		     document.getElementById("closebutton").style.display='block';
			   document.getElementById("loader").style.display='none';			  
		}
	  };
	  xhttp.open("GET","?a=addBooking&bookingName="+catName, true);    
	  xhttp.send();
	
}



</script>	







<br>


<div class="col-sm-12">
		<div class="card" >
              
              <div class="card-body table-responsive p-0">
                <table class="table table-striped table-valign-middle">
                  <thead>
                  <tr>
                  <th>From Date<input type="text" id="txtfromdate" onchange="reloadData()" name="txtfromdate" readonly class="datepicker form-control form-control-sm" placeholder="From Date"/></th>
                  <th>To Date <input type="text" id="txttodate" onchange="reloadData()" name="txttodate" readonly class="datepicker form-control form-control-sm"  placeholder="To Date"/></th>
                  <th><input type="button"  class="btn btn-block btn-primary btn-sm" onclick="window.location='?a=showAddBooking'" value="Add New Booking" class="form-control float-right" ></th>
                  <th style="float:right" >                  
                  
                  	 <div class="input-group input-group-sm" >
                    <div class="icon-bar" style="font-size:22px;color:firebrick">
  <a title="Download Excel" onclick="downloadExcel()"><i class="fa fa-file-excel-o" aria-hidden="true"></i></a> 
  <a title="Download PDF" onclick="downloadPDF()"><i class="fa fa-file-pdf-o"></i></a>
  <a title="Download Text"  onclick="downloadText()"><i class="fa fa-file-text-o"></i></a>  
</div>           
                  </div>
                  </th>              
                  </tr>
                  </thead>
                  <tbody>
                  
                  
                  	
                  
                  
                  
                                    
                  </tbody>
                </table>
              </div>
            </div>
		</div>



<div class="card">









           
              
              
              
              
              
              
              <!-- /.card-header -->
              <div class="card-body table-responsive p-0" style="height: 580px;">                
                <table id="example1" class="table table-head-fixed  table-bordered table-striped dataTable dtr-inline" role="grid" aria-describedby="example1_info">
                  <thead>
                    <tr>
                     <th><b>Booking Id</b></th>
                     <th><b>Customer Name</b></th>
                     <th><b>From Date</b></th>
                     <th><b>To Date</b></th>
                     <th><b>Preffered Employee</b></th>                     
	                     <th></th>
	                     
	                 
                     
                    </tr>
                  </thead>
                  <tbody>
				<c:forEach items="${message}" var="item">
					<tr>
						<td>${item.booking_id}</td>
						<td>${item.customer_name}</td>
						<td>${item.FormattedFromDate }</td>
						<td>${item.FormattedToDate }</td>
						<td>${item.name}</td>
						<td >
												
							<c:if test="${item.status eq 'O'}">
								<button class="btn btn-danger" onclick="deleteBooking(${item.booking_id})">Delete</button>
								<button class="btn btn-primary" onclick="window.location='?a=showGenerateInvoice&editInvoice=Y&booking_id=${item.booking_id}'">Generate Invoice</button>							
							</c:if>
							
							<c:if test="${item.status eq 'S'}">
									<b>Served</b>
							</c:if>
						
						</td>
												
					</tr>
				</c:forEach>
				
				
                  </tbody>
                </table>
              </div>
              <!-- /.card-body -->
            </div>
            
            
            
            



<script>


$( function() 
		{
    $( "#txtfromdate" ).datepicker({ dateFormat: 'dd/mm/yy' });
    $( "#txttodate" ).datepicker({ dateFormat: 'dd/mm/yy' });
    
               
    
  } );
  
function reloadData()
{
	window.location="?a=showBookingsRegister&fromDate="+txtfromdate.value+"&toDate="+txttodate.value;
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
      "pageLength": 50
    });
  });
  
  document.getElementById("divTitle").innerHTML="Bookings Register";
  
  
  
  if('${param.fromDate}'!='')
	{
    txtfromdate.value='${param.fromDate}';
    txttodate.value='${param.toDate}';
	}
else
	{
	
	txtfromdate.value='${todaysDate}';
	txttodate.value='${todaysDate}';

	}
  
</script>