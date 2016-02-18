/**
 * Created by supersoup on 15/11/25.
 */
define(function() {
    /**
     * slider 轮播图
     * next:下一个播放 1,
     * imgBox:a标签的父元素 $('#banner'),
     * navBox:导航的父元素 $('#bannerNav'),
     * time:切换的时间 600
     * */
    var slider = function(sliderObj) {
        var sliderIng = setInterval(slider, 5000);
        $('#bannerBox').mouseenter(function() {
            clearInterval(sliderIng);
        }).mouseleave(function() {
            sliderIng = setInterval(slider, 5000);
        });

        sliderObj.navBox.mouseover(function(event) {
            if (event.target.nodeName == 'LI' || event.target.nodeName == 'li') {
                sliderObj.next = event.target.innerHTML;
                slider();
            }
        });

        function slider() {
            sliderObj.imgBox.children().fadeOut(sliderObj.time).eq(sliderObj.next).fadeIn(slider.time);
            sliderObj.navBox.children().removeClass('banner-nav-now').eq(sliderObj.next).addClass('banner-nav-now');
            sliderObj.next ++;
            if (sliderObj.next >= sliderObj.navBox.children().size()) {
                sliderObj.next = 0;
            }
        }
    };

    /**
     * tab 标签页
     * tabObj对象的属性必须是:
     * tabArea: 一个jquery对象,表示被切换的区域 $('#info,#question,#talking'),
     * tabLi:一个jquery对象,表示将要显示改变的区域 $('#tab li'),
     * tabTarget:一个jqeury对象,表示点击的区域 $('#tab h2'),
     * time表示切换时动画的事件,缺省时立即出现
     * 需要有.choose 的样式
     * */
    var tab = function (tabObj, time) {
        tabObj.tabTarget.click(function(event) {
            tabObj.tabArea.hide();
            tabObj.tabLi.removeClass('choose');
            $(event.target).parent().addClass('choose');
            tabObj.tabArea.eq($(event.target).attr('data-tab')).show(time);
        });
    };

    /**
     * pic 图片放大镜
     * picObj对象的属性必须是:
     * small: 小图的父元素 $('#smallPic'),
     * middle:中图 $('#middlePic'),
     * big: 大图 $('#bigPic'),
     * glass:放大镜元素 $('#glass'),
     * src: '',
     * middleX: $('#middlePic').offset().left,
     * middleY: $('#middlePic').offset().top,
     * x: 95,
     * y: 95
     * */
    var pic = function(picObj) {
        picObj.small.mouseover(function(event) {
            picObj.src = $(event.target).attr('src');
            if (picObj.src) {
                picObj.middle.attr('src', picObj.src);
                picObj.big.css({
                    background: 'url('+picObj.src + ')'
                })
            }
        });
        picObj.middle.parent().mouseover(function() {
            picObj.big.show();
            picObj.glass.show();
        }).mouseout(function() {
            picObj.big.hide();
            picObj.glass.hide();
        });
        picObj.middle.parent().get(0).onmousemove = function(event) {
            picObj.x = event.pageX - picObj.middleX;
            picObj.y = event.pageY - picObj.middleY;
            if(picObj.x < 95) {
                picObj.x = 95;
            } else if (picObj.x > 285) {
                picObj.x = 285;
            }
            if(picObj.y < 95) {
                picObj.y = 95;
            } else if (picObj.y > 285) {
                picObj.y = 285;
            }
            picObj.glass.css({
                left: picObj.x - 95 + 'px',
                top: picObj.y - 95 + 'px'
            });
            picObj.big.css({
                backgroundPosition: '-' + (picObj.x - 95)*2 + 'px -' + (picObj.y-95)*2 + 'px'
            })
        };
    };

    /**
     * fixedHead 固定顶部
     * fixedObj的属性必须是:
     * head 目标对象
     * height 零界点
     * time 出现时间
     * */
    var fixedHead = function(fixedObj) {
        var doc = $(document);
        var height = 0;
        var fix = fixedObj.head;
        var flag = 1;
        $("#right-menu").mouseover(function(){
            flag = 0;
            //$("body").css("overflow",'hidden')
        })
        $("#right-menu").mouseout(function(){
            flag = 1;
            //$("body").css("overflow",'scroll')
        })

        $(window).scroll(function(event) {

            height = doc.scrollTop();

            if (flag === 0) {
                //console.log(height)
            }


            if (height > fixedObj.height) {
                fix.fadeIn(fixedObj.time);
            } else {
                fix.fadeOut(fixedObj.time);
            }
        })
    };

    /**
     * rowList 横向商品列表
     * rowListObj 的属性必须是:
     * list: 列表元素 $('#rowList')
     * left: 点击向左 $('#moveLeft')
     * right 点击向右 $('#moveRight')
     */
    var rowList = function(rowListObj) {
        var width = rowListObj.list.children().outerWidth(true);
        var count = rowListObj.list.children().size();
        var left = 0;

        showTest(left, count, width);

        rowListObj.left.click(function(event) {
            event.preventDefault();
            if (left > - ((count - 5) * width)) {
                left -= width;
                rowListObj.list.animate({
                    left: left + 'px'
                }, 100);
            }
            showTest(left, count, width);
        });
        rowListObj.right.click(function(event) {
            event.preventDefault();
            //移动到最左边时有可能是一个极小的小数left: -5.6843418860808e-14px,所以;
            if (left < -1) {
                left += width;
                rowListObj.list.animate({
                    left: left + 'px'
                }, 100);
            }
            showTest(left, count, width);
        });

        function showTest(left, count, width) {
            //向右点击按钮的显示和消失
            if (left > - ((count - 5) * width)) {
                rowListObj.left.show();
            } else {
                rowListObj.left.hide();
            }
            //向左点击按钮的显示和消失
            if (left < -1) {
                rowListObj.right.show();
            } else {
                rowListObj.right.hide();
            }
        }
    };

    /**
     * rowList 纵向商品列表
     * rowListObj 的属性必须是:
     * list: 列表元素 $('#verticalList')
     * top: 点击向左 $('#moveTop')
     * bottom 点击向右 $('#moveBottom')
     */
    var verticalList = function(verticalListObj) {
        var top = 0;
        var verticalList = verticalListObj.list;
        var ulHeight = verticalList.outerHeight(true);
        var moveBottom = verticalListObj.bottom;
        var moveTop = verticalListObj.top;
        var innerHeight = verticalList.parent().outerHeight(true);

        testBottomShow(top, moveBottom);
        testTopShow(top, moveTop);

        moveBottom.click(function(event) {
            var that = $(this);
            event.preventDefault();

            if (top + ulHeight >= innerHeight) {
                top = top - 246;
                verticalList.animate({
                    top: top + 'px'
                },100);
            }

            testBottomShow(top, moveBottom);
            testTopShow(top, moveTop);


        });
        moveTop.click(function(event) {
            var that = $(this);
            event.preventDefault();

            if (top < 0) {
                top = top + 246;
                verticalList.animate({
                    top: top + 'px'
                },100);
            }

            testBottomShow(top, moveBottom);
            testTopShow(top, moveTop);
        });

        function testBottomShow(top, that) {
            if (top + ulHeight >= innerHeight) {
                that.removeClass('move-bottom-disable');
            } else {
                that.addClass('move-bottom-disable');
            }
        }

        function testTopShow(top, that) {
            if (top < 0) {
                that.removeClass('move-bottom-disable');
            } else {
                that.addClass('move-bottom-disable');
            }
        }
    };

    /**
     * pop 弹出框
     * popUp: 弹出框jq
     * close: 关闭jq
     */
    var pop = function(popObj) {
        popObj.close.click(function() {
            popObj.popUp.fadeOut(300);
        });
        popObj.popUp.click(function(event) {
            if (event.target == popObj.popUp.get(0)) {
                popObj.popUp.fadeOut(300);
            }
        })
    };

    /**
     * foldToggle 折叠
     * munu: 折叠开关
     */
    var foldToggle = function(menu) {
        menu.click(function(event) {
            var that = $(this);

            if (that.siblings('ul').size() > 0) {
                event.preventDefault();
                that.siblings('ul').slideToggle(200);
            }
        })
    };

    /**
     * page 分页
     *  productLIst 被填充的列表合资 $('.product-list');
     *  previousPage 上一页按钮 $('#previousPage');
     *  nextPage 下一页按钮 $('#nextPage');
     *  pageCount 总页数 $('#pageCount');
     *  pageWhich 页数text框 $('#pageWhich');
     *  pageSub 前往按钮 $('#pageSub');
     *  pages 页码盒子 $('#pages');
     *  baseUrl : Prd.baseUrl;
     *  pageObj 分页信息 {
     *     currentIndex: 0,
     *     capacity: 5
     * };
     */
    var page = function(productLIst, previousPage, nextPage, pageCount, pageWhich, pageSub, pages, url, pageObj, loadBolean, callbackFunction) {
        var maxIndex = -1;

        //载入时
        if (loadBolean) {
            getPage(pageObj);
        }

        //前页后页
        previousPage.click(function(event) {
            event.preventDefault();

            pageObj.currentIndex --;
            getPage(pageObj);
        });
        nextPage.click(function(event) {
            event.preventDefault();

            pageObj.currentIndex ++;
            getPage(pageObj);
        });

        //填写页码并确定
        pageSub.click(function(event) {
            event.preventDefault();

            var page = pageWhich.val() - 1;
            if (page < 0) {
                page = 0;
            }
            if (page > maxIndex) {
                page = maxIndex;
            }
            pageObj.currentIndex = page;
            getPage(pageObj);
        });

        //选择页码
        pages.delegate('a', 'click', function(event) {
            event.preventDefault();

            var that = $(event.target);
            pageObj.currentIndex = that.html() - 1;
            getPage(pageObj);
        });

        function getPage(pageObj) {
            $.ajax({
                type: 'get',
                url: url,
                data: {
                    currentIndex: pageObj.currentIndex
                },
                success: function(param) {
                    if (param.success == 1) {
                        pageObj.currentIndex = param.data.currentIndex;
                        maxIndex = param.data.maxIndex;

                        //前页后页选择性隐藏
                        if (pageObj.currentIndex <= 0) {
                            previousPage.hide();
                        } else {
                            previousPage.show();
                        }
                        if (pageObj.currentIndex >= maxIndex) {
                            nextPage.hide();
                        } else {
                            nextPage.show();
                        }

                        //总页数显示
                        pageCount.html(maxIndex + 1);

                        //创造页码
                        var pageStr = '';
                        for (var i = 1; i <= maxIndex + 1; i ++) {
                            pageStr += '<li><a href="#">' + i + '</a></li>';
                        }
                        pages.html(pageStr);
                        pages.children().children('a').eq(pageObj.currentIndex).addClass('page-focus');

                        //渲染数据
                        callbackFunction(param, productLIst);
                    }
                }
            })
        }
    };

    /**
     * select 选择框
     * selectView: 点击的a标签
     * optionView: 选择的a标签
     */
    var select = function(selectView, optionView) {
        selectView.click(function(event) {
            event.preventDefault();
            var that = $(this);

            that.siblings('ul').slideToggle(200);
        });
        optionView.click(function(event) {
            event.preventDefault();
            var that = $(this);
            var ul = that.parents('ul');
            ul.siblings('.select-value')
                .val(that.attr('data-value'));
            ul.slideToggle(200).
                siblings('.select-view')
                .children('.selected')
                .html(that.html());
        });
    };

    /**
     * select2 三级联动所用选择框
     * selectView: 点击的a标签
     * [fnc]: 回调函数
     */
    var select2 = function(selectView, fnc) {
        selectView.click(function(event) {
            event.preventDefault();
            var that = $(this);

            that.siblings('ul').slideToggle(200);
        });
        selectView.siblings('.options').delegate('a', 'click', function(event) {
            event.preventDefault();

            var that = $(event.target);
            var ul = that.parents('ul');
            var id = that.attr('data-value');
            var name = that.html();

            ul.siblings('.select-value')
                .val(name);
            ul.slideToggle(200).
                siblings('.select-view')
                .children('.selected')
                .html(that.html());

            //回调函数
            if (fnc) {
                fnc(id);
            }
        });
    };

    /**
     * checkbox 多选框
     * selectView: 点击的a标签
     * optionView: 选择的a标签
     */
    var checkbox = function(obj) {
        //页面载入时先检查所有checkbox框是否被选中
        checkboxIcon(obj);

        obj.click(function() {
            var that = $(this);
            var input = that.children('input');
            if (input.val() == 0) {
                input.val(1);
                checkboxIcon(that);
            } else if (input.val() == 1) {
                input.val(0);
                checkboxIcon(that);
            }
        });

        function checkboxIcon(that) {
            if (that.children('input').val() == 0) {
                that.children('i').hide();
            } else if (that.children('input').val() == 1) {
                that.children('i').show();
            }
        }
    };

    return {
        slider: slider,
        tab: tab,
        pic: pic,
        fixedHead: fixedHead,
        rowList: rowList,
        verticalList: verticalList,
        foldToggle: foldToggle,
        pop: pop,
        page: page,
        select: select,
        select2: select2,
        checkbox: checkbox
    }
});