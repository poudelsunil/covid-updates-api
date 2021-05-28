package global.sunil.covidupdates.repositories;

import global.sunil.covidupdates.lib.exceptions.ExceptionType;
import global.sunil.covidupdates.lib.mappers.AppException;

/**
 * @author Sunil on 2021-05-27 - १३:२५
 */
public class ExceptionManager extends AppException {
    public ExceptionManager(ExceptionType exceptionType) {
        super(exceptionType);
    }

    public static void throwException(ExceptionType exceptionType){
        throw new AppException(exceptionType);
    }

    public enum CovidUpdatesError implements ExceptionType {
        COULD_NOT_GET_COUNTRIES("CVD001", "Could not get countries."),
        COULD_NOT_GET_COUNTRY_WISE_COVID_INFO("CVD002", "Could not get country wise covid info."),

        COUNTRY_ISO2_IS_MISSING("CVD003", "Country iso2 is missing."),
        COUNTRY_ISO2_IS_INVALID("CVD003", "Country iso2 is invalid."),

        ;
        private final String code;
        private final String description;

        CovidUpdatesError(String code, String description) {
            this.code = code;
            this.description = description;
        }

        @Override
        public String getCode() {
            return this.code;
        }

        @Override
        public String getDescription() {
            return this.description;
        }
    }

    public static void throwCountriesNotFoundExceptionWithCustomMessage(String message){
        throw new AppException(new ExceptionType() {
            @Override
            public String getCode() {
                return CovidUpdatesError.COULD_NOT_GET_COUNTRIES.code;
            }

            @Override
            public String getDescription() {
                return message;
            }
        });
    }

    public static void throwCountryWiseCovidInfoNotFoundExceptionWithCustomMessage(String message){
        throw new AppException(new ExceptionType() {
            @Override
            public String getCode() {
                return CovidUpdatesError.COULD_NOT_GET_COUNTRY_WISE_COVID_INFO.code;
            }

            @Override
            public String getDescription() {
                return message;
            }
        });
    }


    public enum UserError implements ExceptionType {

        EMAIL_SUBJECT_IS_MISSING("URS001", "Email subject is missing."),
        MESSAGE_IS_MISSING("URS002", "Email body is missing."),
        COULD_NOT_SEND_EMAIL("URS003", "Could not send email."),
        COULD_NOT_FOUND_USERS("URS004", "Could not found users."),

        EMAIL_IS_MISSING("URS005", "Email is missing."),
        EMAIL_IS_INVALID("URS006", "Email is invalid."),
        NAME_IS_MISSING("URS007", "Name is missing."),
        COULD_NOT_ADD_USER("URS008", "Could not add user."),

        ;
        private final String code;
        private final String description;

        UserError(String code, String description) {
            this.code = code;
            this.description = description;
        }

        @Override
        public String getCode() {
            return this.code;
        }

        @Override
        public String getDescription() {
            return this.description;
        }
    }

    public enum EmailSenderError implements ExceptionType {

        SMTP_USER_NAME_IS_MISSING("EML001", "Smtp user name is missing."),
        SMTP_USER_PASSWORD_IS_MISSING("EML002", "Smtp user password is missing."),

        ;
        private final String code;
        private final String description;

        EmailSenderError(String code, String description) {
            this.code = code;
            this.description = description;
        }

        @Override
        public String getCode() {
            return this.code;
        }

        @Override
        public String getDescription() {
            return this.description;
        }
    }
}