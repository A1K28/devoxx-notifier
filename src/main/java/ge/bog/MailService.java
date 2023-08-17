package ge.bog;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import java.util.Properties;

public class MailService {
    private final Properties properties;
    private final Session session;

    public MailService() {
        properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", 587);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.smtp.starttls.required", true);
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.connectiontimeout", 30000);
        properties.put("mail.smtp.timeout", 30000);
        properties.put("mail.smtp.writetimeout", 30000);
//        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        "akhorbaladzebogproject@gmail.com", "pfjuhzmhjtgnkwge");
            }
        });
    }

    public void notifyDevoxx(String name) throws MessagingException {
        Logger.info("Sending devoxx notify email with name: " + name);
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("akhorbaladzebogproject@gmail.com"));
//        String addressesString = "aleksandrekhorbaladze@gmail.com,alkhorbaladze@bog.ge";
        String addressesString = "aleksandrekhorbaladze@gmail.com,alkhorbaladze@bog.ge," +
                "tsoniani@bog.ge,snatroshvili@bog.ge";
        InternetAddress[] addresses = InternetAddress.parse(addressesString);
        message.setRecipients(Message.RecipientType.TO, addresses);
        message.setSubject("Devoxx ბილეთები - Conference ~ Batch 1 / 2");

        String msg = String.format("ავტომატური შეტყობინება:" +
                " Devoxx Conference-ის, სახელად - %s, ბილეთების შეძენა უკვე შესაძლებელია.", name);

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(msg, "text/html; charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);

        Transport.send(message);
        Logger.info("Successfully sent Devoxx notify email to: " + addressesString);
    }

    public void sendErrorMail(String m) throws MessagingException {
        Logger.info("Sending error email...");
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("akhorbaladzebogproject@gmail.com"));
        String addressesString = "aleksandrekhorbaladze@gmail.com";
        InternetAddress[] addresses = InternetAddress.parse(addressesString);
        message.setRecipients(Message.RecipientType.TO, addresses);
        message.setSubject("Devoxx ერორი - Conference ~ Batch 1 / 2");

        String msg = String.format("ავტომატური შეტყობინება:" +
                " Devoxx Conference-ის job-ში ერორია, გთხოვთ შეამოწმოთ შეცდომა: %s", m);

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(msg, "text/html; charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);

        Transport.send(message);
        Logger.info("Successfully sent error email to: " + addressesString);
    }
}
