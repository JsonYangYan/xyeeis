package nercel.javaweb.print;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import nercel.javaweb.allassessment.AssessmentDbUtil;
import nercel.javaweb.qxallassessment.QxAssessmentDbUtil;

public class PdfDbUtil {
	public Connection con = null;

	public void openConnection() throws SQLException, IOException,
			ClassNotFoundException {
		if (con == null || con.isClosed()) {

			Properties p = new Properties();
			InputStream in = PdfDbUtil.class
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

	// 把schoolId用逗号分开
	private String getSchoolIds(ArrayList schoolId) {
		String schoolIds = "";
		for (int i = 0; i < schoolId.size(); i++) {
			schoolIds += schoolId.get(i) + ",";
		}
		if (!schoolIds.equals(""))
			schoolIds = schoolIds.substring(0, schoolIds.length() - 1);
		return schoolIds;
	}

	/**
	 * 获取襄阳市提交的所有学校id
	 * 
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Integer> getAllSchoolIdNumber(String areaName,String currentTime)
			throws Exception {
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		String sql = "";
		if(areaName.equals("襄阳市")){
			sql = "SELECT autoId FROM tschoolinfor WHERE state=1 AND userTime LIKE '%"
					+ currentTime + "%'";
		}else{
			sql = "SELECT autoId FROM tschoolinfor WHERE state=1 AND userTime LIKE '%"
					+ currentTime + "%' AND schoolArean = '" + areaName + "'";
		}
		
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		while (rs.next()) {
			arrayList.add(Integer.parseInt(rs.getString("autoId")));
		}
		state.close();
		return arrayList;
	}

	// ///////////////////计算校园网在1-20M 20-100M
	// 100M以上的占有率////////////////////////////
	/**
	 * 计算校园网在1-20M 20-100M 100M以上各个区间的数量 分别对应fig=1,2,3
	 * 
	 * @throws Exception
	 */
	public float getNetwork(int fig, String areaName, String currentTime)
			throws Exception {
		float numTemp = 0;
		String schoolIds = "";
		String sql = "";
		schoolIds = getSchoolIds(getAllSchoolIdNumber(areaName,currentTime));
		// 判断schoolIds是否为空
		if (!schoolIds.isEmpty()) {
			if (fig == 1) {
				sql = "SELECT COUNT(autoId) AS num FROM tblankanswer WHERE userTime LIKE '%"
						+ currentTime
						+ "%' AND queId='121' AND blankText between 1 and 20 AND schoolId in("
						+ schoolIds + ")";
			} else if (fig == 2) {
				sql = "SELECT COUNT(autoId) AS num FROM tblankanswer WHERE userTime LIKE '%"
						+ currentTime
						+ "%' AND queId='121' AND blankText between 20 and 100 AND schoolId in("
						+ schoolIds + ")";
			} else {
				sql = "SELECT COUNT(autoId) AS num FROM tblankanswer WHERE userTime LIKE '%"
						+ currentTime
						+ "%' AND queId='121' AND blankText>100 AND schoolId in("
						+ schoolIds + ")";
			}

			Statement state = con.createStatement();
			ResultSet rs = state.executeQuery(sql);
			while (rs.next()) {
				numTemp = rs.getInt("num");
			}
			state.close();
		} else {
			numTemp = 0;
		}

		return numTemp;
	}

	// /////////////////////////计算无线网覆盖率情况////////////////////

	/**
	 * 无线网覆盖占有率,fig=1为基本全覆盖，fig=2为基本无覆盖
	 * 
	 * @throws Exception
	 */
	public float getWireless(int fig, String areaName, String currentTime)
			throws Exception {
		float numTemp = 0;
		String schoolIds = "";
		String sql = "";
		schoolIds = getSchoolIds(getAllSchoolIdNumber(areaName,currentTime));
		// 判断schoolIds是否为空
		if (!schoolIds.isEmpty()) {
			if (fig == 1) {
				sql = "SELECT COUNT(autoId) AS num FROM tblankanswer WHERE userTime LIKE '%"
						+ currentTime
						+ "%' AND queId='125' AND blankText>95 AND schoolId in("
						+ schoolIds + ")";
			} else {
				sql = "SELECT COUNT(autoId) AS num FROM tblankanswer WHERE userTime LIKE '%"
						+ currentTime
						+ "%' AND queId='125' AND blankText<10 AND schoolId in("
						+ schoolIds + ")";
			}
			Statement state = con.createStatement();
			ResultSet rs = state.executeQuery(sql);
			while (rs.next()) {
				numTemp = rs.getInt("num");
			}
			state.close();
		} else {
			numTemp = 0;
		}

		return numTemp;
	}

	// ///////////////教师信息化终端////////////
	/**
	 * 计算每个学校教师终端数
	 */
//	public float getTerminalNum(int schoolId, String currentTime)
//			throws Exception {
//		int terminalNum = 0;
//		String sql = "SELECT SUM(blankText) AS terminalNum FROM tblankanswer WHERE userTime LIKE '"
//				+ currentTime + "%' AND schoolId=" + schoolId+" AND queId in (135,136,137)";
//		Statement state = con.createStatement();
//		ResultSet rs = state.executeQuery(sql);
//		while (rs.next()) {
//			terminalNum = rs.getInt("terminalNum");
//		}
//
//		state.close();
//		return terminalNum;
//	}
	
	public HashMap<Integer, Integer> getTerminalNum(String areaName,String currentTime)
			throws Exception {
		int schoolId=0;
		int terminalNum = 0;
		String schoolIds = "";
		schoolIds = getSchoolIds(getAllSchoolIdNumber(areaName,currentTime));
		String sql = "SELECT schoolId,SUM(blankText) AS terminalNum FROM tblankanswer WHERE userTime LIKE '"
				+ currentTime + "%' AND schoolId IN (" + schoolIds+") AND queId in (135,136,137) GROUP BY schoolId";
		Statement state = con.createStatement();
		ResultSet rs = state.executeQuery(sql);
		HashMap schoolTerminalNUm=new HashMap<Integer,Integer>();
		while (rs.next()) {
			schoolId=rs.getInt("schoolId");
			terminalNum = rs.getInt("terminalNum");
			schoolTerminalNUm.put(schoolId, terminalNum);
		}

		state.close();
		return schoolTerminalNUm;
	}


	// ///////////计算学校应用的数字化教学系统的使用情况//////////////////
	/**
	 * 计算未建设数组话教学系统、网络教学平台、教学资源管理平台、交互式电子白板教学系统、电子阅览系统 fig=1,2,3,4,5
	 * 
	 */
//	public float getDigitalTeaSystem(int fig, String areaName,
//			String currentTime) throws Exception {
//		float numTemp = 0;
//		String schoolIds = "";
//		String sql = "";
//		schoolIds = getSchoolIds(getAllSchoolIdNumber(areaName,currentTime));
//		// 判断schoolIds是否为空
//		if (!schoolIds.isEmpty()) {
//			if (fig == 1) {
//				sql = "SELECT COUNT(autoId) AS num FROM tchoiceanswer WHERE userTime LIKE '%"
//						+ currentTime
//						+ "%' AND choiceId='230' AND schoolId in("
//						+ schoolIds
//						+ ")";
//			} else if (fig == 2) {
//				sql = "SELECT COUNT(autoId) AS num FROM tchoiceanswer WHERE userTime LIKE '%"
//						+ currentTime
//						+ "%' AND choiceId='231' AND schoolId in("
//						+ schoolIds
//						+ ")";
//			} else if (fig == 3) {
//				sql = "SELECT COUNT(autoId) AS num FROM tchoiceanswer WHERE userTime LIKE '%"
//						+ currentTime
//						+ "%' AND choiceId='232' AND schoolId in("
//						+ schoolIds
//						+ ")";
//			} else if (fig == 4) {
//				sql = "SELECT COUNT(autoId) AS num FROM tchoiceanswer WHERE userTime LIKE '%"
//						+ currentTime
//						+ "%' AND choiceId='233' AND schoolId in("
//						+ schoolIds
//						+ ")";
//			} else {
//				sql = "SELECT COUNT(autoId) AS num FROM tchoiceanswer WHERE userTime LIKE '%"
//						+ currentTime
//						+ "%' AND choiceId='234' AND schoolId in("
//						+ schoolIds
//						+ ")";
//			}
//
//			Statement state = con.createStatement();
//			ResultSet rs = state.executeQuery(sql);
//			while (rs.next()) {
//				numTemp = rs.getInt("num");
//			}
//
//			state.close();
//		} else {
//			numTemp = 0;
//		}
//
//		return numTemp;
//	}

	public HashMap<Integer, Integer> getDigitalTeaSystem(String areaName,
	String currentTime) throws Exception {
    float numTemp = 0;
    String schoolIds = "";
    String sql = "";
    schoolIds = getSchoolIds(getAllSchoolIdNumber(areaName,currentTime));
    HashMap<Integer, Integer> choiceId_schoolNum=new HashMap<Integer,Integer>();
    // 判断schoolIds是否为空
    if (!schoolIds.isEmpty()) {
	sql = "SELECT choiceId,COUNT(*) AS num FROM tchoiceanswer WHERE queId=142 AND "
			+ "userTime LIKE '%"
			+ currentTime
			+ "%' AND schoolId in("
			+ schoolIds
			+ ") group by choiceId";
    int choiceId=0;
    int schoolNum=0;
	Statement state = con.createStatement();
	ResultSet rs = state.executeQuery(sql);
	while (rs.next()) {
		choiceId=rs.getInt("choiceId");
		schoolNum= rs.getInt("num");
		choiceId_schoolNum.put(choiceId,schoolNum);
	}

	state.close();
} else {
	return null;
}

  return choiceId_schoolNum;
}
	
	/**
	 * 开通网络空间比例
	 */
	public HashMap<Integer, Float> getNetWorkSpace(String areaName, String currentTime)
			throws Exception {
		float avgNetSpace = 0;
		String schoolIds = "";
		String sql = "";
		schoolIds = getSchoolIds(getAllSchoolIdNumber(areaName,currentTime));
		HashMap<Integer,Float> netSpacePercent=new HashMap<>();
		int queId=0;
		// 判断schoolIds是否为空
		if (!schoolIds.isEmpty()) {
				sql = "SELECT queId,AVG(blankText) as avgNetWorkSpaceNum FROM tblankanswer WHERE queId in (143,144) AND userTime LIKE '%"
						+ currentTime
						+ "%' AND schoolId in ("
						+ schoolIds
						+ ") group by queId";
			Statement state = con.createStatement();
			ResultSet rs = state.executeQuery(sql);
			while (rs.next()) {
				queId=rs.getInt("queId");
				avgNetSpace= rs.getFloat("avgNetWorkSpaceNum");
				avgNetSpace=Float
						.parseFloat(new java.text.DecimalFormat("#.##")
						.format(avgNetSpace));
				netSpacePercent.put(queId, avgNetSpace);
			}

			state.close();
		} else {
			netSpacePercent=null;
		}

		return netSpacePercent;
	}

	// ////////////教研信息化中数字化教研资源建设情况////////////
	/**
	 * 计算未开展教研信息化建设、未开展教研信息化建设、教研信息资源库、案例教研资源、教案教研资源、课件教研资源fig=1,2,3,4,5,6
	 * 
	 */
	public HashMap<Integer, Integer> getDigitalReaSystem(String areaName,
			String currentTime) throws Exception {
		int numTemp = 0;
		String schoolIds = "";
		String sql = "";
		int choiceId=0;
		schoolIds = getSchoolIds(getAllSchoolIdNumber(areaName,currentTime));
		HashMap<Integer,Integer> schoolPercent=new HashMap<>();
		// 判断schoolIds是否为空
		if (!schoolIds.isEmpty()) {
				sql = "SELECT choiceId,COUNT(autoId) AS num FROM tchoiceanswer WHERE queId=146 AND "
						+ "userTime LIKE '%"
						+ currentTime
						+ "%' AND schoolId in("
						+ schoolIds
						+ ") group by choiceId";
			Statement state = con.createStatement();
			ResultSet rs = state.executeQuery(sql);
			while (rs.next()) {
				choiceId=rs.getInt("choiceId");
				numTemp = rs.getInt("num");
				schoolPercent.put(choiceId, numTemp);
			}
			state.close();
			
		} else {
			return null;
		}
		return schoolPercent;
	}
	
	public float getDigitalReaSystem(int fig, String areaName,
			String currentTime) throws Exception {
		float numTemp = 0;
		String schoolIds = "";
		String sql = "";
		schoolIds = getSchoolIds(getAllSchoolIdNumber(areaName,currentTime));
		// 判断schoolIds是否为空
		if (!schoolIds.isEmpty()) {
			if (fig == 1) {
				sql = "SELECT COUNT(autoId) AS num FROM tchoiceanswer WHERE userTime LIKE '%"
						+ currentTime
						+ "%' AND choiceId='236' AND schoolId in("
						+ schoolIds
						+ ")";
			} else if (fig == 2) {
				sql = "SELECT COUNT(autoId) AS num FROM tchoiceanswer WHERE userTime LIKE '%"
						+ currentTime
						+ "%' AND choiceId='237' AND schoolId in("
						+ schoolIds
						+ ")";
			} else if (fig == 3) {
				sql = "SELECT COUNT(autoId) AS num FROM tchoiceanswer WHERE userTime LIKE '%"
						+ currentTime
						+ "%' AND choiceId='238' AND schoolId in("
						+ schoolIds
						+ ")";
			} else if (fig == 4) {
				sql = "SELECT COUNT(autoId) AS num FROM tchoiceanswer WHERE userTime LIKE '%"
						+ currentTime
						+ "%' AND choiceId='239' AND schoolId in("
						+ schoolIds
						+ ")";
			} else if (fig == 5) {
				sql = "SELECT COUNT(autoId) AS num FROM tchoiceanswer WHERE userTime LIKE '%"
						+ currentTime
						+ "%' AND choiceId='240' AND schoolId in("
						+ schoolIds
						+ ")";
			} else {
				sql = "SELECT COUNT(autoId) AS num FROM tchoiceanswer WHERE userTime LIKE '%"
						+ currentTime
						+ "%' AND choiceId='241' AND schoolId in("
						+ schoolIds
						+ ")";
			}

			Statement state = con.createStatement();
			ResultSet rs = state.executeQuery(sql);
			while (rs.next()) {
				numTemp = rs.getInt("num");
			}
			state.close();
			
		} else {
			numTemp = 0;
		}

		return numTemp;
	}

	// //////////管理与服务信息化/////////////////
	/**
	 * 统计网站建设、管理系统、微信平台、邮箱平台开通率 fig=1,2,3,4
	 */
	public float getManagerSystem(int fig, String areaName, String currentTime)
			throws Exception {
		float numTemp = 0;
		String schoolIds = "";
		String sql = "";
		schoolIds = getSchoolIds(getAllSchoolIdNumber(areaName,currentTime));
		// 判断schoolIds是否为空
		if (!schoolIds.isEmpty()) {
			if (fig == 1) {
				sql = "SELECT COUNT(autoId) AS num FROM tchoiceanswer WHERE userTime LIKE '%"
						+ currentTime
						+ "%' AND choiceId='277' AND schoolId in("
						+ schoolIds
						+ ")";
			} else if (fig == 2) {
				sql = "SELECT COUNT(autoId) AS num FROM tchoiceanswer WHERE userTime LIKE '%"
						+ currentTime
						+ "%' AND choiceId='249' AND schoolId in("
						+ schoolIds
						+ ")";
			} else if (fig == 3) {
				sql = "SELECT COUNT(autoId) AS num FROM tchoiceanswer WHERE userTime LIKE '%"
						+ currentTime
						+ "%' AND choiceId='272' AND schoolId in("
						+ schoolIds
						+ ")";
			} else if (fig == 4) {
				sql = "SELECT COUNT(autoId) AS num FROM tchoiceanswer WHERE userTime LIKE '%"
						+ currentTime
						+ "%' AND choiceId='273' AND schoolId in("
						+ schoolIds
						+ ")";
			}

			Statement state = con.createStatement();
			ResultSet rs = state.executeQuery(sql);
			while (rs.next()) {
				numTemp = rs.getInt("num");
			}

			state.close();
		} else {
			numTemp = 0;
		}
		
		return numTemp;
	}
    
	public HashMap<Integer, Integer> getManagerSystem(String areaName, String currentTime)
			throws Exception {
		int numTemp = 0;
		String schoolIds = "";
		String sql = "";
		int choiceId=0;
		schoolIds = getSchoolIds(getAllSchoolIdNumber(areaName,currentTime));
		HashMap<Integer,Integer> schoolPercent=new HashMap<>();
		// 判断schoolIds是否为空
		if (!schoolIds.isEmpty()) {
				sql = "SELECT choiceId,COUNT(autoId) AS num FROM tchoiceanswer "
						+ "WHERE choiceId in (277,249,272,273) AND "
						+ "userTime LIKE '%"
						+ currentTime
						+ "%' AND schoolId in("
						+ schoolIds
						+ ") group by choiceId";
			Statement state = con.createStatement();
			ResultSet rs = state.executeQuery(sql);
			while (rs.next()) {
				choiceId=rs.getInt("choiceId");
				numTemp = rs.getInt("num");
				schoolPercent.put(choiceId, numTemp);
			}
			state.close();
			
		} else {
			return null;
		}
		return schoolPercent;
	}
	// ///////信息化保障////////
	/**
	 * 教育总经费 教育信息化经费 网络建设与设备购置经费 资源与平台开发经费 培训经费 fig=1,2,3,4,5,
	 */
	public float getFunds(int fig, String areaName, String currentTime)
			throws Exception {
		float numTemp = 0;
		String schoolIds = "";
		String sql = "";
		schoolIds = getSchoolIds(getAllSchoolIdNumber(areaName,currentTime));
		// 判断schoolIds是否为空
		if (!schoolIds.isEmpty()) {
			if (fig == 1) {
				sql = "SELECT AVG(blankText) as num FROM tblankanswer WHERE queId='168' AND userTime LIKE '%"
						+ currentTime
						+ "%' AND schoolId in ("
						+ schoolIds
						+ ")";
			} else if (fig == 2) {
				sql = "SELECT AVG(blankText) as num FROM tblankanswer WHERE queId='169' AND userTime LIKE '%"
						+ currentTime
						+ "%' AND schoolId in ("
						+ schoolIds
						+ ")";
			} else if (fig == 3) {
				sql = "SELECT AVG(blankText) as num FROM tblankanswer WHERE queId='170' AND userTime LIKE '%"
						+ currentTime
						+ "%' AND schoolId in ("
						+ schoolIds
						+ ")";
			} else if (fig == 4) {
				sql = "SELECT AVG(blankText) as num FROM tblankanswer WHERE queId='171' AND userTime LIKE '%"
						+ currentTime
						+ "%' AND schoolId in ("
						+ schoolIds
						+ ")";
			} else if (fig == 5) {
				sql = "SELECT AVG(blankText) as num FROM tblankanswer WHERE queId='172' AND userTime LIKE '%"
						+ currentTime
						+ "%' AND schoolId in ("
						+ schoolIds
						+ ")";
			}

			Statement state = con.createStatement();
			ResultSet rs = state.executeQuery(sql);
			while (rs.next()) {
				numTemp = rs.getInt("num");
			}

			state.close();

		} else {
			numTemp = 0;
		}

		return numTemp;
	}

	
	public HashMap<Integer, Float> getFunds(String areaName, String currentTime)
			throws Exception {
		float fee = 0;
		String schoolIds = "";
		String sql = "";
		int queId=0;
		schoolIds = getSchoolIds(getAllSchoolIdNumber(areaName,currentTime));
		HashMap<Integer,Float> schoolPercent=new HashMap<>();
		DecimalFormat df=new DecimalFormat("0.00");
		// 判断schoolIds是否为空
		if (!schoolIds.isEmpty()) {
				sql = "SELECT queId,AVG(blankText) AS fee FROM tblankanswer "
						+ "WHERE queId BETWEEN 168 AND 172 AND "
						+ "userTime LIKE '%"
						+ currentTime
						+ "%' AND schoolId in("
						+ schoolIds
						+ ") group by queId";
			Statement state = con.createStatement();
			ResultSet rs = state.executeQuery(sql);
			while (rs.next()) {
				queId=rs.getInt("queId");
				fee= rs.getFloat("fee");
				fee=Float.parseFloat(df.format(fee));
				schoolPercent.put(queId, fee);
			}
			state.close();
			
		} else {
			return null;
		}
		return schoolPercent;
	}

	/**
	 * 参与信息技术培训的教师数量 人均参加多少课时的信息技术培训 fig=1,2
	 */
	public float getTrain(int fig, String areaName, String currentTime)
			throws Exception {
		float numTemp = 0;
		String schoolIds = "";
		String sql = "";
		schoolIds = getSchoolIds(getAllSchoolIdNumber(areaName,currentTime));
		// 判断schoolIds是否为空
		if (!schoolIds.isEmpty()) {
			if (fig == 1) {
				sql = "SELECT AVG(blankText) as num FROM tblankanswer WHERE queId='183' AND userTime LIKE '%"
						+ currentTime
						+ "%' AND schoolId in ("
						+ schoolIds
						+ ")";
			} else if (fig == 2) {
				sql = "SELECT AVG(blankText) as num FROM tblankanswer WHERE queId='184' AND userTime LIKE '%"
						+ currentTime
						+ "%' AND schoolId in ("
						+ schoolIds
						+ ")";
			}
			Statement state = con.createStatement();
			ResultSet rs = state.executeQuery(sql);
			while (rs.next()) {
				numTemp = rs.getInt("num");
			}

			state.close();
		} else {
			numTemp = 0;
		}

		return numTemp;
	}

}
