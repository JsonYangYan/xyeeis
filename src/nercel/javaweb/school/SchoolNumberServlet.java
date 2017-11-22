package nercel.javaweb.school;

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

public class SchoolNumberServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		//获取区域名和学校类型
	    String str = request.getParameter("str");
	    String[] strAreanAndSchType = str.split(",");
	   
	    // 获取当前时间
	    /*Date date = new Date();
		SimpleDateFormat Dateformat = new SimpleDateFormat("yyyy-MM");
		String currentTime = Dateformat.format(date).toString();*/
	    String currentTime = request.getParameter("currentdate");
	    
	    
	    SchoolDbUtil schoolDbUtil = new SchoolDbUtil();
	    try {
			schoolDbUtil.openConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    ArrayList everySchool = new ArrayList();
		try {
			everySchool = schoolDbUtil.getEverySchool(strAreanAndSchType[0], strAreanAndSchType[1], currentTime);
			/*if(strAreanAndSchType[0].equals("襄阳市直")){
				everySchool = schoolDbUtil.getEverySchool("襄阳市", strAreanAndSchType[1], currentTime);	
			}else{
				everySchool = schoolDbUtil.getEverySchool(strAreanAndSchType[0], strAreanAndSchType[1], currentTime);   //那个区，那个学校
			}*/
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    try {
			schoolDbUtil.closeConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    	

		out.print(JSONArray.fromObject(everySchool));
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}
