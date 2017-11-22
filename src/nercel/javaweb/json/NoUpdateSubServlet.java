package nercel.javaweb.json;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nercel.javaweb.common.ConvertSessionName;
import nercel.javaweb.common.DateCal;
import nercel.javaweb.common.FormatCheckUtils;
import nercel.javaweb.forgotpassword.ForgotPasswordDbUtil;
import net.sf.json.JSONArray;

public class NoUpdateSubServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		AnswerDbUtil awDbUtil = new AnswerDbUtil();
		DateCal dateCal = new DateCal();
		String type = request.getParameter("type");//学校的类型 normal为正常学校，tp为教学点
		if(type == null) {
			type = "normal";
		}
		//从session中获取用户的名字
		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("name");
		
		// 获取当前时间
		Date date = new Date();
		SimpleDateFormat Dateformat = new SimpleDateFormat("yyyy-MM-dd");
		String time = Dateformat.format(date).toString();
		String[] strDate = time.split("-");
		String currentTime = strDate[0] + "-" + strDate[1];
		// 定义变量
		int schoolId = 0;
		ArrayList listTemp = new ArrayList();
		int i = 0;
		// 打开数据库连接
		try {
			awDbUtil.openConnection();
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			schoolId = awDbUtil.getSchoolIdByMonth(userName, currentTime, type);
			System.out.println(schoolId);
		} catch (Exception e4) {
			// TODO Auto-generated catch block
			e4.printStackTrace();
		}
		try {
			listTemp = awDbUtil.getState(userName, type);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (listTemp.size() != 0) {
			JSONArray jsonArray = JSONArray.fromObject(listTemp);
			int preSchoolId = jsonArray.getJSONObject(0).getInt("schoolId");
			System.out.println("pre"+preSchoolId);
			
			// 删除本月份数据
			try {
				awDbUtil.delBlank(schoolId, currentTime,type);
			} catch (SQLException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			try {
				awDbUtil.delBlankTemp(schoolId, currentTime,type);
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				awDbUtil.delChoice(schoolId, currentTime,type);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				awDbUtil.delChoiceTemp(schoolId, currentTime,type);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// 插入上次数据，变为本月份数据
			try {
				awDbUtil.AddLastMonthChoiceAnswerCurrent(preSchoolId, schoolId,time,type);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				awDbUtil.AddLastMonthChoiceAnswerCurrentTemp(preSchoolId,schoolId,time,type);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				awDbUtil.AddLastMonthTextAnswerToCurrent(preSchoolId, schoolId,time,type);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				awDbUtil.AddLastMonthTextAnswerToCurrentTemp(preSchoolId,schoolId,time,type);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// 提交后改变状态
			try {
				i = awDbUtil.changeState(schoolId,time,type);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			out.print(i);
		}else {
			out.print("error");
		}

		try {
			awDbUtil.closeConnection();
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
