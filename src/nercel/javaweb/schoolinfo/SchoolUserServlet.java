package nercel.javaweb.schoolinfo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;



import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nercel.javaweb.common.ConvertSessionName;
import nercel.javaweb.common.FormatCheckUtils;
import net.sf.json.JSONArray;
import net.sf.json.processors.JsonBeanProcessor;

public class SchoolUserServlet extends HttpServlet {

	public SchoolUserServlet() {
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
		String school_type = request.getParameter("type");//学校类型
		if (school_type == null) {
			school_type = "mormal";
		}
		//从session中获取用户的名字
		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("name");
		
		GetSomeSchoolData schoolData = new GetSomeSchoolData();
		/*
		 * 左上角显示上个月份填写的一部分数据
		 * */
		if (type.equals("somedata")) {
				HashMap data = null;
				try {
					try {
						data = schoolData.getSomeData(userName,school_type);
						
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
				JSONArray jsonArray = new JSONArray();
				jsonArray = JSONArray.fromObject(data);
				out.print(jsonArray);
		}else if (type.equals("getUserName")) {
			out.print(userName);
		}else if (type.equals("checkBefPwd")) {
			//获取用户输入原始的密码
			String pwd = request.getParameter("val");
			String resString = null;
			try {
				resString = schoolData.checkUserPwd(userName, pwd);
			} catch (ClassNotFoundException e) {
			
				e.printStackTrace();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			out.print(resString);
		}else if (type.equals("updatepwd")) {
			String pwd = request.getParameter("pwd");
			String result = null;
			try {
				result = schoolData.updpwd(pwd, userName);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			out.print(result);
		}
		
		out.close();
		
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	
	public void init() throws ServletException {
		
	}

}
