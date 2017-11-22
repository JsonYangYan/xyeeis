

//点击事件
var bindEvent = function() {
	
	//翻页
	$(" .goPageLink").click(function() {
        var linkId = $(this).attr("id").split("_");
        var goPageNo = new Number(linkId[3]);
        goPage(goPageNo);
    });

	
}


//size每页显示的条数， pageNo第几页
var getPageJSON = function(size, pageNo) {
	var pageJSON = {"question" : []};

	if (questionJSON.question.length <= size) {
		return questionJSON;
	} else {
		for (var i = 0; i < size; i++) {
			if ((pageNo - 1) * size + i < questionJSON.question.length) {
				pageJSON.question[i] = questionJSON.question[(pageNo - 1) * size + i];
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
	$("#ques_list").html(TrimPath.processDOMTemplate("ques_template", currentPageJSON));;

	//加载翻页按钮
	var totalItems = "" + questionJSON.question.length;

	//总记录数
	var currentPage = "" + currentPageNo;

	//当前页
	var totalPage = "" + Math.ceil(questionJSON.question.length / pageSize);

	//总页数
	$("#page_numbers").html(makePaging(totalItems, currentPage, totalPage, true, true));
}


//查询获得全部数据
var initData = function() {
    $.ajax({
        url: "",
        type:"POST",
        data: {
            "operation":"allQues"
        },
        success:function(data) {
           questionJSON.question = jQuery.parseJSON(data);
           showData();
        },
        error:function() {
            alert("获取所有的question连接数据库失败!");
        }
    });
};


var init = function() {
	//用于存储从后台获取的数据,所有数据
	var questionJSON = {question:[]};

	//当前页的数据
	var currentPageJSON = {};

	//当前处于第几页
	var currentPageNo = 1;

	//每页显示的条数
	var pageSize = 10;
	
	initData();
	bindEvent();
}
init();
