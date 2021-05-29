package global.sunil.covidupdates.repositories.impl;

import global.sunil.covidupdates.lib.mailclient.MailSender;
import global.sunil.covidupdates.lib.mappers.AppException;
import global.sunil.covidupdates.lib.utils.HelperUtils;
import global.sunil.covidupdates.repositories.ExceptionManager;
import global.sunil.covidupdates.repositories.UserRepository;
import global.sunil.covidupdates.repositories.constraints.AppConstraints;
import global.sunil.covidupdates.repositories.dao.UserDao;
import global.sunil.covidupdates.repositories.dao.entites.UserEntity;
import global.sunil.covidupdates.repositories.dao.entites.UserFilterCriteria;
import global.sunil.covidupdates.repositories.dao.impl.UserDaoImpl;
import global.sunil.covidupdates.repositories.dtos.SendEmailRequest;
import global.sunil.covidupdates.repositories.dtos.SendEmailResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.mail.MessagingException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Sunil on 2021-05-28 - २१:५२
 */
class UserRepositoryImplEmailUserTest {

    UserRepository userRepository;
    UserRepository userRepositoryForMailServiceCheck;

    @BeforeEach
    void setup() {

        UserDao userDao = Mockito.mock(UserDao.class);
        MailSender mailSender = Mockito.mock(MailSender.class);
        UserFilterCriteria userFilterCriteriaWithNoRecord = new UserFilterCriteria();
        userFilterCriteriaWithNoRecord.setSize(10);
        Mockito.when(userDao.search(userFilterCriteriaWithNoRecord)).thenReturn(Collections.emptyList());

        UserFilterCriteria userFilterCriteriaWithTwoRecord = new UserFilterCriteria();
        userFilterCriteriaWithTwoRecord.setSize(2);
        Mockito.when(userDao.search(userFilterCriteriaWithTwoRecord))
                .thenReturn(List.of(
                        new UserEntity((long) 1, "Ram", "ram@abc.com", null),
                        new UserEntity((long) 2, "Hari", "hari@abc.com", null)
                ));

        UserFilterCriteria userFilterCriteriaWithOneInvalidEmailRecord = new UserFilterCriteria();
        userFilterCriteriaWithOneInvalidEmailRecord.setSize(1);
        Mockito.when(userDao.search(userFilterCriteriaWithOneInvalidEmailRecord))
                .thenReturn(List.of(
                        new UserEntity((long) 1, "Ram", "ram@2131@abc.com", null),
                        new UserEntity((long) 2, "Hari", "hari@abc.com", null)
                ));

        UserFilterCriteria userFilterCriteriaWithLargeRecords = new UserFilterCriteria();
        userFilterCriteriaWithLargeRecords.setSize(null);
        Mockito.when(userDao.search(userFilterCriteriaWithLargeRecords))
                .thenReturn(UserDaoImpl.getInitialUserEntities());

        userRepository = new UserRepositoryImpl(mailSender, userDao);

        MailSender mailSenderImpl = Mockito.spy(new MailSender() {
            @Override
            public String getUserName() {
                return null;
            }

            @Override
            public String getPassword() {
                return null;
            }

            @Override
            public void send(List<String> recipients, String subject,
                             String message) throws MessagingException {

                throw new MessagingException();
            }

        });
        userRepositoryForMailServiceCheck = new UserRepositoryImpl(mailSenderImpl, userDao);
    }

    @Test
    @DisplayName("Should throw email subject is missing exception")
    void shouldThrowEmailSubjectIsMissing() {

        SendEmailRequest sendEmailRequest = new SendEmailRequest();
        AppException exception =
                assertThrows(AppException.class, () -> userRepository.sendEmails(sendEmailRequest));
        Assertions.assertEquals(
                ExceptionManager.UserError.EMAIL_SUBJECT_IS_MISSING.getDescription(),
                exception.getMessage());
    }

    @Test
    @DisplayName("Should throw email body is missing exception")
    void shouldThrowMessageIsMissing() {

        SendEmailRequest sendEmailRequest = new SendEmailRequest();
        sendEmailRequest.setSubject("Hi");
        AppException exception =
                assertThrows(AppException.class, () -> userRepository.sendEmails(sendEmailRequest));
        Assertions.assertEquals(
                ExceptionManager.UserError.MESSAGE_IS_MISSING.getDescription(),
                exception.getMessage());
    }

    @Test
    @DisplayName("Should throw count exceeded max limit exception")
    void shouldThrowCountExceededMaxLimit() {

        SendEmailRequest sendEmailRequest = new SendEmailRequest();
        sendEmailRequest.setSubject("Hi");
        sendEmailRequest.setMessage("How are you?");
        sendEmailRequest.setCount(AppConstraints.MAX_EMAIL_RECIPIENTS_NUMBERS+1);
        AppException exception =
                assertThrows(AppException.class, () -> userRepository.sendEmails(sendEmailRequest));
        Assertions.assertEquals(
                ExceptionManager.UserError.COUNT_EXCEEDED_MAX_LIMIT.getDescription(),
                exception.getMessage());
    }

