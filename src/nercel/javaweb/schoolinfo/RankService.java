package nercel.javaweb.schoolinfo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class RankService {
	/**
	 * 在县中的排名
	 * @param userName
	 * @param userTime
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public HashMap<String, Object> getRanking(String userName, String userTime) throws SQLException, ClassNotFoundException, IOException {
		int shirank = shiRanking(userName, userTime);
		int xianrank = xianRanking(userName, userTime);
		HashMap<String, Object> map = new HashMap<>();
		map.put("xian", xianrank);
		map.put("shi", shirank);
		return map;
	}
	
	
	/**
	 * 市中排名
	 * @param userName
	 * @param userTime
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public int shiRanking(String userName, String userTime) throws ClassNotFoundException, SQLException, IOException {
		//String xian = getXian(userName);
		int id = getId(userName, userTime);
		String ids = getShiIds(null,userTime);
		SchoolDataDb sDb = new SchoolDataDb();
		sDb.openConnection();
		int rank = sDb.getRanking(ids, id);
		sDb.closeConnection();
		return rank;
	}
	
	/**
	 * 县中排名
	 * @param userName
	 * @param userTime
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public int xianRanking(String userName, String userTime) throws ClassNotFoundException, SQLException, IOException {
		String xian = getXian(userName);
		int id = getId(userName, userTime);
		String ids = getShiIds(xian,userTime);
		SchoolDataDb sDb = new SchoolDataDb();
		sDb.openConnection();
		int rank = sDb.getRanking(ids, id);
		sDb.closeConnection();
		return rank;
	}
	
	
	public int getId(String userName, String userTime) throws NumberFormatException, SQLException, ClassNotFoundException, IOException {
		SchoolDataDb sdb = new SchoolDataDb();
		sdb.openConnection();
		int id = sdb.getId(userName, userTime);
		sdb.closeConnection();
		return id;
	}
	
	public String getSchoolName(String userName, String userTime) throws SQLException, ClassNotFoundException, IOException {
		SchoolDataDb sdb = new SchoolDataDb();
		sdb.openConnection();
		String schoolName = sdb.getSchoolName(userName, userTime);
		sdb.closeConnection();
		return schoolName;
	}
	
	//获取市级学校的id
	public String getShiIds(String xian,String userTime) throws ClassNotFoundException, SQLException, IOException {
		SchoolDataDb sDb = new SchoolDataDb();
		sDb.openConnection();
		String idString = sDb.getIds(xian, userTime);
		sDb.closeConnection();
		return idString;
		
	}
	
	//获得哪个县的
	public String getXian(String userName) throws SQLException, ClassNotFoundException, IOException {
		SchoolDataDb sDb = new SchoolDataDb();
		sDb.openConnection();
		String xian = sDb.getXian(userName);
		sDb.closeConnection();
		return xian;
	}
	
	//五大维度的平均值
	public ArrayList<Object> avgList(String userName, String userTime) throws Exception {
		int id = getId(userName, userTime);
		String schoolName = getSchoolName(userName, userTime);
		String xian = getXian(userName);
		ArrayList arrSelfAvg = getSelfAvg(id);
		ArrayList arrXianAvg = getXianAvg(userName, userTime);
		ArrayList arrShiAvg = getShiAvg(userTime);
		HashMap<String, Object> selfMap = new HashMap<>();
		selfMap.put("name", schoolName);
		selfMap.put("type", "bar");
		selfMap.put("data", arrSelfAvg);
		ArrayList<Object> avg = new ArrayList<>();
		avg.add(selfMap);
		HashMap<String, Object> xianMap =new HashMap<>();
		xianMap.put("name", xian);
		xianMap.put("type", "bar");
		xianMap.put("data", arrXianAvg);
		avg.add(xianMap);
		HashMap<String, Object> shiMap =new HashMap<>();
		shiMap.put("name", "襄阳市");
		shiMap.put("type", "bar");
		shiMap.put("data", arrShiAvg);
		avg.add(shiMap);
		return avg;
	}
	
	//五大维度自己的值
	public ArrayList getSelfAvg(int schoolId) throws SQLException, ClassNotFoundException, IOException {
		SchoolDataDb sDb = new SchoolDataDb();
		sDb.openConnection();
		ArrayList map = sDb.getSelfAvg( schoolId);
		sDb.closeConnection();
		return map;                                 
	}
	
	//得到五大维度区县的平均值
	
	public ArrayList getXianAvg(String userName, String userTime) throws Exception {
		String xian = getXian(userName);
		SchoolDataDb sDb = new SchoolDataDb();
		sDb.openConnection();
		ArrayList data = sDb.listXianAvg(xian, userTime);
		return data;
	}
	
	//得到五大维度的市的平均值
	public ArrayList getShiAvg(String userTime) throws SQLException, ClassNotFoundException, IOException {
		SchoolDataDb sDb = new SchoolDataDb();
		sDb.openConnection();
		ArrayList arrayList = sDb.getXianAvg(userTime);
		return arrayList;
	}
}
