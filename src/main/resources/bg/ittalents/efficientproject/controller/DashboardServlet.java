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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getSession(false) != null && request.getSession().getAttribute("user") != null) {

			User user = (User) request.getSession().getAttribute("user");
			if (user.isAdmin()) {
				int intorganizationId = user.getOrganization().getId();
				System.out.println("servlet "+intorganizationId);
				try {
					List<Project> projects = IProjectDAO.getDAO(DAOStorageSourse.DATABASE)
							.getAllProjectsFromOrganization(intorganizationId);
					request.setAttribute("projects", projects);

					String organizationName = user.getOrganization().getName();
					request.setAttribute("organizationName", organizationName);
				} catch (DBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (EffPrjDAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.getRequestDispatcher("/contentDashboardAdmin.jsp").forward(request, response);
				response.setContentType("text/html"); 
			} else {
				request.getRequestDispatcher("/contentDashboardWorker.jsp").forward(request, response);

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
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
