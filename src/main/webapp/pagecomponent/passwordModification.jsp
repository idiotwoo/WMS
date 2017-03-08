<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<script>
	$(function() {
		bootstrapValidatorInit();
	});

	function bootstrapValidatorInit(){
		$('#form').bootstrapValidator({
			message:'This value is not valid',
			feedbackIcons:{
				valid:'glyphicon glyphicon-ok',
				invalid:'glyphicon glyphicon-remove',
				validating:'glyphicon glyphicon-refresh'
			},
			excluded: [':disabled'],
			fields:{// 字段验证
				oldPassword:{// 原密码
					validators:{
						notEmpty:{
							message:'输入不能为空'
						},
						callback:{}
					}
				},
				newPassword:{// 新密码
					validators:{
						notEmpty:{
							message:'输入不能为空'
						},
						stringLength:{
							min:6,
							max:16,
							message:'密码长度为6~16位'
						},
						callback:{}
					}
				},
				newPassword_re:{// 重复新密码
					validators:{
						notEmpty:{
							message:'输入不能为空'
						},
						identical:{
							field:'newPassword',
							message:'两次密码不一致'
						}
					}
				}
			}
		})
		.on('success.form.bv',function(e){
			// 禁用默认表单提交
			e.preventDefault();
			
			// 获取 form 实例
			var $form = $(e.target);
			// 获取 bootstrapValidator 实例
			var bv = $form.data('bootstrapValidator');

			var userID = $('#userID').html();
			var oldPassword = $('#oldPassword').val();
			var newPassword = $('#newPassword').val();
			var rePassword = $('#newPassword_re').val();

			oldPassword = passwordEncrying(userID, oldPassword);
			newPassword = passwordEncrying(userID, newPassword);
			rePassword = passwordEncrying(userID, rePassword);
			var data = {
					"oldPassword" : oldPassword,
					"newPassword" : newPassword,
					"rePassword" : rePassword
				}

			// 将数据通过 AJAX 发送到后端
			$.ajax({
				type: "POST",
				url:"account/passwordModify",
				dataType:"json",
				contentType:"application/json",
				data:JSON.stringify(data),
				success:function(response){
					// 接收并处理后端返回的响应e'd'
					if(response.result == "error"){
						var errorMessage;
						if(response.errorMsg == "passwordError"){
							errorMessage = "密码错误";
							field = "oldPassword"
						}else if(response.errorMsg == "passwordUnmatched"){
							errorMessage = "密码不一致";
							field = "newPassword"
						}

						$("#oldPassword").val("");
						$("#newPassword").val("");
						$("#newPassword_re").val("");
						bv.updateMessage(field,'callback',errorMessage);
						bv.updateStatus(field,'INVALID','callback');
					}else{
						// 否则更新成功，弹出模态框并清空表单
						$('#passwordEditSuccess').modal('show');
						$('#reset').trigger("click");
						$('#form').bootstrapValidator("resetForm",true); 
					}
					
				},
				error:function(response){
					//window.location.href = "./";
					location.reload();
				}
			});
		})
	}

	// 密码加密模块
	function passwordEncrying(userID,password){
		var str1 = $.md5(password);
		//var str2 = $.md5(str1 + userID);
		return str1;
	}

</script>
<!-- 修改密码面板 -->
<div class="panel panel-default">
	<!-- 面包屑 -->
	<ol class="breadcrumb">
		<li>修改密码</li>
	</ol>

	<div class="panel-body">
		<!--  修改密码主体部分 -->
		<div class="row">
			<div class="col-md-4 col-sm-2"></div>
			<div class="col-md-4 col-sm-8">

				<form action="" class="form-horizontal" style=""
					role="form" id="form">
					<div class="form-group">
						<label for="" class="control-label col-md-4 col-sm-4"> 用户ID: </label>
						<div class="col-md-8 col-sm-8">
							<span class="hidden" id="userID">${sessionScope.userID }</span>
							<p class="form-control-static">${sessionScope.userID }</p>
						</div>
					</div>

					<div class="form-group">
						<label for="" class="control-label col-md-4 col-sm-4"> 输入原密码: </label>
						<div class="col-md-8 col-sm-8">
							<input type="password" class="form-control" id="oldPassword"
								name="oldPassword">
						</div>
					</div>

					<div class="form-group">
						<label for="" class="control-label col-md-4 col-sm-4"> 输入新密码: </label>
						<div class="col-md-8 col-sm-8">
							<input type="password" class="form-control" id="newPassword"
								name="newPassword">
						</div>
					</div>

					<div class="form-group">
						<label for="" class="control-label col-md-4 col-sm-4"> 确认新密码: </label>
						<div class="col-md-8 col-sm-8 has-feedback">
							<input type="password" class="form-control" id="newPassword_re"
								name="newPassword_re">
						</div>
					</div>

					<div>
						<div class="col-md-4 col-sm-4"></div>
						<div class="col-md-4 col-sm-4">
							<button type="submit" class="btn btn-success">
								&nbsp;&nbsp;&nbsp;&nbsp;确认修改&nbsp;&nbsp;&nbsp;&nbsp;</button>
						</div>
						<div class="col-md-4 col-sm-4"></div>
					</div>
					<input id="reset" type="reset" style="display:none">
				</form>

			</div>
			<div class="col-md-4 col-sm-2"></div>
		</div>

		<div class="row">
			<div class="col-md-3 col-sm-1"></div>
			<div class="col-md-6 col-sm-10">
				<div class="alert alert-info" style="margin-top: 50px">
					<p>登录密码修改规则说明：</p>
					<p>1.密码长度为6~16位，至少包含数字、字母、特殊符号中的两类，字母区分大小写</p>
					<p>2.密码不可与账号相同</p>
				</div>
			</div>
			<div class="col-md-3 col-sm-1"></div>
		</div>
	</div>
</div>

<!-- 更新成功模态框 -->
<div class="modal fade" id="passwordEditSuccess"
	tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close"
					data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">
					提示
				</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-md-4"></div>
					<div class="col-md-4">
						<div style="text-align: center;">
							<img src="media/icons/success-icon.png" alt=""
								style="width: 100px; height: 100px;">
						</div>
					</div>
					<div class="col-md-4"></div>
				</div>
				<div class="row" style="margin-top: 10px">
					<div class="col-md-4"></div>
					<div class="col-md-4" style="text-align:center;"><h4>密码修改成功</h4></div>
					<div class="col-md-4"></div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">
					<span>关闭</span>
				</button>
			</div>
		</div>
	</div>
</div>