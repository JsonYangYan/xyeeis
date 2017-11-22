package nercel.javaweb.schoolranking;

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

public class SchoolRankingDbUtil {
	private Connection con = null;

	public void openConnection() throws SQLException, IOException,
			ClassNotFoundException {
		if (con == null || con.isClosed()) {

			Properties p = new Properties();
			InputStream in = nercel.javaweb.schoolranking.SchoolRankingDbUtil.class
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

	// 襄阳市提交学校的总数
	public int getSchoolNumber(String currentTime) throws Exception {
		int schoolNum = 0;
		String sql = "SELECT COUNT(autoId) AS num FROM tschoolinfor WHERE state=1 AND userTime LIKE '%"
				+ currentTime + "%'";
		//System.out.println(sql);
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()) {
			schoolNum = rs.getInt("num");
		}
		state.close();
		return schoolNum;
	}

	// 对学校评估结果进行排名
	// 查询前十条数据
	public ArrayList getTopSchoolRank(String currentTime,int schoolNum)
			throws ClassNotFoundException, SQLException {
		ArrayList list = new ArrayList();
		String sql = "SELECT schoolScore,schoolName,schoolArean,area_id from tschoolindexscore,"
				+ "tschoolinfor,tarea WHERE indexType=0 AND tschoolindexscore.schoolId=tschoolinfor.autoId "
				+ "AND tschoolinfor.schoolArean=tarea.area_name AND state=1 AND userTime LIKE '"
				+ currentTime + "%' order by schoolScore DESC limit 0,10";
		//System.out.println(sql);
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		String areaName = "";
		while (rs.next()) {
			if(rs.getString("schoolArean").equals("襄阳市")){
				areaName = "襄阳市直";
			}else{
				areaName = rs.getString("schoolArean");
			}
			HashMap map = new HashMap();
			map.put("area_id", rs.getString("area_id"));
			map.put("area_name", areaName);
			map.put("school_name", rs.getString("schoolName"));
			map.put("value", rs.getString("schoolScore"));
			map.put("schoolNum", schoolNum);
			list.add(map);

		}
		state.close();
		return list;
	}

	// 查询中间数据
	public ArrayList getMiddleSchoolRank(String currentTime, int schoolNum)
			throws ClassNotFoundException, SQLException {
		ArrayList list = new ArrayList();
		if (schoolNum > 20) {
			int num = schoolNum-20;
			String sql = "SELECT schoolScore,schoolName,schoolArean,area_id from tschoolindexscore,"
					+ "tschoolinfor,tarea WHERE indexType=0 AND tschoolindexscore.schoolId=tschoolinfor.autoId "
					+ "AND tschoolinfor.schoolArean=tarea.area_name AND state=1 AND userTime LIKE'"
					+ currentTime + "%' ORDER BY schoolScore DESC limit 10,"+num+"";
			Statement state = con.createStatement();
			ResultSet rs = state.executeQuery(sql);
			String areaName = "";
			while (rs.next()) {
				if(rs.getString("schoolArean").equals("襄阳市")){
					areaName = "襄阳市直";
				}else{
					areaName = rs.getString("schoolArean");
				}
				HashMap map = new HashMap();
				map.put("area_id", rs.getString("area_id"));
				map.put("area_name", areaName);
				map.put("school_name", rs.getString("schoolName"));
				map.put("value", rs.getString("schoolScore"));
				map.put("schoolNum", schoolNum);
				list.add(map);

			}
			state.close();
		}else{
			HashMap map = new HashMap();
			map.put("area_id", "");
			map.put("area_name", "");
			map.put("school_name", "");
			map.put("value", "");
			map.put("schoolNum", schoolNum);
			list.add(map);
		}

		return list;
	}

	// 查询后十条数据
	public ArrayList getLastSchoolRank(String currentTime,int schoolNum)
			throws ClassNotFoundException, SQLException {
		ArrayList list = new ArrayList();
		String sql = "SELECT schoolScore,schoolName,schoolArean, area_id from tschoolindexscore,tschoolinfor,tarea "
				+ "WHERE indexType=0 AND tschoolindexscore.schoolId=tschoolinfor.autoId AND state=1 AND userTime LIKE'"
				+ currentTime
				+ "%'"
				+ "AND tschoolinfor.schoolArean=tarea.area_name ORDER BY schoolScore ASC limit 0,10";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		String areaName = "";
		while (rs.next()) {
			if (!rs.getString("schoolArean").equals("襄阳市")) {
				if(rs.getString("schoolArean").equals("襄阳市")){
					areaName = "襄阳市直";
				}else{
					areaName = rs.getString("schoolArean");
				}
				HashMap map = new HashMap();
				map.put("area_id", rs.getString("area_id"));
				map.put("area_name", areaName);
				map.put("school_name", rs.getString("schoolName"));
				map.put("value", rs.getString("schoolScore"));
				map.put("schoolNum", schoolNum);
				list.add(map);

			}
		}
		state.close();
		return list;
	}

}
