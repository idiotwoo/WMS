<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<script>
	var search_type_supplier = "none";
	var search_keyWord = "";
	var selectID;

	$(function() {
		optionAction();
		searchAction();
		supplierListInit();
		bootstrapValidatorInit();

		addSupplierAction();
		editSupplierAction();
		deleteSupplierAction();
		importSupplierAction();
		exportSupplierAction()
	})

	// 下拉框選擇動作
	function optionAction() {
		$(".dropOption").click(function() {
			var type = $(this).text();
			$("#search_input").val("");
			if (type == "所有") {
				$("#search_input").attr("readOnly", "true");
				search_type_supplier = "searchAll";
			} else if (type == "供应商ID") {
				$("#search_input").removeAttr("readOnly");
				search_type_supplier = "searchByID";
			} else if (type == "供应商名称") {
				$("#search_input").removeAttr("readOnly");
				search_type_supplier = "searchByName";
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
			searchType : search_type_supplier,
			keyWord : search_keyWord
		}
		return temp;
	}

	// 表格初始化
	function supplierListInit() {
		$('#supplierList')
				.bootstrapTable(
						{
							columns : [
									{
										field : 'id',
										title : '供应商ID'
									//sortable: true
									},
									{
										field : 'name',
										title : '供应商名称'
									},
									{
										field : 'personInCharge',
										title : '负责人'
									},
									{
										field : 'tel',
										title : '联系电话'
									},
									{
										field : 'address',
										title : '地址',
										visible : false
									},
									{
										field : 'email',
										title : '电子邮件',
										visible : false
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
									+ '/supplierManage/getSupplierList',
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
		$('#supplierList').bootstrapTable('refresh', {
			query : {}
		});
	}

	// 行编辑操作
	function rowEditOperation(row) {
		$('#edit_modal').modal("show");

		// load info
		$('#supplier_form_edit').bootstrapValidator("resetForm", true);
		$('#supplier_name_edit').val(row.name);
		$('#supplier_person_edit').val(row.personInCharge);
		$('#supplier_tel_edit').val(row.tel);
		$('#supplier_email_edit').val(row.email);
		$('#supplier_address_edit').val(row.address);
	}

	// 添加供应商模态框数据校验
	function bootstrapValidatorInit() {
		$("#supplier_form,#supplier_form_edit").bootstrapValidator({
			message : 'This is not valid',
			feedbackIcons : {
				valid : 'glyphicon glyphicon-ok',
				invalid : 'glyphicon glyphicon-remove',
				validating : 'glyphicon glyphicon-refresh'
			},
			excluded : [ ':disabled' ],
			fields : {
				supplier_name : {
					validators : {
						notEmpty : {
							message : '供应商名称不能为空'
						}
					}
				},
				supplier_tel : {
					validators : {
						notEmpty : {
							message : '供应商电话不能为空'
						}
					}
				},
				supplier_email : {
					validators : {
						notEmpty : {
							message : '供应商E-mail不能为空'
						},
						regexp : {
							regexp : '^[^@\\s]+@([^@\\s]+\\.)+[^@\\s]+$',
							message : 'E-mail的格式不正确'
						}
					}
				},
				supplier_address : {
					validators : {
						notEmpty : {
							message : '供应商地址不能为空'
						}
					}
				},
				supplier_person : {
					validators : {
						notEmpty : {
							message : '供应商负责人不能为空'
						}
					}
				}
			}
		})
	}

	// 编辑供应商信息
	function editSupplierAction() {
		$('#edit_modal_submit').click(
				function() {
					$('#supplier_form_edit').data('bootstrapValidator')
							.validate();
					if (!$('#supplier_form_edit').data('bootstrapValidator')
							.isValid()) {
						return;
					}

					var data = {
						id : selectID,
						name : $('#supplier_name_edit').val(),
						personInCharge : $('#supplier_person_edit').val(),
						tel : $('#supplier_tel_edit').val(),
						email : $('#supplier_email_edit').val(),
						address : $('#supplier_address_edit').val()
					}

					// ajax
					$.ajax({
						type : "POST",
						url : requestPrefix + '/supplierManage/updateSupplier',
						dataType : "json",
						contentType : "application/json",
						data : JSON.stringify(data),
						success : function(response) {
							$('#edit_modal').modal("hide");
							var type;
							var msg;
							if (response.result == "success") {
								type = "success";
								msg = "供应商信息更新成功";
							} else if (resposne == "error") {
								type = "error";
								msg = "供应商信息更新失败"
							}
							infoModal(type, msg);
							tableRefresh();
						},
						error : function(response) {
						}
					});
				});
	}

	// 刪除供应商信息
	function deleteSupplierAction(){
		$('#delete_confirm').click(function(){
			var data = {
				"supplierID" : selectID
			}
			
			// ajax
			$.ajax({
				type : "GET",
				url : requestPrefix + "/supplierManage/deleteSupplier",
				dataType : "json",
				contentType : "application/json",
				data : data,
				success : function(response){
					$('#deleteWarning_modal').modal("hide");
					var type;
					var msg;
					if(response.result == "success"){
						type = "success";
						msg = "供应商信息删除成功";
					}else{
						type = "error";
						msg = "供应商信息删除失败";
					}
					infoModal(type, msg);
					tableRefresh();
				},error : function(response){
				}
			})
			
			$('#deleteWarning_modal').modal('hide');
		})
	}

	// 添加供应商信息
	function addSupplierAction() {
		$('#add_supplier').click(function() {
			$('#add_modal').modal("show");
		});

		$('#add_modal_submit').click(function() {
			var data = {
				name : $('#supplier_name').val(),
				personInCharge : $('#supplier_person').val(),
				tel : $('#supplier_tel').val(),
				email : $('#supplier_email').val(),
				address : $('#supplier_address').val()
			}
			// ajax
			$.ajax({
				type : "POST",
				url : requestPrefix + "/supplierManage/addSupplier",
				dataType : "json",
				contentType : "application/json",
				data : JSON.stringify(data),
				success : function(response) {
					$('#add_modal').modal("hide");
					var msg;
					var type;
					if (response.result == "success") {
						type = "success";
						msg = "供应商添加成功";
					} else if (response.result == "error") {
						type = "error";
						msg = "供应商添加失败";
					}
					infoModal(type, msg);
					tableRefresh();

					// reset
					$('#supplier_name').val("");
					$('#supplier_person').val("");
					$('#supplier_tel').val("");
					$('#supplier_email').val("");
					$('#supplier_address').val("");
					$('#supplier_form').bootstrapValidator("resetForm", true);
				},
				error : function(response) {
				}
			})
		})
	}

	var import_step = 1;
	var import_start = 1;
	var import_end = 3;
	// 导入供应商信息
	function importSupplierAction() {
		$('#import_supplier').click(function() {
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
				url : requestPrefix + "/supplierManage/importSupplier",
				secureuri: false,
				dataType: 'json',
				fileElementId:"file",
				success : function(data, status){
					var total = 0;
					var available = 0;
					var msg1 = "供应商信息导入成功";
					var msg2 = "供应商信息导入失败";
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

	// 导出供应商信息
	function exportSupplierAction() {
		$('#export_supplier').click(function() {
			$('#export_modal').modal("show");
		})

		$('#export_supplier_download').click(function(){
			var data = {
				searchType : search_type_supplier,
				keyWord : search_keyWord
			}
			var url = requestPrefix + "/supplierManage/exportSupplier?" + $.param(data)
			window.open(url, '_blank');
			$('#export_modal').modal("hide");
		})
	}

	// 导入供应商模态框重置
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
		<li>学生信息管理</li>
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
				<button class="btn btn-sm btn-default" id="add_supplier">
					<span class="glyphicon glyphicon-plus"></span> <span>添加供应商</span>
				</button>
				<button class="btn btn-sm btn-default" id="import_supplier">
					<span class="glyphicon glyphicon-import"></span> <span>导入</span>
				</button>
				<button class="btn btn-sm btn-default" id="export_supplier">
					<span class="glyphicon glyphicon-export"></span> <span>导出</span>
				</button>
			</div>
			<div class="col-md-5"></div>
		</div>

		<div class="row" style="margin-top: 15px">
			<div class="col-md-12">
				<table id="supplierList" class="table table-striped"></table>
			</div>
		</div>
	</div>
</div>

<!-- 添加供应商信息模态框 -->
<div id="add_modal" class="modal fade" table-index="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true"
	data-backdrop="static">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button class="close" type="button" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">添加供应商信息</h4>
			</div>
			<div class="modal-body">
				<!-- 模态框的内容 -->
				<div class="row">
					<div class="col-md-1"></div>
					<div class="col-md-8">
						<form class="form-horizontal" role="form" id="supplier_form"
							style="margin-top: 25px">
							<div class="form-group">
								<label for="" class="control-label col-md-4"> <span>供应商名称：</span>
								</label>
								<div class="col-md-8">
									<input type="text" class="form-control" id="supplier_name"
										name="supplier_name" placeholder="供应商名称">
								</div>
							</div>
							<div class="form-group">
								<label for="" class="control-label col-md-4"> <span>负责人姓名：</span>
								</label>
								<div class="col-md-8">
									<input type="text" class="form-control" id="supplier_person"
										name="supplier_person" placeholder="负责人姓名">
								</div>
							</div>
							<div class="form-group">
								<label for="" class="control-label col-md-4"> <span>联系电话：</span>
								</label>
								<div class="col-md-8">
									<input type="text" class="form-control" id="supplier_tel"
										name="supplier_tel" placeholder="联系电话">
								</div>
							</div>
							<div class="form-group">
								<label for="" class="control-label col-md-4"> <span>电子邮件：</span>
								</label>
								<div class="col-md-8">
									<input type="text" class="form-control" id="supplier_email"
										name="supplier_email" placeholder="电子邮件">
								</div>
							</div>
							<div class="form-group">
								<label for="" class="control-label col-md-4"> <span>联系地址：</span>
								</label>
								<div class="col-md-8">
									<input type="text" class="form-control" id="supplier_address"
										name="supplier_address" placeholder="联系地址">
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
				<button class="btn btn-success" type="button" id="add_modal_submit">
					<span>提交</span>
				</button>
			</div>
		</div>
	</div>
</div>

<!-- 导入供应商信息模态框 -->
<div class="modal fade" id="import_modal" table-index="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true"
	data-backdrop="static">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button class="close" type="button" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">导入供应商信息</h4>
			</div>
			<div class="modal-body">
				<div id="step1">
					<div class="row" style="margin-top: 15px">
						<div class="col-md-1"></div>
						<div class="col-md-10 col-sm-10">
							<div>
								<h4>点击下面的下载按钮，下载供应商信息电子表格</h4>
							</div>
							<div style="margin-top: 30px; margin-buttom: 15px">
								<a class="btn btn-info"
									href="commons/fileSource/download/supplierInfo.xlsx"
									target="_blank"> <span class="glyphicon glyphicon-download"></span>
									<span>下载</span>
								</a>
							</div>
						</div>
					</div>
				</div>
				<div id="step2" class="hide">
					<div class="row" style="margin-top: 15px">
						<div class="col-md-1"></div>
						<div class="col-md-10 col-sm-10">
							<div>
								<h4>请按照学生信息电子表格中指定的格式填写需要添加的一个或多个供应商信息</h4>
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
						<div class="col-md-1"></div>
						<div class="col-md-8 col-sm-10">
							<div>
								<div>
									<h4>请点击下面上传文件按钮，上传填写好的供应商信息电子表格</h4>
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
						<div class="col-md-1"></div>
						<div class="col-md-10"
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
						<div class="col-md-1"></div>
					</div>
					<div class="row">
						<div class="col-md-4"></div>
						<div class="col-md-4">
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
						<div class="col-md-4"></div>
					</div>
					<div class="row" style="margin-top: 10px">
						<div class="col-md-3"></div>
						<div class="col-md-6" style="text-align: center;">
							<h4 id="import_info"></h4>
						</div>
						<div class="col-md-3"></div>
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

<!-- 导出供应商信息模态框 -->
<div class="modal fade" id="export_modal" table-index="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true"
	data-backdrop="static">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button class="close" type="button" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">导出供应商信息</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-md-3" style="text-align: center;">
						<img src="media/icons/warning-icon.png" alt=""
							style="width: 70px; height: 70px; margin-top: 20px;">
					</div>
					<div class="col-md-8">
						<h3>是否确认导出供应商信息</h3>
						<p>(注意：请确定要导出的供应商信息，导出的内容为当前列表的搜索结果)</p>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button class="btn btn-default" type="button" data-dismiss="modal">
					<span>取消</span>
				</button>
				<button class="btn btn-success" type="button" id="export_supplier_download">
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
					<div class="col-md-4"></div>
					<div class="col-md-4">
						<div id="info_success" class=" hide" style="text-align: center;">
							<img src="media/icons/success-icon.png" alt=""
								style="width: 100px; height: 100px;">
						</div>
						<div id="info_error" style="text-align: center;">
							<img src="media/icons/error-icon.png" alt=""
								style="width: 100px; height: 100px;">
						</div>
					</div>
					<div class="col-md-4"></div>
				</div>
				<div class="row" style="margin-top: 10px">
					<div class="col-md-4"></div>
					<div class="col-md-4" style="text-align: center;">
						<h4 id="info_content"></h4>
					</div>
					<div class="col-md-4"></div>
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
					<div class="col-md-3" style="text-align: center;">
						<img src="media/icons/warning-icon.png" alt=""
							style="width: 70px; height: 70px; margin-top: 20px;">
					</div>
					<div class="col-md-8">
						<h3>是否确认删除该条供应商信息</h3>
						<p>(注意：若该供应商已经有仓库入库记录，则该供应商信息将不能删除成功。如需删除该供应商的信息，请先删除该供应商的入库记录)</p>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button class="btn btn-default" type="button" data-dismiss="modal">
					<span>取消</span>
				</button>
				<button class="btn btn-danger" type="button" id="delete_confirm")>
					<span>确认删除</span>
				</button>
			</div>
		</div>
	</div>
</div>

<!-- 编辑供应商信息模态框 -->
<div id="edit_modal" class="modal fade" table-index="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true"
	data-backdrop="static">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button class="close" type="button" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">编辑供应商信息</h4>
			</div>
			<div class="modal-body">
				<!-- 模态框的内容 -->
				<div class="row">
					<div class="col-md-1"></div>
					<div class="col-md-8">
						<form class="form-horizontal" role="form" id="supplier_form_edit"
							style="margin-top: 25px">
							<div class="form-group">
								<label for="" class="control-label col-md-4"> <span>供应商名称：</span>
								</label>
								<div class="col-md-8">
									<input type="text" class="form-control" id="supplier_name_edit"
										name="supplier_name" placeholder="供应商名称">
								</div>
							</div>
							<div class="form-group">
								<label for="" class="control-label col-md-4"> <span>负责人姓名：</span>
								</label>
								<div class="col-md-8">
									<input type="text" class="form-control"
										id="supplier_person_edit" name="supplier_person"
										placeholder="负责人姓名">
								</div>
							</div>
							<div class="form-group">
								<label for="" class="control-label col-md-4"> <span>联系电话：</span>
								</label>
								<div class="col-md-8">
									<input type="text" class="form-control" id="supplier_tel_edit"
										name="supplier_tel" placeholder="联系电话">
								</div>
							</div>
							<div class="form-group">
								<label for="" class="control-label col-md-4"> <span>电子邮件：</span>
								</label>
								<div class="col-md-8">
									<input type="text" class="form-control"
										id="supplier_email_edit" name="supplier_email"
										placeholder="电子邮件">
								</div>
							</div>
							<div class="form-group">
								<label for="" class="control-label col-md-4"> <span>联系地址：</span>
								</label>
								<div class="col-md-8">
									<input type="text" class="form-control"
										id="supplier_address_edit" name="supplier_address"
										placeholder="联系地址">
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