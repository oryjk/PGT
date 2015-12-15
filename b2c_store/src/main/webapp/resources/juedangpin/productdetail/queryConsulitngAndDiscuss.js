
function createConsulitng() {

	$.ajax({
		type : "POST",
		url : '../consulting/createconsulting',
		data : {
			productId : $('#productId').val(),
			content : $('#contentConsulting').val()
		},// 你的formid
		success : function(data) {

			var logincheck = data.logincheck;

			var herf = window.location.href;

			if (logincheck == "no") {

				$("#consultingredirect").val(herf);

				$("#consultingfrom").submit();

				return;

			}

			$("#popContent").text("咨询已提交!我们会尽快为您解答!");

			$("#popUp").fadeIn(300);

			$("#phoneNumber").val("");
			$("#contentConsulting").val("");

			gotoPageNum(1);
		}

	})

}

function createDiscuss() {

	$.ajax({
		type : "POST",
		url : '../discuss/createDiscuss',
		// data : $('#createDisuss').serialize(),// 你的formid
		data : {
			productId : $('#productId').val(),
			content : $('#contentDiscuss').val()
		},
		success : function(data) {

			var logincheck = data.logincheck;

			var herf = window.location.href;

			if (logincheck == "no") {

				$("#redirect").val(herf);

				$("#discussredirect").submit();

				return;

			}

			$("#popContent").text("讨论已提交!我们会尽快审核!");

			$("#popUp").fadeIn(300);

			$("#phoneNumber").val("");
			$("#contentDiscuss").val("");

			gotoPageNumDiss(1);

		}

	})

}

function gotoPageNum(num) {

	if (num == null) {

		num = $("#pageconsulting").val();

	}



	var pageSize = $("#capacity_consulting").val();

	var productId = $("#productId").val();

	var total = $("#totalPage_discuss").val();

	if (parseInt(num) > parseInt(total)) {

		num = total;

	}

	if (parseInt(num) < 1) {
		num = 1;
	}

	var currentIndex = (num - 1) * pageSize;

	$("#question")
			.load("../consulting/query/" + currentIndex + "/" + productId);

}

function gotoPageNumDiss(num) {

	if (num == null) {

		num = $("#page_discuss").val();

	}

	

	var pageSize = $("#capacity_discuss").val();

	var productId = $("#productId").val();

	var total = $("#totalPage_consulting").val();

	if (parseInt(num) > parseInt(total)) {

		num = total;

	}

	if (parseInt(num) < 1) {
		num = 1;
	}

	var pageSize = $("#capacity_discuss").val();

	var currentIndex = (num - 1) * pageSize;

	$("#talking").load("../discuss/query/" + currentIndex + "/" + productId,
			function() {
				$("#discussprodcuctId").val(productId);
			});

}
