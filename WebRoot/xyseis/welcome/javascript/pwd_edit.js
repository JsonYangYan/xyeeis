function getUserName(){
	$.ajax({
		url:"../../servlet/SchoolUserServlet",
		data:{
			"operation" : "getUserName",
		},
		type:"post",
		success:function(data){
			$("#userName").val(data);
		
		},
		error:function(){
			alert("获取信息失败");
		}
	});
}
//检验原始密码是否填写正确
$("#befpassword").blur(function(){
	checkBefPassword();
});

function checkBefPassword(){
	var result = true;
	$.ajax({
		url:"../../servlet/SchoolUserServlet",
		data:{
			"operation" : "checkBefPwd",
			"val":$("#befpassword").val()
		},
		type:"post",
		async:false,
		success:function(data){
				if(data == "error"){
					$("#beflabel").html("原始密码填写错误");
					$("#befpassword").focus();
					result = false;
				}else{
					$("#beflabel").html("");
				}
				
		},
		error:function(){
			alert("获取信息失败");
			result = false;
		}
	});
	return result;
}
//检验密码1
$("#password1").blur(function(){
	checkPassword1();
});
function checkPassword1(){
	var pwdone = $("#password1").val();
	if(pwdone == ''){
		$("#one_pwd").html("密码不能为空");
		return false;
	}else if(pwdone.length<=4){
		$("#one_pwd").html("密码长度不能小于4位");
		return false;
	}else{
		$("#one_pwd").html("");
	}
	return true;
}
//验证确认密码
$("#password2").blur(function(){
	checkPassword2();
});
function checkPassword2(){
	var pwdone = $("#password2").val();
	if(pwdone == ''){
		$("#two_pwd").html("密码不能为空");
		return false;
	}else if(pwdone.length<=4){
		$("#two_pwd").html("密码长度不能小于4位");
		return false;
	}else if($("#password1").val() != $("#password2").val()){
		$("#two_pwd").html("确认密码和密码填写不一致");
		return false;
	}else{
		$("#two_pwd").html("");
	}
	return true;
}
$("#cancel").click(function(){
	window.location.href="welcome_edit.html";
});

//提交
$("#submit").click(function(){

	if(checkBefPassword() && checkPassword1() && checkPassword2() ){
		$.ajax({
			url:"../../servlet/SchoolUserServlet",
			type:"post",
			data:{
				"operation":"updatepwd",
				"pwd":$("#password1").val()
				
			},
			success:function(data){
				if(data == "ok"){
					alert("修改成功");
					window.location.href="welcome_edit.html";
				}else{
					alert("修改失败");
				}
			},
			error:function(){
				alert("连接失败");
			}
		});
	}
});

init();

function init(){

	getUserName();
}
$(".back").click(function(){
	window.history.back(-1); 
	console.log(111)
})
