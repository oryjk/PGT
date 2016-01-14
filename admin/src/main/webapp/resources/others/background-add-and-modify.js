/**
 * Created by supersoup on 16/1/8.
 */

//上传图片
$('[data-pgt-btn="single"]').change(function () {
    var that = $(this);
    var form = that.parent();
    //var staticPath = $('#staticServer').val();

    //提交图片
    form.ajaxSubmit({
        url:'/upload/image',
        dataType: 'json',
        type: 'POST',
        success: function(responseBody) {

            if(responseBody.mediaType=='header'){
                $('#pgt-header-img').attr('src', responseBody.imagePath);
            }
            if(responseBody.mediaType=='middle'){
                $('#pgt-middle-img').attr('src', responseBody.imagePath);
            }

            if(responseBody.mediaType=='footer'){
                $('#pgt-footer-img').attr('src', responseBody.imagePath);
            }


            //提交图片的关联性
            $.ajax({
                type: 'POST',
                url: '/pageBackground/create/stepImage',
                data: {
                    path: responseBody.imagePath,
                    type: responseBody.mediaType,
                    referenceId:$('#pageBackgroundId').val()
                },
                success: function (param) {
                    var size = '';
                    //测量图片大小所用
                    var test = $('<img>');
                    var mediaId = param.mediaId;
                    var mediaType=param.mediaType;

                    if(mediaType=='header'){
                        $('#pgt-header-delete').attr('data-url', '/media/delete/' + mediaId);
                    }
                    if(mediaType=='middle'){
                        $('#pgt-middle-delete').attr('data-url', '/media/delete/' + mediaId);
                    }

                    if(mediaType=='footer'){
                        $('#pgt-footer-delete').attr('data-url', '/media/delete/' + mediaId);
                    }


                    //等待图片加载完成之后触发回调
                    test.load(function () {
                        size = test.width() + '*' + test.height();

                        $('.pgt-category-img').attr('src', responseBody.imagePath)
                            .siblings('p').html(size);
                        $('.pgt-img-delete').attr('data-url', '/media/delete/' + mediaId);

                        test.remove();

                    });

                    //需要把图片加入dom后才能获取得到它的height和width
                    $('#testbox').append(test);
                    test.attr('src', staticPath + responseBody.imagePath);
                    $('#frontMedia').val(param.mediaId);
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
                $('.pgt-category-img').attr('src', '').siblings('p').html('');
            }
        }
    })
});

