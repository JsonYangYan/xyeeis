package nercel.javaweb.monthcontrast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class MonthContrast {
	
	/**
	 * 根据登录名获取某个区县各个月份的综合绩效得分
	 * @param qxuserName
	 * @return
	 * @throws Exception
	 */
	public ArrayList getQxScore(String qxuserName) throws Exception{
		MonthContrastDbUtil mcdb = new MonthContrastDbUtil();
		String areaName = mcdb.getArean(qxuserName);
		ArrayList list =  new ArrayList();
		ArrayList listScore =  new ArrayList();
		ArrayList<String> listMonth =  new ArrayList<String>();
		HashMap map = new HashMap();
		listMonth = mcdb.getAreanMonth();
		for(String s : listMonth){
			ArrayList listAreaScore =  new ArrayList();
			listAreaScore = mcdb.getSingleAreanScore(areaName,s);
			listScore.add(listAreaScore);
		}
		map.put("area", areaName);
		map.put("listscore", listScore);
		map.put("listmonth", listMonth);
		list.add(map);
		return list;
	}
	
	/**
	 *按月份获取各个区县的绩效得分
	 * @param areaName
	 * @return
	 * @throws Exception
	 */
	public ArrayList getAllScore(int fig) throws Exception{
		MonthContrastDbUtil mcdb = new MonthContrastDbUtil();
		ArrayList list =  new ArrayList();
		ArrayList listScore =  new ArrayList();
		ArrayList<String> listArean =  new ArrayList<String>();
		ArrayList<String> listMonth =  new ArrayList<String>();
		HashMap map = new HashMap();
		listArean = mcdb.getAllAreaName();
		listMonth = mcdb.getAreanMonth();
		for(String s : listMonth){
			ArrayList listAreaScore =  new ArrayList();
			listAreaScore = mcdb.getAllAreanScore(s,fig);
			listScore.add(listAreaScore);
		}
	
		map.put("area", listArean);
		map.put("listscore", listScore);
		map.put("listmonth", listMonth);
		list.add(map);
		return list;
	}
}
