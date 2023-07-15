<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script>
function deleteClient(clientId)
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
	  xhttp.open("GET","?a=deleteClient&clientId="+clientId, true);    
	  xhttp.send();
}




</script>	



<c:set var="ListOfClients" value='${requestScope["outputObject"].get("ListOfClients")}' />




<br>

<div class="card">

<br>




<div class="row">
<div class="col-sm-3" align="center">
	
</div>

<div class="col-sm-3" align="center">
	<div class="input-group input-group-sm" style="width: 200px;">
                    <input type="text" name="txtsearch" id="txtsearch" class="form-control float-right" placeholder="Search" onkeypress="searchprod(event)">                    

                    <div class="input-group-append">
                      <button type="button" class="btn btn-default" onclick='actualSearch()'><i class="fas fa-search"></i></button>
                    </div>
                  </div>
</div>

<div class="col-sm-3" align="center">
	<div class="icon-bar" style="font-size:22px;color:firebrick">
	  <a title="Download Excel" onclick="downloadExcel()"><i class="fa fa-file-excel-o" aria-hidden="true"></i></a> 
	  <a title="Download PDF" onclick="downloadPDF()"><i class="fa fa-file-pdf-o"></i></a>
	  <a title="Download Text"  onclick="downloadText()"><i class="fa fa-file-text-o"></i></a>  
	</div>
</div>

<div class="col-sm-3" align="center">
	<input type="button"  style="width:50%" class="btn btn-block btn-primary btn-sm" onclick="window.location='?a=showAddClient'" value="Add New Client" >
</div>


</div>


                    
              
              
              
              
              <!-- /.card-header -->
              <div class="card-body table-responsive p-0" style="height: 580px;">                
                <table id="example1"class="table table-head-fixed  table-bordered table-striped dataTable dtr-inline" role="grid" aria-describedby="example1_info">
                  <thead>
                    <tr>
                     <th><b>Client Id</b></th><th><b>Name Of Establishment</b></th><th><b>Address</b></th>
                     <th><b>Date of Setup</b></th>
                     <th><b>Company Pan</b></th>
                     <th><b>GST NO</b></th>
                     <th><b>Contact Person Name</b></th>
                     
                     
                     <th></th>
                     <th></th>
                     
                    </tr>
                  </thead>
                  <tbody>
				<c:forEach items="${ListOfClients}" var="client">
					<tr >
					
						<td>${client.client_id}</td><td>${client.client_name}</td>
						<td>${client.address_of_the_establishment}</td>
						<td>${client.FormattedToDate}</td>
						<td>${client.company_pan}</td>
						<td>${client.gst_no}</td>
						<td>${client.contact_person_name}</td>
											
						
						
						
						<td><a href="?a=showAddClient&clientId=${client.client_id}">Edit</a></td><td><button class="btn btn-danger" onclick="deleteClient(${client.client_id})">Delete</button></td>
						
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
      "pageLength": 50,
      "order": [[ 1, "asc" ]]
    });
  });
  
  document.getElementById("divTitle").innerHTML="Client Master";
  
  function actualSearch()
  {
	  window.location="?a=showClientMaster&searchInput="+txtsearch.value;  
  }
  
  function searchprod(evnt)
	{
		if(evnt.which==13)
			{
				// do some search stuff
				actualSearch();					
			}
			
	}
  
  function showThisCategory()
  {
	  window.location="?a=showServiceMaster&searchInput="+txtsearch.value
	  		  +"&categoryId="+drpcategoryId.value;
	  
  }
  
  drpcategoryId.value='${param.categoryId}';
  
</script>