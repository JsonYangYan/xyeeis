function show_time_data() {
	fPart();
	allassment_init();
	statisticInit();
}
//第一部分两个雷达图
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
		 url: "/xyeeis/servlet/OverallCompareServlet",
		 type: "POST",
		 data:{
			 "type": "firstPartPie",
			 "currentdate":$(".total_title").find(".total_active").html()
		 },
		 success:function(bindData) {
			 var data = JSON.parse(bindData);
			 buildNettable("#left_side","襄阳市与湖北省和全国平均水平对比情况",temp_index,data);
		 },
	     error:function() {
	         alert("操作失败！！");
	     }
	});
	
	$.ajax({
		 url: "/xyeeis/servlet/OverallCompareServlet",
		 type: "POST",
		 data:{
			 "type": "secondPartPie",
			 "currentdate":$(".total_title").find(".total_active").html()
		 },
		 success:function(bindData) {
			 var data = JSON.parse(bindData);
			 buildNettable("#right_side","襄阳市各地区对比情况",temp_index,data);
		 },
	     error:function() {
	         alert("操作失败！！");
	     }
	});
};

var init = function() {
	fPart();
};

init();
