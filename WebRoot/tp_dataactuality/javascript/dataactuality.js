var start_time = "2017-3";//开始时间 不要加0，如2016-2
var now = new Date();
var arr = start_time.split('-');
var start_year = arr[0];//开始年份
var start_month = arr[1];//开始月份
var month = now.getMonth()+1;//现在的月份
var year = now.getFullYear();//现在的年份
var count = ( year-start_year ) * 12 + month - start_month +1;//循环的次数
var data = [];
for( i=0; i<count; i++) {
	
	if (start_month < 10) {
  		start_month = "0" + start_month;
	}
	data[i] = start_year + "-" +start_month;
	if(start_month == 12) {
		start_year++;
		start_month =0;
	}
	start_month++;
}
for(var i=0; i<data.length;i++) {
	$(".total_title").append("<span class='total_span total_noraml'>"+data[i]+"</span>");
}

$(".total_title span:last-child").removeClass("total_noraml").addClass("total_active");
$(".total_span").click(function(){
	$(".total_span").removeClass("total_active").addClass("total_noraml");
	$(this).removeClass("total_normal").addClass("total_active");
	init();
});
var showData = function(){
	//平均分
	$.ajax({
			url: "../../servlet/TpBlankAvgServlet",
			type: "post",
			dataType:"text",
			data: {
				"currentdate":$(".total_title").find(".total_active").html()
			},
			success: function(json) {
				var json = JSON.parse(json);
				var arr = new Array(13);
				for(var i=0;i<json.length;i++){
					var valu = json[i].value;
					//console.log(valu);
					arr[i] = new Array(12);
					for(var j=0;j<12;j++){
						
						if(valu[j] == "undefined" || valu[j] == ''||valu[j] == null){
							arr[i][j] = 0;
							
						}else{
							arr[i][j] = valu[j];
						}
						console.log(arr[i][j]);
					}
					
				}
				console.log(arr);
				var norm=['教学点联网的实际运行网速平均能达到','教学点已经拥有的计算机数量是','平板电脑数量','笔记本电脑数量','供教师使用的计算机数量是','供学生学习的计算机数量是','应用数字化资源的课程比例为','教学点应用信息技术开展教学的教师比例','学生平均每周在学校使用信息技术进行学习的次数','教学点本月的信息化投入经费为','本月参与信息技术能力培训的教师人数','教师人均参加信息技术能力培训的课时数'];
				$html = "";
				for(var i=0;i<12;i++){
					if((i+1)%2 ==0){
						$html += "<ul style='background-color: #DCE9F9;'> <li style='width:186px;'>"+ norm[i]+"</li>";
					}else{
						$html += "<ul> <li style='width:186px;'>"+ norm[i]+"</li>";
					}
					
					for(var j=0;j<13;j++){
						$html += "<li style='line-height:60px'>" + arr[j][i] +"</li>";
					}
					$html +="</ul>";
				}
				//console.log($html);
				$(".data_now").html($html);
				
			},
			error: function() {
				alert("失败");
			}
		});
}

//多选题柱状图
var formNewArr_func_3 = function(id,divplace,title,type) {
	 $.ajax({
		 url: "/xyeeis/servlet/TpChoicePectServlet",
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
	
//初始化函数
var init = function() {
	
	showData();
	var initevent = function() { //初始化页面
		
		
		var arr_01 = ["12","classroom_form_colum","教学点建设或应用的数字化教学资源有哪些","column"];
		var arr_02 = ["14","teacher_resource_colum","应用数字化教学资源的课程有哪些","column"];
		var arr_03 = ["15","teacher_system_colum","开设的课程中应用信息技术的课程有哪些","column"];
		var arr_04 = ["16","teacher_research_column","教学点应用“在线课堂”进行教学的课程有哪些","column"];
		var arr_05 = ["18","school_research_column","教学点应用的数字化教学系统有哪些","column"];
		var arr_06 = ["19","info_system","应用数字化教学系统进行教学的课程有哪些","column"];
		var arr_07 = ["33","all_system_colum"," 教学点信息化经费主要来源有哪些","column"];
		var arr_08 = ["34","information_publish_colum","教学点教师信息技术能力培训的渠道有哪些","column"];
		var arr_09 = ["35","information_expenditure_colum","教学点教师信息技术培训的内容有哪些","column"];
		
		
		formNewArr_func_3(arr_01[0],arr_01[1],arr_01[2],arr_01[3]);
		formNewArr_func_3(arr_02[0],arr_02[1],arr_02[2],arr_02[3]);
		formNewArr_func_3(arr_03[0],arr_03[1],arr_03[2],arr_03[3]);
		formNewArr_func_3(arr_04[0],arr_04[1],arr_04[2],arr_04[3]);
		formNewArr_func_3(arr_05[0],arr_05[1],arr_05[2],arr_05[3]);
		formNewArr_func_3(arr_06[0],arr_06[1],arr_06[2],arr_06[3]);
		formNewArr_func_3(arr_07[0],arr_07[1],arr_07[2],arr_07[3]);
		formNewArr_func_3(arr_08[0],arr_08[1],arr_08[2],arr_08[3]);
		formNewArr_func_3(arr_09[0],arr_09[1],arr_09[2],arr_09[3]);
	};
	
	//初始化饼状图
	initevent();		
};

init();		

$(".dataactuality").click(function(){
	window.location.href="../../teaching_point/html/tp_statistics.html";
});
