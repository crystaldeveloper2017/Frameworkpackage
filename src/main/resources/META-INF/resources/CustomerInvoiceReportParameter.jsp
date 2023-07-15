<br>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:set var="customerList" value='${requestScope["outputObject"].get("customerList")}' />




<form method="POST" action="?a=showSalesRegister" id="frm1"> 

<div class="container" style="padding:20px;background-color:white">
	<table class="table table-bordered tablecss">
	
	
	
	
		<tr align="center" style="background-color:cornsilk;">
			<td>From Date</td>
			<td><input type="text" id="txtfromdate" onchange="checkforvalidfromtodate()" name="txtfromdate" readonly class="form-control" placeholder="From Date"/></td>
			<td>To Date</td>
			<td><input type="text" id="txttodate" onchange="checkforvalidfromtodate()" name="txttodate" readonly class="form-control"  placeholder="To Date"/></td>
		</tr>
		


<datalist id="customerList">
<c:forEach items="${customerList}" var="customer">
			    <option id="${customer.customerId}">${customer.customerName}</option>			    
	   </c:forEach></select>	   	   	
</datalist>

		
		<tr align="center" style="background-color:cornsilk;">
			<td>Customer Name</td>
			<td colspan='3']>
			<div class="input-group input-group-sm">
			<input type="text" class="form-control" id="txtsearchcustomer"   placeholder="Search For Customer" name="txtsearchcustomer"  list='customerList' oninput="checkforMatchCustomer()">
			<span class="input-group-append">
                    <button type="button" class="btn btn-danger btn-flat" onclick="resetCustomer()">Reset</button>
                  </span>        
      		
	  </div>
		</tr>
		
		<input  type="hidden" name="customerId" id="customerId" value="">
		
		     
			
		
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
        
        document.getElementById("divTitle").innerHTML="Sales Register";
        
        function checkforMatchCustomer()
        {
        	var searchString= document.getElementById("txtsearchcustomer").value;
        	if(searchString.length<3){return;}
        	var options1=document.getElementById("customerList").options;
        	var customerId=0;
        	for(var x=0;x<options1.length;x++)
        		{
        			if(searchString==options1[x].value)
        				{
        					customerId=options1[x].id;
        					break;
        				}
        		}
        	if(customerId!=0)
        		{
        			document.getElementById("customerId").value=customerId;			
        			document.getElementById("txtsearchcustomer").disabled=true;      						
        		}        	
        	
        }
        
        
        function resetCustomer()
        {
        	txtsearchcustomer.disabled=false;
        	txtsearchcustomer.value="";
        	hdnSelectedCustomer.value=0;
        
        }
        
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
        