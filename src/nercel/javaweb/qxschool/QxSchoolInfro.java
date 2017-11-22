package nercel.javaweb.qxschool;

import java.util.ArrayList;
import java.util.HashMap;

import nercel.javaweb.allassessment.AssessmentDbUtil;
import nercel.javaweb.school.SchoolDbUtil;

public class QxSchoolInfro {
	/**
	 * 计算襄阳市一级指标
	 * @return
	 * @throws Exception
	 */
	public HashMap getFirstIndexScore(String schoolName, int fig,String currentTime) throws Exception {
		
		
		QxSchoolDbUtil schoolDbUtil = new QxSchoolDbUtil();
		schoolDbUtil.openConnection();

		ArrayList<Float> arr = new ArrayList<Float>();
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		float arrScore[] = new float [5];
		HashMap hashMap = new HashMap();
		
        if(fig == 1) {  //取出整个学校的id
        	//arrayList = schoolDbUtil.getAllSchoolIdNumber(currentTime);
        	ArrayList<Float> listScore = new ArrayList<Float>();
        	listScore = schoolDbUtil.getCityScore(currentTime);
        	if(listScore.size()!=0){
        		hashMap.put("name", "襄阳市");
            	hashMap.put("data", listScore);  
        	}else{
        		hashMap.put("name", "襄阳市");
            	hashMap.put("data", arrScore);
        	}
        	      	
        }
        if( fig == 2) {  //取出区域学校id
        	String schoolArean = schoolDbUtil.getSchoolArean(schoolName, currentTime);  //通过学校名字取得所属区域
        	//arrayList = schoolDbUtil.getSchoolAreanNumber(schoolArean, currentTime);   //通过所属区域获得区域内学校
        	ArrayList<Float> listScore = new ArrayList<Float>();
        	listScore = schoolDbUtil.getAreanScore(schoolArean, currentTime);
        	if(listScore.size()!=0){
        		hashMap.put("name", schoolArean);
            	hashMap.put("data", listScore);
        	}else{
        		hashMap.put("name", schoolArean);
            	hashMap.put("data", arrScore);
        	}
        	
        }
        if(fig==3) {    //取出整个学校的id
        	ArrayList<Float> listScore = new ArrayList<Float>();
        	arrayList = schoolDbUtil.getEachSchoolIdNumber(schoolName, currentTime);
        	listScore= schoolDbUtil.getAllFirstIndexScore(arrayList.get(0));
        	if(listScore.size()!=0){
        		hashMap.put("name", schoolName);
            	hashMap.put("data", listScore);
        	}else{
        		hashMap.put("name", schoolName);
            	hashMap.put("data", arrScore);
        	}
        	       	
        }
		/*float sum_1 = 0, sum_2 = 0, sum_3 = 0, sum_4 = 0, sum_5 = 0;
        
		for (Integer a : arrayList) {
			ArrayList<Float> list = new ArrayList<Float>();
			list = schoolDbUtil.getAllFirstIndexScore(a);
			if(list.size()!=0) { 
				sum_1 = sum_1 + list.get(0);
				sum_2 = sum_2 + list.get(1);
				sum_3 = sum_3 + list.get(2);
				sum_4 = sum_4 + list.get(3);
				sum_5 = sum_5 + list.get(4);
			}
		}

		int num = arrayList.size();
		if(num !=0) {
			sum_1 = sum_1 / num;
			sum_2 = sum_2 / num;
			sum_3 = sum_3 / num;
			sum_4 = sum_4 / num;
			sum_5 = sum_5 / num;
		}
		else {
			sum_1 = 0;
			sum_2 = 0;
			sum_3 = 0;
			sum_4 = 0;
			sum_5 = 0;
		}
		arr.add(sum_1);
		arr.add(sum_2);
		arr.add(sum_3);
		arr.add(sum_4);
		arr.add(sum_5);
        
		hashMap.put("data", arr);*/
		return hashMap;
	}
	
}
