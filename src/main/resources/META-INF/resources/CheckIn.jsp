 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 

<c:set var="empDetails" value='${requestScope["outputObject"].get("empDetails")}' />
<c:set var="checkInType" value='${requestScope["outputObject"].get("checkInType")}' />
<c:set var="lstLastCheckIns" value='${requestScope["outputObject"].get("lstLastCheckIns")}' />


<link rel="stylesheet" href="./QRFolder/custom.css">
<link rel="stylesheet" href="./QRFolder/sharebar.css">
<link rel="alternate" type="application/rss+xml" title="Minhaz&#39;s Blog" href="https://blog.minhazav.dev/feed.xml">
<link rel="shortcut icon" href="https://blog.minhazav.dev/assets/favicon.ico">
<link rel="icon" type="image/png" sizes="32x32" href="https://blog.minhazav.dev/assets/favicon.ico">




<script type="text/javascript" async="" src="./QRFolder/analytics.js.download"></script><script src="./QRFolder/f.txt"></script><script src="./QRFolder/f(1).txt" id="google_shimpl"></script><script type="application/ld+json">
{"@context":"https://schema.org","@type":"WebPage","description":"Demo of a cross platform HTML5 QR Code scanner","headline":"HTML5 QR Code Scanner Demo","publisher":{"@type":"Organization","logo":{"@type":"ImageObject","url":"https://blog.minhazav.dev/images/rsz_self_1_1.jpg"}},"url":"https://blog.minhazav.dev/research/html5-qrcode"}</script>
<meta http-equiv="origin-trial" content="Az6AfRvI8mo7yiW5fLfj04W21t0ig6aMsGYpIqMTaX60H+b0DkO1uDr+7BrzMcimWzv/X7SXR8jI+uvbV0IJlwYAAACFeyJvcmlnaW4iOiJodHRwczovL2RvdWJsZWNsaWNrLm5ldDo0NDMiLCJmZWF0dXJlIjoiUHJpdmFjeVNhbmRib3hBZHNBUElzIiwiZXhwaXJ5IjoxNjgwNjUyNzk5LCJpc1N1YmRvbWFpbiI6dHJ1ZSwiaXNUaGlyZFBhcnR5Ijp0cnVlfQ=="><meta http-equiv="origin-trial" content="A+USTya+tNvDPaxUgJooz+LaVk5hPoAxpLvSxjogX4Mk8awCTQ9iop6zJ9d5ldgU7WmHqBlnQB41LHHRFxoaBwoAAACLeyJvcmlnaW4iOiJodHRwczovL2dvb2dsZXN5bmRpY2F0aW9uLmNvbTo0NDMiLCJmZWF0dXJlIjoiUHJpdmFjeVNhbmRib3hBZHNBUElzIiwiZXhwaXJ5IjoxNjgwNjUyNzk5LCJpc1N1YmRvbWFpbiI6dHJ1ZSwiaXNUaGlyZFBhcnR5Ijp0cnVlfQ=="><meta http-equiv="origin-trial" content="A7FovoGr67TUBYbnY+Z0IKoJbbmRmB8fCyirUGHavNDtD91CiGyHHSA2hDG9r9T3NjUKFi6egL3RbgTwhhcVDwUAAACLeyJvcmlnaW4iOiJodHRwczovL2dvb2dsZXRhZ3NlcnZpY2VzLmNvbTo0NDMiLCJmZWF0dXJlIjoiUHJpdmFjeVNhbmRib3hBZHNBUElzIiwiZXhwaXJ5IjoxNjgwNjUyNzk5LCJpc1N1YmRvbWFpbiI6dHJ1ZSwiaXNUaGlyZFBhcnR5Ijp0cnVlfQ=="><meta http-equiv="origin-trial" content="Az6AfRvI8mo7yiW5fLfj04W21t0ig6aMsGYpIqMTaX60H+b0DkO1uDr+7BrzMcimWzv/X7SXR8jI+uvbV0IJlwYAAACFeyJvcmlnaW4iOiJodHRwczovL2RvdWJsZWNsaWNrLm5ldDo0NDMiLCJmZWF0dXJlIjoiUHJpdmFjeVNhbmRib3hBZHNBUElzIiwiZXhwaXJ5IjoxNjgwNjUyNzk5LCJpc1N1YmRvbWFpbiI6dHJ1ZSwiaXNUaGlyZFBhcnR5Ijp0cnVlfQ=="><meta http-equiv="origin-trial" content="A+USTya+tNvDPaxUgJooz+LaVk5hPoAxpLvSxjogX4Mk8awCTQ9iop6zJ9d5ldgU7WmHqBlnQB41LHHRFxoaBwoAAACLeyJvcmlnaW4iOiJodHRwczovL2dvb2dsZXN5bmRpY2F0aW9uLmNvbTo0NDMiLCJmZWF0dXJlIjoiUHJpdmFjeVNhbmRib3hBZHNBUElzIiwiZXhwaXJ5IjoxNjgwNjUyNzk5LCJpc1N1YmRvbWFpbiI6dHJ1ZSwiaXNUaGlyZFBhcnR5Ijp0cnVlfQ=="><meta http-equiv="origin-trial" content="A7FovoGr67TUBYbnY+Z0IKoJbbmRmB8fCyirUGHavNDtD91CiGyHHSA2hDG9r9T3NjUKFi6egL3RbgTwhhcVDwUAAACLeyJvcmlnaW4iOiJodHRwczovL2dvb2dsZXRhZ3NlcnZpY2VzLmNvbTo0NDMiLCJmZWF0dXJlIjoiUHJpdmFjeVNhbmRib3hBZHNBUElzIiwiZXhwaXJ5IjoxNjgwNjUyNzk5LCJpc1N1YmRvbWFpbiI6dHJ1ZSwiaXNUaGlyZFBhcnR5Ijp0cnVlfQ=="><link rel="preload" href="./QRFolder/f(2).txt" as="script"><script type="text/javascript" src="./QRFolder/f(2).txt"></script><link rel="preload" href="./QRFolder/f(3).txt" as="script"><script type="text/javascript" src="./QRFolder/f(3).txt"></script></head>
 <body data-instant-allow-query-string="" data-instant-allow-external-links="" cz-shortcut-listen="true">
 
   <main class="default-content" aria-label="Content">
     <div class="post-sidebar-right" style="position: fixed;">
   
   

   
   
