package nercel.javaweb.qxcurrentdata;

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

public class QxChoicePectServlet extends HttpServlet {

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
 		// 获取session
		HttpSession session = request.getSession(false);
		String qxusername = (String) session.getAttribute("username");
		
		String areaName = "";
		String id = request.getParameter("id");
		PrintWriter out = response.getWriter();
		AssessmentDbUtil assessmentDbUtil = new AssessmentDbUtil();
		try {
			assessmentDbUtil.openConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		try {
			areaName = assessmentDbUtil.getArean(qxusername);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		  ArrayList arrayList = new ArrayList();
		  try {
			arrayList = assessmentDbUtil.getChoiceValue(Integer.parseInt(id),areaName, currentTime);
		} catch (Exception e1) {
			e1.printStackTrace();
		} 
		try {
			assessmentDbUtil.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		out.print(JSONArray.fromObject(arrayList));
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
