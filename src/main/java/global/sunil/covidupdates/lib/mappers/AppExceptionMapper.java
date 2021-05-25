package global.sunil.covidupdates.lib.mappers;


import global.sunil.covidupdates.lib.exceptions.ExceptionType;
import global.sunil.covidupdates.utils.LoggerUtils;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
public class AppExceptionMapper implements ExceptionMapper<AppException> {

  private Logger logger = LoggerUtils.getLogger(AppExceptionMapper.class);

  @Override
  public Response toResponse(AppException exception) {
    Response response =
        Response.status(getStatus(exception.getExceptionType()))
            .entity(toError(exception))
            .type(MediaType.APPLICATION_JSON_TYPE)
            .build();
    logger.log(Level.SEVERE, "EXCEPTION RESPONSE :: ", exception.toJson().toString());
    return response;
  }

  private Status getStatus(ExceptionType exceptionType) {
    return Status.BAD_REQUEST;
  }

  private JsonObject toError(AppException ex) {
    return Json.createObjectBuilder()
        .add("code", ex.getExceptionType().getCode())
        .add("message", ex.getExceptionType().getDescription())
        .build();
  }
}
