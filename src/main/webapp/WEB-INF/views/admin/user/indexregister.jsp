<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<div class="sz_rightTop">
	<form id="searchForm">
		<label class="ml10">账号名称：</label> <input type="text" name="username"
			class="f-text span3"> 
			<%-- <label class="ml10">账号类型：</label> 
			<select class="f-text span2" name="usertype">
			<option value="">全部</option>
			<c:forEach items="${dictionary.roles2 }" var="o">
				<option value="${o.key }">${o.value }</option>
			</c:forEach>
			</select>  --%>
		<a href="javascript:void(0);" class="btn btn-m btn-warn-f" id="btnSearch">检索</a>
	</form>
</div>
<div class="sz_rightBody" id="table_box"></div>
<script>
	$(function() {
		var options = {
			ajaxurl : "${ctx}/admin/user/getdataregister"
		};
		$(this).loadPage(options); 
		$('#table_box').tablePage(options); 
		$("#btnSearch").formSearch(options);
	});
</script>
