package nercel.javaweb.schoolregister;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Flags.Flag;
import javax.management.relation.Role;

import com.mysql.jdbc.Statement;

import nercel.javaweb.json.AnswerDbUtil;

public class RegisterDb {
	
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
	 * 检查用户名是否被注册
	 * @param userName
	 * @return
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public String checkUserName(String userName) throws ClassNotFoundException, SQLException, IOException {
		String sqlString ="select * from tschooluser where username ='"+userName+"'";
		openConnection();
		Statement statement = (Statement) con.createStatement();
		ResultSet rSet = statement.executeQuery(sqlString);
		String flag = null;
		if (rSet.next()) {
			flag = "error";
		}else {
			flag = "ok";
		}
		statement.close();
		closeConnection();
		return flag;
		
	}
	
	public String checkIsReg(int area_id, String schoolName) throws ClassNotFoundException, SQLException, IOException {
		String sqlString ="select * from tschooluser where area_id ="+area_id+" and sch_name ='"+schoolName+"'";
		openConnection();
		Statement statement = (Statement) con.createStatement();
		ResultSet rSet = statement.executeQuery(sqlString);
		String flag = null;
		if (rSet.next()) {
			flag = "error";
		}else {
			flag = "ok";
		}
		statement.close();
		closeConnection();
		return flag;
	}
	
	public String register(int area_id,String schoolName,String userName,String pwd) throws ClassNotFoundException, SQLException, IOException {
		String sqlString =
			"insert into  tschooluser (username,password,role,area_id,sch_name) "
				+ "values('"+userName+"','"+pwd+"','user',"+area_id+",'"+schoolName+"')";
		openConnection();
		Statement statement = (Statement) con.createStatement();
		int res = statement.executeUpdate(sqlString);
		String result = null;
		if (res>0) {
			result = "ok";
		}else {
			result ="error";
		}
		System.out.println(sqlString);
		statement.close();
		closeConnection();
		return result;
	}
	
	public ArrayList getSchoolName(int area_id) throws SQLException, ClassNotFoundException, IOException {
		String sqlString ="select school_name from tschool where area_id="+area_id;
		openConnection();
		Statement statement = (Statement) con.createStatement();
		ArrayList list = new ArrayList();
		ResultSet reSet = statement.executeQuery(sqlString);
		while (reSet.next()) {
			list.add(reSet.getString("school_name"));
		}
		statement.close();
		closeConnection();
		return list;
	}
}
