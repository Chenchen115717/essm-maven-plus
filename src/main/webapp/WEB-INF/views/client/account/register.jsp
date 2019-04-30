<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>${dictionary.SYS_NAME }</title>
	<%@ include file="/WEB-INF/views/include/resources.jsp"%>
	<style type="text/css">
		body{  
			color:#fff;
		    background:url(${ctx}/resources/default/images/web_login_bg.jpg)no-repeat;  
		    background-size:cover;  
		    filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='${ctx}/resources/default/images/web_login_bg.jpg',sizingMethod='scale');  
		}
		.login_group{color:#333;margin-bottom: 12px;overflow: hidden;}
		.title{text-align:right; float: left;width:28%;margin-top: 10px;}
		.control{float: left;width:70%;}
		.form-control{display:inline-block;width:80%;}
	</style>
</head>
<body>
<!-- login -->
	<div class="login">
		<div class="container">
			<h3 class="animated wow zoomIn" data-wow-delay=".5s">用户注册</h3>
			<div class="login-form-grids animated wow slideInUp" data-wow-delay=".5s">
				<form method="post" action="${ctx }/client/register" onSubmit="javascript: doSubmit(this);return false;" enctype="multipart/form-data">
					<div class="login_group">
						<div class="title">账号：</div>
						<div class="control"><input type="text" name="username" placeholder="请输入账号" required="required" ></div>
					</div>
					<div class="login_group">
						<div class="title">密码：</div>
						<div class="control"><input type="password" name="password" placeholder="请输入密码" required="required" ></div>
					</div>
					<div class="login_group">
						<div class="title">确认密码：</div>
						<div class="control"><input type="password" name="repassword" placeholder="请再次输入密码" required="required" ></div>
					</div>
					<div class="login_group">
						<div class="title">姓名：</div>
						<div class="control"><input type="text" name="name" placeholder="请输入姓名" required="required" ></div>
					</div>
					<div class="login_group">
						<div class="title">昵称：</div>
						<div class="control"><input type="text" name="nickname" placeholder="请输入昵称"  ></div>
					</div>
					<div class="login_group">
						<div class="title">邮箱：</div>
						<div class="control"><input type="text" name="email" placeholder="请输入邮箱" required="required" ></div>
					</div>
					<div class="login_group">
						<div class="title">QQ：</div>
						<div class="control"><input type="text" name="qq" placeholder="请输入QQ"  ></div>
					</div>
					<div class="login_group">
						<div class="title">微信：</div>
						<div class="control"><input type="text" name="weixin" placeholder="请输入微信"  ></div>
					</div>
					<div class="login_group">
						<div class="title">IDCARD：</div>
						<div class="control"><input type="text" name="idcard" placeholder="请输入IDCARD"  ></div>
					</div>
					<div class="login_group">
						<div class="title">手机号：</div>
						<div class="control"><input type="text" pattern="^1[0-9]{10}$" name="phone" placeholder="请输入手机号" required="required" maxlength="11"></div>
					</div>
					<div class="login_group">
						<div class="title">性别：</div>
						<div class="control" style="padding:10px;">
							<input type="radio" name="sex" value="男"/>男&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="sex" value="女">女
						</div>
					</div>
					<div class="login_group">
						<div class="title">头像：</div>
						<div class="control">
							<input type="file" name="file">
						</div>
					</div>
					<div class="login_group">
						<div class="title">类型：</div>
						<div class="control">
							<select name="usertype" style="padding: 10px 10px 10px 10px;width:100%;">
								<c:forEach items="${dictionary.roles2 }" var="o">
									<option value="${o.key }">${o.value }</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div style="width:150px;margin: 0 auto;">
						<input type="submit" value="注册">
					</div>
				</form>
			</div>
			<h4 class="animated wow slideInUp" data-wow-delay=".5s">For New People</h4>
			<p class="animated wow slideInUp" data-wow-delay=".5s">
			<a href="${ctx }/admin/login">登录</a> OR <a href="${ctx }/">返回首页<span class="glyphicon glyphicon-menu-right" aria-hidden="true"></span></a></p>
		</div>
	</div>
<!-- //login -->
<script type="text/javascript">
	function doSubmit(form){
		if($("[name=password]").val()!=$("[name=repassword]").val()){
			alert("两次密码不一样！");
			return false;
		}
		$(form).ajaxSubmit({
			success : function(data) {
				alert(data.message);
				if (data.code == 200) {
					window.location.href="${ctx}/";
				}
			}
		});
	}
</script>
</body>
</html>