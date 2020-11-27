import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.smtp.SMTPSendFailedException;

public class JavaEmailTester {
	public static void main(String[] args) {
		/* 寄件者使用的SMTP Mail Server，有單日發信上限 */
		String mailHost = "smtp.gmail.com";
		/* TLS用port，不啟用TLS則需參考Email服務商的說明 */
		final Integer mailPort = 587;
		/* 寄件者email帳號 */
		final String mailUser = "test@gmail.com";
		/* 寄件者密碼或應用程式密碼 */
		final String mailPassword = "testPassword";
		/* 收件者的稱呼 */
		String nickname = "testName";
		/* 收件者email帳號 */
		String mailObj = "target@gmail.com";
		/* 超連結 */
		String activeUrl = "www.google.com.tw";
		/* email內文 */
		String mailContext = "親愛的 " + nickname + " ！<br />" 
		+ "您即將完成本服務的註冊流程，請點選下方的連結以完成帳戶的啟用"
		+ "<br /><a href=" + activeUrl + ">" + activeUrl + "</a>";
		
		Properties props = new Properties();
		/* SMTP設定 */
		props.put("mail.smtp.auth", "true");
		/* 啟用TLS */
		props.put("mail.smtp.starttls.enable", "true");
		/* 設定寄件者email */
		props.put("mail.smtp.host", mailHost);
		/* 設定寄件所需的port */
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
			
			/* 設定email主旨 */
			message.setSubject("您的橙皮驗證連結在此");
			/* 設定email內容與編碼 */
			message.setContent(mailContext, "text/html; Charset=UTF-8");
			
			/* 正式送出 */
			Transport.send(message);
			/* 測試用訊息 */
			System.out.println("信件已成功寄出給："+mailObj);
		} catch (AddressException ae) {
			/* email地址例外 */
			System.out.println("信件無法寄出！錯誤資訊為："+ae.getMessage());
		} catch (NoSuchProviderException nspe) {
			System.out.println("信件無法寄出！錯誤資訊為："+nspe.getMessage());
		} catch(SMTPSendFailedException smtpsfe) {
			/* 應對免費Mail Server單日發送到達500次時會出現的錯誤 */
			System.out.println("信件無法寄出！錯誤資訊為："+smtpsfe.getMessage());
		} catch (MessagingException me) {
			System.out.println("信件無法寄出！錯誤資訊為："+me.getMessage());
		}
	}
}
