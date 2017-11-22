package nercel.javaweb.qxstatistics;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

public class QxStatisticsServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		// 获取session
		HttpSession session = request.getSession(false);
		String qxusername = (String) session.getAttribute("username");
		DbQxStatistics dbstistics = new DbQxStatistics();
		String type = request.getParameter("operation");
		String schoolName = request.getParameter("schoolName");
		//获取当前时间
		/*Date date = new Date();
		SimpleDateFormat Dateformat = new SimpleDateFormat("yyyy-MM");
		String currentTime = Dateformat.format(date).toString();*/
		String currentTime = request.getParameter("currentdate");
		//String currentTime = "2017-02";
		// 打开数据库连接
		try {
			dbstistics.openConnection();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			dbstistics.getAreanId(qxusername);
			//System.out.println(dbstistics.getAreanId(qxusername)+"aaaa");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			dbstistics.getAreanName(qxusername);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (type.equals("wholestatistics")) {
			ArrayList query = null;
			try {
				query = dbstistics.getWholeStatistics(currentTime);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JSONArray jsonArray = new JSONArray();
			String json = jsonArray.fromObject(query).toString();
			out.print(json);
		}

		if (type.equals("getschoolname")) {
			ArrayList query = null;
			try {
				query = dbstistics.searchSchool("",currentTime);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JSONArray jsonArray = new JSONArray();
			String json = jsonArray.fromObject(query).toString();
			out.print(json);
		}
		
		if(type.equals("searchschool")){
			ArrayList query = null;
			try {
				query = dbstistics.searchSchool(schoolName, currentTime);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JSONArray jsonArray = new JSONArray();
			String json = jsonArray.fromObject(query).toString();
			out.print(json);
		}
		// 关闭数据库连接
		try {
			dbstistics.closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}