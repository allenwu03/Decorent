<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.emp.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="java.util.*"%>
<%@ page import="com.emp.model.*"%>
<%@ page import="com.features.model.*"%>
<%@ page import="com.authority.model.*"%>

<jsp:useBean id="empSvc" scope="page" class="com.emp.model.EmpService" />
<jsp:useBean id="featuresSvc" scope="page" class="com.features.model.FeaturesService" />

<html>
<head>
<meta charset="UTF-8">

<title>後台首頁</title>


<style>
* {
	box-sizing: border-box;
}

body {
	margin: 0;
	
}

/* 按鈕css*/
input.btn-color2 {
	padding: 5px 15px;
	background: #dc3545;
	border: 0 none;
	cursor: pointer;
	-webkit-border-radius: 5px;
	border-radius: 5px;
	color: #fff;
}

/* 首頁css*/


.flip h1 {
  font-size: 3em;
  letter-spacing: 0.2em;
   font-weight:normal;
  text-align: center;
  font-family: 'Microsoft JhengHei',sans-serif;
  padding: 1em;
}

.flip {
  position: relative;
  display: inline-block;
  margin-right: 2px;
  margin-bottom: 2rem;
  width: 400px;
}
.flip > .front {
  display: block;
  color: white;
  width: inherit;
  background-size: cover !important;
  background-position: center !important;
  height: 220px;
  padding: 1em 2em;
  background: #313131;
  border-radius: 10px;
}
.flip > .front p {
  font-size: 0.9125rem;
  line-height: 160%;
  color: #999;
}

.text-shadow {
  text-shadow: 1px 1px rgba(0, 0, 0, 0.04), 2px 2px rgba(0, 0, 0, 0.04), 3px 3px rgba(0, 0, 0, 0.04), 4px 4px rgba(0, 0, 0, 0.04), 0.125rem 0.125rem rgba(0, 0, 0, 0.04), 6px 6px rgba(0, 0, 0, 0.04), 7px 7px rgba(0, 0, 0, 0.04), 8px 8px rgba(0, 0, 0, 0.04), 9px 9px rgba(0, 0, 0, 0.04), 0.3125rem 0.3125rem rgba(0, 0, 0, 0.04), 11px 11px rgba(0, 0, 0, 0.04), 12px 12px rgba(0, 0, 0, 0.04), 13px 13px rgba(0, 0, 0, 0.04), 14px 14px rgba(0, 0, 0, 0.04), 0.625rem 0.625rem rgba(0, 0, 0, 0.04), 16px 16px rgba(0, 0, 0, 0.04), 17px 17px rgba(0, 0, 0, 0.04), 18px 18px rgba(0, 0, 0, 0.04), 19px 19px rgba(0, 0, 0, 0.04), 1.25rem 1.25rem rgba(0, 0, 0, 0.04);
}


  .flip{
    transition: .3s ease-in-out;
    transform: scale(1);
  }

  .flip:hover{
    transform: scale(1.2);
  
  }
  
  /* 按鈕css 按鈕class加上btn-color*/
 input.btn-color {  
      padding:5px 15px; 
      background:#303c54;   
      border:0 none;      
      cursor:pointer;  
      -webkit-border-radius: 5px;  
      border-radius: 5px;
      color:#fff;  
  } 

</style>


</head>
<body>
	<div class="d-flex" id="wrapper">
		<!-------------sideBar include--------------->
		<%@ include file="/back-end/sideBar.jsp"%>
		<%
			// 		List<AuthorityVO> list = authoritySvc.findByEmpno(emp_no);
			// 		pageContext.setAttribute("list", list);
		%>
		<!-- Page Content -->
		<div id="page-content-wrapper" style="background-color: #f7f7f8;">

		<%@ include file="/back-end/navBar.txt"%>
			<!---------------------- 網頁內容 開始--------------------------------->
			<div class="container-fluid changePage" style="display: none;text-align:center; ">
