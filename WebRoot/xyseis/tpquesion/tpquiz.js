// 获取基础设施部分题目信息
var security = function(){
	$.ajax({
		url: "/xyeeis/servlet/PaperResultsServlet",
		type: 'POST',
		async:false,
		data:{
			dt:'5',
			"type":"tp",
		},
		dataType:'json',
		success:function(data){
			question = data;
		    $("#security_ques_list").html(TrimPath.processDOMTemplate("ques_detail_template", question));
		    
		    //trimpath模板循环输出时，for循环会多输出一次，将最后一条数据删除
		    var t = question.length;
		    $("#security_ques_list .groupId_contain").eq(t).remove();
		    
		  //基准月试卷隐藏显示
			var check_val = [];
			$.each(data,function(i,field) {
				$.each(field["children"],function(k,choiceData){
					check_val.push(field["children"][k]["testType"]);
					var text_type = field["children"][k]["testType"];//问题的类型  1 单选，2多选，3文字填空，4数字填空
					 var queId = field["children"][k]["queId"];//问题的id
					 var autoId = "";//选择题答案的id
					 var userAns = "";//填空题答案的  内容
					 var html ="<input type='hidden'  name="+queId+" test_type= '"+text_type+"' ";
					 if(field["children"][k]["testType"]=='3'|| field["children"][k]["testType"]=='4'){//填空题
						 
						userAns = field["children"][k]["userAns"];
						html+=" value= '"+userAns+"'/>";
						
					 }else{
						 $.each(field["children"][k]["children"],function(j,choice){//单选或者多选题
							 if(choice["is_checked"]=='1'){
								 autoId+=choice["autoId"]+",";
							 }
							 
						 });
						 
						 if(autoId !='' ) {
							 
							 autoId = autoId.substring(0,autoId.length-1);
						 }
						 if(autoId){
							 html+=" value= '"+autoId+"'/>";
						 }else{
							 html+=" value='undefined'/>";
						 }
						 
					 }
					 
					 $("#base_answer").append(html);
					 
					
				});
			 
			 });
	},
		error:function() {
			alert("获取question5连接数据库失败!");
		}
	});
	
}
//获取信息化应用部分题目信息
var management = function(){
	$.ajax({
		url: "/xyeeis/servlet/PaperResultsServlet",
		type: 'POST',
		async:false,
		data:{
			dt:'4',
			"type":"tp",
		},
		dataType:'json',
		success:function(data) {			
			question = data;
		    $("#management_ques_list").html(TrimPath.processDOMTemplate("ques_detail_template", question));
		    
		    //trimpath模板循环输出时，for循环会多输出一次，将最后一条数据删除
		    var t = question.length;
		    $("#management_ques_list .groupId_contain").eq(t).remove();
		   
		    //选中之后其他的不能填写
		    var radio_152_val = $("input[name=radio_152]:checked").val();
		    if(radio_152_val == '278') {
		    	 $("input[name=radio_153]").attr("disabled","disabled");
		    	 $("input[name=radio_154]").attr("disabled","disabled");
		    	 $("input[name=radio_155]").attr("disabled","disabled");
		    	 $("input[name=radio_153]").removeAttr("checked");
		    	 $("input[name=radio_154]").removeAttr("checked");
		    	 $("input[name=radio_155]").removeAttr("checked");
		    }
		    
		    
		  //基准月试卷隐藏显示
			var check_val = [];
			$.each(data,function(i,field) {
				$.each(field["children"],function(k,choiceData){
					check_val.push(field["children"][k]["testType"]);
					var text_type = field["children"][k]["testType"];//问题的类型  1 单选，2多选，3文字填空，4数字填空
					 var queId = field["children"][k]["queId"];//问题的id
					 var autoId = "";//选择题答案的id
					 var userAns = "";//填空题答案的  内容
					 var html ="<input type='hidden'  name="+queId+" test_type= '"+text_type+"' ";
					 if(field["children"][k]["testType"]=='3'|| field["children"][k]["testType"]=='4'){//填空题
						 
						userAns = field["children"][k]["userAns"];
						html+=" value= '"+userAns+"'/>";
						
					 }else{
						 $.each(field["children"][k]["children"],function(j,choice){//单选或者多选题
							 if(choice["is_checked"]=='1'){
								 autoId+=choice["autoId"]+",";
							 }
							 
						 });
						 
						 if(autoId !='' ) {
							 
							 autoId = autoId.substring(0,autoId.length-1);
						 }
						 
						 if(autoId){
							 html+=" value= '"+autoId+"'/>";
						 }else{
							 html+=" value='undefined'/>";
						 }
					 }
					 
					 $("#base_answer").append(html);
					
				});
			 
			 });
			//基准试卷隐藏显示end
			
			 //点击“无”选项后，下次在进来后，其余的不能点击
			$(".multi_choice").each(function(){
				if($(this).attr("name") =="checkbox_1" && $(this).attr("ques")!= "ques_156" && $(this).attr("ques")!= "ques_157"){
					if($(this).attr('checked'))	{	//选中value为1的框，即“无”选项
				 		$(this).parents(".multi_content").siblings().find('input').attr("disabled","disabled");
				 		$(this).parents(".multi_content").siblings().find('input').removeAttr("checked");
				 	}
				}
			
			});
						 
	},
		error:function() {
			alert("获取question4连接数据库失败!");
		}
	});
	
}

