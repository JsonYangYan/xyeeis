package nercel.javaweb.statistics;

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

import net.sf.json.JSONArray;

/**
 * @Description: 根据文件名字读取对应文件
 * @author: Stone
 * @version: 0.1,上午8:48:50
 * @param:
 */

public class StatisticsTypeServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		// 获取当前时间
		/*Date date = new Date();
		SimpleDateFormat Dateformat = new SimpleDateFormat("yyyy-MM");
		String currentTime = Dateformat.format(date).toString();*/
		String currentTime = request.getParameter("currentdate");
		//String currentTime = "2017-02";
		Statistics stistics = new Statistics();
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