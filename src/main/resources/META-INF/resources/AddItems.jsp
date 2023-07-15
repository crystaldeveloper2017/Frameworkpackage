  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
           
           
           


<c:set var="CategoriesList" value='${requestScope["outputObject"].get("CategoriesList")}' />
<c:set var="BrandList" value='${requestScope["outputObject"].get("BrandList")}' />
<c:set var="itemDetails" value='${requestScope["outputObject"].get("itemDetails")}' />
<c:set var="itemImageFileNames" value='${requestScope["outputObject"].get("itemImageFileNames")}' />
<c:set var="firmList" value='${requestScope["outputObject"].get("firmList")}' />

<c:set var="tentativeProductCode" value='${requestScope["outputObject"].get("tentativeProductCode")}' />




   





</head>


<script>


function addItem()
{
	document.getElementById("frm").submit(); 
}


function updateItem()
{
	document.getElementById("frm").action="?a=updateItem"; 
	document.getElementById("frm").submit();
	return;	
}





function deleteAttachment(id)
{
	document.getElementById("closebutton").style.display='none';
		   document.getElementById("loader").style.display='block';
		$("#myModal").modal();

		var xhttp = new XMLHttpRequest();
		  xhttp.onreadystatechange = function() 
		  {
		    if (xhttp.readyState == 4 && xhttp.status == 200) 
		    { 		      
		      document.getElementById("responseText").innerHTML=xhttp.responseText;
			  document.getElementById("closebutton").style.display='block';
			  document.getElementById("loader").style.display='none';
			  $("#myModal").modal();
		      
			  
			}
		  };
		  xhttp.open("GET","?a=deleteAttachment&attachmentId="+id, true);    
		  xhttp.send();
		
		
		
}








</script>



<br>

<div class="container" style="padding:20px;background-color:white">
    

