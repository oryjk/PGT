/**
 * Created by supersoup on 16/1/5.
 */
$('#newStep').click(function(event) {
    var flag = true;
    $.each($('[type="text"]'), function(i, data) {
        if ($(data).val() == '') {
            flag = false;
        }
    });
    if (flag == false) {
        $('#alertRow').show();
        $('#alertText').html('请把商品信息填写完整');
        event.preventDefault();
    }
});