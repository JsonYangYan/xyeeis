package nercel.javaweb.qxstatistics;

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

import net.sf.json.JSONObject;

public class DbQxStatistics {

	private Connection con = null;

	public void openConnection() throws SQLException, IOException,
			ClassNotFoundException {
		if (con == null || con.isClosed()) {

			Properties p = new Properties();
			InputStream in = nercel.javaweb.qxstatistics.DbQxStatistics.class
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
	double totalsch_num = 0;
	double subsch_num = 0;
	int AreanId = 0;
	String AreaName = "";

	/**
	 * 根据登录时获得的登录名（session），找出所在区县的area_id
	 */
	public int getAreanId(String qxusername) throws SQLException,
			ClassNotFoundException {
		String sql = "SELECT area_id FROM tuser WHERE username='" + qxusername
				+ "'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		if (rs.next()) {
			AreanId = rs.getInt("area_id");
		}
		state.close();
		return AreanId;
	}

	/**
	 * 根据登录时获得的登录名（session），找出所在区县的名称
	 */
	public String getAreanName(String qxusername) throws SQLException,
			ClassNotFoundException {
		String sql = "SELECT area_name FROM tuser,tarea WHERE tarea.area_id=tuser.area_id and username='"
				+ qxusername + "'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		if (rs.next()) {
			AreaName = rs.getString("area_name");
		}
		state.close();
		return AreaName;
	}

	/**
	 * 某县区参加评估的学校总数
	 * 
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public double gettotalSchoolnum() throws SQLException,
			ClassNotFoundException {
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
	public double getsubSchoolnum(String time) throws SQLException,
			ClassNotFoundException {
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
	 * 某县区已经填报学校所在百分比
	 * 
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public ArrayList getWholeStatistics(String time) throws SQLException,
			ClassNotFoundException {
		ArrayList list = new ArrayList();
		totalsch_num = gettotalSchoolnum();
		subsch_num = getsubSchoolnum(time);
		float submitschpercent = 0;
		float unsubmitschpercent = 0;
		int schSubNum = (int) getsubSchoolnum(time);
		/*
		 * NumberFormat nt = NumberFormat.getPercentInstance();
		 * nt.setMinimumFractionDigits(2);
		 */
		if (totalsch_num != 0) {
			submitschpercent = Float.parseFloat(new java.text.DecimalFormat(
					"#.##").format(subsch_num / totalsch_num * 100));
			unsubmitschpercent = Float.parseFloat(new java.text.DecimalFormat(
					"#.##").format(100.00 - submitschpercent));
			HashMap map = new HashMap();
			map.put("name", "已提交");
			map.put("value", submitschpercent);
			map.put("totalnum", totalsch_num);
			map.put("subnum", subsch_num);
			map.put("area", AreaName);
			HashMap map_1 = new HashMap();
			map_1.put("name", "未提交");
			map_1.put("value", unsubmitschpercent);
			map_1.put("totalnum", totalsch_num);
			map_1.put("subnum", subsch_num);
			map_1.put("area", AreaName);
			list.add(map);
			list.add(map_1);
		}
		return list;
	}

	// //////////////////////统计分析 某县区已提交问卷的学校有哪些，未提交的有哪些
	// 某区县的全部学校名称
	/*public String[] getAllSchoolName() throws ClassNotFoundException,
			SQLException {
		int schTotalNum = (int) gettotalSchoolnum();
		String arrAllSchName[] = new String[schTotalNum];
		int i = 0;
		String sql = "SELECT sch_name FROM tschooluser WHERE area_id="
				+ AreanId + "";
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

	// 某区县已参加评估的学校名称
	public String[] getComSchoolName(String time)
			throws ClassNotFoundException, SQLException {
		int schSubNum = (int) getsubSchoolnum(time);
		String arrComSchName[] = new String[schSubNum];
		int i = 0;
		String sql = "SELECT schoolName FROM tschoolinfor WHERE state=1 AND userTime like '"
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
	public ArrayList getCommitSchoolName(String time)
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
	public ArrayList getUnCommitSchoolName(String time)
			throws ClassNotFoundException, SQLException {
		ArrayList list = new ArrayList();
		String arrAllSchName[] = getAllSchoolName();
		String arrComSchName[] = getComSchoolName(time);
		int schTotalNum = (int) gettotalSchoolnum();
		int schSubNum = (int) getsubSchoolnum(time);
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
			mapUnCommit.put("area", AreaName);
			mapUnCommit.put("name", arrUnComSchName[m]);
			mapUnCommit.put("status", "0");
			list.add(mapUnCommit);
		}
		return list;
	}*/

	// 调用函数的入口
	/*public ArrayList getSchoolName(String time) throws ClassNotFoundException,
			SQLException {
		ArrayList listCommit = new ArrayList();
		ArrayList listUnCommit = new ArrayList();
		listUnCommit = getUnCommitSchoolName(time);
		listCommit = getCommitSchoolName(time);
		listUnCommit.addAll(listCommit);
		return listUnCommit;
	}*/
	
	// 查询学校提交情况
	public ArrayList searchSchool(String schoolName, String time)
			throws ClassNotFoundException, SQLException {
		ArrayList list = new ArrayList();
		String sql = "";
		if(schoolName!=""){
			sql = "SELECT c.sch_name,d.schoolArean,IFNULL(d.state,0) state FROM tschooluser c LEFT JOIN (SELECT a.username,a.sch_name,b.schoolArean,IFNULL(b.state,0) state , b.userTime  FROM tschooluser a LEFT JOIN tschoolinfor b ON a.username=b.userName  WHERE a.area_id ="
					+ AreanId
					+ " AND a.role = 'user' AND b.userTime LIKE '"
					+ time
					+ "%') d ON c.username = d.username WHERE c.area_id="
					+ AreanId
					+ " AND c.sch_name LIKE '%" + schoolName + "%' AND c.role = 'user' ORDER BY state ASC";
		}else{
			sql = "SELECT c.sch_name,d.schoolArean,IFNULL(d.state,0) state FROM tschooluser c LEFT JOIN (SELECT a.username,a.sch_name,b.schoolArean,IFNULL(b.state,0) state , b.userTime  FROM tschooluser a LEFT JOIN tschoolinfor b ON a.username=b.userName  WHERE a.area_id ="
					+ AreanId
					+ " AND a.role = 'user' AND b.userTime LIKE '"
					+ time
					+ "%') d ON c.username = d.username WHERE c.area_id="
					+ AreanId
					+ " AND c.role = 'user' ORDER BY state ASC"; 
		}
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()) {
			Map mapAll = new HashMap();
			mapAll.put("area", AreaName);
			mapAll.put("name", rs.getString("sch_name"));
			mapAll.put("status", rs.getString("state"));
			list.add(mapAll);
		}
		state.close();
		return list;
	}
	
}