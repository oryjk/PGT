//表单对象模型 - 经个人实践得出- 必须给初值
var tender = function(){
    //当铺编号
    this.pawnShopId = "";
    //当前票号
    this.pawnTicketId = "";
    //总金额
    this.tenderTotal = "";
    //最小投资金额
    this.smallMoney = "";
    ////开始时间
    //this.publishDate = "";
    ////截止时间
    //this.dueDate = "";
    ////收益率
    //this.interestRate = "";
    ////投资名称
    this.name = "";
    ////投资详情
    this.description = "";
    //多少天收益
    this.prePeriod = "";
    //无息天数
    this.postPeriod = "";
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
            "tender.pawnShopId" : regex_empty,
            "tender.pawnTicketId" : regex_empty,
            "tender.tenderTotal" : regex_username,
            "tender.smallMoney" : regex_number,
            //"tender.publishDate" : regex_date,
            //"tender.dueDate" : regex_date,
            //"tender.interestRate" : regex_number,
            "tender.name" : regex_empty,
            "tender.description" : regex_empty,
            "tender.prePeriod" : regex_number,
            "tender.postPeriod" :regex_number
        }
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
    },
    watch:{
        'tender.publishDate' :function (a){
            console.log(this);
        }
    }
})
