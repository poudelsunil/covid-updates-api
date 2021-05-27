package global.sunil.covidupdates.lib.utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;
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

    public static double formatNumber(double num) {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return Double.valueOf( df.format(num) );
    }

}
