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

/**
 * Servlet implementation class AddTaskToSprintServlet
 */
@WebServlet("/addTaskToSprint")
public class AddTaskToSprintServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final DAOStorageSourse SOURCE_DATABASE = DAOStorageSourse.DATABASE;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int taskId = Integer.parseInt(request.getParameter("taskId"));
		int sprintId = Integer.parseInt(request.getParameter("sprintId"));
		int projectId = Integer.parseInt(request.getParameter("ProjectId"));
		
			try {
				ITaskDAO.getDAO(SOURCE_DATABASE).addTaskToSprint(taskId, sprintId);
			} catch (DBException e) {
				e.printStackTrace();
			}
	
		response.sendRedirect("./allTasksProject?projectId="+projectId+"&backLog=1");
	}


}
