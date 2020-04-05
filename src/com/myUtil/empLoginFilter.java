package com.myUtil;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.emp.model.EmpVO;

public class empLoginFilter implements Filter {

	private FilterConfig config;

	public void destroy() {
		config = null;
	}
	
	public void init(FilterConfig config) {
		this.config = config;
	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		HttpSession session = req.getSession();
		//從session中取得員工帳號
		String emp_account = (String)session.getAttribute("emp_account");
		
		// 判斷此員工是否登入過
		if(emp_account == null) {
			session.setAttribute("location", req.getRequestURI());
			res.sendRedirect(req.getContextPath()+"/back-end/emp/empLogin.jsp");
			return;
		}else {
			chain.doFilter(request, response);
		}
	}
}
