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

public class AddAnswerTempServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String operation = request.getParameter("operation");
		String type = request.getParameter("type");//学校类型 tp为教学点 ，normal为普通学校
		if (type == null) {
			type = "normal";
		}
		
		//从session中获取用户的名字
		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("name");
		
		//获取当前时间
		Date date = new Date();
		SimpleDateFormat Dateformat = new SimpleDateFormat("yyyy-MM-dd");
		String time = Dateformat.format(date).toString();
		String[] strDate = time.split("-");
		String currentTime = strDate[0] + "-" + (strDate[1]);
		AddAnswerTemp adt = new AddAnswerTemp();
		try {
			adt.openConnection();
		} catch (Exception e3) {
			e3.printStackTrace();
		} 
		int schoolId = 0;
		try {
			schoolId = adt.getSchoolIdByMonth(userName, currentTime,type);
		} catch (Exception e1) {
			e1.printStackTrace();
		}//获取学校的id
		
		if(operation.equals("addAns")) {
			String res_choice = null;
			String res_tex = null;
			String result = "error";
			//删除临时表中原来的数据
			
			try {
				adt.deleteAnswerTemp(schoolId, currentTime,type);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			String choiceAnswer = request.getParameter("choice_array");
			String textAnswer = request.getParameter("text_array");
			
			try {
				res_choice = adt.AddChoiceAnswer(choiceAnswer, schoolId, time,type);
			} catch (Exception e2) {
				e2.printStackTrace();
			} 
			
			
			try {
				res_tex = adt.AddTextAnswer(textAnswer, schoolId, time,type);
			} catch (Exception e1) {
				e1.printStackTrace();
			} 
			if(res_choice.equals("ok") && res_tex.equals("ok")){
				result ="ok";
			}
			out.print(result);
		}else if(operation.equals("330_text")){
			String text_val = request.getParameter("val");
			try {
				adt.updateText(text_val, schoolId, currentTime);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			adt.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
