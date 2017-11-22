package nercel.javaweb.tpcurrentdata;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nercel.javaweb.allassessment.AssessmentDbUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class TpCurrentdata {

	// 把schoolId用逗号分开
	private String getSchoolIds(ArrayList schoolId) {
		String schoolIds = "";
		for (int i = 0; i < schoolId.size(); i++) {
			schoolIds += schoolId.get(i) + ",";
		}
		if (!schoolIds.equals(""))
			schoolIds = schoolIds.substring(0, schoolIds.length() - 1);
		return schoolIds;
	}

	/**
	 * 某区域或襄阳市填空题（一些可以量化的三级指标）平均值  函数入口
	 * 
	 */
	public ArrayList getavgThird(String currentTime) throws Exception {
		TpCurrentdataDbUtil qtcDbUtil = new TpCurrentdataDbUtil();
		qtcDbUtil.openConnection();
		ArrayList<String> listArea = qtcDbUtil.getAllAreaName();
		ArrayList list = new ArrayList();
		ArrayList listAvg = new ArrayList();
		int k = 0;
		for (int i = 0; i < listArea.size(); i++) {
			HashMap map = new HashMap();
			listAvg = getTownAvgThirdIndex(listArea.get(i), currentTime);
			map.put("area", listArea.get(i).toString());
			map.put("value", listAvg);
			list.add(map);
		}
		qtcDbUtil.closeConnection();
		return list;
	}
	
	public ArrayList getTownAvgThirdIndex(String areaName,String currentTime)
			throws Exception {
		HashMap map = new HashMap();
		ArrayList listAvg = new ArrayList();
		TpCurrentdataDbUtil qtcDbUtil = new TpCurrentdataDbUtil();
		qtcDbUtil.openConnection();
		ArrayList<Integer> arrayListSchId = new ArrayList<Integer>(); // 记录每个区多少学校
		if(areaName.equals("襄阳市")){
			arrayListSchId = qtcDbUtil.getAllSchoolIdNumber(currentTime);
		}else{
			arrayListSchId = qtcDbUtil.getSomeSchoolIdNumber(areaName,currentTime);
		}
		if(!arrayListSchId.isEmpty()){
			String schoolIds= getSchoolIds(arrayListSchId);
			listAvg = qtcDbUtil.getTownAvg(schoolIds);
		}else{
			for(int i=0;i<12;i++){
				listAvg.add(0);
			}
		}
		
		return listAvg;
	}
	
}
