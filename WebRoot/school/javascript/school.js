function show_time_data() {
	initData();
	$(".to_show_detail").each(function(){
		var href = $(this).attr("href");
		var arr = href.split("&");
		var href_new = arr[0] +"&date="+$(".total_title").find(".total_active").html();
		$(this).attr("href",href_new);
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

    //获取当前页面数据
    currentPageJSON = getPageJSON(pageSize, pageNo);

    //显示数据
    showData();
};


//查询学校
var search_school = function() {
	var q = $("input[name='school_name']").val();  //q表示需要查找的内容  ---学校名称     
        q = q.length == 0 ? "null" : q;       
        if(q == "null") {
            alert("请输入需要查找的学校信息!");
        } else {
            $.ajax({
                url: "",
                type: "POST",
                data: {
                    "name": q,                    
                    "operation": "query_school",
                    "currentdate":$(".total_title").find(".total_active").html()
                },
                success:function(data) {
                     schoolJSON.school = jQuery.parseJSON(data);
                     currentPageNo = 1; 
                     showData();
                },
                error:function() {
                    alert("查找学校连接数据库失败！");
                }                    
            });           
        }
}

var showData = function() {
	
	//获取当前数据，加载模版显示
	currentPageJSON = getPageJSON(pageSize, currentPageNo);
	$("#school_list").html(TrimPath.processDOMTemplate("list_template", currentPageJSON));
	
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
 * 查询学校列表
 */
var eventBind = function(str) {
	 $.ajax({
		 url: "/xyeeis/servlet/SchoolNumberServlet",
		 type: "POST",
		 data:{
			 "str":str,
			 "currentdate":$(".total_title").find(".total_active").html()
		 },
		 success:function(bindData) {
			 var result = JSON.parse(bindData);	
			 if(result.length == 0){
				 	$("#school_list").html('');
					$("#page_numbers").html("<div style = 'text-align: center;'><h2>没有该地区的数据！</h2></div>");
				}else{
					 schoolJSON.schoolData = result;
				     showData();
				}
			 
			 
		},
		error:function() {
	       alert("操作失败！！");
	    }
	});
};

/*
 * 初始化树
 */
var initData = function() {
	
	//初始化树状图
	$.getJSON("../json/area_school_tree.json",function(data) {
		var treeJson = {"school":[]};
		treeJson.school = data;
		$("#treeview_list").html(TrimPath.processDOMTemplate("treeview_template", treeJson));

		//树状图初始化
		$(".treeview").treeview({
			unique : true,
			collapsed : true,
			animated : "fast",
			prerendered : false
		});

		//初始化,显示第一个地区的小学学校列表
		eventBind("襄城区,小学");
	});
};

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
	var pageSize = 5;
	
	initData();
	
	$(".to_show_detail").each(function(){
		var href = $(this).attr("href");
		var arr = href.split("&");
		var href_new = arr[0] +"&date="+$(".total_title").find(".total_active").html();
		$(this).attr("href",href_new);
	});
	
	