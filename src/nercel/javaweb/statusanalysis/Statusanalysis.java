package nercel.javaweb.statusanalysis;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import nercel.javaweb.allassessment.AssessmentDbUtil;

public class Statusanalysis{
	
	private static  Connection con = null;

	public void openConnection() throws SQLException, IOException,
			ClassNotFoundException {
		if (con == null || con.isClosed()) {

			Properties p = new Properties();
			InputStream in = nercel.javaweb.schooldetail.SchooldetailDbUtil.class.getResourceAsStream("/nercel/javaweb/admin/admin-db.properties");
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
	 * 
	 * @param name 县区的名称
	 * @param queId 问题的id
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 */
	public  ArrayList getQuizContent(String name, int queId, String currentTime) throws SQLException, ClassNotFoundException{
			ArrayList list = new ArrayList();
			Statement state = con.createStatement();
			ResultSet rs=null;
			String tchoice_sql="select autoId,choiceContent from tchoice where queId="+queId;
			rs = state.executeQuery(tchoice_sql);
			ArrayList schoolId = getSchoolId(name, currentTime);
			String schoolIds = getSchoolIds(schoolId);
			HashMap<Integer, Integer> choiceSchNumMap=choiceSum(queId, schoolIds, currentTime);
			while(rs.next()){
				int choicesum=0;
				String choiceContent = (String) rs.getObject("choiceContent");
				int autoId= Integer.parseInt(rs.getString("autoId"));
				HashMap map = new HashMap();
				map.put("name", choiceContent);
				//根据地区name值查出这个区里所有的学校schoolId 可以算出学校的总个数
				//查出该区选authId的有多少人	
				if(choiceSchNumMap!=null && choiceSchNumMap.containsKey(autoId))
					choicesum = choiceSchNumMap.get(autoId);
				map.put("value", choicesum);
				list.add(map);
			}
			state.close();	
			return list;
			
	}
	
	/**
	 * 将arrayList转变成用逗号隔开的字符串
	 * @param schoolId
	 * @return
	 */
	private String getSchoolIds(ArrayList schoolId) {
		String schoolIds = "";
		for(int i=0;i<schoolId.size();i++){
			schoolIds+=schoolId.get(i)+",";
		}
		//去除掉最后一个逗号
		if(!schoolIds.equals(""))
		schoolIds = schoolIds.substring(0,schoolIds.length()-1);
		return schoolIds;
	}
	
	/**
	 * 根据地区的名字查询该区所有学校的schoolId
	 * @param name
	 * @return
	 * @throws SQLException
	 */
	public ArrayList getSchoolId(String name, String currentTime) throws SQLException{
		ArrayList list = new ArrayList();
		Statement state = con.createStatement(); 
		ResultSet rs=null;
			//4.执行语句
		String tschool_sql="select autoId from tschoolinfor where state = 1 AND schoolArean='"+name+"' "
				+ "AND userTime LIKE '" +currentTime+"%'";
		rs = state.executeQuery(tschool_sql);
		while(rs.next()){
			int schoolId = (int) rs.getObject("autoId");
			list.add(schoolId);
		}
		state.close();
		return list;
	}
	
	/**
	 * 根据选项id和区域的学校门 得到选这个选项的数量
	 * @param choiceId
	 * @param schoolIds
	 * @return
	 * @throws SQLException
	 */
	public HashMap<Integer, Integer> choiceSum(int queId,String schoolIds, String currentTime) throws SQLException {
		int choiceId=0;
		HashMap<Integer, Integer> choiceNumMap=new HashMap();
		String	tchoice_sql = "select choiceId,count(*) sum from tchoiceanswer a left join "
				+ "tschoolinfor b on a.schoolId = b.autoId where b.state = 1 and a.queId ="+queId+" "
		+ "	and a.schoolId in (" + schoolIds + ") AND a.userTime LIKE '" + currentTime + "%' GROUP BY choiceId";
//		String tchoice_sql = "SELECT choiceId,count(*) AS sum from tchoiceanswer, tschoolinfor WHERE tschoolinfor.state = 1 AND tschoolinfor.autoId = tchoiceanswer.schoolId AND queId="
//				+ queId
//				+ " AND tchoiceanswer.userTime LIKE '"
//				+ currentTime
//				+ "%' GROUP BY choiceId";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(tchoice_sql);
		int sum = 0;
		while(rs.next()) {
			choiceId=rs.getInt("choiceId");
			sum = rs.getInt("sum");
			choiceNumMap.put(choiceId, sum);
		}
		return choiceNumMap;
	}
	
//	public int choiceSum(int choiceId,String schoolIds, String currentTime) throws SQLException {
//		if (schoolIds.equals("")) {
//			return 0;
//		}
//		String	tchoice_sql = "select count(*) sum from tchoiceanswer a left join tschoolinfor b on a.schoolId = b.autoId where b.state = 1 and a.choiceId ="+choiceId+" "
//				+ "	and a.schoolId in (" + schoolIds + ") AND a.userTime LIKE '" + currentTime + "%'";
//		Statement state = con.createStatement();
//		ResultSet rs = state.executeQuery(tchoice_sql);
//		int sum = 0;
//		if (rs.next()) {
//			sum = Integer.parseInt(rs.getString("sum"));
//		}
//		return sum;
//
//	}
	
	/**
	 * 获取提交的所有学校id
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Integer> getAllSchoolIdNumber(String currentTime) throws Exception {
		Statement state = con.createStatement(); 
		ResultSet rs=null;
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		String sql = "SELECT autoId FROM tschoolInfor WHERE state = 1 AND userTime LIKE '"+ currentTime +"%'";
		rs = state.executeQuery(sql);
		while(rs.next()) {
				arrayList.add(Integer.parseInt(rs.getString("autoId")));
			}
		state.close();
		return arrayList;
	}
	
	/**
	 * 获取所有以及指标的分数
	 * @param schoolId
	 * @return
	 * @throws Exception
	 */
	
	public ArrayList<Float> getAllFirstIndexScore( int schoolId) throws Exception {
		Statement state = con.createStatement(); 
		ResultSet rs=null;
		ArrayList<Float> arrayList = new ArrayList<Float>();
		String sql = "SELECT schoolScore FROM tschoolindexScore  WHERE schoolId ="  + schoolId + " AND indexType = 1 ORDER BY indexType ASC";
		rs = state.executeQuery(sql);
		while (rs.next()) {
			arrayList.add(Float.parseFloat(rs.getString("schoolScore")));
			}
		state.close();
		return arrayList;
	}
	
	/**
	 * 学校的各区域学校提交的数量
	 * @param schoolArean
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Integer> getAllSchoolIdNumberBySchoolArean(String schoolArean, String currentTime) throws Exception {
		Statement state = con.createStatement(); 
		ResultSet rs=null;
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		String sql = "SELECT autoId FROM tschoolInfor WHERE schoolArean = '" + schoolArean + "' "
				+ "AND state = 1 AND userTime LIKE '"+ currentTime +"%'";
		rs = state.executeQuery(sql);
		while(rs.next()) {
				arrayList.add(rs.getInt("autoId"));
			}
		state.close();
		return arrayList;
	}
	
	/**
	 * 获取当前学校的区域名字
	 * @return
	 * @throws Exception 
	 */
	 public ArrayList<String> getAllAreaName() throws Exception {
		 Statement state = con.createStatement(); 
			ResultSet rs=null;
			ArrayList<String> arrayList = new ArrayList<String>();
			String sql = "SELECT area_name FROM tarea";
			rs = state.executeQuery(sql);
			while(rs.next()) {
					arrayList.add(rs.getString("area_name"));
				}
			state.close();
			return arrayList;
	 }
	
	
	
	
	/**
	 * 获取各区县一级指标值
	 * @param currentTime
	 * @return
	 * @throws Exception
	 */
	
	public ArrayList getEveryAreanScore(String currentTime) throws Exception {
		
		AssessmentDbUtil assessmentDbUtil = new AssessmentDbUtil();
		assessmentDbUtil.openConnection();
		ArrayList<String> listArea = assessmentDbUtil.getAllAreaName();
		ArrayList list = new ArrayList();

		for (int i = 0; i < listArea.size(); i++) {
			ArrayList<Integer> arrayList = new ArrayList<Integer>();
			HashMap hashMap = new HashMap();

			if (listArea.get(i).equals("襄阳市")) {
				arrayList = assessmentDbUtil.getCityScore(currentTime);

			} else {
				arrayList = assessmentDbUtil.getAreanScore(listArea.get(i).toString(), currentTime);
			}

			hashMap.put("area", listArea.get(i));
			hashMap.put("data", arrayList);
			list.add(hashMap);
		}

		assessmentDbUtil.closeConnection();
		return list;
	}


}