// 获取信息化应用部分题目信息
var teaching =function(){

	$.ajax({
		url: "/xyeeis/servlet/PaperResultsServlet",
		type: 'POST',
		async:false,
		data:{
			dt:'2',
			"type":"tp",
		},
		dataType:'json',
		success:function(data) {			
			question = data;
		    $("#teaching_ques_list").html(TrimPath.processDOMTemplate("ques_detail_template", question));
		    //trimpath模板循环输出时，for循环会多输出一次，将最后一条数据删除
		    var t = question.length;
		    $("#teaching_ques_list .groupId_contain").eq(t).remove();
		  //基准月试卷隐藏显示
			var check_val = [];
			$.each(data,function(i,field) {
				$.each(field["children"],function(k,choiceData){
					check_val.push(field["children"][k]["testType"]);
					var text_type = field["children"][k]["testType"];//问题的类型  1 单选，2多选，3文字填空，4数字填空
					 var queId = field["children"][k]["queId"];//问题的id
					 var autoId = "";//选择题答案的id
					 var userAns = "";//填空题答案的  内容
					 var html ="<input type='hidden'  name="+queId+" test_type= '"+text_type+"' ";
					 if(field["children"][k]["testType"]=='3'|| field["children"][k]["testType"]=='4'){//填空题
						 
						userAns = field["children"][k]["userAns"];
						html+=" value= '"+userAns+"'/>";
						
					 }else{
						 $.each(field["children"][k]["children"],function(j,choice){//单选或者多选题
							 if(choice["is_checked"]=='1'){
								 autoId+=choice["autoId"]+",";
							 }
							 
						 });
						 
						 if(autoId !='' ) {
							 
							 autoId = autoId.substring(0,autoId.length-1);
						 }
						 
						 if(autoId){
							 html+=" value= '"+autoId+"'/>";
						 }else{
							 html+=" value='undefined'/>";
						 }
					 }
					 
					 $("#base_answer").append(html);
					
				});
			 
			 });
			//基准试卷隐藏显示end
		},
		error:function() {
			alert("获取question2连接数据库失败!");
		}
	});
}
// 获取基础设施部分题目信息
var infrastructure =function(){

	$.ajax({
		url: "/xyeeis/servlet/PaperResultsServlet",
		type: 'POST',
		async:false,
		data:{
			dt:'1',
			"type":"tp",
		},
		dataType:'json',
		success:function(data) {
			console.log(data);
			question = data;
		    $("#infrastructure_ques_list").html(TrimPath.processDOMTemplate("ques_detail_template", question));
		    
//		    //trimpath模板循环输出时，for循环会多输出一次，将最后一条数据删除
		    var t = question.length;
		    $("#infrastructure_ques_list .groupId_contain").eq(t).remove();
		    
		    //基准月试卷隐藏显示
			var check_val = [];
			$.each(data,function(i,field) {
				$.each(field["children"],function(k,choiceData){
					check_val.push(field["children"][k]["testType"]);
					var text_type = field["children"][k]["testType"];//问题的类型  1 单选，2多选，3文字填空，4数字填空
					 var queId = field["children"][k]["queId"];//问题的id
					 var autoId = "";//选择题答案的id
					 var userAns = "";//填空题答案的  内容
					 
					 var html ="<input type='hidden'  name="+queId+" test_type= '"+text_type+"' ";
					 if(field["children"][k]["testType"]=='3'|| field["children"][k]["testType"]=='4'){//填空题
						 
						userAns = field["children"][k]["userAns"];
						html+=" value= '"+userAns+"'/>";
						
					 }else{
						 
						 $.each(field["children"][k]["children"],function(j,choice){//单选或者多选题
							 if(choice["is_checked"]=='1'){
								 autoId+=choice["autoId"]+",";
							 }
							 
						 });
						 
						 if(autoId !='' ) {
							 
							 autoId = autoId.substring(0,autoId.length-1);
						 }
						 if(autoId){
							 html+=" value= '"+autoId+"'/>";
						 }else{
							 html+=" value='undefined'/>";
						 }
						 
					 }
					 if(queId == '1' && autoId =='2'){
						 $("input[name='radio_2']").attr("checked",false);
					    	$("input[name='radio_2']").attr("disabled","disabled");
					    	$("input[name='text_3']").val("");
					    	$("input[name='text_3']").attr("disabled","disabled");
					  }
					 
					 if(queId == '9' && autoId =='8'){
						 	$("input[name='radio_10']").attr("checked",false);
					    	$("input[name='radio_10']").attr("disabled","disabled");
					 }
					 
					 $("#base_answer").append(html);
				});
				
			 
			 });
			//基准试卷隐藏显示end
		},
		error:function() {
			alert("获取question1连接数据库失败!");
		}
	});
	
}

