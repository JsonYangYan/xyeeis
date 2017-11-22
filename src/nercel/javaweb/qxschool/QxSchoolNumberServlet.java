package nercel.javaweb.qxschool;

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

public class QxSchoolNumberServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		String type = request.getParameter("operation");
		String schoolName = request.getParameter("schoolName");
		// 获取session
		HttpSession session = request.getSession(false);
		String qxusername = (String) session.getAttribute("username");
		//获取当前时间
		/*Date date = new Date();
		SimpleDateFormat Dateformat = new SimpleDateFormat("yyyy-MM");
		String currentTime = Dateformat.format(date).toString();*/
		String currentTime = request.getParameter("currentdate");
		
	    QxSchoolDbUtil schoolDbUtil = new QxSchoolDbUtil();
	    //打开数据库
	    try {
			schoolDbUtil.openConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    String areaName = "";	//得到区县用户所在地区名称
		try {
			areaName = schoolDbUtil.getArean(qxusername);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (type.equals("querySchool")){
			ArrayList querySchool = new ArrayList();
			try {
				querySchool = schoolDbUtil.querySchool(schoolName,areaName,currentTime);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out.print(JSONArray.fromObject(querySchool));
		}
		
		if (type.equals("getSchool")){
			ArrayList everySchool = new ArrayList();
			try {
				everySchool = schoolDbUtil.getEverySchool(areaName,currentTime);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out.print(JSONArray.fromObject(everySchool));
		}
	    
	    try {
			schoolDbUtil.closeConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			out.flush();
			out.close();
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}
}