<!-- 				<h1 style="margin-bottom:2rem;margin-top:1.5rem;font-size:3.5em;">後台首頁</h1> -->
				<div style="margin-bottom:3.5rem;margin-top:3.5rem;"></div>


				<div class="container-fluid">

					<div class="row">
						<c:forEach items="${authorityList}" var="authVO">
							<c:if test="${fn:contains(authVO.features_no,'FE000001')}">
								<div class="col-4">
								
								<div class="flip"onclick="location.href='${pageContext.request.contextPath}/back-end/emp/listAllEmp.jsp';">
									<div class="front"style="background-image: url(emp/images/employee.jpg);">
										<h1 class="text-shadow">
											員工管理
										</h1>
									</div>
								</div>

								</div>
							</c:if>

							<c:if test="${fn:contains(authVO.features_no,'FE000002')}">
								<div class="col-4">
								
								<div class="flip"onclick="location.href='#';">
									<div class="front"style="background-image: url(emp/images/member.jpg);">
										<h1 class="text-shadow">
											會員管理
										</h1>
									</div>
								</div>
								
								</div>
							</c:if>

							<c:if test="${fn:contains(authVO.features_no,'FE000003')}">
								<div class="col-4">
								
								<div class="flip"onclick="location.href='${pageContext.request.contextPath}/back-end/product/ProductList.jsp';">
									<div class="front"style="background-image: url(emp/images/new.jpg);">
										<h1 class="text-shadow">
											新品管理
										</h1>
									</div>
								</div>

								</div>
							</c:if>

							<c:if test="${fn:contains(authVO.features_no,'FE000004')}">
								<div class="col-4">
								
								<div class="flip"onclick="location.href='${pageContext.request.contextPath}/back-end/rent/listAllRent.jsp';">
									<div class="front"style="background-image: url(emp/images/rent.jpg);">
										<h1 class="text-shadow">
											租賃管理
										</h1>
									</div>
								</div>
								

								</div>
							</c:if>

							<c:if test="${fn:contains(authVO.features_no,'FE000005')}">
								<div class="col-4">
								
								<div class="flip"onclick="location.href='${pageContext.request.contextPath}/back-end/vote/VoteIndex.jsp';">
									<div class="front"style="background-image: url(emp/images/vote.jpg);">
										<h1 class="text-shadow">
											投票活動
										</h1>
									</div>
								</div>
								

								</div>
							</c:if>

							<c:if test="${fn:contains(authVO.features_no,'FE000006')}">
								<div class="col-4">
								
								<div class="flip"onclick="location.href='${pageContext.request.contextPath}/back-end/bid/BidIndex.jsp';">
									<div class="front"style="background-image: url(emp/images/bid.jpg);">
										<h1 class="text-shadow">
											競標活動
										</h1>
									</div>
								</div>
								

								</div>
							</c:if>

							<c:if test="${fn:contains(authVO.features_no,'FE000006')}">
								<div class="col-4">
								
								<div class="flip"onclick="location.href='${pageContext.request.contextPath}/back-end/article/RBlistAllArticle.jsp';">
									<div class="front"style="background-image: url(emp/images/article.jpg);">
										<h1 class="text-shadow">
											論壇文章
										</h1>
									</div>
								</div>
								

								</div>
							</c:if>

							<c:if test="${fn:contains(authVO.features_no,'FE000008')}">
								<div class="col-4">
								
								<div class="flip"onclick="location.href='${pageContext.request.contextPath}/back-end/designer/RBVerifyDesigner.jsp';">
									<div class="front"style="background-image: url(emp/images/designer.jpg);">
										<h1 class="text-shadow">
											設計師
										</h1>
									</div>
								</div>

								</div>
							</c:if>

							<c:if test="${fn:contains(authVO.features_no,'FE000009')}">
								<div class="col-4">
								
								<div class="flip"onclick="location.href='#';">
									<div class="front"style="background-image: url(emp/images/chat.jpg);">
										<h1 class="text-shadow">
											聊天室
										</h1>
									</div>
								</div>

								</div>
							</c:if>
						</c:forEach>
					</div>
				</div>
			</div>
			<!-- /#page-content-wrapper -->
			<!-------------------- 網頁內容 結束--------------------->
		</div>
		<!-- /#wrapper -->

		<!-- Bootstrap core JavaScript -->
		<script src="<%=request.getContextPath()%>/js/jquery-3.3.1.min.js"></script>
<%-- 		<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script> --%>
		<script src="<%=request.getContextPath()%>/js/awesomeFont.js"></script>

		<!-- Menu Toggle Script -->
		<script>
			$("#menu-toggle").click(function(e) {
				e.preventDefault();
				$("#wrapper").toggleClass("toggled");
			});

			$(document).ready(function() {
				$(".changePage").show("500");
			})
		</script>
		

</body>
</html>