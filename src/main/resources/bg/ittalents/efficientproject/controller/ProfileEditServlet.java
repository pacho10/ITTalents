package bg.ittalents.efficientproject.controller;

import static org.apache.commons.lang3.StringEscapeUtils.escapeHtml4;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
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
import bg.ittalents.efficientproject.model.exception.EfficientProjectDAOException;
import bg.ittalents.efficientproject.model.interfaces.DAOStorageSourse;
import bg.ittalents.efficientproject.model.interfaces.IUserDAO;
import bg.ittalents.efficientproject.model.pojo.User;
import bg.ittalents.efficientproject.util.CredentialsChecks;
import bg.ittalents.efficientproject.util.Encrypter;

@WebServlet("/ProfileEdit")
@MultipartConfig(maxFileSize = 16177215) // upload file's size up to 16MB

public class ProfileEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String IMAGES_PATH = INFO.IMAGES_PATH;

	private static final DAOStorageSourse SOURCE_DATABASE = DAOStorageSourse.DATABASE;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setCharacterEncoding("UTF-8");
			request.setCharacterEncoding("UTF-8");
			
			if (request.getSession(false) == null || request.getSession(false).getAttribute("user") == null) {
				response.sendRedirect("/LogIn");
				return;
			}
			// disable cache:
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
			response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
			response.setHeader("Expires", "0"); // Proxies.
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("./profileEdit.jsp");
			dispatcher.forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
			try {
				request.getRequestDispatcher("error.jsp").forward(request, response);
				e.printStackTrace();
			} catch (IOException | ServletException e1) {
				e1.printStackTrace();
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setCharacterEncoding("UTF-8");
			request.setCharacterEncoding("UTF-8");

			if (request.getSession(false) == null || request.getSession(false).getAttribute("user") == null) {
				response.sendRedirect("/LogIn");
				return;
			}

				User user = (User) request.getSession().getAttribute("user");
				int userId = user.getId();

				String firstName = escapeHtml4(request.getParameter("first-name")).trim();
				String lastName = escapeHtml4(request.getParameter("last-name")).trim();
				String email = escapeHtml4(request.getParameter("email")).trim();
				String oldPass = escapeHtml4(request.getParameter("old-password"));
				String newPass = escapeHtml4(request.getParameter("new-password"));
				String reNewPass = escapeHtml4(request.getParameter("rep-new-password"));
				Part avatarPart = request.getPart("avatar");

				RequestDispatcher dispatcher = request.getRequestDispatcher("./profileEdit.jsp");
				// check if there is actually uploaded file:
				if (avatarPart.getSize() != 0) {
					String avatarPath = IMAGES_PATH + File.separator + userId + ".jpg";
					File myFile = new File(avatarPath);

//					String mimeType = Magic.getMagicMatch(myFile, false).getMimeType();// TODO size and type check!!!
//					System.out.println(mimeType);
//					if (!mimeType.equals("jpeg") || !mimeType.equals("png")) {
						if (!myFile.exists()) {
							myFile.createNewFile();
						}
						try (BufferedInputStream fis = new BufferedInputStream(avatarPart.getInputStream());
								BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(myFile))) {
							int b = fis.read();
							while (b != -1) {
								fos.write(b);
								b = fis.read();
							}
						}
						user.setAvatarPath(avatarPath);
//					} else {
//						request.setAttribute("errorMessage", "This file should be .jpeg or .png format!");
//						dispatcher.forward(request, response);
//						return;
//					}
				}

				boolean firstNameNotEmptyandDifferent = firstName.length() > 0 && !firstName.equals(user.getFirstName());
				boolean lastNameNotEmptyAndDifferent = lastName.length() > 0 && !lastName.equals(user.getLastName());
				boolean emailNotEmptyAndDifferent = email.length() > 0 && !email.equals(user.getEmail());
				boolean newPassNotEmpty = newPass.trim().length() > 0;
				boolean oldPassNotEmpty = oldPass.trim().length() > 0;

				if (firstNameNotEmptyandDifferent) {
					user.setFirstName(firstName);
					System.out.println("1change done");
				}

				if (lastNameNotEmptyAndDifferent) {
					user.setLastName(lastName);
					System.out.println("2change done");
				}

				if (emailNotEmptyAndDifferent) {
					// check if the email is with valid characters:
					if (!CredentialsChecks.isMailValid(email)) {
						request.setAttribute("errorMessage", "Invalid e-mail! Try Again");
						dispatcher.forward(request, response);
						return;
					}
					// check if the email is already taken:
					if (IUserDAO.getDAO(SOURCE_DATABASE).isThereSuchAUser(email)) {
						request.setAttribute("errorMessage",
								"User with such email already exists, use another email !");
						dispatcher.forward(request, response);
						return;
					}
					user.setEmail(email);
					System.out.println("3change done");
				}

				if (newPassNotEmpty) {
					if (!oldPassNotEmpty) {
						request.setAttribute("errorMessage",
								"You cannot change the password without confirming the old one");
						dispatcher.forward(request, response);
						return;
					}
					// check if there is old pass input and if there is check if it matches the real
					// one
					if (oldPassNotEmpty && !Encrypter.encrypt(oldPass).equals(user.getPassword())) {
						request.setAttribute("errorMessage", "You entered wrong old password,try again!");
						dispatcher.forward(request, response);
						return;
					}

					// check if there is new pass input and if there is, check if both match
					if (!newPass.equals(reNewPass)) {
						request.setAttribute("errorMessage", "New passwords do no match please make sure they do!");
						dispatcher.forward(request, response);
						return;
					}
					// check the strength of the new pass:
					if (!CredentialsChecks.isPaswordStrong(newPass)) {
						request.setAttribute("errorMessage",
								"New password must contain 5 symbols and at least one number and letter");
						dispatcher.forward(request, response);
						return;
					}
					user.setPassword(Encrypter.encrypt(newPass));
					System.out.println("4change done");
				}


				if (firstNameNotEmptyandDifferent || lastNameNotEmptyAndDifferent || emailNotEmptyAndDifferent || newPassNotEmpty) {
					System.out.println("change done");
					IUserDAO.getDAO(SOURCE_DATABASE).updateUsersDetails(user);
					request.getSession().setAttribute("user", user);
				}
				response.sendRedirect("./Profile");
		} catch (ServletException | IOException | DBException | EfficientProjectDAOException  e) {
			try {
				request.getRequestDispatcher("error.jsp").forward(request, response);
				e.printStackTrace();
			} catch (IOException | ServletException e1) {
				e1.printStackTrace();
			}
		}
	}

}
