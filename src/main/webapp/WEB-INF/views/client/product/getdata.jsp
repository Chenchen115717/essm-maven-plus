<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<c:if test="${empty list }">
	<h1 style="text-align: center;">暂无数据！</h1>
</c:if>
<h3 class="animated wow zoomIn" data-wow-delay=".5s">视频列表</h3>
<div class="new-collections-grids">
	<c:forEach var="o" items="${list }" varStatus="s">
	<div class="col-md-3 new-collections-grid">
		<div class="new-collections-grid1 animated wow slideInUp" data-wow-delay=".5s">
			<div class="new-collections-grid1-image">
				<a href="${ctx }/client/product/getone?id=${o.id}" class="product-image">
					<img alt="" src="${ctx }/getImage?filename=${o.imagepath}" width="240" height="160" >
				</a>
			</div>
			<h4><a href="${ctx }/client/product/getone?id=${o.id}">${o.title }</a></h4>
		</div>
	</div>
	</c:forEach>
	<div class="clearfix"> </div>
</div>
<div class="clearfix"> </div>
<div class="product-top" style="text-align: center;">
	<jsp:include page="/WEB-INF/views/client/page.jsp" flush="true"></jsp:include>
</div>
