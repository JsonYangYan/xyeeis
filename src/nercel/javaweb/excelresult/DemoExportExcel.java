package nercel.javaweb.excelresult;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class DemoExportExcel {
	// 存储excel表头（此处在左侧）
	public static String[] getTitleInfor() throws SQLException,
			ClassNotFoundException {
		String[] Title = { "所在地","地址","班级(个)", "教师(名)", "学生(名)", "校园网出口带宽(兆/M)",
				"校园网平均带宽(兆/M)", "有线网络的覆盖率(%)", "无线网络的覆盖率(%)", "电子备课教室(间) ",
				"计算机教室(间)", "计算机教室座位(个)", "是否联网(是/否)", "使用率(课时/周)",
				"非故障电脑比例(%)", "录播教室(间)", "多媒体教室(间)", "教师终端-台式计算机(台)",
				"教师终端-笔记本电脑(台)", "教师终端-平板电脑(台)", "学生终端-台式计算机(台)",
				"学生终端-笔记本电脑(台)", "学生终端-平板电脑(台)", "教师开通网络学习空间比例(%)",
				"学生开通网络学习空间比例(%)", "应用数字化资源的课程比例(%)", "使用互动平台与家长交流的班级比例(%)",
				"去年信息化经费投入经费(万元)", "最近一年教育总经费(万元)", "信息化经费投入经费(万元)",
				"网络建设与设备购置的费用(万元)", "资源与平台开发的费用(万元)", "培训的费用(万元)",
				"运行和维护的费用(万元)", "研究及其他费用(万元)", "信息技术课程教师(名) ",
				"信息技术课程教师中的专职人员(名)", "信息技术课程教师中的兼职人员(名)", "信息化支持人员(名) ",
				"聘请专职人员(名)", "信息技术专业兼任教师(名)", "其他专业兼任教师(名)",
				"参与信息技术能力培训的教师(名)  ", "教师人均参加信息技术能力培训(课时)","学校多媒体教室形态",
				"学校建设并应用的数字化教学资源","学校应用的数字化教学系统","学校建设并应用的数字化教研资源","学校应用的数字化教研系统",
				"学校应用的信息化管理系统","学校一卡通功能","学校对外发布信息与宣传手段","是否有网站","是否统计访问量","网站是否能上传",
				"是否可以访问学校网站","网站支持的语言","网站可以在哪些设备上自适应呈现","学校信息化经费主要来源","学校信息化领导级别",
				"教师信息技术能力培训的渠道","教师信息技术培训内容","已经实现的信息安全保障","信息化发展规划制定情况","信息化发展规划发布形式",
				"学校及时做出数据备份与恢复"};
		return Title;
	}

	/**
	 * 
	 * 记录当前的数据得分
	 * 
	 * @return
	 * @throws Exception
	 */
	public static ByteArrayOutputStream export() throws Exception {
		DemoAllSchooldetailDbUtil dtDbUtil = new DemoAllSchooldetailDbUtil();
		dtDbUtil.openConnection();
		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		// 在这里增加for循环各区域
		ArrayList listArean = new ArrayList();
		listArean = dtDbUtil.getArean(); // 记录13个县级市
		for (int m = 0; m < listArean.size(); m++) { // listArean.size()
			if (!(listArean.get(m).toString().equals("襄阳市"))
					&& !(listArean.get(m).toString().equals("鱼梁洲"))) {
				
				HSSFSheet sheet = wb.createSheet(listArean.get(m).toString());
				ArrayList listSchoolName = dtDbUtil.getSchoolAreaName(listArean
						.get(m).toString());
				// 第三步，写入实体数据 实际应用中这些数据从数据库得到
				ArrayList listResult = new ArrayList();
				listResult = dtDbUtil.getDetail(listArean.get(m).toString());  //一个地区所有所有的信息
				int schoolNum = dtDbUtil.getschoolNum(listArean.get(m)
						.toString());
				
				//计算出用户所有区域的选择题选项
				ArrayList listChoice = new ArrayList();
				listChoice = dtDbUtil.getChoiceDetail(listArean.get(m).toString());
				
				
				// 首行需要记录的信息,包括42个选项题目
				HSSFRow row = sheet.createRow((int) 0);
				HSSFCellStyle style = wb.createCellStyle();
				style.setAlignment(HSSFCellStyle.ALIGN_JUSTIFY); // 创建一个居中格式
				HSSFCell cell = row.createCell(0);
				cell.setCellValue("");
				cell.setCellStyle(style);
				String[] strTitle = getTitleInfor();

				for (int i = 0; i < strTitle.length; i++) {
					cell = row.createCell(i + 1);
					cell.setCellValue(strTitle[i]);
					cell.setCellStyle(style);
				}

				// 开始设置下一行
				HSSFCellStyle styleContent = wb.createCellStyle();
				styleContent.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

				for (int k = 0; k < schoolNum; k++) {
					row = sheet.createRow(k + 1);
					JSONObject objTemp = JSONObject.fromObject(listResult
							.get(k));
					/////////////////////////////
						String strTemp = objTemp.getString("c" + 1);
						cell = row.createCell(0);
						cell.setCellValue(strTemp);
						cell.setCellStyle(style);

						// 此处增加循环 循环单元格
						for (int j = 0; j<44; j++) {  //手动算出来来的
							cell = row.createCell(j + 1);
							strTemp = objTemp.getString("c" + (j + 2));
							cell.setCellValue(strTemp);
							cell.setCellStyle(styleContent);
						}
						
						JSONObject objChoice = JSONObject.fromObject(listChoice
								.get(k));
						
						for(int j=0; j<22; j++) {  //手动算出来来的
							cell = row.createCell(45+j);
							strTemp = objChoice.getString("a" + j);
							cell.setCellValue(strTemp);
							cell.setCellStyle(styleContent);
						}
						
						
						
					
				}
			}
		}
		// 第四步，将文件写入字节流
		try {
			wb.write(ba);
		} catch (Exception e) {
			e.printStackTrace();
		}
		dtDbUtil.closeConnection();
		return ba;
	}
}