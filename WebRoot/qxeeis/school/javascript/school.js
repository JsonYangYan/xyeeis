function show_time_data() {
	initData();
	smallSchoolShowData();
	middleSchoolShowData();
	highSchoolShowData();
	otherSchoolShowData();
	
	$(".to_show_detail").each(function(){
		var href = $(this).attr("href");
		var arr = href.split("&");
		var href_new = arr[0] +"&date="+$(".total_title").find(".total_active").html();
		$(this).attr("href",href_new);
	});
}

//tab标签切换
function setTab(name, cursel, n) {
	for (i = 1; i <= n; i++) {
		var menu = document.getElementById(name + i);
		var con = document.getElementById("school_" + name + "_" + i);
		menu.className = i == cursel ? "hover" : "";
		con.style.display = i == cursel ? "block" : "none";
	}
}

/*
 * 查询学校
 */
var eventBind = function() {
	// 查询学校
	$("#school_button_query").click(function() {
		var q = $("input[name='school_name']").val(); // q表示需要查找的内容 ---学校名称
		q = q.length == 0 ? "null" : q;
		if (q == "null") {
			alert("请输入需要查找的学校信息!");
		} else {
			$.ajax({
				url : "../../../servlet/QxSchoolNumberServlet",
				type : "POST",
				data : {
					"schoolName" : q,
					"operation" : "querySchool",
					"currentdate":$(".total_title").find(".total_active").html()
				},
				success : function(data) {
					var data = JSON.parse(data);
					$html_primary = "";
					$html_junior = "";
					$html_senior = "";
					$html_other = "";

					for (i = 0; i < data.length; i++) {
						if (data[i].type == "小学") {
							$html_primary += "<ul><li>" + data[i].name
									+ "</li><li ><a href='school_detail.html?name="
									+ data[i].name
									+ "' target='_blank' >详情</a></li></ul>";
						}
						if (data[i].type == "初中") {
							$html_junior += "<ul><li>" + data[i].name
									+ "</li><li ><a href='school_detail.html?name="
									+ data[i].name
									+ "' target='_blank' >详情</a></li></ul>";
						}
						if (data[i].type == "高中") {
							$html_senior += "<ul><li>" + data[i].name
									+ "</li><li ><a href='school_detail.html?name="
									+ data[i].name
									+ "' target='_blank' >详情</a></li></ul>";
						}
						if (data[i].type == "其他") {
							$html_other += "<ul><li>" + data[i].name
									+ "</li><li ><a href='school_detail.html?name="
									+ data[i].name
									+ "' target='_blank' >详情</a></li></ul>";
						}
					}

					$("#school_list_1").html($html_primary);
					$("#school_list_2").html($html_junior);
					$("#school_list_3").html($html_senior);
					$("#school_list_4").html($html_other);
				},
				error : function() {
					alert("查找学校连接数据库失败！");
				}
			});
		}
	});
};

eventBind();

/*
 * 初始化
 */	
	//小学
	//当前页的数据
	var smallSchoolCurrentPageJSON = {};
	//当前处于第几页
	var smallSchoolCurrentPageNo = 1;
	//每页显示的条数
	var smallSchoolPageSize = 5;
	//初中
	//当前页的数据
	var middleSchoolCurrentPageJSON = {};

	//当前处于第几页
	var middleSchoolCurrentPageNo = 1;

	//每页显示的条数
	var middleSchoolPageSize = 5;
	//高中
	//当前页的数据
	var highSchoolCurrentPageJSON = {};

	//当前处于第几页
	var highSchoolCurrentPageNo = 1;

	//每页显示的条数
	var highSchoolPageSize = 5;
	
	//其他
	//当前页的数据
	var otherSchoolCurrentPageJSON = {};

	//当前处于第几页
	var otherSchoolCurrentPageNo = 1;

	//每页显示的条数
	var otherSchoolPageSize = 5;
	
	var small_school_json = {schoolData:[]};
	var middle_school_json = {schoolData:[]};
	var high_school_json = {schoolData:[]};
	var other_school_json = {schoolData:[]};
//翻页 小学
$("#small_school_page").on("click",".goPageLink",function() {
	//alert();
    var linkId = $(this).attr("id").split("_");
    var goPageNo = new Number(linkId[3]);
    smallSchoolGoPage(goPageNo);
})

//初中
$("#middle_school_page").on("click",".goPageLink",function() {
    var linkId = $(this).attr("id").split("_");
    var goPageNo = new Number(linkId[3]);
    middleSchoolGoPage(goPageNo);
})
//高中
$("#high_school_page").on("click",".goPageLink",function() {
    var linkId = $(this).attr("id").split("_");
    var goPageNo = new Number(linkId[3]);
    highSchoolGoPage(goPageNo);
})
//其他
$("#other_school_page").on("click",".goPageLink",function() {
    var linkId = $(this).attr("id").split("_");
    var goPageNo = new Number(linkId[3]);
    otherSchoolGoPage(goPageNo);
})

