package nercel.javaweb.qxallassessment;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

public class QxOverallCompareServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		// 获取session
		HttpSession session = request.getSession(false);
		String qxusername = (String) session.getAttribute("username");
		PrintWriter out = response.getWriter();
		String type = request.getParameter("type");
		QxCityScore cityScore = new QxCityScore();
		QxAssessmentDbUtil Qxadu = new QxAssessmentDbUtil();
		
		// 获取当前时间
 		/*Date date = new Date();
 		SimpleDateFormat Dateformat = new SimpleDateFormat("yyyy-MM");
 		String currentTime = Dateformat.format(date).toString();*/
		String currentTime = request.getParameter("currentdate");
		//String currentTime = "2017-04";
		try {
			Qxadu.openConnection();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String areaName = "";	//地区名
		try {
			areaName = Qxadu.getArean(qxusername);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(type.equals("firstPartPie")) {
			ArrayList arrayList = new ArrayList();
			try {
				arrayList = cityScore.getFirstIndexScore(areaName,currentTime);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out.print(JSONArray.fromObject(arrayList));
		}
		
		if(type.equals("firstType")) {
			ArrayList arrayList = new ArrayList();
			try {
				arrayList = cityScore.getFirstAreanScoreJson(areaName,currentTime);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out.print(JSONArray.fromObject(arrayList));
		}
		
		if(type.equals("secondType")) {
			ArrayList arrayList = new ArrayList();
			try {
				arrayList = cityScore.getSecondAreanScoreJson(areaName,currentTime);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out.print(JSONArray.fromObject(arrayList));
		}
		
		if(type.equals("thirdtType")) {
			ArrayList arrayList = new ArrayList();
			try {
				arrayList = cityScore.getThirdAreanScoreJson(areaName,currentTime);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out.print(JSONArray.fromObject(arrayList));
		}
		
		try {
			Qxadu.closeConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}
}
