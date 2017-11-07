package bg.ittalents.efficientproject.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bg.ittalents.efficientproject.model.exception.DBException;
import bg.ittalents.efficientproject.model.exception.EfficientProjectDAOException;
import bg.ittalents.efficientproject.model.interfaces.DAOStorageSourse;
import bg.ittalents.efficientproject.model.interfaces.IOrganizationDAO;
import bg.ittalents.efficientproject.model.interfaces.IUserDAO;
import bg.ittalents.efficientproject.model.pojo.Organization;
import bg.ittalents.efficientproject.model.pojo.User;
import bg.ittalents.efficientproject.util.CredentialsChecks;

import static org.apache.commons.lang3.StringEscapeUtils.escapeHtml4;

@WebServlet("/SignUp")

public class SignUpServlet extends HttpServlet {

	private static final int MAX_LENGTH_INPUT_CHARACTERS = 45;
	private static final DAOStorageSourse SOURCE_DATABASE = DAOStorageSourse.DATABASE;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
			response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
			response.setHeader("Expires", "0"); // Proxies.

			request.getRequestDispatcher("./signUp.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
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
			response.setCharacterEncoding("UTF-8");
			request.setCharacterEncoding("UTF-8");
			String firstName = escapeHtml4(request.getParameter("first-name")).trim();
			String lastName = escapeHtml4(request.getParameter("last-name")).trim();
			String email = escapeHtml4(request.getParameter("email")).trim();
			String password = escapeHtml4(request.getParameter("password"));
			String reppassword = escapeHtml4(request.getParameter("repPassword"));
			String organization = escapeHtml4(request.getParameter("organization")).trim();
			boolean isAdmin = Boolean.parseBoolean(request.getParameter("isAdmin"));

			RequestDispatcher dispatcher = request.getRequestDispatcher("./signUp.jsp");

			if (firstName.length() > MAX_LENGTH_INPUT_CHARACTERS || lastName.length() > MAX_LENGTH_INPUT_CHARACTERS
					|| email.length() > MAX_LENGTH_INPUT_CHARACTERS || password.length() > MAX_LENGTH_INPUT_CHARACTERS
					|| organization.length() > MAX_LENGTH_INPUT_CHARACTERS) {
				forwardWithErrorMessage(request, response, dispatcher,"Characters number limit reached-no more than " + MAX_LENGTH_INPUT_CHARACTERS + "allowed");
				return;
			}
			if(firstName.length()==0 || lastName.length()==0) {
				forwardWithErrorMessage(request, response, dispatcher,"Empty first or last name! Try Again");
				return;
			}

			if (!CredentialsChecks.isMailValid(email)) {
				forwardWithErrorMessage(request, response, dispatcher,"Invalid e-mail! Try Again");
				return;
			}

			if (!password.equals(reppassword)) {
				forwardWithErrorMessage(request, response, dispatcher,"Passwords do no match please make sure they do!");
				return;
			}

			if (!CredentialsChecks.isPaswordStrong(password)) {
				forwardWithErrorMessage(request, response, dispatcher,"Password must contain 5 symbols and at least one number and letter");
				return;
			}

			if (IUserDAO.getDAO(SOURCE_DATABASE).isThereSuchAUser(email)) {
				forwardWithErrorMessage(request, response, dispatcher,"User with such email already exists, use another email !");
				return;
			}

			if (IOrganizationDAO.getDAO(SOURCE_DATABASE).isThereSuchOrganization(organization)) {
				forwardWithErrorMessage(request, response, dispatcher,"This organization is already registered !");
				return;
			}

			if (isAdmin && organization.length()==0) {
				forwardWithErrorMessage(request, response, dispatcher,"This organization name is empty !");
				return;
			}

			// adding the user to the database:
			User user = null;
			if (!isAdmin) {
				user = new User(firstName, lastName, email, password, false);
				int UserId = IUserDAO.getDAO(DAOStorageSourse.DATABASE).addUserWorker(user);
				user.setId(UserId);
			} else {
				user = new User(firstName, lastName, email, password, true, new Organization(organization));
				int UserId = IUserDAO.getDAO(DAOStorageSourse.DATABASE).addUserAdmin(user);
				user.setId(UserId);
			}

			request.getSession().setAttribute("user", user);
			response.sendRedirect("./ProfileEdit");
		} catch (EfficientProjectDAOException | DBException | IOException | ServletException e) {
			try {
				request.getRequestDispatcher("error.jsp").forward(request, response);
				e.printStackTrace();
			} catch (IOException | ServletException e1) {
				e1.printStackTrace();
			}
		}
	}
	private void forwardWithErrorMessage(HttpServletRequest request, HttpServletResponse response,
			RequestDispatcher dispatcher, String errorMessage) throws ServletException, IOException {
		request.setAttribute("errorMessage", errorMessage);
		dispatcher.forward(request, response);
	}
	
}