//size每页显示的条数， pageNo第几页 小学
var getSmallSchoolPageJSON = function(size, pageNo) {
	var pageJSON = {"schoolData" : []};
	if (small_school_json["schoolData"].length <= size) {
		return small_school_json;
	} else {
		for (var i = 0; i < size; i++) {
			if ((pageNo - 1) * size + i < small_school_json.schoolData.length) {
				pageJSON.schoolData[i] = small_school_json.schoolData[(pageNo - 1) * size + i];
			}
		}
		return pageJSON;
	}
};

//初中
var getMiddleSchoolPageJSON = function(size, pageNo) {
	var pageJSON = {"schoolData" : []};
	if (middle_school_json["schoolData"].length <= size) {
		return middle_school_json;
	} else {
		for (var i = 0; i < size; i++) {
			if ((pageNo - 1) * size + i < middle_school_json.schoolData.length) {
				pageJSON.schoolData[i] = middle_school_json.schoolData[(pageNo - 1) * size + i];
			}
		}
		return pageJSON;
	}
};

//高中
var getHighSchoolPageJSON = function(size, pageNo) {
	var pageJSON = {"schoolData" : []};
	if (high_school_json["schoolData"].length <= size) {
		return high_school_json;
	} else {
		for (var i = 0; i < size; i++) {
			if ((pageNo - 1) * size + i < high_school_json.schoolData.length) {
				pageJSON.schoolData[i] = high_school_json.schoolData[(pageNo - 1) * size + i];
			}
		}
		return pageJSON;
	}
};
//其他
var getOtherSchoolPageJSON = function(size, pageNo) {
	var pageJSON = {"schoolData" : []};
	if (other_school_json["schoolData"].length <= size) {
		return other_school_json;
	} else {
		for (var i = 0; i < size; i++) {
			if ((pageNo - 1) * size + i < other_school_json.schoolData.length) {
				pageJSON.schoolData[i] = other_school_json.schoolData[(pageNo - 1) * size + i];
			}
		}
		return pageJSON;
	}
};

//跳转页面 小学
var smallSchoolGoPage = function(pageNo) {
    //更新设置当前页面
	smallSchoolCurrentPageNo = pageNo;

    //获取当前页面数据
	smallSchoolCurrentPageJSON = getSmallSchoolPageJSON(smallSchoolPageSize, pageNo);

    //显示数据
    smallSchoolShowData();
};

//跳转页面 初中
var middleSchoolGoPage = function(pageNo) {

    //更新设置当前页面
	middleSchoolCurrentPageNo = pageNo;

    //获取当前页面数据
	middleSchoolCurrentPageJSON = getMiddleSchoolPageJSON(middleSchoolPageSize, pageNo);

    //显示数据
    middleSchoolShowData();
};

//高中
//跳转页面
var highSchoolGoPage = function(pageNo) {

    //更新设置当前页面
	highSchoolCurrentPageNo = pageNo;

    //获取当前页面数据
	highSchoolCurrentPageJSON = getHighSchoolPageJSON(highSchoolPageSize, pageNo);

    //显示数据
    highSchoolShowData();
};

//其他
//跳转页面
var otherSchoolGoPage = function(pageNo) {

    //更新设置当前页面
	otherSchoolCurrentPageNo = pageNo;

    //获取当前页面数据
	otherSchoolCurrentPageJSON = getOtherSchoolPageJSON(otherSchoolPageSize, pageNo);

    //显示数据
    otherchoolShowData();
};

