/**
 * Created by supersoup on 15/12/28.
 */
$('.pgt-file-btn').change(function() {
    var that = $(this);
    var form = that.parent();
    var p = form.siblings('p');
    p.html(that.val());

    form.ajaxSubmit(function(param) {

    });

});

$('#levelSelect').change(function() {
    var that = $(this);
    var categoryMain =  $('#categoryMain');
    var categorySon = $('#categorySon');
    var imgUpload = $('#imgUpload');
    if (that.val() == 'ROOT') {
        categoryMain.show();
        imgUpload.show();
        categorySon.hide();
    } else if (that.val() == 'HIERARCHY') {
        categoryMain.hide();
        imgUpload.hide();
        categorySon.show();
    }
});