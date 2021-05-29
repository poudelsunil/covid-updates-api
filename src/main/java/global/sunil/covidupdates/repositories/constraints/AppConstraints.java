package global.sunil.covidupdates.repositories.constraints;

import java.util.regex.Pattern;

/**
 * @author Sunil on 2021-05-28 - १६:२१
 */
public class AppConstraints {

    public static final String VALID_EMAIL_ADDRESS_REGEX = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
    public static final int MAX_EMAIL_RECIPIENTS_NUMBERS = 50;
    public static final int MAX_EMAIL_SUBJECT_LENGTH = 998;
    public static final int MAX_EMAIL_BODY_LENGTH = 4000;

}
