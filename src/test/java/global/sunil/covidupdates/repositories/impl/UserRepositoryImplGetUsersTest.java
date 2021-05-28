package global.sunil.covidupdates.repositories.impl;

import global.sunil.covidupdates.repositories.UserRepository;
import global.sunil.covidupdates.repositories.dao.UserDao;
import global.sunil.covidupdates.repositories.dao.entites.UserEntity;
import global.sunil.covidupdates.repositories.dtos.UserInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Sunil on 2021-05-28 - २१:५२
 */
class UserRepositoryImplGetUsersTest {

    UserRepository userRepository;

    @BeforeEach
    void setup() {

        UserDao userDao = Mockito.mock(UserDao.class);
        Mockito.when(userDao.getAll()).thenReturn(List.of(new UserEntity((long) 121, "ram", "ram@req", null)));
        userRepository = new UserRepositoryImpl(null, userDao);
    }

    @Test
    @DisplayName("Should get success response")
    void shouldWorkWell() {

        List<UserInfo> users = userRepository.getUsers();
        assertNotNull(users);
        assertEquals(1, users.size());
    }
}