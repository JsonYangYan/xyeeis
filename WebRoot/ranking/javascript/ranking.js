function show_time_data() {
	init();
	//show_total_rank();
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
	var pageSize = 13;
/*
 * 表格显示数据函数
 */
function showtable(fig_date) {
	$.ajax({
		url: "../../servlet/RankingServlet",
		type: "post",
		data: {
			"operation": "dimensionRanking",
			"fig":fig_date,
			"currentdate":$(".total_title").find(".total_active").html()
		},
		success: function(result) {	
			var result = JSON.parse(result);
			
			schoolJSON.schoolJSON = result;
			showData();
					
		},
		error: function() {
			alert("失败");
		}
	});
};

/*
 * 单击后，highchats图相应变化
 */
var changeHisogram = function(id) {
	if(id == '1'){
		$("#1").css("background","#ffc300");
		$("#2").css("background","#50bfea");
		$("#3").css("background","#50bfea");
		$("#4").css("background","#50bfea");
		$("#5").css("background","#50bfea");
	}
	if(id == '2'){
		$("#1").css("background","#50bfea");
		$("#2").css("background","#ffc300");
		$("#3").css("background","#50bfea");
		$("#4").css("background","#50bfea");
		$("#5").css("background","#50bfea");
	}
	if(id == '3'){
		$("#1").css("background","#50bfea");
		$("#2").css("background","#50bfea");
		$("#3").css("background","#ffc300");
		$("#4").css("background","#50bfea");
		$("#5").css("background","#50bfea");
	}
	if(id == '4'){
		$("#1").css("background","#50bfea");
		$("#2").css("background","#50bfea");
		$("#3").css("background","#50bfea");
		$("#4").css("background","#ffc300");
		$("#5").css("background","#50bfea");
	}
	if(id == '5'){
		$("#1").css("background","#50bfea");
		$("#2").css("background","#50bfea");
		$("#3").css("background","#50bfea");
		$("#4").css("background","#50bfea");
		$("#5").css("background","#ffc300");
	}
	$("#btn_place span").eq(id).css("background","#FA708C").siblings().css("background","#FA915A");
	var title = "襄阳市各区县发展水平分类得分";
	var tooltip = $("#" + id).text();
	$.ajax({
		url: "../../servlet/RankingServlet",
		type: "post",
		data: {
			"operation": "monthdimensionRanking",
			"fig":id
		},
		success: function(result) {
		var data = JSON.parse(result);
		getMonthRanking(data[0],"histogram");
	},
		error: function() {
			alert("失败");
		}
	});
};

/*
 * 总分排名
 */
var changeHisogram2 = function(id) {
	$("#btn_place span").eq(id).css("background","#FA708C").siblings().css("background","#FA915A");
	var title = "襄阳市各区县发展水平总得分";
	var tooltip = "绩效总得分";
	$.ajax({
		url: "../../servlet/RankingServlet",
		type: "post",
		data: {
			"operation": "ranking", 
			"currentdate":$(".total_title").find(".total_active").html()
		},
		success: function(result) {
			var result = JSON.parse(result);
			var temp_data = new Array();
			var temp_area = new Array();
			for (var i = 0; i < result[0].length; i++) {
				temp_area.push(result[0][i].area);
				temp_data.push(Number(result[0][i].data[id]));
			}
			getTotalRanking("histogram2",title,temp_area,temp_data);
		},
		error: function() {
			alert("失败");
		}
	});

};

/**
 * 总分排名 右边数据的显示
 */
var rightContent = function() {
	$.ajax({
		url: "../../servlet/RankingServlet",
		type: "post",
		data: {
			"operation": "getdetail",
			"currentdate":$(".total_title").find(".total_active").html()
		},
		success: function(result) {
			var result = JSON.parse(result);
		//alert(typeof(result));
			var max_value = result[0]["max_value"];
			$("#max_value").html(max_value);
			var max_area = result[0]["max_area"];
			$("#max_area").html(max_area);
			
			var min_value = result[0]["min_value"];
			$("#min_value").html(min_value);
			var min_area = result[0]["min_area"];
			$("#min_area").html(min_area);
			
			var avg_value = result[0]["avg_value"];
			$("#avg_value").html(avg_value);
			
			var up_avg_num = result[0]["up_avg_num"];
			$("#up_avg_num").html(up_avg_num);
			var down_avg_num = result[0]["down_avg_num"];
			$("#down_avg_num").html(down_avg_num);
			
	},
		error: function() {
			alert("失败");
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
	changeHisogram(1);
	changeHisogram2(5);
	rightContent();
};

init();


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

    //获取当前页面数据
    currentPageJSON = getPageJSON(pageSize, pageNo);

    //显示数据
    showData();
};

var showData = function() {
	
	//获取当前数据，加载模版显示
	currentPageJSON = getPageJSON(pageSize, currentPageNo);
	$("#tb_content").html(TrimPath.processDOMTemplate("tb_content_template", currentPageJSON));
	//隔行换色
    var tbcolor = document.getElementById("tb_data");
    var rows = tbcolor.getElementsByTagName("tr");
    for(var i = 0;i < rows.length;i = i+2) {
    	rows[i].style.background = "#dce9f9";             	       	
    }  
	//加载翻页按钮
	var totalItems = "" + schoolJSON.schoolData.length;

	//总记录数
	var currentPage = "" + currentPageNo;

	//当前页
	var totalPage = "" + Math.ceil(schoolJSON.schoolData.length / pageSize);

	//总页数
	$("#page_numbers").html(makePaging(totalItems, currentPage, totalPage, true, true));
}
