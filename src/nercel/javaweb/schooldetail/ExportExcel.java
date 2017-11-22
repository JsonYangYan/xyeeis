package nercel.javaweb.schooldetail;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExportExcel {
	// 存储excel表头（此处在左侧）
	public static String[] title() throws SQLException, ClassNotFoundException {
		String[] Title = { "班级(个)", "教师(名)", "学生(名)", "校园网出口带宽(兆/M)",
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
				"参与信息技术能力培训的教师(名)  ", "教师人均参加信息技术能力培训(课时)" };
		return Title;
	}

	public static ByteArrayOutputStream export(String schoolName, String currentTime)
			throws Exception {
		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("对比表");
		SchooldetailDbUtil detail = new SchooldetailDbUtil();
		// 第三步，写入实体数据 实际应用中这些数据从数据库得到，
		detail.openConnection();
		Map map_school = detail.getSchooldetail(schoolName, currentTime);
		map_school.remove("c" + "0");
		map_school.remove("c" + "1");
		JSONObject jsonObject_school = JSONObject.fromObject(map_school);
		Map map_area = detail.getAreadetail(schoolName, currentTime);
		map_area.remove("c" + "0");
		map_area.remove("c" + "1");
		JSONObject jsonObject_area = JSONObject.fromObject(map_area);
		Map map_all = detail.getAlldetail(schoolName, currentTime);
		map_all.remove("c" + "0");
		map_all.remove("c" + "1");
		JSONObject jsonObject_all = JSONObject.fromObject(map_all);
		
		HSSFRow row = sheet.createRow((int) 0);
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_JUSTIFY); // 创建一个居中格式
		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("");
		cell.setCellStyle(style);

		cell = row.createCell((short) 1);
		cell.setCellValue(schoolName);
		cell.setCellStyle(style);

		String schoolArean = detail.getArean(schoolName); // 通过schoolName，查找到学校所在县区
		cell = row.createCell((short) 2);
		cell.setCellValue(schoolArean);
		cell.setCellStyle(style);

		cell = row.createCell((short) 3);
		cell.setCellValue("襄阳市");
		cell.setCellStyle(style);

		for (int i = 0; i < jsonObject_school.size(); i++) {
			HSSFRow row1 = sheet.createRow((int) (i + 1));
			String[] title = title();
			HSSFCell cellTemp = row1.createCell((short) 0);
			cellTemp.setCellValue((String) title[i]);
			cellTemp.setCellStyle(style);

			cellTemp = row1.createCell((short) 1);
			cellTemp.setCellValue(
					Float.parseFloat(jsonObject_school.get("c" + (i + 2))
							.toString()));
			cellTemp.setCellStyle(style);

			cellTemp = row1.createCell((short) 2);
			cellTemp.setCellValue(
					Float.parseFloat(jsonObject_area.get("c" + (i + 2))
							.toString()));
			cellTemp.setCellStyle(style);

			cellTemp = row1.createCell((short) 3);
			cellTemp.setCellValue(
					Float.parseFloat(jsonObject_all.get("c" + (i + 2))
							.toString()));
			cellTemp.setCellStyle(style);
		}
		// 第四步，将文件写入字节流
		try {
			wb.write(ba);
		} catch (Exception e) {
			e.printStackTrace();
		}
		detail.closeConnection();
		return ba;
	}
}