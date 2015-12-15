/**
 * Created by supersoup on 15/11/19.
 */

var productList =
{
    "result": [{
        "name": "qin",
        "id": 3,
        "creationDate": 1447921673000,
        "updateDate": 1447921673000,
        "available": true,
        "password": "e9cc75570c7558d9d3eb43c9b087fef8",
        "role": "ADMINISTRATOR",
        "login": "morganqin",
        "salt": "1447921673459",
        "lastLoginDate": 1447921673000,
        "ip": "0:0:0:0:0:0:0:1",
        "phone": "15982223080",
        "email": null,
        "investType": "NONEED"
    }],
    "count": 1,
    "asc": true,
    "currentIndex": 0,
    "capacity": 5,
    "keyword": null,
    "sortFieldName": null,
    "invalidPagination": false,
    "nextIndex": 0,
    "preIndex": 0,
    "maxIndex": 0,
    "sqlStartIndex": 0
};

    var request = {
        base: '/internal',
        iuList: '/iu-list-data'
    };
    var searchInfo = {

    };

    getIuList();

    function getIuList() {
        //$.ajax({
        //    type: 'get',
        //    url: request.base + request.iuList,
        //    data: searchInfo,
        //    success: function(param) {
        //        console.log(param);
        //    }
        //})

        createList(productList);
    }

    function createList(param) {
        var str = '';
        $(param.result).each(function(i, e) {
            console.log($(this));

            var one = e;
            str +=
                '<tr class="gradeX odd" role="row">'
                + '<td>'
                + '<input type="checkbox">'
                + '</td>'
                + '<td class="sorting_1">'
                + one.id
                + '</td>'
                + '<td>'
                + '<button class="btn btn-xs blue">'
                + one.role
                + '</button>'
                + '</td>'
                + '<td>'
                + '<a class="link-name" href="#">'
                + one.name
                + '</a>'
                + '</td>'
                + '<td>'
                + '<a class="link-name" href="#">'
                + one.login
                + '</a>'
                + '</td>'
                + '<td>'

                + '</td>'
                + '<td>'
                + Date(one.lastLoginDate)
            + '</td>'
            + '<td>'
            + one.ip
            + '</td>'
            + '<td>'
            + '<button class="btn btn-xs gray btn-circle">禁用</button>'
            + '</td>'
            + '<td>'
            + '<button class="btn btn-xs blue btn-circle">修改</button>'
            + '<button class="btn btn-xs yellow btn-circle">删除</button>'
            + '</td>'
            + '</tr>'
        });
        $('#list').html(str);
    }


