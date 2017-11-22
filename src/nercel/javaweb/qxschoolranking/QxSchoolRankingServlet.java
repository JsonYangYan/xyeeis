package nercel.javaweb.qxschoolranking;

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
import javax.servlet.http.HttpSession;

import nercel.javaweb.schoolranking.SchoolRankingDbUtil;
import net.sf.json.JSONArray;

public class QxSchoolRankingServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		QxSchoolRankingDbUtil schoolranking = new QxSchoolRankingDbUtil();
		String type = request.getParameter("operation");
		// 获取session
		HttpSession session = request.getSession(false);
		String qxusername = (String) session.getAttribute("username");
		
		// 获取当前时间
		//Date date = new Date();
		SimpleDateFormat Dateformat = new SimpleDateFormat("yyyy-MM");
		//String currentTime = Dateformat.format(date).toString();
		String currentTime = request.getParameter("currentdate");
		Calendar calendar = Calendar.getInstance();
		Date date = new Date();
		try {
			date = Dateformat.parse(currentTime);
		} catch (ParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		calendar.setTime(date);
        calendar.set(Calendar.DATE,-1); // 设置为上一个月最后一天
        String lastTime = Dateformat.format(calendar.getTime()).toString();

		String order = request.getParameter("order");
		//System.out.println(order);
		// 打开数据库连接
		try {
			schoolranking.openConnection();
		} catch (ClassNotFoundException e1) {

			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String areaName = "";
		try {
			areaName = schoolranking.getArean(qxusername);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (type.equals("school_ranking")) {
			ArrayList query = null;
			try {
				query = schoolranking.getSchoolRank(areaName,order,currentTime,lastTime);
				// System.out.println(query);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JSONArray jsonArray = new JSONArray();
			String json = JSONArray.fromObject(query).toString();
			out.print(json);
		}

		/*if (type.equals("school_middleranking")) {
			ArrayList query = null;
			try {
				query = schoolranking.getMiddleSchoolRank(areaName);
				// System.out.println(query);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JSONArray jsonArray = new JSONArray();
			String json = JSONArray.fromObject(query).toString();
			// JSONObject object = JSONObject.fromObject(json);
			out.print(json);
		}

		if (type.equals("school_lastranking")) {
			ArrayList query = null;
			try {
				query = schoolranking.getLastSchoolRank(areaName);
				// System.out.println(query);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JSONArray jsonArray = new JSONArray();
			String json = JSONArray.fromObject(query).toString();
			// JSONObject object = JSONObject.fromObject(json);
			out.print(json);
		}
*/
		try {
			schoolranking.closeConnection();
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
