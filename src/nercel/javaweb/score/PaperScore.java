package nercel.javaweb.score;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import nercel.javaweb.allassessment.AssessmentDbUtil;
import nercel.javaweb.qxallassessment.QxAssessmentDbUtil;

public class PaperScore {

	public void calculationScore(String time, String currentTime)
			throws Exception {

		ArrayList<Integer> listSchoolId = new ArrayList<Integer>();
		ScoreDbUtil scoreDbUtil = new ScoreDbUtil();
		scoreDbUtil.openConnection();
		listSchoolId = scoreDbUtil.getSchoolId(currentTime);
		standardScore(currentTime);
		for (int i = 0; i < listSchoolId.size(); i++) {
			System.out.println("第" + (i + 1) + "所学校");
			setScore(listSchoolId.get(i), time, currentTime);
		}

		scoreDbUtil.closeConnection();
	}

	// 计算标准分
	public static void standardScore(String currentTime) throws Exception {
		ArrayList<Float> arrayList = new ArrayList<Float>();
		ArrayList<Integer> list = new ArrayList<Integer>();
		ScoreDbUtil scoreDbUtil = new ScoreDbUtil();
		scoreDbUtil.openConnection();

		list = scoreDbUtil.getTotalBlank();
		int i;

		// 计算需要计算的标准分数 工作做完
		for (i = 0; i < list.size(); i++) {
			if (list.get(i) == 167) {
				float f = scoreDbUtil
						.getChangeAverageBlankRelateAnswer_167(currentTime); // 167中存储平均增长率
				scoreDbUtil.updateBlankTrueAnswer(167, f);
			}
			if (list.get(i) == 124 || list.get(i) == 125 || list.get(i) == 126
					|| list.get(i) == 127 || list.get(i) == 128
					|| list.get(i) == 129 || list.get(i) == 130
					|| list.get(i) == 131 || list.get(i) == 132
					|| list.get(i) == 133 || list.get(i) == 145
					|| list.get(i) == 158 || list.get(i) == 183
					|| list.get(i) == 184) {
				float f = scoreDbUtil.getChangeAverageBlankWholeAnswer(
						list.get(i), currentTime); // 计算平均值

				scoreDbUtil.updateBlankTrueAnswer(list.get(i), f);
			}
			if (list.get(i) == 170 || list.get(i) == 171 || list.get(i) == 172
					|| list.get(i) == 173 || list.get(i) == 174) {
				float f = scoreDbUtil.getChangeAverageBlankRelateAnswer(
						list.get(i), currentTime);
				scoreDbUtil.updateBlankTrueAnswer(list.get(i), f);
			}

		}
		scoreDbUtil.closeConnection();
	}

