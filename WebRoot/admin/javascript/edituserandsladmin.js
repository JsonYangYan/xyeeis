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
				var sch_name = $("input[name='query_sch_name']").val();
				$.ajax({
					url : "../../servlet/Admin",
					type : "POST",
					data : {
						"operation" : "edit",
						
						"role" : role,
						"area_id" : area_id,
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

				$("#conteiner").html(TrimPath.processDOMTemplate("option_template",options));
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
	edit();
}
init();
