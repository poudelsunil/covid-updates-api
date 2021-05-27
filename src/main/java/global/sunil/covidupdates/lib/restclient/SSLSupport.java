package global.sunil.covidupdates.lib.restclient;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class SSLSupport {
  private static TrustManager[] trustAllCerts =
      new TrustManager[] {
        new X509TrustManager() {
          public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
          }

          public void checkClientTrusted(
              java.security.cert.X509Certificate[] certs, String authType) {}

          public void checkServerTrusted(
              java.security.cert.X509Certificate[] certs, String authType) {}
        }
      };

  private SSLSupport() {}

  public static SSLContext getSslContext() {
    SSLContext sslContext = null;
    try {
      sslContext = SSLContext.getInstance("TLS");
      sslContext.init(null, trustAllCerts, new SecureRandom());
    } catch (NoSuchAlgorithmException | KeyManagementException e) {
      e.printStackTrace();
    }
    return sslContext;
  }
}