</div>
     <div class="wrapper-content">
      <link rel="canonical" href="https://blog.minhazav.dev/research/html5-qrcode">
<style>
#reader {
    width: 640px;
}
@media(max-width: 600px) {
	#reader {
		width: 300px;
	}
}
.empty {
    display: block;
    width: 100%;
    height: 20px;
}
.alert {
    padding: 15px;
    margin-bottom: 20px;
    border: 1px solid transparent;
    border-radius: 4px;
}
.alert-info {
    color: #31708f;
    background-color: #d9edf7;
    border-color: #bce8f1;
}
.alert-success {
    color: #3c763d;
    background-color: #dff0d8;
    border-color: #d6e9c6;
}
#scanapp_ad {
    display: none;
}
@media(max-width: 1500px) {
	#scanapp_ad {
		display: block;
	}
}
</style>


<div class="container">
	<div class="row">
		<div class="col-md-12" style="text-align: center;margin-bottom: 20px;">
			<div id="reader" style="display: inline-block; position: relative; padding: 0px; border: 1px solid silver;"><div style="text-align: left; margin: 0px;"><div style="position: absolute; top: 10px; right: 10px; z-index: 2; display: none; padding: 5pt; border: 1px solid rgb(23, 23, 23); font-size: 10pt; background: rgba(0, 0, 0, 0.69); border-radius: 5px; text-align: center; font-weight: 400; color: white;">Powered by <a href="https://scanapp.org/" target="new" style="color: white;">ScanApp</a><br><br><a href="https://github.com/mebjas/html5-qrcode/issues" target="new" style="color: white;">Report issues</a></div><img alt="Info icon" src="data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCA0NjAgNDYwIiBzdHlsZT0iZW5hYmxlLWJhY2tncm91bmQ6bmV3IDAgMCA0NjAgNDYwIiB4bWw6c3BhY2U9InByZXNlcnZlIj48cGF0aCBkPSJNMjMwIDBDMTAyLjk3NSAwIDAgMTAyLjk3NSAwIDIzMHMxMDIuOTc1IDIzMCAyMzAgMjMwIDIzMC0xMDIuOTc0IDIzMC0yMzBTMzU3LjAyNSAwIDIzMCAwem0zOC4zMzMgMzc3LjM2YzAgOC42NzYtNy4wMzQgMTUuNzEtMTUuNzEgMTUuNzFoLTQzLjEwMWMtOC42NzYgMC0xNS43MS03LjAzNC0xNS43MS0xNS43MVYyMDIuNDc3YzAtOC42NzYgNy4wMzMtMTUuNzEgMTUuNzEtMTUuNzFoNDMuMTAxYzguNjc2IDAgMTUuNzEgNy4wMzMgMTUuNzEgMTUuNzFWMzc3LjM2ek0yMzAgMTU3Yy0yMS41MzkgMC0zOS0xNy40NjEtMzktMzlzMTcuNDYxLTM5IDM5LTM5IDM5IDE3LjQ2MSAzOSAzOS0xNy40NjEgMzktMzkgMzl6Ii8+PC9zdmc+" style="position: absolute; top: 4px; right: 4px; opacity: 0.6; cursor: pointer; z-index: 2; width: 16px; height: 16px;"><div id="reader__header_message" style="display: none; text-align: center; font-size: 14px; padding: 2px 10px; margin: 4px; border-top: 1px solid rgb(246, 246, 246);"></div></div><div id="reader__scan_region" style="width: 100%; min-height: 100px; text-align: center;"><br><img width="64" src="data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCAzNzEuNjQzIDM3MS42NDMiIHN0eWxlPSJlbmFibGUtYmFja2dyb3VuZDpuZXcgMCAwIDM3MS42NDMgMzcxLjY0MyIgeG1sOnNwYWNlPSJwcmVzZXJ2ZSI+PHBhdGggZD0iTTEwNS4wODQgMzguMjcxaDE2My43Njh2MjBIMTA1LjA4NHoiLz48cGF0aCBkPSJNMzExLjU5NiAxOTAuMTg5Yy03LjQ0MS05LjM0Ny0xOC40MDMtMTYuMjA2LTMyLjc0My0yMC41MjJWMzBjMC0xNi41NDItMTMuNDU4LTMwLTMwLTMwSDEyNS4wODRjLTE2LjU0MiAwLTMwIDEzLjQ1OC0zMCAzMHYxMjAuMTQzaC04LjI5NmMtMTYuNTQyIDAtMzAgMTMuNDU4LTMwIDMwdjEuMzMzYTI5LjgwNCAyOS44MDQgMCAwIDAgNC42MDMgMTUuOTM5Yy03LjM0IDUuNDc0LTEyLjEwMyAxNC4yMjEtMTIuMTAzIDI0LjA2MXYxLjMzM2MwIDkuODQgNC43NjMgMTguNTg3IDEyLjEwMyAyNC4wNjJhMjkuODEgMjkuODEgMCAwIDAtNC42MDMgMTUuOTM4djEuMzMzYzAgMTYuNTQyIDEzLjQ1OCAzMCAzMCAzMGg4LjMyNGMuNDI3IDExLjYzMSA3LjUwMyAyMS41ODcgMTcuNTM0IDI2LjE3Ny45MzEgMTAuNTAzIDQuMDg0IDMwLjE4NyAxNC43NjggNDUuNTM3YTkuOTg4IDkuOTg4IDAgMCAwIDguMjE2IDQuMjg4IDkuOTU4IDkuOTU4IDAgMCAwIDUuNzA0LTEuNzkzYzQuNTMzLTMuMTU1IDUuNjUtOS4zODggMi40OTUtMTMuOTIxLTYuNzk4LTkuNzY3LTkuNjAyLTIyLjYwOC0xMC43Ni0zMS40aDgyLjY4NWMuMjcyLjQxNC41NDUuODE4LjgxNSAxLjIxIDMuMTQyIDQuNTQxIDkuMzcyIDUuNjc5IDEzLjkxMyAyLjUzNCA0LjU0Mi0zLjE0MiA1LjY3Ny05LjM3MSAyLjUzNS0xMy45MTMtMTEuOTE5LTE3LjIyOS04Ljc4Ny0zNS44ODQgOS41ODEtNTcuMDEyIDMuMDY3LTIuNjUyIDEyLjMwNy0xMS43MzIgMTEuMjE3LTI0LjAzMy0uODI4LTkuMzQzLTcuMTA5LTE3LjE5NC0xOC42NjktMjMuMzM3YTkuODU3IDkuODU3IDAgMCAwLTEuMDYxLS40ODZjLS40NjYtLjE4Mi0xMS40MDMtNC41NzktOS43NDEtMTUuNzA2IDEuMDA3LTYuNzM3IDE0Ljc2OC04LjI3MyAyMy43NjYtNy42NjYgMjMuMTU2IDEuNTY5IDM5LjY5OCA3LjgwMyA0Ny44MzYgMTguMDI2IDUuNzUyIDcuMjI1IDcuNjA3IDE2LjYyMyA1LjY3MyAyOC43MzMtLjQxMyAyLjU4NS0uODI0IDUuMjQxLTEuMjQ1IDcuOTU5LTUuNzU2IDM3LjE5NC0xMi45MTkgODMuNDgzLTQ5Ljg3IDExNC42NjEtNC4yMjEgMy41NjEtNC43NTYgOS44Ny0xLjE5NCAxNC4wOTJhOS45OCA5Ljk4IDAgMCAwIDcuNjQ4IDMuNTUxIDkuOTU1IDkuOTU1IDAgMCAwIDYuNDQ0LTIuMzU4YzQyLjY3Mi0zNi4wMDUgNTAuODAyLTg4LjUzMyA1Ni43MzctMTI2Ljg4OC40MTUtMi42ODQuODIxLTUuMzA5IDEuMjI5LTcuODYzIDIuODM0LTE3LjcyMS0uNDU1LTMyLjY0MS05Ljc3Mi00NC4zNDV6bS0yMzIuMzA4IDQyLjYyYy01LjUxNCAwLTEwLTQuNDg2LTEwLTEwdi0xLjMzM2MwLTUuNTE0IDQuNDg2LTEwIDEwLTEwaDE1djIxLjMzM2gtMTV6bS0yLjUtNTIuNjY2YzAtNS41MTQgNC40ODYtMTAgMTAtMTBoNy41djIxLjMzM2gtNy41Yy01LjUxNCAwLTEwLTQuNDg2LTEwLTEwdi0xLjMzM3ptMTcuNSA5My45OTloLTcuNWMtNS41MTQgMC0xMC00LjQ4Ni0xMC0xMHYtMS4zMzNjMC01LjUxNCA0LjQ4Ni0xMCAxMC0xMGg3LjV2MjEuMzMzem0zMC43OTYgMjguODg3Yy01LjUxNCAwLTEwLTQuNDg2LTEwLTEwdi04LjI3MWg5MS40NTdjLS44NTEgNi42NjgtLjQzNyAxMi43ODcuNzMxIDE4LjI3MWgtODIuMTg4em03OS40ODItMTEzLjY5OGMtMy4xMjQgMjAuOTA2IDEyLjQyNyAzMy4xODQgMjEuNjI1IDM3LjA0IDUuNDQxIDIuOTY4IDcuNTUxIDUuNjQ3IDcuNzAxIDcuMTg4LjIxIDIuMTUtMi41NTMgNS42ODQtNC40NzcgNy4yNTEtLjQ4Mi4zNzgtLjkyOS44LTEuMzM1IDEuMjYxLTYuOTg3IDcuOTM2LTExLjk4MiAxNS41Mi0xNS40MzIgMjIuNjg4aC05Ny41NjRWMzBjMC01LjUxNCA0LjQ4Ni0xMCAxMC0xMGgxMjMuNzY5YzUuNTE0IDAgMTAgNC40ODYgMTAgMTB2MTM1LjU3OWMtMy4wMzItLjM4MS02LjE1LS42OTQtOS4zODktLjkxNC0yNS4xNTktMS42OTQtNDIuMzcgNy43NDgtNDQuODk4IDI0LjY2NnoiLz48cGF0aCBkPSJNMTc5LjEyOSA4My4xNjdoLTI0LjA2YTUgNSAwIDAgMC01IDV2MjQuMDYxYTUgNSAwIDAgMCA1IDVoMjQuMDZhNSA1IDAgMCAwIDUtNVY4OC4xNjdhNSA1IDAgMCAwLTUtNXpNMTcyLjYyOSAxNDIuODZoLTEyLjU2VjEzMC44YTUgNSAwIDEgMC0xMCAwdjE3LjA2MWE1IDUgMCAwIDAgNSA1aDE3LjU2YTUgNSAwIDEgMCAwLTEwLjAwMXpNMjE2LjU2OCA4My4xNjdoLTI0LjA2YTUgNSAwIDAgMC01IDV2MjQuMDYxYTUgNSAwIDAgMCA1IDVoMjQuMDZhNSA1IDAgMCAwIDUtNVY4OC4xNjdhNSA1IDAgMCAwLTUtNXptLTUgMjQuMDYxaC0xNC4wNlY5My4xNjdoMTQuMDZ2MTQuMDYxek0yMTEuNjY5IDEyNS45MzZIMTk3LjQxYTUgNSAwIDAgMC01IDV2MTQuMjU3YTUgNSAwIDAgMCA1IDVoMTQuMjU5YTUgNSAwIDAgMCA1LTV2LTE0LjI1N2E1IDUgMCAwIDAtNS01eiIvPjwvc3ZnPg==" style="opacity: 0.8;"></div><div id="reader__dashboard" style="width: 100%;"><div id="reader__dashboard_section" style="width: 100%; padding: 10px 0px; text-align: left;"><div><div id="reader__dashboard_section_csr" style="display: block;"><div style="text-align: center;"><button id="html5-qrcode-button-camera-permission" class="html5-qrcode-element">Request Camera Permissions</button></div></div><div style="text-align: center; margin: auto auto 10px; width: 80%; max-width: 600px; border: 6px dashed rgb(235, 235, 235); padding: 10px; display: none;"><label for="html5-qrcode-private-filescan-input" style="display: inline-block;"><button id="html5-qrcode-button-file-selection" class="html5-qrcode-element">Choose Image - No image choosen</button><input id="html5-qrcode-private-filescan-input" class="html5-qrcode-element" type="file" accept="image/*" style="display: none;"></label></div></div><div style="text-align: center;"></div></div></div></div>
			<div class="empty"></div>
			<div id="scanned-result"></div>
		</div>
	</div>
