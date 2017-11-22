package nercel.javaweb.qxprint;

import java.util.ArrayList;

public class PrintPdfInfor {

	// 计算校园网在1-20M 20-100M 100M以上的占有率
	public float getNetworkPercent(int fig, String areaName, String currentTime)
			throws Exception {
		PdfDbUtil pdfDb = new PdfDbUtil();
		pdfDb.openConnection();
		float netWorkPercent = 0;
		float netWorkSchNum = pdfDb.getNetwork(fig, areaName, currentTime); // 各个宽带区间学校的数量
		int totalSchoolNum = pdfDb.getAllSchoolIdNumber(areaName, currentTime)
				.size(); // 襄阳市或者各区县总的学校数

		if (pdfDb.getAllSchoolIdNumber(areaName, currentTime).size() != 0) {

			netWorkPercent = Float.parseFloat(new java.text.DecimalFormat(
					"#.##").format(netWorkSchNum / totalSchoolNum * 100));
		} else {
			netWorkPercent = 0;
		}

		pdfDb.closeConnection();
		return netWorkPercent;
	}

	// 计算无线网覆盖率情况，fig=1全覆盖 fig=0基本没建设无线网络
	public float getWirelessPercent(int fig, String areaName, String currentTime)
			throws Exception {
		PdfDbUtil pdfDb = new PdfDbUtil();
		pdfDb.openConnection();
		float WirelessPercent = 0;
		float wirelessSchNum = pdfDb.getWireless(fig, areaName, currentTime); // 各个无线网络区间学校的数量

		if (pdfDb.getAllSchoolIdNumber(areaName, currentTime).size() != 0) {
			int totalSchoolNum = pdfDb.getAllSchoolIdNumber(areaName,
					currentTime).size();
			WirelessPercent = Float.parseFloat(new java.text.DecimalFormat(
					"#.##").format(wirelessSchNum / totalSchoolNum * 100));
		} else {
			WirelessPercent = 0;
		}

		pdfDb.closeConnection();
		return WirelessPercent;
	}

	// 计算学校教师终端比例
	public float[] getTerminalPercent(String areaName, String currentTime)
			throws Exception {
		PdfDbUtil pdfDb = new PdfDbUtil();
		pdfDb.openConnection();
		ArrayList arrayListId = new ArrayList();
		float arrayTerminal[] = new float[4];
		int num1 = 0, num2 = 0, num3 = 0, num4 = 0;
		float percent1 = 0, percent2 = 0, percent3 = 0, percent4 = 0;
		arrayListId = pdfDb.getAllSchoolIdNumber(areaName, currentTime);

		for (int i = 0; i < arrayListId.size(); i++) {
			float terminalNum = pdfDb.getTerminalNum(
					Integer.parseInt(arrayListId.get(i).toString()),
					currentTime);
			if (terminalNum < 20) {
				num1++; // 小于20
			} else if (terminalNum >= 20 || terminalNum < 60) {
				num2++; // 20-60
			} else if (terminalNum >= 60 || terminalNum < 90) {
				num3++; // 60-90
			} else {
				num4++; // 大于90
			}
		}

		if (pdfDb.getAllSchoolIdNumber(areaName, currentTime).size() != 0) {
			int totalSchoolNum = pdfDb.getAllSchoolIdNumber(areaName,
					currentTime).size();
			percent1 = Float.parseFloat(new java.text.DecimalFormat("#.##")
					.format(num1 / totalSchoolNum * 100));
			percent2 = Float.parseFloat(new java.text.DecimalFormat("#.##")
					.format(num2 / totalSchoolNum * 100));
			percent3 = Float.parseFloat(new java.text.DecimalFormat("#.##")
					.format(num3 / totalSchoolNum * 100));
			percent4 = Float.parseFloat(new java.text.DecimalFormat("#.##")
					.format(num4 / totalSchoolNum * 100));
		} else {
			arrayTerminal[0] = percent1;
			arrayTerminal[1] = percent2;
			arrayTerminal[2] = percent3;
			arrayTerminal[3] = percent4;
		}

		pdfDb.closeConnection();
		return arrayTerminal;
	}

