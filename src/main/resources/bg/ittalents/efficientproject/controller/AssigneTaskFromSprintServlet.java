package bg.ittalents.efficientproject.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bg.ittalents.efficientproject.model.exception.DBException;
import bg.ittalents.efficientproject.model.exception.EfficientProjectDAOException;
import bg.ittalents.efficientproject.model.exception.EffProjectException;
import bg.ittalents.efficientproject.model.interfaces.DAOStorageSourse;
import bg.ittalents.efficientproject.model.interfaces.ITaskDAO;
import bg.ittalents.efficientproject.model.pojo.User;

/**
 * Servlet implementation class AssigneTaskFromSprintServlet
 */
@WebServlet("/assignetask")
public class AssigneTaskFromSprintServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			if (request.getSession(false) == null) {
				response.sendRedirect("./LogIn");
				return;
			}
			User user = (User) request.getSession().getAttribute("user");

			if (user.isAdmin()) {
				response.sendRedirect("./LogIn");
				return;
			}

			if (request.getParameter("taskId") != null) {
				int taskId = Integer.parseInt(request.getParameter("taskId"));
				if(!ITaskDAO.getDAO(DAOStorageSourse.DATABASE).assignTask(taskId, user.getId())) {
					//TODO  task already assiged --->refresh,ajax
				}
			} else {
				throw new EffProjectException("parameters missing in request");
			}
		} catch (EfficientProjectDAOException | IOException | DBException | EffProjectException e) {
			try {
				response.sendRedirect("error.jsp");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	// protected void doPost(HttpServletRequest request, HttpServletResponse
	// response) throws ServletException, IOException {
	// // TODO Auto-generated method stub
	// doGet(request, response);
	// }

}
