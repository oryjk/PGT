/**
 * Created by supersoup on 15/12/28.
 */
$('[data-pgt-btn="single"]').change(function () {
    var that = $(this);
    var form = that.parent();
    var p = form.siblings('p');
    var staticPath = $('#staticServer').val();
    p.html(that.val());

    form.ajaxSubmit({
        url: '/upload/image',
        dataType: 'json',
        type: 'POST',
        success: function (responseBody) {
            var size = '';
            var test = $('<img>');

            //所有功能写进回调:
            test.load(function () {
                size = test.width() + '*' + test.height();
                that.parents('.col-md-2').siblings('.col-md-8').children().children('img').attr('src', staticPath + responseBody.imagePath)
                    .siblings('p').html(size);
                console.log(that.parents('.col-md-2').siblings('.col-md-8').children().children('img').siblings('p'))
                test.remove();
                $.ajax({
                    url: '/product/create/stepImage',
                    type: 'post',
                    data: {
                        referenceId: $('#productId').val(),
                        title: $('#productName').val(),
                        path: responseBody.imagePath,
                        type: responseBody.mediaType
                    },
                    success: function () {
                    }
                });
            });


            $('#testbox').append(test);
            test.attr('src', staticPath + responseBody.imagePath);

        }
    });
});

$('[data-pgt-btn="multiple"]').change(function () {
    var that = $(this);
    var form = that.parent();
    var p = form.siblings('p');
    var staticPath = $('#staticServer').val();
    p.html(that.val());

    form.ajaxSubmit({
        url: '/upload/image',
        dataType: 'json',
        type: 'POST',
        success: function (responseBody) {
            var size = '';
            var test = $('<img>');

            test.attr('src', staticPath + responseBody.imagePath);

            //所有功能写进回调:
            test.load(function () {
                size = test.width() + '*' + test.height();
                test.remove();

                var str =
                    '<div class="pgt-each-img"><div class="pgt-handle-box"><a class="pgt-img-delete" href="#">删除</a> </div> <img class="pgt-main-img" src="' +
                    staticPath + responseBody.imagePath +
                    '" alt=""/> <p class="pgt-img-size">' +
                    size +
                    '</p> </div>';

                that.parents('.col-md-2').siblings('.col-md-8').append($(str));

                $.ajax({
                    url: '/product/create/stepImage',
                    type: 'post',
                    data: {
                        referenceId: $('#productId').val(),
                        title: $('#productName').val(),
                        path: responseBody.imagePath,
                        type: responseBody.mediaType
                    },
                    success: function () {
                    }
                });
            });

            $('#testbox').append(test);
            test.attr('src', staticPath + responseBody.imagePath);
        }
    });
});

