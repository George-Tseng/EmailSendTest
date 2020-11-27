import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

public class JavaTest {
	public static void main(String[] args) {
		String mailHost = "smtp.gmail.com";
		int mailPort = 587;
		final String mailUser = "test@gmail.com";
		final String mailPassword = "password";
		String mailObj = "target@gmail.com";
		
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", mailHost);
		props.put("mail.smtp.port", mailPort);

		Session mailSession = Session.getDefaultInstance(props, new javax.mail.Authenticator(){
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mailUser, mailPassword);
			}
		});
		try {
			Message message = new MimeMessage(mailSession);
			message.setRecipients(
					Message.RecipientType.TO
					, InternetAddress.parse(mailObj));
			
			message.setSubject("主旨");
			message.setContent("內文", "text/html; Charset=UTF-8");
			
			Transport.send(message);
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e2) {
			e2.printStackTrace();
		}
	}
}
