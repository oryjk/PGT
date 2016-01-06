/**
 * Created by supersoup on 15/12/28.
 */
$('.pgt-file-btn').change(function() {
    var that = $(this);
    var form = that.parent();
    var p = form.siblings('p');
    p.html(that.val());

    form.ajaxSubmit(function(param) {

    });

});

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
    var p = form.siblings('p');
    var staticPath = $('#staticServer').val();
    p.html(that.val());

    form.ajaxSubmit({
        dataType: 'json',
        type: 'POST',
        success: function (responseBody) {
            var size = '';
            var test = $('<img>');

            //所有功能写进回调:
            test.load(function () {
                size = test.width() + '*' + test.height();
                $('.pgt-category-img').attr('src', staticPath + responseBody.imagePath)
                    .siblings('p').html(size);
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

$('#pgtColorInput').keydown(categoryColor);

categoryColor();

function categoryColor() {
    $('#pgtColor').css({
        background: $('#pgtColorInput').val()
    })
}