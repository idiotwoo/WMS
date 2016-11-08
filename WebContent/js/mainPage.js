var ParentMenuModel = '<div class="panel panel-default currentP">'
		+ '<div class="panel-heading">'
		+ '<h4 class="panel-title">'
		+ '<a href="" data-toggle="collapse" data-parent="#accordion" class="parentMenuTitle collapseHead"></a>'
		+ '<div class="pull-right	"><span class="caret"></span></div>'
		+ '</h4>'
		+ '</div>'
		+ '<div id="" class="panel-collapse collapse collapseBody"><div class="panel-body"><ul class="list-group curList">'
		+ '</ul></div></div>';
var subMenuModel = '<li class="list-group-item curItem"><a href="javascript:void(0)" id="" class="menu_item" name=""></a></li>';

var requestPrefix;
$(function() {
	sideBarInit();
	signOut();
	getRequestPrefix();
});

// 获取请求前缀
function getRequestPrefix(){
	requestPrefix = $('#requestPrefix').text();
}

// 加载侧边栏
function sideBarInit() {
	$.ajax({
		type : "GET",
		url : "commons/pageLoad/loadMenus",
		dataType : "json",
		contentType : "application/json",
		success : function(response) {
			var num = 1;
			$.each(response, function(key, value) {// parentTitle
				$("#accordion").append(ParentMenuModel);
				$(".currentP .parentMenuTitle").text(key);
				var collapseHref = "#collapse" + num;
				var collapseID = "collapse" + num;
				$(".currentP .collapseHead").attr("href", collapseHref);
				$(".currentP .collapseBody").attr("id", collapseID);

				$.each(value, function(index, subMenu) {
					$(".curList").append(subMenuModel);
					var title = subMenu[0];
					var url = subMenu[1];
					$(".curItem a").text(title);
					$(".curItem a").attr("name", url);
					$(".curItem").removeClass("curItem");
				})
				$(".curList").removeClass("curList");

				$(".currentP").removeClass("currentP");
				num++;
			});
			$("#collapse1").addClass("in");
			menuClick();
		}
	});

}

// 侧边栏连接点击动作
function menuClick() {
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
			url : "commons/account/signOut",
			dataType : "json",
			contentType : "application/json",
			success:function(response){
				window.location.reload(true);
			},error:function(response){
				
			}
		})
	})
}