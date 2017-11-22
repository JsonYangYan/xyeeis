/*
 * 加载饼状图
 */
var build_pieTable = function(divplace,title,data) {
	 $(divplace).highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
        },
        colors:['#7cb5ec', '#90ed7d', '#f7a35c', '#8085e9', '#f15c80', '#e4d354', '#8085e8', '#8d4653', '#91e8e1'],
        title: {
            text: title,
            style:{
            	fontSize: '18px',
	            fontWeight: 'bolder',
	            color: '#333'
            }
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
	        x: -10,
	        style:{
            	fontSize: '18px',
	            fontWeight: 'bolder',
	            color: '#333'
            }
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
	        y: 60,
	        layout: 'vertical'
	    },
	    series: data
	});
}

//堆积图
function getStackedColumnChart(divplace,title,tooltip,result,data1,data2,unit) {
	if(unit == undefined) {
		unit = '';
	}
	$(divplace).highcharts({
        chart: {
            type: 'column',
            height: '400',
            margin: [ 50, 50, 100, 80]
        },
        title: {
            text: title,
            style:{
            	fontSize: '18px',
	            fontWeight: 'bolder',
	            color: '#333'
            }
        },
        xAxis: {
            categories: result,
            labels: {
                rotation: -45,
                align: 'right',
                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif'
                }
            }
        },
        yAxis: {
            min: 0,
            title: {
               // text: tooltip
            	text: ''
            }
        },
        legend: {
	        align: 'right',
	        verticalAlign: 'top',
	        x: 21,
	        y: 70,
	        layout: 'vertical'
	    },
        
        tooltip: {
            pointFormat: '{series.name}: <b>{point.y:.2f}' + unit + '</b>',
        },
        plotOptions: {
            column: {
                stacking: 'percent'
            }
        },

        series: [{
        	name: '已提交',
        	data: data1,
        	dataLabels: {
                enabled: true,
                rotation: -90,
                color: '#FFFFFF',
                align: 'right',
                x: 4,
                y: 10,
                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif',
                    textShadow: '0 0 3px black'
                }
            }
        	
        },
        {
        	name: '未提交',
        	data: data2,
        	dataLabels: {
                enabled: true,
                rotation: -90,
                color: '#FFFFFF',
                align: 'right',
                x: 4,
                y: 10,
                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif',
                    textShadow: '0 0 3px black'
                }
            }
        }]
    });
}


/*
 * 加载总分排名的柱状图
 */
function getTotalRanking (divplace,title,result,data) {

	var myChart = echarts.init(document.getElementById(divplace));
    // 指定图表的配置项和数据
    option = {
		title: {
			x: 'center',
			text: title 
		},
		tooltip: {
			trigger: 'item'
		},
		calculable: true,
		grid: {
			borderWidth: 0,
			y: 80,
			y2: 60
		},
		xAxis: [
			{
				type: 'category',
				show: true,
				data: result,
				 //设置字体倾斜  
                axisLabel:{  
                    interval:0,  
                    rotate:45,//倾斜度 -90 至 90 默认为0  
                    margin:10,  
                    textStyle:{  
                        fontSize: '18px',
				        fontWeight: 'bolder',
				        color: '#333' 
                    }  
                }

			}
		],
		yAxis: [
			{
				type: 'value',
				show: true
			}
		],
		series: [
			{
				name: title,
				type: 'bar',
				itemStyle: {
					normal: {
						color: function(params) {
							// build a color map as your need.
							var colorList = [
								'#4572a7','#aa4643','#89a54e','#80699b','#3d96ae', '#db843d','#4572a7','#aa4643','#89a54e',
								'#80699b','#3d96ae', '#db843d','#4572a7','#aa4643','#89a54e','#80699b','#3d96ae','#db843d',
								'#4572a7','#aa4643','#89a54e','#80699b','#3d96ae','#db843d','#4572a7','#aa4643','#89a54e',
								'#80699b','#3d96ae','#db843d','#92a8cd'
							];
							return colorList[params.dataIndex]
						}
					}
				},
				data: data,
				markLine : {
					data : [{type : 'average', name: '平均值'}]
				}
			}
		]
	};
                

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
	
}



/*
 * 加载分类排名柱形图
 */
var getBlankChart = function(divplace,title,tooltip,result,data,unit) {
	if(unit == undefined) {
		unit = '';
	}
	$(divplace).highcharts({
        chart: {
            type: 'column',
            height: '400',
            margin: [ 50, 50, 100, 80]
        },
        title: {
            text: title,
            style:{
            	fontSize: '18px',
	            fontWeight: 'bolder',
	            color: '#333'
            }
        },
        xAxis: {
            categories: result,
            labels: {
                rotation: -45,
                align: 'right',
                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif'
                }
            }
        },
        yAxis: {
            min: 0,
            title: {
                text: ''
            }
        },
        legend: {
            enabled: false,
            layout: 'vertical'
        },
        tooltip: {
            pointFormat: tooltip + ': <b>{point.y:.2f}' + unit + '</b>',
        },
        series: [{
            name: '各区的平均值',
            data: data ,
            dataLabels: {
                enabled: true,
                rotation: -90,
                color: '#FFFFFF',
                align: 'right',
                x: 4,
                y: 10,
                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif',
                    textShadow: '0 0 3px black'
                }
            }
        }]
    });
};

var getMonthRanking = function(result,position_id){
	//放入数据到series中
	var series=[];
	    for(var i = 0;i<result['listmonth'].length;i++){
	        series.push({
	            name: result['listmonth'][i],
	            type: 'bar',
	            data: result["listscore"][i]
	        });
	    }

	var myChart = echarts.init(document.getElementById(position_id));
    // 指定图表的配置项和数据
    var option = {
			title : {
				text: '',
				subtext: ''
			},
			tooltip : {
				trigger: 'axis'
			},
			legend: {
				data:result['listmonth']
			},
			toolbox: {
				show : false,
				feature : {
					dataView : {show: true, readOnly: false},
					magicType : {show: true, type: ['line', 'bar']},
					restore : {show: true},
					saveAsImage : {show: true}
				}
			},
			calculable : true,
			xAxis : [
				{
					type : 'category',
					data : result["area"]
				}
			],
			yAxis : [
				{
					type : 'value'
				}
			],
			series : series
		};


    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
}

$.ajax({
	url:"../../servlet/MonthContrastServlet",
	type:"post",
	data:{
		"operation":"allmonthcontrast"
	},
	success:function(data){
		var result = JSON.parse(data);
		getMonthRanking(result[0],'month_ranking_center');
	},
	error:function(){
		
	}
});