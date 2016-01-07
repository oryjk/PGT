/**
 * Created by supersoup on 15/12/28.
 */
$('[data-pgt-btn="single"]').change(function () {
    var that = $(this);
    var form = that.parent();
    var staticPath = $('#staticServer').val();

    form.ajaxSubmit({
        url: '/upload/image',
        dataType: 'json',
        type: 'POST',
        success: function (responseBody) {
            $.ajax({
                url: '/product/create/stepImage',
                type: 'post',
                data: {
                    referenceId: $('#productId').val(),
                    title: $('#productName').val(),
                    path: responseBody.imagePath,
                    type: responseBody.mediaType
                },
                success: function (param) {
                    var size = '';
                    var test = $('<img>');
                    var mediaId = param.mediaId;

                    //所有功能写进回调:
                    test.load(function () {
                        var img = that.parents('.col-md-2').siblings('.col-md-8').children().children('img');
                        var p = img.siblings('p');
                        var deleteBtn = img.siblings('.pgt-handle-box').children('a');
                        size = test.width() + '*' + test.height();
                        img.attr('src', staticPath + responseBody.imagePath);
                        p.html(size);
                        deleteBtn.attr('data-url', '/media/delete/'+mediaId);
                        test.remove();
                    });

                    $('#testbox').append(test);
                    test.attr('src', staticPath + responseBody.imagePath);

                }
            });
        }
    });
});

$('[data-pgt-btn="multiple"]').change(function () {
    var that = $(this);
    var form = that.parent();
    var staticPath = $('#staticServer').val();

    form.ajaxSubmit({
        url: '/upload/image',
        dataType: 'json',
        type: 'POST',
        success: function (responseBody) {
            $.ajax({
                url: '/product/create/stepImage',
                type: 'post',
                data: {
                    referenceId: $('#productId').val(),
                    title: $('#productName').val(),
                    path: responseBody.imagePath,
                    type: responseBody.mediaType
                },
                success: function (param) {
                    var size = '';
                    var test = $('<img>');

                    test.load(function () {
                        size = test.width() + '*' + test.height();
                        test.remove();
                        var mediaId = param.mediaId;

                        var str =
                            '<div class="pgt-each-img"><div class="pgt-handle-box"><a class="pgt-img-delete" data-pgt-btn="delete-multiple" data-url="/media/delete/' +
                            mediaId +
                            '" href="#">删除</a> </div> <img class="pgt-main-img" src="' +
                            staticPath + responseBody.imagePath +
                            '" alt=""/> <p class="pgt-img-size">' +
                            size +
                            '</p> </div>';

                        that.parents('.col-md-2').siblings('.col-md-8').append($(str));
                    });

                    test.attr('src', staticPath + responseBody.imagePath);
                    $('#testbox').append(test);
                    test.attr('src', staticPath + responseBody.imagePath);
                }
            });
        }
    });
});

$(document).on('click', '.pgt-img-delete', function(event) {
    event.preventDefault();

    var $this = $(this);

    $.ajax({
        type: 'get',
        url: $this.attr('data-url'),
        success: function(param) {
            if (param.success == true) {
                var dataPgtBtn = $this.attr('data-pgt-btn');
                if (dataPgtBtn == 'delete-single') {
                    $this.parent()
                        .siblings('img').attr('src', '')
                        .siblings('p').html('');
                } else if (dataPgtBtn == 'delete-multiple') {
                    $this.parents('.pgt-each-img').remove();
                }
            }
        }
    })
});