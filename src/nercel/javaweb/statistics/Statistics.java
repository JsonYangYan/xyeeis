package nercel.javaweb.statistics;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Statistics {
	/**
	 * 整体统计，已经填报教学点所在百分比
	 * 
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IOException 
	 */
	public ArrayList getWholeStatistics(String currentTime) throws SQLException,ClassNotFoundException, IOException {
		DbStatistics dbstistics = new DbStatistics();
		dbstistics.openConnection();
		double totalsch_num = 0;	//襄阳市全部学校数量
		double subsch_num = 0;		//襄阳市已提交的学校数量
		ArrayList list = new ArrayList();
		totalsch_num = dbstistics.gettotalSchoolnum();
		subsch_num = dbstistics.getsubSchoolnum(currentTime);
		float submitschpercent = 0;
		float unsubmitschpercent = 0;
		submitschpercent = Float.parseFloat(new java.text.DecimalFormat("#.##")
				.format(subsch_num / totalsch_num * 100));
		unsubmitschpercent = Float.parseFloat(new java.text.DecimalFormat(
				"#.##").format(100 - submitschpercent));
		
		HashMap map = new HashMap();
		map.put("name", "已提交");
		map.put("value", submitschpercent);
		map.put("totalnum", totalsch_num);
		map.put("subnum", subsch_num);
		
		HashMap map_1 = new HashMap();
		map_1.put("name", "未提交");
		map_1.put("value", unsubmitschpercent);
		map_1.put("totalnum", totalsch_num);
		map_1.put("subnum", subsch_num);
		list.add(map);
		list.add(map_1);
		dbstistics.closeConnection();
		return list;
	}
	
	/**
	 * [{"area_id":"20","area_total":"2","name":"老河口市","state_total":"1","value"
	 * :41}] 分类评估，得到上行数据格式
	 * 
	 * @param schoolarea
	 * @return
	 * @throws SQLException
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public Map<String, Object> getClassifyStatistics(String areaName,
			String currentTime) throws SQLException, ClassNotFoundException, IOException {
		DbStatistics dbstistics = new DbStatistics();
		dbstistics.openConnection();
		double subnum = 0;
		double totalnum = 0;
		int AreanId = dbstistics.getAreaId(areaName);
		HashMap map = new HashMap();
		//区县已提交学校数量
		subnum = dbstistics.getAreansubSchoolnum(areaName, currentTime);
		//区县的全部学校数量
		totalnum = dbstistics.getAreantotalSchoolnum(AreanId);
		map.put("area_total", totalnum);
		map.put("name", areaName);
		map.put("area_id", AreanId);
		map.put("state_total", subnum);
		if (totalnum == 0) {
			map.put("value", Float
					.parseFloat(new java.text.DecimalFormat("#.##")
							.format(0)));
		} else {
			map.put("value", Float
					.parseFloat(new java.text.DecimalFormat("#.##")
							.format(subnum / totalnum
									* 100)));
		}
		
		return map;
	}
	
	/**
	 * 分类统计,循环各区域，调用getClassifyStatistics方法，插入到list中
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException 
	 */
	public ArrayList classifyStatistics(String currentTime) throws ClassNotFoundException,
			SQLException, IOException {
		DbStatistics dbstistics = new DbStatistics();
		dbstistics.openConnection();
		ArrayList listArean = new ArrayList();
		ArrayList list = new ArrayList();
		listArean = dbstistics.getArean();
		for(int i=0;i<listArean.size();i++){
			if(!listArean.get(i).toString().equals("襄阳市")){
				Map<String, Object> map = new HashMap();
				map = getClassifyStatistics(listArean.get(i).toString(), currentTime);
				list.add(map);
			}
		}
		dbstistics.closeConnection();
		return list;
	}
	
	/**
	 * 统计各个区县的填报与未填报学校名单
	 */
	public ArrayList getAreanSchool(int areaId, String time) throws Exception{
		DbStatistics dbstistics = new DbStatistics();
		dbstistics.openConnection();
		ArrayList list = new ArrayList();
		list = dbstistics.getSchool(areaId, time);
		dbstistics.closeConnection();
		return list;
	}
	
}
