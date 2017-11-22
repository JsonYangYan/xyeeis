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

public class AddAnswerServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String operation = request.getParameter("operation");
		
		//从session中获取用户的名字
		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("name");	
		PrintWriter out = response.getWriter();
		String type = request.getParameter("type");//学校类型 tp为教学点 ，normal为普通学校
		if (type == null) {
			type = "normal";
		}
		//获取当前时间
		Date date = new Date();
		SimpleDateFormat Dateformat = new SimpleDateFormat("yyyy-MM-dd");
		String time = Dateformat.format(date).toString();
		String[] strDate = time.split("-");
		String currentTime = strDate[0] + "-" + (strDate[1]);
		AddAnswer adda= new AddAnswer();
		//打开连接
		try {
			adda.openConnection();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		//获取学校的id
		int schoolId = 0;
		try {
			schoolId = adda.getSchoolIdByMonth(userName, currentTime,type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(operation.equals("addAnswer")) {
			String res = null;
			//删除本月里正式表中的答案
			try {
				adda.delAnswer(schoolId, currentTime,type);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			//从选择题临时表中复制答案，到正式表中
			
			try {
				res = adda.AddChoiceAnswerToFormal(schoolId, time, currentTime,type);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			//从填空题临时表中 复制答案，到正式表中
			try {
				adda.AddTextAnswerToFormal(schoolId, time, currentTime,type);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			//提交后改变状态
			try {
				if (res == "ok") {
					adda.changeState(schoolId,type);
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			try {
				adda.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			out.print(res);
		}
		
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			doGet(request, response);
		
	}


}
