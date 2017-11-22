package nercel.javaweb.schooldetail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

public class ExportExcelServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// 获取当前时间
		/*Date date = new Date();
		SimpleDateFormat Dateformat = new SimpleDateFormat("yyyy-MM");
		String currentTime = Dateformat.format(date).toString();*/
		String currentTime = request.getParameter("currentdate");
	
 		
		String schoolName = request.getParameter("schoolName");
		//String schoolName = "襄阳七中";
		String fileName = schoolName + ".xls";
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/xls");// 根据个人需要,这个是下载文件的类型
		response.setHeader("Content-Disposition", "attachment; filename="
				+ new String(fileName.getBytes("gb2312"), "ISO8859-1"));

		ByteArrayOutputStream ba = null;
		try {
			ba = ExportExcel.export(schoolName, currentTime);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.setContentType("application/xls");
		response.setContentLength(ba.size());
		ServletOutputStream out = response.getOutputStream();
		ba.writeTo(out);
		out.flush();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}
}
