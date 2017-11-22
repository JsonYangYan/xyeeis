package nercel.javaweb.schooldetail;

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

import nercel.javaweb.ranking.RankingDbUtil;
import net.sf.json.JSONArray;

public class SchooldetailServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		SchooldetailDbUtil schooldetail = new SchooldetailDbUtil();
		String type = request.getParameter("operation");
		String schoolName = request.getParameter("schoolName");
		
		// 获取当前时间
		/*Date date = new Date();
		SimpleDateFormat Dateformat = new SimpleDateFormat("yyyy-MM");
		String currentTime = Dateformat.format(date).toString();*/
		String currentTime = request.getParameter("currentdate");
		//String currentTime = "2017-02";
			    
		
		//打开数据库连接
		try {
			schooldetail.openConnection();
		} catch (ClassNotFoundException e1) {
			
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (type.equals("schooldetail")) {
			ArrayList query = null;
			try {
				query = schooldetail.getDetail(schoolName, currentTime);
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
			schooldetail.closeConnection();
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
