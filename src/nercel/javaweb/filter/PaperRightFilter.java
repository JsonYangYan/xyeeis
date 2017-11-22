package nercel.javaweb.filter;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.FilterChain;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;

public class PaperRightFilter implements Filter {
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		HttpSession session = req.getSession(true);

		// 从session里取的用户名信息
		String username = (String) session.getAttribute("name");
		// 判断如果没有取到用户信息,就跳转到登陆页面
		if (username == null || "".equals(username)) {
			// 跳转到登陆页面
			res.sendRedirect("http://" + req.getHeader("Host")
					+ "/xyeeis/xyseis/login.html");
		} else {
			// 已经登陆,继续此次请求
			chain.doFilter(request, response);
		}
	}

	public void destroy() {
	}
}