    @Test
    @DisplayName("Should throw email subject exceeded max limit exception")
    void shouldThrowSubjectExceededMaxLimit() {

        SendEmailRequest sendEmailRequest = new SendEmailRequest();
        sendEmailRequest.setSubject(HelperUtils.getRandomString(AppConstraints.MAX_EMAIL_SUBJECT_LENGTH+1));
        sendEmailRequest.setMessage("How are you?");
        sendEmailRequest.setCount(2);
        AppException exception =
                assertThrows(AppException.class, () -> userRepository.sendEmails(sendEmailRequest));
        Assertions.assertEquals(
                ExceptionManager.UserError.EMAIL_SUBJECT_EXCEEDED_MAX_LENGTH_LIMIT.getDescription(),
                exception.getMessage());
    }

    @Test
    @DisplayName("Should throw email message exceeded max limit exception")
    void shouldThrowMessageExceededMaxLimit() {

        SendEmailRequest sendEmailRequest = new SendEmailRequest();
        sendEmailRequest.setSubject("Hi");
        sendEmailRequest.setMessage(HelperUtils.getRandomString(AppConstraints.MAX_EMAIL_BODY_LENGTH+1));
        sendEmailRequest.setCount(2);
        AppException exception =
                assertThrows(AppException.class, () -> userRepository.sendEmails(sendEmailRequest));
        Assertions.assertEquals(
                ExceptionManager.UserError.MESSAGE_EXCEEDED_MAX_LENGTH_LIMIT.getDescription(),
                exception.getMessage());
    }

    @Test
    @DisplayName("Should throw Could not found any users exception")
    void shouldThrowCouldNotFoundUsers() {

        SendEmailRequest sendEmailRequest = new SendEmailRequest();
        sendEmailRequest.setSubject("Hi");
        sendEmailRequest.setMessage("How are you?");
        sendEmailRequest.setCount(10);
        AppException exception =
                assertThrows(AppException.class, () -> userRepository.sendEmails(sendEmailRequest));
        Assertions.assertEquals(
                ExceptionManager.UserError.COULD_NOT_FOUND_USERS.getDescription(),
                exception.getMessage());
    }

    @Test
    @DisplayName("Should throw Could not send email exception")
    void shouldThrowCouldNotSendEmail() {

        SendEmailRequest sendEmailRequest = new SendEmailRequest();
        sendEmailRequest.setSubject("Hi");
        sendEmailRequest.setMessage("How are you?");
        sendEmailRequest.setCount(2);
        AppException exception =
                assertThrows(AppException.class, () -> userRepositoryForMailServiceCheck.sendEmails(sendEmailRequest));
        Assertions.assertEquals(
                ExceptionManager.UserError.COULD_NOT_SEND_EMAIL.getDescription(),
                exception.getMessage());
    }


    @Test
    @DisplayName("Should get success response")
    void shouldWorkWell() {

        SendEmailRequest sendEmailRequest = new SendEmailRequest();
        sendEmailRequest.setSubject("Hi");
        sendEmailRequest.setMessage("How are you?");
        sendEmailRequest.setCount(2);
        SendEmailResponse sendEmailResponse = userRepository.sendEmails(sendEmailRequest);
        assertNotNull(sendEmailResponse);
    }

    @Test
    @DisplayName("Should get success response with all valid emails")
    void shouldGetValidEmailResponse() {

        SendEmailRequest sendEmailRequest = new SendEmailRequest();
        sendEmailRequest.setSubject("Hi");
        sendEmailRequest.setMessage("How are you?");
        sendEmailRequest.setCount(2);
        SendEmailResponse sendEmailResponse = userRepository.sendEmails(sendEmailRequest);
        assertEquals(2, sendEmailResponse.getUsersWithValidEmail().size());
    }

    @Test
    @DisplayName("Should get success response with one invalid emails")
    void shouldGetOneInValidEmailResponse() {

        SendEmailRequest sendEmailRequest = new SendEmailRequest();
        sendEmailRequest.setSubject("Hi");
        sendEmailRequest.setMessage("How are you?");
        sendEmailRequest.setCount(1);
        SendEmailResponse sendEmailResponse = userRepository.sendEmails(sendEmailRequest);
        assertEquals(1, sendEmailResponse.getUsersWithInvalidEmail().size());
        assertEquals("ram@2131@abc.com", sendEmailResponse.getUsersWithInvalidEmail().get(0).getEmail());
    }

    @Test
    @DisplayName("Should get success response ")
    void successForLargeNumber() {

        SendEmailRequest sendEmailRequest = new SendEmailRequest();
        sendEmailRequest.setSubject("Hi");
        sendEmailRequest.setMessage("How are you?");
        sendEmailRequest.setCount(null);
        SendEmailResponse sendEmailResponse = userRepository.sendEmails(sendEmailRequest);
        assertEquals(2, sendEmailResponse.getUsersWithInvalidEmail().size());
        assertEquals(60, sendEmailResponse.getUsersWithValidEmail().size());
    }
}