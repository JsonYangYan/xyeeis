package nercel.javaweb.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nercel.javaweb.common.ConvertSessionName;
import nercel.javaweb.common.FormatCheckUtils;
import nercel.javaweb.register.DBUtil;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @Description: 学校用户登录，验证登录这的身份，包括登录名以及密码及验证是第几次登录
 * @author: Yan
 * @version: 0.1,上午8:42:52
 * @param:
 */
public class LoginServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		String userName = request.getParameter("name");
		String password = request.getParameter("password");
		String operation = request.getParameter("operation");
		ConvertSessionName csn = new ConvertSessionName();
		String sessionName = "";
		String sql = "SELECT * FROM tschooluser WHERE username = '" + userName+ "' OR tel = '"+userName+"' OR email = '"+userName+"'";	//验证登录
		//String sql_commit = "SELECT * FROM tschoolinfor WHERE userName = '"+ userName + "'";	//验证非首次登录
		DBUtil dbUtil = new DBUtil();
		try {
			dbUtil.openConnection();
		} catch (Exception e1) {
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
			} catch (SQLException e) {
				e.printStackTrace();
			}
			String pw_1 = DigestUtils.md5Hex(password);
			String pw_2 = DigestUtils.md5Hex(pw_1);
			if (pw_2.equals(pw)) {
				if(FormatCheckUtils.isPhoneLegal(userName)||FormatCheckUtils.isfixedPhone(userName)){
					try {
						csn.openConnection();
						sessionName = csn.getNameBytel(userName).trim();
						csn.closeConnection();
					} catch (SQLException | ClassNotFoundException e) {
						e.printStackTrace();
					}
				}else if(FormatCheckUtils.checkEmaile(userName)){
					try {
						csn.openConnection();
						sessionName = csn.getNameByEmail(userName).trim();
						csn.closeConnection();
					} catch (SQLException | ClassNotFoundException e) {
						e.printStackTrace();
					}
				}else{
					sessionName = userName.trim();
				}
				System.out.println(sessionName);
				// 创建session
				HttpSession session = request.getSession();
				session.setAttribute("name", sessionName);
				boolean flag = false;
				String role = null;
				try {
					role = dbUtil.getRole(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if(role.equals("user")){      //非教学点
					try {
						flag = dbUtil.getIsRecord(userName,"normal");  //判断是tschoolInfor中是否存在插入记录
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (flag) {
						out.print("againlogin");       //有记录，调转到 welcom_edit.html页面
					} else {
						out.print("firstlogin");      //没有记录，调转到welcom.html页面
					}
				}else if(role.equals("tpuser")){    //教学点
					try {
						flag = dbUtil.getIsRecord(userName,"tp");  //判断是tschoolInfor中是否存在插入记录
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (flag) {
						out.print("tpagainlogin");       //有记录，调转到 welcom_edit.html页面
					} else {
						out.print("tp_firstlogin");      //没有记录，调转到welcom.html页面
					}
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