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

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("./index.jsp").forward(request, response);
		} catch (IOException | ServletException e) {
			try {
				request.getRequestDispatcher("error.jsp").forward(request, response);
				e.printStackTrace();
			} catch (IOException | ServletException e1) {
				e1.printStackTrace();
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
			String email = request.getParameter("email");
			String password = request.getParameter("password");

			RequestDispatcher dispatcher = request.getRequestDispatcher("./index.jsp");

			// there is no user with this email:
			if (!IUserDAO.getDAO(DAOStorageSourse.DATABASE).isThereSuchAUser(email)) {
				request.setAttribute("errorMessage", "There is no user registered with this email!");
				dispatcher.forward(request, response);
				return;
			}

			// there is user with this email but password doesn`t match:
			User user = IUserDAO.getDAO(DAOStorageSourse.DATABASE).getUserByEmail(email);

			if ( !user.getPassword().equals(Encrypter.encrypt(password))) {
				request.setAttribute("errorMessage", "Password not matching the user, try again!");
				dispatcher.forward(request, response);
				return;
			}

			// Everything went well:
			request.getSession().setAttribute("user", user);
			response.sendRedirect("./dashboard");
		} catch (DBException | EffPrjDAOException | ServletException | IOException e) {
			try {
				request.getRequestDispatcher("error.jsp").forward(request, response);
				e.printStackTrace();
			} catch (IOException | ServletException e1) {
				e1.printStackTrace();
			}
		}
	}

}
