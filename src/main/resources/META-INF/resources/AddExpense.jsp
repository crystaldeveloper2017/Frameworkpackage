<style>
	.date_field {position: relative; z-index:100;}
</style>

  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
           
           
           


<c:set var="expenseDetails" value='${requestScope["outputObject"].get("expenseDetails")}' />
<c:set var="totalAmount" value='${requestScope["outputObject"].get("totalAmount")}' />

<c:set var="todaysDate" value='${requestScope["outputObject"].get("todaysDate")}' />
<c:set var="distinctExpenseList" value='${requestScope["outputObject"].get("distinctExpenseList")}' />
<c:set var="expenseList" value='${requestScope["outputObject"].get("expenseList")}' />


   





</head>



<script>


function addExpense()
{	
	
	
	document.getElementById("frm").submit(); 
}











</script>



<br>

<div class="container" style="padding:20px;background-color:white">

<form id="frm" action="?a=addExpense" method="post" enctype="multipart/form-data" accept-charset="UTF-8">

<div class="row">
  <div class="col-sm-4">
  	<div class="form-group">
      <label for="email">Expense Date </label>
      
       <c:if test="${expenseDetails.expense_id eq null}">	
      	<input type="text" class="form-control form-control-sm date_field" id="txtdate" readonly   onchange="expenseDateChange()" value="${todaysDate}"   name="txtdate">
      </c:if>
      
      <c:if test="${expenseDetails.expense_id ne null}">	
      	<input type="text" class="form-control form-control-sm date_field" id="txtdate" readonly   value="${expenseDetails.FormattedExpenseDate}"   name="txtdate">
      </c:if>
      
      
      
    </div>
  </div>
  
  <div class="col-sm-4">
  	<div class="form-group">
      <label for="email">Expense Name</label>
      <input type="text" class="form-control form-control-sm" id="expense_name" placeholder="Eg. Tea" value="${expenseDetails.expense_name}" list="distinctExpenseList"  name="expense_name">
      <input type="hidden" name="hdnExpenseId" value="${expenseDetails.expense_id}" id="hdnExpenseId">
      <datalist id="distinctExpenseList">
      			   <c:forEach items="${distinctExpenseList}" var="expense">
			    		<option id="${expense.expense_name}">${expense.expense_name}</option>			    
	   			   </c:forEach>	
	  </datalist>
      
    </div>
  </div>
  
   <div class="col-sm-4">
  	<div class="form-group">
      <label for="email">Qty</label>
      <input type="tel" class="form-control form-control-sm" id="qty" placeholder="Eg. 1" value="${expenseDetails.qty}"   name="qty">
      
    </div>
  </div>
  
   <div class="col-sm-4">
  	<div class="form-group">
      <label for="email">Expense Amount</label>
      <input type="tel" class="form-control form-control-sm" id="amount" placeholder="Eg. 300" value="${expenseDetails.amount}"   name="amount">
      
    </div>
  </div>
</div>

<div class="row">
  
</div>

<div class="row">
 
</div>
  
  

		<button class="btn btn-success btn-sm " type="button" onclick='addExpense()'>Save</button>
		<button class="btn btn-danger btn-sm" type="reset" onclick='window.location="?a=showExpenseEntry"'>Cancel</button>
		
 

</form>

<br>
<br>
<br>
<br>

<div class="card-body table-responsive p-0" style="height: 550px;">                
                <table id="example1"class="table table-head-fixed  table-bordered table-striped dataTable dtr-inline" role="grid" aria-describedby="example1_info">
                  <thead>
                    <tr>
                     	<th><b>Expense Id</b></th><th><b>Expense name</b></th>
                     	 <th><b>Qty</b></th>
                     	 <th><b>Amount</b></th><th></th><th></th>
                    </tr>
                  </thead>
                  <tbody>
				<c:forEach items="${expenseList}" var="item">
					<tr>
						<td>${item.expense_id}</td><td>${item.expense_name}</td>
						<td>${item.qty}</td>
						<td>${item.amount}</td>						
						<td><a href="?a=showAddExpense&expenseId=${item.expense_id}">Edit</a></td><td><button class="btn btn-danger" onclick="deleteExpense(${item.expense_id})">Delete</button></td>
					</tr>
				</c:forEach>
				
				
                  </tbody>
                </table>
</div>

<br>
<br>

<div class="d-flex justify-content-end">
	
	  <div class="p-2">Total Expense : <b>${totalAmount}</b></div>
</div> 
    


<script>
	
	
	<c:if test="${expenseDetails.expense_id eq null}">
		document.getElementById("divTitle").innerHTML="Add Expense";
	</c:if>
	<c:if test="${expenseDetails.expense_id ne null}">
		document.getElementById("divTitle").innerHTML="Update Expense";
	</c:if>
	
	$( "#txtdate" ).datepicker({ dateFormat: 'dd/mm/yy' });
</script>


<script>
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
  
  
  function expenseDateChange()
  {
		
		window.location="?a=showAddExpense&expenseDate="+txtdate.value;
  }
  
</script>


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



