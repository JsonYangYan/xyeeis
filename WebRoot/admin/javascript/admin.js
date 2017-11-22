//获取学校用户某一页的数据
var getData_school = function(startNum,offset) {
	$.post("../../servlet/Admin",
			{
			operation:"paging",
			"startNum":startNum,
			"offset":offset,
			"search":$("#schoolName").val()
			},
			function(result) {
				result = jQuery.parseJSON(result);
					showList(result);
	});
};
// 获取区县用户所有的的数据
var getData_user = function() {
	$.post("../../servlet/Admin",
			{
			operation:"get_area_data",
			},
			function(result) {
				result = jQuery.parseJSON(result);
					showList1(result);
	});
};

// 学校用户管理的分页

// 分页
var paging_school = function(item_all_num,page_item_num,num_nav_box,max_nav_num) {
	
	// 非法数字的处理
	page_item_num = parseInt(page_item_num);
	max_nav_num = parseInt(max_nav_num);
	if(page_item_num < 1) {
		page_item_num = 1;
	}
	if(max_nav_num < 1) {
		max_nav_num = 1;
	}
	
	// 存放指定条数
	var start ;
	var pages_num; // 总页数
	var half_max_nav_num = Math.floor(max_nav_num/2); // 折半导航页码个数
	var current_page_num ; // 当前页号
	
	// 为当前页刷新数据做准备 ，利用location的hash值
	//var handleArr = window.location.hash;
// if(handleArr){
// handleArr = handleArr.split("#");
// current_page_num = parseInt(handleArr[1]);
// }else{
// current_page_num = 1;
// }
	current_page_num = 1;
    
	pages_num = Math.ceil(item_all_num/page_item_num);
	
	// 控制页码导航呈现
	var controlNumNav = function(num) {
		num = parseInt(num);
		//window.location.hash = "#" + num; // 记录当前页页码，供刷新页面使用
		start = page_item_num*(num - 1);
		getData_school(start,page_item_num);
		var num_html = "<ul id='num_nav'>";
		// 当总页数大于指定页数时进行的处理
		if(pages_num > max_nav_num) { 
			
			// 前面有省略的情况
			if(num - half_max_nav_num > 1) {
				num_html += "<li id='prev' class='over'>上一页</li><li style='border:0'>...</li>";
				
				// 后面有省略的情况
				if(num + half_max_nav_num < pages_num) {
					for(var i = num + half_max_nav_num , j = max_nav_num - 1; j >= 0 ; j--) {
						num_html += "<li class='num over' id='num_"+ (num + half_max_nav_num - j) +"' >" + (num + half_max_nav_num - j) + "</li>";
					}
					num_html += "<li style='border:0'>...</li>";
				}else {
					
					// 后面没省略的情况
					for(var i = pages_num , j = max_nav_num - 1 ; j >= 0 ; j--) {
						num_html += "<li class='num over' id='num_"+ (pages_num - j) +"' >" + (pages_num - j) + "</li>";
					}
				}
			}else {
				
				// 前面没省略的情况，后面有省略的情况
				num_html += "<li id='prev' class='over'>上一页</li>";
				for(var i = 1; i <= max_nav_num; i++) {
					num_html += "<li class='num over' id='num_"+ i +"' >" + i + "</li>";
				}
				num_html += "<li style='border:0'>...</li>";
			}
			
		}else {
			
			// 前后都没省略的情况
			num_html += "<li id='prev' class='over'>上一页</li>";
			for(var i = 1; i <= pages_num; i++) {
				num_html += "<li class='num over' id='num_"+ i +"' >" + i + "</li>";
			}
		}
		num_html += "<li id='next' class='over'>下一页</li><li style='border:0'>共"+ pages_num +"页</li></ul>";
		$("#" + num_nav_box).html(num_html);
		$("#num_nav li#num_" + num).css({"color":"white","background-color":"#0E90D2"});
	};
	
	// 点击页码触发的事件
	$("#num_nav li.num").live("click",function() {
	    current_page_num = $(this).text();
	    current_page_num = parseInt(current_page_num);
		controlNumNav(current_page_num);
	});
	
	// 点击上一页触发的事件
	$("#num_nav li#prev").live("click",function() {
		current_page_num = parseInt(current_page_num);
		if(current_page_num > 1 ) {
			current_page_num -= 1;
			controlNumNav(current_page_num);
		}
		
	});
	
	// 点击下一页触发的事件
	$("#num_nav li#next").live("click",function() {
		current_page_num = parseInt(current_page_num);
		if(current_page_num < pages_num ) {
			current_page_num += 1;
			controlNumNav(current_page_num);
		}
	});
	
	// 初始化当前页码为1的时候，页面导航初始化
	controlNumNav(current_page_num);
	
};

