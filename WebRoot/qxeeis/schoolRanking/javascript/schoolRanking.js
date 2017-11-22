//月份
function show_time_data() {
	as_de('DESC');
}

//排名11~90
function as_de(fa_rack){
	$.ajax({
		url: "../../../servlet/QxSchoolRankingServlet",
		type: "post",
		data: {
			"operation": "school_ranking",
			"order":fa_rack,
			"currentdate":$(".total_title").find(".total_active").html()
		},
		success: function(json) {
			var json = JSON.parse(json);
			if(json.length == 0){
				$(".middle_80").html('');
				$("#page_numbers").html("<div style = 'text-align: center;'><h2>没有该地区的数据！</h2></div>");
			}else{
				var listJSON = {"school":[]};
				schoolJSON.schoolData = json;
				showData();
			}
			
	},
		error: function() {
			alert("失败");
		}
	});
}


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
	//获取当前数据，加载模版显示
	currentPageJSON = getPageJSON(pageSize, currentPageNo);
	
	console.log(currentPageJSON);
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
	
	$("#fa-arrow-up").click(function () {
	 	as_de('ASC');
	})
	$("#fa-arrow-down").click(function () {
		as_de('DESC');
	})
	
	
	as_de('DESC');