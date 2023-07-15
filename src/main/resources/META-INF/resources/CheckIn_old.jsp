  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
           
           
           
<c:set var="empDetails" value='${requestScope["outputObject"].get("empDetails")}' />
<c:set var="checkInType" value='${requestScope["outputObject"].get("checkInType")}' />
<c:set var="lstLastCheckIns" value='${requestScope["outputObject"].get("lstLastCheckIns")}' />





</head>





	




<br>



<div class="container" style="padding:20px;background-color:white">

<div class="col-sm-12" align="center">
  		
  		
  							  				
					  									  
			 	
		
				
	</div>	

<form id="frm" action="?a=addBrand" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
<div class="row">


<div class="col-sm-12">
  	<div align="center" class="form-group">
  	
  	
  	<div style="width: 200px;height:200px" id="reader"></div>
  	
      
    </div>
  </div>


  
  
  
  <br>
 	
	
</div>

</form>
</div>
<br>
<br>
<div class="card">

<br>




<div class="row">



</div>


                    
              
              
              
              
              <!-- /.card-header -->
              <div class="card-body table-responsive p-0" style="height: 580px;">                
                <table id="example1"class="table table-head-fixed  table-bordered table-striped dataTable dtr-inline" role="grid" aria-describedby="example1_info">
                
                
                <thead>
                    <tr align="center" >
                     <th align="center" colspan="5">Recent Check In/ Check Out </th>
                     
                    </tr>
                  </thead>
                
                  <thead>
                    <tr>
                    <th><b>Employee Name</b></th>
                     <th><b>Check Time</b></th>
                     <th><b>Type</b></th>    
                                    
                     <th></th>
                     
                    </tr>
                  </thead>
                  <tbody>
				<c:forEach items="${lstLastCheckIns}" var="check">
					<tr >
					
						<td>${check.name}</td>
						<td>${check.checked_time}</td>
						<td>${check.check_in_type}</td>
						<td>
						
						<c:if test="${check.check_in_type eq 'I'}">							  				
							Check In			  									  
						</c:if>
						
						
						<c:if test="${check.check_in_type eq 'O'}">							  				
							Check Out			  									  
						</c:if>
												
						</td>
											
						
						<td>
							<a href="#" onclick='openLocation(${check.latitude},${check.longitude})'>
								${check.latitude} ${check.longitude}								
							</a>
						</td>
						<td>${check.remarks}</td>
						
						
					</tr>
				</c:forEach>
				
				
                  </tbody>
                </table>
              </div>
              <!-- /.card-body -->
            </div>

<script>
	
	
	
		
		
		
	<c:if test="${checkInType eq 'O'}">							  				
		document.getElementById("divTitle").innerHTML="Check In";			  									  
	</c:if>
	
	
		<c:if test="${checkInType eq 'I'}">							  				
			document.getElementById("divTitle").innerHTML="Check out";			  									  
		</c:if>
		
		function openLocation(latitude,longitude)
		{
			window.open("https://maps.google.com/maps?q=loc:"+latitude+","+longitude);
		}
		
	
</script>


<script src="js/html5-qrcode.min.js"></script>
<script>

const html5QrCode = new Html5Qrcode("reader");
const qrCodeSuccessCallback = (decodedText, decodedResult) => {
    /* handle success */
};
const config = { fps: 10, qrbox: { width: 100, height: 100} };

// If you want to prefer front camera
//html5QrCode.start({ facingMode: "user" }, config, qrCodeSuccessCallback);

// If you want to prefer back camera
html5QrCode.start({ facingMode: "environment" }, config, onScanSuccess);


        
function onScanSuccess(decodedText, decodedResult) {
    	
    	  document.getElementById("closebutton").style.display='none';
    	   document.getElementById("loader").style.display='block';
    	$('#myModal').modal({backdrop: 'static', keyboard: false});;

    	var xhttp = new XMLHttpRequest();
    	  xhttp.onreadystatechange = function() 
    	  {
    	    if (xhttp.readyState == 4 && xhttp.status == 200) 
    	    { 	      
    	      alert(xhttp.responseText);
    		  window.location.reload();
    		}
    	  };
    	  xhttp.open("GET","?a=checkInThisEmployee&aadhaar_card_no="+decodedText, true);    
    	  xhttp.send();
    
    
    
}



</script>