	// 计算未建设数字化教学系统、网络教学平台、教学资源管理平台、交互式电子白板教学系统、电子阅览系统 fig=1,2,3,4,5
	public float getDigitalTeaSystemPercent(int fig, String areaName,
			String currentTime) throws Exception {
		PdfDbUtil pdfDb = new PdfDbUtil();
		pdfDb.openConnection();
		float digitalSystemPercent = 0;
		float digitalSystemSchNum = pdfDb.getDigitalTeaSystem(fig, areaName,
				currentTime); // 运用各种数字化教学系统的学校的数量

		if (pdfDb.getAllSchoolIdNumber(areaName, currentTime).size() != 0) {
			int totalSchoolNum = pdfDb.getAllSchoolIdNumber(areaName,
					currentTime).size();
			digitalSystemPercent = Float
					.parseFloat(new java.text.DecimalFormat("#.##")
							.format(digitalSystemSchNum / totalSchoolNum * 100));
		} else {
			digitalSystemPercent = 0;
		}

		pdfDb.closeConnection();
		return digitalSystemPercent;
	}

	// 获取教师和学生的网络空间开通率 fig=1,2
	public float getNetWorkSpacePercent(int fig, String areaName,
			String currentTime) throws Exception {
		PdfDbUtil pdfDb = new PdfDbUtil();
		pdfDb.openConnection();
		float netWorkSpacePercent = 0;
		netWorkSpacePercent = pdfDb.getNetWorkSpace(fig, areaName, currentTime);

		pdfDb.closeConnection();
		return netWorkSpacePercent;
	}

	// 教研信息化 计算未开展教研信息化建设、未开展教研信息化建设、教研信息资源库、案例教研资源、教案教研资源、课件教研资源
	// fig=1,2,3,4,5,6
	public float getDigitalReaSystemPercent(int fig, String areaName,
			String currentTime) throws Exception {
		PdfDbUtil pdfDb = new PdfDbUtil();
		pdfDb.openConnection();
		float digitalReaSystemPercent = 0;
		float digitalSystemSchNum = pdfDb.getDigitalReaSystem(fig, areaName,
				currentTime); // 运用各种数字化教研资源的学校的数量

		if (pdfDb.getAllSchoolIdNumber(areaName, currentTime).size() != 0) {
			int totalSchoolNum = pdfDb.getAllSchoolIdNumber(areaName,
					currentTime).size();
			digitalReaSystemPercent = Float
					.parseFloat(new java.text.DecimalFormat("#.##")
							.format(digitalSystemSchNum / totalSchoolNum * 100));
		} else {
			digitalReaSystemPercent = 0;
		}

		pdfDb.closeConnection();
		return digitalReaSystemPercent;
	}

	// 管理与服务信息化 统计网站建设、管理系统、微信平台、邮箱平台开通率 fig=1,2,3,4
	public float getManagerSystemPercent(int fig, String areaName,
			String currentTime) throws Exception {
		PdfDbUtil pdfDb = new PdfDbUtil();
		pdfDb.openConnection();
		float digitalManagerSystemPercent = 0;
		float digitalSystemSchNum = pdfDb.getDigitalReaSystem(fig, areaName,
				currentTime); // 运用各种数字化教研资源的学校的数量

		if (pdfDb.getAllSchoolIdNumber(areaName, currentTime).size() != 0) {
			int totalSchoolNum = pdfDb.getAllSchoolIdNumber(areaName,currentTime).size();
			if (fig == 2) {
				digitalManagerSystemPercent = Float
						.parseFloat(new java.text.DecimalFormat("#.##")
								.format(100 - digitalSystemSchNum
										/ totalSchoolNum * 100));
			} else {
				digitalManagerSystemPercent = Float
						.parseFloat(new java.text.DecimalFormat("#.##")
								.format(digitalSystemSchNum / totalSchoolNum
										* 100));
			}
		} else {
			digitalManagerSystemPercent = 0;
		}

		pdfDb.closeConnection();
		return digitalManagerSystemPercent;
	}

	// 信息化保障
	// 教育总经费 教育信息化经费 网络建设与设备购置经费 资源与平台开发经费 培训经费 fig=1,2,3,4,5,
	public float getfundsNum(int fig, String areaName, String currentTime)
			throws Exception {
		PdfDbUtil pdfDb = new PdfDbUtil();
		pdfDb.openConnection();
		float fundsNum = 0;
		fundsNum = pdfDb.getFunds(fig, areaName, currentTime);

		pdfDb.closeConnection();
		return fundsNum;
	}

	// 参与信息技术培训的教师数量 人均参加多少课时的信息技术培训 fig=1,2
	public float getTrainNum(int fig, String areaName, String currentTime)
			throws Exception {
		PdfDbUtil pdfDb = new PdfDbUtil();
		pdfDb.openConnection();
		float trainNum = 0;
		trainNum = pdfDb.getTrain(fig, areaName, currentTime);

		pdfDb.closeConnection();
		return trainNum;
	}

}
