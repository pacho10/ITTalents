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
import bg.ittalents.efficientproject.model.interfaces.IEpicDAO;
import bg.ittalents.efficientproject.model.interfaces.ITaskDAO;
import bg.ittalents.efficientproject.model.interfaces.ITypeDAO;
import bg.ittalents.efficientproject.model.pojo.Epic;
import bg.ittalents.efficientproject.model.pojo.Task;
import bg.ittalents.efficientproject.model.pojo.Type;
import bg.ittalents.efficientproject.model.pojo.User;

/**
 * Servlet implementation class CreateTaskServlet
 */
@WebServlet("/createtask")
public class CreateTaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateTaskServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int projectId = Integer.parseInt(request.getParameter("projectId"));
		try {
			List<Epic> epics = IEpicDAO.getDAO(DAOStorageSourse.DATABASE).getAllEpicsByProject(projectId);
			List<Type> types = ITypeDAO.getDAO(DAOStorageSourse.DATABASE).getAllTypes();
			
			request.setAttribute("epics", epics);
			request.setAttribute("types", types);
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EfficientProjectDAOException e) {
			e.printStackTrace();
		}
		request.setAttribute("projectId", projectId);
		RequestDispatcher rd = request.getRequestDispatcher("./createTask.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User reporter = (User) request.getSession().getAttribute("user");
		String summary = request.getParameter("summary");
		String description = request.getParameter("description");
		float estimate = Float.parseFloat(request.getParameter("estimate"));
		int typeId = Integer.parseInt(request.getParameter("types"));
		int epicId = Integer.parseInt(request.getParameter("epics"));
		
		try {
			Type type = ITypeDAO.getDAO(DAOStorageSourse.DATABASE).getTypeById(typeId);
			Epic epic = IEpicDAO.getDAO(DAOStorageSourse.DATABASE).getEpicById(epicId);
			
			Task tskToAdd = new Task(type, summary, description, estimate, reporter, epic);
			
			int id = ITaskDAO.getDAO(DAOStorageSourse.DATABASE).addTask(tskToAdd);
			
			int projectId=epic.getProject().getId();
			response.sendRedirect("./projectdetail?projectId="+projectId);
			
			
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EfficientProjectDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
