<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script>
function deleteItem(itemId)
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
	  xhttp.open("GET","?a=deleteItem&itemId="+itemId, true);    
	  xhttp.send();
}




</script>	



<c:set var="message" value='${requestScope["outputObject"].get("ListOfItems")}' />
<c:set var="ListOfCategories" value='${requestScope["outputObject"].get("ListOfCategories")}' />




<br>

<div class="card">

<br>




<div class="row">
<div class="col-sm-3" align="center">
	<div class="input-group input-group-sm" style="width: 200px;">
  					<select id="drpcategoryId" name="drpcategoryId" class="form-control float-right" onchange='showThisCategory()' style="margin-right: 15px;" >
  						
  						<option value='-1'>--Select--</option>
  						
  						<c:forEach items="${ListOfCategories}" var="item">
							<option value='${item.category_id}'> ${item.category_name}</option>
						</c:forEach>  							
  					</select>
                  </div>
</div>

<div class="col-sm-3" align="center">
	<div class="input-group input-group-sm" style="width: 200px;">
                    <input type="text" name="txtsearch" id="txtsearch" class="form-control float-right" placeholder="Search" onkeypress="searchprod(event)">                    

                    <div class="input-group-append">
                      <button type="button" class="btn btn-default" onclick='actualSearch()'><i class="fas fa-search"></i></button>
                    </div>
                  </div>
</div>

<div class="col-sm-3" align="center">
	<div class="icon-bar" style="font-size:22px;color:firebrick">
	  <a title="Download Excel" onclick="downloadExcel()"><i class="fa fa-file-excel-o" aria-hidden="true"></i></a> 
	  <a title="Download PDF" onclick="downloadPDF()"><i class="fa fa-file-pdf-o"></i></a>
	  <a title="Download Text"  onclick="downloadText()"><i class="fa fa-file-text-o"></i></a>  
	</div>
</div>

<div class="col-sm-3" align="center">
	<input type="button"  style="width:50%" class="btn btn-block btn-primary btn-sm" onclick="window.location='?a=showAddItem'" value="Add New Item" >
</div>



</div>


                    
              
              
              
              
              <!-- /.card-header -->
              <div class="card-body table-responsive p-0" style="height: 580px;">                
                <table id="example1"class="table table-head-fixed  table-bordered table-striped dataTable dtr-inline" role="grid" aria-describedby="example1_info">
                  <thead>
                    <tr>
                     <th><b>Item Id</b></th><th><b>Item Name</b></th><th><b>Product Code</b></th>
                     <th><b>MRP</b></th>
                     <th><b>Color</b></th>
                     <th><b>Size</b></th>
                     <th></th><th></th>
                     <th></th>
                     
                    </tr>
                  </thead>
                  <tbody>
				<c:forEach items="${message}" var="item">
					<tr >
					
						<td>${item.item_id}</td><td>${item.item_name}</td>
											
						
						<td>${item.product_code}</td>
						<td>${item.price}</td>
						<td>${item.color}</td>
						<td>${item.size}</td>
						<td><a href="?a=showAddItem&itemId=${item.item_id}">Edit</a></td><td><button class="btn btn-danger" onclick="deleteItem(${item.item_id})">Delete</button></td>
						<td><a href="?a=showItemHistory&itemId=${item.item_id}">Show Item History</a></td>
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
      "order": [[ 1, "asc" ]]
    });
  });
  
  document.getElementById("divTitle").innerHTML="Item Master";
  
  function actualSearch()
  {
	  window.location="?a=showItemMaster&searchInput="+txtsearch.value+"&categoryId="+drpcategoryId.value;  
  }
  
  function searchprod(evnt)
	{
		if(evnt.which==13)
			{
				// do some search stuff
				actualSearch();					
			}
			
	}
  
  function showThisCategory()
  {
	  window.location="?a=showItemMaster&searchInput="+txtsearch.value
	  		  +"&categoryId="+drpcategoryId.value;
	  
  }
  
  drpcategoryId.value='${param.categoryId}';
  
</script>