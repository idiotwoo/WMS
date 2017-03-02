<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登陆</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/model/login/login.css">
</head>
<body>
	<!-- 定义容器 -->
	<div class="container">
		<div class="row">
			<div class="col-md-4 col-sm-3"></div>

			<!-- 这一列为登陆表单 -->
			<div class="col-md-4 col-sm-6">
				<div class="panel panel-default">

					<!-- 登陆面板的标题 -->
					<div class="panel-title" style="text-align: center">
						<h2>登录</h2>
					</div>

					<!-- 登陆面板的主体 -->
					<div class="panel-body">

						<!-- 表单 -->
						<form id="login_form" class="form-horizontal" style="">

							<div class="form-group">
								<label class="control-label col-md-4 col-sm-4">用户ID：</label>
								<div class="col-md-7 col-sm-7">
									<input type="text" id="userID" class="form-control"
										placeholder="用户ID" name="userID" />
								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-md-4 col-sm-4"> <!-- <span class="glyphicon glyphicon-lock"></span> -->
									密码：
								</label>
								<div class="col-md-7 col-sm-7">
									<input type="password" id="password" class="form-control"
										placeholder="密码" name="password">
								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-md-4 col-sm-4"> <!-- <span class="glyphicon glyphicon-lock"></span> -->
									验证码：
								</label>
								<div class="col-md-5 col-sm-4">
									<input type="text" id="checkCode" class="form-control"
										placeholder="验证码" name="checkCode">
								</div>
								<div>
									<img id="checkCodeImg" alt="checkCodeImg"
										src="account/checkCode/1">
								</div>
							</div>

							<div>
								<div class="col-md-4 col-sm-4"></div>
								<div class="col-md-4 col-sm-4">
									<button id="submit" type="submit" class="btn btn-primary">
										&nbsp;&nbsp;&nbsp;&nbsp;登陆&nbsp;&nbsp;&nbsp;&nbsp;</button>
								</div>
								<div class="col-md-4 col-sm-4"></div>
							</div>
						</form>
					</div>
				</div>
			</div>

			<div class="col-md-4 col-sm-3"></div>
		</div>
	</div>

	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/jquery-2.2.3.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/bootstrapValidator.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/jquery.md5.js"></script>

	<script>
		$(function() {
			validatorInit();
			refreshCheckCode();
		});

		// 刷新图形验证码
		function refreshCheckCode() {
			$('#checkCodeImg').click(function() {
				var timestamp = new Date().getTime();
				$(this).attr("src", "account/checkCode/" + timestamp)
			})
		}

		// 登陆信息加密模块
		function infoEncrypt(userID, password, checkCode) {
			var str1 = $.md5(password);
			var str2 = $.md5(str1 + userID);
			var str3 = $.md5(str2 + checkCode.toUpperCase());
			return str3;
		}

		function validatorInit() {
			$('#login_form').bootstrapValidator({
				message : 'This value is not valid',
				feedbackIcons : {
					valid : 'glyphicon glyphicon-ok',
					invalid : 'glyphicon glyphicon-remove',
					validating : 'glyphicon glyphicon-refresh'
				},
				fields : {
					userID : {
						validators : {
							notEmpty : {
								message : '用户名不能为空'
							},regexp: {
		                        regexp: '[0-9]+',
		                        message: '只允许输入数字'
		                    },
							callback : {}
						}
					},
					password : {
						validators : {
							notEmpty : {
								message : '密码不能为空'
							},
							callback : {}
						}
					},
					checkCode : {
						validators : {
							notEmpty : {
								message : '验证码不能为空'
							}
						}
					}
				}
			})
			.on('success.form.bv', function(e) {
				// 禁用默认表单提交
				e.preventDefault();

				// 获取 form 实例
				var $form = $(e.target);
				// 获取 bootstrapValidator 实例
				var bv = $form.data('bootstrapValidator');

				// 发送数据到后端 进行验证
				var userID = $('#userID').val();
				var password = $('#password').val();
				var checkCode = $('#checkCode').val();

				// 加密
				password = infoEncrypt(userID, password, checkCode)

				var data = {
					"id" : userID,
					"password" : password,
				}
				$.ajax({
					type:"POST",
					url:"account/login",
					dataType:"json",
					contentType:"application/json",
					data:JSON.stringify(data),
					success:function(response){
						// 接收到后端响应
						
						// 分析返回的 JSON 数据
						if(response.result == 'error'){
							var errorMessage;
							var field;
							if(response.msg == "unknownAccount"){
								errorMessage = "用户名错误";
								field = "userID";
							}
							else if(response.msg == "incorrectCredentials"){
								errorMessage = "密码或验证码错误";
								field = "password";
								$('#password').val("");
							}
								
							// 更新 callback 错误信息，以及为错误对应的字段添加 错误信息
							bv.updateMessage(field,'callback',errorMessage);
							bv.updateStatus(field,'INVALID','callback');
							bv.updateStatus("checkCode",'INVALID','callback');
							
							$('#checkCodeImg').attr("src","account/checkCode/" + new Date().getTime());
							$('#checkCode').val("");
						}else{
							// 页面跳转
							//var URL = response.url;
							//if(URL != null){
								window.location.href = "/WMS";
							//}
							
						}
					},
					error:function(data){
						// 处理错误
					}
				});
			});
		}
	</script>
</body>
</html>