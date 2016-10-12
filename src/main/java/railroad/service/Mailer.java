package railroad.service;

import org.jboss.logging.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Mailer class - used to send e-mails using GoogleMail ssl
 *
 * @author VBuevich
 */
public class Mailer {

    private static final Logger LOGGER = Logger.getLogger(Mailer.class);

    private final String username = "javaschool.railroad";
    private final String password = "railroad";
    public Properties props;

    /**
     * Constructor
     */
    public Mailer() {
        props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
    }

    /**
     * Method that sends an email
     *
     * @param subject Subject of message
     * @param text message body
     * @param fromEmail "from" field
     * @param toEmail "to" field
     * @return true if success otherwise false
     */
    public Boolean send(String subject, String text, String fromEmail, String toEmail) throws Exception {
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        Boolean isSuccess = false;

            Message message = new MimeMessage(session);
            InternetAddress fEmail = new InternetAddress(fromEmail);
            fEmail.validate();
            InternetAddress tEmail = new InternetAddress(toEmail);
            tEmail.validate();

            message.setFrom(fEmail); // "from" field
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail)); // "to" field
            message.setSubject(subject); // "subject"
            message.setText(text); // text of e-mail

            Transport.send(message); // sending message

            isSuccess = true; // if we have successfully sent the message - then we return true

        return isSuccess;
    }
}