</div>

<div class="card">

<br>




<div class="row">



</div>


                    
              
              
              
              
              <!-- /.card-header -->
              <div class="card-body table-responsive p-0" style="height: 580px;">                
                <table id="example1" class="table table-head-fixed  table-bordered table-striped dataTable dtr-inline" role="grid" aria-describedby="example1_info">
                
                
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
                                    
                     
                     
                    </tr>
                  </thead>
                  <tbody>
				<c:forEach items="${lstLastCheckIns}" var="check">
					<tr >
					
						<td>${check.name}</td>
						<td>${check.checked_time}</td>
						
						<td>
						
						<c:if test="${check.check_in_type eq 'I'}">							  				
							Check In			  									  
						</c:if>
						
						
						<c:if test="${check.check_in_type eq 'O'}">							  				
							Check Out			  									  
						</c:if>
												
						</td>
											
						
						
						
						
						
					</tr>
				</c:forEach>
				
				
                  </tbody>
                </table>
              </div>
              <!-- /.card-body -->
            </div>










<script src="./QRFolder/highlight.min.js.download"></script>
<script src="./QRFolder/html5-qrcode.min.v2.3.0.js.download"></script>
<script>
function docReady(fn) {
    // see if DOM is already available
    if (document.readyState === "complete" || document.readyState === "interactive") {
        // call on next available tick
        setTimeout(fn, 1);
    } else {
        document.addEventListener("DOMContentLoaded", fn);
    }
}

