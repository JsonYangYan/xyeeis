package nercel.javaweb.print;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Servlet implementation class Pdf
 */
/**
 * @Description: 能够在线生成pdf并且能够预览
 * @author: Stone
 * @version: 0.1,上午8:43:48
 * @param:
 */
@WebServlet("/Pdf")
public class OnlineReadPdf extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OnlineReadPdf() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Document document = new Document(PageSize.A4, 36, 36, 36, 36);
		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		try {
			PdfWriter writer = PdfWriter.getInstance(document, ba);
			document.addTitle("襄阳市信息化水平发展报告");
			document.addAuthor("EISR");
			document.addKeywords("信息化评估、发展水平");
			document.open();
			BaseFont baseFont = BaseFont.createFont(
					"C:/Windows/Fonts/SIMKAI.TTF", BaseFont.IDENTITY_H,
					BaseFont.NOT_EMBEDDED);
			// ////////////////////////////////////////////////
			Font font = new Font(baseFont, 15, Font.BOLD);
			String str = "襄阳市教育信息化绩效总分为56.28:其中基础设施发展指数为52.36,数字资源发展指数为26.36,应用服务发展指数为42,应用效能指数为13.07,"
					+ "机制保障指数为13.09, 湖北省的教育信息化绩效总得分为64.23,机制保障指数为13.09,湖北省信息化绩效总得分为64.23,全国的教育信息化绩效"
					+ "得分为52.89,全国的教育信息化绩效得分为52.8。";
			Paragraph para = new Paragraph(str, font);
			para.setAlignment(Paragraph.ALIGN_JUSTIFIED);
			document.add(new Paragraph(para));
			Paragraph country = new Paragraph();
			Image img = Image
					.getInstance("http://localhost:8080/xyeeis/print_modular/images/p_c1.jpg");
			img.setAlignment(Image.CREATOR | Image.TEXTWRAP);
			img.setBorder(Image.BOX);
			img.setBorderWidth(10);
			img.setBorderColor(BaseColor.WHITE);
			img.scaleToFit(1000, 72);// 大小
			document.add(img);

		} catch (DocumentException de) {
			de.printStackTrace();
			System.err.println("A Document error:" + de.getMessage());
		} finally {
			document.close();
			response.setContentType("application/pdf");
			response.setContentLength(ba.size());
			ServletOutputStream out = response.getOutputStream();
			ba.writeTo(out);
			out.flush();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}