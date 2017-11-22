package nercel.javaweb.currentdata;

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

import nercel.javaweb.allassessment.AssessmentDbUtil;
import net.sf.json.JSONArray;

public class ChoicePectServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ChoicePectServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

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
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		
		 // 获取当前时间
 		/*Date date = new Date();
 		SimpleDateFormat Dateformat = new SimpleDateFormat("yyyy-MM");
 		String currentTime = Dateformat.format(date).toString();*/
		String currentTime = request.getParameter("currentdate");
		//String currentTime = "2017-02";
		String id = request.getParameter("id");
		PrintWriter out = response.getWriter();
		AssessmentDbUtil assessmentDbUtil = new AssessmentDbUtil();
		try {
			assessmentDbUtil.openConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		  ArrayList arrayList = new ArrayList();
		  try {
			arrayList = assessmentDbUtil.getChoiceValue(Integer.parseInt(id), currentTime);
		} catch (Exception e1) {
			e1.printStackTrace();
		} 
		try {
			assessmentDbUtil.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		out.print(JSONArray.fromObject(arrayList));
		out.flush();
		out.close();
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
		this.doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
