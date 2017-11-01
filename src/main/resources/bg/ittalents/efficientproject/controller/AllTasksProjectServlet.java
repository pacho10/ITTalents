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
import bg.ittalents.efficientproject.model.interfaces.ITaskDAO;
import bg.ittalents.efficientproject.model.pojo.Task;

/**
 * Servlet implementation class AllTasksProject
 */
@WebServlet("/allTasksProject")
public class AllTasksProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getSession().getAttribute("user") != null) {
			// User user = (User) request.getSession().getAttribute("user");
			int projectId = Integer.parseInt(request.getParameter("projectId"));
			int backLog = Integer.parseInt(request.getParameter("backLog"));
			request.setAttribute("backLog", backLog);
			// if (user.isAdmin()) {
			try {
				request.setAttribute("project",
						IProjectDAO.getDAO(DAOStorageSourse.DATABASE).getProjectByID(projectId));
				List<Task> tasks = null;
				if (backLog == 1) {
					tasks = ITaskDAO.getDAO(DAOStorageSourse.DATABASE).getProjectBackLog(projectId);
				} else {
					tasks = ITaskDAO.getDAO(DAOStorageSourse.DATABASE).getAllTasksOfProject(projectId);
				}
				if (tasks != null) {

					request.setAttribute("tasks", tasks);

					RequestDispatcher rd = request.getRequestDispatcher("./allProjectTasks.jsp");
					rd.forward(request, response);
				} else {
					// TODO exception
				}
			} catch (DBException e) {
				e.printStackTrace();
			} catch (EffPrjDAOException e) {
				e.printStackTrace();
			}
		} else {

			// TODO exception
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
