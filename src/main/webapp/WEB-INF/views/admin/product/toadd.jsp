<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script type="text/javascript">
	window.UEDITOR_HOME_URL = "${ctx }/resources/ueditor/";
</script>
<form:form action="${ ctx }/admin/product/save" method="post" cssClass="form form-h form-h-10em" enctype="multipart/form-data"
	modelAttribute="bean" id="form1" onsubmit="essm.doSubmit('#form1','${ctx}/admin/product/index');return false;">
	<form:hidden path="id"/>
	<div class="dataBox">
		
		<div class="f-gp">
			<div class="f-lb">
				<label class="f-label">视频名称：</label>
			</div>
			<div class="f-ct">
				<form:input  path="originalname" type="text" required="true" class="f-text span4"/>
			</div>
		</div>
		
		<div class="f-gp">
			<div class="f-lb">
				<label class="f-label">视频标题：</label>
			</div>
			<div class="f-ct">
				<form:input  path="title" type="text" required="true" class="f-text span4"/>
			</div>
		</div>
		<div class="f-gp">
			<div class="f-lb">
				<label class="f-label">自定义缩略图：</label>
			</div>
			<div class="f-ct">
				<input id="file" name="file" type="file" />
				<span>（注：此缩略图系统会自动生成，如需自定义则上传）</span>
			</div>
		</div>
		
		<div class="f-gp">
			<div class="f-lb">
				<label class="f-label">描述：</label>
			</div>
			<div class="f-ct">
				<form:textarea  path="intro" type="text" class="f-textarea span6" cssStyle="height:100px;"/>
			</div>
		</div>
	</div>

	<div class="f-ac f-infer">
		<input type="submit" class="btn btn-m btn-prim-g btn-submit" value="提交">
	</div>
</form:form>
