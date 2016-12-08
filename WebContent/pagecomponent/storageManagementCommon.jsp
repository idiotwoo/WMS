<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<script>
	var search_type_storage = "none";
	var search_keyWord = "";
	var select_goodsID;
	var select_repositoryID;

	$(function() {
		optionAction();
		searchAction();
		storageListInit();

		exportStorageAction()
	})

	// 下拉框選擇動作
	function optionAction() {
		$(".dropOption").click(function() {
			var type = $(this).text();
			$("#search_input").val("");
			if (type == "所有") {
				$("#search_input_type").attr("readOnly", "true");
				search_type_storage = "searchAll";
			} else if (type == "货物ID") {
				$("#search_input_type").removeAttr("readOnly");
				search_type_storage = "searchByGoodsID";
			} else if (type == "货物名称") {
				$("#search_input_type").removeAttr("readOnly");
				search_type_storage = "searchByGoodsName";
			} else if(type = "货物类型"){
				$("#search_input_type").removeAttr("readOnly");
				search_type_storage = "searchByGoodsType";
			}else {
				$("#search_input_type").removeAttr("readOnly");
			}

			$("#search_type").text(type);
			$("#search_input_type").attr("placeholder", type);
		})
	}

	// 搜索动作
	function searchAction() {
		$('#search_button').click(function() {
			search_keyWord = $('#search_input_type').val();
			tableRefresh();
		})
	}

	// 分页查询参数
	function queryParams(params) {
		var temp = {
			limit : params.limit,
			offset : params.offset,
			searchType : search_type_storage,
			keyword : search_keyWord
		}
		return temp;
	}

	// 表格初始化
	function storageListInit() {
		$('#storageList')
				.bootstrapTable(
						{
							columns : [
									{
										field : 'goodsID',
										title : '货物ID'
									//sortable: true
									},
									{
										field : 'goodsName',
										title : '货物名称'
									},
									{
										field : 'goodsType',
										title : '货物类型'
									},
									{
										field : 'goodsSize',
										title : '货物尺寸',
										visible : false
									},
									{
										field : 'goodsValue',
										title : '货物价值',
										visible : false
									},
									{
										field : 'repositoryID',
										title : '仓库ID',
										visible : false
									},
									{
										field : 'number',
										title : '库存数量'
									},
									{
										field : 'operation',
										title : '操作',
										formatter : function(value, row, index) {
											var s = '<button class="btn btn-info btn-sm edit"><span>详细</span></button>';
											var fun = '';
											return s;
										},
										events : {
											// 操作列中编辑按钮的动作
											'click .edit' : function(e, value,
													row, index) {
												rowDetailOperation(row);
											}
										}
									} ],
							url : requestPrefix
									+ '/storageManage/getStorageList',
							method : 'GET',
							queryParams : queryParams,
							sidePagination : "server",
							dataType : 'json',
							pagination : true,
							pageNumber : 1,
							pageSize : 5,
							pageList : [ 5, 10, 25, 50, 100 ],
							clickToSelect : true
						});
	}

	// 表格刷新
	function tableRefresh() {
		$('#storageList').bootstrapTable('refresh', {
			query : {}
		});
	}

	// 行编辑操作
	function rowDetailOperation(row) {
		$('#detail_modal').modal("show");

		// load info
		$('#storage_goodsID').text(row.goodsID);
		$('#storage_goodsName').text(row.goodsName);
		$('#storage_goodsType').text(row.goodsType);
		$('#storage_goodsSize').text(row.goodsSize);
		$('#storage_goodsValue').text(row.goodsValue);
		$('#storage_repositoryBelong').text(row.repositoryID);
		$('#storage_number').text(row.number);
	}

	// 导出库存信息
	function exportStorageAction() {
		$('#export_storage').click(function() {
			$('#export_modal').modal("show");
		})

		$('#export_storage_download').click(function(){
			var data = {
				searchType : search_type_storage,
				keyword : search_keyWord
			}
			var url = requestPrefix + "/storageManage/exportStorageRecord?" + $.param(data)
			window.open(url, '_blank');
			$('#export_modal').modal("hide");
		})
	}

	// 操作结果提示模态框
	function infoModal(type, msg) {
		$('#info_success').removeClass("hide");
		$('#info_error').removeClass("hide");
		if (type == "success") {
			$('#info_error').addClass("hide");
		} else if (type == "error") {
			$('#info_success').addClass("hide");
		}
		$('#info_content').text(msg);
		$('#info_modal').modal("show");
	}
