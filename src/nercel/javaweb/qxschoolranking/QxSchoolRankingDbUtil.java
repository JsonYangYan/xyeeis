package nercel.javaweb.qxschoolranking;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.text.*;

import net.sf.json.JSONObject;

public class QxSchoolRankingDbUtil {
	private Connection con = null;

	public void openConnection() throws SQLException, IOException,
			ClassNotFoundException {
		if (con == null || con.isClosed()) {

			Properties p = new Properties();
			InputStream in = nercel.javaweb.qxschoolranking.QxSchoolRankingDbUtil.class
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

	// 根据登陆名查找到所在区域
	public String getArean(String qxusername) throws SQLException,
			ClassNotFoundException {
		String sql = "SELECT * FROM tuser,tarea WHERE tuser.area_id=tarea.area_id and username='"
				+ qxusername + "'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		String Arean = "";
		if (rs.next()) {
			Arean = rs.getString("area_name");
		}
		state.close();
		return Arean;
	}

	// 对学校评估结果进行排名
	public ArrayList getSchoolRank(String areaName,String order,String currentTime,String lastTime )
			throws ClassNotFoundException, SQLException {
		ArrayList list = new ArrayList();
		ArrayList listCurrent = new ArrayList();
		ArrayList listLast = new ArrayList();
		listCurrent = getinitSchoolRank(areaName,order,currentTime);
		listLast = getinitSchoolRank(areaName,order,lastTime);
		int fig[] = new int[listCurrent.size()];
		fig = contrast(order,listCurrent,listLast);
		String sql = "SELECT schoolScore,schoolName,schoolArean,area_id from tschoolindexscore,tschoolinfor,tarea WHERE state=1 AND indexType=0 AND tschoolindexscore.schoolId=tschoolinfor.autoId AND tschoolinfor.schoolArean=tarea.area_name AND userTime LIKE '"
				+ currentTime + "%' AND tschoolinfor.schoolArean='"
				+ areaName + "' ORDER BY schoolScore "+order+"";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		int i = 0;
		while (rs.next()) {
			if (!rs.getString("schoolArean").equals("襄阳市")) {
				HashMap map = new HashMap();
				map.put("area_id", rs.getString("area_id"));
				map.put("area_name", rs.getString("schoolArean"));
				map.put("school_name", rs.getString("schoolName"));
				map.put("value", rs.getString("schoolScore"));
				map.put("fig",fig[i]);
				list.add(map);
			}
			i++;
		}
		state.close();
		return list;
	}
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 初始化一个线性表  数据类型为[{"name":"","score":""}]
	public ArrayList getinitSchoolRank(String areaName,String order,String time)
			throws ClassNotFoundException, SQLException {
		ArrayList list = new ArrayList();
		String sql = "SELECT schoolName FROM tschoolindexscore,tschoolinfor,tarea WHERE state=1 AND indexType=0 AND tschoolindexscore.schoolId=tschoolinfor.autoId AND tschoolinfor.schoolArean=tarea.area_name AND userTime LIKE '"
				+ time + "%' AND tschoolinfor.schoolArean='"
				+ areaName + "' ORDER BY schoolScore "+order+"";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()) {
			list.add(rs.getString("schoolName"));
		}
		state.close();
		return list;
	}
	
	//当月份和上月份对比
	public int[] contrast(String order,ArrayList<String> listCurrent,ArrayList<String> listLast) {
		int fig[] = new int[listCurrent.size()];
		if (!listLast.isEmpty()) {
			for (int i = 0; i < listCurrent.size(); i++) {
				for (int j = 0; j < listLast.size(); j++) {
					if ((listLast.get(j).toString()).equals(listCurrent.get(i).toString())) {
						if (order.equals("DESC")) {
							if (i < j) {
								fig[i] = 1;
								break;
							} else if (i > j) {
								fig[i] = 0;
								break;
							} else {
								fig[i] = 2;
								break;
							}
						}
						if (order.equals("ASC")) {
							if (i < j) {
								fig[i] = 0;
								break;
							} else if (i > j) {
								fig[i] = 1;
								break;
							} else {
								fig[i] = 2;
								break;
							}
						}
					}
					else{
						fig[i] = 2;
					}
				}
			}
		} else {
			for (int k = 0; k < listCurrent.size(); k++) {
				fig[k] = 2;
			}
		}

		return fig;
	}

	/*// 查询中间数据
	public ArrayList getMiddleSchoolRank(String areaName)
			throws ClassNotFoundException, SQLException {
		String time = getCurrentTime();
		ArrayList list = new ArrayList();
		String sql = "SELECT schoolScore,schoolName,schoolArean,area_id from tschoolindexscore,tschoolinfor,tarea where indexType=0 and tschoolindexscore.schoolId=tschoolinfor.autoId and tschoolinfor.schoolArean=tarea.area_name and userTime LIKE '%"
				+ time + "%' and tschoolinfor.schoolArean='"
				+ areaName + "' order by schoolScore DESC limit 10,80";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()) {
			if (!rs.getString("schoolArean").equals("襄阳市")) {
				HashMap map = new HashMap();
				map.put("area_id", rs.getString("area_id"));
				map.put("area_name", rs.getString("schoolArean"));
				map.put("school_name", rs.getString("schoolName"));
				map.put("value", rs.getString("schoolScore"));
				list.add(map);
			}
		}
		state.close();
		return list;
	}

	// 查询后十条数据
	public ArrayList getLastSchoolRank(String areaName)
			throws ClassNotFoundException, SQLException {
		String time = getCurrentTime();
		ArrayList list = new ArrayList();
		String sql = "SELECT schoolScore,schoolName,schoolArean, area_id from tschoolindexscore,tschoolinfor,tarea where indexType=0 and tschoolindexscore.schoolId=tschoolinfor.autoId and tschoolinfor.schoolArean=tarea.area_name and userTime LIKE '%"
				+ time + "%' and tschoolinfor.schoolArean='"
				+ areaName + "' order by schoolScore asc limit 0,10";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()) {
			if (!rs.getString("schoolArean").equals("襄阳市")) {
				HashMap map = new HashMap();
				map.put("area_id", rs.getString("area_id"));
				map.put("area_name", rs.getString("schoolArean"));
				map.put("school_name", rs.getString("schoolName"));
				map.put("value", rs.getString("schoolScore"));
				list.add(map);
			}
		}
		state.close();
		return list;
	}*/

}
