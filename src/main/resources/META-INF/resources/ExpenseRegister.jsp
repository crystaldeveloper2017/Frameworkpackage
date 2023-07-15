<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>




<c:set var="ListofExpense" value='${requestScope["outputObject"].get("ListofExpense")}' />
<c:set var="todaysDate" value='${requestScope["outputObject"].get("todaysDate")}' />
<c:set var="totalAmount" value='${requestScope["outputObject"].get("totalAmount")}' />




<script>
function deleteExpense(expenseId)
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
	  xhttp.open("GET","?a=deleteExpense&expenseId="+expenseId, true);    
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
              <div class="card-body table-responsive p-0" style="height: 540px;">                
                <table id="example1" class="table table-head-fixed  table-bordered table-striped dataTable dtr-inline" role="grid" aria-describedby="example1_info">
                  <thead>
                    <tr>
                     
                     <th><b>Expense Name</b></th>
                     <th><b>Expense Amount</b></th>
                     <th><b>Expense Qty</b></th>
                     <th><b>Expense Date</b></th>
                     <th></th><th></th>
                    </tr>
                  </thead>
                  <tbody>
				<c:forEach items="${ListofExpense}" var="expense">
					<tr>						
						<td>${expense.expense_name}</td>
						<td>${expense.amount}</td>
						<td>${expense.qty}</td>						
						<td>${expense.FormattedExpenseDate}</td>
						<td><a href="?a=showAddExpense&expenseId=${expense.expense_id}">Edit</a></td><td><button class="btn btn-danger" onclick="deleteExpense(${expense.expense_id})">Delete</button></td>
					</tr>
				</c:forEach>
				
				
                  </tbody>
                </table>
              </div>
             
              <!-- /.card-body -->
            </div>
            
            
            <div class="d-flex justify-content-end">
            	<div class="p-2">Total Expense : <b>${totalAmount}</b></div>
            </div>
            



<script>


$( function() 
		{
    $( "#txtfromdate" ).datepicker({ dateFormat: 'dd/mm/yy' });
    $( "#txttodate" ).datepicker({ dateFormat: 'dd/mm/yy' });
    
               
    
  } );
  
  $(function () {
    
    $('#example1').DataTable({
      "paging": true,      
      "lengthChange": false,
      "searching": false,
      "ordering": true,
      "info": true,
      "autoWidth": false,
      "responsive": false,
      "pageLength": 50,
      "order": [[ 0, "desc" ]]
    });
  });
  
  document.getElementById("divTitle").innerHTML="Expense Register";
  
  
  function reloadData()
  {
  	window.location="?a=showExpenseEntry&fromDate="+txtfromdate.value+"&toDate="+txttodate.value;
  } 
  
  
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