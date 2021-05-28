package global.sunil.covidupdates.repositories.dao;

import global.sunil.covidupdates.repositories.dao.entites.UserEntity;
import global.sunil.covidupdates.repositories.dao.entites.UserFilterCriteria;

import java.util.List;
import java.util.Optional;

/**
 * @author Sunil on 2021-05-27 - २२:२७
 */
public interface UserDao {

    Optional<UserEntity> add(UserEntity userEntity);
    Optional<UserEntity> get(String id);
    Optional<UserEntity> update(UserEntity userEntity);
    List<UserEntity> getAll();
    List<UserEntity> search(UserFilterCriteria filterCriteria);
}