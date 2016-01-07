/**
 * Created by supersoup on 15/12/28.
 */

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

$('[data-pgt-btn="single"]').change(function () {
    var that = $(this);
    var form = that.parent();
    var staticPath = $('#staticServer').val();

    form.ajaxSubmit({
        dataType: 'json',
        type: 'POST',
        success: function(responseBody) {

            $.ajax({
                type: 'POST',
                url: '/media/create',
                data: {
                    id: $('#categoryMediaId').val(),
                    title: $('#categoryMediaRefTitle').val(),
                    path: responseBody.imagePath,
                    type: responseBody.mediaType
                },
                success: function (param) {
                    var size = '';
                    var test = $('<img>');
                    var mediaId = param.mediaId;

                    //所有功能写进回调:
                    test.load(function () {
                        size = test.width() + '*' + test.height();
                        $('.pgt-category-img').attr('src', staticPath + responseBody.imagePath)
                            .siblings('p').html(size);
                        $('.pgt-img-delete').attr('data-url', mediaId);
                        test.remove();

                    });

                    $('#testbox').append(test);
                    test.attr('src', staticPath + responseBody.imagePath);
                }
            });
        }
    });
});

$('#pgtColorInput').keydown(categoryColor);

categoryColor();

function categoryColor() {
    $('#pgtColor').css({
        background: $('#pgtColorInput').val()
    })
}