package bg.ittalents.efficientproject.util;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import bg.ittalents.efficientproject.model.exception.EfficientProjectDAOException;

public class SendingMails {

	public static boolean sendEmail(String recepient,String subject,String content) throws EfficientProjectDAOException {

		final String username = "mimiittalents@gmail.com";
		final String  password= "123456qna";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("from-email@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recepient));
			message.setSubject(subject);
			message.setText(content);

			Transport.send(message);
			System.out.println("Done");
			return true;

		} catch (MessagingException e) {
			throw new EfficientProjectDAOException("sending email failed!",e);
		}

	}

}
