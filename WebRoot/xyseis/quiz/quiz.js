// 获取基础设施部分题目信息
var security = function(){
	$.ajax({
		url: "/xyeeis/servlet/PaperResultsServlet",
		type: 'POST',
		async:false,
		data:{
			dt:'5',
		},
		dataType:'json',
		success:function(data){
			question = data;
		    $("#security_ques_list").html(TrimPath.processDOMTemplate("ques_detail_template", question));
		    
		    //trimpath模板循环输出时，for循环会多输出一次，将最后一条数据删除
		    var t = question.length;
		    $("#security_ques_list .groupId_contain").eq(t).remove();
		    $("#28").addClass("hide_ques_border");
		    
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
			if($(".option_others").attr("checked")=="checked"){  
				$("#base_answer").append("<input type='hidden' name='330_text' value=''>");
				$("input[name='330_text']").val($("#text_choice_330").val());
	        }
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
		},
		dataType:'json',
		success:function(data) {			
			question = data;
		    $("#management_ques_list").html(TrimPath.processDOMTemplate("ques_detail_template", question));
		    
		    //trimpath模板循环输出时，for循环会多输出一次，将最后一条数据删除
		    var t = question.length;
		    $("#management_ques_list .groupId_contain").eq(t).remove();
		    $("#17").addClass("hide_ques_border");
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
// 获取基础设施部分题目信息
var research = function(){

	$.ajax({
		url: "/xyeeis/servlet/PaperResultsServlet",
		type: 'POST',
		async:false,
		data:{
			dt:'3',
		},
		dataType:'json',
		success:function(data) {			
			question = data;
		    $("#research_ques_list").html(TrimPath.processDOMTemplate("ques_detail_template", question));
		    
		    //trimpath模板循环输出时，for循环会多输出一次，将最后一条数据删除
		    var t = question.length;
		    $("#research_ques_list .groupId_contain").eq(t).remove();
		    $("#10").addClass("hide_ques_border");
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
			alert("获取question3连接数据库失败!");
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
		},
		dataType:'json',
		success:function(data) {			
			question = data;
		    $("#teaching_ques_list").html(TrimPath.processDOMTemplate("ques_detail_template", question));
		    //trimpath模板循环输出时，for循环会多输出一次，将最后一条数据删除
		    var t = question.length;
		    $("#teaching_ques_list .groupId_contain").eq(t).remove();
		    $("#8").addClass("hide_ques_border");
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
		},
		dataType:'json',
		success:function(data) {
			question = data;
		    $("#infrastructure_ques_list").html(TrimPath.processDOMTemplate("ques_detail_template", question));
		    
//		    //trimpath模板循环输出时，for循环会多输出一次，将最后一条数据删除
		    var t = question.length;
		    $("#infrastructure_ques_list .groupId_contain").eq(t).remove();
		    $("#4").addClass("hide_ques_border");
		  
		    
		    
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
    if($("input[name=radio_152]:checked").val() == "278"){//如果第152题选的是否，则从总体的个书上减3
    	TotalQuesnum = TotalQuesnum-3;
    	if($("input[name=radio_153]:checked").val()){
    		
    		ansNum = ansNum -1;
    	}
    	if($("input[name=radio_154]:checked").val()){
    		ansNum = ansNum -1;
    	}
    	if($("input[name=radio_155]:checked").val()){
    		ansNum = ansNum -1;
    	}
	}
    //alert($("input[name=radio_153]:checked").val());
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
   // alert(allDone);
    
    //进度条100%时，提交按钮可以点击
    if(allDone == "100%"){
    	$("#submit_button").attr("style","background-color:#ffc300");
    	$("#submit_button").removeAttr("disabled");
    	
    }else{
    	$("#submit_button").attr("disabled","disabled");
    }
  
   
};

//检查用户的第26、27题答案，检查通过后才能提交答案
var checkAns = function() {
	var num = new Array();
	for(i=0;i<=12;i++){
		var j = 169+i;
		num[i] = Number( $("input[name='text_" + j + "']").val());		
	}
	//num[0]存放的是第169题答案   1~5之和
	//num[6]是第175题   7~8之和
	//num[9]是第175题   10~12之和
	var sum1 = num[1] + num[2] + num[3] + num[4] + num[5];
	var sum2 = num[7] + num[8];
	var sum3 = num[12] + num[11] + num[10];
	sum1 = sum1.toFixed(2);
	sum2 = sum2.toFixed(2);
	sum3 = sum3.toFixed(2);
	if(num[0] != sum1) {
		 $("#26").addClass("ques_border");
         setTimeout("hideRedBorder()",5000); 
		$("#26").find(".errorAnswer").html("* 请您仔细核对第26题的答案！");
		return false;
	}else if(num[6] != sum2 || num[9] != sum3) {
		$("#27").addClass("ques_border");
		setTimeout("hideRedBorder()",5000); 
		$("#27").find(".errorAnswer").html("* 请您仔细核对第27题的答案！");
		
		return false;
	}
	return true;
}


//保存操作
function saveOperation() {
		var flag = checkAns();//检查用户的第26、27题答案，检查通过后再提交答案 
		var result = false;
		if($("input[name='flag']").val()=='0'){//页面没有修改
			return "no_change";
		}
	 if(flag){
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
		    var blankAnswer = JSON.stringify(text_array); 
		    $.ajax({
		    	url :"/xyeeis/servlet/AddAnswerTempServlet",
		    	type:"POST",
		    	async:false,
				data: {
				     "choice_array":choiceAnswer,
				     "text_array":blankAnswer,
				     "operation": "addAns"
				 },
				 success:function(data){
					 if(data == "ok"){
						 result = true;
					 }
				 },
				 error:function(){}
		    });
		    
		    //多选题里边的填空题
		    
		    var text_val =$("input[name='330_text']").val();
		    if(text_val){
		    	$.ajax({
		    		url :"/xyeeis/servlet/AddAnswerTempServlet",
			    	type:"POST",
			    	async:false,
					data: {
					     "val":text_val,
					     "operation": "330_text"
					 },
					 success:function(){},
					 error:function(){}
		    	});
		    }
		    
	 }
	 return result;
}


var bintEvent = function() {
	
	//填空题
	$("input[type='text']").live("blur", function() {
		
		//标记已经答过该空---进度条会用到
        $(this).parents(".ques_contain").attr("num", "1");
       
        //标记已经答过该题目---判断是否答完全部题目会用到
        $(this).parents(".groupId_contain").attr("groupnum", "1");
        
        speedBar();//处理进度条
        
        $(this).attr("textKey", "text");//当填空题已经填写时则出现新属性 
        var parClass = $(this).parent().attr("class");
        
        var tmptxt = $(this).val();
        if(parClass == "text_num_contain" && tmptxt) {
	       
	        var str =  $(this).attr("class");
	       //判断百分比
	        if(str == "isPrecent") {
		        var reg = new RegExp(/^((\d|[123456789]\d)(\.[0-9]{0,2})?|100)$/);
		        var data = tmptxt.match(reg);
			    if (data == null) {
			    	$(this).parent().parent().parent().find(".errorAnswer").html("* 填写有误，请重新填写！百分数小数点后最多保留两位有效数字！");
			    	$(this).attr("value","");
			    	return false;
			    } else {
			    	$(this).parent().parent().parent().find(".errorAnswer").html("");
			    }
	        }
	        //判断0和1
	        else if(str == "yesOrNo"){
	        	var reg = new RegExp(/^(0|1)$/);
		        var data = tmptxt.match(reg);
			    if (data == null) {
			    	$(this).parent().parent().parent().find(".errorAnswer").html('* 填写有误，请重新填写! 若"已联网"请填"1";若"未联网"请填写"0"!');
			    	$(this).attr("value","");
			    	return false;
			    } else {
			    	$(this).parent().parent().parent().find(".errorAnswer").html("");
			    }
	        //判断实数
	        }else{
	        	if(tmptxt <= 1000000){
	        		//验证最多保留2位小数的正实数           
			        var reg = new RegExp(/^[0-9]{1,6}(.\d{0,2})?$/);
			        var data = tmptxt.match(reg);
				    if (data == null ) {
				    	$(this).parent().parent().parent().find(".errorAnswer").html("* 请校对后重新填写！小数点后最多保留两位有效数字！");
				    	$(this).attr("value","");
				    	return false;
				    }else{
				    	$(this).parent().parent().parent().find(".errorAnswer").html("");
				    } 
	        	}else{
	        		$(this).parent().parent().parent().find(".errorAnswer").html("* 请校对后重新填写！您填写的数据已超出最大值范围 !");
	        		$(this).attr("value","");
	        	}	        	
	        }
	       
        }
     });
	
	
	$("input[id='text_choice_330']").live("blur", function() {
		var reg = new RegExp(/^([hH][tT]{2}[pP]:\/\/|[hH][tT]{2}[pP][sS]:\/\/)(([A-Za-z0-9-~]+)\.)+([A-Za-z0-9-~\/])+$/)
		var tmptxt = $("input[id='text_choice_330']").val();
	
		var data = tmptxt.match(reg);
		if (data == null && tmptxt) {
			if(($(".option_others").attr("checked")=="checked")) {
				$(this).parent().parent().parent().parent().find(".errorAnswer").html('* 请输入以"http://"或"https://"开头的网址！！');
			}
			
			$(this).attr("value","");
	    }else{
	    	$(this).parent().parent().parent().parent().find(".errorAnswer").html('');
	    }
	});
	
	
	var choiceArray = new Array();//处理 其他选项 ，记录选项
    var choiceQuesArray = new Array();//数组对象，避免删掉其他题目的其他选项，只能删除自己题目的题目选项
	
    
    //多选题
	$("input[type='checkbox']").live("click", function() {
		
        var a = $(this).parents(".ques_contain");//题目 整体 选中
        $(this).parents(".ques_contain").attr("num", "1");
        $(this).parents(".groupId_contain").attr("groupnum", "1");
             
        var quesId = a.attr("id");	//题目id        
        var choiceId = $(this).val();	//当前选项的id
        var choiceName = $(this).attr("name");	//当前选项的name
        
        //var e = $(this).val();//当前选中optionId的id
        //var b = a.find(".option_relation").toArray();//查看是否有关联选项
        var others = a.find(".option_others").toArray();

        
        //输入网站
        if(others.length != 0) {
            var otherId = a.find(".option_others").val();
            var y =  jQuery.inArray(quesId, choiceQuesArray);//判断是否已经在数组中存过
            if( y == -1) {
                var choiceObj = {};
                choiceQuesArray.push(quesId);
                choiceObj.key = quesId;
                choiceObj.value = otherId;
                choiceArray.push(choiceObj);
            }
            if(otherId == choiceId) {
                $("#text_choice_" + otherId).remove();
                if(!$("input[id='text_choice_330']").attr("id"))
                a.find(".option_others").parent().parent().append("<input type='text' id='text_choice_330' ques='ques_330' test_type=3 placeholder='请填写网站'  onchange='choice_change(330)'/>");
                $("#text_choice_" + otherId).focus();
               
            } else {
                for(var i = 0 ; i < choiceArray.length; i++) {
                    if(choiceArray[i].key == quesId) {
                        var optionid = choiceArray[i].value;
                        break;
                    }
                } 
            }
           
                
        }//其他选项  end/////
		 
		 //点击“无”选项，其余选项禁止点击
		 if(choiceName == "checkbox_1" && quesId != "156" && quesId != "157"){
		 	if($(this).attr('checked'))	{	//选中value为1的框，即“无”选项
		 		$(this).parents(".multi_content").siblings().find('input').attr("disabled","disabled");
		 		$(this).parents(".multi_content").siblings().find('input').removeAttr("checked");
		 	}else{	//取消选中value为1的框，即“无”选项
		 		$(this).parents(".multi_content").siblings().find('input').removeAttr("disabled");
		 	}		 	
		 }
		 if(!(a.find(".option_others").attr("checked")=="checked")){ 
	        	$("input[id='text_choice_330']").hide();
	        	$("input[name='330_text']").remove();
	        	setTimeout(function(){$(".option_others").parent().parent().parent().parent().parent().find(".errorAnswer").html('');}, 100);
	        
	        }else{        	
	        	$("input[id='text_choice_330']").show();
	        	$("#base_answer").append("<input type='hidden' test_type=3 name='330_text' value=''>");
	        }
		 speedBar();//处理进度条   
	
    });
    
    //单选题
	$("input[type='radio']").live("click", function() {
        var a = $(this).parents(".ques_contain");//题目 整体 选中
        $(this).parents(".ques_contain").attr("num", "1");
        $(this).parents(".groupId_contain").attr("groupnum", "1");
        speedBar();//处理进度条
        
        var quesId = a.attr("id");	//题目id        
        var choiceId = $(this).val();	//当前选中optionId的id	    
	    if (quesId == "152") {
	    	if(choiceId == "278") {
	    		$(this).parents(".ques_contain").siblings().attr("num", "1");
	    		$(this).parents(".ques_contain").siblings().find('input').attr("disabled","disabled");
	    		$(this).parents(".ques_contain").siblings().find('input').removeAttr("checked");
	    		// $(this).attr("checked",true);
	    		$("input[name='153']").attr("value",'');
	    		$("input[name='154']").attr("value",'');
	    		$("input[name='155']").attr("value",'');
	    	} else {
	    		$(this).parents(".ques_contain").siblings().find('input').removeAttr("disabled");
	    	}
	    }
    });
  
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
			
			     "operation": "addAnswer"
			 },
			 success:function(data){
				 if(data == "ok"){
					 alert("提交成功");
					 window.location = "thanks.html";
				 }else{
					 alert("提交失败");
				 }
				 
			},
			 error:function(){}
	    });
		
				
	});
	
}

var hideRedBorder = function() {
	$(".groupId_contain").removeClass("ques_border");
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

}

//单选题
function radio_change(name){
	//只要有变化，就改变保存标志  为1 保存按钮可以提交
	$("input[name='flag']").val("1");
	if ($.browser.msie) {
		 $("input[name="+name+"]").click(function () { 
		   this.blur();   
		   this.focus(); 
		  });   
	};
	var radio_val = $("input[name="+name+"]:checked").val();

	var arr = name.split("_");
	var queId = arr[1];
	var base_val = $("input[name="+queId+"]").val();
	//改变基准输入框的值
	$("input[name="+queId+"]").val(radio_val);

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
	
}

//多选里边的填空题
function choice_change(choiceId){
	var reg = new RegExp(/^([hH][tT]{2}[pP]:\/\/|[hH][tT]{2}[pP][sS]:\/\/)(([A-Za-z0-9-~]+)\.)+([A-Za-z0-9-~\/])+$/)
	var val = $("#text_choice_330").val();
	if(val){
		var data = val.match(reg);
		if (data == null) {
			return false;
		}
	}
	$("input[name='flag']").val("1");
	$("input[name='330_text']").val(val);

}


var init = function() {
	var question;
	tabChange();
	
	security();
	teaching();
	infrastructure();
	management();	
	research();
	
	bintEvent();
	var allDone;
	speedBar();
}
$(document).ready(function(){
	　init();
}); 
$(".back").click(function(){
	window.history.back(-1); 
	console.log(111)
})

