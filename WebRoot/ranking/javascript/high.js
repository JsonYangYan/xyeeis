
var title = "襄阳市各区县发展水平总得分";
var tooltip = "绩效总得分";
var show_total_rank = function(){
	$.ajax({
		url: "../../servlet/RankingServlet",
		type: "post",
		data: {
			"operation": "ranking", 
			"currentdate":$(".total_title").find(".total_active").html()
		},
		success: function(result) {
			var result = JSON.parse(result);
			var listJSON = {"list":[]};
			listJSON.list = result;		
			var temp_data = new Array();
			var temp_area = new Array();
			var temp_data_before = new Array();
			var temp_area_before = new Array();

			for (var i = 0; i < result[0].length; i++) {
				temp_area.push(result[0][i].area);
				if(result[0][i].data.length>0) {
					temp_data.push(Number(result[0][i].data[5]));
				}else{
					temp_data.push(0);
				}
				temp_area_before.push(result[1][i].area);
				if(result[1][i].data.length>0) {
					temp_data_before.push(Number(result[1][i].data[5]));
				}else {
					temp_data_before.push(0);
				}
				
			}
			var str = $(".total_title").find(".total_active").html();
			var str_befor1 = getPreMonth(str);
			getMonthContrast("#month_contrast",title,temp_area,temp_data,str,str_befor1,temp_area_before,temp_data_before);
		},
		error: function() {
			alert("失败");
		}
	});
}

//show_total_rank();

function getMonthContrast(divplace ,title,temp_area, temp_data,now_date,now_before,temp_area_before,temp_data_before) {
	$(function () {
	    $(divplace).highcharts({
	        chart: {
	            type: 'column'
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
	            categories: temp_area,
	            crosshair: true
	        },
	        yAxis: {
	            min: 0,
	            title:{
	            	text:'  '
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
	                pointPadding: 0.1,
	                borderWidth: 1
	            }
	        },
	        series: [{
	            name: now_before,
	            data: temp_data_before,
	            color: '#333',
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
	        }, {
	            name: now_date,
	            data: temp_data,
	            color:"#7cb5ec",
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
	});
}
