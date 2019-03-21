<%@page import="com.bysj.tools.CommonUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>	
<!DOCTYPE html>
<html>
<head>
	<title>${dictionary.SYS_NAME }</title>
	<link rel="stylesheet" href="${ctxdef }/css/common.css" />
	<link rel="stylesheet" href="${ctxdef }/css/sys-login.css" />
	<script type="text/javascript" src="${ctxdef }/scripts/jquery-1.11.2.js"></script>
</head>
<body class="login_body" style="background:url(${ctx}/resources/default/images/login.jpg);background-size:cover;">
	<div class="logo" id="status_of_notlogin">
		<div class="logoName">
			<div class="logoTitle">${SYS_NAME }</div>
		</div>
	</div>
	<div class="logon" style="height:250px;/* box-shadow:10px 5px 50px #16A05D; */">
		<form action="${pageContext.request.contextPath }/admin/login" method="post">
			<div>
				<label>账 号：</label> <input type="text" class="useName" id="username"
					name="username">
			</div>
			<div class="mt30">
				<label>密 码：</label> <input type="password" class="useName" id="password"
					name="password">
			</div>
			<div class="mt30">
				<label>类型：</label> 
				<select name="usertype" class="useName" style="height:35px;width:316px;">
					<c:forEach items="${dictionary.roles1 }" var="o">
						<option value="${o.key }">${o.value }</option>
					</c:forEach>
				</select>
			</div>
			<div class="logonButton">
				<input id="btn_login" type="submit" value="登&nbsp;&nbsp;&nbsp;&nbsp;录"
					class="btn btn-m btn_login btn-dang-g btn-submit" /> 
			</div>
		</form>
	</div>
<script type="text/javascript">
	$(function() {
	   var error = '${error}';
	   if(error=='on'){
		   alert('登录账号和密码错误！');
	   }
	});
</script>

</body>
</html>