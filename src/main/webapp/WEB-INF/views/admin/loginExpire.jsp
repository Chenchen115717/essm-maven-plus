<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">
	alert('登录已过期，请重新登录！');
	setTimeout(function(){
		window.location.href='${pageContext.request.contextPath}/admin/login';
	},0);
</script>