package nercel.javaweb.schoolregister;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.codec.digest.DigestUtils;

public class RegisterService {
	
	public String checkUserName(String userName) throws ClassNotFoundException, SQLException, IOException {
		RegisterDb registerDb = new RegisterDb();
		String flag = registerDb.checkUserName(userName);
		return flag;
	}
	
	public String checkIsReg(int area_id, String schoolName) throws ClassNotFoundException, SQLException, IOException {
		
		RegisterDb registerDb = new RegisterDb();
		String flag = registerDb.checkIsReg(area_id, schoolName);
		return flag;
	}
	
	public String register(int area_id,String schoolName,String userName,String pwd) throws ClassNotFoundException, SQLException, IOException {
		RegisterDb registerDb = new RegisterDb();
		String pw_1 = DigestUtils.md5Hex(pwd);
		String pw_2 = DigestUtils.md5Hex(pw_1);
		String reString = registerDb.register(area_id, schoolName, userName, pw_2);
		return reString;
	}
	
	public ArrayList getSchoolName(int area_id) throws ClassNotFoundException, SQLException, IOException {
		RegisterDb registerDb = new RegisterDb();
		ArrayList list = registerDb.getSchoolName(area_id);
		return list;
	}

}
