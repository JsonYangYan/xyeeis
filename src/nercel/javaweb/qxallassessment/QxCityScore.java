package nercel.javaweb.qxallassessment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class QxCityScore {

	/**
	 * 此方法是获得一级指标的得分，返回hashmap,可以被其他方法调用
	 * 
	 * @return
	 * @throws Exception
	 */
	public HashMap getFirstScore(String areaName,String currentTime) throws Exception {
		QxAssessmentDbUtil assessmentDbUtil = new QxAssessmentDbUtil();
		assessmentDbUtil.openConnection();
		ArrayList list = new ArrayList();
		/*ArrayList<Integer> arrayList = new ArrayList<Integer>();
		float sum_1 = 0, sum_2 = 0, sum_3 = 0, sum_4 = 0, sum_5 = 0;*/
		HashMap hashMap = new HashMap();
		float arrScore[] = new float[6];
		if (areaName.equals("襄阳市")) {
			list = assessmentDbUtil.getCityScore(currentTime);
		} else {
			list = assessmentDbUtil.getAreanScore(areaName, currentTime);
		}

		/*for (int j = 0; j < arrayList.size(); j++) {
			ArrayList<Float> arrayListScore = new ArrayList<Float>();
			arrayListScore = assessmentDbUtil.getAllFirstIndexScore(arrayList
					.get(j)); // 取出一个人的得分
			if (arrayListScore.size() != 0) {
				sum_1 = sum_1 + arrayListScore.get(0);	//基础设施
				sum_2 = sum_2 + arrayListScore.get(1);	//教学信息化应用
				sum_3 = sum_3 + arrayListScore.get(2);	//教研信息化应用
				sum_4 = sum_4 + arrayListScore.get(3);	//管理与服务信息化应用
				sum_5 = sum_5 + arrayListScore.get(4);	//信息化保障
			} else {
				sum_1 = sum_1 + 0;
				sum_2 = sum_2 + 0;
				sum_3 = sum_3 + 0;
				sum_4 = sum_4 + 0;
				sum_5 = sum_5 + 0;
			}
		}

		if (arrayList.size() != 0) {
			sum_1 = Float.parseFloat(new java.text.DecimalFormat("#.##").format(sum_1 / arrayList.size()));
			sum_2 = Float.parseFloat(new java.text.DecimalFormat("#.##").format(sum_2 / arrayList.size()));
			sum_3 = Float.parseFloat(new java.text.DecimalFormat("#.##").format(sum_3 / arrayList.size()));
			sum_4 = Float.parseFloat(new java.text.DecimalFormat("#.##").format(sum_4 / arrayList.size()));
			sum_5 = Float.parseFloat(new java.text.DecimalFormat("#.##").format(sum_5 / arrayList.size()));
		} else {
			sum_1 = 0;
			sum_2 = 0;
			sum_3 = 0;
			sum_4 = 0;
			sum_5 = 0;
		}*/

		/*ArrayList<Float> arrayListTemp = new ArrayList<Float>();
		
		arrayListTemp.add(sum_1);
		arrayListTemp.add(sum_2);
		arrayListTemp.add(sum_3);
		arrayListTemp.add(sum_4);
		arrayListTemp.add(sum_5);*/
		if(list.size()!=0){
			hashMap.put("name", areaName);
			hashMap.put("data", list);
		}else{
			hashMap.put("name", areaName);
			hashMap.put("data", arrScore);
		}
		
		assessmentDbUtil.closeConnection();
		return hashMap;
	}

	/**
	 * 获取雷达图，把区县、襄阳市、湖北省一级指标作对比
	 * 
	 * @param qxname
	 * @return
	 * @throws Exception
	 */
	public ArrayList getFirstIndexScore(String areaName,String currentTime) throws Exception {
		QxAssessmentDbUtil assessmentDbUtil = new QxAssessmentDbUtil();
		assessmentDbUtil.openConnection();
		ArrayList arrAll = new ArrayList();

		HashMap hashMap_area = new HashMap();
		hashMap_area = getFirstScore(areaName,currentTime); // 区县一级指标得分

		HashMap hashMap = new HashMap();
		hashMap = getFirstScore("襄阳市",currentTime); // 襄阳市一级指标得分

		HashMap hashMap_sheng = new HashMap();
		hashMap_sheng.put("data", assessmentDbUtil.getHubeiFirstIndexScore());
		hashMap_sheng.put("name", "湖北省");

		arrAll.add(hashMap);
		arrAll.add(hashMap_sheng);
		arrAll.add(hashMap_area);

		assessmentDbUtil.closeConnection();

		return arrAll;
	}

	/**
	 * 取出区域的一级指标得分
	 * 
	 * @return
	 * @throws Exception
	 */
	public ArrayList getFirstAreanScore(String areaName,String currentTime) throws Exception {
		ArrayList list = new ArrayList();
		HashMap hashMap = new HashMap();
		hashMap = getFirstScore(areaName,currentTime);
		list.add(hashMap);
		return list;
	}

	// 一级指标类型名称+得分
	public ArrayList getFirstAreanScoreJson(String areaName,String currentTime) throws Exception {
		QxAssessmentDbUtil assessmentDbUtil = new QxAssessmentDbUtil();
		assessmentDbUtil.openConnection();
		ArrayList list = new ArrayList();
		ArrayList listType = new ArrayList();
		HashMap hashMapValue = new HashMap();
		hashMapValue = getFirstScore(areaName,currentTime);
		hashMapValue.remove("name");
		JSONObject objectValue = JSONObject.fromObject(hashMapValue);
		JSONArray jsonValue = new JSONArray();
		jsonValue = (JSONArray) objectValue.get("data");
		listType = assessmentDbUtil.getFirstIndexType();
		if(jsonValue.size()!=0){
			for (int i = 0; i < listType.size(); i++) {
				HashMap hashMapFirst = new HashMap();
				hashMapFirst.put("type", listType.get(i));
				hashMapFirst.put("value", jsonValue.get(i));
				list.add(hashMapFirst);
			}
		}else{
			for (int i = 0; i < listType.size(); i++) {
				HashMap hashMapFirst = new HashMap();
				hashMapFirst.put("type", listType.get(i));
				hashMapFirst.put("value", 0);
				list.add(hashMapFirst);
			}
		}
		
		assessmentDbUtil.closeConnection();
		return list;
	}

	/**
	 * 获得二级指标得分
	 * 
	 */
	public ArrayList getSecondAreanScore(String schoolArean,String currentTime) throws Exception {
		QxAssessmentDbUtil assessmentDbUtil = new QxAssessmentDbUtil();
		assessmentDbUtil.openConnection();
		ArrayList listSecondScore =  new ArrayList();
		listSecondScore = assessmentDbUtil.getAreanSecondIndexScore(schoolArean, currentTime);
		/*ArrayList<Integer> arrayList = new ArrayList<Integer>(); // 记录每个区多少学校
		arrayList = assessmentDbUtil.getSomeSchoolIdNumber(schoolArean,currentTime);

		float a[] = new float[17]; // 存储二级指标的个数
		for (int k = 0; k < a.length; k++) {
			a[k] = 0;
		}

		for (int i = 0; i < arrayList.size(); i++) {
			ArrayList<Float> arrayListScore = new ArrayList<Float>(); // 记录每个学校所有的二级指标得分
			arrayListScore = assessmentDbUtil.getAllSecondIndexScore(arrayList.get(i)); // 这个长度也是17个

			for (int j = 0; j < arrayListScore.size(); j++) {
				a[j] = a[j] + arrayListScore.get(j);
			}
		}

		for (int k = 0; k < a.length; k++) {
			if (arrayList.size() != 0) {
				a[k] = Float.parseFloat(new java.text.DecimalFormat("#.##").format(a[k] / arrayList.size()));
			} else {
				a[k] = 0;
			}
		}*/
		
		assessmentDbUtil.closeConnection();
		return listSecondScore;
	}

	// 二级指标类型名称+得分
	public ArrayList getSecondAreanScoreJson(String areaName,String currentTime) throws Exception {
		QxAssessmentDbUtil assessmentDbUtil = new QxAssessmentDbUtil();
		assessmentDbUtil.openConnection();
		ArrayList list = new ArrayList();
		ArrayList listType = new ArrayList();
		ArrayList listScore = new ArrayList();

		listScore = getSecondAreanScore(areaName,currentTime);
		listType = assessmentDbUtil.getSecondIndexType();
		if(listScore.size()!=0){
			for (int i = 0; i < listType.size(); i++) {
				HashMap hashMapSecond = new HashMap();
				hashMapSecond.put("type", listType.get(i));
				hashMapSecond.put("value", listScore.get(i));
				list.add(hashMapSecond);
			}
		}else{
			for (int i = 0; i < listType.size(); i++) {
				HashMap hashMapSecond = new HashMap();
				hashMapSecond.put("type", listType.get(i));
				hashMapSecond.put("value", 0);
				list.add(hashMapSecond);
			}
		}

		assessmentDbUtil.closeConnection();
		return list;
	}

	/**
	 * 得到三级指标得分
	 * 
	 * @param schoolArean
	 * @return
	 * @throws Exception
	 */
	public ArrayList getThirdAreanScore(String schoolArean,String currentTime) throws Exception {
		QxAssessmentDbUtil assessmentDbUtil = new QxAssessmentDbUtil();
		assessmentDbUtil.openConnection();
		ArrayList listThirdScore =  new ArrayList();
		listThirdScore = assessmentDbUtil.getAreanThirdIndexScore(schoolArean, currentTime);
		/*ArrayList<Integer> arrayList = new ArrayList<Integer>(); // 记录每个区多少学校
		arrayList = assessmentDbUtil.getSomeSchoolIdNumber(schoolArean,currentTime);
		float a[] = new float[33]; // 存储三级指标的个数
		for (int k = 0; k < a.length; k++) {
			a[k] = 0;
		}

		for (int i = 0; i < arrayList.size(); i++) {
			ArrayList<Float> arrayListScore = new ArrayList<Float>(); // 记录每个学校所有的三级指标得分
			arrayListScore = assessmentDbUtil.getAllThirdIndexScore(arrayList
					.get(i)); // 这个长度也是33个

			for (int j = 0; j < arrayListScore.size(); j++) {
				a[j] = a[j] + arrayListScore.get(j);
			}
		}

		for (int k = 0; k < a.length; k++) {
			if (arrayList.size() != 0) {
				a[k] = Float.parseFloat(new java.text.DecimalFormat("#.##").format(a[k] / arrayList.size()));
			} else {
				a[k] = 0;
			}
		}*/
		
		assessmentDbUtil.closeConnection();
		return listThirdScore;
	}

	// 三级指标类型名称+得分
	public ArrayList getThirdAreanScoreJson(String areaName,String currentTime) throws Exception {
		QxAssessmentDbUtil assessmentDbUtil = new QxAssessmentDbUtil();
		assessmentDbUtil.openConnection();
		ArrayList list = new ArrayList();
		ArrayList listType = new ArrayList();
		ArrayList listScore = new ArrayList();
		listScore = getThirdAreanScore(areaName,currentTime);
		listType = assessmentDbUtil.getThirdIndexType();
		if(listScore.size()!=0){
			for (int i = 0; i < listType.size(); i++) {
				HashMap hashMapThird = new HashMap();
				hashMapThird.put("type", listType.get(i));
				hashMapThird.put("value", listScore.get(i));
				list.add(hashMapThird);
			}
		}else{
			for (int i = 0; i < listType.size(); i++) {
				HashMap hashMapThird = new HashMap();
				hashMapThird.put("type", listType.get(i));
				hashMapThird.put("value", 0);
				list.add(hashMapThird);
			}
		}
		
		assessmentDbUtil.closeConnection();
		return list;
	}
}