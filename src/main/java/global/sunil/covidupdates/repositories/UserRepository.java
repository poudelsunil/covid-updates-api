package global.sunil.covidupdates.repositories;

import global.sunil.covidupdates.repositories.dtos.SendEmailRequest;
import global.sunil.covidupdates.repositories.dtos.SendEmailResponse;
import global.sunil.covidupdates.repositories.dtos.UserEmail;
import global.sunil.covidupdates.repositories.dtos.UserInfo;

import java.util.List;

/**
 * @author Sunil on 2021-05-25 - १५:२२
 */
public interface UserRepository {

    UserInfo addUser(UserEmail userEmail);
    List<UserInfo> getUsers();
    SendEmailResponse sendEmails(SendEmailRequest request);
}