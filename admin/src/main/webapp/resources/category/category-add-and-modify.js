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
                        $('.pgt-img-delete').attr('data-url', '/media/delete/' + mediaId);

                        test.remove();

                    });

                    $('#testbox').append(test);
                    test.attr('src', staticPath + responseBody.imagePath);
                    $('#frontMedia').val(param.mediaId);
                }
            });
        }
    });
});

$('#pgtColorInput').keydown(categoryColor);

$(document).on('click', '.pgt-img-delete', function(event) {
    event.preventDefault();

    var $this = $(this);

    $.ajax({
        type: 'get',
        url: $this.attr('data-url'),
        success: function(param) {
            if (param.success == true) {
                $('.pgt-category-img').attr('src', '').siblings('p').html('');
            }
        }
    })
});

categoryColor();

function categoryColor() {
    $('#pgtColor').css({
        background: $('#pgtColorInput').val()
    })
}