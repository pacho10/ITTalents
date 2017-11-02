package bg.ittalents.efficientproject.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bg.ittalents.efficientproject.model.exception.DBException;
import bg.ittalents.efficientproject.model.exception.EffPrjDAOException;
import bg.ittalents.efficientproject.model.interfaces.DAOStorageSourse;
import bg.ittalents.efficientproject.model.interfaces.IOrganizationDAO;
import bg.ittalents.efficientproject.model.interfaces.IUserDAO;
import bg.ittalents.efficientproject.model.pojo.Organization;
import bg.ittalents.efficientproject.model.pojo.User;

@WebServlet("/SignUp")

public class SignUpServlet extends HttpServlet {

	private static final DAOStorageSourse SOURCE_DATABASE = DAOStorageSourse.DATABASE;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		//dissable cache:
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setHeader("Expires", "0"); // Proxies.

		request.getRequestDispatcher("./signUp.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//dissable cache:
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setHeader("Expires", "0"); // Proxies.
		
		String firstName = request.getParameter("first-name");
		String lastName = request.getParameter("last-name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String reppassword = request.getParameter("repPassword");
		String organization = request.getParameter("organization").trim();
		boolean isAdmin = Boolean.parseBoolean(request.getParameter("isAdmin"));
//		System.out.println(isAdmin);

		RequestDispatcher dispatcher = request.getRequestDispatcher("./signUp.jsp");

		if (!isMailValid(email)) {
			request.setAttribute("errorMessage", "Invalid e-mail! Try Again");
			dispatcher.forward(request, response);
			return;
		}

		if (!password.equals(reppassword)) {
			request.setAttribute("errorMessage", "Passwords do no match please make sure they do!");
			dispatcher.forward(request, response);
			return;
		}

		
		if (!isPaswordStrong(password)) {
			request.setAttribute("errorMessage", "Password must contain 5 symbols and at least one number and letter");
			dispatcher.forward(request, response);
			return;
		}

		try {
			if (IUserDAO.getDAO(SOURCE_DATABASE).isThereSuchAnUser(email)) {
				
				request.setAttribute("errorMessage", "User with such email already exists, use another email !");
				dispatcher.forward(request, response);
				return;
			}
		} catch (DBException e2) {
			e2.printStackTrace();
		} catch (EffPrjDAOException e2) {
			e2.printStackTrace();
		}

		try {
			if (IOrganizationDAO.getDAO(SOURCE_DATABASE).isThereSuchOrganization(organization)) {
				request.setAttribute("errorMessage", "This organization is already registered !");
				dispatcher.forward(request, response);
				return;
			}
		} catch (EffPrjDAOException e1) {
			e1.getMessage();
			e1.printStackTrace();
		}

		if(isAdmin && organization.equals("")) {
			request.setAttribute("errorMessage", "This organization name is empty !");
			dispatcher.forward(request, response);
			return;
		}

		// adding the user to the database:
		User user = null;
		try {

			if (!isAdmin) {
				user = new User(firstName, lastName, email, password, false);
				int UserId=IUserDAO.getDAO(DAOStorageSourse.DATABASE).addUserWorker(user);
				user.setId(UserId);

			} else {
				user = new User(firstName, lastName, email, password, true, new Organization(organization));
				IUserDAO.getDAO(DAOStorageSourse.DATABASE).addUserAdmin(user);
			}
		} catch (EffPrjDAOException | DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.getSession().setAttribute("user", user);
		response.sendRedirect("./ProfileEdit");
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
