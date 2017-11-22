var formNewArr_func_3 = function(jsonname,divplace,title,type) {
	$.getJSON("../json/"+jsonname,function(result) {
		var colors = new Array(
				'#4572a7','#aa4643','#89a54e','#80699b','#3d96ae', '#db843d','#4572a7','#aa4643','#89a54e',
				'#80699b','#3d96ae', '#db843d','#4572a7','#aa4643','#89a54e','#80699b','#3d96ae','#db843d',
			    '#4572a7','#aa4643','#89a54e','#80699b','#3d96ae','#db843d','#4572a7','#aa4643','#89a54e',
				'#80699b','#3d96ae','#db843d','#92a8cd'
          );
		var categories = new Array();
		var datacol = new Array();

		 var name = 'Option brands';
		//设定颜色---柱状图
		for (var i = 0; i < result.length; i++) {

			//分类
			categories.push(result[i].name);
			//比例
			var obj = {
				y : "",
				color : ""
			};
			obj.y = result[i].value;
			obj.color = colors[i];

			datacol.push(obj);
		}

		var chart;
		
		//柱状图
		$(document).ready( function() {
			chart = new Highcharts.Chart({
				chart : {
					renderTo : divplace,	//html页面中初始化画布
					type : 'column',
					inverted: true //调换x，y轴
				},
				title : {
					text : title
				},
				subtitle : {
					text : ''
				},
				xAxis : {
					categories : categories,
					labels: { 
				    //rotation: -45,
                    align: 'right',
                    style: {
                    	color:'#000000',
                        fontSize: '16px',
                        //fontWeight : 'bold'
                       fontFamily: '微软雅黑'
                    }
                }
				},
				yAxis : {
					opposite: true,//标尺上下位置
					title : {
						//text : '百分比/%'
						text : ''
					}
				},
				plotOptions : {
					column : {
						cursor : 'pointer',
						point : {},
						dataLabels : {
							enabled : true,
							color : colors[0],
							style : {
								fontWeight : 'bold'
							},
							formatter : function() {
								return this.y + '%';
							}
						}
					}
				},
				tooltip : {
					formatter : function() {
						var point = this.point, s = this.x + ':<b>' + this.y + '%</b><br/>';
						if (point.drilldown) {
							s += 'Click to view ' + point.category + ' versions';
						}
						return s;
					}
				},
				series : [{
					name : name,
					data : datacol,
					color : 'white'
				}],
				exporting : {
					enabled : false
				}
			});
		});
	});
}

/*
 * 加载饼状图
 */
var build_pieTable = function(divplace,title,data) {
	//var div = eval("#".divplace);
	 $(divplace).highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
        },
        title: {
            text: title
        },
        tooltip: {
    	    pointFormat: '{series.name}: <b>{point.percentage:.2f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    color: '#000000',
                    connectorColor: '#000000',
                    format: '<b>{point.name}</b>: {point.percentage:.1f} %'
                }
            }
        },
        series: [{
            type: 'pie',
            name: '所占比例',
            data: data
        }]
    });
}

//获取饼状图所需数据，并进行数据重组  形参对应的值       "schoolInit","#tablePlaceforWhole","全市信息化数据提交情况","pie"
//                                "sectionareaInitData","#tablePlaceforArea","各市区县信息化数据提交情况","column"
var formNewArr_func_1 = function(jsonname,divplace,title,type) { 
	var temp_array =  new Array();
	$.getJSON("../json/"+jsonname,function(data) {
		if( type == "pie" ){
			for(var i=0;i<data.length;i++){
				temp_array.push([data[i].name,data[i].value]);   //将JASON格式的文本存进去
			}
			    build_pieTable(divplace,title,temp_array);  //生成饼状图   将JASON格式的文本
		}
		else{
			   var temp_data = new Array();
			   var temp_area = new Array();
			   //var temp_avg = new Array();
			   var average
			   for (var i = 0; i < data.length; i++) {
				   temp_area.push(data[i].name);
				   temp_data.push(data[i].value);
				   //temp_avg.push(data[i].avg);
				   average = data[0].avg;
			   }			   
			   getBlankChart(divplace, '',"终端使用量",temp_area,temp_data,'%');   //生成执折线图	
			   //getBlankChart(divplace, '',"终端使用量",temp_data,temp_area,'%');   //生成执折线图	
				
				if(average == "16.23"){
					$("#teacher_terminal").html( title + '&nbsp;&nbsp;平均值为：' + average );	
					$("#teacher_terminal").css("display", "block");
				}else{
					$("#student_terminal").html( title + '&nbsp;&nbsp;平均值为：' + average );	
					$("#student_terminal").css("display", "block");
				}				
			}
	});	
}