	// 开始计算分数
	public static void setScore(int schoolId, String time, String currentTime)
			throws Exception {

		ArrayList<Float> arrayList = new ArrayList<Float>();
		ScoreDbUtil scoreDbUtil = new ScoreDbUtil();
		scoreDbUtil.openConnection();

		int i, j, k, x, y, m, n;

		float f_1, f_2, f_3, f_4;

		// //////////////////////开始统计分数
		ArrayList list1_firstIndexType = new ArrayList();
		ArrayList<Float> list1_firstIndexPercent = new ArrayList<Float>();
		list1_firstIndexType = scoreDbUtil.getFirstIndexType();
		list1_firstIndexPercent = scoreDbUtil.getFirstIndexPercent();

		float sum = 0, sum_1 = 0, sum_2 = 0, sum_3 = 0;

		for (i = 0; i < 5; i++) {
			sum_1 = 0;

			ArrayList list2_secondIndexId = new ArrayList();
			ArrayList<Float> list2_secondIndexPercent = new ArrayList<Float>();

			list2_secondIndexId = scoreDbUtil.getSecondIndexId(i + 1); // 一级指标对应的二级指标的id
			list2_secondIndexPercent = scoreDbUtil.getSecondIndexPercent(i + 1); // 二级指标权重

			for (j = 0; j < list2_secondIndexId.size(); j++) {

				sum_2 = 0;
				ArrayList list3_thirdIndexId = new ArrayList();
				ArrayList<Float> list3_thirdIndexPercent = new ArrayList<Float>();

				list3_thirdIndexId = scoreDbUtil
						.getThirdIndexId(list2_secondIndexId.get(j)); // 二级指标对应的三级指标
				list3_thirdIndexPercent = scoreDbUtil
						.getThirdIndexPercent(list2_secondIndexId.get(j)); // 三级指标权重

				for (k = 0; k < list3_thirdIndexId.size(); k++) { // 每存主键 都连续

					float sum_choice = 0, sum_blank = 0;
					sum_3 = 0;

					ArrayList<Integer> list4_choiceNumber = new ArrayList<Integer>();
					ArrayList<Integer> list5_blankNumber = new ArrayList<Integer>();

					list4_choiceNumber = scoreDbUtil
							.getChoiceNumberByThirdIndexId(list3_thirdIndexId
									.get(k)); // 三级标题对用的 题目号数组
					list5_blankNumber = scoreDbUtil
							.getBlankNumberByThirdIndexId(list3_thirdIndexId
									.get(k)); // 三级标题对用的 填空题数组

					for (x = 0; x < list5_blankNumber.size(); x++) { // 计算出填空题部分

						f_1 = scoreDbUtil
								.getBlankAnswerContentByQueId(list5_blankNumber
										.get(x)); // 取出标准答案
						f_2 = scoreDbUtil
								.getBlankAnswerPercentByQueId(list5_blankNumber
										.get(x)); // 取出百分比
						float sum_fig = 0;

						if (list5_blankNumber.get(x) == 167) {
							float f_169 = scoreDbUtil.getBlankValue(169,
									schoolId);
							float f_167 = scoreDbUtil.getBlankValue(167,
									schoolId);
							float s_167 = 0;
							if (f_167 != 0) {
								s_167 = (f_169 - f_167) / f_167;
							}
							if (f_1 != 0) {
								sum_fig = (s_167 / f_1) * 60;
								if (sum_fig > 100) {
									sum_fig = 100;
								}
								sum_blank = sum_blank + sum_fig * f_2;
							}
						}

						else if (list5_blankNumber.get(x) == 169) {
							float f_169 = scoreDbUtil.getBlankValue(169,
									schoolId);
							float f_168 = scoreDbUtil.getBlankValue(168,
									schoolId);
							float s_169 = 0;
							if (f_168 != 0) {
								s_169 = f_169 / f_168;
							}
							if (f_1 != 0) {
								sum_fig = (s_169 / f_1) * 60;
								if (sum_fig > 100) {
									sum_fig = 100;
								}
								sum_blank = sum_blank + sum_fig * f_2;

							}
							// System.out.println("  "+s_169+"  "+f_1+
							// "  "+f_2+"   ");
						}

						else if (list5_blankNumber.get(x) == 170
								|| list5_blankNumber.get(x) == 171
								|| list5_blankNumber.get(x) == 172
								|| list5_blankNumber.get(x) == 173
								|| list5_blankNumber.get(x) == 174) {
							f_3 = scoreDbUtil.getBlankValue(169, schoolId);
							f_4 = scoreDbUtil.getBlankValue(
									list5_blankNumber.get(x), schoolId);
							if ((f_3 != 0) && (f_1 != 0)) {
								sum_fig = (f_4 / f_3) / f_1 * 60;
								if (sum_fig > 100) {
									sum_fig = 100;
								}
								sum_blank = sum_blank + sum_fig * f_2;
							}
							// System.out.println("  "+f_4+"  "+f_3+"   "+f_1+
							// "  "+f_2+"   ");
						}

						else if (list5_blankNumber.get(x) == 176
								|| list5_blankNumber.get(x) == 177) {
							f_3 = scoreDbUtil.getBlankValue(175, schoolId);
							f_4 = scoreDbUtil.getBlankValue(
									list5_blankNumber.get(x), schoolId);
							if ((f_3 != 0) && (f_1 != 0)) {
								sum_fig = (f_4 / f_3) / f_1 * 60;
								if (sum_fig > 100) {
									sum_fig = 100;
								}
								// System.out.println("  "+f_4+"  "+f_3+"   "+f_1+
								// "  "+f_2+"   ");
								sum_blank = sum_blank + sum_fig * f_2;
							}
						}

						else if (list5_blankNumber.get(x) == 179
								|| list5_blankNumber.get(x) == 180
								|| list5_blankNumber.get(x) == 181) {

							f_3 = scoreDbUtil.getBlankValue(178, schoolId);
							f_4 = scoreDbUtil.getBlankValue(
									list5_blankNumber.get(x), schoolId);
							if ((f_3 != 0) && (f_1 != 0)) {
								sum_fig = (f_4 / f_3) / f_1 * 60;
								if (sum_fig > 100) {
									sum_fig = 100;
								}
								sum_blank = sum_blank + sum_fig * f_2;
							}
						} else if (list5_blankNumber.get(x) == 138
								|| list5_blankNumber.get(x) == 139
								|| list5_blankNumber.get(x) == 140) {
							float f_138 = scoreDbUtil.getBlankValue(138,
									schoolId);
							float f_139 = scoreDbUtil.getBlankValue(139,
									schoolId);
							float f_140 = scoreDbUtil.getBlankValue(140,
									schoolId);
							if (list5_blankNumber.get(x) == 138) {
								float s_138 = 0;
								if ((f_138 + f_139 + f_140) != 0) {
									s_138 = f_138 / (f_138 + f_139 + f_140);
								}
								if (f_1 != 0) {
									sum_fig = (s_138 / f_1) * 60;
									if (sum_fig > 100) {
										sum_fig = 100;
									}
									sum_blank = sum_blank + sum_fig * f_2;

								}
							}
							if (list5_blankNumber.get(x) == 139) {
								float s_139 = 0;
								if ((f_138 + f_139 + f_140) != 0) {
									s_139 = f_139 / (f_138 + f_139 + f_140);
								}
								if (f_1 != 0) {
									sum_fig = (s_139 / f_1) * 60;
									if (sum_fig > 100) {
										sum_fig = 100;
									}
									sum_blank = sum_blank + sum_fig * f_2;
								}
							}
							if (list5_blankNumber.get(x) == 140) {
								float s_140 = 0;
								if ((f_138 + f_139 + f_140) != 0) {
									s_140 = f_140 / (f_138 + f_139 + f_140);
								}
								if (f_1 != 0) {
									sum_fig = (s_140 / f_1) * 60;
									if (sum_fig > 100) {
										sum_fig = 100;
									}
									sum_blank = sum_blank + sum_fig * f_2;

								}
								// System.out.println("  "+s_140+"  "+f_1+"   ");
							}
						}

						else if (list5_blankNumber.get(x) == 135
								|| list5_blankNumber.get(x) == 136
								|| list5_blankNumber.get(x) == 137) {
							float f_135 = scoreDbUtil.getBlankValue(135,
									schoolId);
							float f_136 = scoreDbUtil.getBlankValue(136,
									schoolId);
							float f_137 = scoreDbUtil.getBlankValue(137,
									schoolId);
							if (list5_blankNumber.get(x) == 135) {
								float s_135 = 0;
								if ((f_135 + f_136 + f_137) != 0) {
									s_135 = f_135 / (f_135 + f_136 + f_137);
								}
								if (f_1 != 0) {
									sum_fig = (s_135 / f_1) * 60;
									if (sum_fig > 100) {
										sum_fig = 100;
									}
									sum_blank = sum_blank + sum_fig * f_2;
								}
								// System.out.println("  "+s_135+"  "+f_1+"   ");
							}
							if (list5_blankNumber.get(x) == 136) {
								float s_136 = 0;
								if ((f_135 + f_136 + f_137) != 0) {
									s_136 = f_136 / (f_135 + f_136 + f_137);
								}
								if (f_1 != 0) {
									sum_fig = (s_136 / f_1) * 60;
									if (sum_fig > 100) {
										sum_fig = 100;
									}
									sum_blank = sum_blank + sum_fig * f_2;
								}
							}
							if (list5_blankNumber.get(x) == 137) {
								float s_137 = 0;
								if ((f_135 + f_136 + f_137) != 0) {
									s_137 = f_137 / (f_135 + f_136 + f_137);
								}
								if (f_1 != 0) {
									sum_fig = (s_137 / f_1) * 60;
									if (sum_fig > 100) {
										sum_fig = 100;
									}
									sum_blank = sum_blank + sum_fig * f_2;
								}
								// System.out.println("  "+s_137+"  "+f_1+"   ");
							}
						} else if (list5_blankNumber.get(x) == 129) {
							f_3 = scoreDbUtil.getBlankValue(
									list5_blankNumber.get(x), schoolId);
							sum_fig = 100;
							sum_blank = sum_blank + 100 * f_2;
							// System.out.println("  "+f_2+"   ");
						} else {
							f_3 = scoreDbUtil.getBlankValue(
									list5_blankNumber.get(x), schoolId);
							if (f_1 != 0) {
								sum_fig = (f_3 / f_1) * 60;
								if (sum_fig > 100) {
									sum_fig = 100;
								}
								sum_blank = sum_blank + sum_fig * f_2;
							}
							// System.out.println(f_3+"  "+f_1+" "+f_2);
							// System.out.println("  "+sum_fig);
						}

						scoreDbUtil.insertQueScore(list5_blankNumber.get(x),
								schoolId, sum_fig, time);
					}

					// /选择题部分计算
					for (y = 0; y < list4_choiceNumber.size(); y++) {

						// System.out.print(list4_choiceNumber.get(y)+"   ");
						float sum_fig = 0;
						f_2 = scoreDbUtil
								.getChoiceAnswerPercentByQueId(list4_choiceNumber
										.get(y));

						if (list4_choiceNumber.get(y) == 160) {

							ArrayList<Integer> list_choiceId = new ArrayList<Integer>();
							list_choiceId = scoreDbUtil.getChoiceAnswerId(160,
									schoolId);

							for (m = 0; m < list_choiceId.size(); m++) {
								if (list_choiceId.get(m) != 295) {
									if (list_choiceId.get(m) == 296) {
										sum_choice = sum_choice + 100 * f_2;
										sum_fig = 100;
										// System.out.println("  "+f_2+"   ");

									} else if (list_choiceId.get(m) == 297) {
										sum_choice = sum_choice + 75 * f_2;
										sum_fig = 75;
									} else if (list_choiceId.get(m) == 298) {
										sum_choice = sum_choice + 50 * f_2;
										sum_fig = 50;
										// System.out.println("  "+f_2+"   ");

									} else {
										sum_choice = sum_choice + 25 * f_2;
										sum_fig = 25;
										// System.out.println("  "+f_2+"   ");
									}
								}

							}
						} else if (list4_choiceNumber.get(y) == 164) {

							ArrayList<Integer> list_choiceId = new ArrayList<Integer>();
							list_choiceId = scoreDbUtil.getChoiceAnswerId(164,
									schoolId);
							for (m = 0; m < list_choiceId.size(); m++) {
								if (list_choiceId.get(m) == 326) {
									sum_choice = sum_choice + 100 * f_2;
									sum_fig = 100;
									// System.out.println("  "+f_2+"   ");

								}
								if (list_choiceId.get(m) == 327) {
									sum_choice = sum_choice + 50 * f_2;
									sum_fig = 50;
									// System.out.println("  "+f_2+"   ");
								}
							}
						} else if (list4_choiceNumber.get(y) == 152
								|| list4_choiceNumber.get(y) == 153
								|| list4_choiceNumber.get(y) == 154
								|| list4_choiceNumber.get(y) == 155) {
							if (list4_choiceNumber.get(y) == 152) {
								ArrayList<Integer> list_choiceId = new ArrayList<Integer>();
								list_choiceId = scoreDbUtil.getChoiceAnswerId(
										152, schoolId);
								for (m = 0; m < list_choiceId.size(); m++) {
									if (list_choiceId.get(m) == 277) {
										sum_choice = sum_choice + 100 * f_2;
										sum_fig = 100;
										// System.out.println("  "+f_2+"   ");
									}
								}
							}

							if (list4_choiceNumber.get(y) == 153) {
								ArrayList<Integer> list_choiceId = new ArrayList<Integer>();
								list_choiceId = scoreDbUtil.getChoiceAnswerId(
										153, schoolId);
								for (m = 0; m < list_choiceId.size(); m++) {
									if (list_choiceId.get(m) == 279) {
										sum_choice = sum_choice + 100 * f_2;
										sum_fig = 100;
										// System.out.println("  "+f_2+"   ");
									}
								}
							}

							if (list4_choiceNumber.get(y) == 154) {
								ArrayList<Integer> list_choiceId = new ArrayList<Integer>();
								list_choiceId = scoreDbUtil.getChoiceAnswerId(
										154, schoolId);
								for (m = 0; m < list_choiceId.size(); m++) {
									if (list_choiceId.get(m) == 281) {
										sum_choice = sum_choice + 100 * f_2;
										sum_fig = 100;
										// System.out.println("  "+f_2+"   ");
									}
								}
							}

							if (list4_choiceNumber.get(y) == 155) {
								ArrayList<Integer> list_choiceId = new ArrayList<Integer>();
								list_choiceId = scoreDbUtil.getChoiceAnswerId(
										155, schoolId);
								for (m = 0; m < list_choiceId.size(); m++) {
									if (list_choiceId.get(m) == 283) {
										sum_choice = sum_choice + 100 * f_2;
										sum_fig = 100;
										// System.out.println("  "+f_2+"   ");
									}
								}
							}
						} else {

							ArrayList list_choiceAnswer = new ArrayList();
							list_choiceAnswer = scoreDbUtil.getChoiceAnswer(   //选择题答案
									list4_choiceNumber.get(y), schoolId);
							String str = scoreDbUtil
									.getChoiceTrueAnswer(list4_choiceNumber.get(y));    //标准答案
							String[] str_1 = str.split(",");
							int num = 0;
							int t = str_1.length;
							for (m = 0; m < t; m++) {
								for (n = 0; n < list_choiceAnswer.size(); n++) {
									if (str_1[m].equals(list_choiceAnswer
											.get(n))) {
										num++;
									}
								}
							}
							if (t != 0) {
								sum_choice = sum_choice
										+ (float) (num * 1.0 / t * 100 * f_2); // 给多项选择题，每一项都乘100
								sum_fig = (float) (num * 1.0 / t * 100);
								// System.out.println("  "+num+"  "+t+"  "+f_2+"   ");
							}
						}

						scoreDbUtil.insertQueScore(list4_choiceNumber.get(y),
								schoolId, sum_fig, time);
					}

					// //////////////////////////////
					sum_3 = sum_choice + sum_blank;
					sum_2 = sum_2 + sum_3 * list3_thirdIndexPercent.get(k);
					scoreDbUtil.insertSchoolScore(schoolId, sum_3, 3, time);

				}
				sum_1 = sum_1 + sum_2 * list2_secondIndexPercent.get(j); // 三只区域值
				scoreDbUtil.insertSchoolScore(schoolId, sum_2, 2, time);

			}

			sum = sum + sum_1 * (float) list1_firstIndexPercent.get(i); // 二级区域值
			scoreDbUtil.insertSchoolScore(schoolId, sum_1, 1, time);
			arrayList.add(sum_1);
		}
		scoreDbUtil.insertSchoolScore(schoolId, sum, 0, time);
		scoreDbUtil.closeConnection();
	}

