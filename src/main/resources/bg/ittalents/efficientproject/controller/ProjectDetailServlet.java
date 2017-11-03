package bg.ittalents.efficientproject.controller;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bg.ittalents.efficientproject.model.exception.DBException;
import bg.ittalents.efficientproject.model.exception.EffPrjDAOException;
import bg.ittalents.efficientproject.model.exception.EffProjectException;
import bg.ittalents.efficientproject.model.interfaces.DAOStorageSourse;
import bg.ittalents.efficientproject.model.interfaces.IEpicDAO;
import bg.ittalents.efficientproject.model.interfaces.IProjectDAO;
import bg.ittalents.efficientproject.model.interfaces.ISprintDAO;
import bg.ittalents.efficientproject.model.interfaces.IUserDAO;
import bg.ittalents.efficientproject.model.pojo.Epic;
import bg.ittalents.efficientproject.model.pojo.Project;
import bg.ittalents.efficientproject.model.pojo.Sprint;
import bg.ittalents.efficientproject.model.pojo.User;

/**
 * Servlet implementation class projectDetail
 */
@WebServlet("/projectdetail")
public class ProjectDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProjectDetailServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			int projectId = Integer.parseInt(request.getParameter("projectId"));

			Sprint currentSprint = ISprintDAO.getDAO(DAOStorageSourse.DATABASE).getCurrentSprint(projectId);
			if (currentSprint != null) {
				request.setAttribute("currentSprint", currentSprint);
			}

			Project currentProject = IProjectDAO.getDAO(DAOStorageSourse.DATABASE).getProjectByID(projectId);
			request.setAttribute("project", currentProject);

			List<Epic> epics = IEpicDAO.getDAO(DAOStorageSourse.DATABASE).getAllEpicsByProject(projectId);
			request.setAttribute("epics", epics);

			List<User> users = IProjectDAO.getDAO(DAOStorageSourse.DATABASE).getAllWorkersWorkingOnAProject(projectId);
			request.setAttribute("workers", users);

			// get current sprintby id---->if there is no current sprint--->message and
			// redirect to create new one
			// if there is current sprnt--->show the sprint

			RequestDispatcher rd = request.getRequestDispatcher("/projectDetail.jsp");
			rd.forward(request, response);
		} catch (DBException | EffPrjDAOException | IOException | ServletException e) {
			try {
				response.sendRedirect("error.jsp");
				e.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}


}
