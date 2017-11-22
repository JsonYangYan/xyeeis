package nercel.javaweb.qxschool;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import nercel.javaweb.allassessment.AssessmentDbUtil;

public class QxSchoolDbUtil {
	public Connection con = null;

	public void openConnection() throws SQLException, IOException,
			ClassNotFoundException {
		if (con == null || con.isClosed()) {

			Properties p = new Properties();
			InputStream in = AssessmentDbUtil.class
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

	// 根据区县用户名查找其所在区县的名称
	public String getArean(String qxname) throws SQLException,
			ClassNotFoundException {
		String sql = "SELECT * FROM tuser,tarea WHERE tuser.area_id=tarea.area_id and username='"
				+ qxname + "'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		String Arean = "";
		if (rs.next()) {
			Arean = rs.getString("area_name");
		}
		state.close();
		return Arean;
	}

	/**
	 * 查询学校
	 */
	public ArrayList querySchool(String schoolName, String areaName,String time)
			throws Exception {		
		ArrayList listQuery = new ArrayList();
		String sql = "select schoolName,schoolType,schoolArean from tschoolinfor WHERE state=1 AND userTime LIKE '"+ time + "%' AND schoolArean = '"
				+ areaName + "' and schoolName like '%" + schoolName + "%'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		HashMap hmQuery = new HashMap();
		while (rs.next()) {
			hmQuery.put("area", rs.getString("schoolArean"));
			hmQuery.put("name", rs.getString("schoolName"));
			hmQuery.put("type", rs.getString("schoolType"));
			listQuery.add(hmQuery);
		}
		state.close();
		return listQuery;
	}

	/**
	 * 获取各个区域，对应的小学、初中等
	 * 
	 * @return
	 * @throws Exception
	 */
	public ArrayList getEverySchool(String schoolArean,String time) throws Exception {
		ArrayList arrayList = new ArrayList();
		String sql = "SELECT schoolName,schoolType,schoolArean FROM tschoolinfor WHERE state=1 AND userTime LIKE '"+ time + "%' AND schoolArean = '"
				+ schoolArean + "'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()) {
			HashMap hashMap = new HashMap();
			hashMap.put("area", rs.getString("schoolArean"));
			hashMap.put("type", rs.getString("schoolType"));
			hashMap.put("name", rs.getString("schoolName"));
			arrayList.add(hashMap);
		}
		state.close();
		return arrayList;
	}

	/**
	 * 取出所有学校的学校id
	 */
	public ArrayList<Integer> getAllSchoolIdNumber(String time) throws Exception {
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		String sql = "SELECT autoId FROM tschoolinfor WHERE state=1 AND userTime LIKE '"+ time + "%'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()) {
			arrayList.add(Integer.parseInt(rs.getString("autoId")));
		}
		state.close();
		return arrayList;
	}

	/**
	 * 根据学校id获得一级指标得分
	 * 
	 * @param schoolId
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Float> getAllFirstIndexScore(int schoolId)
			throws Exception {
		ArrayList<Float> arrayList = new ArrayList<Float>();
		String sql = "SELECT schoolScore FROM tschoolindexscore  WHERE schoolId ="
				+ schoolId + " AND indexType = 1 ORDER BY indexType ASC";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()) {
			arrayList.add(Float.parseFloat(rs.getString("schoolScore")));
		}
		state.close();
		return arrayList;
	}

	/**
	 * 获得襄阳市一级指标得分
	 */
	public ArrayList getCityScore(String currentTime)
			throws ClassNotFoundException, SQLException {
		ArrayList listScore = new ArrayList();
		String sql = "SELECT AVG(oneone) AS oneone,AVG(onetwo) AS onetwo,AVG(onethree) AS onethree,AVG(onefour) AS onefour,AVG(onefive) AS onefive FROM tareascore WHERE createTime LIKE '"
				+ currentTime + "%'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()) {
			listScore.add(Float.parseFloat(new java.text.DecimalFormat("#.##").format(rs.getFloat("oneone"))));
			listScore.add(Float.parseFloat(new java.text.DecimalFormat("#.##").format(rs.getFloat("onetwo"))));
			listScore.add(Float.parseFloat(new java.text.DecimalFormat("#.##").format(rs.getFloat("onethree"))));
			listScore.add(Float.parseFloat(new java.text.DecimalFormat("#.##").format(rs.getFloat("onefour"))));
			listScore.add(Float.parseFloat(new java.text.DecimalFormat("#.##").format(rs.getFloat("onefive"))));
		}
		state.close();
		return listScore;
	}
	
	/**
	 * 获得襄阳市各区域的一级指标得分
	 */
	public ArrayList getAreanScore(String areaName,String currentTime)
			throws ClassNotFoundException, SQLException {
		ArrayList listScore = new ArrayList();
		String sql = "SELECT * FROM tareascore WHERE areaName = '"+areaName+"' AND createTime LIKE '"+currentTime+"%'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()) {
			listScore.add(Float.parseFloat(new java.text.DecimalFormat("#.##").format(rs.getFloat("oneone"))));
			listScore.add(Float.parseFloat(new java.text.DecimalFormat("#.##").format(rs.getFloat("onetwo"))));
			listScore.add(Float.parseFloat(new java.text.DecimalFormat("#.##").format(rs.getFloat("onethree"))));
			listScore.add(Float.parseFloat(new java.text.DecimalFormat("#.##").format(rs.getFloat("onefour"))));
			listScore.add(Float.parseFloat(new java.text.DecimalFormat("#.##").format(rs.getFloat("onefive"))));
		}
		state.close();
		return listScore;
	}
	
	/**
	 * 取出学校所在的区域
	 */
	public String getSchoolArean(String schoolName,String currentTime) throws Exception {
		String schoolArean = null;
		String sql = "SELECT schoolArean FROM tschoolinfor WHERE schoolName ='"
				+ schoolName + "' AND userTime LIKE '" + currentTime + "%'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()) {
			schoolArean = rs.getString("schoolArean");
		}
		state.close();
		return schoolArean;
	}

	/**
	 * 取出区域所有学校的学校id
	 */
	public ArrayList<Integer> getSchoolAreanNumber(String schoolArean,String time)
			throws Exception {
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		String sql = "SELECT autoId FROM tschoolinfor WHERE state=1 AND userTime LIKE '"+ time + "%' AND schoolArean = '"
				+ schoolArean + "'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()) {
			arrayList.add(Integer.parseInt(rs.getString("autoId")));
		}
		state.close();
		return arrayList;
	}

	/**
	 * 取出每个学校的学校id
	 * 
	 */
	public ArrayList<Integer> getEachSchoolIdNumber(String schoolName,String time)
			throws Exception {
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		String sql = "SELECT autoId FROM tschoolinfor WHERE state=1 AND userTime LIKE '"+ time + "%' AND schoolName ='"
				+ schoolName + "'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()) {
			arrayList.add(Integer.parseInt(rs.getString("autoId")));
		}
		state.close();
		return arrayList;
	}

}
