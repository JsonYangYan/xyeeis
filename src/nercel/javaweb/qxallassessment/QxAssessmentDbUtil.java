package nercel.javaweb.qxallassessment;

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

public class QxAssessmentDbUtil {
	public Connection con = null;

	public void openConnection() throws SQLException, IOException,
			ClassNotFoundException {
		if (con == null || con.isClosed()) {

			Properties p = new Properties();
			InputStream in = QxAssessmentDbUtil.class
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

	/**
	 * 取出一级指标的类型
	 * 
	 * @return
	 * @throws SQLException
	 */
	public ArrayList getFirstIndexType() throws SQLException {
		ArrayList arrayList = new ArrayList();
		int j = 0;
		String sql = "SELECT firstIndexType FROM tfirstindex";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		ResultSetMetaData rsm = rs.getMetaData();

		int count = rsm.getColumnCount();
		while (rs.next()) {
			arrayList.add(rs.getString("firstIndexType"));
		}
		state.close();
		return arrayList;
	}

	/**
	 * 获取二级指标类型
	 * 
	 * @param firstIndexId
	 * @return
	 * @throws SQLException
	 */
	public ArrayList getSecondIndexType() throws SQLException {
		ArrayList arrayList = new ArrayList();
		int j = 0;
		String sql = "SELECT secondIndexType FROM tsecondindex ";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()) {
			arrayList.add(rs.getString("secondIndexType"));
		}
		state.close();
		return arrayList;
	}

	/**
	 * 获取对应区域的id
	 * 
	 * @param schoolArean
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Integer> getSomeSchoolIdNumber(String schoolArean,String currentTime)
			throws Exception {
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		String sql = "SELECT autoId FROM tschoolinfor WHERE state=1 AND userTime LIKE '%"
				+ currentTime + "%' AND schoolArean ='" + schoolArean + "'";
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
	public ArrayList<Integer> getAllSchoolIdNumber(String currentTime) throws Exception {
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		String sql = "SELECT autoId FROM tschoolinfor WHERE state=1 AND userTime LIKE '%"+ currentTime + "%'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()) {
			arrayList.add(Integer.parseInt(rs.getString("autoId")));
		}
		state.close();
		return arrayList;
	}

	// //////////////////////////查询一级指标的分数
	/**
	 * 获取所有一级指标的分数
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
		String sql = "SELECT AVG(oneone) AS oneone,AVG(onetwo) AS onetwo,AVG(onethree) AS onethree,AVG(onefour) AS onefour,AVG(onefive) AS onefive,AVG(comprehensive) AS comprehensive FROM tareascore WHERE createTime LIKE '"
				+ currentTime + "%'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()) {
			listScore.add(Float.parseFloat(new java.text.DecimalFormat("#.##").format(rs.getFloat("oneone"))));
			listScore.add(Float.parseFloat(new java.text.DecimalFormat("#.##").format(rs.getFloat("onetwo"))));
			listScore.add(Float.parseFloat(new java.text.DecimalFormat("#.##").format(rs.getFloat("onethree"))));
			listScore.add(Float.parseFloat(new java.text.DecimalFormat("#.##").format(rs.getFloat("onefour"))));
			listScore.add(Float.parseFloat(new java.text.DecimalFormat("#.##").format(rs.getFloat("onefive"))));
			listScore.add(Float.parseFloat(new java.text.DecimalFormat("#.##").format(rs.getFloat("comprehensive"))));
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
			listScore.add(Float.parseFloat(new java.text.DecimalFormat("#.##").format(rs.getFloat("comprehensive"))));
		}
		state.close();
		return listScore;
	}
	
	// 取得湖北省一级指标得分
	public ArrayList<Float> getHubeiFirstIndexScore() throws Exception {
		ArrayList<Float> arrayList = new ArrayList<Float>();
		String sql = "SELECT indexScore FROM thubeifirstindexscore";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()) {
			arrayList.add(Float.parseFloat(rs.getString("indexScore")));
		}
		state.close();
		return arrayList;
	}

	// 取得全国一级指标得分
	public ArrayList<Float> getQuanGuoFirstIndexScore() throws Exception {
		ArrayList<Float> arrayList = new ArrayList<Float>();
		String sql = "SELECT indexScore FROM tquanguofirstindexscore";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()) {
			arrayList.add(Float.parseFloat(rs.getString("indexScore")));
		}
		state.close();
		return arrayList;
	}

	/**
	 * 学校的各区域学校提交的数量
	 * 
	 * @param schoolArean
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Integer> getAllSchoolIdNumberBySchoolArean(
			String schoolArean,String currentTime) throws Exception {
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		String sql = "SELECT autoId FROM tschoolinfor WHERE state=1 AND userTime LIKE '%"+ currentTime + "%' AND schoolArean = '"
				+ schoolArean + "'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()) {
			arrayList.add(Integer.parseInt(rs.getString("autoId")));
		}
		state.close();
		return arrayList;
	}

	// /////////取出所有三级指标进行计算
	/**
	 * 获取三级指标类型
	 * 
	 * @param secondIndexId
	 * @return
	 * @throws SQLException
	 */
	public ArrayList getThirdIndexType() throws SQLException {
		ArrayList arrayList = new ArrayList();
		String sql = "SELECT thirdIndexType FROM tthirdindex";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()) {
			arrayList.add(rs.getString("thirdIndexType"));
		}
		state.close();
		return arrayList;
	}

	/**
	 * 按学校Id查询所有三级指标分数
	 * 
	 * @param schoolId
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Float> getAllThirdIndexScore(int schoolId)
			throws Exception {
		ArrayList<Float> arrayList = new ArrayList<Float>();
		String sql = "SELECT schoolScore FROM tschoolindexscore  WHERE schoolId ="
				+ schoolId + " AND indexType = 3 ORDER BY autoId ASC LIMIT 33";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()) {
			arrayList.add(Float.parseFloat(rs.getString("schoolScore")));
		}
		state.close();
		return arrayList;
	}
	
	/**
	 * 查询各个区县三级指标分数
	 * 
	 * @param schoolId
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Float> getAreanThirdIndexScore(String areaName,String currentTime) throws Exception {
		ArrayList<Float> arrayList = new ArrayList<Float>();
		String sql = "SELECT thirdscore FROM tthirdareascore WHERE areaname = '"+areaName+"' AND createTime LIKE '"+currentTime+"%' ORDER BY autoId";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()) {
			arrayList.add(Float.parseFloat(rs.getString("thirdscore")));
		}
		state.close();
		return arrayList;
	}

	/**
	 * 按学校Id查询所有二级指标分数
	 * 
	 * @param schoolId
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Float> getAllSecondIndexScore(int schoolId)
			throws Exception {
		ArrayList<Float> arrayList = new ArrayList<Float>();
		String sql = "SELECT schoolScore FROM tschoolindexscore  WHERE schoolId ="
				+ schoolId + " AND indexType = 2 ORDER BY autoId ASC LIMIT 17";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()) {
			arrayList.add(Float.parseFloat(rs.getString("schoolScore")));
		}
		state.close();
		return arrayList;
	}
	
	/**
	 * 查询各个区县二级指标分数
	 * 
	 * @param schoolId
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Float> getAreanSecondIndexScore(String areaName,String currentTime) throws Exception {
		ArrayList<Float> arrayList = new ArrayList<Float>();
		String sql = "SELECT secondscore FROM tsecondareascore WHERE areaname = '"+areaName+"' AND createTime LIKE '"+currentTime+"%' ORDER BY autoId";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()) {
			arrayList.add(Float.parseFloat(rs.getString("secondscore")));
		}
		state.close();
		return arrayList;
	}
}