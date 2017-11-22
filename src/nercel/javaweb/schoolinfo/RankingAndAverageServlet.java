package nercel.javaweb.schoolinfo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.jms.Session;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nercel.javaweb.common.ConvertSessionName;
import nercel.javaweb.common.FormatCheckUtils;
import net.sf.json.JSONArray;

public class RankingAndAverageServlet extends HttpServlet {

	public RankingAndAverageServlet() {
		super();
	}

	public void destroy() {
		super.destroy(); 
		
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String type = request.getParameter("operation");
		
		//从session中获取用户的名字
		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("name");
		
		if(type.equals("shixianrangk")){
			String userTime = request.getParameter("userTime");
			RankService rankService = new RankService();
			HashMap<String, Object> rank = null;
			try {
				rank = rankService.getRanking(userName, userTime);
			} catch (SQLException e) {
				
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			JSONArray jsonArray = new JSONArray();
			jsonArray = JSONArray.fromObject(rank);
			out.print(jsonArray);
		}
		if(type.equals("average")) {
			String userTime = request.getParameter("userTime");
			ArrayList avgList = new ArrayList<>();
			RankService rService = new RankService();
			
			try {
				avgList = rService.avgList(userName, userTime);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			JSONArray jsonArray = new JSONArray();
			jsonArray = JSONArray.fromObject(avgList);
			out.print(jsonArray);
			
		}
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			doGet(request, response);
	}

	
	public void init() throws ServletException {
		
	}

}
