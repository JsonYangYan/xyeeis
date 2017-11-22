var longin = function() {

 	//向服务器请求登陆
	if($("#username").val() === "") {
		//alert("用户名不能为空");
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
				url: "../servlet/LoginServlet",
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
		            else if(data == "firstlogin") {
						goToWelcomePage();
					}
		            else if(data == "againlogin") {
						goToEditWelcomePage();
					}else if(data == 'tpagainlogin'){
						goToTpEditWelcomePage();
					}else if(data == 'tp_firstlogin'){
						goToTpFirstLogin();
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
	
	//页面跳转到信息欢迎页
	var goToWelcomePage = function() {
		window.document.location.href = "welcome/welcome.html?type=normal";
	};
	var goToTpFirstLogin = function(){
		window.document.location.href = "welcome/welcome.html?type=tp";
	}
	//页面跳转到信息欢迎编辑页面
	var goToEditWelcomePage = function() {
		
		window.document.location.href = "welcome/welcome_edit.html";
	};
	
	//页面跳转到教学点信息页面
	var goToTpEditWelcomePage = function() {
		window.document.location.href = "welcome/tp_welcome_edit.html";
	};
};

//事件绑定
var bindLoginEvent = function() {	
	
	//点击登陆按钮
   	$("#login_action").live("click", function(){
		 longin();
	});
	
	//在输入框敲回车
   	$("#password").live("keypress", function(event){
		switch(event.keyCode) {
			case 13:
			$("#login_action").addClass("login_key");
			setTimeout(function(){
				$("#login_action").removeClass("login_key");
				longin();
			},100);
			
			break;
		}
	});

	
	
	
};
$("#register_action").click(function(){

	window.location.href="welcome/register.html";
	
});

var init = function() {
	$("#footer").load("../base/html/footer.html");
	bindLoginEvent();
	$("#username").focus();
};

init();

var wait=60;  
function time(o) {
        if (wait == 0) {  
            o.removeAttribute("disabled");            
            $(o).html("忘记密码？");  
            wait = 60;  
        } else {  
            o.setAttribute("disabled", true);  
            $(o).html("重新发送(" + wait + ")");  
            wait--;  
            setTimeout(function() {
                time(o)  
            },  
            1000)  
        }  
}  


//忘记密码按钮点击事件
function count_down(obj){
	var userName = $("#username").val();
	if(userName == ''){
		alert("请填写正确用户名");
		return false;
	}
	$.ajax({
		url:"../servlet/ForgotPasswordServlet",
		type:"post",
		data:{
			"userName":userName,
			"operation" : "sendeamil",
		},
		success:function(data){
			if(data=="successSend"){
				alert("请接收邮件");
			}else{
				alert("没有此用户");
			}
		},
		error:function(){
			
		}
	});
	time(obj);
	
}

