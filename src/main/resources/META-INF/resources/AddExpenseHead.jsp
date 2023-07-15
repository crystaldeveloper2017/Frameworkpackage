  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
           
           
           


<c:set var="expenseHeadDetails" value='${requestScope["outputObject"].get("expenseHeadDetails")}' />
   





</head>


<script>


function addExpenseHead()
{	
	
	
	document.getElementById("frm").submit(); 
}


function updateExpenseHead()
{
	
	document.getElementById("frm").action="?a=updateExpenseHead"; 
	document.getElementById("frm").submit();
	return;
	
	
}








</script>



<br>

<div class="container" style="padding:20px;background-color:white">

<form id="frm" action="?a=addExpenseHead" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
<div class="row">
  <div class="col-sm-12">
  	<div class="form-group">
      <label for="email">ExpenseHead Name</label>
      <input type="text" class="form-control" id="expense_name" value="${expenseHeadDetails.expense_name}"   name="expense_name">
      <input type="hidden" name="hdnExpenseHeadId" value="${expenseHeadDetails.expense_head_id}" id="hdnExpenseHeadId">
    </div>
  </div>
  
  
  <c:if test="${action ne 'Update'}">
		
		<button class="btn btn-success" type="button" onclick='addExpenseHead()'>Save</button>
		<button class="btn btn-danger" type="reset" onclick='window.location="?a=showCustomerExpenseHead"'>Cancel</button>
		
		
		</c:if>
		
		
		
		
</div>
</form>

<script>
	
	
	<c:if test="${groupDetails.group_id eq null}">
		document.getElementById("divTitle").innerHTML="Add ExpenseHead";
	</c:if>
	<c:if test="${groupDetails.group_id ne null}">
		document.getElementById("divTitle").innerHTML="Update ExpenseHead";
	</c:if>
</script>



