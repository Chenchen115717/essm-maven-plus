<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!-- Login&register -->
<div class="q-layer-header">
    <div class="header-inner">
        <div class="q-header-fun">
            <div id="js_qyer_header_userStatus" class="q-header-user-status">
                <div class="login-wrap">
                    <c:if test="${ empty c_user}">
					<a href="${ctx }/admin/login">登录</a>
					<a href="${ctx }/client/toregister">注册</a>
			  	</c:if>
                </div>
            </div>
        </div>
    </div>
</div>

