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
    console.log(this)
    var that = $(this);
    var categoryMain =  $('#categoryMain');
    var categorySon = $('#categorySon');
    var imgUpload = $('#imgUpload');
    if (that.val() == 1) {
        categoryMain.show();
        imgUpload.show();
        categorySon.hide();
    } else if (that.val() == 2) {
        categoryMain.hide();
        imgUpload.hide();
        categorySon.show();
    }
});