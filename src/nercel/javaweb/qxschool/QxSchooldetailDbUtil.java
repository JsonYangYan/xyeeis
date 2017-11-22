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
import java.util.Map;
import java.util.Properties;

public class QxSchooldetailDbUtil {
	private Connection con = null;

	public void openConnection() throws SQLException, IOException,
			ClassNotFoundException {
		if (con == null || con.isClosed()) {
			Properties p = new Properties();
			InputStream in = nercel.javaweb.qxschool.QxSchooldetailDbUtil.class
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

	// 通过schoolName查找所在区
	public String getArean(String schoolName) throws SQLException,
			ClassNotFoundException {
		String sql = "SELECT schoolArean FROM tschoolinfor WHERE schoolName='"
				+ schoolName + "'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		String arean = "";
		while (rs.next()) {
			arean = rs.getString("schoolArean");
		}
		state.close();
		return arean;
	}

	// 获得各个学校每个题的填报情况
	public Map getSchooldetail(String schoolName,String time)
			throws ClassNotFoundException, SQLException {
		HashMap map = new HashMap();
		String sql = "SELECT schoolName,classNumber,teacherNumber,studentNumber,blankText FROM tschoolinfor,tblankanswer WHERE tschoolinfor.autoId=tblankanswer.schoolId AND state=1 AND tschoolinfor.userTime LIKE '"
				+ time
				+ "%' AND tschoolinfor.schoolName='"
				+ schoolName
				+ "' ORDER BY queId";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		ArrayList<Float> list_answer = new ArrayList<Float>();
		while (rs.next()) {
			list_answer.add(Float.parseFloat(rs.getString("blankText")));
			int num = list_answer.size();
			map.put("c" + "0", 1);
			map.put("c" + "1", rs.getString("schoolName"));
			map.put("c" + "2", rs.getInt("classNumber"));
			map.put("c" + "3", rs.getInt("teacherNumber"));
			map.put("c" + "4", rs.getInt("studentNumber"));
			for (int k = 0; k < num; k++) {
				map.put("c" + (k + 5), Float
						.parseFloat(new java.text.DecimalFormat("#.##")
								.format(list_answer.get(k))));
			}
		}
		state.close();
		return map;
	}

	/**
	 * 根据学校的名字查出这个区所有学校的Id，返回list
	 * 
	 * @param schoolName
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public ArrayList getschoolId(String schoolName,String time) throws SQLException,
			ClassNotFoundException {

		String sql = "SELECT autoId FROM tschoolinfor WHERE state=1 AND userTime LIKE '"
				+ time
				+ "%' AND schoolArean=(SELECT schoolArean FROM tschoolinfor WHERE schoolName='"
				+ schoolName + "' AND userTime LIKE '"
				+ time
				+ "%' )";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		ArrayList list = new ArrayList();
		while (rs.next()) {
			list.add(rs.getInt("autoId"));
		}
		state.close();
		return list;
	}

	// 襄阳市已经提交学校的schoolId，state=1
	public ArrayList getAllCityschoolId(String currentTime)
			throws SQLException, ClassNotFoundException {
		String sql = "SELECT autoId FROM tschoolinfor WHERE state=1 AND userTime LIKE '"
				+ currentTime + "%'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		ArrayList list = new ArrayList();
		while (rs.next()) {
			list.add(rs.getInt("autoId"));
		}
		state.close();
		return list;
	}

	// 把schoolId用逗号分开
	private String getSchoolIds(ArrayList schoolId) {
		String schoolIds = "";
		for (int i = 0; i < schoolId.size(); i++) {
			schoolIds += schoolId.get(i) + ",";
		}
		// 去除掉最后一个逗号
		if (!schoolIds.equals(""))
			schoolIds = schoolIds.substring(0, schoolIds.length() - 1);
		return schoolIds;
	}

	// 获得各个地区每个题的综合填报情况
	public Map getAreandetail(String schoolName,String time) throws ClassNotFoundException,
			SQLException {
		ArrayList schoolIdList = getschoolId(schoolName,time);
		String schoolIds = getSchoolIds(schoolIdList);
		HashMap map = new HashMap();
		String sql = "SELECT schoolArean FROM tschoolinfor WHERE schoolName='"
				+ schoolName + "'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		rs.next();
		map.put("c" + "0", 1);
		map.put("c" + "1", rs.getString("schoolArean"));
		// 学校班级数、教师数、学生数求平均值
		String schoolArean = rs.getString("schoolArean");
		String sql_school_avg = "select SUM(classNumber) as sum_class ,SUM(teacherNumber) as sum_teacher,SUM(studentNumber) as sum_student from tschoolinfor WHERE state=1 AND userTime LIKE '"
				+ time + "%' AND schoolArean = '" + schoolArean + "'";
		Statement state_school_avg = con.createStatement();
		ResultSet rs_school_avg = state.executeQuery(sql_school_avg);
		while (rs_school_avg.next()) {
			map.put("c" + "2", rs_school_avg.getInt("sum_class"));
			map.put("c" + "3", rs_school_avg.getInt("sum_teacher"));
			map.put("c" + "4", rs_school_avg.getInt("sum_student"));
		}
		// 问卷每题答案求平均值
		String sql_answer_avg = "SELECT AVG(blankText) as avg,queId FROM tblankanswer WHERE schoolId in ("
				+ schoolIds + ") GROUP BY queId ORDER BY queId";
		Statement state_answer_avg = con.createStatement();
		ResultSet rs_answer_avg = state.executeQuery(sql_answer_avg);
		ArrayList<Float> list_answer = new ArrayList<Float>();
		while (rs_answer_avg.next()) {
			list_answer.add(Float.parseFloat(rs_answer_avg.getString("avg")));
			int num = list_answer.size();
			for (int k = 0; k < num; k++) {
				map.put("c" + (k + 5), Float
						.parseFloat(new java.text.DecimalFormat("#.##")
								.format((float) list_answer.get(k))));
			}
		}

		state.close();
		state_answer_avg.close();
		state_school_avg.close();
		return map;
	}

	// 襄阳市每个题的综合填报情况
	public Map getAlldetail(String schoolName,String time) throws ClassNotFoundException,
			SQLException {
		ArrayList schoolIdList = getAllCityschoolId(time);
		String schoolIds = getSchoolIds(schoolIdList);
		HashMap map = new HashMap();
		String sql = "select SUM(classNumber) as sum_class ,SUM(teacherNumber) as sum_teacher,SUM(studentNumber) as sum_student FROM tschoolinfor WHERE state=1 AND userTime LIKE '"
				+ time + "%'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()) {
			map.put("c" + "0", 1);
			map.put("c" + "1", "襄阳市");
			map.put("c" + "2", rs.getInt("sum_class"));
			map.put("c" + "3", rs.getInt("sum_teacher"));
			map.put("c" + "4", rs.getInt("sum_student"));
		}
		// 整个襄阳市问卷相同问题答案求平均值
		String sql_answer_avg = "SELECT AVG(blankText) as avg,queId FROM tblankanswer WHERE schoolId in ("
				+ schoolIds + ") GROUP BY queId ORDER BY queId";
		Statement state_answer_avg = con.createStatement();
		ResultSet rs_answer_avg = state.executeQuery(sql_answer_avg);
		ArrayList<Float> list_answer = new ArrayList<Float>();
		while (rs_answer_avg.next()) {
			list_answer.add(Float.parseFloat(rs_answer_avg.getString("avg")));
			int num = list_answer.size();
			// System.out.println(num);
			for (int k = 0; k < num; k++) {
				map.put("c" + (k + 5), Float
						.parseFloat(new java.text.DecimalFormat("#.##")
								.format((float) list_answer.get(k))));
			}
		}
		state_answer_avg.close();
		state.close();
		return map;
	}

	// 学校、区县、襄阳市详细信息的对比
	public ArrayList getDetail(String schoolName,String time) throws SQLException,
			ClassNotFoundException {
		ArrayList list = new ArrayList();
		Map map_shool = new HashMap();
		Map map_area = new HashMap();
		Map map_all = new HashMap();
		map_shool = getSchooldetail(schoolName,time);
		map_area = getAreandetail(schoolName,time);
		map_all = getAlldetail(schoolName,time);
		list.add(map_shool);
		list.add(map_area);
		list.add(map_all);
		return list;
	}

}
