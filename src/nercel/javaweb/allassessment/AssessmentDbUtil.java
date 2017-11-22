package nercel.javaweb.allassessment;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;

import javax.validation.constraints.Null;

import com.mysql.fabric.xmlrpc.base.Array;

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

	// /////////////////////////////////////////////////////////////////
	/**
	 * 获得所有的选择题id choiceId
	 */
	public ArrayList getAllchoiceId() throws SQLException {
		ArrayList arrayList = new ArrayList();
		String sql = "SELECT choiceId FROM tchoiceanswer ORDER BY choiceId";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()) {
			arrayList.add(rs.getString("choiceId"));
		}
		state.close();
		return arrayList;
	}
	// 把schoolId用逗号分开
		private String getSchoolIds(ArrayList schoolId) {
			String schoolIds = "";
			for (int i = 0; i < schoolId.size(); i++) {
				schoolIds += schoolId.get(i) + ",";
			}
			if (!schoolIds.equals(""))
				schoolIds = schoolIds.substring(0, schoolIds.length() - 1);
			return schoolIds;
		}
	/**
	 * 
	 * 取出一级指标的类型
	 * 
	 * @return
	 * @throws SQLException
	 */
	public ArrayList getFirstIndexType() throws SQLException {
		ArrayList arrayList = new ArrayList();
		int j = 0;
		String sql = "SELECT firstIndexType FROM tFirstIndex";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()) {
			arrayList.add(rs.getString("firstIndexType"));
		}
		state.close();
		return arrayList;
	}

	// ////////////////////////////////////////////////////////////////////////////
	/**
	 * 通过一级指标获取二级指标
	 * 
	 * @param firstIndexId
	 * @return
	 * @throws SQLException
	 */
	public ArrayList getSecondIndexId(int firstIndexId) throws SQLException {
		ArrayList arrayList = new ArrayList();
		int j = 0;
		String sql = "SELECT secondIndexId FROM tsecondIndex WHERE firstIndexId ="
				+ firstIndexId;
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()) {
			arrayList.add(rs.getString("secondIndexId"));
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
		String sql = "SELECT schoolScore FROM tschoolindexScore  WHERE schoolId ="
				+ schoolId + " AND indexType = 1 ORDER BY indexType ASC";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()) {
			arrayList.add(Float.parseFloat(rs.getString("schoolScore")));
		}
		state.close();
		return arrayList;
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

	// ////////////
	/**
	 * 区域学校Id
	 * 
	 * @param schoolArean
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Integer> getAllSchoolIdNumberBySchoolArean(
			String schoolArean, String currentTime) throws Exception {
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		String sql = "SELECT autoId FROM tschoolinfor WHERE state = 1 AND schoolArean = '"
				+ schoolArean + "' AND userTime LIKE '" + currentTime + "%'";
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
	public ArrayList getThirdIndexType(Object secondIndexId)
			throws SQLException {
		ArrayList arrayList = new ArrayList();
		String sql = "SELECT thirdIndexType FROM tthirdindex WHERE secondIndexId = "
				+ secondIndexId;
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()) {
			arrayList.add(rs.getString("thirdIndexType"));
		}
		state.close();
		return arrayList;
	}

	/**
	 * 查询所有三级指标分数
	 * 
	 * @param schoolId
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Float> getAllThirdIndexScore(int schoolId)
			throws Exception {
		ArrayList<Float> arrayList = new ArrayList<Float>();
		String sql = "SELECT schoolScore FROM tschoolindexScore  WHERE schoolId ="
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
	
	// 取出每个选项的填空
//	public int getBlankText(int schoolId, int queId) throws Exception {
//		int fTemp = 0;
//		String sql = "SELECT blankText FROM tblankanswer WHERE queId =" + queId
//				+ " AND schoolId =" + schoolId;
//		Statement state = con.createStatement();
//		ResultSet rs = state.executeQuery(sql);
//		while (rs.next()) {
//			fTemp = rs.getInt("blankText");
//		}
//		state.close();
//		return fTemp;
//	}
	
	public HashMap<Integer, Integer> getBlankText(ArrayList schoolIdList, int[] queId) throws Exception {
		int schoolId=0;
		int terminalNum = 0;
		String schoolIdStr = "";
		schoolIdStr = schoolIdList.toString().replaceAll("[\\[\\]]", "");
		String queIdStr=Arrays.toString(queId).replaceAll("[\\[\\]]", "");
		String sql = "SELECT schoolId,SUM(blankText) AS terminalNum FROM tblankanswer WHERE "
				 +"schoolId IN (" + schoolIdStr+") AND queId in ("+queIdStr+") GROUP BY schoolId";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		HashMap schoolTerminalNUm=new HashMap<Integer,Integer>();
		while (rs.next()) {
			schoolId=rs.getInt("schoolId");
			terminalNum = rs.getInt("terminalNum");
			schoolTerminalNUm.put(schoolId, terminalNum);
		}
		state.close();
		return schoolTerminalNUm;
	}

	/**
	 * 取出选择题每个选项有多少人选择
	 * 
	 * @param choiceId
	 * @return
	 * @throws SQLException
	 */
	public HashMap<Integer, Integer> getChoiceSum(int queId, String currentTime)
			throws SQLException {
		int choiceId=0;
		HashMap<Integer, Integer> choiceNumMap=new HashMap();
		String tchoice_sql = "SELECT choiceId,count(*) AS sum from tchoiceanswer, tschoolinfor WHERE tschoolinfor.state = 1 AND tschoolinfor.autoId = tchoiceanswer.schoolId AND queId="
				+ queId
				+ " AND tchoiceanswer.userTime LIKE '"
				+ currentTime
				+ "%' GROUP BY choiceId";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(tchoice_sql);
		int sum = 0;
		while(rs.next()) {
			choiceId=rs.getInt("choiceId");
			sum = rs.getInt("sum");
			choiceNumMap.put(choiceId, sum);
		}
		return choiceNumMap;
	}

	/**
	 * 输入选择题题号，计算出襄阳市每个选项情况，注意autoId才是每个选择题的选项编号，相当于choiceId
	 * 
	 * @param queId
	 * @return
	 * @throws Exception
	 */
	public ArrayList getChoiceValue(int queId, String currentTime)
			throws Exception {
		ArrayList list = new ArrayList();
		String tchoice_sql = "SELECT autoId,choiceContent FROM tchoice WHERE queId="
				+ queId;
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(tchoice_sql);
		int schoolNumber = getAllSchoolIdNumber(currentTime).size();
		HashMap<Integer, Integer> choiceNumMap=getChoiceSum(queId, currentTime);
		while (rs.next()) {
			int sum=0;
			HashMap map = new HashMap();
			map.put("name", rs.getString("choiceContent"));
			if(choiceNumMap!=null&&choiceNumMap.containsKey(rs.getInt("autoId")))
			    sum=choiceNumMap.get(rs.getInt("autoId"));
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
	
//	public ArrayList getChoiceValue(int queId, String currentTime)
//			throws Exception {
//		ArrayList list = new ArrayList();
//		String tchoice_sql = "SELECT autoId,choiceContent FROM tchoice WHERE queId="
//				+ queId;
//		Statement state = con.createStatement();
//		ResultSet rs = state.executeQuery(tchoice_sql);
//		int schoolNumber = getAllSchoolIdNumber(currentTime).size();
//		while (rs.next()) {
//			HashMap map = new HashMap();
//			map.put("name", rs.getString("choiceContent"));
//			int sum = getChoiceSum(rs.getInt("autoId"), currentTime);
//			if (schoolNumber != 0) {
//				map.put("value", Float.parseFloat(new java.text.DecimalFormat(
//						"#.00").format(sum * 100.0 / schoolNumber)));
//			} else {
//				map.put("value", 0);
//			}
//			list.add(map);
//		}
//		return list;
//	}

	/**
	 * 获取当前学校的区域名字
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

	/**
	 * 获取当前学校的区域名字
	 * 
	 * @return
	 * @throws Exception
	 */
	public ArrayList<String> getAreaName() throws Exception {
		Statement state = con.createStatement();
		ResultSet rs = null;
		ArrayList<String> arrayList = new ArrayList<String>();
		String sql = "SELECT area_name FROM tarea WHERE area_name != '襄阳市'";
		rs = state.executeQuery(sql);
		while (rs.next()) {
			arrayList.add(rs.getString("area_name"));
		}
		state.close();
		return arrayList;
	}

	/**
	 * 得到老师的数量
	 * 
	 * @return
	 * @throws Exception
	 */
	public int getTeacherNumber(String currentTime, int schoolId)
			throws Exception {
		String sql = "SELECT teacherNumber FROM tschoolinfor  WHERE state =1 AND userTime LIKE '"
				+ currentTime + "%' AND autoId =" + schoolId;
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		int sum = 0;
		if (rs.next()) {
			sum = rs.getInt("teacherNumber");
		}
		return sum;
	}

	public float getQueContent(int schoolId, int queId) throws Exception {
		String sql = "SELECT blankText FROM tblankanswer WHERE schoolId ="
				+ schoolId + " AND queId =" + queId;
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		float fTemp = 0;
		if (rs.next()) {
			fTemp = Float.parseFloat(rs.getString("blankText"));
		}
		return fTemp;

	}

	/**
	 * 得到学生的数量
	 * 
	 * @return
	 * @throws Exception
	 */
	public int getStudentNumber(String currentTime, int schoolId)
			throws Exception {
		String sql = "SELECT studentNumber FROM tschoolinfor  WHERE state =1 AND userTime LIKE '"
				+ currentTime + "%' AND autoId =" + schoolId;
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		int sum = 0;
		if (rs.next()) {
			sum = rs.getInt("studentNumber");
		}
		return sum;
	}

	/**
	 * 获得某区县或者襄阳市（根据学校Id的范围判定）三级指标的平均值
	 */
	public ArrayList getAvg(String schoolIds) throws ClassNotFoundException,
			SQLException {
		ArrayList listAvg = new ArrayList();
		String sql = "SELECT AVG(blankText) as avg FROM tblankanswer WHERE schoolId in ("
				+ schoolIds + ") GROUP BY queId ORDER BY queId";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()) {
			listAvg.add(Float.parseFloat(new java.text.DecimalFormat("#.##").format(rs.getFloat("avg"))));
		}
		state.close();
		return listAvg;
	}

	/////////////////////////////////by yan
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

}
