var formNewArr_func_3 = function(id,divplace,title,type) {
	 $.ajax({
		 url: "/xyeeis/servlet/ChoicePercentServlet",
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
var formNewArr_func_1 = function(jsonname,divplace,title,type) { 
	 $.ajax({
		 url: "/xyeeis/servlet/TerminalPercentAndScoreServlet",
		 type: "POST",
		 data:{
			 "teacher":jsonname,
			 "currentdate":$(".total_title").find(".total_active").html()
		 },
		 success:function(bindData) {
			 var data = JSON.parse(bindData)
			var temp_array =  new Array();	
			 if( type == "pie" ){
					for(var i=0;i<data.length;i++){
						temp_array.push([data[i].name,data[i].value]);   //将JASON格式的文本存进去
					}
					    build_pieTable(divplace,title,temp_array);  //生成饼状图   将JASON格式的文本
				}
				else{
					   var temp_data = new Array();
					   var temp_area = new Array();
					   //var temp_avg = new Array();
					   for (var i = 0; i < data.length; i++) {
						   temp_area.push(data[i].name);
						   temp_data.push(data[i].value);
						   //temp_avg.push(data[i].avg);
						   average = data[0].avg;
					   }			   
					   getBlankChart(divplace, '',"终端使用量",temp_area,temp_data,'%');   //生成执折线图	
						
						if(jsonname == "teacher"){
							$("#teacher_terminal").html( title + '&nbsp;&nbsp;师机比：'+ '<span style="color:red;">'+average +'</span>' );	
							$("#teacher_terminal").css("display", "block");
						}else{
							$("#student_terminal").html( title + '&nbsp;&nbsp;生机比：'+ '<span style="color:red;">'+average +'</span>' );	
							$("#student_terminal").css("display", "block");
						}				
					}
			},
		 error:function() {
	         alert("操作失败！！");
	     }
	});
	
};
	

	
var formNewArr_func_2 = function(jsonname,divplace,title,type) {
	var temp_array =  new Array();
	$.getJSON("../json/"+jsonname,function(data) {
		if( type == "pie" ){
			for(var i=0;i<data.length;i++){
				temp_array.push([data[i].name,data[i].value]);   //将JASON格式的文本存进去
			}
			    build_pieTable(divplace,title,temp_array);  //生成饼状图   将JASON格式的文本
		}
		else{
			   var temp_data = new Array();
			   var temp_area = new Array();
			   // var average
			   for (var i = 0; i < data.length; i++) {
				   temp_area.push(data[i].name);
				   temp_data.push(data[i].value);
				   // average = data[0].avg;
			   }			   
			   getBlankCrossChart(divplace, title,"",temp_area,temp_data,'%');   //生成柱状图
												
			}
	});	
}
	
	
	
	
//初始化函数
var allassment_init = function() {
	var initevent = function() { //初始化页面
		
		var arr_01 = ["teacher","#teacher_terminal_pie","教学用信息化终端设备中,教师专用终端","pie"];
		var arr_02 = ["teacher","#teacher_terminal_colum","教学用信息化终端设备中,教师专用终端","column"];		
		var arr_03 = ["student","#student_terminal_pie","教学用信息化终端设备中,学生专用终端","pie"];
		var arr_04 = ["student","#student_terminal_colum","教学用信息化终端设备中,学生专用终端","column"];
		
		var arr_05 = ["141","teacher_resources_colum","学校建设并应用的数字化教学资源","column"];
		var arr_06 = ["142","teacher_practice_colum","学校应用的数字化教学系统","column"];
		var arr_07 = ["146","school_management_system","学校建设并应用的数字化教研资源","column"];
		var arr_08 = ["147","teacher_information_training","学校应用的数字化教研系统","column"];
		var arr_09 = ["134","Multimedia_class_form","学校多媒体教室形态","column"];
		var arr_10 = ["148","manager_system","学校应用信息管理系统","column"];
		
		formNewArr_func_1(arr_01[0],arr_01[1],arr_01[2],arr_01[3]);   //获取饼状图所需数据，并进行数据重组
		formNewArr_func_1(arr_02[0],arr_02[1],arr_02[2],arr_02[3]);
		formNewArr_func_1(arr_03[0],arr_03[1],arr_03[2],arr_03[3]);   //获取饼状图所需数据，并进行数据重组
		formNewArr_func_1(arr_04[0],arr_04[1],arr_04[2],arr_04[3]);   //获取所需数据，并进行数据重组
		
		
		formNewArr_func_3(arr_05[0],arr_05[1],arr_05[2],arr_05[3]);
		formNewArr_func_3(arr_06[0],arr_06[1],arr_06[2],arr_06[3]);
		formNewArr_func_3(arr_07[0],arr_07[1],arr_07[2],arr_07[3]);
		formNewArr_func_3(arr_08[0],arr_08[1],arr_08[2],arr_08[3]);
		formNewArr_func_3(arr_09[0],arr_09[1],arr_09[2],arr_09[3]);
		formNewArr_func_3(arr_10[0],arr_10[1],arr_10[2],arr_10[3]);
	};
	
	//初始化饼状图
	initevent();		
};

allassment_init();		