package global.sunil.covidupdates.lib.utils;

import java.util.logging.Logger;

public class LoggerUtils {

  public static Logger getLogger(Class<?> clazz) {
    return Logger.getLogger(clazz.getClass().getCanonicalName());
  }
}
