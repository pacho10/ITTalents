package bg.ittalents.efficientproject.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bg.ittalents.efficientproject.model.exception.DBException;
import bg.ittalents.efficientproject.model.exception.EffPrjDAOException;
import bg.ittalents.efficientproject.model.interfaces.DAOStorageSourse;
import bg.ittalents.efficientproject.model.interfaces.ITaskDAO;

/**
 * Servlet implementation class FinisheTaskServlet
 */
@WebServlet("/finishtask")
public class FinishTaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FinishTaskServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			if (request.getSession().getAttribute("user") != null) {
				int taskId = Integer.parseInt(request.getParameter("taskId"));
				boolean result = false;
				result = ITaskDAO.getDAO(DAOStorageSourse.DATABASE).finishTask(taskId);
				if (result) {
					response.sendRedirect("./dashboard");
					return;
				}

			} else {
				response.sendRedirect("./LogIn");
			}
		} catch (DBException | IOException | EffPrjDAOException e) {
			try {
				response.sendRedirect("./error.jsp");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

}
