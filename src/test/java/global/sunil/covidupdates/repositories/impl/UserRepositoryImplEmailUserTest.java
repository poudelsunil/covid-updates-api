package global.sunil.covidupdates.repositories.impl;

import global.sunil.covidupdates.lib.mailclient.MailSender;
import global.sunil.covidupdates.lib.mappers.AppException;
import global.sunil.covidupdates.repositories.ExceptionManager;
import global.sunil.covidupdates.repositories.UserRepository;
import global.sunil.covidupdates.repositories.dao.UserDao;
import global.sunil.covidupdates.repositories.dao.entites.UserEntity;
import global.sunil.covidupdates.repositories.dao.entites.UserFilterCriteria;
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
import static org.mockito.ArgumentMatchers.any;

/**
 * @author Sunil on 2021-05-28 - २१:५२
 */
class UserRepositoryImplEmailUserTest {

    UserRepository userRepository;

    @BeforeEach
    void setup() {

        UserDao userDao = Mockito.mock(UserDao.class);
        MailSender mailSender = Mockito.mock(MailSender.class);
        UserFilterCriteria userFilterCriteriaWithNoRecord = new UserFilterCriteria();
        userFilterCriteriaWithNoRecord.setSize(100);
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

        userRepository = new UserRepositoryImpl(mailSender, userDao);
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
    @DisplayName("Should throw Could not get countries. exception")
    void shouldThrowCouldNotGetCountries() {

        SendEmailRequest sendEmailRequest = new SendEmailRequest();
        sendEmailRequest.setSubject("Hi");
        sendEmailRequest.setMessage("How are you?");
        sendEmailRequest.setCount(2);
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
        sendEmailRequest.setCount(100);  // mocking to to return user if count is 100
        AppException exception =
                assertThrows(AppException.class, () -> userRepository.sendEmails(sendEmailRequest));
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
}