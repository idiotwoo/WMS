<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- 导航栏 -->
<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
	<div class="container-fluid">
		<!-- 导航栏标题 -->
		<div class="navbar-header">
			<a href="javascript:void(0)" class="navbar-brand home">仓库管理系统（系统管理员）</a>
		</div>

		<!-- 导航栏下拉菜单；用户信息与注销登陆 -->
		<div>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown"> <span class="glyphicon glyphicon-user"></span>
						<span>欢迎&nbsp;</span> <span id="nav_userName">${sessionScope.userName}</span>
						<span class="caret"></span>
				</a>
					<ul class="dropdown-menu">
						<li><a href="#" id="editInfo"> <span
								class="glyphicon glyphicon-pencil"></span> &nbsp;&nbsp;修改个人信息
						</a></li>
						<li><a href="./account/signOut" id="signout"> <span
								class="glyphicon glyphicon-off"></span> &nbsp;&nbsp;注销登录
						</a></li>
					</ul></li>
			</ul>
		</div>
	</div>
</nav>