<style>
	.date_field {position: relative; z-index:1000;}
	.ui-datepicker{position: relative; z-index:1000!important;}
</style>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>



<script>
function deleteVisitor(visitorId)
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
	  
	  xhttp.open("GET","?a=deleteVisitor&visitorId="+visitorId, true);    
	  xhttp.send();
}


function checkoutVisitor(visitorId)
{
	
	
	

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
	  
	  xhttp.open("GET","?a=checkoutVisitor&visitorId="+visitorId, true);    
	  xhttp.send();
}

``// can you point me to the service from where this variable is coming ?
</script>

<c:set var="message" value='${requestScope["outputObject"].get("ListOfVisitors")}' />
<c:set var="txtfromdate" value='${requestScope["outputObject"].get("txtfromdate")}' />
<c:set var="txttodate" value='${requestScope["outputObject"].get("txttodate")}' />

<br>
<div class="card">

		
	
		     <div class="card-header">    
                
                
              
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
                     <th><b>Visitor Id</b></th>
                     <th><b>Name</b></th>
                     <th><b>Purpose Of Visit</b></th>
                     <th><b>Mobile No</b></th>
                     <th><b>Email</b></th>
                      <th><b>Contact To Employee</b></th>
                     <th><b>Checkin Time</b></th>
                     <th><b>Checkout Time</b></th>
					 <th><b>Image</b></th>
					  <th><b>Remarks</b></th>
					 
					 
                     </th>
                    </tr>
                  </thead>
                  <tbody>
				<c:forEach items="${message}" var="item">
					<tr >
						<td>${item.visitor_id}</td>
						<td>${item.visitor_name}</td>
						<td>${item.purpose_of_visit}</td>
						<td>${item.mobile_no}</td>
						<td>${item.email_id}</td>
						<td>${item.name}</td>
						<td>${item.checkin_time}</td>
						<td>${item.checkout_time}</td>
						
						<td>
						

						<c:forTokens items="${item.attachmentIds}" delims="," var="mySplit">
   							
							<img onclick="getVisitorImage('${mySplit}')" src="BufferedImagesFolder/dummyImage.jpg" height="30px" width="30px">
						</c:forTokens>


						
						</td>
						<td>${item.remarks}</td>
						
						
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
      "pageLength": 100,
      "order": [[ 0, "desc" ]]

    });
  });
  
  document.getElementById("divTitle").innerHTML="Visitors Inside";
  document.title +=" Visitors Inside ";
  
  $( "#txtfromdate" ).datepicker({ dateFormat: 'dd/mm/yy' });
  $( "#txttodate" ).datepicker({ dateFormat: 'dd/mm/yy' });
  
  txtfromdate.value='${txtfromdate}';
  txttodate.value='${txttodate}';
  
  function exportPDFForLedger()
  {
	  
	  window.open("?a=exportVisitorEntryAsPDF&fromDate="+txtfromdate.value+"&toDate="+txttodate.value);
		return;

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
  	  window.location="?a=showVisitorsInside&txtfromdate="+txtfromdate.value+"&txttodate="+txttodate.value;  	  
  }
  
  function searchprod(elementInput,evnt)
	{
		if(evnt.which==13)
			{
				// do some search stuff
				window.location="?a=showVisitorsInside&colNames="+document.getElementById("colNames").value+"&searchInput="+elementInput.value;
			}
			
	}

function getVisitorImage(attachment_id)
{
	$.get("?a=getFileFromDbToBuffer&attachment_id="+attachment_id, function(data, status){
    window.open("BufferedImagesFolder/"+data);
  });
}

  
</script>