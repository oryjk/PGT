/**
 * Created by supersoup on 16/1/5.
 */
var tender = function(){
    //当铺编号
    this.name = "";
    this.merchant = "";
    this.title = "";
    this.serialNumber = "";
    this.keyWord = "";
    this.shortDescription = "";
    this.isNew = "";
    this.stock = "";
    this.imageDesc =　"";
    this.listPrice = "";
    this.salePrice = "";
}


//modelandview
var app = new Vue({
    el: '#app',
    data: {
        error : '',
        tender: tender
    },
    ready:function(){
        //定义错误实体
        var validation = new tender();
        this.$data.error = validation;
        //绑定显示错误信息的对象
        this.volidateEntity = validation;
        //绑定正则
        var regex = {
            "tender.name" : regex_empty,
            "tender.merchant" : regex_empty,
            "tender.title" : regex_empty,
            "tender.serialNumber" : regex_empty,
            "tender.keyWord" : regex_empty,
            "tender.shortDescription" : regex_empty,
            "tender.isNew" : regex_empty,
            "tender.stock" : regex_empty,
            "tender.imageDesc" : regex_empty,
            "tender.listPrice" : regex_empty,
            "tender.salePrice" : regex_empty
        };
        this.regexEntity = regex;
    },
    methods: {
        ajaxSubmit: function () {
            //第一个参数是对象（值），第二个参数是方法
            app.submitVolidata(this.$data.tender, tender);
        },
        volidate: function (event) {
            app.excuteVolidata(event);
        }
    }
});



$('#newStep').click(function(event) {
    event.preventDefault();

    var flag = true;
    var rxp = /^\d+\.\d{2}$/g;
    var rxp2 = /^\d+\.\d{2}$/g;

    $.each($('.pgt-requisite-name'), function(i, data) {
        var $this = $(data);
        var text =  $this.parents('.form-group').find('.pgt-error');
        if ($this.val() == '') {
            flag = false;
            text.html('必填项');
        } else {
            text.html('');
        }
    });
    $.each($('.pgt-requisite-price1'), function(i, data) {
        var $this = $(data);

        var text = $this.parents('.form-group').find('.pgt-error');
        if (!rxp.test($this.val())) {
            flag = false;

            text.html('格式不正确');
        } else {
            text.html('');
        }
    });

    $.each($('.pgt-requisite-price2'), function(i, data) {
        var $this = $(data);

        var text = $this.parents('.form-group').find('.pgt-error');
        if (!rxp2.test($this.val())) {
            flag = false;

            text.html('格式不正确');
        } else {
            text.html('');
        }
    });
    if (flag == true) {
        $('#productBase').get(0).submit();
    }
});

$('.pgt-requisite-name, .pgt-requisite-price1, .pgt-requisite-price2').keypress(function() {
    var $this = $(this);
    if ($this.val() != '') {
        $this.parents('.form-group').find('.pgt-error').html('');
    }
});