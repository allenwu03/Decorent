<%@ page contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">
<head>
	<meta charset="UTF-8">
	<!--RWD-->
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

	<!-- Bootstrap CSS -->
   	<link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet" type="text/css" >
   	<script src="<%=request.getContextPath()%>/js/jquery-3.3.1.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/awesomeFont.js"></script>
    <script src="<%=request.getContextPath()%>/js/popper.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
  
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/empLogin.css">  
    <!--sweetalert-->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
    
    <style>
    body{
    	background-image:linear-gradient(to right, rgba(0, 0, 0, 0.4),  rgba(0, 0, 0, 0.4)),
                   url(<%=request.getContextPath()%>/back-end/emp/images/emplogin.jpg);
    }
    
    div.card-body img{
    	height:85px;
    }
    
    </style>
    
	<title>員工登入</title>
</head>

<body>
	<div class="container">
    <div class="row">
      <div class="col-sm-9 col-md-7 col-lg-5 mx-auto">
        <div class="card card-signin my-5">
          <div class="card-body">
          <div class="text-center">
          	<img alt="#" src="<%=request.getContextPath()%>/images/LogoLS.png">
          </div>
            	
            <div class="card-title text-center">
            	員工登入
            </div>
            
            
            <form class="form-signin" action="<%=request.getContextPath() %>/emp/emp.do" 
            method="post" >
            <%-- 錯誤表列 --%>
			 <div  class="row">
			 <c:if test="${not empty errorMsgs}">
				<ul>
					<c:forEach var="message" items="${errorMsgs}">
						<li  style="color: red;list-style: none;font-size: 1.3rem;">${message}</li>
					</c:forEach>
				</ul>
			</c:if>
			</div>
				
              <div class="form-label-group">
                <input type="text" name="emp_account" id="inputAccount" class="form-control" placeholder="Account" required autofocus>        
                <label for="inputAccount">Account</label>
              </div>
				
				
				
              <div class="form-label-group">
                <input type="password" name="emp_password" id="inputPassword" class="form-control" placeholder="Password" required>
                <label for="inputPassword">Password</label>
              </div>
             
              
			  <input type="hidden" name="action" value="emp_account">
              <button class="btn btn-lg btn-primary text-uppercase btn-font" type="submit" style="margin-top:2rem;">登入</button>
            
            </form>
                        
          </div>
        </div>
      </div>
    </div>
  </div>	
</body>
</html>