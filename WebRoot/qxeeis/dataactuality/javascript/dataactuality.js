function show_time_data() {
	init();
}

//初始化函数
var init = function() {
	
//平均分
$.ajax({
		url: "../../../servlet/QxBlankAvgServlet",
		type: "post",
		dataType:"text",
		data: {
			"currentdate":$(".total_title").find(".total_active").html()
		},
		success: function(json) {
			var json = JSON.parse(json);
			var temp_arr =  new Array();
			for(var i=0; i<json.length; i++){
				temp_arr.push([json[i].area,json[i].value]);
			}
			var norm=['出口带宽(M)','平均带宽(M)','有线网络覆盖率(%)','无线网络覆盖率(%)','电子备课教室数','计算机教室数','计算机教室总座位数','计算机教室使用率(%)','非故障电脑百分比(%)','录播教室数','多媒体教室数','教师终端台式机数','教师终端笔记本数','教师终端平板电脑数','学生终端台式机数','学生终端笔记本数','学生终端平板电脑数','网络学习空间开通比例(%)','网络学习平台开通比例(%)','数字化资源课程比例(%)','互动平台与家长交流比例(%)','2016年信息化经费(万元)','当月教育总经费(万元)','当月信息化经费(万元)','网络建设与设备费用(万元)','资源与平台开发费用(万元)','培训费用(万元)','运行和维护费用(万元)','研究及其他费用(万元)','信息技术课程教师数','专职信息技术教师数','兼职信息技术教师数','信息化支持人员','聘请专职人员','信息技术专业兼任教师','其他专业兼任教师','信息技术能力培训教师数','教师人均参加信息能力培训课时'];
			$html = "";
			$(".data_title>li:nth-child(2)").text(temp_arr[0][0])
			$(".data_title>li:nth-child(3)").text(temp_arr[1][0])
			for(var i = 0; i < temp_arr[0][1].length; i++) {
				var j = i+1;
				if(j%2 == 1){
					$html += "<ul>";
					$html += "<li>" + norm[i] + "</li>" + "<li>" + temp_arr[0][1][i] + "</li>" + "<li>" + temp_arr[1][1][i] + "</li></ul>";
				} else {
					$html += "<ul style='background-color: #DCE9F9;'>";
					$html += "<li>" + norm[i] + "</li>" + "<li>" + temp_arr[0][1][i] + "</li>" + "<li>" + temp_arr[1][1][i] + "</li></ul>";
				}
			}
			$(".data_now").html($html);
			$(".data_now ul:eq(18)").after("<ul><li>平均数据</li><li></li><li></li></ul>");
			$(".data_now ul:eq(19)>li:nth-child(2)").text(temp_arr[0][0])
			$(".data_now ul:eq(19)>li:nth-child(3)").text(temp_arr[1][0])
			
		},
		error: function() {
			alert("失败");
		}
	});
//教育概况
$.ajax({
	url: "../../../servlet/QxEduSurveyServlet",
	type: "post",
	dataType:"text",
	data: {
		"currentdate":$(".total_title").find(".total_active").html()
	},
	success: function(json) {
		var json = JSON.parse(json);
		console.log(json);
		var temp_arr =  new Array();
		for(var i=0; i<json.length; i++){
			temp_arr.push([json[i],json[i]]);
		}
		var norm=['城镇学校数','农村学校数','高中学校数','初中学校数','小学学校数','其他类型学校数','学生人数','教师人数'];
		console.log(temp_arr[0][0].edu_norm);
		$html = "";
		for(var i = 0; i < 8; i++) {
			switch (i){
			case 0:
				$html += "<ul style='background-color: #DCE9F9;'>";
				$html += "<li>" + norm[0] + "</li>" + "<li>" + temp_arr[0][0].townSchoolNum + "</li></ul>";
				break;
			case 1:
				$html += "<ul>";
				$html += "<li>" + norm[1] + "</li>" + "<li>" + temp_arr[0][0].villageSchoolNum+ "</li></ul>";
				break;
			case 2:
				$html += "<ul style='background-color: #DCE9F9;'>";
				$html += "<li>" + norm[2] + "</li>" + "<li>" + temp_arr[0][0].highSchoolNum + "</li></ul>";
				break;
			case 3:
				$html += "<ul>";
				$html += "<li>" + norm[3] + "</li>" + "<li>" + temp_arr[0][0].juniorSchoolNum + "</li></ul>";
				break;
			case 4:
				$html += "<ul style='background-color: #DCE9F9;'>";
				$html += "<li>" + norm[4] + "</li>" + "<li>" + temp_arr[0][0].primarySchoolNum + "</li></ul>";
				break;
			case 5:
				$html += "<ul>";
				$html += "<li>" + norm[5] + "</li>" + "<li>" + temp_arr[0][0].otherSchoolNum + "</li></ul>";
				break;
			case 6:
				$html += "<ul style='background-color: #DCE9F9;'>";
				$html += "<li>" + norm[6] + "</li>" + "<li>" + temp_arr[0][0].studentNum + "</li></ul>";
				break;
			case 7:
				$html += "<ul>";
				$html += "<li>" + norm[7] + "</li>" + "<li>" + temp_arr[0][0].teacherNum + "</li></ul>";
				break;
			default:
				break;
			}
		}
		$(".edu_now").html($html);
		
	},
	error: function() {
		alert("失败");
	}
});

//多选题柱状图
var formNewArr_func_3 = function(id,divplace,title,type) {
	 $.ajax({
		 url: "/xyeeis/servlet/QxChoicePectServlet",
		 type: "POST",
		 data:{
			 "id":id,
			 "currentdate":$(".total_title").find(".total_active").html()
		 },
		 success:function(bindData) {
			    var result = JSON.parse(bindData);
				var colors = new Array(
						'#4572a7','#aa4643','#89a54e','#80699b','#3d96ae', '#db843d','#4572a7','#aa4643','#89a54e',
						'#80699b','#3d96ae', '#db843d','#4572a7','#aa4643','#89a54e','#80699b','#3d96ae','#db843d',
					    '#4572a7','#aa4643','#89a54e','#80699b','#3d96ae','#db843d','#4572a7','#aa4643','#89a54e',
						'#80699b','#3d96ae','#db843d','#92a8cd'
		          );
				var categories = new Array();
				var datacol = new Array();

				 var name = 'Option brands';
				//设定颜色---柱状图
				for (var i = 0; i < result.length; i++) {

					//分类
					categories.push(result[i].name);
					//比例
					var obj = {
						y : "",
						color : ""
					};
					obj.y = result[i].value;
					obj.color = colors[i];

					datacol.push(obj);
				}

				var chart;
				
				//柱状图
				$(document).ready( function() {
					chart = new Highcharts.Chart({
						chart : {
							renderTo : divplace,	//html页面中初始化画布
							type : 'column',
							inverted: false //调换x，y轴
						},
						title : {
							text : title,
							style:{
				            	fontSize: '18px',
					            fontWeight: 'bolder',
					            color: '#333'
				            }
						},
						subtitle : {
							text : ''
						},
						xAxis : {
							categories : categories,
							labels: { 
						    rotation: -45,
		                    align: 'right',
		                    style: {
		                    	color:'#333333',
		                        fontSize: '13px',
		                        //fontWeight : 'bold'
		                       fontFamily: '微软雅黑'
		                    }
		                }
						},
						yAxis : {
							opposite: false,//标尺上下位置
							title : {
								//text : '百分比/%'
								text : ''
							}
						},
						plotOptions : {
							column : {
								cursor : 'pointer',
								point : {},
								dataLabels : {
									enabled : true,
									color : colors[0],
									style : {
										fontWeight : 'bold'
									},
									formatter : function() {
										return this.y + '%';
									}
								}
							}
						},
						tooltip : {
							formatter : function() {
								var point = this.point, s = this.x + ':<b>' + this.y + '%</b><br/>';
								if (point.drilldown) {
									s += 'Click to view ' + point.category + ' versions';
								}
								return s;
							}
						},
						series : [{
							name : name,
							data : datacol,
							color : 'white'
						}],
						exporting : {
							enabled : false
						}
					});
				});
			},
		 error:function() {
	         alert("操作失败！！");
	     }
	});
}
	
	var initevent = function() { //初始化页面
		
		
		var arr_01 = ["134","classroom_form_colum","学校多媒体教室形态","column"];
		var arr_02 = ["141","teacher_resource_colum","学校建设并应用的数字化教学资源","column"];
		var arr_03 = ["142","teacher_system_colum","学校应用的数字化教学系统","column"];
		var arr_04 = ["146","teacher_research_column","学校建设并应用的数字化教研资源","column"];
		var arr_05 = ["147","school_research_column","学校应用的数字化教研系统","column"];
		var arr_06 = ["148","info_system","学校应用的信息管理系统","column"];
		var arr_07 = ["149","all_system_colum"," 学校一卡通系统能够实现的功能","column"];
		var arr_08 = ["150","information_publish_colum","学校经常运用哪种手段进行信息发布与对外宣传","column"];
		var arr_09 = ["159","information_expenditure_colum","学校信息化经费主要来源","column"];
		var arr_10 = ["161","teacher_train_column","学校教室信息技术能力培训的渠道","column"];
		var arr_11 = ["162","teacher_content_column","学校教室信息技术培训的内容","column"];
		var arr_12 = ["163","security_assurance","学校已经实现的信息安全保障","column"];
		
		
		formNewArr_func_3(arr_01[0],arr_01[1],arr_01[2],arr_01[3]);
		formNewArr_func_3(arr_02[0],arr_02[1],arr_02[2],arr_02[3]);
		formNewArr_func_3(arr_03[0],arr_03[1],arr_03[2],arr_03[3]);
		formNewArr_func_3(arr_04[0],arr_04[1],arr_04[2],arr_04[3]);
		formNewArr_func_3(arr_05[0],arr_05[1],arr_05[2],arr_05[3]);
		formNewArr_func_3(arr_06[0],arr_06[1],arr_06[2],arr_06[3]);
		formNewArr_func_3(arr_07[0],arr_07[1],arr_07[2],arr_07[3]);
		formNewArr_func_3(arr_08[0],arr_08[1],arr_08[2],arr_08[3]);
		formNewArr_func_3(arr_09[0],arr_09[1],arr_09[2],arr_09[3]);
		formNewArr_func_3(arr_10[0],arr_10[1],arr_10[2],arr_10[3]);
		formNewArr_func_3(arr_11[0],arr_11[1],arr_11[2],arr_11[3]);
		formNewArr_func_3(arr_12[0],arr_12[1],arr_12[2],arr_12[3]);
	};
	
	//初始化饼状图
	initevent();		
};

init();		
