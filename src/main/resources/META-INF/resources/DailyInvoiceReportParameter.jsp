<br>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="message" value='${requestScope["outputObject"].get("listfirmData")}' />

<form method="POST" action="?a=generateDailyInvoiceReport"> 

<div class="container" style="padding:20px;background-color:white">
	<table class="table table-bordered tablecss">
	
	
	
	
		<tr align="center" style="background-color:cornsilk;">
			<td>From Date</td>
			<td><input type="text" id="txtfromdate" onchange="checkforvalidfromtodate()"  name="txtfromdate" readonly class="form-control" placeholder="From Date"/></td>
			<td>To Date</td>
			<td><input type="text" id="txttodate"  onchange="checkforvalidfromtodate()"  name="txttodate" readonly class="form-control"  placeholder="To Date"/></td>
		</tr>
		<tr  align="center" style="background-color:cornsilk;">
			<td>firm Name</td>
			
		
			
			<td>
			<select class='form-control' id="txtfirm" name = "txtfirm" class="form-control" >
				<option value="-1">-------------------------Select --------------------</option>
				<c:forEach items="${message}" var="item">
				    <option value="${item.firmId}">${item.firmName}</option>			    
		   		</c:forEach>
	  		
			
			</select>
			 </td>
				
		</tr>		
		
		<tr>			
			<td colspan="5" align="center">
				<input class="btn btn-primary" type="submit"></button>
			</td>			
		</tr>
		
		
	</table>
</div>
  
  </form>
<!-- to set values while update page is loaded  ends-->



  <script type="text/javascript">
        $( function() 
        		{
            $( "#txtfromdate" ).datepicker({ dateFormat: 'dd/mm/yy' });
            $( "#txttodate" ).datepicker({ dateFormat: 'dd/mm/yy' });
            
            var date = new Date();
            var month=date.getMonth()+1;
            if(month<10){month="0"+month;}
            
            
            var day=date.getDate();
            if(day<10){day="0"+day;}
            
            var reqStr=day+'/'+(month)+'/'+date.getFullYear();
            document.getElementById("txtfromdate").value=reqStr;
            document.getElementById("txttodate").value=reqStr;
            
            
            
            
            
          } );
        
        document.getElementById("divTitle").innerHTML="Daily Invoice Report";
        
        function checkforvalidfromtodate()
        {        	
        	var fromDate=document.getElementById("txtfromdate").value;
        	var toDate=document.getElementById("txttodate").value;
        	
        	var fromDateArr=fromDate.split("/");
        	var toDateArr=toDate.split("/");
        	
        	
        	var fromDateArrDDMMYYYY=fromDate.split("/");
        	var toDateArrDDMMYYYY=toDate.split("/");
        	
        	var fromDateAsDate=new Date(fromDateArrDDMMYYYY[2],fromDateArrDDMMYYYY[1]-1,fromDateArrDDMMYYYY[0]);
        	var toDateAsDate=new Date(toDateArrDDMMYYYY[2],toDateArrDDMMYYYY[1]-1,toDateArrDDMMYYYY[0]);
        	
        	if(fromDateAsDate>toDateAsDate)
        		{
        			alert("From Date should be less than or equal to To Date");
        			window.location.reload();        			
        		}
        }


        </script>
        