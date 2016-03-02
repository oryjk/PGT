/**
 * Created by supersoup on 15/12/25.
 */
//$(document).on('click', 'a[data-pgt-dropdown="status-option"]', function() {
//    var that = $(this);
//    var text = that.text();
//    var value= that.attr('data-pgt-value');
//    var view = that.parents('ul').siblings('a');
//    view.removeClass('blue yellow red').html(text + ' <i class="fa fa-angle-down"></i>').attr('data-pgt-value', text);
//
//    if (value == 0) {
//        view.addClass('blue');
//    } else if (value == 1) {
//        view.addClass('yellow');
//    } else if (value == -1) {
//        view.addClass('red');
//    }
//});
$(document).on('click', '[data-pgt-btn="modify"], [data-pgt-btn="create"]', function () {
    var $this = $(this);
    window.location = $this.attr('data-url');
});

$(document).on('click', '[data-pgt-btn="delete"]', function () {
    var $this = $(this);
    $('#confirmModal').modal();
    $('#comfirmBtn')
        .off()
        .click(function () {
            $.ajax({
                type: 'get',
                url: $this.attr('data-url'),
                success: function (param) {
                    if (param.success == true) {
                        location.reload();
                    }
                }
            })
        });
});


$("#updateIndex").click(function(){

    var list = new Array();
    var i = 0;

    $("input[type='checkbox']").each(function(){

       if($(this).is(":checked")) {

           if($(this).attr("id")!='checkAll') {

               list[i] = $(this).parent().next().text();
               i++;
           }

       }
   });


    $.ajax({
        type: "POST",
        url: "/product/update/index",
        data: {
            products: list.toString(),
        },
        success: function(status){

          if(status.status=='success'){
              alert("同步成功!");
          }

        }
    });


});

$('#checkAll').change(function () {
    var that = $(this);
    var allCheck = $('#list input[type="checkbox"]');
    if (that.prop('checked')) {
        allCheck.prop('checked', true);
    } else {
        allCheck.prop('checked', false);
    }
});

$('#mainCategory').change(function () {
    var $this = $(this);
    var viceCategory = $('#viceCategory');

    $.ajax({
        type: 'get',
        url: '/category/getSubCategoriesAjax/' + $this.val(),
        success: function (param) {
            var str = '<option value="">全部</option>>';

            $.each(param.categories, function (i, data) {
                str += '<option value="' + data.id + '">' + data.name + '</option>';
            });

            viceCategory.html(str);
        }
    });
});

//$('#viceCategory').change(function () {
  //  var $this = $(this);
    //window.location = '/product/productList?categoryId=' + $this.val() + '&currentIndex=0';
//});

$('.pgt-goto-page-btn').on('click', function () {
    var oValue = $('.pgt-goto-page .input-inline').val();
    if(oValue>0){
        $('.pgt-goto-page .input-inline').val(oValue-1);
    }
});

$(".is-Hot>li>a").click(function(){

    var productId= $(this).attr("data-value");
    var hot= $(this).attr("data-pgt-value");

    var a=$(this).parent().parent().prev();

    $.ajax({
        type: "GET",
        url: "/product/updateIsHot",
        data: {
            productId: productId,
            isHot: hot
        },
        success: function(param){

            if(param.status=='success'){
                if(hot==1){
                    a.text('是');
                }else{
                    a.text('否');
                }
            }
        }
    });

});


$('#pagination').on('click', 'a', function(event) {
    event.preventDefault();
    var pageIndex=$(this).text();
    if(pageIndex=='首页'){
        pageIndex=0;
    }
    if(pageIndex=='末页'){
        pageIndex=$("#maxIndex").val();
    }
    $("#currentIndex").val(pageIndex);
    $("#pageSubmit").submit();
});



$('#searchBtn').click(function() {
    var str = '?';
    var categoryId=$("#viceCategory").val();
    if (categoryId!=null){
        if(categoryId!=null){
            str+='categoryId=' + categoryId+"&";
        }
    }
    str += 'term=' + $('#term').val();
    if($("#isHot").is(':checked')){
        str+='&isHot=1';
    }
    var dateSort=$("#dateSort").val();
    if(dateSort!=null){
        str+='&sortBeans[0].propertyName=salePrice&sortBeans[0].sort='+dateSort;
    }
    var priceSort=$("#priceSort").val();
    if(priceSort!=null){
        str+='&sortBeans[1].propertyName=creationDate&sortBeans[1].sort='+priceSort;
    }

    window.location = str;
});