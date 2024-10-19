<style>
    .date_field { position: relative; z-index: 1000; }
    .ui-datepicker { position: relative; z-index: 1000!important; }
</style>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Set the necessary variables from the backend -->
<c:set var="txtfromdate" value='${requestScope["outputObject"].get("txtfromdate")}' />
<c:set var="message" value='${requestScope["outputObject"].get("message")}' />
<c:set var="leaveList" value='${requestScope["outputObject"].get("leaveList")}' />
<c:set var="employeeList" value='${requestScope["outputObject"].get("employeeList")}' />

<!-- Date Selection Section (Only Once) -->
<div class="card">
    <br>
    <div class="row">
        <div class="col-sm-1" align="center">
            <label for="txtfromdate">Date</label>
        </div>
        <div class="col-sm-2" align="center">
            <div class="input-group input-group-sm" style="width: 200px;">
                <input type="text" id="txtfromdate" onchange="checkforvalidfromtodate();ReloadFilters();" 
                       name="txtfromdate" readonly class="form-control date_field" placeholder="From Date"/>
            </div>
        </div>
    </div>
    <br>
</div>

<!-- Table 1: Employee Attendance Summary -->
<div class="card">
    <div class="card-header">
        <h3 class="card-title">Employee Attendance Summary</h3>
    </div>
    <div class="card-body table-responsive p-0" style="height: 300px;">
        <table id="table1" class="table table-head-fixed table-bordered table-striped dataTable dtr-inline" role="grid">
            <thead>
                <tr>
                    <th><b>Type</b></th>
                    <th><b>Count</b></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${message}" var="item">
                    <tr>
                        <td>${item.type}</td>
                        <td>${item.count}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<!-- Table 2: Leave Summary -->
<div class="card">
    <div class="card-header">
        <h3 class="card-title">Employee On Leave Summary</h3>
    </div>
    <div class="card-body table-responsive p-0" style="height: 300px;">
        <table id="table2" class="table table-head-fixed table-bordered table-striped dataTable dtr-inline" role="grid">
            <thead>
                <tr>
                     <th><b>Type</b></th>
                    <th><b>Count</b></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${message}" var="item">
                    <tr>
                       <td>${item.type}</td>
                        <td>${item.count}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<!-- Table 3: Employee List -->
<div class="card">
    <div class="card-header">
        <h3 class="card-title">Employee On Leave Without Intimation</h3>
    </div>
    <div class="card-body table-responsive p-0" style="height: 300px;">
        <table id="table3" class="table table-head-fixed table-bordered table-striped dataTable dtr-inline" role="grid">
            <thead>
                <tr>
                    <th><b>Type</b></th>
                    <th><b>Count</b></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${message}" var="item">
                    <tr>
                      <td>${item.type}</td>
                        <td>${item.count}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<script>
  $(function () {
    $('#table1, #table2, #table3').DataTable({
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
  
  $( "#txtfromdate" ).datepicker({ dateFormat: 'dd/mm/yy' });

    document.getElementById("divTitle").innerHTML="Employee Attendance Summary";

  
  txtfromdate.value='${txtfromdate}';
  
  function checkforvalidfromtodate() {
      var fromDate = document.getElementById("txtfromdate").value;
      // Add your validation logic if needed
  }
  
  function ReloadFilters() {
      window.location = "?a=showAttendanceRegister&txtfromdate=" + txtfromdate.value;
  }
</script>
