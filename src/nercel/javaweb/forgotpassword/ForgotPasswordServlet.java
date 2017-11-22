package nercel.javaweb.forgotpassword;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
public class ForgotPasswordServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		ForgotPassword fp = new ForgotPassword();
		String type =  request.getParameter("operation");
		
		//String email = "1694068435@qq.com";
		String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
        
        if(type.equals("sendeamil")){
        	String flag = "";
        	String loginName = request.getParameter("userName").trim();
        	try {
    			flag = fp.sendmail(loginName,basePath);
    		} catch (ClassNotFoundException | SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	out.print(flag);
        }else if(type.equals("checklink")){
        	String sid = request.getParameter("sid");
        	String userName = request.getParameter("userName").trim();
        	String flag = "";
        	try {
        		flag = fp.checkResetLink(sid,userName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	out.print(flag);
        }else if(type.equals("resetpassword")){
        	String passWord = request.getParameter("password");
        	String userName = request.getParameter("userName").trim();
    		String encryptionPassWord = DigestUtils.md5Hex(DigestUtils.md5Hex(passWord));
        	boolean flag = false;
        	try {
        		flag = fp.resetPassword(encryptionPassWord, userName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	out.print(flag);
        }
        
       
		out.flush();
		out.close();
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}
