var result=true;  
//获取学校名字
function checkSchoolName() {
	$.ajax({
		url:"../../servlet/SchoolUserRegister",
		data:{
			"operation":"getSchoolName",
			"area_id":$("#area_name").val(),
		},
		type:"post",
		async: false, 
		success:function(date){
//			if(date==ok){
//				console.log(date)
				var json = JSON.parse(date);
				var temp_arr =  new Array();
				for(var i=0; i<json.length; i++){
					temp_arr.push(json[i]);
				}
//				console.log(temp_arr)
				var select=$("<select id='school_info'>")
				var html ='<option>请选择学校</option>';
				for(var i = 0; i < temp_arr.length; i++) {
				
					html +="<option>" + temp_arr[i] + "</option>";
				}
				html +="</select>";
				$("#school_info").html(html);
//				alert($("#area_name").val())
			//ok 没有被注册
			//error 被注册
		},
		error:function(){
			alert("数据库连接错误");
		}
	});
}
$("#area_name").change(function(){
//	$("#school_name").empty();
	checkSchoolName();
	if($("#area_name").val()!=0){
		$("#areaname_new").html("");
	}
	
})
$("#school_info").change(function(){
					checkIsReg(); 
//					register();
				});
//用户名
$("#userName").blur(function() {
	var username=$(this).val();
	console.log(username);
	if(username == ''){
		$("#usename_new").html("用户名不能为空，请输入用户名！");
		$("#userName").focus();
		return;
	}
	checkUserName(username);
	
});
//查看用户名是否注册
function checkUserName(user_name) {
	$.ajax({
		url:"../../servlet/SchoolUserRegister",
		data:{
			"operation":"checkUserName",
			"userName":user_name,
		},
		type:"post",
		async: false, 
		success:function(date){
			if(date == "error"){
				$("#usename_new").html("用户名已被注册,请重新输入！");
				$("#userName").focus();
				result = false;
			}else{
				$("#usename_new").html("");
			}
			//ok 没有被注册
			//error 被注册
		},
		error:function(){
			alert("数据库连接错误");
		}
	});
}

//上面学校该地区  是否被注册
function checkIsReg() {
	$.ajax({
		url:"../../servlet/SchoolUserRegister",
		data:{
			"operation":"checkIsReg",
			"area_id":$("#area_name").val(),
			"schoolName":$("#school_info").val(),
		},
		type:"post",
		async: false, 
		success:function(date){
			//ok 没有被注册
			//error 被注册
//			alert(data);
			if(date == "error"){
				$("#schoolname_new").html("学校已被注册,请重新选择！");
//				$("#userName").focus();
				result = false;
			}else{
				$("#schoolname_new").html("");
			}
		},
		error:function(){
			alert("数据库连接错误");
		}
	});
}
//判断两次密码是否一致

$("#password_one").blur(function(){
	var pwd_one=$("#password_one").val();
	if(pwd_one.length<=4){
		$("#beflabel").html("密码长度不能小于4位！");
	}else{
		$("#beflabel").html("");
	}
});
$("#password_two").blur(function () {
	var pwd_one=$("#password_one").val();
	var pwd_two=$("#password_two").val();
	if(pwd_one!=pwd_two){
		$("#one_pwd").html("两次输入密码不一致，请重新输入！");
	}else{
		$("#usename_new").html("");
	}
});


///最终注册验证
function register(){
	$.ajax({
		url:"../../servlet/SchoolUserRegister",
		data:{
			"operation":"register",
			"area_id":$("#area_name").val(),
			"schoolName":$("#school_info").val(),
			"userName":$("#userName").val(),
			"pwd":$("#password_one").val()
		},
		type:"post",
//		async: false, 
		success:function(data){
			if(data=="ok"){
				if($("#school_info").val()==0){
					result= false; 
				}else{
					window.document.location.href = "../login.html";
				}
			}
		},
		error:function(){
			alert("数据库连接错误");
		}
	});  
}
$("#login").click(function(){
	//判断两次密码是否一致
	var pwd_one=$("#password_one").val();
	var pwd_two=$("#password_two").val();
	if(pwd_one!=pwd_two){
		$("#one_pwd").html("两次输入密码不一致，请重新输入！");
	}else{
		$("#one_pwd").html("");
	}	
	var username=$("#userName").val();
	
	
	if($("#area_name").val() == 0){
		$("#areaname_new").html("请选择地区！");
	}else{
		$("#areaname_new").html("");
	}
	if($("#school_info").val()== 0){
		$("#schoolname_new").html("请选择学校！");
	}
	if($("#userName").val()==''){
		$("#usename_new").html("请输入用户名！");
	}
	if($("#password_one").val()==''){
		$("#beflabel").html("请输入密码！");
	}
	if($("#area_name").val()==0 || $("#school_info").val()== 0 ||$("#userName").val()=='' || $("#password_one").val()==''){
		return;
	}
//	checkUserName(username);
//	checkSchoolName();
//	checkIsReg();
	if(result){
		register();
	}
	
	
})


