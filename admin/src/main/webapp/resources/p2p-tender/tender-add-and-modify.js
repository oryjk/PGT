/**
 * Created by supersoup on 15/12/28.
 */

initial()


inspect();

$('[data-pgt-btn="single"]').change(function () {

    var that = $(this);
    var form = that.parent();
    var message = form.siblings('p');
    form.ajaxSubmit({
        url: '/upload/image',
        dataType: 'json',
        type: 'POST',
        success: function (responseBody) {
            $.ajax({
                url: '/tender/addMedias',
                type: 'post',
                data: {
                    referenceId: $('#tenderId').val(),
                    title: $('#tendertName').val(),
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
                        img.attr('src', responseBody.imagePath);
                        p.html(size);
                        deleteBtn.attr('data-url', '/media/delete/' + mediaId);
                        message.html('图片上传成功');
                        test.remove();

                        //检查src以确定是否允许上传图片
                        inspect();
                    });

                    $('#testbox').append(test);
                    test.attr('src', responseBody.imagePath);

                }
            });
        }
    });
});

$('[data-pgt-btn="multiple"]').change(function () {
    var that = $(this);
    var form = that.parent();
    var message = form.siblings('p');

    form.ajaxSubmit({
        url: '/upload/image',
        dataType: 'json',
        type: 'POST',
        success: function (responseBody) {
            $.ajax({
                url: '/tender/addMedias',
                type: 'post',
                data: {
                    referenceId: $('#tenderId').val(),
                    title: $('#tenderName').val(),
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
                            responseBody.imagePath +
                            '" alt=""/> <p class="pgt-img-size">' +
                            size +
                            '</p> </div>';

                        that.parents('.col-md-2').siblings('.col-md-8').append($(str));
                        message.html('图片上传成功');


                    });

                    test.attr('src', responseBody.imagePath);
                    $('#testbox').append(test);
                    test.attr('src', responseBody.imagePath);
                }
            });
        }
    });
});

$(document).on('click', '.pgt-img-delete', function (event) {
    event.preventDefault();

    var $this = $(this);
    var message = $this.parents('.col-md-8').siblings('.col-md-2').children('p');


    $.ajax({
        type: 'get',
        url: $this.attr('data-url'),
        success: function (param) {
            if (param.success == true) {
                var dataPgtBtn = $this.attr('data-pgt-btn');
                if (dataPgtBtn == 'delete-single') {
                    $this.parent()
                        .siblings('img').attr('src', '')
                        .siblings('p').html('');

                    //检查src以确定是否允许上传图片
                    inspect();

                } else if (dataPgtBtn == 'delete-multiple') {
                    $this.parents('.pgt-each-img').remove();
                }

                message.html('图片删除成功');
                console.log(message);
            }
        }
    })
});


function inspect() {
    $('[data-pgt-btn="single"]').each(function () {
        var $this = $(this);
        var img = $this.parents('.col-md-2').siblings('.col-md-8').children().children('img');

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

function initial(){
    var tenderId = getUrlParam('tenderId');
    $('#tenderId').val(tenderId);
    $('#tenderId').html(tenderId);
}

function getUrlParam(name){
    //构造一个含有目标参数的正则表达式对象
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    //匹配目标参数
    var r = window.location.search.substr(1).match(reg);
    //返回参数值
    if (r!=null) return unescape(r[2]);
    return null;
}

$('.green-haze').on('click',function(){
    window.location = $(this).attr('data-url');
})