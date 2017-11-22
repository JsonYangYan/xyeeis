$(document).ready(function(){
	//加载时间
	var data_num = new Date();
	var data_month=data_num.getMonth() + 1;
	var data_year=data_num.getFullYear()
	var str =data_year + "-" + '0' +(data_month);
	console.log(data_month);
//	下载表格
	$("#drop_report").dialog({
		autoOpen: false,
		modal : true, 
		width :300,
		height : 165
	});
	$(".print_report").click(function () {
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
	
	$(".print_allreport").click(function () {
		$("#drop_allreport" ).dialog("open");
	})
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
		$("#drop2 li").eq(data_month_before-1).css("color",'black');
		$("#drop2 li").eq(data_month_before-1).css("cursor",'pointer');
		$("#drop1 li").eq(data_month_before-1).click(function(){
			console.log($(this).text())
			location.href ="/xyeeis/servlet/QxPrintPdfServlet?date="+$(this).text();
			console.log("/xyeeis/servlet/QxPrintPdfServlet?date="+$(this).text())
			$("#drop_allreport" ).dialog("close");
			})
		$("#drop2 li").eq(data_month_before-1).click(function(){
			console.log($(this).text())
			location.href ="/xyeeis/servlet/ExportAreanExcelServlet?date="+$(this).text();
			console.log("/xyeeis/servlet/ExportAreanExcelServlet?date="+$(this).text())
			$("#drop_report" ).dialog("close");
			})	
		
		}
	
	
});	