package bg.ittalents.efficientproject.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bg.ittalents.efficientproject.model.exception.DBException;
import bg.ittalents.efficientproject.model.exception.EffPrjDAOException;
import bg.ittalents.efficientproject.model.interfaces.DAOStorageSourse;
import bg.ittalents.efficientproject.model.interfaces.IUserDAO;

/**
 * Servlet implementation class addWorkerToProjectServlet
 */
@WebServlet("/addWorkerToProject")
public class AddWorkerToProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final DAOStorageSourse SOURCE_DATABASE = DAOStorageSourse.DATABASE;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int projectId = Integer.parseInt(request.getParameter("projectId"));
		int userId = Integer.parseInt(request.getParameter("userId"));
		try {
			IUserDAO.getDAO(SOURCE_DATABASE).addUserToProject(userId, projectId);
		} catch (EffPrjDAOException | DBException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.sendRedirect("./projectdetail?projectId="+projectId);
	
	}


}
