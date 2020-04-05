<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.emp.model.*"%>
<%-- 此頁暫練習採用 Script 的寫法取值 --%>

<html lang="en">
<head>
<title>員工資料-listOneEmp.jsp</title>

<%
	EmpVO empVOreq = (EmpVO)request.getAttribute("empVO");
%>

<style>
/* navBar背景色 */
.navbar-bg{
	background-color:#fff;
	border-bottom:1px solid #e5e5e5;
}

table#table-1 {
	background-color: #CCCCFF;
	border: 2px solid black;
	text-align: center;
}

table#table-1 h4 {
	color: red;
	display: block;
	margin-bottom: 1px;
}

h4 {
	color: blue;
	display: inline;
}
</style>

<style>
table {
	width: 600px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
}

table, th, td {
	border: 1px solid #CCCCFF;
}

th, td {
	padding: 5px;
	text-align: center;
}
</style>

</head>

<body>
	<div class="d-flex" id="wrapper">
		<!-------------sideBar include--------------->
		<%@ include file="/back-end/sideBar.jsp"%>
		<!-- Page Content -->
		<div id="page-content-wrapper">
		<!-------------NavBar--------------->
		<%@ include file="/back-end/navBar.txt"%>
			<!---------------------- 網頁內容 開始--------------------------------->
			<div class="container-fluid">
				<h4>此頁暫練習採用 Script 的寫法取值:</h4>
				<table id="table-1">
					<tr>
						<td>
							<h3>員工資料 - ListOneEmp.jsp</h3>
							<h4>
								<a href="<%=request.getContextPath()%>/back-end/emp/listAllEmp.jsp"">回首頁</a>
							</h4>
						</td>
					</tr>
				</table>

				<table>
					<tr>
						<th>員工編號</th>
						<th>員工照片</th>
						<th>員工姓名</th>
						<th>員工信箱</th>
						<th>員工帳號</th>
						<th>員工密碼</th>

					</tr>
					<tr>
						<td><%=empVOreq.getEmp_no()%></td>
						<td><img width="200" heigth="180"  src="<%=request.getContextPath()%>/myUtil/PictureReader?emp_no=${empVO.emp_no}"></td>
						<td><%=empVOreq.getEmp_name()%></td>
						<td><%=empVOreq.getEmp_email()%></td>
						<td><%=empVOreq.getEmp_account()%></td>
						<td><%=empVOreq.getEmp_password()%></td>
					</tr>
				</table>
			</div>
		</div>
		<!-- /#page-content-wrapper -->
		<!-------------------- 網頁內容 結束--------------------->
	</div>
	<!-- /#wrapper -->

	<!-- Menu Toggle Script -->
	<script>
		$("#menu-toggle").click(function(e) {
			e.preventDefault();
			$("#wrapper").toggleClass("toggled");
		});
	</script>
</body>
</html>
