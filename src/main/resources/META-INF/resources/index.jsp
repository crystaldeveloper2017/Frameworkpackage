<style>
	@media screen and (min-width: 601px) {
  .labelFonts {
    font-size: 40px;
    font-style: italic;color: darkblue;font-weight: 800;
  }
}

/* If the screen size is 600px wide or less, set the font-size of <div> to 30px */
@media screen and (max-width: 600px) {
  .labelFonts {
    font-size: 15px;
    font-style: italic;color: darkblue;font-weight: 800;
  }
}
</style>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="InVisitorsCount" value='${requestScope["outputObject"].get("InVisitorsCount")}' />
<c:set var="inPermanenet" value='${requestScope["outputObject"].get("inPermanenet")}' />
<c:set var="inContract" value='${requestScope["outputObject"].get("inContract")}' />


<br>




<div class="row">
          <div class="col-12 col-sm-6 col-md-3" onclick="window.location='?a=showAddVisitor'">
            <div class="info-box">
              <span class="info-box-icon bg-info elevation-1"><i class="fas fa-pencil-square-o"></i></span>

              <div class="info-box-content">
                <span class="info-box-text">Visitor CheckIn</span>
                
              </div>
              <!-- /.info-box-content -->
            </div>
            <!-- /.info-box -->
          </div>
          
           <div class="col-12 col-sm-6 col-md-3" onclick="window.location='?a=showVisitors'">
            <div class="info-box">
              <span class="info-box-icon bg-secondary elevation-1"><i class="fas fa-shopping-cart"></i></span>

              <div class="info-box-content">
                <span class="info-box-text">Visitor Register</span>
                
              </div>
              <!-- /.info-box-content -->
            </div>
            <!-- /.info-box -->
          </div>
          
          <!-- /.col -->
          <div class="col-12 col-sm-6 col-md-3" onclick="window.location='?a=showCheckInScreen'">
            <div class="info-box mb-3">
              <span class="info-box-icon bg-danger elevation-1"><i class="fas fa-inr"></i></span>

              <div class="info-box-content">
                <span class="info-box-text">Employee Check In</span>
                
              </div>
              <!-- /.info-box-content -->
            </div>
            <!-- /.info-box -->
          </div>
          <!-- /.col -->

          <!-- fix for small devices only -->
          <div class="clearfix hidden-md-up"></div>

          <div class="col-12 col-sm-6 col-md-3" onclick="window.location='?a=showEmployeeMaster'">
            <div class="info-box mb-3">
              <span class="info-box-icon bg-success elevation-1"><i class="fas fa-adjust"></i></span>

              <div class="info-box-content">
                <span class="info-box-text">Employee Master</span>
               
              </div>
              <!-- /.info-box-content -->
            </div>
            <!-- /.info-box -->
          </div>
          <!-- /.col -->
        
          
          
                  <div class="col-12 col-sm-6 col-md-3" onclick="window.location='?a=showVendorCheckIn'">
            <div class="info-box">
              <span class="info-box-icon bg-info elevation-1"><i class="fas fa-pencil-square-o"></i></span>

              <div class="info-box-content">
                <span class="info-box-text">Vendor CheckIn</span>
                
              </div>
              <!-- /.info-box-content -->
            </div>
            <!-- /.info-box -->
          </div>
          
           <div class="col-12 col-sm-6 col-md-3" onclick="window.location='?a=showVendorsCheckIn'">
            <div class="info-box">
              <span class="info-box-icon bg-secondary elevation-1"><i class="fas fa-shopping-cart"></i></span>

              <div class="info-box-content">
                <span class="info-box-text">Vendor CheckIn Register</span>
                
              </div>
              <!-- /.info-box-content -->
            </div>
            <!-- /.info-box -->
          </div>
       
          
         
          
          <!-- /.col -->
        </div>


  
 
 <br>
 

