package bg.ittalents.efficientproject.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bg.ittalents.efficientproject.model.exception.DBException;
import bg.ittalents.efficientproject.model.exception.EfficientProjectDAOException;
import bg.ittalents.efficientproject.model.interfaces.DAOStorageSourse;
import bg.ittalents.efficientproject.model.interfaces.IEpicDAO;
import bg.ittalents.efficientproject.model.pojo.Epic;

/**
 * Servlet implementation class ProfileDetail
 */
@WebServlet("/profileDetail")
public class ProfileDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProfileDetail() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {

			if (request.getSession(false) == null) {
				response.sendRedirect("./LogIn");
				return;
			}
//			if (request.getParameter("epictId") != null) {
//
//				int epicId = Integer.parseInt(request.getParameter("epictId"));
//				Epic epic = IEpicDAO.getDAO(DAOStorageSourse.DATABASE).getEpicById(epicId);
//				request.setAttribute("epic", epic);
//				
//			} else {
//				request.getRequestDispatcher("error2.jsp").forward(request, response);
//			}
		} catch (DBException | EfficientProjectDAOException | IOException | ServletException e) {
			try {
				response.sendRedirect("error.jsp");
				e.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

}