<form id="frm" action="?a=addItem" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
<div class="row">
<div class="col-sm-3">

  	<div class="form-group">
      <label for="email">Category Name <a onclick='showAddCategory()' href="#">Add</a></label>     
      <select class="form-control" name="drpcategoryId" id="drpcategoryId">
      <c:forEach items="${CategoriesList}" var="cat">
			    <option value="${cat.category_id}">${cat.category_name}</option>			    
	   </c:forEach></select>     
    </div>
  </div>
  
  <div class="col-sm-3">
	  <div class="form-group">
	      <label for="email">Brand Name <a onclick='showAddBrand()' href="#">Add</a></label>     
	      <select class="form-control" name="drpbrandId" id="drpbrandId">
	      <c:forEach items="${BrandList}" var="brand">
				    <option value="${brand.brand_id}">${brand.brand_name}</option>			    
		   </c:forEach></select>     
	    </div>
	  </div>

  	

  <div class="col-sm-3">
  	<div class="form-group">
      <label for="email">Item Name</label>
      <input type="text" class="form-control" id="itemname" value="${itemDetails.item_name}"  placeholder="Item Name" name="itemname">
      <input type="hidden" name="hdnItemId" value="${itemDetails.item_id}" id="hdnItemId">
    </div>
  </div>
  

  
  
  
  
  <div class="col-sm-2">
  	<div class="form-group">
      <label for="email">MRP</label>
      <input type="text" class="form-control" id="itemsaleprice" value="${itemDetails.price}"  placeholder="180" name="itemsaleprice">
    </div>
  </div>
  
  
  
  <div class="col-sm-2">
  	<div class="form-group">
      <label for="email">Product Code</label>
      <input type="text" class="form-control" id="product_code" value="${itemDetails.product_code}"  placeholder="${tentativeProductCode}" name="product_code">
    </div>
  </div>
  
  <div class="col-sm-2">
  	<div class="form-group">
      <label for="email">Color</label>
      <input type="text" class="form-control" id="color" value="${itemDetails.color}"  placeholder="Eg. Red" name="color">
    </div>
  </div>
  
  <div class="col-sm-2">
  	<div class="form-group">
      <label for="email">Size</label>
      <input type="text" class="form-control" id="color" value="${itemDetails.size}"  placeholder="Eg. 12X12" id="size" name="size">
    </div>
  </div>
  
  
  <div class="col-sm-2">
  	<div class="form-group">
      <label for="email">HSN Code</label>      
      <input type="text" class="form-control" id="hsn_code" value="${itemDetails.hsn_code}"  placeholder="Eg. HSN CODE" name="hsn_code">
    </div>
  </div>
  
  <div class="col-sm-2">
  	<div class="form-group">
      <label for="email">GST %</label>      
      <input type="text" class="form-control" id="hsn_code" value="${itemDetails.gst}"  placeholder="Eg. 18%" name="gst">
    </div>
  </div>
  
  <div class="col-sm-2">
  	<div class="form-group">
      <label for="email">AOP</label>      
      <input type="text" class="form-control" id="aop" value="${itemDetails.aop}"  placeholder="" name="aop">
    </div>
  </div>
  
  <div class="col-sm-3">
  	<div class="form-group">
      <label for="email">Concept and Additional Details</label>      
      <input type="text" class="form-control" id="product_details" value="${itemDetails.product_details}"  placeholder="Eg. Additional Details" name="product_details">
    </div>
  </div>
  
   
  
 
  
  <div class="col-sm-12">
  	 <div class="form-group">
      <label for="email">Upload the Images (800px X 800px)</label>
      <input type="file" name="file" multiple/>
      </div>
      </div>
      
      
      	  	  
	<div class="col-sm-12">
  	 <div class="form-group">
  	 	<table>
  	 	<tr>  
	   <c:forEach items="${itemImageFileNames}" var="filenamesvar">
	    	
	    		<td align="center" style="padding:10px">  	
	  		<img src="BufferedImagesFolder/${filenamesvar.fileName}"  height="100px" width="100px"/>
	  		<br>
	  		<button type="button" class="btn btn-danger" onclick='deleteAttachment("${filenamesvar.attachment_id}")'>Delete</button>
	  		</td>		   		
	   	</c:forEach>
	   	</tr>
	   	</table> 
	  
	  
	  
	  
    </div>
  </div>
  
  
  <div class="col-sm-12">
  	 <div class="form-group" align="center">
  	 
	  <c:if test="${itemDetails.item_id eq null}">
	   	<button class="btn btn-success" type="button" onclick='addItem()'>Save</button>	
	   </c:if>
	   
	   <c:if test="${itemDetails.item_id ne null}">
	   	<input type="button" type="button" class="btn btn-success" onclick='addItem()' value="Update">	
	   </c:if>
	   
	   <button class="btn btn-danger" type="reset" onclick='window.location="?a=showItemMaster"'>Cancel</button>
   </div>
   </div>
  
		
		 
</div>

</form>




<script>

	<c:if test="${itemDetails.item_id ne null}">
		document.getElementById('drpcategoryId').value='${itemDetails.parent_category_id}';
		document.getElementById('drpbrandId').value='${itemDetails.brand_id}';
		
		
	</c:if>
	<c:if test="${itemDetails.item_id eq null}">
		document.getElementById("divTitle").innerHTML="Add Item";
	</c:if>
	<c:if test="${itemDetails.item_id ne null}">
		document.getElementById("divTitle").innerHTML="Update Item";
	</c:if>
	
	if(itemsaleprice.value=="")	{itemsaleprice.value="0";}
	
	
	
	if(aop.value=="")	{aop.value="0";}
	
	
	
	
	
	
		
	
	
	
	
	
		
	if('${userdetails.app_id}'!='1')
		{
			appspecific.style='display:none';
		}
	
	function showAddCategory()
	{
		window.open('?a=showAddCategory');
	}
	
	function showAddBrand()
	{
		window.open('?a=showAddBrand');
	}
	
	
	
	
	
</script>