//小学
var smallSchoolShowData = function() {
	//获取当前数据，加载模版显示
	smallSchoolCurrentPageJSON = getSmallSchoolPageJSON(smallSchoolPageSize, smallSchoolCurrentPageNo);
	console.log(smallSchoolCurrentPageJSON);
	$("#small_school").html(TrimPath.processDOMTemplate("small_school_temple", smallSchoolCurrentPageJSON));
	
	//加载翻页按钮
	var totalItems = "" + small_school_json.schoolData.length;

	//总记录数
	var currentPageNo = "" + smallSchoolCurrentPageNo;
	//当前页
	var totalPage = "" + Math.ceil(small_school_json.schoolData.length / smallSchoolPageSize);
	
	console.log(small_school_json["schoolData"]);
	//总页数
	if(small_school_json["schoolData"] != ''){
		$("#small_school_page").html(makePaging(totalItems, currentPageNo, totalPage, true, true));
	}
	
	
}
//初中
var middleSchoolShowData = function() {
	
	//获取当前数据，加载模版显示
	middleSchoolCurrentPageJSON = getMiddleSchoolPageJSON(middleSchoolPageSize, middleSchoolCurrentPageNo);
	$("#middle_school").html(TrimPath.processDOMTemplate("middle_school_temple", middleSchoolCurrentPageJSON));
	
	//加载翻页按钮
	var totalItems = "" + middle_school_json.schoolData.length;

	//总记录数
	var currentPageNo = "" + middleSchoolCurrentPageNo;
	//当前页
	var totalPage = "" + Math.ceil(middle_school_json.schoolData.length / middleSchoolPageSize);

	//总页数
	if(middle_school_json["schoolData"] != ''){
		$("#middle_school_page").html(makePaging(totalItems, currentPageNo, totalPage, true, true));
	}
	
}

//高中
var highSchoolShowData = function() {
	
	//获取当前数据，加载模版显示
	highSchoolCurrentPageJSON = getHighSchoolPageJSON(highSchoolPageSize, highSchoolCurrentPageNo);
	$("#high_school").html(TrimPath.processDOMTemplate("high_school_temple", highSchoolCurrentPageJSON));
	
	//加载翻页按钮
	var totalItems = "" + high_school_json.schoolData.length;

	//总记录数
	var currentPageNo = "" + highSchoolCurrentPageNo;
	//当前页
	var totalPage = "" + Math.ceil(high_school_json.schoolData.length / highSchoolPageSize);

	//总页数
	if(high_school_json["schoolData"] != ''){
		$("#high_school_page").html(makePaging(totalItems, currentPageNo, totalPage, true, true));
	}
}

//其他
var otherSchoolShowData = function() {
	
	//获取当前数据，加载模版显示
	otherSchoolCurrentPageJSON = getOtherSchoolPageJSON(otherSchoolPageSize, otherSchoolCurrentPageNo);
	$("#other_school").html(TrimPath.processDOMTemplate("other_school_temple", otherSchoolCurrentPageJSON));
	
	//加载翻页按钮
	var totalItems = "" + other_school_json.schoolData.length;

	//总记录数
	var currentPageNo = "" + otherSchoolCurrentPageNo;
	//当前页
	var totalPage = "" + Math.ceil(other_school_json.schoolData.length / otherSchoolPageSize);

	//总页数
	if(other_school_json["schoolData"] != ''){
		$("#other_school_page").html(makePaging(totalItems, currentPageNo, totalPage, true, true));
	}
	
}

/*
 * 初始化
 */
var initData = function() {
	$.ajax({
		url : "../../../servlet/QxSchoolNumberServlet",
		type : "POST",
		data : {
			"operation" : "getSchool",
			"currentdate":$(".total_title").find(".total_active").html()
		},
		async:false,
		success : function(data) {
			small_school_json["schoolData"] = [];
			middle_school_json["schoolData"] = [];
			high_school_json["schoolData"] = [];
			other_school_json["schoolData"] = [];
			var data = JSON.parse(data);
				for (i = 0; i < data.length; i++) {
					if (data[i].type == "小学") {
						small_school_json["schoolData"].push(data[i]);
						
					}
					if (data[i].type == "初中") {
						middle_school_json["schoolData"].push(data[i]);	
						
					}
					if (data[i].type == "高中") {
						high_school_json["schoolData"].push(data[i]);	
						
					}
					if (data[i].type == "其他") {
						other_school_json["schoolData"].push(data[i]);
					}
					
				}
				if(data.length>0){
					$(".school_list_title").html(data[0].area + "中小学概况");
				}
				if(small_school_json["schoolData"].length==0) {
					$("#small_school_page").hide();
					
				}else{
					$("#small_school_page").show();
				}
				if(middle_school_json["schoolData"].length==0) {
					$("#middle_school_page").hide();
				}else{
					$("#middle_school_page").show();
				}
				if(high_school_json["schoolData"].length==0) {
					
					$("#high_school_page").hide();
				}else{
					$("#high_school_page").show();
				}
				if(other_school_json["schoolData"].length==0) {
					$("#other_school_page").hide();
				}else{
					$("#other_school_page").show();
				}

			
		},
		error : function() {
			alert("操作失败！！");
		}
	});
}

initData();
smallSchoolShowData();
middleSchoolShowData();
highSchoolShowData();
otherSchoolShowData();

$(".to_show_detail").each(function(){
	var href = $(this).attr("href");
	var arr = href.split("&");
	var href_new = arr[0] +"&date="+$(".total_title").find(".total_active").html();
	$(this).attr("href",href_new);
});