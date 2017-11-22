package nercel.javaweb.excelresult;

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

public class DemoAllSchooldetailDbUtil {
	private Connection con = null;

	public void openConnection() throws SQLException, IOException,
			ClassNotFoundException {
		if (con == null || con.isClosed()) {

			Properties p = new Properties();
			InputStream in = nercel.javaweb.excelresult.AllSchooldetailDbUtil.class
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

	// 获取当前时间
	public String getCurrentTime() {
		Date date = new Date();
		SimpleDateFormat Dateformat = new SimpleDateFormat("yyyy-MM-dd");
		String time = Dateformat.format(date).toString();
		String[] strDate = time.split("-");
		String currentTime = strDate[0] + "-" + (strDate[1]);
		return currentTime;
	}

	// 获得各个学校每个题的填报情况
	/**
	 * 
	 * @param schoolName
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Map getSchooldetail(String schoolName)
			throws ClassNotFoundException, SQLException {
		String time = getCurrentTime();
		HashMap map = new HashMap();
		String sql = "SELECT  schoolName, schoolArean, schoolTown,classNumber, teacherNumber, studentNumber, blankText FROM tschoolinfor,tblankanswer WHERE state=1 AND tschoolinfor.userTime LIKE '"
				+ time
				+ "%' AND tschoolinfor.autoId=tblankanswer.schoolId and tschoolinfor.schoolName='"
				+ schoolName + "' ORDER BY queId";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		ArrayList<String> list_answer = new ArrayList<String>();
		while (rs.next()) {
			list_answer.add(rs.getString("blankText"));
			int num = list_answer.size();
			map.put("c" + "1", rs.getString("schoolName"));
			map.put("c" + "2", rs.getString("schoolArean"));
			map.put("c" + "3", rs.getString("schoolTown"));
			map.put("c" + "4", rs.getInt("classNumber"));
			map.put("c" + "5", rs.getInt("teacherNumber"));
			map.put("c" + "6", rs.getInt("studentNumber"));
			for (int k = 0; k < num; k++) {
				map.put("c" + (k + 7), list_answer.get(k));
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
	public ArrayList getschoolName(String schoolArean) throws SQLException,
			ClassNotFoundException {
		String time = getCurrentTime();
		String sql = "SELECT schoolName FROM tschoolinfor WHERE state=1 AND userTime LIKE '"
				+ time + "%' AND schoolArean= '"
				+ schoolArean + "' ORDER BY autoId";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		ArrayList listshcoolName = new ArrayList();
		while (rs.next()) {
			listshcoolName.add(rs.getString("schoolName"));
		}
		state.close();
		return listshcoolName;
	}

	/**
	 * 每个地区的学校数量
	 * 
	 * @param schoolArean
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public int getschoolNum(String schoolArean) throws SQLException,
			ClassNotFoundException {
		String time = getCurrentTime();
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
	public ArrayList<String> getSchoolAreaName(String schoolArean)
			throws SQLException, ClassNotFoundException {
		String time = getCurrentTime();
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
	public ArrayList getDetail(String schoolArean) throws SQLException,
			ClassNotFoundException {
		String schoolAreaname = schoolArean;
		ArrayList list = new ArrayList();
		ArrayList listSchoolName = new ArrayList();
		listSchoolName = getschoolName(schoolAreaname); // 每个区域有多少所学校

		for (int i = 0; i < listSchoolName.size(); i++) {
			Map mapSchool = new HashMap();
			mapSchool = getSchooldetail(listSchoolName.get(i).toString()); // 获取所有的学校名字
			list.add(mapSchool);
		}
		return list;
	}
	
	/**
	 * 记录所有问题的选项
	 */
	public ArrayList<Integer> getChoiceId() throws Exception {
		String strSql = "SELECT DISTINCT(queId) FROM tchoice ORDER BY queId";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(strSql);
		ArrayList<Integer> listTemp = new ArrayList<Integer>();
		while (rs.next()) {
			listTemp.add(rs.getInt("queId"));
		}
		state.close();
		return listTemp;
	}
	
	/*
	 * 用户该选择题，选择了几个题目
	 */
	public ArrayList<Integer> getChoiceAnswer(int queId, String schoolName) throws Exception {
		String currentTime = getCurrentTime();
		String strSql = "SELECT choiceId FROM tchoiceanswer, tschoolinfor WHERE tchoiceanswer.schoolId = tschoolinfor.autoId AND tschoolinfor.schoolName = '" + schoolName +"' AND queId = "+ queId +""
						+ " AND tschoolinfor.userTime LIKE '"+currentTime+"%' ORDER BY choiceId";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(strSql);
		ArrayList<Integer> listTemp = new ArrayList<Integer>();
		while (rs.next()) {
			listTemp.add(rs.getInt("choiceId"));
		}
		state.close();
		return listTemp;
	}
	
	
	/**
	 * 查询改选项是A、B、C、D
	 * @throws Exception 
	 * 
	 */
	public char getChoiceAB(int choiceId) throws Exception {
		String strSql = "SELECT choiceId from tchoice WHERE autoId = " + choiceId;
		char str = 0 ;
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(strSql);
		int temp = 0;
		if (rs.next()) {
			temp = rs.getInt("choiceId");
		}
		state.close();
		for(int i=0; i<temp; i++) {
			str = (char) (65 + i);
		}
		return str;
	}
	
	/**
	 * 生成hash表
	 * @throws Exception 
	 */
	public HashMap getHashChoice(String schoolName) throws Exception {
		ArrayList<Integer> listChoiceId =  getChoiceId();  //得到所有问题答案
		HashMap mapTemp = new HashMap();
		for(int i=0; i<listChoiceId.size(); i++) {
			ArrayList<Integer>  listChoiceAnswer = getChoiceAnswer(listChoiceId.get(i),schoolName);
			if(listChoiceAnswer.size() == 0) {
				mapTemp.put("a"+i, "");
			} else {
				String str ="";
				for(int j=0; j<listChoiceAnswer.size(); j++) {
					str = str + getChoiceAB(listChoiceAnswer.get(j));
				}
				mapTemp.put("a"+i, str);
			}
		}
		return mapTemp;
	}
	
	 /* 各个区域的全部学校的信息情况
	 * 
	 * @param schoolArean
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public ArrayList getChoiceDetail(String schoolArean) throws Exception {
		String schoolAreaname = schoolArean;
		ArrayList list = new ArrayList();
		ArrayList listSchoolName = new ArrayList();
		listSchoolName = getschoolName(schoolAreaname); // 每个区域有多少所学校
		
		for (int i = 0; i < listSchoolName.size(); i++) {
			Map mapSchool = new HashMap();
			mapSchool = getHashChoice(listSchoolName.get(i).toString()); // 获取所有的学校名字
			list.add(mapSchool);
		}
		return list;
	}

}
