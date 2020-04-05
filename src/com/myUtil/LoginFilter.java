package com.myUtil;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LoginFilter implements Filter {

	private FilterConfig config;

	public void init(FilterConfig config) {
		this.config = config;
	}

	public void destroy() {
		config = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws ServletException, IOException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		HttpSession session = req.getSession();
		
		Object account = session.getAttribute("account");
		if (account == null) {
			
			String location = req.getRequestURI();
			session.setAttribute("location", location);
			session.setAttribute("PleaseSignIn", true);
			System.out.println(location);
			
			switch(location) {
				case "/DA105G3_All/front-end/mem/MemberIndex.jsp" :
					res.sendRedirect(req.getContextPath() + "/Index.jsp");
					break;
				case "/DA105G3_All/front-end/article/RaddArticle.jsp" :
					res.sendRedirect(req.getContextPath() + "/front-end/article/RlistAllArticle.jsp");
					break;
				case "/DA105G3_All/front-end/designer/applyDesigner.jsp" :
					res.sendRedirect(req.getContextPath() + "/front-end/designer/RlistAllDesPolio.jsp");
					break;	
				case "/DA105G3_All/front-end/rent/Checkout.jsp" :
					res.sendRedirect(req.getContextPath() + "/front-end/rent/Cart.jsp");
					break;	
			}
			return;
		} else {
			chain.doFilter(request, response);
		}
	}
}