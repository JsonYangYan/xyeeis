package nercel.javaweb.forgotpassword;

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

public class ForgotPasswordDbUtil {
	public Connection con = null;

	public void openConnection() throws SQLException, IOException,
			ClassNotFoundException {
		if (con == null || con.isClosed()) {

			Properties p = new Properties();
			InputStream in = ForgotPasswordDbUtil.class
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
	 * 通过用户名和邮箱号判断用户的存在
	 */
	public boolean judgeUser(String userName, String email) throws Exception {
		boolean flag = false;
		int i = 0;
		String sql = "SELECT count(username) AS num FROM tschooluser WHERE username = '"+userName+"' AND email = '"+email+"'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while(rs.next()){
			i = rs.getInt("num");
		}
		if(i!=0){
			flag = true;
		}
		state.close();
		return flag;
	}
	
	/**
	 * 通过用户名判断用户的存在
	 */
	public boolean judgeUserByuserName(String userName) throws Exception {
		boolean flag = false;
		int i = 0;
		String sql = "SELECT count(username) AS num FROM tschooluser WHERE username = '"+userName+"'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while(rs.next()){
			i = rs.getInt("num");
		}
		if(i!=0){
			flag = true;
		}
		state.close();
		return flag;
	}
	
	/**
	 * 将秘钥和过期时间存入tschooluser表中
	 */
	public void updateKeyAndTime(String secretKey,String outDate,String userName) throws Exception {
		String sql = "UPDATE tschooluser set validatacode = '"+secretKey+"',outdate = '"+outDate+"' WHERE username = '"+userName+"'";
		Statement state = con.createStatement();
		state.executeUpdate(sql);
		con.close();
	}
	
	/**
	 * 获取tschooluser表中outdate
	 */
	public String getoutDate (String userName) throws Exception {
		String outDate = "";
		String sql = "SELECT outdate FROM tschooluser WHERE username = '"+userName+"'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()){
			outDate= rs.getString("outdate");
		}
		state.close();
		return outDate;
	}
	
	/**
	 * 获取tschooluser表中validatacode
	 */
	public String getValidatacode (String userName) throws Exception {
		String validatacode = "";
		String sql = "SELECT validatacode FROM tschooluser WHERE username = '"+userName+"'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()){
			validatacode= rs.getString("validatacode");
		}
		state.close();
		return validatacode;
	}
	
	/**
	 * 获取tschooluser表中email
	 */
	public String getEmail (String userName) throws Exception {
		String email = "";
		String sql = "SELECT email FROM tschooluser WHERE username = '"+userName+"' or tel = '"+userName+"' or email = '"+userName+"'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()){
			email= rs.getString("email");
		}
		state.close();
		return email;
	}
	
	/**
	 * 重置密码
	 */
	public boolean updatePassWord(String passWord,String userName) throws Exception {
		boolean flag = false;
		int i = 0;
		String sql = "UPDATE tschooluser set password = '"+passWord+"' WHERE username = '"+userName+"'";
		Statement state = con.createStatement();
		i = state.executeUpdate(sql);
		if(i!=0){
			flag = true;
		}
		state.close();
		return flag;
	}
	
}
