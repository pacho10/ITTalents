package bg.ittalents.efficientproject.controller;

import java.io.IOException;

import javax.activation.UnsupportedDataTypeException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bg.ittalents.efficientproject.model.exception.DBException;
import bg.ittalents.efficientproject.model.exception.EffPrjDAOException;
import bg.ittalents.efficientproject.model.exception.EffProjectException;
import bg.ittalents.efficientproject.model.interfaces.DAOStorageSourse;
import bg.ittalents.efficientproject.model.interfaces.IProjectDAO;
import bg.ittalents.efficientproject.model.interfaces.ITaskDAO;
import bg.ittalents.efficientproject.model.pojo.User;

/**
 * Servlet implementation class AddTaskToSprintServlet
 */
@WebServlet("/addTaskToSprint")

public class AddTaskToSprintServlet extends HttpServlet {
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

			User user = (User) request.getSession().getAttribute("user");

			if (!user.isAdmin()) {
				response.sendRedirect("./LogIn");
				return;
			}

			if (request.getParameter("taskId") != null && request.getParameter("sprintId") != null
					&& request.getParameter("ProjectId") != null) {

				int projectId = Integer.parseInt(request.getParameter("ProjectId"));
				/**
				 * check if the project is of this admin
				 */
				if (IProjectDAO.getDAO(SOURCE_DATABASE).isThisProjectOfThisUser(projectId, user.getId())) {
					response.sendRedirect("./LogIn");
					return;
				}
				int taskId = Integer.parseInt(request.getParameter("taskId"));
				int sprintId = Integer.parseInt(request.getParameter("sprintId"));

				ITaskDAO.getDAO(SOURCE_DATABASE).addTaskToSprint(taskId, sprintId);
				response.sendRedirect("./allTasksProject?projectId=" + projectId + "&backLog=1");
			} else {
				throw new EffProjectException("parameters missing in request");
			}
		} catch (DBException | EffPrjDAOException | IOException | EffProjectException e) {
			try {
				response.sendRedirect("error.jsp");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

	}

}