	// 为各个区县算出一级指标得分，存到数据库tareascore
	public static void setAreaScore(String currentTime, String time)
			throws Exception {
		ScoreDbUtil scoreDbUtil = new ScoreDbUtil();
		scoreDbUtil.openConnection();
		ArrayList listAreaName = new ArrayList();
		listAreaName = scoreDbUtil.getArean();
		for (int i = 0; i < listAreaName.size(); i++) {
			if (!(listAreaName.get(i).toString()).equals("襄阳市")) {
				String schoolArean = listAreaName.get(i).toString();
				float subNum = scoreDbUtil.getsubSchoolnum(schoolArean,currentTime);// 得到一个区域学校提交数量
				ArrayList listId = new ArrayList();

				listId = scoreDbUtil.getschoolId(schoolArean, currentTime); // 获取一个区域的学校schoolId
				float sum = 0, sum1 = 0, sum2 = 0, sum3 = 0, sum4 = 0, sum5 = 0, aversum1 = 0, aversum2 = 0, aversum3 = 0, aversum4 = 0, aversum5 = 0, aversum = 0;

				for (int j = 0; j < listId.size(); j++) {
					ArrayList list = new ArrayList();
					list = scoreDbUtil.getschoolScore((int) listId.get(j));
					if (list.size() > 0) {
						sum1 = sum1 + (float) list.get(0); // 基础设施
						sum2 = sum2 + (float) list.get(1); // 数字资源
						sum3 = sum3 + (float) list.get(2); // 应用服务
						sum4 = sum4 + (float) list.get(3); // 应用效能
						sum5 = sum5 + (float) list.get(4); // 机制保障
						sum = sum + (float) list.get(5); // 总得分
					}
				}
				if (subNum != 0) {
					aversum1 = Float.parseFloat(new java.text.DecimalFormat(
							"#.00").format(sum1 / subNum)); // 基础设施平均得分
					aversum2 = Float.parseFloat(new java.text.DecimalFormat(
							"#.00").format(sum2 / subNum)); // 数字资源平均得分
					aversum3 = Float.parseFloat(new java.text.DecimalFormat(
							"#.00").format(sum3 / subNum)); // 应用服务平均得分
					aversum4 = Float.parseFloat(new java.text.DecimalFormat(
							"#.00").format(sum4 / subNum)); // 应用效能平均得分
					aversum5 = Float.parseFloat(new java.text.DecimalFormat(
							"#.00").format(sum5 / subNum)); // 机制保障平均得分
					aversum = Float.parseFloat(new java.text.DecimalFormat(
							"#.00").format(sum / subNum)); // 总得分平均得分
				} else {
					aversum1 = 0;
					aversum2 = 0;
					aversum3 = 0;
					aversum4 = 0;
					aversum5 = 0;
					aversum = 0;
				}
				
				scoreDbUtil.insertAreaScore(schoolArean, aversum1, aversum2, aversum3, aversum4, aversum5, aversum, time);
			}
		}

		scoreDbUtil.closeConnection();
	}
	
