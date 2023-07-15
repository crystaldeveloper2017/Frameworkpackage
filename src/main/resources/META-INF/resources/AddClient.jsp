<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
           
           
           


<c:set var="clientDetails" value='${requestScope["outputObject"].get("clientDetails")}' />
<c:set var="groupList" value='${requestScope["outputObject"].get("groupList")}' />

<c:set var="type" value='${requestScope["outputObject"].get("type")}' />


   





</head>


<script>


function saveClient()
{	
	
	var reqString="";
	for(var m=1;m<tblitems.rows.length;m++)
	{
		reqString+=tblitems.rows[m].childNodes[0].childNodes[0].innerHTML;
		reqString+="~"+tblitems.rows[m].childNodes[1].childNodes[0].innerHTML+"|";
		
		
	}
	//alert this name~pancard|name~pancard
	// create new hidden input with id and name as hiddendirectorvalues
	// set the above value into hidden input
	hiddendirectorvalues.value=reqString;
	
	document.getElementById("frm").submit(); 
}






</script>



<br>



<div class="container" style="padding:20px;background-color:white"> 

<form id="frm" action="?a=saveClient" method="post" accept-charset="UTF-8">
<div class="row">
  <div class="col-sm-4">
  	<div class="form-group">
  	
      <label for="Name">Name OF THE ESTABLISHMENT</label>
      
      <input type="text" class="form-control" id="client_name" value="${clientDetails.client_name}" name="client_name" placeholder="Name OF THE ESTABLISHMENT">
      <input type="hidden" name="hdnClientId" value="${clientDetails.client_id}" id="hdnClientId">
      <input type="hidden" name="hiddendirectorvalues" value="" id="hiddendirectorvalues">
      
    </div>
  </div>
  
  
   <div class="col-sm-4">
  	<div class="form-group">
      <label for="Address">ADDRESS OF THE ESTABLISHMENT</label>
      <input type="text" class="form-control" id="address_of_the_establishment" value="${clientDetails.address_of_the_establishment}" name="address_of_the_establishment" placeholder="ADDRESS OF THE ESTABLISHMENT">
      
    </div>
  </div>
  
  <div class="col-sm-4">
  	<div class="form-group">
      <label for="email">Date Of Setup</label>
      <input type="text" class="form-control" readonly id="txtdateOfSetup" placeholder="Date Of Setup" value="${clientDetails.dateOfSetup}" name="txtdateOfSetup" >
      
    </div>
  </div>
  
  <div class="col-sm-4">
  	<div class="form-group">
      <label for="CompanyPan">Company Pan</label>
      <input type="text" class="form-control" id="company_pan" value="${clientDetails.company_pan}" name="company_pan" placeholder="Company Pan" >
    </div>
  </div>
  
  
  <div class="col-sm-4">
  	<div class="form-group">
      <label for="MobileNumber">GST No </label>
      <input type="text" class="form-control" id="gst_no" value="${clientDetails.gst_no}" name="gst_no" placeholder="GST ">
    </div>
  </div>
  
    <div class="col-sm-4">
  	<div class="form-group">
      <label for="Name">Contact Person Name</label>
      <input type="text" class="form-control" id="contact_person_name" value="${clientDetails.contact_person_name}" name="contact_person_name" placeholder="Contact Person Name">
    </div>
  </div>
  
  <div class="col-sm-4">
  	<div class="form-group">
      <label for="EmailId">Contact Person Email Id</label>
      <input type="text" class="form-control" id="contact_person_email_id" value="${clientDetails.contact_peron_email_id}" name="contact_person_email_id" placeholder="Contact Person Email Id">
    </div>
  </div>
  
  <div class="col-sm-4">
  	<div class="form-group">
      <label for="MobileNumber">Contact Person Mobile No</label>
      <input type="text" class="form-control" id="contact_person_mobile_no" maxlength="10" onkeypress="digitsOnly(event)"  value="${clientDetails.contact_person_mobile_no}" name="contact_person_mobile_no" placeholder="Contact Person Mobile No">
    </div>
  </div>
  
   <div class="col-sm-4">
  	<div class="form-group">
      <label for="email">Director DOB</label>
      <input type="text" class="form-control" readonly id="txtdirectorDob" placeholder="Director DOB" value="${clientDetails.directorDOB }" name="txtdirectorDob" >
      
    </div>
  </div>
  
    <div class="col-sm-4">
  	<div class="form-group">
      <label for="EmailId">Director Email Id</label>
      <input type="text" class="form-control" id="director_email_id" value="${clientDetails.director_email_id}"  name="director_email_id" placeholder="Director Email Id">
    </div>
  </div>
  
  
  <div class="col-sm-4">
  	<div class="form-group">
      <label for="MobileNumber">Director Mobile No</label>
      <input type="text" class="form-control"  id="director_mobile_no" maxlength="10" onkeypress="digitsOnly(event)" value="${clientDetails.director_mobile_no}" name="director_mobile_no" placeholder="Director Mobile No">
    </div>
  </div>
  
  
  
  <div class="col-sm-4">
  	<div class="form-group">
      <label for="firmEmail">Services Mapping</label><br>
      <input type="button" type="button" class="btn btn-primary" onclick='window.location="?a=showClientServicesMapping"' value="Update">
    </div>
  </div>
  

  
  <div class="col-sm-12">
  	<div class="form-group">
      <label for="OwnershipDetails">Ownership Details </label>
      <select id="ownership_details" name="ownership_details" class='form-control' onchange="changeLabel()">
	      <option value="Proprietership">Proprietership</option>
	      <option value="Partnership">Partnership</option>
	      <option value="LLP">LLP</option>    
	      <option value="Pvt">Pvt.Ltd</option>
	      <option value="Ltd">Ltd</option>   
      </select>
      
    </div>
  </div>
  
  	<div class="col-12">
		<div align="center" class="form-group">
		<hr/> 			
		</div>
	</div>
	
	
	
    
   <div class="col-sm-5">
  	<div class="form-group">
      <label for="Name" id="lblname">Director Name</label>
      <input type="text" class="form-control" id="txtdirectorname" value="${clientDetails.director_name}" name="txtdirectorname" placeholder="Director Name">
    </div>
  </div>
  
  <div class="col-sm-5">
  	<div class="form-group">
      <label for="PanCard" id="lblpancardno">Director Pan Card</label>
      <input type="text" class="form-control" id="txtdirectorpancard" value="${clientDetails.director_pan_card}" name="txtdirectorpancard" placeholder="Director Pan Card">
    </div>
  </div>
  
  
   <div class="col-sm-2">
  	<div class="form-group">
      <label for="add"><br></label><br>
      <input type="button" type="button" class="btn btn-success" onclick='addDirectorRow()' value="Add">
    </div>
  </div>
  
 

	<div class="card-body table-responsive p-0" style="height: 550px;">                
		<table id="tblitems"class="table table-head-fixed  table-bordered table-striped dataTable dtr-inline" role="grid" aria-describedby="example1_info">
			<thead>
				<tr>
				<th><b>Name</b></th>
				<th><b>Pan Card</b></th>
				<th><b></b></th>
				</tr>
			</thead>
		</table>
	</div>
  
  

  <div class="col-sm-4">
  	<div class="form-group">
      <label for="Name">Billing Amount</label>
      <input type="text" class="form-control" id="billing_amount" value="${clientDetails.billing_amount}" name="billing_amount" placeholder="Billing Amount" onkeypress="digitsOnlyWithDot(event)" >
    </div>
  </div>

 
  <div class="col-sm-12" align="center">
  	<div class="form-group">
   <button class="btn btn-success" type="button" onclick='saveClient()'>Save</button>
   
   
  <button class="btn btn-danger" type="reset" onclick='window.location="?a=showClientMaster&type="'>Cancel</button>
     
    </div>
  </div>
  
  
  
  
  
  
  </div>
  
  
