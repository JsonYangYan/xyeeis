package nercel.javaweb.score;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ScoreServlet extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		// 获取当前时间
		Date date = new Date();
		SimpleDateFormat Dateformat = new SimpleDateFormat("yyyy-MM-dd");
		String time = Dateformat.format(date).toString();
		//String time = "2017-04-25";
		String[] strDate = time.split("-");
		String currentTime = strDate[0] + "-" + strDate[1];
		//String currentTime = "2017-04";
		// 获取Calendar  
		Calendar calendar = Calendar.getInstance();
		// 设置日期为本月最大日期  
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE)); 
		String lastTime = Dateformat.format((calendar.getTime())).toString(); 
		
		PaperScore paperScore = new PaperScore();
		DeleteScore deleteScore = new DeleteScore();
		
		/*if(time.equals(lastTime)){
			out.print("timeover");   //表示算分时间截止
		}else{}*/

		//删除本月绩效分数
		try {
			System.out.println("before delete");
			deleteScore.deleteScore(currentTime);
			System.out.println("after delete");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//计算本月绩效分数
		try {
			paperScore.calculationScore(time, currentTime);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//计算本月各区县一级指标得分
		try {
			System.out.println("正在计算一级指标得分...");
			paperScore.setAreaScore(currentTime, time);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//计算本月各区县二级指标得分
		try {
			System.out.println("正在计算二级指标得分...");
			paperScore.setSecondAreanScore(currentTime, time);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//计算本月各区县三级指标得分
		try {
			System.out.println("正在计算三级指标得分...");
			paperScore.setThirdAreanScore(currentTime, time);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			out.print("ok"); //ok时表示计算绩效成功
	
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}
