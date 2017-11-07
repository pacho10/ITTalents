package bg.ittalents.efficientproject.controller.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "SessionCheckFilter", urlPatterns = "/*")
public class ServletFilter implements Filter {

	@Override
	public void init(FilterConfig config) throws ServletException {


	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String url = request.getServletPath();
		HttpSession session = request.getSession(false);
		if (url.contains("/.") || url.contains("SignUp") || url.contains("LogIn")) {
			chain.doFilter(req, res);
			return;
		}
		if (request.getSession(false) == null ||  request.getSession(false).getAttribute("user") == null) {
			response.sendRedirect("./LogIn");
			return;
		}
		chain.doFilter(req, res);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}



}
