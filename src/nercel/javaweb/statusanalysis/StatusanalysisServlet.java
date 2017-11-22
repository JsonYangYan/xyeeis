package nercel.javaweb.statusanalysis;

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

import net.sf.json.JSONArray;

public class StatusanalysisServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String type = request.getParameter("operation");

	    // 获取当前时间
		/*Date date = new Date();
		SimpleDateFormat Dateformat = new SimpleDateFormat("yyyy-MM");
		String currentTime = Dateformat.format(date).toString();*/
		String currentTime = request.getParameter("currentdate");
		Statusanalysis ss = new Statusanalysis();
		//打开数据库连接
		try {
			ss.openConnection();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		
		////////////////////////////////////
		if(type.equals("allasessment")){
			String name = request.getParameter("name");
			int queId = Integer.parseInt(request.getParameter("queId"));
			ArrayList list = null;
			try {
				list = ss.getQuizContent(name, queId, currentTime);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			JSONArray jsonArray = new JSONArray();
    		String json = jsonArray.fromObject(list).toString();
    		out.print(json);
			
		}else if(type.equals("table_data")){//画表格

			ArrayList list = null;
			try {
				list = ss.getEveryAreanScore(currentTime);
			} catch (Exception e) {
				e.printStackTrace();
			}
			JSONArray jsonArray = new JSONArray();
    		String json = jsonArray.fromObject(list).toString();
    		out.print(json);
		}
		out.flush();
		out.close();
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}

}
