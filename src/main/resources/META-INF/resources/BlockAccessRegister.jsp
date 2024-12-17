<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script>
    function toggleCheckbox(row, checkboxId) {
        const checkbox = document.getElementById(checkboxId);
        checkbox.checked = !checkbox.checked;
    }

    function deleteSelectedEntries() {
        const checkboxes = document.querySelectorAll('.deleteCheckbox:checked');
        const selectedIds = Array.from(checkboxes).map(cb => cb.value);

        if (selectedIds.length === 0) {
            alert("No entries selected for deletion.");
            return;
        }

        const confirmation = confirm(`Are you sure you want to delete ${selectedIds.length} entries?`);
        if (!confirmation) {
            return;
        }

        const xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            if (xhttp.readyState == 4 && xhttp.status == 200) {
                alert("Selected entries deleted successfully!");
                window.location.reload();
            }
        };
        xhttp.open("POST", "?a=deleteAccessBlockEntry", true);
        xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhttp.send(`accessblockIds=`+selectedIds.join(","));
    }

    function searchEntry(event) {
        if (event.which == 13) {
            actualSearch();
        }
    }

    function actualSearch() {
        const searchInput = document.getElementById("txtsearch").value;        
        window.location.href = `?a=showAccessBlockEntry&searchInput=`+searchInput;

    }
</script>

<c:set var="message" value='${requestScope["outputObject"].get("ListOfAccessBlockEntry")}' />

<br>
<div class="card">
    <div class="card-header">
        <div class="card-tools" style="display: flex; justify-content: space-between; width: 100%;">
            <div class="input-group input-group-sm" style="width: 50%;">
                <input type="text" id="txtsearch" name="txtsearch" class="form-control float-right" placeholder="Search" onkeypress="searchEntry(event)">
                <div class="input-group-append">
                    <button type="button" class="btn btn-default" onclick="actualSearch()">
                        <i class="fas fa-search"></i>
                    </button>
                </div>
            </div>

            <!-- New Button Added for "Add Access Block" -->
            <div class="input-group input-group-sm" style="width: 20%;">
                <input type="button" class="btn btn-block btn-primary btn-sm" 
                       onclick="window.location='?a=showAddAccessBlockEntry'" 
                       value="Add Access Block" />
            </div>

            <!-- New Flex Container for Excel Export Buttons -->
            <div class="input-group input-group-sm" style="width: 25%; display: flex; justify-content: space-evenly;">
                <a title="Download Excel" onclick="downloadExcel()"><i class="fa fa-file-excel-o" aria-hidden="true" style="font-size:22px;color:firebrick"></i></a>
                <a title="Download PDF" onclick="downloadPDF()"><i class="fa fa-file-pdf-o" style="font-size:22px;color:firebrick"></i></a>
                <a title="Download Text" onclick="downloadText()"><i class="fa fa-file-text-o" style="font-size:22px;color:firebrick"></i></a>
            </div>
        </div>
    </div>

    <div class="card-body table-responsive p-0" style="height: 800px;">
        <table id="example1" class="table table-head-fixed table-bordered table-striped dataTable dtr-inline" role="grid" aria-describedby="example1_info">
            <thead>
                <tr>
                    <th>Select</th>
                    <th><b>Access Block Id</b></th>
                    <th><b>Employee Id</b></th>
                    <th><b>Employee Name</b></th>
                    <th><b>Remarks</b></th>
                    <th><b>Updated By</b></th>
                    <th><b>Qr Code</b></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${message}" var="item">
                    <tr onclick="toggleCheckbox(this, 'checkbox-${item.access_block_id}')">
                        <td><input type="checkbox" class="deleteCheckbox" id="checkbox-${item.access_block_id}" value="${item.access_block_id}" /></td>
                        <td>${item.access_block_id}</td>
                        <td>${item.user_id}</td>
                        <td>${item.name}</td>
                        <td>${item.remarks}</td>
                        <td>${item.updated_by}</td>
                        <td>${item.qr_code}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <button class="btn btn-danger mt-3" onclick="deleteSelectedEntries()">Delete Selected</button>
    </div>
</div>

<script>
    $(function () {
        $('#example1').DataTable({
            "paging": true,
            "lengthChange": false,
            "searching": false,
            "ordering": false,
            "info": true,
            "autoWidth": false,
            "responsive": true,
            "pageLength": 100
        });
    });

    document.getElementById("divTitle").innerHTML = "Block Access Register";
</script>
