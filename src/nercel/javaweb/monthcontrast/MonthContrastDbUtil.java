package nercel.javaweb.monthcontrast;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import nercel.javaweb.dbpool.MyJDBC;

public class MonthContrastDbUtil {

	/**
	 *根据登陆名获取区县名称 
	 */
	public String getArean(String qxusername) throws SQLException,
			ClassNotFoundException {
		String Arean = "";
		String sql = "SELECT area_name FROM tuser,tarea WHERE tuser.area_id=tarea.area_id AND username=?";
		ResultSet rs = MyJDBC.query(sql, qxusername);
		if (rs.next()) {
			Arean = rs.getString("area_name");
		}
		return Arean;
	}
	
	/**
	 * 获取各区县名称
	 * @return
	 * @throws Exception
	 */
	public ArrayList<String> getAllAreaName() throws Exception {
		ArrayList<String> arrayList = new ArrayList<String>();
		String sql = "SELECT DISTINCT areaName FROM tareascore ORDER BY id";
		ResultSet rs = MyJDBC.query(sql);
		while (rs.next()) {
			arrayList.add(rs.getString("areaName"));
		}
		return arrayList;
	}
	
	/**
	 * 按时间查询全部区县的综合绩效得分
	 */
	public ArrayList getAllAreanScore(String time,int fig) throws Exception{
		ArrayList list = new ArrayList();
		if(fig==0){
			String sql = "SELECT comprehensive FROM tareascore WHERE createTime like ? ORDER BY id";
			ResultSet rs = MyJDBC.query(sql, time+'%');
			while(rs.next()){
				list.add(rs.getFloat("comprehensive"));
			}
		}else if(fig==1){
			String sql = "SELECT oneone FROM tareascore WHERE createTime like ? ORDER BY id";
			ResultSet rs = MyJDBC.query(sql, time+'%');
			while(rs.next()){
				list.add(rs.getFloat("oneone"));
			}
		}else if(fig==2){
			String sql = "SELECT onetwo FROM tareascore WHERE createTime like ? ORDER BY id";
			ResultSet rs = MyJDBC.query(sql, time+'%');
			while(rs.next()){
				list.add(rs.getFloat("onetwo"));
			}
		}else if(fig==3){
			String sql = "SELECT onethree FROM tareascore WHERE createTime like ? ORDER BY id";
			ResultSet rs = MyJDBC.query(sql, time+'%');
			while(rs.next()){
				list.add(rs.getFloat("onethree"));
			}
		}else if(fig==4){
			String sql = "SELECT onefour FROM tareascore WHERE createTime like ? ORDER BY id";
			ResultSet rs = MyJDBC.query(sql, time+'%');
			while(rs.next()){
				list.add(rs.getFloat("onefour"));
			}
		}else if(fig==5){
			String sql = "SELECT onefive FROM tareascore WHERE createTime like ? ORDER BY id";
			ResultSet rs = MyJDBC.query(sql, time+'%');
			while(rs.next()){
				list.add(rs.getFloat("onefive"));
			}
		}
		
		return list;
	}
	
	/**
	 * 按时间查询单个区县的综合绩效得分
	 */
	public ArrayList getSingleAreanScore(String areaName,String time) throws Exception{
		ArrayList list = new ArrayList();
		String sql = "SELECT comprehensive FROM tareascore WHERE areaName = ? AND createTime like ? ORDER BY id";
		ResultSet rs = MyJDBC.query(sql, areaName,time+'%');
		while(rs.next()){
			list.add(rs.getFloat("comprehensive"));
		}

		return list;
	}

	/**
	 * 查询时间
	 */
	public ArrayList getAreanMonth() throws Exception{
		ArrayList list = new ArrayList();
		String sql = "SELECT DISTINCT createTime FROM tareascore ORDER BY createTime";
		ResultSet rs = MyJDBC.query(sql);
		while(rs.next()){
			list.add(rs.getString("createTime").substring(0, 7));
		}
		
		return list;
	}
	
	
}
