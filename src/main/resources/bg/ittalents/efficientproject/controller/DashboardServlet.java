package bg.ittalents.efficientproject.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bg.ittalents.efficientproject.model.exception.DBException;
import bg.ittalents.efficientproject.model.exception.EffPrjDAOException;
import bg.ittalents.efficientproject.model.interfaces.DAOStorageSourse;
import bg.ittalents.efficientproject.model.interfaces.IProjectDAO;
import bg.ittalents.efficientproject.model.interfaces.IUserDAO;
import bg.ittalents.efficientproject.model.pojo.Project;
import bg.ittalents.efficientproject.model.pojo.User;

/**
 * Servlet implementation class DashboardServlet
 */
@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DashboardServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {

			if (request.getSession(false) == null) {
				response.sendRedirect("/LogIn");
				return;
			}
			if (request.getSession().getAttribute("user") != null) {

				User user = (User) request.getSession().getAttribute("user");
				// if the user is admin forward to admins page
				if (user.isAdmin()) {
					int intorganizationId = user.getOrganization().getId();
					List<Project> projects = IProjectDAO.getDAO(DAOStorageSourse.DATABASE)
							.getAllProjectsFromOrganization(intorganizationId);
					if (projects != null) {
						request.setAttribute("projects", projects);
					} else {
						request.getRequestDispatcher("error2.jsp").forward(request, response);
					}

					String organizationName = user.getOrganization().getName();
					request.setAttribute("organizationName", organizationName);

				request.setCharacterEncoding("UTF-8");
				response.setCharacterEncoding("UTF-8");
					request.getRequestDispatcher("./homePageAdmin.jsp").forward(request, response);
					response.setContentType("text/html");
				} else {//if the user is worker:
					int CurrentProjectId= IUserDAO.getDAO(DAOStorageSourse.DATABASE).returnCurrentWorkersProject(user);
					Project project = null;
					if (CurrentProjectId > 0) {
						project = IProjectDAO.getDAO(DAOStorageSourse.DATABASE).getProjectByID(CurrentProjectId);
					}
					request.getSession().setAttribute("project", project);

					response.sendRedirect("./workertasks");

				}
			} else {
				request.getRequestDispatcher("error2.jsp").forward(request, response);
			}
		} catch (IOException | ServletException | EffPrjDAOException | DBException e) {
			try {
				request.getRequestDispatcher("error.jsp").forward(request, response);
				e.printStackTrace();
			} catch (IOException | ServletException e1) {
				e1.printStackTrace();
			}
		}
	}

}
