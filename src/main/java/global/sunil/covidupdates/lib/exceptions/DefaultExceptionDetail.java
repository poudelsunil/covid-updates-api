package global.sunil.covidupdates.lib.exceptions;

/**
 * @author Sunil on 2021-05-25 - १३:५७
 */
public class DefaultExceptionDetail implements ExceptionDetail {

    private String statusCode;
    private String description;

    public DefaultExceptionDetail(String statusCode, String description) {
        this.statusCode = statusCode;
        this.description = description;
    }

    @Override
    public String getStatusCode() {
        return statusCode;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
