<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"> <span class="glyphicon glyphicon-user"></span>
							<span>欢迎&nbsp;</span> <span id="nav_userName">ID:${sessionScope.userID}</span>
							<span class="caret"></span>
					</a>
						<ul class="dropdown-menu">
							<li><a href="#" id="editInfo"> <span
									class="glyphicon glyphicon-pencil"></span> &nbsp;&nbsp;修改个人信息
							</a></li>
							<li><a href="javascript:void(0)" id="signOut"> <span
									class="glyphicon glyphicon-off"></span> &nbsp;&nbsp;注销登录
							</a></li>
						</ul></li>
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
				<div class="panel-group" id="accordion"></div>
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
									<a href="#" class="thumbnail"> <img
										src="images/icons/find.png" alt="成绩查询"
										class="img-rounded link" style="width: 150px; height: 150px;">
										<div class="caption">
											<h3>库存查询</h3>
										</div>
									</a>
								</div>
								<div class="col-md-4 col-sm-4">
									<a href="#" class="thumbnail"> <img
										src="images/icons/edit.png" alt="教学评价"
										class="img-rounded link" style="width: 150px; height: 150px;">
										<div class="caption">
											<h3>货物入库</h3>
										</div>
									</a>
								</div>
								<div class="col-md-4 col-sm-4">
									<a href="#" class="thumbnail"> <img
										src="images/icons/maintain.png" alt="信息维护"
										class="img-rounded link" style="width: 150px; height: 150px;">
										<div class="caption">
											<h3>货物出库</h3>
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
		src="${pageContext.request.contextPath}/js/bootstrapValidator.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/bootstrap-table.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/bootstrap-table-zh-CN.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/jquery.md5.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/mainPage.js"></script>
</body>
</html>