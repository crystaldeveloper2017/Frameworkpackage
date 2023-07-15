<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>




<c:set var="ListOfRoutines" value='${requestScope["outputObject"].get("ListOfRoutines")}' />
<c:set var="ListOfCustomers" value='${requestScope["outputObject"].get("ListOfCustomers")}' />



<script>
function deleteRoutine(RoutineId)
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
	  xhttp.open("GET","?a=deleteRoutine&RoutineId="+RoutineId, true);    
	  xhttp.send();
}
</script>


<br>
<div class="card">









           <div class="card-header">    
                
                
                
               
                
                
                <div class="card-tools">
                  <div class="input-Routine input-Routine-sm" style="width: 200px;">                    
                    <input type="button"  class="btn btn-block btn-primary btn-sm" onclick="window.location='?a=showAddRoutine'" value="Add New Routine" class="form-control float-right" >                      
                  </div>
                </div>
                
                <div class="card-tools">
                  <div class="input-Routine input-Routine-sm" align="center" style="width: 200px;display:inherit">
                    <div class="icon-bar" style="font-size:22px;color:firebrick">
  <a title="Download Excel" onclick="downloadExcel()"><i class="fa fa-file-excel-o" aria-hidden="true"></i></a> 
  <a title="Download PDF" onclick="downloadPDF()"><i class="fa fa-file-pdf-o"></i></a>
  <a title="Download Text"  onclick="downloadText()"><i class="fa fa-file-text-o"></i></a>  
</div>           
                  </div>
                </div>
                
                 <div class="card-tools">
                  <div class="input-group input-group-sm" style="width: 200px;">
  					<select id="drpcustomerid" name="drpcustomerid" class="form-control float-right" onchange='showThisCustomer()' style="margin-right: 15px;" >
  						
  						<option value='-1'>--Select--</option>
  						
  						<c:forEach items="${ListOfCustomers}" var="item">
							<option value='${item.customerId}'> ${item.customerName}</option>
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
                     <th><b>Routine Id</b></th>
                     <th><b>Customer Name</b></th>
                     <th>Item Name</th>
                     <th>Custom_rate</th>
                     <th>Item Qty</th>                     
                     <th>Item Occurance</th>
                     <th></th>
                     <th></th>                     
                    </tr>
                  </thead>
                  <tbody>
				<c:forEach items="${ListOfRoutines}" var="Routine">
					<tr >
						<td>${Routine.routine_id}</td>
						<td>${Routine.customer_name}</td>
						<td>${Routine.item_name}</td>
						<td>${Routine.custom_rate}</td>
						<td>${Routine.qty}</td>
						<td>${Routine.occurance}</td>
						<td><a href="?a=showAddRoutine&RoutineId=${Routine.routine_id}">Edit</a></td><td><button class="btn btn-danger" onclick="deleteRoutine(${Routine.routine_id})">Delete</button></td>
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
  
  document.getElementById("divTitle").innerHTML="Routine Master";
  
  
  function showThisCustomer()
  {
	  window.location="?a=showCustomerDeliveryRoutine&customerId="+drpcustomerid.value;
	  
  }
  drpcustomerid.value="${param.customerId}";
  
</script>