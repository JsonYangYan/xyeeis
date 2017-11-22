
package nercel.javaweb.qxschoolmanager;

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

public class QxschoolmanagerDbUtil {

	private static Connection con = null;

	public static void openConnection() throws SQLException, IOException,
			ClassNotFoundException {
		if (con == null || con.isClosed()) {

			Properties p = new Properties();
			InputStream in = nercel.javaweb.qxschoolmanager.QxschoolmanagerDbUtil.class
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
	static int AreanId = 0;
	/**
	 * 根据区县用户名查找其所在区县的area_id
	 * @param qxusername
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public int getAreanId(String qxusername) throws SQLException,
			ClassNotFoundException {
		String sql = "SELECT * FROM tuser,tarea WHERE tuser.area_id=tarea.area_id AND username='"
				+ qxusername + "'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		if (rs.next()) {
			AreanId = rs.getInt("area_id");
		}
		state.close();
		return AreanId;
	}

	/**
	 * 根据区县用户名查找其所在区县的area_id
	 * @param qxusername
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public String getschooName(int id) throws SQLException,
			ClassNotFoundException {
		String schoolName = "";
		String sql = "select sch_name from tschooluser where id = ?";
		PreparedStatement psmt = con.prepareStatement(sql);
		psmt.setInt(1, id);
		ResultSet rs = psmt.executeQuery();
		if (rs.next()) {
			schoolName = rs.getString("sch_name");
		}
		
		return schoolName;
	}
	
	/**
	 * 获取当前区县全部学校/查询学校
	 * @param startNum
	 * @param offset
	 * @param search
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public ArrayList getAllAndSearchSchool(String startNum,String offset,String search)
			throws ClassNotFoundException, SQLException {
		ArrayList list = new ArrayList();
		String where = "SELECT * FROM tschooluser WHERE tschooluser.area_id="+AreanId+"";

		if (!search.equals(null)) {
			where += " and tschooluser.sch_name like '%" + search + "%' ";
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

	public int pageAmount(String search) throws ClassNotFoundException,
			SQLException {
		ArrayList list = new ArrayList();
		String sql = "SELECT count(*) sum FROM tschooluser WHERE area_id = "+AreanId+"";
		if (!search.equals(null)) {
			sql += " and sch_name like '%" + search + "%'  ";
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

	/**
	 * 在tschooluser表中删除学校用户
	 * @param id
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void deleteSchool(int id) throws ClassNotFoundException,
			SQLException {
		boolean flag = false;
		String sql = "DELETE FROM tschooluser WHERE id=?";
		PreparedStatement psmt = con.prepareStatement(sql);
		psmt.setInt(1, id);
		psmt.executeUpdate();
		
	}

	/**
	 * 当删除的学校用户已经提交问卷，则要在tschoolifnfor中删除该学校
	 * @param id
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */

	// 1.在tschoolinfor中删除,单个删除入口函数
	public void deleteSchUser(int id) throws ClassNotFoundException, SQLException {
		String schoolName = getschooName(id);
		String sql = "DELETE FROM tschoolinfor WHERE schoolName=?";
		PreparedStatement psmt = con.prepareStatement(sql);
		psmt.setString(1, schoolName);
		psmt.executeUpdate();
	}

	// 2.在tschoolinfor中删除批量删除入口函数
	public void delBatchSchUser(int[] ids) throws ClassNotFoundException, SQLException {
		String sql = "DELETE FROM tschoolinfor WHERE schoolName=?";
		PreparedStatement psmt = con.prepareStatement(sql);
		for(int i=0;i<ids.length;i++){
			String schoolName = getschooName(ids[i]);
			psmt.setString(1, schoolName);
		}
		psmt.executeUpdate();	
	}
	
	/**
	 * 重置密码
	 * @param id
	 * @param password
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean resetPasswd(int id, String password)
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
		psmt.close();
		return flag;
	}

	/**
	 * 编辑查询学校用户信息,显示在编辑页面上
	 * @param str
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public ArrayList editsearchSchoolContent(int id)
			throws ClassNotFoundException, SQLException {

		ArrayList list = new ArrayList();
		String sql = "SELECT * FROM tschooluser,tarea WHERE tschooluser.area_id=tarea.area_id and id = ?";
		PreparedStatement psmt = con.prepareStatement(sql);
		psmt.setInt(1, id);
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
		psmt.close();
		return list;
	}

	/**
	 * 更新区县学校用户信息
	 * @param userid
	 * @param ro
	 * @param areaid
	 * @param schname
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void editSchool(int userid, String role,
			int areaid, String schname) throws ClassNotFoundException,
			SQLException {
		ArrayList list = new ArrayList();
		String sql = "UPDATE tschooluser SET role = ?,areaid = ?,schname = ? where userid = ?";
		PreparedStatement psmt = con.prepareStatement(sql);
		psmt.setString(1,role);
		psmt.setInt(2,areaid);
		psmt.setString(3,schname);
		psmt.setInt(4,userid);
		psmt.executeUpdate();
		
	}

}