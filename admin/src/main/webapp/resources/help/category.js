$(function(){
	$('#capacityList').change(function(e){
		$('#capacity').val($(this).val());
		$('#submitBtn').click();
	});
	$('#categorySelect').change(function(e){
		$('#categoryId').val($(this).val());
		$('#submitBtn').click();
	});
	
	$('.js-change-page').click(function(e){
		$('#currentPage').val($(this).data('page'));
		$('#submitBtn').click();
	});
	
	$('.js-update-category-status').click(function(e){
		$('#categoryId').val($(this).data('category-id'));
		$('#categoryStatus').val($(this).data('pgt-value'));
		$('#updateCategoryStatusForm').submit();
	});
});

