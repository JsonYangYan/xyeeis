package nercel.javaweb.print;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.enterprise.inject.New;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

public class DemoPdfServlet extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		System.out.println(new Date()+"：进入后台");
		String areaName = request.getParameter("areaName");
		String timeTemp = request.getParameter("date");
		String strTitle = areaName + "教育信息化发展水平评估报告";
		String fileName = strTitle + ".pdf";
		response.setContentType("application/pdf");// 根据个人需要,这个是下载文件的类型
		response.setHeader("Content-Disposition",
				"attachment; filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
		/*// 获取当前时间
		Date date = new Date();
		SimpleDateFormat Dateformat = new SimpleDateFormat("yyyy-MM-dd");
		String time = Dateformat.format(date).toString();
		String[] strDate = time.split("-");
		String currentTime = strDate[0] + "-" + strDate[1];*/
		
		DemoPdf demoPdf = new DemoPdf();
//		try {
//			response.setContentLength(demoPdf.getDemoDocumet(areaName,timeTemp).size());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		ServletOutputStream out = response.getOutputStream();
		try {
//			System.out.println(new Date()+":开始生成pdf文件");
			demoPdf.getDemoDocumet(areaName,timeTemp).writeTo(out);
//			System.out.println(new Date()+":已经生成pdf文件");
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.flush();
	}

	/**
	 * This method is called when a form has its tag value method equals to
	 * post.
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}
