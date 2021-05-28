package global.sunil.covidupdates.repositories.dao.impl;

import global.sunil.covidupdates.repositories.dao.UserDao;
import global.sunil.covidupdates.repositories.dao.entites.UserEntity;
import global.sunil.covidupdates.repositories.dao.entites.UserFilterCriteria;

import javax.enterprise.context.Dependent;
import javax.inject.Named;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Sunil on 2021-05-27 - २२:२७
 */
@Named
@Dependent
public class UserDaoImpl implements UserDao {

//    private static final String USERS_FILE_NAME = "json/users.json";
    static HashMap<Long, UserEntity> userIdMap = new HashMap<>();

    @Override
    public Optional<UserEntity> add(UserEntity userEntity) {
        getUserIdMap().put(userEntity.getId(), userEntity);
        writeUserIdMapToJson();
        return Optional.of(userEntity);
    }

    @Override
    public Optional<UserEntity> get(String id) {
        UserEntity entity = getUserIdMap().get(id);
        if (entity == null) {
            return Optional.empty();
        }
        return Optional.of(entity);
    }

    @Override
    public Optional<UserEntity> update(UserEntity userEntity) {
        UserEntity dbEntity = getUserIdMap().get(userEntity.getId());
        if (dbEntity == null) {
            return Optional.empty();
        } else {
            dbEntity = this.updateEditableFields(dbEntity, userEntity);
        }
        writeUserIdMapToJson();
        return Optional.of(dbEntity);

    }

    UserEntity updateEditableFields(UserEntity dbUserEntity, UserEntity userEntity) {
        dbUserEntity.setName(userEntity.getName());
        dbUserEntity.setEmail(userEntity.getEmail());
        dbUserEntity.setEmailValid(userEntity.getEmailValid());
        return dbUserEntity;
    }

