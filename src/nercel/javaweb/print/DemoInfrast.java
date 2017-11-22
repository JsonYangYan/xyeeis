package nercel.javaweb.print;

import java.util.ArrayList;
import java.util.HashMap;

import nercel.javaweb.allassessment.AssessmentDbUtil;

public class DemoInfrast {
	/**
	    * 计算教师、学生终端比例
	    * @param fig
	    * @return
	    * @throws Exception
	    */
		public float[] getTeminalScore( int fig) throws Exception,
				ClassNotFoundException, Exception {
			PaperDbUtil peDbUtil = new PaperDbUtil();
			peDbUtil.openConnection();
			ArrayList listResult = new ArrayList();
			
			float strResult[] =  new float[4];
			
			int quePercent[] = { 0, 0, 0, 0 };
			float queAvgScore[] = { 0, 0, 0, 0 };
			int queId[] = { 135, 136, 137 };
			if (fig == 1) {
				queId[0] = 138;
				queId[1] = 139;
				queId[2] = 140;
			}

			//计算……
			float FRISTLEVEL = 20, SECONDLEVEL = 60, THIRDLEVEL = 90;
			int totalSchool = peDbUtil.getAllSchoolIdNumber().size();
			for (int j = 0; j < 3; j++) {
				ArrayList<Float> listTemp = null;
				listTemp = peDbUtil.getQueScoreByQueId(queId[j]);
				for (int i = 0; i < listTemp.size(); i++) {

					if (listTemp.get(i) <= FRISTLEVEL && listTemp.get(i) >= 0) {
						quePercent[0]++;
						queAvgScore[0] = queAvgScore[0] + listTemp.get(i);
					} else if (listTemp.get(i) <= SECONDLEVEL) {
						quePercent[1]++;
						queAvgScore[1] = queAvgScore[1] + listTemp.get(i);
					} else if (listTemp.get(i) <= THIRDLEVEL) {
						quePercent[2]++;
						queAvgScore[2] = queAvgScore[2] + listTemp.get(i);
					} else {
						quePercent[3]++;
						queAvgScore[3] = queAvgScore[3] + listTemp.get(i);
					}
				}
			}

			//……
			if (totalSchool != 0) {
				for (int k = 0; k < 4; k++) {
					strResult[k] = Float.parseFloat(new java.text.DecimalFormat("#.00").format(quePercent[k] * 100.0 / totalSchool));
				}
			} else {
				for (int k = 0; k < 4; k++) {
					strResult[k] = Float.parseFloat(new java.text.DecimalFormat("#.00").format(0));

				}

			}
			
			peDbUtil.closeConnection();
			return strResult;
		}
	
	
	
}
