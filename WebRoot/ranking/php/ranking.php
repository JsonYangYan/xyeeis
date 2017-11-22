<?php
include("../../base/php/base.php");
include("../../base/php/func.php");
error_reporting(0);

/*
 * 获取数据
 */
function get_tb_info() {
	$query = "SELECT * FROM eeie_regulation_sectionassess,eeie_area WHERE eeie_regulation_sectionassess.area_id = eeie_area.id";
	mysql_query("SET NAMES UTF8");
	$result = mysql_query($query);
	$arr = array();
	$i = 0;
	while ($row = mysql_fetch_array($result)) {
		$arr[$i]['area'] = urlencode($row['area']);
		$index1 = $row['sectionassess1'];
		$index2 = $row['sectionassess2'];
		$index3 = $row['sectionassess3'];
		$index4 = $row['sectionassess4'];
		$index5 = $row['sectionassess5'];
		$total = $index1+$index2+$index3+$index4+$index5;
		$arr[$i]["data"] = array($index1*1.0,$index2*1.0,$index3*1.0,$index4*1.0,$index5*1.0,$total);
		$i++;
	}
	for($i = 0; $i < count($arr); $i++) {
		for($j = 0; $j < count($arr)- $i - 1; $j++) {
			if($arr[$j]['data'][5] < $arr[$j+1]['data'][5]) {
				$tmp = $arr[$j];
				$arr[$j] = $arr[$j+1];
				$arr[$j+1] = $tmp;
			}
		}
	}
	//var_dump($arr);
	$name = "for_rank";
	$filepath = "../json";
	outputJson($filepath,$arr,$name);
}
 
/*
 * 初始化
 */
// function init() {
	// $operation = $_REQUEST["operation"];
	// if($operation == "showtbinfo") {
		// get_tb_info();
	// }else if($operation == "histogram_data") {
		// histogramdata();
	// }		
// }
get_tb_info();
// init();
?>