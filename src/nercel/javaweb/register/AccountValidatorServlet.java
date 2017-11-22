package nercel.javaweb.register;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @Description: 验证登录这的身份，包括登录名以及密码
 * @author: Stone
 * @version: 0.1,上午8:42:52
 * @param:
 */
public class AccountValidatorServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		String userName = request.getParameter("name");
		String password = request.getParameter("password");
		String operation = request.getParameter("operation");
		String sql = "SELECT * FROM tuser WHERE username = '" + userName + "'";
		// System.out.print(sql);
		DBUtil dbUtil = new DBUtil();
		try {
			dbUtil.openConnection();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String str = null;
		// 登陆验证
		if (operation.equals("login")) {
			try {
				str = dbUtil.getName(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (str == null) {
			out.print("username error");
		} else {
			String pw = null;
			try {
				pw = dbUtil.getPassword(sql);
				// System.out.print(pw);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			String pw_1 = DigestUtils.md5Hex(password);
			String pw_2 = DigestUtils.md5Hex(pw_1);
			if (pw_2.equals(pw)) {
				// 创建session
				HttpSession session = request.getSession();
				session.setAttribute("username", userName);
				String ro = null;
				try {
					ro = dbUtil.getRole(sql);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (ro.equals("admin")) {
					out.print("admin");
				} else if (ro.equals("superadmin")) {
					out.print("su");
				} else if (ro.equals("sladmin")) {
					out.print("sl");
				}
			} else {
				out.print("password error");
			}
		}

		try {
			dbUtil.closeConnection();
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