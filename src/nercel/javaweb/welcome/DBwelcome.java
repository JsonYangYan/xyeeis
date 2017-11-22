package nercel.javaweb.welcome;

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

public class DBwelcome {
	private  Connection con = null;

	public void openConnection() throws SQLException, IOException,
			ClassNotFoundException {
		if (con == null || con.isClosed()) {

			Properties p = new Properties();
			InputStream in = nercel.javaweb.welcome.DBwelcome.class
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
	
	public  boolean addpaper(String schoolArean,String schoolName,String schoolType,int teacherNumber,int studentNumber,String schoolTown,String personName,String telPhone,String eMail,int classNumber,String username) throws ClassNotFoundException, SQLException{
		boolean flag = false;	  
		String sql = "INSERT INTO tSchoolInfor (userName,schoolName,schoolType,schoolArean,schoolTown,classNumber,teacherNumber,studentNumber,personName,telPhone,eMail) VALUES(?,?,?,?,?,?,?,?,?,?,?)";		 
		PreparedStatement psmt = con.prepareStatement(sql);
		//System.out.println(sql);
		psmt.setString(1,username);
		psmt.setString(2,schoolName);
		psmt.setString(3,schoolType);
		psmt.setString(4,schoolArean);
		psmt.setString(5,schoolTown);
		psmt.setInt(6,classNumber);
		psmt.setInt(7,teacherNumber);
		psmt.setInt(8,studentNumber);
		psmt.setString(9,personName);
		psmt.setString(10,telPhone);
		psmt.setString(11,eMail);
		
		int i = psmt.executeUpdate();
		if (i == 1) {
			flag = true;
		}
		
		return flag;
	}
	
	//把已经提交的学校的状态state标示为"1"，信息统计中会用到
	public  boolean submitSchool(String schoolName) throws ClassNotFoundException, SQLException{
		boolean flag = false;	  
		String sql = "UPDATE tschooluser SET state=1 WHERE sch_name=?";		 
		PreparedStatement psmt = con.prepareStatement(sql);
		//System.out.println(sql);
		psmt.setString(1,schoolName);
		int i = psmt.executeUpdate();
		if (i == 1) {
			flag = true;
		}
		return flag;
	}
	/**
	 * welcome页面显示已经登录过用户的信息
	 * @param usname
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public  ArrayList editsearchContent(String usname) throws ClassNotFoundException, SQLException{
		
		   ArrayList list = new ArrayList();
		   String sql = "SELECT * FROM tschoolinfor WHERE userName ='"+usname+"'";
		   //System.out.println(sql);		   
		   Statement state = con.createStatement(); 
		   ResultSet rs = state.executeQuery(sql);
		   ResultSetMetaData rsm = rs.getMetaData();
		   int count = rsm.getColumnCount();
		   while(rs.next()) {
			  HashMap map = new HashMap();
			  for(int i=0; i< count; i++) {
				  String columName = rsm.getColumnName((i+1));
				  Object sqlView = rs.getString(columName);
			      map.put(columName, sqlView);
			  }
			  list.add(map);
		   }
		    state.close();
		   return list;
	}

	/**
	 * welcome页面更新信息
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
	public  boolean editContent(String schoolArean,String schoolName,String schoolType,int teacherNumber,int studentNumber,String schoolTown,String personName,String telPhone,String eMail,int classNumber,String username) throws ClassNotFoundException, SQLException{
		boolean flag = false;
		String sql = "UPDATE tschoolinfor SET schoolName=?,schoolType=?,schoolArean=?,schoolTown=?,classNumber=?,teacherNumber=?,studentNumber=?,personName=?,telPhone=?,eMail=? WHERE userName=?";
		PreparedStatement psmt = con.prepareStatement(sql);
		psmt.setString(1,schoolName);
		psmt.setString(2,schoolType);
		psmt.setString(3,schoolArean);
		psmt.setString(4,schoolTown);
		psmt.setInt(5,classNumber);
		psmt.setInt(6,teacherNumber);
		psmt.setInt(7,studentNumber);
		psmt.setString(8,personName);
		psmt.setString(9,telPhone);
		psmt.setString(10,eMail);
		psmt.setString(11,username);
		
		int i = psmt.executeUpdate();
		if (i == 1) {
			flag = true;
		}
		
		return flag;           
	}
	/**
	 * 得到用户的信息,把所在区域和学校显示在第一次登陆后欢迎页面上
	 * @param usname
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public  ArrayList getUserinfor(String usname) throws ClassNotFoundException, SQLException{
		
		   ArrayList list = new ArrayList();
		   String sql = "SELECT * FROM tschooluser,tarea WHERE tschooluser.area_id=tarea.area_id and userName='"+usname+"'";
		   //System.out.println(sql);		   
		   Statement state = con.createStatement(); 
		   ResultSet rs = state.executeQuery(sql);
		   ResultSetMetaData rsm = rs.getMetaData();
		   int count = rsm.getColumnCount();
		   while(rs.next()){
			  HashMap map = new HashMap();
			  for( int i=0; i<count; i++ ){
				  String columName = rsm.getColumnName((i+1));
				  Object sqlView = rs.getString(columName);
			      map.put(columName, sqlView);
			  }
			  list.add(map);
		   }
		    state.close();
		   return list;
	}
	
	/**
	 * 修改用户密码	
	 * @param password
	 * @param usname
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean resetpasswd(String password,String usname) throws ClassNotFoundException, SQLException{
		boolean flag = false;
		String sql = "UPDATE tschooluser SET password=? WHERE username = '"+usname+"'  ";	 
		PreparedStatement psmt = con.prepareStatement(sql);
		psmt.setString(1, password);
		int i = psmt.executeUpdate();
		if (i == 1) {
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 判断tschoolinfor表中是否存在记录,存在记录返回true,否则返回false
	 * @throws Exception 
	 * 
	 */
	public boolean getIsRecord(String userName) throws Exception {
	   boolean fig = false;
	   String strSql = "SELECT * FROM tschoolinfor WHERE userName='"+userName+"'";
	   Statement state = con.createStatement(); 
	   ResultSet rs = state.executeQuery(strSql);
	   if(rs.next()) {
		   fig = true;
	   }
	   state.close();
	   return fig;
	}
	
	/**
	 * 判断是否提交成功，state为1 返回true， state为0返回false
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
	
	
}