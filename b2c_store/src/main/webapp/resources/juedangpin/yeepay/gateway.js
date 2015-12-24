require.config({
	baseUrl: "../../../resources",
    paths: {
        jquery: './juedangpin/core/js/jquery.min',
        jqueryForm: './juedangpin/core/js/jquery.form',
        component: './juedangpin/core/js/module/component'
    },
    shim: {
        'jqueryForm': ['jquery']
    }
});


define(["jquery", "jqueryForm", 'component'], function($,jqForm,Cpn) {
	$("#yeepayDataForm").ajaxForm({
		
		dataType: 'json',
		success: function (data) {
			if (data.error) {
				alert(data.error);
			} else {
				var requestXML = data.requestXML;
				var sign = data.sign;
				var requestURL = data.requestURL;
				$("#req").val(requestXML);
				$("#sign").val(sign);
				$("#yeepayGatewayForm").attr("action", requestURL);
				$("#yeepayGatewayForm").submit();
			}
			
		}
	});
	
	if ("Y" === $("#yeepayGatewayForm").attr("auto-submit")) {
		$("#yeepayGatewayForm").submit();
	}
	if ("Y" === $("#yeepayDataForm").attr("auto-submit")) {
		$("#yeepayDataForm").submit();
	}
	 //横向商品列表点击左右移动
    Cpn.rowList({
        list: $('#rowList'),
        left: $('#moveRight'),
        right: $('#moveLeft')
    });

    //为仿select绑定事件
    Cpn.select($('.select-view'), $('.option-view'));
});

