package bg.ittalents.efficientproject.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ProfileShowServlet
 */
@WebServlet(name = "ProfileShow", urlPatterns = { "/Profile" })
public class ProfileShowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			if (request.getSession(false) == null || request.getSession().getAttribute("user") == null) {
				response.sendRedirect("/LogIn");
				return;
			}
			request.getRequestDispatcher("./profileShow.jsp").forward(request, response);
		} catch (IOException | ServletException e) {
			try {
				request.getRequestDispatcher("error.jsp").forward(request, response);
				e.printStackTrace();
			} catch (IOException | ServletException e1) {
				e1.printStackTrace();
			}
		}

	}
}
