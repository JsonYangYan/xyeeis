package nercel.javaweb.qxexcelresult;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

public class ExportAreanExcelServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获取session
		HttpSession session = request.getSession(false);
		String qxusername = (String) session.getAttribute("username");
		AreanSchooldetailDbUtil areaSchDb = new AreanSchooldetailDbUtil();
		//获取当前时间
		/*Date date = new Date();
 		SimpleDateFormat Dateformat = new SimpleDateFormat("yyyy-MM");
 		String currentTime = Dateformat.format(date).toString();*/
		String currentTime = request.getParameter("date");
		//打开数据库
		try {
			areaSchDb.openConnection();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//获取用户所在的区县名称
		String areaName = "";
		try {
			areaName = areaSchDb.getArean(qxusername);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String strTitle = currentTime+areaName+"教育信息化发展现状数据";
		String fileName = strTitle + ".xls";
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/xls");// 根据个人需要,这个是下载文件的类型
		response.setHeader("Content-Disposition", "attachment; filename="
				+ new String(fileName.getBytes("gb2312"), "ISO8859-1"));

		ByteArrayOutputStream ba = null;
		try {
			ba = ExportExcel.export(areaName,currentTime);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.setContentType("application/xls");
		response.setContentLength(ba.size());
		ServletOutputStream out = response.getOutputStream();
		ba.writeTo(out);
		//断开连接
		try {
			areaSchDb.closeConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			out.flush();
			out.close();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}
}
