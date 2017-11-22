package nercel.javaweb.welcome;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nercel.javaweb.common.ConvertSessionName;
import nercel.javaweb.common.FormatCheckUtils;
import nercel.javaweb.qxschoolmanager.QxschoolmanagerDbUtil;
import nercel.javaweb.welcome.DBwelcome;
import net.sf.json.JSONArray;

import org.apache.commons.codec.digest.DigestUtils;


public class Welcome extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		DBwelcome Db = new DBwelcome();
		String type = request.getParameter("operation");
		int k = 0;
		
		//从session中获取用户的名字
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("name");
		
		try {
			Db.openConnection();
		} catch (Exception e1) {
			e1.printStackTrace();
		} 
		
		// System.out.println(username);
		if (type.equals("addpaper")) {
			boolean query = false;
			boolean submitschool = false;	//标示学校是否提交
			String schoolArean = request.getParameter("schoolArean");
			String schoolName = request.getParameter("schoolName");
			String schoolType = request.getParameter("school_type");
			
			String strTeacherNumber = request.getParameter("teacherNumber");
			String strStudentNumber = request.getParameter("studentNumber");
			String strClassNumber = request.getParameter("classNumber");
			
			String schoolTown = request.getParameter("schoolTown");
			String personName = request.getParameter("personName");
			String telPhone = request.getParameter("telPhone");
			String eMail = request.getParameter("eMail");
			
			/////////////////////////////////////////////////////////////////////////
			//2016.11.10  by stone
		/*	if((schoolArean.length() == 0) || (schoolName.length() == 0) || (schoolType == null) ||
		    	(strTeacherNumber.length() >= 8) || (schoolTown == null) || (personName.length() == 0) ||
				(strStudentNumber.length() >= 8)  || (telPhone.length() >= 30) || (eMail.length() >=30) || 
				(strClassNumber.length() >= 8)) {
				out.print("no");
				
			} else {*/
				int teacherNumber = Integer.parseInt(strTeacherNumber);
				int studentNumber = Integer.parseInt(strStudentNumber);
				int classNumber = Integer.parseInt(strClassNumber);
				try {
					query = Db.addpaper(schoolArean, schoolName,schoolType,teacherNumber,
							studentNumber, schoolTown, personName, telPhone, eMail,
							classNumber, username);
				//2016.11.08  by stone
				//submitschool = Db.submitSchool(schoolName);
				} catch (Exception e) {
					e.printStackTrace();
				} 
				if (query) {
					out.print("ok");
				}
			/*}*/
		}
		///////////////////////////////////////////////////////////////
		//@by stone  11.09  
		if(type.equals("judge")) {
			
			boolean fig = false;
			try {
				fig = Db.getIsSubPaper(username);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(fig) {
				out.print("ok");
			}
			else {
				out.print("no");
			}
		}
		/////////////////////////////////////////////////////////
		// welcome页面显示已经登录过用户的信息
		if (type.equals("edit_display")) {
			ArrayList query = null;

			try {
				query = Db.editsearchContent(username);

			} catch (Exception e) {
				e.printStackTrace();
			} 
			
			JSONArray jsonArray = new JSONArray();
			String json = jsonArray.fromObject(query).toString();
			out.print(json);
		}

		// 更新welcome页面信息
		if (type.equals("edit")) {
			boolean query = false;

			String schoolArean = request.getParameter("schoolArean");
			String schoolName = request.getParameter("schoolName");
			String schoolType = request.getParameter("schoolType");
			int teacherNumber = Integer.parseInt(request
					.getParameter("teacherNumber"));
			int studentNumber = Integer.parseInt(request
					.getParameter("studentNumber"));
			String schoolTown = request.getParameter("schoolTown");
			String personName = request.getParameter("personName");
			String telPhone = request.getParameter("telPhone");
			String eMail = request.getParameter("eMail");
			int classNumber = Integer.parseInt(request
					.getParameter("classNumber"));
			try {
				query = Db.editContent(schoolArean, schoolName,schoolType,teacherNumber,
						studentNumber, schoolTown, personName, telPhone, eMail,
						classNumber, username);

			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (query) {
				out.print("ok");
			}
		}

		//修改用户密码
		 if( type.equals("updata_password") ){
	        	/*$pass = inject_sql($_REQUEST['pass']);
	        	$password = md5(md5($pass));*/
	        	String password = request.getParameter("password");
	        	String password1 = md5(md5(password));
	        	boolean flag = false;
				try {
					flag = Db.resetpasswd(password1,username);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	if (flag) {	        		        		  
	        		//session.invalidate();
	        		out.print("ok");
	    		} else {
	    			out.print("resetpasswd error");
	    		}
	        	//System.out.print(password);
	        }
		 
		 //用户登录后显示用户相应的区域和所在学校
		 //@stone 这部分主要显示的是用户登陆注册页面 显示出基本的信息
		 if( type.equals("userinfo") ){	        	        	
			 ArrayList query = null;
				try {
					query = Db.getUserinfor(username);

				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				JSONArray jsonArray = new JSONArray();
				String json = jsonArray.fromObject(query).toString();
				out.print(json);
	        }
		 
		try {
			Db.closeConnection();
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

		doGet(request, response);
	}

}
