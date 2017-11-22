/**
 * 
 */
/**
 * @author yan
 *	对数据库进行管理的java
 */
package nercel.javaweb.sladmin;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class Dbsladmin {
	private  Connection con = null;

	public void openConnection() throws SQLException, IOException,
			ClassNotFoundException {
		if (con == null || con.isClosed()) {

			Properties p = new Properties();
			InputStream in = nercel.javaweb.qxschoolmanager.QxschoolmanagerDbUtil.class
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
	//全局变量
	String area_name="";
	//获得区县管理员所在区县的名称
	public String getAreaname(String username) throws SQLException, ClassNotFoundException{
		String sql = "SELECT area_name FROM tarea WHERE area_id=(SELECT area_id FROM user WHERE userName='"+username+"') ";
		System.out.println(sql);
		Statement state = con.createStatement(); 
		ResultSet rs = state.executeQuery(sql);		
		if(rs.next()) {
			area_name=rs.getString("area_name");
		}
		state.close();		
		return area_name;		
	}
	//获得区县试点学校总数
	public double getSchoolnum(String username) throws SQLException, ClassNotFoundException{
		String sql = "SELECT sch_num FROM tarea WHERE area_id=(SELECT area_id FROM user WHERE userName='"+username+"') ";		 
		Statement state = con.createStatement(); 
		ResultSet rs = state.executeQuery(sql);
		double sch_num=0;
		if(rs.next()) {
			sch_num=rs.getInt("sch_num");
		}
		state.close();		
		return sch_num;		
	}
	//各区县已填报学校数量
	public double getSubmitedSchoolnum() throws SQLException, ClassNotFoundException{
		String sql = "SELECT COUNT(userName) AS num FROM tschoolinfor WHERE schoolArean='"+area_name+"'";		 
		Statement state = con.createStatement(); 
		ResultSet rs = state.executeQuery(sql);
		double submitsch_num=0;
		if(rs.next()) {
			submitsch_num=rs.getInt("num");
		}
		state.close();		
		return submitsch_num;		
	}
	
	/**
	 * 返回欢迎XXX登录
	 * @param userName
	 * @return
	 * @throws SQLException
	 */
	public String getSchName(String userName) throws SQLException {
		String strSql = "SELECT sch_name from tuser WHERE username ='"+userName+"'";
		Statement stat = con.createStatement();
		ResultSet rs = stat.executeQuery(strSql);
		if( rs.next() ) {
			userName = rs.getString("sch_name");
		}
		return userName;
	}
}