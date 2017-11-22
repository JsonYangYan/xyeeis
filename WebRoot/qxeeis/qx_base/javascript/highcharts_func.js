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
            text: title
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
                text: ""
            }
        },
        legend: {
            enabled: false,
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
 * 加载柱形图
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
                rotation: -30,
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
                //text: tooltip
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

/*
 * 加载横向柱形图
 */
var getBlankCrossChart = function(divplace,title,tooltip,result,data,unit) {
	
	if(unit == undefined) {
		unit = '';
	}
	
	$(divplace).highcharts({
        chart: {
            type: 'column',
            height: '400',
            inverted: true, //调换x，y轴
            // margin: [ 50, 50, 100, 80]
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
                // rotation: -45,
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
                text: tooltip
            }
        },
        legend: {
            enabled: false,
            layout: 'vertical'
        },
        
        plotOptions : {
			column : {
				cursor : 'pointer',
				point : {},
				colorByPoint: true,
				dataLabels : {
					enabled : true,
					colors : ['#5E8BC0', '#C36F5C', '#A2BE67', '#9982B4', '#56AFC7', '#F49D56', '#5E8BC0'],
					style : {
						fontWeight : 'bold'
					},
					formatter : function() {
						return this.y + '%';
					}
				}
			}
		},
        
        
        tooltip: {
            pointFormat: tooltip + ': <b>{point.y:.2f}' + unit + '</b>',
        },
        series: [{
            name: '各区的平均值',
            data: data ,          
            dataLabels: {
                enabled: true,
                // rotation: -90,
                color: '#FFFFFF',
                align: 'right',
                x: 4,
                y: 10,
                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif',
                    textShadow: '0 0 3px black'
                }
            },
            //legend.enabled = false
            showInLegend: false
        }]
    });
};


var getBlankChartCompare = function(divplace,title,result,data) {
	$(divplace).highcharts({
        chart: {
            type: 'column'
        },
        title: {
            text: '襄阳市各地区2014年与2015年绩效总得分对比'
        },
        subtitle: {
            text: ''
        },
        xAxis: {
            categories: result
        },
        yAxis: {
            min: 0,
            title: {
                text: '绩效总得分'
            }
        },
        tooltip: {
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                '<td style="padding:0"><b>{point.y:.1f} </b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        plotOptions: {
            column: {
                pointPadding: 0.2,
                borderWidth: 0
            }
        },
        series: data
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
                    rotate:20,//倾斜度 -90 至 90 默认为0  
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