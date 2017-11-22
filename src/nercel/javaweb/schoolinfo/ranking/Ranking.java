package nercel.javaweb.schoolinfo.ranking;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Ranking {
	// 定义存放各个维度得分的数组以及存放各区县的数组
	int size = 13;
	float arryOneScore[] = new float[size];
	float arryTwoScore[] = new float[size];
	float arryThreeScore[] = new float[size];
	float arryFourScore[] = new float[size];
	float arryFiveScore[] = new float[size];
	float arrySixScore[] = new float[size];
	String arryArean[] = new String[size];

	// 获取各区县绩效得分
	public ArrayList getScore(String schoolArean, String currentTime)
			throws SQLException, ClassNotFoundException, IOException {
		RankingDbUtil rankingdb = new RankingDbUtil();
		rankingdb.openConnection();
		ArrayList listScore = new ArrayList();
		listScore = rankingdb.getAreanScore(schoolArean, currentTime);
		rankingdb.closeConnection();
		return listScore;
	}

	public ArrayList getallAreaScore(int fig, String currentTime)
			throws ClassNotFoundException, SQLException, IOException {
		RankingDbUtil rankingdb = new RankingDbUtil();
		rankingdb.openConnection();
		ArrayList list = new ArrayList();
		ArrayList listAreaName = new ArrayList();
		listAreaName = rankingdb.getArean();
		String arean[] = new String[12];
		if (fig == 1) {
			arean = rankingdb.getDimenScore(fig, currentTime);
		} else if (fig == 2) {
			arean = rankingdb.getDimenScore(fig, currentTime);
		} else if (fig == 3) {
			arean = rankingdb.getDimenScore(fig, currentTime);
		} else if (fig == 4) {
			arean = rankingdb.getDimenScore(fig, currentTime);
		} else if (fig == 5) {
			arean = rankingdb.getDimenScore(fig, currentTime);
		} else if (fig == 6) {
			arean = rankingdb.getDimenScore(fig, currentTime);
		} else if (fig == 7) {
			int i = 0;
			for (int k=0;k<listAreaName.size();k++) {
				if (!listAreaName.get(k).toString().equals("襄阳市")) {
					arean[i] = listAreaName.get(k).toString();
					i++;
				}
			}
		}
		
		for (int i = arean.length-1; i >= 0; i--) {
			HashMap map = new HashMap();
			String areaName = arean[i];
			map.put("area", areaName);
			map.put("data", getScore(areaName, currentTime));
			list.add(map);
		}
		rankingdb.closeConnection();
		return list;
	}

	// /////用来总分排名条形图的具体信息，如 最大值、最小值、平均值、绩效高于或低于平均值的地区//////
	
	public ArrayList getDetail(String currentTime)
			throws ClassNotFoundException, SQLException, IOException {
		RankingDbUtil rankingdb = new RankingDbUtil();
		rankingdb.openConnection();
		ArrayList list = new ArrayList();
		ArrayList listMax = new ArrayList();
		ArrayList listMin = new ArrayList();
		listMax = rankingdb.getMaxAreanScoreAndName(currentTime);
		listMin = rankingdb.getMinAreanScoreAndName(currentTime);
		if(listMax.size()!=0&&listMin.size()!=0){
			float max = Float.parseFloat(listMax.get(0).toString());
			float min = Float.parseFloat(listMin.get(0).toString());
			float aver = rankingdb.getAvgCitylScore(currentTime);
			String scoreMaxArean = listMax.get(1).toString();
			String scoreMinArean = listMin.get(1).toString();
			int upAverAreanNum = rankingdb.getUpAvgAreanScore(currentTime);
			int lowAverAreanNum = rankingdb.getLowAvgAreanScore(currentTime);
			
			HashMap map = new HashMap();
			map.put("max_value", max);
			map.put("min_value", min);
			map.put("max_area", scoreMaxArean);
			map.put("min_area", scoreMinArean);
			map.put("avg_value", aver);
			map.put("up_avg_num", upAverAreanNum);
			map.put("down_avg_num", lowAverAreanNum);
			list.add(map);
		}else{
			HashMap map = new HashMap();
			map.put("max_value", 0);
			map.put("min_value", 0);
			map.put("max_area", "");
			map.put("min_area", "");
			map.put("avg_value", 0);
			map.put("up_avg_num", 0);
			map.put("down_avg_num", 0);
			list.add(map);
		}
		
		rankingdb.closeConnection();
		return list;
	}
}
