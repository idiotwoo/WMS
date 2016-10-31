<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>仓库管理系统（系统管理员）</title>
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
	</div>

	<div class="container-fluid" style="padding-left: 0px;">
		<div class="row">
			<!-- 左侧导航栏 -->
			<div id="sideBar" class="col-md-2 col-sm-3">
				<!--  此处加载左侧导航栏 -->

			</div>

			<!-- 面板区域 -->
			<div id="panel" class="col-md-10 col-sm-9">
				<!--  此处异步加载各个面板 -->


			</div>
		</div>
	</div>

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

	<script>
		$(function() {
			initPage();
		});

		// 初始化页面，加载页面组件
		function initPage() {
			$('#navBar').load("pagecomponent/systemAdmin/navBar.jsp");
			$('#sideBar').load("pagecomponent/systemAdmin/sideBar.jsp");
		}
	</script>
</body>
</html>