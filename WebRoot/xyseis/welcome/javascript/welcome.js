var school_type_ = "normal";//学校类型，normal正常普通学校，tp教学点
$(".noteawoke").hide();
$(".noteok").hide();
$("#relation").blur(function() {
	var tmptxt = $("#relation").val();
	
	var reg = new RegExp(/^[\u2E80-\u9FFF]+$/);
	var result = tmptxt.match(reg);
	if (result == null) {
		$("#warningRelationRightInfo").hide();
		$("#warningRelationErrorInfo").show();
		
		$(this).attr("value","");
    } else {
    	$("#warningRelationErrorInfo").hide();
    	$("#warningRelationRightInfo").show();
    }
});
$("#mobile").blur(function() {
	var tmptxt = $("#mobile").val();
	
	//var reg = new RegExp(/^1[0-9]{10}$/);		//不验证第二位规则	
	//第一位是【1】开头，第二位则则有【3,4,5,7,8】，第三位则是【0-9】，第三位之后则是数字【0-9】
	var reg = new RegExp(/^1[3|4|5|7|8][0-9]{9}$/);	//验证手机号码
	var reg2 = new RegExp(/^0\d{2,3}-?\d{7,8}$/);	//验证座机
	var result2 = tmptxt.match(reg2);
	
	var result = tmptxt.match(reg);
	if (result == null && result2 == null) {	//若手机号码和座机号码都填写错误
		$("#warningMobileRightInfo").hide();
		$("#warningMobileErrorInfo").show();
		$(this).attr("value","");
    } else {
    	$("#warningMobileErrorInfo").hide();
    	$("#warningMobileRightInfo").show();
    }
});
$("#email").blur(function() {
	var tmptxt = $("#email").val();
	var reg = new RegExp(/^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/);
	var result = tmptxt.match(reg);
    if (result == null) {
    	$("#warningEmailRightInfo").hide();
    	$("#warningEmailErrorInfo").show();
    	$(this).attr("value","");
    } else {
    	$("#warningEmailErrorInfo").hide();
    	$("#warningEmailRightInfo").show();    	
    }
});

//正则验证数字
$("#class_num").blur(function() {	
	var tmptxt = $("#class_num").val();
	var reg = new RegExp(/^[1-9][0-9]{0,7}$/);
	var result = tmptxt.match(reg);
    if (result == null) {
    	$("#warningCNRightInfo").hide();
    	$("#warningCNErrorInfo").show();
    	$(this).attr("value","");
    } else {
    	$("#warningCNErrorInfo").hide();
    	$("#warningCNRightInfo").show();   	
    }
});
$("#staffteacher").blur(function() {
	var tmptxt = $("#staffteacher").val();
	var reg = new RegExp(/^[1-9][0-9]{0,7}$/);
	var result = tmptxt.match(reg);
    if (result == null) {
    	$("#warningSTRightInfo").hide();
    	$("#warningSTErrorInfo").show();
    	$(this).attr("value","");
    } else {
    	$("#warningSTErrorInfo").hide();
    	$("#warningSTRightInfo").show();   	
    }
});
$("#nativestudent").blur(function() {	
	var tmptxt = $("#nativestudent").val();
	var reg = new RegExp(/^[1-9][0-9]{0,7}$/);
	var result = tmptxt.match(reg);
    if (result == null) {
    	$("#warningNSRightInfo").hide();
    	$("#warningNSErrorInfo").show();
    	$(this).attr("value","");
    } else {
    	$("#warningNSErrorInfo").hide();
    	$("#warningNSRightInfo").show();   	
    }
});

/*
 * 表单提交验证和服务器请求
 */

