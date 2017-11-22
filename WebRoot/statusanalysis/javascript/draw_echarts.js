$("#gaoxin").click(function(ec){   
	 	show_table_data("高新区");		//用table显示被点击地区的数据	
	 	$("#area_name").html("<h2>高新区典型指标详情</h2>");

		drawTable("drawPieChart_01","高新区",134,"学校多媒体教室的形态有哪些");
		drawTable("drawPieChart_02","高新区",141,"学校建设并应用的数字化教学资源有哪些");
		drawTable("drawPieChart_03","高新区",142,"学校应用的数字化教学系统有哪些");
		drawTable("drawPieChart_04","高新区",146,"学校建设并应用的数字化教研资源有哪些");
		
});
$("#dongjin").click(function(ec){
	show_table_data("东津新区");
	$("#area_name").html("<h2>东津新区典型指标详情</h2>");
	drawTable("drawPieChart_01","东津新区",134,"学校多媒体教室的形态有哪些");
	drawTable("drawPieChart_02","东津新区",141,"学校建设并应用的数字化教学资源有哪些");
	drawTable("drawPieChart_03","东津新区",142,"学校应用的数字化教学系统有哪些");
	drawTable("drawPieChart_04","东津新区",146,"学校建设并应用的数字化教研资源有哪些");
	
});

$("#yuliang").click(function(ec){
	show_table_data("襄阳市直");
	$("#area_name").html("<h2>襄阳市直典型指标详情</h2>");
	drawTable("drawPieChart_01","襄阳市直",134,"学校多媒体教室的形态有哪些");
	drawTable("drawPieChart_02","襄阳市直",141,"学校建设并应用的数字化教学资源有哪些");
	drawTable("drawPieChart_03","襄阳市直",142,"学校应用的数字化教学系统有哪些");
	drawTable("drawPieChart_04","襄阳市直",146,"学校建设并应用的数字化教研资源有哪些");
	
});

function drawTable(DivTable,name,queId,text){
	document.getElementById("pie_01").style.display = "none";
	document.getElementById("pie_02").style.display = "none";
	document.getElementById("pie_03").style.display = "none";
	document.getElementById("pie_04").style.display = "none";
    document.getElementById("drawPieChart_01").style.display = "block";
    document.getElementById("drawPieChart_02").style.display = "block";
    document.getElementById("drawPieChart_03").style.display = "block";
    document.getElementById("drawPieChart_04").style.display = "block";
    require.config({
        paths:{ 
            echarts:'echarts',
			'echarts/chart/pie' : 'echarts-map'
        }
    });
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
    
		    require(
		        [
		            'echarts',
					'echarts/chart/pie'
		        ],
		        function (ec) {
				  //--- 饼图 ---
		            
					var myChart = ec.init(document.getElementById(DivTable));
		            myChart.setOption({
		                 title : {
				        text: text,
				        x:'center'		//主、副标题居中
				    },
				    tooltip : {
				        trigger: 'item',		//数值触发
				        formatter: "{a} <br/>{b} : {c} ({d}%)"
				    },
				    color: ['#7cb5ec', '#90ed7d', '#f7a35c', '#8085e9', '#f15c80', '#e4d354', '#8085e8', '#8d4653', '#91e8e1'],
				    toolbox: {
				        show : true,
				        feature : {
				            magicType : {
				                show: false, 
				                type: ['pie', 'funnel'],
				                option: {
				                    funnel: {
				                        x: '25%',
				                        width: '50%',
				                        funnelAlign: 'left',
				                        max: 1548
				                    }
				                }
				            },
				            saveAsImage : {show: false}
				        }
				    },
				    calculable : true,
				    series : [
				        {
				            name:'访问来源',
				            type:'pie',
				            radius : '55%',
				            center: ['50%', '60%'],
				            data:result,
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
				        }
				    ]
		            });
		        }
		    );	
		   },
		   error:function(){
			   alert("链接失败");
			   }
		   });
}