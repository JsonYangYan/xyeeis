$(document).ready(function(){	
	$("#laohe").on("mouseover",function () {
		$(".laohekou").css("display","none");
		$('div.select-main').css("display","block");
	})
	$("#gucheng").on("mouseover",function () {
		$(".guchengxian").css("display","none");
		$('div.select-main1').css("display","block");
	})
	//加载时间
	var data_num = new Date();
	var data_month=data_num.getMonth() + 1;
	var data_year=data_num.getFullYear()
	var str =data_year + "-" + '0' +(data_month);
	$("#drop1_1").text(str);
	$("#drop2_1").text(str);
	//前一个月
	var data_before1=data_month-1;
	if (data_before1==0) {
		var data_before1=12;
		var str_befor1 =data_year-1 + "-" +(data_before1);
	} else{
		var str_befor1 =data_year + "-" + '0' +(data_before1);
	}
	$("#drop1_2").text(str_befor1);
	$("#drop2_2").text(str_befor1);
//点击事件
	$(".ui-select").selectWidget({
	change: function (changes) {
		return changes;
	},
	effect       : "slide",
	keyControl   : true,
	speed        : 200,
	scrollHeight : 250
	});
	$(".ui-select1").selectWidget1({
	change: function (changes) {
		return changes;
	},
	effect       : "slide",
	keyControl   : true,
	speed        : 200,
	scrollHeight : 250
	});
	$(".select-items").click(function () {
//		alert('1'+$(this).index())
		var index=$(this).index();
		if (index==0) {
			return;
		} else{
			var date=$(this).text();
			console.log(date);
			console.log("/xyeeis/servlet/DemoPdfServlet?areaName=老河口市&date="+date)
		}
		
	})
	$(".select-items1").click(function () {
		var index1=$(this).index();
		var date1=$(this).text();
		console.log("/xyeeis/servlet/DemoPdfServlet?areaName=谷城县&date="+date1)
	})
});	