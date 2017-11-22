package nercel.javaweb.statistics;

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

public class DbStatistics {

	private Connection con = null;

	public void openConnection() throws SQLException, IOException,
			ClassNotFoundException {
		if (con == null || con.isClosed()) {

			Properties p = new Properties();
			InputStream in = nercel.javaweb.statistics.DbStatistics.class
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
	 * 襄阳市参加评估的学校总数
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
		String sql = "SELECT count(*) as totalsch_num FROM tschooluser WHERE role = 'user'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		if (rs.next()) {
			totalsch_num = rs.getInt("totalsch_num");
		}
		state.close();
		return totalsch_num;
	}

	/**
	 * 襄阳市已经提交过问卷的学校数量
	 * 
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public double getsubSchoolnum(String currentTime) throws SQLException,
			ClassNotFoundException {
		int subsch_num = 0;
		String sql = "SELECT count(autoId) as subsch_num FROM tschoolinfor WHERE state=1 AND userTime like '"
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
	 * 某县区参加评估的学校总数
	 * 
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public int getAreantotalSchoolnum(int AreanId) throws SQLException,
			ClassNotFoundException {
		int totalsch_num = 0;
		String sql = "SELECT count(username) as totalsch_num FROM tschooluser WHERE area_id="
				+ AreanId + " AND role = 'user'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		if (rs.next()) {
			totalsch_num = rs.getInt("totalsch_num");
		}
		state.close();
		return totalsch_num;
	}

	/**
	 * 某县区已经提交过问卷的学校数量
	 * 
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public int getAreansubSchoolnum(String AreaName, String time)
			throws SQLException, ClassNotFoundException {
		int subsch_num = 0;
		String sql = "SELECT count(username) as subsch_num FROM tschoolinfor WHERE state=1 AND userTime like '"
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

	// 统计分析 已提交问卷的学校有哪些，未提交的有哪些

	/*// 某区县的全部教学点名称
	public String[] getAllSchoolName(int AreanId)
			throws ClassNotFoundException, SQLException {
		int id = AreanId;
		int schTotalNum = (int) getAreantotalSchoolnum(id);
		String arrAllSchName[] = new String[schTotalNum];
		int i = 0;
		String sql = "SELECT sch_name FROM tschooluser WHERE area_id=" + id
				+ " AND role = 'tpuser'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()) {
			String strTemp = rs.getString("sch_name");
			arrAllSchName[i] = strTemp;
			i++;
		}
		state.close();
		return arrAllSchName;
	}

	// 某区县已参加评估的教学点名称
	public String[] getComSchoolName(String AreaName, String time)
			throws ClassNotFoundException, SQLException {
		int schSubNum = 0;
		schSubNum = (int) getAreansubSchoolnum(AreaName, time);
		String arrComSchName[] = new String[schSubNum];
		int i = 0;
		String sql = "SELECT schoolName FROM tpschoolinfor WHERE state=1 AND userTime like '"
				+ time + "%' and schoolArean='" + AreaName + "'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()) {
			arrComSchName[i] = rs.getString("schoolName");
			i++;
		}
		state.close();
		return arrComSchName;
	}

	// 已提交学校,拼成相应json格式的数据
	public ArrayList getCommitSchoolName(String AreaName, String time)
			throws ClassNotFoundException, SQLException {
		ArrayList list = new ArrayList();
		String sql = "SELECT schoolName,schoolArean FROM tschoolinfor WHERE state=1 AND userTime like '"
				+ time + "%' and schoolArean='" + AreaName + "'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()) {
			Map mapCommit = new HashMap();
			mapCommit.put("area", rs.getString("schoolArean"));
			mapCommit.put("name", rs.getString("schoolName"));
			mapCommit.put("status", "1");
			list.add(mapCommit);
		}
		state.close();
		return list;
	}

	// 未提交学校，需要从总学校名称和已提交的学校名称作比较来得到
	public ArrayList getUnCommitSchoolName(int areaId, String time)
			throws ClassNotFoundException, SQLException {
		String areaName = getAreaName(areaId);
		int schTotalNum = getAreantotalSchoolnum(areaId);
		int schSubNum = getAreansubSchoolnum(areaName, time);
		ArrayList list = new ArrayList();
		String arrAllSchName[] = getAllSchoolName(areaId);
		String arrComSchName[] = getComSchoolName(areaName, time);
		String arrUnComSchName[] = new String[schTotalNum - schSubNum];
		int k = 0;
		for (int i = 0; i < arrAllSchName.length; i++) {
			boolean fig = true;
			String temp = arrAllSchName[i];
			for (int j = 0; j < arrComSchName.length; j++) {
				if (temp.equals(arrComSchName[j])) {
					fig = false;
					break;
				}
			}
			if (fig) {
				arrUnComSchName[k++] = temp;
			}
		}
		for (int m = 0; m < arrUnComSchName.length; m++) {
			Map mapUnCommit = new HashMap();
			mapUnCommit.put("area", areaName);
			mapUnCommit.put("name", arrUnComSchName[m]);
			mapUnCommit.put("status", "0");
			list.add(mapUnCommit);
		}
		return list;
	}

	// 已提交学校和未提交学校
	public ArrayList getSchoolName(int id, String time)
			throws ClassNotFoundException, SQLException {
		int areaId = id;
		String areaName = getAreaName(areaId);
		ArrayList listCommit = new ArrayList();
		ArrayList listUnCommit = new ArrayList();
		listUnCommit = getUnCommitSchoolName(areaId, time);
		listCommit = getCommitSchoolName(areaName, time);
		listUnCommit.addAll(listCommit);
		return listUnCommit;
	}*/
	
	public ArrayList getSchool(int areaId, String time)
			throws ClassNotFoundException, SQLException {
		String areaName = getAreaName(areaId);
		ArrayList list = new ArrayList();
		String sql = "SELECT c.sch_name,d.schoolArean,IFNULL(d.state,0) state FROM tschooluser c LEFT JOIN (SELECT a.username,a.sch_name,b.schoolArean,IFNULL(b.state,0) state , b.userTime  FROM tschooluser a LEFT JOIN tschoolinfor b ON a.username=b.userName  WHERE a.area_id ="
				+ areaId
				+ " and b.userTime LIKE '"
				+ time
				+ "%' AND a.role = 'user') d ON c.username = d.username WHERE c.area_id="
				+ areaId
				+ " AND c.role = 'user' ORDER BY state ASC";
		
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