docReady(function() {
	hljs.initHighlightingOnLoad();
	var lastMessage;
	var codeId = 0;
	function onScanSuccess(decodedText, decodedResult) {
		beep();
		html5QrCode.stop();
		
		
		
		//document.getElementById("closebutton").style.display='none';
 	   //document.getElementById("loader").style.display='block';
 	//$('#myModal').modal({backdrop: 'static', keyboard: false});;

 	var xhttp = new XMLHttpRequest();
 	  xhttp.onreadystatechange = function() 
 	  {
 	    if (xhttp.readyState == 4 && xhttp.status == 200) 
 	    { 	      
 	    	if(xhttp.responseText.includes('alreadyCaptured'))
 	    		{
 	      			alert(xhttp.responseText);
 	    		}
 	    	else
 	    		{
	 	      var resposneTextReceived=xhttp.responseText.split('|');
              
              if(resposneTextReceived[0].includes("Access is blocked for this employee"))
              {
                    errorBeep();
                    alert(resposneTextReceived[0]);
              }
              else
              {
	 	        alert(resposneTextReceived[0]);
              }
	 	      var details=resposneTextReceived[1].split('~');
	 	      //alert(details);
	 	      var checkString;
	 	      if(details[2]=="O"){checkString="Check Out";} else {checkString="Check In";}
	 	     $("#example1 tbody").prepend("<tr><td>"+details[0]+"</td><td>"+details[1]+"</td><td>"+checkString+"</td></tr>");
			
	 	     
 	    		}
 	    	html5QrCode.start({ facingMode: { exact: "environment"} }, config, onScanSuccess);
 	     
 		  //window.location.reload();
 		}
 	  };
 	  xhttp.open("GET","?a=checkInThisEmployee&qr_code="+decodedText, true);    
 	  xhttp.send();
		
		
        //alert(decodedText);        
        return;
		if (lastMessage !== decodedText) {
			lastMessage = decodedText;
            printScanResultPretty(codeId, decodedText, decodedResult);
            ++codeId;
		}
	}
    var qrboxFunction = function(viewfinderWidth, viewfinderHeight) {
        // Square QR Box, with size = 80% of the min edge.
        var minEdgeSizeThreshold = 250;
        var edgeSizePercentage = 0.75;
        var minEdgeSize = (viewfinderWidth > viewfinderHeight) ?
            viewfinderHeight : viewfinderWidth;
        var qrboxEdgeSize = Math.floor(minEdgeSize * edgeSizePercentage);
        if (qrboxEdgeSize < minEdgeSizeThreshold) {
            if (minEdgeSize < minEdgeSizeThreshold) {
                return {width: minEdgeSize, height: minEdgeSize};
            } else {
                return {
                    width: minEdgeSizeThreshold,
                    height: minEdgeSizeThreshold
                };
            }
        }
        return {width: qrboxEdgeSize, height: qrboxEdgeSize};
    }
	let html5QrcodeScanner = new Html5QrcodeScanner(
        "reader", 
        { 
            fps: 10,
            qrbox: qrboxFunction,
            // Important notice: this is experimental feature, use it at your
            // own risk. See documentation in
            // mebjas@/html5-qrcode/src/experimental-features.ts
            experimentalFeatures: {
                useBarCodeDetectorIfSupported: true
            },
            rememberLastUsedCamera: true,
            supportedScanTypes: [Html5QrcodeScanType.SCAN_TYPE_CAMERA],
            showTorchButtonIfSupported: true,
            disableFlip:true
        });
	//html5QrcodeScanner.render(onScanSuccess);
	
	const html5QrCode = new Html5Qrcode("reader",{ 
        fps: 10,
        qrbox: qrboxFunction,
        // Important notice: this is experimental feature, use it at your
        // own risk. See documentation in
        // mebjas@/html5-qrcode/src/experimental-features.ts
        experimentalFeatures: {
            useBarCodeDetectorIfSupported: true
        },
        rememberLastUsedCamera: true,
        supportedScanTypes: [Html5QrcodeScanType.SCAN_TYPE_CAMERA],
        showTorchButtonIfSupported: true,
        disableFlip:true
    })
	
	const config = { fps: 10, qrbox: qrboxFunction ,experimentalFeatures: {
        useBarCodeDetectorIfSupported: true
    }};
	
	html5QrCode.start({ facingMode: { exact: "environment"} }, config, onScanSuccess);

});