</script>
<div class="panel panel-default">
	<ol class="breadcrumb">
		<li>库存信息管理</li>
	</ol>
	<div class="panel-body">
		<div class="row">
			<div class="col-md-1 col-sm-2">
				<div class="btn-group">
					<button class="btn btn-default dropdown-toggle"
						data-toggle="dropdown">
						<span id="search_type">查询方式</span> <span class="caret"></span>
					</button>
					<ul class="dropdown-menu" role="menu">
						<li><a href="javascript:void(0)" class="dropOption">货物ID</a></li>
						<li><a href="javascript:void(0)" class="dropOption">货物名称</a></li>
						<li><a href="javascript:void(0)" class="dropOption">货物类型</a></li>
						<li><a href="javascript:void(0)" class="dropOption">所有</a></li>
					</ul>
				</div>
			</div>
			<div class="col-md-9 col-sm-9">
				<div>
					<div class="col-md-3 col-sm-3">
						<input id="search_input_type" type="text" class="form-control"
							placeholder="货物ID">
					</div>
					<div class="col-md-2 col-sm-2">
						<button id="search_button" class="btn btn-success">
							<span class="glyphicon glyphicon-search"></span> <span>查询</span>
						</button>
					</div>
				</div>
			</div>
		</div>

		<div class="row" style="margin-top: 25px">
			<div class="col-md-5 col-sm-5">
				<button class="btn btn-sm btn-default" id="export_storage">
					<span class="glyphicon glyphicon-export"></span> <span>导出</span>
				</button>
			</div>
			<div class="col-md-5 col-sm-5"></div>
		</div>

		<div class="row" style="margin-top: 15px">
			<div class="col-md-12">
				<table id="storageList" class="table table-striped"></table>
			</div>
		</div>
	</div>
</div>

<!-- 导出库存信息模态框 -->
<div class="modal fade" id="export_modal" table-index="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true"
	data-backdrop="static">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button class="close" type="button" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">导出库存信息</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-md-3 col-sm-3" style="text-align: center;">
						<img src="media/icons/warning-icon.png" alt=""
							style="width: 70px; height: 70px; margin-top: 20px;">
					</div>
					<div class="col-md-8 col-sm-8">
						<h3>是否确认导出库存信息</h3>
						<p>(注意：请确定要导出的库存信息，导出的内容为当前列表的搜索结果)</p>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button class="btn btn-default" type="button" data-dismiss="modal">
					<span>取消</span>
				</button>
				<button class="btn btn-success" type="button" id="export_storage_download">
					<span>确认下载</span>
				</button>
			</div>
		</div>
	</div>
</div>

<!-- 详细的库存模态框 -->
<div id="detail_modal" class="modal fade" table-index="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true"
	data-backdrop="static">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button class="close" type="button" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">编辑货物信息</h4>
			</div>
			<div class="modal-body">
				<!-- 模态框的内容 -->
				<div class="row">
					<div class="col-md-12">
						<form class="form-horizontal" role="form" id="storage_detail"
							style="margin-top: 25px">
							<div class="row">
								<div class="col-md-6 col-sm-6">
									<div class="form-group">
										<label for="" class="control-label col-md-6 col-sm-6"> <span>货物ID：</span>
										</label>
										<div class="col-md-4 col-sm-4">
											<p id="storage_goodsID" class="form-control-static"></p>
										</div>
									</div>
									<div class="form-group">
										<label for="" class="control-label col-md-6 col-sm-6"> <span>货物名称：</span>
										</label>
										<div class="col-md-4 col-sm-4">
											<p id="storage_goodsName" class="form-control-static"></p>
										</div>
									</div>
									<div class="form-group">
										<label for="" class="control-label col-md-6 col-sm-6"> <span>货物类型：</span>
										</label>
										<div class="col-md-4 col-sm-4">
											<p id="storage_goodsType" class="form-control-static"></p>
										</div>
									</div>
									<div class="form-group">
										<label for="" class="control-label col-md-6 col-sm-6"> <span>货物规格：</span>
										</label>
										<div class="col-md-4 col-sm-4">
											<p id="storage_goodsSize" class="form-control-static"></p>
										</div>
									</div>
								</div>
								<div class="col-md-6 col-sm-6">
									<div class="form-group">
										<label for="" class="control-label col-md-6 col-sm-6"> <span>货物价值：</span>
										</label>
										<div class="col-md-4 col-sm-4">
											<p id="storage_goodsValue" class="form-control-static"></p>
										</div>
									</div>
									<div class="form-group">
										<label for="" class="control-label col-md-6 col-sm-6"> <span>货物存储仓库ID：</span>
										</label>
										<div class="col-md-4 col-sm-4">
											<p id="storage_repositoryBelong" class="form-control-static"></p>
										</div>
									</div>
									<div class="form-group">
										<label for="" class="control-label col-md-6 col-sm-6"> <span>库存数量：</span>
										</label>
										<div class="col-md-4 col-sm-4">
											<p id="storage_number" class="form-control-static"></p>
										</div>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button class="btn btn-default" type="button" data-dismiss="modal">
					<span>关闭</span>
				</button>
			</div>
		</div>
	</div>
</div>