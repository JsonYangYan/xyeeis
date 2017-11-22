package nercel.javaweb.common;

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

public class ConvertSessionName {
	public Connection con = null;

	public void openConnection() throws SQLException, IOException,
			ClassNotFoundException {
		if (con == null || con.isClosed()) {

			Properties p = new Properties();
			InputStream in = ConvertSessionName.class
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
	 * 通过手机号找到用户名
	 * @throws SQLException 
	 */
	public String getNameBytel(String tel) throws SQLException{
		String userName = "";
		String sql = "SELECT username FROM tschooluser WHERE tel = '"+tel+"'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()) {
			userName = rs.getString("username").trim();
		}
		state.close();
		return userName;
	}
	
	/**
	 * 通过邮箱找到用户名
	 * @throws SQLException 
	 */
	public String getNameByEmail(String email) throws SQLException{
		String userName = "";
		String sql = "SELECT username FROM tschooluser WHERE email = '"+email+"'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()) {
			userName = rs.getString("username").trim();
			
		}
		state.close();
		return userName;
	}
	
}
