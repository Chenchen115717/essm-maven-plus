<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<table class="table mt10" id="datatable">
	<thead>
		<tr>
			<th class="tdnum">序号</th>
			<th>账号</th>
			<th>密码</th>
			<th>姓名</th>
			<th>昵称</th>
			<th>角色</th>
			<th>性别</th>
			<th>邮箱</th>
			<th>手机号</th>
			<th>QQ号</th>
			<th>微信号</th>
			<th>身份证号</th>
			<th>头像</th>
			<th style="width:30px;">操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${list }" var="o">
			<c:set var="number" value="${number+1 }"></c:set>
			<tr>
				<td data-type="num">${number }</td>
				<td>${o.username }</td>
				<td>${o.password }</td>
				<td>${o.name }</td>
				<td>${o.nickname }</td>
				<td>${o.usertype }</td>
				<td>${o.sex }</td>
				<td>${o.email }</td>
				<td>${o.phone }</td>
				<td>${o.qq }</td>
				<td>${o.weixin }</td>
				<td>${o.idcard }</td>
				<td style="text-align: center;">
					<img alt="" src="${ctx }/getImage?filename=${o.imagepath}" height="50">
				</td>
				<td class="tc">
					<a onclick="essm.doDel(this,'${ctx}/admin/user/delete?id=${o.id }')" href="javascript:void(0);">删除</a>
					<a onclick="essm.doEdit('${ctx}/admin/user/toadd?id=${o.id }')" href="javascript:void(0);">编辑</a>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<jsp:include page="/WEB-INF/views/admin/page.jsp" flush="true"></jsp:include>