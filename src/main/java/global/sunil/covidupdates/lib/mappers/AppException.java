package global.sunil.covidupdates.lib.mappers;

import global.sunil.covidupdates.lib.exceptions.CovidUpdateException;
import global.sunil.covidupdates.lib.exceptions.ExceptionType;
import global.sunil.covidupdates.utils.HelperUtils;

import javax.json.Json;
import javax.json.JsonObject;

public class AppException extends CovidUpdateException {

  public AppException(ExceptionType exceptionType) {
    super(exceptionType);
  }

  public JsonObject toJson() {
    return Json.createObjectBuilder()
        .add("time", HelperUtils.getTime())
        .add("code", this.getExceptionType().getCode())
        .add("message", this.getExceptionType().getDescription())
        .build();
  }
}
