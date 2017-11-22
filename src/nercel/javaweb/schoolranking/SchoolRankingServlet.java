package nercel.javaweb.schoolranking;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nercel.javaweb.ranking.RankingDbUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SchoolRankingServlet extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		SchoolRankingDbUtil schoolranking = new SchoolRankingDbUtil();
		String type = request.getParameter("operation");

		// 获取当前时间
		/*Date date = new Date();
		SimpleDateFormat Dateformat = new SimpleDateFormat("yyyy-MM");
		String currentTime = Dateformat.format(date).toString();*/
		String currentTime = request.getParameter("currentdate");
		
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

		// 提交的学校总数
		int schoolNum = 0;
		try {
			schoolNum = schoolranking.getSchoolNumber(currentTime);
			//System.out.println(schoolNum);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (schoolNum != 0) {
			if (type.equals("school_topranking")) {
				ArrayList query = null;
				try {
					query = schoolranking.getTopSchoolRank(currentTime,schoolNum);
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

			if (type.equals("school_middleranking")) {
				ArrayList query = null;
				try {
					query = schoolranking.getMiddleSchoolRank(currentTime,schoolNum);
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
					query = schoolranking.getLastSchoolRank(currentTime,schoolNum);
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
		} else {
			if (type.equals("school_lastranking")
					|| type.equals("school_topranking")
					|| type.equals("school_middleranking")) {
				ArrayList list = new ArrayList();
				HashMap map = new HashMap();
				map.put("area_id", "");
				map.put("area_name", "");
				map.put("school_name", "");
				map.put("value", "");
				map.put("schoolNum", schoolNum);
				list.add(map);
				JSONArray jsonArray = new JSONArray();
				String json = JSONArray.fromObject(list).toString();
				// JSONObject object = JSONObject.fromObject(json);
				out.print(json);
			}
		}

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
