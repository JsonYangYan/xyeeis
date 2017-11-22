package nercel.javaweb.qxprint;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import nercel.javaweb.allassessment.AssessmentDbUtil;

public class PaperDbUtil {
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

	// 获取当前时间
	public String getCurrentTime() {
		Date date = new Date();
		SimpleDateFormat Dateformat = new SimpleDateFormat("yyyy-MM-dd");
		String time = Dateformat.format(date).toString();
		String[] strDate = time.split("-");
		String currentTime = strDate[0] + "-" + (strDate[1]);
		return currentTime;
	}

	/**
	 * 获取提交的所有学校id
	 * 
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Integer> getAllSchoolIdNumber() throws Exception {
		String time = getCurrentTime();
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		String strSql = "SELECT autoId FROM tschoolInfor WHERE state=1 AND userTime LIKE '"
				+ time + "%'";
		Statement state = con.createStatement();
		ResultSet rsResult = state.executeQuery(strSql);
		while (rsResult.next()) {
			arrayList.add(Integer.parseInt(rsResult.getString("autoId")));
		}
		state.close();
		return arrayList;
	}

	// 取出每个题目的得分
	public ArrayList<Float> getQueScoreByQueId(int queId) throws Exception {
		ArrayList<Float> arrayList = new ArrayList<Float>();
		String sql = "SELECT queScore FROM tqueScore WHERE queId =" + queId;
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()) {
			arrayList.add(Float.parseFloat(rs.getString("queScore")));
		}
		state.close();
		return arrayList;
	}

	/**
	 * 取出选择题每个选项有多少人选择
	 * 
	 * @param choiceId
	 * @return
	 * @throws SQLException
	 */
	public int getChoiceSum(int choiceId) throws SQLException {
		String tchoice_sql = "select count(*) sum from tchoiceanswer where choiceId="
				+ choiceId;
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
	public ArrayList getChoiceValue(int queId) throws Exception {
		ArrayList list = new ArrayList();
		String tchoice_sql = "select autoId,choiceContent from tchoice where queId="
				+ queId;
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(tchoice_sql);
		ResultSetMetaData rsm = rs.getMetaData();
		int intSchoolNumber = getAllSchoolIdNumber().size();
		int count = rsm.getColumnCount();
		while (rs.next()) {
			int intSumChoice = getChoiceSum(Integer.parseInt(rs
					.getString("autoId")));
			if (intSchoolNumber != 0) {
				list.add((Float.parseFloat(new java.text.DecimalFormat("#.00")
						.format(intSumChoice * 100.0 / intSchoolNumber))));
			} else {
				list.add(0);
			}
		}
		return list;
	}

}
