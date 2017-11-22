$(".print_allreport").click(function() {
//	$(this).jqLoading();
	var date = $(this).text();
	location.href = "/xyeeis/servlet/DemoPdfServlet?areaName=襄阳市";
//	setTimeout(function () {
//		$(this).jqLoading("destroy");
//	},13000);
//	
//	var start_time = (new Date()).getTime();
//	console.log(start_time);
//	var end_time = "";
//	var t = setInterval(function() {
//		if(document.readyState == "complete") {
//			end();
//		}
//	},500);
//	function end() {
//		end_time = (new Date()).getTime();
////		clearInterval(t);
//		console.log(end_time);
//		console.log(end_time - start_time);
//	}
	
});
$(".print_report").click(function() {
//	$(this).jqLoading();
	var date = $(this).text();
	location.href = "/xyeeis/servlet/ExportCityExcelServlet";
//	setTimeout(function () {
//		$(this).jqLoading("destroy");
//	},13000);
});

$(".guchengxian").click(function(){
	
	location.href = "/xyeeis/servlet/DemoPdfServlet?areaName=谷城县";
});
$(".laohekou").click(function(){
	location.href = "/xyeeis/servlet/DemoPdfServlet?areaName=老河口市";
});

$(".baokangxian").click(function(){
	location.href = "/xyeeis/servlet/DemoPdfServlet?areaName=保康县";
});

$(".fanzhouqu").click(function(){
	location.href = "/xyeeis/servlet/DemoPdfServlet?areaName=襄州区";
});

$(".fanchengqu").click(function(){
	location.href = "/xyeeis/servlet/DemoPdfServlet?areaName=樊城区";
});

$(".yichengshi").click(function(){
	location.href = "/xyeeis/servlet/DemoPdfServlet?areaName=宜城市";
});

$(".xiangchengqu").click(function(){
	location.href = "/xyeeis/servlet/DemoPdfServlet?areaName=襄城区";
});

$(".nanzhangxian").click(function(){
	location.href = "/xyeeis/servlet/DemoPdfServlet?areaName=南漳县";
});

$(".zaoyangshi").click(function(){
	location.href = "/xyeeis/servlet/DemoPdfServlet?areaName=枣阳市";
});
$(".gaoxinqu").click(function(){
	location.href = "/xyeeis/servlet/DemoPdfServlet?areaName=高新区";
});
$(".yuliangzhou").click(function(){
	location.href = "/xyeeis/servlet/DemoPdfServlet?areaName=鱼梁洲";
});
$(".dongjinxinqu").click(function(){
	location.href = "/xyeeis/servlet/DemoPdfServlet?areaName=东津新区";
});

























