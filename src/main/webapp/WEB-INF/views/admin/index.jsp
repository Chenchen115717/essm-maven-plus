<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>${dictionary.SYS_NAME }</title>
<%@ include file="/WEB-INF/views/include/resources_sys.jsp"%>
	<style type="text/css">
		.userinfo{height: 25px;padding:4px 15px 3px 15px;background: #5AB2CE;float: right;margin-left: 20px;border-radius:20px;}
		.userphoto{height: 25px;padding:4px 15px 3px 15px;float: right;margin-left: 20px;}
		.userphoto img{border-radius: 25px;position: absolute;left: 0px;top: -8px;}
		.menu_list{width:268px;}
		.menu_head{height:47px;line-height:47px;padding-left:38px;font-size:14px;color:#525252;cursor:pointer;border:1px solid #e1e1e1;position:relative;margin:0px;font-weight:bold;background:#f1f1f1 url(${ctx}/resources/default/images/pro_left.png) center right no-repeat;}
		.menu_list .current{background:#f1f1f1 url(${ctx}/resources/default/images/pro_down.png) center right no-repeat;}
		.menu_body{line-height:38px;border-left:1px solid #e1e1e1;backguound:#fff;border-right:1px solid #e1e1e1;}
		.menu_body a{display:block;height:38px;line-height:38px;padding-left:38px;color:#777777;background:#fff;text-decoration:none;border-bottom:1px solid #e1e1e1;}
		.menu_body a:hover{text-decoration:none;}
		#firstpane h3{padding:0px;padding-left:38px;}
		.right_content{padding:20px;margin-top: 20px;}
		.tdnum{width:40px;}
	</style>
	<script type="text/javascript">
		$(document).ready(function(){
			$("#firstpane .menu_body:eq(0)").show();
			$("#firstpane h3.menu_head").click(function(){
				$(this).addClass("current").next("div.menu_body").slideToggle(300).siblings("div.menu_body").slideUp("slow");
				$(this).siblings().removeClass("current");
			});
			
			$("#secondpane .menu_body:eq(0)").show();
			$("#secondpane h3.menu_head").mouseover(function(){
				$(this).addClass("current").next("div.menu_body").slideDown(500).siblings("div.menu_body").slideUp("slow");
				$(this).siblings().removeClass("current");
			});
		});
	</script>
</head>
<body>
	<div class="sz_top sec_top posr">
		<h1 class="logo">${dictionary.SYS_NAME}</h1>
		<div class="sz_login">
			<div class="userinfo">
				<a class="szExit" href="${ctx }/">返回首页</a>
			</div>
			<div class="userinfo">
				<a class="szExit" href="${ctx }/admin/login">退出系统</a>
			</div>
			<div class="userinfo">${user.usertype }</div>
			<div class="userinfo">${user.username }</div>
			<div class="userphoto">
				<img alt="" src="${ctx }/getImage?filename=${user.imagepath}" height="50" width="50">
			</div>
		</div>
	</div>
	
	<!-- content start -->
    <div class="sz_content clearfix">
	   
	   <div id="firstpane" class="menu_list sz_left page-sidebar">
	   <c:if test="${user.usertype =='管理员'}">
		<h3 class="menu_head current">系统管理</h3>
		<div style="" class="menu_body">
			<!--essm 是封装的一个类 方法执行为ajax请求；
			    href=”javascript:void(0);”这个的含义是，让超链接去执行一个js函数，而不是去跳转到一个地址，
               而void(0)表示一个空的方法，也就是不执行js函数。-->
			<a onclick="essm.load('${ctx}/admin/user/index')" href="javascript:void(0);">账号管理</a>
			<a onclick="essm.load('${ctx}/admin/user/toadd')" href="javascript:void(0);">添加账号</a>
		</div>
		   <h3 class="menu_head">视频管理</h3>
		   <div style="display:none" class="menu_body">
			   <a onclick="essm.load('${ctx}/admin/product/index')" href="javascript:void(0);">视频管理</a>
		   </div>
		</c:if>

		   <h3 class="menu_head">视频上传</h3>
		   <div style="display:none" class="menu_body">
			   <a onclick="essm.load('${ctx}/admin/product/toadd2')" href="javascript:void(0);">上传视频</a>
		   </div>
		

       <c:if test="${user.usertype =='普通用户'}">

	   </c:if>
		<h3 class="menu_head">个人信息</h3>
		<div style="display:none" class="menu_body">
			<a onclick="essm.load('${ctx}/admin/user/myinfo')" href="javascript:void(0);">我的信息</a>
			<a onclick="essm.load('${ctx}/admin/user/tochpwd')" href="javascript:void(0);">修改密码</a>
		</div>
	  </div>
	   
	   <div class="sz_right">
	     <div class="right_content" id="sz_right">
	    	欢迎访问
	   	 </div>
	   </div>
	</div>
	<!-- content end -->
</body>
</html>