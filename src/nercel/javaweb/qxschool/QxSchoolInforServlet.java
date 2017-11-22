package nercel.javaweb.qxschool;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

public class QxSchoolInforServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String schoolName = request.getParameter("schoolName");
		//获取当前时间
		/*Date date = new Date();
		SimpleDateFormat Dateformat = new SimpleDateFormat("yyyy-MM");
		String currentTime = Dateformat.format(date).toString();*/
		String currentTime = request.getParameter("currentdate");
		
		ArrayList arrayList = new ArrayList();
		QxSchoolInfro schoolInfor = new QxSchoolInfro();
		try {
			arrayList.add(schoolInfor.getFirstIndexScore(schoolName,3,currentTime));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			arrayList.add(schoolInfor.getFirstIndexScore(schoolName,2,currentTime));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			arrayList.add(schoolInfor.getFirstIndexScore(schoolName,1,currentTime));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.print(JSONArray.fromObject(arrayList));
		out.flush();
		out.close();
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}
}
