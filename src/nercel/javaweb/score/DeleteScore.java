package nercel.javaweb.score;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class DeleteScore {
	
	public static void deleteScore(String currentTime) throws Exception{
		
		ScoreDbUtil scoreDbUtil = new ScoreDbUtil();
		scoreDbUtil.openConnection();
		
		scoreDbUtil.delQueScore(currentTime);
		scoreDbUtil.delSchoolScore(currentTime);
		scoreDbUtil.delAreaScore(currentTime);
		scoreDbUtil.delThirdAreaScore(currentTime);
		scoreDbUtil.delSecondAreaScore(currentTime);
		scoreDbUtil.closeConnection();
		
	}
	
}