function beep() {
    var snd = new  Audio("data:audio/wav;base64,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qJrx4KOQjahZxWbcZgztj2c49nKmkId44S71j0c8eV9yDK6uPRzx5X18eDvjvQ6yKo9ZSS6l//8elePK/Lf//IInrOF/FvDoADYAGBMGb7FtErm5MXMlmPAJQVgWta7Zx2go+8xJ0UiCb8LHHdftWyLJE0QIAIsI+UbXu67dZMjmgDGCGl1H+vpF4NSDckSIkk7Vd+sxEhBQMRU8j/12UIRhzSaUdQ+rQU5kGeFxm+hb1oh6pWWmv3uvmReDl0UnvtapVaIzo1jZbf/pD6ElLqSX+rUmOQNpJFa/r+sa4e/pBlAABoAAAAA3CUgShLdGIxsY7AUABPRrgCABdDuQ5GC7DqPQCgbbJUAoRSUj+NIEig0YfyWUho1VBBBA//uQZB4ABZx5zfMakeAAAAmwAAAAF5F3P0w9GtAAACfAAAAAwLhMDmAYWMgVEG1U0FIGCBgXBXAtfMH10000EEEEEECUBYln03TTTdNBDZopopYvrTTdNa325mImNg3TTPV9q3pmY0xoO6bv3r00y+IDGid/9aaaZTGMuj9mpu9Mpio1dXrr5HERTZSmqU36A3CumzN/9Robv/Xx4v9ijkSRSNLQhAWumap82WRSBUqXStV/YcS+XVLnSS+WLDroqArFkMEsAS+eWmrUzrO0oEmE40RlMZ5+ODIkAyKAGUwZ3mVKmcamcJnMW26MRPgUw6j+LkhyHGVGYjSUUKNpuJUQoOIAyDvEyG8S5yfK6dhZc0Tx1KI/gviKL6qvvFs1+bWtaz58uUNnryq6kt5RzOCkPWlVqVX2a/EEBUdU1KrXLf40GoiiFXK///qpoiDXrOgqDR38JB0bw7SoL+ZB9o1RCkQjQ2CBYZKd/+VJxZRRZlqSkKiws0WFxUyCwsKiMy7hUVFhIaCrNQsKkTIsLivwKKigsj8XYlwt/WKi2N4d//uQRCSAAjURNIHpMZBGYiaQPSYyAAABLAAAAAAAACWAAAAApUF/Mg+0aohSIRobBAsMlO//Kk4soosy1JSFRYWaLC4qZBYWFRGZdwqKiwkNBVmoWFSJkWFxX4FFRQWR+LsS4W/rFRb/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////VEFHAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAU291bmRib3kuZGUAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAMjAwNGh0dHA6Ly93d3cuc291bmRib3kuZGUAAAAAAAAAACU=");  
    snd.play();
}

