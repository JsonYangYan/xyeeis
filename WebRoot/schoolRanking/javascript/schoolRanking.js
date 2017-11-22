function show_time_data() {
	init();
}
	
//排名前十
var init =function(){
	$.ajax({
		url: "../../servlet/SchoolRankingServlet",
		type: "post",
		data: {
			"operation": "school_topranking",
			"currentdate":$(".total_title").find(".total_active").html()
		},
		success: function(json) {
			var json = JSON.parse(json);
			var temp_arr =  new Array();
			if(json.length!=0){
				
				for(var i=0; i<json.length; i++){
					temp_arr.push([json[i].area_id,json[i].area_name,json[i].school_name,json[i].value]);
				}
				var html = "";
				for(var i = 0; i < temp_arr.length; i++) {
					var j = i+1;
					//$html += "<ul>";
					//$html += "<li>" + j + "</li>" + "<li>" + temp_arr[i][1] + "</li>"+ "<li>" + temp_arr[i][2] + "</li>"+ "<li>" + temp_arr[i][3] + "</li></ul>";
					if(j%2 == 1){
						html += "<ul>";
						html += "<li>" + j + "</li>" + "<li>" + temp_arr[i][1] + "</li>"+ "<li>" + temp_arr[i][2] + "</li>"+ "<li>" + temp_arr[i][3] + "</li></ul>";
					} else {
						html += "<ul style='background-color: #DCE9F9;'>";
						html += "<li>" + j + "</li>" + "<li>" + temp_arr[i][1] + "</li>"+ "<li>" + temp_arr[i][2] + "</li>"+ "<li>" + temp_arr[i][3] + "</li></ul>";
					}
				}
			}else{
				var html ="<div style='text-align:center;padding:20px 0px'><h2>没有数据</h2></div>"
			}
			$(".top_10").html(html);
			
		},
		error: function() {
			alert("失败");
		}
	});
	
	//排名11~90
	$.ajax({
		url: "../../servlet/SchoolRankingServlet",
		type: "post",
		data: {
			"operation": "school_middleranking",
			"currentdate":$(".total_title").find(".total_active").html()
		},
		success: function(json) {
			var json = JSON.parse(json);
			if(json.length!=0){
				var listJSON = {"school":[]};
				schoolJSON.schoolData = json;
				showData();
			}else{
				var html ="<div style='text-align:center;padding:20px 0px'><h2>没有数据</h2></div>";
				$(".middle_80").html(html);
			}
			
	},
		error: function() {
			alert("失败");
		}
	});
	
	//排名90~100
	$.ajax({
		url: "../../servlet/SchoolRankingServlet",
		type: "post",
		data: {
			"operation": "school_lastranking",
			"currentdate":$(".total_title").find(".total_active").html()
		},
		success: function(json) {
			var json = JSON.parse(json);
			if(json.length!=0 ){
				
				var temp_arr =  new Array();
				for(var i=0; i<json.length; i++){
					temp_arr.push([json[i].area_id,json[i].area_name,json[i].school_name,json[i].value]);
				}
				var schoolNum = json[4].schoolNum;
				var html = "";
				var k = temp_arr.length-1;
				for(var i = 0; i < temp_arr.length; i++) {
					var j = i+schoolNum-9;
					//$html += "<ul>";
					//$html += "<li>" + j + "</li>" + "<li>" + temp_arr[i][1] + "</li>"+ "<li>" + temp_arr[i][2] + "</li>"+ "<li>" + temp_arr[i][3] + "</li></ul>";
					if(j%2 == 1){
						html += "<ul>";
						html += "<li>" + j + "</li>" + "<li>" + temp_arr[k][1] + "</li>"+ "<li>" + temp_arr[k][2] + "</li>"+ "<li>" + temp_arr[k][3] + "</li></ul>";
					} else {
						html += "<ul style='background-color: #DCE9F9;'>";
						html += "<li>" + j + "</li>" + "<li>" + temp_arr[k][1] + "</li>"+ "<li>" + temp_arr[k][2] + "</li>"+ "<li>" + temp_arr[k][3] + "</li></ul>";
					}
					k--;
				}
			}else{

				html ="<div style='text-align:center;padding:20px 0px'><h2>没有数据</h2></div>"
			}
			$(".last_10").html(html);
			
	},
		error: function() {
			alert("失败");
		}
	});
	
}


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
	$(".middle_80").html(TrimPath.processDOMTemplate("list_template", currentPageJSON));
	
	//加载翻页按钮
	var totalItems = "" + schoolJSON.schoolData.length;

	//总记录数
	var currentPage = "" + currentPageNo;

	//当前页
	var totalPage = "" + Math.ceil(schoolJSON.schoolData.length / pageSize);

	//总页数
	$("#page_numbers").html(makePaging(totalItems, currentPage, totalPage, true, true));
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