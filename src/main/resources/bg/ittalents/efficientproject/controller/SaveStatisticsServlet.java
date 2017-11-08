package bg.ittalents.efficientproject.controller;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.DocumentException;

import bg.ittalents.efficientproject.util.PDFGenerator;

/**
 * Servlet implementation class SaveStatisticsServlet
 */
@WebServlet("/SaveStatistics")
public class SaveStatisticsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			if (request.getSession(false) == null || request.getSession().getAttribute("user") == null) {
				response.sendRedirect("./LogIn");
				return;
			}
			String statisticsParam = request.getParameter("statistics");
			String home = System.getProperty("user.home");
			if (statisticsParam != null) {
				System.out.println(home);
				String fileDir = home + "/Downloads/" + "statistics-"+LocalDate.now() + ".pdf";
				String statistics = request.getParameter("statistics");
				PDFGenerator.createPdf(fileDir, statistics);
			} else {
				request.getRequestDispatcher("error2.jsp").forward(request, response);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
}
