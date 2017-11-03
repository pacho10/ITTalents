package bg.ittalents.efficientproject.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import bg.ittalents.efficientproject.model.exception.DBException;
import bg.ittalents.efficientproject.model.interfaces.DAOStorageSourse;
import bg.ittalents.efficientproject.model.interfaces.ITaskDAO;
import bg.ittalents.efficientproject.model.pojo.Element;

/**
 * Servlet implementation class BarCharStatisticsServlet
 */
@WebServlet("/barchartstatisticsservlet")
public class BarChartStatisticsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BarChartStatisticsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//if (request.getSession().getAttribute("user") != null) {
			int projectId = Integer.parseInt(request.getParameter("projectId"));
			response.setHeader("Access-Control-Allow-Origin", "*");
			try {
				List<Element> elements = ITaskDAO.getDAO(DAOStorageSourse.DATABASE).getAllTaskFromAllSprints(projectId);
				Gson gson = new Gson();
				
				String data = gson.toJson(elements);
				//String data = "{tasks: 4, name: first}";
				
				response.setContentType("application/json");
				 
				  response.getWriter().write(data);
			} catch (DBException e) {
				e.printStackTrace();
				response.sendRedirect("./error.jsp");
				
				return;
			}
		//} else {
			//response.sendRedirect("./LogIn");
			
			//return;
		//}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
