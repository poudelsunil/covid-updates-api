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

        Stream<UserEntity> userEntityStream = getUserIdMap().values().stream()
                .filter(userEntity -> isMatchWithFilterCriteria(userEntity, filterCriteria))
                .sorted((u1, u2) -> Long.compare(u2.getId(), u1.getId()));

        if (filterCriteria.getSize() != null || filterCriteria.getSize() != 0) {
            return userEntityStream.limit(filterCriteria.getSize()).collect(Collectors.toList());
        }

        return userEntityStream.collect(Collectors.toList());
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

    public static List<UserEntity> getInitialUserEntities() {
        return Arrays.asList(
                new UserEntity((long) 11, "user1111","test111@test.com",  true),
                new UserEntity((long) 12, "user1112","test112@test.com",  true),
                new UserEntity((long) 13, "user1113","test113@test.com",  true),
                new UserEntity((long) 14, "user1114","test114@test.com",  true),
                new UserEntity((long) 15, "user1115","test115@test.com",  true),
                new UserEntity((long) 16, "user1116","test116@test.com",  true),
                new UserEntity((long) 17, "user1117","test117@test.com",  true),
                new UserEntity((long) 18, "user1118","test118@test.com",  true),
                new UserEntity((long) 19, "user1119","test119@test.com",  true),
                new UserEntity((long) 20, "user1120","test120@test.com",  true),
                new UserEntity((long) 21, "user1121","test121@test.com",  true),
                new UserEntity((long) 22, "user1122","test122@test.com",  true),
                new UserEntity((long) 23, "user1123","test123@test.com",  true),
                new UserEntity((long) 24, "user1124","test124@test.com",  true),
                new UserEntity((long) 25, "user1125","test125@test.com",  true),
                new UserEntity((long) 26, "user1126","test126@test.com",  true),
                new UserEntity((long) 27, "user1127","test127@test.com",  true),
                new UserEntity((long) 28, "user1128","test128@test.com",  true),
                new UserEntity((long) 29, "user1129","test129@test.com",  true),
                new UserEntity((long) 30, "user1130","test130@test.com",  true),
                new UserEntity((long) 31, "user1131","test131@test.com",  true),
                new UserEntity((long) 32, "user1132","test132@test.com",  true),
                new UserEntity((long) 33, "user1133","test133@test.com",  true),
                new UserEntity((long) 34, "user1134","test134@test.com",  true),
                new UserEntity((long) 35, "user1135","test135@test.com",  true),
                new UserEntity((long) 36, "user1136","test136@test.com",  true),
                new UserEntity((long) 37, "user1137","test137@test.com",  true),
                new UserEntity((long) 38, "user1138","test138@test.com",  true),
                new UserEntity((long) 39, "user1139","test139@test.com",  true),
                new UserEntity((long) 40, "user1140","test140@test.com",  true),
                new UserEntity((long) 41, "user1141","test141@test.com",  true),
                new UserEntity((long) 42, "user1142","test142@test.com",  true),
                new UserEntity((long) 43, "user1143","test143@test.com",  true),
                new UserEntity((long) 44, "user1144","test144@test.com",  true),
                new UserEntity((long) 45, "user1145","test145@test.com",  true),
                new UserEntity((long) 46, "user1146","test146@test.com",  true),
                new UserEntity((long) 47, "user1147","test147@test.com",  true),
                new UserEntity((long) 48, "user1148","test148@test.com",  true),
                new UserEntity((long) 49, "user1149","test149@test.com",  true),
                new UserEntity((long) 50, "user1150","test150@test.com",  true),
                new UserEntity((long) 51, "user1151","test151@test.com",  true),
                new UserEntity((long) 52, "user1152","test152@test.com",  true),
                new UserEntity((long) 53, "user1153","test153@test.com",  true),
                new UserEntity((long) 54, "user1154","test154@test.com",  true),
                new UserEntity((long) 55, "user1155","test155@test.com",  true),
                new UserEntity((long) 56, "user1156","test156@test.com",  true),
                new UserEntity((long) 57, "user1157","test157@test.com",  true),
                new UserEntity((long) 58, "user1158","test158@test.com",  true),
                new UserEntity((long) 59, "user1159","test159@test.com",  true),
                new UserEntity((long) 60, "user1160","test160@test.com",  true),
                new UserEntity((long) 61, "user1161","test161@test.com",  true),
                new UserEntity((long) 62, "user1162","test162@test.com",  true),
                new UserEntity((long) 63, "user1163","test163@test.com",  true),
                new UserEntity((long) 64, "user1164","test164@test.com",  true),
                new UserEntity((long) 65, "user1165","test165@test.com",  true),
                new UserEntity((long) 66, "user1166","test166@test.com",  true),
                new UserEntity((long) 67, "user1167","test167@test.com",  true),
                new UserEntity((long) 68, "user1168","test168@test.com",  true),
                new UserEntity((long) 69, "user1169","test169@test.com",  true),
                new UserEntity((long) 70, "user1170","test170@test.com",  true),
                new UserEntity((long) 70, "user1171","test1@71@test.com",  false),
                new UserEntity((long) 70, "user1172","test@172@test.com",  false)
        );
    }
}