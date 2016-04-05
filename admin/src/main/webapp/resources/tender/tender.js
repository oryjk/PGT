//表单对象模型 - 经个人实践得出- 必须给初值
var tender = function () {
    //当铺编号
    this.pawnShopId = "";
    this.pawnShopOwnerId = "";
    this.pawnTicketId = "";
    this.name = "";
    this.publishDate = "";
    this.dueDate = "";
    this.interestRate = "";
    this.prePeriod = "";
    this.postPeriod = "";
    this.handlingFeeRate = "";
    this.description = "";
}


//modelandview
var app = new Vue({
    el: '#app',
    data: {
        error: '',
        tender: tender
    },
    ready: function () {
        //定义错误实体
        var validation = new tender();
        this.$data.error = validation;
        //绑定显示错误信息的对象
        this.volidateEntity = validation;
        //绑定正则
        var regex = {
            "tender.pawnShopId": regex_number,
            "tender.pawnShopOwnerId": regex_number,
            "tender.pawnTicketId": regex_number,
            "tender.name": regex_empty,
            "tender.publishDate": regex_date,
            "tender.dueDate": regex_date,
            "tender.interestRate": regex_empty,
            "tender.prePeriod": regex_empty,
            "tender.postPeriod": regex_empty,
            "tender.handlingFeeRate": regex_empty,
            "tender.description": regex_empty
        }
        this.regexEntity = regex;
    },
    methods: {
        ajaxSubmit: function () {
            //第一个参数是对象（值），第二个参数是方法
            var content = ue.getContent();
            this.tender.description = content;
            app.submitVolidata(this.$data.tender, tender);
        },
        down: function (event) {
            if (event.target.value.length == 0 && event.keyCode == 9) {
                app.excuteVolidata(event);
            }
        },
        volidate: function (event) {
            if (event.target.value.length > 0) {
                app.excuteVolidata(event);
            }
        },
        setDate: function (event) {
            var data = this.$data.tender;
            var vue_event = event;
            $(".calendar_daysCell").click(function (event) {
                if (event.target.nodeName == "A") {
                    var key = vue_event.target.__v_model.expression.split(".")[1];
                    for (var o in data) {
                        if (o == key) {
                            data[o] = $("input[id=" + key + "]").val();
                            console.log(data[o]);
                            vue_event.target.value = $("input[id=" + key + "]").val();
                        }
                    }
                    app.excuteVolidata(vue_event);
                }
            });
        }
    }
})
