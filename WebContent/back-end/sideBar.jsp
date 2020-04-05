<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="java.util.*"%>
<%@ page import="com.emp.model.*"%>
<%@ page import="com.features.model.*"%>
<%@ page import="com.authority.model.*"%>

<html lang="en">
<head>
<title>sideBar</title>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<!-- Bootstrap core CSS -->
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css"
	rel="stylesheet">
<!-- Custom styles for this template -->
<link href="<%=request.getContextPath()%>/css/backIndex.css"
	rel="stylesheet">

<style>
body {
	font-family: 'Microsoft JhengHei', sans-serif;
}

div.sidebar-heading img {
	height: 45px;

}

div.list-group a {
	background-color: rgba(60, 75, 100);
	color: #fff;
	font-size: 20px;
	text-align: center;
	letter-spacing: 2px;
}

/* sideBar 子目錄 */
ul li .sidebarColor { 
 	background-color: #546580;  
 } 
 
ul li .sidebarColor:hover {  
 	background-color: #fff; 
  }  

#sidebar-wrapper {
	background-color: rgba(48, 60, 84);
	color: #fff;
}

/* sideBar動畫 */
.list-group-item-action:focus, .list-group-item-action:hover {
	transition: background .5s;
}

table.table td, table.table th {
    vertical-align: middle;
/*     font-size: 1.4rem; */
}


</style>


</head>
<body>
	<%

		EmpVO empVO = (EmpVO) session.getAttribute("empVO");
		String emp_no = empVO.getEmp_no();

		AuthorityService authoritySvc = new AuthorityService();
		List<AuthorityVO> authorityList = authoritySvc.findByEmpno(emp_no);
		pageContext.setAttribute("authorityList", authorityList);
		
		
		AuthorityVO authVO = new AuthorityVO();
		List<String> listFeatures = new ArrayList<String>();
		for (AuthorityVO auth : authorityList) {
			listFeatures.add(auth.getFeatures_no());
		}
	%>


	<!-- Sidebar -->
	<div class="border-right" id="sidebar-wrapper">
		<div class="sidebar-heading" style="font-size: 25px;">
			<img alt="#" src="<%=request.getContextPath()%>/images/LogoLS.png">

		</div>
		<div class="list-group list-group-flush">
			<a href="<%=request.getContextPath()%>/back-end/backIndex.jsp"
				class="list-group-item list-group-item-action">後台首頁</a>

			<c:forEach items="${authorityList}" var="authVO">
				<c:if test="${fn:contains(authVO.features_no,'FE000001')}">
					<a href="${pageContext.request.contextPath}/back-end/emp/listAllEmp.jsp" class="list-group-item list-group-item-action">員工管理</a>
				</c:if>
				
				<c:if test="${fn:contains(authVO.features_no,'FE000002')}">
					<a href="#" class="list-group-item list-group-item-action">會員管理</a>
				</c:if>
				
				<c:if test="${fn:contains(authVO.features_no,'FE000003')}">
				<li class="active" style="list-style: none;">
                <a href="#newItem" data-toggle="collapse" aria-expanded="false" class="list-group-item list-group-item-action dropdown-toggle">新品管理</a>
                
                 <ul class="collapse list-unstyled" id="newItem" style="margin: 0;">
                    <li>
                        <a href="${pageContext.request.contextPath}/back-end/product/ProductList.jsp" class="list-group-item list-group-item-action sidebarColor">新品管理</a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/back-end/product/ProductOrderList.jsp" class="list-group-item list-group-item-action sidebarColor">新品訂單</a>
                    </li>
                </ul>          
				</li>
				</c:if>
				
				<c:if test="${fn:contains(authVO.features_no,'FE000004')}">
				<li class="active" style="list-style: none;">
                <a href="#rentItem" data-toggle="collapse" aria-expanded="false" class="list-group-item list-group-item-action dropdown-toggle">租賃管理</a>
                
                 <ul class="collapse list-unstyled" id="rentItem" style="margin: 0;">
                    <li>
                        <a href="${pageContext.request.contextPath}/back-end/rent/listAllRent.jsp" class="list-group-item list-group-item-action sidebarColor">租賃商品</a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/back-end/rent_order/listAllRent_order.jsp" class="list-group-item list-group-item-action sidebarColor">租賃訂單</a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/back-end/rent_detail/listAllRent_detail.jsp" class="list-group-item list-group-item-action sidebarColor">租賃明細</a>
                    </li>
                </ul>          
				</li>
				
				</c:if>
				
				<c:if test="${fn:contains(authVO.features_no,'FE000005')}">
					<a href="${pageContext.request.contextPath}/back-end/vote/VoteIndex.jsp" class="list-group-item list-group-item-action">投票活動</a>
				</c:if>
				
				<c:if test="${fn:contains(authVO.features_no,'FE000006')}">
				<li class="active" style="list-style: none;">
                <a href="#bid" data-toggle="collapse" aria-expanded="false" class="list-group-item list-group-item-action dropdown-toggle">競標活動</a>
                
                 <ul class="collapse list-unstyled" id="bid" style="margin: 0;">
                    <li>
                        <a href="${pageContext.request.contextPath}/back-end/bid/BidIndex.jsp" class="list-group-item list-group-item-action sidebarColor">競標活動</a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/back-end/bid_lots/BidLotsIndex.jsp" class="list-group-item list-group-item-action sidebarColor">競標訂單</a>
                    </li>
                </ul>
                </li>          
				</c:if>
				
				<c:if test="${fn:contains(authVO.features_no,'FE000007')}">
					<a href="${pageContext.request.contextPath}/back-end/article/RBlistAllArticle.jsp" class="list-group-item list-group-item-action">論壇文章</a>
				</c:if>
				
				<c:if test="${fn:contains(authVO.features_no,'FE000008')}">
					<a href="${pageContext.request.contextPath}/back-end/designer/RBVerifyDesigner.jsp" class="list-group-item list-group-item-action">設計師</a>
				</c:if>
				
				<c:if test="${fn:contains(authVO.features_no,'FE000009')}">
					<a href="#" class="list-group-item list-group-item-action">聊天室</a>
				</c:if>
			</c:forEach>

		</div>
	</div>
	<!-- /#sidebar-wrapper -->

	<!-- Bootstrap core JavaScript -->
	<script src="<%=request.getContextPath()%>/js/jquery-3.3.1.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/awesomeFont.js"></script>
	

</body>
</html>