//月份
function show_time_data() {
	init();
	var str = $(".total_title").find(".total_active").html();
	var str_befor1 = getPreMonth(str);

	$("#now_month_button").text(str);
	$("#before_month_button").text(str_befor1);
	date_month(str);
}

/*
	 * 初始化
	 */	
		//分页
		//用于存储从后台获取的数据,所有数据
		var schoolJSON = {schoolData:[]};
		//当前页的数据
		var currentPageJSON = {};
		//当前处于第几页
		var currentPageNo = 1;
		//每页显示的条数
		var pageSize = 10;

/*
 * 表格显示数据函数
 */
var showtable = function(fig_date) {
	$.ajax({
		url: "../../../servlet/QxRankingServlet",
		type: "post",
		data: {
			"operation": "ranking",
			"fig":fig_date,
			"currentdate":$(".total_title").find(".total_active").html()
		},
		success: function(result) {	
			var result = JSON.parse(result);
			schoolJSON.schoolData=result;
			showData();
			setTimeout(function(){
				var str = result[0].area+"各学校发展水平排名";
				$("#tdtitle").html(str);
			},1000);
        
		
		},
		error: function() {
			/*alert("失败");*/
		}
	});
	

};

/*
 * 单击后，highchats图相应变化
 */
var changeHisogram = function(id) {
	if(id == '0'){
		$("#0").css("background","#ffc300");
		$("#1").css("background","#50bfea");
		$("#2").css("background","#50bfea");
		$("#3").css("background","#50bfea");
		$("#4").css("background","#50bfea");
	}
	if(id == '1'){
		$("#0").css("background","#50bfea");
		$("#1").css("background","#ffc300");
		$("#2").css("background","#50bfea");
		$("#3").css("background","#50bfea");
		$("#4").css("background","#50bfea");
	}
	if(id == '2'){
		$("#0").css("background","#50bfea");
		$("#1").css("background","#50bfea");
		$("#2").css("background","#ffc300");
		$("#3").css("background","#50bfea");
		$("#4").css("background","#50bfea");
	}
	if(id == '3'){
		$("#0").css("background","#50bfea");
		$("#1").css("background","#50bfea");
		$("#2").css("background","#50bfea");
		$("#3").css("background","#ffc300");
		$("#4").css("background","#50bfea");
	}
	if(id == '4'){
		$("#0").css("background","#50bfea");
		$("#1").css("background","#50bfea");
		$("#2").css("background","#50bfea");
		$("#3").css("background","#50bfea");
		$("#4").css("background","#ffc300");
	}
	$("#btn_place span").eq(id).css("background","#FA708C").siblings().css("background","#FA915A");
	var tooltip = $("#" + id).text();
	$.ajax({
		url: "../../../servlet/QxRankingServlet",
		type: "post",
		data: {
			"operation": "toptenranking",
			"currentdate":$(".total_title").find(".total_active").html()
		},
		success: function(result) {
			var result = JSON.parse(result);
		var listJSON = {"list":[]};
		listJSON.list = result;
		schoolJSON.schoolData = result;
		showData();
//		插件换色
		var str = result[0].area+"各学校发展水平排名";
		$("#tdtitle").html(str);
		var title = result[0].area+"各学校发展水平分类得分";
		//隔行换色
		var temp_data = new Array();
		var temp_schoolName = new Array();
		for (var i = 0; i < result.length; i++) {
			temp_schoolName.push(result[i].schoolName);
			temp_data.push(Number(result[i].data[id]));
		}
		getBlankChart("#histogram",title,tooltip,temp_schoolName,temp_data);
	},
		error: function() {
			/*alert("失败");*/
		}
	});
	
};

//翻页
$("#page_numbers").on("click",".goPageLink",function() {
    var linkId = $(this).attr("id").split("_");
    var goPageNo = new Number(linkId[3]);
    goPage(goPageNo);
})

//size每页显示的条数， pageNo第几页
var getPageJSON = function(size, pageNo) {
	var pageJSON = {"schoolData" : []};

	if (schoolJSON.schoolData.length <= size) {
		return schoolJSON;
	} else {
		for (var i = 0; i < size; i++) {
			if ((pageNo - 1) * size + i < schoolJSON.schoolData.length) {
				pageJSON.schoolData[i] = schoolJSON.schoolData[(pageNo - 1) * size + i];
			}
		}
		return pageJSON;
	}
};

//跳转页面
var goPage = function(pageNo) {
    //更新设置当前页面
    currentPageNo = pageNo;
    console.log(currentPageNo)
    console.log(currentPageNo)
    //获取当前页面数据
    currentPageJSON = getPageJSON(pageSize, pageNo);
    console.log(currentPageJSON)
    //显示数据
    showData();
};

