package nercel.javaweb.json;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class PaperResults {
	
	//普通学校
	public String getPaperResults(int fig, String userName, String time,String type) throws Exception {
	
		AnswerDbUtil answerDbUtil = new AnswerDbUtil();
		answerDbUtil.openConnection();
		
		//获取当前月份的schoolId
		int schoolId = answerDbUtil.getSchoolIdByMonth(userName, time,type);
		
		
		ArrayList<Object> listFrist = new ArrayList<Object>();
		
		listFrist = answerDbUtil.getQueGroupId(fig,type);   //一级指标 有多少组记录有多少组
		
		JSONArray jsonArray = new JSONArray();
		
		
		for(int i=0; i<listFrist.size(); i++) {
			
			ArrayList arrAnswer = new ArrayList();
			arrAnswer = answerDbUtil.getQueIdByGroupId((Integer)listFrist.get(i),type);  //每组     包含多少个问题
			System.out.println(arrAnswer);
			JSONObject objFirst = new JSONObject();
			objFirst.put("groupId", listFrist.get(i));
			
			JSONArray arrSecond = new JSONArray();
			for(int j=0; j<arrAnswer.size(); j++) {
				
				JSONObject objSecond = new JSONObject();
				//需要改
				objSecond = JSONObject.fromObject(answerDbUtil.getQue((Integer)arrAnswer.get(j), schoolId, time,type));  //传递id给我,计算的是填空题
				//这是选择题在后面加入 blankText
				//需要改
				JSONArray arrThird = new JSONArray();
				arrThird = JSONArray.fromObject(answerDbUtil.getChoice((Integer)arrAnswer.get(j), schoolId, time,type));  //这需要重新写 判断是否被选中，以及加入文本框
				
				objSecond.put("children", arrThird);
				arrSecond.add(objSecond);	
			}
			
			objFirst.put("children", arrSecond);
			jsonArray.add(objFirst);
		}
	   	answerDbUtil.closeConnection();
		return jsonArray.toString();

	}
	
	
	////////////////////////////////////////////////////////////////////////////////////
	////////////        将数据库中最新的一条用户用户记录，复制出来，然后再次插入
	
	/**
	 * 
	 * @param userName 
	 * @param time 本月的时间  格式 yy-mm-dd
	 * @param preSchoolId 上个月学校的id
	 * @throws Exception
	 */
	public void copyAndInsert(String userName, String time, int preSchoolId,String school_type) throws Exception {
		
		AnswerDbUtil awDbUtil = new AnswerDbUtil();
		awDbUtil.openConnection();
		
		String[] strDate = time.split("-");
		String currentTime = strDate[0] + "-" + (strDate[1]);//本月格式yy-mm
		
		//获取数据库中最新的学校id
		int schoolId = awDbUtil.getSchoolIdByMonth(userName, currentTime,school_type);
		//将上个月的选择题复制到本月的临时表中	
		awDbUtil.AddLastMonthChoiceAnswerCurrentTemp(preSchoolId, schoolId, time,school_type);
		//将上个月的填空题复制到本月的临时表中
		awDbUtil.AddLastMonthTextAnswerToCurrentTemp(preSchoolId, schoolId, time,school_type);
		awDbUtil.closeConnection();
		
	}
	
	
		
}
	
