package bg.ittalents.efficientproject.controller;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import bg.ittalents.efficientproject.model.interfaces.DAOStorageSourse;
import bg.ittalents.efficientproject.model.interfaces.IUserDAO;

/**
 * Servlet implementation class returnUnemployedWorkersS
 */
@WebServlet("/returnUnemployedWorkers")
public class returnUnemployedWorkersS extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public returnUnemployedWorkersS() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		Collection workers = IUserDAO.getDAO(DAOStorageSourse.DATABASE).getAllUnemployedWorkers();
		String s = new Gson().toJson(workers);
		System.out.println(s);
		response.getWriter().println(s);	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
