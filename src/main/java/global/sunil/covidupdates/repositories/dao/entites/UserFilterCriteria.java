package global.sunil.covidupdates.repositories.dao.entites;

import java.util.Objects;

/**
 * @author Sunil on 2021-05-28 - ११:५७
 */
public class UserFilterCriteria {

    private Long id;
    private String name;
    private Boolean isEmailValid;
    private Integer size;

    public UserFilterCriteria() {
    }

    public UserFilterCriteria(Long id, String name, Boolean isEmailValid, Integer size) {
        this.id = id;
        this.name = name;
        this.isEmailValid = isEmailValid;
        this.size = size;
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

    public Boolean getEmailValid() {
        return isEmailValid;
    }

    public void setEmailValid(Boolean emailValid) {
        isEmailValid = emailValid;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserFilterCriteria that = (UserFilterCriteria) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(isEmailValid, that.isEmailValid) && Objects.equals(size, that.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, isEmailValid, size);
    }
}
