package nercel.javaweb.allassessment;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ThirdIndexScoreServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		
		 // 获取当前时间
 		Date date = new Date();
 		SimpleDateFormat Dateformat = new SimpleDateFormat("yyyy-MM-dd");
 		String time = Dateformat.format(date).toString();
		String currentTime = request.getParameter("currentdate");
		//String currentTime = "2017-02";
		String id = request.getParameter("id");
		int fig = Integer.parseInt(id) -1;
		
		PrintWriter out = response.getWriter();
		
		CityScore cityScore = new CityScore();
		
		AssessmentDbUtil assessmentDbUtil = new AssessmentDbUtil();
		try {
			assessmentDbUtil.openConnection();
		} catch (Exception e1) {
			e1.printStackTrace();
		} 
		ArrayList<String> listArea = null;
		try {
			listArea = assessmentDbUtil.getAreaName();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
		/*try {
			HashMap map = new HashMap();
			map = (HashMap) cityScore.avgThird(0, currentTime);
			out.print(JSONObject.fromObject(map));
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}*/
		try {
			assessmentDbUtil.closeConnection();
		} catch (Exception e1) {
			e1.printStackTrace();
		} 
		

		float temp[] = null;
		
		HashMap hashMap = new HashMap();
	    JSONArray jsonArray = new JSONArray();
	    ArrayList arrayList = new ArrayList();
	    
	    jsonArray = JSONArray.fromObject(listArea);
	    hashMap.put("area", jsonArray);
	
		for(int i=0; i<listArea.size(); i++) {
				try {
					temp = cityScore.getThirdAreanScore(listArea.get(i), currentTime);
				} catch (Exception e) {
					e.printStackTrace();
				} 
		    arrayList.add(temp[fig]);
			
		}
		hashMap.put("rule", arrayList);
		out.print(JSONObject.fromObject(hashMap));
		
		out.flush();
		out.close();
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}
}
