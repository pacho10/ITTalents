package bg.ittalents.efficientproject.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bg.ittalents.efficientproject.model.dao.INFO;
import bg.ittalents.efficientproject.model.exception.DBException;
import bg.ittalents.efficientproject.model.exception.EffPrjDAOException;
import bg.ittalents.efficientproject.model.interfaces.DAOStorageSourse;
import bg.ittalents.efficientproject.model.interfaces.IUserDAO;
import bg.ittalents.efficientproject.model.pojo.User;

@WebServlet("/ImgOutputServlet")
public class ReadPictureFromFileSysServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String IMAGES_PATH = INFO.IMAGES_PATH;;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.addHeader("Content-Type", "image/jpeg");

			if (request.getSession(false) == null) {
				response.sendRedirect("/LogIn");
				return;
			}

			if (request.getSession().getAttribute("user") != null) {
				User userSession = (User) request.getSession().getAttribute("user");
				int userId = Integer.parseInt(request.getParameter("userid"));

				if (userSession.getId() != userId) {
					request.getRequestDispatcher("errorNotAuthorized.jsp").forward(request, response);
					return;
				}

				User user = IUserDAO.getDAO(DAOStorageSourse.DATABASE).getUserById(userId);
				String avatarPath = user.getAvatarPath();
				File imgFile = new File(avatarPath);

				try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(imgFile));
						ServletOutputStream fos = response.getOutputStream()) {
					do {
						int b = bis.read();
						if (b != -1) {
							fos.write(b);
							b = bis.read();
						} else {
							break;
						}
					} while (true);
				}
			} else {
				request.getRequestDispatcher("error2.jsp").forward(request, response);
			}
		} catch (EffPrjDAOException | DBException | IOException | ServletException e) {
		}

	}



}
