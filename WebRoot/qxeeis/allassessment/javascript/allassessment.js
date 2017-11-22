function show_time_data() {
	init();
}
//第一部分一个雷达图
var fPart = function() {
	var temp_index = new Array();
	var temp_name = new Array();
	var temp_result = new Array();
    //这部分保留,主要是涉及到标准的使用
	$.getJSON("../json/index_arr.json",function(data) {
		for(var i=0; i<data.length; i++) {
			temp_index.push([data[i].index]);
		}
	});	
		
	$.ajax({
		 url: "/xyeeis/servlet/QxOverallCompareServlet",
		 type: "POST",
		 data:{
			 "type": "firstPartPie",
			 "currentdate":$(".total_title").find(".total_active").html()
		 },
		 success:function(bindData) {
			// alert(bindData);
			 var data = JSON.parse(bindData);
			 //console.log(data);
			 //alert(data[2].name);
			 //$(".header").html(data[2].name + "教育信息化发展水平评估系统");
			 title = data[2].name + "与襄阳市和湖北省平均水平对比情况";
			 buildNettable("#left_side",title,temp_index,data);
			 
		 },
	     error:function() {
	         alert("操作失败！！");
	     }
	});	
};

//一级指标表格显示
var firsIndex = function() {
	$.ajax({
		 url: "/xyeeis/servlet/QxOverallCompareServlet",
		 type: "POST",
		 data:{
			 "type": "firstType",
			 "currentdate":$(".total_title").find(".total_active").html()
		 },
		 success:function(data) {
			 //alert(data);
			 var data = JSON.parse(data);
			 $html = "";
			 $html +="<table>";
			 for(var i=0;i<data.length;i++){
				$html +="<tr>";
					$html +="<td width=50%>" + data[i].type +"</td>" +"<td width=50%>" + data[i].value +"</td>";
				$html +="</tr>";	
			 }
			 $html +="</table>";
			 $("#firstIndex").html($html);
		 },
	     error:function() {
	         alert("操作失败！！");
	     }
	});	
}

//二级指标表格显示
var secondIndex = function() {
	$.ajax({
		 url: "/xyeeis/servlet/QxOverallCompareServlet",
		 type: "POST",
		 data:{
			 "type": "secondType",
			 "currentdate":$(".total_title").find(".total_active").html()
		 },
		 success:function(data) {
			 //alert(data);
			 var data = JSON.parse(data);
			 $html = "";
			 $html +="<table>";
			 for(var i=0;i<data.length;i++){
				$html +="<tr>";
					$html +="<td width=50%>" + data[i].type +"</td>" +"<td width=50%>" + data[i].value +"</td>";
				$html +="</tr>";	
			 }
			 $html +="</table>";
			 $("#secondIndex").html($html);
		 },
	     error:function() {
	         alert("操作失败！！");
	     }
	});	
}

//三级指标表格显示
var thirdIndex = function() {
	$.ajax({
		 url: "/xyeeis/servlet/QxOverallCompareServlet",
		 type: "POST",
		 data:{
			 "type": "thirdtType",
			 "currentdate":$(".total_title").find(".total_active").html()
		 },
		 success:function(data) {
			 //alert(data);
			 var data = JSON.parse(data);
			 $html = "";
			 $html +="<table>";
			 for(var i=0;i<data.length;i++){
				$html +="<tr>";
					$html +="<td width=50%>" + data[i].type +"</td>" +"<td width=50%>" + data[i].value +"</td>";
				$html +="</tr>";	
			 }
			 $html +="</table>";
			 $("#thirdIndex").html($html);
		 },
	     error:function() {
	         alert("操作失败！！");
	     }
	});	
}

var init = function() {
	fPart();
	firsIndex();
	secondIndex();
	thirdIndex();
};

init();