function show_time_data() {
	show_table_data("襄州区");
	$("#area_name").html("<h2>襄州区典型指标详情</h2>");
	drawTable("drawPieChart_01","襄州区",134,"学校多媒体教室的形态有哪些");
	drawTable("drawPieChart_02","襄州区",141,"学校建设并应用的数字化教学资源有哪些");
	drawTable("drawPieChart_03","襄州区",142,"学校应用的数字化教学系统有哪些");
	drawTable("drawPieChart_04","襄州区",146,"学校建设并应用的数字化教研资源有哪些");
}

var formNewArr_func_1 = function(jsonname,divplace,title,type) { 
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
			   for (var i = 0; i < data.length; i++) {
				   temp_area.push(data[i].name);
				   temp_data.push(data[i].value);
			   }
			   getBlankChart(divplace,title,"提交学校",temp_area,temp_data,'%');   //生成执折线图	
		}
	});
	
	$(this).next().fadeIn(500);
	$(this).next().fadeOut(500);
}
	
//初始化函数
var init = function() {
	
	var initevent = function() { //初始化页面
		var arr_01 = ["schoolInit.json","#pie_01","区域教育信息化发展典型指数","pie"];		
		var arr_02 = ["schoolInit.json","#pie_02","区域教育信息化发展典型指数","pie"];
		var arr_03 = ["schoolInit.json","#pie_03","区域教育信息化发展典型指数","pie"];
		var arr_04 = ["schoolInit.json","#pie_04","区域教育信息化发展典型指数","pie"];
	};
	
	//初始化饼状图
	initevent();
	
};
init();		


