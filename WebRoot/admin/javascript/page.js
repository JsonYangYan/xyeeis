////    分页函数
////    int item_all_num 所有页面数据总条数
////	int page_item_num 每个页面要呈现的条数
////	string num_nav_box 页码导航框的ID
////	int max_nav_num 页面呈现的导航页码的个数
////	function getData 每个页面加载数据的
var paging = function(item_all_num,page_item_num,num_nav_box,max_nav_num) {
	//非法数字的处理
	page_item_num = parseInt(page_item_num);
	max_nav_num = parseInt(max_nav_num);
	if(page_item_num < 1) {
		page_item_num = 1;
	}
	if(max_nav_num < 1) {
		max_nav_num = 1;
	}
	
	//存放指定条数
	var start ;
	var pages_num; // 总页数
	var half_max_nav_num = Math.floor(max_nav_num/2); //折半导航页码个数
	var current_page_num ; // 当前页号
	
	// 为当前页刷新数据做准备 ，利用location的hash值
    var handleArr = window.location.hash; 
    if(handleArr){
    	handleArr = handleArr.split("#");
    	current_page_num = parseInt(handleArr[1]);
    }else{
    	current_page_num = 1;
    } 
       
	pages_num = Math.ceil(item_all_num/page_item_num);
	
	// 控制页码导航呈现
	var controlNumNav = function(num) {
		num = parseInt(num);
		window.location.hash = "#" + num; //记录当前页页码，供刷新页面使用
		start = page_item_num*(num - 1);
		getData(start,page_item_num);
		
		var num_html = "<ul id='num_nav'>";
		// 当总页数大于指定页数时进行的处理
		if(pages_num > max_nav_num) { 
			
			//前面有省略的情况
			if(num - half_max_nav_num > 1) {
				num_html += "<li id='prev' class='over'>上一页</li><li style='border:0'>...</li>";
				
				//后面有省略的情况
				if(num + half_max_nav_num < pages_num) {
					for(var i = num + half_max_nav_num , j = max_nav_num - 1; j >= 0 ; j--) {
						num_html += "<li class='num over' id='num_"+ (num + half_max_nav_num - j) +"' >" + (num + half_max_nav_num - j) + "</li>";
					}
					num_html += "<li style='border:0'>...</li>";
				}else {
					
					//后面没省略的情况
					for(var i = pages_num , j = max_nav_num - 1 ; j >= 0 ; j--) {
						num_html += "<li class='num over' id='num_"+ (pages_num - j) +"' >" + (pages_num - j) + "</li>";
					}
				}
			}else {
				
				//前面没省略的情况，后面有省略的情况
				num_html += "<li id='prev' class='over'>上一页</li>";
				for(var i = 1; i <= max_nav_num; i++) {
					num_html += "<li class='num over' id='num_"+ i +"' >" + i + "</li>";
				}
				num_html += "<li style='border:0'>...</li>";
			}
			
		}else {
			
			//前后都没省略的情况
			num_html += "<li id='prev' class='over'>上一页</li>";
			for(var i = 1; i <= pages_num; i++) {
				num_html += "<li class='num over' id='num_"+ i +"' >" + i + "</li>";
			}
		}
		num_html += "<li id='next' class='over'>下一页</li><li style='border:0'>共"+ pages_num +"页</li></ul>";
		$("#" + num_nav_box).html(num_html);
		$("#num_nav li#num_" + num).css({"color":"white","background-color":"#0E90D2"});
	
	};
	//点击页码触发的事件
	 $("#num_nav li.num").live("click",function() {
	    current_page_num = $(this).text();
	    current_page_num = parseInt(current_page_num);
		controlNumNav(current_page_num);
	});
	
	//点击上一页触发的事件
	 $("#" + num_nav_box + " #num_nav li#prev").live("click",function() {
		current_page_num = parseInt(current_page_num);
		if(current_page_num > 1 ) {
			current_page_num -= 1;
			controlNumNav(current_page_num);
		}
	});
	
	//点击下一页触发的事件 
		$("#" + num_nav_box + " #num_nav li#next").live("click",function() {
		current_page_num = parseInt(current_page_num);
		if(current_page_num < pages_num ) {
			current_page_num += 1;
			controlNumNav(current_page_num);
		}
	});
	
	//初始化当前页码为1的时候，页面导航初始化
	controlNumNav(current_page_num);
	
};
