package global.sunil.covidupdates.lib.restclient;


public class HttpClientResponse<T> {
  private int code;
  private T data;

  public HttpClientResponse(int code, T data) {
    this.code = code;
    this.data = data;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }
}
