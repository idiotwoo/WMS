<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<script>
	$(function() {
		optionAction();
		supplierListInit()
	})

	// 下拉框選擇動作
	function optionAction() {
		$(".dropOption").click(function() {
			var type = $(this).text();
			if (type == "所有") {
				$("#search_input").attr("readOnly", "true");
			} else {
				$("#search_input").removeAttr("readOnly");
			}
			$("#search_type").text(type);
			$("#search_input").attr("placeholder", type);
		})
	}

	// 表格初始化
	function supplierListInit() {
		$('#studentlist')
				.bootstrapTable(
						{
							columns : [
									{
										field : 'supplierID',
										title : '供应商ID',
									//sortable: true
									},
									{
										field : 'supplierName',
										title : '供应商名称'
									},
									{
										field : 'person',
										title : '负责人'
									},
									{
										field : 'tel',
										title : '联系电话'
									},
									{
										field : 'operation',
										title : '操作',
										formatter : function(value, row, index) {
											var s = '<button class="btn btn-info btn-sm edit"><span>编辑</span></button>';
											var d = '<button class="btn btn-danger btn-sm delete"><span>删除</span></button>';
											var fun = '';
											return s + ' ' + d;
										},
										events : {
											// 操作列中编辑按钮的动作
											'click .edit' : function(e, value,
													row, index) {
												stuID = row.stuID;
												rowEditOperation();
											},
											'click .delete' : function(e,
													value, row, index) {
												stuID = row.stuID;
												$('#deleteWarnning').modal(
														'show');
											}
										}
									} ],
							url : '',
							method : 'GET',
							queryParams : queryParams,
							sidePagination : "server",
							dataType : 'json',
							pagination : true,
							pageNumber : 1,
							pageSize : 5,
							pageList : [ 5, 10, 25, 50, 100 ]
						});
	}

	// 分页查询参数
	function queryParams(params) {
		var temp = {
			limit : params.limit,
			offset : params.offset,
		//type:searchType,
		//id:targetID
		}
		return temp;
	}
</script>
<div class="panel panel-default">
	<ol class="breadcrumb">
		<li>供应商信息管理</li>
	</ol>
	<div class="panel-body">
		<div class="row">
			<div class="col-md-1">
				<div class="btn-group">
					<button class="btn btn-default dropdown-toggle"
						data-toggle="dropdown">
						<span id="search_type">查询方式</span> <span class="caret"></span>
					</button>
					<ul class="dropdown-menu" role="menu">
						<li><a href="javascript:void(0)" class="dropOption">供应商ID</a></li>
						<li><a href="javascript:void(0)" class="dropOption">供应商名称</a></li>
						<li><a href="javascript:void(0)" class="dropOption">所有</a></li>
					</ul>
				</div>
			</div>
			<div class="col-md-9">
				<div>
					<div class="col-md-3">
						<input id="search_input" type="text" class="form-control"
							placeholder="供应商ID">
					</div>
					<div class="col-md-2">
						<button id="search_button" class="btn btn-success">
							<span class="glyphicon glyphicon-search"></span> <span>查询</span>
						</button>
					</div>
				</div>
			</div>
		</div>
		<div class="row" style="margin-top: 25px">
			<div class="col-md-5">
				<button class="btn btn-sm btn-default">
					<span class="glyphicon glyphicon-plus"></span>
					<span>添加供应商</span>
				</button>
				<button class="btn btn-sm btn-default">
					<span class="glyphicon glyphicon-import"></span>
					<span>导入</span>
				</button>
				<button class="btn btn-sm btn-default">
					<span class="glyphicon glyphicon-export"></span>
					<span>导出</span>
				</button>
			</div>
			<div class="col-md-5"></div>
		</div>


		<div class="row" style="margin-top: 15px">
			<div class="col-md-12">
				<table id="studentlist" class="table table-striped"></table>
			</div>
		</div>
	</div>
</div>
