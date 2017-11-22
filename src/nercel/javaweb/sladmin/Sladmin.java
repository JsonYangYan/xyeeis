package nercel.javaweb.sladmin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import javax.servlet.http.HttpSession;






import net.sf.json.JSONArray;

import org.apache.commons.codec.digest.DigestUtils;

import java.text.NumberFormat;

public class Sladmin extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		//修改Response中的码表，在字符输出时使用。
		//response.setCharacterEncoding("UTF-8");
		//response.setHeader("content-type","text/html;charset=UTF-8");//设置浏览器以utf-8打开
		PrintWriter out = response.getWriter();		
		String type = request.getParameter("operation");
		//System.out.print(type);
        int k = 0;
        String usname = "";
        //获取session
        HttpSession session = request.getSession(false);
        usname = (String)session.getAttribute("username");
        Dbsladmin Dbsl = new Dbsladmin();
        try {
			Dbsl.openConnection();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
       if( type.equals("submited_schnum") ){        	
        	String submitschpercent = "";
        	try {
				Dbsl.getAreaname(usname);
				double subnum=Dbsl.getSubmitedSchoolnum();
				double totalnum=Dbsl.getSchoolnum(usname);				
				NumberFormat nt = NumberFormat.getPercentInstance();
				nt.setMinimumFractionDigits(2);
				submitschpercent=nt.format(subnum/totalnum);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        	
        	out.print(submitschpercent);       	
       }
        
       if(type.equals("welcome")) {
			 String schName = "";
			 try {
				 schName = Dbsl.getSchName(usname);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			 out.print(schName);
       }
        try {
        	Dbsl.closeConnection();
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
  

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