//进度条
var speedBar = function() {

    var TotalQuesnum =$("#base_answer input").size();
    var ansNum=0;
    $("#base_answer input").each(function(i,item){
    	if($(this).val() && $(this).val() !="undefined" && $(this).val() !='-1') {
    		ansNum++;
    	}
    });
    
    if($("input[name=radio_1]:checked").val() == "2"){//如果第一题选的是否，则从总体的个书上减2
    	TotalQuesnum = TotalQuesnum-2;
    	//如果不能填写的比如第二题，第三题，已经填写上，则从已经填写的里面去掉
    	if($("input[name=radio_2]:checked").val()){
    		
    		ansNum = ansNum -1;
    	}
    	if($("input[name=text_3]").val()){
    		ansNum = ansNum -1;
    	}
	}
    if($("input[name='radio_9']:checked").val() == "8"){
    	TotalQuesnum = TotalQuesnum-1;
    }
    var pent=(ansNum/TotalQuesnum)*100;
    var max="barred";
    var maxValue=0;
    var minValue=0;
    $(".charts").each( function(i,item) {
        var a=parseInt(pent);
        if(i==0) {
            minValue=a;
            minIndex=i;
        }
        if(a>maxValue) {
            maxValue=a;
            maxIndex=i;
        } else if(a<minValue) {
            minValue=a;
            minIndex=i;
        }

    });
    $(".charts").each( function(i,item) {
        var  a=pent;
        $(item).animate({
            width: a+"%"
        },100);
    });
    var c=Math.round(pent*100)/100+"%";//保留小数点后两位
   
    allDone = c;
    $(".last").html(c);
    
    //进度条100%时，提交按钮可以点击
    if(allDone == "100%"){
    	$("#submit_button").attr("style","background-color:#ffc300");
    	$("#submit_button").removeAttr("disabled");
    	
    }else{
    	
    	$("#submit_button").attr("style","background-color:#cccccc");
    	$("#submit_button").attr("disabled","disabled");
    }
  
   
};


//保存用户答案
var SubmitAns = function() {

    $.ajax({
		 url: "/xyeeis/servlet/PaperAnswerServlet",
		 type:"POST",
		 data: {
		 },
		 success: function(data) {
		     if(data == "ok") {
		    	 window.location = "thanks.html";
		     } else {
		         alert("问卷填写不成功，选择题或者填空题未全部填写，请重新填写！");
		     }
		 },
		 error: function() {
		     alert("获取所有的quiz连接数据库失败!");
		 }
    });              
         
};////初次提交答案 end/////

