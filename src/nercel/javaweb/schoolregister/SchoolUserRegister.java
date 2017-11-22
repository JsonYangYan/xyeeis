package nercel.javaweb.schoolregister;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.mail.Flags.Flag;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

public class SchoolUserRegister extends HttpServlet {

	private static final long serialVersionUID = 1L;


	public SchoolUserRegister() {
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
		String type =request.getParameter("operation");
		//查看用户名是否被注册
		RegisterService rService = new RegisterService();
		if(type.equals("checkUserName")) {
			String userName = request.getParameter("userName");
			String flag = null;
			try {
				flag = rService.checkUserName(userName);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			out.print(flag);
		}
		//查看该学校是否已经注册
		if(type.equals("checkIsReg")) {
			int area_id = Integer.parseInt(request.getParameter("area_id"));
			String schoolName = request.getParameter("schoolName");
			String flag = null;
			try {
				flag = rService.checkIsReg(area_id, schoolName);
			} catch (ClassNotFoundException | SQLException e) {
		
				e.printStackTrace();
			}
			out.print(flag);
			
		}
		//注册
		if (type.equals("register")) {
			int area_id = Integer.parseInt(request.getParameter("area_id"));
			String schoolName = request.getParameter("schoolName");
			String userName = request.getParameter("userName");
			String pwd = request.getParameter("pwd");
			String flag = null;
			try {
				flag = rService.register(area_id, schoolName, userName, pwd);
			} catch (ClassNotFoundException | SQLException e) {
				
				e.printStackTrace();
			}
			out.print(flag);
		}
		if(type.equals("getSchoolName")) {
			int area_id = Integer.parseInt(request.getParameter("area_id"));
			ArrayList list = new ArrayList();
			try {
				list = rService.getSchoolName(area_id);
			} catch (ClassNotFoundException | SQLException e) {
				
				e.printStackTrace();
			}
			out.print(JSONArray.fromObject(list));
		}
		
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	
	public void init() throws ServletException {
	}

}
