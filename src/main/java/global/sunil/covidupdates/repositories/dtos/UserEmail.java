package global.sunil.covidupdates.repositories.dtos;

import global.sunil.covidupdates.lib.utils.ServiceObject;

import java.util.Objects;

/**
 * @author Sunil on 2021-05-28 - १२:५१
 */
public class UserEmail implements ServiceObject {
    private String name;
    private String email;

    public UserEmail() {
    }

    public UserEmail(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEmail userEmail = (UserEmail) o;
        return Objects.equals(name, userEmail.name) && Objects.equals(email, userEmail.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email);
    }
}
