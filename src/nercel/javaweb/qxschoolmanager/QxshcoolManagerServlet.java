package nercel.javaweb.qxschoolmanager;

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

import org.apache.commons.codec.digest.DigestUtils;

import net.sf.json.JSONArray;

public class QxshcoolManagerServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		// 获取session
		HttpSession session = request.getSession(false);
		String qxusername = (String) session.getAttribute("username");
		
		int id = Integer.parseInt(request.getParameter("id"));
		String type = request.getParameter("operation");
		//打开连接
		try {
			QxschoolmanagerDbUtil.openConnection();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		QxschoolmanagerDbUtil QxschManager = new QxschoolmanagerDbUtil();
		try {
			QxschManager.getAreanId(qxusername);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(type.equals("query")){
			String startNum = request.getParameter("startNum");
			String offset = request.getParameter("offset");
			String search = request.getParameter("search");
			ArrayList query = null;
			try {
				query = QxschManager.getAllAndSearchSchool(startNum,offset,search);
			} catch (Exception e) {
				e.printStackTrace();
			}
			JSONArray jsonArray = new JSONArray();
			String json = jsonArray.fromObject(query).toString();
			out.print(json);
		}else if(type.equals("delete")){
			try {
				QxschManager.deleteSchool(id);
				QxschManager.deleteSchUser(id);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(type.equals("resetPassWord")){
			String password = md5(md5("123456"));
			try {
				QxschManager.resetPasswd(id, password);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//关闭连接
		try {
			QxschoolmanagerDbUtil.closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}

	}
	
	// 使用MD5加密密码
	public static String md5(String message) {

		return DigestUtils.md5Hex(message);
	}
		
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}
