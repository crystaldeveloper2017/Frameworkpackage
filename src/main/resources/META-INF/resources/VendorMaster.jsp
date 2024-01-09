<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script>
function deleteVendor(vendorId)
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
 	xhttp.open("GET","?a=deleteVendor&vendorId="+vendorId, true);    
  	xhttp.send();
}

</script>


<c:set var="message" value='${requestScope["outputObject"].get("ListOfVendor")}' />

<br>
<div class="card">

<br>




<div class="row">


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
	<input type="button"  style="width:50%" class="btn btn-block btn-primary btn-sm" onclick="window.location='?a=showAddVendor'" value="Add New" >
</div>



</div>


                    
              
              
              
              
              <!-- /.card-header -->
              <div class="card-body table-responsive p-0" style="height: 580px;">                
                <table id="example1"class="table table-head-fixed  table-bordered table-striped dataTable dtr-inline" role="grid" aria-describedby="example1_info">
                <thead>
                   <tr>
                   	<th><b>Vendor Id</b></th><th><b>Vendor Name</b></th><th><b>Contact Person</b></th><th><b>Address</b></th><th><b>State</b></th><th><b>Email</b></th><th><b>Contact No1</b></th>
                   	<th></th><th></th>
                   </tr>
                 </thead>
                 <tbody>
				 <c:forEach items="${message}" var="item">
					<tr>
						<td>${item.vendor_id}</td><td>${item.vendor_name}</td><td>${item.contact_person}</td><td>${item.address}</td><td>${item.state}</td><td>${item.email}</td><td>${item.contact_no1}</td>
						<td><a href="?a=showAddVendor&vendorId=${item.vendor_id}">Edit</a></td><td><button class="btn btn-danger" onclick="deleteVendor(${item.vendor_id})">Delete</button></td>
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
  
  document.getElementById("divTitle").innerHTML="Vendor Master";
  
  function searchprod(evnt)
	{
		if(evnt.which==13)
			{
				// do some search stuff
				actualSearch();					
			}
			
	}
  function actualSearch()
	{
		
				
				window.location="?a=showVendorMaster&searchString="+txtsearch.value;
			
			
	}
  txtsearch.value='${param.searchString}';
  
</script>