	//为各区县计算二级指标得分，存到tseconddareascore
	public void setSecondAreanScore(String currentTime,String time) throws Exception {
		ScoreDbUtil scoreDbUtil = new ScoreDbUtil();
		scoreDbUtil.openConnection();
		ArrayList<String> listAreaName = new ArrayList<String>();
		listAreaName = scoreDbUtil.getArean();
		for(int m=0;m<listAreaName.size();m++){
			if (!(listAreaName.get(m).toString()).equals("襄阳市")){
				String areaName = listAreaName.get(m).toString();
				ArrayList<Integer> arrayList = new ArrayList<Integer>(); // 记录每个区多少学校
				arrayList = scoreDbUtil.getAllSchoolIdNumberBySchoolArean(listAreaName.get(m).toString(), currentTime);
				float a[] = new float[17]; // 存储二级指标的个数
				for (int k = 0; k < a.length; k++) {
					a[k] = 0;
				}

				for (int i = 0; i < arrayList.size(); i++) {
					ArrayList<Float> arrayListScore = new ArrayList<Float>(); // 记录每个学校所有的二级指标得分
					arrayListScore = scoreDbUtil.getAllSecondIndexScore(arrayList.get(i)); // 这个长度也是17个

					for (int j = 0; j < arrayListScore.size(); j++) {
						a[j] = a[j] + arrayListScore.get(j);
					}
				}

				for (int k = 0; k < a.length; k++) {
					if (arrayList.size() != 0) {
						a[k] = Float.parseFloat(new java.text.DecimalFormat("#.##").format(a[k] / arrayList.size()));
					} else {
						a[k] = 0;
					}
				}
				
				//执行插入操作
				for(int n=0;n<a.length;n++ ){
					scoreDbUtil.insertSecondAreaScore(a[n], areaName, time);
				}
			}
			
		}
		
		scoreDbUtil.closeConnection();
	}
	
