<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script>

</script>
	
<!-- 左侧导航栏  -->
<div class="panel-group" id="accordion">
	<!-- 信息维护 -->
	<div class="panel panel-default">
		<div class="panel-heading">
			<h4 class="panel-title">
				<span class="glyphicon glyphicon-plus"></span> <a
					href="#collapseOne" data-toggle="collapse" data-parent="#accordion">
					信息管理</a>
				<div class="pull-right	">
					<span class="caret"></span>
				</div>
			</h4>
		</div>
		<div id="collapseOne" class="panel-collapse collapse in">
			<div class="panel-body">
				<ul class="list-group">
					<li class="list-group-item"><a href="javascript:void(0)" id="">供应商信息管理</a></li>
					<li class="list-group-item"><a href="javascript:void(0)" id="">客户信息管理</a></li>
					<li class="list-group-item"><a href="javascript:void(0)" id="">仓库信息管理</a></li>
					<li class="list-group-item"><a href="javascript:void(0)" id="">货物信息管理</a></li>
				</ul>
			</div>
		</div>
	</div>

	<!-- 信息维护 -->
	<div class="panel panel-default">
		<div class="panel-heading">
			<h4 class="panel-title">
				<span class="glyphicon glyphicon-cog"></span> <a href="#collapseSix"
					data-toggle="collapse" data-parent="#accordion"> 信息维护 </a>
				<div class="pull-right	">
					<span class="caret"></span>
				</div>
			</h4>
		</div>
		<div id="collapseSix" class="panel-collapse collapse">
			<div class="panel-body">
				<ul class="list-group">
					<li class="list-group-item"><a href="#" id="stu_infoEdit">修改个人信息</a>
					</li>
					<li class="list-group-item"><a href="#" id="admin_passwordEdit">修改密码</a>
					</li>
				</ul>
			</div>
		</div>
	</div>
</div>