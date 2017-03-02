$(function() {
	menuClickAction();
	welcomePageInit();
	signOut();
	getRequestPrefix();
	homePage();
});

// 获取请求前缀
function getRequestPrefix(){
	requestPrefix = $('#requestPrefix').text();
}

// 加载欢迎界面
function welcomePageInit(){
	$('#panel').load('pagecomponent/welcomePage.jsp');
}

// 跳回首页
function homePage(){
	$('.home').click(function(){
		$('#panel').load('pagecomponent/welcomePage.jsp');
	})
}

// 侧边栏连接点击动作
function menuClickAction() {
	$(".menu_item").click(function() {
		var url = $(this).attr("name");
		$('#panel').load(url);
	})
}

// 注销登陆
function signOut() {
	$("#signOut").click(function() {
		$.ajax({
			type : "GET",
			url : "account/logout",
			dataType : "json",
			contentType : "application/json",
			success:function(response){
				window.location.reload(true);
			},error:function(response){
				
			}
		})
	})
}