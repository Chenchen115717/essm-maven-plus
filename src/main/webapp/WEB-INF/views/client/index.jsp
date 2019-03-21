<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>${dictionary.SYS_NAME }</title>
<%@ include file="/WEB-INF/views/include/resources.jsp"%>
</head>
<body class="home">
	<%@ include file="/WEB-INF/views/include/_0_topinfo.jsp"%>
	<%@ include file="/WEB-INF/views/include/_1_search.jsp"%>
	<%@ include file="/WEB-INF/views/include/_3_slider.jsp"%>

	<!-- collections -->
	<div class="new-collections">
		<div class="container" id="table_box">
		</div>
	</div>
   <!-- //collections -->
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
		
		function addToCart(productid){
			$.ajax({
				url : '${ctx}/client/cart/addcart',
				type : "POST",
				dataType : "json",
				data : {
					productid : productid
				},
				success : function(data) {
					alert(data.message);
					if (data.code == 300) {
						window.location.href = '${ctx}/client/tologin';
					}
				}
			});
		}
	</script>
</body>
</html>