function errorBeep()
{
    var err=new Audio("data:audio/wav;base64,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iDkw2FNDkO8xqacbnsMbrFM6Er3prdKy8l39126v/6vkf/9v/tVJU4DUHPTEDSyrQlNEJrtygaF0iEMqrjtZATXHaIF9Sqi7SH3ywKzhxdIC3hLRcJ3icPB1g8mUjtFWLwuz76ZSAwb41+i0eprJMsnuw11/Xwm5GPXLt5L//6v68ECy//tSxOYDy2yzGA5gxclmF+LBvJi5jmgFSASlcpmx3MIw5CLkXrVaQciqssnQV1IX7Mw+uKiO/VwT/5NQZDvTYKPM2TDpTnuyrq1fGMvqhcEsr9y87wX1dJbE6lqOdH85IyS4Z2u6e4U+3/6lDiTJ+gqRwVCEXRBAkU1NWFy5KPQklmzyUSYvZqbolJZ2s1v+o6YOXMmkcLZOWrTYIs3elnMjSYS29LfFm/93otjUQaCYdMOFZZgK3vWcmmQT0oWKPQnTYxqgzei/YDN4q1SLYvT/+1LE6YMKrLUcTWEBgYycokGmGWDJgIAY2jKBjWOrUllI1JqzHttYWucb/oChlNYesOq1Wrw1NS8Ic1+zMzeqms+k115wVUCoywyAqZH+GON+l1apsXHiX5Teu4dFpVNafKSKpUXG/Bf+/u+KTEFNRTMuMTAwqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqABa+zlMFDAg5Ds7fuxgoIEcjpoRmnyMirmRn+RmXyM///zUMDBo5Gf/7UsTrAwto6RINMMrBZRwiRYYY+DWQy//7LPP5UMmUMGBo6VD///kv6tQQJyMmWWl/zImWWVDaoZGoYKFCOhkwUMCDoZGrBQaVTEFNRTMuMTAwVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV//tSxO6ADMS/Cgeww8FbHyCkMI+BVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVX/+1LE0APLjaLCQQR1AAAANIAAAARVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVQ==")
    err.play();
}


