package nercel.javaweb.json;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nercel.javaweb.common.ConvertSessionName;
import nercel.javaweb.common.FormatCheckUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SchoolInforServlet extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 * This method is called when a form has its tag value method equals to get.
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String type = request.getParameter("operation");
		String school_type = request.getParameter("school_type");
		String school_type_ = request.getParameter("school_type_");//等进来的学校类型 tp教学点，normal正常学校
		
		if(school_type_ == null){
			school_type_ = "normal";
		}
		//从session中获取用户的名字
		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("name");
		
		AnswerDbUtil awDbUtil = new AnswerDbUtil();
		try {
			awDbUtil.openConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Date date = new Date();
		SimpleDateFormat Dateformat = new SimpleDateFormat("yyyy-MM-dd"); // yyyy-MM-dd
		String time = Dateformat.format(date).toString();
		String[] strDate = time.split("-");
		String currentTime = strDate[0] + "-" + (strDate[1]);

		// 用户登录后显示用户相应的区域和所在学校
		// @吴磊  这部分主要显示的是用户登陆注册页面 显示出基本的信息
		// 已核对
		if (type.equals("userinfo")) {
			ArrayList listTemp = null;
			try {
				listTemp = awDbUtil.getUserInfor(userName);
			} catch (Exception e) {
				e.printStackTrace();
			}
			JSONArray jsonArray = new JSONArray();
			String json = jsonArray.fromObject(listTemp).toString();
			out.print(json);
		}

		// 增加信息,已经判断用户不存在
		if (type.equals("addpaper")) {

			// 获取前面传的值
			String schoolArean = request.getParameter("schoolArean");
			String schoolName = request.getParameter("schoolName");
			String schoolType = request.getParameter("school_type");

			String strTeacherNumber = request.getParameter("teacherNumber");
			String strStudentNumber = request.getParameter("studentNumber");
			String strClassNumber = request.getParameter("classNumber");

			String schoolTown = request.getParameter("schoolTown");
			String personName = request.getParameter("personName");
			String telPhone = request.getParameter("telPhone");
			String eMail = request.getParameter("eMail");

				// 类型转换
				int teacherNumber = Integer.parseInt(strTeacherNumber);
				int studentNumber = Integer.parseInt(strStudentNumber);
				int classNumber = Integer.parseInt(strClassNumber);

				// 插入最新记录
				boolean flag = false;
				try {
					flag = awDbUtil.addNewSchoolInfor(schoolArean, schoolName,
							schoolType, teacherNumber, studentNumber,
							schoolTown, personName, telPhone, eMail,
							classNumber, userName, time,school_type_);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (flag) {
					out.print("ok"); // 第一次登陆成功
				} else {
					out.print("no");
				}
			/*}*/

		}

		// ///////////////////////////////////////////////////////
		// welcome页面显示已经登录过用户的信息
		if (type.equals("edit_display")) {

			// 用户已经登陆过，判断该月用户是否已经登陆
			int count = 0;
			try {
				count = awDbUtil.getSchoolRecordDateByMonth(userName,
						currentTime,school_type_).size();
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			ArrayList listTemp = null;
			if (count == 0) {

				// 在tchoolInofor表中插入一条新记录
				try {
					listTemp = awDbUtil.getNewContent(userName,school_type_);
				} catch (Exception e) {
					e.printStackTrace();
				}

				// 记录未插入新记录之前的学校id
				int preSchoolId = 0;
				try {
					preSchoolId = awDbUtil.getSchoolIdPreMonth(userName,school_type_);
				} catch (Exception e2) {
					e2.printStackTrace();
				}

				// 每个月用户登录竟来
				String schoolArean = (String) listTemp.get(4);
				String schoolName = (String) listTemp.get(2);
				String schoolType = (String) listTemp.get(3);
				int teacherNumber = (int) listTemp.get(7);
				int studentNumber = (int) listTemp.get(8);
				String schoolTown = (String) listTemp.get(5);
				String personName = (String) listTemp.get(9);
				String telPhone = (String) listTemp.get(10);
				String eMail = (String) listTemp.get(11);
				int classNumber = (int) listTemp.get(6);
				String username = (String) listTemp.get(1);

				try {
					awDbUtil.addNewSchoolInfor(schoolArean, schoolName,
							schoolType, teacherNumber, studentNumber,
							schoolTown, personName, telPhone, eMail,
							classNumber, username, time,school_type_);
				} catch (Exception e) {
					e.printStackTrace();
				}

				// 将上个月的答案复制一份，作为这个月的答案的基准值
				PaperResults paperResult = new PaperResults();
				try {
					paperResult.copyAndInsert(userName, time, preSchoolId,school_type_);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}

			try {
				listTemp = awDbUtil.editsearchContent(userName, currentTime,school_type_);
			} catch (Exception e) {
				e.printStackTrace();
			}

			String json = JSONArray.fromObject(listTemp).toString();
			out.print(json);
		}

		// 更新welcome页面信息
		if (type.equals("edit")) {

			String schoolArean = request.getParameter("schoolArean");
			String schoolName = request.getParameter("schoolName");
			String schoolType = request.getParameter("schoolType");

			String strTeacherNumber = request.getParameter("teacherNumber");
			String strStudentNumber = request.getParameter("studentNumber");
			String strClassNumber = request.getParameter("classNumber");

			String schoolTown = request.getParameter("schoolTown");
			String personName = request.getParameter("personName");
			String telPhone = request.getParameter("telPhone");
			String eMail = request.getParameter("eMail");


			int studentNumber = Integer.parseInt(strStudentNumber);
			int teacherNumber = Integer.parseInt(strTeacherNumber);
			int classNumber = Integer.parseInt(strClassNumber);

			boolean blfig = false;
			try {
				blfig = awDbUtil.editSchoolInfor(schoolArean, schoolName,
						schoolType, teacherNumber, studentNumber,
						schoolTown, personName, telPhone, eMail,
						classNumber, userName, currentTime);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (blfig) {
				out.print("ok");
			} else {
				out.print("no");
			}
		}

		// 判断用户是否已经提交
		if (type.equals("judge")) {
			int state = 0;
			try {
				state = awDbUtil.getSchoolState(userName, currentTime);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (state == 1) {

				out.print("ok");
			}

			else {
				out.print("no");
			}

		}

		// 用户从quiz向前调转并进行判断
		if (type.equals("check")) {

			boolean blfig = false;
			try {
				blfig = awDbUtil.getIsRecord(userName, currentTime); // 有记录返回true，
																		// 没有返回false
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (blfig) {
				out.print("yes");
			} else {
				out.print("no");
			}

		}

		try {
			awDbUtil.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
