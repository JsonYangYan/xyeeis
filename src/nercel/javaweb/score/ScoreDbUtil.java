package nercel.javaweb.score;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

public class ScoreDbUtil {
	private Connection con = null;

	public void openConnection() throws SQLException, IOException,
			ClassNotFoundException {
		if (con == null || con.isClosed()) {

			Properties p = new Properties();
			InputStream in = ScoreDbUtil.class
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

	// 获得区县的名称
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

	// //// 更新答案操作
	public void updateBlankTrueAnswer(int queId, float averageBlankAnswerContent)
			throws SQLException {
		String sql = "UPDATE tblanktrueanswer SET  blankAnswerContent ="
				+ averageBlankAnswerContent + " WHERE queId =" + queId;
		Statement state = con.createStatement();
		state.execute(sql);
		state.close();
	}

	/**
	 * 计算124,125,126,127,128,129,130, 131,132,133,145,158,183,184 的平均值
	 * 
	 * @param queId
	 * @return
	 * @throws SQLException
	 */
	public float getChangeAverageBlankWholeAnswer(int queId, String currentTime)
			throws SQLException {
		ArrayList<Integer> listTemp = new ArrayList<Integer>();
		listTemp = getSchoolId(currentTime);
		int length = listTemp.toString().length();
		String strTemp = null;
		float f = 0;
		if (length > 0) {
			strTemp = listTemp.toString().substring(1, length - 1); //
			String sql = "SELECT AVG(blankText) AS avgBlankText FROM tblankanswer WHERE queId = "
					+ queId + " AND schoolId IN (" + strTemp + ")";

			Statement state = con.createStatement();
			ResultSet rs = state.executeQuery(sql);
			if (rs.next()) {
				// f = Float.parseFloat(rs.getString("avgBlankText"));
				f = rs.getFloat("avgBlankText");
			}
		}
		return f;
	}

	// //// 需要统计所有体系后，算法平均分，且需要算出每个的增长率，计算给个增长率 题目编号： 167
	//
	public float getChangeAverageBlankRelateAnswer_167(String currentTime)
			throws SQLException {

		ArrayList<Integer> list = new ArrayList<Integer>();
		list = getSchoolId(currentTime);

		float f_167, f_169;
		float f = 0, sum = 0;
		int i = 0;

		for (; i < list.size(); i++) {
			f_167 = getBlankTextBySchoolId(167, list.get(i));
			f_169 = getBlankTextBySchoolId(169, list.get(i));
			if (f_167 != 0) {
				f_167 = (f_169 - f_167) / f_167; // 教育总经费增长率
			}
			sum = sum + f_167;
		}
		f = sum / i;
		return f;

	}

	// //// 需要统计所有体系后，算法平均分，且需要算出每个的增长率，计算给个增长率 题目编号： 169
	//
	public float getChangeAverageBlankRelateAnswer_169(String currentTime,
			String areaName) throws SQLException {

		ArrayList<Integer> list = new ArrayList<Integer>();
		list = getSchoolId(currentTime); // 算出有多少个提交了

		float f_169, f_168;
		float f = 0, sum = 0;
		int i = 0;

		for (; i < list.size(); i++) {
			f_169 = getBlankTextBySchoolId(169, list.get(i));
			f_168 = getBlankTextBySchoolId(168, list.get(i));
			f_169 = f_169 / f_168; // 信息化经费占教育总经费的比例 存放在169中
			sum = sum + f_169;
		}
		f = sum / i;
		return f;
	}

	/**
	 * 需要统计所有体系后，算法平均分，且需要算出每个所占的比例，计算每个 题目所占的比例是多少 题目编号：170,171,172,173,174
	 */
	public float getChangeAverageBlankRelateAnswer(int queId, String currentTime)
			throws SQLException {
		ArrayList<Integer> list = new ArrayList<Integer>();
		list = getSchoolId(currentTime);

		float f_169, f_1;
		float f = 0, sum = 0;
		int i = 0;

		for (; i < list.size(); i++) {
			f_169 = getBlankTextBySchoolId(169, list.get(i));
			f_1 = getBlankTextBySchoolId(queId, list.get(i));
			if (f_169 != 0) {
				f_1 = f_1 / f_169;
			}
			sum = sum + f_1;
		}

		f = sum / i;
		return f;
	}

	// ////得到每个用户的schoolId
	public ArrayList<Integer> getSchoolId(String currentTime)
			throws SQLException {
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		String sql = "SELECT autoId FROM tschoolinfor WHERE state =1 AND userTime LIKE '"
				+ currentTime + "%'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()) {
			arrayList.add(Integer.parseInt(rs.getString("autoId")));
		}
		return arrayList;
	}

	// ///得带每个用户的题目的值得大小
	public float getBlankTextBySchoolId(int queId, int schoolId)
			throws SQLException {
		String sql = "SELECT blankText FROM tblankanswer WHERE schoolId ="
				+ schoolId + " and queId =" + queId;
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		float f = 0;
		if (rs.next()) {
			f = rs.getFloat("blankText");
		}
		return f;
	}

	public float getBlankAnswerContentByQueId(int queId) throws SQLException {
		String sql = "SELECT blankAnswerContent FROM tblanktrueanswer WHERE queId ="
				+ queId;
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		float f = 0;
		if (rs.next()) {
			f = Float.parseFloat(rs.getString("blankAnswerContent"));
		}
		return f;
	}

	// 取出填空题的答案所占比
	public float getBlankAnswerPercentByQueId(int queId) throws SQLException {
		String sql = "SELECT blankAnswerPercent FROM tblanktrueanswer WHERE queId ="
				+ queId;
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		float f = 0;
		if (rs.next()) {
			f = Float.parseFloat(rs.getString("blankAnswerPercent"));
		}
		return f;
	}

	// 取出选择题所占比
	public float getChoiceAnswerPercentByQueId(int queId) throws SQLException {
		String sql = "SELECT choiceAnswerPercent FROM tchoicetrueanswer WHERE queId ="
				+ queId;
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		float f = 0;
		if (rs.next()) {
			f = Float.parseFloat(rs.getString("choiceAnswerPercent"));
		}
		return f;
	}

	public ArrayList<Integer> getChoiceNumberByThirdIndexId(Object thirdIndexId)
			throws SQLException {
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		String sql = "SELECT queId FROM tchoicetrueanswer WHERE thirdIndexId ="
				+ thirdIndexId;
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()) {
			arrayList.add(rs.getInt("queId"));
		}
		return arrayList;
	}

