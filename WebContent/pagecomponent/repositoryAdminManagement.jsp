<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<script>
	var search_type_repositoryAdmin = "none";
	var search_keyWord = "";
	var selectID;

	$(function() {
		optionAction();
		searchAction();
		repositoryAdminListInit();
		bootstrapValidatorInit();
		datePickerInit();

		addRepositoryAdminAction();
		editRepositoryAdminAction();
		deleteRepositoryAdminAction();
		importRepositoryAdminAction();
		exportRepositoryAdminAction()
	})

	// 下拉框選擇動作
	function optionAction() {
		$(".dropOption").click(function() {
			var type = $(this).text();
			$("#search_input").val("");
			if (type == "所有") {
				$("#search_input").attr("readOnly", "true");
				search_type_repositoryAdmin = "searchAll";
			} else if (type == "仓库管理员ID") {
				$("#search_input").removeAttr("readOnly");
				search_type_repositoryAdmin = "searchByID";
			} else if (type == "仓库管理员姓名") {
				$("#search_input").removeAttr("readOnly");
				search_type_repositoryAdmin = "searchByName";
			}else if(type == "仓库ID"){
				$("#search_input").removeAttr("readOnly");
				search_type_repositoryAdmin = "searchByRepositoryID";
			} else {
				$("#search_input").removeAttr("readOnly");
			}

			$("#search_type").text(type);
			$("#search_input").attr("placeholder", type);
		})
	}

	// 搜索动作
	function searchAction() {
		$('#search_button').click(function() {
			search_keyWord = $('#search_input').val();
			tableRefresh();
		})
	}

	// 分页查询参数
	function queryParams(params) {
		var temp = {
			limit : params.limit,
			offset : params.offset,
			searchType : search_type_repositoryAdmin,
			keyWord : search_keyWord
		}
		return temp;
	}

	// 表格初始化
	function repositoryAdminListInit() {
		$('#repositoryAdminList')
				.bootstrapTable(
						{
							columns : [
									{
										field : 'id',
										title : '仓库管理员ID'
									//sortable: true
									},
									{
										field : 'name',
										title : '仓库管理员姓名'
									},
									{
										field : 'sex',
										title : '性别'
									},
									{
										field : 'tel',
										title : '联系电话',
											visible : false
									},
									{
										field : 'address',
										title : '地址',
										visible : false
									},
									{
										field : 'birth',
										title : '出生日期',
										visible : false
									},
									{
										field : "repositoryBelongID",
										title : "所属仓库ID"
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
												selectID = row.id;
												rowEditOperation(row);
											},
											'click .delete' : function(e,
													value, row, index) {
												selectID = row.id;
												$('#deleteWarning_modal').modal(
														'show');
											}
										}
									} ],
							url : requestPrefix
									+ '/repositoryAdminManage/getRepositoryAdminList',
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
		$('#repositoryAdminList').bootstrapTable('refresh', {
			query : {}
		});
	}

	// 行编辑操作
	var unassignRepoCache;
	function rowEditOperation(row) {
		$('#edit_modal').modal("show");

		// load info
		$('#repositoryAdmin_form_edit').bootstrapValidator("resetForm", true);
		$('#repositoryAdmin_name_edit').val(row.name);
		$('#repositoryAdmin_sex_edit').val(row.sex);
		$('#repositoryAdmin_tel_edit').val(row.tel);
		$('#repositoryAdmin_address_edit').val(row.address);
		$('#repositoryAdmin_birth_edit').val(row.birth);
		$('#repositoryAdmin_repoID_edit').text("");
		
		// 加载未分配仓库信息
		if(row.repositoryBelongID != null){
			$('#repositoryAdmin_repoID_edit').append("<option value='" + row.repositoryBelongID + "'>" + row.repositoryBelongID + "</option>");
		}
			$('#repositoryAdmin_repoID_edit').append("<option value=''>不指派</option>");
		
		$('#repositoryInfo').removeClass('hide').addClass('hide');
		$.ajax({
			type : 'GET',
			url : requestPrefix + '/repositoryManage/getUnassignRepository',
			dataType : 'json',
			contentTypr : 'application/json',
			success : function(response){
				data = response.data;
				unassignRepoCache = data;
				$.each(data,function(index,element){
					$('#repositoryAdmin_repoID_edit').append("<option value='" + element.id + "'>" + element.id + "</option>");
				})
			}
		});
	}

	// 日期选择器初始化
	function datePickerInit(){
		$('.form_date').datetimepicker({
			format:'yyyy-mm-dd',
			language : 'zh-CN',
			endDate : new Date(),
			weekStart : 1,
			todayBtn : 1,
			autoClose : 1,
			todayHighlight : 1,
			startView : 2,
			forceParse : 0,
			minView:2
		});
	}

	// 添加供应商模态框数据校验
	function bootstrapValidatorInit() {
		$("#repositoryAdmin_form,#repositoryAdmin_form_edit").bootstrapValidator({
			message : 'This is not valid',
			feedbackIcons : {
				valid : 'glyphicon glyphicon-ok',
				invalid : 'glyphicon glyphicon-remove',
				validating : 'glyphicon glyphicon-refresh'
			},
			excluded : [ ':disabled' ],
			fields : {
				repositoryAdmin_name : {
					validators : {
						notEmpty : {
							message : '仓库管理员姓名不能为空'
						}
					}
				},
				repositoryAdmin_tel : {
					validators : {
						notEmpty : {
							message : '仓库管理员联系电话不能为空'
						}
					}
				},
				repositoryAdmin_address : {
					validators : {
						notEmpty : {
							message : '仓库管理员联系地址不能为空'
						}
					}
				},
				repositoryAdmin_birth : {
					validators : {
						notEmpty : {
							message : '仓库管理员出身日期不能为空'
						}
					}
				}
				
			}
		})
	}

	// 编辑仓库管理员信息
	function editRepositoryAdminAction() {
		$('#edit_modal_submit').click(
				function() {
					$('#repositoryAdmin_form_edit').data('bootstrapValidator')
							.validate();
					if (!$('#repositoryAdmin_form_edit').data('bootstrapValidator')
							.isValid()) {
						return;
					}

					var data = {
						id : selectID,
						name : $('#repositoryAdmin_name_edit').val(),
						sex : $('#repositoryAdmin_sex_edit').val(),
						tel : $('#repositoryAdmin_tel_edit').val(),
						address : $('#repositoryAdmin_address_edit').val(),
						birth : $('#repositoryAdmin_birth_edit').val(),
						repositoryBelongID : $('#repositoryAdmin_repoID_edit').val()
					}

					// ajax
					$.ajax({
						type : "POST",
						url : requestPrefix + '/repositoryAdminManage/updateRepositoryAdmin',
						dataType : "json",
						contentType : "application/json",
						data : JSON.stringify(data),
						success : function(response) {
							$('#edit_modal').modal("hide");
							var type;
							var msg;
							if (response.result == "success") {
								type = "success";
								msg = "仓库管理员信息更新成功";
							} else if (resposne == "error") {
								type = "error";
								msg = "仓库管理员信息更新失败"
							}
							infoModal(type, msg);
							tableRefresh();
						},
						error : function(response) {
						}
					});
				});

		$('#repositoryAdmin_repoID_edit').change(function(){
			var repositoryID = $(this).val();
			$('#repositoryInfo').removeClass('hide').addClass('hide');
			$.each(unassignRepoCache,function(index,element){
				if(element.id == repositoryID){
					$('#repository_address').text(element.address);
					$('#repository_area').text(element.area);
					$('#repository_status').text(element.status);
					$('#repositoryInfo').removeClass('hide');
				}
			})
			
		})
	}

	// 刪除仓库管理员信息
	function deleteRepositoryAdminAction(){
		$('#delete_confirm').click(function(){
			var data = {
				"repositoryAdminID" : selectID
			}
			
			// ajax
			$.ajax({
				type : "GET",
				url : requestPrefix + "/repositoryAdminManage/deleteRepositoryAdmin",
				dataType : "json",
				contentType : "application/json",
				data : data,
				success : function(response){
					$('#deleteWarning_modal').modal("hide");
					var type;
					var msg;
					if(response.result == "success"){
						type = "success";
						msg = "仓库管理员信息删除成功";
					}else{
						type = "error";
						msg = "仓库管理员信息删除失败";
					}
					infoModal(type, msg);
					tableRefresh();
				},error : function(response){
				}
			})
			
			$('#deleteWarning_modal').modal('hide');
		})
	}

	// 添加仓库管理员信息
	function addRepositoryAdminAction() {
		$('#add_repositoryAdmin').click(function() {
			$('#add_modal').modal("show");
		});

		$('#add_modal_submit').click(function() {
			var data = {
				name : $('#repositoryAdmin_name').val(),
				tel : $('#repositoryAdmin_tel').val(),
				sex : $('#repositoryAdmin_sex').val(),
				address : $('#repositoryAdmin_address').val(),
				birth : $('#repositoryAdmin_birth').val()
			}
			// ajax
			$.ajax({
				type : "POST",
				url : requestPrefix + "/repositoryAdminManage/addRepositoryAdmin",
				dataType : "json",
				contentType : "application/json",
				data : JSON.stringify(data),
				success : function(response) {
					$('#add_modal').modal("hide");
					var msg;
					var type;
					if (response.result == "success") {
						type = "success";
						msg = "仓库管理员添加成功";
					} else if (response.result == "error") {
						type = "error";
						msg = "仓库管理员添加失败";
					}
					infoModal(type, msg);
					tableRefresh();

					// reset
					$('#repositoryAdmin_name').val("");
					$('#repositoryAdmin_sex').val("男");
					$('#repositoryAdmin_tel').val("");
					$('#repositoryAdmin_address').val("");
					$('#repositoryAdmin_birth').val("");
					$('#repositoryAdmin_form').bootstrapValidator("resetForm", true);
				},
				error : function(response) {
				}
			})
		})
	}

	var import_step = 1;
	var import_start = 1;
	var import_end = 3;
	// 导入仓库管理员信息
	function importRepositoryAdminAction() {
		$('#import_repositoryAdmin').click(function() {
			$('#import_modal').modal("show");
		});

		$('#previous').click(function() {
			if (import_step > import_start) {
				var preID = "step" + (import_step - 1)
				var nowID = "step" + import_step;

				$('#' + nowID).addClass("hide");
				$('#' + preID).removeClass("hide");
				import_step--;
			}
		})

		$('#next').click(function() {
			if (import_step < import_end) {
				var nowID = "step" + import_step;
				var nextID = "step" + (import_step + 1);

				$('#' + nowID).addClass("hide");
				$('#' + nextID).removeClass("hide");
				import_step++;
			}
		})

		$('#file').on("change", function() {
			$('#previous').addClass("hide");
			$('#next').addClass("hide");
			$('#submit').removeClass("hide");
		})

		$('#submit').click(function() {
			var nowID = "step" + import_end;
			$('#' + nowID).addClass("hide");
			$('#uploading').removeClass("hide");

			// next
			$('#confirm').removeClass("hide");
			$('#submit').addClass("hide");

			// ajax
			$.ajaxFileUpload({
				url : requestPrefix + "/repositoryAdminManage/importRepositoryAdmin",
				secureuri: false,
				dataType: 'json',
				fileElementId:"file",
				success : function(data, status){
					var total = 0;
					var available = 0;
					var msg1 = "仓库管理员信息导入成功";
					var msg2 = "仓库管理员信息导入失败";
					var info;

					$('#import_progress_bar').addClass("hide");
					if(data.result == "success"){
						total = data.total;
						available = data.available;
						info = msg1;
						$('#import_success').removeClass('hide');
					}else{
						info = msg2
						$('#import_error').removeClass('hide');
					}
					info = info + ",总条数：" + total + ",有效条数:" + available;
					$('#import_result').removeClass('hide');
					$('#import_info').text(info);
					$('#confirm').removeClass('disabled');
				},error : function(data, status){
				}
			})
		})

		$('#confirm').click(function() {
			// modal dissmiss
			importModalReset();
		})
	}

	// 导出仓库管理员信息
	function exportRepositoryAdminAction() {
		$('#export_repositoryAdmin').click(function() {
			$('#export_modal').modal("show");
		})

		$('#export_repositoryAdmin_download').click(function(){
			var data = {
				searchType : search_type_repositoryAdmin,
				keyWord : search_keyWord
			}
			var url = requestPrefix + "/repositoryAdminManage/exportRepositoryAdmin?" + $.param(data)
			window.open(url, '_blank');
			$('#export_modal').modal("hide");
		})
	}

	// 导入仓库管理员模态框重置
	function importModalReset(){
		var i;
		for(i = import_start; i <= import_end; i++){
			var step = "step" + i;
			$('#' + step).removeClass("hide")
		}
		for(i = import_start; i <= import_end; i++){
			var step = "step" + i;
			$('#' + step).addClass("hide")
		}
		$('#step' + import_start).removeClass("hide");

		$('#import_progress_bar').removeClass("hide");
		$('#import_result').removeClass("hide");
		$('#import_success').removeClass('hide');
		$('#import_error').removeClass('hide');
		$('#import_progress_bar').addClass("hide");
		$('#import_result').addClass("hide");
		$('#import_success').addClass('hide');
		$('#import_error').addClass('hide');
		$('#import_info').text("");
		$('#file').val("");

		$('#previous').removeClass("hide");
		$('#next').removeClass("hide");
		$('#submit').removeClass("hide");
		$('#confirm').removeClass("hide");
		$('#submit').addClass("hide");
		$('#confirm').addClass("hide");

		//$('#file').wrap('<form>').closest('form').get(0).reset();
		//$('#file').unwrap();
		//var control = $('#file');
		//control.replaceWith( control = control.clone( true ) );
		$('#file').on("change", function() {
			$('#previous').addClass("hide");
			$('#next').addClass("hide");
			$('#submit').removeClass("hide");
		})
		
		import_step = 1;
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
		<li>仓库管理员信息管理</li>
	</ol>
	<div class="panel-body">
		<div class="row">
			<div class="col-md-8 col-sm-8">
				<div class="row">
					<div class="col-md-2 col-sm-3">
						<div class="btn-group">
							<button class="btn btn-default dropdown-toggle"
								data-toggle="dropdown">
								<span id="search_type">查询方式</span> <span class="caret"></span>
							</button>
							<ul class="dropdown-menu" role="menu">
								<li><a href="javascript:void(0)" class="dropOption">仓库管理员ID</a></li>
								<li><a href="javascript:void(0)" class="dropOption">仓库管理员姓名</a></li>
								<li><a href="javascript:void(0)" class="dropOption">仓库ID</a></li>
								<li><a href="javascript:void(0)" class="dropOption">所有</a></li>
							</ul>
						</div>
					</div>
					<div class="col-md-9 col-sm-9">
						<div>
							<div class="col-md-5 col-sm-7">
								<input id="search_input" type="text" class="form-control"
									placeholder="查询仓库管理员信息">
							</div>
							<div class="col-md-2 col-sm-5">
								<button id="search_button" class="btn btn-success">
									<span class="glyphicon glyphicon-search"></span> <span>查询</span>
								</button>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- 
			<div class="col-md-2">
				<div class="btn-group">
					<button class="btn btn-default dropdown-toggle"
						data-toggle="dropdown">
						<span id="search_type">查询方式</span> <span class="caret"></span>
					</button>
					<ul class="dropdown-menu" role="menu">
						<li><a href="javascript:void(0)" class="dropOption">仓库管理员ID</a></li>
						<li><a href="javascript:void(0)" class="dropOption">仓库管理员姓名</a></li>
						<li><a href="javascript:void(0)" class="dropOption">仓库ID</a></li>
						<li><a href="javascript:void(0)" class="dropOption">所有</a></li>
					</ul>
				</div>
			</div>
			<div class="col-md-9">
				<div>
					<div class="col-md-3">
						<input id="search_input" type="text" class="form-control"
							placeholder="查询仓库管理员信息">
					</div>
					<div class="col-md-2">
						<button id="search_button" class="btn btn-success">
							<span class="glyphicon glyphicon-search"></span> <span>查询</span>
						</button>
					</div>
				</div>
			</div>
			 -->
		</div>

		<div class="row" style="margin-top: 25px">
			<div class="col-md-5">
				<button class="btn btn-sm btn-default" id="add_repositoryAdmin">
					<span class="glyphicon glyphicon-plus"></span> <span>添加仓库管理员信息</span>
				</button>
				<button class="btn btn-sm btn-default" id="import_repositoryAdmin">
					<span class="glyphicon glyphicon-import"></span> <span>导入</span>
				</button>
				<button class="btn btn-sm btn-default" id="export_repositoryAdmin">
					<span class="glyphicon glyphicon-export"></span> <span>导出</span>
				</button>
			</div>
			<div class="col-md-5"></div>
		</div>

		<div class="row" style="margin-top: 15px">
			<div class="col-md-12">
				<table id="repositoryAdminList" class="table table-striped"></table>
			</div>
		</div>
	</div>
</div>

<!-- 添加仓库管理员信息模态框 -->
<div id="add_modal" class="modal fade" table-index="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true"
	data-backdrop="static">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button class="close" type="button" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">添加仓库管理员信息</h4>
			</div>
			<div class="modal-body">
				<!-- 模态框的内容 -->
				<div class="row">
					<div class="col-md-1 col-sm-2"></div>
					<div class="col-md-8 col-sm-8">
						<form class="form-horizontal" role="form" id="repositoryAdmin_form"
							style="margin-top: 25px">
							<div class="form-group">
								<label for="" class="control-label col-md-5 col-sm-5"> <span>仓库管理员姓名：</span>
								</label>
								<div class="col-md-7 col-sm-7">
									<input type="text" class="form-control" id="repositoryAdmin_name"
										name="repositoryAdmin_name" placeholder="仓库管理员姓名">
								</div>
							</div>
							<div class="form-group">
								<label for="" class="control-label col-md-5 col-sm-5"> <span>仓库管理员性别:</span>
								</label>
								<div class="col-md-5 col-sm-5">
									<select name="" class="form-control" id="repositoryAdmin_sex">
										<option value="男">男性</option>
										<option value="女">女性</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="" class="control-label col-md-5 col-sm-5"> <span>联系电话：</span>
								</label>
								<div class="col-md-7 col-sm-7">
									<input type="text" class="form-control" id="repositoryAdmin_tel"
										name="repositoryAdmin_tel" placeholder="联系电话">
								</div>
							</div>
							<div class="form-group">
								<label for="" class="control-label col-md-5 col-sm-5"> <span>联系地址：</span>
								</label>
								<div class="col-md-7 col-sm-7">
									<input type="text" class="form-control" id="repositoryAdmin_address"
										name="repositoryAdmin_address" placeholder="联系地址">
								</div>
							</div>
							<div class="form-group">
								<label for="BirthDate" class="control-label col-md-5 col-sm-5"> 
									<span>出生日期:</span>
								</label>
								<div class="col-md-7 col-sm-7">
									<input class="form_date form-control" value="" id="repositoryAdmin_birth" name="repositoryAdmin_birth" placeholder="出生日期">
								</div>
							</div>
						</form>
					</div>
					<div class="col-md-1 col-sm-1"></div>
				</div>
			</div>
			<div class="modal-footer">
				<button class="btn btn-default" type="button" data-dismiss="modal">
					<span>取消</span>
				</button>
				<button class="btn btn-success" type="button" id="add_modal_submit">
					<span>提交</span>
				</button>
			</div>
		</div>
	</div>
</div>

<!-- 导入仓库管理员信息模态框 -->
<div class="modal fade" id="import_modal" table-index="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true"
	data-backdrop="static">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button class="close" type="button" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">导入仓库管理员信息</h4>
			</div>
			<div class="modal-body">
				<div id="step1">
					<div class="row" style="margin-top: 15px">
						<div class="col-md-1 col-sm-1"></div>
						<div class="col-md-10 col-sm-10">
							<div>
								<h4>点击下面的下载按钮，下载仓库管理员信息电子表格</h4>
							</div>
							<div style="margin-top: 30px; margin-buttom: 15px">
								<a class="btn btn-info"
									href="commons/fileSource/download/repositoryAdminInfo.xlsx"
									target="_blank"> <span class="glyphicon glyphicon-download"></span>
									<span>下载</span>
								</a>
							</div>
						</div>
					</div>
				</div>
				<div id="step2" class="hide">
					<div class="row" style="margin-top: 15px">
						<div class="col-md-1 col-sm-1"></div>
						<div class="col-md-10 col-sm-10">
							<div>
								<h4>请按照仓库管理员信息电子表格中指定的格式填写需要添加的一个或多个仓库管理员信息</h4>
							</div>
							<div class="alert alert-info"
								style="margin-top: 10px; margin-buttom: 30px">
								<p>注意：表格中各个列均不能为空，若存在未填写的项，则该条信息将不能成功导入</p>
							</div>
						</div>
					</div>
				</div>
				<div id="step3" class="hide">
					<div class="row" style="margin-top: 15px">
						<div class="col-md-1 col-sm-1"></div>
						<div class="col-md-8 col-sm-10">
							<div>
								<div>
									<h4>请点击下面上传文件按钮，上传填写好的仓库管理员信息电子表格</h4>
								</div>
								<div style="margin-top: 30px; margin-buttom: 15px">
									<span class="btn btn-info btn-file"> <span> <span
											class="glyphicon glyphicon-upload"></span> <span>上传文件</span>
									</span> 
									<form id="import_file_upload"><input type="file" id="file" name="file"></form>
									</span>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="hide" id="uploading">
					<div class="row" style="margin-top: 15px" id="import_progress_bar">
						<div class="col-md-1 col-sm-1"></div>
						<div class="col-md-10 col-sm-10"
							style="margin-top: 30px; margin-bottom: 30px">
							<div class="progress progress-striped active">
								<div class="progress-bar progress-bar-success"
									role="progreessbar" aria-valuenow="60" aria-valuemin="0"
									aria-valuemax="100" style="width: 100%;">
									<span class="sr-only">请稍后...</span>
								</div>
							</div>
							<!-- 
							<div style="text-align: center">
								<h4 id="import_info"></h4>
							</div>
							 -->
						</div>
						<div class="col-md-1 col-sm-1"></div>
					</div>
					<div class="row">
						<div class="col-md-4 col-sm-4"></div>
						<div class="col-md-4 col-sm-4">
							<div id="import_result" class="hide">
								<div id="import_success" class="hide" style="text-align: center;">
									<img src="media/icons/success-icon.png" alt=""
										style="width: 100px; height: 100px;">
								</div>
								<div id="import_error" class="hide" style="text-align: center;">
									<img src="media/icons/error-icon.png" alt=""
										style="width: 100px; height: 100px;">
								</div>
							</div>
						</div>
						<div class="col-md-4 col-sm-4"></div>
					</div>
					<div class="row" style="margin-top: 10px">
						<div class="col-md-3 col-sm-3"></div>
						<div class="col-md-6 col-sm-6" style="text-align: center;">
							<h4 id="import_info"></h4>
						</div>
						<div class="col-md-3 col-sm-3"></div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button class="btn ben-default" type="button" id="previous">
					<span>上一步</span>
				</button>
				<button class="btn btn-success" type="button" id="next">
					<span>下一步</span>
				</button>
				<button class="btn btn-success hide" type="button" id="submit">
					<span>&nbsp;&nbsp;&nbsp;提交&nbsp;&nbsp;&nbsp;</span>
				</button>
				<button class="btn btn-success hide disabled" type="button"
					id="confirm" data-dismiss="modal">
					<span>&nbsp;&nbsp;&nbsp;确认&nbsp;&nbsp;&nbsp;</span>
				</button>
			</div>
		</div>
	</div>
</div>

<!-- 导出仓库管理员信息模态框 -->
<div class="modal fade" id="export_modal" table-index="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true"
	data-backdrop="static">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button class="close" type="button" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">导出客户信息</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-md-3 col-sm-3" style="text-align: center;">
						<img src="media/icons/warning-icon.png" alt=""
							style="width: 70px; height: 70px; margin-top: 20px;">
					</div>
					<div class="col-md-8 col-sm-8">
						<h3>是否确认导出仓库管理员信息</h3>
						<p>(注意：请确定要导出的仓库管理员信息，导出的内容为当前列表的搜索结果)</p>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button class="btn btn-default" type="button" data-dismiss="modal">
					<span>取消</span>
				</button>
				<button class="btn btn-success" type="button" id="export_repositoryAdmin_download">
					<span>确认下载</span>
				</button>
			</div>
		</div>
	</div>
</div>

<!-- 提示消息模态框 -->
<div class="modal fade" id="info_modal" table-index="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button class="close" type="button" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">信息</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-md-4 col-sm-4"></div>
					<div class="col-md-4 col-sm-4">
						<div id="info_success" class=" hide" style="text-align: center;">
							<img src="media/icons/success-icon.png" alt=""
								style="width: 100px; height: 100px;">
						</div>
						<div id="info_error" style="text-align: center;">
							<img src="media/icons/error-icon.png" alt=""
								style="width: 100px; height: 100px;">
						</div>
					</div>
					<div class="col-md-4 col-sm-4"></div>
				</div>
				<div class="row" style="margin-top: 10px">
					<div class="col-md-4 col-sm-4"></div>
					<div class="col-md-4 col-sm-4" style="text-align: center;">
						<h4 id="info_content"></h4>
					</div>
					<div class="col-md-4 col-sm-4"></div>
				</div>
			</div>
			<div class="modal-footer">
				<button class="btn btn-default" type="button" data-dismiss="modal">
					<span>&nbsp;&nbsp;&nbsp;关闭&nbsp;&nbsp;&nbsp;</span>
				</button>
			</div>
		</div>
	</div>
</div>

<!-- 删除提示模态框 -->
<div class="modal fade" id="deleteWarning_modal" table-index="-1"
	role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button class="close" type="button" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">警告</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-md-3 col-sm-3" style="text-align: center;">
						<img src="media/icons/warning-icon.png" alt=""
							style="width: 70px; height: 70px; margin-top: 20px;">
					</div>
					<div class="col-md-8 col-sm-8">
						<h3>是否确认删除该条仓库管理员信息</h3>
						<p>(注意：若该仓库管理员已经指派管理的仓库，则该仓库管理员信息将不能删除成功。如需删除该客户的信息，请先解除该仓库管理员的指派)</p>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button class="btn btn-default" type="button" data-dismiss="modal">
					<span>取消</span>
				</button>
				<button class="btn btn-danger" type="button" id="delete_confirm">
					<span>确认删除</span>
				</button>
			</div>
		</div>
	</div>
</div>

<!-- 编辑仓库管理员信息模态框 -->
<div id="edit_modal" class="modal fade" table-index="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true"
	data-backdrop="static">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button class="close" type="button" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">编辑仓库管理员信息</h4>
			</div>
			<div class="modal-body">
				<!-- 模态框的内容 -->
				<div class="row">
					<div class="col-md-1 col-sm-1"></div>
					<div class="col-md-8 col-sm-8">
						<form class="form-horizontal" role="form" id="repositoryAdmin_form_edit"
							style="margin-top: 25px">
							<div class="form-group">
								<label for="" class="control-label col-md-5 col-sm-5"> <span>仓库管理员姓名：</span>
								</label>
								<div class="col-md-7 col-sm-7">
									<input type="text" class="form-control" id="repositoryAdmin_name_edit"
										name="repositoryAdmin_name" placeholder="仓库管理员姓名">
								</div>
							</div>
							<div class="form-group">
								<label for="" class="control-label col-md-5 col-sm-5"> <span>仓库管理员性别:</span>
								</label>
								<div class="col-md-5 col-sm-5">
									<select name="" class="form-control" id="repositoryAdmin_sex_edit">
										<option value="男">男性</option>
										<option value="女">女性</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="" class="control-label col-md-5 col-sm-5"> <span>联系电话：</span>
								</label>
								<div class="col-md-7 col-sm-7">
									<input type="text" class="form-control" id="repositoryAdmin_tel_edit"
										name="repositoryAdmin_tel" placeholder="联系电话">
								</div>
							</div>
							<div class="form-group">
								<label for="" class="control-label col-md-5 col-sm-5"> <span>联系地址：</span>
								</label>
								<div class="col-md-7 col-sm-7">
									<input type="text" class="form-control" id="repositoryAdmin_address_edit"
										name="repositoryAdmin_address" placeholder="联系地址">
								</div>
							</div>
							<div class="form-group">
								<label for="BirthDate" class="control-label col-md-5 col-sm-5"> 
									<span>出生日期:</span>
								</label>
								<div class="col-md-7 col-sm-7">
									<input class="form_date form-control" value="" id="repositoryAdmin_birth_edit" name="repositoryAdmin_birth" placeholder="出生日期">
								</div>
							</div>
							<div class="form-group">
								<label for="" class="control-label col-md-5 col-sm-5"> <span>所属仓库ID：</span>
								</label>
								<div class="col-md-7 col-sm-7">
									<select name="" class="form-control" id="repositoryAdmin_repoID_edit">
										<option value=""></option>
									</select>
								</div>
							</div>
							<div class="form-group hide" id="repositoryInfo">
								<div class="col-md-2"></div>
								<div class="col-md-10 alert alert-info">
									<div><label>仓库地址：</label><span id="repository_address"></span></div>
									<div><label>仓库面积：</label><span id="repository_area"></span></div>
									<div><label>仓库状态:</label><span id="repository_status"></span></div>
								</div>
							</div>
						</form>
					</div>
					<div class="col-md-1"></div>
				</div>
			</div>
			<div class="modal-footer">
				<button class="btn btn-default" type="button" data-dismiss="modal">
					<span>取消</span>
				</button>
				<button class="btn btn-success" type="button" id="edit_modal_submit">
					<span>确认更改</span>
				</button>
			</div>
		</div>
	</div>
</div>