</script>
     </div>
   </main><ins class="adsbygoogle adsbygoogle-noablate" data-adsbygoogle-status="done" style="display: none !important;" data-ad-status="unfilled"><div id="aswift_0_host" tabindex="0" title="Advertisement" aria-label="Advertisement" style="border: none; height: 0px; width: 0px; margin: 0px; padding: 0px; position: relative; visibility: visible; background-color: transparent; display: inline-block;"><iframe id="aswift_0" name="aswift_0" style="left:0;position:absolute;top:0;border:0;width:undefinedpx;height:undefinedpx;" sandbox="allow-forms allow-popups allow-popups-to-escape-sandbox allow-same-origin allow-scripts allow-top-navigation-by-user-activation" frameborder="0" marginwidth="0" marginheight="0" vspace="0" hspace="0" allowtransparency="true" scrolling="no" src="./QRFolder/ads.html" data-google-container-id="a!1" data-load-complete="true"></iframe></div></ins>
   
    <script async="" src="./QRFolder/js"></script>
    <script>
        window.dataLayer = window.dataLayer || [];
        function gtag(){dataLayer.push(arguments);}
        gtag('js', new Date());
        gtag('config', 'UA-158605510-1');
    </script>
 

<iframe src="./QRFolder/aframe.html" width="0" height="0" style="display: none;"></iframe></body><iframe id="google_esf" name="google_esf" src="./QRFolder/zrt_lookup.html" style="display: none;"></iframe></html>