	public ArrayList<Integer> getBlankNumberByThirdIndexId(Object thirdIndexId)
			throws SQLException {
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		String sql = "SELECT queId FROM tblanktrueanswer WHERE thirdIndexId ="
				+ thirdIndexId;
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()) {
			arrayList.add(Integer.parseInt(rs.getString("queId")));
		}
		return arrayList;
	}

	public ArrayList<Integer> getTotalBlank() throws SQLException {
		ArrayList<Integer> list = new ArrayList<Integer>();
		String sql = "SELECT queId from tblanktrueanswer";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()) {
			list.add(Integer.parseInt(rs.getString("queId")));
		}
		return list;
	}

	/**
	 * 取出填空题的值
	 * 
	 * @param queId
	 * @param schoolId
	 * @return
	 * @throws SQLException
	 */
	public float getBlankValue(int queId, int schoolId) throws SQLException {
		String sql = "SELECT blankText FROM tblankanswer WHERE queId = "
				+ queId + " AND schoolId =" + schoolId;
		// System.out.println(sql);
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		if (rs.next()) {
			return Float.parseFloat(rs.getString("blankText"));
		}
		return 0;
	}

	// /////////////////////////////////////////////////////////////////
	public ArrayList getFirstIndexType() throws SQLException {
		ArrayList arrayList = new ArrayList();
		int j = 0;
		String sql = "SELECT firstIndexType FROM tFirstIndex";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		ResultSetMetaData rsm = rs.getMetaData();

		int count = rsm.getColumnCount();
		while (rs.next()) {
			arrayList.add(rs.getString("firstIndexType"));
		}
		return arrayList;
	}

	public ArrayList<Float> getFirstIndexPercent() throws SQLException {
		ArrayList<Float> arrayList = new ArrayList<Float>();
		int j = 0;
		String sql = "SELECT firstIndexPercent FROM tFirstIndex";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		ResultSetMetaData rsm = rs.getMetaData();

		int count = rsm.getColumnCount();
		while (rs.next()) {
			arrayList.add(rs.getFloat("firstIndexPercent"));
		}
		return arrayList;
	}

	// ////////////////////////////////////////////////////////////////////////////
	public ArrayList getSecondIndexId(int firstIndexId) throws SQLException {
		ArrayList arrayList = new ArrayList();
		int j = 0;
		String sql = "SELECT secondIndexId FROM tsecondIndex WHERE firstIndexId ="
				+ firstIndexId;
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		ResultSetMetaData rsm = rs.getMetaData();

		int count = rsm.getColumnCount();
		while (rs.next()) {
			arrayList.add(rs.getString("secondIndexId"));
		}
		return arrayList;
	}

	public ArrayList<Float> getSecondIndexPercent(int firstIndexId)
			throws Exception {
		ArrayList<Float> arrayList = new ArrayList<Float>();
		String sql = "SELECT secondIndexPercent FROM tsecondIndex WHERE firstIndexId ="
				+ firstIndexId;
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);

		while (rs.next()) {
			arrayList.add(rs.getFloat("secondIndexPercent"));
		}
		return arrayList;
	}

	/**
	 * 
	 * @param secondIndexId
	 * @return
	 * @throws SQLException
	 */
	public ArrayList getThirdIndexId(Object secondIndexId) throws SQLException {
		ArrayList arrayList = new ArrayList();
		int j = 0;
		String sql = "SELECT thirdIndexId FROM tthirdIndex WHERE secondIndexId ="
				+ secondIndexId;
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);

		while (rs.next()) {
			arrayList.add(rs.getString("thirdIndexId"));
		}
		return arrayList;

	}

	public ArrayList<Float> getThirdIndexPercent(Object secondIndexId)
			throws Exception {

		ArrayList<Float> arrayList = new ArrayList<Float>();
		int j = 0;
		String sql = "SELECT thirdIndexPercent FROM tthirdIndex WHERE secondIndexId ="
				+ secondIndexId;
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()) {
			arrayList.add(rs.getFloat("thirdIndexPercent"));
		}
		return arrayList;

	}

	// //////////////////////////////////////////////////////////////////////////////////////
	// 取出用户选择的单选项 id
	public ArrayList<Integer> getChoiceAnswerId(int queId, int schoolId)
			throws NumberFormatException, SQLException {
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		int j = 0;
		String sql = "SELECT choiceId FROM tchoiceanswer WHERE queId =" + queId
				+ " AND schoolId =" + schoolId;
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		ResultSetMetaData rsm = rs.getMetaData();

		int count = rsm.getColumnCount();
		while (rs.next()) {
			arrayList.add(Integer.parseInt(rs.getString("choiceId")));
		}
		return arrayList;
	}

	// ///获取 用户获取多选题，选项构成一个数组
	public ArrayList getChoiceAnswer(int queId, int schoolId)
			throws SQLException {
		ArrayList arrayList = new ArrayList();
		int j = 0;
		String sql = "SELECT choiceId FROM tchoiceanswer WHERE queId =" + queId
				+ " AND schoolId =" + schoolId;
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		ResultSetMetaData rsm = rs.getMetaData();

		int count = rsm.getColumnCount();
		while (rs.next()) {
			arrayList.add(rs.getString("choiceId"));
		}

		return arrayList;

	}

	/**
	 * 获取 用户获取多选题，选项构成一个数组
	 * 
	 * @param queId
	 * @return
	 * @throws SQLException
	 */
	public String getChoiceTrueAnswer(int queId) throws SQLException {
		String str = null;
		int j = 0;
		String sql = "SELECT choiceAnswerContent FROM tchoicetrueanswer WHERE queId ="
				+ queId;
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		if (rs.next()) {
			str = rs.getString("choiceAnswerContent");
		}

		return str;
	}

	/**
	 * 插入每个题目的得分 list5_blankNumber.get(x), schoolId, sum_fig
	 * 
	 * @return
	 * @throws Exception
	 */
	public void insertQueScore(int queId, int schoolId, float queScore,
			String currentTime) throws Exception {
		String strTemp = "INSERT INTO tquescore(queScore, queId, schoolId,createTime) VALUES("
				+ queScore
				+ ","
				+ queId
				+ ","
				+ schoolId
				+ ",'"
				+ currentTime
				+ "')";
		Statement state = con.createStatement();
		state.execute(strTemp);
	}

	/**
	 * 插入填空题的分数
	 * 
	 * @throws SQLException
	 *             schoolId, sum_3 * list3_thirdIndexPercent.get(k), 3
	 */
	public void insertSchoolScore(int schoolId, float schoolScore,
			int indexType, String currentTime) throws SQLException {
		String strTemp = "INSERT INTO tschoolindexscore(schoolId, schoolScore, indexType,createTime) VALUES("
				+ schoolId
				+ ","
				+ schoolScore
				+ ","
				+ indexType
				+ ",'"
				+ currentTime + "')";
		Statement state = con.createStatement();
		state.execute(strTemp);
	}

	/**
	 * 删除tquescore表中本月份的数据
	 * 
	 */
	public void delQueScore(String currentTime) throws Exception {
		String sql = "DELETE FROM tquescore WHERE createTime LIKE '"
				+ currentTime + "%'";
		Statement state = con.createStatement();
		state.execute(sql);
	}

	/**
	 * 删除tschoolindexscore表中本月份的数据
	 * 
	 */
	public void delSchoolScore(String currentTime) throws Exception {
		String sql = "DELETE FROM tschoolindexscore WHERE createTime LIKE '"
				+ currentTime + "%'";
		Statement state = con.createStatement();
		state.execute(sql);
	}
	
	/**
	 * 删除tareascore表中本月份的数据
	 * 
	 */
	public void delAreaScore(String currentTime) throws Exception {
		String sql = "DELETE FROM tareascore WHERE createTime LIKE '"
				+ currentTime + "%'";
		Statement state = con.createStatement();
		state.execute(sql);
	}

	/**
	 * 插入本月份的数据到tareascore
	 * 
	 */
	public void insertAreaScore(String areaName,float oneone,float onetwo,float onethree,float onefour,float onefive,float comprehensive,String time) throws Exception {
		String sql = "INSERT INTO tareascore(areaName,oneone,onetwo,onethree,onefour,onefive,comprehensive,createTime) VALUES('"+areaName+"',"+oneone+","+onetwo+","+onethree+","+onefour+","+onefive+","+comprehensive+",'"+time+"')";
		Statement state = con.createStatement();
		state.execute(sql);
	}
	
	/**
	 * 删除tthirdareascore表中本月份的数据
	 * 
	 */
	public void delThirdAreaScore(String currentTime) throws Exception {
		String sql = "DELETE FROM tthirdareascore WHERE createTime LIKE '"
				+ currentTime + "%'";
		Statement state = con.createStatement();
		state.execute(sql);
	}
	
	/**
	 * 插入tthirdareascore表中本月份的数据
	 * 
	 */
	public void insertThirdAreaScore(float thirdScore,String areaName,String time) throws Exception {
		String sql = "INSERT INTO tthirdareascore(thirdscore,areaname,createTime) VALUES("+thirdScore+",'"+areaName+"','"+time+"')";
		Statement state = con.createStatement();
		state.execute(sql);
	}
	
	/**
	 * 删除tsecondareascore表中本月份的数据
	 * 
	 */
	public void delSecondAreaScore(String currentTime) throws Exception {
		String sql = "DELETE FROM tsecondareascore WHERE createTime LIKE '"
				+ currentTime + "%'";
		Statement state = con.createStatement();
		state.execute(sql);
	}
	
	/**
	 * 插入tsecondareascore表中本月份的数据
	 * 
	 */
	public void insertSecondAreaScore(float secondScore,String areaName,String time) throws Exception {
		String sql = "INSERT INTO tsecondareascore(secondScore,areaname,createTime) VALUES("+secondScore+",'"+areaName+"','"+time+"')";
		Statement state = con.createStatement();
		state.execute(sql);
	}
	
	
	// 获取各地区已提交学校数
	public float getsubSchoolnum(String schoolArean, String currentTime)
			throws SQLException, ClassNotFoundException {
		String sql = "SELECT count(autoId) as subsch_num FROM tschoolinfor WHERE state =1 AND userTime LIKE '"
				+ currentTime + "%' AND schoolArean='" + schoolArean + "'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		int subSchNum = 0;
		if (rs.next()) {
			subSchNum = rs.getInt("subsch_num");
		}
		state.close();
		return subSchNum;
	}

	// 获得各区域各学校的schooId
	public ArrayList getschoolId(String schoolArean, String currentTime)
			throws SQLException, ClassNotFoundException {
		String sql = "SELECT autoId FROM tschoolinfor WHERE state =1 AND schoolArean='"
				+ schoolArean + "' AND userTime LIKE '" + currentTime + "%'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		ArrayList list = new ArrayList();
		while (rs.next()) {
			list.add(rs.getInt("autoId"));
		}
		// System.out.println(list);
		state.close();
		return list;
	}

	// 对一个学校评分，从数据库schoolScore表中查，返回list
	public ArrayList getschoolScore(int id) throws SQLException,
			ClassNotFoundException {
		String sql = "SELECT schoolScore FROM tschoolindexscore WHERE indexType=1 AND schoolId="
				+ id
				+ " or indexType=0 AND schoolId="
				+ id
				+ " ORDER BY autoId";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		ArrayList list = new ArrayList();
		while (rs.next()) {
			list.add(rs.getFloat("schoolScore"));
		}
		state.close();
		return list;
	}
	
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
		String sql = "SELECT autoId FROM tschoolInfor WHERE state = 1 AND schoolArean = '"
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
	 * 查询所有二级指标分数
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

}
