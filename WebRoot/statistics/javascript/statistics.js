function show_time_data() {
	init();
}

var formNewArr_func_1 = function(divplace,title,type) { 
	var temp_array =  new Array();
	
	 $.ajax({
		 url: "../../servlet/StatisticsTypeServlet",
		 type: "POST",
		 data:{
			 operation:"wholestatistics",
			 "currentdate":$(".total_title").find(".total_active").html()
		 },
		 success:function(data) {
			 var data = JSON.parse(data);
			 if( type == "pie" ){
				 for(var i=0;i<data.length;i++){
					 temp_array.push([data[i].name,data[i].value]);   //将JASON格式的文本存进去
				 }	
				$("#d_title").text(title);
				$("#data").text(temp_array[0][1] + "%");
				$("#data_describe").text(temp_array[0][0]);
				$("#d_info").html("<li><span>" + data[0].subnum + "</span>个学校" + temp_array[0][0] + "</li><li><span>" + (data[0].totalnum-data[0].subnum) + "</span>个学校" + temp_array[1][0] + "</li>");
				 
				 
				     build_pieTable(divplace,title,temp_array);  //生成饼状图   将JASON格式的文本
			 }
			 else{
				    var temp_data = new Array();
				    var temp_area = new Array();
				    for (var i = 0; i < data.length; i++) {
					    temp_area.push(data[i].name);
					    temp_data.push(data[i].value);
				    }
				    getBlankChart(divplace,title,"提交学校",temp_area,temp_data,'%');   //生成执折线图	
			 }
         },
         error:function() {
             alert("操作失败！！");
         }
	 });
}

var formNewArr_func_2 = function(divplace,title,type) { 
	
	 $.ajax({
		 url: "../../servlet/StatisticsTypeServlet",
		 type: "POST",
		 data:{
			 operation:"classifystatistics",
			 "currentdate":$(".total_title").find(".total_active").html()
		 },
		 success:function(data) {
			 var data = JSON.parse(data);
			 var temp_arr =  new Array();
			for(var i=0; i<data.length; i++){
				temp_arr.push([data[i].name,data[i].value,data[i].area_total,data[i].state_total,data[i].area_id]);
			}
			
			//画图
			if( type == "pie" ){
				 for(var i=0;i<data.length;i++){
					 var temp_arr =  new Array();
					 temp_array.push([data[i].name,data[i].value]);   //将JASON格式的文本存进去
				 }
				     build_pieTable(divplace,title,temp_array);  //生成饼状图   将JASON格式的文本
			 }
			 else{
				    var temp_data = new Array();
				    var temp_area = new Array();
				    var temp_data2 = new Array();
				    for (var i = 0; i < data.length; i++) {
				    	var j = 100 -  data[i].value;
					    temp_area.push(data[i].name);
					    temp_data.push(data[i].value);
					    temp_data2.push(j);
				    }
				    getStackedColumnChart(divplace,title,"提交学校",temp_area,temp_data,temp_data2,'%');//生成堆积图
				    //getBlankChart(divplace,title,"提交学校",temp_area,temp_data,'%');   //生成执折线图	
			 }
			
			//输出区县数据
			var valuedata = 0;
			for(var i=0; i<temp_arr.length; i++){
				valuedata += temp_arr[i][1];
			}
			valuedata = temp_arr[0][1]/valuedata * 100; 
			$("#a_data").text(valuedata.toFixed(2) + "%");
			$html = "<table><tr><td>区域</td><td>学校总数</td><td>已提交数量</td></tr>";
			for(var i = 0; i < temp_arr.length; i++) {
				$html += "<tr><td><a onclick='showSchool("+ temp_arr[i][4] +");' href='#" + temp_arr[i][4] + "'>" + temp_arr[i][0] + "</a></td><td>"+ temp_arr[i][2] + "</td><td>" + temp_arr[i][3] + "</td></tr>"; 
			}
			$html += "</table>";
			$(".describePlace1").html($html);
			 
		 },
		 error:function() {
            alert("操作失败！！");
       }
	 });
}


