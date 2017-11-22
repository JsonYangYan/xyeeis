var sum = 0;//存详细规则的条数
var rank2_sum = 0;//二级指标条数
var jsonSurvey = {"list":[]};//存后台获取的数据

/*
 * 隔行换色
 */
var bgColor = function() {
	$("td.column_tb table.rank2_tb:even").css("background-color","#b3cff4");//隔行换色
	$("td.answer tr").height(function() {
		var tmp = $(this).index();
		var jqobj = $(this).parents("td").prev().find("li:eq("+tmp+")");
		return jqobj.innerHeight();
	});
	$("td.assessment").find("li:odd").css("background-color","#c9defb");//隔行换色
	$("td.weight_3").find("tr:odd").css("background-color","#c9defb");
};

/*
 * 加载内容
 */
var loadList = function() {
	$.ajax({
		url:"../json/standard.json",
		success:function(data) {
			jsonSurvey.list = data;
			$("#assetment_box").html(TrimPath.processDOMTemplate("assetment_template",jsonSurvey));
			sum = jsonSurvey.list.sum;
			rank2_sum = jsonSurvey.list.rank2_sum;
			$(document).ready(function() {
				bgColor();
			});
		},
		error:function() {
			alert("error");
		}
	});
};

/*
 * 初始化函数
 */
var init = function() {
	loadList();
};

init();