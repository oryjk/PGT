/**
 * Created by supersoup on 15/12/3.
 */


/**
 * Created by carl on 15/10/30.
 */
require.config({
    paths: {
        jquery: '../../core/js/jquery.min',
        component: '../../core/js/module/component'
    }
});

require(['jquery', 'component'], function($, Cpn) {
    $(document).ready(function() {

        //固定头部
        Cpn.fixedHead({
            head: $('#fixedHead'),
            height: 800,
            time: 200
        });

        //弹出框
        Cpn.pop({
            popUp: $('#popUp'),
            close: $('#popClose, #popReset')
        });
        $('#addAddress').click(function() {
            $('#popUp').fadeIn(300);
        });
        
        $('#popSubmit').click(function(e){
        	e.preventDefault();
        	var $this = $(this);
        	if($this.data('pending')){
        		return false;
        	}
        	$this.data('pending',true);
        	var form = $('#popForm'),
        		action = form.attr('action'),
        		data = form.serialize();
        	form.find('span.js-error-msg').remove();
        	$.post(action,data).done(function(result){
        		if(result.success == 'true'){
        			window.location.reload();
        		}else{
        			if(result.redirectUrl){
        				window.location.href = result.redirectUrl;
        			}else{
        				var errors = result.errors;
        				$.each(errors,function(key,value){
        					form.find('[name='+key+']').closest('div').append('<span class="js-error-msg">'+value+'</span>');
        				});
        			}
        		}
        		$this.data('pending',false);
        	});
        });
        
        $('.js-update-address').click(function(e) {
        	e.preventDefault();
        	var $this = $(this),
        	 	form = $('#popForm');
        	if($this.data('pending')){
        		return false;
        	}
        	$this.data('pending',true);
        	form.attr('action',$this.data('href'));
        	$.get('findAddress/'+$this.data('address-id')).done(function(result){
        		if(result.success == 'true'){
        			var data = JSON.parse(result.data);
        			$.each(data, function(key,value){
        				var el = form.find('[name='+key+']');
        				if(el){
        					el.val(value);
        				}
        			});
        			$('#popUp').fadeIn(300);
        			$this.data('pending',false);
        		}
        	});
        });
        
        $('.js-delete-address').click(function(e){
        	e.preventDefault();
        	var $this = $(this);
        	$.post($this.data('href')).done(function(result){
        		if(result.success == 'true'){
        			$this.closest('div.address').remove();
        		}
        	});
        });
        
        $('.js-set-default-address').click(function(e){
        	e.preventDefault();
        	var $this = $(this);
        	$.post($this.data('href')).done(function(result){
        		if(result.success == 'true'){
        			window.location.reload();
        		}
        	});
        });
      
    });
});