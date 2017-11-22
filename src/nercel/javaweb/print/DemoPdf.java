package nercel.javaweb.print;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import javax.enterprise.inject.New;
import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;

import nercel.javaweb.admin.FilePathUtil;

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

import java.util.HashMap;

public class DemoPdf {

	public ByteArrayOutputStream getDemoDocumet(String areaName,
			String currentTime) throws Exception {
//		System.out.println(new Date()+":进入pdf生成函数！");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Document document = new Document(PageSize.A4, 36, 36, 50, 36);
		PdfWriter writer = PdfWriter.getInstance(document, baos);

		URL resource = DemoPdf.class.getResource("simfang.ttf");
		String strPath = DemoPdf.class.getResource("/").getPath();
		String absolutePath = new File(System.getProperty("java.io.tmpdir"))
				.getParentFile().getAbsolutePath();
//		absolutePath = absolutePath + "\\webapps\\ROOT\\dianxin\\xyeeis\\print_modular\\font";
		absolutePath = absolutePath + "\\webapps\\xyeeis\\print_modular\\font";
//		BaseFont bfFang = BaseFont.createFont(absolutePath + "\\simfang.ttf",
//				BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
//		BaseFont bfHei = BaseFont.createFont(absolutePath + "\\simhei.ttf",
//				BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		
		BaseFont bfFang = BaseFont.createFont(FilePathUtil.getRealFilePath(absolutePath + "\\simfang.ttf"),
				BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		BaseFont bfHei = BaseFont.createFont(FilePathUtil.getRealFilePath(absolutePath + "\\simhei.ttf"),
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

//		System.out.println(new Date()+":开始生成第一、二段落！");
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

//		System.out.println(new Date()+":第一二段落生成，开始进入基础设施建设部分！");
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
//		System.out.println(new Date()+":开始进入1.1宽带网络建设部分！");
		// 1.1宽带网络建设
		Paragraph fristParaOneOne = new Paragraph("1.1宽带网络建设", fSecondParaHei);
		fristParaOneOne.setLeading(40f);// 设置行间距
		fristParaOneOne.setSpacingAfter(10f);
		fristParaOneOne.setAlignment(Paragraph.ALIGN_LEFT);
		document.add(fristParaOneOne);
		
//		float netF1 = pdfInfor.getNetworkPercent(1, areaName, currentTime);
//		float netF2 = pdfInfor.getNetworkPercent(2, areaName, currentTime);
//		float netF3 = pdfInfor.getNetworkPercent(3, areaName, currentTime);
		float[] netF=pdfInfor.getNetworkPercent(areaName, currentTime);
		String StrFristParaThree = pdfCon.getFristParaThree(areaName, netF[0],
				netF[1], netF[2]);
		Paragraph fristParaThree = new Paragraph(StrFristParaThree,
				fContentFang);
		fristParaThree.setLeading(25f);// 设置行间距
		fristParaThree.setAlignment(Paragraph.ALIGN_JUSTIFIED);
		document.add(fristParaThree);
//		System.out.println(new Date()+":1.1宽带网络建设部分已完成，开始进入1.2无线网络覆盖情况部分");
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
//		System.out.println(new Date()+":1.2无线网络覆盖情况部分已完成，开始进入1.3信息化终端部分");
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

//		System.out.println(new Date()+":基础设施建设部分生成，开始进入教学信息化应用部分！");
		// 2教学信息化应用
		Paragraph secondParaOne = new Paragraph("2.教学信息化应用", fParaHei);
		secondParaOne.setLeading(40f);// 设置行间距
		secondParaOne.setSpacingAfter(10f);
		secondParaOne.setAlignment(Paragraph.ALIGN_LEFT);
		document.add(secondParaOne);

		HashMap<Integer, Float> digitalTeaPercent = pdfInfor.getDigitalTeaSystemPercent(areaName,
				currentTime);
		HashMap<Integer, Float> netSpacePercent = pdfInfor.getNetWorkSpacePercent(areaName,
				currentTime); // 网络学习空间
		float[] digitalTeaPercents={0,0,0,0,0};
		float[] netSpaces={0,0};
		if(digitalTeaPercent!=null)
		  for(int i=0;i<5;i++){
			  if(digitalTeaPercent.containsKey(230+i)){
				  digitalTeaPercents[i]=digitalTeaPercent.get(230+i);
			  }else{
				  digitalTeaPercents[i]=0;
			  }
			
		  }
		if(netSpacePercent!=null)
			for(int i=0;i<2;i++){
				if(netSpacePercent.containsKey(143+i)){
					netSpaces[i]=netSpacePercent.get(143+i);
				}else{
					netSpaces[i]=0;
				}
				
			}
		String StrSecondParaTwo = pdfCon.getSecondParaTwo(areaName,
				digitalTeaPercents[0], digitalTeaPercents[1], digitalTeaPercents[2], digitalTeaPercents[3],
				digitalTeaPercents[4], netSpaces[0], netSpaces[1]);
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

		ByteArrayOutputStream stream = pieTest.CreatePieChart(pieTitle,
				PieType1, PieType2, PieType3, PieType4, digitalTeaPercents[1] , digitalTeaPercents[2],
				digitalTeaPercents[3], digitalTeaPercents[4]);
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

//		System.out.println(new Date()+":教学信息化应用部分生成，开始进入教研信息化应用部分！");
		// 3教研信息化应用
		Paragraph thirdParaOne = new Paragraph("3.教研信息化应用", fParaHei);
		thirdParaOne.setLeading(40f);// 设置行间距
		thirdParaOne.setSpacingAfter(10f);
		thirdParaOne.setAlignment(Paragraph.ALIGN_LEFT);
		document.add(thirdParaOne);

		HashMap<Integer, Float> digitalTReaPercent=pdfInfor.getDigitalReaSystemPercent(areaName,
				currentTime);
		float[] digitalTReaPercents={0,0,0,0,0,0};
		if(digitalTReaPercent!=null){
			for(int i=0;i<6;i++){
				if(digitalTReaPercent.containsKey(236+i)){
					digitalTReaPercents[i]=digitalTReaPercent.get(236+i);
				}else {
					digitalTReaPercents[i]=0;
				}
				
			}
		}
		String StrThirdParaTwo = pdfCon.getThirdParaTwo(areaName,
				digitalTReaPercents[0], digitalTReaPercents[1],digitalTReaPercents[2], digitalTReaPercents[3],
				digitalTReaPercents[4], digitalTReaPercents[5]);
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
				barHTitle, barVTitle, digitalTReaPercents[1], digitalTReaPercents[2],
				digitalTReaPercents[3], digitalTReaPercents[4], digitalTReaPercents[5], barType1,
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

//		System.out.println(new Date()+":教研信息化应用部分生成，开始进入管理与服务信息化部分！");
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

//		float managerF1 = pdfInfor.getManagerSystemPercent(1, areaName,
//				currentTime);
//		float managerF2 = pdfInfor.getManagerSystemPercent(2, areaName,
//				currentTime);
//		float managerF3 = pdfInfor.getManagerSystemPercent(3, areaName,
//				currentTime);
//		float managerF4 = pdfInfor.getManagerSystemPercent(4, areaName,
//				currentTime);
		HashMap<Integer, Float> manSerInfoPercent=pdfInfor.getManagerSystemPercent(areaName, currentTime);
		float[] manSerInfoPercents={0,0,0,0};
		int[] choiceIds={277,249,272,273};
		if(digitalTReaPercent!=null){
			for(int i=0;i<4;i++){
				if(manSerInfoPercent.containsKey(choiceIds[i])){
					manSerInfoPercents[i]=manSerInfoPercent.get(choiceIds[i]);
				}else {
					manSerInfoPercents[i]=0;
				}
				
			}
		}
		String StrFourthParaThree = pdfCon.getFourthParaThree(areaName,
				manSerInfoPercents[0], manSerInfoPercents[1], manSerInfoPercents[2], manSerInfoPercents[3]);
		Paragraph fourthParaThree = new Paragraph(StrFourthParaThree,
				fContentFang);
		fourthParaThree.setLeading(25f);// 设置行间距
		fourthParaThree.setAlignment(Paragraph.ALIGN_JUSTIFIED);
		document.add(fourthParaThree);

//		System.out.println(new Date()+":管理与服务信息化部分生成，开始进入信息化保障部分！");
		// 5信息化保障
		Paragraph fifthParaOne = new Paragraph("5.信息化保障", fParaHei);
		fifthParaOne.setLeading(40f);// 设置行间距
		fifthParaOne.setSpacingAfter(10f);
		fifthParaOne.setAlignment(Paragraph.ALIGN_LEFT);
		document.add(fifthParaOne);

//		float fundsF1 = pdfInfor.getfundsNum(1, areaName, currentTime);
//		float fundsF2 = pdfInfor.getfundsNum(2, areaName, currentTime);
//		float fundsF3 = pdfInfor.getfundsNum(3, areaName, currentTime);
//		float fundsF4 = pdfInfor.getfundsNum(4, areaName, currentTime);
//		float fundsF5 = pdfInfor.getfundsNum(5, areaName, currentTime);
		HashMap<Integer, Float> fundsPercent=pdfInfor.getfundsNum(areaName, currentTime);
		float[] fundsPercents={0,0,0,0,0};
		int[] queIds={168,169,170,171,172};
		if(fundsPercent!=null){
			for(int i=0;i<5;i++){
				if(fundsPercent.containsKey(queIds[i])){
					fundsPercents[i]=fundsPercent.get(queIds[i]);
				}else {
					fundsPercents[i]=0;
				}
				
			}
		}
		String StrFifthParaTwo = pdfCon.getFifthParaTwo(areaName, fundsPercents[0],
				fundsPercents[1], fundsPercents[2], fundsPercents[3], fundsPercents[4]);
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
				monerybarTitle, monerybarHTitle, monerybarVTitle, fundsPercents[0],
				fundsPercents[1], fundsPercents[2], fundsPercents[3], fundsPercents[4], monerybarType1,
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

//		System.out.println(new Date()+":信息化保障部分已完成！");
		document.close();
		return baos;
	}

}
