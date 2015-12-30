/**
 * Created by supersoup on 15/12/28.
 */
$('.pgt-file-btn').change(function () {
    var that = $(this);
    var form = that.parent();
    var p = form.siblings('p');
    p.html(that.val());

    form.ajaxSubmit({
        url: $('#contextPath').val() + '/upload/image',
        dataType: 'json',
        type: 'POST',
        success: function (responseBody) {
            $(this).parent('.form-group').children('img').attr('src', responseBody.imagePath)
        }
    });

});

