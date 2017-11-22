package nercel.javaweb.qxschool.monthcontrast;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nercel.javaweb.qxstatistics.DbQxStatistics;
import net.sf.json.JSONArray;

public class MonthContrastServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		MonthContrast MC = new MonthContrast();
		// 获取session
		HttpSession session = request.getSession(false);
		String qxusername = (String) session.getAttribute("username");
		String type = request.getParameter("operation");
		if(type.equals("qxmonthcontrast")){
			ArrayList list = new ArrayList();
			try {
				list = MC.getQxScore(qxusername);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out.print(JSONArray.fromObject(list));
		}
		else if(type.equals("allmonthcontrast")){
			ArrayList list = new ArrayList();
			try {
				list = MC.getAllScore(0);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out.print(JSONArray.fromObject(list));
		}
		out.flush();
		out.close();
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
