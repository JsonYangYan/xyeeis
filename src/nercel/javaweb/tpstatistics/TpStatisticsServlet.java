package nercel.javaweb.tpstatistics;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nercel.javaweb.statistics.DbStatistics;
import net.sf.json.JSONArray;

public class TpStatisticsServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		// 获取当前时间
		String currentTime = request.getParameter("currentdate");
		TpStatistics stistics = new TpStatistics();
		String type = request.getParameter("operation");

		if (type.equals("wholestatistics")) {
			ArrayList query = null;
			try {
				query = stistics.getWholeStatistics(currentTime);
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
		
		if (type.equals("classifystatistics")) {
			ArrayList query = null;
			try {
				query = stistics.classifyStatistics(currentTime);
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
			int id = Integer.parseInt(request.getParameter("id"));
			try {
				query = stistics.getAreanSchool(id,currentTime);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			JSONArray jsonArray = new JSONArray();
    		String json = jsonArray.fromObject(query).toString();
    		out.print(json);
		}
		
		out.flush();
		out.close();
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
