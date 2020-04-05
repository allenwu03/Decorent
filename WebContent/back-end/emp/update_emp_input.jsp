<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.emp.model.*"%>

<html lang="en">
<head>
<title>員工資料修改 - update_emp_input.jsp</title>

<%
	EmpVO empVOreq = (EmpVO) request.getAttribute("empVO");
	System.out.print(empVOreq);
%>

<style>
/* navBar背景色 */
.navbar-bg {
	background-color: #fff;
	border-bottom: 1px solid #e5e5e5;
}

/* 按鈕css*/
input.btn-color {
	padding: 5px 15px;
	background: #303c54;
	border: 0 none;
	cursor: pointer;
	-webkit-border-radius: 5px;
	border-radius: 5px;
	color: #fff;
}

input.btn-color2 {
	padding: 5px 15px;
	background: #dc3545;
	border: 0 none;
	cursor: pointer;
	-webkit-border-radius: 5px;
	border-radius: 5px;
	color: #fff;
}

input.btn-color3 {
	padding: 5px 15px;
	background: #adb5bd;
	border: 0 none;
	cursor: pointer;
	-webkit-border-radius: 5px;
	border-radius: 5px;
	color: #fff;
}
/*標籤的css*/
label.labelStyle {
	font-size: 1.5rem;
}
</style>


</head>

<body>
	<div class="d-flex" id="wrapper">
		<!-------------sideBar include--------------->
		<%@ include file="/back-end/sideBar.jsp"%>
		<!-- Page Content -->
		<div id="page-content-wrapper">
			<!-------------NavBar include--------------->
		<%@ include file="/back-end/navBar.txt"%>
			<!---------------------- 網頁內容 開始--------------------------------->
			<div class="container">

				<h1 style="margin-top: 2rem; margin-bottom: 2rem;">員工資料修改:</h1>

				<%-- 錯誤表列 --%>
				<c:if test="${not empty errorMsgs}">
					<font style="color: red">請修正以下錯誤:</font>
					<ul>
						<c:forEach var="message" items="${errorMsgs}">
							<li style="color: red">${message}</li>
						</c:forEach>
					</ul>
				</c:if>


				<FORM METHOD="post"
					ACTION="<%=request.getContextPath()%>/emp/emp.do" name="form1"
					enctype="multipart/form-data">

					<div class="form-group row">
						<label for="inputEmail3"
							class="col-sm-2 col-form-label labelStyle"><font
							color=red><b>*</b></font>編號</label>
						<div class="col-sm-5">
							<input type="text" class="form-control"
								value="<%=empVOreq.getEmp_no()%>" readonly="readonly">
						</div>
					</div>
					<div class="form-group row">
						<label for="inputEmail3"
							class="col-sm-2 col-form-label labelStyle">照片</label>

						<input type="file" id="file" name="emp_photo" class="form-control" style="margin-bottom: 0.5rem;"> 
						<span id="image">
						<img src="<%=request.getContextPath()%>/myUtil/PictureReader?emp_no=${empVO.emp_no}"style="width: 100px; height: 120px;" style="width: 6rem; height: 6rem;"  />
						</span>
					</div>

					<div class="form-group row">
						<label for="inputEmail3"
							class="col-sm-2 col-form-label labelStyle">信箱</label>
						<div class="col-sm-5">
							<input type="email" class="form-control" name="emp_email"
								value="<%=(empVOreq == null) ? "111@aaa.com" : empVOreq.getEmp_email()%>" />
						</div>
					</div>

					<div class="form-group row">
						<label for="inputEmail3"
							class="col-sm-2 col-form-label labelStyle">姓名</label>
						<div class="col-sm-5">
							<input type="text" name="emp_name"
								value="<%=(empVOreq == null) ? "吳永志" : empVOreq.getEmp_name()%>"
								class="form-control" />
						</div>
					</div>

					<div class="form-group row">
						<label for="inputPassword3"
							class="col-sm-2 col-form-label labelStyle">帳號</label>
						<div class="col-sm-5">
							<input type="text" class="form-control" name="emp_account"
								value="<%=(empVOreq == null) ? "DA105G3" : empVOreq.getEmp_account()%>" />
						</div>
					</div>

					<div class="form-group row">
						<label for="inputPassword3"
							class="col-sm-2 col-form-label labelStyle">密碼</label>
						<div class="col-sm-5">
							<input type="text" class="form-control" name="emp_password"
								value="<%=(empVOreq == null) ? "123456" : empVOreq.getEmp_password()%>" />
						</div>
					</div>

					<br> <input type="hidden" name="action" value="update">
					<input type="hidden" name="emp_no"
						value="<%=empVOreq.getEmp_no()%>"> 
					<input type="submit" value="送出修改" class="btn-color" style="margin-right: 1rem;">
					<input type="button" value="取消" class="btn-color3" onclick="javascript:location.href='<%=request.getContextPath()%>/back-end/emp/listAllEmp.jsp'">
				</FORM>
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

		document.getElementById('file').onchange = function() {
			var imgFile = this.files[0];
			var fr = new FileReader();
			fr.onload = function() {
				document.getElementById('image').getElementsByTagName('img')[0].src = fr.result;
			};
			fr.readAsDataURL(imgFile);
		};
	</script>

</body>

</html>