	//为各区县计算三级指标得分，存到tthirdareascore
	public void setThirdAreanScore(String currentTime,String time) throws Exception {
		ScoreDbUtil scoreDbUtil = new ScoreDbUtil();
		scoreDbUtil.openConnection();
		ArrayList<String> listAreaName = new ArrayList<String>();
		listAreaName = scoreDbUtil.getArean();
		for(int m=0;m<listAreaName.size();m++){
			if(!listAreaName.get(m).toString().equals("襄阳市")){
				String areaName = listAreaName.get(m).toString();
				ArrayList<Integer> arrayList = new ArrayList<Integer>(); // 记录每个区多少学校
				arrayList = scoreDbUtil.getAllSchoolIdNumberBySchoolArean(areaName, currentTime);

				float a[] = new float[33]; // 存储三级指标的个数
				for (int k = 0; k < a.length; k++) {
					a[k] = 0;
				}

				for (int i = 0; i < arrayList.size(); i++) {

					ArrayList<Float> arrayList2 = new ArrayList<Float>(); // 记录每个学校所有的三级指标得分
					arrayList2 = scoreDbUtil.getAllThirdIndexScore(arrayList
							.get(i)); // 这个长度也是33个
					for (int j = 0; j < arrayList2.size(); j++) {
						a[j] = a[j] + arrayList2.get(j);
					}
				}

				for (int k = 0; k < a.length; k++) {
					if (arrayList.size() != 0) {
						a[k] = Float.parseFloat(new java.text.DecimalFormat("#.00")
								.format(a[k] / arrayList.size()));
					} else {
						a[k] = 0;
					}
				}
				
				//执行插入操作
				for(int n=0;n<a.length;n++ ){
					scoreDbUtil.insertThirdAreaScore(a[n], areaName, time);
				}
			}
		}
		
		scoreDbUtil.closeConnection();
	}

}