<c:if test="${adminFlag eq true}">
	
	
<div class="row">
	
	
		<div class="col-lg-3 col-6" >
            <!-- small box -->
            <div class="small-box bg-info">
              <div class="inner">
              
              


                <h3>${InVisitorsCount}</h3>

                <p>Total Items</p>
              </div>
              <div class="icon">
                <i class="ion ion-bag"></i>
              </div>
              <a href="#"  class="small-box-footer">More info <i class="fas fa-arrow-circle-right"></i></a>
            </div>
          </div>
          <!-- ./col -->
          <div class="col-lg-3 col-6">
            <!-- small box -->
            <div class="small-box bg-success">
              <div class="inner">
                <h3>${inPermanenet}</h3>

                <p>Total Permanent Employees</p>
              </div>
              <div class="icon">
                <i class="ion ion-stats-bars"></i>
              </div>
              <a href="#" onclick="window.location='?a=showCategoryMasterNew'" class="small-box-footer">More info <i class="fas fa-arrow-circle-right"></i></a>
            </div>
          </div>
          <!-- ./col -->
          <div class="col-lg-3 col-6" >
            <!-- small box -->
            <div class="small-box bg-warning">
              <div class="inner">
                <h3>${inContract}</h3>

                <p>Inside Contract Employees</p>
              </div>
              <div class="icon">
                <i class="ion ion-person-add"></i>
              </div>
              <a href="#"  class="small-box-footer">More info <i class="fas fa-arrow-circle-right"></i></a>
            </div>
          </div>
          
          <div class="col-lg-3 col-6" onclick="window.location='?a=showDailyInvoiceReportParameter'">
            <!-- small box -->
            <div class="small-box bg-danger">
              <div class="inner">
                <h3>${HomePageContent.get('InvoicesCreated')}</h3>
                <p>Total Sales Invoices</p>
              </div>
              <div class="icon">
                <i class="ion ion-document"></i>
                
              </div>
              <a href="#"  onclick="window.location='?a=showDailyInvoiceReportParameter'" class="small-box-footer">More info <i class="fas fa-arrow-circle-right"></i></a>
            </div>
          </div>

</div>
	
</c:if> 
 

<c:if test="${adminFlag eq true or franchiseFlag eq true}">



