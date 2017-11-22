package nercel.javaweb.schoolinfo;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import org.apache.commons.codec.digest.DigestUtils;

public class GetSomeSchoolData {
	
	public HashMap getSomeData(String userName, String type) throws SQLException, ClassNotFoundException, IOException {
		
		String lastMonthString = getLastMonth();
		//根据用户名和时间获取用户的id
		int schoolId = getSchoolId(userName, lastMonthString);
		ArrayList aChoiceList = getChoiceQuestion(schoolId, type);
		HashMap<String, Object> map  = new HashMap<>();
		map.put("lastMonth", lastMonthString);
		map.put("data", aChoiceList);
		return map;
		
	}
	
	
	/**
	 * 获取上个月的时间
	 * @return
	 */
	public String getLastMonth() {
		
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -1);
		SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM");
		String lastMonthTime = format.format(c.getTime());
		return lastMonthTime;
	}
	
	//根据用户名和时间查出学校的id
	public int getSchoolId (String userName,String lastMonth) throws SQLException, ClassNotFoundException, IOException {
		SchoolDataDb sDb = new SchoolDataDb();
		try {
			sDb.openConnection();
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		SchoolDataDb sdb = new SchoolDataDb();
		int schoolId = sdb.getSchoolId(userName, lastMonth);
		sDb.closeConnection();
		return schoolId;
		
	}
	
	/**
	 * 获取填空题的一些问题
	 * @return
	 * @throws SQLException
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public ArrayList getChoiceQuestion(int schoolId, String type) throws SQLException {
		SchoolDataDb sDb = new SchoolDataDb();
		String queId = null;
		if(type.equals("mormal")){
			queId = "121,123,124,125,126,127,128,129,130,131";
		}else {
			queId = "3,4,5,6,7,8,13,20,21,37,38,39";
		} 
		
		try {
			sDb.openConnection();
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		ArrayList aList = sDb.getBlackQuestion(queId,schoolId,type);
		sDb.closeConnection();
		return aList;
	}
	
	/**
	 * 验证原密码是否正确
	 * @param userName
	 * @param pwd
	 * @return
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public String checkUserPwd(String userName, String pwd) throws ClassNotFoundException, SQLException, IOException {
		SchoolDataDb db = new SchoolDataDb();
		db.openConnection();
		String res = db.checkPwd(userName, pwd);
		db.closeConnection();
		return res;		
	}
	
	public String updpwd(String pwd, String userName) throws ClassNotFoundException, SQLException, IOException {
		SchoolDataDb sdbDb = new SchoolDataDb();
		sdbDb.openConnection();
		String result = sdbDb.updpwd(pwd, userName);
		sdbDb.closeConnection();
		System.out.println(result+"getsomeschool");
		return result;
	}
}
