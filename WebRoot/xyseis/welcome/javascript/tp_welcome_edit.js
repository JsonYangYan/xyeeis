//判断提交状态  1没有提交过（包括本月和以前） ，2 提交过，且本月提交过，3 提交过，且本月没有提交

$.ajax({
	url:"../../servlet/SubmitStateAndDaysServlet",
	data:{
		"school_type":"tp"
	},
	type:"post",
	success:function(data){
		var data = JSON.parse(data)[0];
		console.log(data);
		var fig = data["fig"];
		if(fig == 1){
			$(".update_submit a").html("问卷填写");
			$(".submit").hide();
			$(".button_time_diff").hide();
		}
		if(fig == 2){
			$(".submit").hide();
			var datys = data["days"];
			$(".button_time_diff").html("距上次提交"+datys+"天");
		}
		if(fig == 3){
			$(".button_time_diff").hide();
		}
	},
	error:function(){
		
	}
});

$(".submit").click(function(){
	$.ajax({
		url:"../../servlet/NoUpdateSubServlet",
		data:{
			"type":"tp"
		},
		type:"post",
		success:function(data){
			if(data == 1){
				alert("提交成功");
				$(".submit").hide();
				$(".button_time_diff").html("距上次提交0天");
			}else{
				alert("提交失败");
			}
		},
		error:function(){
			
		}
		
	});
	
});

$("#next_information").live("click", function() {
	
	$.ajax({
		type : "POST",
		url : "../../servlet/SchoolInforServlet",
		data : {
			"operation" : "judge",
		},
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		success : function(data) {
			window.location.replace("../quiz/quiz.html");
		},
		error : function() {
			//alert("information/连接失败！");
			var txt=  "information/连接失败！";
			window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
		}
	});
	
});


//这个方法包括了从数据库中查询本月分是否有上个月的数据，没有就复制一份
var user_info = function() {

	$.ajax({
		url: "../../servlet/SchoolInforServlet",
		type: 'POST',
		data : {
			
			"operation" : "edit_display",
			"school_type_":"tp"
		},
		success:function(data) {
			//将数据原样显示在input中
			var data = jQuery.parseJSON(data);
			$("#area").html(data[0].schoolArean);
			$("#school_addr").html(data[0].schoolName);
			$("#staffteacher").html(data[0].teacherNumber);
			$("#nativestudent").html(data[0].studentNumber);
			$("#relation").html(data[0].personName);
			$("#mobile").html(data[0].telPhone);
			$("#email").html(data[0].eMail);
			$("#class_num").html(data[0].classNumber);
			$("#school_area").html(data[0].schoolTown);
			$("#school_type").html(data[0].schoolType);  
		},
		error:function() {
			var txt=  "获取用户基本信息连接数据库失败！";
			window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
		}
	});
}

function showSomeData() {
	$.ajax({
		url:"../../servlet/SchoolUserServlet",
		type:"post",
		data:{
			"operation":"somedata",
			"type":"tp",
		},
		success:function(data){
			var data = JSON.parse(data);
			$("#lastMonth").text(data[0]["lastMonth"]);
			var str = "";
			for(var i=0;i<data[0]["data"].length;i++){
				
				var s = data[0]["data"][i]["unitSymbol"];
				s = s.substring(0,s.length-1);
				str+="<tr><td>"+ '*' + data[0]["data"][i]["queContent"] + ':' + "</td>" + "<td>" + data[0]["data"][i]["answer"] + s + "</td></tr>";
			}
			$("#somedtab").html(str);
		},
		error:function(data){
			alert("数据库连接失败");
		}
	});
}

function getRanking(selectDate){
	$.ajax({
		url:"../../servlet/RankingAndAverageServlet",
		type:"post",
		data:{
			"operation":"shixianrangk",
			"userTime":selectDate
		},
		success:function(data){
			var data = JSON.parse(data);
			var shi = data[0]["shi"];
			var xian = data[0]["xian"];
			$("#shi").html(shi);
			$("#xian").html(xian);
		},
		error:function(){
			alert("加载失败");
		}
	});
}

function average(selectDate){
	$.ajax({
		url:"../../servlet/RankingAndAverageServlet",
		type:"post",
		async:false,
		data:{
			"operation":"average",
			"userTime":selectDate
		},
		success:function(data){
			var series = JSON.parse(data);
			
			var name1 =series[0]["name"];
			var name2 =series[1]["name"];
			var name3 =series[2]["name"];
			 // 基于准备好的dom，初始化echarts实例
	        var myChart = echarts.init(document.getElementById('average'));
	        // 指定图表的配置项和数据
				option = {
					tooltip: {
						trigger: 'axis'
					},
					toolbox: {
						feature: {
							dataView: {show: false, readOnly: true},
							magicType: {show: false, type: ['line', 'bar']},
							restore: {show: false},
							saveAsImage: {show: false}
						}
					},
					legend: {
						data:[name1,name2,name3]
					},
					xAxis: [
						{	
							axisLabel: {
								rotate: 30,
							},
							type: 'category',
							data: ['基础设施','教学信息化','教研信息化','管理与服务','信息化保障']
						}
					],
					yAxis: [
						{
							type: 'value',
//							name: '温度',
							min: 0,
							max: 100,
							interval: 10,
							axisLabel: {
								formatter: '{value}'
							}
						}
					],
					series:series
				};

	        // 使用刚指定的配置项和数据显示图表。
	        myChart.setOption(option);
		},
		error:function(){
			alert("加载失败");
		}
	});
}


var init = function() {
	user_info();	//页面初始化---显示用户数据
	showSomeData();//显示用户的一些数据
	getRanking(str_before_month);//求排名
	//average($('#selectAge_aver').val());//五大维度的平均值	
}

//获取时间月份
//加载时间
var data_num = new Date();
var data_month=data_num.getMonth() + 1;
var data_year=data_num.getFullYear();
var str =data_year + "-" + '0' +(data_month);
var str_before_month =data_year + "-" + '0' +(data_month-1);
getRanking(str_before_month);

for(var i=1;i<=12;i++){
	var data_before=data_month-i;

	if (data_before==0) {
		var data_before=12;
		var str_befor=(data_year-1)+ "-" +(data_before);
//		console.log(str_befor)
		var option=$("<option>" + str_befor + "</option>")
		$(".selectAge").append(option);
		if(option.text() == "2016-12"){
			break;
		}
		$("#drop1 li").eq(0).css("color",'black');
		$("#drop1 li").eq(0).css("cursor",'pointer');
		$("#drop1 li").eq(0).click(function(){
			getRanking($(this).text());
		});
		break;
	} else{
		var str_befor=data_year + "-" + '0' +(data_before);
		var option=$("<option>" + str_befor + "</option>")
		$(".selectAge").append(option);
		console.log(str_befor)
		if(option.text() == "2017-01" || option.text() == "2017-02"){
			break;
		}
		$("#drop1 li").eq(data_before).css("color",'black');
		$("#drop1 li").eq(data_before).css("cursor",'pointer');
		$("#drop1 li").eq(data_before).click(function(){
			getRanking($(this).text());
		})
	}

}


$('#selectAge_aver').change(function() {
	var fig_index=$(this).val()
	console.log(fig_index)
	average(fig_index);
})

init();


















