package bg.ittalents.efficientproject.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bg.ittalents.efficientproject.model.exception.DBException;
import bg.ittalents.efficientproject.model.exception.EffPrjDAOException;
import bg.ittalents.efficientproject.model.interfaces.DAOStorageSourse;
import bg.ittalents.efficientproject.model.interfaces.IProjectDAO;
import bg.ittalents.efficientproject.model.interfaces.ISprintDAO;
import bg.ittalents.efficientproject.model.pojo.Project;
import bg.ittalents.efficientproject.model.pojo.Sprint;

/**
 * Servlet implementation class CreateSprintServlet
 */
@WebServlet("/createsprint")
public class CreateSprintServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateSprintServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getSession().getAttribute("user") != null) {
			Sprint currentSprint = null;
			int projectId = Integer.parseInt(request.getParameter("projectId"));
			int all = Integer.parseInt(request.getParameter("all"));
			request.setAttribute("all", all);
			try {
				currentSprint = ISprintDAO.getDAO(DAOStorageSourse.DATABASE).getCurrentSprint(projectId);
				Project project = IProjectDAO.getDAO(DAOStorageSourse.DATABASE).getProjectByID(projectId);
				if (currentSprint == null) {
					request.setAttribute("projectId", projectId);
					request.setAttribute("project", project);
					request.getRequestDispatcher("./createSprint.jsp").forward(request, response);
				} else {
					response.sendRedirect("./hasCurrentSprint.jsp");
				}

			} catch (DBException | EffPrjDAOException e) {
				e.printStackTrace();
				response.sendRedirect("./error.jsp");
			}

		} else {
			response.sendRedirect("./LogIn");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getSession().getAttribute("user") != null) {
			int projectId = Integer.parseInt(request.getParameter("projectId"));

			String name = request.getParameter("name");
			int duration = Integer.parseInt(request.getParameter("duration"));
			Sprint sprintToAdd = new Sprint(name, duration, projectId);
			try {
				int id = ISprintDAO.getDAO(DAOStorageSourse.DATABASE).createSprint(sprintToAdd);
			} catch (DBException e) {
				e.printStackTrace();
				response.sendRedirect("./error.jsp");
			}
		} else {
			response.sendRedirect("./LogIn");
		}
		String name = request.getParameter("name");
		int duration = Integer.parseInt(request.getParameter("duration"));
		int projectId = Integer.parseInt(request.getParameter("projectId"));
		// request.setAttribute("projectId", projectId);

		Sprint sprintToAdd = new Sprint(name, duration, projectId);
		try {
			ISprintDAO.getDAO(DAOStorageSourse.DATABASE).createSprint(sprintToAdd);
		} catch (DBException e) {
			e.printStackTrace();

		}

		response.sendRedirect("./projectdetail?projectId=" + projectId);
	}
}
