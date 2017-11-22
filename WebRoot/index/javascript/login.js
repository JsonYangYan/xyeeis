var longin = function() {

 	//向服务器请求登陆
	if($("#username").val() === "") {
//		alert("用户名不能为空");
		var txt=  "用户名不能为空！";
		window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
		$("#username").focus();
	}else{
		if($("#password").val() === "") {
//			alert("密码不能为空");
			var txt=  "密码不能为空！";
			window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
			$("#password").focus();
		}else{
			//若用户名密码都不为空，则登陆
			$.ajax({
				url: "/xyeeis/servlet/AccountValidatorServlet",
			   	type: "POST",
			   	data: {
			   		"operation": "login",
					"name": $("#username").val(),
					"password": $("#password").val(),
				},
					success: function(data) {
					if(data == "username error") {
//						alert("请输入正确用户名!");
						var txt=  "请输入正确用户名！";
						window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
				      	$("#username").val("");
				      	$("#password").val("");
			      		$("#username").focus();
					}
					else if(data == "password error") {
//						alert("请输入正确密码！");
						var txt=  "请输入正确密码！";
						window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
				      	$("#password").val("");
			      		$("#password").focus();
		            }
		            else if(data == "userid error") {
//						alert("请选择正确的登陆权限！");
						var txt=  "请选择正确的登录权限！";
						window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
				      	$("#password").val("");
			      		$("#password").focus();
		            }
		            else if(data == "admin") {
						goToAdminPage();
					}else if(data == "expert") {
						goToExpertPage();
					}
					else if(data == "user") {
						goToUserPage();
					}else if(data == "su") {
						goToSuPage();
					}else if(data == "sl") {
						goToSlPage();
					}
				},
				error: function() {
//					alert("连接服务器失败！");
					var txt=  "连接服务器失败！";
					window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
			      	$("#username").val("");
			      	$("#password").val("");
			      	$("#username").focus();
				}
			});
		};
	};
	// 跳转到超级管理员（开发）
	var goToSuPage = function() {
		window.document.location.href = "admin/html/admin.html";
	};
	//跳转到二级管理员（各县区）
	var goToSlPage = function() {
		window.document.location.href = "qxeeis/sladmin/html/sladmin.html";
	};
	//页面跳转到一级管理员（襄阳电教馆）
	var goToAdminPage = function() {
		window.document.location.href = "main/html/main.html";
	};
};



//事件绑定
var bindLoginEvent = function() {	
	
	//点击登陆按钮
   	$(".login_btn").live("click", function(){
		 longin();
	});
   	   	
	//在输入框敲回车
   	$("#password").live("keypress", function(event){
		switch(event.keyCode) {
			case 13:
			$(".login_btn").addClass("login_key");
			setTimeout(function(){
				$(".login_btn").removeClass("login_key");
				longin();
			},100);
			
			break;
		}
	});

};

var init = function() {
	$("#footer").load("base/html/footer.html");
	bindLoginEvent();
	$("#username").focus();
};

init();