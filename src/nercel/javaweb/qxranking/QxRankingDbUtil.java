package nercel.javaweb.qxranking;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.text.*;

public class QxRankingDbUtil {
	private Connection con = null;

	public void openConnection() throws SQLException, IOException,
			ClassNotFoundException {
		if (con == null || con.isClosed()) {

			Properties p = new Properties();
			InputStream in = nercel.javaweb.qxranking.QxRankingDbUtil.class
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

	// 根据登陆名查找到所在区域的名称
	public String getArean(String qxusername) throws SQLException,
			ClassNotFoundException {
		String sql = "SELECT * FROM tuser,tarea WHERE tuser.area_id=tarea.area_id AND username='"
				+ qxusername + "'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		String Arean = "";
		if (rs.next()) {
			Arean = rs.getString("area_name");
		}
		state.close();
		return Arean;
	}

	// 获取各地区已提交学校数
	public float getsubSchoolnum(String schoolArean, String time)
			throws SQLException, ClassNotFoundException {
		String sql = "SELECT count(autoId) as subsch_num FROM tschoolinfor WHERE state=1 AND userTime LIKE '"
				+ time + "%' AND schoolArean='" + schoolArean + "'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		int subsch_num = 0;
		if (rs.next()) {
			subsch_num = rs.getInt("subsch_num");
		}
		state.close();
		return subsch_num;
	}

	// 获得区域提交学校的schooId
	public ArrayList getSchoolId(String schoolArean, String time)
			throws SQLException, ClassNotFoundException {
		/*
		 * String sql =
		 * "SELECT autoId FROM tschoolinfor WHERE state=1 AND userTime LIKE '" +
		 * time + "%' AND schoolArean='" + schoolArean + "'";
		 */
		String sql = "SELECT distinct schoolId FROM tschoolinfor,tschoolindexscore WHERE tschoolinfor.autoId = schoolId AND indexType=0 AND state=1 AND userTime LIKE '"
				+ time
				+ "%' AND schoolArean='"
				+ schoolArean
				+ "' ORDER BY schoolScore DESC";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		ArrayList list = new ArrayList();
		while (rs.next()) {
			list.add(rs.getInt("schoolId"));
		}
		state.close();
		return list;
	}

	// 获得区域提交学校综合得分前十的schooId
	public ArrayList getTopTenSchoolId(String schoolArean, String time)
			throws SQLException, ClassNotFoundException {
		String sql = "SELECT schoolId FROM tschoolinfor,tschoolindexscore WHERE tschoolinfor.autoId = schoolId AND indexType=0 AND state=1 AND userTime LIKE '"
				+ time
				+ "%' AND schoolArean='"
				+ schoolArean
				+ "' ORDER BY schoolScore DESC LIMIT 0,10";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		ArrayList list = new ArrayList();
		while (rs.next()) {
			list.add(rs.getInt("schoolId"));
		}
		state.close();
		return list;
	}

	// 根据学校Id获得学校名称
	public String getSchoolNamebyId(int schoolId) throws SQLException,
			ClassNotFoundException {
		String schoolName = "";
		String sql = "SELECT schoolName FROM tschoolinfor WHERE autoId="
				+ schoolId + "";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()) {
			schoolName = rs.getString("schoolName");
		}

		state.close();
		return schoolName;
	}

	// 对一个学校评分，从数据库schoolScore表中查，返回list
	public ArrayList getschoolScore(int id) throws SQLException,
			ClassNotFoundException {
		String sql = "SELECT schoolScore FROM tschoolindexscore WHERE indexType=1 and schoolId="
				+ id
				+ " or indexType=0 and schoolId="
				+ id
				+ " order by autoId";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		ArrayList list = new ArrayList();
		while (rs.next()) {
			list.add(rs.getFloat("schoolScore"));
		}
		state.close();
		return list;
	}

	// 查询绩效得分最大的学校Id和分数
	public ArrayList getMaxSchoolScoreAndId(String areaName,String time)
			throws SQLException, ClassNotFoundException {
		String sql = "SELECT schoolScore,schoolId FROM tschoolindexscore,tschoolinfor WHERE schoolScore = (SELECT MAX(schoolScore) FROM tschoolindexscore,tschoolinfor WHERE indexType = 0 AND schoolArean = '"+areaName+"' AND tschoolinfor.autoId = schoolId AND userTime LIKE '"+time+"%') AND schoolArean = '"+areaName+"' AND tschoolinfor.autoId = schoolId AND indexType = 0 AND userTime LIKE '"+time+"%'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		ArrayList list = new ArrayList();
		while (rs.next()) {
			list.add(rs.getFloat("schoolScore"));
			list.add(rs.getInt("schoolId"));
		}
		state.close();
		return list;
	}
	
	//查询绩效得分最小的学校Id和分数
	public ArrayList getMinSchoolScoreAndId(String areaName,String time)
			throws SQLException, ClassNotFoundException {
		String sql = "SELECT schoolScore,schoolId FROM tschoolindexscore,tschoolinfor WHERE schoolScore = (SELECT MIN(schoolScore) FROM tschoolindexscore,tschoolinfor WHERE indexType = 0 AND schoolArean = '"+areaName+"' AND tschoolinfor.autoId = schoolId AND userTime LIKE '"+time+"%') AND schoolArean = '"+areaName+"' AND tschoolinfor.autoId = schoolId AND indexType = 0 AND userTime LIKE '"+time+"%'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		ArrayList list = new ArrayList();
		while (rs.next()) {
			list.add(rs.getFloat("schoolScore"));
			list.add(rs.getInt("schoolId"));
		}
		state.close();
		return list;
	}
	
	//查询某地区绩效平均分
	public float getAvgSchoolScore(String areaName,String time)
			throws SQLException, ClassNotFoundException {
		String sql = "SELECT AVG(schoolScore) AS num FROM tschoolindexscore,tschoolinfor WHERE indexType = 0 AND schoolArean = '"+areaName+"' AND tschoolinfor.autoId = schoolId AND userTime LIKE '"+time+"%'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		float avgScore = 0;
		while (rs.next()) {
			avgScore = Float.parseFloat(new java.text.DecimalFormat("#.##").format(rs.getFloat("num")));
		}
		state.close();
		return avgScore;
	}
	
	//查询高于平均分的学校数
	public int getUpAvgSchoolScore(String areaName,String time)
			throws SQLException, ClassNotFoundException {
		String sql = "SELECT COUNT(schoolId) AS num FROM tschoolindexscore,tschoolinfor WHERE schoolScore > (SELECT AVG(schoolScore) FROM tschoolindexscore,tschoolinfor WHERE indexType = 0 AND schoolArean = '"+areaName+"' AND tschoolinfor.autoId = schoolId AND userTime LIKE '"+time+"%') AND schoolArean = '"+areaName+"' AND tschoolinfor.autoId = schoolId AND userTime LIKE '"+time+"%' AND indexType = 0";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		int schoolNum = 0;
		while (rs.next()) {
			schoolNum = rs.getInt("num");
		}
		state.close();
		return schoolNum;
	}
	
	//查询低于平均分的学校数
	public int getDownAvgSchoolScore(String areaName,String time)
			throws SQLException, ClassNotFoundException {
		String sql = "SELECT COUNT(schoolId) AS num FROM tschoolindexscore,tschoolinfor WHERE schoolScore < (SELECT AVG(schoolScore) FROM tschoolindexscore,tschoolinfor WHERE indexType = 0 AND schoolArean = '"+areaName+"' AND tschoolinfor.autoId = schoolId AND userTime LIKE '"+time+"%') AND schoolArean = '"+areaName+"' AND tschoolinfor.autoId = schoolId AND userTime LIKE '"+time+"%' AND indexType = 0";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		int schoolNum = 0;
		while (rs.next()) {
			schoolNum = rs.getInt("num");
		}
		state.close();
		return schoolNum;
	}
}