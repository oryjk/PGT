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
            var newImg = document.createElement('img');
            newImg.src = staticPath + responseBody.imagePath;
            var width = newImg.width;
            var height = newImg.height;
            console.log(width + '*' + height);
            that.parents('.col-md-2').siblings('.col-md-8').children().children('img').attr('src', staticPath + responseBody.imagePath);
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

        }
    });

});

