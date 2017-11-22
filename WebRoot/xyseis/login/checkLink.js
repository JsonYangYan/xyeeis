var sid = GetQueryString("sid");
var userName = GetQueryString("userName");

$.ajax({
		url:"../servlet/ForgotPasswordServlet",
		type:"post",
		data:{
			"userName":userName,
			'sid':sid,
			"operation" : "checklink",
		},
		success:function(data){
			console.log(data);
			if(data == "success") {
				$(".success").show();
				$(".error").hide();
				//$(".success").html("正确");
			}else{
				$(".error").show();
				$(".success").hide();
				if(data == "linkoutTime"){
					$(".resend a").html("时间超时");
				}
				if(data == "userNotExist") {
					$(".resend a").html("用户不存在");
				}
				if(data == "linkerror") {
					$(".resend a").html("连接不正确");
				}
				if(data == "inkIncomplete") {
					$(".resend a").html("连接不完整");
				}
			}
						
		},
		error:function(){
			
		}
});

function GetQueryString(name)
{
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
}

$(".confirm").click(function(){
	var pwd_one = $("#pwd_one").val();
	var pwd_two = $("#pwd_two").val();
	if(pwd_one == '' || pwd_two == '') {
		
		alert("密码不能为空");
		return false;
	}
	if(pwd_one != pwd_two) {
		
		alert("两次密码输入不一致");
		return false;
	}
	//验证密码长度和数字 字母组合
	if(checkRegexp(pwd_one, pwd_two)) {
		$.ajax({
			type : "POST",
			url : "../servlet/ForgotPasswordServlet",
			data : {
				"operation" : "resetpassword",
				"password" : pwd_one,
				"userName" : userName,
			},
			success : function(data) {
				console.log(data);
				if (data) {
					alert("修改密码成功");
					window.location.href = "login.html";
				}

			},
			error : function(data) {
				alert("修改密码失败");
			}

		});
		
	}else{
		alert("请正确输入6-12位字母、数字组合密码。");
	}
	
});

//验证密码为a-zA-Z0-9 6-12位
function checkRegexp(passVal1, passVal2) {
	if (passVal1.length<6||passVal1.length>12) {
		return false;
	}
	var regexp = /^(?![^a-zA-Z]+$)(?!\D+$).{6,12}/;
	if (!(regexp.test(passVal1))) {
		return false;
	}
	return true;
}