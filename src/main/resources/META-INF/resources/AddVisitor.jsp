
 <style type="text/css">
            body {
              padding: 0;
              margin: 0;
            }

            svg:not(:root) {
              display: block;
            }

            .playable-code {
              background-color: #f4f7f8;
              border: none;
              border-left: 6px solid #558abb;
              border-width: medium medium medium 6px;
              color: #4d4e53;
              height: 100px;
              width: 90%;
              padding: 10px 10px 0;
            }

            .playable-canvas {
              border: 1px solid #4d4e53;
              border-radius: 2px;
            }

            .playable-buttons {
              text-align: right;
              width: 90%;
              padding: 5px 10px 5px 26px;
            }
        </style>
        
        <style type="text/css">
            #video {
  border: 1px solid black;
  box-shadow: 2px 2px 3px black;
  width:320px;
  height:240px;
}

#photo {
  border: 1px solid black;
  box-shadow: 2px 2px 3px black;
  width:320px;
  height:240px;
}

#canvas {
  display:none;
}

.camera {
  width: 340px;
  display:inline-block;
}

.output {
  width: 340px;
  display:inline-block;
  vertical-align: top;
}

#startbutton {
  display:block;
  position:relative;
  margin-left:auto;
  margin-right:auto;
  bottom:32px;
  background-color: rgba(0, 150, 0, 0.5);
  border: 1px solid rgba(255, 255, 255, 0.7);
  box-shadow: 0px 0px 1px 2px rgba(0, 0, 0, 0.2);
  font-size: 14px;
  font-family: "Lucida Grande", "Arial", sans-serif;
  color: rgba(255, 255, 255, 1.0);
}

.contentarea {
  font-size: 16px;
  font-family: "Lucida Grande", "Arial", sans-serif;
  width: 760px;
}

        </style>

  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="prefilledvisitorDetails" value='${requestScope["outputObject"].get("prefilledvisitorDetails")}' />
<c:set var="distinctPurposeOfVisist" value='${requestScope["outputObject"].get("distinctPurposeOfVisist")}' />
<c:set var="employeeList" value='${requestScope["outputObject"].get("employeeList")}' />
<c:set var="listOfEmployees" value='${requestScope["outputObject"].get("EmployeeList")}' />


</head>


<script>


function addVisitor()
{	

  var listofPhotosImage=document.getElementsByClassName("photosimg");
  for(var m=0;m<listofPhotosImage.length;m++)
  {
    hdnphotosbase64div.innerHTML+="<input type='hidden' name='hdnphotobase64"+m+"' value='"+document.getElementById("photo"+m).src+"'>";
  }


  

  
  
  
  
	
	 if(visitorname.value=="")
	  {
	  	
	  
	  toastr["error"]("Please enter Visitor Name");
   	  toastr.options = {"closeButton": false,"debug": false,"newestOnTop": false,"progressBar": false,
   	  "positionClass": "toast-top-right","preventDuplicates": false,"onclick": null,"showDuration": "1000",
   	  "hideDuration": "500","timeOut": "1000","extendedTimeOut": "1000","showEasing": "swing","hideEasing": "linear",
   	  "showMethod": "fadeIn","hideMethod": "fadeOut"
   	   };
	  	
	  	 
       	
   	visitorname.focus();
	  	return;
	  }
	 

	 if(purpose_of_visit.value=="")
	  {
	  	
	  
	  toastr["error"]("Please enter Purpose Of Visit");
  	  toastr.options = {"closeButton": false,"debug": false,"newestOnTop": false,"progressBar": false,
  	  "positionClass": "toast-top-right","preventDuplicates": false,"onclick": null,"showDuration": "1000",
  	  "hideDuration": "500","timeOut": "1000","extendedTimeOut": "1000","showEasing": "swing","hideEasing": "linear",
  	  "showMethod": "fadeIn","hideMethod": "fadeOut"
  	   };
	  	
	  	 
      	
  	purpose_of_visit.focus();
	  	return;
	  }
	 
	 if(document.getElementById("MobileNo").value=="" || document.getElementById("MobileNo").value.length!=10)
	  {
	  	
	  
	  toastr["error"]("Please enter Mobile Number");
	  toastr.options = {"closeButton": false,"debug": false,"newestOnTop": false,"progressBar": false,
	  "positionClass": "toast-top-right","preventDuplicates": false,"onclick": null,"showDuration": "1000",
	  "hideDuration": "500","timeOut": "1000","extendedTimeOut": "1000","showEasing": "swing","hideEasing": "linear",
	  "showMethod": "fadeIn","hideMethod": "fadeOut"
	   };
	  	
	  	 
   	
	  MobileNo.focus();
	  	return;
	  }
	 
	
	
	document.getElementById("frm").submit(); 
}

