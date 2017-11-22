$(".print_allreport").click(function() {
	var date = $(this).text();
			location.href = "/xyeeis/servlet/QxPrintPdfServlet";
		});
$(".print_report").click(function() {
	var date = $(this).text();
			location.href = "/xyeeis/servlet/ExportAreanExcelServlet";
		});

























