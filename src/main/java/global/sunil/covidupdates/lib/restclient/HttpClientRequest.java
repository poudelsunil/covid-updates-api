package global.sunil.covidupdates.lib.restclient;

import global.sunil.covidupdates.lib.utils.HelperUtils;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class HttpClientRequest<T> {
  private String url;
  private Map<String, String> headers;
  private String body;
  private Duration duration;

  private HttpClientRequest(String url, Map<String, String> headers) {
    this.url = url;
    this.headers = headers;
  }

  public String getUrl() {
    return url;
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  public String getBody() {
    return body;
  }

  public Duration getDuration() {
    return duration;
  }

  public static class Builder<T> {
    public String url;
    public Map<String, String> headers;
    public String body;
    public Duration duration;

    public Builder with(Consumer<Builder> consumer) {
      consumer.accept(this);
      return this;
    }

    public HttpClientRequest build() {
      if (this.headers == null) this.headers = new HashMap<>();
      HttpClientRequest request = new HttpClientRequest(this.url, this.headers);
      if (this.duration == null) this.duration = Duration.ofMinutes(5);
      request.duration = this.duration;
      if (!HelperUtils.isBlankOrNull(this.body)) request.body = this.body;

      return request;
    }
  }
}