$("#add_information").click(function() {
	var a = $("#area").val();
	var b = $("input[name='school_addr']").val();	//学校名称
	var c = $("input[name='staffteacher']").val();	//学校在职教师人数
	var s = $("input[name='school_area']:checked").val();
	var d = $("input[name='nativestudent']").val();	//学校在籍学生人数	
	var r = $("input[name='relation']").val();	//联系人姓名
	var m = $("input[name='mobile']").val();	//手机
	var e = $("input[name='email']").val();		//邮箱
	var n = $("input[name='class_num']").val();		//该学校的班级数
	var t = $("input[name='school_type']:checked").val();	//小学、初中、高中、其他
	
		//问卷填写人 的验证
	var flag_relation = true;
		var tmptxt = $("#relation").val();
		var reg = new RegExp(/^[a-zA-Z\u4e00-\u9fa5]+$/);
		var result = tmptxt.match(reg);
		if (result == null) {
			flag_relation = false;
	    }else{
	    	flag_relation = true;
	    }
		
		//电话验证
		var flag_mobile= true;
		var tmptxt = $("#mobile").val();
		//var reg = new RegExp(/^1[0-9]{10}$/);		//不验证第二位规则	
		//第一位是【1】开头，第二位则则有【3,4,5,7,8】，第三位则是【0-9】，第三位之后则是数字【0-9】
		var reg = new RegExp(/^1[3|4|5|7|8][0-9]{9}$/);	//验证手机号码
		var reg2 = new RegExp(/^0\d{2,3}-?\d{7,8}$/);	//验证座机
		var result2 = tmptxt.match(reg2);
		
		var result = tmptxt.match(reg);
		if (result == null && result2 == null) {	//若手机号码和座机号码都填写错误
			flag_mobile = false;
	    }else{
	    	flag_mobile = true;
	    }
		
		//邮箱验证
	    var flag_email = true;
		var tmptxt = $("#email").val();
		var reg = new RegExp(/^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/);
		var result = tmptxt.match(reg);
	    if (result == null) {
	    	flag_email = false;
	    }else{
	    	flag_email = true;
	    }

	//正则验证数字
	    var flag_class_num=true;
		var tmptxt = $("#class_num").val();
		var reg = new RegExp(/^[1-9][0-9]*$/);
		var result = tmptxt.match(reg);
	    if (result == null) {
	    	flag_class_num = false;
	    }else{
	    	flag_class_num=true;
	    }
	    
	    //学校在职人数
	    var flag_staffteacher = true;
		var tmptxt = $("#staffteacher").val();
		var reg = new RegExp(/^[1-9][0-9]*$/);
		var result = tmptxt.match(reg);
	    if (result == null) {
	    	flag_staffteacher=false;	
	    }else{
	    	flag_staffteacher=true;
	    }
	    
	    
		//在籍学生总数
	    var flag_nativestudent = true;
		var tmptxt = $("#nativestudent").val();
		var reg = new RegExp(/^[1-9][0-9]*$/);
		var result = tmptxt.match(reg);
	    if (result == null) {
	    	flag_nativestudent = false;
	    }else{
	    	flag_nativestudent = true;
	    }
	  //所在地
	   var flag_school_area=true;
	   var school_area = $('input[name="school_area"]:checked').val(); 
	   if(school_area){
		   flag_school_area=true;
	   }else{
		   flag_school_area=false;
	   }
	   
	   //学校类型
	   var flag_school_type=true;
	   var school_type = $('input[name="school_type"]:checked').val(); 
	   if(school_type){
		   flag_school_type=true;
	   }else{
		   flag_school_type=false;
	   }
	
	
	if (s == "" || c == "" || d == "" || e == "" || n == ""|| m == "" || r == "" || t == "") {
		//alert("请您将信息填写完整后再提交！");
		var txt=  "请您将信息填写完整后再提交！";
		window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
	}else if(!(flag_school_type && flag_school_area && flag_nativestudent && flag_staffteacher && flag_class_num && flag_email && flag_mobile && flag_relation)){
		//alert("请认真检查填写的信息格式");
		var txt=  "请检查填写的信息格式!";
		window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
	} else {
		$.ajax({
			type : "POST",
			url : "../../servlet/SchoolInforServlet",
			data : {
				"operation" : "addpaper",
				"schoolArean" : a,
				"schoolName" : b,
				"teacherNumber" : c,
				"studentNumber" : d,
				"schoolTown" : s,
				"personName" : r,
				"telPhone" : m,
				"eMail" : e,
				"classNumber" : n,
				"school_type" : t,
				"school_type_":school_type_
			},
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			success : function(data) {
				if (data == "ok") {
					if(school_type_ == "normal"){
						window.location.href="../quiz/quiz.html";
					}else{
						window.location.href="../tpquesion/tpquiz.html";
					}
					
				} else {
					//alert("请认真检查选项！");
					var txt=  "请认真检查选项！";
					window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
				}
			},
			error : function() {
				//alert("information/连接失败！");
				var txt=  "information/连接失败！";
				window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
			}
		});
	}
}); 

var query_user_info = function() {
	$.ajax({
		url: "../../servlet/SchoolInforServlet",
		type: 'POST',
		data : {
			"operation" : "userinfo",
		},
		success:function(data) {
			//将数据原样显示在input中
			var data = jQuery.parseJSON(data);
			$("#area").val(data[0].area_name);	//区域名
			$("input[name='school_addr']").val(data[0].sch_name);
			if(data[0]["role"] == "user"){
				school_type_ = "normal"
			}else{
				school_type_ = "tp"
			}
		},
		error:function() {
			//alert("连接数据库失败!");
			var txt=  "连接数据库失败!";
			window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
		}
	});
	
};
query_user_info();

function GetQueryString(name)
{
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
}

if(GetQueryString("type") == "normal"){
	$(".tp_radio").attr("disabled","disabled");
}
if(GetQueryString("type") == "tp"){
	$(".norm_radio").attr("disabled","disabled");
}


