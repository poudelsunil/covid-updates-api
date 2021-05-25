package global.sunil.covidupdates.lib.mappers;

import global.sunil.covidupdates.utils.LoggerUtils;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;

@Provider
public class NullPointerExceptionMapper implements ExceptionMapper<NullPointerException> {

  private Logger logger = LoggerUtils.getLogger(NullPointerExceptionMapper.class);

  @Override
  public Response toResponse(NullPointerException exception) {
    Response response =
        Response.status(Response.Status.INTERNAL_SERVER_ERROR)
            .entity(toError(exception))
            .type(MediaType.APPLICATION_JSON_TYPE)
            .build();
    return response;
  }

  private JsonObject toError(NullPointerException ex) {
    return Json.createObjectBuilder()
        .add("code", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
        .add("message", "NULL REFERENCE IS MISSING")
        .build();
  }
}
