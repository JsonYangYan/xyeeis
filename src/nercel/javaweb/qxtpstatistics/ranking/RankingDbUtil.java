package nercel.javaweb.qxtpstatistics.ranking;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.text.*;

public class RankingDbUtil {
	private Connection con = null;

	public void openConnection() throws SQLException, IOException,
			ClassNotFoundException {
		if (con == null || con.isClosed()) {

			Properties p = new Properties();
			InputStream in = nercel.javaweb.ranking.RankingDbUtil.class
					.getResourceAsStream("/nercel/javaweb/admin/admin-db.properties");
			p.load(in);
			Class.forName(p.getProperty("db_driver"));
			con = DriverManager.getConnection(p.getProperty("db_url"),
					p.getProperty("db_user"), p.getProperty("db_password"));
		}
	}

	public void closeConnection() throws SQLException {
		try {
			if (con != null) {
				con.close();
			}
		} finally {
			con = null;
			System.gc();
		}
	}

	// 获得区县的名称
	public ArrayList getArean() throws SQLException {
		ArrayList arrayList = new ArrayList();
		String sql = "SELECT area_name FROM tarea";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()) {
			arrayList.add(rs.getString("area_name"));
		}
		return arrayList;
	}

	// 获取区县的绩效得分
	public ArrayList getAreanScore(String areaName, String currentTime)
			throws SQLException {
		ArrayList arrayList = new ArrayList();
		String sql = "SELECT * FROM tareascore WHERE areaName = '" + areaName
				+ "' AND createTime LIKE '" + currentTime + "%'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()) {
			arrayList.add(rs.getFloat("oneone"));
			arrayList.add(rs.getFloat("onetwo"));
			arrayList.add(rs.getFloat("onethree"));
			arrayList.add(rs.getFloat("onefour"));
			arrayList.add(rs.getFloat("onefive"));
			arrayList.add(rs.getFloat("comprehensive"));
		}
		return arrayList;
	}

	// 查询绩效得分最大的区县名称和分数
	public ArrayList getMaxAreanScoreAndName(String time)
			throws SQLException, ClassNotFoundException {
		String sql = "SELECT areaName,comprehensive FROM tareascore  WHERE comprehensive =(SELECT MAX(comprehensive) FROM tareascore WHERE createTime LIKE '"+time+"%') AND createTime LIKE '"+time+"%'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		ArrayList list = new ArrayList();
		while (rs.next()) {
			list.add(rs.getFloat("comprehensive"));
			list.add(rs.getString("areaName"));
		}
		state.close();
		return list;
	}

	// 查询绩效得分最小的区县名称和分数
	public ArrayList getMinAreanScoreAndName(String time)
			throws SQLException, ClassNotFoundException {
		String sql = "SELECT areaName,comprehensive FROM tareascore  WHERE comprehensive =(SELECT MIN(comprehensive) FROM tareascore WHERE createTime LIKE '"+time+"%') AND createTime LIKE '"+time+"%'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		ArrayList list = new ArrayList();
		while (rs.next()) {
			list.add(rs.getFloat("comprehensive"));
			list.add(rs.getString("areaName"));
		}
		state.close();
		return list;
	}

	// 查询全市绩效平均分
	public float getAvgCitylScore(String time)
			throws SQLException, ClassNotFoundException {
		String sql = "SELECT AVG(comprehensive) AS num FROM tareascore  WHERE createTime LIKE '"+time+"%'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		float avgScore = 0;
		while (rs.next()) {
			avgScore = Float.parseFloat(new java.text.DecimalFormat("#.##")
					.format(rs.getFloat("num")));
		}
		state.close();
		return avgScore;
	}

	// 查询高于平均分的区县数
	public int getUpAvgAreanScore(String time)
			throws SQLException, ClassNotFoundException {
		String sql = "SELECT COUNT(id) AS num FROM tareascore WHERE comprehensive > (SELECT AVG(comprehensive) FROM tareascore  WHERE createTime LIKE '"+time+"%') AND createTime LIKE '"+time+"%'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		int schoolNum = 0;
		while (rs.next()) {
			schoolNum = rs.getInt("num");
		}
		state.close();
		return schoolNum;
	}

	// 查询低于平均分的区县数
	public int getLowAvgAreanScore(String time)
			throws SQLException, ClassNotFoundException {
		String sql = "SELECT COUNT(id) AS num FROM tareascore WHERE comprehensive < (SELECT AVG(comprehensive) FROM tareascore  WHERE createTime LIKE '"+time+"%') AND createTime LIKE '"+time+"%'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		int schoolNum = 0;
		while (rs.next()) {
			schoolNum = rs.getInt("num");
		}
		state.close();
		return schoolNum;
	}
	
	//按照某种维度排名
	public String[] getDimenScore(int fig,String currentTime)
			throws SQLException {
		ArrayList arrayList = new ArrayList();
		String areaName[] = new String[12];
		int k= 0;
		String sql = "";
		if(fig==1){
			sql = "SELECT areaName FROM tareascore WHERE createTime LIKE '" + currentTime + "%' ORDER BY oneone";
		}else if(fig==2){
			sql = "SELECT areaName FROM tareascore WHERE createTime LIKE '" + currentTime + "%' ORDER BY onetwo";
		}else if(fig==3){
			sql = "SELECT areaName FROM tareascore WHERE createTime LIKE '" + currentTime + "%' ORDER BY onethree";
		}else if(fig==4){
			sql = "SELECT areaName FROM tareascore WHERE createTime LIKE '" + currentTime + "%' ORDER BY onefour";
		}else if(fig==5){
			sql = "SELECT areaName FROM tareascore WHERE createTime LIKE '" + currentTime + "%' ORDER BY onefive";
		}else if(fig==6){
			sql = "SELECT areaName FROM tareascore WHERE createTime LIKE '" + currentTime + "%' ORDER BY comprehensive";
		}
		
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()) {
			 areaName[k] = rs.getString("areaName");
			 k++;
		}
		return areaName;
	}
}
