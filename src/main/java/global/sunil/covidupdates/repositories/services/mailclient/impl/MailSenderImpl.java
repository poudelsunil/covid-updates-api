package global.sunil.covidupdates.repositories.services.mailclient.impl;

import global.sunil.covidupdates.lib.mailclient.MailSender;
import global.sunil.covidupdates.lib.utils.HelperUtils;
import global.sunil.covidupdates.repositories.ExceptionManager;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.MessagingException;

/**
 * @author Sunil on 2021-05-28 - १५:३२
 */
@Named
@Dependent
public class MailSenderImpl extends MailSender {

    @Inject
    @ConfigProperty(name = "SMTP_SERVER_USER_NAME", defaultValue = "")
    private String smtpUserName;

    @Inject
    @ConfigProperty(name = "SMTP_SERVER_PASSWORD", defaultValue = "")
    private String smtpUserPassword;

    @Override
    public String getUserName() {
        return smtpUserName;
    }

    @Override
    public String getPassword() {
        return smtpUserPassword;
    }

    @Override
    public void send(String[] recipients, String subject,
                     String message, String from) throws MessagingException {
        if(HelperUtils.isBlankOrNull(smtpUserName)){
            ExceptionManager.throwException(ExceptionManager.EmailSenderError.SMTP_USER_NAME_IS_MISSING);
        }

        if(HelperUtils.isBlankOrNull(smtpUserPassword)){
            ExceptionManager.throwException(ExceptionManager.EmailSenderError.SMTP_USER_PASSWORD_IS_MISSING);
        }
        super.send(recipients, subject, message, from);
    }
}
