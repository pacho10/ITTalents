package bg.ittalents.efficientproject.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bg.ittalents.efficientproject.model.exception.DBException;
import bg.ittalents.efficientproject.model.interfaces.DAOStorageSourse;
import bg.ittalents.efficientproject.model.interfaces.ISprintDAO;
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("./createSprint.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		int duration = Integer.parseInt(request.getParameter("duration"));
		
		Sprint sprintToAdd = new Sprint(name, duration);
		try {
			ISprintDAO.getDAO(DAOStorageSourse.DATABASE).createSprint(sprintToAdd);
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//TODO
//		response.sendRedirect("./projectdetail?projectId="+project.getId());
//		RequestDispatcher rd = request.getRequestDispatcher("./profileShow.jsp");
//		rd.forward(request, response);
	}
}