//保存操作
function saveOperation() {
	var result = false;
	
	if($("input[name='flag']").val()=='0'){//页面没有修改
		return "no_change";
	}
	 if($("input[name='flag']").val()=='1'){
	    	var choice_array = new Array();//选择题
			var text_array = new Array();//填空题
		    $("#base_answer input").each(function(i,item){
		    	//如果用户填写的数据有效 则保存到临时表中
		    	if($(this).val()!="undefined"){
		    		if($(this).attr("test_type") == '4') {//填空题
		    			var obj = {"questionId":"","answertext":"","answerId":0};
		    			obj.questionId = parseInt($(this).attr("name"));
		    			obj.answertext = $(this).val();
		    			text_array.push(obj);
		    		}
		    		
		    		if($(this).attr("test_type") == '2' || $(this).attr("test_type") == '1') {//多选题
		    			var val = $(this).val();
		    			var var_arr = val.split(",");
		    			for(var i=0;i<var_arr.length;i++){
		    				var obj = {"questionId":"","answerId":"","answertext":"null"};
		    				obj.questionId = parseInt($(this).attr("name"));
		    		        obj.answerId = var_arr[i];
		    		        choice_array.push(obj);
		    			}
		    		}
		    		
		    		
		    	}
	    });
		  //向后台传输数据
		    var choiceAnswer = JSON.stringify(choice_array); 
//		    console.log(choice_array);
		    var blankAnswer = JSON.stringify(text_array); 
		    $.ajax({
		    	url :"/xyeeis/servlet/AddAnswerTempServlet",
		    	type:"POST",
		    	async:false,
				data: {
				     "choice_array":choiceAnswer,
				     "text_array":blankAnswer,
				     "operation": "addAns",
				     'type':"tp"
				 },
				 success:function(data){
					 if(data == "ok"){
						 result = true;
					 }
				 },
				 error:function(){}
		    });
		    	    
	 }
	 return result;
}


