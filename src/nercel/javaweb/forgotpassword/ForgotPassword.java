
package nercel.javaweb.forgotpassword;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.UUID;

import nercel.javaweb.common.ConvertSessionName;
import nercel.javaweb.common.FormatCheckUtils;

import org.apache.commons.codec.digest.DigestUtils;

public class ForgotPassword {

    private String sid;
	private Object key;
    
    public String sendmail(String loginName,String basePath) throws Exception {
    	ForgotPasswordDbUtil fpDb = new ForgotPasswordDbUtil();
    	ConvertSessionName csn = new ConvertSessionName();
    	fpDb.openConnection();
    	String userName = "";
    	if(FormatCheckUtils.isPhoneLegal(loginName)||FormatCheckUtils.isfixedPhone(loginName)){
			try {
				csn.openConnection();
				userName = csn.getNameBytel(loginName);
				csn.closeConnection();
			} catch (SQLException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(FormatCheckUtils.checkEmaile(loginName)){
			try {
				csn.openConnection();
				userName = csn.getNameByEmail(loginName);
				csn.closeConnection();
			} catch (SQLException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			userName = loginName;
		}
    	String state = "";
    	if(!userName.equals("")){
    		String email = fpDb.getEmail(userName);
        	boolean flag = false;
            try {
                //通过用户名和邮箱判断有无此用户
            	flag = fpDb.judgeUser(userName, email);
                if (flag) {
                    Mail mail = new Mail();
                    String secretKey = UUID.randomUUID().toString(); // 密钥
                    Timestamp outDate = new Timestamp(System.currentTimeMillis() + 30 * 60 * 1000);// 30分钟后过期
                    //long date = outDate.getTime() / 1000 * 1000;// 忽略毫秒数  mySql 取出时间是忽略毫秒数的
                    SimpleDateFormat dateformatAll= new SimpleDateFormat("yyyy-MM-dd");
                    String date = dateformatAll.format(outDate);
                    //存到数据库
                    fpDb.updateKeyAndTime(secretKey,outDate+"",userName);
                    String key = userName+"$"+ date +"$"+secretKey;
                    System.out.println("key>>>"+key);
                    String digitalSignature = DigestUtils.md5Hex(DigestUtils.md5Hex(key));// 数字签名

                    String resetPassHref = basePath + "xyseis/checkLink.html?sid="
                            + digitalSignature +"&userName="+userName;
                    String emailContent = "请勿回复本邮件.点击下面的链接,重设密码<br/><a href="
                            + resetPassHref + " target='_BLANK'>" + resetPassHref
                            + "</a>  或者    <a href=" + resetPassHref
                            + " target='_BLANK'>点击我重新设置密码</a>"
                            + "<br/>tips:本邮件超过30分钟，链接将会失效，需要重新申请'找回密码'。";

                    mail.setTo(email);
                    mail.setFrom("yanzhao155@163.com");// 你的邮箱
                    mail.setHost("smtp.163.com");
                    mail.setUsername("15565279572@163.com");// 用户
                    mail.setPassword("06121993yg");// 密码
                    mail.setSubject("找回您的账户密码");
                    mail.setContent(emailContent);
                    if(mail.sendMail()){
                    	System.out.println(" 发送成功");
                    	state = "successSend";
                    }
                 
                }else{
                	state = "nouser";
                } 
            } catch (Exception e) {
                // TODO: handle exception 
                e.printStackTrace();
            }
            fpDb.closeConnection();
    	}else{
    		state = "nouser";
    	}
    	
    	return state;
    }

    public String checkResetLink(String ssid,String userName) throws Exception {
        ForgotPasswordDbUtil fpDb = new ForgotPasswordDbUtil();
    	fpDb.openConnection();
    	boolean flag = false;
        if (ssid.equals("")  || userName.equals("")) {
            return "linkIncomplete";
        }

        flag = fpDb.judgeUserByuserName(userName);
        if (flag) {
            Timestamp outDate = Timestamp.valueOf(fpDb.getoutDate(userName));
            if(outDate.getTime() <= System.currentTimeMillis()){ //表示已经过期
                 System.out.println("时间超时");
                 return "linkoutTime";
            }
            
             SimpleDateFormat dateformatAll= new SimpleDateFormat("yyyy-MM-dd");
             String date = dateformatAll.format(outDate);
             
             String key = userName+"$"+ date +"$"+fpDb.getValidatacode(userName);//数字签名
             System.out.println("key:"+key);
             String digitalSignature = DigestUtils.md5Hex(DigestUtils.md5Hex(key));// 数字签名
             
              if(!digitalSignature.equals(ssid)) {
                    System.out.println("标示不正确");
                    return "linkerror";
              }else {
            	System.out.println("链接有效");
                return "success";
            }
        }else {
            System.out.println("用户不存在");
            return "userNotExist";
        }
    }
    
    public boolean resetPassword(String passWord,String userName) throws Exception{
    	boolean flag = false;
    	ForgotPasswordDbUtil fpDb = new ForgotPasswordDbUtil();
    	fpDb.openConnection();
    	flag = fpDb.updatePassWord(passWord, userName);
    	
    	fpDb.closeConnection();
    	return flag;
    }

}