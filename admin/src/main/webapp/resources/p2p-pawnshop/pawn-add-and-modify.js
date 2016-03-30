/**
 * Created by supersoup on 15/12/28.
 */
inspect();


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

//上传图片
$('[data-pgt-btn="single"]').change(function () {
    var that = $(this);
    var form = that.parent();

    //提交图片
    form.ajaxSubmit({
        dataType: 'json',
        type: 'POST',
        success: function(responseBody) {

            //提交图片的关联性
            $.ajax({
                type: 'POST',
                url: '/media/create',
                data: {
                    path: responseBody.imagePath,
                    type: responseBody.mediaType
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
                        inspect();

                    });

                    //需要把图片加入dom后才能获取得到它的height和width
                    $('#testbox').append(test);
                    test.attr('src', responseBody.imagePath);
                    $('#frontMedia').val(param.mediaId);
                    $('#frontMediaPath').val(responseBody.imagePath);

                }
            });
        }
    });
});

//有输入时变色
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
            inspect();
        }
    })
});

categoryColor();

function categoryColor() {
    $('#pgtColor').css({
        background: $('#pgtColorInput').val()
    })
}

function inspect() {
    $('[data-pgt-btn="single"]').each(function () {
        var $this = $(this);
        var img = $this.parents('.row').siblings('.row').find('.pgt-category-img');

        if (img.attr('src') == '') {
            $this.attr('data-pgt-available', '1').css({
                display: 'inline'
            }).siblings('button').addClass('blue');
        } else {
            $this.attr('data-pgt-available', '0').css({
                display: 'none'
            }).siblings('button').removeClass('blue');
        }
    });
}