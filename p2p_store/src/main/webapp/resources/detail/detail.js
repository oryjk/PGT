/**
 * Created by zhangxiaodong on 16-2-26.
 */

$(document).ready(function () {


    $(".detail-content>ul>li").click(function () {

        $(".detail-content>ul>li").each(function () {
            $(this).removeAttr("class");
        });

        $(this).attr("class", "tab-choose");

        var tabindex = $(this).index();

        $(".content-box > div").each(function () {

            $(this).removeClass("content-choose");

            if (tabindex == $(this).index()) {
                $(this).addClass("content-choose");
            }

        });


    });

    var setQuantity = function(obj, num) {
            $(obj).parent('.each-item').find('.quantity').val(num);
    }
    //add product quantity
    $(".item-buy-plus").click(function () {

        //get limit quantity
        var limitQuantity = $(this).parent().next().text();

        //get buy quantity
        var num = $(this).parent().prev().text();

        num++;
        if (num > limitQuantity) {
            alert("此商品购买的数量不能超过" + limitQuantity + "件");
            return;
        }
        //set buy quantity
        $(this).parent().prev().text(num);
        setQuantity(this,num);
    });


    //reduce product quantity


    $(".item-buy-minus").click(function () {

        var num = $(this).parent().prev().text();

        num--;

        if (num == 0) {
            return;
        }

        $(this).parent().prev().text(num);
        setQuantity(this,num);

    });
    $('#item-buy-now').on('click', function (event) {
        event.preventDefault();
        $(this).parent('.col-content').find('.addToCart').submit();
    })


});