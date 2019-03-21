<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>${dictionary.SYS_NAME }</title>
<%@ include file="/WEB-INF/views/include/resources.jsp"%>
<script type="text/javascript" src="${ctx}/resources/ckplayer/ckplayer.js" charset="utf-8"></script>
<style type="text/css">
	.title{font-size:20px;font-weight: bold;}
	.price {font-size:14px;color: #959595;}
	.price em{font-size:20px;color: #636363;}
	.button_2 {
	    display: inline-block;
	    width: 160px;
	    height: 60px;
	    line-height: 60px;
	    border: none;
	    border-radius: 0;
	    text-decoration: none;
	    font-size: 24px;
	    color: #fff;
	    cursor: pointer;
	    text-align: center;
	    background: #ff7466;
    }
    .button_2:hover {
    	background: #e56b5f;
	}
	.btn-primary {
	    display: inline-block;
	    width: 260px;
	    height: 50px;
	    line-height: 50px;
	    border: none;
	    border-radius: 0;
	    text-decoration: none;
	    font-size: 20px;
	    color: #fff;
	    cursor: pointer;
	    text-align: center;
	    background: #ff7466;
    }
    .btn-primary:hover {
    	background: #e56b5f;
	}
	.pagewapper{border:none;border:0px;}
</style>
</head>
<body>
	<%@ include file="/WEB-INF/views/include/_0_topinfo.jsp"%>
	<%@ include file="/WEB-INF/views/include/_1_search.jsp"%>
	<div class="container" style="margin: 45px auto 40px;width: 1160px;">
		<div id="video" style="width: 600px;float: left;">
			<img alt="" src="${ctx }/getImage?filename=${bean.imagepath}" width="90%" >
		</div>
		<div style="width: 480px;float: right;">
			<div class="title">${bean.title }（${bean.originalname }）</div>
			<div class="_22WKbL_29ugsF5e3muRLuG_0" style="margin-top: 20px;">
				<span class="comment">上传者</span>
				<span class="comment2">${bean.username }</span>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<span class="price">
				<em>
					<fmt:formatNumber type="number" value="${bean.number/1024/1024 }" pattern="0.00" maxFractionDigits="2"/>
				</em> MB</span>
			</div>
			<div class="_22WKbL_29ugsF5e3muRLuG_0" style="margin-top: 20px;">
				<span class="comment">上传时间</span>
				<span class="comment2">${bean.createdate }</span>
			</div>
			<br/>
			<br/>
			<div class="title">简介描述：</div>
			<br/>
			<div class="_22WKbL_29ugsF5e3muRLuG_0">
				<div class="tab-pane fade active in" id="tab-1" style="text-indent:25px;">
					${bean.intro } 
				</div>
			</div>
		</div>
		<div class="clearfix"></div>
	</div>
	<%@ include file="/WEB-INF/views/include/_4_footer.jsp"%>
	
	<script type="text/javascript">
        var flashvars = {
			f:'${ctx }/getImage?filename=${bean.videopath}',
            c: 0,
            b: 1
        };
        var params = { bgcolor: '#FFF', allowFullScreen: true, allowScriptAccess: 'always', wmode: 'transparent' };
        CKobject.embedSWF('${ctx }/resources/ckplayer/ckplayer.swf', 'video', 'ckplayer_a1', '600', '400', flashvars, params);
        /*
          CKobject.embedSWF(播放器路径,容器id,播放器id/name,播放器宽,播放器高,flashvars的值,其它定义也可省略);
                     下面三行是调用html5播放器用到的
        */
        var video = [flashvars.f + '->video/mp4'];
        var support = ['iPad', 'iPhone', 'ios', 'android+false', 'msie10+false'];
        CKobject.embedHTML5('a1', 'ckplayer_a1', 600, 400, video, flashvars, support);
        
        function flashRightClick(){}
        
    </script>
</body>
</html>