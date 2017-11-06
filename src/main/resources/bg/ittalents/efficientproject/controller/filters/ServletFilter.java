//package bg.ittalents.efficientproject.controller.filters;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.StringTokenizer;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.annotation.WebInitParam;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//@WebFilter(filterName = "SessionCheckFilter", urlPatterns = "/*", initParams = {
//		@WebInitParam(name = "avoid-urls", value = "/*.*,/img/*,/SignUp,/LogIn,/error.jsp,/error2.jsp,/index.jsp,/signUp.jsp,/errorNotAuthorized.jsp"),
//		@WebInitParam(name = "allowedTypes", value = "jpg,jpeg,gif,png") })
//public class ServletFilter implements Filter {
//	private ArrayList<String> urlList;
//
//	@Override
//	public void init(FilterConfig config) throws ServletException {
//		String urls = config.getInitParameter("avoid-urls");
//		StringTokenizer token = new StringTokenizer(urls, ",");
//
//		urlList = new ArrayList<String>();
//		// "./SignUp,./LogIn,error.jsp,error2.jsp,index.jsp,signUp.jsp,errorNotAuthorized.jsp"
//		while (token.hasMoreTokens()) {
//			System.out.println(urlList.add(token.nextToken()));
//			;
//
//		}
//
//	}
//
//	@Override
//	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
//			throws IOException, ServletException {
//		HttpServletRequest request = (HttpServletRequest) req;
//		HttpServletResponse response = (HttpServletResponse) res;
//		String url = request.getServletPath();
//		System.out.println(request.getServletPath());
//		boolean allowedRequest = false;
//
//		System.out.println(urlList);
//
//		if (urlList.contains(url)) {
//			System.out.println(1);
//			allowedRequest = true;
//		}
//		System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
//		if (!allowedRequest) {
//			HttpSession session = request.getSession(false);
//			if (null == session || session.getAttribute("user") == null) {
//				System.out.println(2);
//				response.sendRedirect("./LogIn");
//				return;
//			}
//		}
//
//		chain.doFilter(req, res);
//
//	}
//
//	@Override
//	public void destroy() {
//		// TODO Auto-generated method stub
//
//	}
//
//}
