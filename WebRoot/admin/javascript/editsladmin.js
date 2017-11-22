var edit = function() {
	// 用于存储从后台获取的数据,所有数据
	var questionJSON = {
		question : []
	};

	var bindEvent = function() {

		$("#conteiner").on("click", "#confirm_up", function() {

			if (confirm("您确定修改完毕吗？")) {
				var username = $("input[name='query_username']").val();
				var role = $("select[name='query_role']").val();
				var area_id = $("input[name='query_area_id']").val();
				var newareaId;
				if(area_id=="襄城区"){
					newareaId = 1;
				}else if(area_id=="樊城区"){
					newareaId = 2;
				}else if(area_id=="襄州区"){
					newareaId = 3;
				}else if(area_id=="高新区"){
					newareaId = 4;
				}else if(area_id=="东津新区"){
					newareaId = 5;
				}else if(area_id=="枣阳市"){
					newareaId = 7;
				}else if(area_id=="宜城市"){
					newareaId = 8;
				}else if(area_id=="老河口市"){
					newareaId = 9;
				}else if(area_id=="南漳县"){
					newareaId = 10;
				}else if(area_id=="保康县"){
					newareaId = 11;
				}else if(area_id=="谷城县"){
					newareaId = 12;
				}else{
					newareaId = 13;
				}
				var sch_name = $("input[name='query_sch_name']").val();
				$.ajax({
					url : "../../servlet/Admin",
					type : "POST",
					data : {
						"operation" : "edit",
						
						"role" : role,
						"area_id" : newareaId,
						"sch_name" : sch_name,
						id : $.query.get("userid")
					},
					success : function(data) {
						
						window.location.href = "admin.html";
	
					},
					error : function() {
						alert("连接数据库失败！");
					}
				});
			}
		});

	};

	// 查询获得一条编辑数据
	var initData = function() {
		$.ajax({
			url : "../../servlet/Admin",
			type : "POST",
			data : {
				"operation" : "edit_display",
				id : $.query.get("userid")
			},
			success : function(data) {
				options = jQuery.parseJSON(data);

				$(".cont").html(TrimPath.processDOMTemplate("option_template",options));
				$(".option_info").eq(2).remove();
			},
			error : function() {
				alert("获取数据连接数据库失败!");
			}
		});
	};

	initData();
	bindEvent();
};
var init = function() {
	$("#footer").load("../base/html/footer.html");
	edit();
	
}
init();
