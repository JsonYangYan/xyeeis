<?php
session_start();
include("../../base/php/db_conn.php");

/*
 *加载数据 
 */
function loadList() {
	$arr = array();

	//获取一级指标
	$query_rank1 = "SELECT DISTINCT rank1,weight_1,section_id FROM eeie_regulation";
	mysql_query("SET NAMES UTF8");
	$result_rank1 = mysql_query($query_rank1);
	$i = 0;
	$arr_rank1 = array();
	while($row = mysql_fetch_array($result_rank1)) {
		$arr['tb'][$i]['section_id'] = urlencode($row['section_id']);
		$arr['tb'][$i]['weight_1'] = urlencode($row['weight_1']);
		//$arr['tb'][$i]['rank1'] = urlencode($row['rank1']."<br/> <span style='color:#808285'>权重：".$row['weight_1']."</span>");
		$arr['tb'][$i]['rank1'] = urlencode($row['rank1']);
		$arr_rank1[$i] = $row['rank1'];
		$i++;
	}

	//获取二级指标
	for($i = 0; $i < count($arr_rank1); $i++) {
		$query_rank2 = "SELECT id,rank2,weight_2 FROM eeie_regulation WHERE rank1 = '$arr_rank1[$i]'";
		mysql_query("SET NAMES UTF8");
		$result_rank2 = mysql_query($query_rank2);
		$j = 0;
		while($row = mysql_fetch_array($result_rank2)) {
			$arr['tb'][$i]['reg_tb'][$j]['id'] = urlencode($row['id']);
			//$arr['tb'][$i]['reg_tb'][$j]['rank2'] = urlencode($row['rank2']."<br/><span style='color:#808285'>权重：".$row['weight_2']."</span>");
			$arr['tb'][$i]['reg_tb'][$j]['rank2'] = urlencode($row['rank2']);
			$arr['tb'][$i]['reg_tb'][$j]['weight_2'] = urlencode($row['weight_2']);
			$rank2 = $row['rank2'];

			//获取三级指标
			$query_detail ="SELECT b.id AS detail_id, detail, weight_3, up_limit, target FROM eeie_regulation a, eeie_regulation_detail b WHERE a.id = b.regulation_id AND a.rank2 ='$rank2'";
			mysql_query("SET NAMES UTF8");
			$result_detail = mysql_query($query_detail);
			$t = 0;
			while($row = mysql_fetch_array($result_detail)) {
				$arr['tb'][$i]['reg_tb'][$j]['detail_tb'][$t]['detail_id'] = urlencode($row['detail_id']);
				$arr['tb'][$i]['reg_tb'][$j]['detail_tb'][$t]['up_limit'] = urlencode($row['up_limit']);
				$arr['tb'][$i]['reg_tb'][$j]['detail_tb'][$t]['weight_3'] = urlencode($row['weight_3']);
				$arr['tb'][$i]['reg_tb'][$j]['detail_tb'][$t]['target'] = urlencode($row['target']);
				$arr['tb'][$i]['reg_tb'][$j]['detail_tb'][$t]['detail'] = urlencode($row['detail']);
				$t++;
			}
			$j++;
		}
	}

	$str = json_encode($arr);
	echo urldecode($str);
}

/*
 * 初始化函数
 */
function init() {
	$operation = $_POST['operation'];
	if($operation == "list") {
		loadList();
	}
}

init();
?>