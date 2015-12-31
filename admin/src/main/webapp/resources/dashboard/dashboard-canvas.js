/**
 * Created by supersoup on 15/12/30.
 */
var EcommerceIndex = function () {

    function showTooltip1(x, y, labelX, labelY) {
        $('<div id="tooltip" class="chart-tooltip">' +'¥'+ (labelY.toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, '$1,')) + '<\/div>').css({
            position: 'absolute',
            display: 'none',
            top: y - 40,
            left: x - 60,
            border: '0px solid #ccc',
            padding: '2px 6px',
            'background-color': '#fff'
        }).appendTo("body").fadeIn(200);
    }

    function showTooltip2(x, y, labelX, labelY) {
        $('<div id="tooltip" class="chart-tooltip">' + (labelY.toFixed(0).replace(/(\d)(?=(\d{3})+\.)/g, '$1,')) + '<\/div>').css({
            position: 'absolute',
            display: 'none',
            top: y - 40,
            left: x - 60,
            border: '0px solid #ccc',
            padding: '2px 6px',
            'background-color': '#fff'
        }).appendTo("body").fadeIn(200);
    }

    //销售总数
    var initChart1 = function () {

        var data = eval($('#amountSale').html());

        var plot_statistics = $.plot(
            $("#statistics_1"),
            [
                {
                    data:data,
                    lines: {
                        fill: 0.6,
                        lineWidth: 0
                    },
                    color: ['#45B6AF']
                },
                {
                    data: data,
                    points: {
                        show: true,
                        fill: true,
                        radius: 5,
                        fillColor: "#45B6AF",
                        lineWidth: 3
                    },
                    color: '#fff',
                    shadowSize: 0
                }
            ],
            {

                xaxis: {
                    tickLength: 0,
                    tickDecimals: 0,
                    mode: "categories",
                    min: 2,
                    font: {
                        lineHeight: 15,
                        style: "normal",
                        variant: "small-caps",
                        color: "#6F7B8A"
                    }
                },
                yaxis: {
                    ticks: 3,
                    tickDecimals: 0,
                    tickColor: "#f0f0f0",
                    font: {
                        lineHeight: 15,
                        style: "normal",
                        variant: "small-caps",
                        color: "#6F7B8A"
                    }
                },
                grid: {
                    backgroundColor: {
                        colors: ["#fff", "#fff"]
                    },
                    borderWidth: 1,
                    borderColor: "#f0f0f0",
                    margin: 0,
                    minBorderMargin: 0,
                    labelMargin: 20,
                    hoverable: true,
                    clickable: true,
                    mouseActiveRadius: 6
                },
                legend: {
                    show: false
                }
            }
        );

        var previousPoint = null;

        $("#statistics_1").bind("plothover", function (event, pos, item) {
            $("#x").text(pos.x.toFixed(2));
            $("#y").text(pos.y.toFixed(2));
            if (item) {
                if (previousPoint != item.dataIndex) {
                    previousPoint = item.dataIndex;

                    $("#tooltip").remove();
                    var x = item.datapoint[0].toFixed(2),
                        y = item.datapoint[1].toFixed(2);

                    showTooltip1(item.pageX, item.pageY, item.datapoint[0], item.datapoint[1]);
                }
            } else {
                $("#tooltip").remove();
                previousPoint = null;
            }
        });

    }

    //super:成单量
    var initChart2 = function() {

        var data = eval($('#amountOrder').html());

        var plot_statistics = $.plot(
            $("#statistics_2"),
            [
                {
                    data:data,
                    lines: {
                        fill: 0.6,
                        lineWidth: 0
                    },
                    color: ['#BAD9F5']
                },
                {
                    data: data,
                    points: {
                        show: true,
                        fill: true,
                        radius: 5,
                        fillColor: "#BAD9F5",
                        lineWidth: 3
                    },
                    color: '#fff',
                    shadowSize: 0
                }
            ],
            {

                xaxis: {
                    tickLength: 0,
                    tickDecimals: 0,
                    mode: "categories",
                    min: 2,
                    font: {
                        lineHeight: 14,
                        style: "normal",
                        variant: "small-caps",
                        color: "#6F7B8A"
                    }
                },
                yaxis: {
                    ticks: 3,
                    tickDecimals: 0,
                    tickColor: "#f0f0f0",
                    font: {
                        lineHeight: 14,
                        style: "normal",
                        variant: "small-caps",
                        color: "#6F7B8A"
                    }
                },
                grid: {
                    backgroundColor: {
                        colors: ["#fff", "#fff"]
                    },
                    borderWidth: 1,
                    borderColor: "#f0f0f0",
                    margin: 0,
                    minBorderMargin: 0,
                    labelMargin: 20,
                    hoverable: true,
                    clickable: true,
                    mouseActiveRadius: 6
                },
                legend: {
                    show: false
                }
            }
        );

        var previousPoint = null;

        $("#statistics_2").bind("plothover", function (event, pos, item) {
            $("#x").text(pos.x.toFixed(2));
            $("#y").text(pos.y.toFixed(2));
            if (item) {
                if (previousPoint != item.dataIndex) {
                    previousPoint = item.dataIndex;

                    $("#tooltip").remove();
                    var x = item.datapoint[0].toFixed(2),
                        y = item.datapoint[1].toFixed(2);

                    showTooltip2(item.pageX, item.pageY, item.datapoint[0], item.datapoint[1]);
                }
            } else {
                $("#tooltip").remove();
                previousPoint = null;
            }
        });

    };

    //super:成交件数
    var initChart3 = function() {

        var data = eval($('#amountProduct').html());

        var plot_statistics = $.plot(
            $("#statistics_3"),
            [
                {
                    data:data,
                    lines: {
                        fill: 0.6,
                        lineWidth: 0
                    },
                    color: ['#F3565D']
                },
                {
                    data: data,
                    points: {
                        show: true,
                        fill: true,
                        radius: 5,
                        fillColor: "#F3565D",
                        lineWidth: 3
                    },
                    color: '#fff',
                    shadowSize: 0
                }
            ],
            {

                xaxis: {
                    tickLength: 0,
                    tickDecimals: 0,
                    mode: "categories",
                    min: 2,
                    font: {
                        lineHeight: 14,
                        style: "normal",
                        variant: "small-caps",
                        color: "#6F7B8A"
                    }
                },
                yaxis: {
                    ticks: 3,
                    tickDecimals: 0,
                    tickColor: "#f0f0f0",
                    font: {
                        lineHeight: 14,
                        style: "normal",
                        variant: "small-caps",
                        color: "#6F7B8A"
                    }
                },
                grid: {
                    backgroundColor: {
                        colors: ["#fff", "#fff"]
                    },
                    borderWidth: 1,
                    borderColor: "#f0f0f0",
                    margin: 0,
                    minBorderMargin: 0,
                    labelMargin: 20,
                    hoverable: true,
                    clickable: true,
                    mouseActiveRadius: 6
                },
                legend: {
                    show: false
                }
            }
        );

        var previousPoint = null;

        $("#statistics_3").bind("plothover", function (event, pos, item) {
            $("#x").text(pos.x.toFixed(2));
            $("#y").text(pos.y.toFixed(2));
            if (item) {
                if (previousPoint != item.dataIndex) {
                    previousPoint = item.dataIndex;

                    $("#tooltip").remove();
                    var x = item.datapoint[0].toFixed(2),
                        y = item.datapoint[1].toFixed(2);

                    showTooltip2(item.pageX, item.pageY, item.datapoint[0], item.datapoint[1]);
                }
            } else {
                $("#tooltip").remove();
                previousPoint = null;
            }
        });

    }

    return {

        //main function
        init: function () {
            initChart1();

            $('#statistics_order_tab').on('shown.bs.tab', function (e) {
                initChart2();
            });

            $('#statistics_product_tab').on('shown.bs.tab', function (e) {
                initChart3();
            });
        }

    };

}();