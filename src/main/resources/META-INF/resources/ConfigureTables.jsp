  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
           
           
           




   





</head>


<script>







</script>


<br>

<div class="container" style="padding:20px;background-color:white">

<form id="frm" action="?a=addfirm" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
<div class="row">

<div class="col-sm-12">
  	<div class="form-group">
      <label for="email">Number Of Tables</label>     
      <select class="form-control" name="noOfTables" id="noOfTables">
      		<option value="1">1</option>
      		<option value="2">2</option>
      		<option value="3">3</option>
      		<option value="4">4</option>
      		<option value="5">5</option>
      		<option value="6">6</option>
      		<option value="7">7</option>
      		<option value="8">8</option>
      		<option value="9">9</option>
      		<option value="10">10</option>
      		<option value="11">11</option>
      		<option value="12">12</option>    			      
	  </select>
	  
	  <button class="btn btn-success" type="button" onclick='saveConfig()'>Save</button>
	   
    </div>
  </div>
   
</div>
</form>





<script>
	document.getElementById("divTitle").innerHTML="Configure Tables";	
	
	function saveConfig()
	{
		
		
		  document.getElementById("closebutton").style.display='none';
		   document.getElementById("loader").style.display='block';
		$('#myModal').modal({backdrop: 'static', keyboard: false});;

		var xhttp = new XMLHttpRequest();
		  xhttp.onreadystatechange = function() 
		  {
		    if (xhttp.readyState == 4 && xhttp.status == 200) 
		    { 		      
		      document.getElementById("responseText").innerHTML=xhttp.responseText;
			  document.getElementById("closebutton").style.display='block';
			  document.getElementById("loader").style.display='none';
			  $('#myModal').modal({backdrop: 'static', keyboard: false});;
		      
			  
			}
		  };
		  xhttp.open("GET","?a=saveTableConfig&noOfTables="+noOfTables.value, true);    
		  xhttp.send();
		
	}
</script>
