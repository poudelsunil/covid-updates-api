package global.sunil.covidupdates.lib.mailclient;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

/**
 * @author Sunil on 2021-05-28 - १३:२०
 */
public abstract class MailSender {

    private static final boolean DEBUG = true;
    private static final String SMTP_HOST_NAME = "smtp.gmail.com";
    private static final String SMTP_PORT = "465";
    private static final String defaultFromAddress = "sunil@gmail.com";
    private static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";


    public abstract String getUserName();

    public abstract String getPassword();

    public void send(List<String> recipients, String subject,
                     String message) throws MessagingException {

        send(recipients.toArray(new String[0]), subject, message, defaultFromAddress);
    }

    public void send(String[] recipients, String subject,
                     String message, String from) throws MessagingException {

        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST_NAME);
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.socketFactory.port", SMTP_PORT);
        props.put("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.put("mail.smtp.socketFactory.fallback", "false");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(getUserName(), getPassword());
                    }
                });

        session.setDebug(DEBUG);

        Message msg = new MimeMessage(session);
        InternetAddress addressFrom = new InternetAddress(from);
        msg.setFrom(addressFrom);

        InternetAddress[] addressTo = new InternetAddress[recipients.length];
        for (int i = 0; i < recipients.length; i++) {
            addressTo[i] = new InternetAddress(recipients[i]);
        }
        msg.setRecipients(Message.RecipientType.TO, addressTo);

        msg.setSubject(subject);
        msg.setContent(message, "text/plain");
        Transport.send(msg);
    }
}
