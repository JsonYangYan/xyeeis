$(".noteawoke").hide();
$(".noteok").hide();
$("#relation").blur(function() {
	var tmptxt = $("#relation").val();
	
	var reg = new RegExp(/^[a-zA-Z\u4e00-\u9fa5]+$/);
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
$("#email").live("blur",function() {
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
$("#class_num").live("blur",function(){
	var tmptxt = $("#class_num").val();
	var reg = new RegExp(/^[1-9][0-9]*$/);
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
$("#staffteacher").live("blur",function(){
	var tmptxt = $("#staffteacher").val();
	var reg = new RegExp(/^[1-9][0-9]*$/);
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
$("#nativestudent").live("blur",function(){
	var tmptxt = $("#nativestudent").val();
	var reg = new RegExp(/^[1-9][0-9]*$/);
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
$("#edit_information").live("click", function() {
	//var a = $("input[name='area']").val(); 	//地区
	var a = $("#area").val();
	var b = $("input[name='school_addr']").val();	//学校名称
	var c = $("input[name='staffteacher']").val();	//学校在职教师人数
	var d = $("input[name='nativestudent']").val();	//学校在籍学生人数
	var s = $("input[name='school_area']:checked").val();	//学校所在地区类型
	var r = $("input[name='relation']").val();	//联系人姓名
	var m = $("input[name='mobile']").val();	//手机
	var e = $("input[name='email']").val();		//邮箱
	var n = $("input[name='class_num']").val();		//该学校的班级数
	var t = $("input[name='school_type']:checked").val();	//小学、初中、高中、其他
	if (s == "" || c == "" || d == "" || e == "" || n == ""|| m == "" || r == "" || t == "") {
		//alert("请填写完整的信息");
		var txt=  "请填写完整的信息";
		window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
	} else {
		
		$.ajax({
			type : "POST",
			url : "../../servlet/SchoolInforServlet",
			data : {
				//更新数据库
				"operation" : "edit",
				
				"schoolArean" : a,
				"schoolName" : b,
				"teacherNumber" : c,
				"studentNumber" : d,
				"schoolTown" : s,
				"personName" : r,
				"telPhone" : m,
				"eMail" : e,
				"classNumber" : n,
				"schoolType" : t
			},
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			success : function(data) {
				if (data == "ok") {
					//alert("更新成功！");
					var txt=  "更新成功！";
					window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
				//	user_info();
				} else {
					//alert("提交失败！");
					var txt=  "提交失败！";
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


var user_info = function() {
	$.ajax({
		url: "../../servlet/SchoolInforServlet",
		type: 'POST',
		data : {
			
			"operation" : "edit_display",
		},
		success:function(data) {
			//将数据原样显示在input中
			var data = jQuery.parseJSON(data);
			//console.log(data[0].schoolTown);
			//alert(data[0].schoolArean);
			
			//alert(data.schoolArean);
			$("#area").val(data[0].schoolArean);
			$("input[name='school_addr']").val(data[0].schoolName);
			$("input[name='staffteacher']").val(data[0].teacherNumber);
			$("input[name='nativestudent']").val(data[0].studentNumber);
			$("input[name='relation']").val(data[0].personName);
			$("input[name='mobile']").val(data[0].telPhone);
			$("input[name='email']").val(data[0].eMail);
			$("input[name='class_num']").val(data[0].classNumber);
//			$("input[name='school_area']").val(data[0].schoolTown);
//			if(data[0].schoolTown == "城镇") {
//				$("#town").attr("checked",true);
//			}else{
//				$("#village").attr("checked",true);
//			};
			//选中单选框
			showRadio("school_area",data[0].schoolTown);
			showRadio("school_type",data[0].schoolType);  
		},
		error:function() {
			//alert("获取用户基本信息连接数据库失败!");
			var txt=  "获取用户基本信息连接数据库失败！";
			window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
		}
	});
}

function showRadio(name,value){  
    var nValue =  $("input[name='"+name+"']");  
    for(var i=0;i<nValue.length;i++){  
        if(nValue[i].value == value){  
            nValue[i].checked = true;  
            break;  
        }  
    }  
}



var init = function() {
	user_info();	//页面初始化---显示用户数据
}

init();


