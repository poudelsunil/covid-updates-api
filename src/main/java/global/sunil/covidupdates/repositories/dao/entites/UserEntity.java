package global.sunil.covidupdates.repositories.dao.entites;

import java.util.Objects;

/**
 * @author Sunil on 2021-05-27 - २२:३०
 */
public class UserEntity {

    private Long id;
    private String name;
    private String email;
    private Boolean isEmailValid;

    public UserEntity(Long id, String name, String email, Boolean isEmailValid) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.isEmailValid = isEmailValid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getEmailValid() {
        return isEmailValid;
    }

    public void setEmailValid(Boolean emailValid) {
        isEmailValid = emailValid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return /*Objects.equals(id, that.id) &&*/ Objects.equals(name, that.name) && Objects.equals(email, that.email) && Objects.equals(isEmailValid, that.isEmailValid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(/*id,*/ name, email, isEmailValid);
    }
}
