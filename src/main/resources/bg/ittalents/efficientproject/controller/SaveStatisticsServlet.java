package bg.ittalents.efficientproject.controller;

import java.io.File;
import java.io.IOException;
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
       

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
		String home = System.getProperty("user.home");
		System.out.println(home);
		String fileDir=home+"/Downloads/" + "statistics" + ".pdf"; 
		String statistics=request.getParameter("statistics");
			PDFGenerator.createPdf(fileDir, statistics);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}



}