//本校、本地区和襄阳市的基本情况对比表
var data_table = function() {
	var str=location.href; //取得整个地址栏
//	var num=str.indexOf("=") 
//	str=decodeURI(str.substr(num+1)); //取得学校的名称
	var schoolName = decodeURI(str.split("=")[1].split("&")[0]);
	var date = window.location.href.split('&')[1].split("=")[1];
	$.ajax({
		url: "/xyeeis/servlet/SchooldetailServlet",
		type: "post",
		data: {
			"operation":"schooldetail",
			"schoolName": schoolName,
			"currentdate":date
		},
		success: function(json) {
			
			var data = JSON.parse(json);
			
			var basicInfoJson = {"infoData":[]};
			basicInfoJson.infoData = data;
			//alert(infoData.1);
			$("#basicInfo_list").html(TrimPath.processDOMTemplate("list_template", basicInfoJson));
		},
		error: function() {
			alert("失败");
		}
	});	
	
	$(".downLoad").click(function(){
		var str=window.location.href; //取得整个地址栏
		var schoolName = decodeURI(str.split("=")[1].split("&")[0]);
		var date = str.split('&')[1].split("=")[1];  
		location.href = "/xyeeis/servlet/ExportExcelServlet?schoolName="+schoolName+"&currentdate="+date;
	});
	
	
}


/*
 * 加载雷达图
 */
var buildNettable = function(divplace,title,category,data) {
	$(divplace).highcharts({
	    chart: {
	        polar: true,
	        backgroundColor: "#fff",
	        type: 'line'
	    },
	    title: {
	        text: title,
	        x: -10
	    },
	    pane: {
	    	size: '80%'
	    },
	    xAxis: {
	        categories: category,
	        tickmarkPlacement: 'on',
	        lineWidth: 0
	    },
	    yAxis: {
	        gridLineInterpolation: 'polygon',
	        lineWidth: 0,
	        min: 0
	    },
	    tooltip: {
	    	shared: true,
	        pointFormat: '<span style="color:{series.color}">{series.name}: <b>{point.y:,.2f}</b><br/>'
	    },
	    
	    legend: {
	        align: 'right',
	        verticalAlign: 'top',
	        y: 70,
	        layout: 'vertical'
	    },
	    series: data
	});
}

//本校、本地区和襄阳市的指标对比----雷达图
var fPart = function() {
	var temp_index = new Array();
	
	var str=window.location.href; //取得整个地址栏
	var schoolName = decodeURI(str.split("=")[1].split("&")[0]);
	//根据学校id查询本校、本地区的数据，，，此处用json代替查询过程
	//根据学校id查询学校名称、该地区的名称及5个方面的发展指数	
	$.getJSON("../json/index_arr.json",function(data) {
		for(var i=0; i<data.length; i++) {
			temp_index.push([data[i].index]);
		}
	//传输请求
		var date = window.location.href.split('&')[1].split("=")[1]; 
	$.ajax({
		url: "/xyeeis/servlet/schoolInforServlet",
		type: "post",
		data: {
			"schoolName": schoolName,
			"currentdate":date
		},
		success: function(json) {
			var data = JSON.parse(json);
			var strSchoolName = data[0].name;
			var strAreaName = data[1].name;
			buildNettable("#left_side",""+strSchoolName+"、"+strAreaName+"和襄阳市"+date+"月份的平均水平对比情况",temp_index,data);
		},
		error: function() {
			alert("失败");
		}
	});		
	});
};



var init = function() {
	fPart();		//本校、本地区和襄阳市的指标对比----雷达图
	data_table();
	
};

init();