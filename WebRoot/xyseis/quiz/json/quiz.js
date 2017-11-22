// 获取基础设施部分题目信息
var security = function(){
	$.getJSON("json/5.txt",function(data){
	question = data;
    $("#security_ques_list").html(TrimPath.processDOMTemplate("ques_detail_template", question));
    
    //trimpath模板循环输出时，for循环会多输出一次，将最后一条数据删除
    var t = question.length-1;
    $("#security_ques_list ul:first-child").siblings().eq(t).remove();
    $("#28").addClass("hide_ques_border");
    
    //基准月试卷隐藏显示
	var check_val = [];
	
	$.each(data,function(i,field) {
		
	 check_val.push(field["children"][0]["testType"]);
	 var text_type = field["children"][0]["testType"];//问题的类型  1 单选，2多选，3文字填空，4数字填空
	 var queId = field["children"][0]["queId"];//问题的id
	 var autoId = "";//选择题答案的id
	 var userAns = "";//填空题答案的  内容
	 var html ="<input type='hidden'  name="+queId+" test_type= '"+text_type+"' ";
	 if(field["children"][0]["testType"]=='3'||field["children"][0]["testType"]=='4'){//填空题
		 
		userAns = field["children"][0]["userAns"];
		html+=" value= '"+userAns+"'/>";
		
	 }else{
		 $.each(field["children"][0]["children"],function(j,choice){//单选或者多选题
			 if(choice["is_checked"]=='1'){
				 autoId+=choice["autoId"]+",";
			 }
			 
		 });
		 
		 if(autoId !='' ) {
			 
			 autoId = autoId.substring(0,autoId.length-1);
		 }
		 
		 html+=" value= '"+autoId+"'/>";
	 }
	 
	 $("#base_answer").append(html);
	 console.log(html);
	});
	

	});
}
//获取信息化应用部分题目信息
var management =	function(){
	$.getJSON("json/4.txt",function(data){			
			question = data;
		    $("#management_ques_list").html(TrimPath.processDOMTemplate("ques_detail_template", question));
		    
		    //trimpath模板循环输出时，for循环会多输出一次，将最后一条数据删除
		    var t = question.length-1;
		    $("#management_ques_list ul:first-child").siblings().eq(t).remove();
		    $("#17").addClass("hide_ques_border");
		    
		  //基准月试卷隐藏显示
			var check_val = [];
			
			$.each(data,function(i,field) {
				
			 check_val.push(field["children"][0]["testType"]);
			 var text_type = field["children"][0]["testType"];//问题的类型  1 单选，2多选，3文字填空，4数字填空
			 var queId = field["children"][0]["queId"];//问题的id
			 var autoId = "";//选择题答案的id
			 var userAns = "";//填空题答案的  内容
			 var html ="<input type='hidden'  name="+queId+" test_type= '"+text_type+"' ";
			 if(field["children"][0]["testType"]=='3'||field["children"][0]["testType"]=='4'){//填空题
				 
				userAns = field["children"][0]["userAns"];
				html+=" value= '"+userAns+"'/>";
				
			 }else{
				 $.each(field["children"][0]["children"],function(j,choice){//单选或者多选题
					 if(choice["is_checked"]=='1'){
						 autoId+=choice["autoId"]+",";
					 }
					 
				 });
				 
				 if(autoId !='' ) {
					 
					 autoId = autoId.substring(0,autoId.length-1);
				 }
				 
				 html+=" value= '"+autoId+"'/>";
			 }
			 
			 $("#base_answer").append(html);
			 console.log(html);
			});
			//基准试卷隐藏显示end
		    
	});
}
// 获取基础设施部分题目信息
var research = function(){
	$.getJSON("json/3.txt",function(data)	 {			
			question = data;
		    $("#research_ques_list").html(TrimPath.processDOMTemplate("ques_detail_template", question));
		    
		    //trimpath模板循环输出时，for循环会多输出一次，将最后一条数据删除
		    var t = question.length-1;
		    $("#research_ques_list ul:first-child").siblings().eq(t).remove();
		    $("#10").addClass("hide_ques_border");
		  //基准月试卷隐藏显示
			var check_val = [];
			
			$.each(data,function(i,field) {
				
			 check_val.push(field["children"][0]["testType"]);
			 var text_type = field["children"][0]["testType"];//问题的类型  1 单选，2多选，3文字填空，4数字填空
			 var queId = field["children"][0]["queId"];//问题的id
			 var autoId = "";//选择题答案的id
			 var userAns = "";//填空题答案的  内容
			 var html ="<input type='hidden'  name="+queId+" test_type= '"+text_type+"' ";
			 if(field["children"][0]["testType"]=='3'||field["children"][0]["testType"]=='4'){//填空题
				 
				userAns = field["children"][0]["userAns"];
				html+=" value= '"+userAns+"'/>";
				
			 }else{
				 $.each(field["children"][0]["children"],function(j,choice){//单选或者多选题
					 if(choice["is_checked"]=='1'){
						 autoId+=choice["autoId"]+",";
					 }
					 
				 });
				 
				 if(autoId !='' ) {
					 
					 autoId = autoId.substring(0,autoId.length-1);
				 }
				 
				 html+=" value= '"+autoId+"'/>";
			 }
			 
			 $("#base_answer").append(html);
			 console.log(html);
			});
			//基准试卷隐藏显示end
	});
}
// 获取信息化应用部分题目信息
var teaching =function(){
	$.getJSON("json/2.txt",function(data)	  {			
			question = data;
		    $("#teaching_ques_list").html(TrimPath.processDOMTemplate("ques_detail_template", question));
		    //trimpath模板循环输出时，for循环会多输出一次，将最后一条数据删除
		    var t = question.length-1;
		    $("#teaching_ques_list ul:first-child").siblings().eq(t).remove();
		    $("#8").addClass("hide_ques_border");
		  //基准月试卷隐藏显示
			var check_val = [];
			
			$.each(data,function(i,field) {
				
			 check_val.push(field["children"][0]["testType"]);
			 var text_type = field["children"][0]["testType"];//问题的类型  1 单选，2多选，3文字填空，4数字填空
			 var queId = field["children"][0]["queId"];//问题的id
			 var autoId = "";//选择题答案的id
			 var userAns = "";//填空题答案的  内容
			 var html ="<input type='hidden'  name="+queId+" test_type= '"+text_type+"' ";
			 if(field["children"][0]["testType"]=='3'||field["children"][0]["testType"]=='4'){//填空题
				 
				userAns = field["children"][0]["userAns"];
				html+=" value= '"+userAns+"'/>";
				
			 }else{
				 $.each(field["children"][0]["children"],function(j,choice){//单选或者多选题
					 if(choice["is_checked"]=='1'){
						 autoId+=choice["autoId"]+",";
					 }
					 
				 });
				 
				 if(autoId !='' ) {
					 
					 autoId = autoId.substring(0,autoId.length-1);
				 }
				 
				 html+=" value= '"+autoId+"'/>";
			 }
			 
			 $("#base_answer").append(html);
			 console.log(html);
			});
			//基准试卷隐藏显示end
	});
}
// 获取基础设施部分题目信息
var infrastructure =function(){
	$.getJSON("json/1.txt",function(data)	 {
			question = data;
		    $("#infrastructure_ques_list").html(TrimPath.processDOMTemplate("ques_detail_template", question));
		    
		    //trimpath模板循环输出时，for循环会多输出一次，将最后一条数据删除
		    var t = question.length-1;
		    $("#infrastructure_ques_list ul:first-child").siblings().eq(t).remove();
		    $("#4").addClass("hide_ques_border");
		  //基准月试卷隐藏显示
			var check_val = [];
			
			$.each(data,function(i,field) {
				
			 check_val.push(field["children"][0]["testType"]);
			 var text_type = field["children"][0]["testType"];//问题的类型  1 单选，2多选，3文字填空，4数字填空
			 var queId = field["children"][0]["queId"];//问题的id
			 var autoId = "";//选择题答案的id
			 var userAns = "";//填空题答案的  内容
			 var html ="<input type='hidden'  name="+queId+" test_type= '"+text_type+"' ";
			 if(field["children"][0]["testType"]=='3'||field["children"][0]["testType"]=='4'){//填空题
				 
				userAns = field["children"][0]["userAns"];
				html+=" value= '"+userAns+"'/>";
				
			 }else{
				 $.each(field["children"][0]["children"],function(j,choice){//单选或者多选题
					 if(choice["is_checked"]=='1'){
						 autoId+=choice["autoId"]+",";
					 }
					 
				 });
				 
				 if(autoId !='' ) {
					 
					 autoId = autoId.substring(0,autoId.length-1);
				 }
				 
				 html+=" value= '"+autoId+"'/>";
			 }
			 
			 $("#base_answer").append(html);
			 console.log(html);
			});
			//基准试卷隐藏显示end
	});
}

