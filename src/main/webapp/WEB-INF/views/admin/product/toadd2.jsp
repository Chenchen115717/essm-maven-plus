<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<style>
  .progressbar{text-align:center; width:100%;border-bottom: 1px solid #dddddd;position: relative;}
  .progressbar2{margin-top:1px;}
  .progressing-bar{background: #14d0c0;width:50%;height: 5px;}
  .progressing-label{height: 32px;line-height: 32px;font-size: 14px;}
  .progressing-title{margin-top: 0px;background: #669FFF;font-size: 12px;line-height: 30px;color: #fff;}
  .progressing-close{position: absolute;top:10px;right: 15px;font-size: 12px;font-weight: bold;color: #000;text-decoration: none;}
</style>
<form method="post" id="form1" name="baseInfoForm" class="form form-h form-h-10em">
	<div class="dataBox">
		<div class="lbwh_right">
			<div class="title">上传视频信息</div>
			  <div class="content">
				<div class="formbox">
					<span class="fmlabel">视频</span>
					<span>
						<input type="file" multiple="multiple" id="file_video" onchange="selectMultifile(this,'video','file_video_list',-1)" accept=".mp4;.flv;.avi"/>（支持.mp4;.flv;.avi）
					</span>
			    </div>
			    <div class="formbox ml80" id="file_video_list">
			    </div>
			    <div style="clear: both;"></div>
			</div>
			
			<div style="clear: both;"></div>
			<div class="tc mt30" style="float:left;margin-left: 150px;">
				<a href="javascript:void(0)" id="btnSave" onclick="doSubmit()" class="btn btn-m btn-prim-g btn-submit">上传</a>
			</div>
		</div>
	</div>
</form>
<div id="progressWarp"  style="display:none;max-height:400px; width:600px;bottom:10px;right:10px; position: fixed;border: 1px solid #cccccc;border-radius: 5px;box-shadow: -5px 5px 5px #cccccc;">
  <div class="progressbar progressbar2" style="margin-top: 0px;line-height: 35px;height: 35px;">
    <span>上传文件列表</span>
    <a href="javascript:void(0)" onclick="closeWarp()" class="progressing-close" style="top:0px;">X</a>
  </div>
  <div id="progressBarTip" class="progressbar progressbar2 progressing-title">
    严禁上传、传播暴力恐吓、色情违法及侵犯他人合法权益的违法行为，一经发现将严格按照相关法律解决
  </div>
  
  
</div>
<script type="text/javascript">
	let max = 5;
	//选择
	var fileArray = {"video":[]};
	var cancelArray = []
	var convertArray = []
	//清空已选择文件以及表单
	function clearFiles(){
		$("#file_video_list").html("");
		$("#file_image_list").html("");
		for (var type in fileArray){
			fileArray[type].length=0;
		}
		$(document).clearQueue('myqueue');
	}
	
	//obj:input file 表单
    //type:video类型
    //warpId:选择之后显示在哪里
    //max:最多可以上传几个文件
	function selectMultifile(obj,type,warpId,max){
    	let files = obj.files;
    	for (let k = 0, len = files.length; k < len; k++) {
    		let file = files[k];
            var filename = file.name.replace(/.*(\/|\\)/, "");
            console.log(filename);
    		var fileExt = (/[.]/.exec(filename)) ? /[^.]+$/.exec(filename.toLowerCase()) : "";
            var accept = $(obj).attr('accept');
            if (accept != "" && accept.indexOf(fileExt) == -1) {
                alert('仅支持' + accept + '格式的文件！');
                $(obj).val("");
                return false;
            }
            for (var i = 0; i < fileArray[type].length; i++) {
                if (fileArray[type][i].filename == filename) {
                    alert('该文件已经添加，不能重复添加');
                    return false;
                }
            }
            if(max > 0 &&  fileArray[type].length >= max){
				alert('只能选择'+max+"个！");
                return false;
			}
            var fileid = new Date().getTime();
            var saveFileName = new Date().getTime() + "_"+ i + "_" + Math.round(Math.random()*9999+1000) +"."+ fileExt;
            convertArray.push(saveFileName);
            var kv = { filename: filename,saveFileName:saveFileName,fileExt:fileExt, fileid: fileid, value: file };
            fileArray[type].push(kv);
            
            var html = '<span class="selected_file"><span>';
            html += filename;
            html += '</span>&nbsp;&nbsp;<a id="' + fileid + '" href="javascript:;" data-file-type = '+type+' data-type="del_selected_file" class="del">x</a>';
            html += '</span>';
            $(html).appendTo('#'+warpId);
        }
    	$(obj).val("");
	}
	
	var FUNC = [];
    var aniCB = function () {
        $(document).dequeue('myqueue');
    }
    
    let isSubmit = true;
    function doSubmit(){
    	if(!isSubmit){
    		alert('正在上传中，请勿重复操作！')
    		return false
    	}
    	isSubmit = false
		if(allFileCounter()>0){
			start();
		}
		var debateId = '1';
		for (var type in fileArray){
		   //正在上传 type
		   for (var i = 0; i < fileArray[type].length; i++) {
				//正在上传第i个	
				var label = fileArray[type][i].filename.substr(0,60);
				var index = 0; 
				//保存的文件名
				var filename = fileArray[type][i].saveFileName;
				//排队
				FUNC.push((function (file, index,debateId,type,filename,label,i) {
					return function () {
						//console.log("index=" + index + ",filename=" + filename);
						uploadFiles(file, index,debateId,type,filename,label,i,function(){
							setTimeout(aniCB, 200);
						});
					}
				})(fileArray[type][i].value, index,debateId,type,filename,label,i));
			}
		}

		$(document).queue('myqueue', FUNC);
		aniCB();
	}
	
	//获取已选择的总文件数
	function allFileCounter(){
		var counter = 0;
		for (var type in fileArray){
			for (var i = 0; i < fileArray[type].length; i++) {
				   counter ++ ;
			}
		}
		return counter;
	}
	
	function uploadFiles(file, index,debateId,type,filename,label,i,callback) {

        var name = file.name,        //文件名
            size = file.size;        //总大小

        var shardSize = 2 * 1024 * 1024,     //以4MB为一个分片
            shardCount = Math.ceil(size / shardSize);   //总片数

        if (index == shardCount){
        	callback();
        	var len = $(document).queue('myqueue').length;
            console.log(len);
        	if(len == 0){
        		alert("全部上传完成！")
        		clearFiles()
        		isSubmit=true
        		//处理取消
        		if(cancelArray.length>0){
        			$.post("${ctx}/admin/product/cancelvideo.shtml",{
            			productId:cancelArray.join(';')
            		},function(data){
            			//处理视频图片等
            			handerVideo()
            		});
        		}else{
        			handerVideo()
        		}
        	}
        	return;
        } 

        var start = index * shardSize,
            end = Math.min(size, start + shardSize);
        
        var data = file.slice(start, end);

        //构造一个表单，FormData是HTML5新增的
        var form = new FormData();
        form.append("data", data);  //slice方法用于切出文件的一部分
        form.append("name", name);  //文件的原始名
        form.append("total", shardCount);   //总片数
        form.append("index", index + 1);        //当前是第几片
        form.append("size", size);        //总大小
        form.append("debateId", debateId);
        form.append("type", type);
        form.append("filename", filename);
        data = null;

        //Ajax提交
        $.ajax({
            url: "${ctx}/admin/product/postUploadFiles.shtml",
            type: "POST",
            data: form,
            async: true,         //异步
            processData: false,  //很重要，告诉jquery不要对form进行处理
            contentType: false,  //很重要，指定为false才能形成正确的Content-Type
            cache: false,
            success: function (data) {
                index++;
                form = null;
                setProgress(label,index,shardCount,i,size);
                uploadFiles(file, index,debateId,type,filename,label,i,callback);
            }
        });
    }

	function handerVideo(){
		//截屏，转格式
		$.post("${ctx}/admin/product/videoformat.shtml",{
			
		},function(data){
			$.post("${ctx}/admin/product/convertvideo.shtml",{
				productId:convertArray.join(';')
			},function(data){
				
			});
		});
		
	}
	
	//进度条
	function setProgress(label,index, shardCount,i,size) {
		let pid = `${"#p${i}"}`
		let progressBar = $(pid).find('.progressing-bar')
		let labelDom = $(pid).find('.progressing-label')
		console.log(pid)
		//debugger
		var succeed = index + 1;

		var percent = (succeed / shardCount).toFixed(2) * 100;
		labelDom.html(`上传中&nbsp;[${"${label}"}]&nbsp;[${"${(size/1000).toFixed(0)}KB"}]&nbsp;[${"${percent.toFixed(0)}"}%]`);
		if (percent >= 100) {
			labelDom.html(`上传完成&nbsp;[${"${label}"}]&nbsp;[${"${(size/1000).toFixed(0)}KB"}]&nbsp;[100%]`);
			progressBar.attr("style", "width: " + 100 + "%;");
		}else{
			progressBar.attr("style", "width: " + percent + "%;");
		}
	}

	function start(){
		//待传列表
		let html = ''
		for(let i = 0;i<fileArray['video'].length;i++){
			let pid = 'p'+i
			html += 
				`<div id="${"${pid}"}" class="progressbar progressbar2" >
				    <div class="progressing-bar" data-index="${"${i}"}" style="width: 0%;">
				    </div>
				    <div class="progressing-label">排队中</div>
				    <a href="javascript:void(0)" class="progressing-close" onclick="deleteOne(this,'#${"${pid}"}','${"${fileArray['video'][i].filename}"}','${"${fileArray['video'][i].saveFileName}"}')">X</a>
				  </div>`
		}
		$('#progressBarTip').after(html)
		$("#progressWarp").show()
	}

	function deleteOne(obj,parentid,filename,saveFileName){
		if(!confirm('确定要删除该任务吗？')){
			return false
		}
		let label = $(parentid).find('.progressing-label').html()
		if(label == '排队中'){
			$(obj).parents('div.progressbar').remove()
			cancelArray.push(saveFileName)
			//console.log(cancelArray)
			//删除数组里的任务 //TOTO
			//{ filename: filename,fileExt:fileExt, fileid: fileid, value: file }
			for(let i = 0;i<fileArray['video'].length;i++){
				if(fileArray['video'][i].filename==filename){
					fileArray['video'].splice(i,1)
					return false
				}
			}
		}else{
			alert('不允许删除正在执行或者已经执行完的任务！')
		}
		return false
	}
	
	function closeWarp(){
		if(!confirm('确定要关闭该上传列表吗？')){
			return false
		}
		$('#progressWarp').hide();
	}
	
	$(function(){
		//删除已选择文件(封面，音视频，附件，图片)
	    $(document).on('click', '[data-type=del_selected_file]', function () {
	        var id = $(this).attr('id');
	        var type =$(this).attr("data-file-type");
	        $(this).parent('.selected_file').remove();
	        //debugger;
	        for (var i = 0; i < fileArray[type].length; i++) {
	            if (fileArray[type][i].fileid == id) {
	                fileArray[type].splice(i, 1);
	            }
	        }
	    });
	})
	
	//获取文件后缀名
	function getFileExtendName(name){
		if(name!=null && name != undefined){
			var i = name.lastIndexOf(".");
			return name.substr(i,name.length);
		}
	}

	//获取文件名
	function getFileName(name){
		if(name!=null && name != undefined){
			var i = name.lastIndexOf("\\")+1;
			return name.substr(i,name.length);
		}
	}

	//转值
	function castVal(num){
		switch(num){
		  case 1:
			 return "一";
		  case 2:
			  return "二";
		  case 3:
			  return "三";
		  case 4:
			  return "四";
		  case "video":
		  	  return "音视频";
		  case "image":
		  	  return "图片";
		  case "attach":
		  	  return "附件";
		  case "coverimage":
		  	  return "封面图片"; 
		  default:
		    return "";
		}
	}
</script>


