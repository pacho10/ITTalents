package bg.ittalents.efficientproject.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bg.ittalents.efficientproject.model.pojo.User;

@WebServlet("/ImgOutputServlet") // TODO ne iskam da e dostypno ot url????
@MultipartConfig

public class ReadPictureFromFileSysServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public static final String IMAGES_PATH = "F:" + File.separator + "Java" + File.separator + "final-project-img";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getSession(false) == null) {
			response.sendRedirect("/LogIn");
		}
		User user = (User) request.getSession().getAttribute("user");
		String avatarPath = user.getAvatarPath();
		File imgFile = new File(avatarPath);
		try (InputStream fis = new FileInputStream(imgFile); ServletOutputStream fos = response.getOutputStream()) {
			int b = fis.read();
			while (b != -1) {
				fos.write(b);
				b = fis.read();
			}

		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
