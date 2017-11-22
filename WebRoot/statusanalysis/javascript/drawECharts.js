// 路径配置
require.config({
    paths: {
        echarts: '../lib/echarts/build/dist'
    }
});

// 使用
require(
    [
        'echarts',
        'echarts/chart/map', // 使用柱状图就加载bar模块，按需加载
        'echarts/chart/pie'
    ],
    　		drawEcharts
);

function drawEcharts(ec){
　　    drawMap(ec);　
　　    　
	drawPieCharts(ec,"digital_resources","pie_01","学校多媒体教室的形态有哪些",134);
	drawPieCharts(ec,"practice_step","pie_02","学校建设并应用的数字化教学资源有哪些",141);
	drawPieCharts(ec,"management_system","pie_03","学校应用的数字化教学系统有哪些",142);
	drawPieCharts(ec,"information_training","pie_04","学校建设并应用的数字化教研资源有哪些",146);
}
var drawMap = function(ec) {
    	
    // 基于准备好的dom，初始化echarts图表
    var myMapChart = ec.init(document.getElementById('main'));
    
    var cityMap = {                    
         "襄阳市": "420600"
                 };
  
     var curIndx = 0;
     var mapType = [];
     var mapGeoData = require('echarts/util/mapData/params');
     for (var city in cityMap) {
         mapType.push(city);
         
         // 自定义扩展图表类型
         mapGeoData.params[city] = {
             getGeoJson: (function (c) {
                 var geoJsonName = cityMap[c];
                 return function (callback) {
                     $.getJSON('../lib/echarts/geoJson/china-main-city/' + geoJsonName + '.json', callback);
                             }
                         })(city)
                     }
                 }
	     var ecConfig = require('echarts/config');
	     var zrEvent = require('zrender/tool/event');
	                        
	     option = {
	         title: {
	         },
	         tooltip : {
	             trigger: 'item',
	             formatter: '{b}<br/>{c}'
	         },
	         dataRange: {
				min: 54,
				max: 69,
				color:['#308BAE','#FFDF85','#F2F2B0'],
				text:['高','低'],           // 文本，默认为数值文本
				calculable : true
			},
	         series : [
	             {
	                 name: '襄阳市',
	                 type: 'map',
	                 mapType: '襄阳市',
	                 selectedMode : 'single',
	                 itemStyle:{	//主题样式
	                     normal:{
	                     	borderColor: '#fff',
	                     	borderWIdth: 1,
	                     	// areaStyle:{
	                     		// color: '#ccc'
	                     	// },
	                     	label:{
	                     		show:true,
	                     		textStyle:{
	                     			color: 'rgba(139,69,19,1)'
	                     		}
	                     		}                               	
	                     	},
	                     emphasis:{	//选中样式
	                     	//label:{show:true},                                	
	                     	 borderColor: 'rgba(0,0,0,0)',
			                 borderWidth: 1,
			                 areaStyle: {
			                     color: '#CDDB3E'
			                 },
			                 label: {
			                     show: true,
			                     textStyle: {
			                         color: 'rgba(139,69,19,1)'
			                     }
			                 } }
	                 },
	                 data:[
	                 	{name: '老河口市',value: '63.74'},
		                {name: '襄州区',value: '55.89'},
		                {name: '枣阳市',value: '68.48'},
		                {name: '宜城市',value: '63.06'},
		                {name: '保康县',value: '64.41'},
		                {name: '樊城区',value: '54.71'},
		                {name: '谷城县',value: '68.2'},
		                {name: '襄城区',value: '62.28'},
		                {name: '南漳县',value: '59.76'}
	
	                 ]
	             }
	         ]
	     };
          
		myMapChart.on(ecConfig.EVENT.CLICK, eMapConsole);	//添加点击事件
		
		function eMapConsole (param){	//点击事件
		    show_table_data(param.name);		//用table显示被点击地区的数据	
		    $("#area_name").html("<h2>"+param.name + "典型指标详情</h2>");

		    show_pie_data(ec,"drawPieChart_01","学校多媒体教室的形态有哪些",param.name,134);
			show_pie_data(ec,"drawPieChart_02","学校建设并应用的数字化教学资源有哪些",param.name,141);
			show_pie_data(ec,"drawPieChart_03","学校应用的数字化教学系统有哪些",param.name,142);
			show_pie_data(ec,"drawPieChart_04","学校建设并应用的数字化教研资源有哪些",param.name,146);
			
	    };
	    myMapChart.setOption(option);		//为echarts对象加载数据    
	 };       

	 
	var show_table_data = function(name) {
		// console.log(param);
		var allData = {
	    	"发展指数" :["基础设施","教学信息化应用","教研信息化应用","管理与服务信息化应用","信息化保障"],	               	
	    	"襄阳市" : [41.32,38.56,50.43,50,40.25]
		};
		
		$.ajax({
			url:"../../servlet/StatusanalysisServlet",
			data:{
				operation:"table_data",
				"currentdate":$(".total_title").find(".total_active").html()
			},
			type:"post",
			dataType:"text",
			success:function(result){
				var result = JSON.parse(result);
				var selected = name;
				var $html = "<tr><th>地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;区</th><th>发展指数</th><th>本地区状况</th><th>襄阳市平均水平</th></tr><tr><td rowspan = '5'>" + selected + "" + "</td>";
		       	var color = "";	               	              		               			               	
		       	
				for (var k = 0; k < result.length; k++) {
					if(result[k].area == selected){
						for(var i = 0; i < 5; i++) {
		            	color = "red";
		            	if(result[k].data[i] > allData["襄阳市"][i]) {
		            		color = "green";
		            	}
		            	if(i == 0) {
		            		$html += "<td>"+ allData["发展指数"][i] + "</td><td style='color:" + color + ";'>" + result[k].data[i] + "</td><td>" + allData["襄阳市"][i] + "</td></tr>";
		            		continue;
		            	}
		            	$html += "<tr><td>"+ allData["发展指数"][i] + "</td><td style='color:" + color + ";'>" + result[k].data[i] + "</td><td>" + allData["襄阳市"][i] + "</td></tr>";
		            	}
					}						
		       }
		       document.getElementById("init_tab").style.display = "none";
		       document.getElementById("sub_tb").innerHTML = $html;
		       document.getElementById("sub_tb").style.display = "table";
				
			},
			error:function(){
				alert("链接失败");
			}
		});
		
	};
	
	var show_pie_data = function(ec,DivPlace,title,name,queId) {
		document.getElementById("pie_01").style.display = "none";
		document.getElementById("pie_02").style.display = "none";
		document.getElementById("pie_03").style.display = "none";
		document.getElementById("pie_04").style.display = "none";
	    document.getElementById("drawPieChart_01").style.display = "block";
	    document.getElementById("drawPieChart_02").style.display = "block";
	    document.getElementById("drawPieChart_03").style.display = "block";
	    document.getElementById("drawPieChart_04").style.display = "block";
	  
	   $.ajax({
		   type:"post",
		   url:"../../servlet/StatusanalysisServlet",
		   data:{
			   operation:"allasessment",
			   name:name,
			   queId:queId,
			   "currentdate":$(".total_title").find(".total_active").html()
			   },
		   dataType:"text",
		   success:function(result){
			   var result = JSON.parse(result);
			   var myPieChart = ec.init(document.getElementById(DivPlace));	// 基于准备好的dom，初始化echarts实例
			   
			   	// 指定图表的配置项和数据
			    option = {
				    title : {
				        text: title,
				        x:'center'		//主、副标题居中
				    },
				    tooltip : {
				        trigger: 'item',		//数值触发
				        formatter: "{a} <br/>{b} : {c} ({d}%)"
				    },
				    color: ['#7cb5ec', '#90ed7d', '#f7a35c', '#8085e9', '#f15c80', '#e4d354', '#8085e8', '#8d4653', '#91e8e1'],
				    calculable : false,
				    animation : true,
				    cursor: 'pointer',
				    toolbox: {
				        show : true,
				        transitionDuration:0.4,
				        feature : {
				            //saveAsImage : {show: true}
				        }
				    },
				    series : [
				        {
				            name:'发展指数',
				            type:'pie',
				            radius : '55%',
				            center: ['50%', '60%'],
				            data: result,
				            itemStyle: {		//图形样式
				            	normal: {
				            		label: {
				            			show: true,
				            			formatter: '{b} : {d}%' ,
				            			position: 'outer',
				            			textStyle: {
				            				color: '#000000',
				            				fontsize: 12,
				            				fontWeight: 'bold'
				            			}
				            		},
				            		labelLine: {
				            			show: true
				            		}
				            	}
				            }
				        }]
					};
					// 使用刚指定的配置项和数据显示图表。
			    	myPieChart.setOption(option);
				},
		   error:function(){
			   alert("链接失败");
		   }
	   });
	};
	
	//页面初始化
	var drawPieCharts = function(ec,jsonname,DivPlace,title,queId) {
	
		$.ajax({
			   type:"post",
			   url:"../../servlet/StatusanalysisServlet",
			   data:{
				   operation:"allasessment",
				   name:"樊城区",
				   queId:queId,
				   "currentdate":$(".total_title").find(".total_active").html()
				},
			   dataType:"text",
			   success:function(result){
				  var result = JSON.parse(result);
				   	var myPieChart = ec.init(document.getElementById(DivPlace));	// 基于准备好的dom，初始化echarts实例
				   
				   	// 指定图表的配置项和数据
				    option = {
					    title : {
					        text: title,
					        textStyle: {
					            fontSize: 18,
					            fontWeight: 'bolder',
					            color: '#333'          // 主标题文字颜色
					        },
					        x:'center'		//主、副标题居中
					    },
					    tooltip : {
					        trigger: 'item',		//数值触发
					        formatter: "{a} <br/>{b} : {c} ({d}%)"
					    },
					    color: ['#7cb5ec', '#90ed7d', '#f7a35c', '#8085e9', '#f15c80', '#e4d354', '#8085e8', '#8d4653', '#91e8e1'],
					    calculable : false,
					    animation : true,
					    cursor: 'pointer',
					    toolbox: {
					        show : true,
					        transitionDuration:0.4,
					        feature : {
					            //saveAsImage : {show: true}
					        }
					    },
					    series : [
					        {
					            name:'发展指数',
					            type:'pie',
					            radius : '55%',
					            center: ['50%', '60%'],
					            data: result,
					            itemStyle: {		//图形样式
					            	normal: {
					            		label: {
					            			show: true,
					            			formatter: '{b} : {d}%' ,
					            			position: 'outer',
					            			textStyle: {
					            				color: '#000000',
					            				fontsize: 12,
					            				fontWeight: 'bold'
					            			}
					            		},
					            		labelLine: {
					            			show: true
					            		}
					            	}
					            }
					        }]
					};
					// 使用刚指定的配置项和数据显示图表。
			    	myPieChart.setOption(option);
				},
				error:function(){
					alert("链接失败");   
			}
		}
		
		);    
};
