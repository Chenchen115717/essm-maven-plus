(function ($) {
    this.UploadMsg = "正在上传中,请稍候...";
    this.LoadingMsg = "正在加载中,请稍候...";
    this.ErrorMsg = "您的请求发生了错误...";
    this.JsErrorMsg = "脚本执行发生错误";

    this.myShow = function (msg) {
        alert(msg);
    }
    $.Messager = this;
    
    var formData = "input[type='text'],input[type='hidden'],input[type='checkbox'],select,input[type='radio']:checked";//要查找的form数据

    ///	<summary>
    ///	获取form的数据
    ///	</summary>
    ///	<returns type="Object" />
    jQuery.fn.getFormData = function () {
        var data = {};
        $(this).find(formData).each(function () {
            var name = $(this).attr("name");
            if (name != null && name != undefined && name != '') {
                if (data.hasOwnProperty(name)) {
                    var value = data[name];
                    if (value != null && value != '') {
                        data[name] += ',' + $(this).val();
                    } else {
                        data[name] = $(this).val();
                    }
                } else {
                    data[name] = $(this).val();
                }
            }
        })
        return data;

    };

    ///	<summary>
    /// ajax返回数据
    ///	</summary>
    function BaseAjax(options) {
        var settings = $.extend({
            ajaxurl: '',
            formid: 'searchForm', //检索form
            data: {},
            isLogin: true, //是否需要登录
            dataTableId: 'datatable',  //表格ID
            fillType: 'replace',       //数据填充方式after,replace
            fillArea: 'table_box',     //数据填充区域ID
            succCallback: undefined,   //成功后回调函数
            beforeCallback: undefined  //执行前回调
        }, options, {});

        if (typeof settings.beforeCallback == "function") {

            var result = settings.beforeCallback();
            if (result == false)
            {
                return false;
            }
        }

        $.ajax({
            type: "POST",
            url: settings.ajaxurl,
            data: $.extend($("#" + settings.formid).getFormData(), settings.data, {}),
            success: function (msg) {
                if (settings.fillType == "after") {
                    $("#" + settings.fillArea).nextAll().remove();
                    $("#" + settings.fillArea).after(msg);
                }
                else if (settings.fillType == "replace") {
                	//alert(settings.fillArea);
                	//checkLogin(msg);
                    $("#" + settings.fillArea).html(msg);
                }
                if (typeof settings.succCallback == "function") {
                    settings.succCallback(msg);
                }
            },
            error: function (msg) {
                if (settings.fillType == "after") {
                    $("#" + settings.fillArea).nextAll().remove();
                    $("#" + settings.fillArea).after(msg.responseText);
                }
                else if (settings.fillType == "replace") {
                    $("#" + settings.fillArea).html(msg.responseText);
                }
            }
        });
        return false;
    }

    ///	<summary>
    /// 1.初始化加载数据
    ///	</summary>
    jQuery.fn.loadPage = function (options, callback) {

        var tableid = options.dataTableId; //表格ID

        var settings = $.extend({
            //回调:添加默认的排序图标:th具有此属性的添加默认排序图标data-order-by
            succCallback: function () {

                var orderby = $('#' + tableid + ' th[data-order-col][data-order-by]').attr('data-order-by'); //排序方式
                if (orderby == 'desc') {
                    $('#' + tableid + ' th[data-order-by=desc]').append('<img alt="" src="/Content/imges/desc.gif"  style="float:left;  margin-top:3px;">').css({ 'cursor': 'pointer' });//&#xf000b;下箭头
                    //$('#' + tableid + ' th[data-order-col][data-order-by=' + orderby + ']').append('<i class="iconfont">&#xf000b;</i>').css({ 'cursor': 'pointer' });
                }
                else if (orderby == 'asc') {
                    $('#' + tableid + ' th[data-order-by=asc]').append('<img alt="" src="/Content/imges/asc.gif"  style="float:left;  margin-top:3px;">').css({ 'cursor': 'pointer' });//&#xf000b;下箭头
                    //$('#' + tableid + ' th[data-order-col][data-order-by=' + orderby + ']').append('<i class="iconfont">&#xf000d;</i>').css({ 'cursor': 'pointer' });
                }
            }
        }, options, {});


        try {

            //附加的参数
            if (typeof callback == 'function') {
                var cb = callback();
                if (typeof cb == 'boolean') {
                    if (cb == false)
                        return false;
                }
                if (cb.hasOwnProperty('data'))
                    settings.data = $.extend(options.data, cb.data);
                if (cb.hasOwnProperty('ajaxurl'))
                    settings.ajaxurl = cb.ajaxurl;
            }

            //去掉分页
            if (settings.hasOwnProperty('data')) {
                if (settings.data.hasOwnProperty('pageIndex')) {
                    settings.data.pageIndex = 0;
                    delete settings.data.pageIndex;
                }
            }
        }
        catch (error) {

        }

        BaseAjax(settings);

        return jQuery;
    }

    ///	<summary>
    /// 3.检索按钮
    ///	</summary>
    jQuery.fn.formSearch = function (options, callback) {

        $(this).off("click");

        $(this).on('click', function () {

            var tableid = options.dataTableId; //表格ID

            var settings = $.extend({
                //回调:添加默认的排序图标:th具有此属性的添加默认排序图标data-order-by
                succCallback: function () {
                    var orderby = $('#' + tableid + ' th[data-order-col][data-order-by]').attr('data-order-by'); //排序方式
                    if (orderby == 'desc') {
                        $('#' + tableid + ' th[data-order-by=desc]').append('<img alt="" src="/Content/imges/desc.gif"  style="float:left;  margin-top:3px;">').css({ 'cursor': 'pointer' });//&#xf000b;下箭头
                        //$('#' + tableid + ' th[data-order-col][data-order-by=' + orderby + ']').append('<i class="iconfont">&#xf000b;</i>').css({ 'cursor': 'pointer' });
                    }
                    else if (orderby == 'asc') {
                        $('#' + tableid + ' th[data-order-by=desc]').append('<img alt="" src="/Content/imges/asc.gif"  style="float:left;  margin-top:3px;">').css({ 'cursor': 'pointer' });//&#xf000b;上箭头
                        //$('#' + tableid + ' th[data-order-col][data-order-by=' + orderby + ']').append('<i class="iconfont">&#xf000d;</i>').css({ 'cursor': 'pointer' });
                    }
                }
            }, options, {});


            try {
                //附加的参数
                if (typeof callback == 'function') {
                    var cb = callback();
                    if (typeof cb == 'boolean') {
                        if (cb == false)
                            return false;
                    }
                    if (cb.hasOwnProperty('data'))
                        settings.data = $.extend(options.data, cb.data);
                    if (cb.hasOwnProperty('ajaxurl'))
                        settings.ajaxurl = cb.ajaxurl;
                }
                //去掉分页
                if (settings.hasOwnProperty('data')) {
                    if (settings.data.hasOwnProperty('pageIndex')) {
                        settings.data.pageIndex = 0;
                        delete settings.data.pageIndex;
                    }
                }
            }
            catch (error) {

            }

            BaseAjax(settings);
        });
        return jQuery;
    }

    ///	<summary>
    /// 4.表格分页
    ///	</summary>
    jQuery.fn.tablePage = function (options, callback) {

        $(this).off("click", "a.pageclick");
        $(this).on('click', 'a.pageclick', function () {

            var tableid = options.dataTableId; //表格ID

            //获取当前排序
            var order = $("#" + tableid).find("th[data-order-col][data-order-by]");
            var ordercol = order.attr('data-order-col'); //排序字段
            var orderby = order.attr('data-order-by'); //排序方式
           
            //获取当前页码
            var pageIndex = $(this).attr("data-page");

            //参数
            var paradata = {};
            if (ordercol != undefined && ordercol != null && ordercol != '') {
                paradata.orderfunction = orderby;
                paradata.orderfield = ordercol;
            }
            paradata.pageIndex = pageIndex;
           
            var settings = $.extend({
                data: paradata,
                succCallback: function () {//保持当前排序
                    if (orderby == 'desc') {
                      
                        $('#' + tableid + ' th[data-order-by=desc]').append('<img alt="" src="/Content/imges/desc.gif"  style="float:left;  margin-top:3px;">').css({ 'cursor': 'pointer' });//&#xf000b;下箭头
                        //$('#' + tableid + ' th[data-order-col=' + ordercol + ']').append('<i class="iconfont">&#xf000b;</i>').css({ 'cursor': 'pointer' });//&#xf000b;下箭头
                        $('#' + tableid + ' th[data-order-col=' + ordercol + ']').attr('data-order-by', orderby)
                    }
                    else if (orderby == 'asc') {
                        $('#' + tableid + ' th[data-order-by=desc]').append('<img alt="" src="/Content/imges/asc.gif"  style="float:left;  margin-top:3px;">').css({ 'cursor': 'pointer' });//&#xf000b;上箭头
                        //$('#' + tableid + ' th[data-order-col=' + ordercol + ']').append('<i class="iconfont">&#xf000d;</i>').css({ 'cursor': 'pointer' }); //&#xf000d;上箭头
                        $('#' + tableid + ' th[data-order-col=' + ordercol + ']').attr('data-order-by', orderby)
                    }
                }
            }, options);

            try {
                if (typeof callback == 'function') {
                    var cb = callback();
                    if (cb.hasOwnProperty('data'))
                        settings.data = $.extend(options.data, cb.data);
                    if (cb.hasOwnProperty('ajaxurl'))
                        settings.ajaxurl = cb.ajaxurl;
                }
            }
            catch (err)
            { }

            //合并参数
            if (typeof options.data != 'undefined') {
                settings.data = $.extend(options.data, paradata);
            }

            BaseAjax(settings);

            return false;
        });
    }
})(jQuery);

