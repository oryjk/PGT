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

$('#levelSelect').change(function() {
    var that = $(this);
    var categoryMain =  $('#categoryMain');
    var categorySon = $('#categorySon');
    var imgUpload = $('#imgUpload');
    if (that.val() == 'TENDER_ROOT') {
        categoryMain.show();
        imgUpload.show();
        categorySon.hide();
    } else if (that.val() == 'TENDER_HIERARCHY') {
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

                        if(responseBody.mediaType=="icon"){

                            $('.pgt-icon-img').attr('src', responseBody.imagePath)
                                .siblings('p').html(size);
                            $('.pgt-icon-delete').attr('data-url', '/media/delete/' + mediaId);
                            $('#iconMedia').val(param.mediaId);
                        }else{
                            $('.pgt-category-img').attr('src', responseBody.imagePath)
                                .siblings('p').html(size);
                            $('.pgt-img-delete').attr('data-url', '/media/delete/' + mediaId);
                            $('#frontMedia').val(param.mediaId);
                        }



                        test.remove();

                    });

                    //需要把图片加入dom后才能获取得到它的height和width
                    $('#testbox').append(test);
                    test.attr('src', responseBody.imagePath);


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
        }
    })
});

categoryColor();

function categoryColor() {
    $('#pgtColor').css({
        background: $('#pgtColorInput').val()
    })
}