var showData = function() {
	currentPageJSON = getPageJSON(pageSize, currentPageNo);
	$("#tb_content").html(TrimPath.processDOMTemplate("tb_content_template",currentPageJSON));
	//获取当前数据，加载模版显示
	//加载翻页按钮
	var totalItems = "" + schoolJSON.schoolData.length;
	//总记录数
	var currentPage = "" + currentPageNo;
	//当前页
	var totalPage = "" + Math.ceil(schoolJSON.schoolData.length / pageSize);

	//总页数
	console.log('totalItems'+totalItems+",currentPage"+currentPage+",totalPage"+totalPage);
	
	$("#page_numbers").html(makePaging(totalItems, currentPage, totalPage, true, true));
//	隔行换色
    var tbcolor = document.getElementById("tb_data");
    var rows = tbcolor.getElementsByTagName("tr");
    for(var i = 0;i < rows.length;i = i+2) {
    	rows[i].style.background = "#dce9f9";            	       	
    }  
}

/*
 * 总分排名
 */
var changeHisogram2 = function(id) {
	$("#btn_place span").eq(id).css("background","#FA708C").siblings().css("background","#FA915A");
	var tooltip = "绩效总得分";
	$.ajax({
		url: "../../../servlet/QxRankingServlet",
		type: "post",
		data: {
			"operation": "toptenranking", 
			"currentdate":$(".total_title").find(".total_active").html()
		},
		success: function(result) {
			var result = JSON.parse(result);
			var listJSON = {"list":[]};
			listJSON.list = result;
			var title = result[0].area +"各学校发展水平总得分";
			var temp_data = new Array();
			var temp_area = new Array();
			var temp_schoolName = new Array();
			for (var i = 0; i < result.length; i++) {
				temp_schoolName.push(result[i].schoolName);
				temp_area.push(result[i].area);
				temp_data.push(Number(result[i].data[id]));
			}
			getTotalRanking("histogram2",title,temp_schoolName,temp_data);
		},
		error: function() {
		}
	});

};

/**
 * 总分排名 右边数据的显示
 */
var rightContent = function() {
	$.ajax({
		url: "../../../servlet/QxRankingServlet",
		type: "post",
		data: {
			"operation": "getdetail",
			"currentdate":$(".total_title").find(".total_active").html()
		},
		success: function(result) {
			var result = JSON.parse(result);
			var max_value = result[0]["max_value"];
			$("#max_value").html(max_value);
			var max_area = result[0]["max_school"];
			$("#max_area").html(max_area);
			
			var min_value = result[0]["min_value"];
			$("#min_value").html(min_value);
			var min_area = result[0]["min_school"];
			$("#min_area").html(min_area);
			
			var avg_value = result[0]["avg_value"];
			$("#avg_value").html(avg_value);
			
			var up_avg_num = result[0]["up_avg_num"];
			$("#up_avg_num").html(up_avg_num);
			var down_avg_num = result[0]["down_avg_num"];
			$("#down_avg_num").html(down_avg_num);
			
			
	},
		error: function() {
		}
	});
};

/*
 * 初始化
 */
var init = function() {
	showtable(6);
$('#fig_sel').change(function() {
	var fig_index=$(this).val()
	if (fig_index==1) {
		showtable(1);
	} else if(fig_index==2){
		showtable(2);
	} else if(fig_index==3){
		showtable(3);
	} else if(fig_index==4){
		showtable(4);
	} else if(fig_index==5){
		showtable(5);
	} else if(fig_index==6){
		showtable(6);
	}
		
})
	changeHisogram(0);	//分类排名
	changeHisogram2(5);	//总分排名
	rightContent();
};

init();

var getMonthRanking = function(result){
	//放入数据到series中
	var series=[];
	    for(var i = 0;i<result['listmonth'].length;i++){
	        series.push({
	            name: result['listmonth'][i],
	            type: 'bar',
	            data: result["listscore"][i]
	        });
	    }
	    console.log(result["area"]);
	    
	var myChart = echarts.init(document.getElementById('month_ranking_center'));

    // 指定图表的配置项和数据
	var option =  {
		title : {
			text: '',
			subtext: ''
		},
		tooltip : {
			trigger: 'axis'
		},
		legend: {
			data:result['listmonth']
		},
		toolbox: {
			show : false,
			feature : {
				dataView : {show: true, readOnly: false},
				magicType : {show: true, type: ['line', 'bar']},
				restore : {show: true},
				saveAsImage : {show: true}
			}
		},
		calculable : true,
		xAxis : [
			{
				type : 'category',
				data : [result["area"]]
			}
		],
		yAxis : [
			{
				type : 'value'
			}
		],
		series : series
	};



    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
}
$.ajax({
	url:"../../../servlet/MonthContrastServlet",
	type:"post",
	data:{
		"operation":"qxmonthcontrast"
	},
	success:function(data){
		var result = JSON.parse(data);
		getMonthRanking(result[0]);
	},
	error:function(){
		
	}
});
