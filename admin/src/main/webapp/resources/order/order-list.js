/**
 * Created by supersoup on 15/12/25.
 */
$(function () {
    $(".jcDate").jcDate({
        IcoClass: "jcDateIco",
        Event: "click",
        Speed: 100,
        Left: 0,
        Top: 28,
        format: "-",
        Timeout: 100
    });

    $(".timePicker").datetimepicker({
        timeFormat: "HH:mm:ss"
    });
});

changeStatusUrl = ctx + "/order/ajax-change-order-status"

$(document).on('click', '[data-pgt-btn="modify"]', function (e) {
    var source = $(e.target);
    var status = source.data("status");
    var id = source.data("order-id");
    $(document).off('click', '.btn_confirm').on('click', '.btn_confirm', function (e) {
        $.post(changeStatusUrl, {'id': id, 'status': status}, function (response) {
            if (response != null && response != undefined) {
                if (response.success == 1) {
                    window.location.reload();
                }
                $('#confirmModal').modal('hide')
            }
        });
    });
    $('#confirmText').text('确定要修改?')
    $('#confirmModal').modal('show');
});
$(document).on('click', '[data-pgt-btn="delete"]', function () {
    $('#confirmModal').modal('show');
    $('#confirmText').text('确定要删除?')
});
$(document).on('click', '[data-pgt-btn="modifyChecked"]', function () {
    $('#confirmModal').modal('show');
    $('#confirmText').text('确定要修改所选项?')
});
$(document).on('click', '[data-pgt-btn="deleteChecked"]', function () {
    $('#confirmModal').modal('show');
    $('#confirmText').text('确定要删除所选项?')
});

$('#checkAll').change(function () {
    var that = $(this);
    var allCheck = $('#list input[type="checkbox"]');
    if (that.prop('checked')) {
        allCheck.prop('checked', true);
    } else {
        allCheck.prop('checked', false);
    }
});