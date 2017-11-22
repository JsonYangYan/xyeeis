package nercel.javaweb.json;

import java.io.IOException;
import java.io.InputStream;
import java.lang.Thread.State;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AddAnswerTemp {
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
	 * 删除本月里用户保存的临时信息
	 * 
	 * @throws Exception
	 * @author yyn
	 */
	public void deleteAnswerTemp(int schoolId, String time,String type) throws Exception {
		String sql_blank = null;
		String sql_choice = null;
		Statement state = con.createStatement();
		if (type.equals("normal")) {
			sql_blank = "delete from tblankanswer_temp where schoolId = "
					+ schoolId + " and userTime like '" + time + "%'";
			
			state.execute(sql_blank);
			sql_choice = "delete from tchoiceanswer_temp where schoolId = "
					+ schoolId + " and userTime like '" + time + "%'";
		}else {
			sql_blank = "delete from tpblankanswer_temp where schoolId = "
					+ schoolId + " and userTime like '" + time + "%'";
			state.execute(sql_blank);
			sql_choice = "delete from tpchoiceanswer_temp where schoolId = "
					+ schoolId + " and userTime like '" + time + "%'";
		}
			
		
		state.execute(sql_choice);
		state.close();

	}

	/**
	 * 把选择题的答案存进临时表
	 * 
	 * @param choiceAnswer
	 * @param schoolId
	 * @param currentTime
	 * type 学校类型 如果为normal，则为普通学校，如果为tp，则为教学点
	 * @author yyn
	 * @throws SQLException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public String AddChoiceAnswer(String choiceAnswer, int schoolId,
			String currentTime,String type) throws SQLException, ClassNotFoundException,
			IOException {
		JSONArray choiceJsonArray = new JSONArray();
		choiceJsonArray = JSONArray.fromObject(choiceAnswer);
		con.setAutoCommit(false);
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_READ_ONLY);

		for (int i = 0; i < choiceJsonArray.size(); i++) {
			JSONObject obj = new JSONObject();
			obj = choiceJsonArray.getJSONObject(i);
			if (obj.getString("answerId").toString().length() != 0) {
				int queId = new Integer(obj.getString("questionId"));

				int choiceId = new Integer(obj.getString("answerId"));
				String choiceText = obj.getString("answertext");
					if (type.equals("normal")) {
						stmt.execute("INSERT INTO tchoiceanswer_temp(queId, choiceId, choiceText, schoolId, userTime) VALUES("
								+ queId
								+ ","
								+ choiceId
								+ ",'"
								+ choiceText
								+ "',"
								+ new Integer(schoolId) + ",'" + currentTime + "')");
					}else {
						stmt.execute("INSERT INTO tpchoiceanswer_temp(queId, choiceId, choiceText, schoolId, userTime) VALUES("
								+ queId
								+ ","
								+ choiceId
								+ ",'"
								+ choiceText
								+ "',"
								+ new Integer(schoolId) + ",'" + currentTime + "')");
					}
				
				
			}

		
		}
		con.commit();
		return "ok";

	}

	/**
	 * 填空题插入到临时表中
	 * 
	 * @param textAnswer
	 * @param schoolId
	 * @param currentTime
	 * @author yyn
	 * @throws SQLException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public String AddTextAnswer(String textAnswer, int schoolId,
			String currentTime,String type) throws SQLException, ClassNotFoundException,
			IOException {
		JSONArray textJsonArray = new JSONArray();
		textJsonArray = JSONArray.fromObject(textAnswer);
		con.setAutoCommit(false);
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		for (int j = 0; j < textJsonArray.size(); j++) {
			JSONObject obj = new JSONObject();
			obj = textJsonArray.getJSONObject(j);
			int queId = new Integer(obj.getString("questionId"));
			String blankText = obj.getString("answertext");
			if (blankText.length() != 0) {
				if (type.equals("normal")) {
					stmt.execute("INSERT INTO tblankanswer_temp(queId, blankText, schoolId, userTime) VALUES("
							+ queId
							+ ",'"
							+ blankText
							+ "',"
							+ schoolId
							+ ",'"
							+ currentTime + "')");
				}else {
					stmt.execute("INSERT INTO tpblankanswer_temp(queId, blankText, schoolId, userTime) VALUES("
							+ queId
							+ ",'"
							+ blankText
							+ "',"
							+ schoolId
							+ ",'"
							+ currentTime + "')");
				}
				
				
			}
		}
		con.commit();
		return "ok";

	}

	/**
	 * 多选题里边的填空题的更新
	 * 
	 * @param text_val
	 * @param schoolId
	 * @param currentTime
	 */
	public void updateText(String text_val, int schoolId, String currentTime)
			throws Exception {
		String sql_update = "Update tchoiceanswer_temp set choiceText = '"
				+ text_val + "' where choiceId =330 and schoolId = " + schoolId
				+ " and userTime like '" + currentTime + "%'";
		Statement state = con.createStatement();
		state.execute(sql_update);
		state.close();
	}

	/**
	 * 获取当月用户的学校id
	 * 
	 */
	public int getSchoolIdByMonth(String userName, String time, String type)
			throws Exception {
		String strSql = null;
		if (type.equals("normal")) {
			strSql = "SELECT autoId FROM tschoolinfor WHERE userName='"
					+ userName + "' AND userTime LIKE '" + time + "%'";
		}else {
			strSql = "SELECT autoId FROM tpschoolinfor WHERE userName='"
					+ userName + "' AND userTime LIKE '" + time + "%'";
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
}
