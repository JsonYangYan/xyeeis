package nercel.javaweb.schoolinfo;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.persistence.Id;
import javax.persistence.criteria.CriteriaBuilder.In;

import org.apache.commons.codec.digest.DigestUtils;

import nercel.javaweb.json.AnswerDbUtil;
import nercel.javaweb.score.ScoreDbUtil;

public class SchoolDataDb {
	
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
	 * 根据用户名和上个月的时间 获取学校的id
	 * @param userName
	 * @param lastMonth
	 * @return
	 * @throws SQLException 
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public int getSchoolId(String userName, String lastMonth) throws SQLException, ClassNotFoundException, IOException {
		
		String sqlString = "select * from tschoolinfor where userName = '"+ userName +"' and userTime LIKE '" + lastMonth + "%'";
		openConnection();
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sqlString);
		int i = 0;
		int status = 2;
		if (rs.next()) {
			i = Integer.parseInt(rs.getString("autoId"));
			status = Integer.parseInt(rs.getString("state"));
		}
		if(status == 2){
			i = 0;
		}	
		state.close();
		closeConnection();
		return i;
	}
	
	/**
	 * 根据问题的id获取问题
	 * @param queId
	 * @return
	 * @throws SQLException
	 */
	public ArrayList getBlackQuestion(String queIds,int schoolId,String type) throws SQLException {
		String sql = null;
		if(type.equals("mormal")){
			sql = "select queId,queContent,unitSymbol from tquestion where queId in ("+ queIds +")";
		}else {
			sql = "select queId,queContent,unitSymbol from tpquestion where queId in ("+ queIds +")";
		}
		ArrayList list = new ArrayList();
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
				if(columName.equals("queId")){
					if(schoolId == 0){
						map.put("answer", 0);
					}else {
						//
						String answer = getUserAnswer(Integer.parseInt(String.valueOf(rs.getString(columName))), schoolId,type);
						map.put("answer", answer);
					}
				}
			}
		
			list.add(map);
			
		}
		
		state.close();
		return list;
	}
	
	public String getUserAnswer(int queId, int schoolId,String type) throws SQLException {
		String sqlString = null;
		if (type.equals("normal")) {
			sqlString = "select blankText from tblankanswer where queId = "+ queId +" and schoolId = "+schoolId;
		}else {
			
			sqlString = "select blankText from tpblankanswer where queId = "+ queId +" and schoolId = "+schoolId;
		}
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sqlString);
		String blankText = null;
		if (rs.next()) {
			blankText = rs.getString("blankText");
		}
		state.close();
		return blankText;
	}
	
	
	public String checkPwd(String userName,String pwd) throws SQLException {
		String pw_1 = DigestUtils.md5Hex(pwd);
		String pw_2 = DigestUtils.md5Hex(pw_1);
		String sql = "select * from tschooluser where userName='"+ userName +"' and password = '"+ pw_2 +"'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		if (rs.next()) {
			
			return "ok";
		}
		state.close();
		return "error";
	}
	
	public String updpwd(String pwd, String userName) throws SQLException {
		String pw_1 = DigestUtils.md5Hex(pwd);
		String pw_2 = DigestUtils.md5Hex(pw_1);
		String sql = "update tschooluser set password ='" + pw_2 + "' where username = '"+ userName +"' "; 
		Statement state = con.createStatement();
		boolean result = state.execute(sql);
		state.close();
		if(!result){
			return "ok";
		}else {
			return "error";
		}
	}
	
	//获得县名
	public String getXian(String userName) throws SQLException{
		String sqlString = "select schoolArean from tschoolinfor where userName ='" + userName + "'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sqlString);
		String xian = null;
		if (rs.next()) {
			xian = rs.getString("schoolArean");
		}
		return xian;
	}
	
	public String getIds(String xian,String userTime) throws SQLException {
		String sql = "select autoId from tschoolinfor where 1 ";
		if (!(xian == null || xian.length() <=0)) {
			sql +=" and schoolArean ='"+ xian +"'";
		}
		sql +="and userTime like '"+userTime+"%'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		String ids ="";
		
		while (rs.next()) {
			xian = rs.getString("autoId");
			ids +=xian+",";
		}
		if(!ids.equals("")) {
			ids = ids.substring(0, ids.length()-1);
		}
		return ids;
	}
	public ArrayList getSchoolIdsArr(String xian,String userTime) throws SQLException {
		String sql = "select autoId from tschoolinfor where 1 ";
		if (!(xian == null || xian.length() <=0)) {
			sql +=" and schoolArean ='"+ xian +"'";
		}
		sql +="and userTime like '"+userTime+"%'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		ArrayList arr= new ArrayList<>();
		while (rs.next()) {
			int schoolId = Integer.parseInt(rs.getString("autoId"));
			arr.add(schoolId);
		}
		return arr;
	}
	
	public ArrayList listXianAvg(String xian, String userTime) throws Exception{
		String sql = "select oneone, onetwo,onetwo,onefour,onefive from tareascore where areaName ='"+xian+"' and createTime like '"+userTime+"%'";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		ArrayList<Object> data = new ArrayList<Object>();
		while (rs.next()) {
			data.add(rs.getInt("oneone"));
			data.add(rs.getInt("onetwo"));
			data.add(rs.getInt("onetwo"));
			data.add(rs.getInt("onefour"));
			data.add(rs.getInt("onefive"));
		}
		return data;
	}
	
	public int getId(String userName, String userTime) throws NumberFormatException, SQLException {
		String sqlString ="select autoId from tschoolinfor where userName ='" +userName+ "' and userTime like '%"+userTime+"%'";
		Statement statement = con.createStatement();
		ResultSet rSet = statement.executeQuery(sqlString);
		int id =0;
		if (rSet.next()) {
			id =Integer.parseInt(rSet.getString("autoId"));
		}
		return id;
	}
	
	public String getSchoolName(String userName, String userTime) throws SQLException{
		String sqlString ="select schoolName from tschoolinfor where userName ='" +userName+ "' and userTime like '%"+userTime+"%'";
		Statement statement = con.createStatement();
		ResultSet rSet = statement.executeQuery(sqlString);
		String schoolName =null;
		if (rSet.next()) {
			schoolName =rSet.getString("schoolName");
		}
		return schoolName;
	}
	
	public int getRanking(String ids,int id) throws SQLException {
		if (id ==0) {
			return 0;
		}
		String sqlString ="select count(*) rank from tschoolindexscore where schoolId in ("+ids+") and indexType =0 and schoolScore >(select schoolScore from tschoolindexscore where schoolId="+ id +" and indexType=0)";
		Statement statement = con.createStatement();
		ResultSet rSet = statement.executeQuery(sqlString);
		int rank =0;
		if (rSet.next()) {
			rank =Integer.parseInt(rSet.getString("rank"));
		}
		rank ++;
		return rank;
		
	}
	
	//得到自己学校五大维度的值
	public ArrayList getSelfAvg(int schoolId) throws SQLException {
		String sql = "select schoolScore from tschoolindexscore where schoolId ="+schoolId+" and indexType=1";
		Statement statement = con.createStatement();
		ResultSet rst = statement.executeQuery(sql);
		ArrayList<Float> aList = new ArrayList<Float>();
		while (rst.next()) {
			float score = rst.getFloat("schoolScore");
			aList.add(score);
		}
		return aList;
	}
	
	//得到五大维度区县的平均值
	public ArrayList getXianAvg(String userTime) throws SQLException {
		String sql = "SELECT round(AVG(oneone),2) one,round(AVG(onetwo),2) two,round(AVG(onethree),2) three,round(AVG(onefour),2) four,round(AVG(onefive),2) five FROM tareascore WHERE createTime like '"+userTime+"%'";
		Statement statement = con.createStatement();
		ResultSet rst = statement.executeQuery(sql);
		ArrayList<Float> aList = new ArrayList<Float>();
		while (rst.next()) {
			
			aList.add(rst.getFloat("one"));
			aList.add(rst.getFloat("two"));
			aList.add(rst.getFloat("three"));
			aList.add(rst.getFloat("four"));
			aList.add(rst.getFloat("five"));
		}
		return aList;
	}
}