    @Override
    public List<UserEntity> getAll() {
        return getUserIdMap().values().stream()
                .sorted((u1, u2) -> Long.compare(u2.getId(), u1.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserEntity> search(UserFilterCriteria filterCriteria) {
        Stream<UserEntity> userEntityStream = getUserIdMap().values().stream().filter(userEntity ->
                isMatchWithFilterCriteria(userEntity, filterCriteria));

        if (filterCriteria.getSize() != null) {
            return userEntityStream.limit(filterCriteria.getSize()).collect(Collectors.toList());
        }

        return userEntityStream
                .sorted((u1, u2) -> Long.compare(u2.getId(), u1.getId()))
                .collect(Collectors.toList());
    }

    boolean isMatchWithFilterCriteria(UserEntity userEntity, UserFilterCriteria filterCriteria) {
        if (filterCriteria == null) {
            return true;
        }
        if (filterCriteria.getId() != null && !filterCriteria.getId().equals(userEntity.getId())) {
            return false;
        }

        if (filterCriteria.getName() != null && !filterCriteria.getName().equals(userEntity.getName())) {
            return false;
        }

        return filterCriteria.getEmailValid() == null || filterCriteria.getEmailValid().equals(userEntity.getEmailValid());
    }

    HashMap<Long, UserEntity> getUserIdMap() {
        if (userIdMap.size() <= 0) {
            readFromJsonFileAndUpdateUserIdMap();
        }
        return userIdMap;
    }

    private void readFromJsonFileAndUpdateUserIdMap() {

//        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(USERS_FILE_NAME);
//             InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
//             BufferedReader br = new BufferedReader(streamReader);
//        ) {

        List<UserEntity> userEntities = getInitialUserEntities(); // Jsons.fromJsonToList(getUserJson(), UserEntity[].class);
        for (UserEntity userEntity : userEntities) {
            userIdMap.put(userEntity.getId(), userEntity);
        }
//            for (int i = 0; i < userEntities.size(); i++) {
//                UserEntity entity = userEntities.get(i);
//                entity.setId((long) i);
//                userIdMap.put(entity.getId(), entity);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
////            TODO
////            throw e;
//        }
    }

    private void writeUserIdMapToJson() {
//        try (FileWriter file = new FileWriter(USERS_FILE_NAME)) {
//            file.write(Jsons.toJsonList(new ArrayList<>(userIdMap.values())));
//            file.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    List<UserEntity> getInitialUserEntities() {
        return Arrays.asList(
                new UserEntity((long) 11, "test111@test.com", "user1111", null),
                new UserEntity((long) 12, "test112@test.com", "user1112", null),
                new UserEntity((long) 13, "test113@test.com", "user1113", null),
                new UserEntity((long) 14, "test114@test.com", "user1114", null),
                new UserEntity((long) 15, "test115@test.com", "user1115", null),
                new UserEntity((long) 16, "test116@test.com", "user1116", null),
                new UserEntity((long) 17, "test117@test.com", "user1117", null),
                new UserEntity((long) 18, "test118@test.com", "user1118", null),
                new UserEntity((long) 19, "test119@test.com", "user1119", null),
                new UserEntity((long) 20, "test120@test.com", "user1120", null),
                new UserEntity((long) 21, "test121@test.com", "user1121", null),
                new UserEntity((long) 22, "test122@test.com", "user1122", null),
                new UserEntity((long) 23, "test123@test.com", "user1123", null),
                new UserEntity((long) 24, "test124@test.com", "user1124", null),
                new UserEntity((long) 25, "test125@test.com", "user1125", null),
                new UserEntity((long) 26, "test126@test.com", "user1126", null),
                new UserEntity((long) 27, "test127@test.com", "user1127", null),
                new UserEntity((long) 28, "test128@test.com", "user1128", null),
                new UserEntity((long) 29, "test129@test.com", "user1129", null),
                new UserEntity((long) 30, "test130@test.com", "user1130", null),
                new UserEntity((long) 31, "test131@test.com", "user1131", null),
                new UserEntity((long) 32, "test132@test.com", "user1132", null),
                new UserEntity((long) 33, "test133@test.com", "user1133", null),
                new UserEntity((long) 34, "test134@test.com", "user1134", null),
                new UserEntity((long) 35, "test135@test.com", "user1135", null),
                new UserEntity((long) 36, "test136@test.com", "user1136", null),
                new UserEntity((long) 37, "test137@test.com", "user1137", null),
                new UserEntity((long) 38, "test138@test.com", "user1138", null),
                new UserEntity((long) 39, "test139@test.com", "user1139", null),
                new UserEntity((long) 40, "test140@test.com", "user1140", null),
                new UserEntity((long) 41, "test141@test.com", "user1141", null),
                new UserEntity((long) 42, "test142@test.com", "user1142", null),
                new UserEntity((long) 43, "test143@test.com", "user1143", null),
                new UserEntity((long) 44, "test144@test.com", "user1144", null),
                new UserEntity((long) 45, "test145@test.com", "user1145", null),
                new UserEntity((long) 46, "test146@test.com", "user1146", null),
                new UserEntity((long) 47, "test147@test.com", "user1147", null),
                new UserEntity((long) 48, "test148@test.com", "user1148", null),
                new UserEntity((long) 49, "test149@test.com", "user1149", null),
                new UserEntity((long) 50, "test150@test.com", "user1150", null),
                new UserEntity((long) 51, "test151@test.com", "user1151", null),
                new UserEntity((long) 52, "test152@test.com", "user1152", null),
                new UserEntity((long) 53, "test153@test.com", "user1153", null),
                new UserEntity((long) 54, "test154@test.com", "user1154", null),
                new UserEntity((long) 55, "test155@test.com", "user1155", null),
                new UserEntity((long) 56, "test156@test.com", "user1156", null),
                new UserEntity((long) 57, "test157@test.com", "user1157", null),
                new UserEntity((long) 58, "test158@test.com", "user1158", null),
                new UserEntity((long) 59, "test159@test.com", "user1159", null),
                new UserEntity((long) 60, "test160@test.com", "user1160", null),
                new UserEntity((long) 61, "test161@test.com", "user1161", null),
                new UserEntity((long) 62, "test162@test.com", "user1162", null),
                new UserEntity((long) 63, "test163@test.com", "user1163", null),
                new UserEntity((long) 64, "test164@test.com", "user1164", null),
                new UserEntity((long) 65, "test165@test.com", "user1165", null),
                new UserEntity((long) 66, "test166@test.com", "user1166", null),
                new UserEntity((long) 67, "test167@test.com", "user1167", null),
                new UserEntity((long) 68, "test168@test.com", "user1168", null),
                new UserEntity((long) 69, "test169@test.com", "user1169", null),
                new UserEntity((long) 70, "test170@test.com", "user1170", null)
        );
    }
}