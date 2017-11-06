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
import bg.ittalents.efficientproject.model.exception.EfficientProjectDAOException;
import bg.ittalents.efficientproject.model.interfaces.DAOStorageSourse;
import bg.ittalents.efficientproject.model.interfaces.ITaskDAO;
import bg.ittalents.efficientproject.model.pojo.Task;
import bg.ittalents.efficientproject.model.pojo.User;

/**
 * Servlet implementation class WorkerTasksServlet
 */
@WebServlet("/workertasks")
public class WorkerTasksServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			if (request.getSession().getAttribute("user") != null) {
				User user = (User) request.getSession().getAttribute("user");
				List<Task> tasks = ITaskDAO.getDAO(DAOStorageSourse.DATABASE).getAllTasksByUser(user.getId());
				request.setAttribute("tasks", tasks);

				RequestDispatcher rd = request.getRequestDispatcher("./workerTasks.jsp");
				rd.forward(request, response);
			} else {
				response.sendRedirect("./LogIn");
			}
		} catch (EfficientProjectDAOException | IOException | ServletException | DBException e) {
			try {
				response.sendRedirect("error.jsp");
				e.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}
	}

}