function checkforMatchEmployee()
{
	var searchString= document.getElementById("ContactToEmployee").value;
	
	var options1=document.getElementById("listOfEmployee").options;
	var employeeId=0;
	for(var x=0;x<options1.length;x++)
		{
			if(searchString==options1[x].value)
				{
					employeeId=options1[x].id;
					break;
				}
		}
	if(employeeId!=0)
		{
			document.getElementById("hdnselectedemployee").value=employeeId;			
			document.getElementById("ContactToEmployee").disabled=true;						
		}
	else
		{
			//searchForCustomer(searchString);
		}
	
	
}

function resetEmployee()
{	
	ContactToEmployee.disabled=false;
	ContactToEmployee.value="";
	hdnselectedemployee.value=0;
}





</script>

<datalist id="listOfEmployee">
<c:forEach items="${listOfEmployees}" var="cat">
 <option id="${cat.user_id}">${cat.name} </option>	
 </c:forEach>
</datalist>
<br>

<div class="container" style="padding:20px;background-color:white">


<form id="frm" action="?a=addVisitor" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
<input type="hidden" name="app_id" value="${userdetails.app_id}">
<input type="hidden" name="user_id" value="${userdetails.user_id}">
<input type="hidden" name="callerUrl" id="callerUrl" value="">

<div class="row">
  <div class="col-sm-12">
  	<div class="form-group">
      <label for="visitorname">Visitor Name*</label>
      <input type="text" class="form-control" id="visitorname" value="${prefilledvisitorDetails.visitor_name}" name="visitorname" placeholder="Visitor Name">
      <input type="hidden" name="hdnvisitorId" value="${prefilledvisitorDetails.visitor_id}" id="hdnvisitorId">
    </div>
  </div>
  
  <div class="col-sm-12">
  	<div class="form-group">
      <label for=Address>Address</label>
      <input type="text" class="form-control" id="address" value="${prefilledvisitorDetails.address}" name="address" placeholder="Address">
    </div>
  </div>
  
	<div class="col-sm-12">
		<div class="form-group">
			<label for="PurposeofVisit">Purpose Of Visit*</label>
			<input type="text" class="form-control form-control-sm" id="purpose_of_visit" placeholder="Eg. case" value="${prefilledvisitorDetails.purpose_of_visit}" list="datalistPurposes"  name="purpose_of_visit">
			<input type="hidden" name="drpVisitorId" value="${prefilledvisitorDetails.visitor_id}" id="drpVisitorId">
			<datalist id="datalistPurposes">
				<c:forEach items="${distinctPurposeOfVisist}" var="purpose">
				<option id="${purpose.purpose_of_visit}">${purpose.purpose_of_visit}</option>			    
				</c:forEach>	
			</datalist>      
		</div>
	</div>
  
   <div class="col-sm-12">
  	<div class="form-group">
      <label for="Remarks">Remarks</label>
      <input type="text" class="form-control" id="remarks" value="${prefilledvisitorDetails.remarks}" name="remarks" placeholder="Remarks">
    </div>
  </div>
  
  <div class="col-sm-12">
  	<div class="form-group">
      <label for="MobileNo">Mobile No*</label>
      <input type="text" class="form-control" id="MobileNo" value="${prefilledvisitorDetails.mobile_no}" name="MobileNo" placeholder="Mobile No" onkeypress="digitsOnly(event)" maxlength="10" required>
    </div>
  </div>
  
  <div class="col-sm-12">
  	<div class="form-group">
      <label for="EmailId">Email Id</label>
      <input type="text" class="form-control" id="EmailId" value="${prefilledvisitorDetails.email_id}" name="EmailId" placeholder="Email Id">
    </div>
  </div>
  

  <div class="col-sm-12">
	<div class="form-group">
		
	<label for="email">Contact To Employee </label>     
	<div class="input-group input-group-sm">

	<input type="textbox" name="ContactToEmployee" id="ContactToEmployee" class="form-control form-control-sm" list="listOfEmployee" onchange="checkforMatchEmployee()" value="${prefilledvisitorDetails.name}"/> 
	<input type="hidden" name="hdnselectedemployee" id="hdnselectedemployee" value="${prefilledvisitorDetails.user_id}">  <span class="input-group-append">
		<button type="button" class="btn btn-danger btn-flat" onclick="resetEmployee()">Reset</button>
		</span>
	</div>
	</div>
		</div>



  
  
      <div class="contentarea col-sm-12">
 
  <div class="camera " >
    <video id="video">Video stream not available.</video>
    <button id="startbutton">Take photo (F4)</button> 
  </div>
  <canvas id="canvas">
  </canvas>
  </div>
  <div class="output col-sm-12" id="output">
    
  </div>

<div id="hdnphotosbase64div">
  
