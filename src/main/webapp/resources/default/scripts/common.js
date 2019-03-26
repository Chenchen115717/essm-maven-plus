
var ESSM = function(){
    var self = this;
    self.test = function(){
        alert('test');
    },
        self.loadData = function(url, data) {
            var datas = $.extend(data,{});
            $.ajax({
                url : url,
                type : "POST",
                data : datas,
                success : function(data) {
                    $('#sz_right').html(data);
                },
                error : function(xhr, errmsg, errobject) {
                    alert(errmsg);
                }
            });
        },
        self.load = function(url) {
            $('#accordion a').removeClass('active');
            $(this).addClass('active');
            self.loadData(url);
        },
        self.doSubmit = function(form,redirectUrl,before,callback){
            if(before){
                return before();
            }

            $(form).ajaxSubmit({
                success : function(data) {
                    alert(data.message);
                    if (data.code == 200) {
                        document.forms[0].reset();
                        if(self.isNotEmpty(redirectUrl)){
                            self.loadData(redirectUrl);
                        }
                        if(callback){
                            callback();
                        }
                    }
                }
            });
            return false;
        },
        self.doDel = function(obj,url){
            if (!confirm('确定删除吗?'))
                return false;
            $.post(url, function(data) {
                alert(data.message);
                if (data.code == 200) {
                    // 移除该行
                    $(obj).parents('tr').fadeOut();
                    var thatTotal = $("#table_box").find('.total-font');
                    var total = thatTotal.html();
                    thatTotal.html(parseInt(total - 1));
                }
            });
        },
        self.doEdit = function(url){
            self.loadData(url);
        },
        self.doCus = function(obj,url,title,redirectUrl){
            //自定义
            var mes = '确定要执行该操作吗？'
            if(title){
                var mes = title;
            }
            if (!confirm(mes))
                return false;
            $.post(url, function(data) {
                alert(data.message);
                if (data.code == 200) {
                    if(self.isNotEmpty(redirectUrl)){
                        self.loadData(redirectUrl);
                    }
                }
            });

        },self.isNotEmpty = function(data){
        return data != "" && data != null && data != undefined &&  data != "undefined" ;
    }
};

// *******************************//
$(function() {
    var Accordion = function(el, multiple) {
        this.el = el || {};
        this.multiple = multiple || false;

        // Variables privadas
        var links = this.el.find('.link');
        // Evento
        links.on('click', {
            el : this.el,
            multiple : this.multiple
        }, this.dropdown)
    }

    Accordion.prototype.dropdown = function(e) {
        var $el = e.data.el;
        $this = $(this), $next = $this.next();

        $next.slideToggle();
        $this.parent().toggleClass('open');

        if (!e.data.multiple) {
            $el.find('.submenu').not($next).slideUp().parent().removeClass(
                'open');
        }
        ;
    }

    var accordion = new Accordion($('#accordion'), false);
});