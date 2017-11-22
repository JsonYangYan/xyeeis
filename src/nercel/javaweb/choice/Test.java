package nercel.javaweb.choice;

import java.sql.ResultSet;

import nercel.javaweb.dbpool.MyJDBC;

public class Test {
	
	public static void main(String[] args) throws Exception 
	{
		query();
	}
	public static void query() throws Exception{
		String name = "第五";
		String sql = "select * from tschoolinfor where schoolName like ?";
		ResultSet rs = MyJDBC.query(sql,"%"+name+"%");
		while(rs.next()){
			System.out.println(rs.getInt("autoId"));
		}
	}
		

}
