package nercel.javaweb.qxranking;

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

import nercel.javaweb.ranking.RankingDbUtil;
import net.sf.json.JSONArray;

public class QxRankingServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		// 获取session
		HttpSession session = request.getSession(false);
		String qxusername = (String) session.getAttribute("username");
		QxRankingDbUtil dbranking = new QxRankingDbUtil();
		QxRanking qxranking = new QxRanking();
		String type = request.getParameter("operation");
		//获取当前时间
		/*Date date = new Date();
		SimpleDateFormat Dateformat = new SimpleDateFormat("yyyy-MM");
		String currentTime = Dateformat.format(date).toString();*/
		String currentTime = request.getParameter("currentdate");
		
		//打开数据库连接
		try {
			dbranking.openConnection();
		} catch (ClassNotFoundException e1) {
			
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String areaName = "";
		
		try {
			areaName = dbranking.getArean(qxusername);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (type.equals("monthranking")) {
			ArrayList query = null;
			try {
				String tempTime = request.getParameter("date");
				query = qxranking.getScore(areaName,7,tempTime);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			JSONArray jsonArray = new JSONArray();
			jsonArray = JSONArray.fromObject(query);
    		out.print(jsonArray);
		}
		
		if (type.equals("toptenranking")) {
			ArrayList query = null;
			try {
				query = qxranking.getScore(areaName,7,currentTime);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			JSONArray jsonArray = new JSONArray();
			jsonArray = JSONArray.fromObject(query);
    		out.print(jsonArray);
		}
		
		if (type.equals("ranking")) {
			ArrayList query = null;
			try {
				int fig = Integer.parseInt(request.getParameter("fig").trim());
				query = qxranking.getScore(areaName,fig,currentTime);
				
			} catch (ClassNotFoundException e) {
				
				e.printStackTrace();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}			
			JSONArray jsonArray = new JSONArray();
			jsonArray = JSONArray.fromObject(query);
    		out.print(jsonArray);
		}
		
		if (type.equals("getdetail")) {
			ArrayList query = null;
			try {
				query = qxranking.getDetail(areaName,currentTime);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}			
			JSONArray jsonArray = new JSONArray();
			jsonArray = JSONArray.fromObject(query);
    		out.print(jsonArray);
		}
		
		try {
			dbranking.closeConnection();
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