/**
 * Created by supersoup on 15/12/27.
 */
//$(document).on('click', 'a[data-pgt-dropdown="status-option"]', function() {
//    var that = $(this);
//    var text = that.text();
//    var value= that.attr('data-pgt-value');
//    var view = that.parents('ul').siblings('a');
//    view.removeClass('blue yellow red').html(text + ' <i class="fa fa-angle-down"></i>').attr('data-pgt-value', text);
//
//    if (value == 0) {
//        view.addClass('default');
//    } else if (value == 1) {
//        view.addClass('blue');
//    }
//});


$("#term").change(function(){
    $("#term_hidden").val($(this).val())
    $('#submitBtn').click();
});

/*
$('#categorySelect').change(function() {
    var $this = $(this);
    window.location = '/category/getSubCategories/' + $this.val();
});
*/
/*
$('#categorySelect').change(function() {

    var categoryId= $(this).val();

    if(categoryId=="all"){
        $("#type").val("ROOT");
        $("#category").val("");
    }else{
        $("#type").val("HIERARCHY");
        $("#category").val(categoryId);
    }

    $("#pageSubmit").submit();
});
*/

$('#tenderCategorySelect').change(function() {
    var $this = $(this);
    window.location = '/tenderCategory/getSubCategories/' + $this.val();
});

$('.tenderIndex').click(function(){
         var url=$(this).attr('data-url');
         $.ajax({
             type: 'GET',
             url: url,
        success: function (param) {
          var m=param.success;
            if(m==1){
                 alert('同步成功!');
            }else {
                 alert('同步失败！');
            }
        }
    });
});

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
                data: {
                    categoryType: $this.attr('data-pgt-type')
                },
                success: function (param) {
                    if (param.success == true) {
                        location.reload();
                    }
                }
            })
        });
});


$('#searchBtn').click(function() {


    var categoryId= $("#categorySelect").val();

    if(categoryId=="all"){
        $("#type").val("ROOT");
        $("#category").val("");
    }else{
        $("#type").val("HIERARCHY");
        $("#category").val(categoryId);
    }
    var term = $("#name").val();
    $("#term").val(term);

    $("#pageSubmit").submit();
});

$('#pagination').on('click', 'a', function(event) {
    event.preventDefault();
    var pageIndex="";
    var href =$(this).attr("href");
    var start=href.lastIndexOf("=");
    pageIndex=href.substring(start+1);
    $("#currentIndex").val(pageIndex);
    $("#pageSubmit").submit();
});
