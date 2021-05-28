package global.sunil.covidupdates.repositories.dtos;

import global.sunil.covidupdates.lib.utils.ServiceObject;

import java.util.Objects;

/**
 * @author Sunil on 2021-05-28 - १२:५७
 */
public class SendEmailRequest implements ServiceObject {

    String subject;
    String message;
    Integer count;

    public SendEmailRequest() {
    }

    public SendEmailRequest(String subject, String message, Integer count) {
        this.subject = subject;
        this.message = message;
        this.count = count;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SendEmailRequest that = (SendEmailRequest) o;
        return Objects.equals(subject, that.subject) && Objects.equals(message, that.message) && Objects.equals(count, that.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, message, count);
    }
}