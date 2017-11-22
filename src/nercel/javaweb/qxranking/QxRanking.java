package nercel.javaweb.qxranking;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class QxRanking {
	// 对一个区县各学校计算其绩效,通过getSchoolScore方法
	public ArrayList getScore(String schoolArean, int fig, String time)
			throws ClassNotFoundException, SQLException, IOException {
		QxRankingDbUtil dbranking = new QxRankingDbUtil();
		dbranking.openConnection();
		// 定义存放各个维度得分的数组以及存放各区县的数组
		int size = (int) dbranking.getsubSchoolnum(schoolArean, time);
		float arryOneScore[] = new float[size];
		float arryTwoScore[] = new float[size];
		float arryThreeScore[] = new float[size];
		float arryFourScore[] = new float[size];
		float arryFiveScore[] = new float[size];
		float arrySixScore[] = new float[size];
		String arrySchoolId[] = new String[size];

		ArrayList listInitSchoolId = new ArrayList();
		ArrayList listSchoolId = new ArrayList();
		ArrayList list = new ArrayList();
		ArrayList listSchoolScore = new ArrayList();

		// 初始化
		listInitSchoolId = dbranking.getSchoolId(schoolArean, time);
		int k = 0;
		for (int i = 0; i < listInitSchoolId.size(); i++) {
			listSchoolScore = dbranking.getschoolScore(Integer
					.parseInt(listInitSchoolId.get(i).toString()));
			if (!listSchoolScore.isEmpty()) {
				arryOneScore[k] = Float.parseFloat(listSchoolScore.get(0)
						.toString());
				arryTwoScore[k] = Float.parseFloat(listSchoolScore.get(1)
						.toString());
				arryThreeScore[k] = Float.parseFloat(listSchoolScore.get(2)
						.toString());
				arryFourScore[k] = Float.parseFloat(listSchoolScore.get(3)
						.toString());
				arryFiveScore[k] = Float.parseFloat(listSchoolScore.get(4)
						.toString());
				arrySixScore[k] = Float.parseFloat(listSchoolScore.get(5)
						.toString());
				arrySchoolId[k] = listInitSchoolId.get(i).toString();
			} else {
				arryOneScore[k] = 0;
				arryTwoScore[k] = 0;
				arryThreeScore[k] = 0;
				arryFourScore[k] = 0;
				arryFiveScore[k] = 0;
				arrySixScore[k] = 0;
				arrySchoolId[k] = listInitSchoolId.get(i).toString();
			}

			k++;
		}

		if (fig == 1) {
			listSchoolId = arrSort(arryOneScore, arrySchoolId);
		} else if (fig == 2) {
			listSchoolId = arrSort(arryTwoScore, arrySchoolId);
		} else if (fig == 3) {
			listSchoolId = arrSort(arryThreeScore, arrySchoolId);
		} else if (fig == 4) {
			listSchoolId = arrSort(arryFourScore, arrySchoolId);
		} else if (fig == 5) {
			listSchoolId = arrSort(arryFiveScore, arrySchoolId);
		} else if (fig == 6) {
			listSchoolId = listInitSchoolId;
		} else if (fig == 7) {
			listSchoolId = dbranking.getTopTenSchoolId(schoolArean, time);
		}
		if (!listSchoolId.isEmpty()) {
			for (int i = 0; i < listSchoolId.size(); i++) {
				HashMap map = new HashMap();
				map.put("area", schoolArean);
				map.put("schoolName", dbranking.getSchoolNamebyId(Integer
						.parseInt(listSchoolId.get(i).toString())));
				map.put("data", dbranking.getschoolScore(Integer
						.parseInt(listSchoolId.get(i).toString())));
				list.add(map);
			}
		} else {
			ArrayList listData = new ArrayList();
			listData.add("");
			HashMap map = new HashMap();
			map.put("area", schoolArean);
			map.put("schoolName", "null");
			map.put("data", listData);
			list.add(map);
		}
		dbranking.closeConnection();
		return list;
	}

	// 选择排序法对某个维度进行排序
	public ArrayList arrSort(float a[], String b[]) {
		for (int i = 0; i < a.length - 1; i++) {
			for (int j = i + 1; j < a.length; j++) {
				if (a[j] > a[i]) {

					float tempScore = a[i];
					a[i] = a[j];
					a[j] = tempScore;

					String tempSchoolId = b[i];
					b[i] = b[j];
					b[j] = tempSchoolId;
				}
			}
		}

		ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(b));
		return arrayList;

	}

	// /////用来总分排名条形图的具体信息，如 最大值、最小值、平均值、绩效高于或低于平均值的地区//////
	public ArrayList getDetail(String areaName, String time)
			throws ClassNotFoundException, SQLException, IOException {
		QxRankingDbUtil dbranking = new QxRankingDbUtil();
		dbranking.openConnection();
		ArrayList list = new ArrayList();
		if (dbranking.getMaxSchoolScoreAndId(areaName, time).size() != 0) {
			float max = (float) dbranking
					.getMaxSchoolScoreAndId(areaName, time).get(0);
			float min = (float) dbranking
					.getMinSchoolScoreAndId(areaName, time).get(0);
			int maxScoreSchId = (int) dbranking.getMaxSchoolScoreAndId(
					areaName, time).get(1);
			int minScoreSchId = (int) dbranking.getMinSchoolScoreAndId(
					areaName, time).get(1);
			float aver = dbranking.getAvgSchoolScore(areaName, time);
			String scoreMaxSchool = dbranking.getSchoolNamebyId(maxScoreSchId);
			String scoreMinSchool = dbranking.getSchoolNamebyId(minScoreSchId);
			int upAverSchoolNum = dbranking.getUpAvgSchoolScore(areaName, time);
			int lowAverSchoolNum = dbranking.getDownAvgSchoolScore(areaName,
					time);

			HashMap map = new HashMap();
			map.put("max_value", max);
			map.put("min_value", min);
			map.put("max_school", scoreMaxSchool);
			map.put("min_school", scoreMinSchool);
			map.put("avg_value", aver);
			map.put("up_avg_num", upAverSchoolNum);
			map.put("down_avg_num", lowAverSchoolNum);
			list.add(map);
		} else {
			HashMap map = new HashMap();
			map.put("max_value", 0);
			map.put("min_value", 0);
			map.put("max_school", "");
			map.put("min_school", "");
			map.put("avg_value", 0);
			map.put("up_avg_num", 0);
			map.put("down_avg_num", 0);
			list.add(map);
		}

		dbranking.closeConnection();
		return list;
	}

}
