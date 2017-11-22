/**
 * 
 */
/**
 * @author yan
 *	对数据库进行管理的java
 */
package nercel.javaweb.admin;

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

public class Sql_Admin {

	private static Connection con = null;

	public static void openConnection() throws SQLException, IOException,
			ClassNotFoundException {
		if (con == null || con.isClosed()) {

			Properties p = new Properties();
			InputStream in = nercel.javaweb.admin.Sql_Admin.class
					.getResourceAsStream("admin-db.properties");
			p.load(in);
			Class.forName(p.getProperty("db_driver"));
			con = DriverManager.getConnection(p.getProperty("db_url"),
					p.getProperty("db_user"), p.getProperty("db_password"));
		}
	}

	public static void closeConnection() throws SQLException {
		try {
			if (con != null) {
				con.close();
			}
		} finally {
			con = null;
			System.gc();
		}
	}

	public static ArrayList paging(int fig,String startNum, String offset, String search)
			throws ClassNotFoundException, SQLException {
		ArrayList list = new ArrayList();
		String where = "SELECT id,username,sch_name,area_name FROM tschooluser,tarea WHERE tschooluser.area_id=tarea.area_id";
		if(fig==1){
			if (!search.equals(null)) {
				where += " AND tschooluser.username = '" + search + "' ";
			}
		}else{
			if (!search.equals(null)) {
				where += " AND tschooluser.sch_name LIKE '%" + search + "%' ";
			}
		}
		String sql = where + "LIMIT " + startNum + "," + offset;
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

	public static ArrayList get_area_data() throws ClassNotFoundException,
			SQLException {
		ArrayList list = new ArrayList();
		String sql = "SELECT * FROM tuser,tarea WHERE `role` != 'superadmin' and tuser.area_id=tarea.area_id";
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

	public static int pageAmount(String search) throws ClassNotFoundException,
			SQLException {
		ArrayList list = new ArrayList();
		String sql = "SELECT count(*) sum FROM tschooluser ";
		if (!search.equals(null)) {
			sql += " where sch_name like '%" + search + "%' ";
		}
		// System.out.println(sql);
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		ResultSetMetaData rsm = rs.getMetaData();
		int count = rsm.getColumnCount();
		int rowCount = 0;
		if (rs.next()) {
			rowCount = rs.getInt("sum");
		}
		state.close();
		return rowCount;
	}

	public static int pageAmount1() throws ClassNotFoundException, SQLException {
		ArrayList list = new ArrayList();
		String sql = "SELECT count(*) FROM tuser WHERE `role` != 'superadmin'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		ResultSetMetaData rsm = rs.getMetaData();
		int count = rsm.getColumnCount();
		int rowCount = 0;
		if (rs.next()) {
			rowCount = rs.getInt("count(*)");
		}
		state.close();
		return rowCount;
	}

	public static boolean deleteAction(int id) throws ClassNotFoundException,
			SQLException {
		boolean flag = false;
		String sql = "DELETE FROM tschooluser WHERE id=?";
		PreparedStatement psmt = con.prepareStatement(sql);
		psmt.setInt(1, id);
		int i = psmt.executeUpdate();
		if (i == 1) {
			flag = true;
		}
		return flag;
	}

	public static boolean deleteAdminAction(int id)
			throws ClassNotFoundException, SQLException {
		boolean flag = false;
		String sql = "DELETE FROM tuser WHERE id=?";
		PreparedStatement psmt = con.prepareStatement(sql);
		psmt.setInt(1, id);
		int i = psmt.executeUpdate();
		if (i == 1) {
			flag = true;
		}
		return flag;
	}

	public static boolean resetPasswd(int id, String password)
			throws ClassNotFoundException, SQLException {
		boolean flag = false;
		String sql = "UPDATE tschooluser SET password=? WHERE id=?";
		PreparedStatement psmt = con.prepareStatement(sql);
		psmt.setString(1, password);
		psmt.setInt(2, id);
		int i = psmt.executeUpdate();
		if (i == 1) {
			flag = true;
		}
		return flag;
	}

	public static boolean resetAdminPasswd(int id, String password)
			throws ClassNotFoundException, SQLException {
		boolean flag = false;
		String sql = "UPDATE tuser SET password=? WHERE id=?";
		PreparedStatement psmt = con.prepareStatement(sql);
		psmt.setString(1, password);
		psmt.setInt(2, id);
		// System.out.print(id);
		int i = psmt.executeUpdate();
		if (i == 1) {
			flag = true;
		}
		return flag;
	}

	public static ArrayList searchSchool(int fig,String txt)
			throws ClassNotFoundException, SQLException {
		ArrayList list = new ArrayList();
		String sql = "";
		String name = "";
		if(fig ==1 ){
			name = "+txt+";
			sql = "SELECT id,username,sch_name FROM tschooluser WHERE username = ? ";
		}else{
			name = "%" + txt + "%";
			sql = "SELECT id,username,sch_name FROM tschooluser WHERE sch_name LIKE ? ";
		}
		PreparedStatement psmt = con.prepareStatement(sql);
		psmt.setString(1, name);
		System.out.println(sql);
		ResultSet rs = psmt.executeQuery();
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
		return list;
	}

	public static boolean resetSuAdminpasswd(String password, String usname)
			throws ClassNotFoundException, SQLException {
		boolean flag = false;
		String sql = "UPDATE tuser SET password=? WHERE username = '" + usname
				+ "'  ";
		PreparedStatement psmt = con.prepareStatement(sql);
		psmt.setString(1, password);
		// System.out.print(password);
		int i = psmt.executeUpdate();
		if (i == 1) {
			flag = true;
		}
		return flag;
	}

	// 编辑查询区县管理员信息,显示在编辑页面上
	public static ArrayList editsearchAdminContent(String str)
			throws ClassNotFoundException, SQLException {

		ArrayList list = new ArrayList();
		String sql = "SELECT * FROM tuser,tarea WHERE tuser.area_id=tarea.area_id and id ="
				+ "'" + str + "'";
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

	// 更新区县管理员信息
	public static ArrayList editAdminContent(int userid, String ro, int areaid,
			String schname) throws ClassNotFoundException, SQLException {
		ArrayList list = new ArrayList();
		String sql = "UPDATE tuser SET role = " + "'" + ro + "'"
				+ ",area_id = " + "'" + areaid + "'" + ",sch_name = " + "'"
				+ schname + "'" + "WHERE id=" + userid;
		Statement state = con.createStatement();
		state.execute(sql);
		state.close();
		return list;
	}

	// 编辑查询学校用户信息,显示在编辑页面上
	public static ArrayList editsearchSchoolContent(String str)
			throws ClassNotFoundException, SQLException {

		ArrayList list = new ArrayList();
		String sql = "SELECT username,role,tschooluser.area_id,sch_name FROM tschooluser,tarea WHERE tschooluser.area_id=tarea.area_id and id ="
				+ "'" + str + "'";
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

	// 更新区学校用户信息
	public static ArrayList editSchoolContent(int userid, String ro,
			int areaid, String schname) throws ClassNotFoundException,
			SQLException {
		ArrayList list = new ArrayList();
		String sql = "UPDATE tschooluser SET role = " + "'" + ro + "'"
				+ ",area_id = " + "'" + areaid + "'" + ",sch_name = " + "'"
				+ schname + "'" + "WHERE id=" + userid;
		Statement state = con.createStatement();
		state.execute(sql);
		state.close();
		return list;
	}

	// 当删除的学校用户已经提交问卷，则要在tschoolifnfor中删除该学校

	// 1.在tschoolinfor中删除
	public static void deleteSchUser(String schoolName)
			throws ClassNotFoundException, SQLException {
		String sql = "DELETE FROM tschoolinfor WHERE schoolName=?";
		PreparedStatement psmt = con.prepareStatement(sql);
		psmt.setString(1, schoolName);
		psmt.executeUpdate();

	}

	// 2.单个删除入口函数
	public static void delCommitSchUser(String schoolName)
			throws ClassNotFoundException, SQLException {
		String sql = "SELECT schoolName FROM tschoolinfor ";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()) {
			String schName = rs.getString("schoolName");
			if (schName.equals(schoolName)) {
				deleteSchUser(schName);
			}
		}
	}

	// 批量删除入口函数
	public static void delBatchCommitSchUser(String[] schoolNames)
			throws ClassNotFoundException, SQLException {
		String sql = "SELECT schoolName FROM tschoolinfor ";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		for (int i = 0; i < schoolNames.length; i++) {
			while (rs.next()) {
				String schName = rs.getString("schoolName");
				if (schName.equals(schoolNames[i])) {
					deleteSchUser(schName);
				}
			}

		}
	}
}