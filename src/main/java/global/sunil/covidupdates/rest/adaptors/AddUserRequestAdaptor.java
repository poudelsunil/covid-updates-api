package global.sunil.covidupdates.rest.adaptors;

import global.sunil.covidupdates.lib.utils.RequestAdaptor;
import global.sunil.covidupdates.repositories.dtos.UserEmail;
import global.sunil.covidupdates.repositories.dtos.UserInfo;

import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.json.JsonObject;

/**
 * @author Sunil on 2021-05-28 - १७:४२
 */
@Named
public class AddUserRequestAdaptor implements RequestAdaptor<UserEmail> {

    @Override
    public UserEmail toServiceObject(JsonObject jsonObject) {
        UserEmail userEmail = new UserEmail();
        userEmail.setName(jsonObject.getString("name"));
        userEmail.setEmail(jsonObject.getString("email"));
        return userEmail;
    }
}
