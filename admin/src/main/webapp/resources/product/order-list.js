/**
 * Created by supersoup on 15/12/25.
 */
$(function (){
    $(".jcDate").jcDate({
        IcoClass : "jcDateIco",
        Event : "click",
        Speed : 100,
        Left : 0,
        Top : 28,
        format : "-",
        Timeout : 100
    });
});

$(document).on('click', '[data-pgt-btn="modify"]', function() {
    $('#confirmModal').modal('show');
    $('#confirmText').text('确定要修改?')
});
$(document).on('click', '[data-pgt-btn="delete"]', function() {
    $('#confirmModal').modal('show');
    $('#confirmText').text('确定要删除?')
});
$(document).on('click', '[data-pgt-btn="modifyChecked"]', function() {
    $('#confirmModal').modal('show');
    $('#confirmText').text('确定要修改所选项?')
});
$(document).on('click', '[data-pgt-btn="deleteChecked"]', function() {
    $('#confirmModal').modal('show');
    $('#confirmText').text('确定要删除所选项?')
});
$('#checkAll').change(function() {
    var that = $(this);
    var allCheck = $('#list input[type="checkbox"]');
    if (that.prop('checked')) {
        allCheck.prop('checked', true);
    } else {
        allCheck.prop('checked', false);
    }
})