<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="documentDetails" value='${requestScope["outputObject"].get("documentDetails")}' />

</head>
<script>
// Submits the form when Save/Update is clicked
function addCategory() {  
    document.getElementById("frm").submit(); 
}

// Existing deleteAttachment function remains unchanged
function deleteAttachment(id) {
    document.getElementById("closebutton").style.display = 'none';
    document.getElementById("loader").style.display = 'block';
    $("#myModal").modal();

    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (xhttp.readyState == 4 && xhttp.status == 200) {             
            document.getElementById("responseText").innerHTML = xhttp.responseText;
            document.getElementById("closebutton").style.display = 'block';
            document.getElementById("loader").style.display = 'none';
            $("#myModal").modal();
        }
    };
    xhttp.open("GET", "?a=deleteAttachment&attachmentId=" + id, true);    
    xhttp.send();
}
function  submittechnical()
{
	if(checkifPDF())
		{
			document.getElementById("frm").submit();
		}
	else
		{
			alert("only pdf is allowed");
		}
		
}

</script>

<br>
<div class="container" style="padding:20px;background-color:white">
    <form id="frm" action="?a=addDocument" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
        <input type="hidden" name="app_id" value="${userdetails.app_id}">
        <input type="hidden" name="user_id" value="${userdetails.user_id}">
        <input type="hidden" name="callerUrl" id="callerUrl" value="">

        <div class="row">
            <div class="col-sm-12">
                <div class="form-group">
                    <label for="email">Document Name</label>
                    <input type="text" class="form-control" id="txtdocumentname" value="${documentDetails.document_name}" placeholder="eg. Name" name="txtdocumentname">
                    <input type="hidden" name="hdnDocumentId" value="${documentDetails.document_id}" id="hdnDocumentId">
                </div>
            </div>
            
            <div class="col-sm-12">
                <div class="form-group">
                    <label for="description">Document Description</label>
                    <input type="text" class="form-control" id="txtdescription" value="${documentDetails.document_description}" name="txtdescription" placeholder="">
                </div>
            </div>

             <div class="col-sm-12">
                <div class="form-group">
                    <label for="description">Document Code</label>
                    <input type="text" class="form-control" id="txtdocumentcode" value="${documentDetails.document_description}" name="txtdocumentcode" placeholder="">
                </div>
            </div>
            
            <div class="col-sm-12">
                <table class="table table-bordered tablecss" border="3">
                    <tr align="center" >
                        <td>Upload Document</td>
                        <td><input type="file" id="TechnicalDocument" name="TechnicalDocument" multiple/></td>
                    </tr>
                    
                </table>

                <!-- Save/Cancel buttons are wrapped in this div and are initially hidden -->
                <div  class="col-sm-12" id="uploadActions" > 

                    <c:if test="${action ne 'Update'}">
                        <button class="btn btn-success" type="button" onclick="addCategory()">Save</button>
                        <button class="btn btn-danger" type="reset" onclick="window.location='?a=showCategoryMasterNew'">Cancel</button>
                    </c:if>
                    <c:if test="${action eq 'Update'}">
                        <input class="btn btn-success" type="button" onclick="addCategory()" value="Update">
                    </c:if>
                </div>
            </div>
        </div>
    </form>

    <script>
        <c:if test="${abbreviationDetails.categoryId eq null}">
            document.getElementById("divTitle").innerHTML = "Add Document";
        </c:if>
        <c:if test="${abbreviationDetails.categoryId ne null}">
            document.getElementById("divTitle").innerHTML = "Update Document";
        </c:if>
    </script>
</div>