var bintEvent = function() {
	
	//填空题
	$("input[type='text']").live("blur", function() {
            
        var tmptxt = $(this).val();
        if($(this).attr("name") == 'text_13' && tmptxt) {

	       //判断百分比
	      
	        var reg = new RegExp(/^((\d|[123456789]\d)(\.[0-9]{0,2})?|100)$/);
	        var data = tmptxt.match(reg);
		    if (data == null) {
		    	$(this).parent().parent().parent().find(".errorAnswer").html("* 填写有误，请重新填写！百分数小数点后最多保留两位有效数字！");
		    	$(this).attr("value","");
		    	return false;
		    } else {
		    	$(this).parent().parent().parent().find(".errorAnswer").html("");
		    }
	        //判断0和1
        }
     });
	
	
	var choiceArray = new Array();//处理 其他选项 ，记录选项
    var choiceQuesArray = new Array();//数组对象，避免删掉其他题目的其他选项，只能删除自己题目的题目选项
	
  
	//保存答案
	$("#save_button").click(function(){
		if(saveOperation() == "no_change"){
			alert("页面无修改");
		}else if(saveOperation()){
			alert("保存成功");
		}else{
			alert("保存失败");
		}		
			
	});
	
	//提交答案
	$("#submit_button").click(function(){
		if(!saveOperation()){
			alert("提交失败");
			return;
		}
		
		$.ajax({
	    	url :"/xyeeis/servlet/AddAnswerServlet",
	    	type:"POST",
	    	async:false,
			data: {
			
			     "operation": "addAnswer",
			     "type":"tp",
			 },
			 success:function(data){
				 if(data == "ok"){
					 alert("提交成功");
					 window.location = "../quiz/thanks.html";
				 }else{
					 alert("提交失败");
				 }
			 },
			 error:function(){
				 alert("连接失败");
			 }
	    });
		
				
	});
	
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



//onchang事件

//填空题
function text_change(name){
	//只要有变化，就改变保存标志  为1 保存按钮可以提交
	$("input[name=flag]").attr("value",1);
	var text_val = $("input[name="+name+"]").val();
    var str =  $("input[name="+name+"]").attr("class");
   //判断百分比
    if(text_val){
    	if(str == "isPrecent") {
        var reg = new RegExp(/^((\d|[123456789]\d)(\.[0-9]{0,2})?|100)$/);
        var data = text_val.match(reg); 
	    if (data == null) {
	    	return false;
	    } 
    }else if(str == "yesOrNo"){
    	var reg = new RegExp(/^(0|1)$/);
        var data = text_val.match(reg);
	    if (data == null) {
	        return false;
	     }
    }else{
    	if(text_val <= 1000000){
    		//验证最多保留2位小数的正实数           
	        var reg = new RegExp(/^[0-9]{1,6}(.\d{0,2})?$/);
	        var data = text_val.match(reg);
		    if (data == null ) {	
		    	return false;
		    }    	
    
    }else{
    	return false;
    }
    }
    
}
	
	var arr = name.split("_");
	var queId = arr[1];
	var base_val = $("input[name="+queId+"]").val();

	//改变基准输入框的值
	$("input[name="+queId+"]").val(text_val);
	speedBar();//处理进度条   
}

//单选题
function radio_change(name){
	//只要有变化，就改变保存标志  为1 保存按钮可以提交
	$("input[name='flag']").val("1");
	
	var radio_val = $("input[name="+name+"]:checked").val();

	var arr = name.split("_");
	var queId = arr[1];
	var base_val = $("input[name="+queId+"]").val();
	//改变基准输入框的值
	$("input[name="+queId+"]").val(radio_val);
	
    //第一题单选如果选了无的话，第二、第三题就不用填写
    if(name == "radio_1" && radio_val == "2"){
    	$("input[name='radio_2']").attr("checked",false);
    	$("input[name='radio_2']").attr("disabled","disabled");
    	$("input[name='2']").val("");
    	$("input[name='text_3']").val("");
    	$("input[name='text_3']").attr("disabled","disabled");
    	$("input[name='3']").val("");
    	
    	
    }
    if(name == "radio_1" && radio_val == "1") {
    	$("input[name='radio_2']").removeAttr("disabled");
    	$("input[name='text_3']").removeAttr("disabled");
    }
    
    
    //第五题如果选了无，则第六题不用填写
    if(name == "radio_9" ){
    	if(radio_val == "8") {
    		$("input[name='radio_10']").attr("checked",false);
    		$("input[name='radio_10']").attr("disabled","disabled");
    		$("input[name='10']").val("");
    	}else{
    		$("input[name='radio_10']").removeAttr("disabled");
    	}
    	
    }
    speedBar();//处理进度条
   


}

//多选题的改变
function textbox_change(a) {
	//只要有变化，就改变保存标志  为1 保存按钮可以提交
	$("input[name='flag']").val("1");
	
	
	var name = $(a).attr("ques");
	var choiceName = $(a).attr("name");
	if ($.browser.msie) {
		  $("input[ques="+name+"]").click(function () { 
		   this.blur();   
		   this.focus(); 
		 });   
	};
	if(choiceName == "checkbox_1" && $(a).prop("checked") == true){
		var parent = $(a).parent().parent().parent();
		parent.find("input").attr("checked",false);
		parent.find("input").attr("disabled","disabled");
		$(a).attr("checked",true);
		$(a).removeAttr("disabled");
	}
	if(choiceName == "checkbox_1" && $(a).prop("checked") == false){
		var parent = $(a).parent().parent().parent();
		parent.find("input").removeAttr("disabled");
		
	}
	
	var arr = name.split("_");
	var queId = arr[1];
	var operation = "";
	var base_val = $("input[name="+queId+"]").val();
	var chk_value =""; 
	var str = "";
	
	$("input[ques="+name+"]:checked").each(function(){ 
	  
		str+=$(this).val()+",";
	});
	if(str){
		str =str.substring(0,str.length-1);
	}else{
		str = "-1";
	}
	if(choiceName == "checkbox_1" && queId != "156" && queId != "157"){
		if($(a).attr("checked")){
			str=$(a).val();
		}
	}	
	

	//改变基础月的值
	$("input[name="+queId+"]").val(str);
	speedBar();//处理进度条   
}

var init = function() {
	var question;
	tabChange();
	
	security();
	teaching();
	infrastructure();
	management();	
	
	bintEvent();
	var allDone;
	speedBar();
}
$(document).ready(function(){
	　init();
}); 
$(".back").click(function(){
	window.history.back(-1); 
})