// 获取学校普通用户总数据条数
var pagedata_school= function(){
	$.post("../../servlet/Admin",
	{
		operation:"pageAmount",
		"search":$("#schoolName").val()
	},
	function(result) {
		paging_school(result,6,"page_school",4);
		
	});
}

//点击复选框，记录复选框的状态
var changeIds = function(){
	 //获取被选中的复选框的id
	 var oneches=document.getElementsByName("checkSchooluser");

	 
	 for(var i=0;i<oneches.length;i++){
		if(oneches[i].checked==true){
			//避免重复累计id （不含该id时进行累加）									
			if(checkedIds.indexOf(","+oneches[i].value+",")==-1){		//indexOf() 方法可返回某个指定的字符串值在字符串中首次出现的位置
				checkedIds=checkedIds+oneches[i].value+",";
			}					
		}
		
		if(oneches[i].checked==false){
			//取消复选框时 含有该id时将id从全局变量中去除
			if(checkedIds.indexOf(","+oneches[i].value+",")!=-1){
				checkedIds=checkedIds.replace((oneches[i].value+","),"");
			}			
		}
		
	 }
};
	

//检查已经选过的多选框
var getChecked = function(){	
	//获取所有复选框的id
	var allches=document.getElementsByName("checkSchooluser");
//	获取所有复选框对应的学校名字
	var checkName=$("div[name='checkName']").text();
	
	
	for(var i=0;i<allches.length;i++){
	 	
		//全局变量中含有id，则该复选框选中
		if(checkedIds.indexOf(","+allches[i].value+",")!=-1){
			allches[i].checked=true;
			
			alert(allches)
			
			alert(checkName)
			
		}
		
	}
	

	
	
	
};





// 学校用户管理
var showList = function(data) {
	$("#displayData").html("");
	var html = "<table class='tabl'><thead><tr align='center'><td></td><td>用户名</td><td>学校</td><td>操作</td></tr><thead>";
	$.each(data,function(key,value) {
		html += "<tr align='center'><td><input type='checkbox' onclick='changeIds();' value='" + value.id + "' name='checkSchooluser' /></td><td width=18%>" + value.username + "</td><td width=22% name='checkName' value='" + value.sch_name + "'>" 
	        + value.sch_name + "</td>"
	        + "<td align='center' width=60%>" +
        		"<div id='" + value.id + "' name=showlist_button action='deleteAction' class='del' title='" + value.sch_name + "'></div>" +
        		"<div name=showlist_button action='resetPasswd' id='" + value.id + "' class='reset'></div>" +
        		"<a href=editschooluser.html?userid=" + value.id + "><div name=showlist_button2 class='update'></div></a></td></tr>";
	});
	html += "</table>";
	$("#displayData").html(html);
	getChecked();//翻页后，检查已选中的多选框
	
	//删除、重置按钮点击
	$("div[name='showlist_button']").click(function() {
		var action = $(this).attr("action");
		var id = $(this).attr("id");
		var schoolName = $(this).attr("title");
		//alert(schoolName);
		
		if(action =="deleteAction"){
			if(!confirm("确定要删除吗？")){
				return false;
			}
		}
		if(action == "resetPasswd"){
			if(!confirm("确定要重置密码吗？")){
				return false;
			}
		}
		
		$.ajax({
			url: "../../servlet/Admin",
			type: "post",
			data: {
				"id": id,
				"schoolName":schoolName,
				"operation": action 
			},
			success: function(data) {
				if(data == "success"){
//					alert("操作成功,重置为初始密码：123456");
					var txt=  "操作成功,重置为初始密码：123456";
					window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
				}
				else if(data == "ok"){
					window.location.reload();
				}
			},
			error: function() {
//				alert("失败");
				var txt=  "失败";
				window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
			}
		})
	});
	
};

//学校用户批量删除
$("#delete_all").click(function() {
	
	var checkedBox = "";//所有的选中学校的id
	checkedBox = checkedIds.substring(1);	//把第一个逗号去掉
	
//	选中所有学校的name
	var checkNameBox= "";
	checkNameBox = checkedIds.substring(1);
	
	alert(checkNameBox);
	
	
	if(checkedBox.length == 0 || checkedBox == "") {
//		alert("请选择至少一项！");
		var txt=  "请选择至少一项！";
		window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
	}else{
		
		if(!confirm("确定要删除所选项目吗？")){
			
			return false;
		}
		// 批量选择--删除
		$.ajax({
			url: "../../servlet/Admin",
			type: "post",
			data: {
				"ids": checkedBox,
				"schoolNames":checkNameBox,
				"operation": "Schooluserbatchdelete" 
			},
			success: function(data) {
				$("[name='checkSchooluser']:checkbox").attr("checked", false);
				window.location.reload();
			},
			error: function() {
				//alert("连接数据库失败！");
				var txt=  "连接数据库失败！";
				window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
			}
		})
	}
});


