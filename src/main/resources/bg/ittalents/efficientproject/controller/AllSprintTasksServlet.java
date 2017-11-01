package bg.ittalents.efficientproject.controller;

import java.io.IOException;
import java.util.List;

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
import bg.ittalents.efficientproject.model.interfaces.ISprintDAO;
import bg.ittalents.efficientproject.model.interfaces.ITaskDAO;
import bg.ittalents.efficientproject.model.pojo.Sprint;
import bg.ittalents.efficientproject.model.pojo.Task;

/**
 * Servlet implementation class AllSprintTasksServlet
 */
@WebServlet("/allsprinttasks")
public class AllSprintTasksServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AllSprintTasksServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getSession().getAttribute("user") != null) {	
			try {
				int projectId = Integer.parseInt(request.getParameter("projectId"));
				Sprint currentSprint = ISprintDAO.getDAO(DAOStorageSourse.DATABASE).getCurrentSprint(projectId);
				List<Task> tasks = ITaskDAO.getDAO(DAOStorageSourse.DATABASE).getAllTasksFromSprint(currentSprint.getId());
				request.setAttribute("tasks", tasks);
				
				RequestDispatcher rd = request.getRequestDispatcher("./workerTasksCurrentSprint.jsp");
				rd.forward(request, response);
			} catch (DBException | EffPrjDAOException e) {
				e.printStackTrace();
				response.sendRedirect("./error.jsp");
			}
		} else {
			response.sendRedirect("./error.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
