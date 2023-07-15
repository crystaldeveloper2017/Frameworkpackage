

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ListStockModifications" value='${requestScope["outputObject"].get("ListStockModifications")}' />

<c:set var="listOffirm" value='${requestScope["outputObject"].get("listOffirm")}' />

<script>
function deleteStock(stockId)
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
	  xhttp.open("GET","?a=deleteStock&stockid="+stockId, true);    
	  xhttp.send();
}

function configureLowStock(stockId)
{
		window.location='?a=showConfigureLowStock&stockId='+stockId;
}

</script>	







<br>
<div class="card">









           <div class="card-header">    
                
                
               <div class="card-tools">
                  <div class="input-group input-group-sm" >                    
                   	<input type="button"  class="btn btn-primary btn-sm" style="margin-right:11px;" onclick="window.location='?a=showInventoryCounting'" value="Inventory Couting" class="form-control float-right" >                                         
                  </div>
                </div>
                
                
                <div class="card-tools">
                  <div class="input-group input-group-sm" >                    
                    <input type="button"  class="btn btn-primary btn-sm" style="margin-right:11px;" onclick="window.location='?a=showStockTransfer'" value="Stock Transfer" class="form-control float-right" >                                         
                  </div>
                </div>
                
                <div class="card-tools">
                  <div class="input-group input-group-sm" >                    
                    <input type="button"  class="btn btn-primary btn-sm" style="margin-right:11px;" onclick="window.location='?a=showAddStock&type=Add'" value="Add New Stock" class="form-control float-right" >                                         
                  </div>
                </div>
                
                <div class="card-tools">
                  <div class="input-group input-group-sm" >                    
                    <input type="button"  class="btn btn-primary btn-sm" style="margin-right:11px;" onclick="window.location='?a=showAddStock&type=Remove'" value="Damaged Stock" class="form-control float-right" >                                         
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
                
                 
              
              <div class="card-tools">
                  <div class="input-group input-group-sm" style="width: 200px;">
  					<select id="drpfirmId" name="drpfirmId" class="form-control float-right" onchange='ReloadFilters()' style="margin-right: 15px;" >
  						
  						<option value='-1'>--Select--</option>  						
  						<c:forEach items="${listOffirm}" var="firm">
							<option value='${firm.firmId}'> ${firm.firmName}</option>
						</c:forEach>  							
  					</select>
                  </div>
              </div>
              
              
                
                
                
                
                

                
              </div>
              
              
              
              
              
              
              <!-- /.card-header -->
              <div class="card-body table-responsive p-0" style="height: 580px;">                
                <table id="example1"class="table table-head-fixed  table-bordered table-striped dataTable dtr-inline" role="grid" aria-describedby="example1_info">
                  <thead>
                    <tr>
                     <th><b>Id</b></th><th><b>Type</b></th><th>Transaction Date</th><th>Updated Date</th> 
                     <th>Updated User</th>
                     <th></th> 
                    </tr>
                  </thead>
                  <tbody>
				<c:forEach items="${ListStockModifications}" var="item">
					<tr>
					
						<c:if test="${item.stock_modification_type eq 'StockIn'}">
							<td><a href="?a=showAddStock&type=Add&stockModificationId=${item.stock_modification_id}"> ${item.stock_modification_id}</a></td>	
						</c:if>
						
						<c:if test="${item.stock_modification_type eq 'Damage'}">
							<td><a href="?a=showAddStock&type=Remove&stockModificationId=${item.stock_modification_id}"> ${item.stock_modification_id}</a></td>	
						</c:if>
						
						<c:if test="${item.stock_modification_type eq 'Inventory Counting'}">
							<td><a href="?a=showInventoryCounting&type=Remove&stockModificationId=${item.stock_modification_id}"> ${item.stock_modification_id}</a></td>	
						</c:if>
						
						<c:if test="${item.stock_modification_type eq 'StockTransfer'}">
							<td><a href="?a=showStockTransfer&stockModificationId=${item.stock_modification_id}"> ${item.stock_modification_id}</a></td>	
						</c:if>
						
						
						
						<td>${item.stock_modification_type}</td>
						<td>${item.transaction_date}</td>
						<td>${item.formattedUpdatedDate}</td>
						<td>${item.name}</td>
						<td>
						<c:if test="${item.stock_modification_type eq 'StockTransfer'}">
							<button class="btn btn-primary" onclick="generatePdfStockTransfer(${item.stock_modification_id})">Generate PDF</button></td>
						</c:if>							 																				 
						
						
						<c:if test="${item.stock_modification_type eq 'StockIn'}">
							<button class="btn btn-primary" onclick="generatePdfAddStock(${item.stock_modification_id})">Generate PDF</button></td>
						</c:if>
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
      "order": [[ 0, "desc" ]]
    });
  });
  
  document.getElementById("divTitle").innerHTML="Stock Modifications";
  
  function ReloadFilters()
  {
	  window.location="?a=showStockModifications&firmId="+drpfirmId.value;
	  
  }
  
  
  
  
  
  function generatePdfStockTransfer(modificationId)
  {
  	var xhttp = new XMLHttpRequest();
  	  xhttp.onreadystatechange = function() 
  	  {
  	    if (xhttp.readyState == 4 && xhttp.status == 200) 
  	    { 		      
  	    	window.open("BufferedImagesFolder/"+xhttp.responseText);		  
  		}
  	  };
  	  xhttp.open("GET","?a=generatePdfStockTransfer&modificationId="+modificationId, false);    
  	  xhttp.send();
  }
  
  function generatePdfAddStock(modificationId)
  {
	  var xhttp = new XMLHttpRequest();
  	  xhttp.onreadystatechange = function() 
  	  {
  	    if (xhttp.readyState == 4 && xhttp.status == 200) 
  	    { 		      
  	    	window.open("BufferedImagesFolder/"+xhttp.responseText);		  
  		}
  	  };
  	  xhttp.open("GET","?a=generatePdfAddStock&modificationId="+modificationId, false);    
  	  xhttp.send();  
  }
  
  
  drpfirmId.value="${param.firmId}";
  
</script>