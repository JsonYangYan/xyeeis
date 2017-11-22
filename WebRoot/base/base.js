
/*加载网站头尾和左侧导航*/
var start_init = function() {
	
	$("#floatNavEducation").load("../../base/html/floatNavEducation.html");
	$("#floatNavAllassessment").load("../../base/html/floatNavAllassessment.html");
	$("#floatNavStatusAnalysis").load("../../base/html/floatNavStatusAnalysis.html");
	$("#floatNavStatistics").load("../../base/html/floatNavStatistics.html");
	/*$("#floatNavTpStatistcs").load("../../base/html/floatNavTpStatistics.html");*/
	$("#floatNavStandard").load("../../base/html/floatNavStandard.html");
	$("#floatNavDataactuality").load("../../base/html/floatNavDataactuality.html");
	$("#floatNavSchool").load("../../base/html/floatNavSchool.html");
	$("#floatNavRanking").load("../../base/html/floatNavRanking.html");
	$("#floatNavPrintPDF").load("../../base/html/floatNavPrintPDF.html");
	$("#floatNavMain").load("../../base/html/floatNavMain.html");
	$("#floatNavSchRanking").load("../../base/html/floatNavSchRanking.html");
	$("#footer").load("../../base/html/footer.html");
};

start_init();

/**
 * 获取上一个月
 *
 * @date 格式为yyyy-mm的日期，如：2014-01
 */
function getPreMonth(date) {
    var arr = date.split('-');
    var year = arr[0]; //获取当前日期的年份
    var month = arr[1]; //获取当前日期的月份
    var days = new Date(year, month, 0);
    days = days.getDate(); //获取当前日期中月的天数
    var year2 = year;
    var month2 = parseInt(month) - 1;
    if (month2 == 0) {//如果是1月份，则取上一年的12月份
        year2 = parseInt(year2) - 1;
        month2 = 12;
    }
    if (month2 < 10) {
        month2 = '0' + month2;//月份填补成2位。
    }
    var t2 = year2 + '-' + month2;
    return t2;
}


//月份显示
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
		if(start_month == 7 || start_month == 8) {
			continue;
		}
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
	show_time_data();
});
