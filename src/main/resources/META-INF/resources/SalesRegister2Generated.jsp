<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:set var="message" value='${requestScope["outputObject"].get("CustomerInvoiceHistory")}' />
<c:set var="customerDetails" value='${requestScope["outputObject"].get("customerDetails")}' />
<c:set var="totalDetails" value='${requestScope["outputObject"].get("totalDetails")}' />




<br>

 <div class="card">

           <div class="card-header">    
                
                
                
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
              
              
               
                <div class="card-body table-responsive p-0">                
                <table id="testing"class="table table-head-fixed  table-bordered table-striped" role="grid" aria-describedby="example1_info">             
                 <thead>
                    <tr>                  
                    <th colspan="3"><b>Customer Name:-${customerDetails.customer_name }</b></th>
                    <th colspan="2"><b>Type:- ${customerDetails.customer_type }</b></th>
                    <th colspan="1"><b>From Date:-${fromDate1}</b></th>                    
                    <th colspan="1"><b>To Date:-${toDate1}</b></th>                                        
                     </th>
                     </tr>
                  <thead>    
                  
              
				
				
                  </tbody>
                </table>
              </div>
              
              
              
              <!-- /.card-header -->
              <div class="card-body table-responsive p-0" style="height: 480px;">                
                <table id="example1"class="table table-head-fixed  table-bordered table-striped dataTable dtr-inline" role="grid" aria-describedby="example1_info">
                
                 
                    <tr>                  
                    <th><b>Invoice Date</b></th>
                     <th><b>Id</b>
                     <th><b>Item Name</b>
                     <th><b>Qty</b>
                     <th><b>Return</b>
                     <th><b>BilledQty</b>
                                          
                     <th><b>Rate</b>
                     
                     <th><b>Bill Amount</b>                
                     </th><th><b>Updated Date</b></th>
                    </tr>
                  </thead>
                  <tbody>
				<c:forEach items="${message}" var="item">
					<tr >
					  
					  <td>${item.formattedInvoiceDate}</td>
						<td><a href="?a=showGenerateInvoice&invoice_id=${item.invoice_id}">${item.invoice_no}</a></td>
						<td>${item.item_name}</td>
						<td>${item.qty}</td>
						<td>${item.qty_to_return}</td>
						<td>${item.BilledQty}</td>
						
						<td>${item.custom_rate}</td>
						
						
						<td>${item.ItemAmount}</td>
						  <td>${item.formattedUpdatedDate}</td>
					</tr>
				</c:forEach>
				
				
                  </tbody>
                </table>
              </div>
              <!-- /.card-body -->
              
              
               <div class="card-body table-responsive p-0">                
                <table id="testing"class="table table-head-fixed  table-bordered table-striped" role="grid" aria-describedby="example1_info">             
                 <thead>
                    <tr>                  
                    <th colspan="3"></th>
                    <th colspan="2"></th>
                    <th colspan="1"><b></b></th>                    
                    
                    <th colspan="1"><b>Discount Amount: ${totalDetails.discountAmountSum}</b></th>
                    <th colspan="1"><b>Total Amount: ${totalDetails.ItemAmountSum}</b></th>                                        
                     </th>
                     </tr>
                  <thead>    
                  
              
				
				
                  </tbody>
                </table>
              </div>
</div>
            
            
       
            
   

<script>
  $(function () {
    
    $('#example1').DataTable({
      "paging": true,      
      "lengthChange": false,
      "searching": false,      
      "info": true,
      "autoWidth": false,
      "responsive": true,
      "pageLength": 50,
      "order": [[ 2, 'desc' ]]

    });
  });
  
  document.getElementById("divTitle").innerHTML="Sales Register - 2";
  
  
  function exportSalesRegister2()
  {
	
	  var xhttp = new XMLHttpRequest();
		  xhttp.onreadystatechange = function() 
		  {
		    if (xhttp.readyState == 4 && xhttp.status == 200) 
		    { 		      
		    	window.location="BufferedImagesFolder/"+xhttp.responseText;		  
			}
		  };
		  xhttp.open("GET","?a=exportSalesRegister2", true);    
		  xhttp.send();
  }
  
</script> 