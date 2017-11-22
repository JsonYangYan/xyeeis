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
                text: tooltip
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
