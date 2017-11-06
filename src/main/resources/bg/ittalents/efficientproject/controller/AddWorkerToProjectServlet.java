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

/**
 * Servlet implementation class addWorkerToProjectServlet
 */
@WebServlet("/addWorkerToProject")
public class AddWorkerToProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final DAOStorageSourse SOURCE_DATABASE = DAOStorageSourse.DATABASE;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			/**
			 * check if there is no session or there is but the user is different or the
			 * user is not admin:
			 */
			if (request.getSession(false) == null) {
				response.sendRedirect("./LogIn");
				return;
			}

			if (request.getParameter("userId") != null && request.getParameter("ProjectId") != null) {
			User user = (User) request.getSession().getAttribute("user");

			if (!user.isAdmin()) {
				response.sendRedirect("LogIn");
				return;
			}
			int projectId = Integer.parseInt(request.getParameter("projectId"));
			/**
			 * check if the project is of this admin
			 */
			if (!IProjectDAO.getDAO(SOURCE_DATABASE).isThisProjectOfThisUser(projectId, user.getId())) {
				request.getRequestDispatcher("errorNotAuthorized.jsp").forward(request, response);
				return;
			}
			
				int userId = Integer.parseInt(request.getParameter("userId"));
				IUserDAO.getDAO(SOURCE_DATABASE).addUserToProject(userId, projectId);
				response.sendRedirect("./projectdetail?projectId=" + projectId);
			}else {
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

}
