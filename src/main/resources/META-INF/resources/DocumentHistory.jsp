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
	window.location="?a=showDocumentMaster&document_group_id="+drpdocumentgroup.value+"&department_id="+drpdepartmentname.value;
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

<c:set var="lstDocumentHistory" value='${requestScope["outputObject"].get("lstDocumentHistory")}' />




<div class="card">

   

<div class="row">




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
                    <th><b>Changes Description</b></th>                    
                    <th><b>Time Stamp</b></th>                    
                    
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${lstDocumentHistory}" var="item">
                    <tr>
                    <td>${item.group_name}</td>
                    <td>${item.department_name}</td>                        
                        <td>${item.document_name}</td>
                        <td>${item.document_code}</td>
                        <td>${item.document_description}</td>
                        <td>${item.current_status}</td>                        
                        <td>${item.document_changes}</td>                        
                        <td>${item.created_date}</td>                        

                        

                       
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<script>
$(function () {
    
});

document.getElementById("divTitle").innerHTML = "Document Management System";
document.title += " Document Management System ";

  txtsearch.value='${param.searchString}';

  
</script>