// 区县用户管理信息显示
var showList1 = function(data) {
	$("#sldisplayData").html("");
	var html = 
		"<table class='tabl'><thead>" +
		"<tr align='center'><td>用户名</td>" +
		"<td>地区</td><td>操作</td></tr></thead>";
	var html_user = 
			"<div class='user_name'>用户名</div>" +
			"<div class='user_area'>地区</div>" +
			"<div class='user_action'>操作</div>" +
			"<div class='user_name dotted'>用户名</div>" +
			"<div class='user_area'>地区</div>" +
			"<div class='user_action'>操作</div>";
	
	$.each(data,function(key,value) {
		// 中间用虚线隔开
		if(key%2==0){
			 html_user += 
				  "<div class='user_name'>"+ value.username +"</div>" +
				  "<div class='user_area'>"+value.area_name+"</div>" +
				  "<div class='user_action'>" +
						"<div id='" + value.id + "' name=showlist_button1 action='deleteAdminAction' class='del_user'></div>" +
						"<div name=showlist_button1 action='resetAdminPasswd' id='" + value.id + "' class='reset_user'></div>" +
						"<a href=editsladmin.html?userid=" + value.id + "><div name=showlist_button2  class='update_user'></div></a>" +
				 "</div>";
		}else{
			 html_user += 
				  "<div class='user_name dotted'>"+ value.username +"</div>" +
				  "<div class='user_area'>"+value.area_name+"</div>" +
				  "<div class='user_action'>" +
						"<div id='" + value.id + "' name=showlist_button1 action='deleteAdminAction' class='del_user'></div>" +
						"<div name=showlist_button1 action='resetAdminPasswd' id='" + value.id + "' class='reset_user'></div>" +
						"<a href=editsladmin.html?userid=" + value.id + "><div name=showlist_button2  class='update_user'></div></a>" +
				 "</div>";
			
		}
	 
		$("#sldisplayData").html(html_user);
	});
	
	// 删除、重置按钮点击
	$("div[name='showlist_button1']").click(function() {
		var action = $(this).attr("action");
		var id = $(this).attr("id");
		
		if(action =="deleteAdminAction"){
			if(!confirm("确定要删除吗？")){
				return false;
			}
		}
		if(action == "resetAdminPasswd"){
			if(!confirm("确定要重置密码吗？")){
				return false;
			}
		}
		
		$.ajax({
			url: "../../servlet/Admin",
			type: "post",
			data: {
				"id": id,
				"operation": action 
			},
			success: function(data) {
				if(data == "success"){
					//alert("操作成功,重置为初始密码：123456");	
					var txt=  "操作成功,重置为初始密码：123456";
					window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
				}
				else if(data == "ok"){
					// window.location.reload();
				}
			},
			error: function() {
				//alert("失败");
				var txt=  "失败";
				window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
			}
		})
	});
};

// 管理员密码重置
var resetSuAdminPasswd = function() {
	// 点击提交按钮
	$("#submit").click(function() {
		var password = $("input[name='password1']").val();
		
		var passconfirm = $("input[name='password2']").val();
		if(password == passconfirm) {
			$.ajax({
				url: "../../servlet/Admin",
				type: "post",
				data: {
					"pass": password,
					"operation": "resetSuAdminpasswd" 
				},
				success: function(data) {
	                    if(data == "ok1") {
	                    	//alert("修改成功");
	                    	var txt=  "修改成功";
	    					window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
	                    	window.location.href = "../../index.html";
	                    
	                    }else {
	                        //alert("修改密码操作数据库失败！2");
	                        var txt=  "修改密码操作数据库失败！2";
	    					window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
	                    }
	             },
	             error:function() {
	            	// alert("修改密码连接数据库失败！1");
	            	 var txt=  "修改密码操作数据库失败！1";
 					window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
	             }    
			})
		}else{
			//alert("密码不一致");
			var txt=  "密码不一致";
			window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
		}
	});
};

// 搜索功能
var searchFunc = function() {
	$("#search").click(function() {
		getData_school(0,6);
	});
};

// 获得用户名
var getNameFunc = function() {
	
		$.ajax({
			url: "../../servlet/Admin",
			type: "post",
			data: {
				
				"operation": "getname"
			},
			success:function(result) {
				
				$("#name").val(result);
				
			},
			error:function() {
				alert("搜索连接数据库失败！");
				//var txt=  "搜索连接数据库失败！";
				window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
			}    
		});
	
	
};

