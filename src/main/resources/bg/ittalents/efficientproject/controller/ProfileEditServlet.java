package bg.ittalents.efficientproject.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import bg.ittalents.efficientproject.model.dao.INFO;
import bg.ittalents.efficientproject.model.exception.DBException;
import bg.ittalents.efficientproject.model.exception.EffPrjDAOException;
import bg.ittalents.efficientproject.model.interfaces.DAOStorageSourse;
import bg.ittalents.efficientproject.model.interfaces.IUserDAO;
import bg.ittalents.efficientproject.model.pojo.User;
import bg.ittalents.efficientproject.util.Encrypter;

@WebServlet("/ProfileEdit")
@MultipartConfig(maxFileSize = 16177215) // upload file's size up to 16MB

public class ProfileEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String IMAGES_PATH = INFO.IMAGES_PATH;

	private static final DAOStorageSourse SOURCE_DATABASE = DAOStorageSourse.DATABASE;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {

			// disable cache:
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
			response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
			response.setHeader("Expires", "0"); // Proxies.

			RequestDispatcher dispatcher = request.getRequestDispatcher("./profileEdit.jsp");
			dispatcher.forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
			try {
				response.sendRedirect("./error.jsp");
			} catch (IOException e1) {
				e1.printStackTrace();// TODO is this ok?
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			if (request.getSession(false) == null) {
				response.sendRedirect("/LogIn");
				return;
			}
			User user = (User) request.getSession().getAttribute("user");
			int userId = user.getId();

			String firstName = request.getParameter("first-name");
			String lastName = request.getParameter("last-name");
			String email = request.getParameter("email");
			String oldPass = request.getParameter("old-password");
			String newPass = request.getParameter("new-password");
			String reNewPass = request.getParameter("rep-new-password");
			Part avatarPart = request.getPart("avatar");

			// check if there is actually uploaded file:
			if (avatarPart.getSize() != 0) {
				InputStream fis = new BufferedInputStream(avatarPart.getInputStream());
				String avatarPath = IMAGES_PATH + File.separator + userId + ".jpg";
				File myFile = new File(avatarPath);
				if (!myFile.exists()) {
					myFile.createNewFile();
				}
				FileOutputStream fos = new FileOutputStream(myFile);
				int b = fis.read();
				while (b != -1) {
					fos.write(b);
					b = fis.read();
				}
				fis.close();
				fos.close();
				user.setAvatarPath(avatarPath);
			}

			/*
			 * check if the new email already exists in the db
			 * 
			 */
			RequestDispatcher dispatcher = request.getRequestDispatcher("./profileEdit.jsp");

			if (!SignUpServlet.isMailValid(email)) {
				request.setAttribute("errorMessage", "Invalid e-mail! Try Again");
				dispatcher.forward(request, response);
				return;
			}

			if (oldPass.trim().length() > 0 && !Encrypter.encrypt(oldPass).equals(user.getPassword())) {
				request.setAttribute("errorMessage", "You entered wrong password,try again!");
				dispatcher.forward(request, response);
				return;
			}

			if (newPass.trim().length() > 0 && !newPass.equals(reNewPass)) {
				request.setAttribute("errorMessage", "Passwords do no match please make sure they do!");
				dispatcher.forward(request, response);
				return;
			}

			if (newPass.trim().length() > 0 && !SignUpServlet.isPaswordStrong(newPass)) {
				request.setAttribute("errorMessage",
						"Password must contain 5 symbols and at least one number and letter");
				dispatcher.forward(request, response);
				return;
			}

			// check if the email is already taken:
			if (!email.equals(user.getEmail()) && IUserDAO.getDAO(SOURCE_DATABASE).isThereSuchAnUser(email)) {
				request.setAttribute("errorMessage", "User with such email already exists, use another email !");
				dispatcher.forward(request, response);
				return;
			}

			// update information:
			user.setFirstName(firstName);
			user.setLastName(lastName);
			if (newPass.trim().length() > 0) {
				user.setPassword(Encrypter.encrypt(newPass));
			}
			// user.setAvatarPath(avatarPath);
			user.setEmail(email);

			IUserDAO.getDAO(SOURCE_DATABASE).updateUsersDetails(user);
			// everythig went well:
			request.getSession().setAttribute("user", user);
			System.out.println(user);
			response.sendRedirect("./Profile");
		} catch (ServletException | IOException | DBException | EffPrjDAOException e) {
			try {
				response.sendRedirect("./error.jsp");
			} catch (IOException e1) {
				e1.printStackTrace();// TODO is this ok?
			}
		}
	}

}
