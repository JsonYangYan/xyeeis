
//读取json文件
var readJson = function(id,questionTitle,tooltip,unit) {
	 $.ajax({
	 url: "/xyeeis/servlet/ThirdIndexScoreServlet",
	 type: "POST",
	 data:{
		 "id": id,
		 "currentdate":$(".total_title").find(".total_active").html()
	 },
	 success:function(bindData) {
		 var bindData = JSON.parse(bindData);
		 var temp_data = new Array();
			var sum = 0;
			var average = 0;
			for (var i = 0; i < bindData.rule.length; i++) {
				temp_data.push(Number(bindData.rule[i]));
				sum += Number(bindData.rule[i]);
			}
			average = sum/bindData.rule.length;
			average = average.toString();
			average = average.substr(0,5);
			var questionTitle1 = "";
			getBlankChart("#container_colum",questionTitle1,tooltip,bindData.area,temp_data);
			
			$("#containeravg").html('襄阳市&nbsp;&nbsp;'+ questionTitle + '&nbsp;&nbsp;平均值为：' + '<span style="color:red;">'+average +'</span>' );	
			$("#containeravg").css("display", "block");
	 },
     error:function() {
         alert("操作失败！！");
     }
	});
	
};

/*
 * 切换题目,加载柱状图
 */
var eventBind = function(id) {
	var questionTitle = $("#" + id).html();
	var tooltip = questionTitle;
	var unit = '';
	var arr = [{type:"比例",uni:"%"},{type:"终端数",uni:"台"},{type:"小时数",uni:"小时"},{type:"平均时间",uni:"小时"},{type:"使用率",uni:"%"}];//拥有的单位数组
	for(var i = 0; i<5; i++) {
		if(questionTitle.indexOf(arr[i].type)) {
			unit = arr[i].uni;
		}
	}
	$("#container_colum").height(400);
	readJson(id,questionTitle,tooltip,unit);
};

/*
 * 初始化树
 */
var initData = function() {
	
	//初始化树状图
	$.getJSON("../json/index_tree.json",function(data) {
		var treeJson = {"quizs":[]};
		treeJson.quizs = data;
		$("#treeview_list").html(TrimPath.processDOMTemplate("treeview_template", treeJson));

		//树状图初始化
		$(".treeview").treeview({
			unique : true,
			collapsed : true,
			animated : "fast",
			prerendered : false
		});
		
		//初始化柱状图
		eventBind(1);
	});
};

/*
 * 初始化
 */	
var statisticInit = function() {
	initData();
};

statisticInit();