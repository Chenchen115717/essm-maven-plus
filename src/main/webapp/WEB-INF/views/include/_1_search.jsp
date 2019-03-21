<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!-- LOGO&Search -->
<div class="zw-home-header">
    <div class="zw-home-header-wrap">
        <div class="zw-home-header-logo" style="padding-top: 35px;">
            <a data-bn-ipg="zw-logo" href="${ctx }">
                <span class="title">
                    ${SYS_NAME }
                </span>
            </a>
        </div>
        <%-- <div class="zw-home-header-logo" style="padding: 10px;">
            <a data-bn-ipg="zw-logo" href="${ctx }">
                <span class="title">
                     <img alt="" src="${ctx}/resources/default/images/logo2.png" style="height: 100px;">
                </span>
            </a>
        </div> --%>
        <div class="zw-home-header-search" id="zwhomeSearchs">
            <form action="${ctx }/client/product/index" method="post">
                <input type="text" id="titlexxx" name="titlexxx" placeholder="输入搜索词" autocomplete="off" class="zw-home-header-search-text" id="zwhomeSearchText">
                <p class="zw-home-header-search-gap"></p>
                <input type="submit"  value="搜索" class="zw-home-header-search-submit" data-bn-ipg="zsj-search">
            </form>
        </div>
    </div>
</div>
<!-- <script>
	function doSearch(){
		var title = escape  ($("#title").val());
		window.location.href="${ctx }/client/product/index?title="+title;
	}
</script>
 -->