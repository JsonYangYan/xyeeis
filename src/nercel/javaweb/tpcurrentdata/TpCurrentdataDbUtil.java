package nercel.javaweb.tpcurrentdata;

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

public class TpCurrentdataDbUtil {
	public Connection con = null;

	public void openConnection() throws SQLException, IOException,
			ClassNotFoundException {
		if (con == null || con.isClosed()) {

			Properties p = new Properties();
			InputStream in = TpCurrentdataDbUtil.class
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
	
	
	/**
	 * 获取区域名字
	 * 
	 * @return
	 * @throws Exception
	 */
	public ArrayList<String> getAllAreaName() throws Exception {
		Statement state = con.createStatement();
		ResultSet rs = null;
		ArrayList<String> arrayList = new ArrayList<String>();
		String sql = "SELECT area_name FROM tarea";
		rs = state.executeQuery(sql);
		while (rs.next()) {
			arrayList.add(rs.getString("area_name"));
		}
		state.close();
		return arrayList;
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
		String sql = "SELECT autoId FROM tpschoolinfor WHERE state = 1 AND schoolArean ='"
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
		String sql = "SELECT autoId FROM tpschoolinfor WHERE state =1 AND userTime LIKE '"
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
	public int getChoiceSum(int choiceId,String currentTime) throws Exception {
		ArrayList listSchId = new ArrayList();
		String tchoice_sql = "SELECT count(*) AS sum from tpchoiceanswer, tpschoolinfor WHERE tpschoolinfor.state = 1 AND tpchoiceanswer.schoolId = tpschoolinfor.autoId AND choiceId="
				+ choiceId + " AND tpchoiceanswer.userTime LIKE '" + currentTime+ "%'";
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
	public ArrayList getChoiceValue(int queId,String currentTime) throws Exception {
		ArrayList list = new ArrayList();
		String tchoice_sql = "SELECT autoId,choiceContent FROM tpchoice WHERE queId="
				+ queId;
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(tchoice_sql);
		int schoolNumber = getAllSchoolIdNumber(currentTime).size();
		while (rs.next()) {
			HashMap map = new HashMap();
			map.put("name", rs.getString("choiceContent"));
			int sum = getChoiceSum(rs.getInt("autoId"),currentTime);
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
	 * 获得某区县或襄阳市（根据schoolId的范围判定）填空题的平均值
	 */
	public ArrayList getTownAvg(String schoolIds) throws ClassNotFoundException, SQLException {
		ArrayList listAvg = new ArrayList();
		String sql = "SELECT AVG(blankText) as avg FROM tpblankanswer WHERE schoolId in ("+ schoolIds + ") GROUP BY queId ORDER BY queId";
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
	
}
