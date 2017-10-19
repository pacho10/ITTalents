package bg.ittalents.efficientproject.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Logout")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * request.getSession(false) will return current session if current session
		 * exists, then it will not create a new session.
		 */
		HttpSession session = request.getSession(false);
		//if there is current session-invalidate it:
		if (session != null) {
			session.invalidate();
		}
		response.sendRedirect("./");
	}

}
