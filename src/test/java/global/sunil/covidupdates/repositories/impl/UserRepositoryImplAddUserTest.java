package global.sunil.covidupdates.repositories.impl;

import global.sunil.covidupdates.lib.mappers.AppException;
import global.sunil.covidupdates.repositories.ExceptionManager;
import global.sunil.covidupdates.repositories.UserRepository;
import global.sunil.covidupdates.repositories.dao.UserDao;
import global.sunil.covidupdates.repositories.dao.entites.UserEntity;
import global.sunil.covidupdates.repositories.dtos.UserEmail;
import global.sunil.covidupdates.repositories.dtos.UserInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Sunil on 2021-05-28 - २१:५२
 */
class UserRepositoryImplAddUserTest {

    UserRepository userRepository;

    @BeforeEach
    void setup() {

        UserDao userDao = Mockito.mock(UserDao.class);
        UserEntity userEntity = new UserEntity((long) 121, "uncle bob", "uncle@bob.com", true);
        Mockito.when(userDao.add(userEntity)).thenReturn(Optional.of(userEntity));
        userRepository = new UserRepositoryImpl(null, userDao);
    }


    @Test
    @DisplayName("Should throw email is missing exception")
    void shouldThrowEmailIsMissing() {

        UserEmail userEmail = new UserEmail();
        AppException exception =
                assertThrows(AppException.class, () -> userRepository.addUser(userEmail));
        Assertions.assertEquals(
                ExceptionManager.UserError.EMAIL_IS_MISSING.getDescription(),
                exception.getMessage());
    }

    @Test
    @DisplayName("Should throw email is invalid exception")
    void shouldThrowEmailIsInvalid() {

        UserEmail userEmail = new UserEmail();
        userEmail.setEmail("uncle@bob@.com");
        AppException exception =
                assertThrows(AppException.class, () -> userRepository.addUser(userEmail));
        Assertions.assertEquals(
                ExceptionManager.UserError.EMAIL_IS_INVALID.getDescription(),
                exception.getMessage());
    }

    @Test
    @DisplayName("Should throw name is missing exception")
    void shouldThrowNameIsMissing() {

        UserEmail userEmail = new UserEmail();
        userEmail.setEmail("uncle@bob.com");
        AppException exception =
                assertThrows(AppException.class, () -> userRepository.addUser(userEmail));
        Assertions.assertEquals(
                ExceptionManager.UserError.NAME_IS_MISSING.getDescription(),
                exception.getMessage());
    }

    @Test
    @DisplayName("Should throw could not add user exception")
    void shouldThrowCouldNotAddUser() {

        UserEmail userEmail = new UserEmail();
        userEmail.setEmail("uncle@bob.com");
        userEmail.setName("Jhon papa");
        AppException exception =
                assertThrows(AppException.class, () -> userRepository.addUser(userEmail));
        Assertions.assertEquals(
                ExceptionManager.UserError.COULD_NOT_ADD_USER.getDescription(),
                exception.getMessage());
    }

    @Test
    @DisplayName("Should get success response")
    void shouldWorkWell() {

        UserEmail userEmail = new UserEmail();
        userEmail.setEmail("uncle@bob.com");
        userEmail.setName("uncle bob");
        UserInfo userInfo = userRepository.addUser(userEmail);
        assertNotNull(userInfo);
    }

}