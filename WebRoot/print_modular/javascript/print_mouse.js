$(document).ready(function(){
	//加载时间
	var data_num = new Date();
	var data_month=data_num.getMonth() + 1;
	var data_year=data_num.getFullYear()
	var str =data_year + "-" + '0' +(data_month);
	console.log(data_month);
//	console.log($("#drop1 li:lt(data_month)"))
//	下载表格
	$("#drop_report").dialog({
		autoOpen: false,
		modal : true, 
		width :300,
		height : 165
	});
	$("#print_report").click(function () {
		$("#drop_report" ).dialog("open");
	});
	//加载模态框
	//下载文档
	$("#drop_allreport" ).dialog({
		autoOpen: false,
		modal : true, 
		width :300,
		height : 165
	});
	$("#print_allreport").click(function () {
		$("#drop_allreport" ).dialog("open");
	})
//	老河口
	$("#drop_laohe" ).dialog({
		autoOpen: false,
		modal : true, 
		width :300,
		height : 165
	});
	$("#laohe").click(function () {
		$("#drop_laohe" ).dialog("open");
	})
//	谷城县
	$("#drop_gucheng" ).dialog({
		autoOpen: false,
		modal : true, 
		width :300,
		height : 165
	});
	$("#gucheng").click(function () {
		$("#drop_gucheng" ).dialog("open");
	})
//	保康
	$("#drop_baokang" ).dialog({
		autoOpen: false,
		modal : true, 
		width :300,
		height : 165
	});
	$("#baokang").click(function () {
		$("#drop_baokang" ).dialog("open");
	})
	//	襄州
	$("#drop_fanzhou" ).dialog({
		autoOpen: false,
		modal : true, 
		width :300,
		height : 165
	});
	$("#fanzhou").click(function () {
		$("#drop_fanzhou" ).dialog("open");
	})
	//	樊城
	$("#drop_fancheng" ).dialog({
		autoOpen: false,
		modal : true, 
		width :300,
		height : 165
	});
	$("#fancheng").click(function () {
		$("#drop_fancheng" ).dialog("open");
	})
		//	宜城区
	$("#drop_yicheng" ).dialog({
		autoOpen: false,
		modal : true, 
		width :300,
		height : 165
	});
	$("#yicheng").click(function () {
		$("#drop_yicheng" ).dialog("open");
	})
		//	襄城区
	$("#drop_xiangcheng").dialog({
		autoOpen: false,
		modal : true, 
		width :300,
		height : 165
	});
	$("#xiangcheng").click(function () {
		$("#drop_xiangcheng" ).dialog("open");
	})
	//	南漳
	$("#drop_nanzhang").dialog({
		autoOpen: false,
		modal : true, 
		width :300,
		height : 165
	});
	$("#nanzhang").click(function () {
		$("#drop_nanzhang" ).dialog("open");
	})
	//	枣阳
	$("#drop_zaoyang").dialog({
		autoOpen: false,
		modal : true, 
		width :300,
		height : 165
	});
	$("#zaoyang").click(function () {
		$("#drop_zaoyang" ).dialog("open");
	})
	//	高新区
	$("#drop_gaoxin").dialog({
		autoOpen: false,
		modal : true, 
		width :300,
		height : 165
	});
	$("#gaoxin").click(function () {
		$("#drop_gaoxin" ).dialog("open");
	})
	//鱼梁洲
	$("#drop_yuliang").dialog({
		autoOpen: false,
		modal : true, 
		width :300,
		height : 165
	});
	$("#yuliang").click(function () {
		$("#drop_yuliang" ).dialog("open");
	})
	//东津新区
	$("#drop_dongjing").dialog({
		autoOpen: false,
		modal : true, 
		width :300,
		height : 165
	});
	$("#dongjing").click(function () {
		$("#drop_dongjing" ).dialog("open");
	})
	
	
//	下载
	for(var i=0;i<12;i++){
		var data_month_before=data_month--;
		if(data_month_before==0){
			return;
		}
		if(data_month_before =='1' || data_month_before =='2' || data_month_before =='3') {
			return;
		}
		console.log(data_month_before)
		$("#drop1 li").eq(data_month_before-1).css("color",'black');
		$("#drop1 li").eq(data_month_before-1).css("cursor",'pointer');
		$("#drop1 li").eq(data_month_before-1).click(function(){
			console.log($(this).text())
			location.href ="/xyeeis/servlet/DemoPdfServlet?areaName=襄阳市&date="+$(this).text();
			console.log("/xyeeis/servlet/DemoPdfServlet?areaName=襄阳市&date="+$(this).text())
			$("#drop_allreport" ).dialog("close");
			})
		$("#drop2 li").eq(data_month_before-1).css("color",'black');
		$("#drop2 li").eq(data_month_before-1).css("cursor",'pointer');
		$("#drop2 li").eq(data_month_before-1).click(function(){
			console.log($(this).text())
			location.href ="/xyeeis/servlet/ExportCityExcelServlet?date="+$(this).text();
			console.log("/xyeeis/servlet/ExportCityExcelServlet?date="+$(this).text())
			$("#drop_report" ).dialog("close");
			})	
//		老河口
		$("#drop3 li").eq(data_month_before-1).css("color",'black');
		$("#drop3 li").eq(data_month_before-1).css("cursor",'pointer');
		$("#drop3 li").eq(data_month_before-1).click(function(){
			console.log($(this).text())
			location.href ="/xyeeis/servlet/DemoPdfServlet?areaName=老河口市&date="+$(this).text();
			console.log("/xyeeis/servlet/DemoPdfServlet?areaName=老河口市&date="+$(this).text())
			$("#drop_laohe" ).dialog("close");
			})
	
	//谷城
		$("#drop4 li").eq(data_month_before-1).css("color",'black');
		$("#drop4 li").eq(data_month_before-1).css("cursor",'pointer');
		$("#drop4 li").eq(data_month_before-1).click(function(){
			console.log($(this).text())
			location.href ="/xyeeis/servlet/DemoPdfServlet?areaName=谷城县&date="+$(this).text();
			console.log("/xyeeis/servlet/DemoPdfServlet?areaName=谷城县&date="+$(this).text())
			$("#drop_gucheng" ).dialog("close");
			})
//	保康
		$("#drop5 li").eq(data_month_before-1).css("color",'black');
		$("#drop5 li").eq(data_month_before-1).css("cursor",'pointer');
		$("#drop5 li").eq(data_month_before-1).click(function(){
			console.log($(this).text())
			location.href ="/xyeeis/servlet/DemoPdfServlet?areaName=保康县&date="+$(this).text();
			console.log("/xyeeis/servlet/DemoPdfServlet?areaName=保康县&date="+$(this).text())
			$("#drop_baokang" ).dialog("close");
			})
	//	襄州
		$("#drop6 li").eq(data_month_before-1).css("color",'black');
		$("#drop6 li").eq(data_month_before-1).css("cursor",'pointer');
		$("#drop6 li").eq(data_month_before-1).click(function(){
			console.log($(this).text())
			location.href ="/xyeeis/servlet/DemoPdfServlet?areaName=襄州区&date="+$(this).text();
			console.log("/xyeeis/servlet/DemoPdfServlet?areaName=襄州区&date="+$(this).text())
			$("#drop_fanzhou" ).dialog("close");
			})
		//	樊城
		$("#drop7 li").eq(data_month_before-1).css("color",'black');
		$("#drop7 li").eq(data_month_before-1).css("cursor",'pointer');
		$("#drop7 li").eq(data_month_before-1).click(function(){
			console.log($(this).text())
			location.href ="/xyeeis/servlet/DemoPdfServlet?areaName=樊城区&date="+$(this).text();
			console.log("/xyeeis/servlet/DemoPdfServlet?areaName=樊城区&date="+$(this).text())
			$("#drop_fancheng" ).dialog("close");
			})	
		//	宜城区
		$("#drop8 li").eq(data_month_before-1).css("color",'black');
		$("#drop8 li").eq(data_month_before-1).css("cursor",'pointer');
		$("#drop8 li").eq(data_month_before-1).click(function(){
			console.log($(this).text())
			location.href ="/xyeeis/servlet/DemoPdfServlet?areaName=宜城市&date="+$(this).text();
			console.log("/xyeeis/servlet/DemoPdfServlet?areaName=宜城市&date="+$(this).text())
			$("#drop_yicheng" ).dialog("close");
			})	
//			宜城区
		$("#drop9 li").eq(data_month_before-1).css("color",'black');
		$("#drop9 li").eq(data_month_before-1).css("cursor",'pointer');
		$("#drop9 li").eq(data_month_before-1).click(function(){
			console.log($(this).text())
			location.href ="/xyeeis/servlet/DemoPdfServlet?areaName=襄城区&date="+$(this).text();
			console.log("/xyeeis/servlet/DemoPdfServlet?areaName=襄城区&date="+$(this).text())
			$("#drop_xiangcheng" ).dialog("close");
			})	
		//南漳
		$("#drop10 li").eq(data_month_before-1).css("color",'black');
		$("#drop10 li").eq(data_month_before-1).css("cursor",'pointer');
		$("#drop10 li").eq(data_month_before-1).click(function(){
			console.log($(this).text())
			location.href ="/xyeeis/servlet/DemoPdfServlet?areaName=南漳县&date="+$(this).text();
			console.log("/xyeeis/servlet/DemoPdfServlet?areaName=南漳县&date="+$(this).text())
			$("#drop_nanzhang" ).dialog("close");
			})
		//枣阳
		$("#drop11 li").eq(data_month_before-1).css("color",'black');
		$("#drop11 li").eq(data_month_before-1).css("cursor",'pointer');
		$("#drop11 li").eq(data_month_before-1).click(function(){
			console.log($(this).text())
			location.href ="/xyeeis/servlet/DemoPdfServlet?areaName=枣阳市&date="+$(this).text();
			console.log("/xyeeis/servlet/DemoPdfServlet?areaName=枣阳市&date="+$(this).text())
			$("#drop_zaoyang" ).dialog("close");
			})	
		//高新区
		$("#drop12 li").eq(data_month_before-1).css("color",'black');
		$("#drop12 li").eq(data_month_before-1).css("cursor",'pointer');
		$("#drop12 li").eq(data_month_before-1).click(function(){
			console.log($(this).text())
			location.href ="/xyeeis/servlet/DemoPdfServlet?areaName=高新区&date="+$(this).text();
			console.log("/xyeeis/servlet/DemoPdfServlet?areaName=高新区&date="+$(this).text())
			$("#drop_gaoxin" ).dialog("close");
			})	
			
		//鱼梁洲
		$("#drop13 li").eq(data_month_before-1).css("color",'black');
		$("#drop13 li").eq(data_month_before-1).css("cursor",'pointer');
		$("#drop13 li").eq(data_month_before-1).click(function(){
			console.log($(this).text())
			location.href ="/xyeeis/servlet/DemoPdfServlet?areaName=鱼梁洲&date="+$(this).text();
			console.log("/xyeeis/servlet/DemoPdfServlet?areaName=鱼梁洲&date="+$(this).text())
			$("#drop_yuliang" ).dialog("close");
			})	
		//东津新区
		$("#drop14 li").eq(data_month_before-1).css("color",'black');
		$("#drop14 li").eq(data_month_before-1).css("cursor",'pointer');
		$("#drop14 li").eq(data_month_before-1).click(function(){
			console.log($(this).text())
			location.href ="/xyeeis/servlet/DemoPdfServlet?areaName=东津新区&date="+$(this).text();
			console.log("/xyeeis/servlet/DemoPdfServlet?areaName=东津新区&date="+$(this).text())
			$("#drop_dongjing" ).dialog("close");
			})					
	
	}	
});	