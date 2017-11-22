package nercel.javaweb.currentdata;

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

import nercel.javaweb.allassessment.AssessmentDbUtil;
import nercel.javaweb.allassessment.CityScore;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class BlankAvgServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		
		 // 获取当前时间
 		/*Date date = new Date();
 		SimpleDateFormat Dateformat = new SimpleDateFormat("yyyy-MM");
 		String currentTime = Dateformat.format(date).toString();*/
		String currentTime = request.getParameter("currentdate");
		//String currentTime = "2017-02";
		PrintWriter out = response.getWriter();
		
		CityScore cityScore = new CityScore();
		AssessmentDbUtil assessmentDbUtil = new AssessmentDbUtil();
		try {
			assessmentDbUtil.openConnection();
		} catch (Exception e1) {
			e1.printStackTrace();
		} 
		
		try {
			ArrayList list = new ArrayList();
			list = cityScore.getavgThird(currentTime);
			out.print(JSONArray.fromObject(list));
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		try {
			assessmentDbUtil.closeConnection();
		} catch (Exception e1) {
			e1.printStackTrace();
		} 
		
		
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}
