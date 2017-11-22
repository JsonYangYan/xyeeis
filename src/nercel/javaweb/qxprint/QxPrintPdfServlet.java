package nercel.javaweb.qxprint;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

public class QxPrintPdfServlet extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取session
		HttpSession session = request.getSession(false);
		String qxusername = (String) session.getAttribute("username");
		String areaName ="";
		PdfDbUtil pdfDb= new PdfDbUtil();
		try {
			pdfDb.openConnection();
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//根据登录名获得区县名称
		try {
			areaName = pdfDb.getArean(qxusername);
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			pdfDb.closeConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String strTitle = areaName + "教育信息化发展水平评估报告";
		String fileName = strTitle + ".pdf";
		response.setContentType("application/pdf");// 根据个人需要,这个是下载文件的类型
		response.setHeader("Content-Disposition",
				"attachment; filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
		// 获取当前时间
		/*Date date = new Date();
		SimpleDateFormat Dateformat = new SimpleDateFormat("yyyy-MM-dd");
		String time = Dateformat.format(date).toString();
		String[] strDate = time.split("-");
		String currentTime = strDate[0] + "-" + strDate[1];*/
		String timeTemp = request.getParameter("date");
		System.out.println(timeTemp);
		QxPrintPdf demoPdf = new QxPrintPdf();
		try {
			response.setContentLength(demoPdf.getDemoDocumet(areaName,timeTemp).size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		ServletOutputStream out = response.getOutputStream();
		try {
			demoPdf.getDemoDocumet(areaName,timeTemp).writeTo(out);
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
