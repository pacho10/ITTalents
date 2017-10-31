package bg.ittalents.efficientproject.controller;

import java.io.IOException;
import java.util.ArrayList;
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
import bg.ittalents.efficientproject.model.interfaces.IEpicDAO;
import bg.ittalents.efficientproject.model.interfaces.IProjectDAO;
import bg.ittalents.efficientproject.model.pojo.Epic;
import bg.ittalents.efficientproject.model.pojo.Project;
import bg.ittalents.efficientproject.model.pojo.User;

/**
 * Servlet implementation class CreateEpicServlet
 */
@WebServlet("/createepic")
public class CreateEpicServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateEpicServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getSession().getAttribute("user") != null) {
			int projectId= Integer.parseInt(request.getParameter("projectId"));
			request.setAttribute("projectId", projectId);
			List<Project> projects = new ArrayList<>();
			User user = (User) request.getSession().getAttribute("user");
			
			try {
				projects = IProjectDAO.getDAO(DAOStorageSourse.DATABASE).getAllProjectsFromOrganization(
						user.getOrganization().getId());
				request.setAttribute("projects", projects);
				RequestDispatcher rd = request.getRequestDispatcher("./createEpic.jsp");
				rd.forward(request, response);
			} catch (DBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (EffPrjDAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		int estimate = Integer.parseInt(request.getParameter("estimate"));
		String description = request.getParameter("description");
		int projectId = Integer.parseInt(request.getParameter("projects"));
		Project project=null;
		try {
			project = IProjectDAO.getDAO(DAOStorageSourse.DATABASE).getProjectByID(projectId);
			Epic epicToAdd = new Epic(name, estimate, description, project);
			int id = IEpicDAO.getDAO(DAOStorageSourse.DATABASE).createEpic(epicToAdd);
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EffPrjDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response.sendRedirect("./projectdetail?projectId="+project.getId());
	}

}
