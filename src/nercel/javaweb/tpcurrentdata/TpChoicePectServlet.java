package nercel.javaweb.tpcurrentdata;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nercel.javaweb.qxcurrentdata.AssessmentDbUtil;
import net.sf.json.JSONArray;

public class TpChoicePectServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");

		// 获取当前时间
		String currentTime = request.getParameter("currentdate");

		String id = request.getParameter("id");
		PrintWriter out = response.getWriter();
		TpCurrentdataDbUtil tcDbUtil = new TpCurrentdataDbUtil();
		try {
			tcDbUtil.openConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		ArrayList arrayList = new ArrayList();
		try {
			arrayList = tcDbUtil.getChoiceValue(Integer.parseInt(id),currentTime);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			tcDbUtil.closeConnection();
		} catch (SQLException e) {
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
