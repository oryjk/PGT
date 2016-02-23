<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="admin" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<style>
    table {
        border: 2px solid #42b983;
        border-radius: 3px;
        background-color: #fff;
        width:100%;
    }
    th {
        width:100%;
        background-color: #42b983;
        color: rgba(255,255,255,0.66);
        cursor: pointer;
        -webkit-user-select: none;
        -moz-user-select: none;
        -user-select: none;
    }
    td {
        background-color: #f9f9f9;
    }
    th, td {
        width:25%;
        min-width: 80px;
        padding: 10px 20px;
        text-align: center;
    }
</style>

<admin:container id="tenderList" pageJsPath="/resources/log/log.js">
    <div class="row">
        <div class="col-xs-12">
            <ul class="page-breadcrumb breadcrumb">
                <li>
                    <a href="/">首页</a>
                    <i class="fa fa-circle"></i>
                </li>
                <li>
                    <a href="">日志</a>
                    <i class="fa fa-circle"></i>
                </li>
                <li class="active">
                    <a href="#">日志管理</a>
                </li>
            </ul>
        </div>
    </div>

    <div class="row">
        <div class="col-xs-12">

            <div class="portlet light">
                <div class="portlet-title">
                    <div class="caption">
                        <i class="fa fa-cogs font-green-sharp"></i>
                        <span class="caption-subject font-green-sharp bold uppercase">表格</span>
                    </div>
                </div>

                <div id="demo">
                    <table>
                        <thead>
                            <td>文件名</td>
                            <td>备份时间</td>
                            <td>文件大小</td>
                            <td>操作</td>
                        </thead>
                        <tbody>
                            <tr v-for="entry in gridData" v-model="entry">
                                <td>{{entry.name}}</td>
                                <td>{{entry.create_time}}</td>
                                <td>{{entry.size}}</td>
                                <td>
                                    <button value={{$index}} v-on:click="recover">恢复</button>
                                    <button value={{$index}} v-on:click="del">删除</button>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>

        </div>
    </div>
</admin:container>


