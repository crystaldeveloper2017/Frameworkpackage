<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>



<c:set var="auditList" value='${requestScope["outputObject"].get("auditList")}' />
<c:set var="latestUserHits" value='${requestScope["outputObject"].get("latestUserHits")}' />


<c:set var="memoryStats" value='${requestScope["outputObject"].get("memoryStats")}' />
<c:set var="activeConnections" value='${requestScope["outputObject"].get("activeConnections")}' />



<br>
<div class="card">









           <div class="card-header">    
                
                
                
                
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


	<div class="card-body table-responsive p-0" style="height: 580px;">
		<table id="example1"
			class="table table-head-fixed  table-bordered table-striped dataTable dtr-inline"
			role="grid" aria-describedby="example1_info">
			<thead>
				<tr>
					<th><b>Stats</b></th>
					
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${memoryStats}" var="item">
					<tr>
						<td>${item}</td>
						
					</tr>
				</c:forEach>
				
				
				<tr>
					<td>activeConnections</td>
					<td>${ activeConnections}</td>
				</tr>


			</tbody>
		</table>
	</div>

	<div class="card-body table-responsive p-0" style="height: 580px;">
		<table id="example1"
			class="table table-head-fixed  table-bordered table-striped dataTable dtr-inline"
			role="grid" aria-describedby="example1_info">
			<thead>
				<tr>
					<th><b>Username</b></th>
					<th><b>Last Accessed Time</b></th>
					
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${latestUserHits}" var="item">
					<tr>
						<td><a href="?a=showAuditTrail&username=${item.user_name}">${item.user_name}</a></td>
						<td>${item.T1}</td>
						<td>${item.appName}</td>						
					</tr>
				</c:forEach>
				
				
				<tr>
					<td>activeConnections</td>
					<td>${ activeConnections}</td>
				</tr>


			</tbody>
		</table>
	</div>


	<!-- /.card-header -->
              <div class="card-body table-responsive p-0" style="height: 580px;">                
                <table id="example1"class="table table-head-fixed  table-bordered table-striped dataTable dtr-inline" role="grid" aria-describedby="example1_info">
                  <thead>
                    <tr>
                     <th><b>Audit ID</b></th>
                     <th><b>User Name</b></th>
                     <th><b>url</b></th>
                     <th><b>Parameters </b></th>
                     <th><b>Accessed Time</b></th>
                     <th><b>IP</b></th>
                     <th><b>Browser Name</b></th>                                         
                     <th></th><th></th>
                    </tr>
                  </thead>
                  <tbody>
				<c:forEach items="${auditList}" var="item">
					<tr >
						<td>${item.audit_id}</td>
						<td>${item.user_name}</td>
						<td>${item.url}</td>
						<td>${item.parameters}</td>
						<td>${item.accessed_time}</td>
						<td>${item.ip}</td>
						<td>${item.browser_name}</td>
						<td></td>						
						<td></td>												
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
      "pageLength": 100,
      "order": [[ 0, "desc" ]]
    });
  });
  
  document.getElementById("divTitle").innerHTML="Audit Trail";
  
</script>