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

public class PaperResultsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		//从session中获取用户的名字
		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("name");
		
		//获取当前时间
		Date date = new Date();
		SimpleDateFormat Dateformat = new SimpleDateFormat("yyyy-MM-dd");
		String time = Dateformat.format(date).toString();
		String[] strDate = time.split("-");
		String currentTime = strDate[0] + "-" + (strDate[1]);
		String data = request.getParameter("dt");
		String type = request.getParameter("type");//传过来的类别 tp为教学点
		if (type == null) {
			type = "normal";
		}
		String str = null ;
		PaperResults paperResults = new PaperResults();
		try {
			str = paperResults.getPaperResults(Integer.parseInt(data), userName, currentTime,type);
		} catch (NumberFormatException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.print(str);
		out.flush();
		out.close();
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}
