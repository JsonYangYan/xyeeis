package nercel.javaweb.qxprint;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

public class QxPrintPdf {

	public ByteArrayOutputStream getDemoDocumet(String areaName,
			String currentTime) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Document document = new Document(PageSize.A4, 36, 36, 50, 36);
		PdfWriter writer = PdfWriter.getInstance(document, baos);

		URL resource = QxPrintPdf.class.getResource("simfang.ttf");
		String strPath = QxPrintPdf.class.getResource("/").getPath();
		String absolutePath = new File(System.getProperty("java.io.tmpdir"))
				.getParentFile().getAbsolutePath();
		absolutePath = absolutePath + "\\webapps\\xyeeis\\print_modular\\font";

		BaseFont bfFang = BaseFont.createFont(absolutePath + "\\simfang.ttf",
				BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		BaseFont bfHei = BaseFont.createFont(absolutePath + "\\simhei.ttf",
				BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

		Font fTitleHei = new Font(bfHei, 25, Font.BOLD);
		Font fParaHei = new Font(bfHei, 18, Font.BOLD);
		Font fSecondParaHei = new Font(bfHei, 18, Font.NORMAL);
		Font fContentFang = new Font(bfFang, 18, Font.NORMAL);
		Font fchartTitleFang = new Font(bfFang, 15, Font.NORMAL);

		PdfContent pdfCon = new PdfContent();
		PrintPdfInfor pdfInfor = new PrintPdfInfor();
		// 打开文件夹
		document.open();
		Paragraph title = new Paragraph("" + areaName + "教育信息化发展水平评估报告",
				fTitleHei);// 抬头
		title.setAlignment(Element.ALIGN_CENTER); // 居中设置
		title.setLeading(5f);// 设置行间距//设置上面空白宽度
		document.add(title);

		// 第一、二段落
		String StrAbstractOne = pdfCon.getAbstractStrOne();
		Paragraph abstractStrOne = new Paragraph(StrAbstractOne, fContentFang);
		abstractStrOne.setLeading(25f);// 设置行间距
		abstractStrOne.setSpacingBefore(25f); // 这是段前面
		abstractStrOne.setAlignment(Paragraph.ALIGN_JUSTIFIED);
		document.add(abstractStrOne);

		String StrAbstractTwo = pdfCon.getAbstractStrTwo();
		Paragraph abstractStrTwo = new Paragraph(StrAbstractTwo, fContentFang);
		abstractStrTwo.setLeading(25f);// 设置行间距
		abstractStrTwo.setAlignment(Paragraph.ALIGN_JUSTIFIED);
		document.add(abstractStrTwo);

		// 1基础设施建设
		Paragraph fristParaOne = new Paragraph("1.基础设施建设", fParaHei);
		fristParaOne.setLeading(40f);// 设置行间距
		fristParaOne.setSpacingAfter(10f);
		fristParaOne.setAlignment(Paragraph.ALIGN_LEFT);
		document.add(fristParaOne);

		String StrFristParaTwo = pdfCon.getFristParaTwo(areaName);
		Paragraph fristParaTwo = new Paragraph(StrFristParaTwo, fContentFang);
		fristParaTwo.setLeading(25f);// 设置行间距
		fristParaTwo.setAlignment(Paragraph.ALIGN_JUSTIFIED);
		document.add(fristParaTwo);

		// 1.1宽带网络建设
		Paragraph fristParaOneOne = new Paragraph("1.1宽带网络建设", fSecondParaHei);
		fristParaOneOne.setLeading(40f);// 设置行间距
		fristParaOneOne.setSpacingAfter(10f);
		fristParaOneOne.setAlignment(Paragraph.ALIGN_LEFT);
		document.add(fristParaOneOne);

		float netF1 = pdfInfor.getNetworkPercent(1, areaName, currentTime);
		float netF2 = pdfInfor.getNetworkPercent(2, areaName, currentTime);
		float netF3 = pdfInfor.getNetworkPercent(3, areaName, currentTime);
		String StrFristParaThree = pdfCon.getFristParaThree(areaName, netF1,
				netF2, netF3);
		Paragraph fristParaThree = new Paragraph(StrFristParaThree,
				fContentFang);
		fristParaThree.setLeading(25f);// 设置行间距
		fristParaThree.setAlignment(Paragraph.ALIGN_JUSTIFIED);
		document.add(fristParaThree);

		// 1.2无线网络覆盖情况
		Paragraph fristParaOneTwo = new Paragraph("1.2无线网络覆盖情况",
				fSecondParaHei);
		fristParaOneTwo.setLeading(40f);// 设置行间距
		fristParaOneTwo.setSpacingAfter(10f);
		fristParaOneTwo.setAlignment(Paragraph.ALIGN_LEFT);
		document.add(fristParaOneTwo);

		float wirelessF1 = pdfInfor
				.getWirelessPercent(1, areaName, currentTime);
		float wirelessF2 = pdfInfor
				.getWirelessPercent(2, areaName, currentTime);
		String StrFristParaFour = pdfCon.getFristParaFour(areaName, wirelessF1,
				wirelessF2);
		Paragraph fristParaFour = new Paragraph(StrFristParaFour, fContentFang);
		fristParaFour.setLeading(25f);// 设置行间距
		fristParaFour.setAlignment(Paragraph.ALIGN_JUSTIFIED);
		document.add(fristParaFour);

		// 1.3信息化终端
		Paragraph fristParaOneThree = new Paragraph("1.3信息化终端", fSecondParaHei);
		fristParaOneThree.setLeading(40f);// 设置行间距
		fristParaOneThree.setSpacingAfter(10f);
		fristParaOneThree.setAlignment(Paragraph.ALIGN_LEFT);
		document.add(fristParaOneThree);

		float terminal[] = pdfInfor.getTerminalPercent(areaName, currentTime);
		String StrFristParaFive = pdfCon.getFristParaFive(areaName,
				terminal[0], terminal[1], terminal[2], terminal[3]);
		Paragraph fristParaFive = new Paragraph(StrFristParaFive, fContentFang);
		fristParaFive.setLeading(25f);// 设置行间距
		fristParaFive.setAlignment(Paragraph.ALIGN_JUSTIFIED);
		document.add(fristParaFive);

		// 2教学信息化应用
		Paragraph secondParaOne = new Paragraph("2.教学信息化应用", fParaHei);
		secondParaOne.setLeading(40f);// 设置行间距
		secondParaOne.setSpacingAfter(10f);
		secondParaOne.setAlignment(Paragraph.ALIGN_LEFT);
		document.add(secondParaOne);

		float digitalTeaF1 = pdfInfor.getDigitalTeaSystemPercent(1, areaName,
				currentTime);
		float digitalTeaF2 = pdfInfor.getDigitalTeaSystemPercent(2, areaName,
				currentTime);
		float digitalTeaF3 = pdfInfor.getDigitalTeaSystemPercent(3, areaName,
				currentTime);
		float digitalTeaF4 = pdfInfor.getDigitalTeaSystemPercent(4, areaName,
				currentTime);
		float digitalTeaF5 = pdfInfor.getDigitalTeaSystemPercent(5, areaName,
				currentTime);
		float digitalTeaF6 = pdfInfor.getNetWorkSpacePercent(1, areaName,
				currentTime); // 网络学习空间
		float digitalTeaF7 = pdfInfor.getNetWorkSpacePercent(1, areaName,
				currentTime);
		String StrSecondParaTwo = pdfCon.getSecondParaTwo(areaName,
				digitalTeaF1, digitalTeaF2, digitalTeaF3, digitalTeaF4,
				digitalTeaF5, digitalTeaF6, digitalTeaF7);
		Paragraph secondParaTwo = new Paragraph(StrSecondParaTwo, fContentFang);
		secondParaTwo.setLeading(25f);// 设置行间距
		secondParaTwo.setAlignment(Paragraph.ALIGN_JUSTIFIED);
		document.add(secondParaTwo);

		// 教学信息化饼状图
		CreatePieChart pieTest = new CreatePieChart();
		Paragraph country = new Paragraph(); // pieTest2.getStream()
		String pieTitle = "";
		String PieType1 = "网络教学平台";
		String PieType2 = "教学资源管理平台";
		String PieType3 = "交互式电子白板教学系统";
		String PieType4 = "电子阅览系统";

		float pieValue1 = pdfInfor.getDigitalTeaSystemPercent(2, areaName,
				currentTime);
		float pieValue2 = pdfInfor.getDigitalTeaSystemPercent(3, areaName,
				currentTime);
		float pieValue3 = pdfInfor.getDigitalTeaSystemPercent(4, areaName,
				currentTime);
		float pieValue4 = pdfInfor.getDigitalTeaSystemPercent(5, areaName,
				currentTime);

		ByteArrayOutputStream stream = pieTest.CreatePieChart(pieTitle,
				PieType1, PieType2, PieType3, PieType4, pieValue1, pieValue2,
				pieValue3, pieValue4);
		Image img = Image.getInstance(stream.toByteArray());
		img.setAlignment(Image.CREATOR | Image.TEXTWRAP);

		img.setBorder(Image.BOX);
		img.setBorderWidth(10);
		img.setBorderColor(BaseColor.WHITE);
		img.scaleToFit(3000, 250);
		document.add(img);

		// 饼状图图标
		Paragraph piecharTitle = new Paragraph("图1 数字化教学系统建设应用情况",
				fchartTitleFang);
		piecharTitle.setLeading(20f);// 设置行间距
		piecharTitle.setSpacingAfter(10f);
		piecharTitle.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(piecharTitle);

		// 3教研信息化应用
		Paragraph thirdParaOne = new Paragraph("3.教研信息化应用", fParaHei);
		thirdParaOne.setLeading(40f);// 设置行间距
		thirdParaOne.setSpacingAfter(10f);
		thirdParaOne.setAlignment(Paragraph.ALIGN_LEFT);
		document.add(thirdParaOne);

		float digitalTReaF1 = pdfInfor.getDigitalReaSystemPercent(1, areaName,
				currentTime);
		float digitalTReaF2 = pdfInfor.getDigitalReaSystemPercent(2, areaName,
				currentTime);
		float digitalTReaF3 = pdfInfor.getDigitalReaSystemPercent(3, areaName,
				currentTime);
		float digitalTReaF4 = pdfInfor.getDigitalReaSystemPercent(4, areaName,
				currentTime);
		float digitalTReaF5 = pdfInfor.getDigitalReaSystemPercent(5, areaName,
				currentTime);
		float digitalTReaF6 = pdfInfor.getDigitalReaSystemPercent(6, areaName,
				currentTime);
		String StrThirdParaTwo = pdfCon.getThirdParaTwo(areaName,
				digitalTReaF1, digitalTReaF2, digitalTReaF3, digitalTReaF4,
				digitalTReaF5, digitalTReaF6);
		Paragraph thirdParaTwo = new Paragraph(StrThirdParaTwo, fContentFang);
		thirdParaTwo.setLeading(25f);// 设置行间距
		thirdParaTwo.setAlignment(Paragraph.ALIGN_JUSTIFIED);
		document.add(thirdParaTwo);

		// 教研信息化柱状图
		CreateBarChart barChart = new CreateBarChart();
		Paragraph country1 = new Paragraph();

		String barTitle = "";
		String barHTitle = "资源类型";
		String barVTitle = "百分比（%）";
		String barType1 = "学术文献数据库";
		String barType2 = "教研信息资源库";
		String barType3 = "案例";
		String barType4 = "教案";
		String barType5 = "课件";

		ByteArrayOutputStream barstream = barChart.CreateBarChart(barTitle,
				barHTitle, barVTitle, digitalTReaF2, digitalTReaF3,
				digitalTReaF4, digitalTReaF5, digitalTReaF6, barType1,
				barType2, barType3, barType4, barType5);
		Image barimg = Image.getInstance(barstream.toByteArray());

		barimg.setAlignment(Image.CREATOR | Image.TEXTWRAP);
		barimg.setBorder(Image.BOX);
		barimg.setBorderWidth(10);
		barimg.setBorderColor(BaseColor.WHITE);
		barimg.scaleToFit(3000, 250);
		document.add(barimg);

		// 柱状图图标
		Paragraph barcharTitle = new Paragraph("图2 数字化教研资源建设应用情况",
				fchartTitleFang);
		barcharTitle.setLeading(20f);// 设置行间距
		barcharTitle.setSpacingAfter(10f);
		barcharTitle.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(barcharTitle);

		// 4管理与服务信息化
		Paragraph fourthParaOne = new Paragraph("4.管理与服务信息化", fParaHei);
		fourthParaOne.setLeading(40f);// 设置行间距
		fourthParaOne.setSpacingAfter(10f);
		fourthParaOne.setAlignment(Paragraph.ALIGN_LEFT);
		document.add(fourthParaOne);

		String StrFourthParaTwo = pdfCon.getFourthParaTwo();
		Paragraph fourthParaTwo = new Paragraph(StrFourthParaTwo, fContentFang);
		fourthParaTwo.setLeading(25f);// 设置行间距
		fourthParaTwo.setAlignment(Paragraph.ALIGN_JUSTIFIED);
		document.add(fourthParaTwo);

		float managerF1 = pdfInfor.getManagerSystemPercent(1, areaName,
				currentTime);
		float managerF2 = pdfInfor.getManagerSystemPercent(2, areaName,
				currentTime);
		float managerF3 = pdfInfor.getManagerSystemPercent(3, areaName,
				currentTime);
		float managerF4 = pdfInfor.getManagerSystemPercent(4, areaName,
				currentTime);
		String StrFourthParaThree = pdfCon.getFourthParaThree(areaName,
				managerF1, managerF2, managerF3, managerF4);
		Paragraph fourthParaThree = new Paragraph(StrFourthParaThree,
				fContentFang);
		fourthParaThree.setLeading(25f);// 设置行间距
		fourthParaThree.setAlignment(Paragraph.ALIGN_JUSTIFIED);
		document.add(fourthParaThree);

		// 5信息化保障
		Paragraph fifthParaOne = new Paragraph("5.信息化保障", fParaHei);
		fifthParaOne.setLeading(40f);// 设置行间距
		fifthParaOne.setSpacingAfter(10f);
		fifthParaOne.setAlignment(Paragraph.ALIGN_LEFT);
		document.add(fifthParaOne);

		float fundsF1 = pdfInfor.getfundsNum(1, areaName, currentTime);
		float fundsF2 = pdfInfor.getfundsNum(2, areaName, currentTime);
		float fundsF3 = pdfInfor.getfundsNum(3, areaName, currentTime);
		float fundsF4 = pdfInfor.getfundsNum(4, areaName, currentTime);
		float fundsF5 = pdfInfor.getfundsNum(5, areaName, currentTime);
		String StrFifthParaTwo = pdfCon.getFifthParaTwo(areaName, fundsF1,
				fundsF2, fundsF3, fundsF4, fundsF5);
		Paragraph fifthParaTwo = new Paragraph(StrFifthParaTwo, fContentFang);
		fifthParaTwo.setLeading(25f);// 设置行间距
		fifthParaTwo.setAlignment(Paragraph.ALIGN_JUSTIFIED);
		document.add(fifthParaTwo);

		// 经费保障画图
		CreateBarChart monerybarChart = new CreateBarChart();
		Paragraph country2 = new Paragraph();

		String monerybarTitle = "";
		String monerybarHTitle = "经费类型";
		String monerybarVTitle = "经费（万）";
		String monerybarType1 = "教育总经费";
		String monerybarType2 = "教育信息化经费";
		String monerybarType3 = "网络设备经费";
		String monerybarType4 = "资源平台开发经费";
		String monerybarType5 = "培训经费";

		ByteArrayOutputStream monerybarstream = monerybarChart.CreateBarChart(
				monerybarTitle, monerybarHTitle, monerybarVTitle, fundsF1, fundsF2,
				fundsF3, fundsF4, fundsF5, monerybarType1,
				monerybarType2, monerybarType3, monerybarType4, monerybarType5);
		Image monerybarimg = Image.getInstance(monerybarstream.toByteArray());

		monerybarimg.setAlignment(Image.CREATOR | Image.TEXTWRAP);
		monerybarimg.setBorder(Image.BOX);
		monerybarimg.setBorderWidth(10);
		monerybarimg.setBorderColor(BaseColor.WHITE);
		monerybarimg.scaleToFit(3000, 250);
		document.add(monerybarimg);

		// 柱状图图标
		Paragraph monerybarcharTitle = new Paragraph("图3 教育经费保障情况",
				fchartTitleFang);
		monerybarcharTitle.setLeading(20f);// 设置行间距
		monerybarcharTitle.setSpacingAfter(10f);
		monerybarcharTitle.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(monerybarcharTitle);
		
		float trainF1 = pdfInfor.getTrainNum(1, areaName, currentTime);
		float trainF2 = pdfInfor.getTrainNum(2, areaName, currentTime);
		String StrFifthParathree = pdfCon.getFifthParaThree(areaName, trainF1,
				trainF2);
		Paragraph fifthParathree = new Paragraph(StrFifthParathree,
				fContentFang);
		fifthParathree.setLeading(25f);// 设置行间距
		fifthParathree.setAlignment(Paragraph.ALIGN_JUSTIFIED);
		document.add(fifthParathree);

		
		document.close();
		return baos;
	}

}
