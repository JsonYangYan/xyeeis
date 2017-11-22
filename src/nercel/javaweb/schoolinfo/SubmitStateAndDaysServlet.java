package nercel.javaweb.schoolinfo;

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
import nercel.javaweb.json.AnswerDbUtil;
import net.sf.json.JSONArray;

public class SubmitStateAndDaysServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String school_type = request.getParameter("school_type");//学校类型 normal普通学校，tp教学点
		AnswerDbUtil awDbUtil = new AnswerDbUtil();
		DateCal dateCal = new DateCal();
		ArrayList listTemp = new ArrayList();
		ArrayList list = new ArrayList();
		HashMap map = new HashMap();
		int flag = 1;
		int days = 0;
		
		//从session中获取用户的名字
		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("name");
		//String userName = "2142005596";
		
		// 获取当前时间
		Date date = new Date();
		SimpleDateFormat Dateformat = new SimpleDateFormat("yyyy-MM-dd");
		String time = Dateformat.format(date).toString();
		String[] strDate = time.split("-");
		String currentTime = strDate[0] + "-" + strDate[1];
		
		//打开数据库连接
		try {
			awDbUtil.openConnection();
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
		try {
			listTemp = awDbUtil.getState(userName,school_type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(listTemp.size()!=0){
			JSONArray jsonArray = JSONArray.fromObject(listTemp);
			String lastTime = jsonArray.getJSONObject(0).getString("lastTime");
			//把lastTime拆解为yyyy-MM格式
			String[] strDateLast = lastTime.split("-");
			String lastMonthTime = strDateLast[0] + "-" + strDateLast[1];
			if(lastMonthTime.equals(currentTime)){
				flag = 2;
			}else{
				flag = 3;
			}
			
			Date dateLast = null;
			Date dateCurrent = null;
			try {
				dateLast = Dateformat.parse(lastTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			try {
				dateCurrent =Dateformat.parse(time);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			Calendar calendar1 = Calendar.getInstance();
			Calendar calendar2 = Calendar.getInstance();
			calendar1.setTime(dateLast);
			calendar2.setTime(dateCurrent);
			//两次填写相差的天数
			days= dateCal.getDaysBetween(calendar1,calendar2);
		}
		map.put("fig",flag);
		map.put("days", days);
		list.add(map);
		JSONArray jsonArray = new JSONArray();
		jsonArray = JSONArray.fromObject(list);
		out.print(jsonArray);
		
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
