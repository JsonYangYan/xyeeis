package nercel.javaweb.tpstatistics;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.text.NumberFormat;

public class TpDbStatistics {

	private Connection con = null;

	public void openConnection() throws SQLException, IOException,
			ClassNotFoundException {
		if (con == null || con.isClosed()) {

			Properties p = new Properties();
			InputStream in = nercel.javaweb.tpstatistics.TpDbStatistics.class
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

	// 全局变量
	/*
	 * double totalsch_num = 0; //襄阳市全部教学点数量 double subsch_num = 0;
	 * //襄阳市已提交的教学点数量 int schTotalNum = 0; //某区县全部教学点数量 int schSubNum = 0;
	 * //某区县已提交的教学点数量
	 */
	/**
	 * 获得所有区县的名称
	 */
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

	/**
	 * 襄阳市参加评估的教学点总数
	 * 
	 * @author Stone
	 * @time 2016-11-10
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public double gettotalSchoolnum() throws SQLException,
			ClassNotFoundException {
		int totalsch_num = 0;
		String sql = "SELECT count(*) as totalsch_num FROM tschooluser WHERE role = 'tpuser'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		if (rs.next()) {
			totalsch_num = rs.getInt("totalsch_num");
		}
		state.close();
		return totalsch_num;
	}

	/**
	 * 襄阳市已经提交过问卷的教学点数量
	 * 
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public double getsubSchoolnum(String currentTime) throws SQLException,
			ClassNotFoundException {
		int subsch_num = 0;
		String sql = "SELECT count(autoId) as subsch_num FROM tpschoolinfor WHERE state=1 AND userTime like '"
				+ currentTime + "%'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		if (rs.next()) {
			subsch_num = rs.getInt("subsch_num");
		}
		state.close();
		return subsch_num;
	}

	/**
	 * 某县区参加评估的教学点总数
	 * 
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public int getAreantotalSchoolnum(int AreanId) throws SQLException,
			ClassNotFoundException {
		int totalsch_num = 0;
		String sql = "SELECT count(username) as totalsch_num FROM tschooluser WHERE area_id="
				+ AreanId + " AND role = 'tpuser'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		if (rs.next()) {
			totalsch_num = rs.getInt("totalsch_num");
		}
		state.close();
		return totalsch_num;
	}

	/**
	 * 某县区已经提交过问卷的教学点数量
	 * 
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public int getAreansubSchoolnum(String AreaName, String time)
			throws SQLException, ClassNotFoundException {
		int subsch_num = 0;
		String sql = "SELECT count(username) as subsch_num FROM tpschoolinfor WHERE state=1 AND userTime like '"
				+ time + "%' and schoolArean='" + AreaName + "'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		if (rs.next()) {
			subsch_num = rs.getInt("subsch_num");
		}
		state.close();
		return subsch_num;
	}

	/**
	 * 根据区县所在地区的Id获得区县名称
	 * @param areaId
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public String getAreaName(int areaId) throws SQLException,
			ClassNotFoundException {
		String areaName = "";
		String sql = "SELECT area_name FROM tarea WHERE area_id = " + areaId
				+ " ";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		if (rs.next()) {
			areaName = rs.getString("area_name");
		}
		state.close();
		return areaName;
	}
	
	/**
	 * 根据区县所在地区的区县名称获得区县Id
	 * @param areaId
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public int getAreaId(String areaName) throws SQLException,
			ClassNotFoundException {
		int areaId = 0;
		String sql = "SELECT area_id FROM tarea WHERE area_name = '" +areaName+ "' ";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		if (rs.next()) {
			areaId = rs.getInt("area_id");
		}
		state.close();
		return areaId;
	}

	/**
	 * 统计各区县教学点已提交和未提交名单
	 * @param areaId
	 * @param time
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public ArrayList getSchool(int areaId, String time)
			throws ClassNotFoundException, SQLException {
		String areaName = getAreaName(areaId);
		ArrayList list = new ArrayList();
		String sql = "SELECT c.sch_name,d.schoolArean,IFNULL(d.state,0) state FROM tschooluser c LEFT JOIN (SELECT a.username,a.sch_name,b.schoolArean,IFNULL(b.state,0) state , b.userTime  FROM tschooluser a LEFT JOIN tpschoolinfor b ON a.username=b.userName  WHERE a.area_id ="
				+ areaId
				+ " and b.userTime LIKE '"
				+ time
				+ "%' AND role = 'tpuser') d ON c.username = d.username WHERE c.area_id="
				+ areaId
				+ " AND role = 'tpuser' ORDER BY state ASC";
		
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()) {
			Map mapAll = new HashMap();
			mapAll.put("area", areaName);
			mapAll.put("name", rs.getString("sch_name"));
			mapAll.put("status", rs.getString("state"));
			list.add(mapAll);
		}
		state.close();
		return list;
	}

}
