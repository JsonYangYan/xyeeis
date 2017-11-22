var str = $(".total_title").find(".total_active").html();
var str_befor1 = getPreMonth(str);

$("#now_month_button").text(str);
$("#before_month_button").text(str_befor1);
$("#now_month_button").css("background","rgb(255, 195, 0)");
$("#now_month_button").click(function(){
	$("#now_month_button").css("background","rgb(255, 195, 0)");
	$("#before_month_button").css("background","rgb(80, 191, 234)");
	date_month(str)
})
$("#before_month_button").click(function(){
	$("#before_month_button").css("background","rgb(255, 195, 0)");
	$("#now_month_button").css("background","rgb(80, 191, 234)");
	date_month(str_befor1);
})
date_month(str);
function date_month(date_month){
	$.ajax({
		url: "../../../servlet/QxRankingServlet",
		type: "post",
		data: {
			"operation": "monthranking", 
			"date":date_month,
			"currentdate":$(".total_title").find(".total_active").html()
		},
		success: function(result) {
			var result = JSON.parse(result);
			var listJSON = {"list":[]};
			listJSON.list = result;		
			var temp_data = new Array();
			var temp_area = new Array();
			var temp_schoolName = new Array();
			var title = result[0].area+"各学校发展水平总得分";
			for (var i = 0; i < result.length; i++) {
				temp_schoolName.push(result[i].schoolName);
				temp_area.push(result[i].area);
				temp_data.push(Number(result[i].data[5]));
			}
			getMonthContrast("#month_contrast",title, temp_schoolName,temp_data,date_month);
		},
		error: function() {
			alert("失败");
		}
	});
}

function getMonthContrast(divplace ,title,temp_area, temp_data,date_month) {
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
	            crosshair: true,
	         
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
	            name:date_month,
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
