package bg.ittalents.efficientproject.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import javax.activation.UnsupportedDataTypeException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;

import bg.ittalents.efficientproject.model.exception.DBException;
import bg.ittalents.efficientproject.model.exception.EfficientProjectDAOException;
import bg.ittalents.efficientproject.model.interfaces.DAOStorageSourse;
import bg.ittalents.efficientproject.model.interfaces.IOrganizationDAO;
import bg.ittalents.efficientproject.model.interfaces.IProjectDAO;
import bg.ittalents.efficientproject.model.pojo.Organization;
import bg.ittalents.efficientproject.model.pojo.Project;
import bg.ittalents.efficientproject.model.pojo.User;

/**
 * Servlet implementation class CreateCategoryServlet
 */
@WebServlet("/createproject")
public class CreateProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getSession() != null && request.getSession().getAttribute("user") != null) {
			User user = (User) request.getSession().getAttribute("user");
			String organizationName = user.getOrganization().getName();
			request.setAttribute("organizationName", organizationName);

			RequestDispatcher dispatcher = request.getRequestDispatcher("./createProject.jsp");
			dispatcher.forward(request, response);
		} else {
			response.sendRedirect("./LogIn");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			if (request.getSession() != null && request.getSession().getAttribute("user") != null) {
				String name = StringEscapeUtils.escapeHtml4(request.getParameter("name"));
				name = URLEncoder.encode(name, "ISO-8859-1");
				name = URLDecoder.decode(name, "UTF-8");
				String deadline = request.getParameter("deadline");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				LocalDate date = new Date((sdf.parse(deadline)).getTime()).toLocalDate();
				User user = (User) request.getSession().getAttribute("user");
				Organization org = IOrganizationDAO.getDAO(DAOStorageSourse.DATABASE)
						.getOrgById(user.getOrganization().getId());
				

				request.setCharacterEncoding("UTF-8");
				//response.setCharacterEncoding("UTF-8");

				Project projectToAdd = new Project(name, date, org);

				int id = IProjectDAO.getDAO(DAOStorageSourse.DATABASE).addProject(projectToAdd,user.getId());
				// everything went well:
				response.sendRedirect("./dashboard");
			} else {
				response.sendRedirect("./LogIn");
			}
		} catch (ParseException | EfficientProjectDAOException | DBException | IOException e) {
			try {
				e.printStackTrace();
				response.sendRedirect("./error.jsp");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

	}

}
