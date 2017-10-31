package bg.ittalents.efficientproject.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bg.ittalents.efficientproject.model.exception.DBException;
import bg.ittalents.efficientproject.model.interfaces.DAOStorageSourse;
import bg.ittalents.efficientproject.model.interfaces.ITaskDAO;
import bg.ittalents.efficientproject.model.pojo.User;

/**
 * Servlet implementation class AssigneTaskFromSprintServlet
 */
@WebServlet("/assignetask")
public class AssigneTaskFromSprintServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AssigneTaskFromSprintServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getSession().getAttribute("user") != null) {
			int taskId = Integer.parseInt(request.getParameter("taskId"));
			User user = (User) request.getSession().getAttribute("user");
			boolean result = false;
			
			try {
				result = ITaskDAO.getDAO(DAOStorageSourse.DATABASE).assignTask(taskId, user.getId());
			} catch (DBException e) {
				e.printStackTrace();
			}
			
			if (result) {
				response.sendRedirect("./dashboard");
			}
			
		} else {
			response.sendRedirect("./LogIn");
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
