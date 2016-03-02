$(document).ready(function(){


		  $("#getSmsPath").click(function(){

			  $.ajax({
				  type: 'get',
				  url: $('#smsPath').attr('data-value'),
				  success: function() {
					  alert("发送成功!");
				  }
			  });


		  });


});