//进度条
var speedBar = function() {
    var TotalQuesnum = $(".ques_contain").size();
    var ansNum = $(".ques_contain[num='1']").size();   
    //console.log("问题总数是"+TotalQuesnum+";已经做过"+ansNum+"个问题！");     
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
	if(num[0] != sum1) {
		alert("请您仔细核对第26题的答案！");
	}else if(num[6] != sum2 || num[9] != sum3) {
		alert("请您仔细核对第27题的答案！");
	}else{
		SubmitAns();	//检查无误后提交答案
	}	
}

//提交用户初次提交的答案
var SubmitAns = function() {
	$("#submit_button").attr("disabled","disabled");
  
	var choice_array = new Array();
	var text_array = new Array();

   //选择题
   var inputLen = $("input:checked").size();
   var ques =  jQuery.makeArray($("input:checked"));//将每个问题转化为一个数组

   for(var i = 0; i < inputLen; i++) {
        var obj = {"questionId":"","answerId":"","answertext":"null"};
        var value = ques[i].value;	//被选中的选项id
        var t = $("input[value='" + value + "']").attr("ques");
        var quesId = t.split("_")[1];//获得题目id
        var quesClass = $("input[value='" + value + "']").attr("class");
        
        if(quesClass == "option_others"){
             obj.answertext = $("input[type='text'][id='text_choice_" + value + "']").val();//存在其他选项的题目
        }
        obj.questionId = parseInt(quesId);
        obj.answerId = value
        choice_array.push(obj); 
   }

   //填空题
   var txtLen = $("input[type='text'][textkey='text']").size();
   var txtques =  jQuery.makeArray($("input[type='text'][textkey='text']"));
   for(var j = 0; j < txtLen; j++) {
        var obj = {"questionId":"","answertext":"","answerId":0};
        var txtname = txtques[j].name;//遍历每个填空题
        if(txtname != "") {
            var txtcontent = $("input[name='" + txtname +"']").val();
            var txtquesId = $("input[name='" + txtname +"']").attr("ques");
            var txtId = txtquesId.split("_")[1];
            obj.questionId = parseInt(txtId);
            obj.answertext = txtcontent;
            text_array.push(obj);
        }       
   }
   //向后台传输数据
   var choiceAnswer = JSON.stringify(choice_array); 
   var blankAnswer = JSON.stringify(text_array); 
    $.ajax({
		 url: "/xyeeis/servlet/PaperAnswerServlet",
		 type:"POST",
		 data: {
		     "choice_array":choiceAnswer,
		     "text_array":blankAnswer,
		      "operation": "addAns"
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

var bintEvent = function() {
	
	//填空题
	$("input[type='text']").live("keyup", function() {
		//标记已经答过该空---进度条会用到
        $(this).parents(".ques_contain").attr("num", "1");
       
        //标记已经答过该题目---判断是否答完全部题目会用到
        $(this).parents(".groupId_contain").attr("groupnum", "1");
        speedBar();//处理进度条
        $(this).attr("textKey", "text");//当填空题已经填写时则出现新属性 
        var parClass = $(this).parent().attr("class");
        if(parClass == "text_num_contain") {
	        var tmptxt = $(this).val();
	        var str =  $(this).attr("class");
	       //判断百分比
	        if(str == "isPrecent") {
		        var reg = new RegExp(/^((\d|[123456789]\d)(\.[0-9]{0,2})?|100)$/);
		        //var reg = new RegExp(/^((\d|[1-9]\d)(\.\d\d?)?|100)$/);
		        var data = tmptxt.match(reg);
			    if (data == null) {
			        alert("请填写正确的最多仅保留两位小数的百分数!");
			    	$(this).attr("value","");
			    	return false;
			    } 
	        }
	        //判断0和1
	        else if(str == "yesOrNo"){
	        	var reg = new RegExp(/^(0|1)$/);
		        var data = tmptxt.match(reg);
			    if (data == null) {
			        alert("请填写0或1!");
			    	$(this).attr("value","");
			    	return false;
			    } 
	        //判断实数
	        }else{
	        	if(tmptxt <= 1000000){
	        		//验证最多保留2位小数的正实数           
			        var reg = new RegExp(/^[0-9]+(.[0-9]{0,2})?$/);
			        var data = tmptxt.match(reg);
				    if (data == null ) {
				        alert("请填写数字，并且最多只保留两位小数!");
				    	$(this).attr("value","");
				    	return false;
				    } 
	        	}else{
	        		alert("请仔细核对您的答案！");
	        		$(this).attr("value","");
	        		return false;
	        	}	        	
	        }
	       
        }
     });
	
	
	
	$("input[id='text_choice_330']").live("blur", function() {
		//var reg = new RegExp(/^http:\/\/([0-9a-z][0-9a-z\-]*\.)+[a-z]{2,}(:\d+)?\/[0-9a-z%\-_\/\.]+/i)
		var reg = new RegExp(/^([hH][tT]{2}[pP]:\/\/|[hH][tT]{2}[pP][sS]:\/\/)(([A-Za-z0-9-~]+)\.)+([A-Za-z0-9-~\/])+$/)
		var tmptxt = $(this).val();
		var data = tmptxt.match(reg);
		if (data == null) {
	        alert("请输入以http://或者https://开头的正确的网址!");
	    	$(this).attr("value","");
	    }
	});
	
	
	var choiceArray = new Array();//处理 其他选项 ，记录选项
    var choiceQuesArray = new Array();//数组对象，避免删掉其他题目的其他选项，只能删除自己题目的题目选项
	
    
    //多选题
	$("input[type='checkbox']").live("click", function() {
		
        var a = $(this).parents(".ques_contain");//题目 整体 选中
        $(this).parents(".ques_contain").attr("num", "1");
        $(this).parents(".groupId_contain").attr("groupnum", "1");
        speedBar();//处理进度条        
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
                a.find(".option_others").parent().parent().append("<input type='text' id='text_choice_" + otherId + "' ques='ques_" + quesId + "' placeholder='请填写网站'  onchange='choice_change("+otherId+")'/>");
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
	    	} else {
	    		$(this).parents(".ques_contain").siblings().find('input').removeAttr("disabled");
	    	}
	    }
    });
  
	
	//提交答案
	$("#submit_button").click(function(){
		
		//判断用户是否做完全部题目
	 // var unfinishQues =  jQuery.makeArray($(".groupId_contain[groupnum='0']"));
	  var unfinishQues =  jQuery.makeArray($(".groupId_contain[groupnum='0']"));
      var unfinishArr = new Array();
      for(var i = 0; i< unfinishQues.length; i++) {
          var groupId = unfinishQues[i].id;
          if(jQuery.inArray(groupId, unfinishArr) == -1){	//id不在数组中，则返回-1
            unfinishArr.push(groupId);
          }
      }
      if(unfinishArr.length == 0 && allDone == "100%") {
    	  checkAns();//检查用户的第26、27题答案，检查通过后再提交答案 
         //SubmitAns();///提交用户初次的答案 
      } else {
          alert("所有问题都是必答题，请您作答全部题目后再提交！");
          $(".groupId_contain[groupnum='0']").addClass("ques_border");
          setTimeout("hideRedBorder()",3000);                           
      }			
	});
	
}

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



//onchang事件

//填空题
function text_change(name){	
	var text_val = $("input[name="+name+"]").val();
    var str =  $("input[name="+name+"]").attr("class");
   
   //判断百分比
    if(str == "isPrecent") {
        var reg = new RegExp(/^((\d|[123456789]\d)(\.[0-9]{0,2})?|100)$/);
        //var reg = new RegExp(/^((\d|[1-9]\d)(\.\d\d?)?|100)$/);
        var data = text_val.match(reg); 
	    if (data == null) {
	    	return false;
	    } 
    }
    
    if(str == "yesOrNo"){
    	var reg = new RegExp(/^(0|1)$/);
        var data = text_val.match(reg);
	    if (data == null) {
	        return false;
	     }
    }
    
    
	
	var arr = name.split("_");
	var queId = arr[1];
	var base_val = $("#"+queId+"").val();
	var operation = "";//操作，1 update，2 insert
	if(base_val == ""){
		operation = "2";
	}else{
		operation = "1";
	}
	//改变基准输入框的值
	 $("#"+queId+"").val(text_val);
	 //改变数据库里的值
	 $.ajax({
		 url:"",
		 data:{
			 "queId":queId,
			 
			 "operation":operation,
			 "value":text_val
		 },
		 success:function(){},
		 error:function(){}
	 });	
}

//单选题
function radio_change(name){
	if ($.browser.msie) {
		 $("input[name="+name+"]").click(function () { 
		   this.blur();   
		   this.focus(); 
		  });   
	};
	var radio_val = $("input[name="+name+"]:checked").val();
	var arr = name.split("_");
	var queId = arr[1];
	var base_val = $("#"+queId+"").val();
	var operation = "";//操作，1 update，2 insert
	if(base_val == ""){
		operation = "2";
	}else{
		operation = "1";
	}
	//改变基准输入框的值
	 $("#"+queId+"").val(radio_val);
	 //改变数据库里的值
	 $.ajax({
		 url:"",
		 data:{
			 "queId":queId,
			 "operation":operation,
			 "value":radio_val
		 },
		 success:function(){},
		 error:function(){}
	 });	
	 
	
}

//多选题的改变
function textbox_change(a) {
	var name = $(a).attr("ques");
	if ($.browser.msie) {
		  $("input[ques="+name+"]").click(function () { 
		   this.blur();   
		   this.focus(); 
		 });   
	};
	
	
	var arr = name.split("_");
	var queId = arr[1];
	var operation = "";
	var base_val = $("#"+queId+"").val();
	var chk_value =""; 
	var str = "";
		
		$("input[ques="+name+"]:checked").each(function(){ 
		  
			str+=$(this).val()+",";
			//alert($(this).val());	
		});
	str =str.substring(0,str.length-1);
	if(base_val==""){
		operation = '2';
	}else{
		operation = '3';
	}

	//改变基础月的值
	$("#"+queId+"").val(str);
	$.ajax({
		url:"",
		data:{
			"queId":queId,
			 "operation":operation,
			 "value":str
		},
		type:"post",
		dataType:"json",
		success:function(){},
		error:function(){}
		
	});
}

//多选里边的填空题
function choice_change(queId){
	var val = $("#text_choice_"+queId).val();
	$.ajax({
		url:"",
		data:{
			"choiceId":queId,
			"value":val
		},
		type:"post",
		success:function(){},
		error:function(){}
		
	});
	
}


var init = function() {
	var question;
	tabChange();
	bintEvent();
	teaching();
	infrastructure();
	management();
	research();
	security();
	var allDone;
}

init();