<section class="content">
      <div class="container-fluid">
        <!-- Small boxes (Stat box) -->
        <div class="row">
          
          
		
		
		
		<div class="col-sm-12">
		<div class="card" >
              
              <div class="card-body table-responsive p-0">
                <table class="table table-striped table-valign-middle">
                  <thead>
                  <tr>
                  <th>From Date</th>
                    <th>To Date</th>
                    
                                                                                                   
                  </tr>
                  </thead>
                  <tbody>
                  
                  
                  	<tr>
                    <td><input type="text" id="txtfromdate" onchange="reloadData()" name="txtfromdate" readonly class="datepicker form-control form-control-sm" placeholder="From Date"/></td>
                    <td><input type="text" id="txttodate" onchange="reloadData()" name="txttodate" readonly class="datepicker form-control form-control-sm"  placeholder="To Date"/></td>
                        
                                        
                  </tr>	
                  
                  
                  
                                    
                  </tbody>
                </table>
              </div>
            </div>
		</div>
		
		
		
		
		
		
	
		
	<c:if test="${HomePageContent.user_total_payments eq 'Y'}">	
	
		<div class="col-sm-12">
		
		
		
		
		<div class="card card-primary">
              <div class="card-header">
                <h3 class="card-title">1. Total Payments</h3>

                <div class="card-tools">
                  <button type="button" class="btn btn-tool" data-card-widget="collapse">
                    <i class="fas fa-minus"></i>
                  </button>
                  <button type="button" class="btn btn-tool" data-card-widget="remove">
                    <i class="fas fa-times"></i>
                  </button>
                </div>
              </div>
              <div class="card-body table-responsive p-0">
                <table class="table table-striped table-valign-middle">
                  <thead>
                  <tr>
                  <th>Store Name</th>
                    <th>Cash</th>
                    <th>Paytm</th>
                    <th>Amazon</th>
                    <th>GooglePay</th>
                    <th>Phone Pay</th>
                    
                    <c:if test="${HomePageContent.app_id eq '1'}">
	                    <th>Zomato</th>
	                    <th>Swiggy</th>
	                </c:if>
                    
                    <th>Card</th>                    
                    <th>Total</th>
                    <th>Kasar</th>
                                                                                                   
                  </tr>
                  </thead>
                  <tbody>
                  
                  <c:forEach items="${HomePageContent.get('paymentData')}" var="item">
                  	<tr>
                    <td>${item.store_name}</td>
                    <td><a href="?a=generateDailyPaymentRegister&storeId=${item.store_id}&paymentMode=Cash&txtfromdate=${HomePageContent.fromDate}&txttodate=${HomePageContent.toDate}"> ${item.Cash}</a></td>
                    <td><a href="?a=generateDailyPaymentRegister&storeId=${item.store_id}&paymentMode=Paytm&txtfromdate=${HomePageContent.fromDate}&txttodate=${HomePageContent.toDate}"> ${item.Paytm}</a></td>
                    <td><a href="?a=generateDailyPaymentRegister&storeId=${item.store_id}&paymentMode=Amazon&txtfromdate=${HomePageContent.fromDate}&txttodate=${HomePageContent.toDate}"> ${item.Amazon}</a></td>
                    <td><a href="?a=generateDailyPaymentRegister&storeId=${item.store_id}&paymentMode=Google Pay&txtfromdate=${HomePageContent.fromDate}&txttodate=${HomePageContent.toDate}"> ${item.GooglePay}</a></td>
                    
                    <td><a href="?a=generateDailyPaymentRegister&storeId=${item.store_id}&paymentMode=Phone Pay&txtfromdate=${HomePageContent.fromDate}&txttodate=${HomePageContent.toDate}"> ${item.PhonePay}</a></td>
                    
					<c:if test="${HomePageContent.app_id eq '1' }">                    
	                    <td><a href="?a=generateDailyPaymentRegister&storeId=${item.store_id}&paymentMode=Zomato&txtfromdate=${HomePageContent.fromDate}&txttodate=${HomePageContent.toDate}"> ${item.Zomato}</a></td>
	                    <td><a href="?a=generateDailyPaymentRegister&storeId=${item.store_id}&paymentMode=Swiggy&txtfromdate=${HomePageContent.fromDate}&txttodate=${HomePageContent.toDate}"> ${item.Swiggy}</a></td>
	                </c:if>
                    
                    <td><a href="?a=generateDailyPaymentRegister&storeId=${item.store_id}&paymentMode=Card&txtfromdate=${HomePageContent.fromDate}&txttodate=${HomePageContent.toDate}"> ${item.Card}</a></td>
                    <td><a href="?a=generateDailyPaymentRegister&storeId=${item.store_id}&txtfromdate=${HomePageContent.fromDate}&txttodate=${HomePageContent.toDate}"> ${item.HoriTotal}</a></td>
                    
                    
                    
                    <td><a href="?a=generateDailyPaymentRegister&storeId=${item.store_id}&paymentMode=Kasar&txtfromdate=${HomePageContent.fromDate}&txttodate=${HomePageContent.toDate}"> ${item.Kasar}</a></td>
                    
                    
                            
                                        
                  </tr>	
                  </c:forEach>
                  
                  <tr>
                  <td>Total</td>
                  	<td>${HomePageContent.get('paymentDataTotal').get('Cash')}</td>
                  	<td>${HomePageContent.get('paymentDataTotal').get('Paytm')}</td>
                  	<td>${HomePageContent.get('paymentDataTotal').get('Amazon')}</td>
                  	<td>${HomePageContent.get('paymentDataTotal').get('GooglePay')}</td>
                  	<td>${HomePageContent.get('paymentDataTotal').get('PhonePay')}</td>
                  	
                  	<c:if test="${HomePageContent.app_id eq '1' }">
	                  	<td>${HomePageContent.get('paymentDataTotal').get('Zomato')}</td>
	                  	<td>${HomePageContent.get('paymentDataTotal').get('Swiggy')}</td>
                  	</c:if>
                  	<td>${HomePageContent.get('paymentDataTotal').get('Card')}</td>
                  	<td>${HomePageContent.get('paymentDataTotal').get('HoriTotal')}</td>
                  	<td>${HomePageContent.get('paymentDataTotal').get('Kasar')}</td>
                  	
                  </tr>
                  
                                    
                  </tbody>
                </table>
              </div>
              <!-- /.card-body -->
            </div>
		
		
		</div>
		</c:if>
		
         <c:if test="${HomePageContent.user_payment_collections eq 'Y'}">  
            <div class="col-sm-12">
            <div class="card card-primary">
            
            
             <div class="card-header">
                <h3 class="card-title">2.Payments Against Collection :</h3>

                <div class="card-tools">
                  <button type="button" class="btn btn-tool" data-card-widget="collapse">
                    <i class="fas fa-minus"></i>
                  </button>
                  <button type="button" class="btn btn-tool" data-card-widget="remove">
                    <i class="fas fa-times"></i>
                  </button>
                </div>
              </div>
            
              
              <div class="card-body table-responsive p-0">
                <table class="table table-striped table-valign-middle">
                  <thead>
                  <tr>
                  <th>Store Name</th>
                    <th>Cash</th>
                    <th>Paytm</th>
                    <th>Amazon</th>
                    <th>GooglePay</th>
                    <th>Phone Pay</th>
                    <c:if test="${HomePageContent.app_id eq '1' }">
	                    <th>Zomato</th>
	                    <th>Swiggy</th>
                    </c:if>
                    <th>Card</th>
                    <th>Total</th>
                    
                    <th>Kasar</th>
                    
                                                                                                   
                  </tr>
                  </thead>
                  <tbody>
                  
                  <c:forEach items="${HomePageContent.get('PaymentDataAgainstCollection')}" var="item">
                  	<tr>
                    <td>${item.store_name}</td>
                    <td><a href="?a=generateDailyPaymentRegister&storeId=${item.store_id}&paymentFor=Collection&paymentMode=Cash&txtfromdate=${HomePageContent.fromDate}&txttodate=${HomePageContent.toDate}"> ${item.Cash}</a></td>
                    <td><a href="?a=generateDailyPaymentRegister&storeId=${item.store_id}&paymentFor=Collection&paymentMode=Paytm&txtfromdate=${HomePageContent.fromDate}&txttodate=${HomePageContent.toDate}"> ${item.Paytm}</a></td>
                    <td><a href="?a=generateDailyPaymentRegister&storeId=${item.store_id}&paymentFor=Collection&paymentMode=Amazon&txtfromdate=${HomePageContent.fromDate}&txttodate=${HomePageContent.toDate}"> ${item.Amazon}</a></td>
                    <td><a href="?a=generateDailyPaymentRegister&storeId=${item.store_id}&paymentFor=Collection&paymentMode=Google Pay&txtfromdate=${HomePageContent.fromDate}&txttodate=${HomePageContent.toDate}"> ${item.GooglePay}</a></td>
                    <td><a href="?a=generateDailyPaymentRegister&storeId=${item.store_id}&paymentFor=Collection&paymentMode=Phone Pay&txtfromdate=${HomePageContent.fromDate}&txttodate=${HomePageContent.toDate}"> ${item.PhonePay}</a></td>
                    <c:if test="${HomePageContent.app_id eq '1' }">
	                    <td><a href="?a=generateDailyPaymentRegister&storeId=${item.store_id}&paymentFor=Collection&paymentMode=Zomato&txtfromdate=${HomePageContent.fromDate}&txttodate=${HomePageContent.toDate}"> ${item.Zomato}</a></td>
	                    <td><a href="?a=generateDailyPaymentRegister&storeId=${item.store_id}&paymentFor=Collection&paymentMode=Swiggy&txtfromdate=${HomePageContent.fromDate}&txttodate=${HomePageContent.toDate}"> ${item.Swiggy}</a></td>
                    </c:if>
                    <td><a href="?a=generateDailyPaymentRegister&storeId=${item.store_id}&paymentFor=Collection&paymentMode=Card&txtfromdate=${HomePageContent.fromDate}&txttodate=${HomePageContent.toDate}"> ${item.Card}</a></td>
                    <td><a href="?a=generateDailyPaymentRegister&storeId=${item.store_id}&paymentFor=Collection&txtfromdate=${HomePageContent.fromDate}&txttodate=${HomePageContent.toDate}"> ${item.HoriTotal}</a></td>
                    
                    <td><a href="?a=generateDailyPaymentRegister&storeId=${item.store_id}&paymentFor=Collection&paymentMode=Kasar&txtfromdate=${HomePageContent.fromDate}&txttodate=${HomePageContent.toDate}"> ${item.Kasar}</a></td>
                    
                    
                    
                             
                  </tr>	
                  </c:forEach>
                  
                   <tr>
                  <td>Total</td>
                  	<td>${HomePageContent.get('PaymentDataAgainstCollectionTotal').get('Cash')}</td>
                  	<td>${HomePageContent.get('PaymentDataAgainstCollectionTotal').get('Paytm')}</td>
                  	<td>${HomePageContent.get('PaymentDataAgainstCollectionTotal').get('Amazon')}</td>
                  	<td>${HomePageContent.get('PaymentDataAgainstCollectionTotal').get('GooglePay')}</td>
                  	<td>${HomePageContent.get('PaymentDataAgainstCollectionTotal').get('PhonePay')}</td>
                  	<c:if test="${HomePageContent.app_id eq '1' }">
	                  	<td>${HomePageContent.get('PaymentDataAgainstCollectionTotal').get('Zomato')}</td>
	                  	<td>${HomePageContent.get('PaymentDataAgainstCollectionTotal').get('Swiggy')}</td>
	                </c:if>
                  	
                  	<td>${HomePageContent.get('PaymentDataAgainstCollectionTotal').get('Card')}</td>
                  	<td>${HomePageContent.get('PaymentDataAgainstCollectionTotal').get('HoriTotal')}</td>
                  	<td>${HomePageContent.get('PaymentDataAgainstCollectionTotal').get('Kasar')}</td>
                  	
                  </tr>
                  
                  
                  
                                    
                  </tbody>
                </table>
              </div>
            </div>
            </div>
            </c:if>
            
            
            
		<c:if test="${HomePageContent.user_counter_sales eq 'Y'}"> 	
        <div class="col-sm-12">    
        <div class="card card-primary">
        
        
             <div class="card-header">
                <h3 class="card-title">3.Counter Sales:</h3>

                <div class="card-tools">
                  <button type="button" class="btn btn-tool" data-card-widget="collapse">
                    <i class="fas fa-minus"></i>
                  </button>
                  <button type="button" class="btn btn-tool" data-card-widget="remove">
                    <i class="fas fa-times"></i>
                  </button>
                </div>
              </div>
              
              
              <div class="card-body table-responsive p-0">
                <table class="table table-striped table-valign-middle">
                  <thead>
                  <tr>
                    <th>Store</th>
                    <th>Gross</th>
                    <th>Discount</th>
                    <th>Returns</th>
                    <th>Net</th>
                    <th>Paid</th>
                    <th>Pending</th>
                    <th>Profit</th>
                    <th>Count</th>
                    
                                                                                
                  </tr>
                  </thead>
                  <tbody>
                  
                  <c:forEach items="${HomePageContent.get('statisticalData')}" var="item">
                  	<tr>
                    
                    <td>${item.store_name}</td>
                    <td><a href="?a=generateDailyInvoiceReport&drpstoreId=${item.store_id}&txtfromdate=${HomePageContent.fromDate}&txttodate=${HomePageContent.toDate}">${item.gross_amount}</a></td>
                    
                    
                    <td><a href="?a=generateDailyInvoiceReport&discount=Y&drpstoreId=${item.store_id}&txtfromdate=${HomePageContent.fromDate}&txttodate=${HomePageContent.toDate}">${item.discount}</a></td>
                    <td><a href="?a=generateReturnRegisterReport&drpstoreId=${item.store_id}&txtfromdate=${HomePageContent.fromDate}&txttodate=${HomePageContent.toDate}">${item.returnedAmount}</a></td>
                    <td><a href="?a=generateDailyInvoiceReport&drpstoreId=${item.store_id}&txtfromdate=${HomePageContent.fromDate}&txttodate=${HomePageContent.toDate}">${item.total_amount}</a></td>
                    <td><a href="?a=generateDailyInvoiceReport&paymentType=Paid&drpstoreId=${item.store_id}&txtfromdate=${HomePageContent.fromDate}&txttodate=${HomePageContent.toDate}">${item.paid}</a></td>
                    <td><a href="?a=generateDailyInvoiceReport&paymentType=Pending,Partial&drpstoreId=${item.store_id}&txtfromdate=${HomePageContent.fromDate}&txttodate=${HomePageContent.toDate}">${item.pending}</a></td>
                    <td><a href="?a=generateDailyInvoiceReport&drpstoreId=${item.store_id}&txtfromdate=${HomePageContent.fromDate}&txttodate=${HomePageContent.toDate}">${item.profit}</a></td>
                    
                    <td><a href="?a=generateDailyInvoiceReport&drpstoreId=${item.store_id}&txtfromdate=${HomePageContent.fromDate}&txttodate=${HomePageContent.toDate}">${item.Count}</a></td>
                    
                    
                                        
                  </tr>	
                  </c:forEach>
                  
                  
                  <tr>
                  <td>Total</td>
                  	<td>${HomePageContent.get('statisticalDataTotal').get('gross_amount')}</td>
                  	<td>${HomePageContent.get('statisticalDataTotal').get('discount')}</td>
                  	<td>${HomePageContent.get('statisticalDataTotal').get('returnedAmount')}</td>
                  	<td>${HomePageContent.get('statisticalDataTotal').get('total_amount')}</td>
                  	<td>${HomePageContent.get('statisticalDataTotal').get('paid')}</td>
                  	<td>${HomePageContent.get('statisticalDataTotal').get('pending')}</td>
                  	<td>${HomePageContent.get('statisticalDataTotal').get('profit')}</td>
                  	<td>${HomePageContent.get('statisticalDataTotal').get('Count')}</td>
                  	
                  	
                  </tr>
                                    
                  </tbody>
                </table>
              </div>
            </div>
            </div>
            </c:if>
            
            <c:if test="${HomePageContent.user_payment_sales eq 'Y'}"> 
            <div class="col-sm-12">
            <div class="card card-primary">
            
            
              <div class="card-header">
                <h3 class="card-title">4.Payments Against Sales :</h3>

                <div class="card-tools">
                  <button type="button" class="btn btn-tool" data-card-widget="collapse">
                    <i class="fas fa-minus"></i>
                  </button>
                  <button type="button" class="btn btn-tool" data-card-widget="remove">
                    <i class="fas fa-times"></i>
                  </button>
                </div>
              </div>
            
           
              <div class="card-body table-responsive p-0">
                <table class="table table-striped table-valign-middle">
                  <thead>
                  <tr>
                  <th>Store Name</th>
                    <th>Cash</th>
                    <th>Paytm</th>
                    <th>Amazon</th>
                    <th>GooglePay</th>
                    <th>Phone Pay</th>
                    <c:if test="${HomePageContent.app_id eq '1' }">
	                    <th>Zomato</th>
	                    <th>Swiggy</th>
                    </c:if>
                    <th>Card</th>
                    <th>Total</th>                                                                               
                  </tr>
                  </thead>
                  <tbody>
                  
                  <c:forEach items="${HomePageContent.get('paymentDataAgainstSales')}" var="item">
                  	<tr>
                   <td>${item.store_name}</td>
                    <td><a href="?a=generateDailyPaymentRegister&storeId=${item.store_id}&paymentFor=Invoice&paymentMode=Cash&txtfromdate=${HomePageContent.fromDate}&txttodate=${HomePageContent.toDate}"> ${item.Cash}</a></td>
                    <td><a href="?a=generateDailyPaymentRegister&storeId=${item.store_id}&paymentFor=Invoice&paymentMode=Paytm&txtfromdate=${HomePageContent.fromDate}&txttodate=${HomePageContent.toDate}"> ${item.Paytm}</a></td>
                    <td><a href="?a=generateDailyPaymentRegister&storeId=${item.store_id}&paymentFor=Invoice&paymentMode=Amazon&txtfromdate=${HomePageContent.fromDate}&txttodate=${HomePageContent.toDate}"> ${item.Amazon}</a></td>
                    <td><a href="?a=generateDailyPaymentRegister&storeId=${item.store_id}&paymentFor=Invoice&paymentMode=Google Pay&txtfromdate=${HomePageContent.fromDate}&txttodate=${HomePageContent.toDate}"> ${item.GooglePay}</a></td>
                    <td><a href="?a=generateDailyPaymentRegister&storeId=${item.store_id}&paymentFor=Invoice&paymentMode=Phone Pay&txtfromdate=${HomePageContent.fromDate}&txttodate=${HomePageContent.toDate}"> ${item.PhonePay}</a></td>
                    <c:if test="${HomePageContent.app_id eq '1' }">
	                    <td><a href="?a=generateDailyPaymentRegister&storeId=${item.store_id}&paymentFor=Invoice&paymentMode=Zomato&txtfromdate=${HomePageContent.fromDate}&txttodate=${HomePageContent.toDate}"> ${item.Zomato}</a></td>
	                    <td><a href="?a=generateDailyPaymentRegister&storeId=${item.store_id}&paymentFor=Invoice&paymentMode=Swiggy&txtfromdate=${HomePageContent.fromDate}&txttodate=${HomePageContent.toDate}"> ${item.Swiggy}</a></td>
                    </c:if>
                    <td><a href="?a=generateDailyPaymentRegister&storeId=${item.store_id}&paymentFor=Invoice&paymentMode=Card&txtfromdate=${HomePageContent.fromDate}&txttodate=${HomePageContent.toDate}"> ${item.Card}</a></td>
                    <td><a href="?a=generateDailyPaymentRegister&storeId=${item.store_id}&paymentFor=Invoice&txtfromdate=${HomePageContent.fromDate}&txttodate=${HomePageContent.toDate}"> ${item.HoriTotal}</a></td>           
                  </tr>	
                  </c:forEach>
                  
                   <tr>
                  <td>Total</td>
                  	<td>${HomePageContent.get('paymentDataAgainstSalesTotal').get('Cash')}</td>
                  	<td>${HomePageContent.get('paymentDataAgainstSalesTotal').get('Paytm')}</td>
                  	<td>${HomePageContent.get('paymentDataAgainstSalesTotal').get('Amazon')}</td>
                  	<td>${HomePageContent.get('paymentDataAgainstSalesTotal').get('GooglePay')}</td>
                  	<td>${HomePageContent.get('paymentDataAgainstSalesTotal').get('PhonePay')}</td>
                  	<c:if test="${HomePageContent.app_id eq '1' }">
	                  	<td>${HomePageContent.get('paymentDataAgainstSalesTotal').get('Zomato')}</td>
	                  	<td>${HomePageContent.get('paymentDataAgainstSalesTotal').get('Swiggy')}</td>
                  	</c:if>
                  	<td>${HomePageContent.get('paymentDataAgainstSalesTotal').get('Card')}</td>
                  	<td>${HomePageContent.get('paymentDataAgainstSalesTotal').get('HoriTotal')}</td>
                  </tr>
                  
                  
                                    
                  </tbody>
                </table>
              </div>
            </div>
            </div>
            </c:if>
            
            <c:if test="${HomePageContent.user_store_sales eq 'Y'}">
            <div class="col-sm-12">
            <div class="card card-primary">
            
            
               <div class="card-header">
                <h3 class="card-title">5.Store Wise - Employee Wise Sales:</h3>

                <div class="card-tools">
                  <button type="button" class="btn btn-tool" data-card-widget="collapse">
                    <i class="fas fa-minus"></i>
                  </button>
                  <button type="button" class="btn btn-tool" data-card-widget="remove">
                    <i class="fas fa-times"></i>
                  </button>
                </div>
              </div>
            
           
              <div class="card-body table-responsive p-0">
                <table class="table table-striped table-valign-middle">
                  <thead>
                  <tr>
                    <th>Store</th>
                    <th>Name</th>
                    <th>Cash</th>
                    <th>Paytm</th>
                    <th>Amazon</th>
                    <th>GooglePay</th>
                    <c:if test="${HomePageContent.app_id eq '1' }">
	                    <th>Zomato</th>
	                    <th>Swiggy</th>
                    </c:if>
                    <th>PhonePay</th>
                    <th>Card</th>
                    <th>pendingAmount</th>
                    <th>Total</th>                   									                                                                                
                  </tr>
                  </thead>
                  <tbody>
                  
                  <c:forEach items="${HomePageContent.get('employeeData')}" var="item">
                  	<tr>
                  	
                    <td>${item.store_name}</td>
                    <td>${item.name}</td>
                    
                    
                    <td>${item.Cash}</td>
                    
                    <td>${item.Paytm}</td>
                    <td>${item.Amazon}</td>
                    <td>${item.GooglePay}</td>
                    <c:if test="${HomePageContent.app_id eq '1' }">
	                    <td>${item.Zomato}</td>
	                    <td>${item.Swiggy}</td>
                    </c:if>
                    <td>${item.PhonePay}</td>
                    <td>${item.Card}</td>                    
                    <td>${item.pendingAmount}</td>
                    <td><a href="?a=generateDailyInvoiceReport&updatedBy=${item.updated_by}&storeId=${item.store_id}&paymentFor=Invoice&txtfromdate=${HomePageContent.fromDate}&txttodate=${HomePageContent.toDate}">${item.get('HoriTotal')}</a></td>      
                       <td></td>                
                  </tr>	
                  </c:forEach>
                  
                   <tr>
                  <td>Total</td>
                  <td></td>
                  	<td>${HomePageContent.get('employeeDataTotal').get('Cash')}</td>
                  	<td>${HomePageContent.get('employeeDataTotal').get('Paytm')}</td>
                  	<td>${HomePageContent.get('employeeDataTotal').get('Amazon')}</td>
                  	<td>${HomePageContent.get('employeeDataTotal').get('GooglePay')}</td>
                  	<c:if test="${HomePageContent.app_id eq '1' }">
	                  	<td>${HomePageContent.get('employeeDataTotal').get('Zomato')}</td>
	                  	<td>${HomePageContent.get('employeeDataTotal').get('Swiggy')}</td>
                  	</c:if>
                  	
                  	<td>${HomePageContent.get('employeeDataTotal').get('PhonePay')}</td>
                  	<td>${HomePageContent.get('employeeDataTotal').get('Card')}</td>
                  	<td>${HomePageContent.get('employeeDataTotal').get('pendingAmount')}</td>           	
                  	
                  	
                  	<td>${HomePageContent.get('employeeDataTotal').get('HoriTotal')}</td>
                  </tr>
                  
                                    
                  </tbody>
                </table>
              </div>
            </div>
            </div>
            </c:if>
            
            <c:if test="${HomePageContent.user_store_bookings eq 'Y'}">
            
            <div class="col-sm-12">
            <div class="card card-primary">
            
            
               <div class="card-header">
                <h3 class="card-title">6.Store Wise - Bookings:</h3>

                <div class="card-tools">
                  <button type="button" class="btn btn-tool" data-card-widget="collapse">
                    <i class="fas fa-minus"></i>
                  </button>
                  <button type="button" class="btn btn-tool" data-card-widget="remove">
                    <i class="fas fa-times"></i>
                  </button>
                </div>
              </div>
            
           
              <div class="card-body table-responsive p-0">
                <table class="table table-striped table-valign-middle">
                  <thead>
                  <tr>
                    <th>Store</th>                    
                    <th>Total Bookings</th>                   									                                                                                
                  </tr>
                  </thead>
                  <tbody>
                  
                  <c:forEach items="${HomePageContent.get('bookingData')}" var="item">
                  	<tr>
                  	
                    <td>${item.store_name}</td>
                    
                    <td><a href="?a=generateDailyInvoiceReport&updatedBy=${item.updated_by}&storeId=${item.store_id}&paymentFor=Invoice&txtfromdate=${HomePageContent.fromDate}&txttodate=${HomePageContent.toDate}">${item.cnt}</a></td>
                    
                                  
                  </tr>	
                  </c:forEach>
                  
                            
                  </tbody>
                </table>
              </div>
            </div>
            </div>
            </c:if>
        
        
        <c:if test="${HomePageContent.user_store_expenses eq 'Y'}">
        <div class="col-sm-12">
            <div class="card card-primary">
            
            
               <div class="card-header">
                <h3 class="card-title">7.Store Wise Expenses</h3>

                <div class="card-tools">
                  <button type="button" class="btn btn-tool" data-card-widget="collapse">
                    <i class="fas fa-minus"></i>
                  </button>
                  <button type="button" class="btn btn-tool" data-card-widget="remove">
                    <i class="fas fa-times"></i>
                  </button>
                </div>
              </div>
            
           
              <div class="card-body table-responsive p-0">
                <table class="table table-striped table-valign-middle">
                  <thead>
                  <tr>
                    <th>Store</th>                    
                    <th>Total Expenses</th>                   									                                                                                
                  </tr>
                  </thead>
                  <tbody>
                  
                  <c:forEach items="${HomePageContent.get('expenseData')}" var="item">
                  	<tr>
                  	
                    <td>${item.store_name}</td>
                    
                    <td><a href="?a=showExpenseEntry&fromDate=${HomePageContent.fromDate}&toDate=${HomePageContent.toDate}&store_id=${item.store_id}">${item.cnt}</a></td>
                    
                                  
                  </tr>	
                  </c:forEach>
                  
                            
                  </tbody>
                </table>
              </div>
            </div>
            </div>
            </c:if>
        
      </div><!-- /.container-fluid -->
      
      <a id="back-to-top" href="#" class="btn btn-primary back-to-top" role="button" aria-label="Scroll to top">
      <i class="fas fa-chevron-up"></i>
    </a>
    
    </section>
    </c:if>


    <div class="row">
	
	
		<div class="col-lg-3 col-6" >
            <!-- small box -->
            <div class="small-box bg-info">
              <div class="inner">
              
              


                <h3>${InVisitorsCount}</h3>

                <p>Visitors Inside</p>
              </div>
              <div class="icon">
                <i class="ion ion-bag"></i>
              </div>
              <a href="#"  class="small-box-footer">More info <i class="fas fa-arrow-circle-right"></i></a>
            </div>
          </div>
          <!-- ./col -->
          <div class="col-lg-3 col-6" >
            <!-- small box -->
            <div class="small-box bg-success">
              <div class="inner">
                <h3>${inPermanenet}</h3>

                <p>Permanent Employees Inside</p>
              </div>
              <div class="icon">
                <i class="ion ion-stats-bars"></i>
              </div>
              <a href="#"  class="small-box-footer">More info <i class="fas fa-arrow-circle-right"></i></a>
            </div>
          </div>
          <!-- ./col -->
          <div class="col-lg-3 col-6">
            <!-- small box -->
            <div class="small-box bg-warning">
              <div class="inner">
                <h3>${inContract}</h3>

                <p>Contract Employees Inside</p>
              </div>
              <div class="icon">
                <i class="ion ion-person-add"></i>
              </div>
              <a href="#"  class="small-box-footer">More info <i class="fas fa-arrow-circle-right"></i></a>
            </div>
          </div>
          
          

