package global.sunil.covidupdates.repositories.dtos;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Sunil on 2021-05-28 - १२:५३
 */
public class SendEmailResponse {
    List<UserEmail> usersWithValidEmail;
    List<UserEmail> usersWithInvalidEmail;

    public SendEmailResponse() {
        usersWithValidEmail = new ArrayList<>();
        usersWithInvalidEmail = new ArrayList<>();
    }

    public SendEmailResponse(List<UserEmail> usersWithValidEmail, List<UserEmail> usersWithInvalidEmail) {
        this.usersWithValidEmail = usersWithValidEmail;
        this.usersWithInvalidEmail = usersWithInvalidEmail;
    }

    public List<UserEmail> getUsersWithValidEmail() {
        return usersWithValidEmail;
    }

    public void setUsersWithValidEmail(List<UserEmail> usersWithValidEmail) {
        this.usersWithValidEmail = usersWithValidEmail;
    }

    public List<UserEmail> getUsersWithInvalidEmail() {
        return usersWithInvalidEmail;
    }

    public void setUsersWithInvalidEmail(List<UserEmail> usersWithInvalidEmail) {
        this.usersWithInvalidEmail = usersWithInvalidEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SendEmailResponse that = (SendEmailResponse) o;
        return Objects.equals(usersWithValidEmail, that.usersWithValidEmail) && Objects.equals(usersWithInvalidEmail, that.usersWithInvalidEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usersWithValidEmail, usersWithInvalidEmail);
    }
}