</div>
  

  
  
  
  <c:if test="${action ne 'Update'}">
	<div class="col-sm-12" align="center">
  		<div class="form-group">
			<button class="btn btn-success" type="button" onclick='addVisitor()'>Save</button>
			<button class="btn btn-danger" type="reset" onclick='window.location="?a=showVisitors"'>Cancel</button>
		</div>
  	</div>
		
  </c:if>
  
		
		</div>
  
  </div>
		
		
</div>
</form>








<script>

<c:if test="${prefilledvisitorDetails.visitor_id eq null}">
	document.getElementById("divTitle").innerHTML="Add Visitor";
	document.title +=" Add Visitor ";
</c:if>



var arr=window.location.toString().split("/");
callerUrl.value=(arr[0]+"//"+arr[1]+arr[2]+"/"+arr[3]+"/");	
	
</script>



  <script>
                (function() {
  // The width and height of the captured photo. We will set the
  // width to the value defined here, but the height will be
  // calculated based on the aspect ratio of the input stream.

  var width = 320;    // We will scale the photo width to this
  var height = 0;     // This will be computed based on the input stream

  // |streaming| indicates whether or not we're currently streaming
  // video from the camera. Obviously, we start at false.

  var streaming = false;

  // The various HTML elements we need to configure or control. These
  // will be set by the startup() function.

  var video = null;
  var canvas = null;
  var photo = null;
  var startbutton = null;

  function showViewLiveResultButton() {
    if (window.self !== window.top) {
      // Ensure that if our document is in a frame, we get the user
      // to first open it in its own tab or window. Otherwise, it
      // won’t be able to request permission for camera access.
      document.querySelector(".contentarea").remove();
      const button = document.createElement("button");
      button.textContent = "View live result of the example code above";
      document.body.append(button);
      button.addEventListener('click', () => window.open(location.href));
      return true;
    }
    return false;
  }

  function startup() {
    if (showViewLiveResultButton()) { return; }
    video = document.getElementById('video');
    canvas = document.getElementById('canvas');
    photo = document.getElementById('photo');
    startbutton = document.getElementById('startbutton');

    navigator.mediaDevices.getUserMedia({video: {
        facingMode: 'environment'
    }, audio: false})
    .then(function(stream) {
      video.srcObject = stream;
      video.play();
    })
    .catch(function(err) {
      console.log("An error occurred: " + err);
    });

    video.addEventListener('canplay', function(ev){
      if (!streaming) {
        height = video.videoHeight / (video.videoWidth/width);
      
        // Firefox currently has a bug where the height can't be read from
        // the video, so we will make assumptions if this happens.
      
        if (isNaN(height)) {
          height = width / (4/3);
        }
      
        video.setAttribute('width', width);
        video.setAttribute('height', height);
        canvas.setAttribute('width', width);
        canvas.setAttribute('height', height);
        streaming = true;
      }
    }, false);

    startbutton.addEventListener('click', function(ev){
      takepicture();
      ev.preventDefault();
    }, false);
    
    clearphoto();
  }

  // Fill the photo with an indication that none has been
  // captured.

  function clearphoto() {
    var context = canvas.getContext('2d');
    context.fillStyle = "#AAA";
    context.fillRect(0, 0, canvas.width, canvas.height);

    var data = canvas.toDataURL('image/png');
    photo.setAttribute('src', data);
  }
  
  // Capture a photo by fetching the current contents of the video
  // and drawing it into a canvas, then converting that to a PNG
  // format data URL. By drawing it on an offscreen canvas and then
  // drawing that to the screen, we can change its size and/or apply
  // other changes before drawing it.
  var clicknumber=0;
  function takepicture() {
    var context = canvas.getContext('2d');
    if (width && height) {
      canvas.width = width;
      canvas.height = height;
      context.drawImage(video, 0, 0, width, height);
    
      var data = canvas.toDataURL('image/png');

  document.getElementById("output").innerHTML+=`<img class="col-sm-3 photosimg" id="photo`+clicknumber+`"  name="photo" alt="The screen capture will appear in this box."> `;
    

      document.getElementById("photo"+clicknumber).setAttribute('src', data);
      clicknumber++;
    } else {
      clearphoto();
    }
  }

  // Set up our event listener to run the startup process
  // once loading is complete.
  window.addEventListener('load', startup, false);
})();
                
                
                window.addEventListener('keydown', function (e) {
                	if(event.which==115)
                	{
                		startbutton.click();
                	} 
                	
                	
                	if(event.which==113)
                	{
                		addVisitor();
                	}
                	
                	
                	});


            </script>


<script>
  if('${param.prefilledvisitorId}'!='')
  {
   
   visitorname.readOnly=true;
   address.readOnly=true;
   purpose_of_visit.readOnly=true;
   remarks.readOnly=true;
   MobileNo.readOnly=true;
   EmailId.readOnly=true;
   ContactToEmployee.readOnly=true;

  }
</script>