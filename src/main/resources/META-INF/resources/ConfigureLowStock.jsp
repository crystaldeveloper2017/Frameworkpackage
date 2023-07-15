  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
           
           
           


<c:set var="stockDetails" value='${requestScope["outputObject"].get("stockDetails")}' />
   
 <c:set var="firmList" value='${requestScope["outputObject"].get("firmList")}' />





</head>


<script>






</script>



<br>

<div class="container" style="padding:20px;background-color:white">

<form id="frm" action="?a=updateLowStock" method="post"  accept-charset="UTF-8">
<div class="row">

<input type="hidden" value="${stockDetails.stock_id}" name="stock_id" id="stock_id">

<div class="col-sm-6">
  	<div class="form-group">
      <label for="email">firm Name</label>
      
      
      <select class='form-control' disabled id="drpfirmid" name="drpfirmid">
        <c:forEach items="${firmList}" var="firm">
					<option value='${ firm.firmId}'>${ firm.firmName}</option>						
		</c:forEach>
		</select>
      
    </div>
  </div>

  <div class="col-sm-6">
  	<div class="form-group">
      <label for="email">Item Name</label>
      <input type="text" class="form-control" id="categoryname" readonly value="${stockDetails.item_name}"  placeholder="eg. Sweets" name="categoryName">      
    </div>
  </div>
  
   <div class="col-sm-6">
  	<div class="form-group">
      <label for="email">Available Quantity</label>
      <input type="text" class="form-control" id="categoryname" readonly value="${stockDetails.qty_available}"  placeholder="eg. Sweets" name="categoryName">      
    </div>
  </div>
  
   <div class="col-sm-6">
  	<div class="form-group">
      <label for="email">Low Stock Quantity</label>
      <input type="text" class="form-control" id="lowqty" value="${stockDetails.low_stock_limit}"  name="lowqty">      
    </div>
  </div>
  
  
  
  
  
		<button class="btn btn-success" type="submit" >Save</button>
		<button class="btn btn-danger" type="reset" onclick='window.location="?a=showCategoryMasterNew"'>Cancel</button>
		

</form>

<script>
	document.getElementById("divTitle").innerHTML="Configure Low Stock";
	drpfirmid.value='${stockDetails.firm_id}';
</script>