</div>
    
    
    <!-- below is the link to find more icons -->
<!-- https://www.tutorialspoint.com/ionic/ionic_icons.htm -->


<script type="text/javascript">
        $( function() 
        		{
            $( "#txtfromdate" ).datepicker({ dateFormat: 'dd/mm/yy' });
            $( "#txttodate" ).datepicker({ dateFormat: 'dd/mm/yy' });
            
                       
            
          } );
        
        
        function reloadData()
        {
        	window.location="?a=showHomePage&fromDate="+txtfromdate.value+"&toDate="+txttodate.value;
        }   
        
        if('${param.fromDate}'!='')
        	{
		        txtfromdate.value='${param.fromDate}';
		        txttodate.value='${param.toDate}';
        	}
        else
        	{
        	
        	txtfromdate.value='${HomePageContent.get('todaysDate')}';
        	txttodate.value='${HomePageContent.get('todaysDate')}';
	        
        	}
        
         
        	
        	$(document).ready(function () {
        		
        		
        		var validDays=Number("${userdetails.validDays}");        
                if(validDays<=7)
                	{
                		alert("Application Subscription will Expire in "+validDays+" Days");
                		alert("All Configuration and Transaction May Be Lost In case not Renewed.");
                	}
                if(validDays<=0)
                	{
                	   alert("Please Renew Subscription or Contact Administrator");
                	   document.getElementById("refLogout").click();
                	}
        		
        	  });
        
        
        
      
        
        	document.getElementById("divTitle").innerHTML="<p1 class='labelFonts'>Hisaab Cloud</p1>";
        	document.title +=" <p1 class='labelFonts'>Hisaab Cloud</p1> ";
        </script>
        
        