</div>
</form>



<script>	

$( "#txtdateOfSetup" ).datepicker({ dateFormat: 'dd/mm/yy' });



$( "#txtdirectorDob" ).datepicker({ dateFormat: 'dd/mm/yy' });


<c:if test="${clientDetails.client_id eq null}">
	document.getElementById("divTitle").innerHTML="Add Client";
</c:if>


<c:if test="${clientDetails.client_id ne null}">
document.getElementById("divTitle").innerHTML="Update Client";
</c:if>


function addDirectorRow()
{
	
	
	var table = document.getElementById("tblitems");	    	
	var row = table.insertRow(-1);	    	
	var cell1 = row.insertCell(0);
	var cell2 = row.insertCell(1);
	var cell3 = row.insertCell(2);
	
	cell1.innerHTML = "<div>"+txtdirectorname.value+"</div>";
	cell2.innerHTML = "<div>"+txtdirectorpancard.value+"</div>";	
	cell3.innerHTML = '<button type="button" class="btn btn-sm btn-danger"  onclick=removethisitem(this) id="btn11" style="cursor:pointer">Delete</button>';
	
	
	txtdirectorname.value="";
	txtdirectorpancard.value="";
	
	
}

function removethisitem(btn1)
{
	btn1.parentElement.parentElement.remove();
	
}
function changeLabel()
{
	if(ownership_details.value=="Proprietership")
		{
			lblname.innerHTML="Proprieter Name";
			lblpancardno.innerHTML="Proprieter Pan Card No";
		}

	if(ownership_details.value=="Partnership" || ownership_details.value=="LLP")
	{
		lblname.innerHTML="Partner Name";
		lblpancardno.innerHTML="Partner Pan Card No";
	}
	
	if(ownership_details.value=="Pvt" || ownership_details.value=="Ltd")
	{
		lblname.innerHTML="Director Name";
		lblpancardno.innerHTML="Director Pan Card No";
	}
	
}
changeLabel();

</script>


