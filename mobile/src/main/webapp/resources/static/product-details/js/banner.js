/**
 * Created by zhoujialiang on 16/1/19.
 */
/*
 * touchslide 1.1
 * Copyright (c) 2014 BowenLuo http://www.luobo.com/
 * Date: 2014-06-08
 * Example:$('.WSCSlideWrapper').touchslide({timecontrol:3000,animatetime:300,direction:'left',navshow:true,canvassuport:true});
 * Update:增加对IE9+等非Safari内核浏览器的鼠标拖动图片功能
 */
(function ($) {
    $.fn.touchslide = function (options) {
        var defaults = {
            timecontrol: 3000,//图片停留时间
            animatetime: 300, //图片滑动所需时间
            direction: 'left', //轮播方向
            navshow: true,//是否显示图片导航栏
            canvassuport: true//图片导航栏是否开启cavas绘制圆
        }
        var options = $.extend(defaults, options);
        var timecontrol = options.timecontrol || 3000;
        var animatetime = options.animatetime || 300;
        var direction = options.direction || 'left';
        var navshow = options.navshow;
        this.each(function () {
            var slideWrapper = $(this);
            var slideImgWrapper = slideWrapper.children('div:first');
            var slideAs = slideImgWrapper.children('a');
            var slideImgs = slideAs.children('img');
            var imgcount = slideImgs.length;
            var imgcountrealy = slideImgs.length;
            var navimgs;
            var circlewrapper;
            var circles;
            var canvassuport = true;
            var circler = 0;
            var navimgsbuttom = 0;
            if (imgcount > 1) {
                $((slideImgWrapper.html().split("/a>")[0] + "/a>" + slideImgWrapper.html().split("/a>")[1] + "/a>")).insertAfter(slideAs.last());
                if (navshow) {
                    $("<div class='navimgs'></div>").insertAfter(slideImgWrapper);
                    navimgs = slideWrapper.children('div:last');
                    navimgs.append("<div class='circlewrapper'></div>");
                    circlewrapper = navimgs.children('div:first');
                    for (var i = 0; i < imgcountrealy; i++) {
                        circlewrapper.append("<canvas class='circle' width='15' height='15'></canvas>");
                    }
                    circles = circlewrapper.children('canvas');
                    var myCanvas = circles[1];
                    if (!myCanvas.getContext) {
                        canvassuport = false;
                    } else {
                        canvassuport = options.canvassuport;
                    }
                }
            }
            slideAs = slideImgWrapper.children('a');
            slideImgs = slideAs.children('img');
            imgcount = slideImgs.length;
            var slideWrapperWidth = slideWrapper.width();
            var slideWrapperHeight = slideWrapper.height();
            slideWrapper.css({"overflow": "hidden", "width": slideWrapperWidth, "height": slideWrapperHeight});
            slideImgWrapper.css({
                'position': "absolute",
                "z-index": "1",
                "overflow": "hidden",
                "width": slideWrapperWidth * imgcount,
                "height": slideWrapperHeight
            });
            slideAs.css({
                'position': "relative",
                "overflow": "hidden",
                "float": "left",
                "width": slideWrapperWidth,
                "height": slideWrapperHeight
            });
            slideImgs.css({
                'position': "relative",
                "z-index": "1",
                "width": slideWrapperWidth,
                "height": slideWrapperHeight
            });
            if (typeof(navimgs) !== 'undefined') {
                if (navshow) {
                    if (slideWrapperWidth * 0.03 > 15) {
                        circler = 15;
                    } else {
                        circler = slideWrapperWidth * 0.03;
                    }
                    if (slideWrapperHeight > 360) {
                        navimgsbuttom = 8;
                    } else if (slideWrapperHeight > 240) {
                        navimgsbuttom = 6;
                    } else if (slideWrapperHeight > 120) {
                        navimgsbuttom = 4;
                    } else {
                        navimgsbuttom = 2;
                    }
                    navimgs.css({
                        "position": "absolute",
                        "z-index": "3",
                        "overflow": "hidden",
                        "display": "block",
                        "width": slideWrapperWidth,
                        "height": circler,
                        "bottom": navimgsbuttom
                    });
                    circlewrapper.css({
                        "position": "relative",
                        "overflow": "hidden",
                        "width": (slideWrapperWidth * 0.05 * imgcountrealy),
                        "height": circler,
                        "margin": "auto"
                    });
                    circles.css({
                        'position': "relative",
                        "float": "left",
                        "max-width": 15,
                        "max-height": 15,
                        "margin-left": slideWrapperWidth * 0.01
                    });
                    circles.attr("width", circler);
                    circles.attr("height", circler);
                    canvacircle(0);
                    for (var i = 0; i < circles.length; i++) {
                        navclick(i);
                    }
                } else {
                    navimgs.css({"display": "none"});
                }
            }
            if (imgcount < 4) {
                return;
            }
            slideImgWrapper.css({'left': -slideWrapperWidth});
            var st;
            var sts;
            sts = setTimeout(function () {
                timedCount();
            }, timecontrol);

            slideWrapper.hover(function () {
                    stopAll();
                },
                function () {
                    sts = setTimeout(function () {
                        timedCount();
                    }, timecontrol);
                });
            mouseDrag(slideWrapper);
            touchDrag(slideWrapper);

            function timedCount() {
                if (direction == 'left') {
                    turnleft();
                }
                if (direction == 'right') {
                    turnright();
                }
                clearTimeout(st);
                st = setTimeout(function () {
                    timedCount();
                }, timecontrol);
            }

            function stopCount() {
                if (st != "") {
                    clearTimeout(st);
                }
            }

            function stopAll() {
                stopCount();
                clearTimeout(sts);
                slideImgWrapper.stop(true);
            }

            function navclick(navnum) {
                circlewrapper.children('canvas:eq(' + navnum + ')').click(function (e) {
                    scideimgskip(navnum + 1);
                })
            }

            function scideimgskip(imgnum) {
                stopAll();
                turnleftwidth = imgnum * slideWrapperWidth;
                slideImgWrapper.stop(true).animate({left: -turnleftwidth}, animatetime, function () {
                    var imgnum = turnleftwidth / slideWrapperWidth - 1;
                    if (imgnum == 0) {
                        imgnum = imgcountrealy;
                    }
                    if ((imgnum - imgcountrealy) == 0) {
                        imgnum = 0;
                    }
                    canvacircle(imgnum);
                });
            }

            function canvacircle(canvanum) {
                circles.attr("width", circler);
                circles.attr("height", circler);
                for (var i = 0; i < circles.length; i++) {
                    if (canvassuport) {
                        var navCanvas = circles[i];
                        var cxt = navCanvas.getContext("2d");
                        if (i == canvanum) {
                            cxt.fillStyle = "#0182D7";
                        } else {
                            cxt.fillStyle = "#ddd";
                        }
                        cxt.arc(circler * 0.5, circler * 0.5, circler * 0.5, 0, Math.PI * 2, true);
                        cxt.closePath();
                        cxt.fill();
                    } else {
                        circles.css("background", "#ddd");
                        circlewrapper.children('canvas:eq(' + canvanum + ')').css("background", "#0182D7");
                    }

                }
            }

            var turnleftwidth = slideWrapperWidth;

            function turnleft() {
                turnleftwidth = turnleftwidth + slideWrapperWidth;
                if (turnleftwidth > (imgcount - 2) * slideWrapperWidth) {
                    slideImgWrapper.css({'left': 0});
                    turnleftwidth = slideWrapperWidth;
                }
                slideImgWrapper.stop(true).animate({left: -turnleftwidth}, animatetime, function () {
                    var imgnum = turnleftwidth / slideWrapperWidth - 1;
                    if (imgnum == 0) {
                        imgnum = imgcountrealy;
                    }
                    if ((imgnum - imgcountrealy) == 0) {
                        imgnum = 0;
                    }
                    canvacircle(imgnum);
                });
            }

            function turnright() {
                turnleftwidth = turnleftwidth - slideWrapperWidth;
                if (turnleftwidth == 0) {
                    slideImgWrapper.css({'left': -slideWrapperWidth * (imgcount - 1)});
                    turnleftwidth = slideWrapperWidth * (imgcount - 2);
                }
                slideImgWrapper.stop(true).animate({left: -turnleftwidth}, animatetime, function () {
                    var imgnum = turnleftwidth / slideWrapperWidth - 1;
                    if (imgnum == 0) {
                        imgnum = imgcountrealy;
                    }
                    if ((imgnum - imgcountrealy) == 0) {
                        imgnum = 0;
                    }
                    canvacircle(imgnum);
                });
            }

            var distanceX = 0;

            function toLeft() {
                if (turnleftwidth > (imgcount - 3) * slideWrapperWidth) {
                    slideImgWrapper.css({"left": distanceX});
                    turnleftwidth = 0;
                }
                turnleft();
                sts = setTimeout(function () {
                    timedCount();
                }, timecontrol);
            }

            function toRight() {
                if (turnleftwidth == slideWrapperWidth) {
                    slideImgWrapper.css({'left': -slideWrapperWidth * (imgcount - 1) + distanceX});
                    turnleftwidth = slideWrapperWidth * (imgcount - 1);
                }
                turnright();
                sts = setTimeout(function () {
                    timedCount();
                }, timecontrol);
            }

            function mouseDrag($element) {
                var eventEle = $element;
                var stx = etx = curX = 0;
                var MAction = false;
                var ahrefs = [];
                for (var i = 0; i < slideAs.length; i++) {
                    if (typeof(slideImgWrapper.children('a:eq(' + i + ')').attr('href')) !== 'undefined') {
                        ahrefs.push(slideImgWrapper.children('a:eq(' + i + ')').attr('href'));
                    }
                }
                eventEle.mouseover(function () {
                    for (var i = 0; i < slideAs.length; i++) {
                        if (typeof(slideImgWrapper.children('a:eq(' + i + ')').attr('href')) !== 'undefined') {
                            slideImgWrapper.children('a:eq(' + i + ')').attr('href', ahrefs[i]);
                        }
                    }
                });
                eventEle.mousemove(function (event) {
                    if (MAction) {
                        var changeX = event.pageX - stx;
                        slideImgWrapper.css({"left": -turnleftwidth + changeX});
                        distanceX = changeX;
                    }
                    event.preventDefault();
                }).mousedown(function (event) {
                    stopAll();
                    MAction = true;
                    stx = event.pageX;
                    event.preventDefault();
                });
                eventEle.mouseup(function (event) {
                    etx = event.pageX;
                    curX = etx - stx;
                    MAction = false;
                    if (curX > 5) {
                        slideAs.attr("href", "javascript:void(0)");
                        toRight();
                    }
                    if (curX < -5) {
                        slideAs.attr("href", "javascript:void(0)");
                        toLeft();
                    }
                    event.preventDefault();
                });
            }

            function touchDrag($element) {
                var gundongX = 0;
                var gundongY = 0;
                var moveEle = $element;
                var stx = sty = etx = ety = curX = curY = 0;

                var ImgWidth_arr = [];
                for (var i = 0; i < imgcount; i++) {
                    ImgWidth_arr.push(i * slideWrapperWidth);
                }

                moveEle.on("touchstart", function (event) { //touchstart
                    stopAll();
                    var event = event.originalEvent;
                    gundongX = 0;
                    gundongY = 0;
                    //alert(etx);
                    // 手指位置
                    stx = event.touches[0].pageX;
                    sty = event.touches[0].pageY;
                });
                moveEle.on("touchmove", function (event) {
                    var event = event.originalEvent;
                    // 防止拖动页面
                    event.preventDefault();
                    // 手指位置 减去 元素当前位置 就是 要移动的距离
                    gundongX = event.touches[0].pageX - stx;
                    gundongY = event.touches[0].pageY - sty;
                    // 目标位置 就是 要移动的距离 加上 元素当前位置
                    slideImgWrapper.css({"left": -turnleftwidth + gundongX});
                    distanceX = gundongX;
                });
                moveEle.on("touchend", function (event) {
                    if (Math.abs(gundongX) > 5) {
                        event.preventDefault();
                        if (gundongX > 0) {
                            // 右滑
                            toRight();
                        } else {
                            // 左滑
                            toLeft();
                        }
                    }
                });
            }
        });
    };
})(jQuery);

<!-- Generated by RunJS (Tue Jan 19 16:34:22 CST 2016) 2ms -->