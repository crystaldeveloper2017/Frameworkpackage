  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
           
           

<c:set var="bankList" value='${requestScope["outputObject"].get("bankList")}' />           


<c:set var="clientList" value='${requestScope["outputObject"].get("clientList")}' />
<c:set var="serviceList" value='${requestScope["outputObject"].get("serviceList")}' />




  
</head>
<br>

<div class="container" style="padding:20px;background-color:white">

<form id="frm" action="?a=addCategory" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
<div class="row">

<div class="col-sm-12">
  	<div class="form-group">
  	<label for="EmployeeName">Search</label> 	
  	<div class="input-group input-group-md">
  	

	<input type="text" class="form-control" id="txtsearchfirm"   placeholder="Search" name="txtsearchfirm"  list='clientList' oninput="checkforMatchFirm()">



	

                  
                  <span class="input-group-append">
                    <button type="button" class="btn btn-danger btn-flat" onclick="resetUser()">Reset</button>
                  </span>
    </div>                 
      <input  type="hidden" name="hdnSelectedFirm" id="hdnSelectedFirm" value="">
   			      
    </div>
  </div>
  
  
  <div class="col-sm-6">
                      <!-- Select multiple-->
                      <div class="form-group">
                        <label>Available Services</label>
                        <input class="form-control" type="text" id="txtsearchbank" placeholder="search for available Services" onkeyup="searchBank()"/>
                        
                        <select  style="height:500px" multiple="" class="custom-select" id="drpavailablebanks">
                        	<c:forEach items="${serviceList}" var="service">
			    				<option id="${service.service_id}">${service.service_name}-${service.occurance}</option>			    
	   						</c:forEach>                          
                        </select>
                      </div>
                    </div>
                    
                    
                    <div class="col-sm-6">
                      <!-- Select multiple-->
                      <div class="form-group">
                        <label>Current Mapped Services</label>
                        
                        <select style="height:540px" multiple="" class="custom-select" id="drpcurrentbanks">                          
                        </select>
                      </div>
                    </div>
  
  
  
  
  <div class="col-sm-12" align="center">
  	<div class="form-group">
      <button class="btn btn-success" type="button" onclick='addRemoveRole(1)'>Add ></button>
	  <button class="btn btn-danger" type="reset" onclick='addRemoveRole(0)'>< Remove</button>
	</div>
  </div>
  
  
   
</div>





<datalist id="clientList">	
		<c:forEach items="${clientList}" var="client">
					    <option id="${client.client_id}">${client.client_name}</option>			    
		</c:forEach></select>   	   	
</datalist>







</form>

<script>


	document.getElementById("divTitle").innerHTML="Client Service Mapping";

	
	
	function checkforMatchFirm()
	{
		var searchString= document.getElementById("txtsearchfirm").value;	
		var options1=document.getElementById("clientList").options;	
		var userId=0;
		for(var x=0;x<options1.length;x++)
			{
				if(searchString==options1[x].value)
					{
					userId=options1[x].id;
						break;
					}
			}
		if(userId!=0)
			{				
				document.getElementById("txtsearchfirm").disabled=true;
				hdnSelectedFirm.value=userId;
				getServiceMappingForThisClient(userId);
			}		
	}
	
	
	function getServiceMappingForThisClient(clientId)
	{
		document.getElementById("closebutton").style.display='none';
		
		var xhttp = new XMLHttpRequest();
		  xhttp.onreadystatechange = function() 
		  {
		    if (xhttp.readyState == 4 && xhttp.status == 200) 
		    { 		      
		      console.log(xhttp.responseText);
		      var listOfRoleDetails=JSON.parse(xhttp.responseText);
		      var reqString="";
		      for(var x=0;x<listOfRoleDetails.length;x++)
		      {
		    	  reqString+="<option id='"+listOfRoleDetails[x].service_id+"'>"+listOfRoleDetails[x].service_name+"-"+listOfRoleDetails[x].occurance+"</option>";
		      }
		      drpcurrentbanks.innerHTML=reqString;
		      
			}
		  };
		  xhttp.open("GET","?a=getServiceMappingForthisClient&clientId="+clientId, true);    
		  xhttp.send();
	}
	
	
	function addRemoveRole(addRemoveFlag)
	{
		
		var drpName="";
		if(addRemoveFlag==1)
			{
				//getListOfRolefirmmove
				drpName="drpavailablebanks";		
			}
		else
			{
				//getListofRolesToAdd
			drpName="drpcurrentbanks";
			}
		var options1=document.getElementById(drpName).options;
		var listOfOptions="";
		for(var x=0;x<options1.length;x++)
			{
				if(options1[x].selected)
					{
						listOfOptions+=options1[x].id+"~";
					}
			}	
		document.getElementById("closebutton").style.display='none';		
		var xhttp = new XMLHttpRequest();
		  xhttp.onreadystatechange = function() 
		  {
		    if (xhttp.readyState == 4 && xhttp.status == 200) 
		    { 
		    	alert(xhttp.responseText);
		    	getServiceMappingForThisClient(hdnSelectedFirm.value);
			}
		  };
		  xhttp.open("GET","?a=addRemoveClientServiceMapping&clientId="+hdnSelectedFirm.value+"&lsitOfServices="+listOfOptions+"&action="+addRemoveFlag, true);    
		  xhttp.send();		
	}
	
	function resetUser()
	{
		window.location.reload();
	}
	function searchBank()
	{	
		if(txtsearchbank.value=='')
		{
			drpavailablebanks.innerHTML=realOptions;
			return;
		}
		drpavailablebanks.innerHTML=realOptions;
		var options=drpavailablebanks.options;
		var filteredOptions="";
		for(var m=0;m<options.length;m++)
			{				
				if(options[m].value.indexOf(txtsearchbank.value)!=-1)
				{
					filteredOptions+="<option id='"+drpavailablebanks.options[m].id+"' >"+drpavailablebanks.options[m].value+"</option> ";
				}
			}
		drpavailablebanks.innerHTML=filteredOptions;
		
	}
	
	
	
	function getRoleDetailsForthisFirm(clientId)
	{
		document.getElementById("closebutton").style.display='none';
		
		var xhttp = new XMLHttpRequest();
		  xhttp.onreadystatechange = function() 
		  {
		    if (xhttp.readyState == 4 && xhttp.status == 200) 
		    { 		      
		      
		      var listOfRoleDetails=JSON.parse(xhttp.responseText);
		      console.log(listOfRoleDetails);
		      var reqString="";
		      for(var x=0;x<listOfRoleDetails.length;x++)
		      {
		    	  reqString+="<option id='"+listOfRoleDetails[x].service_id+"'>"+listOfRoleDetails[x].service_name+"-"+listOfRoleDetails[x].occurance+"</option>";
		      }
		      drpcurrentbanks.innerHTML=reqString;
		      
			}
		  };
		  xhttp.open("GET","?a=getClientServiceMappingForThisClient&clientId="+clientId, true);    
		  xhttp.send();
	}
	
	
	var realOptions="";
	var options=drpavailablebanks.options;
	for(var m=0;m<options.length;m++)
		{				
			realOptions+="<option id='"+drpavailablebanks.options[m].id+"' value='"+drpavailablebanks.options[m].value+"'>"+drpavailablebanks.options[m].value+"</option> ";
		}
</script>



