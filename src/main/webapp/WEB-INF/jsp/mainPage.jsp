<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>仓库管理系统</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/css/bootstrap-table.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/css/bootstrap-datetimepicker.min.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/css/jquery-ui.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/css/mainPage.css">
</head>
<body>

<!-- 导航栏 -->
<div id="navBar">
    <!-- 此处加载顶部导航栏 -->
    <!-- 导航栏 -->
    <nav class="navbar navbar-default navbar-fixed-top" role="navigation">
        <div class="container-fluid">
            <!-- 导航栏标题 -->
            <div class="navbar-header">
                <a href="javascript:void(0)" class="navbar-brand home">仓库管理系统</a>
            </div>

            <!-- 导航栏下拉菜单；用户信息与注销登陆 -->
            <div>
                <ul class="nav navbar-nav navbar-right">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle"
                           data-toggle="dropdown"> <span class="glyphicon glyphicon-user"></span>
                            <span>欢迎&nbsp;</span> <span id="nav_userName">用户名:${sessionScope.userName}</span>
                            <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu">
                            <shiro:hasRole name="commonsAdmin">
                                <li>
                                    <a href="#" id="editInfo"> <span
                                            class="glyphicon glyphicon-pencil"></span> &nbsp;&nbsp;修改个人信息</a></li>
                            </shiro:hasRole>
                            <li>
                                <a href="javascript:void(0)" id="signOut"> <span
                                        class="glyphicon glyphicon-off"></span> &nbsp;&nbsp;注销登录
                                </a>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</div>

