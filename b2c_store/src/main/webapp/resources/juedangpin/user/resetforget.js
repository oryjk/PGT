require.config({
    paths: {
        jquery: '../core/js/jquery.min',
    }
});

require(['jquery'], function($) {

	  $(document).ready(function() {
		  //把变量放在对象里,避免全局变量
		  var forgetObj = {
			num: 60
		  };

		  //我不太清楚这句话是什么意思.如果需要可以放在我的事件里面.
		  $("#getSmsPath").click(function(){
			  //var  smsPath = $("#smsPath").val();
			  //window.location.href= smsPath;
		  });

		  //$('#getSmsPath').click(function() {
			//  getPhoneCom($(this));
		  //});

		  $(document).on('click','#getSmsPath', function() {
			  getPhoneCom($(this));

		  });

		  $('#getAuthCode').on('click', function() {
			  $(this).attr('src', '/code/resetPassword?'+Math.ceil(Math.random()*100000000000000));
		  });

		  $('#changeAuthCode').on('click', function(event) {
			  event.preventDefault();
			  $('#getAuthCode').attr('src', '/code/resetPassword?'+Math.ceil(Math.random()*100000000000000));
		  });

		  function getPhoneCom(that) {
			  that.val('请稍等...')
				  .addClass('disable')
				  .prop('disabled', true);

			  //发送ajax请求，在成功后调用以下setTimeout()
			  $.ajax({
				  type: 'get',
				  url: $('#smsPath').attr('data-value'),
				  data: {
					  phoneNumber: $('#userPhone').val()
				  },
				  success: function() {
					  setTimeout(numDown, 1000);
				  }
			  });
		  }

		  function numDown() {
			  var btn = $('#getSmsPath');

			  forgetObj.num--;
			  btn.val(forgetObj.num + '秒');
			  if (forgetObj.num > 0) {
				  setTimeout(numDown, 1000);
			  } else {
				  //结束循环.
				  forgetObj.num = 10;
				  btn.val('获取手机验证码')
					  .removeClass('disable')
					  .prop('disabled', false);
			  }
		  }



	  });
});