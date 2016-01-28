/**
 * Created by carl on 15/10/30.
 */
require.config({
    paths: {
        jquery: '../core/js/jquery.min',
        component: '../core/js/module/component',
        product: '../core/js/module/product'
    }
});

require(['jquery', 'component', 'product'], function($, Cpn, Prd) {

    $(document).ready(function() {

        //调整banner的高度,让他适应menu
        //$('#bannerBox').css({
        //    //height: $('#menuList').height() - 10 + 'px'
        //    height: $('#menuList').height()  + 'px'
        //});
        //right全屏幕高
        var height = $('body').height();
        console.log(height);
        $(".side-bar").css("height", height);

        //轮播图
        Cpn.slider({
            next: 1,
            imgBox: $('#banner'),
            navBox: $('#bannerNav'),
            time: 600
        });

        //固定顶部
        Cpn.fixedHead({
            head: $('#fixedHead'),
            height: 800,
            time: 200
        });

        //right-box
        $(function(){

            var flag = 0;
            var status = "";
            $(".right1").click(function(){
                var m_status = $(this).attr("title");
                if(status != m_status || flag == 0){
                    $("#side-bar").animate({right:"0"});
                    var url = $(this).attr("path");
                    status = $(this).attr("title");
                    $("#side-bar").show();
                    $("#right-menu").load(url);
                    flag = 1;
                }else if(flag == 1 && status == m_status){
                    $("#side-bar").animate({right:"-300px"});
                    flag = 0;
                }
            });
        });


        //显示购物车数量
        Prd.getOrderItemCount($('#asideCartCount, #fixedCartCount, #cartCount'));

        //弹出框
        Cpn.pop({
            popUp: $('#classifyPop'),
            close: $('#closeClassifyPop')
        });
        
        //content部分点击事件委托
        $('#content').click(function(event) {
            var target = $(event.target);
            var targetType = target.attr('data-btn');

            if (targetType == 'addCart' || targetType == 'addEnjoy') {
                var productId = target.attr('data-value');
                var productMessage = $(target).parent().parent().children().filter('.product-message');

                event.preventDefault();
                event.stopPropagation();

                if (targetType == 'addCart') {
                    //加入购物车
                    Prd.addItemToOrder(productId, productMessage, $('#asideCartCount, #fixedCartCount, #cartCount'));
                    $('#asideCartCount').parent().css({
                        'transform': 'scale(4)',
                        'opacity': '.5'
                    });
                    setTimeout(function() {
                        $('#asideCartCount').parent().css({
                            'transform': 'scale(1)',
                            'opacity': '1'
                        });
                    }, 500);
                } else if (targetType == 'addEnjoy') {
                    //添加收藏
                    Prd.addItemToFavourite(productId, productMessage);
                }
            }
        });

        $(document).on('click', '.each-classify', function(event) {
        event.preventDefault();
        $('#classifyPopCentent').load('classify.html', function() {
            $('#classifyPop').fadeIn(300);
        })
    })

});
});