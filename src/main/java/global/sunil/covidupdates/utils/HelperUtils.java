package global.sunil.covidupdates.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Sunil on 2021-05-25 - १३:५८
 */
public class HelperUtils {

    private HelperUtils() {}

    public static String getTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss");
        return simpleDateFormat.format(new Date());
    }

    public static boolean isBlankOrNull(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }
}
