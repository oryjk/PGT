/**
 * Created by supersoup on 15/12/27.
 */
$(document).on('click', 'a[data-pgt-dropdown="status-option"]', function() {
    var that = $(this);
    var text = that.text();
    var value= that.attr('data-pgt-value');
    var view = that.parents('ul').siblings('a');
    view.removeClass('blue yellow red').html(text + ' <i class="fa fa-angle-down"></i>').attr('data-pgt-value', text);

    if (value == 0) {
        view.addClass('default');
    } else if (value == 1) {
        view.addClass('blue');
    }
});