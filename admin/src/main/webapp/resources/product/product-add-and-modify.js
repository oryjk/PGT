/**
 * Created by supersoup on 15/12/28.
 */
$('.pgt-file-btn').change(function () {
    var that = $(this);
    var form = that.parent();
    var p = form.siblings('p');
    var staticPath = $('#staticServer').val();
    p.html(that.val());

    form.ajaxSubmit({
        url: $('#contextPath').val() + '/upload/image',
        dataType: 'json',
        type: 'POST',
        success: function (responseBody) {
            var size = '';
            var test = $('#test');
            test.attr('src', staticPath + responseBody.imagePath);
            //所有功能写进回调:
            test.load(function() {
                size = test.width() + '*' + test.height();
                that.parents('.col-md-2').siblings('.col-md-8').children().children('img').attr('src', staticPath + responseBody.imagePath)
                    .siblings('p').html(size);
                $.ajax({
                    url: $('#contextPath').val() + '/product/create/stepImage',
                    type: 'post',
                    data: {
                        referenceId: $('#productId').val(),
                        title: $('#productName').val(),
                        path: responseBody.imagePath,
                        type: responseBody.mediaType
                    },
                    success: function() {}
                });
            });
        }
    });

});

