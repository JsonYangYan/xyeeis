package nercel.javaweb.register;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

/**
 * @Description: 数据库操作，包括连接，查询数据等操作
 * @author: Stone
 * @version: 0.1,上午8:44:54
 * @param:
 */

public class DBUtil {
	private  Connection con = null;

	public void openConnection() throws SQLException, IOException,
			ClassNotFoundException {
		if (con == null || con.isClosed()) {

			Properties p = new Properties();
			InputStream in = nercel.javaweb.register.DBUtil.class
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
	
	//获得用户名
	public String getName(String sql) throws SQLException {
		String name = null;
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		
		if (rs.next()) {
			name = rs.getString("username");
		}
		rs.close();

		return name;
	}
	
	//用户密码
	public String getPassword(String sql) throws SQLException {
		String pw = null;
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		
		if (rs.next()) {
			pw = rs.getString("password");
		}
		rs.close();
		return pw;
	}
	
	//用户角色
	public String getRole(String sql) throws SQLException {
		String role = null;
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		if (rs.next()) {
			role = rs.getString("role");
		}
		rs.close();

		return role;
	}
	
	/**
	 * 验证学校用户是不是第一次登录
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public boolean isCommited(String sql) throws SQLException {
		boolean flag = false;
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		if (rs.next()) {
			flag = true;
		}
		rs.close();
		return flag;
	}
	
	/**
	 * 判断是否提交成功
	 * @author stone
	 * @param schoolName
	 * @return
	 * @throws Exception 
	 */
	public boolean getIsSubPaper(String username) throws Exception {
		String strSql = "SELECT state from tschooluser WHERE username ='"+username+"'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(strSql);
		boolean fig = false;
		int st = 0;
		if(rs.next()) {
			st = rs.getInt("state");
		}
		if(st > 0) {
			fig = true;
		}
		return fig;
	}
		
	/**
	 * 判断tschoolinfor表中是否存在记录,存在记录返回true,否则返回false
	 * @throws Exception 
	 * 
	 */
	public boolean getIsRecord(String userName,String school_type) throws Exception {
	   boolean fig = false;
	   String strSql = null;
	   if (school_type.equals("normal")) {
		
		   strSql = "SELECT * FROM tschoolinfor WHERE userName='"+userName+"' OR telPhone = '"+userName+"' OR eMail = '"+userName+"'";
	   }else {
		
		  strSql = "SELECT * FROM tpschoolinfor WHERE userName='"+userName+"' OR telPhone = '"+userName+"' OR eMail = '"+userName+"'";
	   }
	   Statement state = con.createStatement(); 
	   ResultSet rs = state.executeQuery(strSql);
	   if(rs.next()) {
		   fig = true;
	   }
	   state.close();
	   return fig;
	}
	
}