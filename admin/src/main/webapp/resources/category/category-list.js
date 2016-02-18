/**
 * Created by supersoup on 15/12/27.
 */
//$(document).on('click', 'a[data-pgt-dropdown="status-option"]', function() {
//    var that = $(this);
//    var text = that.text();
//    var value= that.attr('data-pgt-value');
//    var view = that.parents('ul').siblings('a');
//    view.removeClass('blue yellow red').html(text + ' <i class="fa fa-angle-down"></i>').attr('data-pgt-value', text);
//
//    if (value == 0) {
//        view.addClass('default');
//    } else if (value == 1) {
//        view.addClass('blue');
//    }
//});

$('#categorySelect').change(function() {
    var $this = $(this);
    window.location = '/category/getSubCategories/' + $this.val();
});

$('#tenderCategorySelect').change(function() {
    var $this = $(this);
    window.location = '/tenderCategory/getSubCategories/' + $this.val();
});

$(document).on('click', '[data-pgt-btn="modify"], [data-pgt-btn="create"]', function () {
    var $this = $(this);
    window.location = $this.attr('data-url');
});

$(document).on('click', '[data-pgt-btn="delete"]', function () {
    var $this = $(this);
    $('#confirmModal').modal();
    $('#comfirmBtn')
        .off()
        .click(function () {
            $.ajax({
                type: 'get',
                url: $this.attr('data-url'),
                data: {
                    categoryType: $this.attr('data-pgt-type')
                },
                success: function (param) {
                    if (param.success == true) {
                        location.reload();
                    }
                }
            })
        });
});


