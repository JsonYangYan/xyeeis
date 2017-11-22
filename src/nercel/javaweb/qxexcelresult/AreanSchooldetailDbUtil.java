package nercel.javaweb.qxexcelresult;

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
import java.util.Map;
import java.util.Properties;

public class AreanSchooldetailDbUtil {
	private Connection con = null;

	public void openConnection() throws SQLException, IOException,
			ClassNotFoundException {
		if (con == null || con.isClosed()) {

			Properties p = new Properties();
			InputStream in = nercel.javaweb.qxexcelresult.AreanSchooldetailDbUtil.class
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

	static String areaName = "";

	// 根据登陆名查找到所在区域的名称
	public String getArean(String qxusername) throws SQLException,
			ClassNotFoundException {
		String sql = "SELECT * FROM tuser,tarea WHERE tuser.area_id=tarea.area_id and username='"
				+ qxusername + "'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		if (rs.next()) {
			areaName = rs.getString("area_name");
		}
		state.close();
		return areaName;
	}
	
	/**
	 * 根据tschoolinfor.autoId获取schoolName
	 * @throws SQLException 
	 */
	public String getSchoolNamebyId(int schoolId) throws SQLException{
		String schoolName = "";
		String sql = "SELECT schoolName FROM tschoolinfor WHERE autoId= "
				+ schoolId + "";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()) {
			schoolName = rs.getString("schoolName");
		}
		state.close();
		
		return schoolName;
		
	}
	
	// 获得各个学校每个题的填报情况
	/**
	 * 
	 * @param schoolName
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Map getSchooldetail(int schoolId,String time)
			throws ClassNotFoundException, SQLException {
		HashMap map = new HashMap();
		String sql = "SELECT schoolName,classNumber,teacherNumber,studentNumber,blankText FROM tschoolinfor,tblankanswer WHERE state=1 AND tschoolinfor.userTime LIKE '"
				+ time
				+ "%' AND tschoolinfor.autoId=tblankanswer.schoolId AND tschoolinfor.autoId="
				+ schoolId + " ORDER BY queId";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		ArrayList<String> list_answer = new ArrayList<String>();
		while (rs.next()) {
			list_answer.add(rs.getString("blankText"));
			int num = list_answer.size();
			map.put("c" + "1", rs.getString("schoolName"));
			map.put("c" + "2", rs.getInt("classNumber"));
			map.put("c" + "3", rs.getInt("teacherNumber"));
			map.put("c" + "4", rs.getInt("studentNumber"));
			for (int k = 0; k < num; k++) {
				map.put("c" + (k + 5), list_answer.get(k));
			}
		}
		state.close();
		return map;
	}

	/**
	 * 根据区域名称查找到该区域的全部学校名称，返回list
	 * 
	 * @param schoolArean
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public ArrayList getSchoolId(String schoolArean,String time) throws SQLException,
			ClassNotFoundException {
		String sql = "SELECT autoId FROM tschoolinfor WHERE state=1 AND userTime LIKE '"
				+ time + "%' AND schoolArean= '"
				+ schoolArean + "'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		ArrayList listshcoolId = new ArrayList();
		while (rs.next()) {
			listshcoolId.add(rs.getInt("autoId"));
		}
		state.close();
		return listshcoolId;
	}

	/**
	 * 每个地区的学校数量
	 * 
	 * @param schoolArean
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public int getschoolNum(String schoolArean,String time) throws SQLException,
			ClassNotFoundException {
		String sql = "SELECT count(schoolName) as schoolNum FROM tschoolinfor WHERE state=1 AND userTime LIKE '"
				+ time + "%' AND schoolArean= '"
				+ schoolArean + "'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		int schoolNum = 0;
		while (rs.next()) {
			schoolNum = rs.getInt("schoolNum");
		}
		state.close();
		return schoolNum;
	}

	/**
	 * 返回每个填报上去有多少
	 * 
	 * @param schoolArean
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public ArrayList<String> getSchoolAreaName(String schoolArean,String time)
			throws SQLException, ClassNotFoundException {
		String sql = "SELECT SchoolName FROM tschoolinfor WHERE state=1 AND userTime LIKE '"
				+ time + "%' AND schoolArean= '"
				+ schoolArean + "'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		ArrayList<String> listSchoolName = new ArrayList<String>();
		while (rs.next()) {
			listSchoolName.add(rs.getString("SchoolName"));
		}
		state.close();
		return listSchoolName;
	}

	/**
	 * 统计襄阳市一共用多少区县，把区县名字存放到list中
	 * 
	 * @author stone
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public ArrayList getArean() throws SQLException, ClassNotFoundException {
		String strSql = "SELECT area_name FROM tarea";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(strSql);
		ArrayList listArean = new ArrayList();
		while (rs.next()) {
			listArean.add(rs.getString("area_name"));
		}
		state.close();
		return listArean;
	}

	/**
	 * 各个区域的全部学校的信息情况
	 * 
	 * @param schoolArean
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public ArrayList getDetail(String schoolArean,String time) throws SQLException,
			ClassNotFoundException {
		ArrayList list = new ArrayList();
		ArrayList listSchoolId = new ArrayList();
		listSchoolId = getSchoolId(schoolArean,time); // 每个区域有多少所学校

		for (int i = 0; i < listSchoolId.size(); i++) {
			Map mapSchool = new HashMap();
			mapSchool = getSchooldetail(Integer.parseInt(listSchoolId.get(i).toString()),time); // 获取一个学校的信息
			list.add(mapSchool);
		}
		return list;
	}

}
