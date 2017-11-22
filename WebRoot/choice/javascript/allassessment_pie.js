var formNewArr_func_3 = function(id,divplace,title,type) {
	 $.ajax({
		 url: "/xyeeis/servlet/ChoiceServlet",
		 type: "POST",
		 data:{
			 "id":id,
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
var init = function() {
	var initevent = function() { //初始化页面
		var arr_01 = ["141","teacher_resources_colum","学校建设并应用的数字化教学资源","column"];
		var arr_02 = ["142","teacher_practice_colum","学校应用的数字化教学系统","column"];
		var arr_03 = ["146","school_research_system","学校建设并应用的数字化教研资源","column"];
		var arr_04 = ["147","teacher_information_training","学校应用的数字化教研系统","column"];
		var arr_05 = ["134","Multimedia_class_form","学校多媒体教室形态","column"];
		var arr_06 = ["148","manager_system","学校应用信息管理系统","column"];
		var arr_07 = ["149","schoolcard_func","学校一卡通功能","column"];
		var arr_08 = ["150","release_information","学校发布信息与对外宣传手段","column"];
		var arr_09 = ["152","schoolweb","学校是否有网站","column"];
		var arr_10 = ["153","schoolweb_statics","网站是否统计访问量","column"];
		var arr_11 = ["154","information_uplode","核心信息是否上传","column"];
		var arr_12 = ["155","schoolweb_visit","校外网是否可以访问学校网站","column"];
		var arr_13 = ["156","schoolweb_language","学校网站支持的语言","column"];
		var arr_14 = ["157","schoolweb_equipment_show","学校门户网站可在哪些设备上自适应呈现","column"];
		var arr_15 = ["159","funds_source","学校信息化经费主要来源","column"];
		var arr_16 = ["160","leader_level","学校信息化领导级别","column"];
		var arr_17 = ["161","training_source","学校教师信息技术能力培训渠道","column"];
		var arr_18 = ["162","training_content","学校教师信息技术培训内容","column"];
		var arr_19 = ["163","information_security_guarantee","学校应经实现的信息安全保障","column"];
		var arr_20 = ["164","information_development_plan","学校信息化发展规划制定情况","column"];
		var arr_21 = ["165","information_development_form","学校信息化发展公布形式 ","column"];
		var arr_22 = ["166","data_backup","学校是否可以及时的做出数据备份与恢复","column"];
		
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
		formNewArr_func_3(arr_13[0],arr_13[1],arr_13[2],arr_13[3]);
		formNewArr_func_3(arr_14[0],arr_14[1],arr_14[2],arr_14[3]);
		formNewArr_func_3(arr_15[0],arr_15[1],arr_15[2],arr_15[3]);
		formNewArr_func_3(arr_16[0],arr_16[1],arr_16[2],arr_16[3]);
		formNewArr_func_3(arr_17[0],arr_17[1],arr_17[2],arr_17[3]);
		formNewArr_func_3(arr_18[0],arr_18[1],arr_18[2],arr_18[3]);
		formNewArr_func_3(arr_19[0],arr_19[1],arr_19[2],arr_19[3]);
		formNewArr_func_3(arr_20[0],arr_20[1],arr_20[2],arr_20[3]);
		formNewArr_func_3(arr_21[0],arr_21[1],arr_21[2],arr_21[3]);
		formNewArr_func_3(arr_22[0],arr_22[1],arr_22[2],arr_22[3]);
		
	};
	
	//初始化饼状图
	initevent();		
};

init();		