//标签页的切换
$("#adminManage").click(function(){
	$("#adminManage").removeClass("adminmg");
	$("#adminManage").addClass("adminmg1");
	$("#userManage").removeClass("usermg1");
	$("#userManage").addClass("usermg");
	$("#schoolManage").removeClass("schoolmg1");
	$("#schoolManage").addClass("schoolmg");
	$("#jixiao").removeClass("jixiao_active").addClass("jixiao_normal");
	$("#user").hide();
	$("#admin").show();
	$("#school").hide();
	$("#jixiaoContent").hide();
});

$("#userManage").click(function(){
	$("#adminManage").removeClass("adminmg1");
	$("#adminManage").addClass("adminmg");
	$("#userManage").removeClass("usermg");
	$("#userManage").addClass("usermg1");
	$("#schoolManage").removeClass("schoolmg1");
	$("#schoolManage").addClass("schoolmg");
	$("#jixiao").removeClass("jixiao_active").addClass("jixiao_normal");
	$("#admin").hide();
	$("#user").show();
	$("#school").hide();
	$("#jixiaoContent").hide();
	getData_user();
});

$("#schoolManage").click(function(){
	$("#adminManage").removeClass("adminmg1");
	$("#adminManage").addClass("adminmg");
	$("#userManage").removeClass("usermg1");
	$("#userManage").addClass("usermg");
	$("#schoolManage").removeClass("schoolmg");
	$("#schoolManage").addClass("schoolmg1");
	$("#jixiao").removeClass("jixiao_active").addClass("jixiao_normal");
	$("#admin").hide();
	$("#user").hide();
	$("#jixiaoContent").hide();
	$("#school").show();
	pagedata_school();
});
$("#jixiao").click(function(){
	$("#adminManage").removeClass("adminmg1");
	$("#adminManage").addClass("adminmg");
	$("#userManage").removeClass("usermg1");
	$("#userManage").addClass("usermg");
	$("#schoolManage").removeClass("schoolmg1");
	$("#schoolManage").addClass("schoolmg");
	$("#admin").hide();
	$("#user").hide();
	$("#school").hide();
	$("#jixiaoContent").show();
	$("#jixiao").removeClass("jixiao_normal").addClass("jixiao_active");
});

//标记tab标签
var urlParser = window.location.href.split("#");
if(urlParser.length>1 && (curLi=$("a[href='#"+urlParser[1]+"']")).length>0){
 
	if(urlParser[1] == "chpw"){
		$("#adminManage").removeClass("adminmg");
		$("#adminManage").addClass("adminmg1");
		$("#userManage").removeClass("usermg1");
		$("#userManage").addClass("usermg");
		$("#schoolManage").removeClass("schoolmg1");
		$("#schoolManage").addClass("schoolmg");
		
		$("#user").hide();
		$("#admin").show();
		$("#school").hide();
		
	}else if(urlParser[1] == "usmg"){
		$("#adminManage").removeClass("adminmg1");
		$("#adminManage").addClass("adminmg");
		$("#userManage").removeClass("usermg");
		$("#userManage").addClass("usermg1");
		$("#schoolManage").removeClass("schoolmg1");
		$("#schoolManage").addClass("schoolmg");
		
		$("#admin").hide();
		$("#user").show();
		$("#school").hide();
		getData_user();
		
	}else if(urlParser[1] == "shmg"){
		$("#adminManage").removeClass("adminmg1");
		$("#adminManage").addClass("adminmg");
		$("#userManage").removeClass("usermg1");
		$("#userManage").addClass("usermg");
		$("#schoolManage").removeClass("schoolmg");
		$("#schoolManage").addClass("schoolmg1");
		
		$("#admin").hide();
		$("#user").hide();
		$("#school").show();
		pagedata_school();
	}
}

$(".jixiaoBtn").click(function(){
	$(".jixiaoBtn").removeClass("jixiao_able").addClass("jixiao_disable");	
	$.ajax({
		url:"../../servlet/ScoreServlet",
		data:{
			
		},
		async:"true",
		success:function(data){
			if(data == "timeover"){
				alert("本月计算绩效时间已截止！");
			}else if(data=="ok"){
				$(".jixiaoBtn").removeClass("jixiao_disable").addClass("jixiao_able");
				$(".jixiaoBtn").removeAttr("disabled");
				alert("计算完成");
			}
		},
		error:function(){
			alert("error")
		}
	});
	$(".jixiaoBtn").attr("disabled", "disabled");
});

// 初始化
var init = function() {
	checkedIds=",";//翻页保存选中的复选框id,用“，”分隔
	var intervalID; 
	var curLi;
	resetSuAdminPasswd();
	searchFunc();
	getNameFunc();
};

init();  // 入口
