package bg.ittalents.efficientproject.controller.servlets;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bg.ittalents.efficientproject.model.interfaces.IUserDAO;
import bg.ittalents.efficientproject.model.pojo.User;

@WebServlet("/SignUp")

public class SignUpServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String fullName = request.getParameter("full-name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String reppassword = request.getParameter("repPassword");

		response.getWriter().println(fullName + ":" + email + ":" + password + ":" + reppassword);

		RequestDispatcher dispatcher = request.getRequestDispatcher("./index.jsp");

		if (fullName.trim().length() < 5) {
			request.setAttribute("errorMessage", "Full Name must me at least 5 symbols");
			dispatcher.forward(request, response);
		}

		if (!fullName.contains(" ")) {
			request.setAttribute("errorMessage", "Full Name without space separotor is not allowed");
			dispatcher.forward(request, response);
			return;
		}

		if (!isMailValid(email)) {
			request.setAttribute("errorMessage", "Invalid e-mail! Try Again");
			dispatcher.forward(request, response);
		}

		if (!password.equals(reppassword)) {
			request.setAttribute("errorMessage", "Passwords do no match please use the button!");
			dispatcher.forward(request, response);
		}

		if (!isPaswordStrong(password)) {
			request.setAttribute("errorMessage", "Password must contain 5 symbols and at least one number and leter");
			dispatcher.forward(request, response);
		}

		// TODO method in the dao:
		if (IUserDAO.getDAO("db").isThereSuchAnUser(email)) {
			request.setAttribute("errorMessage", "User with such email already exists, use another email !!!");
			dispatcher.forward(request, response);
		}

		User user = new User(fullName,email,password,true);

		request.getSession().setAttribute("user", user);
		response.sendRedirect("./moreDetails.jsp");//!!!!!!!!!!!!!!!!!!!!!!!!create moredetails JSP!!!!!--->avatar and organization

	}

	public static boolean isPaswordStrong(String pass) {
		boolean letter = false;
		boolean number = false;

		for (int i = 0; i < pass.length(); i++) {
			if (letter == false && pass.charAt(i) >= 'A' && pass.charAt(i) <= 'z') {
				letter = true;
			}
			if (number == false && pass.charAt(i) >= '0' && pass.charAt(i) <= '9') {
				number = true;
			}

			if (number && letter && pass.length() > 4) {
				return true;
			}
		}
		return false;
	}

	public static boolean isMailValid(String mail) {

		final Pattern pat = Pattern.compile(
				"^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$");

		return pat.matcher(mail).matches();
	}
}