<div class="container-fluid" style="padding-left: 0px;">
    <div class="row">
        <!-- 左侧导航栏 -->
        <div id="sideBar" class="col-md-2 col-sm-3">
            <!--  此处加载左侧导航栏 -->
            <!-- 左侧导航栏  -->
            <div class="panel-group" id="accordion">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a href="#collapse1" data-toggle="collapse" data-parent="#accordion"
                               class="parentMenuTitle collapseHead">库存管理</a>
                            <div class="pull-right">
                                <span class="caret"></span>
                            </div>
                        </h4>
                    </div>
                    <div id="collapse1" class="panel-collapse collapse collapseBody">
                        <div class="panel-body">
                            <ul class="list-group">
                                <shiro:hasRole name="commonsAdmin">
                                    <li class="list-group-item">
                                        <a href="javascript:void(0)" id="" class="menu_item"
                                           name="pagecomponent/storageManagementCommon.jsp">库存查询</a>
                                    </li>
                                </shiro:hasRole>
                                <shiro:hasRole name="systemAdmin">
                                    <li class="list-group-item">
                                        <a href="javascript:void(0)" id="" class="menu_item"
                                           name="pagecomponent/storageManagement.jsp">库存查询</a>
                                    </li>
                                    <li class="list-group-item">
                                        <a href="javascript:void(0)" id=""
                                           class="menu_item"
                                           name="pagecomponent/stockRecordManagement.html">出入库记录</a>
                                    </li>
                                </shiro:hasRole>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a href="#collapse2" data-toggle="collapse" data-parent="#accordion"
                               class="parentMenuTitle collapseHead">出入库管理</a>
                            <div class="pull-right">
                                <span class="caret"></span>
                            </div>
                        </h4>
                    </div>
                    <div id="collapse2" class="panel-collapse collapse collapseBody in">
                        <div class="panel-body">
                            <shiro:hasRole name="systemAdmin">
                                <ul class="list-group">
                                    <li class="list-group-item">
                                        <a href="javascript:void(0)" id="" class="menu_item"
                                           name="pagecomponent/stock-inManagement.jsp">货物入库</a>
                                    </li>
                                    <li class="list-group-item">
                                        <a href="javascript:void(0)" id="" class="menu_item"
                                           name="pagecomponent/stock-outManagement.jsp">货物出库</a>
                                    </li>
                                </ul>
                            </shiro:hasRole>
                            <shiro:hasRole name="commonsAdmin">
                                <ul class="list-group">
                                    <li class="list-group-item">
                                        <a href="javascript:void(0)" id="" class="menu_item"
                                           name="#">货物入库</a>
                                    </li>
                                    <li class="list-group-item">
                                        <a href="javascript:void(0)" id="" class="menu_item"
                                           name="#">货物出库</a>
                                    </li>
                                </ul>
                            </shiro:hasRole>
                        </div>
                    </div>
                </div>
                <shiro:hasRole name="systemAdmin">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h4 class="panel-title">
                                <a href="#collapse3" data-toggle="collapse" data-parent="#accordion"
                                   class="parentMenuTitle collapseHead">人员管理</a>
                                <div class="pull-right	">
                                    <span class="caret"></span>
                                </div>
                            </h4>
                        </div>
                        <div id="collapse3" class="panel-collapse collapse collapseBody">
                            <div class="panel-body">
                                <ul class="list-group">
                                    <li class="list-group-item">
                                        <a href="javascript:void(0)" id="" class="menu_item"
                                           name="pagecomponent/repositoryAdminManagement.jsp">仓库管理员管理</a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </shiro:hasRole>
                <shiro:hasRole name="systemAdmin">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h4 class="panel-title">
                                <a href="#collapse4" data-toggle="collapse" data-parent="#accordion"
                                   class="parentMenuTitle collapseHead">基础数据</a>
                                <div class="pull-right	">
                                    <span class="caret"></span>
                                </div>
                            </h4>
                        </div>
                        <div id="collapse4" class="panel-collapse collapse collapseBody">
                            <div class="panel-body">
                                <ul class="list-group">
                                    <li class="list-group-item">
                                        <a href="javascript:void(0)" id="" class="menu_item"
                                           name="pagecomponent/supplierManagement.jsp">供应商信息管理</a>
                                    </li>
                                    <li class="list-group-item">
                                        <a href="javascript:void(0)" id="" class="menu_item"
                                           name="pagecomponent/customerManagement.jsp">客户信息管理</a>
                                    </li>
                                    <li class="list-group-item">
                                        <a href="javascript:void(0)" id="" class="menu_item"
                                           name="pagecomponent/goodsManagement.jsp">货物信息管理</a>
                                    </li>
                                    <li class="list-group-item">
                                        <a href="javascript:void(0)" id="" class="menu_item"
                                           name="pagecomponent/repositoryManagement.jsp">仓库信息管理</a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </shiro:hasRole>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a href="#collapse5" data-toggle="collapse" data-parent="#accordion"
                               class="parentMenuTitle collapseHead">系统维护</a>
                            <div class="pull-right	"><span class="caret"></span></div>
                        </h4>
                    </div>
                    <div id="collapse5" class="panel-collapse collapse collapseBody">
                        <div class="panel-body">
                            <ul class="list-group">
                                <li class="list-group-item">
                                    <a href="javascript:void(0)" id="" class="menu_item"
                                       name="pagecomponent/passwordModification.jsp">更改密码</a>
                                </li>
                                <shiro:hasRole name="systemAdmin">
                                    <li class="list-group-item">
                                        <a href="javascript:void(0)" id=""
                                           class="menu_item">系统日志</a>
                                    </li>
                                    <li class="list-group-item">
                                        <a href="javascript:void(0)" id=""
                                           class="menu_item">登陆日志</a>
                                    </li>
                                </shiro:hasRole>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 面板区域 -->
        <div id="panel" class="col-md-10 col-sm-9">
            <!--  此处异步加载各个面板 -->

            <!-- 欢迎界面 -->
            <div class="panel panel-default">
                <!-- 面包屑 -->
                <ol class="breadcrumb">
                    <li>主页</li>
                </ol>

                <div class="panel-body">
                    <div class="row" style="margin-top: 100px; margin-bottom: 100px">
                        <div class="col-md-1"></div>
                        <div class="col-md-10" style="text-align: center">
                            <div class="col-md-4 col-sm-4">
                                <a href="javascript:void(0)" class="thumbnail shortcut"> <img
                                        src="media/icons/stock_search-512.png" alt="库存查询"
                                        class="img-rounded link" style="width: 150px; height: 150px;">
                                    <div class="caption">
                                        <h3 class="title">库存查询</h3>
                                    </div>
                                </a>
                            </div>
                            <div class="col-md-4 col-sm-4">
                                <a href="javascript:void(0)" class="thumbnail shortcut"> <img
                                        src="media/icons/stock_in-512.png" alt="货物入库"
                                        class="img-rounded link" style="width: 150px; height: 150px;">
                                    <div class="caption">
                                        <h3 class="title">货物入库</h3>
                                    </div>
                                </a>
                            </div>
                            <div class="col-md-4 col-sm-4">
                                <a href="javascript:void(0)" class="thumbnail shortcut"> <img
                                        src="media/icons/stock_out-512.png" alt="货物出库"
                                        class="img-rounded link" style="width: 150px; height: 150px;">
                                    <div class="caption">
                                        <h3 class="title">货物出库</h3>
                                    </div>
                                </a>
                            </div>
                        </div>
                        <div class="col-md-1"></div>
                    </div>
                </div>
            </div>

            <!-- end -->
        </div>
    </div>
</div>

<span id="requestPrefix" class="hide">${sessionScope.requestPrefix}</span>

<script type="text/javascript"
        src="${pageContext.request.contextPath}/js/jquery-2.2.3.min.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/js/jquery-ui.min.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/js/bootstrapValidator.min.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/js/bootstrap-table.min.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/js/bootstrap-table-zh-CN.min.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/js/jquery.md5.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/js/mainPage.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/js/ajaxfileupload.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/js/bootstrap-datetimepicker.zh-CN.js"></script>
</body>
</html>