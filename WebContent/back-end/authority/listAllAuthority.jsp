<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.emp.model.*"%>
<%@ page import="com.authority.model.*"%>
<%@ page import="com.features.model.*"%>

<%

	EmpVO empVOAuth = (EmpVO) request.getAttribute("empVO");
	List<String> list = new ArrayList<String>();
	List<AuthorityVO> authlist = (List<AuthorityVO>) request.getAttribute("list");
	
	for (AuthorityVO i : authlist) {
		list.add(i.getFeatures_no());
	}
%>

<html>
<head>
<meta charset="UTF-8">
<title>listAllAuthority</title>

<style>
/* navBar背景色 */
.navbar-bg {
	background-color: #fff;
	border-bottom: 1px solid #e5e5e5;
}

/* 按鈕css*/
 input.btn-color {  
      padding:5px 15px; 
      background:#303c54;   
      border:0 none;      
      cursor:pointer;  
      -webkit-border-radius: 5px;  
      border-radius: 5px;
      color:#fff;  
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
label.labelStyle{
	font-size:1.5rem;
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
		
		<div class="container">
			<h1 style="margin-top: 2rem;margin-bottom: 2rem;">員工權限修改:</h1>
			
				<FORM method="post"
					ACTION="<%=request.getContextPath()%>/authority/authority.do">
					<table>
					
					<div class="form-group row">
						<label for="inputEmail3" class="col-sm-2 col-form-label labelStyle">編號</label>
						<div class="col-sm-5">
							<input type="text" class="form-control"
								value="<%=empVOAuth.getEmp_no()%>" readonly="readonly">
						</div>
					</div>
					
					<div class="form-group row">
						<label for="inputEmail3" class="col-sm-2 col-form-label labelStyle">姓名</label>
						<div class="col-sm-5">
							<input type="text" class="form-control"
								value="<%=empVOAuth.getEmp_name()%>" readonly="readonly">
						</div>
					</div>
					
					<div class="form-group row">
					<div class="col-sm-2" style="font-size:1.5rem;">權限</div>
					
					<div class="col-sm-10">
						<div class="form-check">
						<c:if test="<%=list.contains(\"FE000001\")%>">
							<div class="col-3">
								<input id="fe1" type="checkbox" name="authority" value="FE000001" checked />					
								<label for="fe1">員工管理</label>
							</div>
						</c:if>

						<c:if test="<%=!(list.contains(\"FE000001\"))%>">
							<div class="col-3">
								<input id="fe1" type="checkbox" name="authority" value="FE000001" /> 
								<label for="fe1">員工管理</label>
							</div>
						</c:if>


						<c:if test="<%=list.contains(\"FE000002\")%>">
							<div class="col-3">
								<input id="fe2" type="checkbox" name="authority" value="FE000002" checked />
								
								<label for="fe2">會員管理</label>
							</div>
						</c:if>

						<c:if test="<%=!(list.contains(\"FE000002\"))%>">
							<div class="col-3">
								<input id="fe2" type="checkbox" name="authority" value="FE000002" />
								<label for="fe2">會員管理</label>
							</div>
						</c:if>

						<c:if test="<%=list.contains(\"FE000003\")%>">
							<div class="col-3">
								<input id="fe3" type="checkbox" name="authority" value="FE000003" checked />
								
								<label for="fe3">新品管理</label>
							</div>
						</c:if>

						<c:if test="<%=!(list.contains(\"FE000003\"))%>">
							<div class="col-3">
								<input id="fe3" type="checkbox" name="authority" value="FE000003" /> 
								<label for="fe3">新品管理</label>
							</div>
						</c:if>

						<c:if test="<%=list.contains(\"FE000004\")%>">
							<div class="col-3">
								<input id="fe4" type="checkbox" name="authority" value="FE000004" checked />
								
								<label for="fe4">租賃管理</label>
							</div>
						</c:if>

						<c:if test="<%=!(listFeatures.contains(\"FE000004\"))%>">
							<div class="col-3">
								<input id="fe4" type="checkbox" name="authority" value="FE000004" />
								<label for="fe4">租賃管理</label>
							</div>
						</c:if>
						
						<c:if test="<%=list.contains(\"FE000005\")%>">
							<div class="col-3">
								<input id="fe5" type="checkbox" name="authority" value="FE000005" checked />
								
								<label for="fe5">投票活動</label>
							</div>
						</c:if>

						<c:if test="<%=!(list.contains(\"FE000005\"))%>">
							<div class="col-3">
								<input id="fe5" type="checkbox" name="authority" value="FE000005" />
								<label for="fe5">投票活動</label>
							</div>
						</c:if>


						<c:if test="<%=list.contains(\"FE000006\")%>">
							<div class="col-3">
								<input id="fe6" type="checkbox" name="authority" value="FE000006" checked />
							
								<label for="fe6">競標活動</label>
							</div>
						</c:if>

						<c:if test="<%=!(list.contains(\"FE000006\"))%>">
							<div class="col-3">
								<input id="fe6" type="checkbox" name="authority" value="FE000006" />
								<label for="fe6">競標活動</label>
							</div>
						</c:if>

						<c:if test="<%=list.contains(\"FE000007\")%>">
							<div class="col-3">
								<input id="fe7" type="checkbox" name="authority" value="FE000007" checked />
								
								<label for="fe7">論壇文章</label>
							</div>
						</c:if>

						<c:if test="<%=!(list.contains(\"FE000007\"))%>">
							<div class="col-3">
								<input id="fe7" type="checkbox" name="authority" value="FE000007" /> 
								<label for="fe7">論壇文章</label>
							</div>
						</c:if>

						<c:if test="<%=list.contains(\"FE000008\")%>">
							<div class="col-3">
								<input id="fe8" type="checkbox" name="authority" value="FE000008" checked />
								
								<label for="fe8">設計師</label>
							</div>
						</c:if>

						<c:if test="<%=!(list.contains(\"FE000008\"))%>">
							<div class="col-3">
								<input id="fe8" type="checkbox" name="authority" value="FE000008" />
								<label for="fe8">設計師</label>
							</div>
						</c:if>

						<c:if test="<%=list.contains(\"FE000009\")%>">
							<div class="col-3">
								<input id="fe9" type="checkbox" name="authority" value="FE000009" checked />
								
								<label for="fe9">聊天室</label>
							</div>
						</c:if>

						<c:if test="<%=!(list.contains(\"FE000009\"))%>">
							<div class="col-3">
								<input id="fe9" type="checkbox" name="authority" value="FE000009" />
								<label for="fe9">聊天室</label>
							</div>
						</c:if>
						</div>
						</div>
					</div>
					</table>
					<input type="hidden" name="action" value="update"> <input
						type="hidden" name="emp_no" value="<%=empVOAuth.getEmp_no()%>">
					<input type="submit" value="送出修改" class="btn-color" style="margin-right: 1rem;">
					
					<input type="button" value="取消" class="btn-color3" onclick="javascript:location.href='<%=request.getContextPath()%>/back-end/emp/listAllEmp.jsp'">
				</FORM>

			</div>
		</div>
		<!-- /#page-content-wrapper -->
		<!-------------------- 網頁內容 結束--------------------->
	</div>
	<!-- /#wrapper -->

</body>
</html>