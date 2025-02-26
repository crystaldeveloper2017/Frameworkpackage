<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script>

function deleteAbbreviation(abbreviationId)
{
	
	var answer = window.confirm("Are you sure you want to delete ?");
	if (!answer) 
	{
		return;    
	}
	
	  

	var xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() 
	  {
	    if (xhttp.readyState == 4 && xhttp.status == 200) 
	    { 		      
	      
		  toastr["success"](xhttp.responseText);
	    	toastr.options = {"closeButton": false,"debug": false,"newestOnTop": false,"progressBar": false,
	    	  "positionClass": "toast-top-right","preventDuplicates": false,"onclick": null,"showDuration": "1000",
	    	  "hideDuration": "500","timeOut": "500","extendedTimeOut": "500","showEasing": "swing","hideEasing": "linear",
	    	  "showMethod": "fadeIn","hideMethod": "fadeOut"}
	    	
	    	window.location.reload();
	      
		  
		}
	  };
	  xhttp.open("GET","?a=deleteAbbreviation&abbreviationId="+abbreviationId, true);    
	  xhttp.send();
}





function addShift()
{			
	
	var catName=document.getElementById('txtShiftnamepopup').value;
	var xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() 
	  {
	    if (xhttp.readyState == 4 && xhttp.status == 200) 
	    { 		    	
	    	
	    	document.getElementById("responseText").innerHTML=xhttp.responseText;
		     document.getElementById("closebutton").style.display='block';
			   document.getElementById("loader").style.display='none';			  
		}
	  };
	  xhttp.open("GET","?a=addShift&ShiftName="+catName, true);    
	  xhttp.send();
	
}



</script>	



<c:set var="ListOfDocument" value='${requestScope["outputObject"].get("ListOfDocument")}' />



<br>
<div class="card">









           <div class="card-header">    
                
                
                <div class="card-tools">
                  <div class="input-group input-group-sm" style="width: 200px;">                    
                    <input type="button"  class="btn btn-block btn-primary btn-sm" onclick="window.location='?a=showAddDocument'" value="Add Document" class="form-control float-right" >                      
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
                
                
                
                
                

                
              </div>
              
              
              
              
              
              
              <!-- /.card-header -->
              <div class="card-body table-responsive p-0" style="height: 800px;">                
                <table id="example1"class="table table-head-fixed  table-bordered table-striped dataTable dtr-inline" role="grid" aria-describedby="example1_info">
                  <thead>
                    <tr>
                     <th><b>Document Id</b></th>
                     <th><b>Document Name </b></th>
                       <th><b>Document Description </b></th>
                        <th><b>Document Description </b></th>
                     
                     
                     <th></th><th></th>
                    </tr>
                  </thead>
                  <tbody>
				<c:forEach items="${ListOfDocument}" var="item">
					<tr >
						<td>${item.document_id}</td>
						<td>${item.document_name}</td>
            <td>${item.document_description}</td>
            <td>${item.description}</td>
						<td>${item.document_attachement}</td><td><a href="BufferedImagesFolder/${item.actualPath}">View</a></td>
						
						<td><a href="?a=showAddAbbreviation&abbreviationId=${item.abbreviation_id}">Edit</a></td>
            <td><button class="btn btn-danger" onclick="deleteAbbreviation(${item.abbreviation_id})">Delete</button></td>
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
      "pageLength": 100
    });
  });



  document.getElementById("divTitle").innerHTML="Document Management System";
  document.title +=" Abbreviation Master ";
  
</script>