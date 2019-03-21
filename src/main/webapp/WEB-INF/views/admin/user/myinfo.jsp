<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<form:form action="${ ctx }/admin/user/savemyinfo" method="post" enctype="multipart/form-data"
	cssClass="form form-h form-h-10em" modelAttribute="bean" id="form1" onsubmit="doSubmit2();return false;">
	<div class="dataBox">
		<div class="f-gp">
			<div class="f-lb">
				<label class="f-label">账号：</label>
			</div>
			<div class="f-ct">
				<form:input  path="username" type="text" required="required" class="f-text span4"/>
			</div>
		</div>
		
		<div class="f-gp">
			<div class="f-lb">
				<label class="f-label">密码：</label>
			</div>
			<div class="f-ct">
				<form:input  path="password" type="password" required="required" class="f-text span4"/>
			</div>
		</div>
		
		<div class="f-gp">
			<div class="f-lb">
				<label class="f-label">姓名：</label>
			</div>
			<div class="f-ct">
				<form:input  path="name" type="text" required="required" class="f-text span4"/>
			</div>
		</div>
		
		<div class="f-gp">
			<div class="f-lb">
				<label class="f-label">昵称：</label>
			</div>
			<div class="f-ct">
				<form:input  path="nickname" type="text" class="f-text span4"/>
			</div>
		</div>
		
		<div class="f-gp">
			<div class="f-lb">
				<label class="f-label">角色：</label>
			</div>
			<div class="f-ct">
				<form:select path="usertype" items="${dictionary.roles1 }" cssClass="f-select block span4"/>
			</div>
		</div>
		
		<div class="f-gp">
			<div class="f-lb">
				<label class="f-label">性别：</label>
			</div>
			<div class="f-ct">
				<form:radiobuttons path="sex" items="${fn:split('男;女', ';') }" cssClass="f-radio" />
			</div>
		</div>
		
		<div class="f-gp">
			<div class="f-lb">
				<label class="f-label">生日：</label>
			</div>
			<div class="f-ct">
				<form:input  path="birthday" type="date" required="true" class="f-text span4"/>
			</div>
		</div>
		
		<div class="f-gp">
			<div class="f-lb">
				<label class="f-label">邮箱：</label>
			</div>
			<div class="f-ct">
				<form:input  path="email" type="email" required="required" class="f-text span4"/>
			</div>
		</div>
		
		<div class="f-gp">
			<div class="f-lb">
				<label class="f-label">手机号：</label>
			</div>
			<div class="f-ct">
				<form:input  path="phone" type="number" required="required" class="f-text span4"/>
			</div>
		</div>
		
		<div class="f-gp">
			<div class="f-lb">
				<label class="f-label">QQ号：</label>
			</div>
			<div class="f-ct">
				<form:input  path="qq" type="number" required="true" class="f-text span4"/>
			</div>
		</div>
		
		<div class="f-gp">
			<div class="f-lb">
				<label class="f-label">微信号：</label>
			</div>
			<div class="f-ct">
				<form:input  path="weixin" type="text" required="true" class="f-text span4"/>
			</div>
		</div>
		
		<div class="f-gp">
			<div class="f-lb">
				<label class="f-label">身份证号：</label>
			</div>
			<div class="f-ct">
				<form:input  path="idcard" type="text" pattern="[0-9]{18}" class="f-text span4"/>
			</div>
		</div>
		
		<div class="f-gp">
			<div class="f-lb">
				<label class="f-label">头像：</label>
			</div>
			<div class="f-ct">
				<input id="file" name="file" type="file" />
			</div>
		</div>
		
	</div>
	<div class="f-ac f-infer">
		<input type="submit" class="btn btn-m btn-prim-g btn-submit" value="提交">
	</div>
</form:form>
<script>
	function doSubmit2(){
		essm.doSubmit('#form1');return false;
	}
</script>