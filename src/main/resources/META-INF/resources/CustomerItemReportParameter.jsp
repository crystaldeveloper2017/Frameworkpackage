<br>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>







<form method="POST" action="?a=showCustomerItemHistory" id="frm1"> 

<div class="container" style="padding:20px;background-color:white">
	<table class="table table-bordered tablecss">
	
	
	
	
		<tr align="center" style="background-color:cornsilk;">
			<td>From Date</td>
			<td><input type="text" id="txtfromdate" name="txtfromdate" readonly class="form-control" placeholder="From Date"/></td>
			<td>To Date</td>
			<td><input type="text" id="txttodate" name="txttodate" readonly class="form-control"  placeholder="To Date"/></td>
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
        
        document.getElementById("divTitle").innerHTML="Customer Item Report";
        
       

        </script>
        