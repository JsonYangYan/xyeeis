package nercel.javaweb.json;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class AddAnswer {
	
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
	
	/**
	 * 删除  本月中  正式表中的答案
	 * @param schoolId
	 * @param currentTime
	 * @throws SQLException 
	 */
	public void delAnswer(int schoolId, String time,String type) throws SQLException {
		Statement state = con.createStatement();
		String sql_blank = null;
		String sql_choice = null;
		if (type.equals("normal")) {
			sql_blank = "DELETE from tblankanswer where schoolId = "+schoolId +" and userTime like '"+ time+"%'";
			sql_choice = "DELETE from tchoiceanswer where schoolId = "+schoolId +" and userTime like '"+ time+"%'";
		}else {
			sql_blank = "DELETE from tpblankanswer where schoolId = "+schoolId +" and userTime like '"+ time+"%'";
			sql_choice = "DELETE from tpchoiceanswer where schoolId = "+schoolId +" and userTime like '"+ time+"%'";
		}
		state.execute(sql_blank);
		state.execute(sql_choice);
		state.close();
		
	}
	
	/**
	 * 提交之后改变状态
	 * @param currentTime
	 * @param schoolId
	 * @throws SQLException 
	 */
	public void changeState(int schoolId, String type) throws SQLException {
		String updState = null;
		if (type.equals("normal")) {
			
			updState = "UPDATE tschoolinfor SET state=1 where autoId ="+schoolId;
		}else {
			
			updState = "UPDATE tpschoolinfor SET state=1 where autoId ="+schoolId;
		}
		Statement state = con.createStatement();
		con.setAutoCommit(false);
		state.execute(updState);
		con.commit();
		state.close();
	}
	
	/**
	 * 选择题 从临时表中查出来，复制到正式表中
	 * @param schoolId
	 * @param currentTime yy-mm-dd 格式
	 * @param time yy-mm格式
	 * @throws SQLException
	 */
	public String AddChoiceAnswerToFormal(int schoolId,String currentTime, String time, String type) throws SQLException {
		String tempChoiceSql = null;
		if(type.equals("normal")){
			tempChoiceSql = "Select * from tchoiceanswer_temp where schoolId = "+schoolId+" and userTime like'"+time+"%'";
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
		}else {
			tempChoiceSql = "Select * from tpchoiceanswer_temp where schoolId = "+schoolId+" and userTime like'"+time+"%'";
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
		return "ok";
		
	}
	
	/**
	 * 填空题的答案从临时表中复制到正式表
	 * @param schoolId
	 * @param currentTime
	 * @param time
	 * @throws SQLException
	 */
	public void AddTextAnswerToFormal(int schoolId,String currentTime, String time, String type) throws SQLException {
		
		if (type.equals("normal")) {
			String tempTextSql = "Select * from tblankanswer_temp where schoolId = "+schoolId+" and userTime like'"+time+"%'";
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
		}else {
			String tempTextSql = "Select * from tpblankanswer_temp where schoolId = "+schoolId+" and userTime like'"+time+"%'";
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
			System.out.println(tempTextSql);
		}
		
		con.commit();
		}
	
	
}
