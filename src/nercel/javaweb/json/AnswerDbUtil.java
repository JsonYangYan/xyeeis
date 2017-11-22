package nercel.javaweb.json;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import nercel.javaweb.register.DBUtil;
import net.sf.json.JSONObject;

public class AnswerDbUtil {

	private Connection con = null;

	public void openConnection() throws SQLException, IOException,
			ClassNotFoundException {
		if (con == null || con.isClosed()) {

			Properties p = new Properties();
			InputStream in = AnswerDbUtil.class
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
	 * 第一次插入选择时，插入选择题答案以及时间
	 * 
	 * @param queId
	 * @param choiceId
	 * @param choiceText
	 * @param schoolId
	 * @throws SQLException
	 */
	public void insertChoiceAnswer(int queId, int choiceId, String choiceText,
			int schoolId, String time) throws SQLException {
		String sql = "INSERT INTO tchoiceanswer_temp(queId, choiceId, choiceText, schoolId, userTime) VALUES("
				+ queId
				+ ","
				+ choiceId
				+ ",'"
				+ choiceText
				+ "',"
				+ schoolId
				+ ",'" + time + "')";
		Statement state = con.createStatement();
		state.execute(sql);
		state.close();
	}

	/**
	 * 第一次插入填空题时，插入填空题答案以及时间
	 * 
	 * @param queId
	 * @param blankText
	 * @param schoolId
	 * @throws SQLException
	 */
	public void insertBlankAnswer(int queId, String blankText, int schoolId,
			String time) throws SQLException {
		String sql = "INSERT INTO tblankanswer_temp(queId, blankText, schoolId, userTime) VALUES("
				+ queId
				+ ",'"
				+ blankText
				+ "',"
				+ schoolId
				+ ",'"
				+ time
				+ "')";
		Statement state = con.createStatement();
		state.execute(sql);
		state.close();
	}

	/**
	 * 获得问题的groupId
	 * 
	 * @param indexType,type 学校类型 normal普通学校，tp教学点
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public ArrayList<Object> getQueGroupId(int indexType,String type) throws SQLException,
			ClassNotFoundException {
		String sql = null;
		
		ArrayList<Object> arrayList = new ArrayList<Object>();
		if(type.equals("normal")){
			sql = "SELECT groupId FROM tquestion WHERE indexType ="
					+ indexType + " GROUP BY groupId";
		}else {
			sql = "SELECT groupId FROM tpquestion WHERE indexType ="
					+ indexType + " GROUP BY groupId";
		}
		
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		ResultSetMetaData rsm = rs.getMetaData();

		int count = rsm.getColumnCount();
		while (rs.next()) {
			for (int i = 0; i < count; i++) {
				String columName = rsm.getColumnName((i + 1));
				int queId = rs.getInt((i + 1));
				arrayList.add(queId);
			}

		}
		return arrayList;
	}

	/**
	 * 通过groupId得到问题的id
	 * 
	 * @param groupId
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public ArrayList getQueIdByGroupId(int groupId,String type) throws SQLException,
			ClassNotFoundException {

		ArrayList arrayList = new ArrayList();
		int j = 0;
		String sql = null;
		if(type.equals("normal")){
			
			sql = "SELECT queId FROM tquestion WHERE groupId =" + groupId;
		}else {
			sql = "SELECT queId FROM tpquestion WHERE groupId =" + groupId;
		}
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		ResultSetMetaData rsm = rs.getMetaData();
		int count = rsm.getColumnCount();
		while (rs.next()) {
			for (int i = 0; i < count; i++) {
				String columName = rsm.getColumnName((i + 1));
				int queId = rs.getInt((i + 1));
				arrayList.add(queId);
			}

		}
		return arrayList;
	}

	/**
	 * 获得问题
	 * 
	 * @param queId
	 * @param schoolId
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public HashMap getQue(int queId, int schoolId, String time, String type)
			throws ClassNotFoundException, SQLException {
		ArrayList list = new ArrayList();
		String sql = null;
		if(type.equals("normal")){
			
			sql = "SELECT * FROM tquestion WHERE queId =" + queId;
		}else {
			sql = "SELECT * FROM tpquestion WHERE queId =" + queId;
		}
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		ResultSetMetaData rsm = rs.getMetaData();
		int count = rsm.getColumnCount();
		HashMap map = new HashMap();
		while (rs.next()) {
			for (int i = 0; i < count; i++) {
				String columName = rsm.getColumnName((i + 1));
				Object sqlView = rs.getString(columName);
				map.put(columName, sqlView);
				if (columName.equals("queId")) {
					int queID = Integer.parseInt((String) sqlView);
					if (type.equals("normal")) {
						if ((queID == 121) || (queID == 123) || (queID == 126)
								|| (queID == 127) || (queID == 128)
								|| (queID == 130) || (queID == 132)
								|| (queID == 133) || (queID == 135)
								|| (queID == 136) || (queID == 137)
								|| (queID == 138) || (queID == 139)
								|| (queID == 140) || (queID == 167)
								|| (queID == 168) || (queID == 169)
								|| (queID == 170) || (queID == 171)
								|| (queID == 172) || (queID == 173)
								|| (queID == 174) || (queID == 175)
								|| (queID == 176) || (queID == 177)
								|| (queID == 178) || (queID == 179)
								|| (queID == 180) || (queID == 181)
								|| (queID == 183) || (queID == 184)) {
								map.put("isPercent", "0");    	//一般的数字标记为0
							}

							if ((queID == 124) || (queID == 125) || (queID == 131)
								|| (queID == 143) || (queID == 144) || (queID == 158) || (queID == 145)) {
								map.put("isPercent", "1");   	//百分比的数字标记为1
							}

							if ((queID == 152) || (queID == 153) || (queID == 154)
								|| (queID == 155) || (queID == 129)) {
								map.put("isPercent", "2");  	//判断0和1
							}
					}else{
						
						map.put("isPercent", "0");
					}
					
					

					String str = getBlankAnswer(sqlView, schoolId, time,type); // 判断填空题部分
					if (str != null) {
						map.put("userAns", str);
					}
				}
			}
		}
		state.close();
		return map;
	}

	public String getBlankAnswer(Object queId, int schoolId, String time,String type)
			throws SQLException {
		String sql = null;
		if (type.equals("normal")) {
			sql = "SELECT blankText From tblankanswer_temp WHERE queId = "
					+ queId + " AND schoolId =" + schoolId + " AND userTime LIKE '"
					+ time + "%'";
		}else {
			sql = "SELECT blankText From tpblankanswer_temp WHERE queId = "
					+ queId + " AND schoolId =" + schoolId + " AND userTime LIKE '"
					+ time + "%'";
		}
		
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		if (rs.next()) {
			return rs.getString("blankText");
		} else {
			return null;
		}
	}

	/**
	 * 获得选择题选项
	 * 
	 * @param queId
	 * @param schoolId
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public ArrayList getChoice(int queId, int schoolId, String time,String type)
			throws ClassNotFoundException, SQLException {
		ArrayList list = new ArrayList();
		char ch = 'A';
		String sql = null;
		if(type.equals("normal")){
			sql = "SELECT * FROM tchoice WHERE queId='" + queId
					+ "'ORDER BY autoId";
			
		}else {
			sql = "SELECT * FROM tpchoice WHERE queId='" + queId
					+ "'ORDER BY autoId";
		}
		
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		ResultSetMetaData rsm = rs.getMetaData();
		int count = rsm.getColumnCount();

		while (rs.next()) {
			HashMap map = new HashMap();
			for (int i = 0; i < count; i++) {
				String columName = rsm.getColumnName((i + 1));
				Object sqlView = rs.getString(columName);
				map.put(columName, sqlView);
				if (columName.equals("autoId")) {
					String str = getChoiceAnswer(sqlView, schoolId, time,type);
					if (!str.equals("no")) {
						map.put("is_checked", "1");
						map.put("answer", str);
					} else {
						map.put("is_checked", "0");
					}
				}
			}
			map.put("order", ch);
			ch++;
			list.add(map);
		}
		state.close();
		return list;
	}

	public String getChoiceAnswer(Object choiceId, int schoolId, String time,String type)
			throws SQLException {
		String sql = null;
		if (type.equals("normal")) {
			sql = "SELECT choiceText FROM tchoiceanswer_temp WHERE choiceId ="
					+ choiceId + " AND schoolId = " + schoolId
					+ " AND userTime LIKE '" + time + "%'";
		}else {
			sql = "SELECT choiceText FROM tpchoiceanswer_temp WHERE choiceId ="
					+ choiceId + " AND schoolId = " + schoolId
					+ " AND userTime LIKE '" + time + "%'";
		}
		
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		if (rs.next()) {
			return rs.getString("choiceText");
		} else {
			return "no";
		}
	}

	// 获取autoId
	// @author: yanzhaoyang
	// @function:获取用户表用户的id
	public int getId(String username) throws SQLException,
			ClassNotFoundException {
		String sql = "SELECT autoId FROM tschoolinfor WHERE userName='"
				+ username + "'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		int i = 0;
		if (rs.next()) {
			i = Integer.parseInt(rs.getString("autoId"));
		}
		state.close();
		return i;
	}

	/**
	 * 把已经提交的学校的状态state标示为"1"，信息统计中会用到
	 * 
	 * @param schoolName
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void setSubmitSchool(String username) throws Exception {
		String sql = "UPDATE tschooluser SET state=1 WHERE username='"
				+ username + "'";
		Statement state = con.createStatement();
		state.execute(sql);
		state.close();
	}

	/**
	 * 得到最新插入的学校名字
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getUserName() throws Exception {
		String strSql = "SELECT userName FROM tschoolinfor ORDER BY autoId DESC LIMIT 1";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(strSql);
		String schoolName = null;
		if (rs.next()) {
			schoolName = rs.getString("userName");
		}
		state.close();
		return schoolName;
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////////////////////
	// /////////////////////////////////以下是关于SaveRecord的记录////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * @author stone 获取年月日时间中获取当前的年月字字段
	 * @return
	 */
	private String getYearAndMonth(String tempTime) {
		Date date = new Date();
		SimpleDateFormat Dateformat = new SimpleDateFormat(tempTime);
		String[] strDate = Dateformat.format(date).split("-");
		String strTime = strDate[0] + "-" + (strDate[1]);
		return strTime;
	}

	/**
	 * 首先判断用户该月份是否插入了数据,若没有需要在tschoolinfor表中新增加一条记录 若有记录则记为true， 否则返回为false
	 * 
	 * @throws Exception
	 */
	public boolean getIsRecord(String userName, String currentTime)
			throws Exception {
		boolean fig = false;
		String strSql = "SELECT userTime FROM tschoolinfor WHERE userName = '"
				+ userName + "' ORDER BY autoId DESC LIMIT 1";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(strSql);
		String tempTime = null;
		if (rs.next()) {
			tempTime = rs.getString("userTime");
			if (tempTime.length() > 0) {
				String strTime = getYearAndMonth(tempTime);
				if (currentTime.equals(strTime)) {
					fig = true;
				}
			}	
		}
		return fig;
	}

	/**
	 * 每个月份，在tschoolinfor或者tpschoolinfor表中新增加一条记录
	 * 
	 * @param schoolArean
	 * @param schoolName
	 * @param schoolType
	 * @param teacherNumber
	 * @param studentNumber
	 * @param schoolTown
	 * @param personName
	 * @param telPhone
	 * @param eMail
	 * @param classNumber
	 * @param username
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */

	public boolean addNewSchoolInfor(String schoolArean, String schoolName,
			String schoolType, int teacherNumber, int studentNumber,
			String schoolTown, String personName, String telPhone,
			String eMail, int classNumber, String userName, String time,String school_type)
			throws Exception {
		int num = getSchoolRecordByMonth(userName,time);
		boolean flag = false;
		if(num==0){
			String strSql = null;
			if (school_type.equals("normal")) {
				strSql = "INSERT INTO tschoolinfor (userName,schoolName,schoolType,schoolArean,schoolTown,classNumber,teacherNumber,studentNumber,personName,telPhone,eMail,userTime) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
			}else {
				strSql = "INSERT INTO tpschoolinfor (userName,schoolName,schoolType,schoolArean,schoolTown,classNumber,teacherNumber,studentNumber,personName,telPhone,eMail,userTime) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
				
			}
			
			PreparedStatement psmt = con.prepareStatement(strSql);
			psmt.setString(1, userName);
			psmt.setString(2, schoolName);
			psmt.setString(3, schoolType);
			psmt.setString(4, schoolArean);
			psmt.setString(5, schoolTown);
			psmt.setInt(6, classNumber);
			psmt.setInt(7, teacherNumber);
			psmt.setInt(8, studentNumber);
			psmt.setString(9, personName);
			psmt.setString(10, telPhone);
			psmt.setString(11, eMail);
			psmt.setString(12, time);
			int i = psmt.executeUpdate();
			if (i == 1) {
				flag = true;
			}
		}
		
		return flag;
	}

	/**
	 * 
	 * @param schoolArean
	 * @param schoolName
	 * @param schoolType
	 * @param teacherNumber
	 * @param studentNumber
	 * @param schoolTown
	 * @param personName
	 * @param telPhone
	 * @param eMail
	 * @param classNumber
	 * @param username
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean editSchoolInfor(String schoolArean, String schoolName,
			String schoolType, int teacherNumber, int studentNumber,
			String schoolTown, String personName, String telPhone,
			String eMail, int classNumber, String username, String currentTime)
			throws ClassNotFoundException, SQLException {
		boolean flag = false;
		String sql = "UPDATE tschoolinfor SET schoolName=?,schoolType=?,schoolArean=?,schoolTown=?,"
				+ "classNumber=?,teacherNumber=?,studentNumber=?,personName=?,telPhone=?,"
				+ "eMail=? WHERE userName=? AND userTime LIKE ?";
		PreparedStatement psmt = con.prepareStatement(sql);
		psmt.setString(1, schoolName);
		psmt.setString(2, schoolType);
		psmt.setString(3, schoolArean);
		psmt.setString(4, schoolTown);
		psmt.setInt(5, classNumber);
		psmt.setInt(6, teacherNumber);
		psmt.setInt(7, studentNumber);
		psmt.setString(8, personName);
		psmt.setString(9, telPhone);
		psmt.setString(10, eMail);
		psmt.setString(11, username);
		psmt.setString(12, currentTime + '%');
		int i = psmt.executeUpdate();
		if (i == 1) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 
	 * 判断tschoolinfor中有几条数据,传给前台的下拉框 若为没有数据，则执行插入造作
	 * 
	 * @throws Exception
	 * 
	 */
	public ArrayList<String> getAllSchoolRecord(String userName)
			throws Exception {
		ArrayList<String> listDate = new ArrayList<String>();
		String strSql = "SELECT userTime FROM tschoolinfor WHERE userName = '"
				+ userName + "'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(strSql);
		while (rs.next()) {
			String[] strTemp = rs.getString("userTime").split("-");
			listDate.add(strTemp[0] + "-" + strTemp[1]);
		}
		return listDate;
	}

	/**
	 * 
	 * 判断tschoolinfor中有几条数据,传给前台的下拉框 若为没有数据，则执行插入造作
	 * 
	 * @throws Exception
	 * 
	 */
	public Integer getSchoolRecordByMonth(String userName, String time)
			throws Exception {
		ArrayList<String> listDate = new ArrayList<String>();
		String strSql = "SELECT count(*) as aa FROM tschoolinfor WHERE userName = '"
				+ userName + "' AND userTime LIKE '" + time + "%'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(strSql);
		int count = 0;
		if (rs.next()) {
			count = rs.getInt("aa");
		}
		return count;
	}

	/**
	 * 
	 * 判断tschoolinfor中改月有没有记录,若没有的话
	 * 
	 * @throws Exception
	 * 
	 */
	public ArrayList<String> getSchoolRecordDateByMonth(String userName,
			String time,String school_type) throws Exception {
		ArrayList<String> listDate = new ArrayList<String>();
		String strSql = null;
		if (school_type.equals("normal")) {
			strSql = "SELECT userTime FROM tschoolinfor WHERE userName ='"
					+ userName + "' AND userTime LIKE '" + time + "%'";
		}else {
			strSql = "SELECT userTime FROM tpschoolinfor WHERE userName ='"
					+ userName + "' AND userTime LIKE '" + time + "%'";
		}
		
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(strSql);
		while (rs.next()) {
			String[] strTemp = rs.getString("userTime").split("-");
			listDate.add(strTemp[0] + "-" + strTemp[1]);
		}
		
		return listDate;
	}

	/**
	 * 在tschoolInfor表中进行插入操作，将isBase中插入标志1
	 * 
	 * @throws Exception
	 */
	public void updateIsBase(String userName, String time) throws Exception {
		String strSql = " UPDATE  tschoolinfor SET isBase =1   WHERE userName = '"
				+ userName + "' AND userTime LIKE '" + time + "%'";
		Statement state = con.createStatement();
		state.execute(strSql);
		state.close();
	}

	/**
	 * 更新选择题，针对传过来的单项选择题
	 */
	public void updateChoiceAnswerByMonth(int queId, int choiceId,
			String choiceText, int schoolId, String time) throws SQLException {
		String strSql = "UPDATE tchoiceAnswer SET choiceText = '" + choiceText
				+ "' WHERE queId =" + queId + " AND userTime LIKE '" + time
				+ "%' AND schoolId =" + schoolId + " AND choiceId = '"
				+ choiceId + "'";
		Statement state = con.createStatement();
		state.execute(strSql);
		state.close();
	}

	/**
	 * 更新选择题，针对传过来的单项选择题
	 */
	public void updateNewChoiceAnswerByMonth(int queId, int choiceId,
			String choiceText, int schoolId, String time) throws SQLException {
		String strSql = "UPDATE tchoiceAnswer SET choiceId = '" + choiceId
				+ "' WHERE queId =" + queId + " AND userTime LIKE '" + time
				+ "%' AND schoolId =" + schoolId;
		Statement state = con.createStatement();
		state.execute(strSql);
		state.close();
	}

	/**
	 * 删除选择题，针对多选题目,关过去题号，那个日期
	 * 
	 * @throws SQLException
	 * 
	 */
	public void deleteChoiceAnswer(int queId, String time, int schoolId)
			throws SQLException {
		String strSql = "DELETE FROM tchoiceAnswer  WHERE queId =" + queId
				+ " AND userTime LIKE '" + time + "%' AND schoolId ="
				+ schoolId;
		Statement state = con.createStatement();
		state.execute(strSql);
		state.close();
	}

	/**
	 * 针对填空题进行更新操作，答案以及时间
	 * 
	 * @param queId
	 * @param blankText
	 * @param schoolId
	 * @throws SQLException
	 */
	public void UpdateBlankAnswer(int queId, String blankText, int schoolId,
			String time) throws SQLException {

		String strSql = "UPDATE tblankanswer SET blankText = ? WHERE queId = ?  AND schoolId = ? AND userTime LIKE ?";
		PreparedStatement psmt = con.prepareStatement(strSql);
		
		psmt.setString(1, blankText);
		psmt.setInt(2, queId);
		psmt.setInt(3, schoolId);
		psmt.setString(4, time + '%');
		psmt.executeUpdate();
		
		/*String strSql = "UPDATE tblankanswer SET blankText = '" + blankText
				+ "' WHERE queId =" + queId + " AND userTime LIKE '" + time
				+ "%' AND schoolId =" + schoolId;
		Statement state = con.createStatement();
		state.execute(strSql);
		state.close();*/
	}

	/**
	 * fig 为1时，从tblankAnswer中获得当前月份已填写问题题号
	 * 
	 * fig 为2时，从tchoiceAnswer中获得当前月份已填写的问题题号
	 * 
	 * @throws Exception
	 */
	public ArrayList<Integer> getAnswerQueIdByMonth(String schoolId,
			String time, int fig) throws Exception {
		String strSql = null;
		if (fig == 1) {
			strSql = "SELECT queId FROM　tblankanswer_temp WHERE shoolId = "
					+ schoolId + " AND userTime = '" + time + "'";
		} else {
			strSql = "SELECT queId FROM tchoiceanswer WHERE shoolId = "
					+ schoolId + " AND userTime = '" + time + "'";
		}
		ArrayList<Integer> listTemp = new ArrayList<Integer>();
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(strSql);
		while (rs.next()) {
			listTemp.add(rs.getInt("queId"));
		}
		return listTemp;
	}

	/**
	 * 第一次插入选择时，插入选择题答案以及时间   插入到临时表中
	 * 
	 * @param queId
	 * @param choiceId
	 * @param choiceText
	 * @param schoolId
	 * @throws SQLException
	 */
	public void insertChoiceAnswerByTime(int queId, int choiceId,
			String choiceText, int schoolId, String time) throws SQLException {
		if(choiceId == 330) {
			choiceText = "http://";
		}
		
		String strSql = "INSERT INTO tchoiceanswer_temp(queId, choiceId, choiceText, schoolId, userTime) VALUES("
				+ queId
				+ ","
				+ choiceId
				+ ",'"
				+ choiceText
				+ "',"
				+ schoolId
				+ ",'" + time + "')";
		Statement state = con.createStatement();
		state.execute(strSql);
		state.close();
	}

	/**
	 * 第一次插入填空题时，插入填空题答案以及时间         插入到临时表中
	 * 
	 * @param queId
	 * @param blankText
	 * @param schoolId
	 * @throws SQLException
	 */
	public void insertBlankAnswerByTime(int queId, String blankText,
			int schoolId, String time) throws SQLException {
		String strSql  =  "INSERT INTO tblankanswer_temp(queId, blankText, schoolId, userTime) VALUES(? ,? ,? ,?)";
		PreparedStatement psmt = con.prepareStatement(strSql);
		psmt.setInt(1, queId);
		psmt.setString(2, blankText);
		psmt.setInt(3, schoolId);
		psmt.setString(4, time);
		psmt.executeUpdate();
		
	/*	String sql = "INSERT INTO tblankanswer(queId, blankText, schoolId, userTime) VALUES("
				+ queId
				+ ",'"
				+ blankText
				+ "',"
				+ schoolId
				+ ",'"
				+ time
				+ "')";
		Statement state = con.createStatement();
		state.execute(sql);
		state.close();*/
	}

	/**
	 * welcome页面显示已经登录过用户的信息
	 * 
	 * @param usname
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public ArrayList editsearchContent(String userName, String time,String school_type)
			throws ClassNotFoundException, SQLException {

		ArrayList list = new ArrayList();
		String sql = null;
		if (school_type.equals("normal")) {
			sql = "SELECT * FROM tschoolinfor WHERE userName ='" + userName
					+ "' AND userTime LIKE'" + time + "%'";
		}else {
			sql = "SELECT * FROM tpschoolinfor WHERE userName ='" + userName
					+ "' AND userTime LIKE'" + time + "%'";
		}
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		ResultSetMetaData rsm = rs.getMetaData();
		int count = rsm.getColumnCount();
		while (rs.next()) {
			HashMap map = new HashMap();
			for (int i = 0; i < count; i++) {
				String columName = rsm.getColumnName((i + 1));
				Object sqlView = rs.getString(columName);
				map.put(columName, sqlView);
			}
			list.add(map);
		}
		state.close();
		return list;
	}

	/**
	 * 得到用户的信息,把所在区域和学校显示在第一次登陆后欢迎页面上
	 * 
	 * @param usname
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public ArrayList getUserInfor(String usname) throws Exception {

		ArrayList list = new ArrayList();
		String sql = "SELECT * FROM tschooluser,tarea WHERE tschooluser.area_id=tarea.area_id and userName='"
				+ usname + "'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		ResultSetMetaData rsm = rs.getMetaData();
		int count = rsm.getColumnCount();
		while (rs.next()) {
			HashMap map = new HashMap();
			for (int i = 0; i < count; i++) {
				String columName = rsm.getColumnName((i + 1));
				Object sqlView = rs.getString(columName);
				map.put(columName, sqlView);
			}
			list.add(map);
		}
		state.close();
		return list;
	}

	/**
	 * welcome页面显示已经登录过用户的信息
	 * 
	 * @param usname
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public ArrayList getNewContent(String userName,String school_type) throws Exception {
		String strSql = null;
		if (school_type.equals("normal")) {
			strSql = "SELECT * FROM tschoolinfor WHERE userName ='"
					+ userName + "' ORDER BY autoId DESC  LIMIT 1";
		}else {
			strSql = "SELECT * FROM tpschoolinfor WHERE userName ='"
					+ userName + "' ORDER BY autoId DESC  LIMIT 1";
		}
		
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(strSql);
		ResultSetMetaData rsm = rs.getMetaData();
		int count = rsm.getColumnCount();
		ArrayList listTemp = new ArrayList();
		while (rs.next()) {
			for (int i = 0; i < count; i++) {
				String columName = rsm.getColumnName((i + 1));
				listTemp.add(rs.getObject(columName));
			}
		}
		state.close();
		return listTemp;
	}

	/**
	 * 获取当月用户的学校id
	 * 
	 */
	public int getSchoolIdByMonth(String userName, String currentTime,String type)
			throws Exception {
		String strSql = null;
		if (type.equals("normal")) {
			strSql = "SELECT autoId FROM tschoolinfor WHERE userName='"
					+ userName + "' AND userTime LIKE '" + currentTime + "%'";
		}else {
			strSql = "SELECT autoId FROM tpschoolinfor WHERE userName='"
					+ userName + "' AND userTime LIKE '" + currentTime + "%'";
		}
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(strSql);
		int i = 0;
		if (rs.next()) {
			i = Integer.parseInt(rs.getString("autoId"));
		}
		state.close();
		return i;
	}

	/**
	 * 设置tschoolInfor表中的state为1
	 * 
	 * @throws Exception
	 * 
	 */
	public boolean upDateSchoolInforState(String userName, String time)
			throws Exception {
		boolean flag = false;
		String strSql = "UPDATE tschoolinfor SET state = 1 WHERE userName = ? AND userTime LIKE ?";
		PreparedStatement psmt = con.prepareStatement(strSql);
		psmt.setString(1, userName);
		psmt.setString(2, time + '%');
		int i = psmt.executeUpdate();
		if (i == 1) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 提交之后改变状态
	 * @param currentTime
	 * @param schoolId
	 * @throws SQLException 
	 */
	public int changeState(int schoolId,String time,String type) throws SQLException {
		String updState = "";
		if(type.equals("normal")){
			updState = "UPDATE tschoolinfor SET state=1,userTime = '"+time+"' WHERE autoId ="+schoolId;
		}else{
			updState = "UPDATE tpschoolinfor SET state=1,userTime = '"+time+"' WHERE autoId ="+schoolId;
		} 
		System.out.println(updState);
		Statement state = con.createStatement();
		int i= state.executeUpdate(updState);
		state.close();
		con.commit();
		return i;
		
	}
	
	/**
	 * 获取上个月的学校id
	 * 
	 */
	public int getSchoolIdPreMonth(String userName, String school_type) throws SQLException,
			ClassNotFoundException {
		String strSql = null;
		if (school_type.equals("normal")) {
			strSql = "SELECT autoId FROM tschoolinfor WHERE userName='"
					+ userName + "' ORDER BY autoId DESC  LIMIT 1";
		}else {
			strSql = "SELECT autoId FROM tpschoolinfor WHERE userName='"
					+ userName + "' ORDER BY autoId DESC  LIMIT 1";
		}
	
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(strSql);
		int i = 0;
		if (rs.next()) {
			i = rs.getInt("autoId");
		}
		state.close();
		return i;
	}

	/**
	 * 获取选择题的答案
	 * 
	 * @throws Exception
	 * 
	 */
	public ArrayList getPreMonthBlankAnswer(int schoolId) throws Exception {

		String strSql = "SELECT queId, blankText FROM tblankanswer WHERE schoolId ="
				+ schoolId;
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(strSql);
		ResultSetMetaData rsm = rs.getMetaData();
		int count = rsm.getColumnCount();
		ArrayList listTemp = new ArrayList();

		while (rs.next()) {
			HashMap mapTemp = new HashMap();
			for (int i = 0; i < count; i++) {
				String columName = rsm.getColumnName((i + 1));
				Object sqlView = rs.getString(columName);
				mapTemp.put(columName, sqlView);
			}
			listTemp.add(mapTemp);
		}
		return listTemp;
	}

	/**
	 * 获取选择题的答案
	 * 
	 * @throws Exception
	 * 
	 */
	public ArrayList getPreMonthChoiceAnswer(int schoolId) throws Exception {

		String strSql = "SELECT queId, choiceId, choiceText FROM tchoiceanswer WHERE schoolId ="
				+ schoolId;
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(strSql);
		ResultSetMetaData rsm = rs.getMetaData();
		int count = rsm.getColumnCount();
		ArrayList listTemp = new ArrayList();

		while (rs.next()) {
			HashMap mapTemp = new HashMap();
			for (int i = 0; i < count; i++) {
				String columName = rsm.getColumnName((i + 1));
				Object sqlView = rs.getString(columName);
				mapTemp.put(columName, sqlView);
			}
			listTemp.add(mapTemp);
		}
		return listTemp;
	}

	/**
	 * 判断判断该state，是否为1
	 * 
	 * @throws SQLException
	 */

	public int getSchoolState(String userName, String currentTime)
			throws SQLException {
		String strSql = "SELECT state FROM tschoolinfor WHERE userName='"
				+ userName + "' AND userTime LIKE '" + currentTime + "%'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(strSql);
		int i = 0;
		if (rs.next()) {
			i = rs.getInt("state");
		}
		state.close();
		if (i == 1) {
			return 1;
		} else {
			return 0;
		}
	}
	
	/**
	 * 判断一个学校是否提交过问卷，如果提交返回提交的autoId,userTime
	 */
	public ArrayList getState(String userName,String school_type) throws Exception {
		ArrayList listTemp = new ArrayList();
		String strSql = null;
		if (school_type.equals("normal")) {
			strSql = "SELECT autoId,userTime FROM tschoolinfor WHERE state = 1 AND userName = '"+userName+"' ORDER BY userTime DESC";
		}else {
			strSql = "SELECT autoId,userTime FROM tpschoolinfor WHERE state = 1 AND userName = '"+userName+"' ORDER BY userTime DESC";
		}
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(strSql);
		while (rs.next()) {
			JSONObject object = new JSONObject();
			object.put("schoolId",rs.getInt("autoId"));
			object.put("lastTime",rs.getString("userTime"));
			listTemp.add(object);
		}
		return listTemp;
	}
	
	/**
	 * 删除填空题临时表中相应学校本月份数据
	 */
	public void delBlankTemp(int schoolId,String currentTime,String type) throws SQLException{
		String sql = null;
		if(type.equals("normal")){
			sql = "DELETE FROM tblankanswer_temp WHERE schoolId = "+schoolId+" AND userTime LIKE '"+currentTime+"%'";
		}else {
			
			sql = "DELETE FROM tpblankanswer_temp WHERE schoolId = "+schoolId+" AND userTime LIKE '"+currentTime+"%'";
		}
		Statement state = con.createStatement();
		state.execute(sql);
		state.close();
	}
	
	/**
	 * 删除选择题临时表中相应学校本月份数据
	 */
	public void delChoiceTemp(int schoolId,String currentTime,String type) throws SQLException{
		String sql = null;
		if (type.equals("normal")) {
			
			sql = "DELETE FROM tchoiceanswer_temp WHERE schoolId = "+schoolId+" AND userTime LIKE '"+currentTime+"%'";
		}else {
			
			sql = "DELETE FROM tchoiceanswer_temp WHERE schoolId = "+schoolId+" AND userTime LIKE '"+currentTime+"%'";
		}
		Statement state = con.createStatement();
		state.execute(sql);
		state.close();
	}
	
	/**
	 * 删除填空题正式表中相应学校本月份数据
	 */
	public void delBlank(int schoolId,String currentTime,String type) throws SQLException{
		String sql = null;
		if(type.equals("normal")){
			sql = "DELETE FROM tblankanswer WHERE schoolId = "+schoolId+" AND userTime LIKE '"+currentTime+"%'";
		}else{
			sql = "DELETE FROM tpblankanswer WHERE schoolId = "+schoolId+" AND userTime LIKE '"+currentTime+"%'";
		}
		Statement state = con.createStatement();
		state.execute(sql);
		state.close();
	}
	
	/**
	 * 删除选择题正式表中相应学校本月份数据
	 */
	public void delChoice(int schoolId,String currentTime, String type) throws SQLException{
		String sql = null;
		if (type.equals("normal")) {
			
			sql = "DELETE FROM tchoiceanswer WHERE schoolId = "+schoolId+" AND userTime LIKE '"+currentTime+"%'";
		}else {
			
			sql = "DELETE FROM tpchoiceanswer WHERE schoolId = "+schoolId+" AND userTime LIKE '"+currentTime+"%'";
		}
		Statement state = con.createStatement();
		state.execute(sql);
		state.close();
	}
	
	/**
	 * 选择题 从上个月正式表中查出来，复制到本月的临时表中
	 * @param schoolId
	 * @param currentTime yy-mm-dd 格式
	 * @param time yy-mm格式
	 * @throws SQLException
	 */
	public void AddLastMonthChoiceAnswerCurrentTemp(int preSchoolId, int schoolId,String currentTime,String school_type) throws SQLException {
		//普通学校
		if (school_type.equals("normal")) {
			String tempChoiceSql = "Select * from tchoiceanswer where schoolId = "+preSchoolId;
			Statement state = con.createStatement();
			ResultSet rsTempChoice = state.executeQuery(tempChoiceSql);
			con.setAutoCommit(false); 
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			while (rsTempChoice.next()) {
				stmt.execute("INSERT INTO tchoiceanswer_temp (queId, choiceId, choiceText, schoolId, userTime) VALUES("
						+ rsTempChoice.getString("queId")
						+ ","
						+ rsTempChoice.getString("choiceId")
						+ ",'"
						+ rsTempChoice.getString("choiceText")
						+ "',"
						+ schoolId
						+ ",'" + currentTime + "')");

			}
			con.commit();
		}else {//教学点
			String tempChoiceSql = "Select * from tpchoiceanswer where schoolId = "+preSchoolId;
			Statement state = con.createStatement();
			ResultSet rsTempChoice = state.executeQuery(tempChoiceSql);
			con.setAutoCommit(false); 
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			while (rsTempChoice.next()) {
				stmt.execute("INSERT INTO tpchoiceanswer_temp (queId, choiceId, choiceText, schoolId, userTime) VALUES("
						+ rsTempChoice.getString("queId")
						+ ","
						+ rsTempChoice.getString("choiceId")
						+ ",'"
						+ rsTempChoice.getString("choiceText")
						+ "',"
						+ schoolId
						+ ",'" + currentTime + "')");

			}
			con.commit();
		}
		
		
	}
	
	/**
	 * 选择题 从上个月正式表中查出来，复制到本月的正式表中
	 * @param schoolId
	 * @param currentTime yy-mm-dd 格式
	 * @param time yy-mm格式
	 * @throws SQLException
	 */
	public void AddLastMonthChoiceAnswerCurrent(int preSchoolId, int schoolId,String currentTime,String type) throws SQLException {
		if(type.equals("normal")){
			String tempChoiceSql = "Select * from tchoiceanswer where schoolId = "+preSchoolId;
			Statement state = con.createStatement();
			ResultSet rsTempChoice = state.executeQuery(tempChoiceSql);
			con.setAutoCommit(false); 
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			while (rsTempChoice.next()) {
				stmt.execute("INSERT INTO tchoiceanswer (queId, choiceId, choiceText, schoolId, userTime) VALUES("
						+ rsTempChoice.getString("queId")
						+ ","
						+ rsTempChoice.getString("choiceId")
						+ ",'"
						+ rsTempChoice.getString("choiceText")
						+ "',"
						+ schoolId
						+ ",'" + currentTime + "')");

			}
		}else{

			String tempChoiceSql = "Select * from tpchoiceanswer where schoolId = "+preSchoolId;
			Statement state = con.createStatement();
			ResultSet rsTempChoice = state.executeQuery(tempChoiceSql);
			con.setAutoCommit(false); 
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			while (rsTempChoice.next()) {
				stmt.execute("INSERT INTO tpchoiceanswer (queId, choiceId, choiceText, schoolId, userTime) VALUES("
						+ rsTempChoice.getString("queId")
						+ ","
						+ rsTempChoice.getString("choiceId")
						+ ",'"
						+ rsTempChoice.getString("choiceText")
						+ "',"
						+ schoolId
						+ ",'" + currentTime + "')");

			}
		
		}
		 
		con.commit();
		
	}
	
	/**
	 * 填空题的答案从上个月正式表中复制到本月的临时表中
	 * @param schoolId
	 * @param currentTime 格式 yy-mm-dd
	 * @param time
	 * @throws SQLException
	 */
	public void AddLastMonthTextAnswerToCurrentTemp(int preSchoolId,int schoolId,String currentTime,String school_type) throws SQLException {
		if (school_type.equals("normal")) {
			String tempTextSql = "Select * from tblankanswer where schoolId = "+preSchoolId;
			Statement state = con.createStatement();
			ResultSet rsTempText = state.executeQuery(tempTextSql);
			con.setAutoCommit(false); 
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			while (rsTempText.next()) {
				int queId = new Integer(rsTempText.getString("queId"));
				String blankText = rsTempText.getString("blankText");
				if(blankText.length()==0) {
					blankText = "0";
				}
				stmt.execute("INSERT INTO tblankanswer_temp(queId, blankText, schoolId, userTime) VALUES("
						+ queId
						+ ",'"
						+ blankText
						+ "',"
						+ schoolId
						+ ",'"
						+ currentTime
						+ "')");	
			}
			con.commit();
		}else {
			String tempTextSql = "Select * from tpblankanswer where schoolId = "+preSchoolId;
			Statement state = con.createStatement();
			ResultSet rsTempText = state.executeQuery(tempTextSql);
			con.setAutoCommit(false); 
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			while (rsTempText.next()) {
				int queId = new Integer(rsTempText.getString("queId"));
				String blankText = rsTempText.getString("blankText");
				if(blankText.length()==0) {
					blankText = "0";
				}
				stmt.execute("INSERT INTO tpblankanswer_temp(queId, blankText, schoolId, userTime) VALUES("
						+ queId
						+ ",'"
						+ blankText
						+ "',"
						+ schoolId
						+ ",'"
						+ currentTime
						+ "')");	
			}
			con.commit();
		}
		
		
		}
	
	/**
	 * 填空题的答案从上个月正式表中复制到本月的正式表中
	 * @param schoolId
	 * @param currentTime 格式 yy-mm-dd
	 * @param time
	 * @throws SQLException
	 */
	public void AddLastMonthTextAnswerToCurrent(int preSchoolId,int schoolId,String currentTime,String schoolType) throws SQLException {
		if(schoolType.equals("normal")){
			String tempTextSql = "Select * from tblankanswer where schoolId = "+preSchoolId;
			Statement state = con.createStatement();
			ResultSet rsTempText = state.executeQuery(tempTextSql);
			con.setAutoCommit(false); 
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			while (rsTempText.next()) {
				int queId = new Integer(rsTempText.getString("queId"));
				String blankText = rsTempText.getString("blankText");
				if(blankText.length()==0) {
					blankText = "0";
				}
				stmt.execute("INSERT INTO tblankanswer(queId, blankText, schoolId, userTime) VALUES("
						+ queId
						+ ",'"
						+ blankText
						+ "',"
						+ schoolId
						+ ",'"
						+ currentTime
						+ "')");	
			}
			con.commit();
		}else{
			String tempTextSql = "Select * from tpblankanswer where schoolId = "+preSchoolId;
			Statement state = con.createStatement();
			ResultSet rsTempText = state.executeQuery(tempTextSql);
			con.setAutoCommit(false); 
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			while (rsTempText.next()) {
				int queId = new Integer(rsTempText.getString("queId"));
				String blankText = rsTempText.getString("blankText");
				if(blankText.length()==0) {
					blankText = "0";
				}
				stmt.execute("INSERT INTO tpblankanswer(queId, blankText, schoolId, userTime) VALUES("
						+ queId
						+ ",'"
						+ blankText
						+ "',"
						+ schoolId
						+ ",'"
						+ currentTime
						+ "')");	
			}
			con.commit();
		}
		
	}
	
}
