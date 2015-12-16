/**
 * Created by supersoup on 15/11/20.
 */
require.config({
    paths: {
        jquery: '../core/js/jquery.min',
        component: '../core/js/module/component',
		product: '../core/js/module/product'
    }
});

require(['jquery', 'component', 'product'], function($, Cpn, Prd) {
    $(document).ready(function() {
        var stepObj = {
            step: 1,
            stepSign: $('#step'),
            content1: $('#content1'),
            content2: $('#content2'),
            content3: $('#content3'),
            content4: $('#content4')
        };


        //tab切换
        Cpn.tab({
            tabArea: $('#recommend>div'),
            tabLi: $('#tab>li'),
            tabTarget: $('#tab h2')
        });

        //三个rowList的水平移动
        Cpn.rowList({
            list: $('#rowList1'),
            left: $('#moveRight1'),
            right: $('#moveLeft1')
        });
        Cpn.rowList({
            list: $('#rowList2'),
            left: $('#moveRight2'),
            right: $('#moveLeft2')
        });
        Cpn.rowList({
            list: $('#rowList3'),
            left: $('#moveRight3'),
            right: $('#moveLeft3')
        });

		//事件委托:加入购物车, 添加收藏
		$(document).on('click', '.addCart', addCart);
		$(document).on('click', '.addEnjoy', addEnjoy);

		//加入购物车
		function addCart(event) {
			var that = $(this);
			var productId = that.attr('data-value');
			var productMessage = that.parent().siblings().filter('.product-message');

			event.preventDefault();

			Prd.addItemToOrder(productId, productMessage, $('#asideCartCount, #fixedCartCount, #cartCount'));
		}

		//添加收藏
		function addEnjoy(event) {
			var that = $(this);
			var productId = that.attr('data-value');
			var productMessage = that.parent().siblings().filter('.product-message');

			event.preventDefault();

			Prd.addItemToFavourite(productId, productMessage);
		}

		//为仿select绑定事件
		//省->市
		var areaObj = {
			province: $('#province'),
			city: $('#city'),
			country: $('#country')
		};

		Cpn.select2(areaObj.province, function(id) {
			var url = Prd.baseUrl + '/getCityByProvinceId/' + id;
			$.ajax({
				url: url,
				data: null,
				success: function(param) {
					var str = '';
					$.each($.parseJSON(param), function() {
						str += '<li><a class="option-view" data-value="'+ this.id+'" href="#">'+ this.name+'</a></li>'
					});
					$('#city').children('.selected').html('请选择').end().siblings('.options').html(str).next('.select-value').val('');
					$('#country').children('.selected').html('请选择').end().siblings('.options').html('').next('.select-value').val('');
				}
			})
		});

		//市->区
		Cpn.select2(areaObj.city, function(id) {
			var url = Prd.baseUrl + '/getAreaByCityId/' + id;
			$.ajax({
				type: 'get',
				url: url,
				data: null,
				success: function(param) {
					var str = '';
					$.each($.parseJSON(param), function() {
						str += '<li><a class="option-view" data-value="'+ this.id+'" href="#">'+ this.name+'</a></li>'
					});
					$('#country').children('.selected').html('请选择').end().siblings('.options').html(str).next('.select-value').val('');
				}
			})
		});

		//区
		Cpn.select2(areaObj.country);

	});
    
    
 //===============shipping page begin======================
    $('#addAddress').click(function() {
        $('#popUp').fadeIn(300);
    });
    
    $('#js-add-address').click(function(e){
    	e.preventDefault();
    	var $this = $(this);
    	if($this.data('pending')){
    		return false;
    	}
    	$this.data('pending',true);
    	var form = $('#addAddressForm'),
    		action = form.attr('action'),
    		data = form.serialize();
    	form.find('span.js-error-msg').empty();
    	$.post(action,data).done(function(result){
    		if(result.success == 'true'){
    			$('#addressInfoId').val(result.addedAddressId);
    			$('#js-add-address-to-order').click();
    			$this.data('pending',false);
    		}else{
    			if(result.redirectUrl){
    				window.location.href = result.redirectUrl;
    			}else{
    				var errors = result.errors;
    				$.each(errors,function(key,value){
    					form.find('[name='+key+']').closest('tr').find('span.js-error-msg').text(value);
    				});
    			}
    			$this.data('pending',false);
    		}
    	});
    });
    
    $('.js-update-address').click(function(e) {
    	e.preventDefault();
    	var $this = $(this),
    		form = $('#addAddressForm');
    	if($this.data('pending')){
    		return false;
    	}
    	$this.data('pending',true);
    	$('#js-address-form-area').show();
    	form.attr('action',$this.data('href'));
    	$.get($this.data('find-adress-url')).done(function(result){
    		if(result.success == 'true'){
    			var data = JSON.parse(result.data);
    			$.each(data, function(key,value){
    				var el = form.find('[name='+key+']');
    				if(el){
    					el.val(value);
    				}
    			});
    			$this.data('pending',false);
    		}
    		$this.data('pending',false);
    	});
    });
    
    $('.js-delete-address').click(function(e){
    	e.preventDefault();
    	var $this = $(this);
    	$.post($this.data('href')).done(function(result){
    		if(result.success == 'true'){
    			$this.closest('div.row').remove();
    		}
    	});
    });
    $('.js-address-item').change(function(){
    	$('#addressInfoId').val($(this).val());
		$('#js-add-address-to-order').click();
    });
    
    
    $('#js-add-pickup').click(function(e){
    	e.preventDefault();
    	var $this = $(this);
    	if($this.data('pending')){
    		return false;
    	}
    	$this.data('pending',true);
    	var form = $('#addPickup'),
			action = form.attr('action'),
			data = form.serialize();
		form.find('span.js-error-msg').empty();
		$.post(action,data).done(function(result){
			if(result.success == 'true'){
				window.location.reload();
			}else{
				if(result.redirectUrl){
					window.location.href = result.redirectUrl;
				}else{
					var errors = result.errors;
					$.each(errors,function(key,value){
						form.find('[name='+key+']').closest('div').append('<span class="js-error-msg">'+value+'</span>');;
					});
				}
			}
			$this.data('pending',false);
		});
    });
    $('#js-add-address-to-order').click(function(){
    	var form = $('#addAddressToOrder'),
		action = form.attr('action'),
		data = form.serialize();
    	$.post(action,data).done(function(result){//
    		if(result.success == 'true'){
				window.location.reload();
			}else{
				if(result.redirectUrl){
					window.location.href = result.redirectUrl;
				}
			}
    	});
    });
    
    
    
    $('#js-delivery-shipping').click(function(){
    	if($(this).hasClass('d-btn')){
    		return;
    	}
    	$(this).addClass('d-btn').removeClass('l-btn');
    	$('#js-pickup-shipping').removeClass('d-btn').addClass('l-btn');
    	$('#addAddressForm').show();
    	$('#js-selected-delivery-info').show();
     	$('#js-selected-pickup-info').hide();
      	$('#js-pickup-area').hide();
    });
    
    $('#js-pickup-shipping').click(function(){
    	if($(this).hasClass('d-btn')){
    		return;
    	}
     	$(this).addClass('d-btn').removeClass('l-btn');
     	$('#js-delivery-shipping').removeClass('d-btn').addClass('l-btn');
     	$('#addAddressForm').hide();
     	$('#js-selected-delivery-info').hide();
     	$('#js-selected-pickup-info').show();
     	$('#js-pickup-area').show();
    });
    
    $('.js-change-shipping').click(function(){
    	var isDelivery = $(this).data('is-delivery');
    	if(isDelivery){
    		$('#addAddressForm').show();
    	}else{
    		$('#js-pickup-area').show();
    	}
    });
    $('#show-address-form').click(function(){
    	$('#addAddressForm')[0].reset();
    	$('#js-address-form-area').show();
    });
    $('#js-hide-address-form').click(function(){
    	$('#js-address-form-area').hide();
    });
    
 //===============shipping page end========================
});