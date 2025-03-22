<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script>
function deleteDocument(documentId) {
    var answer = window.confirm("Are you sure you want to delete ?");
    if (!answer) {
        return;
    }
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (xhttp.readyState == 4 && xhttp.status == 200) {
            toastr["success"](xhttp.responseText);
            window.location.reload();
        }
    };
    xhttp.open("GET", "?a=deleteDocument&document_id=" + documentId, true);
    xhttp.send();
}

function changeStatus(documentId, newStatus) {
    if (!confirm("Are you sure you want to change the status to " + newStatus + "?")) {
        return;
    }
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (xhttp.readyState == 4 && xhttp.status == 200) {
            toastr["success"]("Status updated successfully");
            window.location.reload();
        }
    };
    xhttp.open("GET", "?a=updateDocumentStatus&documentId=" + documentId + "&newStatus=" + newStatus, true);
    xhttp.send();
}

function reloadFilters()
  {
	window.location="?a=showDocumentMaster&document_group_id="+drpdocumentgroup.value+"&department_id="+drpdepartmentname.value+"&current_status="+drpCurrentStatus.value;
  }
  
   function searchprod(evnt)
	{
		if(evnt.which==13)
			{
				// do some search stuff
				actualSearch();					
			}
			
	}
  
  function actualSearch()
	{
		
				
				window.location="?a=showDocumentMaster&searchString="+txtsearch.value;
			
			
	}


</script>

<c:set var="ListOfDocument" value='${requestScope["outputObject"].get("ListOfDocument")}' />

<c:set var="lstDocumentGroup" value='${requestScope["outputObject"].get("lstDocumentGroup")}' />


<c:set var="lstDepartmentMaster" value='${requestScope["outputObject"].get("lstDepartmentMaster")}' />

<c:set var="lstCurrentStatusDocumentMaster" value='${requestScope["outputObject"].get("lstCurrentStatusDocumentMaster")}' />


<div class="card">

    <div class="card-header">
        <div class="card-tools">
            <input type="button" class="btn btn-block btn-primary btn-sm" onclick="window.location='?a=showAddDocument'" value="Add Document">
        </div>
    </div>


<div class="row">


<div class="col-sm-3" align="center">
	<div class="input-group input-group-sm" style="width: 200px;">
                    <input type="text" name="txtsearch" id="txtsearch" class="form-control float-right" placeholder="Search" onkeypress="searchprod(event)">                    

                    <div class="input-group-append">
                      <button type="button" class="btn btn-default" onclick='actualSearch()'><i class="fas fa-search"></i></button>
                    </div>
                  </div>
</div>



                  <div class="card-tools">
  <div class="input-group input-group-sm">
				  <div class="input-group input-group-sm" style="width: 230px;">
  			
  					<select id="drpdocumentgroup" name="drpdocumentgroup" class="form-control float-right" onchange='reloadFilters()' style="margin-right: 20px;" >
  						
  						<option value='-1'>--Group--</option>
  						
  						<c:forEach items="${lstDocumentGroup}" var="cat">
							<option value='${cat.document_group_id}'> ${cat.group_name}</option>
						</c:forEach>  							
  					</select>


    

					<select id="drpdepartmentname" name="drpdepartmentname" class="form-control float-right" onchange='reloadFilters()'  >
  						
  						<option value='-1'>--Department Name--</option>
  						
  						<c:forEach items="${lstDepartmentMaster}" var="cat">
							<option value='${cat.department_id}'> ${cat.department_name}</option>
						</c:forEach>  							
  					</select>


                    <select id="drpCurrentStatus" name="drpCurrentStatus" class="form-control float-left" onchange='reloadFilters()'  >
  						
  						<option value='-1'>--Current Status--</option>
  						
  						<c:forEach items="${lstCurrentStatusDocumentMaster}" var="cat">
							<option value='${cat.status}'> ${cat.status}</option>
						</c:forEach>  							
  					</select>
				</div>
			</div>
		</div>

               
  	</div>         

    <div class="card-body table-responsive p-0" style="height: 800px;">
        <table id="example1" class="table table-head-fixed table-bordered table-striped dataTable dtr-inline" role="grid" aria-describedby="example1_info">
            <thead>
                <tr>
                    
                    <th><b>Group</b></th>
                    <th><b>Department</b></th>
                    <th><b>Document Name</b></th>
                    <th><b>Document Code</b></th>
                    <th><b>Document Description</b></th>
                    <th><b>Current Status</b></th>
                    <th><b>Attachment(s)</b></th>
                    <th><b>Actions</b></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${ListOfDocument}" var="item">
                    <tr>
                    <td>${item.group_name}</td>
                    <td>${item.department_name}</td>                        
                        <td>${item.document_name}</td>
                        <td>${item.document_code}</td>
                        <td>${item.document_description}</td>
                        <td>${item.current_status}</td>
                        <td><a href="BufferedImagesFolder/${item.actualPath}">${item.actualPath}</a></td>

                        

                        <td>
                        <button class="btn btn-primary" onclick="window.location='?a=showAddDocument&documentId=${item.document_id}'">Edit</button>
                            <c:if test="${item.current_status eq 'DRAFT'}">
                                <button class="btn btn-success" onclick="changeStatus(${item.document_id}, 'APPROVED')">Approve</button>
                            </c:if>
                            <c:if test="${item.current_status eq 'APPROVED'}">
                                <button class="btn btn-primary" onclick="changeStatus(${item.document_id}, 'PUBLISHED')">Publish</button>
                            </c:if>
                            
                            <button class="btn btn-primary" onclick="window.location='?a=showDocumentHistory&documentId=${item.document_id}'">Show History</button>
                            <button class="btn btn-danger" onclick="deleteDocument(${item.document_id})">Delete</button>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
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

document.getElementById("divTitle").innerHTML = "Document Management System";
document.title += " Document Management System ";

  txtsearch.value='${param.searchString}';

  
</script>