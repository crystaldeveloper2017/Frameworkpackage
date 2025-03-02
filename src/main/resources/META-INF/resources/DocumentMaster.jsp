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
</script>

<c:set var="ListOfDocument" value='${requestScope["outputObject"].get("ListOfDocument")}' />

<div class="card">
    <div class="card-header">
        <div class="card-tools">
            <input type="button" class="btn btn-block btn-primary btn-sm" onclick="window.location='?a=showAddDocument'" value="Add Document">
        </div>
    </div>
    <div class="card-body table-responsive p-0" style="height: 800px;">
        <table id="example1" class="table table-head-fixed table-bordered table-striped dataTable dtr-inline">
            <thead>
                <tr>
                    
                    <th>Group</th>
                    <th>Department</th>
                    <th>Document Name</th>
                    <th>Document Code</th>
                    <th>Document Description</th>
                    <th>Current Status</th>
                    <th>Attachment(s)</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${ListOfDocument}" var="item">
                    <tr>
                    <td>${item.
                    }</td>
                    <td>${item.department_name}</td>                        
                        <td>${item.document_name}</td>
                        <td>${item.document_code}</td>
                        <td>${item.document_description}</td>
                        <td>${item.current_status}</td>
                        <td><a href="BufferedImagesFolder/${item.actualPath}">${item.actualPath}</a></td>
                        <td>
                            <c:if test="${item.current_status eq 'DRAFT'}">
                                <button class="btn btn-success" onclick="changeStatus(${item.document_id}, 'APPROVED')">Approve</button>
                            </c:if>
                            <c:if test="${item.current_status eq 'APPROVED'}">
                                <button class="btn btn-primary" onclick="changeStatus(${item.document_id}, 'PUBLISHED')">Publish</button>
                            </c:if>
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
document.title += " Document Master ";
</script>
