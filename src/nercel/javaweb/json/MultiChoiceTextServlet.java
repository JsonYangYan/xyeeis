package nercel.javaweb.json;

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

public class MultiChoiceTextServlet extends HttpServlet {

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

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		AnswerDbUtil awDbUtil = new AnswerDbUtil();
		String type = request.getParameter("type");//学校的类型 normal为正常学校，tp为教学点
		if(type == null) {
			type = "normal";
		}
		//从session中获取用户的名字
		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("name");
		
		//获取当前时间
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String time = dateFormat.format(date).toString();
		String[] strDate = time.split("-");
		String currentTime = strDate[0] + "-" + (strDate[1]);

		
		//获取前台传递的参数
		String choiceText = request.getParameter("value");
		String choiceId = request.getParameter("choiceId");
		String queId = request.getParameter("queId");
		
		
		
		try {
			awDbUtil.openConnection();
		} catch (Exception e2) {
			e2.printStackTrace();
		} 
		
		//获得学校的id号
		int schoolId = 0;
		try {
			schoolId = awDbUtil.getSchoolIdByMonth(userName, currentTime,type);
		} catch (Exception e1) {
			e1.printStackTrace();
		} 
		
		try {
			awDbUtil.updateChoiceAnswerByMonth(Integer.parseInt(queId), Integer.parseInt(choiceId), choiceText, schoolId, currentTime);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		try {
			awDbUtil.closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
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

}
