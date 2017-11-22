package nercel.javaweb.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nercel.javaweb.admin.Sql_Admin;
import net.sf.json.JSONArray;

import org.apache.commons.codec.digest.DigestUtils;

public class Admin extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		String type = request.getParameter("operation");
		// System.out.print(type);
		int k = 0;
		String usname = "";
		// 获取session
		HttpSession session = request.getSession(false);
		usname = (String) session.getAttribute("username");

		try {
			Sql_Admin.openConnection();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		if (type.equals("resetSuAdminpasswd")) {
			String password = request.getParameter("pass");
			String password1 = md5(md5(password));
			boolean flag = false;
			try {
				flag = Sql_Admin.resetSuAdminpasswd(password1, usname);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			if (flag) {

				session.invalidate();
				out.print("ok1");
			} else {
				out.print("resetAdminpasswd error");
			}
		} else if (type.equals("deleteAction")) {			//删除学校用户
			String id = request.getParameter("id");
			String schoolName = request.getParameter("schoolName");
			boolean flag = false;
			try {
				flag = Sql_Admin.deleteAction(Integer.parseInt(id));
				 Sql_Admin.delCommitSchUser(schoolName);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (flag) {
				out.print("ok");
			} else {
				out.print("deleteAction error");
			}

		} else if (type.equals("deleteAdminAction")) {
			String id = request.getParameter("id");
			boolean flag = false;
			try {
				flag = Sql_Admin.deleteAdminAction(Integer.parseInt(id));
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (flag) {
				out.print("ok");
			} else {
				out.print("deleteAction error");
			}

		} else if (type.equals("Adminbatchdelete")) {				//批量删除区县管理员
			String[] ids = request.getParameter("id").split(",");
			for (int i = 0; i < ids.length; i++) {
				try {
					Sql_Admin.deleteAdminAction(Integer.parseInt(ids[i]));
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}  else if (type.equals("Schooluserbatchdelete")) {			//批量删除学校用户
			String[] ids = request.getParameter("ids").split(",");
			String[] schoolNames = request.getParameter("schoolName").split(",");
			boolean flag = false;
			try {
				for (int i = 0; i < ids.length; i++) {
					flag = Sql_Admin.deleteAction(Integer.parseInt(ids[i]));
				}
				Sql_Admin.delBatchCommitSchUser(schoolNames);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}if (flag) {
				out.print("ok");
			} else {
				out.print("delete error");
			}

		} else if (type.equals("resetPasswd")) {
			String id = request.getParameter("id");
			String password = md5(md5("123456"));
			boolean flag = false;
			try {
				flag = Sql_Admin.resetPasswd(Integer.parseInt(id), password);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (flag) {
				out.print("success");
			} else {
				out.print("重置用户密码  error");
			}

		} else if (type.equals("resetAdminPasswd")) {
			String id = request.getParameter("id");
			String password = md5(md5("123456"));
			boolean flag = false;
			try {
				flag = Sql_Admin.resetAdminPasswd(Integer.parseInt(id),
						password);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (flag) {
				out.print("success");
			} else {
				out.print("重置用户密码  error");
			}

		} else if (type.equals("paging")) {   //搜索功能
			ArrayList query = null;
			String startNum = request.getParameter("startNum");
			String offset = request.getParameter("offset");
			String search = request.getParameter("search");
			boolean flag = search.matches("[0-9]+");
			if(flag){
				try {
					query = Sql_Admin.paging(1,startNum, offset, search);
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				try {
					query = Sql_Admin.paging(2,startNum, offset, search);
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			JSONArray jsonArray = new JSONArray();
			String json = jsonArray.fromObject(query).toString();
			out.print(json);
		} else if (type.equals("get_area_data")) {
			ArrayList query = null;
			try {
				query = Sql_Admin.get_area_data();
			} catch (Exception e) {
				e.printStackTrace();
			}
			JSONArray jsonArray = new JSONArray();
			String json = jsonArray.fromObject(query).toString();
			out.print(json);
		} else if (type.equals("pageAmount")) {
			String search = request.getParameter("search");
			int query = 0;
			try {
				query = Sql_Admin.pageAmount(search);
			} catch (Exception e) {
				e.printStackTrace();
			}
			out.print(query);
		} else if (type.equals("pageAmount1")) {
			int query = 0;
			try {
				query = Sql_Admin.pageAmount1();
			} catch (Exception e) {
				e.printStackTrace();
			}
			out.print(query);
		} else if (type.equals("search_School")) {
			ArrayList query = null;
			String txt = request.getParameter("txt");
			boolean flag = txt.matches("[0-9]+");
			if(flag){
				try {
					query = Sql_Admin.searchSchool(1,txt);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				try {
					query = Sql_Admin.searchSchool(2,txt);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			JSONArray jsonArray = new JSONArray();
			String json = jsonArray.fromObject(query).toString();
			out.print(json);
			// System.out.print(json);
		}

		// 获取用户名
		else if (type.equals("getname")) {
			// System.out.println(usname);
			out.print(usname);
		}

		// 查询编辑的区县管理员用户信息
		else if (type.equals("edit_display")) {
			ArrayList query = null;
			String userid = request.getParameter("id");
			try {
				query = Sql_Admin.editsearchAdminContent(userid);

			} catch (Exception e) {
				e.printStackTrace();
			}

			JSONArray jsonArray = new JSONArray();
			String json = jsonArray.fromObject(query).toString();
			out.print(json);
		}

		// 更新区县管理员用户信息
		else if (type.equals("edit")) {
			ArrayList query = null;

			String role = request.getParameter("role");
			int area_id = Integer.parseInt(request.getParameter("area_id"));
			String sch_name = request.getParameter("sch_name");
			int userid = Integer.parseInt(request.getParameter("id"));
			try {
				query = Sql_Admin.editAdminContent(userid, role, area_id,
						sch_name);

			} catch (Exception e) {
				e.printStackTrace();
			}
			JSONArray jsonArray = new JSONArray();
			String json = jsonArray.fromObject(query).toString();
			out.print(json);
		}
		// 查询编辑的学校用户的用户信息
		else if (type.equals("edit_schooluserdisplay")) {
			ArrayList query = null;
			String userid = request.getParameter("id");
			try {
				query = Sql_Admin.editsearchSchoolContent(userid);

			} catch (Exception e) {
				e.printStackTrace();
			}

			JSONArray jsonArray = new JSONArray();
			String json = jsonArray.fromObject(query).toString();
			out.print(json);
		}
		// 更新学校用户信息
		else if (type.equals("editschooluser")) {
			ArrayList query = null;

			String role = request.getParameter("role");
			int area_id = Integer.parseInt(request.getParameter("area_id"));
			String sch_name = request.getParameter("sch_name");
			int userid = Integer.parseInt(request.getParameter("id"));
			try {
				query = Sql_Admin.editSchoolContent(userid, role, area_id,
						sch_name);

			} catch (Exception e) {
				e.printStackTrace();
			}
			JSONArray jsonArray = new JSONArray();
			String json = jsonArray.fromObject(query).toString();
			out.print(json);
		} else {
			out.print("ajax传输错误");
		}

		try {
			Sql_Admin.closeConnection();
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
