<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.emp.model.*"%>
<%@ page import="com.authority.model.*"%>

<jsp:useBean id="empSvc" scope="page" class="com.emp.model.EmpService" />

<%
	List<EmpVO> list = empSvc.getAll();

	pageContext.setAttribute("list", list);
// 	EmpVO empVOreq = (EmpVO)request.getAttribute("empVO");
%>

<html lang="en">
<head>
<title>所有員工資料-listAllEmp.jsp</title>

<style>
/* navBar背景色 */
.navbar-bg{
	background-color:#fff;
	border-bottom:1px solid #e5e5e5;
}

/* 表格總寬度 */
div.container-fluid {
	max-width: 1600px;
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
/* 第二組按鈕css*/  
 input.btn-color2 {  
      padding:5px 15px; 
      background:#dc3545;   
      border:0 none;      
      cursor:pointer;  
      -webkit-border-radius: 5px;  
      border-radius: 5px;
      color:#fff;  
  } 
  
/* 表格的表頭 */
table.table-striped .thead th{
	background-color:#303c54;
	color:#fff;
}

/* 按鈕css*/  
 input.btn-color2 {  
      padding:5px 15px; 
      background:#dc3545;   
      border:0 none;      
      cursor:pointer;  
      -webkit-border-radius: 5px;  
      border-radius: 5px;
      color:#fff;  
  } 
  
/*表格欄位置中*/
table.table td, table.table th{
	vertical-align: middle;
	font-size: 1.4rem;
}  

</style>
</head>

<body>
	<div class="d-flex" id="wrapper">
		<!-------------sideBar ↓--------------->
		<%@ include file="/back-end/sideBar.jsp"%>
		<!-------------sideBar ↑-------------->
		<% 
			int index = -1;
			if(list.contains(empVO)){
				index = list.indexOf(empVO);
			}			
			if(index!=-1){
				list.remove(index);
			}
		%>
		<!-- Page Content -->
		<div id="page-content-wrapper" style="background-color: #f7f7f8;">
			<!-------------------- navBar 開始--------------->
			<%@ include file="/back-end/navBar.txt"%>

			<!---------------------- 網頁內容 開始--------------------------------->
			<div class="container-fluid"
				style="margin-left: auto; margin-right:auto; margin-top: 15px;">
				
				<h1>員工管理:</h1>

				<%-- 錯誤表列 --%>
				<c:if test="${not empty errorMsgs}">
					<font style="color: red">請修正以下錯誤:</font>
					<ul>
						<c:forEach var="message" items="${errorMsgs}">
							<li style="color: red">${message}</li>
						</c:forEach>
					</ul>
				</c:if>
							
						<span>
<%-- 							<input onclick="javascript:location.href='<%=request.getContextPath()%>/back-end/emp/addEmp.jsp'"  --%>
<!-- 							type="button" value="新增員工" class="btn-color"  -->
<!-- 							style="float:right; margin-bottom:5px;" data-toggle="modal" -->
<!-- 					data-target="#exampleModal">  -->
					
						<input type="button" value="新增員工" class="btn-color"
							style="float:right; margin-bottom:5px;" onclick="location.href='<%=request.getContextPath()%>/back-end/emp/addEmp.jsp'"> 
						</span>
						
				<!-- 新增部分 -->
						<div>
							<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/emp/emp.do" style="margin-bottom: 0px; float:right;margin-right:1.5rem;">
								<input type="submit" value="個人資料修改" class="btn-color" data-toggle="modal"> 
								<input type="hidden" name="emp_no" value="${empVO.emp_no}"> 
								<input type="hidden" name="action" value="getOne_For_Update">
							</FORM>
						</div>
				<!-- 新增部分 -->
				
				<table class="table table-striped text-center">
					<thead class="thead ">
						<tr>
							<th>員工編號</th>
							<th>員工照片</th>
							<th>員工姓名</th>
							<th>員工信箱</th>
							<th>員工帳號</th>
<!-- 							<th>員工密碼</th> -->
							<th colspan="2" style="text-align:center">功能項目</th>
<!-- 							<th>刪除</th> -->
						</tr>
					</thead>
					<tbody>
						<%@ include file="/back-end/pages/page1.file"%>
						
						<c:forEach var="empVO2" items="${list}" begin="<%=pageIndex%>"
							end="<%=pageIndex+rowsPerPage-1%>">

							<tr>
								<th scope="row">${empVO2.emp_no}</th>
								<td><img width="100" height="120"
									src="<%=request.getContextPath()%>/myUtil/PictureReader?emp_no=${empVO2.emp_no}"></td>
								<td>${empVO2.emp_name}</td>
								<td>${empVO2.emp_email}</td>
								<td>${empVO2.emp_account}</td>
<%-- 								<td>${empVO2.emp_password}</td> --%>
								
								
								<td>
									<FORM METHOD="post"
										ACTION="<%=request.getContextPath()%>/authority/authority.do"
										style="margin-bottom: 0px;">
									<input type="submit" value="權限修改" class="btn-color"> 
									<input type="hidden" name="emp_no" value="${empVO2.emp_no}"/>
									<input type="hidden" name="action" value="getAuthority"/>
									</FORM>									
								</td>

								<td>
									<FORM METHOD="post"
										ACTION="<%=request.getContextPath()%>/emp/emp.do"
										style="margin-bottom: 0px;">
										<input type="submit" value="個資修改" class="btn-color2" data-toggle="modal"> 
										<input type="hidden" name="emp_no" value="${empVO2.emp_no}"> 
										<input type="hidden" name="action" value="getOne_For_Update">
									</FORM>
								</td>

<!-- 								<td> -->
<!-- 									<FORM METHOD="post" -->
<%-- 										ACTION="<%=request.getContextPath()%>/emp/emp.do" --%>
<!-- 										style="margin-bottom: 0px;"> -->
<!-- 										<input type="submit" value="刪除" class="btn-color2">  -->
<%-- 										<input type="hidden" name="emp_no" value="${empVO2.emp_no}">  --%>
<!-- 										<input type="hidden" name="action" value="delete"> -->
<!-- 									</FORM> -->
<!-- 								</td> -->
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<%@ include file="/back-end/pages/page2.file"%>
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
		
		
		// 照片更換
		document.getElementById('file').onchange = function() {
			var imgFile = this.files[0];
			var fr = new FileReader();
			fr.onload = function() {
				document.getElementById('image')
						.getElementsByTagName('img')[0].src = fr.result;
			};
			fr.readAsDataURL(imgFile);
		};
	</script>

</body>

</html>
