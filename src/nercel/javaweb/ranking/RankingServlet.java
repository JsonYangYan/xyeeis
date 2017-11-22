package nercel.javaweb.ranking;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nercel.javaweb.monthcontrast.MonthContrast;
import nercel.javaweb.statistics.DbStatistics;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class RankingServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		Ranking ranking = new Ranking();
		MonthContrast MC = new MonthContrast();
		String type = request.getParameter("operation");
		// 获取当前时间
		//Date date = new Date();
		SimpleDateFormat Dateformat = new SimpleDateFormat("yyyy-MM");
		//String currentTime = Dateformat.format(date).toString();
		String currentTime = request.getParameter("currentdate");
		/*Calendar calendar = Calendar.getInstance();
		Date date = new Date();
		try {
			date = Dateformat.parse(currentTime);
		} catch (ParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		calendar.setTime(date);
        calendar.set(Calendar.DATE,-1); // 设置为上一个月最后一天
        String lastTime = Dateformat.format(calendar.getTime()).toString();*/
		
		if (type.equals("ranking")) {
			ArrayList list = new ArrayList();
			ArrayList queryCurrent = null;
			//ArrayList queryLast = null;
			try {
				queryCurrent = ranking.getallAreaScore(7,currentTime);
				//queryLast = ranking.getallAreaScore(7,lastTime);
				list.add(queryCurrent);
				//list.add(queryLast);
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			JSONArray jsonArray = new JSONArray();
			jsonArray = JSONArray.fromObject(list);
			//System.out.println(jsonArray);
    		out.print(jsonArray);
		}
		if(type.equals("monthdimensionRanking")){
			ArrayList list = new ArrayList();
			try {
				int fig = Integer.parseInt(request.getParameter("fig").trim());
				list = MC.getAllScore(fig);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out.print(JSONArray.fromObject(list));
			
		}
		if (type.equals("dimensionRanking")) {
			ArrayList query = null;
			try {
				int fig = Integer.parseInt(request.getParameter("fig").trim());
				//ranking.getinitAreaScore(currentTime);
				query = ranking.getallAreaScore(fig,currentTime);   //被选择的学校分数
				//System.out.println(query);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			JSONArray jsonArray = new JSONArray();
			jsonArray = JSONArray.fromObject(query);
			//System.out.println(jsonArray);
    		out.print(jsonArray);
		}
		
		if (type.equals("getdetail")) {
			ArrayList query = null;
			try {
				query = ranking.getDetail(currentTime);   
				//System.out.println(query);
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
		out.flush();
		out.close();
		
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}
