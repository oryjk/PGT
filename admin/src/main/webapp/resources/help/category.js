$(function(){
	$('#capacityList').change(function(e){
		$('#capacity').val($(this).val());
		$('#submitBtn').click();
	});
	
	$('.js-change-page').click(function(e){
		$('#currentPage').val($(this).data('page'));
		$('#submitBtn').click();
	});
});