function checkLogin(data){
	if(data.indexOf('status_of_notlogin')>-1){
		setTimeout(function(){
			window.location.href='/ssm/admin/login';
		},0);
	};
};

//ajax状态显示
$(document).ready(function () {

    var css = { "display": "none","margin-left": "-100px", "left": "50%", "top": "0px", "float": "none", "z-index": "999999999", "position": "fixed","width":"200px","background":"rgb(254, 207, 0)","padding-left":"30px" };
    var loading = $("<div id=\"loading\"></div>").addClass("loading").css(css).html("<div style='color: rgb(102, 102, 102); font-weight: bold;background:rgb(254, 207, 0) url("+window.contextPath+"/resources/default/images/load.gif) no-repeat no-repeat scroll 6% center / 28px auto; text-align: center; padding: 6px 0px;'><span style=\"padding-left:  20px;\">数据加载中...</span><div>");
    $(loading).appendTo(window.document.body);

    $(document).ajaxStart( function () {
        $('#loading').slideDown(200);
    })
    $(document).ajaxStop( function () {
        $('#loading').slideUp(800);
    })

    window.onerror = function (e) {
        $(loading).hide();
        //alert("测试阶段提示：\n\n" + $.Messager.JsErrorMsg + ":" + e); 
        window.onerror = null;
        return true;
    }
});
