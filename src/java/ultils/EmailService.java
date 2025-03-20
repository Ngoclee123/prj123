package ultils;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmailService {
    private static final String FROM_EMAIL = "vanbi12092004@gmail.com"; 
    private static final String APP_PASSWORD = "lhbt bqhr qjvw wnjl"; 
    private static final Logger LOGGER = Logger.getLogger(EmailService.class.getName());

    public static void sendEmail(String toEmail, String subject, String content) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, APP_PASSWORD);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject, "UTF-8");
            message.setText(content, "UTF-8");

            Transport.send(message);
            LOGGER.log(Level.INFO, "✅ Email đã gửi thành công đến {0}", toEmail);
        } catch (MessagingException e) {
            LOGGER.log(Level.SEVERE, "❌ Lỗi khi gửi email đến {0}: {1}", new Object[]{toEmail, e.getMessage()});
            e.printStackTrace();
        }
    }
}