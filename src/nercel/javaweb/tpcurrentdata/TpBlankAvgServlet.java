package nercel.javaweb.tpcurrentdata;

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

import nercel.javaweb.qxtpcurrentdata.QxTpCurrentdata;
import nercel.javaweb.qxtpcurrentdata.QxTpCurrentdataDbUtil;
import net.sf.json.JSONArray;

public class TpBlankAvgServlet extends HttpServlet {

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
 		
		TpCurrentdata tc = new TpCurrentdata();
		try {
			ArrayList list = new ArrayList();
			list = tc.getavgThird(currentTime);
			out.print(JSONArray.fromObject(list));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
