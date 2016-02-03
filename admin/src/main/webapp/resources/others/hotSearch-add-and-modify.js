/**
 * Created by supersoup on 16/1/8.
 */

//上传图片
$('[data-pgt-btn="single"]').change(function () {
    var that = $(this);
    var form = that.parent();

    //提交图片
    form.ajaxSubmit({
        url: '/upload/image',
        dataType: 'json',
        type: 'POST',
        success: function (responseBody) {

            $("#pgt-banner-img").val(responseBody.imagePath);
            $('#pgt-category-img').attr('src', responseBody.imagePath);
            //提交图片的关联性
            $.ajax({
                type: 'POST',
                url: '/hotSearch/create/stepImage',
                data: {
                    path: responseBody.imagePath,
                    type: responseBody.mediaType,
                    referenceId: $('#hotSearchId').val()
                },
                success: function (param) {
                    var size = '';
                    //测量图片大小所用
                    var test = $('<img>');
                    var mediaId = param.mediaId;

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
                    test.attr('src', responseBody.imagePath);
                    $('#frontMedia').val(param.mediaId);
                }
            });
        }
    });
});


$(document).on('click', '.pgt-img-delete', function (event) {
    event.preventDefault();

    var $this = $(this);

    $.ajax({
        type: 'get',
        url: $this.attr('data-url'),
        success: function (param) {
            if (param.success == true) {
                $('.pgt-category-img').attr('src', '').siblings('p').html('');
            }
        }
    })
});

