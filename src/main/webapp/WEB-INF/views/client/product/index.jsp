<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>${dictionary.SYS_NAME }</title>
<%@ include file="/WEB-INF/views/include/resources.jsp"%>
</head>
<body>
	<%@ include file="/WEB-INF/views/include/_0_topinfo.jsp"%>
	<%@ include file="/WEB-INF/views/include/_1_search.jsp"%>
	<div class="new-collections">
		<div class="container" id="table_box">
		</div>
	</div>
	<%@ include file="/WEB-INF/views/include/_4_footer.jsp"%>
	<script>
		
		getdata();
		
		function getdata(){
			var options = {
				ajaxurl : "${ctx}/client/product/getdata",
				data:{
					cid:"${cid}",
					titlexxx:"${titlexxx}",
					type:"${type}"
				}
			};
			$(this).loadPage(options);
			$('#table_box').tablePage(options);
			$("#btnSearch").formSearch(options);
		}
	</script>
</body>
</html>