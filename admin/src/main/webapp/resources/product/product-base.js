/**
 * Created by supersoup on 16/1/5.
 */
$('#newStep').click(function(event) {
    event.preventDefault();

    var flag = true;
    var rxp = /^\d+\.\d{2}$/g;
    var rxp2 = /^\d+\.\d{2}$/g;

    $.each($('.pgt-requisite-name'), function(i, data) {
        var $this = $(data);
        var text =  $this.parents('.form-group').find('.pgt-error');
        if ($this.val() == '') {
            flag = false;
            text.html('必填项');
        } else {
            text.html('');
        }
    });
    $.each($('.pgt-requisite-price1'), function(i, data) {
        var $this = $(data);

        var text = $this.parents('.form-group').find('.pgt-error');
        if (!rxp.test($this.val())) {
            flag = false;

            text.html('格式不正确');
        } else {
            text.html('');
        }
    });

    $.each($('.pgt-requisite-price2'), function(i, data) {
        var $this = $(data);

        var text = $this.parents('.form-group').find('.pgt-error');
        if (!rxp2.test($this.val())) {
            flag = false;

            text.html('格式不正确');
        } else {
            text.html('');
        }
    });
    if (flag == true) {
        $('#productBase').get(0).submit();
    }
});

$('.pgt-requisite-name, .pgt-requisite-price1, .pgt-requisite-price2').keypress(function() {
    var $this = $(this);
    if ($this.val() != '') {
        $this.parents('.form-group').find('.pgt-error').html('');
    }
});