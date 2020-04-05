<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.emp.model.*"%>

<html lang="en">
<head>
<title>員工資料新增-addEmp.jsp</title>

<style>
/* navBar背景色 */
.navbar-bg {
	background-color: #fff;
	border-bottom: 1px solid #e5e5e5;
}

table#table-1 {
	background-color: #7474b3;
	border: 2px solid black;
	text-align: center;
}

table#table-1 h3 {
	color: white;
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
	margin-top: 1px;
	margin-bottom: 1px;
}

table, th, td {
	border: 0px solid #CCCCFF;
}

th, td {
	padding: 1px;
}

table tr img {
	width: 65px;
	height: 65px;
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
		<%@ include file="/back-end/navBar.txt"%>
			<!---------------------- 網頁內容 開始--------------------------------->

			<div class="container">
				<h1 style="margin-top: 2rem; margin-bottom: 2rem;">員工資料新增:</h1>

				<%-- 錯誤表列 --%>
				<c:if test="${not empty errorMsgs}">
				
					<font style="color: red">請修正以下錯誤:</font>
					<ul>
						<c:forEach var="message" items="${errorMsgs}">
							<li style="color: red">${message}</li>
						</c:forEach>
					</ul>
					
				</c:if>

				<jsp:useBean id="featuresSvc" scope="page"
					class="com.features.model.FeaturesService" />

				<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/emp/emp.do" name="form1"
					enctype="multipart/form-data">
					
					<table>
					<tr>
						<div class="form-group row">
							<label for="inputEmail3"
								class="col-sm-2 col-form-label labelStyle">姓名</label>
							<div class="col-sm-5">
								<input type="text" name="emp_name"
									value=""
									class="form-control" />
							</div>
						</div>
					</tr>	
						<tr>
							<div class="form-group row">
								<label for="inputEmail3"
									class="col-sm-2 col-form-label labelStyle">照片</label>

								<div class="col-sm-5">
									<input type="file" id="file" name="emp_photo"
										class="form-control" style="margin-bottom: 0.5rem;"> <span
										id="image"><img
										src="<%=request.getContextPath()%>/images/預設頭像.png"
										style="width: 6rem; height: 6rem;" /></span>
								</div>
							</div>
						</tr>
						<div class="form-group row">
							<label for="inputEmail3"
								class="col-sm-2 col-form-label labelStyle">信箱</label>

							<div class="col-sm-5">
								<input type="email" class="form-control" name="emp_email"
									value="" />
							</div>
						</div>

						<div class="form-group row">
							<label for="inputPassword3"
								class="col-sm-2 col-form-label labelStyle">帳號</label>
							<div class="col-sm-5">
								<input type="text" class="form-control" name="emp_account"
									value="" />
							</div>
						</div>

						<div class="form-group row">
							<div class="col-sm-2" style="font-size: 1.5rem;">權限</div>
							<div class="col-sm-5">
								<c:forEach var="featuresVO" items="${featuresSvc.all}">

									<div class="form-check">
										<input class="form-check-input" type="checkbox" value="${featuresVO.features_no}"
											name="features_no" id="${featuresVO.features_no}"> <label
											class="form-check-label checkStyle"
											for="${featuresVO.features_no}"
											>${featuresVO.features}</label>
									</div>
								</c:forEach>
							</div>
						</div>
			</div>
			</table>
			<br> <input type="hidden" name="action" value="insert">
			<input type="submit" value="新增員工" class="btn-color" style="margin-right: 1rem;">
			<input type="button" value="取消" class="btn-color3" onclick="javascript:location.href='<%=request.getContextPath()%>/back-end/emp/listAllEmp.jsp'">
			<br>
			<input type="button" value="神奇小按鈕" class="btn-color3" id="btnAddEmp" style="margin-top:1rem;">
			</FORM>
		</div>
	</div>

	</div>
	<!-- /#wrapper -->

	<!-- Menu Toggle Script -->
	<script>
		$("#menu-toggle").click(function(e) {
			e.preventDefault();
			$("#wrapper").toggleClass("toggled");
		});
		// 照片更換
		document.getElementById('file').onchange = function() {
			var imgFile = this.files[0];
			var fr = new FileReader();
			fr.onload = function() {
				document.getElementById('image').getElementsByTagName('img')[0].src = fr.result;
			};
			fr.readAsDataURL(imgFile);
		};
		
		//神奇小按鈕
		$("#btnAddEmp").click(function(){
			$("[name='emp_name']").val("章魚哥");
			$("[name='emp_email']").val("allenwu032016@gmail.com");
			$("[name='emp_account']").val("666");
			
			$('input:checkbox').eq(0).attr("checked",'true');
			$('input:checkbox').eq(2).attr("checked",'true');
			$('input:checkbox').eq(3).attr("checked",'true');
			$('input:checkbox').eq(4).attr("checked",'true');
			$('input:checkbox').eq(5).attr("checked",'true');
			$('input:checkbox').eq(6).attr("checked",'true');
			$('input:checkbox').eq(7).attr("checked",'true');
		});
	</script>
</body>

</html>
