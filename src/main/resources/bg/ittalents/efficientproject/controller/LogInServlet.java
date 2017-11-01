package bg.ittalents.efficientproject.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bg.ittalents.efficientproject.model.exception.DBException;
import bg.ittalents.efficientproject.model.exception.EffPrjDAOException;
import bg.ittalents.efficientproject.model.interfaces.DAOStorageSourse;
import bg.ittalents.efficientproject.model.interfaces.IUserDAO;
import bg.ittalents.efficientproject.model.pojo.User;
import bg.ittalents.efficientproject.util.Encrypter;

@WebServlet("/LogIn")
public class LogInServlet extends HttpServlet {

	private static final long serialVersionUID = 4252149006661628687L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// dissable cache:
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setHeader("Expires", "0"); // Proxies.

		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		RequestDispatcher dispatcher = request.getRequestDispatcher("./index.jsp");
		dispatcher.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("email");
		// System.out.println(email);
		String password = request.getParameter("password");

		RequestDispatcher dispatcher = request.getRequestDispatcher("./index.jsp");

		// there is no user with this email:
		try {
			if (!IUserDAO.getDAO(DAOStorageSourse.DATABASE).isThereSuchAnUser(email)) {
				request.setAttribute("errorMessage", "There is no user registered with this email!");
				dispatcher.forward(request, response);
				return;
			}
		} catch (DBException | EffPrjDAOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// there is user with this email but password doesn`t match:
		User user = null;
		try {
			user = IUserDAO.getDAO(DAOStorageSourse.DATABASE).getUserByEmail(email);
		} catch (EffPrjDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (user == null || !user.getPassword().equals(Encrypter.encrypt(password))) {
			request.setAttribute("errorMessage", "Password not matching the user, try again!");
			dispatcher.forward(request, response);
			return;
		}

		// everythig went well:
		request.getSession().setAttribute("user", user);
		response.sendRedirect("./dashboard");

	}

}
