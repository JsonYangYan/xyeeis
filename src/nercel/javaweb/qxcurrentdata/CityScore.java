package nercel.javaweb.qxcurrentdata;

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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class CityScore {

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
	public Map getTownAvgThirdIndex(String areaName, String currentTime)
			throws Exception {
		HashMap map = new HashMap();
		ArrayList listAvg = new ArrayList();
		AssessmentDbUtil assessmentDbUtil = new AssessmentDbUtil();
		assessmentDbUtil.openConnection();
		ArrayList<Integer> arrayListSchId = new ArrayList<Integer>(); // 记录每个区多少学校
		if(areaName.equals("襄阳市")){
			arrayListSchId = assessmentDbUtil.getAllSchoolIdNumber(currentTime);
		}else{
			arrayListSchId = assessmentDbUtil.getSomeSchoolIdNumber(areaName,currentTime);
		}
		if(!arrayListSchId.isEmpty()){
			String schoolIds= getSchoolIds(arrayListSchId);
			listAvg = assessmentDbUtil.getTownAvg(schoolIds);
		}else{
			for(int i=0;i<38;i++){
				listAvg.add(0);
			}
		}
		map.put("area", areaName);
		map.put("value", listAvg);
		return map;

	}
	
	/**
	 * 教育概况  函数入口
	 */
	public ArrayList educationSurvey(String areaName, String currentTime) throws Exception {
		ArrayList arrayList = new ArrayList();
		HashMap map = new HashMap();
		JSONObject object = new JSONObject();
		AssessmentDbUtil assessmentDbUtil = new AssessmentDbUtil();
		assessmentDbUtil.openConnection();
		int highSchoolNum = assessmentDbUtil.highSchoolNum(areaName, currentTime);
		int juniorSchoolNum = assessmentDbUtil.juniorSchoolNum(areaName, currentTime);
		int primarySchoolNum = assessmentDbUtil.primarySchoolNum(areaName, currentTime);
		int otherSchoolNum = assessmentDbUtil.otherSchoolNum(areaName, currentTime);
		int teacherNum = assessmentDbUtil.teacherNum(areaName, currentTime);
		int studentNum= assessmentDbUtil.studentNum(areaName, currentTime);
		int townSchoolNum= assessmentDbUtil.townSchoolNum(areaName, currentTime);
		int villageSchoolNum= assessmentDbUtil.villageSchoolNum(areaName, currentTime);
		object.put("highSchoolNum", highSchoolNum);
		object.put("juniorSchoolNum", juniorSchoolNum);
		object.put("primarySchoolNum", primarySchoolNum);
		object.put("otherSchoolNum", otherSchoolNum);
		object.put("teacherNum", teacherNum);
		object.put("studentNum", studentNum);
		object.put("townSchoolNum", townSchoolNum);
		object.put("villageSchoolNum", villageSchoolNum);
		
		arrayList.add(object);
		return arrayList;
		
	}
	
}
