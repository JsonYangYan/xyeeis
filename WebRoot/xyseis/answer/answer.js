// 获取基础设施部分题目信息
var security = function() {
	$.ajax({
		url: "/xyeeis/servlet/PaperResultsServlet",
		type: 'POST',
		data:{
			dt:'5',
		},
		dataType:'json',
		success:function(data) {			
			question = data;
		    $("#security_ques_list").html(TrimPath.processDOMTemplate("ques_detail_template", question));
		    
		    //trimpath模板循环输出时，for循环会多输出一次，将最后一条数据删除
		    var t = question.length-1;
		    $("#security_ques_list ul:first-child").siblings().eq(t).remove();
		    $("#28").addClass("hide_ques_border");
		},
		error:function() {
			alert("获取question5连接数据库失败!");
		}
	});
};
// 获取信息化应用部分题目信息
var management = function() {
	$.ajax({
		url: "/xyeeis/servlet/PaperResultsServlet",
		type: 'POST',
		data:{
			dt:'4',
		},
		dataType:'json',
		success:function(data) {			
			question = data;
		    $("#management_ques_list").html(TrimPath.processDOMTemplate("ques_detail_template", question));
		    
		    //trimpath模板循环输出时，for循环会多输出一次，将最后一条数据删除
		    var t = question.length-1;
		    $("#management_ques_list ul:first-child").siblings().eq(t).remove();
		    $("#17").addClass("hide_ques_border");
		},
		error:function() {
			alert("获取question4连接数据库失败!");
		}
	});
};
// 获取基础设施部分题目信息
var research = function() {
	$.ajax({
		url: "/xyeeis/servlet/PaperResultsServlet",
		type: 'POST',
		data:{
			dt:'3',
		},
		dataType:'json',
		success:function(data) {			
			question = data;
		    $("#research_ques_list").html(TrimPath.processDOMTemplate("ques_detail_template", question));
		    
		    //trimpath模板循环输出时，for循环会多输出一次，将最后一条数据删除
		    var t = question.length-1;
		    $("#research_ques_list ul:first-child").siblings().eq(t).remove();
		    $("#10").addClass("hide_ques_border");
		},
		error:function() {
			alert("获取question3连接数据库失败!");
		}
	});
};
// 获取信息化应用部分题目信息
var teaching = function() {
	$.ajax({
		url: "/xyeeis/servlet/PaperResultsServlet",
		type: 'POST',
		data:{
			dt:'2',
		},
		dataType:'json',
		success:function(data) {			
			question = data;
		    $("#teaching_ques_list").html(TrimPath.processDOMTemplate("ques_detail_template", question));
		    
		    //trimpath模板循环输出时，for循环会多输出一次，将最后一条数据删除
		    var t = question.length-1;
		    $("#teaching_ques_list ul:first-child").siblings().eq(t).remove();
		    $("#8").addClass("hide_ques_border");
		},
		error:function() {
			alert("获取question2连接数据库失败!");
		}
	});
};
// 获取基础设施部分题目信息
var infrastructure = function() {
	$.ajax({
		url: "/xyeeis/servlet/PaperResultsServlet",
		type: 'POST',
		data:{
			dt:'1',
		},
		dataType:'json',
		success:function(data) {
			question = data;
		    $("#infrastructure_ques_list").html(TrimPath.processDOMTemplate("ques_detail_template", question));
		    
		    //trimpath模板循环输出时，for循环会多输出一次，将最后一条数据删除
		    var t = question.length-1;
		    $("#infrastructure_ques_list ul:first-child").siblings().eq(t).remove();
		    $("#4").addClass("hide_ques_border");
		},
		error:function() {
			alert("获取question1连接数据库失败!");
		}
	});
};	



var hideRedBorder = function() {
	$(".groupId_contain[groupnum='0']").removeClass("ques_border");
}
var tabChange = function() {		
		
	// tab点击效果
	initTabEvent = function(tabtit,tab_conbox,shijian) {
	    $(tab_conbox).find(">li").hide();		//将所有的问题隐藏
	    $(tabtit).find(">li:first").addClass("this_tab").show(); //显示第一个选项卡
	    $(tab_conbox).find(">li:first").show();		//显示第一个选项卡的问题
	    
	    //点击事件
	    $(tabtit).find(">li").bind(shijian,function(){		
		    $(this).addClass("this_tab").siblings("li").removeClass("this_tab"); 		//被点击的选项卡添加css样式
		    var activeindex = $(tabtit).find(">li").index(this);		//获取被点击选项卡的索引      	
		    $(tab_conbox).children(".li_content").eq(activeindex).show().siblings().hide();		//根据索引将被点击的选项卡对应的问题显示，其余的隐藏
		    return false;
	    });
	};
    
   initTabEvent("#tab_container","#content_container", "click");	
};

var init = function() {
	var question;
	infrastructure();
	teaching();
	research();
	management();
	security();	
	tabChange();
}

init();