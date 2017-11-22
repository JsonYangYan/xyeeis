package nercel.javaweb.qxtpcurrentdata;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nercel.javaweb.qxcurrentdata.AssessmentDbUtil;
import nercel.javaweb.qxcurrentdata.CityScore;
import net.sf.json.JSONArray;

public class QxTpBlankAvgServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		// 获取当前时间
 		/*Date date = new Date();
 		SimpleDateFormat Dateformat = new SimpleDateFormat("yyyy-MM");
 		String currentTime = Dateformat.format(date).toString();*/
		String currentTime = request.getParameter("currentdate");
 		// 获取session
		HttpSession session = request.getSession(false);
		String qxusername = (String) session.getAttribute("username");
		
		String areaName = "";
		QxTpCurrentdata qtc = new QxTpCurrentdata();
		QxTpCurrentdataDbUtil qtcDbUtil = new QxTpCurrentdataDbUtil();
		try {
			qtcDbUtil.openConnection();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		try {
			areaName = qtcDbUtil.getArean(qxusername);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			ArrayList list = new ArrayList();			
			HashMap mapTown = new HashMap();
			HashMap mapCity = new HashMap();
			mapTown = (HashMap) qtc.getTownAvgThirdIndex(areaName, currentTime);
			mapCity = (HashMap) qtc.getTownAvgThirdIndex("襄阳市",currentTime);
			list.add(mapTown);
			list.add(mapCity);
		
			out.print(JSONArray.fromObject(list));
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		try {
			qtcDbUtil.closeConnection();
		} catch (Exception e1) {
			e1.printStackTrace();
		} 
		
		
		out.flush();
		out.close();
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}
