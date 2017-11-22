package nercel.javaweb.qxcurrentdata;

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
import java.util.Properties;

public class AssessmentDbUtil {
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
		
	// /////////////////////////////////////////////////////////////////	
	
	/**
	 * 获取对应区域的id
	 * 
	 * @param schoolArean
	 * @return
	 * @throws Exception
	 */

	public ArrayList<Integer> getSomeSchoolIdNumber(String schoolArean,
			String currentTime) throws Exception {
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		String sql = "SELECT autoId FROM tschoolinfor WHERE state = 1 AND schoolArean ='"
				+ schoolArean + "' AND userTime LIKE '" + currentTime + "%'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()) {
			arrayList.add(Integer.parseInt(rs.getString("autoId")));
		}
		state.close();
		return arrayList;
	}

	/**
	 * 获取提交的所有学校id
	 * 
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Integer> getAllSchoolIdNumber(String currentTime)
			throws Exception {
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		String sql = "SELECT autoId FROM tschoolinfor WHERE state =1 AND userTime LIKE '"
				+ currentTime + "%'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()) {
			arrayList.add(Integer.parseInt(rs.getString("autoId")));
		}
		state.close();
		return arrayList;
	}
	

	/**
	 * 取出选择题每个选项有多少人选择
	 * 
	 * @param choiceId
	 * @return
	 * @throws Exception 
	 */
	public int getChoiceSum(int choiceId,String areaName, String currentTime) throws Exception {
		ArrayList listSchId = new ArrayList();
		String tchoice_sql = "SELECT count(*) AS sum from tchoiceanswer, tschoolinfor WHERE tschoolinfor.state = 1 AND tchoiceanswer.schoolId = tschoolinfor.autoId AND tschoolinfor.schoolArean = '"+areaName+"' AND choiceId="
				+ choiceId + " AND tchoiceanswer.userTime LIKE '" + currentTime+ "%'";
		//System.out.println(tchoice_sql);
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(tchoice_sql);
		int sum = 0;
		if (rs.next()) {
			sum = Integer.parseInt(rs.getString("sum"));
		}
		return sum;
	}

	/**
	 * 输入选择题题号，计算出襄阳市每个选项情况
	 * 
	 * @param queId
	 * @return
	 * @throws Exception
	 */
	public ArrayList getChoiceValue(int queId, String areaName,String currentTime) throws Exception {
		ArrayList list = new ArrayList();
		String tchoice_sql = "SELECT autoId,choiceContent FROM tchoice WHERE queId="
				+ queId;
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(tchoice_sql);
		int schoolNumber = getSomeSchoolIdNumber(areaName,currentTime).size();
		while (rs.next()) {
			HashMap map = new HashMap();
			map.put("name", rs.getString("choiceContent"));
			int sum = getChoiceSum(rs.getInt("autoId"),areaName, currentTime);
			//System.out.println(sum);
			if (schoolNumber != 0) {
				map.put("value", Float.parseFloat(new java.text.DecimalFormat(
						"#.00").format(sum * 100.0 / schoolNumber)));
			} else {
				map.put("value", 0);
			}
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 获得某区县三级指标的平均值
	 */
	public ArrayList getTownAvg(String schoolIds) throws ClassNotFoundException, SQLException {
		ArrayList listAvg = new ArrayList();
		String sql = "SELECT AVG(blankText) as avg FROM tblankanswer WHERE schoolId in ("+ schoolIds + ") AND queId !=129 GROUP BY queId ORDER BY queId";
		//System.out.println(sql);
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while(rs.next()){
			listAvg.add(Float.parseFloat(new java.text.DecimalFormat("#.##")
			.format((float) rs.getFloat("avg"))));
		}
		state.close();
		return listAvg;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////教育概况////////////////////////////////////////////////////////////////
	
	/**
	 * 某区县高中人数
	 * 
	 */
	public int highSchoolNum(String areaName,String currentTime) throws ClassNotFoundException, SQLException{
		int highSchoolNum = 0;
		String sql = "SELECT COUNT(autoId) AS num FROM tschoolinfor WHERE schoolType = '高中' AND schoolArean = '"+areaName+"' AND state=1 AND userTime LIKE '" + currentTime+ "%'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		if(rs.next()){
			highSchoolNum = rs.getInt("num");
		}
		state.close();
		return highSchoolNum;
		
	}
	
	/**
	 * 某区县初中人数
	 * 
	 */
	public int juniorSchoolNum(String areaName,String currentTime) throws ClassNotFoundException, SQLException{
		int juniorSchoolNum = 0;
		String sql = "SELECT COUNT(autoId) AS num FROM tschoolinfor WHERE schoolType = '初中' AND schoolArean = '"+areaName+"' AND state=1 AND userTime LIKE '" + currentTime+ "%'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		if(rs.next()){
			juniorSchoolNum = rs.getInt("num");
		}
		state.close();
		return juniorSchoolNum;
		
	}
	
	/**
	 * 某区县小学人数
	 * 
	 */
	public int primarySchoolNum(String areaName,String currentTime) throws ClassNotFoundException, SQLException{
		int primarySchoolNum = 0;
		String sql = "SELECT COUNT(autoId) AS num FROM tschoolinfor WHERE schoolType = '小学' AND schoolArean = '"+areaName+"' AND state=1 AND userTime LIKE '" + currentTime+ "%'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		if(rs.next()){
			primarySchoolNum = rs.getInt("num");
		}
		state.close();
		return primarySchoolNum;
		
	}
	
	/**
	 * 某区县其他类型年级人数
	 * 
	 */
	public int otherSchoolNum(String areaName,String currentTime) throws ClassNotFoundException, SQLException{
		int otherSchoolNum = 0;
		String sql = "SELECT COUNT(autoId) AS num FROM tschoolinfor WHERE schoolType = '其他' AND schoolArean = '"+areaName+"' AND state=1 AND userTime LIKE '" + currentTime+ "%'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		if(rs.next()){
			otherSchoolNum = rs.getInt("num");
		}
		state.close();
		return otherSchoolNum;
		
	}
	
	/**
	 * 某区县教师人数
	 * 
	 */
	public int teacherNum(String areaName,String currentTime) throws ClassNotFoundException, SQLException{
		int teacherNum = 0;
		String sql = "SELECT sum(teacherNumber) AS num FROM tschoolinfor WHERE schoolArean = '"+areaName+"' AND state=1 AND userTime LIKE '" + currentTime+ "%'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		if(rs.next()){
			teacherNum = rs.getInt("num");
		}
		state.close();
		return teacherNum;
		
	}
	
	/**
	 * 某区县学生人数
	 * 
	 */
	public int studentNum(String areaName,String currentTime) throws ClassNotFoundException, SQLException{
		int studentNum = 0;
		String sql = "SELECT sum(studentNumber) AS num FROM tschoolinfor WHERE schoolArean = '"+areaName+"' AND state=1 AND userTime LIKE '" + currentTime+ "%'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		if(rs.next()){
			studentNum = rs.getInt("num");
		}
		state.close();
		return studentNum;
		
	}
	/**
	 * 某区县城镇学校数
	 * 
	 */
	public int townSchoolNum(String areaName,String currentTime) throws ClassNotFoundException, SQLException{
		int townSchoolNum = 0;
		String sql = "SELECT COUNT(autoId) AS num FROM tschoolinfor WHERE schoolTown = '城镇' AND schoolArean = '"+areaName+"' AND state=1 AND userTime LIKE '" + currentTime+ "%'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		if(rs.next()){
			townSchoolNum = rs.getInt("num");
		}
		state.close();
		return townSchoolNum;
		
	}
	
	/**
	 * 某区县农村学校数
	 * 
	 */
	public int villageSchoolNum(String areaName,String currentTime) throws ClassNotFoundException, SQLException{
		int villageSchoolNum = 0;
		String sql = "SELECT COUNT(autoId) AS num FROM tschoolinfor WHERE schoolTown = '农村' AND schoolArean = '"+areaName+"' AND state=1 AND userTime LIKE '" + currentTime+ "%'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		if(rs.next()){
			villageSchoolNum = rs.getInt("num");
		}
		state.close();
		return villageSchoolNum;
		
	}
}
