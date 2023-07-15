<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>





<c:set var="itemHistoryList" value='${requestScope["outputObject"].get("itemHistoryList")}' />
<c:set var="itemDetails" value='${requestScope["outputObject"].get("itemDetails")}' />





<br>
<div class="card">
              <!-- /.card-header -->
              <div class="card-body table-responsive p-0" style="height: 580px;">                
                <table id="example1"class="table table-head-fixed  table-bordered table-striped dataTable dtr-inline" role="grid" aria-describedby="example1_info">
                  <thead>
                    <tr>
                    <th>Updated_date</th>
                    <th>firm Name</th>
                    <th>Employee Name</th>
                    <th>Type</th>
                      
                   </th><th><b>Invoice Id</b></th>
                   <th>Adjustment</th>
                   <th>Stock After</th>
                    </tr>
                  </thead>
                  <tbody>
				<c:forEach items="${itemHistoryList}" var="item">
					<tr >
					<td>${item.formattedUpdatedDate}</td>
					<td>${item.firm_name}</td>
					<td>${item.name}</td>					
					<td>${item.type}</td>
										
						<td><a href="?a=showGenerateInvoice&invoice_id=${item.invoice_id}">${item.invoice_no}</a></td>
						<td>${item.qty}</td>
						<td>${item.closing_qty}</td>
						 
						
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
      "pageLength": 50
      
    });
  });
  
  document.getElementById("divTitle").innerHTML="Item History "+"( ${itemDetails.item_name} -  ${itemDetails.product_code})";
  
</script>