<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<table class="table mt10" id="datatable">
	<thead>
		<tr>
			<th class="tdnum">序号</th>
			<th>视频名称</th>
			<th>视频标题</th>
			<th width="60px;">大小(MB)</th>
			<th width="80px;">上传时间</th>
			<th width="60px;">上传者</th>
			<th>缩略图</th>
			<th width="16%;">描述</th>
			<th width="100px;">操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${list }" var="o">
			<c:set var="number" value="${number+1 }"></c:set>
			<tr>
				<td data-type="num">${number }</td>
				<td>${o.originalname }</td>
				<td>${o.title }</td>
				<td>
				<fmt:formatNumber type="number" value="${o.number/1024/1024 }" pattern="0.00" maxFractionDigits="2"/>
				</td>
				<td>${o.createdate }</td>
				<td>${o.username }</td>
				<td style="text-align: center;">
					<img alt="" src="${ctx }/getImage?filename=${o.imagepath}" height="50">
				</td>
				<td title="${o.intro }">${fn:substring(o.intro,0,25) }...</td>
				
				<td class="tc">
					<a onclick="essm.doDel(this,'${ctx}/admin/product/delete?id=${o.id }')" href="javascript:void(0);">删除</a>&#12288;
					<a onclick="essm.doEdit('${ctx}/admin/product/toadd?id=${o.id }')" href="javascript:void(0);">完善信息</a>&#12288;
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<jsp:include page="/WEB-INF/views/admin/page.jsp" flush="true"></jsp:include>