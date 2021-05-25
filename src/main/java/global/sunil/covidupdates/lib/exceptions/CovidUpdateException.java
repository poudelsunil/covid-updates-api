package global.sunil.covidupdates.lib.exceptions;

/**
 * @author Sunil on 2021-05-25 - १३:५३
 */
public class CovidUpdateException extends RuntimeException {

    protected ExceptionType exceptionType;

    public CovidUpdateException(ExceptionType exceptionType) {
        super(exceptionType.getDescription());
        this.exceptionType = exceptionType;
    }

    public ExceptionType getExceptionType() {
        return exceptionType;
    }
}