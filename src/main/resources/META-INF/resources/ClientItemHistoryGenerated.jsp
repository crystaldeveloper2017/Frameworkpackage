<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:set var="message" value='${requestScope["outputObject"].get("ClientInvoiceHistory")}' />



<br>
<div class="card">

           <div class="card-header">    
                
                
                
                <div class="card-tools">
                  <div class="input-group input-group-sm" align="center" style="width: 200px;display:inherit">
                    <div class="icon-bar" style="font-size:22px;color:firebrick">
  						<a title="Download Excel" onclick="downloadExcel()"><i class="fa fa-file-excel-o" aria-hidden="true"></i></a> 
 						<a title="Download PDF" onclick="exportCustomerItemHistory()"><i class="fa fa-file-pdf-o"></i></a>
  						<a title="Download Text"  onclick="downloadText()"><i class="fa fa-file-text-o"></i></a>  
					</div>           
                  </div>
                </div>
                               
                               
              </div>
              
              
              
              
              
              
              <!-- /.card-header -->
              <div class="card-body table-responsive p-0" style="height: 580px;">                
                <table id="example1"class="table table-head-fixed  table-bordered table-striped dataTable dtr-inline" role="grid" aria-describedby="example1_info">
                  <thead>
                    <tr>
                     <th><b>Invoice Id</b></th><th><b>Invoice Date</b></th><th><b>Updated Date</b>
                     </th><th><b>Client Name</b></th>
                     <th><b>Item Name</b></th>
                     <th><b>Qty</b></th>
                     <th><b>Custom Rate</b></th>
                     <th><b>Item Amount</b></th>
                     
                    </tr>
                  </thead>
                  <tbody>
				<c:forEach items="${message}" var="item">
					<tr >
						<td><a href="?a=showGenerateInvoice&invoice_id=${item.invoice_id}">${item.invoice_no}</a></td>
						<td>${item.formattedInvoiceDate}</td>  <td>${item.formattedUpdatedDate}</td>  <td>${item.client_name}</td>
						<td>${item.item_name}</td>
						<td>${item.qty}</td>
						<td>${item.custom_rate}</td>
						<td>${item.ItemAmount}</td>
						
					</tr>
				</c:forEach>
				
				
                  </tbody>
                </table>
              </div>
              <!-- /.card-body -->
</div>
            
            
            
            



<script>


function exportClientItemHistory()
{
	
	  var xhttp = new XMLHttpRequest();
		  xhttp.onreadystatechange = function() 
		  {
		    if (xhttp.readyState == 4 && xhttp.status == 200) 
		    { 		      
		    	window.location="BufferedImagesFolder/"+xhttp.responseText;		  
			}
		  };
		  xhttp.open("GET","?a=exportClientItemHistory", true);    
		  xhttp.send();
}



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
  
  document.getElementById("divTitle").innerHTML="Client Item Report";
  
</script>