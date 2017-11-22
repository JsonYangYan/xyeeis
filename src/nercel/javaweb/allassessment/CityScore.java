package nercel.javaweb.allassessment;

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
import java.util.zip.CRC32;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class CityScore {

	/**
	 * 襄阳市一级指标得分
	 * 
	 * @param currentTime
	 * @return
	 * @throws Exception
	 */
	public ArrayList getFirstIndexScore(String currentTime) throws Exception {

		AssessmentDbUtil assessmentDbUtil = new AssessmentDbUtil();
		assessmentDbUtil.openConnection();

		ArrayList arrAll = new ArrayList();
		ArrayList<Float> arr = new ArrayList<Float>();
		arr = assessmentDbUtil.getCityScore(currentTime);
		HashMap hashMap = new HashMap();
		hashMap.put("data", arr);
		hashMap.put("name", "襄阳市");

		HashMap hashMap_1 = new HashMap();
		hashMap_1.put("data", assessmentDbUtil.getHubeiFirstIndexScore());
		hashMap_1.put("name", "湖北省");

		HashMap hashMap_2 = new HashMap();
		hashMap_2.put("data", assessmentDbUtil.getQuanGuoFirstIndexScore());
		hashMap_2.put("name", "全国");

		arrAll.add(hashMap);
		arrAll.add(hashMap_1);
		arrAll.add(hashMap_2);

		assessmentDbUtil.closeConnection();

		return arrAll;
	}

	/**
	 * 取出每个区域的一级指数的值
	 * 
	 * @return
	 * @throws Exception
	 */
	public ArrayList getEveryAreanScore(String currentTime) throws Exception {

		AssessmentDbUtil assessmentDbUtil = new AssessmentDbUtil();
		assessmentDbUtil.openConnection();
		ArrayList<String> listArea = assessmentDbUtil.getAllAreaName();
		ArrayList list = new ArrayList();

		for (int i = 0; i < listArea.size(); i++) {
			ArrayList<Integer> arrayList = new ArrayList<Integer>();
			HashMap hashMap = new HashMap();

			if (listArea.get(i).equals("襄阳市")) {
				arrayList = assessmentDbUtil.getCityScore(currentTime);

			} else {
				arrayList = assessmentDbUtil.getAreanScore(listArea.get(i)
						.toString(), currentTime);
			}

			hashMap.put("name", listArea.get(i));
			hashMap.put("data", arrayList);
			list.add(hashMap);
		}

		assessmentDbUtil.closeConnection();
		return list;
	}

	// //获取树形显示
	/**
	 * 生成树形显示图
	 * 
	 * @throws SQLException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */

	private static void getIndexTree() throws SQLException, IOException,
			ClassNotFoundException {
		AssessmentDbUtil assessmentDbUtil = new AssessmentDbUtil();
		assessmentDbUtil.openConnection();

		ArrayList arrayList_1 = new ArrayList(); // /取得一级指标所有的权重
		HashMap hashMap_1 = new HashMap();
		hashMap_1.put("quizTitle", "襄阳市中小学信息化发展状况调研");

		ArrayList list = new ArrayList();

		arrayList_1 = assessmentDbUtil.getFirstIndexType();
		int f_1 = 1, f_3 = 1;

		for (int i = 0; i < arrayList_1.size(); i++) {

			HashMap hashMap_2 = new HashMap();
			hashMap_2.put("id", i + 1);
			hashMap_2.put("rank1", arrayList_1.get(i));

			ArrayList arrayList_2 = new ArrayList(); // 存储一级指标获取到二级指标id
			arrayList_2 = assessmentDbUtil.getSecondIndexId(i + 1); // 存储二级指标的id

			ArrayList listChild = new ArrayList(); // 所有三级指标

			for (int j = 0; j < arrayList_2.size(); j++) {

				ArrayList arrayList_3 = new ArrayList(); // 存储二级指标获取到三级指标id
				arrayList_3 = assessmentDbUtil.getThirdIndexType(arrayList_2
						.get(j)); // 存储二级指标获取到三级指标类型

				for (int k = 0; k < arrayList_3.size(); k++) {
					HashMap hashMap_3 = new HashMap();
					hashMap_3.put("rank2id", f_3++);
					hashMap_3.put("rank2", arrayList_3.get(k));
					listChild.add(hashMap_3);
				}
			}

			hashMap_2.put("children", listChild);
			list.add(hashMap_2);
			hashMap_1.put("children", list);
		}
		assessmentDbUtil.closeConnection();
	}

	/**
	 * 得到三级指标得分
	 * 
	 * @param schoolArean
	 * @return
	 * @throws Exception
	 */
	public float[] getThirdAreanScore(String schoolArean, String currentTime)
			throws Exception {
		AssessmentDbUtil assessmentDbUtil = new AssessmentDbUtil();
		assessmentDbUtil.openConnection();
		ArrayList<Float> listThirdScore = new ArrayList<Float>();
		float a[] = new float[33]; // 存储三级指标的个数
		for (int k = 0; k < a.length; k++) {
			a[k] = 0;
		}
		listThirdScore = assessmentDbUtil.getAreanThirdIndexScore(schoolArean, currentTime);
		for(int i=0;i<listThirdScore.size();i++){
			a[i] = listThirdScore.get(i);
		}
		assessmentDbUtil.closeConnection();
		return a;
	}

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
	 * 某区域填空题（一些可以量化的三级指标）平均值
	 * 
	 */
	public ArrayList getAvgThirdIndex(String areaName, String currentTime)
			throws Exception {
		ArrayList listAvg = new ArrayList();
		AssessmentDbUtil assessmentDbUtil = new AssessmentDbUtil();
		assessmentDbUtil.openConnection();
		ArrayList<Integer> arrayListSchId = new ArrayList<Integer>(); // 记录每个区多少学校
		if(areaName.equals("襄阳市")){
			arrayListSchId = assessmentDbUtil.getAllSchoolIdNumber(currentTime);
		}else{
			arrayListSchId = assessmentDbUtil.getAllSchoolIdNumberBySchoolArean(areaName, currentTime);
		}
		
		if (!arrayListSchId.isEmpty()) {
			String schoolIds = getSchoolIds(arrayListSchId);//改了
			listAvg = assessmentDbUtil.getAvg(schoolIds);
			//System.out.println(listAvg);
		} else {
			for (int i = 0; i < 39; i++) {
				listAvg.add(0.0);
			}
		}

		return listAvg;
	}

	/**
	 * 计算三级指标平均值入口函数
	 */
	public ArrayList getavgThird(String currentTime) throws Exception {
		AssessmentDbUtil assessmentDbUtil = new AssessmentDbUtil();
		assessmentDbUtil.openConnection();
		ArrayList<String> listArea = assessmentDbUtil.getAllAreaName();
		ArrayList list = new ArrayList();
		ArrayList listAvg = new ArrayList();
		int k = 0;
		for (int i = 0; i < listArea.size(); i++) {
			HashMap map = new HashMap();
			listAvg = getAvgThirdIndex(listArea.get(i), currentTime);
			map.put("area", listArea.get(i).toString());
			map.put("value", listAvg);
			list.add(map);
		}
		
		return list;
	}

	/**
	 * 计算教师、学生终端比例
	 * 
	 * @param fig
	 * @return
	 * @throws Exception
	 */
	public ArrayList getTeminalScore(int fig, String currentTime)
			throws Exception, ClassNotFoundException, Exception {
		AssessmentDbUtil amDbUtil = new AssessmentDbUtil();
		amDbUtil.openConnection();
		ArrayList arrayList = new ArrayList();
		int quePercent[] = { 0, 0, 0, 0 };
		int queId[] = { 135, 136, 137 };
		if (fig == 1) {
			queId[0] = 138;
			queId[1] = 139;
			queId[2] = 140;
		}

		// 计算……
		float t_1 = 20, t_2 = 60, t_3 = 90, fTeacher = 0, fQue = 0;
		ArrayList<Integer> allSchoolId = amDbUtil
				.getAllSchoolIdNumber(currentTime);
		int totalSchool = allSchoolId.size();
		HashMap<Integer, Integer> schTerNumHashMap=amDbUtil.getBlankText(allSchoolId, queId);
		if(schTerNumHashMap!=null){
			for (int schoolId : schTerNumHashMap.keySet()) {
				int sumTemp = schTerNumHashMap.get(schoolId);
				if (sumTemp <= t_1 && sumTemp >= 0) {
					quePercent[0]++;
				} else if (sumTemp <= t_2) {
					quePercent[1]++;
				} else if (sumTemp <= t_3) {
					quePercent[2]++;
				} else {
					quePercent[3]++;
				}
			}
		}	
		if (totalSchool != 0) {
			for (int k = 0; k < 4; k++) {
				HashMap hashMap = new HashMap();
				hashMap.put("value", Float
						.parseFloat(new java.text.DecimalFormat("#.00")
								.format(quePercent[k] * 100.0 / totalSchool)));
				if (k == 0) {
					hashMap.put("name", "0-20");
				} else if (k == 1) {
					hashMap.put("name", "20-60");
				} else if (k == 2) {
					hashMap.put("name", "60-90");
				} else {
					hashMap.put("name", "大于90");
				}
				arrayList.add(hashMap);
			}
		} else {
			for (int k = 0; k < 4; k++) {
				HashMap hashMap = new HashMap();
				hashMap.put("value", 0);
				if (k == 0) {
					hashMap.put("name", "0-20");
				} else if (k == 1) {
					hashMap.put("name", "20-60");
				} else if (k == 2) {
					hashMap.put("name", "60-90");
				} else {
					hashMap.put("name", "大于90");
				}
				arrayList.add(hashMap);
			}
		}
		amDbUtil.closeConnection();
		return arrayList;
	}
}