//显示区域提交学校情况
function showSchool(id) {
	var area_id = id;
	$.ajax({
		url: "../../servlet/StatisticsTypeServlet",
		type: "POST",
		data:{
			operation:"getschoolname",
			id: area_id,
			"currentdate":$(".total_title").find(".total_active").html()
		},
		success:function(data) {
			var data = JSON.parse(data);
			document.getElementById('show_area_detail').style.display='block';
			document.getElementById('fade').style.display='block';
			schoolJSON.schoolData = data;
			showData();
			console.log(data);
			$("#school_list h2").html(data[0]["area"]+"信息化数据提交情况");
		},
		error:function() {
			
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

    //获取当前页面数据
    currentPageJSON = getPageJSON(pageSize, pageNo);

    //显示数据
    showData();
};

var showData = function() {
	
	//获取当前数据，加载模版显示
	currentPageJSON = getPageJSON(pageSize, currentPageNo);
	
	$("#question").html(TrimPath.processDOMTemplate("list_template", currentPageJSON));
	
	//加载翻页按钮
	var totalItems = "" + schoolJSON.schoolData.length;

	//总记录数
	var currentPage = "" + currentPageNo;

	//当前页
	var totalPage = "" + Math.ceil(schoolJSON.schoolData.length / pageSize);

	//总页数
	$("#page_numbers").html(makePaging(totalItems, currentPage, totalPage, true, true));
	console.log(currentPageJSON);
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
	var pageSize = 15;


//隐藏“更新成功”提示语
function hideRedNotic() {
	document.getElementById('updata_notic_1').style.display='none';
}

//事件绑定函数
var bindevent = function() {
	
	$("#update_btn").click(function() {
		var title = $(this).prev().find(".d_title").text();
		var divplace = $(this).parent().parent().next().attr("id");
		var temp_array =  new Array();
		formNewArr_func_1("#"+divplace,title);
	});
	
	$("#update_btn_1").click(function() {
		document.getElementById('updata_notic_1').style.display='block';
		setTimeout("hideRedNotic()",2000);
		
		var title = $(this).prev().find(".d_title").text();
		var divplace = $(this).parent().parent().next().attr("id"); 
		var temp_array =  new Array();
		formNewArr_func_1("#"+divplace,title,"pie");
	});
	
	$("#close_list").on("click",function() {
		document.getElementById('show_area_detail').style.display='none';
		document.getElementById('fade').style.display='none';
	});
}
	
//初始化函数
var init = function() {
	//加载右侧信息
	var loadDatefromDB = function(operation,title,func,num) { 
		var temp_arr = new Array();
		$.ajax({
			url: "../../servlet/StatisticsTypeServlet",
			type: "POST",
			data:{
				operation: "classifystatistics",
				"func": func,
				"currentdate":$(".total_title").find(".total_active").html()
			},
			success:function(data) {
				config.log(data);
				if(num == 0) {
					var json = eval(data);
					for(var i=0; i<json.length; i++){
						temp_arr.push([json[i].name,json[i].value]);
					}
					$("#d_title").text(title);
					$("#data").text(temp_arr[0][1] + "%");
					$("#data_describe").text(temp_arr[0][0]);
					$("#d_info").html("<li><span>" + temp_arr[0][1] + "</span>个学校" + temp_arr[0][0] + "</li><li><span>" + temp_arr[1][1] + "</span>个学校" + temp_arr[1][0] + "</li>"); 
				}else{
					var json = eval(data);
					for(var i=0; i<json.length; i++){
						temp_arr.push([json[i].name,json[i].value,json[i].area_total,json[i].state_total,json[i].area_id]);
					}
					$("#a_title").text(title);
					var valuedata = 0;
					for(var i=0; i<temp_arr.length; i++){
						valuedata += temp_arr[i][1];
					}
					valuedata = temp_arr[0][1]/valuedata * 100; 
					$("#a_data").text(valuedata.toFixed(2) + "%");
					$html = "<table><tr><td>区域</td><td>试点学校</td><td>已提交数据</td></tr>";
					for(var i = 0; i < temp_arr.length; i++) {
						$html += "<tr><td><a href='#" + temp_arr[i][4] + "'>" + temp_arr[i][0] + "</a></td><td>"+ temp_arr[i][2] + "</td><td>" + temp_arr[i][3] + "</td></tr>"; 
					}
					$html += "</table>";
					$("#a_info").html($html);
				}
				      
	        },
	        error:function() {	        	
	            alert("操作失败！！");
	        }
		});
	};
	
	var initevent = function() { //初始化页面
		var arr = ["#tablePlaceforWhole","全市信息化数据提交情况","pie"];
		var brr = ["#tablePlaceforArea","各市区县信息化数据提交情况","column"];
		
		//loadDatefromDB("noreadJson",arr[2],"getDataSchool",0);  //
		//loadDatefromDB("noreadJson",brr[2],"sectionAreaData",1);  //
		
		formNewArr_func_1(arr[0],arr[1],arr[2]);   //获取饼状图所需数据，并进行数据重组
		formNewArr_func_1(arr[0],arr[1],arr[2]);   //获取饼状图所需数据，并进行数据重组
		formNewArr_func_2(brr[0],brr[1],brr[2]);	  //输出数据
	};
	
	//初始化饼状图
	initevent();
	
	//事件绑定
	bindevent();
};

init();		

