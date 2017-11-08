package bg.ittalents.efficientproject.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bg.ittalents.efficientproject.model.exception.DBException;
import bg.ittalents.efficientproject.model.exception.EfficientProjectDAOException;
import bg.ittalents.efficientproject.model.interfaces.DAOStorageSourse;
import bg.ittalents.efficientproject.model.interfaces.IProjectDAO;
import bg.ittalents.efficientproject.model.interfaces.IUserDAO;
import bg.ittalents.efficientproject.model.pojo.User;
import bg.ittalents.efficientproject.util.IntegerChecker;
import bg.ittalents.efficientproject.util.SendingMails;

/**
 * Servlet implementation class addWorkerToProjectServlet
 */
@WebServlet("/addWorkerToProject")
public class AddWorkerToProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final DAOStorageSourse SOURCE_DATABASE = DAOStorageSourse.DATABASE;
	private static final String SEND_EMAIL_SUBJECT = "added to new efficient project";

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			/**
			 * check if there is no session or there is but the user is different or the
			 * user is not admin:
			 */
			if (request.getSession(false) == null || request.getSession(false).getAttribute("user") == null) {
				response.sendRedirect("./LogIn");
				return;
			}

			String userIdParam = request.getParameter("userId");
			String projectIdParam = request.getParameter("projectId");
			if (userIdParam != null && IntegerChecker.isInteger(userIdParam) && projectIdParam != null
					&& IntegerChecker.isInteger(projectIdParam)) {
				User user = (User) request.getSession().getAttribute("user");

				if (!user.isAdmin()) {
					response.sendRedirect("LogIn");
					return;
				}
				int projectId = Integer.parseInt(projectIdParam);
				/**
				 * check if the project is of this admin
				 */
				if (!IProjectDAO.getDAO(SOURCE_DATABASE).isThisProjectOfThisUser(projectId, user.getId())) {
					request.getRequestDispatcher("errorNotAuthorized.jsp").forward(request, response);
					return;
				}

				int userId = Integer.parseInt(userIdParam);
				IUserDAO.getDAO(SOURCE_DATABASE).addWorkerToProject(userId, projectId);
				
				User recepient=IUserDAO.getDAO(SOURCE_DATABASE).getUserById(userId);
				SendingMails.sendEmail(recepient.getEmail(), SEND_EMAIL_SUBJECT, messageContent(user.getFirstName(),user.getLastName()));
				response.sendRedirect("./projectdetail?projectId=" + projectId);
			} else {
				request.getRequestDispatcher("error2.jsp").forward(request, response);
			}
		} catch (EfficientProjectDAOException | DBException | IOException | ServletException e) {
			try {
				response.sendRedirect("error.jsp");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

	}
	private String messageContent(String firstName,String lastName) {
		return "Dear "+firstName+" "+lastName+", "+"\n\n you you have been added to new project, for extra information, please log in efficientproject.bg";
				
	}

}
